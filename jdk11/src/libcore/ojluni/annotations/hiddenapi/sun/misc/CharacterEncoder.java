/*
 * Copyright (c) 1995, 2005, Oracle and/or its affiliates. All rights reserved.
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
public abstract class CharacterEncoder {

    @android.compat.annotation.UnsupportedAppUsage
    public CharacterEncoder() {
        throw new RuntimeException("Stub!");
    }

    protected abstract int bytesPerAtom();

    protected abstract int bytesPerLine();

    @android.compat.annotation.UnsupportedAppUsage
    protected void encodeBufferPrefix(java.io.OutputStream aStream) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void encodeBufferSuffix(java.io.OutputStream aStream) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void encodeLinePrefix(java.io.OutputStream aStream, int aLength)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void encodeLineSuffix(java.io.OutputStream aStream) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected abstract void encodeAtom(
            java.io.OutputStream aStream, byte[] someBytes, int anOffset, int aLength)
            throws java.io.IOException;

    protected int readFully(java.io.InputStream in, byte[] buffer) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void encode(java.io.InputStream inStream, java.io.OutputStream outStream)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void encode(byte[] aBuffer, java.io.OutputStream aStream) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.lang.String encode(byte[] aBuffer) {
        throw new RuntimeException("Stub!");
    }

    private byte[] getBytes(java.nio.ByteBuffer bb) {
        throw new RuntimeException("Stub!");
    }

    public void encode(java.nio.ByteBuffer aBuffer, java.io.OutputStream aStream)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String encode(java.nio.ByteBuffer aBuffer) {
        throw new RuntimeException("Stub!");
    }

    public void encodeBuffer(java.io.InputStream inStream, java.io.OutputStream outStream)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void encodeBuffer(byte[] aBuffer, java.io.OutputStream aStream)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.lang.String encodeBuffer(byte[] aBuffer) {
        throw new RuntimeException("Stub!");
    }

    public void encodeBuffer(java.nio.ByteBuffer aBuffer, java.io.OutputStream aStream)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String encodeBuffer(java.nio.ByteBuffer aBuffer) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    protected java.io.PrintStream pStream;
}
