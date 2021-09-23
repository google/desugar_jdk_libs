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

package libcore.javax.net.ssl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.auth.x500.X500Principal;
import junit.framework.TestCase;

/**
 * Tests for the platform-default {@link HostnameVerifier} as provided by
 * {@link HttpsURLConnection#getDefaultHostnameVerifier()}.
 */
public final class DefaultHostnameVerifierTest extends TestCase {
    private static final int ALT_UNKNOWN = 0;
    private static final int ALT_DNS_NAME = 2; // DNS name
    private static final int ALT_IPA_NAME = 7; // IP address

    private final HostnameVerifier verifier = HttpsURLConnection.getDefaultHostnameVerifier();

    public void testVerify_wrongHost() {
        assertFalse(verifyWithServerCertificate(
                "imap.g.com", StubX509Certificate.dns("imap2.g.com")));
        assertFalse(verifyWithServerCertificate(
                "imap.g.com", StubX509Certificate.dns("sub.imap.g.com")));
    }

    public void testVerify_matchesAltNameButNotCommonName() {
        assertTrue(verifyWithServerCertificate(
                "imap.g.com", new StubX509Certificate("Common Name")
                        .addSubjectAlternativeName(ALT_DNS_NAME, "imap.g.com")));

        assertFalse(verifyWithServerCertificate(
                "imap.g.com", new StubX509Certificate("imap.g.com")
                        .addSubjectAlternativeName(ALT_DNS_NAME, "example.com")));
    }

    /**
     * If a subjectAltName extension of type ALT_DNS_NAME is present, that MUST
     * be used as the identity and the CN should be ignored.
     */
    public void testSubjectAltNameAndCn() {
        assertFalse(verifyWithServerCertificate("imap.g.com", new StubX509Certificate()
                .addSubjectAlternativeName(ALT_DNS_NAME, "a.y.com")));
        assertFalse(verifyWithServerCertificate("imap.g.com", new StubX509Certificate("imap.g.com")
                .addSubjectAlternativeName(ALT_DNS_NAME, "a.y.com")));
        assertTrue(verifyWithServerCertificate("imap.g.com", new StubX509Certificate()
                .addSubjectAlternativeName(ALT_DNS_NAME, "imap.g.com")));
    }

    public void testSubjectAltNameWithWildcard() {
        assertTrue(verifyWithServerCertificate("imap.g.com", StubX509Certificate.dns("*.g.com")));
    }

    public void testSubjectAltNameWithIpAddress() {
        assertTrue(verifyWithServerCertificate("1.2.3.4", StubX509Certificate.ipa("1.2.3.4")));
        assertFalse(verifyWithServerCertificate("1.2.3.5", StubX509Certificate.ipa("1.2.3.4")));
        assertTrue(verifyWithServerCertificate("192.168.100.1",
                StubX509Certificate.ipa("1.2.3.4", "192.168.100.1")));
    }

    public void testUnknownSubjectAltName() {
        // Has unknown subject alternative names
        assertTrue(verifyWithServerCertificate("imap.g.com", new StubX509Certificate()
                .addSubjectAlternativeName(ALT_UNKNOWN, "random string 1")
                .addSubjectAlternativeName(ALT_UNKNOWN, "random string 2")
                .addSubjectAlternativeName(ALT_DNS_NAME, "a.b.c.d")
                .addSubjectAlternativeName(ALT_DNS_NAME, "*.google.com")
                .addSubjectAlternativeName(ALT_DNS_NAME, "imap.g.com")
                .addSubjectAlternativeName(ALT_IPA_NAME, "2.33.44.55")
                .addSubjectAlternativeName(ALT_UNKNOWN, "random string 3")));
        assertTrue(verifyWithServerCertificate("2.33.44.55", new StubX509Certificate()
                .addSubjectAlternativeName(ALT_UNKNOWN, "random string 1")
                .addSubjectAlternativeName(ALT_UNKNOWN, "random string 2")
                .addSubjectAlternativeName(ALT_DNS_NAME, "a.b.c.d")
                .addSubjectAlternativeName(ALT_DNS_NAME, "*.google.com")
                .addSubjectAlternativeName(ALT_DNS_NAME, "imap.g.com")
                .addSubjectAlternativeName(ALT_IPA_NAME, "2.33.44.55")
                .addSubjectAlternativeName(ALT_UNKNOWN, "random string 3")));
        assertFalse(verifyWithServerCertificate("g.com", new StubX509Certificate()
                .addSubjectAlternativeName(ALT_UNKNOWN, "random string 1")
                .addSubjectAlternativeName(ALT_UNKNOWN, "random string 2")
                .addSubjectAlternativeName(ALT_DNS_NAME, "a.b.c.d")
                .addSubjectAlternativeName(ALT_DNS_NAME, "*.google.com")
                .addSubjectAlternativeName(ALT_DNS_NAME, "imap.g.com")
                .addSubjectAlternativeName(ALT_IPA_NAME, "2.33.44.55")
                .addSubjectAlternativeName(ALT_UNKNOWN, "random string 3")));
        assertFalse(verifyWithServerCertificate("2.33.44.1", new StubX509Certificate()
                .addSubjectAlternativeName(ALT_UNKNOWN, "random string 1")
                .addSubjectAlternativeName(ALT_UNKNOWN, "random string 2")
                .addSubjectAlternativeName(ALT_DNS_NAME, "a.b.c.d")
                .addSubjectAlternativeName(ALT_DNS_NAME, "*.google.com")
                .addSubjectAlternativeName(ALT_DNS_NAME, "imap.g.com")
                .addSubjectAlternativeName(ALT_IPA_NAME, "2.33.44.55")
                .addSubjectAlternativeName(ALT_UNKNOWN, "random string 3")));
    }

