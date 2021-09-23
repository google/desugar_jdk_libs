/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1994, 2012, Oracle and/or its affiliates. All rights reserved.
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
public class Object {

    public Object() {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.Class<?> getClass() {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    static int identityHashCode(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    private static native int identityHashCodeNative(java.lang.Object obj);

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Object clone() throws java.lang.CloneNotSupportedException {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.Object internalClone();

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public final native void notify();

    public final native void notifyAll();

    public final void wait(long timeout) throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public final native void wait(long timeout, int nanos) throws java.lang.InterruptedException;

    public final void wait() throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    protected void finalize() throws java.lang.Throwable {
        throw new RuntimeException("Stub!");
    }

    private transient java.lang.Class<?> shadow$_klass_;

    private transient int shadow$_monitor_;
}
