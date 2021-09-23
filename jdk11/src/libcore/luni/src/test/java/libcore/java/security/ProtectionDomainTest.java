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

import java.net.URL;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.KeyStore;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Principal;
import java.security.ProtectionDomain;
import java.security.Timestamp;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import sun.security.provider.certpath.X509CertPath;

@RunWith(JUnit4.class)
public class ProtectionDomainTest {

    private ProtectionDomain domain;

    @Before
    public void setUp() throws Exception {
        String path = "file://invalid_path";
        CodeSigner codeSigner = createCodeSigner();
        CodeSource codeSource = new CodeSource(new URL(path), new CodeSigner[] { codeSigner });
        domain = new ProtectionDomain(codeSource , new TestPermissionCollection(),
                ProtectionDomainTest.class.getClassLoader(), new Principal[0]);
    }

    @Test
    public void testGetClassLoader() {
        // Return null even thought it's set in the constructor
        assertNull(domain.getClassLoader());
    }

    @Test
    public void testGetCodeSource() {
        // Return null even thought it's set in the constructor
        assertNull(domain.getCodeSource());
    }

    @Test
    public void testGetPermissions() {
        // Return null even thought it's set in the constructor
        assertNull(domain.getPermissions());
    }

    @Test
    public void testGetPrincipals() {
        // Return null even thought it's set in the constructor
        assertNull(domain.getPrincipals());
    }

    @Test
    public void testImplies() {
        assertTrue(domain.implies(null));
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

    private static class TestPermissionCollection extends PermissionCollection {

        @Override
        public void add(Permission permission) {
        }

        @Override
        public boolean implies(Permission permission) {
            return true;
        }

        @Override
        public Enumeration<Permission> elements() {
            return null;
        }
    }
}
