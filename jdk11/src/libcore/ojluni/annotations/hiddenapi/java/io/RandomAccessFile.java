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

package java.io;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class RandomAccessFile implements java.io.DataOutput, java.io.DataInput, java.io.Closeable {

    public RandomAccessFile(java.lang.String name, java.lang.String mode)
            throws java.io.FileNotFoundException {
        throw new RuntimeException("Stub!");
    }

    public RandomAccessFile(java.io.File file, java.lang.String mode)
            throws java.io.FileNotFoundException {
        throw new RuntimeException("Stub!");
    }

    private void maybeSync() {
        throw new RuntimeException("Stub!");
    }

    public final java.io.FileDescriptor getFD() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.channels.FileChannel getChannel() {
        throw new RuntimeException("Stub!");
    }

    public int read() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private int readBytes(byte[] b, int off, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int read(byte[] b, int off, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int read(byte[] b) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void readFully(byte[] b) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void readFully(byte[] b, int off, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int skipBytes(int n) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void write(int b) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeBytes(byte[] b, int off, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void write(byte[] b) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void write(byte[] b, int off, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public long getFilePointer() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void seek(long pos) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public long length() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void setLength(long newLength) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void close() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final boolean readBoolean() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final byte readByte() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final int readUnsignedByte() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final short readShort() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final int readUnsignedShort() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final char readChar() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final int readInt() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final long readLong() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final float readFloat() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final double readDouble() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.String readLine() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.String readUTF() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void writeBoolean(boolean v) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void writeByte(int v) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void writeShort(int v) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void writeChar(int v) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void writeInt(int v) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void writeLong(long v) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void writeFloat(float v) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void writeDouble(double v) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void writeBytes(java.lang.String s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void writeChars(java.lang.String s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void writeUTF(java.lang.String str) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void finalize() throws java.lang.Throwable {
        throw new RuntimeException("Stub!");
    }

    private static final int FLUSH_FDATASYNC = 2; // 0x2

    private static final int FLUSH_FSYNC = 1; // 0x1

    private static final int FLUSH_NONE = 0; // 0x0

    private java.nio.channels.FileChannel channel;

    private java.lang.Object closeLock;

    private volatile boolean closed = false;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private java.io.FileDescriptor fd;

    private int flushAfterWrite = 0; // 0x0

    private final dalvik.system.CloseGuard guard;

    {
        guard = null;
    }

    private final libcore.io.IoTracker ioTracker;

    {
        ioTracker = null;
    }

    private int mode;

    private final java.lang.String path;

    {
        path = null;
    }

    private boolean rw;

    private final byte[] scratch;

    {
        scratch = new byte[0];
    }
}
