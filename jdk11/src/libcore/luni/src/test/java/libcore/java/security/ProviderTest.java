/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package libcore.java.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.NoSuchPaddingException;
import libcore.javax.crypto.MockKey;
import libcore.junit.junit3.TestCaseWithRules;
import libcore.junit.util.EnableDeprecatedBouncyCastleAlgorithmsRule;
import org.junit.Rule;
import org.junit.rules.TestRule;

public class ProviderTest extends TestCaseWithRules {

    // Allow access to deprecated BC algorithms in this test, so we can ensure they
    // continue to work
    @Rule
    public TestRule enableDeprecatedBCAlgorithmsRule =
            EnableDeprecatedBouncyCastleAlgorithmsRule.getInstance();

    private static final boolean LOG_DEBUG = false;

    /**
     * Makes sure all all expected implementations (but not aliases)
     * and that there are no extras, according to what we expect from
     * StandardNames
     */
    public void test_Provider_getServices() throws Exception {
        // build set of expected algorithms
        Map<String,Set<String>> remainingExpected
                = new HashMap<String,Set<String>>(StandardNames.PROVIDER_ALGORITHMS);
        for (Entry<String,Set<String>> entry : remainingExpected.entrySet()) {
            entry.setValue(new HashSet<String>(entry.getValue()));
        }

        List<String> extra = new ArrayList<String>();
        List<String> missing = new ArrayList<String>();

        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            String providerName = provider.getName();
            // ignore BouncyCastle provider if it is installed on the RI
            if (StandardNames.IS_RI && providerName.equals("BC")) {
                continue;
            }
            Set<Provider.Service> services = provider.getServices();
            assertNotNull(services);
            assertFalse(services.isEmpty());
            if (LOG_DEBUG) {
                Set<Provider.Service> originalServices = services;
                services = new TreeSet<Provider.Service>(
                        new Comparator<Provider.Service>() {
                            public int compare(Provider.Service a, Provider.Service b) {
                                int typeCompare = a.getType().compareTo(b.getType());
                                if (typeCompare != 0) {
                                    return typeCompare;
                                }
                                return a.getAlgorithm().compareTo(b.getAlgorithm());
                            }
                        });
                services.addAll(originalServices);
            }

            for (Provider.Service service : services) {
                String type = service.getType();
                String algorithm = service.getAlgorithm().toUpperCase();
                String className = service.getClassName();
                if (LOG_DEBUG) {
                    System.out.println(providerName
                                       + " " + type
                                       + " " + algorithm
                                       + " " + className);
                }

                // remove from remaining, assert unknown if missing
                Set<String> remainingAlgorithms = remainingExpected.get(type);
                if (remainingAlgorithms == null || !remainingAlgorithms.remove(algorithm)) {
                    // seems to be missing, but sometimes the same
                    // algorithm is available from multiple providers
                    // (e.g. KeyFactory RSA is available from
                    // SunRsaSign and SunJSSE), so double check in
                    // original source before giving error
                    if (!(StandardNames.PROVIDER_ALGORITHMS.containsKey(type)
                            && StandardNames.PROVIDER_ALGORITHMS.get(type).contains(algorithm))) {
                        extra.add("Unknown " + type + " " + algorithm + " " + providerName + "\n");
                    }
                } else if ("Cipher".equals(type) && !algorithm.contains("/")) {
                    /*
                     * Cipher selection follows special rules where you can
                     * specify the mode and padding during the getInstance call.
                     * Try to see if the service supports this.
                     */
                    Set<String> toRemove = new HashSet<String>();
                    for (String remainingAlgo : remainingAlgorithms) {
                        String[] parts = remainingAlgo.split("/");
                        if (parts.length == 3 && algorithm.equals(parts[0])) {
                            try {
                                Cipher.getInstance(remainingAlgo, provider);
                                toRemove.add(remainingAlgo);
                            } catch (NoSuchAlgorithmException ignored) {
                            } catch (NoSuchPaddingException ignored) {
                            }
                        }
                    }
                    remainingAlgorithms.removeAll(toRemove);
                }

                // make sure class exists and can be initialized
                try {
                    assertNotNull(Class.forName(className,
                                                true,
                                                provider.getClass().getClassLoader()));
                } catch (ClassNotFoundException e) {
                    // Sun forgot their own class
                    if (!className.equals("sun.security.pkcs11.P11MAC")) {
                        missing.add(className);
                    }
                }
            }

            // last chance: some algorithms might only be provided by their alias
            remainingExpected.entrySet()
                .forEach(entry ->
                    entry.getValue()
                        .removeIf(algorithm ->
                            provider.getService(entry.getKey(), algorithm) != null)
            );
        }

        // assert that we don't have any extra in the implementation
        Collections.sort(extra); // sort so that its grouped by type
        assertEquals("Algorithms are provided but not present in StandardNames",
                Collections.EMPTY_LIST, extra);

        if (remainingExpected.containsKey("Cipher")) {
            // For any remaining ciphers, they may be aliases for other ciphers or otherwise
            // don't show up as a service but can still be instantiated.
            for (Iterator<String> cipherIt = remainingExpected.get("Cipher").iterator();
                    cipherIt.hasNext(); ) {
                String missingCipher = cipherIt.next();
                try {
                    Cipher.getInstance(missingCipher);
                    cipherIt.remove();
                } catch (NoSuchAlgorithmException|NoSuchPaddingException e) {
                }
            }
        }

