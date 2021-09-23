/*
 * Copyright (C) 2017 The Android Open Source Project
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

package libcore.sun.security.jca;

import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import dalvik.system.VMRuntime;

import java.lang.reflect.Method;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;

import sun.security.jca.Providers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Tests that the deprecation of algorithms from the BC provider works as expected.  Requests
 * from an application targeting an API level before the deprecation should receive them,
 * but those targeting an API level after the deprecation should cause an exception.  Tests
 * a representative sample of services and algorithms and various ways of naming them.
 */
@RunWith(JUnit4.class)
public class ProvidersTest {

    /**
     * An object that can be called to call an appropriate getInstance method.  Since
     * each type of object has its own class that the method should be called on,
     * it's either this or reflection, and this seems more straightforward.
     */
    private interface Algorithm {
        Object getInstance() throws GeneralSecurityException;
    }

    // getInstance calls that result in requests to BC
    private static final List<Algorithm> BC_ALGORITHMS = new ArrayList<>();
    // getInstance calls that result in requests to Conscrypt
    private static final List<Algorithm> CONSCRYPT_ALGORITHMS = new ArrayList<>();
    static {
        // All the same algorithms as for BC, but with no provider, which should produce
        // the Conscrypt implementation
        CONSCRYPT_ALGORITHMS.add(new Algorithm() {
            @Override
            public Object getInstance() throws GeneralSecurityException {
                return Signature.getInstance("sha224withrsa");
            }
        });
        CONSCRYPT_ALGORITHMS.add(new Algorithm() {
            @Override
            public Object getInstance() throws GeneralSecurityException {
                return KeyFactory.getInstance("EC");
            }
        });
        CONSCRYPT_ALGORITHMS.add(new Algorithm() {
            @Override
            public Object getInstance() throws GeneralSecurityException {
                return Signature.getInstance("MD5withRSAEncryption");
            }
        });
        CONSCRYPT_ALGORITHMS.add(new Algorithm() {
            @Override
            public Object getInstance() throws GeneralSecurityException {
                return KeyGenerator.getInstance("HMAC-MD5");
            }
        });
        CONSCRYPT_ALGORITHMS.add(new Algorithm() {
            @Override
            public Object getInstance() throws GeneralSecurityException {
                return Mac.getInstance("Hmac/sha256");
            }
        });
        CONSCRYPT_ALGORITHMS.add(new Algorithm() {
            @Override
            public Object getInstance() throws GeneralSecurityException {
                return Signature.getInstance("SHA384/rsA");
            }
        });
        CONSCRYPT_ALGORITHMS.add(new Algorithm() {
            @Override
            public Object getInstance() throws GeneralSecurityException {
                // OID for SHA-256
                return MessageDigest.getInstance("2.16.840.1.101.3.4.2.1");
            }
        });
        CONSCRYPT_ALGORITHMS.add(new Algorithm() {
            @Override
            public Object getInstance() throws GeneralSecurityException {
                // OID for AES-128
                return AlgorithmParameters.getInstance("2.16.840.1.101.3.4.1.2");
            }
        });
    }

