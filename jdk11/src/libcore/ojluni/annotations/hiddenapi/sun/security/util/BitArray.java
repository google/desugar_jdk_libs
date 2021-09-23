/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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
public class BitArray {

    public BitArray(int length) throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public BitArray(int length, byte[] a) throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    public BitArray(boolean[] bits) {
        throw new RuntimeException("Stub!");
    }

    private BitArray(sun.security.util.BitArray ba) {
        throw new RuntimeException("Stub!");
    }

    private static int subscript(int idx) {
        throw new RuntimeException("Stub!");
    }

    private static int position(int idx) {
        throw new RuntimeException("Stub!");
    }

    public boolean get(int index) throws java.lang.ArrayIndexOutOfBoundsException {
        throw new RuntimeException("Stub!");
    }

    public void set(int index, boolean value) throws java.lang.ArrayIndexOutOfBoundsException {
        throw new RuntimeException("Stub!");
    }

    public int length() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public byte[] toByteArray() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public boolean[] toBooleanArray() {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object clone() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.util.BitArray truncate() {
        throw new RuntimeException("Stub!");
    }

    private static final int BITS_PER_UNIT = 8; // 0x8

    private static final int BYTES_PER_LINE = 8; // 0x8

    private static final byte[][] NYBBLE;

    static {
        NYBBLE = new byte[0][];
    }

    private int length;

    private byte[] repn;
}
