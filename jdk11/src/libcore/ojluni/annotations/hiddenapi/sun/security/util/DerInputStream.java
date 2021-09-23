/*
 * Copyright (c) 1996, 2016, Oracle and/or its affiliates. All rights reserved.
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
public class DerInputStream {

    @android.compat.annotation.UnsupportedAppUsage
    public DerInputStream(byte[] data) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public DerInputStream(byte[] data, int offset, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public DerInputStream(byte[] data, int offset, int len, boolean allowIndefiniteLength)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    DerInputStream(sun.security.util.DerInputBuffer buf) {
        throw new RuntimeException("Stub!");
    }

    private void init(byte[] data, int offset, int len, boolean allowIndefiniteLength)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.DerInputStream subStream(int len, boolean do_skip)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public byte[] toByteArray() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public int getInteger() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.math.BigInteger getBigInteger() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.math.BigInteger getPositiveBigInteger() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int getEnumerated() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public byte[] getBitString() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.util.BitArray getUnalignedBitString() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public byte[] getOctetString() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void getBytes(byte[] val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void getNull() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.ObjectIdentifier getOID() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.util.DerValue[] getSequence(
            int startLen, boolean originalEncodedFormRetained) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.DerValue[] getSequence(int startLen) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.DerValue[] getSet(int startLen) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.DerValue[] getSet(int startLen, boolean implicit)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.util.DerValue[] getSet(
            int startLen, boolean implicit, boolean originalEncodedFormRetained)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected sun.security.util.DerValue[] readVector(int startLen) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected sun.security.util.DerValue[] readVector(
            int startLen, boolean originalEncodedFormRetained) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.DerValue getDerValue() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public java.lang.String getUTF8String() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getPrintableString() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getT61String() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getIA5String() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getBMPString() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getGeneralString() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String readString(
            byte stringTag, java.lang.String stringName, java.lang.String enc)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.util.Date getUTCTime() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.Date getGeneralizedTime() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    int getByte() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public int peekByte() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    int getLength() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    static int getLength(java.io.InputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    static int getLength(int lenByte, java.io.InputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void mark(int value) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void reset() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public int available() {
        throw new RuntimeException("Stub!");
    }

    sun.security.util.DerInputBuffer buffer;

    @android.compat.annotation.UnsupportedAppUsage public byte tag;
}
