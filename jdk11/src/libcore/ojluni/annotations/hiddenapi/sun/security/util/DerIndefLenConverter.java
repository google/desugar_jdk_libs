/*
 * Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.util;


@SuppressWarnings({"unchecked", "deprecation", "all"})
class DerIndefLenConverter {

    @android.compat.annotation.UnsupportedAppUsage
    DerIndefLenConverter() {
        throw new RuntimeException("Stub!");
    }

    private boolean isEOC(int tag) {
        throw new RuntimeException("Stub!");
    }

    static boolean isLongForm(int lengthByte) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    static boolean isIndefinite(int lengthByte) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    private void parseTag() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    private void writeTag() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    private int parseLength() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    private void writeLengthAndValue() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeLength(int curLen) {
        throw new RuntimeException("Stub!");
    }

    private byte[] getLengthBytes(int curLen) {
        throw new RuntimeException("Stub!");
    }

    private int getNumOfLenBytes(int len) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    private void parseValue(int curLen) {
        throw new RuntimeException("Stub!");
    }

    private void writeValue(int curLen) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    byte[] convert(byte[] indefData) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static final int CLASS_MASK = 192; // 0xc0

    private static final int FORM_MASK = 32; // 0x20

    private static final int LEN_LONG = 128; // 0x80

    private static final int LEN_MASK = 127; // 0x7f

    private static final int SKIP_EOC_BYTES = 2; // 0x2

    private static final int TAG_MASK = 31; // 0x1f

    @android.compat.annotation.UnsupportedAppUsage private byte[] data;

    @android.compat.annotation.UnsupportedAppUsage private int dataPos;

    @android.compat.annotation.UnsupportedAppUsage private int dataSize;

    private int index;

    private java.util.ArrayList<java.lang.Object> ndefsList;

    @android.compat.annotation.UnsupportedAppUsage private byte[] newData;

    private int newDataPos;

    @android.compat.annotation.UnsupportedAppUsage private int numOfTotalLenBytes = 0; // 0x0

    private int unresolved = 0; // 0x0
}