    public void testWildcardsRejectedForIpAddress() {
        assertFalse(verifyWithServerCertificate("1.2.3.4", new StubX509Certificate("*.2.3.4")));
        assertFalse(verifyWithServerCertificate("1.2.3.4", new StubX509Certificate("*.2.3.4")
                .addSubjectAlternativeName(ALT_IPA_NAME, "*.2.3.4")
                .addSubjectAlternativeName(ALT_DNS_NAME, "*.2.3.4")));
        assertFalse(verifyWithServerCertificate(
                "2001:1234::1", new StubX509Certificate("*:1234::1")));
        assertFalse(verifyWithServerCertificate(
                "2001:1234::1", new StubX509Certificate("*:1234::1")
                .addSubjectAlternativeName(ALT_IPA_NAME, "*:1234::1")
                .addSubjectAlternativeName(ALT_DNS_NAME, "*:1234::1")));
    }

    public void testNullParameters() {
        // Confirm that neither of the parameters used later in the test cause the verifier to blow
        // up
        String hostname = "www.example.com";
        StubSSLSession session = new StubSSLSession();
        session.peerCertificates =
                new Certificate[] {new StubX509Certificate("cn=www.example.com")};
        verifier.verify(hostname, session);

        try {
            verifier.verify(hostname, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            verifier.verify(null, session);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void testInvalidDomainNames() {
        assertFalse(verifyWithDomainNamePattern("", ""));
        assertFalse(verifyWithDomainNamePattern(".test.example.com", ".test.example.com"));
        assertFalse(verifyWithDomainNamePattern("ex*ample.com", "ex*ample.com"));
        assertFalse(verifyWithDomainNamePattern("example.com..", "example.com."));
        assertFalse(verifyWithDomainNamePattern("example.com.", "example.com.."));
    }

    public void testWildcardCharacterMustBeLeftMostLabelOnly() {
        assertFalse(verifyWithDomainNamePattern("test.www.example.com", "test.*.example.com"));
        assertFalse(verifyWithDomainNamePattern("www.example.com", "www.*.com"));
        assertFalse(verifyWithDomainNamePattern("www.example.com", "www.example.*"));
        assertFalse(verifyWithDomainNamePattern("www.example.com", "*www.example.com"));
        assertFalse(verifyWithDomainNamePattern("www.example.com", "*w.example.com"));
        assertFalse(verifyWithDomainNamePattern("www.example.com", "w*w.example.com"));
        assertFalse(verifyWithDomainNamePattern("www.example.com", "w*.example.com"));
        assertFalse(verifyWithDomainNamePattern("www.example.com", "www*.example.com"));
    }

    public void testWildcardCannotMatchEmptyLabel() {
        assertFalse(verifyWithDomainNamePattern("example.com", "*.example.com"));
        assertFalse(verifyWithDomainNamePattern(".example.com", "*.example.com"));
    }

    public void testWildcardCannotMatchChildDomain() {
        assertFalse(verifyWithDomainNamePattern("sub.www.example.com", "*.example.com"));
    }

    public void testWildcardRejectedForSingleLabelPatterns() {
        assertFalse(verifyWithDomainNamePattern("d", "*"));
        assertFalse(verifyWithDomainNamePattern("d.", "*."));
        assertFalse(verifyWithDomainNamePattern("d", "d*"));
        assertFalse(verifyWithDomainNamePattern("d.", "d*."));
        assertFalse(verifyWithDomainNamePattern("d", "*d"));
        assertFalse(verifyWithDomainNamePattern("d.", "*d."));
        assertFalse(verifyWithDomainNamePattern("ddd", "d*d"));
        assertFalse(verifyWithDomainNamePattern("ddd.", "d*d."));
    }

    public void testNoPrefixMatch() {
        assertFalse(verifyWithDomainNamePattern("imap.google.com.au", "imap.google.com"));
        assertFalse(verifyWithDomainNamePattern("imap.google.com.au", "*.google.com"));
    }

    public void testVerifyHostName() {
        assertTrue(verifyWithDomainNamePattern("a.b.c.d", "a.b.c.d"));
        assertTrue(verifyWithDomainNamePattern("a.b.c.d", "*.b.c.d"));
        assertFalse(verifyWithDomainNamePattern("a.b.c.d", "*.*.c.d"));
        assertTrue(verifyWithDomainNamePattern("imap.google.com", "imap.google.com"));
        assertFalse(verifyWithDomainNamePattern("imap2.google.com", "imap.google.com"));
        assertTrue(verifyWithDomainNamePattern("imap.google.com", "*.google.com"));
        assertTrue(verifyWithDomainNamePattern("imap2.google.com", "*.google.com"));
        assertFalse(verifyWithDomainNamePattern("imap.google.com", "*.googl.com"));
        assertFalse(verifyWithDomainNamePattern("imap2.google2.com", "*.google3.com"));
        assertFalse(verifyWithDomainNamePattern("imap.google.com", "a*.google.com"));
        assertFalse(verifyWithDomainNamePattern("imap.google.com", "ix*.google.com"));
        assertTrue(verifyWithDomainNamePattern("imap.google.com", "iMap.Google.Com"));
        assertTrue(verifyWithDomainNamePattern("weird", "weird"));
        assertTrue(verifyWithDomainNamePattern("weird", "weird."));

        // Wildcards rejected for domain names consisting of fewer than two labels (excluding root).
        assertFalse(verifyWithDomainNamePattern("weird", "weird*"));
        assertFalse(verifyWithDomainNamePattern("weird", "*weird"));
        assertFalse(verifyWithDomainNamePattern("weird", "weird*."));
        assertFalse(verifyWithDomainNamePattern("weird", "weird.*"));
    }

    public void testVerifyAbsoluteHostName() {
        assertTrue(verifyWithDomainNamePattern("a.b.c.d.", "a.b.c.d"));
        assertTrue(verifyWithDomainNamePattern("a.b.c.d.", "*.b.c.d"));
        assertFalse(verifyWithDomainNamePattern("a.b.c.d.", "*.*.c.d"));
        assertTrue(verifyWithDomainNamePattern("imap.google.com.", "imap.google.com"));
        assertFalse(verifyWithDomainNamePattern("imap2.google.com.", "imap.google.com"));
        assertTrue(verifyWithDomainNamePattern("imap.google.com.", "*.google.com"));
        assertTrue(verifyWithDomainNamePattern("imap2.google.com.", "*.google.com"));
        assertFalse(verifyWithDomainNamePattern("imap.google.com.", "*.googl.com"));
        assertFalse(verifyWithDomainNamePattern("imap2.google2.com.", "*.google3.com"));
        assertFalse(verifyWithDomainNamePattern("imap.google.com.", "a*.google.com"));
        assertFalse(verifyWithDomainNamePattern("imap.google.com.", "ix*.google.com"));
        assertTrue(verifyWithDomainNamePattern("imap.google.com.", "iMap.Google.Com"));
        assertTrue(verifyWithDomainNamePattern("weird.", "weird"));
        assertTrue(verifyWithDomainNamePattern("weird.", "weird."));

        // Wildcards rejected for domain names consisting of fewer than two labels (excluding root).
        assertFalse(verifyWithDomainNamePattern("weird.", "*weird"));
        assertFalse(verifyWithDomainNamePattern("weird.", "weird*"));
        assertFalse(verifyWithDomainNamePattern("weird.", "weird*."));
        assertFalse(verifyWithDomainNamePattern("weird.", "weird.*"));
    }

    public void testSubjectOnlyCert() throws Exception {
        // subject: C=JP, CN=www.example.com
        // subject alt names: n/a
        X509Certificate cert = parseCertificate("-----BEGIN CERTIFICATE-----\n"
                + "MIIC0TCCAbmgAwIBAgIJANCQbJPPw31SMA0GCSqGSIb3DQEBBQUAMCcxCzAJBgNV\n"
                + "BAYTAkpQMRgwFgYDVQQDEw93d3cuZXhhbXBsZS5jb20wIBcNMTAwMTEyMjA1ODE4\n"
                + "WhgPMjA2NDEwMTUyMDU4MThaMCcxCzAJBgNVBAYTAkpQMRgwFgYDVQQDEw93d3cu\n"
                + "ZXhhbXBsZS5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDsdUJk\n"
                + "4KxADA3vlDHxNbyC27Ozw4yiSVzPTHUct471YmdDRW3orO2P5a5hRnUGV70gjH9X\n"
                + "MU4oeOdWYAgXB9pxfLyr6621k1+uNrmaZtzp0ECH9twcwxNJJFDZsN7o9vt7V6Ej\n"
                + "NN9weeqDr/aeQXo07a12vyVfR6jWO8jHB0e4aemwZNoYjNvM69fivQTse2ZoRVfj\n"
                + "eSHhjRTX6I8ry4a31Hwt+fT1QiWWNN6o7+WOtpJAhX3eg4smhSD1svi2kOT8tdUe\n"
                + "NS4hWlmXmumU9G4tI8PBurcLNTm7PB2lUlbn/IV18WavqKE/Uy/1WgAx+a1EJNdp\n"
                + "i07AG1PsqaONKkf1AgMBAAEwDQYJKoZIhvcNAQEFBQADggEBAJrNsuL7fZZNC8gL\n"
                + "BdePJ7DYW2e7mXANU3bCBe2BZqmXKQxKwibZnEsqA+yMLqcSd8uxISlyHY2tw9wT\n"
                + "4wB9KPIttfNLbwn/rk+MbOTHpvyF60d9WhJJVUkPBl8D4VuPSl+VnlA54kU9dtZN\n"
                + "+ZYdxYbNtSsI/Flz9SCoOV79W9GhN+uYJhv6RwyIMIHeMpZpyX1xSUVx5dZlmerQ\n"
                + "WAUvghDH3fFRt2ZdnA4OXoKkTAaM3Pv7PUMsnah8bux6MQi0AuLMWFWOI1H34koH\n"
                + "rs2oQLwOLnuifH52ey9+tJguabo+brlYYigAuWWFEzJfBzikDkIwnE/L7wlrypIk\n"
                + "taXDWI4=\n"
                + "-----END CERTIFICATE-----");
        assertFalse(verifyWithServerCertificate("www.example.com", cert));
        assertFalse(verifyWithServerCertificate("www2.example.com", cert));
    }

    public void testSubjectAltOnlyCert() throws Exception {
        // subject: C=JP (no CN)
        // subject alt names: DNS:www.example.com
        X509Certificate cert = parseCertificate("-----BEGIN CERTIFICATE-----\n"
                + "MIICvTCCAaWgAwIBAgIJALbA0TZk2YmNMA0GCSqGSIb3DQEBBQUAMA0xCzAJBgNV\n"
                + "BAYTAkpQMCAXDTEwMDExMjIwNTg1NFoYDzIwNjQxMDE1MjA1ODU0WjANMQswCQYD\n"
                + "VQQGEwJKUDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMEg6acVC9V4\n"
                + "xNGoLNVLPbqBc8IvMvcsc88dF6MW3d9VagX3aeWU8c79tI/KOV/1AOakH7WYxw/w\n"
                + "yD8aOX7+9BK1Hu0qKKKbSM+ycqaMthXd6xytrNDsIx5WiGUz8zTko0Gk3orIR7p7\n"
                + "rPcNzB/zwtESkscqPv85aEn7S/yClNkzLfEzm3CtaYOc0tfhBMyzi/ipXzGMxUmx\n"
                + "PvOLr3v/Oz5pZEQw7Kxlm4+tAtn7bJlHziQ1UW4WPIy+T3hySBEpODFiqZi7Ok3X\n"
                + "Zjxdii62fgo5B2Ee7q5Amo0mUIwcQTDjJ2CLAqzYnSh3tpiPJGjEIjmRyCoMQ1bx\n"
                + "7D+y7nSPIq8CAwEAAaMeMBwwGgYDVR0RBBMwEYIPd3d3LmV4YW1wbGUuY29tMA0G\n"
                + "CSqGSIb3DQEBBQUAA4IBAQBsGEh+nHc0l9FJTzWqvG3qs7i6XoJZdtThCDx4HjKJ\n"
                + "8GMrJtreNN4JvIxn7KC+alVbnILjzCRO+c3rsnpxKBi5cp2imjuw5Kf/x2Seimb9\n"
                + "UvZbaJvBVOzy4Q1IGef9bLy3wZzy2/WfBFyvPTAkgkRaX7LN2jnYOYVhNoNFrwqe\n"
                + "EWxkA6fzrpyseUEFeGFFjGxRSRCDcQ25Eq6d9rkC1x21zNtt4QwZBO0wHrTy155M\n"
                + "JPRynf9244Pn0Sr/wsnmdsTRFIFYynrc51hQ7DkwbUxpcaewkZzilru/SwZ3+pPT\n"
                + "9JSqm5hJ1pg5WDlPkW7c/1VA0/141N52Q8MIU+2ZpuOj\n"
                + "-----END CERTIFICATE-----");
        assertTrue(verifyWithServerCertificate("www.example.com", cert));
        assertFalse(verifyWithServerCertificate("www2.example.com", cert));
    }

    public void testSubjectWithAltNamesCert() throws Exception {
        // subject: C=JP, CN=www.example.com
        // subject alt names: DNS:www2.example.com, DNS:www3.example.com
        // * Subject should be ignored, because it has subject alt names.
        X509Certificate cert = parseCertificate("-----BEGIN CERTIFICATE-----\n"
                + "MIIDBDCCAeygAwIBAgIJALv14qjcuhw9MA0GCSqGSIb3DQEBBQUAMCcxCzAJBgNV\n"
                + "BAYTAkpQMRgwFgYDVQQDEw93d3cuZXhhbXBsZS5jb20wIBcNMTAwMTEyMjA1OTM4\n"
                + "WhgPMjA2NDEwMTUyMDU5MzhaMCcxCzAJBgNVBAYTAkpQMRgwFgYDVQQDEw93d3cu\n"
                + "ZXhhbXBsZS5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCiTVgU\n"
                + "kBO9KNYZZLmiPR0eBrk8u61CLnm35BGKW8EFpDaINLbbIFIQvqOMekURON/N+xFY\n"
                + "D8roo7aFZVuHWAUqFcOJ4e6NmviK5qocLihtzAexsw4f4AzZxM3A8kcLlWLyAt7e\n"
                + "EVLxhcMHogY7GaF6q+33Z8p+zp6x3tj07mwyPrriCLse2PeRsRunZl/fp/VvRlr6\n"
                + "YbC7CbRrhnIv5nqohs8BsbBiiFpxQftsMQmiXhY2LUzqY2RXUIOw24fHjoQkHTL2\n"
                + "4z5nUM3b6ueQe+CBnobUS6fzK/36Nct4dRpev9i/ORdRLuIDKJ+QR16G1V/BJYBR\n"
                + "dAK+3iXvg6z8vP1XAgMBAAGjMTAvMC0GA1UdEQQmMCSCEHd3dzIuZXhhbXBsZS5j\n"
                + "b22CEHd3dzMuZXhhbXBsZS5jb20wDQYJKoZIhvcNAQEFBQADggEBAJQNf38uXm3h\n"
                + "0vsF+Yd6/HqM48Su7tWnTDAfTXnQZZkzjzITq3JXzquMXICktAVN2cLnT9zPfRAE\n"
                + "8V8A3BNO5zXiR5W3o/mJP5HQ3/WxpzBGM2N+YmDCJyBoQrIVaAZaXAZUaBBvn5A+\n"
                + "kEVfGWquwIFuvA67xegbJOCRLD4eUzRdNsn5+NFiakWO1tkFqEzqyQ0PNPviRjgu\n"
                + "z9NxdPvd1JQOhydkucsPKJzlEBbGyL5QL/Jkot3Qy+FOeuNzgQUfAGtQgzRrsZDK\n"
                + "hrTVypLSoRXuTB2aWilu4p6aNh84xTdyqo2avtNr2MiQMZIcdamBq8LdBIAShFXI\n"
                + "h5G2eVGXH/Y=\n"
                + "-----END CERTIFICATE-----");
        assertFalse(verifyWithServerCertificate("www.example.com", cert));
        assertTrue(verifyWithServerCertificate("www2.example.com", cert));
        assertTrue(verifyWithServerCertificate("www3.example.com", cert));
        assertFalse(verifyWithServerCertificate("www4.example.com", cert));
    }

    public void testSubjectWithWildAltNamesCert() throws Exception {
        // subject: C=JP, CN=www.example.com
        // subject alt names: DNS:*.example2.com
        // CN should be ignored in all cases, only subject alt names should be considered.
        X509Certificate cert = parseCertificate("-----BEGIN CERTIFICATE-----\n"
                + "MIIC8DCCAdigAwIBAgIJAL/oWJ64VAdXMA0GCSqGSIb3DQEBBQUAMCcxCzAJBgNV\n"
                + "BAYTAkpQMRgwFgYDVQQDEw93d3cuZXhhbXBsZS5jb20wIBcNMTAwMTEyMjEwMDAx\n"
                + "WhgPMjA2NDEwMTUyMTAwMDFaMCcxCzAJBgNVBAYTAkpQMRgwFgYDVQQDEw93d3cu\n"
                + "ZXhhbXBsZS5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCbx1QB\n"
                + "92iea7VybLYICA4MX4LWipYrRsgXUXQrcIQ3YLTQ9rH0VwScrHL4O4JDxgXCQnR+\n"
                + "4VOzD42q1KXHJAqzqGUYCNPyvZEzkGCnQ4FBIUEmxZd5SNEefJVH3Z6GizYJomTh\n"
                + "p78yDcoqymD9umxRC2cWFu8GscfFGMVyhsqLlOofu7UWOs22mkXPo43jDx+VOAoV\n"
                + "n48YP3P57a2Eo0gcd4zVL00y62VegqBO/1LW38aTS7teiCBFc1TkNYa5I40yN9lP\n"
                + "rB9ICHYQWyzf/7OxU9iauEK2w6DmSsQoLs9JzEhgeNZddkcc77ciSUCo2Hx0VpOJ\n"
                + "BFyf2rbryJeAk+FDAgMBAAGjHTAbMBkGA1UdEQQSMBCCDiouZXhhbXBsZTIuY29t\n"
                + "MA0GCSqGSIb3DQEBBQUAA4IBAQA2a14pRL+4laJ8sscQlucaDB/oSdb0cwhk4IkE\n"
                + "kKl/ZKr6rKwPZ81sJRgzvI4imLbUAKt4AJHdpI9cIQUq1gw9bzil7LKwmFtFSPmC\n"
                + "MYb1iadaYrvp7RE4yXrWCcSbU0hup9JQLHTrHLlqLtRuU48NHMvWYThBcS9Q/hQp\n"
                + "nJ/JxYy3am99MHALWLAfuRxQXhE4C5utDmBwI2KD6A8SA30s+CnuegmkYScuSqBu\n"
                + "Y3R0HZvKzNIU3pwAm69HCJoG+/9MZEIDJb0WJc5UygxDT45XE9zQMQe4dBOTaNXT\n"
                + "+ntgaB62kE10HzrzpqXAgoAWxWK4RzFcUpBWw9qYq9xOCewJ\n"
                + "-----END CERTIFICATE-----");
        assertFalse(verifyWithServerCertificate("www.example.com", cert));
        assertFalse(verifyWithServerCertificate("www2.example.com", cert));
        assertTrue(verifyWithServerCertificate("www.example2.com", cert));
        assertTrue(verifyWithServerCertificate("abc.example2.com", cert));
        assertFalse(verifyWithServerCertificate("www.example3.com", cert));
    }

    public void testWildAltNameOnlyCert() throws Exception {
        // subject: C=JP
        // subject alt names: DNS:*.example.com
        // CN should be ignored in all cases, only subject alt names should be considered.
        X509Certificate cert = parseCertificate("-----BEGIN CERTIFICATE-----\n"
                + "MIICuzCCAaOgAwIBAgIJAP82tgcvmAGxMA0GCSqGSIb3DQEBBQUAMA0xCzAJBgNV\n"
                + "BAYTAkpQMCAXDTEwMDExMjIxMDAyN1oYDzIwNjQxMDE1MjEwMDI3WjANMQswCQYD\n"
                + "VQQGEwJKUDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALs528EQbcB1\n"
                + "x4BwxthQBZrgDJzoO7KPV3dhGYoeP8EnRjapZm+T/sj9P/O4HvfxjnB+fsjYSdmE\n"
                + "WWUtnFrP7wtG9DUC748Ea2PMV8WFhOG58dqBNIko5XzkHB7SxkNZD5S/0KQYMGLr\n"
                + "rchDsDlmsEf2Qb6qiqpNEU70aSkExZJcH+B9nWdeBpsVFu7wtezwSWEc2NUa2bhW\n"
                + "gcXQ/aafwHZ4o2PyGwy0sgS/UifqO9tEllC2tPleSNJOmYsVudv5Bz4Q0GG38BSz\n"
                + "Pc0IcOoln0ZWpXbGr03V2vlXWCwzaFAl3I1T3O7YVqDiaSWoP+d0tHZzmw8aJLXd\n"
                + "B+KaUUGxRPsCAwEAAaMcMBowGAYDVR0RBBEwD4INKi5leGFtcGxlLmNvbTANBgkq\n"
                + "hkiG9w0BAQUFAAOCAQEAJbVan4QgJ0cvpJnK9UWIVJNC+UbP87RC5go2fQiTnmGv\n"
                + "prOrIuMqz1+vGcpIheLTLctJRHPoadXq0+UbQEIaU3pQbY6C4nNdfl+hcvmJeqrt\n"
                + "kOCcvmIamO68iNvTSeszuHuu4O38PefrW2Xd0nn7bjFZrzBzHFhTudmnqNliP3ue\n"
                + "KKQpqkUt5lCytnH8V/u/UCWdvVx5LnUa2XFGVLi3ongBIojW5fvF+yxn9ADqxdrI\n"
                + "va++ow5r1VxQXFJc0ZPzsDo+6TlktoDHaRQJGMqQomqHWT4i7F5UZgf6BHGfEUPU\n"
                + "qep+GsF3QRHSBtpObWkVDZNFvky3a1iZ2q25+hFIqQ==\n"
                + "-----END CERTIFICATE-----");
        assertTrue(verifyWithServerCertificate("www.example.com", cert));
        assertTrue(verifyWithServerCertificate("www2.example.com", cert));
        assertFalse(verifyWithServerCertificate("www.example2.com", cert));
    }

    public void testAltIpOnlyCert() throws Exception {
        // subject: C=JP
        // subject alt names: IP Address:192.168.10.1
        // CN should be ignored in all cases, only subject alt names should be considered.
        X509Certificate cert = parseCertificate("-----BEGIN CERTIFICATE-----\n"
                + "MIICsjCCAZqgAwIBAgIJALrC37YAXFIeMA0GCSqGSIb3DQEBBQUAMA0xCzAJBgNV\n"
                + "BAYTAkpQMCAXDTEwMDExMjIxMzk0NloYDzIwNjQxMDE1MjEzOTQ2WjANMQswCQYD\n"
                + "VQQGEwJKUDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALr8s/4Abpby\n"
                + "IYks5YCJE2nbWH7kj6XbwnRzsVP9RVC33bPoQ1M+2ZY24HqkigjQS/HEXR0s0bYh\n"
                + "dewNUnTj1uGyGs6cYzsbu7x114vmVYqjxUo3hKjwfYiPeF6f3IE1vpLI7I2G32gq\n"
                + "Zwm9c1/vXNHIdWQxCpFcuPA8P3YGfoApFX4pQPFplBUNAQqnjdmA68cbxxMC+1F3\n"
                + "mX42D7iIEVwyVpah5HjyxjIZQlf3X7QBj0bCmkL+ibIHTALrkNNwNM6i4xzYLz/5\n"
                + "14GkN9ncHY87eSOk6r53ptER6mQMhCe9qPRjSHnpWTTyj6IXTaYe+dDQw657B80w\n"
                + "cSHL7Ed25zUCAwEAAaMTMBEwDwYDVR0RBAgwBocEwKgKATANBgkqhkiG9w0BAQUF\n"
                + "AAOCAQEAgrwrtOWZT3fbi1AafpGaAiOBWSJqYqRhtQy0AfiZBxv1U0XaYqmZmpnq\n"
                + "DVAqr0NkljowD28NBrxIFO5gBNum2ZOPDl2/5vjFn+IirUCJ9u9wS7zYkTCW2lQR\n"
                + "xE7Ic3mfWv7wUbKDfjlWqP1IDHUxwkrBTAl+HnwOPiaKKk1ttwcrgS8AHlqASe03\n"
                + "mlwnvJ+Stk54IneRaegL0L93sNAy63RZqnPCTxGz7eHcFwX8Jdr4sbxTxQqV6pIc\n"
                + "WPjHQcWfpkFzAF5wyOq0kveVfx0g5xPhOVDd+U+q7WastbXICpCoHp9FxISmZVik\n"
                + "sAyifp8agkYdzaSh55fFmKXlFnRsQw==\n"
                + "-----END CERTIFICATE-----");
        assertTrue(verifyWithServerCertificate("192.168.10.1", cert));
        assertFalse(verifyWithServerCertificate("192.168.10.2", cert));
    }

    /**
     * Verifies the provided hostname against the provided domain name pattern from server
     * certificate.
     */
    private boolean verifyWithDomainNamePattern(String hostname, String pattern) {
        StubSSLSession session = new StubSSLSession();

        // Verify using a certificate where the pattern is in the CN
        session.peerCertificates = new Certificate[] {
                new StubX509Certificate("cn=\"" + pattern + "\"")
        };
        assertFalse("Verifier should ignore CN.", verifier.verify(hostname, session));

        // Verify using a certificate where the pattern is in a DNS SubjectAltName
        session.peerCertificates = new Certificate[] {
                new StubX509Certificate("ou=test")
                        .addSubjectAlternativeName(ALT_DNS_NAME, pattern)
        };
        return verifier.verify(hostname, session);
    }

    /**
     * Verifies the provided hostname against the provided server certificate.
     */
    private boolean verifyWithServerCertificate(String hostname, X509Certificate certificate) {
        StubSSLSession session = new StubSSLSession();
        session.peerCertificates =
                (certificate != null) ? new Certificate[] {certificate} : new Certificate[0];
        return verifier.verify(hostname, session);
    }

    X509Certificate parseCertificate(String encoded) throws Exception {
        InputStream in = new ByteArrayInputStream(encoded.getBytes(StandardCharsets.US_ASCII));
        return (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(in);
    }

    private static class StubSSLSession implements SSLSession {

        public Certificate[] peerCertificates = new Certificate[0];

        @Override
        public int getApplicationBufferSize() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getCipherSuite() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getCreationTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte[] getId() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getLastAccessedTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Certificate[] getLocalCertificates() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Principal getLocalPrincipal() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getPacketBufferSize() {
            throw new UnsupportedOperationException();
        }

        @Override
        public javax.security.cert.X509Certificate[] getPeerCertificateChain()
                throws SSLPeerUnverifiedException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
            return peerCertificates;
        }

        @Override
        public String getPeerHost() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getPeerPort() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getProtocol() {
            throw new UnsupportedOperationException();
        }

        @Override
        public SSLSessionContext getSessionContext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getValue(String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String[] getValueNames() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void invalidate() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public void putValue(String name, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void removeValue(String name) {
            throw new UnsupportedOperationException();
        }
    }

    private static class StubX509Certificate extends X509Certificate {
        private final X500Principal subjectX500Principal;
        private Collection<List<?>> subjectAlternativeNames;

        public StubX509Certificate() {
            subjectX500Principal = new X500Principal("");
            subjectAlternativeNames = null;
        }

        public StubX509Certificate(String commonName) {
            subjectX500Principal = new X500Principal("cn=" + commonName);
            subjectAlternativeNames = null;
        }

        public static StubX509Certificate of(int type, String... altNames) {
            StubX509Certificate result = new StubX509Certificate();
            for (String altName : altNames) {
                result.addSubjectAlternativeName(type, altName);
            }
            return result;
        }

        /**
         * A StubX509Certificate with {@link #ALT_DNS_NAME} subjectAlternativeNames.
         */
        public static StubX509Certificate dns(String... dnsNames) {
            return of(ALT_DNS_NAME, dnsNames);
        }

        /**
         * A StubX509Certificate with {@link #ALT_IPA_NAME} subjectAlternativeNames.
         */
        public static StubX509Certificate ipa(String... ipaNames) {
            return of(ALT_IPA_NAME, ipaNames);
        }


        public final StubX509Certificate addSubjectAlternativeName(int type, String name) {
            if (subjectAlternativeNames == null) {
                subjectAlternativeNames = new ArrayList<List<?>>();
            }
            LinkedList<Object> entry = new LinkedList<Object>();
            entry.add(type);
            entry.add(name);
            subjectAlternativeNames.add(entry);
            return this;
        }

        @Override public Collection<List<?>> getSubjectAlternativeNames() {
            return subjectAlternativeNames;
        }

        @Override public X500Principal getSubjectX500Principal() {
            return subjectX500Principal;
        }

        @Override public void checkValidity() {
            throw new UnsupportedOperationException();
        }

        @Override public void checkValidity(Date date) {
            throw new UnsupportedOperationException();
        }

        @Override public int getBasicConstraints() {
            throw new UnsupportedOperationException();
        }

        @Override public Principal getIssuerDN() {
            throw new UnsupportedOperationException();
        }

        @Override public boolean[] getIssuerUniqueID() {
            throw new UnsupportedOperationException();
        }

        @Override public boolean[] getKeyUsage() {
            throw new UnsupportedOperationException();
        }

        @Override public Date getNotAfter() {
            throw new UnsupportedOperationException();
        }

        @Override public Date getNotBefore() {
            throw new UnsupportedOperationException();
        }

        @Override public BigInteger getSerialNumber() {
            throw new UnsupportedOperationException();
        }

        @Override public String getSigAlgName() {
            throw new UnsupportedOperationException();
        }

        @Override public String getSigAlgOID() {
            throw new UnsupportedOperationException();
        }

        @Override public byte[] getSigAlgParams() {
            throw new UnsupportedOperationException();
        }

        @Override public byte[] getSignature() {
            throw new UnsupportedOperationException();
        }

        @Override public Principal getSubjectDN() {
            throw new UnsupportedOperationException();
        }

        @Override public boolean[] getSubjectUniqueID() {
            throw new UnsupportedOperationException();
        }

        @Override public byte[] getTBSCertificate() {
            throw new UnsupportedOperationException();
        }

        @Override public int getVersion() {
            throw new UnsupportedOperationException();
        }

        @Override public byte[] getEncoded() {
            throw new UnsupportedOperationException();
        }

        @Override public PublicKey getPublicKey() {
            throw new UnsupportedOperationException();
        }

        @Override public String toString() {
            throw new UnsupportedOperationException();
        }

        @Override public void verify(PublicKey key) {
            throw new UnsupportedOperationException();
        }

        @Override public void verify(PublicKey key, String sigProvider) {
            throw new UnsupportedOperationException();
        }

        @Override public Set<String> getCriticalExtensionOIDs() {
            throw new UnsupportedOperationException();
        }

        @Override public byte[] getExtensionValue(String oid) {
            throw new UnsupportedOperationException();
        }

        @Override public Set<String> getNonCriticalExtensionOIDs() {
            throw new UnsupportedOperationException();
        }

        @Override public boolean hasUnsupportedCriticalExtension() {
            throw new UnsupportedOperationException();
        }
    }
}
