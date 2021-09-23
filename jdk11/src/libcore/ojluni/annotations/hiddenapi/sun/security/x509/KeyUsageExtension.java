/*
 * Copyright (c) 1997, 2015, Oracle and/or its affiliates. All rights reserved.
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

import sun.security.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class KeyUsageExtension extends sun.security.x509.Extension
        implements sun.security.x509.CertAttrSet<java.lang.String> {

    public KeyUsageExtension(byte[] bitString) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public KeyUsageExtension(boolean[] bitString) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public KeyUsageExtension(sun.security.util.BitArray bitString) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public KeyUsageExtension(java.lang.Boolean critical, java.lang.Object value)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public KeyUsageExtension() {
        throw new RuntimeException("Stub!");
    }

    private void encodeThis() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private boolean isSet(int position) {
        throw new RuntimeException("Stub!");
    }

    private void set(int position, boolean val) {
        throw new RuntimeException("Stub!");
    }

    public void set(java.lang.String name, java.lang.Object obj) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public java.lang.Boolean get(java.lang.String name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void delete(java.lang.String name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public void encode(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<java.lang.String> getElements() {
        throw new RuntimeException("Stub!");
    }

    public boolean[] getBits() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public static final java.lang.String CRL_SIGN = "crl_sign";

    public static final java.lang.String DATA_ENCIPHERMENT = "data_encipherment";

    public static final java.lang.String DECIPHER_ONLY = "decipher_only";

    public static final java.lang.String DIGITAL_SIGNATURE = "digital_signature";

    public static final java.lang.String ENCIPHER_ONLY = "encipher_only";

    public static final java.lang.String IDENT = "x509.info.extensions.KeyUsage";

    public static final java.lang.String KEY_AGREEMENT = "key_agreement";

    public static final java.lang.String KEY_CERTSIGN = "key_certsign";

    public static final java.lang.String KEY_ENCIPHERMENT = "key_encipherment";

    public static final java.lang.String NAME = "KeyUsage";

    public static final java.lang.String NON_REPUDIATION = "non_repudiation";

    private boolean[] bitString;
}
