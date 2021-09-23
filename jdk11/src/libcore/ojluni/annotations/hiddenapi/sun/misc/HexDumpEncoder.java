/*
 * Copyright (c) 1995, 1997, Oracle and/or its affiliates. All rights reserved.
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
public class HexDumpEncoder extends sun.misc.CharacterEncoder {

    @android.compat.annotation.UnsupportedAppUsage
    public HexDumpEncoder() {
        throw new RuntimeException("Stub!");
    }

    static void hexDigit(java.io.PrintStream p, byte x) {
        throw new RuntimeException("Stub!");
    }

    protected int bytesPerAtom() {
        throw new RuntimeException("Stub!");
    }

    protected int bytesPerLine() {
        throw new RuntimeException("Stub!");
    }

    protected void encodeBufferPrefix(java.io.OutputStream o) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void encodeLinePrefix(java.io.OutputStream o, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void encodeAtom(java.io.OutputStream o, byte[] buf, int off, int len)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void encodeLineSuffix(java.io.OutputStream o) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage private int currentByte;

    @android.compat.annotation.UnsupportedAppUsage private int offset;

    @android.compat.annotation.UnsupportedAppUsage private byte[] thisLine;

    @android.compat.annotation.UnsupportedAppUsage private int thisLineLength;
}
