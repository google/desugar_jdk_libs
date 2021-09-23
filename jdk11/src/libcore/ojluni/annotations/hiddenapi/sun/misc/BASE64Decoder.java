/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
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
public class BASE64Decoder extends sun.misc.CharacterDecoder {

    @android.compat.annotation.UnsupportedAppUsage
    public BASE64Decoder() {
        throw new RuntimeException("Stub!");
    }

    protected int bytesPerAtom() {
        throw new RuntimeException("Stub!");
    }

    protected int bytesPerLine() {
        throw new RuntimeException("Stub!");
    }

    protected void decodeAtom(
            java.io.PushbackInputStream inStream, java.io.OutputStream outStream, int rem)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    byte[] decode_buffer;

    private static final char[] pem_array;

    static {
        pem_array = new char[0];
    }

    @android.compat.annotation.UnsupportedAppUsage private static final byte[] pem_convert_array;

    static {
        pem_convert_array = new byte[0];
    }
}
