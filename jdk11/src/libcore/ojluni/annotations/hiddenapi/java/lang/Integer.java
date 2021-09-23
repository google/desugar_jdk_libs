/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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
public final class Integer extends java.lang.Number
        implements java.lang.Comparable<java.lang.Integer> {

    public Integer(int value) {
        throw new RuntimeException("Stub!");
    }

    public Integer(java.lang.String s) throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(int i, int radix) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toUnsignedString(int i, int radix) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toHexString(int i) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toOctalString(int i) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toBinaryString(int i) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String toUnsignedString0(int val, int shift) {
        throw new RuntimeException("Stub!");
    }

    static int formatUnsignedInt(int val, int shift, char[] buf, int offset, int len) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(int i) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toUnsignedString(int i) {
        throw new RuntimeException("Stub!");
    }

    static void getChars(int i, int index, char[] buf) {
        throw new RuntimeException("Stub!");
    }

    static int stringSize(int x) {
        throw new RuntimeException("Stub!");
    }

    public static int parseInt(java.lang.String s, int radix)
            throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static int parseInt(java.lang.String s) throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static int parseUnsignedInt(java.lang.String s, int radix)
            throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static int parseUnsignedInt(java.lang.String s) throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Integer valueOf(java.lang.String s, int radix)
            throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Integer valueOf(java.lang.String s)
            throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Integer valueOf(int i) {
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

    public static int hashCode(int value) {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Integer getInteger(java.lang.String nm) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Integer getInteger(java.lang.String nm, int val) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Integer getInteger(java.lang.String nm, java.lang.Integer val) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Integer decode(java.lang.String nm)
            throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public int compareTo(java.lang.Integer anotherInteger) {
        throw new RuntimeException("Stub!");
    }

    public static int compare(int x, int y) {
        throw new RuntimeException("Stub!");
    }

    public static int compareUnsigned(int x, int y) {
        throw new RuntimeException("Stub!");
    }

    public static long toUnsignedLong(int x) {
        throw new RuntimeException("Stub!");
    }

    public static int divideUnsigned(int dividend, int divisor) {
        throw new RuntimeException("Stub!");
    }

    public static int remainderUnsigned(int dividend, int divisor) {
        throw new RuntimeException("Stub!");
    }

    public static int highestOneBit(int i) {
        throw new RuntimeException("Stub!");
    }

    public static int lowestOneBit(int i) {
        throw new RuntimeException("Stub!");
    }

    public static int numberOfLeadingZeros(int i) {
        throw new RuntimeException("Stub!");
    }

    public static int numberOfTrailingZeros(int i) {
        throw new RuntimeException("Stub!");
    }

    public static int bitCount(int i) {
        throw new RuntimeException("Stub!");
    }

    public static int rotateLeft(int i, int distance) {
        throw new RuntimeException("Stub!");
    }

    public static int rotateRight(int i, int distance) {
        throw new RuntimeException("Stub!");
    }

    public static int reverse(int i) {
        throw new RuntimeException("Stub!");
    }

    public static int signum(int i) {
        throw new RuntimeException("Stub!");
    }

    public static int reverseBytes(int i) {
        throw new RuntimeException("Stub!");
    }

    public static int sum(int a, int b) {
        throw new RuntimeException("Stub!");
    }

    public static int max(int a, int b) {
        throw new RuntimeException("Stub!");
    }

    public static int min(int a, int b) {
        throw new RuntimeException("Stub!");
    }

    public static final int BYTES = 4; // 0x4

    static final char[] DigitOnes;

    static {
        DigitOnes = new char[0];
    }

    static final char[] DigitTens;

    static {
        DigitTens = new char[0];
    }

    public static final int MAX_VALUE = 2147483647; // 0x7fffffff

    public static final int MIN_VALUE = -2147483648; // 0x80000000

    public static final int SIZE = 32; // 0x20

    private static final java.lang.String[] SMALL_NEG_VALUES;

    static {
        SMALL_NEG_VALUES = new java.lang.String[0];
    }

    private static final java.lang.String[] SMALL_NONNEG_VALUES;

    static {
        SMALL_NONNEG_VALUES = new java.lang.String[0];
    }

    public static final java.lang.Class<java.lang.Integer> TYPE;

    static {
        TYPE = null;
    }

    static final char[] digits;

    static {
        digits = new char[0];
    }

    private static final long serialVersionUID = 1360826667806852920L; // 0x12e2a0a4f7818738L

    static final int[] sizeTable;

    static {
        sizeTable = new int[0];
    }

    /**
     * @deprecated Use {@link #intValue()}.
     */
    @UnsupportedAppUsage(maxTargetSdk = VersionCodes.P)
    private final int value;

    {
        value = 0;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class IntegerCache {

        private IntegerCache() {
            throw new RuntimeException("Stub!");
        }

        static final java.lang.Integer[] cache;

        static {
            cache = new java.lang.Integer[0];
        }

        static final int high;

        static {
            high = 0;
        }

        static final int low = -128; // 0xffffff80
    }
}
