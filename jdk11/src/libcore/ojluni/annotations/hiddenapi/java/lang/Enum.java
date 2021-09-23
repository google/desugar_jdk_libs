/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class Enum<E extends java.lang.Enum<E>>
        implements java.lang.Comparable<E>, java.io.Serializable {

    protected Enum(java.lang.String name, int ordinal) {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.String name() {
        throw new RuntimeException("Stub!");
    }

    public final int ordinal() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public final boolean equals(java.lang.Object other) {
        throw new RuntimeException("Stub!");
    }

    public final int hashCode() {
        throw new RuntimeException("Stub!");
    }

    protected final java.lang.Object clone() throws java.lang.CloneNotSupportedException {
        throw new RuntimeException("Stub!");
    }

    public final int compareTo(E o) {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.Class<E> getDeclaringClass() {
        throw new RuntimeException("Stub!");
    }

    public static <T extends java.lang.Enum<T>> T valueOf(
            java.lang.Class<T> enumType, java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.Object[] enumValues(java.lang.Class<? extends java.lang.Enum> clazz) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static <T extends java.lang.Enum<T>> T[] getSharedConstants(
            java.lang.Class<T> enumType) {
        throw new RuntimeException("Stub!");
    }

    protected final void finalize() {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream in)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObjectNoData() throws java.io.ObjectStreamException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private final java.lang.String name;

    {
        name = null;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private final int ordinal;

    {
        ordinal = 0;
    }

    private static final libcore.util.BasicLruCache<
                    java.lang.Class<? extends java.lang.Enum>, java.lang.Object[]>
            sharedConstantsCache;

    static {
        sharedConstantsCache = null;
    }
}
