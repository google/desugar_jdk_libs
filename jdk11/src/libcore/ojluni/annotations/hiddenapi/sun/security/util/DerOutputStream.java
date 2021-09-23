/*
 * Copyright (c) 1996, 2010, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.util;


@SuppressWarnings({"unchecked", "deprecation", "all"})
public class DerOutputStream extends java.io.ByteArrayOutputStream
        implements sun.security.util.DerEncoder {

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public DerOutputStream(int size) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public DerOutputStream() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void write(byte tag, byte[] buf) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void write(byte tag, sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeImplicit(byte tag, sun.security.util.DerOutputStream value)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putDerValue(sun.security.util.DerValue val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putBoolean(boolean val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void putEnumerated(int i) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putInteger(java.math.BigInteger i) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void putInteger(java.lang.Integer i) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putInteger(int i) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void putIntegerContents(int i) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putBitString(byte[] bits) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void putUnalignedBitString(sun.security.util.BitArray ba) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void putTruncatedUnalignedBitString(sun.security.util.BitArray ba)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putOctetString(byte[] octets) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putNull() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putOID(sun.security.util.ObjectIdentifier oid) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putSequence(sun.security.util.DerValue[] seq) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void putSet(sun.security.util.DerValue[] set) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putOrderedSetOf(byte tag, sun.security.util.DerEncoder[] set)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void putOrderedSet(byte tag, sun.security.util.DerEncoder[] set)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void putOrderedSet(
            byte tag, sun.security.util.DerEncoder[] set, java.util.Comparator<byte[]> order)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putUTF8String(java.lang.String s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putPrintableString(java.lang.String s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void putT61String(java.lang.String s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putIA5String(java.lang.String s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void putBMPString(java.lang.String s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void putGeneralString(java.lang.String s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeString(java.lang.String s, byte stringTag, java.lang.String enc)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void putUTCTime(java.util.Date d) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void putGeneralizedTime(java.util.Date d) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void putTime(java.util.Date d, byte tag) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void putLength(int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void putTag(byte tagClass, boolean form, byte val) {
        throw new RuntimeException("Stub!");
    }

    public void derEncode(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static sun.security.util.ByteArrayLexOrder lexOrder;

    private static sun.security.util.ByteArrayTagOrder tagOrder;
}
