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
public class Adler32 implements java.util.zip.Checksum {

    public Adler32() {
        throw new RuntimeException("Stub!");
    }

    public void update(int b) {
        throw new RuntimeException("Stub!");
    }

    public void update(byte[] b, int off, int len) {
        throw new RuntimeException("Stub!");
    }

    public void update(byte[] b) {
        throw new RuntimeException("Stub!");
    }

    public void update(java.nio.ByteBuffer buffer) {
        throw new RuntimeException("Stub!");
    }

    public void reset() {
        throw new RuntimeException("Stub!");
    }

    public long getValue() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private static native int update(int adler, int b);

    private static native int updateBytes(int adler, byte[] b, int off, int len);

    private static native int updateByteBuffer(int adler, long addr, int off, int len);

    private int adler = 1; // 0x1
}
