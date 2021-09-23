/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import android.compat.annotation.UnsupportedAppUsage;

import dalvik.annotation.compat.VersionCodes;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class Unsafe {

    private Unsafe() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public static sun.misc.Unsafe getUnsafe() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public long objectFieldOffset(java.lang.reflect.Field field) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public int arrayBaseOffset(java.lang.Class clazz) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public int arrayIndexScale(java.lang.Class clazz) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = VersionCodes.O)
    private static native int getArrayBaseOffsetForComponentType(java.lang.Class component_class);

    @UnsupportedAppUsage(maxTargetSdk = VersionCodes.O)
    private static native int getArrayIndexScaleForComponentType(java.lang.Class component_class);

    @UnsupportedAppUsage
    public native boolean compareAndSwapInt(
            java.lang.Object obj, long offset, int expectedValue, int newValue);

    @UnsupportedAppUsage
    public native boolean compareAndSwapLong(
            java.lang.Object obj, long offset, long expectedValue, long newValue);

    @UnsupportedAppUsage
    public native boolean compareAndSwapObject(
            java.lang.Object obj,
            long offset,
            java.lang.Object expectedValue,
            java.lang.Object newValue);

    @UnsupportedAppUsage
    public native int getIntVolatile(java.lang.Object obj, long offset);

    @UnsupportedAppUsage
    public native void putIntVolatile(java.lang.Object obj, long offset, int newValue);

    @UnsupportedAppUsage
    public native long getLongVolatile(java.lang.Object obj, long offset);

    @UnsupportedAppUsage
    public native void putLongVolatile(java.lang.Object obj, long offset, long newValue);

    @UnsupportedAppUsage
    public native java.lang.Object getObjectVolatile(java.lang.Object obj, long offset);

    @UnsupportedAppUsage
    public native void putObjectVolatile(
            java.lang.Object obj, long offset, java.lang.Object newValue);

    @UnsupportedAppUsage
    public native int getInt(java.lang.Object obj, long offset);

    @UnsupportedAppUsage
    public native void putInt(java.lang.Object obj, long offset, int newValue);

    @UnsupportedAppUsage
    public native void putOrderedInt(java.lang.Object obj, long offset, int newValue);

    @UnsupportedAppUsage
    public native long getLong(java.lang.Object obj, long offset);

    @UnsupportedAppUsage
    public native void putLong(java.lang.Object obj, long offset, long newValue);

    @UnsupportedAppUsage
    public native void putOrderedLong(java.lang.Object obj, long offset, long newValue);

    @UnsupportedAppUsage
    public native java.lang.Object getObject(java.lang.Object obj, long offset);

    @UnsupportedAppUsage
    public native void putObject(java.lang.Object obj, long offset, java.lang.Object newValue);

    @UnsupportedAppUsage
    public native void putOrderedObject(
            java.lang.Object obj, long offset, java.lang.Object newValue);

    @UnsupportedAppUsage
    public native boolean getBoolean(java.lang.Object obj, long offset);

    @UnsupportedAppUsage
    public native void putBoolean(java.lang.Object obj, long offset, boolean newValue);

    @UnsupportedAppUsage
    public native byte getByte(java.lang.Object obj, long offset);

    @UnsupportedAppUsage
    public native void putByte(java.lang.Object obj, long offset, byte newValue);

    @UnsupportedAppUsage
    public native char getChar(java.lang.Object obj, long offset);

    @UnsupportedAppUsage
    public native void putChar(java.lang.Object obj, long offset, char newValue);

    @UnsupportedAppUsage
    public native short getShort(java.lang.Object obj, long offset);

    @UnsupportedAppUsage
    public native void putShort(java.lang.Object obj, long offset, short newValue);

    @UnsupportedAppUsage
    public native float getFloat(java.lang.Object obj, long offset);

    @UnsupportedAppUsage
    public native void putFloat(java.lang.Object obj, long offset, float newValue);

    @UnsupportedAppUsage
    public native double getDouble(java.lang.Object obj, long offset);

    @UnsupportedAppUsage
    public native void putDouble(java.lang.Object obj, long offset, double newValue);

    @UnsupportedAppUsage
    public void park(boolean absolute, long time) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public void unpark(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public native java.lang.Object allocateInstance(java.lang.Class<?> c);

    @UnsupportedAppUsage
    public native int addressSize();

    @UnsupportedAppUsage
    public native int pageSize();

    @UnsupportedAppUsage
    public native long allocateMemory(long bytes);

    @UnsupportedAppUsage
    public native void freeMemory(long address);

    @UnsupportedAppUsage
    public native void setMemory(long address, long bytes, byte value);

    @UnsupportedAppUsage
    public native byte getByte(long address);

    @UnsupportedAppUsage
    public native void putByte(long address, byte x);

    @UnsupportedAppUsage
    public native short getShort(long address);

    @UnsupportedAppUsage
    public native void putShort(long address, short x);

    @UnsupportedAppUsage
    public native char getChar(long address);

    @UnsupportedAppUsage
    public native void putChar(long address, char x);

    @UnsupportedAppUsage
    public native int getInt(long address);

    @UnsupportedAppUsage
    public native void putInt(long address, int x);

    @UnsupportedAppUsage
    public native long getLong(long address);

    @UnsupportedAppUsage
    public native void putLong(long address, long x);

    @UnsupportedAppUsage
    public native float getFloat(long address);

    @UnsupportedAppUsage
    public native void putFloat(long address, float x);

    @UnsupportedAppUsage
    public native double getDouble(long address);

    @UnsupportedAppUsage
    public native void putDouble(long address, double x);

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public native void copyMemoryToPrimitiveArray(
            long srcAddr, java.lang.Object dst, long dstOffset, long bytes);

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public native void copyMemoryFromPrimitiveArray(
            java.lang.Object src, long srcOffset, long dstAddr, long bytes);

    @UnsupportedAppUsage
    public native void copyMemory(long srcAddr, long dstAddr, long bytes);

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public int getAndAddInt(java.lang.Object o, long offset, int delta) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public long getAndAddLong(java.lang.Object o, long offset, long delta) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public int getAndSetInt(java.lang.Object o, long offset, int newValue) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public long getAndSetLong(java.lang.Object o, long offset, long newValue) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public java.lang.Object getAndSetObject(
            java.lang.Object o, long offset, java.lang.Object newValue) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public native void loadFence();

    @UnsupportedAppUsage
    public native void storeFence();

    @UnsupportedAppUsage
    public native void fullFence();

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static final int INVALID_FIELD_OFFSET = -1; // 0xffffffff

    @UnsupportedAppUsage private static final sun.misc.Unsafe THE_ONE;

    static {
        THE_ONE = null;
    }

    @UnsupportedAppUsage private static final sun.misc.Unsafe theUnsafe;

    static {
        theUnsafe = null;
    }
}
