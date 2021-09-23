/*
 * Copyright (C) 2014 The Android Open Source Project
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

package sun.nio.ch;


@SuppressWarnings({"unchecked", "deprecation", "all"})
public class FileChannelImpl extends java.nio.channels.FileChannel {

    private FileChannelImpl(
            java.io.FileDescriptor fd,
            java.lang.String path,
            boolean readable,
            boolean writable,
            boolean append,
            java.lang.Object parent) {
        throw new RuntimeException("Stub!");
    }

    public static java.nio.channels.FileChannel open(
            java.io.FileDescriptor fd,
            java.lang.String path,
            boolean readable,
            boolean writable,
            java.lang.Object parent) {
        throw new RuntimeException("Stub!");
    }

    public static java.nio.channels.FileChannel open(
            java.io.FileDescriptor fd,
            java.lang.String path,
            boolean readable,
            boolean writable,
            boolean append,
            java.lang.Object parent) {
        throw new RuntimeException("Stub!");
    }

    private void ensureOpen() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void implCloseChannel() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void finalize() throws java.lang.Throwable {
        throw new RuntimeException("Stub!");
    }

    public int read(java.nio.ByteBuffer dst) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public long read(java.nio.ByteBuffer[] dsts, int offset, int length)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int write(java.nio.ByteBuffer src) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public long write(java.nio.ByteBuffer[] srcs, int offset, int length)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public long position() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.nio.channels.FileChannel position(long newPosition) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public long size() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.nio.channels.FileChannel truncate(long newSize) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void force(boolean metaData) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private long transferToDirectlyInternal(
            long position,
            int icount,
            java.nio.channels.WritableByteChannel target,
            java.io.FileDescriptor targetFD)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private long transferToDirectly(
            long position, int icount, java.nio.channels.WritableByteChannel target)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private long transferToTrustedChannel(
            long position, long count, java.nio.channels.WritableByteChannel target)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private long transferToArbitraryChannel(
            long position, int icount, java.nio.channels.WritableByteChannel target)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public long transferTo(long position, long count, java.nio.channels.WritableByteChannel target)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private long transferFromFileChannel(sun.nio.ch.FileChannelImpl src, long position, long count)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private long transferFromArbitraryChannel(
            java.nio.channels.ReadableByteChannel src, long position, long count)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public long transferFrom(java.nio.channels.ReadableByteChannel src, long position, long count)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int read(java.nio.ByteBuffer dst, long position) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private int readInternal(java.nio.ByteBuffer dst, long position) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int write(java.nio.ByteBuffer src, long position) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private int writeInternal(java.nio.ByteBuffer src, long position) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static void unmap(java.nio.MappedByteBuffer bb) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.MappedByteBuffer map(
            java.nio.channels.FileChannel.MapMode mode, long position, long size)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static boolean isSharedFileLockTable() {
        throw new RuntimeException("Stub!");
    }

    private sun.nio.ch.FileLockTable fileLockTable() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.nio.channels.FileLock lock(long position, long size, boolean shared)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.nio.channels.FileLock tryLock(long position, long size, boolean shared)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    void release(sun.nio.ch.FileLockImpl fli) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private native long map0(int prot, long position, long length) throws java.io.IOException;

    @android.compat.annotation.UnsupportedAppUsage
    private static native int unmap0(long address, long length);

    private native long transferTo0(
            java.io.FileDescriptor src, long position, long count, java.io.FileDescriptor dst);

    private native long position0(java.io.FileDescriptor fd, long offset);

    private static native long initIDs();

    private static final long MAPPED_TRANSFER_SIZE = 8388608L; // 0x800000L

    private static final int MAP_PV = 2; // 0x2

    private static final int MAP_RO = 0; // 0x0

    private static final int MAP_RW = 1; // 0x1

    private static final int TRANSFER_SIZE = 8192; // 0x2000

    private static final long allocationGranularity;

    static {
        allocationGranularity = 0;
    }

    private final boolean append;

    {
        append = false;
    }

    public final java.io.FileDescriptor fd;

    {
        fd = null;
    }

    private volatile sun.nio.ch.FileLockTable fileLockTable;

    private static volatile boolean fileSupported = true;

    private final dalvik.system.CloseGuard guard;

    {
        guard = null;
    }

    private static boolean isSharedFileLockTable;

    private final sun.nio.ch.FileDispatcher nd;

    {
        nd = null;
    }

    private final java.lang.Object parent;

    {
        parent = null;
    }

    private final java.lang.String path;

    {
        path = null;
    }

    private static volatile boolean pipeSupported = true;

    private final java.lang.Object positionLock;

    {
        positionLock = null;
    }

    private static volatile boolean propertyChecked;

    private final boolean readable;

    {
        readable = false;
    }

    private final sun.nio.ch.NativeThreadSet threads;

    {
        threads = null;
    }

    private static volatile boolean transferSupported = true;

    private final boolean writable;

    {
        writable = false;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class SimpleFileLockTable extends sun.nio.ch.FileLockTable {

        public SimpleFileLockTable() {
            throw new RuntimeException("Stub!");
        }

        private void checkList(long position, long size)
                throws java.nio.channels.OverlappingFileLockException {
            throw new RuntimeException("Stub!");
        }

        public void add(java.nio.channels.FileLock fl)
                throws java.nio.channels.OverlappingFileLockException {
            throw new RuntimeException("Stub!");
        }

        public void remove(java.nio.channels.FileLock fl) {
            throw new RuntimeException("Stub!");
        }

        public java.util.List<java.nio.channels.FileLock> removeAll() {
            throw new RuntimeException("Stub!");
        }

        public void replace(java.nio.channels.FileLock fl1, java.nio.channels.FileLock fl2) {
            throw new RuntimeException("Stub!");
        }

        private final java.util.List<java.nio.channels.FileLock> lockList;

        {
            lockList = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class Unmapper implements java.lang.Runnable {

        private Unmapper(long address, long size, int cap, java.io.FileDescriptor fd) {
            throw new RuntimeException("Stub!");
        }

        public void run() {
            throw new RuntimeException("Stub!");
        }

        private volatile long address;

        private final int cap;

        {
            cap = 0;
        }

        static volatile int count;

        private final java.io.FileDescriptor fd;

        {
            fd = null;
        }

        private static final sun.nio.ch.NativeDispatcher nd;

        static {
            nd = null;
        }

        private final long size;

        {
            size = 0;
        }

        static volatile long totalCapacity;

        static volatile long totalSize;
    }
}
