/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1996, 2015, Oracle and/or its affiliates. All rights reserved.
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
public class ZipOutputStream extends java.util.zip.DeflaterOutputStream
        implements java.util.zip.ZipConstants {

    public ZipOutputStream(java.io.OutputStream out) {
        super((java.io.OutputStream) null);
        throw new RuntimeException("Stub!");
    }

    public ZipOutputStream(java.io.OutputStream out, java.nio.charset.Charset charset) {
        super((java.io.OutputStream) null);
        throw new RuntimeException("Stub!");
    }

    private static int version(java.util.zip.ZipEntry e) throws java.util.zip.ZipException {
        throw new RuntimeException("Stub!");
    }

    private void ensureOpen() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void setComment(java.lang.String comment) {
        throw new RuntimeException("Stub!");
    }

    public void setMethod(int method) {
        throw new RuntimeException("Stub!");
    }

    public void setLevel(int level) {
        throw new RuntimeException("Stub!");
    }

    public void putNextEntry(java.util.zip.ZipEntry e) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void closeEntry() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void write(byte[] b, int off, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void finish() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void close() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeLOC(java.util.zip.ZipOutputStream.XEntry xentry) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeEXT(java.util.zip.ZipEntry e) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeCEN(java.util.zip.ZipOutputStream.XEntry xentry) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeEND(long off, long len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private int getExtraLen(byte[] extra) {
        throw new RuntimeException("Stub!");
    }

    private void writeExtra(byte[] extra) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeByte(int v) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeShort(int v) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeInt(long v) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeLong(long v) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeBytes(byte[] b, int off, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public static final int DEFLATED = 8; // 0x8

    public static final int STORED = 0; // 0x0

    private boolean closed = false;

    private byte[] comment;

    private java.util.zip.CRC32 crc;

    private java.util.zip.ZipOutputStream.XEntry current;

    private boolean finished;

    private static final boolean inhibitZip64 = false;

    private long locoff = 0; // 0x0

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private int method = 8; // 0x8

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private java.util.HashSet<java.lang.String> names;

    @UnsupportedAppUsage
    private long written = 0; // 0x0

    private java.util.Vector<java.util.zip.ZipOutputStream.XEntry> xentries;

    private final java.util.zip.ZipCoder zc;

    {
        zc = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class XEntry {

        public XEntry(java.util.zip.ZipEntry entry, long offset) {
            throw new RuntimeException("Stub!");
        }

        final java.util.zip.ZipEntry entry;

        {
            entry = null;
        }

        final long offset;

        {
            offset = 0;
        }
    }
}