    private static final Set<String> REMOVED_BC_ALGORITHMS = new HashSet<String>();
    static {
        REMOVED_BC_ALGORITHMS.addAll(Arrays.asList(
                "ALGORITHMPARAMETERS.1.2.840.113549.3.7",
                "ALGORITHMPARAMETERS.2.16.840.1.101.3.4.1.2",
                "ALGORITHMPARAMETERS.2.16.840.1.101.3.4.1.22",
                "ALGORITHMPARAMETERS.2.16.840.1.101.3.4.1.26",
                "ALGORITHMPARAMETERS.2.16.840.1.101.3.4.1.42",
                "ALGORITHMPARAMETERS.2.16.840.1.101.3.4.1.46",
                "ALGORITHMPARAMETERS.2.16.840.1.101.3.4.1.6",
                "ALGORITHMPARAMETERS.AES",
                "ALGORITHMPARAMETERS.DESEDE",
                "ALGORITHMPARAMETERS.EC",
                "ALGORITHMPARAMETERS.GCM",
                "ALGORITHMPARAMETERS.OAEP",
                "ALGORITHMPARAMETERS.TDEA",
                "CERTIFICATEFACTORY.X.509",
                "CERTIFICATEFACTORY.X509",
                // List of Ciphers produced by ProviderOverlap:
                "CIPHER.1.2.840.113549.3.4",
                "CIPHER.2.16.840.1.101.3.4.1.26",
                "CIPHER.2.16.840.1.101.3.4.1.46",
                "CIPHER.2.16.840.1.101.3.4.1.6",
                "CIPHER.AES/GCM/NOPADDING",
                "CIPHER.ARC4",
                "CIPHER.ARCFOUR",
                "CIPHER.OID.1.2.840.113549.3.4",
                "CIPHER.RC4",
                // End of Ciphers produced by ProviderOverlap
                // Additional ciphers transformations that will resolve to the same things as
                // the automatically-produced overlap due to the Cipher transformation rules.
                // These have been added manually.
                "CIPHER.ARC4/ECB/NOPADDING",
                "CIPHER.ARC4/NONE/NOPADDING",
                "CIPHER.ARCFOUR/ECB/NOPADDING",
                "CIPHER.ARCFOUR/NONE/NOPADDING",
                "CIPHER.RC4/ECB/NOPADDING",
                "CIPHER.RC4/NONE/NOPADDING",
                // End of additional Ciphers
                "KEYAGREEMENT.ECDH",
                "KEYFACTORY.1.2.840.10045.2.1",
                "KEYFACTORY.1.2.840.113549.1.1.1",
                "KEYFACTORY.1.2.840.113549.1.1.7",
                "KEYFACTORY.1.3.133.16.840.63.0.2",
                "KEYFACTORY.2.5.8.1.1",
                "KEYFACTORY.EC",
                "KEYGENERATOR.1.2.840.113549.2.10",
                "KEYGENERATOR.1.2.840.113549.2.11",
                "KEYGENERATOR.1.2.840.113549.2.7",
                "KEYGENERATOR.1.2.840.113549.2.8",
                "KEYGENERATOR.1.2.840.113549.2.9",
                "KEYGENERATOR.1.3.6.1.5.5.8.1.1",
                "KEYGENERATOR.1.3.6.1.5.5.8.1.2",
                "KEYGENERATOR.2.16.840.1.101.3.4.2.1",
                "KEYGENERATOR.AES",
                "KEYGENERATOR.DESEDE",
                "KEYGENERATOR.HMAC-MD5",
                "KEYGENERATOR.HMAC-SHA1",
                "KEYGENERATOR.HMAC-SHA224",
                "KEYGENERATOR.HMAC-SHA256",
                "KEYGENERATOR.HMAC-SHA384",
                "KEYGENERATOR.HMAC-SHA512",
                "KEYGENERATOR.HMAC/MD5",
                "KEYGENERATOR.HMAC/SHA1",
                "KEYGENERATOR.HMAC/SHA224",
                "KEYGENERATOR.HMAC/SHA256",
                "KEYGENERATOR.HMAC/SHA384",
                "KEYGENERATOR.HMAC/SHA512",
                "KEYGENERATOR.HMACMD5",
                "KEYGENERATOR.HMACSHA1",
                "KEYGENERATOR.HMACSHA224",
                "KEYGENERATOR.HMACSHA256",
                "KEYGENERATOR.HMACSHA384",
                "KEYGENERATOR.HMACSHA512",
                "KEYGENERATOR.TDEA",
                "KEYPAIRGENERATOR.1.2.840.10045.2.1",
                "KEYPAIRGENERATOR.1.2.840.113549.1.1.1",
                "KEYPAIRGENERATOR.1.2.840.113549.1.1.7",
                "KEYPAIRGENERATOR.1.3.133.16.840.63.0.2",
                "KEYPAIRGENERATOR.2.5.8.1.1",
                "KEYPAIRGENERATOR.EC",
                "KEYPAIRGENERATOR.RSA",
                "MAC.1.2.840.113549.2.10",
                "MAC.1.2.840.113549.2.11",
                "MAC.1.2.840.113549.2.7",
                "MAC.1.2.840.113549.2.8",
                "MAC.1.2.840.113549.2.9",
                "MAC.1.3.6.1.5.5.8.1.1",
                "MAC.1.3.6.1.5.5.8.1.2",
                "MAC.2.16.840.1.101.3.4.2.1",
                "MAC.HMAC-MD5",
                "MAC.HMAC-SHA1",
                "MAC.HMAC-SHA224",
                "MAC.HMAC-SHA256",
                "MAC.HMAC-SHA384",
                "MAC.HMAC-SHA512",
                "MAC.HMAC/MD5",
                "MAC.HMAC/SHA1",
                "MAC.HMAC/SHA224",
                "MAC.HMAC/SHA256",
                "MAC.HMAC/SHA384",
                "MAC.HMAC/SHA512",
                "MAC.HMACMD5",
                "MAC.HMACSHA1",
                "MAC.HMACSHA224",
                "MAC.HMACSHA256",
                "MAC.HMACSHA384",
                "MAC.HMACSHA512",
                "MAC.PBEWITHHMACSHA224",
                "MAC.PBEWITHHMACSHA256",
                "MAC.PBEWITHHMACSHA384",
                "MAC.PBEWITHHMACSHA512",
                "MESSAGEDIGEST.1.2.840.113549.2.5",
                "MESSAGEDIGEST.1.3.14.3.2.26",
                "MESSAGEDIGEST.2.16.840.1.101.3.4.2.1",
                "MESSAGEDIGEST.2.16.840.1.101.3.4.2.2",
                "MESSAGEDIGEST.2.16.840.1.101.3.4.2.3",
                "MESSAGEDIGEST.2.16.840.1.101.3.4.2.4",
                "MESSAGEDIGEST.MD5",
                "MESSAGEDIGEST.SHA",
                "MESSAGEDIGEST.SHA-1",
                "MESSAGEDIGEST.SHA-224",
                "MESSAGEDIGEST.SHA-256",
                "MESSAGEDIGEST.SHA-384",
                "MESSAGEDIGEST.SHA-512",
                "MESSAGEDIGEST.SHA1",
                "MESSAGEDIGEST.SHA224",
                "MESSAGEDIGEST.SHA256",
                "MESSAGEDIGEST.SHA384",
                "MESSAGEDIGEST.SHA512",
                "SECRETKEYFACTORY.DESEDE",
                "SECRETKEYFACTORY.TDEA",
                "SIGNATURE.1.2.840.10045.4.1",
                "SIGNATURE.1.2.840.10045.4.3.1",
                "SIGNATURE.1.2.840.10045.4.3.2",
                "SIGNATURE.1.2.840.10045.4.3.3",
                "SIGNATURE.1.2.840.10045.4.3.4",
                "SIGNATURE.1.2.840.113549.1.1.11",
                "SIGNATURE.1.2.840.113549.1.1.12",
                "SIGNATURE.1.2.840.113549.1.1.13",
                "SIGNATURE.1.2.840.113549.1.1.14",
                "SIGNATURE.1.2.840.113549.1.1.4",
                "SIGNATURE.1.2.840.113549.1.1.5",
                "SIGNATURE.1.3.14.3.2.29",
                "SIGNATURE.ECDSA",
                "SIGNATURE.ECDSAWITHSHA1",
                "SIGNATURE.MD5/RSA",
                "SIGNATURE.MD5WITHRSA",
                "SIGNATURE.MD5WITHRSAENCRYPTION",
                "SIGNATURE.NONEWITHECDSA",
                "SIGNATURE.OID.1.2.840.10045.4.3.1",
                "SIGNATURE.OID.1.2.840.10045.4.3.2",
                "SIGNATURE.OID.1.2.840.10045.4.3.3",
                "SIGNATURE.OID.1.2.840.10045.4.3.4",
                "SIGNATURE.OID.1.2.840.113549.1.1.11",
                "SIGNATURE.OID.1.2.840.113549.1.1.12",
                "SIGNATURE.OID.1.2.840.113549.1.1.13",
                "SIGNATURE.OID.1.2.840.113549.1.1.14",
                "SIGNATURE.OID.1.2.840.113549.1.1.4",
                "SIGNATURE.OID.1.2.840.113549.1.1.5",
                "SIGNATURE.OID.1.3.14.3.2.29",
                "SIGNATURE.SHA1/RSA",
                "SIGNATURE.SHA1WITHECDSA",
                "SIGNATURE.SHA1WITHRSA",
                "SIGNATURE.SHA1WITHRSAENCRYPTION",
                "SIGNATURE.SHA224/ECDSA",
                "SIGNATURE.SHA224/RSA",
                "SIGNATURE.SHA224WITHECDSA",
                "SIGNATURE.SHA224WITHRSA",
                "SIGNATURE.SHA224WITHRSAENCRYPTION",
                "SIGNATURE.SHA256/ECDSA",
                "SIGNATURE.SHA256/RSA",
                "SIGNATURE.SHA256WITHECDSA",
                "SIGNATURE.SHA256WITHRSA",
                "SIGNATURE.SHA256WITHRSAENCRYPTION",
                "SIGNATURE.SHA384/ECDSA",
                "SIGNATURE.SHA384/RSA",
                "SIGNATURE.SHA384WITHECDSA",
                "SIGNATURE.SHA384WITHRSA",
                "SIGNATURE.SHA384WITHRSAENCRYPTION",
                "SIGNATURE.SHA512/ECDSA",
                "SIGNATURE.SHA512/RSA",
                "SIGNATURE.SHA512WITHECDSA",
                "SIGNATURE.SHA512WITHRSA",
                "SIGNATURE.SHA512WITHRSAENCRYPTION"
        ));
    }

