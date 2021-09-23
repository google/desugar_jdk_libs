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

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class System {

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private System() {
        throw new RuntimeException("Stub!");
    }

    public static void setIn(java.io.InputStream in) {
        throw new RuntimeException("Stub!");
    }

    public static void setOut(java.io.PrintStream out) {
        throw new RuntimeException("Stub!");
    }

    public static void setErr(java.io.PrintStream err) {
        throw new RuntimeException("Stub!");
    }

    public static java.io.Console console() {
        throw new RuntimeException("Stub!");
    }

    public static java.nio.channels.Channel inheritedChannel() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static native void setIn0(java.io.InputStream in);

    private static native void setOut0(java.io.PrintStream out);

    private static native void setErr0(java.io.PrintStream err);

    public static void setSecurityManager(java.lang.SecurityManager s) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.SecurityManager getSecurityManager() {
        throw new RuntimeException("Stub!");
    }

    public static native long currentTimeMillis();

    public static native long nanoTime();

    public static native void arraycopy(
            java.lang.Object src, int srcPos, java.lang.Object dest, int destPos, int length);

    @UnsupportedAppUsage
    private static void arraycopy(char[] src, int srcPos, char[] dst, int dstPos, int length) {
        throw new RuntimeException("Stub!");
    }

    private static native void arraycopyCharUnchecked(
            char[] src, int srcPos, char[] dst, int dstPos, int length);

    @UnsupportedAppUsage
    public static void arraycopy(byte[] src, int srcPos, byte[] dst, int dstPos, int length) {
        throw new RuntimeException("Stub!");
    }

    private static native void arraycopyByteUnchecked(
            byte[] src, int srcPos, byte[] dst, int dstPos, int length);

    @UnsupportedAppUsage
    private static void arraycopy(short[] src, int srcPos, short[] dst, int dstPos, int length) {
        throw new RuntimeException("Stub!");
    }

    private static native void arraycopyShortUnchecked(
            short[] src, int srcPos, short[] dst, int dstPos, int length);

    @UnsupportedAppUsage
    private static void arraycopy(int[] src, int srcPos, int[] dst, int dstPos, int length) {
        throw new RuntimeException("Stub!");
    }

    private static native void arraycopyIntUnchecked(
            int[] src, int srcPos, int[] dst, int dstPos, int length);

    @UnsupportedAppUsage
    private static void arraycopy(long[] src, int srcPos, long[] dst, int dstPos, int length) {
        throw new RuntimeException("Stub!");
    }

    private static native void arraycopyLongUnchecked(
            long[] src, int srcPos, long[] dst, int dstPos, int length);

    @UnsupportedAppUsage
    private static void arraycopy(float[] src, int srcPos, float[] dst, int dstPos, int length) {
        throw new RuntimeException("Stub!");
    }

    private static native void arraycopyFloatUnchecked(
            float[] src, int srcPos, float[] dst, int dstPos, int length);

    private static void arraycopy(double[] src, int srcPos, double[] dst, int dstPos, int length) {
        throw new RuntimeException("Stub!");
    }

    private static native void arraycopyDoubleUnchecked(
            double[] src, int srcPos, double[] dst, int dstPos, int length);

    @UnsupportedAppUsage
    private static void arraycopy(
            boolean[] src, int srcPos, boolean[] dst, int dstPos, int length) {
        throw new RuntimeException("Stub!");
    }

    private static native void arraycopyBooleanUnchecked(
            boolean[] src, int srcPos, boolean[] dst, int dstPos, int length);

    public static int identityHashCode(java.lang.Object x) {
        throw new RuntimeException("Stub!");
    }

    private static native java.lang.String[] specialProperties();

    private static void parsePropertyAssignments(
            java.util.Properties p, java.lang.String[] assignments) {
        throw new RuntimeException("Stub!");
    }

    private static java.util.Properties initUnchangeableSystemProperties() {
        throw new RuntimeException("Stub!");
    }

    private static java.util.Properties initProperties() {
        throw new RuntimeException("Stub!");
    }

    private static java.util.Properties setDefaultChangeableProperties(java.util.Properties p) {
        throw new RuntimeException("Stub!");
    }

    public static void setUnchangeableSystemProperty(java.lang.String key, java.lang.String value) {
        throw new RuntimeException("Stub!");
    }

    private static void addLegacyLocaleSystemProperties() {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Properties getProperties() {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String lineSeparator() {
        throw new RuntimeException("Stub!");
    }

    public static void setProperties(java.util.Properties props) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String getProperty(java.lang.String key) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String getProperty(java.lang.String key, java.lang.String def) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String setProperty(java.lang.String key, java.lang.String value) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String clearProperty(java.lang.String key) {
        throw new RuntimeException("Stub!");
    }

    private static void checkKey(java.lang.String key) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String getenv(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Map<java.lang.String, java.lang.String> getenv() {
        throw new RuntimeException("Stub!");
    }

    public static void exit(int status) {
        throw new RuntimeException("Stub!");
    }

    public static void gc() {
        throw new RuntimeException("Stub!");
    }

    public static void runFinalization() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public static void runFinalizersOnExit(boolean value) {
        throw new RuntimeException("Stub!");
    }

    public static void load(java.lang.String filename) {
        throw new RuntimeException("Stub!");
    }

    public static void loadLibrary(java.lang.String libname) {
        throw new RuntimeException("Stub!");
    }

    public static native java.lang.String mapLibraryName(java.lang.String libname);

    private static java.io.PrintStream newPrintStream(
            java.io.FileOutputStream fos, java.lang.String enc) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public static void logE(java.lang.String message) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public static void logE(java.lang.String message, java.lang.Throwable th) {
        throw new RuntimeException("Stub!");
    }

    public static void logI(java.lang.String message) {
        throw new RuntimeException("Stub!");
    }

    public static void logI(java.lang.String message, java.lang.Throwable th) {
        throw new RuntimeException("Stub!");
    }

    public static void logW(java.lang.String message) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public static void logW(java.lang.String message, java.lang.Throwable th) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private static native void log(char type, java.lang.String message, java.lang.Throwable th);

    private static final int ARRAYCOPY_SHORT_BOOLEAN_ARRAY_THRESHOLD = 32; // 0x20

    private static final int ARRAYCOPY_SHORT_BYTE_ARRAY_THRESHOLD = 32; // 0x20

    private static final int ARRAYCOPY_SHORT_CHAR_ARRAY_THRESHOLD = 32; // 0x20

    private static final int ARRAYCOPY_SHORT_DOUBLE_ARRAY_THRESHOLD = 32; // 0x20

    private static final int ARRAYCOPY_SHORT_FLOAT_ARRAY_THRESHOLD = 32; // 0x20

    private static final int ARRAYCOPY_SHORT_INT_ARRAY_THRESHOLD = 32; // 0x20

    private static final int ARRAYCOPY_SHORT_LONG_ARRAY_THRESHOLD = 32; // 0x20

    private static final int ARRAYCOPY_SHORT_SHORT_ARRAY_THRESHOLD = 32; // 0x20

    private static final java.lang.Object LOCK;

    static {
        LOCK = null;
    }

    private static volatile java.io.Console cons;

    public static final java.io.PrintStream err;

    static {
        err = null;
    }

    public static final java.io.InputStream in;

    static {
        in = null;
    }

    private static boolean justRanFinalization;

    private static java.lang.String lineSeparator;

    public static final java.io.PrintStream out;

    static {
        out = null;
    }

    private static java.util.Properties props;

    private static boolean runGC;

    private static java.util.Properties unchangeableProps;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class PropertiesWithNonOverrideableDefaults extends java.util.Properties {

        PropertiesWithNonOverrideableDefaults(java.util.Properties defaults) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object put(java.lang.Object key, java.lang.Object value) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object remove(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }
    }
}
