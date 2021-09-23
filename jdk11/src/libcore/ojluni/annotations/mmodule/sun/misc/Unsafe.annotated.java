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

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class Unsafe {

private Unsafe() { throw new RuntimeException("Stub!"); }

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public static sun.misc.Unsafe getUnsafe() { throw new RuntimeException("Stub!"); }

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public long objectFieldOffset(java.lang.reflect.Field field) { throw new RuntimeException("Stub!"); }

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public int arrayBaseOffset(java.lang.Class clazz) { throw new RuntimeException("Stub!"); }


@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public int arrayIndexScale(java.lang.Class clazz) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public native boolean compareAndSwapInt(java.lang.Object obj, long offset, int expectedValue, int newValue);

@libcore.api.Hide
public native boolean compareAndSwapLong(java.lang.Object obj, long offset, long expectedValue, long newValue);

@libcore.api.Hide
public native boolean compareAndSwapObject(java.lang.Object obj, long offset, java.lang.Object expectedValue, java.lang.Object newValue);

@libcore.api.Hide
public native int getIntVolatile(java.lang.Object obj, long offset);

@libcore.api.Hide
public native void putIntVolatile(java.lang.Object obj, long offset, int newValue);

@libcore.api.Hide
public native long getLongVolatile(java.lang.Object obj, long offset);

@libcore.api.Hide
public native void putLongVolatile(java.lang.Object obj, long offset, long newValue);

@libcore.api.Hide
public native void putObjectVolatile(java.lang.Object obj, long offset, java.lang.Object newValue);

@libcore.api.Hide
public native java.lang.Object getObjectVolatile(java.lang.Object obj, long offset);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native int getInt(java.lang.Object obj, long offset);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void putInt(java.lang.Object obj, long offset, int newValue);

@libcore.api.Hide
public native void putOrderedInt(java.lang.Object obj, long offset, int newValue);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native long getLong(java.lang.Object obj, long offset);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void putLong(java.lang.Object obj, long offset, long newValue);

@libcore.api.Hide
public native void putOrderedLong(java.lang.Object obj, long offset, long newValue);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native java.lang.Object getObject(java.lang.Object obj, long offset);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void putObject(java.lang.Object obj, long offset, java.lang.Object newValue);

@libcore.api.Hide
public native void putOrderedObject(java.lang.Object obj, long offset, java.lang.Object newValue);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native boolean getBoolean(java.lang.Object obj, long offset);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void putBoolean(java.lang.Object obj, long offset, boolean newValue);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native byte getByte(java.lang.Object obj, long offset);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void putByte(java.lang.Object obj, long offset, byte newValue);

@libcore.api.Hide
public native char getChar(java.lang.Object obj, long offset);

@libcore.api.Hide
public native void putChar(java.lang.Object obj, long offset, char newValue);

@libcore.api.Hide
public native short getShort(java.lang.Object obj, long offset);

@libcore.api.Hide
public native void putShort(java.lang.Object obj, long offset, short newValue);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native float getFloat(java.lang.Object obj, long offset);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void putFloat(java.lang.Object obj, long offset, float newValue);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native double getDouble(java.lang.Object obj, long offset);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void putDouble(java.lang.Object obj, long offset, double newValue);

@libcore.api.Hide
public native void park(boolean absolute, long time);

@libcore.api.Hide
public native void unpark(java.lang.Object obj);

@libcore.api.Hide
public native java.lang.Object allocateInstance(java.lang.Class<?> c);

@libcore.api.Hide
public native int addressSize();

@libcore.api.Hide
public native int pageSize();

@libcore.api.Hide
public native long allocateMemory(long bytes);

@libcore.api.Hide
public native void freeMemory(long address);

@libcore.api.Hide
public native void setMemory(long address, long bytes, byte value);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native byte getByte(long address);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void putByte(long address, byte x);

@libcore.api.Hide
public native short getShort(long address);

@libcore.api.Hide
public native void putShort(long address, short x);

@libcore.api.Hide
public native char getChar(long address);

@libcore.api.Hide
public native void putChar(long address, char x);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native int getInt(long address);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void putInt(long address, int x);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native long getLong(long address);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void putLong(long address, long x);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native float getFloat(long address);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void putFloat(long address, float x);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native double getDouble(long address);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void putDouble(long address, double x);

@libcore.api.Hide
public native void copyMemoryToPrimitiveArray(long srcAddr, java.lang.Object dst, long dstOffset, long bytes);

@libcore.api.Hide
public native void copyMemoryFromPrimitiveArray(java.lang.Object src, long srcOffset, long dstAddr, long bytes);

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public native void copyMemory(long srcAddr, long dstAddr, long bytes);

@libcore.api.Hide
public int getAndAddInt(java.lang.Object o, long offset, int delta) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public long getAndAddLong(java.lang.Object o, long offset, long delta) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public int getAndSetInt(java.lang.Object o, long offset, int newValue) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public long getAndSetLong(java.lang.Object o, long offset, long newValue) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public java.lang.Object getAndSetObject(java.lang.Object o, long offset, java.lang.Object newValue) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public native void loadFence();

@libcore.api.Hide
public native void storeFence();

@libcore.api.Hide
public native void fullFence();

@libcore.api.Hide
public static final int INVALID_FIELD_OFFSET = -1; // 0xffffffff
}

