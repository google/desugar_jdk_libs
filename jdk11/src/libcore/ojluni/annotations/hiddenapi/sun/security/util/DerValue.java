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

import java.io.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class DerValue {

    @android.compat.annotation.UnsupportedAppUsage
    public DerValue(java.lang.String value) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public DerValue(byte stringTag, java.lang.String value) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public DerValue(byte tag, byte[] data) {
        throw new RuntimeException("Stub!");
    }

    DerValue(sun.security.util.DerInputBuffer in, boolean originalEncodedFormRetained)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public DerValue(byte[] buf) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public DerValue(byte[] buf, int offset, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public DerValue(java.io.InputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean isUniversal() {
        throw new RuntimeException("Stub!");
    }

    public boolean isApplication() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public boolean isContextSpecific() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public boolean isContextSpecific(byte cntxtTag) {
        throw new RuntimeException("Stub!");
    }

    boolean isPrivate() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public boolean isConstructed() {
        throw new RuntimeException("Stub!");
    }

    public boolean isConstructed(byte constructedTag) {
        throw new RuntimeException("Stub!");
    }

    private sun.security.util.DerInputStream init(byte stringTag, java.lang.String value)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private sun.security.util.DerInputStream init(boolean fullyBuffered, java.io.InputStream in)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void encode(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public final sun.security.util.DerInputStream getData() {
        throw new RuntimeException("Stub!");
    }

    public final byte getTag() {
        throw new RuntimeException("Stub!");
    }

    public boolean getBoolean() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.ObjectIdentifier getOID() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private byte[] append(byte[] a, byte[] b) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public byte[] getOctetString() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int getInteger() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.math.BigInteger getBigInteger() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
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

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.BitArray getUnalignedBitString() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.lang.String getAsString() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public byte[] getBitString(boolean tagImplicit) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.util.BitArray getUnalignedBitString(boolean tagImplicit)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public byte[] getDataBytes() throws java.io.IOException {
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

    public java.lang.String getUTF8String() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getGeneralString() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.Date getUTCTime() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.Date getGeneralizedTime() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object other) {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(sun.security.util.DerValue other) {
        throw new RuntimeException("Stub!");
    }

    private static boolean doEquals(sun.security.util.DerValue d1, sun.security.util.DerValue d2) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getOriginalEncodedForm() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public byte[] toByteArray() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.DerInputStream toDerInputStream() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int length() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static boolean isPrintableStringChar(char ch) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static byte createTag(byte tagClass, boolean form, byte val) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void resetTag(byte tag) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public static final byte TAG_APPLICATION = 64; // 0x40

    public static final byte TAG_CONTEXT = -128; // 0xffffff80

    public static final byte TAG_PRIVATE = -64; // 0xffffffc0

    public static final byte TAG_UNIVERSAL = 0; // 0x0

    @android.compat.annotation.UnsupportedAppUsage protected sun.security.util.DerInputBuffer buffer;

    @android.compat.annotation.UnsupportedAppUsage
    public final sun.security.util.DerInputStream data;

    {
        data = null;
    }

    private int length;

    private byte[] originalEncodedForm;

    @android.compat.annotation.UnsupportedAppUsage public byte tag;

    public static final byte tag_BMPString = 30; // 0x1e

    public static final byte tag_BitString = 3; // 0x3

    public static final byte tag_Boolean = 1; // 0x1

    public static final byte tag_Enumerated = 10; // 0xa

    public static final byte tag_GeneralString = 27; // 0x1b

    public static final byte tag_GeneralizedTime = 24; // 0x18

    public static final byte tag_IA5String = 22; // 0x16

    public static final byte tag_Integer = 2; // 0x2

    public static final byte tag_Null = 5; // 0x5

    public static final byte tag_ObjectId = 6; // 0x6

    public static final byte tag_OctetString = 4; // 0x4

    public static final byte tag_PrintableString = 19; // 0x13

    public static final byte tag_Sequence = 48; // 0x30

    public static final byte tag_SequenceOf = 48; // 0x30

    public static final byte tag_Set = 49; // 0x31

    public static final byte tag_SetOf = 49; // 0x31

    public static final byte tag_T61String = 20; // 0x14

    public static final byte tag_UTF8String = 12; // 0xc

    public static final byte tag_UniversalString = 28; // 0x1c

    public static final byte tag_UtcTime = 23; // 0x17
}