    private static Provider getProvider(Object object) throws Exception {
        // Every JCA object has a getProvider() method
        Method m = object.getClass().getMethod("getProvider");
        return (Provider) m.invoke(object);
    }

    @Test
    public void testBeforeLimit() throws Exception {
        // When we're before the limit of the target API, all calls should succeed
        try {
            Providers.setMaximumAllowableApiLevelForBcDeprecation(
                    VMRuntime.getRuntime().getTargetSdkVersion() + 1);
            for (Algorithm a : BC_ALGORITHMS) {
                Object result = a.getInstance();
                assertEquals("BC", getProvider(result).getName());
            }
            for (Algorithm a : CONSCRYPT_ALGORITHMS) {
                Object result = a.getInstance();
                assertEquals("AndroidOpenSSL", getProvider(result).getName());
            }
        } finally {
            Providers.setMaximumAllowableApiLevelForBcDeprecation(
                    Providers.DEFAULT_MAXIMUM_ALLOWABLE_TARGET_API_LEVEL_FOR_BC_DEPRECATION);
        }
    }

    @Test
    public void testAtLimit() throws Exception {
        // When we're at the limit of the target API, all calls should still succeed
        try {
            Providers.setMaximumAllowableApiLevelForBcDeprecation(
                    VMRuntime.getRuntime().getTargetSdkVersion());
            for (Algorithm a : BC_ALGORITHMS) {
                Object result = a.getInstance();
                assertEquals("BC", getProvider(result).getName());
            }
            for (Algorithm a : CONSCRYPT_ALGORITHMS) {
                Object result = a.getInstance();
                assertEquals("AndroidOpenSSL", getProvider(result).getName());
            }
        } finally {
            Providers.setMaximumAllowableApiLevelForBcDeprecation(
                    Providers.DEFAULT_MAXIMUM_ALLOWABLE_TARGET_API_LEVEL_FOR_BC_DEPRECATION);
        }
    }

