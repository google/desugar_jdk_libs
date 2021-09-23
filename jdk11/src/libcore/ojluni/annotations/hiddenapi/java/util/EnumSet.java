/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class EnumSet<E extends java.lang.Enum<E>> extends java.util.AbstractSet<E>
        implements java.lang.Cloneable, java.io.Serializable {

    EnumSet(java.lang.Class<E> elementType, java.lang.Enum<?>[] universe) {
        throw new RuntimeException("Stub!");
    }

    public static <E extends java.lang.Enum<E>> java.util.EnumSet<E> noneOf(
            java.lang.Class<E> elementType) {
        throw new RuntimeException("Stub!");
    }

    public static <E extends java.lang.Enum<E>> java.util.EnumSet<E> allOf(
            java.lang.Class<E> elementType) {
        throw new RuntimeException("Stub!");
    }

    abstract void addAll();

    public static <E extends java.lang.Enum<E>> java.util.EnumSet<E> copyOf(
            java.util.EnumSet<E> s) {
        throw new RuntimeException("Stub!");
    }

    public static <E extends java.lang.Enum<E>> java.util.EnumSet<E> copyOf(
            java.util.Collection<E> c) {
        throw new RuntimeException("Stub!");
    }

    public static <E extends java.lang.Enum<E>> java.util.EnumSet<E> complementOf(
            java.util.EnumSet<E> s) {
        throw new RuntimeException("Stub!");
    }

    public static <E extends java.lang.Enum<E>> java.util.EnumSet<E> of(E e) {
        throw new RuntimeException("Stub!");
    }

    public static <E extends java.lang.Enum<E>> java.util.EnumSet<E> of(E e1, E e2) {
        throw new RuntimeException("Stub!");
    }

    public static <E extends java.lang.Enum<E>> java.util.EnumSet<E> of(E e1, E e2, E e3) {
        throw new RuntimeException("Stub!");
    }

    public static <E extends java.lang.Enum<E>> java.util.EnumSet<E> of(E e1, E e2, E e3, E e4) {
        throw new RuntimeException("Stub!");
    }

    public static <E extends java.lang.Enum<E>> java.util.EnumSet<E> of(
            E e1, E e2, E e3, E e4, E e5) {
        throw new RuntimeException("Stub!");
    }

    public static <E extends java.lang.Enum<E>> java.util.EnumSet<E> of(E first, E... rest) {
        throw new RuntimeException("Stub!");
    }

    public static <E extends java.lang.Enum<E>> java.util.EnumSet<E> range(E from, E to) {
        throw new RuntimeException("Stub!");
    }

    abstract void addRange(E from, E to);

    public java.util.EnumSet<E> clone() {
        throw new RuntimeException("Stub!");
    }

    abstract void complement();

    final void typeCheck(E e) {
        throw new RuntimeException("Stub!");
    }

    private static <E extends java.lang.Enum<E>> E[] getUniverse(java.lang.Class<E> elementType) {
        throw new RuntimeException("Stub!");
    }

    java.lang.Object writeReplace() {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws java.io.InvalidObjectException {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.Enum<?>[] ZERO_LENGTH_ENUM_ARRAY;

    @UnsupportedAppUsage
    final java.lang.Class<E> elementType;

    {
        elementType = null;
    }

    final java.lang.Enum<?>[] universe;

    {
        universe = new java.lang.Enum[0];
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class SerializationProxy<E extends java.lang.Enum<E>>
            implements java.io.Serializable {

        SerializationProxy(java.util.EnumSet<E> set) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.Object readResolve() {
            throw new RuntimeException("Stub!");
        }

        private final java.lang.Class<E> elementType;

        {
            elementType = null;
        }

        private final java.lang.Enum<?>[] elements;

        {
            elements = new java.lang.Enum[0];
        }

        private static final long serialVersionUID = 362491234563181265L; // 0x507d3db7654cad1L
    }
}
