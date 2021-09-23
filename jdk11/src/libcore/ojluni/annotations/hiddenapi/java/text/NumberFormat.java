/*
 * Copyright (C) 2014 The Android Open Source Project
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

/*
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 */

package java.text;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class NumberFormat extends java.text.Format {

    protected NumberFormat() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.StringBuffer format(
            java.lang.Object number,
            java.lang.StringBuffer toAppendTo,
            java.text.FieldPosition pos) {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.Object parseObject(
            java.lang.String source, java.text.ParsePosition pos) {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.String format(double number) {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.String format(long number) {
        throw new RuntimeException("Stub!");
    }

    public abstract java.lang.StringBuffer format(
            double number, java.lang.StringBuffer toAppendTo, java.text.FieldPosition pos);

    public abstract java.lang.StringBuffer format(
            long number, java.lang.StringBuffer toAppendTo, java.text.FieldPosition pos);

    public abstract java.lang.Number parse(
            java.lang.String source, java.text.ParsePosition parsePosition);

    public java.lang.Number parse(java.lang.String source) throws java.text.ParseException {
        throw new RuntimeException("Stub!");
    }

    public boolean isParseIntegerOnly() {
        throw new RuntimeException("Stub!");
    }

    public void setParseIntegerOnly(boolean value) {
        throw new RuntimeException("Stub!");
    }

    public static final java.text.NumberFormat getInstance() {
        throw new RuntimeException("Stub!");
    }

    public static java.text.NumberFormat getInstance(java.util.Locale inLocale) {
        throw new RuntimeException("Stub!");
    }

    public static final java.text.NumberFormat getNumberInstance() {
        throw new RuntimeException("Stub!");
    }

    public static java.text.NumberFormat getNumberInstance(java.util.Locale inLocale) {
        throw new RuntimeException("Stub!");
    }

    public static final java.text.NumberFormat getIntegerInstance() {
        throw new RuntimeException("Stub!");
    }

    public static java.text.NumberFormat getIntegerInstance(java.util.Locale inLocale) {
        throw new RuntimeException("Stub!");
    }

    public static final java.text.NumberFormat getCurrencyInstance() {
        throw new RuntimeException("Stub!");
    }

    public static java.text.NumberFormat getCurrencyInstance(java.util.Locale inLocale) {
        throw new RuntimeException("Stub!");
    }

    public static final java.text.NumberFormat getPercentInstance() {
        throw new RuntimeException("Stub!");
    }

    public static java.text.NumberFormat getPercentInstance(java.util.Locale inLocale) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Locale[] getAvailableLocales() {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object clone() {
        throw new RuntimeException("Stub!");
    }

    public boolean isGroupingUsed() {
        throw new RuntimeException("Stub!");
    }

    public void setGroupingUsed(boolean newValue) {
        throw new RuntimeException("Stub!");
    }

    public int getMaximumIntegerDigits() {
        throw new RuntimeException("Stub!");
    }

    public void setMaximumIntegerDigits(int newValue) {
        throw new RuntimeException("Stub!");
    }

    public int getMinimumIntegerDigits() {
        throw new RuntimeException("Stub!");
    }

    public void setMinimumIntegerDigits(int newValue) {
        throw new RuntimeException("Stub!");
    }

    public int getMaximumFractionDigits() {
        throw new RuntimeException("Stub!");
    }

    public void setMaximumFractionDigits(int newValue) {
        throw new RuntimeException("Stub!");
    }

    public int getMinimumFractionDigits() {
        throw new RuntimeException("Stub!");
    }

    public void setMinimumFractionDigits(int newValue) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Currency getCurrency() {
        throw new RuntimeException("Stub!");
    }

    public void setCurrency(java.util.Currency currency) {
        throw new RuntimeException("Stub!");
    }

    public java.math.RoundingMode getRoundingMode() {
        throw new RuntimeException("Stub!");
    }

    public void setRoundingMode(java.math.RoundingMode roundingMode) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private static java.text.NumberFormat getInstance(java.util.Locale desiredLocale, int choice) {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static final int CURRENCYSTYLE = 1; // 0x1

    public static final int FRACTION_FIELD = 1; // 0x1

    private static final int INTEGERSTYLE = 3; // 0x3

    public static final int INTEGER_FIELD = 0; // 0x0

    private static final int NUMBERSTYLE = 0; // 0x0

    private static final int PERCENTSTYLE = 2; // 0x2

    static final int currentSerialVersion = 1; // 0x1

    private boolean groupingUsed = true;

    private byte maxFractionDigits = 3; // 0x3

    private byte maxIntegerDigits = 40; // 0x28

    private int maximumFractionDigits = 3; // 0x3

    private int maximumIntegerDigits = 40; // 0x28

    private byte minFractionDigits = 0; // 0x0

    private byte minIntegerDigits = 1; // 0x1

    private int minimumFractionDigits = 0; // 0x0

    private int minimumIntegerDigits = 1; // 0x1

    private boolean parseIntegerOnly = false;

    private int serialVersionOnStream = 1; // 0x1

    static final long serialVersionUID = -2308460125733713944L; // 0xdff6b3bf137d07e8L

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static class Field extends java.text.Format.Field {

        protected Field(java.lang.String name) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        protected java.lang.Object readResolve() throws java.io.InvalidObjectException {
            throw new RuntimeException("Stub!");
        }

        public static final java.text.NumberFormat.Field CURRENCY;

        static {
            CURRENCY = null;
        }

        public static final java.text.NumberFormat.Field DECIMAL_SEPARATOR;

        static {
            DECIMAL_SEPARATOR = null;
        }

        public static final java.text.NumberFormat.Field EXPONENT;

        static {
            EXPONENT = null;
        }

        public static final java.text.NumberFormat.Field EXPONENT_SIGN;

        static {
            EXPONENT_SIGN = null;
        }

        public static final java.text.NumberFormat.Field EXPONENT_SYMBOL;

        static {
            EXPONENT_SYMBOL = null;
        }

        public static final java.text.NumberFormat.Field FRACTION;

        static {
            FRACTION = null;
        }

        public static final java.text.NumberFormat.Field GROUPING_SEPARATOR;

        static {
            GROUPING_SEPARATOR = null;
        }

        public static final java.text.NumberFormat.Field INTEGER;

        static {
            INTEGER = null;
        }

        public static final java.text.NumberFormat.Field PERCENT;

        static {
            PERCENT = null;
        }

        public static final java.text.NumberFormat.Field PERMILLE;

        static {
            PERMILLE = null;
        }

        public static final java.text.NumberFormat.Field SIGN;

        static {
            SIGN = null;
        }

        private static final java.util.Map<java.lang.String, java.text.NumberFormat.Field>
                instanceMap;

        static {
            instanceMap = null;
        }

        private static final long serialVersionUID = 7494728892700160890L; // 0x6802a038193ff37aL
    }
}
