/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1997, 2005, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.ref;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class ReferenceQueue<T> {

    public ReferenceQueue() {
        throw new RuntimeException("Stub!");
    }

    private boolean enqueueLocked(java.lang.ref.Reference<? extends T> r) {
        throw new RuntimeException("Stub!");
    }

    boolean isEnqueued(java.lang.ref.Reference<? extends T> reference) {
        throw new RuntimeException("Stub!");
    }

    boolean enqueue(java.lang.ref.Reference<? extends T> reference) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.ref.Reference<? extends T> reallyPollLocked() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.ref.Reference<? extends T> poll() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.ref.Reference<? extends T> remove(long timeout)
            throws java.lang.IllegalArgumentException, java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.ref.Reference<? extends T> remove() throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public static void enqueuePending(java.lang.ref.Reference<?> list) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    static void add(java.lang.ref.Reference<?> list) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.ref.Reference<? extends T> head;

    private final java.lang.Object lock;

    {
        lock = null;
    }

    private static final java.lang.ref.Reference sQueueNextUnenqueued;

    static {
        sQueueNextUnenqueued = null;
    }

    private java.lang.ref.Reference<? extends T> tail;

    public static java.lang.ref.Reference<?> unenqueued;
}
