/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
public class Deflater {

    public Deflater(int level, boolean nowrap) {
        throw new RuntimeException("Stub!");
    }

    public Deflater(int level) {
        throw new RuntimeException("Stub!");
    }

    public Deflater() {
        throw new RuntimeException("Stub!");
    }

    public void setInput(byte[] b, int off, int len) {
        throw new RuntimeException("Stub!");
    }

    public void setInput(byte[] b) {
        throw new RuntimeException("Stub!");
    }

    public void setDictionary(byte[] b, int off, int len) {
        throw new RuntimeException("Stub!");
    }

    public void setDictionary(byte[] b) {
        throw new RuntimeException("Stub!");
    }

    public void setStrategy(int strategy) {
        throw new RuntimeException("Stub!");
    }

    public void setLevel(int level) {
        throw new RuntimeException("Stub!");
    }

    public boolean needsInput() {
        throw new RuntimeException("Stub!");
    }

    public void finish() {
        throw new RuntimeException("Stub!");
    }

    public boolean finished() {
        throw new RuntimeException("Stub!");
    }

    public int deflate(byte[] b, int off, int len) {
        throw new RuntimeException("Stub!");
    }

    public int deflate(byte[] b) {
        throw new RuntimeException("Stub!");
    }

    public int deflate(byte[] b, int off, int len, int flush) {
        throw new RuntimeException("Stub!");
    }

    public int getAdler() {
        throw new RuntimeException("Stub!");
    }

    public int getTotalIn() {
        throw new RuntimeException("Stub!");
    }

    public long getBytesRead() {
        throw new RuntimeException("Stub!");
    }

    public int getTotalOut() {
        throw new RuntimeException("Stub!");
    }

    public long getBytesWritten() {
        throw new RuntimeException("Stub!");
    }

    public void reset() {
        throw new RuntimeException("Stub!");
    }

    public void end() {
        throw new RuntimeException("Stub!");
    }

    protected void finalize() {
        throw new RuntimeException("Stub!");
    }

    private void ensureOpen() {
        throw new RuntimeException("Stub!");
    }

    private static native long init(int level, int strategy, boolean nowrap);

    private static native void setDictionary(long addr, byte[] b, int off, int len);

    private native int deflateBytes(long addr, byte[] b, int off, int len, int flush);

    private static native int getAdler(long addr);

    private static native void reset(long addr);

    private static native void end(long addr);

    public static final int BEST_COMPRESSION = 9; // 0x9

    public static final int BEST_SPEED = 1; // 0x1

    public static final int DEFAULT_COMPRESSION = -1; // 0xffffffff

    public static final int DEFAULT_STRATEGY = 0; // 0x0

    public static final int DEFLATED = 8; // 0x8

    public static final int FILTERED = 1; // 0x1

    public static final int FULL_FLUSH = 3; // 0x3

    public static final int HUFFMAN_ONLY = 2; // 0x2

    public static final int NO_COMPRESSION = 0; // 0x0

    public static final int NO_FLUSH = 0; // 0x0

    public static final int SYNC_FLUSH = 2; // 0x2

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private byte[] buf;

    private long bytesRead;

    private long bytesWritten;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private boolean finish;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private boolean finished;

    private final dalvik.system.CloseGuard guard;

    {
        guard = null;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private int len;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private int level;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private int off;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private boolean setParams;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private int strategy;

    private final java.util.zip.ZStreamRef zsRef;

    {
        zsRef = null;
    }
}
