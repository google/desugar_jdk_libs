/*
 * Copyright (c) 2000, 2012, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.provider.certpath;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class X509CertPath extends java.security.cert.CertPath {

    @android.compat.annotation.UnsupportedAppUsage
    public X509CertPath(java.util.List<? extends java.security.cert.Certificate> certs)
            throws java.security.cert.CertificateException {
        super(null);
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X509CertPath(java.io.InputStream is) throws java.security.cert.CertificateException {
        super(null);
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X509CertPath(java.io.InputStream is, java.lang.String encoding)
            throws java.security.cert.CertificateException {
        super(null);
        throw new RuntimeException("Stub!");
    }

    private static java.util.List<java.security.cert.X509Certificate> parsePKIPATH(
            java.io.InputStream is) throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private static java.util.List<java.security.cert.X509Certificate> parsePKCS7(
            java.io.InputStream is) throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private static byte[] readAllBytes(java.io.InputStream is) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public byte[] getEncoded() throws java.security.cert.CertificateEncodingException {
        throw new RuntimeException("Stub!");
    }

    private byte[] encodePKIPATH() throws java.security.cert.CertificateEncodingException {
        throw new RuntimeException("Stub!");
    }

    private byte[] encodePKCS7() throws java.security.cert.CertificateEncodingException {
        throw new RuntimeException("Stub!");
    }

    public byte[] getEncoded(java.lang.String encoding)
            throws java.security.cert.CertificateEncodingException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static java.util.Iterator<java.lang.String> getEncodingsStatic() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Iterator<java.lang.String> getEncodings() {
        throw new RuntimeException("Stub!");
    }

    public java.util.List<java.security.cert.X509Certificate> getCertificates() {
        throw new RuntimeException("Stub!");
    }

    private static final java.lang.String COUNT_ENCODING = "count";

    private static final java.lang.String PKCS7_ENCODING = "PKCS7";

    private static final java.lang.String PKIPATH_ENCODING = "PkiPath";

    @android.compat.annotation.UnsupportedAppUsage
    private java.util.List<java.security.cert.X509Certificate> certs;

    private static final java.util.Collection<java.lang.String> encodingList;

    static {
        encodingList = null;
    }

    private static final long serialVersionUID = 4989800333263052980L; // 0x453f54f74c4520b4L
}
