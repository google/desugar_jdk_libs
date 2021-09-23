/*
 * Copyright (C) 2021 The Android Open Source Project
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.KeyStore;
import java.security.Timestamp;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import sun.security.provider.certpath.X509CertPath;

@RunWith(JUnit4.class)
public class CodeSourceTest {


    private static final String PATH = "file://invalid_cert_path";

    private CodeSource codeSource;

    @Before
    public void setUp() throws Exception {
        CodeSigner codeSigner = createCodeSigner();
        codeSource = new CodeSource(new URL(PATH), new CodeSigner[] { codeSigner });
    }

    private static CodeSigner createCodeSigner() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidCAStore");
        keyStore.load(null);
        // Get a X509Certificate from the keyStore
        X509Certificate cert = null;
        for (Enumeration<String> aliases = keyStore.aliases(); aliases.hasMoreElements(); ) {
            String alias = aliases.nextElement();
            Certificate certificate = keyStore.getCertificate(alias);
            assertTrue(certificate instanceof X509Certificate);
            cert = (X509Certificate) certificate;
            break;
        }

        assertNotNull(cert);
        X509CertPath certPath = new X509CertPath(List.of(cert));
        return new CodeSigner(certPath, new Timestamp(new Date(), certPath));
    }

    @Test
    public void testGetCerificates() {
        assertNull(codeSource.getCertificates());
    }

    @Test
    public void testGetCodeSigners() {
        assertNull(codeSource.getCodeSigners());
    }

    @Test
    public void testGetLocation() throws MalformedURLException {
        assertEquals(new URL(PATH), codeSource.getLocation());
    }

    @Test
    public void testImplies() {
        assertTrue(codeSource.implies(null));
        assertTrue(codeSource.implies(codeSource));
    }
}
