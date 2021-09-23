/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

// -- This file was mechanically generated: Do not edit! -- //

package java.nio;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class CharBuffer extends java.nio.Buffer
        implements java.lang.Comparable<java.nio.CharBuffer>,
                java.lang.Appendable,
                java.lang.CharSequence,
                java.lang.Readable {

    CharBuffer(int mark, int pos, int lim, int cap, char[] hb, int offset) {
        super(0, 0, 0, 0, 0);
        throw new RuntimeException("Stub!");
    }

    CharBuffer(int mark, int pos, int lim, int cap) {
        super(0, 0, 0, 0, 0);
        throw new RuntimeException("Stub!");
    }

    public static java.nio.CharBuffer allocate(int capacity) {
        throw new RuntimeException("Stub!");
    }

    public static java.nio.CharBuffer wrap(char[] array, int offset, int length) {
        throw new RuntimeException("Stub!");
    }

    public static java.nio.CharBuffer wrap(char[] array) {
        throw new RuntimeException("Stub!");
    }

    public int read(java.nio.CharBuffer target) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public static java.nio.CharBuffer wrap(java.lang.CharSequence csq, int start, int end) {
        throw new RuntimeException("Stub!");
    }

    public static java.nio.CharBuffer wrap(java.lang.CharSequence csq) {
        throw new RuntimeException("Stub!");
    }

    public abstract java.nio.CharBuffer slice();

    public abstract java.nio.CharBuffer duplicate();

    public abstract java.nio.CharBuffer asReadOnlyBuffer();

    public abstract char get();

    public abstract java.nio.CharBuffer put(char c);

    public abstract char get(int index);

    abstract char getUnchecked(int index);

    public abstract java.nio.CharBuffer put(int index, char c);

    public java.nio.CharBuffer get(char[] dst, int offset, int length) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.CharBuffer get(char[] dst) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.CharBuffer put(java.nio.CharBuffer src) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.CharBuffer put(char[] src, int offset, int length) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.CharBuffer put(char[] src) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.CharBuffer put(java.lang.String src, int start, int end) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.CharBuffer put(java.lang.String src) {
        throw new RuntimeException("Stub!");
    }

    public final boolean hasArray() {
        throw new RuntimeException("Stub!");
    }

    public final char[] array() {
        throw new RuntimeException("Stub!");
    }

    public final int arrayOffset() {
        throw new RuntimeException("Stub!");
    }

    public abstract java.nio.CharBuffer compact();

    public abstract boolean isDirect();

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object ob) {
        throw new RuntimeException("Stub!");
    }

    private static boolean equals(char x, char y) {
        throw new RuntimeException("Stub!");
    }

    public int compareTo(java.nio.CharBuffer that) {
        throw new RuntimeException("Stub!");
    }

    private static int compare(char x, char y) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    abstract java.lang.String toString(int start, int end);

    public final int length() {
        throw new RuntimeException("Stub!");
    }

    public final char charAt(int index) {
        throw new RuntimeException("Stub!");
    }

    public abstract java.nio.CharBuffer subSequence(int start, int end);

    public java.nio.CharBuffer append(java.lang.CharSequence csq) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.CharBuffer append(java.lang.CharSequence csq, int start, int end) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.CharBuffer append(char c) {
        throw new RuntimeException("Stub!");
    }

    public abstract java.nio.ByteOrder order();

    public java.util.stream.IntStream chars() {
        throw new RuntimeException("Stub!");
    }

    final char[] hb;

    {
        hb = new char[0];
    }

    boolean isReadOnly;

    final int offset;

    {
        offset = 0;
    }
}
