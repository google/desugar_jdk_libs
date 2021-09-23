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

package sun.misc;

import java.util.regex.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class FloatingDecimal {

    public FloatingDecimal() {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toJavaFormatString(double d) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toJavaFormatString(float f) {
        throw new RuntimeException("Stub!");
    }

    public static void appendTo(double d, java.lang.Appendable buf) {
        throw new RuntimeException("Stub!");
    }

    public static void appendTo(float f, java.lang.Appendable buf) {
        throw new RuntimeException("Stub!");
    }

    public static double parseDouble(java.lang.String s) throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static float parseFloat(java.lang.String s) throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    private static sun.misc.FloatingDecimal.BinaryToASCIIBuffer getBinaryToASCIIBuffer() {
        throw new RuntimeException("Stub!");
    }

    public static sun.misc.FloatingDecimal.BinaryToASCIIConverter getBinaryToASCIIConverter(
            double d) {
        throw new RuntimeException("Stub!");
    }

    static sun.misc.FloatingDecimal.BinaryToASCIIConverter getBinaryToASCIIConverter(
            double d, boolean isCompatibleFormat) {
        throw new RuntimeException("Stub!");
    }

    private static sun.misc.FloatingDecimal.BinaryToASCIIConverter getBinaryToASCIIConverter(
            float f) {
        throw new RuntimeException("Stub!");
    }

    static sun.misc.FloatingDecimal.ASCIIToBinaryConverter readJavaFormatString(java.lang.String in)
            throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    static sun.misc.FloatingDecimal.ASCIIToBinaryConverter parseHexString(java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    static java.lang.String stripLeadingZeros(java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    static int getHexDigit(java.lang.String s, int position) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage private static boolean $assertionsDisabled;

    static final sun.misc.FloatingDecimal.ASCIIToBinaryConverter A2BC_NEGATIVE_INFINITY;

    static {
        A2BC_NEGATIVE_INFINITY = null;
    }

    static final sun.misc.FloatingDecimal.ASCIIToBinaryConverter A2BC_NEGATIVE_ZERO;

    static {
        A2BC_NEGATIVE_ZERO = null;
    }

    static final sun.misc.FloatingDecimal.ASCIIToBinaryConverter A2BC_NOT_A_NUMBER;

    static {
        A2BC_NOT_A_NUMBER = null;
    }

    static final sun.misc.FloatingDecimal.ASCIIToBinaryConverter A2BC_POSITIVE_INFINITY;

    static {
        A2BC_POSITIVE_INFINITY = null;
    }

    static final sun.misc.FloatingDecimal.ASCIIToBinaryConverter A2BC_POSITIVE_ZERO;

    static {
        A2BC_POSITIVE_ZERO = null;
    }

    private static final sun.misc.FloatingDecimal.BinaryToASCIIConverter B2AC_NEGATIVE_INFINITY;

    static {
        B2AC_NEGATIVE_INFINITY = null;
    }

    private static final sun.misc.FloatingDecimal.BinaryToASCIIConverter B2AC_NEGATIVE_ZERO;

    static {
        B2AC_NEGATIVE_ZERO = null;
    }

    private static final sun.misc.FloatingDecimal.BinaryToASCIIConverter B2AC_NOT_A_NUMBER;

    static {
        B2AC_NOT_A_NUMBER = null;
    }

    private static final sun.misc.FloatingDecimal.BinaryToASCIIConverter B2AC_POSITIVE_INFINITY;

    static {
        B2AC_POSITIVE_INFINITY = null;
    }

    private static final sun.misc.FloatingDecimal.BinaryToASCIIConverter B2AC_POSITIVE_ZERO;

    static {
        B2AC_POSITIVE_ZERO = null;
    }

    static final int BIG_DECIMAL_EXPONENT = 324; // 0x144

    static final long EXP_ONE = 4607182418800017408L; // 0x3ff0000000000000L

    static final int EXP_SHIFT = 52; // 0x34

    static final long FRACT_HOB = 4503599627370496L; // 0x10000000000000L

    private static final int INFINITY_LENGTH;

    static {
        INFINITY_LENGTH = 0;
    }

    private static final java.lang.String INFINITY_REP = "Infinity";

    static final int INT_DECIMAL_DIGITS = 9; // 0x9

    static final int MAX_DECIMAL_DIGITS = 15; // 0xf

    static final int MAX_DECIMAL_EXPONENT = 308; // 0x134

    static final int MAX_NDIGITS = 1100; // 0x44c

    static final int MAX_SMALL_BIN_EXP = 62; // 0x3e

    static final int MIN_DECIMAL_EXPONENT = -324; // 0xfffffebc

    static final int MIN_SMALL_BIN_EXP = -21; // 0xffffffeb

    private static final int NAN_LENGTH;

    static {
        NAN_LENGTH = 0;
    }

    private static final java.lang.String NAN_REP = "NaN";

    static final int SINGLE_EXP_SHIFT = 23; // 0x17

    static final int SINGLE_FRACT_HOB = 8388608; // 0x800000

    static final int SINGLE_MAX_DECIMAL_DIGITS = 7; // 0x7

    static final int SINGLE_MAX_DECIMAL_EXPONENT = 38; // 0x26

    static final int SINGLE_MAX_NDIGITS = 200; // 0xc8

    static final int SINGLE_MIN_DECIMAL_EXPONENT = -45; // 0xffffffd3

    private static final java.lang.ThreadLocal<sun.misc.FloatingDecimal.BinaryToASCIIBuffer>
            threadLocalBinaryToASCIIBuffer;

    static {
        threadLocalBinaryToASCIIBuffer = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class ASCIIToBinaryBuffer implements sun.misc.FloatingDecimal.ASCIIToBinaryConverter {

        ASCIIToBinaryBuffer(boolean negSign, int decExponent, char[] digits, int n) {
            throw new RuntimeException("Stub!");
        }

        public double doubleValue() {
            throw new RuntimeException("Stub!");
        }

        public float floatValue() {
            throw new RuntimeException("Stub!");
        }

        private static final double[] BIG_10_POW;

        static {
            BIG_10_POW = new double[0];
        }

        private static final int MAX_SMALL_TEN;

        static {
            MAX_SMALL_TEN = 0;
        }

        private static final int SINGLE_MAX_SMALL_TEN;

        static {
            SINGLE_MAX_SMALL_TEN = 0;
        }

        private static final float[] SINGLE_SMALL_10_POW;

        static {
            SINGLE_SMALL_10_POW = new float[0];
        }

        private static final double[] SMALL_10_POW;

        static {
            SMALL_10_POW = new double[0];
        }

        private static final double[] TINY_10_POW;

        static {
            TINY_10_POW = new double[0];
        }

        int decExponent;

        char[] digits;

        boolean isNegative;

        int nDigits;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static interface ASCIIToBinaryConverter {

        public double doubleValue();

        public float floatValue();
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class BinaryToASCIIBuffer implements sun.misc.FloatingDecimal.BinaryToASCIIConverter {

        BinaryToASCIIBuffer() {
            throw new RuntimeException("Stub!");
        }

        BinaryToASCIIBuffer(boolean isNegative, char[] digits) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toJavaFormatString() {
            throw new RuntimeException("Stub!");
        }

        public void appendTo(java.lang.Appendable buf) {
            throw new RuntimeException("Stub!");
        }

        public int getDecimalExponent() {
            throw new RuntimeException("Stub!");
        }

        public int getDigits(char[] digits) {
            throw new RuntimeException("Stub!");
        }

        public boolean isNegative() {
            throw new RuntimeException("Stub!");
        }

        public boolean isExceptional() {
            throw new RuntimeException("Stub!");
        }

        public boolean digitsRoundedUp() {
            throw new RuntimeException("Stub!");
        }

        public boolean decimalDigitsExact() {
            throw new RuntimeException("Stub!");
        }

        private void setSign(boolean isNegative) {
            throw new RuntimeException("Stub!");
        }

        private void developLongDigits(int decExponent, long lvalue, int insignificantDigits) {
            throw new RuntimeException("Stub!");
        }

        private void dtoa(
                int binExp, long fractBits, int nSignificantBits, boolean isCompatibleFormat) {
            throw new RuntimeException("Stub!");
        }

        private void roundup() {
            throw new RuntimeException("Stub!");
        }

        static int estimateDecExp(long fractBits, int binExp) {
            throw new RuntimeException("Stub!");
        }

        private static int insignificantDigits(int insignificant) {
            throw new RuntimeException("Stub!");
        }

        private static int insignificantDigitsForPow2(int p2) {
            throw new RuntimeException("Stub!");
        }

        private int getChars(char[] result) {
            throw new RuntimeException("Stub!");
        }

        private static final int[] N_5_BITS;

        static {
            N_5_BITS = new int[0];
        }

        private final char[] buffer;

        {
            buffer = new char[0];
        }

        private int decExponent;

        private boolean decimalDigitsRoundedUp = false;

        private final char[] digits;

        {
            digits = new char[0];
        }

        private boolean exactDecimalConversion = false;

        private int firstDigitIndex;

        private static int[] insignificantDigitsNumber;

        private boolean isNegative;

        private int nDigits;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static interface BinaryToASCIIConverter {

        public java.lang.String toJavaFormatString();

        public void appendTo(java.lang.Appendable buf);

        public int getDecimalExponent();

        public int getDigits(char[] digits);

        public boolean isNegative();

        public boolean isExceptional();

        public boolean digitsRoundedUp();

        public boolean decimalDigitsExact();
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class ExceptionalBinaryToASCIIBuffer
            implements sun.misc.FloatingDecimal.BinaryToASCIIConverter {

        public ExceptionalBinaryToASCIIBuffer(java.lang.String image, boolean isNegative) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toJavaFormatString() {
            throw new RuntimeException("Stub!");
        }

        public void appendTo(java.lang.Appendable buf) {
            throw new RuntimeException("Stub!");
        }

        public int getDecimalExponent() {
            throw new RuntimeException("Stub!");
        }

        public int getDigits(char[] digits) {
            throw new RuntimeException("Stub!");
        }

        public boolean isNegative() {
            throw new RuntimeException("Stub!");
        }

        public boolean isExceptional() {
            throw new RuntimeException("Stub!");
        }

        public boolean digitsRoundedUp() {
            throw new RuntimeException("Stub!");
        }

        public boolean decimalDigitsExact() {
            throw new RuntimeException("Stub!");
        }

        private final java.lang.String image;

        {
            image = null;
        }

        private boolean isNegative;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class HexFloatPattern {

        private HexFloatPattern() {
            throw new RuntimeException("Stub!");
        }

        private static final java.util.regex.Pattern VALUE;

        static {
            VALUE = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class PreparedASCIIToBinaryBuffer
            implements sun.misc.FloatingDecimal.ASCIIToBinaryConverter {

        public PreparedASCIIToBinaryBuffer(double doubleVal, float floatVal) {
            throw new RuntimeException("Stub!");
        }

        public double doubleValue() {
            throw new RuntimeException("Stub!");
        }

        public float floatValue() {
            throw new RuntimeException("Stub!");
        }

        private final double doubleVal;

        {
            doubleVal = 0;
        }

        private final float floatVal;

        {
            floatVal = 0;
        }
    }
}
