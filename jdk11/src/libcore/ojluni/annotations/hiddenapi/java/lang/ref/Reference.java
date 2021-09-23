/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1997, 2017, Oracle and/or its affiliates. All rights reserved.
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
public abstract class Reference<T> {

    Reference(T referent) {
        throw new RuntimeException("Stub!");
    }

    Reference(T referent, java.lang.ref.ReferenceQueue<? super T> queue) {
        throw new RuntimeException("Stub!");
    }

    public T get() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private final native T getReferent();

    public void clear() {
        throw new RuntimeException("Stub!");
    }

    native void clearReferent();

    public boolean isEnqueued() {
        throw new RuntimeException("Stub!");
    }

    public boolean enqueue() {
        throw new RuntimeException("Stub!");
    }

    public static void reachabilityFence(java.lang.Object ref) {
        throw new RuntimeException("Stub!");
    }

    private static boolean disableIntrinsic = false;

    java.lang.ref.Reference<?> pendingNext;

    final java.lang.ref.ReferenceQueue<? super T> queue;

    {
        queue = null;
    }

    java.lang.ref.Reference queueNext;

    @UnsupportedAppUsage
    volatile T referent;

    private static boolean slowPathEnabled = false;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class SinkHolder {

        private SinkHolder() {
            throw new RuntimeException("Stub!");
        }

        private static volatile int finalize_count = 0; // 0x0

        static volatile java.lang.Object sink;

        private static java.lang.Object sinkUser;
    }
}
