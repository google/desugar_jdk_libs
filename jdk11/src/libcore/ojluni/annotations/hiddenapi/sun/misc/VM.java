/*
 * Copyright (c) 1996, 2014, Oracle and/or its affiliates. All rights reserved.
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
public class VM {

    public VM() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public static boolean threadsSuspended() {
        throw new RuntimeException("Stub!");
    }

    public static boolean allowThreadSuspension(java.lang.ThreadGroup g, boolean b) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public static boolean suspendThreads() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public static void unsuspendThreads() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public static void unsuspendSomeThreads() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public static final int getState() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public static void asChange(int as_old, int as_new) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public static void asChange_otherthread(int as_old, int as_new) {
        throw new RuntimeException("Stub!");
    }

    public static void booted() {
        throw new RuntimeException("Stub!");
    }

    public static boolean isBooted() {
        throw new RuntimeException("Stub!");
    }

    public static void awaitBooted() throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static long maxDirectMemory() {
        throw new RuntimeException("Stub!");
    }

    public static boolean isDirectMemoryPageAligned() {
        throw new RuntimeException("Stub!");
    }

    public static boolean allowArraySyntax() {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String getSavedProperty(java.lang.String key) {
        throw new RuntimeException("Stub!");
    }

    public static void saveAndRemoveProperties(java.util.Properties props) {
        throw new RuntimeException("Stub!");
    }

    public static void initializeOSEnvironment() {
        throw new RuntimeException("Stub!");
    }

    public static int getFinalRefCount() {
        throw new RuntimeException("Stub!");
    }

    public static int getPeakFinalRefCount() {
        throw new RuntimeException("Stub!");
    }

    public static void addFinalRefCount(int n) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Thread.State toThreadState(int threadStatus) {
        throw new RuntimeException("Stub!");
    }

    private static final int JVMTI_THREAD_STATE_ALIVE = 1; // 0x1

    private static final int JVMTI_THREAD_STATE_BLOCKED_ON_MONITOR_ENTER = 1024; // 0x400

    private static final int JVMTI_THREAD_STATE_RUNNABLE = 4; // 0x4

    private static final int JVMTI_THREAD_STATE_TERMINATED = 2; // 0x2

    private static final int JVMTI_THREAD_STATE_WAITING_INDEFINITELY = 16; // 0x10

    private static final int JVMTI_THREAD_STATE_WAITING_WITH_TIMEOUT = 32; // 0x20

    @Deprecated public static final int STATE_GREEN = 1; // 0x1

    @Deprecated public static final int STATE_RED = 3; // 0x3

    @Deprecated public static final int STATE_YELLOW = 2; // 0x2

    private static boolean allowArraySyntax;

    private static volatile boolean booted = false;

    private static boolean defaultAllowArraySyntax = false;

    private static long directMemory = 67108864; // 0x4000000

    private static volatile int finalRefCount = 0; // 0x0

    private static final java.lang.Object lock;

    static {
        lock = null;
    }

    private static boolean pageAlignDirectMemory;

    private static volatile int peakFinalRefCount = 0; // 0x0

    private static final java.util.Properties savedProps;

    static {
        savedProps = null;
    }

    private static boolean suspended = false;
}
