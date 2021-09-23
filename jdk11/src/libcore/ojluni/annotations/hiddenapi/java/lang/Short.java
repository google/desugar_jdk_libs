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

package java.lang;

import android.compat.annotation.UnsupportedAppUsage;
import dalvik.annotation.compat.VersionCodes;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class Short extends java.lang.Number implements java.lang.Comparable<java.lang.Short> {

    public Short(short value) {
        throw new RuntimeException("Stub!");
    }

    public Short(java.lang.String s) throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(short s) {
        throw new RuntimeException("Stub!");
    }

    public static short parseShort(java.lang.String s, int radix)
            throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static short parseShort(java.lang.String s) throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Short valueOf(java.lang.String s, int radix)
            throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Short valueOf(java.lang.String s)
            throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Short valueOf(short s) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Short decode(java.lang.String nm)
            throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public byte byteValue() {
        throw new RuntimeException("Stub!");
    }

    public short shortValue() {
        throw new RuntimeException("Stub!");
    }

    public int intValue() {
        throw new RuntimeException("Stub!");
    }

    public long longValue() {
        throw new RuntimeException("Stub!");
    }

    public float floatValue() {
        throw new RuntimeException("Stub!");
    }

    public double doubleValue() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public static int hashCode(short value) {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int compareTo(java.lang.Short anotherShort) {
        throw new RuntimeException("Stub!");
    }

    public static int compare(short x, short y) {
        throw new RuntimeException("Stub!");
    }

    public static short reverseBytes(short i) {
        throw new RuntimeException("Stub!");
    }

    public static int toUnsignedInt(short x) {
        throw new RuntimeException("Stub!");
    }

    public static long toUnsignedLong(short x) {
        throw new RuntimeException("Stub!");
    }

    public static final int BYTES = 2; // 0x2

    public static final short MAX_VALUE = 32767; // 0x7fff

    public static final short MIN_VALUE = -32768; // 0xffff8000

    public static final int SIZE = 16; // 0x10

    public static final java.lang.Class<java.lang.Short> TYPE;

    static {
        TYPE = null;
    }

    private static final long serialVersionUID = 7515723908773894738L; // 0x684d37133460da52L

    /**
     * @deprecated Use {@link #shortValue()}.
     */
    @UnsupportedAppUsage(maxTargetSdk = VersionCodes.P)
    private final short value;

    {
        value = 0;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class ShortCache {

        private ShortCache() {
            throw new RuntimeException("Stub!");
        }

        static final java.lang.Short[] cache;

        static {
            cache = new java.lang.Short[0];
        }
    }
}