    @Test
    public void testPastLimit() throws Exception {
        // When we're beyond the limit of the target API, the Conscrypt calls should succeed
        // but the BC calls should throw NoSuchAlgorithmException
        try {
            Providers.setMaximumAllowableApiLevelForBcDeprecation(
                    VMRuntime.getRuntime().getTargetSdkVersion() - 1);
            for (Algorithm a : BC_ALGORITHMS) {
                try {
                    a.getInstance();
                    fail("getInstance should have thrown");
                } catch (NoSuchAlgorithmException expected) {
                }
            }
            for (Algorithm a : CONSCRYPT_ALGORITHMS) {
                Object result = a.getInstance();
                assertEquals("AndroidOpenSSL", getProvider(result).getName());
            }
        } finally {
            Providers.setMaximumAllowableApiLevelForBcDeprecation(
                    Providers.DEFAULT_MAXIMUM_ALLOWABLE_TARGET_API_LEVEL_FOR_BC_DEPRECATION);
        }
    }

    @Test
    public void testCustomProvider() throws Exception {
        // When we install our own separate instance of Bouncy Castle, the system should
        // respect that and allow us to use its implementation.
        Provider originalBouncyCastle = null;
        int originalBouncyCastleIndex = -1;
        for (int i = 0; i < Security.getProviders().length; i++) {
            if (Security.getProviders()[i].getName().equals("BC")) {
                originalBouncyCastle = Security.getProviders()[i];
                originalBouncyCastleIndex = i;
                break;
            }
        }
        assertNotNull(originalBouncyCastle);
        Provider newBouncyCastle = new BouncyCastleProvider();
        assertEquals("BC", newBouncyCastle.getName());
        try {
            // Remove the existing BC provider and replace it with a different one
            Security.removeProvider("BC");
            Security.insertProviderAt(newBouncyCastle, originalBouncyCastleIndex);
            // Set the target API limit such that the BC algorithms are disallowed
            Providers.setMaximumAllowableApiLevelForBcDeprecation(
                    VMRuntime.getRuntime().getTargetSdkVersion() - 1);
            for (Algorithm a : BC_ALGORITHMS) {
                Object result = a.getInstance();
                assertEquals("BC", getProvider(result).getName());
            }
            for (Algorithm a : CONSCRYPT_ALGORITHMS) {
                Object result = a.getInstance();
                assertEquals("AndroidOpenSSL", getProvider(result).getName());
            }
        } finally {
            Providers.setMaximumAllowableApiLevelForBcDeprecation(
                    Providers.DEFAULT_MAXIMUM_ALLOWABLE_TARGET_API_LEVEL_FOR_BC_DEPRECATION);
            Security.removeProvider("BC");
            Security.insertProviderAt(originalBouncyCastle, originalBouncyCastleIndex);
        }
    }
    
