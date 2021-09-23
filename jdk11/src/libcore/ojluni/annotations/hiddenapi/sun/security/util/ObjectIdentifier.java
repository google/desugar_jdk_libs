/*
 * Copyright (c) 1996, 2006, Oracle and/or its affiliates. All rights reserved.
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

import java.io.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class ObjectIdentifier implements java.io.Serializable {

    @android.compat.annotation.UnsupportedAppUsage
    public ObjectIdentifier(java.lang.String oid) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public ObjectIdentifier(int[] values) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public ObjectIdentifier(sun.security.util.DerInputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    ObjectIdentifier(sun.security.util.DerInputBuffer buf) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream is)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeObject(java.io.ObjectOutputStream os) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void init(int[] components, int length) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static sun.security.util.ObjectIdentifier newInternal(int[] values) {
        throw new RuntimeException("Stub!");
    }

    void encode(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    @android.compat.annotation.UnsupportedAppUsage
    public boolean equals(sun.security.util.ObjectIdentifier other) {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public int[] toIntArray() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    private static byte[] pack(byte[] in, int ioffset, int ilength, int iw, int ow) {
        throw new RuntimeException("Stub!");
    }

    private static int pack7Oid(byte[] in, int ioffset, int ilength, byte[] out, int ooffset) {
        throw new RuntimeException("Stub!");
    }

    private static int pack8(byte[] in, int ioffset, int ilength, byte[] out, int ooffset) {
        throw new RuntimeException("Stub!");
    }

    private static int pack7Oid(int input, byte[] out, int ooffset) {
        throw new RuntimeException("Stub!");
    }

    private static int pack7Oid(java.math.BigInteger input, byte[] out, int ooffset) {
        throw new RuntimeException("Stub!");
    }

    private static void check(byte[] encoding) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static void checkCount(int count) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static void checkFirstComponent(int first) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static void checkFirstComponent(java.math.BigInteger first) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static void checkSecondComponent(int first, int second) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static void checkSecondComponent(int first, java.math.BigInteger second)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static void checkOtherComponent(int i, int num) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static void checkOtherComponent(int i, java.math.BigInteger num)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private int componentLen = -1; // 0xffffffff

    private java.lang.Object components;

    private transient boolean componentsCalculated = false;

    private byte[] encoding;

    private static final long serialVersionUID = 8697030238860181294L; // 0x78b20eec64177f2eL

    private transient volatile java.lang.String stringForm;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class HugeOidNotSupportedByOldJDK implements java.io.Serializable {

        HugeOidNotSupportedByOldJDK() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = 1L; // 0x1L

        static sun.security.util.ObjectIdentifier.HugeOidNotSupportedByOldJDK theOne;
    }
}
