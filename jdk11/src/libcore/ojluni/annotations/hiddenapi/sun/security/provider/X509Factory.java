/*
 * Copyright (c) 1998, 2014, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.provider;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class X509Factory {

    public X509Factory() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static synchronized sun.security.x509.X509CertImpl intern(
            java.security.cert.X509Certificate c) throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static synchronized sun.security.x509.X509CRLImpl intern(java.security.cert.X509CRL c)
            throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    private static synchronized <K, V> V getFromCache(
            sun.security.util.Cache<K, V> cache, byte[] encoding) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    private static synchronized <V> void addToCache(
            sun.security.util.Cache<java.lang.Object, V> cache, byte[] encoding, V value) {
        throw new RuntimeException("Stub!");
    }

    private static final int ENC_MAX_LENGTH = 4194304; // 0x400000

    @android.compat.annotation.UnsupportedAppUsage
    private static final sun.security.util.Cache<java.lang.Object, sun.security.x509.X509CertImpl>
            certCache;

    static {
        certCache = null;
    }

    @android.compat.annotation.UnsupportedAppUsage
    private static final sun.security.util.Cache<java.lang.Object, sun.security.x509.X509CRLImpl>
            crlCache;

    static {
        crlCache = null;
    }
}
