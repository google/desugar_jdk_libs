/*
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

package sun.misc;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class Cleaner extends java.lang.ref.PhantomReference<java.lang.Object> {

    private Cleaner(java.lang.Object referent, java.lang.Runnable thunk) {
        super(null, null);
        throw new RuntimeException("Stub!");
    }

    private static synchronized sun.misc.Cleaner add(sun.misc.Cleaner cl) {
        throw new RuntimeException("Stub!");
    }

    private static synchronized boolean remove(sun.misc.Cleaner cl) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public static sun.misc.Cleaner create(java.lang.Object ob, java.lang.Runnable thunk) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public void clean() {
        throw new RuntimeException("Stub!");
    }

    private static final java.lang.ref.ReferenceQueue<java.lang.Object> fakeQueue;

    static {
        fakeQueue = null;
    }

    private static sun.misc.Cleaner first;

    private sun.misc.Cleaner next;

    private sun.misc.Cleaner prev;

    private final java.lang.Runnable thunk;

    {
        thunk = null;
    }
}
