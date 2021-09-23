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
public class X509CertificatePair {

    public X509CertificatePair() {
        throw new RuntimeException("Stub!");
    }

    public X509CertificatePair(
            java.security.cert.X509Certificate forward, java.security.cert.X509Certificate reverse)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private X509CertificatePair(byte[] encoded) throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static synchronized void clearCache() {
        throw new RuntimeException("Stub!");
    }

    public static synchronized sun.security.provider.certpath.X509CertificatePair
            generateCertificatePair(byte[] encoded) throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    public void setForward(java.security.cert.X509Certificate cert)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    public void setReverse(java.security.cert.X509Certificate cert)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    public java.security.cert.X509Certificate getForward() {
        throw new RuntimeException("Stub!");
    }

    public java.security.cert.X509Certificate getReverse() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getEncoded() throws java.security.cert.CertificateEncodingException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    private void parse(sun.security.util.DerValue val)
            throws java.security.cert.CertificateException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void emit(sun.security.util.DerOutputStream out)
            throws java.security.cert.CertificateEncodingException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void checkPair() throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private static final byte TAG_FORWARD = 0; // 0x0

    private static final byte TAG_REVERSE = 1; // 0x1

    private static final sun.security.util.Cache<
                    java.lang.Object, sun.security.provider.certpath.X509CertificatePair>
            cache;

    static {
        cache = null;
    }

    private byte[] encoded;

    private java.security.cert.X509Certificate forward;

    private java.security.cert.X509Certificate reverse;
}
