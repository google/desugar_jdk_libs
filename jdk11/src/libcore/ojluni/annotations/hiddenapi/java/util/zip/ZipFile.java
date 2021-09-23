/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1995, 2015, Oracle and/or its affiliates. All rights reserved.
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

package java.util.zip;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class ZipFile implements java.util.zip.ZipConstants, java.io.Closeable {

    public ZipFile(java.lang.String name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public ZipFile(java.io.File file, int mode) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public ZipFile(java.io.File file) throws java.io.IOException, java.util.zip.ZipException {
        throw new RuntimeException("Stub!");
    }

    public ZipFile(java.io.File file, int mode, java.nio.charset.Charset charset)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public ZipFile(java.lang.String name, java.nio.charset.Charset charset)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public ZipFile(java.io.File file, java.nio.charset.Charset charset) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getComment() {
        throw new RuntimeException("Stub!");
    }

    public java.util.zip.ZipEntry getEntry(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private static native long getEntry(long jzfile, byte[] name, boolean addSlash);

    private static native void freeEntry(long jzfile, long jzentry);

    public java.io.InputStream getInputStream(java.util.zip.ZipEntry entry)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.util.zip.Inflater getInflater() {
        throw new RuntimeException("Stub!");
    }

    private void releaseInflater(java.util.zip.Inflater inf) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<? extends java.util.zip.ZipEntry> entries() {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.Stream<? extends java.util.zip.ZipEntry> stream() {
        throw new RuntimeException("Stub!");
    }

    private java.util.zip.ZipEntry getZipEntry(java.lang.String name, long jzentry) {
        throw new RuntimeException("Stub!");
    }

    private static native long getNextEntry(long jzfile, int i);

    public int size() {
        throw new RuntimeException("Stub!");
    }

    public void close() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void finalize() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private static native void close(long jzfile);

    private void ensureOpen() {
        throw new RuntimeException("Stub!");
    }

    private void ensureOpenOrZipException() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean startsWithLocHeader() {
        throw new RuntimeException("Stub!");
    }

    public int getFileDescriptor() {
        throw new RuntimeException("Stub!");
    }

    private static native int getFileDescriptor(long jzfile);

    private static native long open(
            java.lang.String name, int mode, long lastModified, boolean usemmap)
            throws java.io.IOException;

    private static native int getTotal(long jzfile);

    private static native boolean startsWithLOC(long jzfile);

    private static native int read(long jzfile, long jzentry, long pos, byte[] b, int off, int len);

    private static native long getEntryTime(long jzentry);

    private static native long getEntryCrc(long jzentry);

    private static native long getEntryCSize(long jzentry);

    private static native long getEntrySize(long jzentry);

    private static native int getEntryMethod(long jzentry);

    private static native int getEntryFlag(long jzentry);

    private static native byte[] getCommentBytes(long jzfile);

    private static native byte[] getEntryBytes(long jzentry, int type);

    private static native java.lang.String getZipMessage(long jzfile);

    private static final int DEFLATED = 8; // 0x8

    private static final int JZENTRY_COMMENT = 2; // 0x2

    private static final int JZENTRY_EXTRA = 1; // 0x1

    private static final int JZENTRY_NAME = 0; // 0x0

    public static final int OPEN_DELETE = 4; // 0x4

    public static final int OPEN_READ = 1; // 0x1

    private static final int STORED = 0; // 0x0

    private volatile boolean closeRequested = false;

    private final java.io.File fileToRemoveOnClose;

    {
        fileToRemoveOnClose = null;
    }

    private final dalvik.system.CloseGuard guard;

    {
        guard = null;
    }

    private java.util.Deque<java.util.zip.Inflater> inflaterCache;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private long jzfile;

    private final boolean locsig;

    {
        locsig = false;
    }

    private final java.lang.String name;

    {
        name = null;
    }

    private final java.util.Map<java.io.InputStream, java.util.zip.Inflater> streams;

    {
        streams = null;
    }

    private final int total;

    {
        total = 0;
    }

    private static final boolean usemmap;

    static {
        usemmap = false;
    }

    private java.util.zip.ZipCoder zc;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class ZipEntryIterator
            implements java.util.Enumeration<java.util.zip.ZipEntry>,
                    java.util.Iterator<java.util.zip.ZipEntry> {

        public ZipEntryIterator() {
            throw new RuntimeException("Stub!");
        }

        public boolean hasMoreElements() {
            throw new RuntimeException("Stub!");
        }

        public boolean hasNext() {
            throw new RuntimeException("Stub!");
        }

        @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        public java.util.zip.ZipEntry nextElement() {
            throw new RuntimeException("Stub!");
        }

        public java.util.zip.ZipEntry next() {
            throw new RuntimeException("Stub!");
        }

        private int i = 0; // 0x0
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class ZipFileInflaterInputStream extends java.util.zip.InflaterInputStream {

        ZipFileInflaterInputStream(
                java.util.zip.ZipFile.ZipFileInputStream zfin,
                java.util.zip.Inflater inf,
                int size) {
            super((java.io.InputStream) null);
            throw new RuntimeException("Stub!");
        }

        public void close() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        protected void fill() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int available() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        protected void finalize() throws java.lang.Throwable {
            throw new RuntimeException("Stub!");
        }

        private volatile boolean closeRequested = false;

        private boolean eof = false;

        private final java.util.zip.ZipFile.ZipFileInputStream zfin;

        {
            zfin = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class ZipFileInputStream extends java.io.InputStream {

        ZipFileInputStream(long jzentry) {
            throw new RuntimeException("Stub!");
        }

        public int read(byte[] b, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int read() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public long skip(long n) {
            throw new RuntimeException("Stub!");
        }

        public int available() {
            throw new RuntimeException("Stub!");
        }

        public long size() {
            throw new RuntimeException("Stub!");
        }

        public void close() {
            throw new RuntimeException("Stub!");
        }

        protected void finalize() {
            throw new RuntimeException("Stub!");
        }

        protected long jzentry;

        private long pos;

        protected long rem;

        protected long size;

        private volatile boolean zfisCloseRequested = false;
    }
}
