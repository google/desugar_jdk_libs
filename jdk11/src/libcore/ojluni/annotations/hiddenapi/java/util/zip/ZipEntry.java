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
public class ZipEntry implements java.util.zip.ZipConstants, java.lang.Cloneable {

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public ZipEntry(
            java.lang.String name,
            java.lang.String comment,
            long crc,
            long compressedSize,
            long size,
            int compressionMethod,
            int xdostime,
            byte[] extra,
            long dataOffset) {
        throw new RuntimeException("Stub!");
    }

    public ZipEntry(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public ZipEntry(java.util.zip.ZipEntry e) {
        throw new RuntimeException("Stub!");
    }

    ZipEntry() {
        throw new RuntimeException("Stub!");
    }

    public long getDataOffset() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public void setTime(long time) {
        throw new RuntimeException("Stub!");
    }

    public long getTime() {
        throw new RuntimeException("Stub!");
    }

    public java.util.zip.ZipEntry setLastModifiedTime(java.nio.file.attribute.FileTime time) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.file.attribute.FileTime getLastModifiedTime() {
        throw new RuntimeException("Stub!");
    }

    public java.util.zip.ZipEntry setLastAccessTime(java.nio.file.attribute.FileTime time) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.file.attribute.FileTime getLastAccessTime() {
        throw new RuntimeException("Stub!");
    }

    public java.util.zip.ZipEntry setCreationTime(java.nio.file.attribute.FileTime time) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.file.attribute.FileTime getCreationTime() {
        throw new RuntimeException("Stub!");
    }

    public void setSize(long size) {
        throw new RuntimeException("Stub!");
    }

    public long getSize() {
        throw new RuntimeException("Stub!");
    }

    public long getCompressedSize() {
        throw new RuntimeException("Stub!");
    }

    public void setCompressedSize(long csize) {
        throw new RuntimeException("Stub!");
    }

    public void setCrc(long crc) {
        throw new RuntimeException("Stub!");
    }

    public long getCrc() {
        throw new RuntimeException("Stub!");
    }

    public void setMethod(int method) {
        throw new RuntimeException("Stub!");
    }

    public int getMethod() {
        throw new RuntimeException("Stub!");
    }

    public void setExtra(byte[] extra) {
        throw new RuntimeException("Stub!");
    }

    void setExtra0(byte[] extra, boolean doZIP64) {
        throw new RuntimeException("Stub!");
    }

    public byte[] getExtra() {
        throw new RuntimeException("Stub!");
    }

    public void setComment(java.lang.String comment) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getComment() {
        throw new RuntimeException("Stub!");
    }

    public boolean isDirectory() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object clone() {
        throw new RuntimeException("Stub!");
    }

    public static final int DEFLATED = 8; // 0x8

    static final long DOSTIME_BEFORE_1980 = 2162688L; // 0x210000L

    public static final int STORED = 0; // 0x0

    public static final long UPPER_DOSTIME_BOUND = 4036608000000L; // 0x3abd8960000L

    java.nio.file.attribute.FileTime atime;

    java.lang.String comment;

    long crc = -1; // 0xffffffff

    long csize = -1; // 0xffffffff

    java.nio.file.attribute.FileTime ctime;

    long dataOffset;

    byte[] extra;

    int flag = 0; // 0x0

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    int method = -1; // 0xffffffff

    java.nio.file.attribute.FileTime mtime;

    java.lang.String name;

    long size = -1; // 0xffffffff

    long xdostime = -1; // 0xffffffff
}
