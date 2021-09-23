/*
 * Copyright (C) 2011 The Android Open Source Project
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

import com.android.org.bouncycastle.asn1.x509.GeneralName;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import junit.framework.TestCase;
import libcore.java.security.TestKeyStore;

public final class X509CertSelectorTest extends TestCase {

    public void testMatchIpv4SubjectAlternativeName() throws Exception {
        X509CertSelector certSelector = new X509CertSelector();
        certSelector.addSubjectAlternativeName(GeneralName.iPAddress, "127.0.0.1");

        byte[] match = { 127, 0, 0, 1 };
        assertTrue(certSelector.match(newCertWithSubjectAltNameIpAddress(match)));

        byte[] noMatch = { 127, 0, 0, 2 };
        assertFalse(certSelector.match(newCertWithSubjectAltNameIpAddress(noMatch)));
    }

    public void testMatchIpv4MappedSubjectAlternativeName() throws Exception {
        X509CertSelector certSelector = new X509CertSelector();
        certSelector.addSubjectAlternativeName(GeneralName.iPAddress, "::ffff:127.0.0.1");

        byte[] match = { 127, 0, 0, 1 };
        assertTrue(certSelector.match(newCertWithSubjectAltNameIpAddress(match)));

        byte[] noMatch = { 127, 0, 0, 2 };
        assertFalse(certSelector.match(newCertWithSubjectAltNameIpAddress(noMatch)));
    }

    public void testMatchIpv6SubjectAlternativeName() throws Exception {
        X509CertSelector certSelector = new X509CertSelector();
        certSelector.setMatchAllSubjectAltNames(false);
        certSelector.addSubjectAlternativeName(GeneralName.iPAddress, "::1");

        byte[] match = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
        assertTrue(certSelector.match(newCertWithSubjectAltNameIpAddress(match)));

        byte[] noMatch = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 };
        assertFalse(certSelector.match(newCertWithSubjectAltNameIpAddress(noMatch)));
    }

    public void testMatchMaskedIpv4NameConstraint() throws Exception {
        byte[] excluded = { (byte) 192, (byte) 168, 0, 1 };
        X509CertSelector certSelector = new X509CertSelector();
        certSelector.addPathToName(GeneralName.iPAddress, "127.0.0.1");

        // This constraint matches 127.0.0.1/255.255.255.255 aka 127.0.0.1
        byte[] directMatch = { 127, 0, 0, 1, -1, -1, -1, -1 };
        assertTrue(certSelector.match(newCertWithNameConstraint(directMatch, excluded)));

        // This constraint matches 127.0.0.2/255.255.255.255 aka 127.0.0.2
        byte[] noMatch = { 127, 0, 0, 2, -1, -1, -1, -1 };
        assertFalse(certSelector.match(newCertWithNameConstraint(noMatch, excluded)));

        // This constraint matches 127.0.0.0/255.255.255.255 aka 127.0.0.0
        byte[] subnetWithNoMask = { 127, 0, 0, 0, -1, -1, -1, -1 };
        assertFalse(certSelector.match(newCertWithNameConstraint(subnetWithNoMask, excluded)));

        // This constraint matches 127.0.0.0/255.255.255.0 aka 127.0.0.0/24
        byte[] maskedMatch = { 127, 0, 0, 0, -1, -1, -1, 0 };
        assertTrue(certSelector.match(newCertWithNameConstraint(maskedMatch, excluded)));
    }

    public void testMatchMaskedIpv6NameConstraint() throws Exception {
        byte[] excluded = {
                0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0
        };
        X509CertSelector certSelector = new X509CertSelector();
        certSelector.addPathToName(GeneralName.iPAddress, "1::1");

        // This constraint matches 1::1/128 aka 1::1
        byte[] directMatch = {
                0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
        };
        assertTrue(certSelector.match(newCertWithNameConstraint(directMatch, excluded)));

        // This constraint matches 1::2/128 aka 1::2
        byte[] noMatch = {
                0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
        };
        assertFalse(certSelector.match(newCertWithNameConstraint(noMatch, excluded)));

        // This constraint matches 1::/128 aka 1::
        byte[] subnetWithNoMask = {
                0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
        };
        assertFalse(certSelector.match(newCertWithNameConstraint(subnetWithNoMask, excluded)));

        // This constraint matches 1::/120
        byte[] maskedMatch = {
                0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0
        };
        assertTrue(certSelector.match(newCertWithNameConstraint(maskedMatch, excluded)));
    }

    public void testMatchMalformedSubjectAlternativeName() throws Exception {
        X509CertSelector certSelector = new X509CertSelector();
        try {
            certSelector.addSubjectAlternativeName(GeneralName.iPAddress, "1::x");
            fail();
        } catch (IOException expected) {
        }
        try {
            certSelector.addSubjectAlternativeName(GeneralName.iPAddress, "127.0.0.x");
            fail();
        } catch (IOException expected) {
        }
    }

    private X509Certificate newCertWithSubjectAltNameIpAddress(byte[] ipAddress) throws Exception {
        TestKeyStore testKeyStore = new TestKeyStore.Builder()
                .addSubjectAltNameIpAddress(ipAddress)
                .build();
        return bouncycastleToJava(testKeyStore.getRootCertificate("RSA"));
    }

    private X509Certificate newCertWithNameConstraint(byte[] permitted, byte[] excluded)
            throws Exception {
        TestKeyStore testKeyStore = new TestKeyStore.Builder()
                .addNameConstraint(true, permitted)
                .addNameConstraint(false, excluded)
                .ca(true)
                .build();
        return bouncycastleToJava(testKeyStore.getRootCertificate("RSA"));
    }

    private X509Certificate bouncycastleToJava(Certificate certificate) throws Exception {
        byte[] encoded = certificate.getEncoded();
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(encoded));
    }
}