    @Test
    public void testRemovedBCAlgorithms() throws Exception {
        for (String fullAlgorithm : REMOVED_BC_ALGORITHMS) {
            String[] parts = fullAlgorithm.split("\\.", 2);
            assertEquals("Algortihm names are expected to be of format Type.Name",
                2, parts.length);

            Provider bcProvider = Security.getProvider("BC");
            String type = parts[0];
            String algorithm = parts[1];
            try {
                switch (parts[0]) {
                    case "ALGORITHMPARAMETERS":
                        AlgorithmParameters.getInstance(algorithm, bcProvider);
                    case "CERTIFICATEFACTORY":
                        CertificateFactory.getInstance(algorithm, bcProvider);
                    case "CIPHER":
                        Cipher.getInstance(algorithm, bcProvider);
                    case "KEYAGREEMENT":
                        KeyAgreement.getInstance(algorithm, bcProvider);
                    case "KEYFACTORY":
                        KeyFactory.getInstance(algorithm, bcProvider);
                    case "KEYGENERATOR":
                        KeyGenerator.getInstance(algorithm, bcProvider);
                    case "KEYPAIRGENERATOR":
                        KeyPairGenerator.getInstance(algorithm, bcProvider);
                    case "MAC":
                        Mac.getInstance(algorithm, bcProvider);
                    case "MESSAGEDIGEST":
                        MessageDigest.getInstance(algorithm, bcProvider);
                    case "SECRETKEYFACTORY":
                        SecretKeyFactory.getInstance(algorithm, bcProvider);
                    case "SIGNATURE":
                        Signature.getInstance(algorithm, bcProvider);
                    default:
                        fail("unhandled algorithm type " + parts[0]);
                }
                fail("getInstance should have thrown for type: " + parts[0] + ", name: " + algorithm);
            } catch(CertificateException | NoSuchAlgorithmException expected) {
            }
        }
    }
}