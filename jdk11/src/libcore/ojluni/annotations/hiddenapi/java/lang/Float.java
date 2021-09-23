/*
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
public final class Float extends java.lang.Number implements java.lang.Comparable<java.lang.Float> {

    public Float(float value) {
        throw new RuntimeException("Stub!");
    }

    public Float(double value) {
        throw new RuntimeException("Stub!");
    }

    public Float(java.lang.String s) throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(float f) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toHexString(float f) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Float valueOf(java.lang.String s)
            throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Float valueOf(float f) {
        throw new RuntimeException("Stub!");
    }

    public static float parseFloat(java.lang.String s) throws java.lang.NumberFormatException {
        throw new RuntimeException("Stub!");
    }

    public static boolean isNaN(float v) {
        throw new RuntimeException("Stub!");
    }

    public static boolean isInfinite(float v) {
        throw new RuntimeException("Stub!");
    }

    public static boolean isFinite(float f) {
        throw new RuntimeException("Stub!");
    }

    public boolean isNaN() {
        throw new RuntimeException("Stub!");
    }

    public boolean isInfinite() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
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

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public static int hashCode(float value) {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public static int floatToIntBits(float value) {
        throw new RuntimeException("Stub!");
    }

    public static native int floatToRawIntBits(float value);

    public static native float intBitsToFloat(int bits);

    public int compareTo(java.lang.Float anotherFloat) {
        throw new RuntimeException("Stub!");
    }

    public static int compare(float f1, float f2) {
        throw new RuntimeException("Stub!");
    }

    public static float sum(float a, float b) {
        throw new RuntimeException("Stub!");
    }

    public static float max(float a, float b) {
        throw new RuntimeException("Stub!");
    }

    public static float min(float a, float b) {
        throw new RuntimeException("Stub!");
    }

    public static final int BYTES = 4; // 0x4

    public static final int MAX_EXPONENT = 127; // 0x7f

    public static final float MAX_VALUE = 3.4028235E38f;

    public static final int MIN_EXPONENT = -126; // 0xffffff82

    public static final float MIN_NORMAL = 1.17549435E-38f;

    public static final float MIN_VALUE = 1.4E-45f;

    public static final float NEGATIVE_INFINITY = (-1.0f / 0.0f);

    public static final float NaN = (0.0f / 0.0f);

    public static final float POSITIVE_INFINITY = (1.0f / 0.0f);

    public static final int SIZE = 32; // 0x20

    public static final java.lang.Class<java.lang.Float> TYPE;

    static {
        TYPE = null;
    }

    private static final long serialVersionUID = -2671257302660747028L; // 0xdaedc9a2db3cf0ecL

    /**
     * @deprecated Use {@link #floatValue()}.
     */
    @UnsupportedAppUsage(maxTargetSdk = VersionCodes.P)
    private final float value;

    {
        value = 0;
    }
}
