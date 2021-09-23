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
public class X509CertInfo implements sun.security.x509.CertAttrSet<java.lang.String> {

    @android.compat.annotation.UnsupportedAppUsage
    public X509CertInfo() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X509CertInfo(byte[] cert) throws java.security.cert.CertificateParsingException {
        throw new RuntimeException("Stub!");
    }

    public X509CertInfo(sun.security.util.DerValue derVal)
            throws java.security.cert.CertificateParsingException {
        throw new RuntimeException("Stub!");
    }

    public void encode(java.io.OutputStream out)
            throws java.security.cert.CertificateException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<java.lang.String> getElements() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getEncodedInfo() throws java.security.cert.CertificateEncodingException {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object other) {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(sun.security.x509.X509CertInfo other) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void set(java.lang.String name, java.lang.Object val)
            throws java.security.cert.CertificateException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void delete(java.lang.String name)
            throws java.security.cert.CertificateException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.lang.Object get(java.lang.String name)
            throws java.security.cert.CertificateException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Object getX500Name(java.lang.String name, boolean getIssuer)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void parse(sun.security.util.DerValue val)
            throws java.security.cert.CertificateParsingException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void verifyCert(
            sun.security.x509.X500Name subject, sun.security.x509.CertificateExtensions extensions)
            throws java.security.cert.CertificateParsingException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void emit(sun.security.util.DerOutputStream out)
            throws java.security.cert.CertificateException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private int attributeMap(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    private void setVersion(java.lang.Object val) throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private void setSerialNumber(java.lang.Object val)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private void setAlgorithmId(java.lang.Object val)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private void setIssuer(java.lang.Object val) throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private void setValidity(java.lang.Object val) throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private void setSubject(java.lang.Object val) throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private void setKey(java.lang.Object val) throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private void setIssuerUniqueId(java.lang.Object val)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private void setSubjectUniqueId(java.lang.Object val)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private void setExtensions(java.lang.Object val)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    public static final java.lang.String ALGORITHM_ID = "algorithmID";

    private static final int ATTR_ALGORITHM = 3; // 0x3

    private static final int ATTR_EXTENSIONS = 10; // 0xa

    private static final int ATTR_ISSUER = 4; // 0x4

    private static final int ATTR_ISSUER_ID = 8; // 0x8

    private static final int ATTR_KEY = 7; // 0x7

    private static final int ATTR_SERIAL = 2; // 0x2

    private static final int ATTR_SUBJECT = 6; // 0x6

    private static final int ATTR_SUBJECT_ID = 9; // 0x9

    private static final int ATTR_VALIDITY = 5; // 0x5

    private static final int ATTR_VERSION = 1; // 0x1

    public static final java.lang.String DN_NAME = "dname";

    public static final java.lang.String EXTENSIONS = "extensions";

    public static final java.lang.String IDENT = "x509.info";

    public static final java.lang.String ISSUER = "issuer";

    public static final java.lang.String ISSUER_ID = "issuerID";

    public static final java.lang.String KEY = "key";

    public static final java.lang.String NAME = "info";

    public static final java.lang.String SERIAL_NUMBER = "serialNumber";

    public static final java.lang.String SUBJECT = "subject";

    public static final java.lang.String SUBJECT_ID = "subjectID";

    public static final java.lang.String VALIDITY = "validity";

    public static final java.lang.String VERSION = "version";

    protected sun.security.x509.CertificateAlgorithmId algId;

    protected sun.security.x509.CertificateExtensions extensions;

    protected sun.security.x509.CertificateValidity interval;

    protected sun.security.x509.X500Name issuer;

    protected sun.security.x509.UniqueIdentity issuerUniqueId;

    private static final java.util.Map<java.lang.String, java.lang.Integer> map;

    static {
        map = null;
    }

    protected sun.security.x509.CertificateX509Key pubKey;

    private byte[] rawCertInfo;

    protected sun.security.x509.CertificateSerialNumber serialNum;

    protected sun.security.x509.X500Name subject;

    protected sun.security.x509.UniqueIdentity subjectUniqueId;

    protected sun.security.x509.CertificateVersion version;
}
