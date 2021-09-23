/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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
public abstract class TimerTask implements java.lang.Runnable {

    protected TimerTask() {
        throw new RuntimeException("Stub!");
    }

    public abstract void run();

    public boolean cancel() {
        throw new RuntimeException("Stub!");
    }

    public long scheduledExecutionTime() {
        throw new RuntimeException("Stub!");
    }

    static final int CANCELLED = 3; // 0x3

    static final int EXECUTED = 2; // 0x2

    static final int SCHEDULED = 1; // 0x1

    static final int VIRGIN = 0; // 0x0

    final java.lang.Object lock;

    {
        lock = null;
    }

    long nextExecutionTime;

    @UnsupportedAppUsage
    long period = 0; // 0x0

    int state = 0; // 0x0
}
