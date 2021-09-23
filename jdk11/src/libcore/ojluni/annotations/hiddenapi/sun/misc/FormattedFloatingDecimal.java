/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class FormattedFloatingDecimal {

    private FormattedFloatingDecimal(
            int precision,
            sun.misc.FormattedFloatingDecimal.Form form,
            sun.misc.FloatingDecimal.BinaryToASCIIConverter fdConverter) {
        throw new RuntimeException("Stub!");
    }

    public static sun.misc.FormattedFloatingDecimal valueOf(
            double d, int precision, sun.misc.FormattedFloatingDecimal.Form form) {
        throw new RuntimeException("Stub!");
    }

    private static char[] getBuffer() {
        throw new RuntimeException("Stub!");
    }

    public int getExponentRounded() {
        throw new RuntimeException("Stub!");
    }

    public char[] getMantissa() {
        throw new RuntimeException("Stub!");
    }

    public char[] getExponent() {
        throw new RuntimeException("Stub!");
    }

    private static int applyPrecision(int decExp, char[] digits, int nDigits, int prec) {
        throw new RuntimeException("Stub!");
    }

    private void fillCompatible(
            int precision, char[] digits, int nDigits, int exp, boolean isNegative) {
        throw new RuntimeException("Stub!");
    }

    private static char[] create(boolean isNegative, int size) {
        throw new RuntimeException("Stub!");
    }

    private void fillDecimal(
            int precision, char[] digits, int nDigits, int exp, boolean isNegative) {
        throw new RuntimeException("Stub!");
    }

    private void fillScientific(
            int precision, char[] digits, int nDigits, int exp, boolean isNegative) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage private static boolean $assertionsDisabled;

    private int decExponentRounded;

    private char[] exponent;

    private char[] mantissa;

    private static final java.lang.ThreadLocal<java.lang.Object> threadLocalCharBuffer;

    static {
        threadLocalCharBuffer = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static enum Form {
        @android.compat.annotation.UnsupportedAppUsage
        SCIENTIFIC,
        @android.compat.annotation.UnsupportedAppUsage
        COMPATIBLE,
        @android.compat.annotation.UnsupportedAppUsage
        DECIMAL_FLOAT,
        GENERAL;

        private Form() {
            throw new RuntimeException("Stub!");
        }
    }
}
