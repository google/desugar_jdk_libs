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
public class ZipInputStream extends java.util.zip.InflaterInputStream
        implements java.util.zip.ZipConstants {

    public ZipInputStream(java.io.InputStream in) {
        super((java.io.InputStream) null);
        throw new RuntimeException("Stub!");
    }

    public ZipInputStream(java.io.InputStream in, java.nio.charset.Charset charset) {
        super((java.io.InputStream) null);
        throw new RuntimeException("Stub!");
    }

    private void ensureOpen() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.zip.ZipEntry getNextEntry() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void closeEntry() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int available() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int read(byte[] b, int off, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public long skip(long n) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void close() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.util.zip.ZipEntry readLOC() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected java.util.zip.ZipEntry createZipEntry(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    private void readEnd(java.util.zip.ZipEntry e) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readFully(byte[] b, int off, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static final int DEFLATED = 8; // 0x8

    private static final int STORED = 0; // 0x0

    private byte[] b;

    private boolean closed = false;

    private java.util.zip.CRC32 crc;

    private java.util.zip.ZipEntry entry;

    private boolean entryEOF = false;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private int flag;

    private long remaining;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private byte[] tmpbuf;

    private java.util.zip.ZipCoder zc;
}
