/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.apache.harmony.tests.javax.net.ssl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.security.auth.x500.X500Principal;
import junit.framework.TestCase;
import org.apache.harmony.xnet.tests.support.mySSLSession;

public class HostnameVerifierTest extends TestCase implements
        CertificatesToPlayWith {

    /**
     * javax.net.ssl.HostnameVerifier#verify(String hostname, SSLSession
     *        session)
     */
    public final void test_verify() throws Exception {
        mySSLSession session = new mySSLSession("localhost", 1080, null);
        HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
        assertFalse(hv.verify("localhost", session));
    }

    // copied and modified from apache http client test suite.
    public void testVerify() throws Exception {
        HostnameVerifier verifier = HttpsURLConnection.getDefaultHostnameVerifier();
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream in;
        X509Certificate x509;
        // CN=foo.com, no subjectAlt
        in = new ByteArrayInputStream(X509_FOO);
        x509 = (X509Certificate) cf.generateCertificate(in);
        mySSLSession session = new mySSLSession(new X509Certificate[] {x509});
        assertFalse(verifier.verify("foo.com", session));
        assertFalse(verifier.verify("a.foo.com", session));
        assertFalse(verifier.verify("bar.com", session));

        // CN=花子.co.jp, no subjectAlt
        in = new ByteArrayInputStream(X509_HANAKO);
        x509 = (X509Certificate) cf.generateCertificate(in);
        session = new mySSLSession(new X509Certificate[] {x509});
        assertFalse(verifier.verify("\u82b1\u5b50.co.jp", session));
        assertFalse(verifier.verify("a.\u82b1\u5b50.co.jp", session));

        // CN=foo.com, subjectAlt=bar.com
        in = new ByteArrayInputStream(X509_FOO_BAR);
        x509 = (X509Certificate) cf.generateCertificate(in);
        session = new mySSLSession(new X509Certificate[] {x509});
        assertFalse(verifier.verify("foo.com", session));
        assertFalse(verifier.verify("a.foo.com", session));
        assertTrue(verifier.verify("bar.com", session));
        assertFalse(verifier.verify("a.bar.com", session));

        // CN=foo.com, subjectAlt=bar.com, subjectAlt=花子.co.jp
        in = new ByteArrayInputStream(X509_FOO_BAR_HANAKO);
        x509 = (X509Certificate) cf.generateCertificate(in);
        session = new mySSLSession(new X509Certificate[] {x509});
        assertFalse(verifier.verify("foo.com", session));
        assertFalse(verifier.verify("a.foo.com", session));
        assertTrue(verifier.verify("bar.com", session));
        assertFalse(verifier.verify("a.bar.com", session));
        // The certificate has this name in the altnames section, but OkHostnameVerifier drops
        // any altnames that are improperly encoded according to RFC 5280, which requires
        // non-ASCII characters to be encoded in ASCII via Punycode.
        assertFalse(verifier.verify("\u82b1\u5b50.co.jp", session));
        assertFalse(verifier.verify("a.\u82b1\u5b50.co.jp", session));

        // no CN, subjectAlt=foo.com
        in = new ByteArrayInputStream(X509_NO_CNS_FOO);
        x509 = (X509Certificate) cf.generateCertificate(in);
        session = new mySSLSession(new X509Certificate[] {x509});
        assertTrue(verifier.verify("foo.com", session));
        assertFalse(verifier.verify("a.foo.com", session));

        // CN=foo.com, CN=bar.com, CN=花子.co.jp, no subjectAlt
        in = new ByteArrayInputStream(X509_THREE_CNS_FOO_BAR_HANAKO);
        x509 = (X509Certificate) cf.generateCertificate(in);
        session = new mySSLSession(new X509Certificate[] {x509});
        assertFalse(verifier.verify("foo.com", session));
        assertFalse(verifier.verify("a.foo.com", session));
        assertFalse(verifier.verify("bar.com", session));
        assertFalse(verifier.verify("a.bar.com", session));
        assertFalse(verifier.verify("\u82b1\u5b50.co.jp", session));
        assertFalse(verifier.verify("a.\u82b1\u5b50.co.jp", session));

        // CN=*.foo.com, no subjectAlt
        in = new ByteArrayInputStream(X509_WILD_FOO);
        x509 = (X509Certificate) cf.generateCertificate(in);
        session = new mySSLSession(new X509Certificate[] {x509});
        assertFalse(verifier.verify("foo.com", session));
        assertFalse(verifier.verify("www.foo.com", session));
        assertFalse(verifier.verify("\u82b1\u5b50.foo.com", session));
        assertFalse(verifier.verify("a.b.foo.com", session));

        // CN=*.co.jp, no subjectAlt
        in = new ByteArrayInputStream(X509_WILD_CO_JP);
        x509 = (X509Certificate) cf.generateCertificate(in);
        session = new mySSLSession(new X509Certificate[] {x509});
        assertFalse(verifier.verify("foo.co.jp", session));
        assertFalse(verifier.verify("\u82b1\u5b50.co.jp", session));

        // CN=*.foo.com, subjectAlt=*.bar.com, subjectAlt=花子.co.jp
        in = new ByteArrayInputStream(X509_WILD_FOO_BAR_HANAKO);
        x509 = (X509Certificate) cf.generateCertificate(in);
        session = new mySSLSession(new X509Certificate[] {x509});
        // try the foo.com variations
        assertFalse(verifier.verify("foo.com", session));
        assertFalse(verifier.verify("www.foo.com", session));
        assertFalse(verifier.verify("\u82b1\u5b50.foo.com", session));
        assertFalse(verifier.verify("a.b.foo.com", session));
        assertFalse(verifier.verify("bar.com", session));
        assertTrue(verifier.verify("www.bar.com", session));
        assertFalse(verifier.verify("a.b.bar.com", session));
        // The certificate has this name in the altnames section, but OkHostnameVerifier drops
        // any altnames that are improperly encoded according to RFC 5280, which requires
        // non-ASCII characters to be encoded in ASCII via Punycode.
        assertFalse(verifier.verify("\u82b1\u5b50.bar.com", session));
        assertFalse(verifier.verify("\u82b1\u5b50.co.jp", session));
        assertFalse(verifier.verify("a.\u82b1\u5b50.co.jp", session));
    }

    public void testSubjectAlt() throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream in = new ByteArrayInputStream(X509_MULTIPLE_SUBJECT_ALT);
        X509Certificate x509 = (X509Certificate) cf.generateCertificate(in);
        mySSLSession session = new mySSLSession(new X509Certificate[] {x509});

        HostnameVerifier verifier = HttpsURLConnection.getDefaultHostnameVerifier();
        assertEquals(new X500Principal("CN=localhost"), x509.getSubjectX500Principal());

        assertTrue(verifier.verify("localhost", session));
        assertTrue(verifier.verify("localhost.localdomain", session));
        assertFalse(verifier.verify("local.host", session));
    }

    public void testVerifyIpAddress() throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream in = new ByteArrayInputStream(X509_MULTIPLE_SUBJECT_ALT);
        X509Certificate x509 = (X509Certificate) cf.generateCertificate(in);
        mySSLSession session = new mySSLSession(new X509Certificate[] { x509 });
        HostnameVerifier verifier = HttpsURLConnection.getDefaultHostnameVerifier();

        assertTrue(verifier.verify("127.0.0.1", session));
        assertFalse(verifier.verify("127.0.0.2", session));
    }

    public void testWildcardsCannotMatchIpAddresses() throws Exception {
        // openssl req -x509 -nodes -days 36500 -subj '/CN=*.0.0.1' -newkey rsa:512 -out cert.pem
        String cert = "-----BEGIN CERTIFICATE-----\n"
                + "MIIBkjCCATygAwIBAgIJAMdemqOwd/BEMA0GCSqGSIb3DQEBBQUAMBIxEDAOBgNV\n"
                + "BAMUByouMC4wLjEwIBcNMTAxMjIwMTY0NDI1WhgPMjExMDExMjYxNjQ0MjVaMBIx\n"
                + "EDAOBgNVBAMUByouMC4wLjEwXDANBgkqhkiG9w0BAQEFAANLADBIAkEAqY8c9Qrt\n"
                + "YPWCvb7lclI+aDHM6fgbJcHsS9Zg8nUOh5dWrS7AgeA25wyaokFl4plBbbHQe2j+\n"
                + "cCjsRiJIcQo9HwIDAQABo3MwcTAdBgNVHQ4EFgQUJ436TZPJvwCBKklZZqIvt1Yt\n"
                + "JjEwQgYDVR0jBDswOYAUJ436TZPJvwCBKklZZqIvt1YtJjGhFqQUMBIxEDAOBgNV\n"
                + "BAMUByouMC4wLjGCCQDHXpqjsHfwRDAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEB\n"
                + "BQUAA0EAk9i88xdjWoewqvE+iMC9tD2obMchgFDaHH0ogxxiRaIKeEly3g0uGxIt\n"
                + "fl2WRY8hb4x+zRrwsFaLEpdEvqcjOQ==\n"
                + "-----END CERTIFICATE-----";
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream in = new ByteArrayInputStream(cert.getBytes("UTF-8"));
        X509Certificate x509 = (X509Certificate) cf.generateCertificate(in);
        mySSLSession session = new mySSLSession(new X509Certificate[] { x509 });
        HostnameVerifier verifier = HttpsURLConnection.getDefaultHostnameVerifier();

        assertFalse(verifier.verify("127.0.0.1", session));
    }

    /**
     * Earlier implementations of Android's hostname verifier required that
     * wildcard names wouldn't match "*.com" or similar. This was a nonstandard
     * check that we've since dropped. It is the CA's responsibility to not hand
     * out certificates that match so broadly.
     */
    public void testWildcardsDoesNotNeedTwoDots() throws Exception {
        /*
         * $ cat ./cert.cnf
         * [req]
         * distinguished_name=distinguished_name
         * req_extensions=req_extensions
         * x509_extensions=x509_extensions
         * [distinguished_name]
         * [req_extensions]
         * [x509_extensions]
         * subjectAltName=DNS:*.com
         */
        // openssl req -x509 -nodes -days 36500 -subj '/CN=CommonName' -config ./cert.cnf -newkey rsa:512 -out cert.pem
        String cert = "-----BEGIN CERTIFICATE-----\n"
                + "MIIBODCB46ADAgECAgkA5o09Q/EN/kMwDQYJKoZIhvcNAQELBQAwFTETMBEGA1UE\n"
                + "AxMKQ29tbW9uTmFtZTAgFw0xODAxMTEwMDM1MDNaGA8yMTE3MTIxODAwMzUwM1ow\n"
                + "FTETMBEGA1UEAxMKQ29tbW9uTmFtZTBcMA0GCSqGSIb3DQEBAQUAA0sAMEgCQQDE\n"
                + "u2Yguj/n8mUvmEVIJeSxbtcK98yCkg07BIVPQaRBpBTjWk/lxRWlMGVAWTcls1El\n"
                + "IvLn+/NsBLx5l4UFfkDFAgMBAAGjFDASMBAGA1UdEQQJMAeCBSouY29tMA0GCSqG\n"
                + "SIb3DQEBCwUAA0EASyUpA60cGL8ePVO5XD4XGGIms5Dwd147+wiqKcYodnB8rlbF\n"
                + "nxeiH6VZH3lBKJjrAXB0rOaBzb9jCuVxjYldew==\n"
                + "-----END CERTIFICATE-----\n";
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream in = new ByteArrayInputStream(cert.getBytes("UTF-8"));
        X509Certificate x509 = (X509Certificate) cf.generateCertificate(in);
        mySSLSession session = new mySSLSession(new X509Certificate[] { x509 });
        HostnameVerifier verifier = HttpsURLConnection.getDefaultHostnameVerifier();

        assertTrue(verifier.verify("google.com", session));
    }

    public void testSubjectAltName() throws Exception {
        /*
         * $ cat ./cert.cnf
         * [req]
         * distinguished_name=distinguished_name
         * req_extensions=req_extensions
         * x509_extensions=x509_extensions
         * [distinguished_name]
         * [req_extensions]
         * [x509_extensions]
         * subjectAltName=DNS:bar.com,DNS:baz.com
         *
         * $ openssl req -x509 -nodes -days 36500 -subj '/CN=foo.com' -config ./cert.cnf \
         *     -newkey rsa:512 -out cert.pem
         */
        String cert = "-----BEGIN CERTIFICATE-----\n"
                + "MIIBPTCB6KADAgECAgkA7zoHaaqNGHQwDQYJKoZIhvcNAQEFBQAwEjEQMA4GA1UE\n"
                + "AxMHZm9vLmNvbTAgFw0xMDEyMjAxODM5MzZaGA8yMTEwMTEyNjE4MzkzNlowEjEQ\n"
                + "MA4GA1UEAxMHZm9vLmNvbTBcMA0GCSqGSIb3DQEBAQUAA0sAMEgCQQC+gmoSxF+8\n"
                + "hbV+rgRQqHIJd50216OWQJbU3BvdlPbca779NYO4+UZWTFdBM8BdQqs3H4B5Agvp\n"
                + "y7HeSff1F7XRAgMBAAGjHzAdMBsGA1UdEQQUMBKCB2Jhci5jb22CB2Jhei5jb20w\n"
                + "DQYJKoZIhvcNAQEFBQADQQBXpZZPOY2Dy1lGG81JTr8L4or9jpKacD7n51eS8iqI\n"
                + "oTznPNuXHU5bFN0AAGX2ij47f/EahqTpo5RdS95P4sVm\n"
                + "-----END CERTIFICATE-----";
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream in = new ByteArrayInputStream(cert.getBytes("UTF-8"));
        X509Certificate x509 = (X509Certificate) cf.generateCertificate(in);
        mySSLSession session = new mySSLSession(new X509Certificate[] { x509 });
        HostnameVerifier verifier = HttpsURLConnection.getDefaultHostnameVerifier();

        assertFalse(verifier.verify("foo.com", session));
        assertTrue(verifier.verify("bar.com", session));
        assertTrue(verifier.verify("baz.com", session));
        assertFalse(verifier.verify("a.foo.com", session));
        assertFalse(verifier.verify("quux.com", session));
    }

    public void testSubjectAltNameWithWildcard() throws Exception {
        /*
         * $ cat ./cert.cnf
         * [req]
         * distinguished_name=distinguished_name
         * req_extensions=req_extensions
         * x509_extensions=x509_extensions
         * [distinguished_name]
         * [req_extensions]
         * [x509_extensions]
         * subjectAltName=DNS:bar.com,DNS:*.baz.com
         *
         * $ openssl req -x509 -nodes -days 36500 -subj '/CN=foo.com' -config ./cert.cnf \
         *     -newkey rsa:512 -out cert.pem
         */
        String cert = "-----BEGIN CERTIFICATE-----\n"
                + "MIIBPzCB6qADAgECAgkAnv/7Jv5r7pMwDQYJKoZIhvcNAQEFBQAwEjEQMA4GA1UE\n"
                + "AxMHZm9vLmNvbTAgFw0xMDEyMjAxODQ2MDFaGA8yMTEwMTEyNjE4NDYwMVowEjEQ\n"
                + "MA4GA1UEAxMHZm9vLmNvbTBcMA0GCSqGSIb3DQEBAQUAA0sAMEgCQQDAz2YXnyog\n"
                + "YdYLSFr/OEgSumtwqtZKJTB4wqTW/eKbBCEzxnyUMxWZIqUGu353PzwfOuWp2re3\n"
                + "nvVV+QDYQlh9AgMBAAGjITAfMB0GA1UdEQQWMBSCB2Jhci5jb22CCSouYmF6LmNv\n"
                + "bTANBgkqhkiG9w0BAQUFAANBAB8yrSl8zqy07i0SNYx2B/FnvQY734pxioaqFWfO\n"
                + "Bqo1ZZl/9aPHEWIwBrxYNVB0SGu/kkbt/vxqOjzzrkXukmI=\n"
                + "-----END CERTIFICATE-----";
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream in = new ByteArrayInputStream(cert.getBytes("UTF-8"));
        X509Certificate x509 = (X509Certificate) cf.generateCertificate(in);
        mySSLSession session = new mySSLSession(new X509Certificate[] { x509 });
        HostnameVerifier verifier = HttpsURLConnection.getDefaultHostnameVerifier();

        assertFalse(verifier.verify("foo.com", session));
        assertTrue(verifier.verify("bar.com", session));
        assertTrue(verifier.verify("a.baz.com", session));
        assertFalse(verifier.verify("baz.com", session));
        assertFalse(verifier.verify("a.foo.com", session));
        assertFalse(verifier.verify("a.bar.com", session));
        assertFalse(verifier.verify("quux.com", session));
    }
}
