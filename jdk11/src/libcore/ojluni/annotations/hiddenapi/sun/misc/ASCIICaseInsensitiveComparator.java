/*
 * Copyright (c) 2002, 2004, Oracle and/or its affiliates. All rights reserved.
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
public class ASCIICaseInsensitiveComparator implements java.util.Comparator<java.lang.String> {

    public ASCIICaseInsensitiveComparator() {
        throw new RuntimeException("Stub!");
    }

    public int compare(java.lang.String s1, java.lang.String s2) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static int lowerCaseHashCode(java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    static boolean isLower(int ch) {
        throw new RuntimeException("Stub!");
    }

    static boolean isUpper(int ch) {
        throw new RuntimeException("Stub!");
    }

    static int toLower(int ch) {
        throw new RuntimeException("Stub!");
    }

    static int toUpper(int ch) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final java.util.Comparator<java.lang.String> CASE_INSENSITIVE_ORDER;

    static {
        CASE_INSENSITIVE_ORDER = null;
    }
}
