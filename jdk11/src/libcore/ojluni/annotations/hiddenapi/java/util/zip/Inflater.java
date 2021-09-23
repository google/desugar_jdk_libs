/*
 * Copyright (C) 2014 The Android Open Source Project
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
public class Inflater {

    public Inflater(boolean nowrap) {
        throw new RuntimeException("Stub!");
    }

    public Inflater() {
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

    public int getRemaining() {
        throw new RuntimeException("Stub!");
    }

    public boolean needsInput() {
        throw new RuntimeException("Stub!");
    }

    public boolean needsDictionary() {
        throw new RuntimeException("Stub!");
    }

    public boolean finished() {
        throw new RuntimeException("Stub!");
    }

    public int inflate(byte[] b, int off, int len) throws java.util.zip.DataFormatException {
        throw new RuntimeException("Stub!");
    }

    public int inflate(byte[] b) throws java.util.zip.DataFormatException {
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

    boolean ended() {
        throw new RuntimeException("Stub!");
    }

    private static native long init(boolean nowrap);

    private static native void setDictionary(long addr, byte[] b, int off, int len);

    private native int inflateBytes(long addr, byte[] b, int off, int len)
            throws java.util.zip.DataFormatException;

    private static native int getAdler(long addr);

    private static native void reset(long addr);

    private static native void end(long addr);

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private byte[] buf;

    private long bytesRead;

    private long bytesWritten;

    private static final byte[] defaultBuf;

    static {
        defaultBuf = new byte[0];
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private boolean finished;

    private final dalvik.system.CloseGuard guard;

    {
        guard = null;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private int len;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private boolean needDict;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private int off;

    private final java.util.zip.ZStreamRef zsRef;

    {
        zsRef = null;
    }
}
