/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package sun.security.x509;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class X509CRLImpl extends java.security.cert.X509CRL
        implements sun.security.util.DerEncoder {

    private X509CRLImpl() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X509CRLImpl(byte[] crlData) throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X509CRLImpl(sun.security.util.DerValue val) throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X509CRLImpl(java.io.InputStream inStrm) throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    public X509CRLImpl(
            sun.security.x509.X500Name issuer, java.util.Date thisDate, java.util.Date nextDate) {
        throw new RuntimeException("Stub!");
    }

    public X509CRLImpl(
            sun.security.x509.X500Name issuer,
            java.util.Date thisDate,
            java.util.Date nextDate,
            java.security.cert.X509CRLEntry[] badCerts)
            throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    public X509CRLImpl(
            sun.security.x509.X500Name issuer,
            java.util.Date thisDate,
            java.util.Date nextDate,
            java.security.cert.X509CRLEntry[] badCerts,
            sun.security.x509.CRLExtensions crlExts)
            throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public byte[] getEncodedInternal() throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    public byte[] getEncoded() throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    public void encodeInfo(java.io.OutputStream out) throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    public void verify(java.security.PublicKey key)
            throws java.security.cert.CRLException, java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException,
                    java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void verify(java.security.PublicKey key, java.lang.String sigProvider)
            throws java.security.cert.CRLException, java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException,
                    java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void verify(java.security.PublicKey key, java.security.Provider sigProvider)
            throws java.security.cert.CRLException, java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException, java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public void sign(java.security.PrivateKey key, java.lang.String algorithm)
            throws java.security.cert.CRLException, java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException,
                    java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public void sign(
            java.security.PrivateKey key, java.lang.String algorithm, java.lang.String provider)
            throws java.security.cert.CRLException, java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException,
                    java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public boolean isRevoked(java.security.cert.Certificate cert) {
        throw new RuntimeException("Stub!");
    }

    public int getVersion() {
        throw new RuntimeException("Stub!");
    }

    public java.security.Principal getIssuerDN() {
        throw new RuntimeException("Stub!");
    }

    public javax.security.auth.x500.X500Principal getIssuerX500Principal() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Date getThisUpdate() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Date getNextUpdate() {
        throw new RuntimeException("Stub!");
    }

    public java.security.cert.X509CRLEntry getRevokedCertificate(
            java.math.BigInteger serialNumber) {
        throw new RuntimeException("Stub!");
    }

    public java.security.cert.X509CRLEntry getRevokedCertificate(
            java.security.cert.X509Certificate cert) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Set<java.security.cert.X509CRLEntry> getRevokedCertificates() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getTBSCertList() throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    public byte[] getSignature() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getSigAlgName() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getSigAlgOID() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getSigAlgParams() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.AlgorithmId getSigAlgId() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.KeyIdentifier getAuthKeyId() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.AuthorityKeyIdentifierExtension getAuthKeyIdExtension()
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.CRLNumberExtension getCRLNumberExtension() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.math.BigInteger getCRLNumber() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.DeltaCRLIndicatorExtension getDeltaCRLIndicatorExtension()
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.math.BigInteger getBaseCRLNumber() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.IssuerAlternativeNameExtension getIssuerAltNameExtension()
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.IssuingDistributionPointExtension
            getIssuingDistributionPointExtension() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean hasUnsupportedCriticalExtension() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Set<java.lang.String> getCriticalExtensionOIDs() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Set<java.lang.String> getNonCriticalExtensionOIDs() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getExtensionValue(java.lang.String oid) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object getExtension(sun.security.util.ObjectIdentifier oid) {
        throw new RuntimeException("Stub!");
    }

    private void parse(sun.security.util.DerValue val)
            throws java.security.cert.CRLException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public static javax.security.auth.x500.X500Principal getIssuerX500Principal(
            java.security.cert.X509CRL crl) {
        throw new RuntimeException("Stub!");
    }

    public static byte[] getEncodedInternal(java.security.cert.X509CRL crl)
            throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.x509.X509CRLImpl toImpl(java.security.cert.X509CRL crl)
            throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    private javax.security.auth.x500.X500Principal getCertIssuer(
            sun.security.x509.X509CRLEntryImpl entry,
            javax.security.auth.x500.X500Principal prevCertIssuer)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void derEncode(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static final long YR_2050 = 2524636800000L; // 0x24bd0146400L

    private sun.security.x509.CRLExtensions extensions;

    private sun.security.x509.AlgorithmId infoSigAlgId;

    private static final boolean isExplicit = true;

    private sun.security.x509.X500Name issuer;

    private javax.security.auth.x500.X500Principal issuerPrincipal;

    private java.util.Date nextUpdate;

    private boolean readOnly = false;

    private java.util.List<java.security.cert.X509CRLEntry> revokedList;

    private java.util.Map<
                    sun.security.x509.X509CRLImpl.X509IssuerSerial, java.security.cert.X509CRLEntry>
            revokedMap;

    private sun.security.x509.AlgorithmId sigAlgId;

    private byte[] signature;

    private byte[] signedCRL;

    private byte[] tbsCertList;

    private java.util.Date thisUpdate;

    private java.lang.String verifiedProvider;

    private java.security.PublicKey verifiedPublicKey;

    private int version;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static final class X509IssuerSerial
            implements java.lang.Comparable<sun.security.x509.X509CRLImpl.X509IssuerSerial> {

        X509IssuerSerial(
                javax.security.auth.x500.X500Principal issuer, java.math.BigInteger serial) {
            throw new RuntimeException("Stub!");
        }

        X509IssuerSerial(java.security.cert.X509Certificate cert) {
            throw new RuntimeException("Stub!");
        }

        javax.security.auth.x500.X500Principal getIssuer() {
            throw new RuntimeException("Stub!");
        }

        java.math.BigInteger getSerial() {
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public int compareTo(sun.security.x509.X509CRLImpl.X509IssuerSerial another) {
            throw new RuntimeException("Stub!");
        }

        volatile int hashcode = 0; // 0x0

        final javax.security.auth.x500.X500Principal issuer;

        {
            issuer = null;
        }

        final java.math.BigInteger serial;

        {
            serial = null;
        }
    }
}
