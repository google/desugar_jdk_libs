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

package java.nio;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class Buffer {

    Buffer(int mark, int pos, int lim, int cap, int elementSizeShift) {
        throw new RuntimeException("Stub!");
    }

    public final int capacity() {
        throw new RuntimeException("Stub!");
    }

    public final int position() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.Buffer position(int newPosition) {
        throw new RuntimeException("Stub!");
    }

    public final int limit() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.Buffer limit(int newLimit) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.Buffer mark() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.Buffer reset() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.Buffer clear() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.Buffer flip() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.Buffer rewind() {
        throw new RuntimeException("Stub!");
    }

    public final int remaining() {
        throw new RuntimeException("Stub!");
    }

    public final boolean hasRemaining() {
        throw new RuntimeException("Stub!");
    }

    public abstract boolean isReadOnly();

    public abstract boolean hasArray();

    public abstract java.lang.Object array();

    public abstract int arrayOffset();

    public abstract boolean isDirect();

    final int nextGetIndex() {
        throw new RuntimeException("Stub!");
    }

    final int nextGetIndex(int nb) {
        throw new RuntimeException("Stub!");
    }

    final int nextPutIndex() {
        throw new RuntimeException("Stub!");
    }

    final int nextPutIndex(int nb) {
        throw new RuntimeException("Stub!");
    }

    final int checkIndex(int i) {
        throw new RuntimeException("Stub!");
    }

    final int checkIndex(int i, int nb) {
        throw new RuntimeException("Stub!");
    }

    final int markValue() {
        throw new RuntimeException("Stub!");
    }

    final void truncate() {
        throw new RuntimeException("Stub!");
    }

    final void discardMark() {
        throw new RuntimeException("Stub!");
    }

    static void checkBounds(int off, int len, int size) {
        throw new RuntimeException("Stub!");
    }

    public int getElementSizeShift() {
        throw new RuntimeException("Stub!");
    }

    static final int SPLITERATOR_CHARACTERISTICS = 16464; // 0x4050

    @UnsupportedAppUsage
    final int _elementSizeShift;

    {
        _elementSizeShift = 0;
    }

    @UnsupportedAppUsage
    long address;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private int capacity;

    @UnsupportedAppUsage
    private int limit;

    private int mark = -1; // 0xffffffff

    @UnsupportedAppUsage
    int position = 0; // 0x0
}