        remainingExpected.entrySet()
            .removeIf(entry ->
                entry.getValue().isEmpty());

        // assert that we don't have any missing in the implementation
        assertEquals("Algorithms are present in StandardNames but not provided",
                Collections.EMPTY_MAP, remainingExpected);

        // assert that we don't have any missing classes
        Collections.sort(missing); // sort it for readability
        assertEquals("Missing classes", Collections.EMPTY_LIST, missing);
    }

    // This tests the CDD requirement that specifies the first seven security providers
    // (section 3.5, [C-0-9] as of P).
    public void testProviderList() {
        Provider[] providers = Security.getProviders();
        assertTrue(providers.length >= 7);
        assertProviderProperties(providers[0], "AndroidNSSP",
            "android.security.net.config.NetworkSecurityConfigProvider");
        assertProviderProperties(providers[1], "AndroidOpenSSL",
            "com.android.org.conscrypt.OpenSSLProvider");
        assertProviderProperties(providers[2], "CertPathProvider",
            "sun.security.provider.CertPathProvider");
        assertProviderProperties(providers[3], "AndroidKeyStoreBCWorkaround",
            "android.security.keystore2.AndroidKeyStoreBCWorkaroundProvider");
        assertProviderProperties(providers[4], "BC",
            "com.android.org.bouncycastle.jce.provider.BouncyCastleProvider");
        assertProviderProperties(providers[5], "HarmonyJSSE",
            "com.android.org.conscrypt.JSSEProvider");
        assertProviderProperties(providers[6], "AndroidKeyStore",
            "android.security.keystore2.AndroidKeyStoreProvider");
    }

    private void assertProviderProperties(Provider p, String name, String className) {
        assertEquals(name, p.getName());
        assertEquals(className, p.getClass().getName());
    }

    private static final Pattern alias = Pattern.compile("Alg\\.Alias\\.([^.]*)\\.(.*)");

    /**
     * Makes sure all provider properties either point to a class
     * implementation that exists or are aliases to known algorithms.
     */
    public void test_Provider_Properties() throws Exception {
        /*
         * A useful reference on Provider properties
         * <a href="http://java.sun.com/javase/6/docs/technotes/guides/security/crypto/HowToImplAProvider.html">
         * How to Implement a Provider in the Java &trade; Cryptography Architecture
         * </a>
         */

        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            // check Provider.id proprieties
            assertEquals(provider.getName(),
                         provider.get("Provider.id name"));
            assertEquals(String.valueOf(provider.getVersion()),
                         provider.get("Provider.id version"));
            assertEquals(provider.getInfo(),
                         provider.get("Provider.id info"));
            assertEquals(provider.getClass().getName(),
                         provider.get("Provider.id className"));

            // build map of all known aliases and implementations
            Map<String,String> aliases = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            Map<String,String> implementations = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            for (Entry<Object,Object> entry : provider.entrySet()) {
                Object k = entry.getKey();
                Object v = entry.getValue();
                assertEquals(String.class, k.getClass());
                assertEquals(String.class, v.getClass());
                String key = (String)k;
                String value = (String)v;

                // skip Provider.id keys, we check well known ones values above
                if (key.startsWith("Provider.id ")) {
                    continue;
                }

                // skip property settings such as: "Signature.SHA1withDSA ImplementedIn" "Software"
                if (key.indexOf(' ') != -1) {
                    continue;
                }

                Matcher m = alias.matcher(key);
                if (m.find()) {
                    String type = m.group(1);
                    aliases.put(key, type + "." + value);
                } else {
                    implementations.put(key, value);
                }
            }

            // verify implementation classes are available
            for (Entry<String,String> entry : implementations.entrySet()) {
                String typeAndAlgorithm = entry.getKey();
                String className = entry.getValue();
                try {
                    assertNotNull(Class.forName(className,
                                                true,
                                                provider.getClass().getClassLoader()));
                } catch (ClassNotFoundException e) {
                    // Sun forgot their own class
                    if (!className.equals("sun.security.pkcs11.P11MAC")) {
                        fail("Could not find class " + className + " for " + typeAndAlgorithm
                        + " [provider=" + provider.getName() + "]");
                    }
                }
            }

            // make sure all aliases point to some known implementation
            for (Entry<String,String> entry : aliases.entrySet()) {
                String alias  = entry.getKey();
                String actual = entry.getValue();
                assertTrue("Could not find implementation " + actual + " for alias " + alias +
                        " [provider=" + provider.getName() + "]",
                        implementations.containsKey(actual));
            }
        }
    }

    /**
     * Helper function to fetch services for Service.Algorithm IDs
     */
    private static Provider.Service getService(Provider p, String id) {
        String[] typeAndAlg = id.split("\\.", 2);
        assertEquals(id + " is not formatted as expected.", 2, typeAndAlg.length);
        return p.getService(typeAndAlg[0], typeAndAlg[1]);
    }

    /**
     * Identifiers provided by Bouncy Castle that we exclude from consideration
     * when checking that all Bouncy Castle identifiers are also covered by Conscrypt.
     * Each block of excluded identifiers is preceded by the justification specific
     * to those IDs.
     */
    private static final Set<String> BC_OVERRIDE_EXCEPTIONS = new HashSet<>();
    static {
        // A typo caused Bouncy Castle to accept these incorrect OIDs for AES, and they
        // maintain these aliases for backwards compatibility.  We don't want to continue
        // this in Conscrypt.
        BC_OVERRIDE_EXCEPTIONS.add("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.2");
        BC_OVERRIDE_EXCEPTIONS.add("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.22");
        BC_OVERRIDE_EXCEPTIONS.add("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.42");

        // BC uses the same class to implement AlgorithmParameters.DES and
        // AlgorithmParameters.DESEDE.  Conscrypt doesn't support DES, so it doesn't
        // include an implementation of AlgorithmParameters.DES, and this isn't a problem.
        BC_OVERRIDE_EXCEPTIONS.add("AlgorithmParameters.DES");
        BC_OVERRIDE_EXCEPTIONS.add("Alg.Alias.AlgorithmParameters.1.3.14.3.2.7");
        BC_OVERRIDE_EXCEPTIONS.add("Alg.Alias.AlgorithmParameters.OID.1.3.14.3.2.7");
    }

    /**
     * Ensures that, for all algorithms provided by Conscrypt, there is no alias from
     * the BC provider that's not provided by Conscrypt.  If there is, then a request
     * for that alias with no provider specified will return the BC implementation of
     * it even though we have a Conscrypt implementation available.
     */
    public void test_Provider_ConscryptOverridesBouncyCastle() throws Exception {
        if (StandardNames.IS_RI) {
            // These providers aren't installed on RI
            return;
        }
        Provider conscrypt = Security.getProvider("AndroidOpenSSL");
        Provider bc = Security.getProvider("BC");

        // 1. Find all the algorithms provided by Conscrypt.
        Set<String> conscryptAlgs = new HashSet<>();
        for (Entry<Object, Object> entry : conscrypt.entrySet()) {
            String key = (String) entry.getKey();
            if (key.contains(" ")) {
                // These are implementation properties like "Provider.id name"
                continue;
            }
            if (key.startsWith("Alg.Alias.")) {
                // Ignore aliases, we only want the concrete algorithms
                continue;
            }
            conscryptAlgs.add(key);
        }

        // 2. Determine which classes in BC implement those algorithms
        Set<String> bcClasses = new HashSet<>();
        for (String conscryptAlg : conscryptAlgs) {
            Provider.Service service = getService(bc, conscryptAlg);
            if (service != null) {
                bcClasses.add(service.getClassName());
            }
        }
        assertTrue(bcClasses.size() > 0);

        // 3. Determine which IDs in BC point to that set of classes
        Set<String> shouldBeOverriddenBcIds = new HashSet<>();
        for (Object keyObject : bc.keySet()) {
            String key = (String) keyObject;
            if (key.contains(" ")) {
                continue;
            }
            if (BC_OVERRIDE_EXCEPTIONS.contains(key)) {
                continue;
            }
            if (key.startsWith("Alg.Alias.")) {
                key = key.substring("Alg.Alias.".length());
            }
            Provider.Service service = getService(bc, key);
            if (bcClasses.contains(service.getClassName())) {
                shouldBeOverriddenBcIds.add(key);
            }
        }
        assertTrue(shouldBeOverriddenBcIds.size() > 0);

        // 4. Check each of those IDs to ensure that it's present in Conscrypt
        Set<String> nonOverriddenIds = new TreeSet<>();
        for (String shouldBeOverridenBcId : shouldBeOverriddenBcIds) {
            if (getService(conscrypt, shouldBeOverridenBcId) == null) {
                nonOverriddenIds.add(shouldBeOverridenBcId);
            }
        }
        assertTrue("Conscrypt does not provide IDs " + nonOverriddenIds
                + ", but it does provide other IDs that point to the same implementation(s)"
                + " in BouncyCastle.",
                nonOverriddenIds.isEmpty());
    }

    private static final String[] TYPES_SERVICES_CHECKED = new String[] {
            "KeyFactory", "CertPathBuilder", "Cipher", "SecureRandom",
            "AlgorithmParameterGenerator", "Signature", "KeyPairGenerator", "CertificateFactory",
            "MessageDigest", "KeyAgreement", "CertStore", "SSLContext", "AlgorithmParameters",
            "TrustManagerFactory", "KeyGenerator", "Mac", "CertPathValidator", "SecretKeyFactory",
            "KeyManagerFactory", "KeyStore",
    };

    private static final HashSet<String> TYPES_SUPPORTS_PARAMETER = new HashSet<String>(
            Arrays.asList(new String[] {
                    "Mac", "KeyAgreement", "Cipher", "Signature",
            }));

    private static final HashSet<String> TYPES_NOT_SUPPORTS_PARAMETER = new HashSet<String>(
            Arrays.asList(TYPES_SERVICES_CHECKED));
    static {
        TYPES_NOT_SUPPORTS_PARAMETER.removeAll(TYPES_SUPPORTS_PARAMETER);
    }

    public void test_Provider_getServices_supportsParameter() throws Exception {
        HashSet<String> remainingTypes = new HashSet<String>(Arrays.asList(TYPES_SERVICES_CHECKED));

        HashSet<String> supportsParameterTypes = new HashSet<String>();
        HashSet<String> noSupportsParameterTypes = new HashSet<String>();

        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            Set<Provider.Service> services = provider.getServices();
            assertNotNull(services);
            assertFalse(services.isEmpty());

            for (Provider.Service service : services) {
                final String type = service.getType();
                remainingTypes.remove(type);
                try {
                    service.supportsParameter(new MockKey());
                    supportsParameterTypes.add(type);
                } catch (InvalidParameterException e) {
                    noSupportsParameterTypes.add(type);
                    try {
                        service.supportsParameter(new Object());
                        fail("Should throw on non-Key parameter");
                    } catch (InvalidParameterException expected) {
                    }
                }
            }
        }

        supportsParameterTypes.retainAll(TYPES_SUPPORTS_PARAMETER);
        assertEquals("Types that should support parameters", TYPES_SUPPORTS_PARAMETER,
                supportsParameterTypes);

        noSupportsParameterTypes.retainAll(TYPES_NOT_SUPPORTS_PARAMETER);
        assertEquals("Types that should not support parameters", TYPES_NOT_SUPPORTS_PARAMETER,
                noSupportsParameterTypes);

        assertEquals("Types that should be checked", Collections.EMPTY_SET, remainingTypes);
    }

    public static class MockSpi {
        public Object parameter;

        public MockSpi(MockKey parameter) {
            this.parameter = parameter;
        }
    };

    @SuppressWarnings("serial")
    public void testProviderService_supportsParameter_UnknownService_Success() throws Exception {
        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("Fake.FOO", MockSpi.class.getName());
            }
        };

        Security.addProvider(provider);
        try {
            Provider.Service service = provider.getService("Fake", "FOO");
            assertTrue(service.supportsParameter(new Object()));
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_supportsParameter_KnownService_NoClassInitialization_Success()
            throws Exception {
        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("Signature.FOO", MockSpi.class.getName());
                put("Signature.FOO SupportedKeyClasses", getClass().getName()
                        + ".UninitializedMockKey");
            }
        };

        Security.addProvider(provider);
        try {
            Provider.Service service = provider.getService("Signature", "FOO");
            assertFalse(service.supportsParameter(new MockKey()));
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public static class UninitializedMockKey extends MockKey {
        static {
            fail("This should not be initialized");
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_supportsParameter_TypeDoesNotSupportParameter_Failure()
            throws Exception {
        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("KeyFactory.FOO", MockSpi.class.getName());
            }
        };

        Security.addProvider(provider);
        try {
            Provider.Service service = provider.getService("KeyFactory", "FOO");
            try {
                service.supportsParameter(new MockKey());
                fail("Should always throw exception");
            } catch (InvalidParameterException expected) {
            }
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_supportsParameter_SupportedKeyClasses_NonKeyClass_Success()
            throws Exception {
        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("Signature.FOO", MockSpi.class.getName());
                put("Signature.FOO SupportedKeyClasses", MockSpi.class.getName());
            }
        };

        Security.addProvider(provider);
        try {
            Provider.Service service = provider.getService("Signature", "FOO");
            assertFalse(service.supportsParameter(new MockKey()));
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_supportsParameter_KnownService_NonKey_Failure()
            throws Exception {
        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("Signature.FOO", MockSpi.class.getName());
            }
        };

        Security.addProvider(provider);
        try {
            Provider.Service service = provider.getService("Signature", "FOO");
            try {
                service.supportsParameter(new Object());
                fail("Should throw when non-Key passed in");
            } catch (InvalidParameterException expected) {
            }
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_supportsParameter_KnownService_SupportedKeyClasses_NonKey_Failure()
            throws Exception {
        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("Signature.FOO", MockSpi.class.getName());
                put("Signature.FOO SupportedKeyClasses", RSAPrivateKey.class.getName());
            }
        };

        Security.addProvider(provider);
        try {
            Provider.Service service = provider.getService("Signature", "FOO");
            try {
                service.supportsParameter(new Object());
                fail("Should throw on non-Key instance passed in");
            } catch (InvalidParameterException expected) {
            }
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_supportsParameter_KnownService_Null_Failure() throws Exception {
        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("Signature.FOO", MockSpi.class.getName());
                put("Signature.FOO SupportedKeyClasses", RSAPrivateKey.class.getName());
            }
        };

        Security.addProvider(provider);
        try {
            Provider.Service service = provider.getService("Signature", "FOO");
            assertFalse(service.supportsParameter(null));
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_supportsParameter_SupportedKeyClasses_Success()
            throws Exception {
        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("Signature.FOO", MockSpi.class.getName());
                put("Signature.FOO SupportedKeyClasses", MockKey.class.getName());
            }
        };

        Security.addProvider(provider);
        try {
            Provider.Service service = provider.getService("Signature", "FOO");
            assertTrue(service.supportsParameter(new MockKey()));
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_supportsParameter_SupportedKeyClasses_Failure()
            throws Exception {
        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("Signature.FOO", MockSpi.class.getName());
                put("Signature.FOO SupportedKeyClasses", RSAPrivateKey.class.getName());
            }
        };

        Security.addProvider(provider);
        try {
            Provider.Service service = provider.getService("Signature", "FOO");
            assertFalse(service.supportsParameter(new MockKey()));
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_supportsParameter_SupportedKeyFormats_Success()
            throws Exception {
        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("Signature.FOO", MockSpi.class.getName());
                put("Signature.FOO SupportedKeyFormats", new MockKey().getFormat());
            }
        };

        Security.addProvider(provider);
        try {
            Provider.Service service = provider.getService("Signature", "FOO");
            assertTrue(service.supportsParameter(new MockKey()));
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_supportsParameter_SupportedKeyFormats_Failure()
            throws Exception {
        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("Signature.FOO", MockSpi.class.getName());
                put("Signature.FOO SupportedKeyFormats", "Invalid");
            }
        };

        Security.addProvider(provider);
        try {
            Provider.Service service = provider.getService("Signature", "FOO");
            assertFalse(service.supportsParameter(new MockKey()));
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_newInstance_DoesNotCallSupportsParameter_Success()
            throws Exception {
        MockProvider provider = new MockProvider("MockProvider");

        provider.putServiceForTest(new Provider.Service(provider, "CertStore", "FOO",
                MyCertStoreSpi.class.getName(), null, null) {
            @Override
            public boolean supportsParameter(Object parameter) {
                fail("This should not be called");
                return false;
            }
        });

        Security.addProvider(provider);
        try {
            Provider.Service service = provider.getService("CertStore", "FOO");
            assertNotNull(service.newInstance(new MyCertStoreParameters()));
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_newInstance_PrivateClass_throws()
            throws Exception {
        MockProvider provider = new MockProvider("MockProvider");

        provider.putServiceForTest(new Provider.Service(provider, "CertStore", "FOO",
                CertStoreSpiPrivateClass.class.getName(), null, null));

        Security.addProvider(provider);
        // The class for the service is private, it must fail with NoSuchAlgorithmException
        try {
            Provider.Service service = provider.getService("CertStore", "FOO");
            service.newInstance(null);
            fail();
        } catch (NoSuchAlgorithmException expected) {
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_newInstance_PrivateEmptyConstructor_throws()
            throws Exception {
        MockProvider provider = new MockProvider("MockProvider");

        provider.putServiceForTest(new Provider.Service(provider, "CertStore", "FOO",
                CertStoreSpiPrivateEmptyConstructor.class.getName(), null, null));

        Security.addProvider(provider);
        // The empty constructor is private, it must fail with NoSuchAlgorithmException
        try {
            Provider.Service service = provider.getService("CertStore", "FOO");
            service.newInstance(null);
            fail();
        } catch (NoSuchAlgorithmException expected) {
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_AliasDoesNotEraseCanonical_Success()
            throws Exception {
        // Make sure we start with a "known good" alias for this OID.
        {
            EncryptedPrivateKeyInfo epki1 = new EncryptedPrivateKeyInfo("OID.1.2.840.113549.1.1.5",
                    new byte[1]);
            assertEquals("SHA1WITHRSA", epki1.getAlgName().toUpperCase(Locale.US));
        }

        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("Signature.FOO", MockSpi.class.getName());
                put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.5", "FOO");
            }
        };

        Security.addProvider(provider);
        try {
            // This triggers a re-indexing of the algorithm id data:
            try {
                new EncryptedPrivateKeyInfo("nonexistent", new byte[1]);
                fail("Should not find 'nonexistent' algorithm");
            } catch (NoSuchAlgorithmException expected) {
            }

            EncryptedPrivateKeyInfo epki2 = new EncryptedPrivateKeyInfo("OID.1.2.840.113549.1.1.5", new byte[1]);
            assertEquals("SHA1WITHRSA", epki2.getAlgName().toUpperCase(Locale.US));
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    @SuppressWarnings("serial")
    public void testProviderService_CanFindNewOID_Success()
            throws Exception {
        Provider provider = new MockProvider("MockProvider") {
            public void setup() {
                put("Signature.NEWALG", MockSpi.class.getName());
                put("Alg.Alias.Signature.OID.1.2.9999.9999.9999", "NEWALG");
            }
        };

        Security.addProvider(provider);
        try {
            EncryptedPrivateKeyInfo epki2 = new EncryptedPrivateKeyInfo("OID.1.2.9999.9999.9999", new byte[1]);
            assertEquals("NEWALG", epki2.getAlgName().toUpperCase(Locale.US));
        } finally {
            Security.removeProvider(provider.getName());
        }
    }

    public void testProvider_removeProvider_Success() throws Exception {
        MockProvider provider = new MockProvider("MockProvider");
        assertNull(Security.getProvider(provider.getName()));
        Security.addProvider(provider);
        assertNotNull(Security.getProvider(provider.getName()));
        Security.removeProvider(provider.getName());
        assertNull(Security.getProvider(provider.getName()));
    }

    public static class MyCertStoreSpi extends CertStoreSpi {
        public MyCertStoreSpi(CertStoreParameters params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        @Override
        public Collection<? extends Certificate> engineGetCertificates(CertSelector selector)
                throws CertStoreException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<? extends CRL> engineGetCRLs(CRLSelector selector)
                throws CertStoreException {
            throw new UnsupportedOperationException();
        }
    }

    private static class CertStoreSpiPrivateClass extends CertStoreSpi {
        public CertStoreSpiPrivateClass()
                throws InvalidAlgorithmParameterException {
            super(null);
        }

        @Override
        public Collection<? extends Certificate> engineGetCertificates(CertSelector selector)
                throws CertStoreException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<? extends CRL> engineGetCRLs(CRLSelector selector)
                throws CertStoreException {
            throw new UnsupportedOperationException();
        }
    }

    private static class CertStoreSpiPrivateEmptyConstructor extends CertStoreSpi {
        private CertStoreSpiPrivateEmptyConstructor(CertStoreParameters params)
                throws InvalidAlgorithmParameterException {
            super(null);
        }

        @Override
        public Collection<? extends Certificate> engineGetCertificates(CertSelector selector)
                throws CertStoreException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<? extends CRL> engineGetCRLs(CRLSelector selector)
                throws CertStoreException {
            throw new UnsupportedOperationException();
        }
    }


    public static class MyCertStoreParameters implements CertStoreParameters {
        public Object clone() {
            return new MyCertStoreParameters();
        }
    }

    /**
     * http://code.google.com/p/android/issues/detail?id=21449
     */
    public void testSecureRandomImplementationOrder() {
        @SuppressWarnings("serial")
        Provider srp = new MockProvider("SRProvider") {
            public void setup() {
                put("SecureRandom.SecureRandom1", SecureRandom1.class.getName());
                put("SecureRandom.SecureRandom2", SecureRandom2.class.getName());
                put("SecureRandom.SecureRandom3", SecureRandom3.class.getName());
            }
        };
        try {
            int position = Security.insertProviderAt(srp, 1); // first is one, not zero
            assertEquals(1, position);
            SecureRandom sr = new SecureRandom();
            if (!sr.getAlgorithm().equals("SecureRandom1")) {
                throw new IllegalStateException("Expected SecureRandom1 was " + sr.getAlgorithm());
            }
        } finally {
            Security.removeProvider(srp.getName());
        }
    }

    // TODO(29631070): this is a general testing mechanism to test other operations that are
    // going to be added.
    public void testHashMapOperations() {
        performHashMapOperationAndCheckResults(
                PUT /* operation */,
                mapOf("class1.algorithm1", "impl1") /* initialStatus */,
                new Pair("class2.algorithm2", "impl2") /* operationParameters */,
                mapOf("class1.algorithm1", "impl1",
                        "class2.algorithm2", "impl2"),
                true /* mustChangeSecurityVersion */);
        performHashMapOperationAndCheckResults(
                PUT_ALL,
                mapOf("class1.algorithm1", "impl1"),
                mapOf("class2.algorithm2", "impl2", "class3.algorithm3", "impl3"),
                mapOf("class1.algorithm1", "impl1",
                        "class2.algorithm2", "impl2",
                        "class3.algorithm3", "impl3"),
                true /* mustChangeSecurityVersion */);
        performHashMapOperationAndCheckResults(
                REMOVE,
                mapOf("class1.algorithm1", "impl1"),
                "class1.algorithm1",
                mapOf(),
                true /* mustChangeSecurityVersion */);
        performHashMapOperationAndCheckResults(
                REMOVE,
                mapOf("class1.algorithm1", "impl1"),
                "class2.algorithm1",
                mapOf("class1.algorithm1", "impl1"),
                true /* mustChangeSecurityVersion */);
        performHashMapOperationAndCheckResults(
                COMPUTE,
                mapOf("class1.algorithm1", "impl1"),
                // It's really difficult to find an example of this that sounds realistic
                // for a Provider...
                new Pair("class1.algorithm1", CONCAT),
                mapOf("class1.algorithm1", "class1.algorithm1impl1"),
                true);
        performHashMapOperationAndCheckResults(
                PUT_IF_ABSENT,
                mapOf("class1.algorithm1", "impl1"),
                new Pair("class1.algorithm1", "impl2"),
                // Don't put because key is absent.
                mapOf("class1.algorithm1", "impl1"),
                true);
        performHashMapOperationAndCheckResults(
                PUT_IF_ABSENT,
                mapOf("class1.algorithm1", "impl1"),
                new Pair("class2.algorithm2", "impl2"),
                mapOf("class1.algorithm1", "impl1", "class2.algorithm2", "impl2"),
                true);
        performHashMapOperationAndCheckResults(
                PUT_IF_ABSENT,
                mapOf("class1.algorithm1", "impl1"),
                new Pair("class2.algorithm2", "impl2"),
                mapOf("class1.algorithm1", "impl1", "class2.algorithm2", "impl2"),
                true);
        performHashMapOperationAndCheckResults(
                COMPUTE_IF_PRESENT,
                mapOf("class1.algorithm1", "impl1"),
                new Pair("class1.algorithm1", CONCAT),
                mapOf("class1.algorithm1", "class1.algorithm1impl1"),
                true);
        performHashMapOperationAndCheckResults(
                COMPUTE_IF_PRESENT,
                mapOf("class1.algorithm1", "impl1"),
                new Pair("class2.algorithm2", CONCAT),
                // Don't compute because is not present.
                mapOf("class1.algorithm1", "impl1"),
                true);
        performHashMapOperationAndCheckResults(
                COMPUTE_IF_ABSENT,
                mapOf("class1.algorithm1", "impl1"),
                new Pair("class2.algorithm2", TO_UPPER_CASE),
                mapOf("class1.algorithm1", "impl1", "class2.algorithm2", "CLASS2.ALGORITHM2"),
                true);
        performHashMapOperationAndCheckResults(
                COMPUTE_IF_ABSENT,
                mapOf("class1.algorithm1", "impl1"),
                new Pair("class1.algorithm1", TO_UPPER_CASE),
                // Don't compute because if not absent.
                mapOf("class1.algorithm1", "impl1"),
                true);
        performHashMapOperationAndCheckResults(
                REPLACE_USING_KEY,
                mapOf("class1.algorithm1", "impl1", "class2.algorithm2", "impl2"),
                new Pair("class1.algorithm1", "impl3"),
                mapOf("class1.algorithm1", "impl3", "class2.algorithm2", "impl2"),
                true);
        performHashMapOperationAndCheckResults(
                REPLACE_USING_KEY,
                mapOf("class1.algorithm1", "impl1", "class2.algorithm2", "impl2"),
                new Pair("class1.algorithm3", "impl3"),
                // Do not replace as the key is not present.
                mapOf("class1.algorithm1", "impl1", "class2.algorithm2", "impl2"),
                true);
        performHashMapOperationAndCheckResults(
                REPLACE_USING_KEY_AND_VALUE,
                mapOf("class1.algorithm1", "impl1", "class2.algorithm2", "impl2"),
                new Pair(new Pair("class1.algorithm1", "impl1"), "impl3"),
                mapOf("class1.algorithm1", "impl3", "class2.algorithm2", "impl2"),
                true);
        performHashMapOperationAndCheckResults(
                REPLACE_USING_KEY_AND_VALUE,
                mapOf("class1.algorithm1", "impl1", "class2.algorithm2", "impl2"),
                new Pair(new Pair("class1.algorithm1", "impl4"), "impl3"),
                // Do not replace as the key/value pair is not present.
                mapOf("class1.algorithm1", "impl1", "class2.algorithm2", "impl2"),
                true);
        performHashMapOperationAndCheckResults(
                REPLACE_ALL,
                mapOf("class1.algorithm1", "impl1", "class2.algorithm2", "impl2"),
                // Applying simply CONCAT will affect internal mappings of the provider (version,
                // info, name, etc)
                CONCAT_IF_STARTING_WITH_CLASS,
                mapOf("class1.algorithm1", "class1.algorithm1impl1",
                        "class2.algorithm2", "class2.algorithm2impl2"),
                true);
        performHashMapOperationAndCheckResults(
                MERGE,
                mapOf("class1.algorithm1", "impl1", "class2.algorithm2", "impl2"),
                new Pair(new Pair("class1.algorithm1", "impl3"), CONCAT),
                // The key is present, so the function is used.
                mapOf("class1.algorithm1", "impl1impl3",
                        "class2.algorithm2", "impl2"),
                true);
        performHashMapOperationAndCheckResults(
                MERGE,
                mapOf("class1.algorithm1", "impl1", "class2.algorithm2", "impl2"),
                new Pair(new Pair("class3.algorithm3", "impl3"), CONCAT),
                // The key is not present, so the value is used.
                mapOf("class1.algorithm1", "impl1",
                        "class2.algorithm2", "impl2",
                        "class3.algorithm3", "impl3"),
                true);
    }

    public void test_getOrDefault() {
        Provider p = new MockProvider("MockProvider");
        p.put("class1.algorithm1", "impl1");
        assertEquals("impl1", p.getOrDefault("class1.algorithm1", "default"));
        assertEquals("default", p.getOrDefault("thisIsNotInTheProvider", "default"));
    }

    public void test_elements() {
        Provider p = new MockProvider("MockProvider");
        p.put("class1.algorithm1", "impl1");
        Enumeration<Object> elements = p.elements();
        boolean isImpl1Found = false;
        while (elements.hasMoreElements()) {
            if ("impl1".equals(elements.nextElement())) {
                isImpl1Found = true;
                break;
            }
        }

        assertTrue("impl1 is not found.", isImpl1Found);
    }

    private static class Pair<A, B> {
        private final A first;
        private final B second;
        Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }
    }

    /* Holder class for the provider parameter and the parameter for the operation. */
    private static class ProviderAndOperationParameter<T> {
        private final Provider provider;
        private final T operationParameters;
        ProviderAndOperationParameter(Provider p, T o) {
            provider = p;
            operationParameters = o;
        }
    }

    private static final Consumer<ProviderAndOperationParameter<Pair<String, String>>> PUT =
            provAndParam ->
                    provAndParam.provider.put(
                            provAndParam.operationParameters.first,
                            provAndParam.operationParameters.second);

    private static final Consumer<ProviderAndOperationParameter<Map<String, String>>> PUT_ALL =
            provAndParam -> provAndParam.provider.putAll(provAndParam.operationParameters);

    private static final Consumer<ProviderAndOperationParameter<String>> REMOVE =
            provAndParam -> provAndParam.provider.remove(provAndParam.operationParameters);

    private static final Consumer<ProviderAndOperationParameter<
            Pair<String, BiFunction<Object, Object, Object>>>> COMPUTE =
                    provAndParam -> provAndParam.provider.compute(
                            provAndParam.operationParameters.first,
                            provAndParam.operationParameters.second);

    private static final BiFunction<Object, Object, Object> CONCAT =
            (a, b) -> Objects.toString(a) + Objects.toString(b);

    private static final Consumer<ProviderAndOperationParameter<Pair<String, String>>>
            PUT_IF_ABSENT = provAndParam ->
                    provAndParam.provider.putIfAbsent(
                            provAndParam.operationParameters.first,
                            provAndParam.operationParameters.second);

    private static final Consumer<ProviderAndOperationParameter<
            Pair<String, BiFunction<Object, Object, Object>>>> COMPUTE_IF_PRESENT =
                    provAndParam -> provAndParam.provider.computeIfPresent(
                            provAndParam.operationParameters.first,
                            provAndParam.operationParameters.second);

    private static final Consumer<ProviderAndOperationParameter<
            Pair<String, Function<Object, Object>>>> COMPUTE_IF_ABSENT =
                    provAndParam -> provAndParam.provider.computeIfAbsent(
                            provAndParam.operationParameters.first,
                            provAndParam.operationParameters.second);

    private static final Function<Object, Object> TO_UPPER_CASE =
            s -> Objects.toString(s).toUpperCase();

    private static final Consumer<ProviderAndOperationParameter<Pair<String, String>>>
            REPLACE_USING_KEY = provAndParam ->
                    provAndParam.provider.replace(
                            provAndParam.operationParameters.first,
                            provAndParam.operationParameters.second);

    private static final Consumer<ProviderAndOperationParameter<Pair<Pair<String, String>, String>>>
            REPLACE_USING_KEY_AND_VALUE = provAndParam ->
            provAndParam.provider.replace(
                    provAndParam.operationParameters.first.first,
                    provAndParam.operationParameters.first.second,
                    provAndParam.operationParameters.second);

    private static final Consumer<ProviderAndOperationParameter<
                BiFunction<Object, Object, Object>>> REPLACE_ALL =
                        provAndParam -> provAndParam.provider.replaceAll(
                                provAndParam.operationParameters);

    private static final BiFunction<Object, Object, Object> CONCAT_IF_STARTING_WITH_CLASS =
            (a, b) -> (Objects.toString(a).startsWith("class"))
                    ? Objects.toString(a) + Objects.toString(b)
                    : b;

    private static final Consumer<ProviderAndOperationParameter<
                    Pair<Pair<String, String>, BiFunction<Object, Object, Object>>>>
            MERGE = provAndParam -> provAndParam.provider.merge(
                    provAndParam.operationParameters.first.first,
                    provAndParam.operationParameters.first.second,
                    provAndParam.operationParameters.second);



    private static Map<String, String> mapOf(String... elements) {
        Map<String, String> ret = new HashMap<String, String>();
        for (int i = 0; i < elements.length; i += 2) {
            ret.put(elements[i], elements[i + 1]);
        }
        return ret;
    }


    private <A> void performHashMapOperationAndCheckResults(
            Consumer<ProviderAndOperationParameter<A>> operation,
            Map<String, String> initialState,
            A operationParameters,
            Map<String, String> expectedResult,
            boolean mustChangeVersion) {
        Provider p = new MockProvider("MockProvider");
        // Need to set as registered so that the security version will change on update.
        p.setRegistered();
        int securityVersionBeforeOperation = Security.getVersion();
        p.putAll(initialState);

        // Perform the operation.
        operation.accept(new ProviderAndOperationParameter<A>(p, operationParameters));

        // Check that elements are correctly mapped to services.
        HashMap<String, String> services = new HashMap<String, String>();
        for (Provider.Service s : p.getServices()) {
            services.put(s.getType() + "." + s.getAlgorithm(), s.getClassName());
        }
        assertEquals(expectedResult.entrySet(), services.entrySet());

        // Check that elements are in the provider hash map.
        // The hash map in the provider has info other than services, include those in the
        // expected results.
        HashMap<String, String> hashExpectedResult = new HashMap<String, String>();
        hashExpectedResult.putAll(expectedResult);
        hashExpectedResult.put("Provider.id info", p.getInfo());
        hashExpectedResult.put("Provider.id className", p.getClass().getName());
        hashExpectedResult.put("Provider.id version", String.valueOf(p.getVersion()));
        hashExpectedResult.put("Provider.id name", p.getName());

        assertEquals(hashExpectedResult.entrySet(), p.entrySet());

        if (mustChangeVersion) {
            assertTrue(securityVersionBeforeOperation != Security.getVersion());
        }
    }

    @SuppressWarnings("serial")
    private static class MockProvider extends Provider {
        public MockProvider(String name) {
            super(name, 1.0, "Mock provider used for testing");
            setup();
        }

        public void setup() {
        }

        public void putServiceForTest(Provider.Service service) {
            putService(service);
        }
    }

    @SuppressWarnings("serial")
    public static abstract class AbstractSecureRandom extends SecureRandomSpi {
        protected void engineSetSeed(byte[] seed) {
            throw new UnsupportedOperationException();
        }
        protected void engineNextBytes(byte[] bytes) {
            throw new UnsupportedOperationException();
        }
        protected byte[] engineGenerateSeed(int numBytes) {
            throw new UnsupportedOperationException();
        }
    }

    @SuppressWarnings("serial")
    public static class SecureRandom1 extends AbstractSecureRandom {}

    @SuppressWarnings("serial")
    public static class SecureRandom2 extends AbstractSecureRandom {}

    @SuppressWarnings("serial")
    public static class SecureRandom3 extends AbstractSecureRandom {}

}
