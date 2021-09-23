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

package libcore.java.security.cert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import libcore.java.security.TestKeyStore;
import libcore.junit.util.EnableDeprecatedBouncyCastleAlgorithmsRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PKIXParametersTest {
    private  static final FakeCertPathChecker CHECK_ONE = new FakeCertPathChecker("One");
    private  static final FakeCertPathChecker CHECK_TWO = new FakeCertPathChecker("Two");
    private  static final FakeCertPathChecker CHECK_THREE = new FakeCertPathChecker("Three");
    private static final List<PKIXCertPathChecker> CHECK_LIST
        = List.of(CHECK_ONE, CHECK_TWO, CHECK_THREE);

    // Allow access to deprecated BC algorithms in this test, so we can ensure they
    // continue to work
    @Rule
    public TestRule enableDeprecatedBCAlgorithmsRule =
            EnableDeprecatedBouncyCastleAlgorithmsRule.getInstance();

    @Test
    public void keyStoreConstructor() throws Exception {
        TestKeyStore server = TestKeyStore.getServer();
        KeyStore.PrivateKeyEntry pke = server.getPrivateKey("RSA", "RSA");
        char[] password = "password".toCharArray();

        // contains CA and server certificates
        assertEquals(2, new PKIXParameters(server.keyStore).getTrustAnchors().size());

        // just copy server certificates
        KeyStore ks = TestKeyStore.createKeyStore();
        ks.setKeyEntry("key", pke.getPrivateKey(), password, pke.getCertificateChain());
        ks.setCertificateEntry("cert", pke.getCertificateChain()[0]);
        assertEquals(1, new PKIXParameters(ks).getTrustAnchors().size());

        // should fail with just key, even though cert is present in key entry
        try {
            KeyStore keyOnly = TestKeyStore.createKeyStore();
            keyOnly.setKeyEntry("key", pke.getPrivateKey(), password, pke.getCertificateChain());
            new PKIXParameters(keyOnly);
            fail();
        } catch (InvalidAlgorithmParameterException expected) {
        }

        // should fail with empty KeyStore
        try {
            new PKIXParameters(TestKeyStore.createKeyStore());
            fail();
        } catch (InvalidAlgorithmParameterException expected) {
        }
    }

    @Test
    public void addCertPathChecker() throws Exception {
        PKIXParameters parameters = newPkixParameters();
        for (PKIXCertPathChecker pathChecker : CHECK_LIST) {
            parameters.addCertPathChecker(pathChecker);
        }

        List<PKIXCertPathChecker> actualCheckers = parameters.getCertPathCheckers();
        assertEquals(CHECK_LIST, actualCheckers);
    }

    @Test
    public void addCertPathChecker_Null() throws Exception {
        PKIXParameters parameters = newPkixParameters();

        parameters.addCertPathChecker(null);
        assertEquals(0, parameters.getCertPathCheckers().size());
    }

    @Test
    public void setCertPathCheckers() throws Exception {
        PKIXParameters parameters = newPkixParameters();
        parameters.setCertPathCheckers(CHECK_LIST);

        List<PKIXCertPathChecker> actualCheckers = parameters.getCertPathCheckers();
        assertEquals(CHECK_LIST, actualCheckers);
        assertNotSame(CHECK_LIST, actualCheckers);
    }

    @Test
    public void setCertPathCheckers_NullEmpty() throws Exception {
        PKIXParameters parameters = newPkixParameters();

        parameters.setCertPathCheckers(null);
        assertEquals(0, parameters.getCertPathCheckers().size());

        parameters.setCertPathCheckers(new ArrayList<>());
        assertEquals(0, parameters.getCertPathCheckers().size());
    }

    @Test
    public void setCertPathCheckers_Replacement() throws Exception {
        PKIXParameters parameters = newPkixParameters(CHECK_LIST);
        parameters.setCertPathCheckers(null);
        assertEquals(0, parameters.getCertPathCheckers().size());

        parameters = newPkixParameters(CHECK_LIST);
        parameters.setCertPathCheckers(new ArrayList<>());
        assertEquals(0, parameters.getCertPathCheckers().size());

        parameters = newPkixParameters(CHECK_LIST);
        List<PKIXCertPathChecker> reversed = new ArrayList<>(CHECK_LIST);
        Collections.reverse(reversed);
        parameters.setCertPathCheckers(reversed);
        assertEquals(reversed, parameters.getCertPathCheckers());
    }

    @Test
    public void setCertPathCheckers_Error() throws Exception {
        PKIXParameters parameters = newPkixParameters();
        List badList = List.of("Wrong class");
        try {
            parameters.setCertPathCheckers(badList);
            fail();
        } catch (ClassCastException expected) {
        }
    }

    @Test
    public void toStringContainsPathCheckers() throws Exception {
        PKIXParameters parameters = newPkixParameters(CHECK_LIST);

        String regex = ".*" + CHECK_ONE + ".*" + CHECK_TWO + ".*" + CHECK_THREE + ".*";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        assertTrue(pattern.matcher(parameters.toString()).matches());
    }

    private PKIXParameters newPkixParameters() throws Exception {
        PKIXParameters parameters = new PKIXParameters(TestKeyStore.getServer().keyStore);

        List<PKIXCertPathChecker> pathCheckers = parameters.getCertPathCheckers();
        assertEquals(0, pathCheckers.size());
        return parameters;
    }

    private PKIXParameters newPkixParameters(List<PKIXCertPathChecker> pathCheckers)
        throws Exception {

        PKIXParameters parameters = newPkixParameters();
        parameters.setCertPathCheckers(pathCheckers);
        assertEquals(pathCheckers, parameters.getCertPathCheckers());
        return parameters;
    }

    private static class FakeCertPathChecker extends PKIXCertPathChecker {
        private final String tag;

        FakeCertPathChecker(String tag) {
            this.tag = tag;
        }

        @Override
        public void init(boolean forward) {
            // No-op
        }

        @Override
        public boolean isForwardCheckingSupported() {
            return false;
        }

        @Override
        public Set<String> getSupportedExtensions() {
            return new HashSet<>();
        }

        @Override
        public void check(Certificate cert, Collection<String> unresolvedCritExts) {
            // No-op
        }

        @Override
        public String toString() {
            return "FakeCertPathChecker: " + tag;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof FakeCertPathChecker) {
                return tag.equals(((FakeCertPathChecker) other).tag);
            }
            return false;
        }
    }
}
