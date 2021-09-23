/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package sun.misc;


@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class CharacterDecoder {

    @android.compat.annotation.UnsupportedAppUsage
    public CharacterDecoder() {
        throw new RuntimeException("Stub!");
    }

    protected abstract int bytesPerAtom();

    protected abstract int bytesPerLine();

    protected void decodeBufferPrefix(
            java.io.PushbackInputStream aStream, java.io.OutputStream bStream)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void decodeBufferSuffix(
            java.io.PushbackInputStream aStream, java.io.OutputStream bStream)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected int decodeLinePrefix(
            java.io.PushbackInputStream aStream, java.io.OutputStream bStream)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void decodeLineSuffix(
            java.io.PushbackInputStream aStream, java.io.OutputStream bStream)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void decodeAtom(
            java.io.PushbackInputStream aStream, java.io.OutputStream bStream, int l)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected int readFully(java.io.InputStream in, byte[] buffer, int offset, int len)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void decodeBuffer(java.io.InputStream aStream, java.io.OutputStream bStream)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public byte[] decodeBuffer(java.lang.String inputString) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public byte[] decodeBuffer(java.io.InputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.nio.ByteBuffer decodeBufferToByteBuffer(java.lang.String inputString)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.nio.ByteBuffer decodeBufferToByteBuffer(java.io.InputStream in)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }
}
