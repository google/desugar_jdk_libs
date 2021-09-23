/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.nio;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class DirectByteBuffer extends java.nio.MappedByteBuffer implements sun.nio.ch.DirectBuffer {

    DirectByteBuffer(int capacity, java.nio.DirectByteBuffer.MemoryRef memoryRef) {
        super(0, 0, 0, 0);
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private DirectByteBuffer(long addr, int cap) {
        super(0, 0, 0, 0);
        throw new RuntimeException("Stub!");
    }

    public DirectByteBuffer(
            int cap,
            long addr,
            java.io.FileDescriptor fd,
            java.lang.Runnable unmapper,
            boolean isReadOnly) {
        super(0, 0, 0, 0);
        throw new RuntimeException("Stub!");
    }

    DirectByteBuffer(
            java.nio.DirectByteBuffer.MemoryRef memoryRef,
            int mark,
            int pos,
            int lim,
            int cap,
            int off) {
        super(0, 0, 0, 0);
        throw new RuntimeException("Stub!");
    }

    DirectByteBuffer(
            java.nio.DirectByteBuffer.MemoryRef memoryRef,
            int mark,
            int pos,
            int lim,
            int cap,
            int off,
            boolean isReadOnly) {
        super(0, 0, 0, 0);
        throw new RuntimeException("Stub!");
    }

    public final java.lang.Object attachment() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public final sun.misc.Cleaner cleaner() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer slice() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer duplicate() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer asReadOnlyBuffer() {
        throw new RuntimeException("Stub!");
    }

    public final long address() {
        throw new RuntimeException("Stub!");
    }

    private long ix(int i) {
        throw new RuntimeException("Stub!");
    }

    private byte get(long a) {
        throw new RuntimeException("Stub!");
    }

    public final byte get() {
        throw new RuntimeException("Stub!");
    }

    public final byte get(int i) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.ByteBuffer get(byte[] dst, int dstOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    private java.nio.ByteBuffer put(long a, byte x) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.ByteBuffer put(java.nio.ByteBuffer src) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer put(byte x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer put(int i, byte x) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.ByteBuffer put(byte[] src, int srcOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer compact() {
        throw new RuntimeException("Stub!");
    }

    public final boolean isDirect() {
        throw new RuntimeException("Stub!");
    }

    public final boolean isReadOnly() {
        throw new RuntimeException("Stub!");
    }

    final byte _get(int i) {
        throw new RuntimeException("Stub!");
    }

    final void _put(int i, byte b) {
        throw new RuntimeException("Stub!");
    }

    public final char getChar() {
        throw new RuntimeException("Stub!");
    }

    public final char getChar(int i) {
        throw new RuntimeException("Stub!");
    }

    char getCharUnchecked(int i) {
        throw new RuntimeException("Stub!");
    }

    void getUnchecked(int pos, char[] dst, int dstOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    private java.nio.ByteBuffer putChar(long a, char x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer putChar(char x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer putChar(int i, char x) {
        throw new RuntimeException("Stub!");
    }

    void putCharUnchecked(int i, char x) {
        throw new RuntimeException("Stub!");
    }

    void putUnchecked(int pos, char[] src, int srcOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.CharBuffer asCharBuffer() {
        throw new RuntimeException("Stub!");
    }

    private short getShort(long a) {
        throw new RuntimeException("Stub!");
    }

    public final short getShort() {
        throw new RuntimeException("Stub!");
    }

    public final short getShort(int i) {
        throw new RuntimeException("Stub!");
    }

    short getShortUnchecked(int i) {
        throw new RuntimeException("Stub!");
    }

    void getUnchecked(int pos, short[] dst, int dstOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    private java.nio.ByteBuffer putShort(long a, short x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer putShort(short x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer putShort(int i, short x) {
        throw new RuntimeException("Stub!");
    }

    void putShortUnchecked(int i, short x) {
        throw new RuntimeException("Stub!");
    }

    void putUnchecked(int pos, short[] src, int srcOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ShortBuffer asShortBuffer() {
        throw new RuntimeException("Stub!");
    }

    private int getInt(long a) {
        throw new RuntimeException("Stub!");
    }

    public int getInt() {
        throw new RuntimeException("Stub!");
    }

    public int getInt(int i) {
        throw new RuntimeException("Stub!");
    }

    final int getIntUnchecked(int i) {
        throw new RuntimeException("Stub!");
    }

    final void getUnchecked(int pos, int[] dst, int dstOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    private java.nio.ByteBuffer putInt(long a, int x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer putInt(int x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer putInt(int i, int x) {
        throw new RuntimeException("Stub!");
    }

    final void putIntUnchecked(int i, int x) {
        throw new RuntimeException("Stub!");
    }

    final void putUnchecked(int pos, int[] src, int srcOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.IntBuffer asIntBuffer() {
        throw new RuntimeException("Stub!");
    }

    private long getLong(long a) {
        throw new RuntimeException("Stub!");
    }

    public final long getLong() {
        throw new RuntimeException("Stub!");
    }

    public final long getLong(int i) {
        throw new RuntimeException("Stub!");
    }

    final long getLongUnchecked(int i) {
        throw new RuntimeException("Stub!");
    }

    final void getUnchecked(int pos, long[] dst, int dstOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    private java.nio.ByteBuffer putLong(long a, long x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer putLong(long x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer putLong(int i, long x) {
        throw new RuntimeException("Stub!");
    }

    final void putLongUnchecked(int i, long x) {
        throw new RuntimeException("Stub!");
    }

    final void putUnchecked(int pos, long[] src, int srcOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.LongBuffer asLongBuffer() {
        throw new RuntimeException("Stub!");
    }

    private float getFloat(long a) {
        throw new RuntimeException("Stub!");
    }

    public final float getFloat() {
        throw new RuntimeException("Stub!");
    }

    public final float getFloat(int i) {
        throw new RuntimeException("Stub!");
    }

    final float getFloatUnchecked(int i) {
        throw new RuntimeException("Stub!");
    }

    final void getUnchecked(int pos, float[] dst, int dstOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    private java.nio.ByteBuffer putFloat(long a, float x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer putFloat(float x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer putFloat(int i, float x) {
        throw new RuntimeException("Stub!");
    }

    final void putFloatUnchecked(int i, float x) {
        throw new RuntimeException("Stub!");
    }

    final void putUnchecked(int pos, float[] src, int srcOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.FloatBuffer asFloatBuffer() {
        throw new RuntimeException("Stub!");
    }

    private double getDouble(long a) {
        throw new RuntimeException("Stub!");
    }

    public final double getDouble() {
        throw new RuntimeException("Stub!");
    }

    public final double getDouble(int i) {
        throw new RuntimeException("Stub!");
    }

    final double getDoubleUnchecked(int i) {
        throw new RuntimeException("Stub!");
    }

    final void getUnchecked(int pos, double[] dst, int dstOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    private java.nio.ByteBuffer putDouble(long a, double x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer putDouble(double x) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer putDouble(int i, double x) {
        throw new RuntimeException("Stub!");
    }

    final void putDoubleUnchecked(int i, double x) {
        throw new RuntimeException("Stub!");
    }

    final void putUnchecked(int pos, double[] src, int srcOffset, int length) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.DoubleBuffer asDoubleBuffer() {
        throw new RuntimeException("Stub!");
    }

    public final boolean isAccessible() {
        throw new RuntimeException("Stub!");
    }

    public final void setAccessible(boolean value) {
        throw new RuntimeException("Stub!");
    }

    final sun.misc.Cleaner cleaner;

    {
        cleaner = null;
    }

    final java.nio.DirectByteBuffer.MemoryRef memoryRef;

    {
        memoryRef = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class MemoryRef {

        MemoryRef(int capacity) {
            throw new RuntimeException("Stub!");
        }

        MemoryRef(long allocatedAddress, java.lang.Object originalBufferObject) {
            throw new RuntimeException("Stub!");
        }

        void free() {
            throw new RuntimeException("Stub!");
        }

        long allocatedAddress;

        byte[] buffer;

        boolean isAccessible;

        boolean isFreed;

        final int offset;

        {
            offset = 0;
        }

        final java.lang.Object originalBufferObject;

        {
            originalBufferObject = null;
        }
    }
}
