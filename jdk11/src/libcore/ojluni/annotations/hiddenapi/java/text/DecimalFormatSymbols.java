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
public class DecimalFormatSymbols implements java.lang.Cloneable, java.io.Serializable {

    public DecimalFormatSymbols() {
        throw new RuntimeException("Stub!");
    }

    public DecimalFormatSymbols(java.util.Locale locale) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Locale[] getAvailableLocales() {
        throw new RuntimeException("Stub!");
    }

    public static final java.text.DecimalFormatSymbols getInstance() {
        throw new RuntimeException("Stub!");
    }

    public static final java.text.DecimalFormatSymbols getInstance(java.util.Locale locale) {
        throw new RuntimeException("Stub!");
    }

    public char getZeroDigit() {
        throw new RuntimeException("Stub!");
    }

    public void setZeroDigit(char zeroDigit) {
        throw new RuntimeException("Stub!");
    }

    public char getGroupingSeparator() {
        throw new RuntimeException("Stub!");
    }

    public void setGroupingSeparator(char groupingSeparator) {
        throw new RuntimeException("Stub!");
    }

    public char getDecimalSeparator() {
        throw new RuntimeException("Stub!");
    }

    public void setDecimalSeparator(char decimalSeparator) {
        throw new RuntimeException("Stub!");
    }

    public char getPerMill() {
        throw new RuntimeException("Stub!");
    }

    public void setPerMill(char perMill) {
        throw new RuntimeException("Stub!");
    }

    public char getPercent() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public java.lang.String getPercentString() {
        throw new RuntimeException("Stub!");
    }

    public void setPercent(char percent) {
        throw new RuntimeException("Stub!");
    }

    public char getDigit() {
        throw new RuntimeException("Stub!");
    }

    public void setDigit(char digit) {
        throw new RuntimeException("Stub!");
    }

    public char getPatternSeparator() {
        throw new RuntimeException("Stub!");
    }

    public void setPatternSeparator(char patternSeparator) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getInfinity() {
        throw new RuntimeException("Stub!");
    }

    public void setInfinity(java.lang.String infinity) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getNaN() {
        throw new RuntimeException("Stub!");
    }

    public void setNaN(java.lang.String NaN) {
        throw new RuntimeException("Stub!");
    }

    public char getMinusSign() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getMinusSignString() {
        throw new RuntimeException("Stub!");
    }

    public void setMinusSign(char minusSign) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getCurrencySymbol() {
        throw new RuntimeException("Stub!");
    }

    public void setCurrencySymbol(java.lang.String currency) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getInternationalCurrencySymbol() {
        throw new RuntimeException("Stub!");
    }

    public void setInternationalCurrencySymbol(java.lang.String currencyCode) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Currency getCurrency() {
        throw new RuntimeException("Stub!");
    }

    public void setCurrency(java.util.Currency currency) {
        throw new RuntimeException("Stub!");
    }

    public char getMonetaryDecimalSeparator() {
        throw new RuntimeException("Stub!");
    }

    public void setMonetaryDecimalSeparator(char sep) {
        throw new RuntimeException("Stub!");
    }

    char getExponentialSymbol() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getExponentSeparator() {
        throw new RuntimeException("Stub!");
    }

    void setExponentialSymbol(char exp) {
        throw new RuntimeException("Stub!");
    }

    public void setExponentSeparator(java.lang.String exp) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object clone() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    private void initialize(java.util.Locale locale) {
        throw new RuntimeException("Stub!");
    }

    public static char maybeStripMarkers(java.lang.String symbol, char fallback) {
        throw new RuntimeException("Stub!");
    }

    protected android.icu.text.DecimalFormatSymbols getIcuDecimalFormatSymbols() {
        throw new RuntimeException("Stub!");
    }

    protected static java.text.DecimalFormatSymbols fromIcuInstance(
            android.icu.text.DecimalFormatSymbols dfs) {
        throw new RuntimeException("Stub!");
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String NaN;

    private transient android.icu.text.DecimalFormatSymbols cachedIcuDFS;

    private transient java.util.Currency currency;

    private java.lang.String currencySymbol;

    private static final int currentSerialVersion = 3; // 0x3

    private char decimalSeparator;

    private char digit;

    private char exponential;

    private java.lang.String exponentialSeparator;

    private char groupingSeparator;

    private java.lang.String infinity;

    private java.lang.String intlCurrencySymbol;

    private java.util.Locale locale;

    private char minusSign;

    private char monetarySeparator;

    private char patternSeparator;

    private char perMill;

    private char percent;

    private static final java.io.ObjectStreamField[] serialPersistentFields;

    static {
        serialPersistentFields = new java.io.ObjectStreamField[0];
    }

    private int serialVersionOnStream = 3; // 0x3

    static final long serialVersionUID = 5772796243397350300L; // 0x501d17990868939cL

    private char zeroDigit;
}
