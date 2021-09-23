/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
public class X509CertImpl extends java.security.cert.X509Certificate
        implements sun.security.util.DerEncoder {

    public X509CertImpl() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X509CertImpl(byte[] certData) throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X509CertImpl(sun.security.x509.X509CertInfo certInfo) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X509CertImpl(sun.security.util.DerValue derVal)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    public X509CertImpl(sun.security.util.DerValue derVal, byte[] encoded)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    public void encode(java.io.OutputStream out)
            throws java.security.cert.CertificateEncodingException {
        throw new RuntimeException("Stub!");
    }

    public void derEncode(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public byte[] getEncoded() throws java.security.cert.CertificateEncodingException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public byte[] getEncodedInternal() throws java.security.cert.CertificateEncodingException {
        throw new RuntimeException("Stub!");
    }

    public void verify(java.security.PublicKey key)
            throws java.security.cert.CertificateException, java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException,
                    java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void verify(java.security.PublicKey key, java.lang.String sigProvider)
            throws java.security.cert.CertificateException, java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException,
                    java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void verify(java.security.PublicKey key, java.security.Provider sigProvider)
            throws java.security.cert.CertificateException, java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException, java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public static void verify(
            java.security.cert.X509Certificate cert,
            java.security.PublicKey key,
            java.security.Provider sigProvider)
            throws java.security.cert.CertificateException, java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException, java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void sign(java.security.PrivateKey key, java.lang.String algorithm)
            throws java.security.cert.CertificateException, java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException,
                    java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public void sign(
            java.security.PrivateKey key, java.lang.String algorithm, java.lang.String provider)
            throws java.security.cert.CertificateException, java.security.InvalidKeyException,
                    java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException,
                    java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public void checkValidity()
            throws java.security.cert.CertificateExpiredException,
                    java.security.cert.CertificateNotYetValidException {
        throw new RuntimeException("Stub!");
    }

    public void checkValidity(java.util.Date date)
            throws java.security.cert.CertificateExpiredException,
                    java.security.cert.CertificateNotYetValidException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.lang.Object get(java.lang.String name)
            throws java.security.cert.CertificateParsingException {
        throw new RuntimeException("Stub!");
    }

    public void set(java.lang.String name, java.lang.Object obj)
            throws java.security.cert.CertificateException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void delete(java.lang.String name)
            throws java.security.cert.CertificateException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<java.lang.String> getElements() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public java.security.PublicKey getPublicKey() {
        throw new RuntimeException("Stub!");
    }

    public int getVersion() {
        throw new RuntimeException("Stub!");
    }

    public java.math.BigInteger getSerialNumber() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.SerialNumber getSerialNumberObject() {
        throw new RuntimeException("Stub!");
    }

    public java.security.Principal getSubjectDN() {
        throw new RuntimeException("Stub!");
    }

    public javax.security.auth.x500.X500Principal getSubjectX500Principal() {
        throw new RuntimeException("Stub!");
    }

    public java.security.Principal getIssuerDN() {
        throw new RuntimeException("Stub!");
    }

    public javax.security.auth.x500.X500Principal getIssuerX500Principal() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Date getNotBefore() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Date getNotAfter() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getTBSCertificate() throws java.security.cert.CertificateEncodingException {
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

    public boolean[] getIssuerUniqueID() {
        throw new RuntimeException("Stub!");
    }

    public boolean[] getSubjectUniqueID() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.KeyIdentifier getAuthKeyId() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.KeyIdentifier getSubjectKeyId() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.AuthorityKeyIdentifierExtension getAuthorityKeyIdentifierExtension() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.BasicConstraintsExtension getBasicConstraintsExtension() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.CertificatePoliciesExtension getCertificatePoliciesExtension() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.ExtendedKeyUsageExtension getExtendedKeyUsageExtension() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.IssuerAlternativeNameExtension getIssuerAlternativeNameExtension() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.NameConstraintsExtension getNameConstraintsExtension() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.PolicyConstraintsExtension getPolicyConstraintsExtension() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.PolicyMappingsExtension getPolicyMappingsExtension() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.PrivateKeyUsageExtension getPrivateKeyUsageExtension() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.SubjectAlternativeNameExtension getSubjectAlternativeNameExtension() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.SubjectKeyIdentifierExtension getSubjectKeyIdentifierExtension() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.CRLDistributionPointsExtension getCRLDistributionPointsExtension() {
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

    public sun.security.x509.Extension getExtension(sun.security.util.ObjectIdentifier oid) {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.Extension getUnparseableExtension(
            sun.security.util.ObjectIdentifier oid) {
        throw new RuntimeException("Stub!");
    }

    public byte[] getExtensionValue(java.lang.String oid) {
        throw new RuntimeException("Stub!");
    }

    public boolean[] getKeyUsage() {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.util.List<java.lang.String> getExtendedKeyUsage()
            throws java.security.cert.CertificateParsingException {
        throw new RuntimeException("Stub!");
    }

    public static java.util.List<java.lang.String> getExtendedKeyUsage(
            java.security.cert.X509Certificate cert)
            throws java.security.cert.CertificateParsingException {
        throw new RuntimeException("Stub!");
    }

    public int getBasicConstraints() {
        throw new RuntimeException("Stub!");
    }

    private static java.util.Collection<java.util.List<?>> makeAltNames(
            sun.security.x509.GeneralNames names) {
        throw new RuntimeException("Stub!");
    }

    private static java.util.Collection<java.util.List<?>> cloneAltNames(
            java.util.Collection<java.util.List<?>> altNames) {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.util.Collection<java.util.List<?>> getSubjectAlternativeNames()
            throws java.security.cert.CertificateParsingException {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Collection<java.util.List<?>> getSubjectAlternativeNames(
            java.security.cert.X509Certificate cert)
            throws java.security.cert.CertificateParsingException {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.util.Collection<java.util.List<?>> getIssuerAlternativeNames()
            throws java.security.cert.CertificateParsingException {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Collection<java.util.List<?>> getIssuerAlternativeNames(
            java.security.cert.X509Certificate cert)
            throws java.security.cert.CertificateParsingException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.AuthorityInfoAccessExtension getAuthorityInfoAccessExtension() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    private void parse(sun.security.util.DerValue val)
            throws java.security.cert.CertificateException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void parse(sun.security.util.DerValue val, byte[] originalEncodedForm)
            throws java.security.cert.CertificateException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static javax.security.auth.x500.X500Principal getX500Principal(
            java.security.cert.X509Certificate cert, boolean getIssuer) throws java.lang.Exception {
        throw new RuntimeException("Stub!");
    }

    public static javax.security.auth.x500.X500Principal getSubjectX500Principal(
            java.security.cert.X509Certificate cert) {
        throw new RuntimeException("Stub!");
    }

    public static javax.security.auth.x500.X500Principal getIssuerX500Principal(
            java.security.cert.X509Certificate cert) {
        throw new RuntimeException("Stub!");
    }

    public static byte[] getEncodedInternal(java.security.cert.Certificate cert)
            throws java.security.cert.CertificateEncodingException {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.x509.X509CertImpl toImpl(java.security.cert.X509Certificate cert)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    public static boolean isSelfIssued(java.security.cert.X509Certificate cert) {
        throw new RuntimeException("Stub!");
    }

    public static boolean isSelfSigned(
            java.security.cert.X509Certificate cert, java.lang.String sigProvider) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String getFingerprint(
            java.lang.String algorithm, java.security.cert.X509Certificate cert) {
        throw new RuntimeException("Stub!");
    }

    private static void byte2hex(byte b, java.lang.StringBuffer buf) {
        throw new RuntimeException("Stub!");
    }

    public static final java.lang.String ALG_ID = "algorithm";

    private static final java.lang.String AUTH_INFO_ACCESS_OID = "1.3.6.1.5.5.7.1.1";

    private static final java.lang.String BASIC_CONSTRAINT_OID = "2.5.29.19";

    private static final java.lang.String DOT = ".";

    private static final java.lang.String EXTENDED_KEY_USAGE_OID = "2.5.29.37";

    public static final java.lang.String INFO = "info";

    private static final java.lang.String ISSUER_ALT_NAME_OID = "2.5.29.18";

    public static final java.lang.String ISSUER_DN = "x509.info.issuer.dname";

    private static final java.lang.String KEY_USAGE_OID = "2.5.29.15";

    public static final java.lang.String NAME = "x509";

    private static final int NUM_STANDARD_KEY_USAGE = 9; // 0x9

    public static final java.lang.String PUBLIC_KEY = "x509.info.key.value";

    public static final java.lang.String SERIAL_ID = "x509.info.serialNumber.number";

    public static final java.lang.String SIG = "x509.signature";

    public static final java.lang.String SIGNATURE = "signature";

    public static final java.lang.String SIGNED_CERT = "signed_cert";

    public static final java.lang.String SIG_ALG = "x509.algorithm";

    private static final java.lang.String SUBJECT_ALT_NAME_OID = "2.5.29.17";

    public static final java.lang.String SUBJECT_DN = "x509.info.subject.dname";

    public static final java.lang.String VERSION = "x509.info.version.number";

    @android.compat.annotation.UnsupportedAppUsage protected sun.security.x509.AlgorithmId algId;

    private java.util.Set<sun.security.x509.AccessDescription> authInfoAccess;

    private java.util.List<java.lang.String> extKeyUsage;

    private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.String> fingerprints;

    protected sun.security.x509.X509CertInfo info;

    private java.util.Collection<java.util.List<?>> issuerAlternativeNames;

    @android.compat.annotation.UnsupportedAppUsage private boolean readOnly = false;

    private static final long serialVersionUID = -3457612960190864406L; // 0xd0041754f90963eaL

    @android.compat.annotation.UnsupportedAppUsage protected byte[] signature;

    @android.compat.annotation.UnsupportedAppUsage private byte[] signedCert;

    private java.util.Collection<java.util.List<?>> subjectAlternativeNames;

    private boolean verificationResult;

    private java.lang.String verifiedProvider;

    private java.security.PublicKey verifiedPublicKey;
}
