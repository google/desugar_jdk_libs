/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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
public class Thread implements java.lang.Runnable {

    public Thread() {
        throw new RuntimeException("Stub!");
    }

    public Thread(java.lang.Runnable target) {
        throw new RuntimeException("Stub!");
    }

    Thread(java.lang.Runnable target, java.security.AccessControlContext acc) {
        throw new RuntimeException("Stub!");
    }

    public Thread(java.lang.ThreadGroup group, java.lang.Runnable target) {
        throw new RuntimeException("Stub!");
    }

    public Thread(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public Thread(java.lang.ThreadGroup group, java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    Thread(java.lang.ThreadGroup group, java.lang.String name, int priority, boolean daemon) {
        throw new RuntimeException("Stub!");
    }

    public Thread(java.lang.Runnable target, java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public Thread(java.lang.ThreadGroup group, java.lang.Runnable target, java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public Thread(
            java.lang.ThreadGroup group,
            java.lang.Runnable target,
            java.lang.String name,
            long stackSize) {
        throw new RuntimeException("Stub!");
    }

    private static synchronized int nextThreadNum() {
        throw new RuntimeException("Stub!");
    }

    private static synchronized long nextThreadID() {
        throw new RuntimeException("Stub!");
    }

    public void blockedOn(sun.nio.ch.Interruptible b) {
        throw new RuntimeException("Stub!");
    }

    public static native java.lang.Thread currentThread();

    public static native void yield();

    public static void sleep(long millis) throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    private static native void sleep(java.lang.Object lock, long millis, int nanos)
            throws java.lang.InterruptedException;

    public static void sleep(long millis, int nanos) throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    private void init(
            java.lang.ThreadGroup g,
            java.lang.Runnable target,
            java.lang.String name,
            long stackSize) {
        throw new RuntimeException("Stub!");
    }

    private void init(
            java.lang.ThreadGroup g,
            java.lang.Runnable target,
            java.lang.String name,
            long stackSize,
            java.security.AccessControlContext acc) {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Object clone() throws java.lang.CloneNotSupportedException {
        throw new RuntimeException("Stub!");
    }

    private void init2(java.lang.Thread parent) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void start() {
        throw new RuntimeException("Stub!");
    }

    private static native void nativeCreate(java.lang.Thread t, long stackSize, boolean daemon);

    public void run() {
        throw new RuntimeException("Stub!");
    }

    private void exit() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public final void stop() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public final synchronized void stop(java.lang.Throwable obj) {
        throw new RuntimeException("Stub!");
    }

    public void interrupt() {
        throw new RuntimeException("Stub!");
    }

    public static native boolean interrupted();

    public native boolean isInterrupted();

    @Deprecated
    public void destroy() {
        throw new RuntimeException("Stub!");
    }

    public final boolean isAlive() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public final void suspend() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public final void resume() {
        throw new RuntimeException("Stub!");
    }

    public final void setPriority(int newPriority) {
        throw new RuntimeException("Stub!");
    }

    public final int getPriority() {
        throw new RuntimeException("Stub!");
    }

    public final synchronized void setName(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.ThreadGroup getThreadGroup() {
        throw new RuntimeException("Stub!");
    }

    public static int activeCount() {
        throw new RuntimeException("Stub!");
    }

    public static int enumerate(java.lang.Thread[] tarray) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public int countStackFrames() {
        throw new RuntimeException("Stub!");
    }

    public final void join(long millis) throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public final void join(long millis, int nanos) throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public final void join() throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public static void dumpStack() {
        throw new RuntimeException("Stub!");
    }

    public final void setDaemon(boolean on) {
        throw new RuntimeException("Stub!");
    }

    public final boolean isDaemon() {
        throw new RuntimeException("Stub!");
    }

    public final void checkAccess() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.ClassLoader getContextClassLoader() {
        throw new RuntimeException("Stub!");
    }

    public void setContextClassLoader(java.lang.ClassLoader cl) {
        throw new RuntimeException("Stub!");
    }

    public static native boolean holdsLock(java.lang.Object obj);

    public java.lang.StackTraceElement[] getStackTrace() {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Map<java.lang.Thread, java.lang.StackTraceElement[]>
            getAllStackTraces() {
        throw new RuntimeException("Stub!");
    }

    private static boolean isCCLOverridden(java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    private static boolean auditSubclass(java.lang.Class<?> subcl) {
        throw new RuntimeException("Stub!");
    }

    public long getId() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Thread.State getState() {
        throw new RuntimeException("Stub!");
    }

    public static void setDefaultUncaughtExceptionHandler(
            java.lang.Thread.UncaughtExceptionHandler eh) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Thread.UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
        throw new RuntimeException("Stub!");
    }

    public static void setUncaughtExceptionPreHandler(
            java.lang.Thread.UncaughtExceptionHandler eh) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public static java.lang.Thread.UncaughtExceptionHandler getUncaughtExceptionPreHandler() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        throw new RuntimeException("Stub!");
    }

    public void setUncaughtExceptionHandler(java.lang.Thread.UncaughtExceptionHandler eh) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public final void dispatchUncaughtException(java.lang.Throwable e) {
        throw new RuntimeException("Stub!");
    }

    static void processQueue(
            java.lang.ref.ReferenceQueue<java.lang.Class<?>> queue,
            java.util.concurrent.ConcurrentMap<
                            ? extends java.lang.ref.WeakReference<java.lang.Class<?>>, ?>
                    map) {
        throw new RuntimeException("Stub!");
    }

    private native void setPriority0(int newPriority);

    private native void interrupt0();

    private native void setNativeName(java.lang.String name);

    private native int nativeGetStatus(boolean hasBeenStarted);

    public final void unpark$() {
        throw new RuntimeException("Stub!");
    }

    public final void parkFor$(long nanos) {
        throw new RuntimeException("Stub!");
    }

    public final void parkUntil$(long time) {
        throw new RuntimeException("Stub!");
    }

    private static final java.lang.StackTraceElement[] EMPTY_STACK_TRACE;

    static {
        EMPTY_STACK_TRACE = new java.lang.StackTraceElement[0];
    }

    public static final int MAX_PRIORITY = 10; // 0xa

    public static final int MIN_PRIORITY = 1; // 0x1

    private static final int NANOS_PER_MILLI = 1000000; // 0xf4240

    public static final int NORM_PRIORITY = 5; // 0x5

    private static final java.lang.RuntimePermission SUBCLASS_IMPLEMENTATION_PERMISSION;

    static {
        SUBCLASS_IMPLEMENTATION_PERMISSION = null;
    }

    private volatile sun.nio.ch.Interruptible blocker;

    private final java.lang.Object blockerLock;

    {
        blockerLock = null;
    }

    @UnsupportedAppUsage
    private java.lang.ClassLoader contextClassLoader;

    @UnsupportedAppUsage
    private boolean daemon = false;

    private static volatile java.lang.Thread.UncaughtExceptionHandler
            defaultUncaughtExceptionHandler;

    private long eetop;

    @UnsupportedAppUsage
    private java.lang.ThreadGroup group;

    @UnsupportedAppUsage
    java.lang.ThreadLocal.ThreadLocalMap inheritableThreadLocals;

    @UnsupportedAppUsage
    private java.security.AccessControlContext inheritedAccessControlContext;

    @UnsupportedAppUsage
    private final java.lang.Object lock;

    {
        lock = null;
    }

    @UnsupportedAppUsage
    private volatile java.lang.String name;

    private long nativeParkEventPointer;

    @UnsupportedAppUsage
    private volatile long nativePeer;

    @UnsupportedAppUsage
    volatile java.lang.Object parkBlocker;

    private int parkState = 1; // 0x1

    @UnsupportedAppUsage
    private int priority;

    private boolean single_step;

    private long stackSize;

    boolean started = false;

    private boolean stillborn = false;

    @UnsupportedAppUsage
    private java.lang.Runnable target;

    private static int threadInitNumber;

    int threadLocalRandomProbe;

    int threadLocalRandomSecondarySeed;

    long threadLocalRandomSeed;

    @UnsupportedAppUsage(publicAlternatives = "Please update to a current version of the "
        + "{@code Streamsupport} library; older versions of {@code Streamsupport} do not "
        + "support current versions of Android.")
    java.lang.ThreadLocal.ThreadLocalMap threadLocals;

    private java.lang.Thread threadQ;

    @UnsupportedAppUsage
    private static long threadSeqNumber;

    private long tid;

    private volatile java.lang.Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    @UnsupportedAppUsage
    private static volatile java.lang.Thread.UncaughtExceptionHandler uncaughtExceptionPreHandler;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class Caches {

        private Caches() {
            throw new RuntimeException("Stub!");
        }

        static final java.util.concurrent.ConcurrentMap<
                        java.lang.Thread.WeakClassKey, java.lang.Boolean>
                subclassAudits;

        static {
            subclassAudits = null;
        }

        static final java.lang.ref.ReferenceQueue<java.lang.Class<?>> subclassAuditsQueue;

        static {
            subclassAuditsQueue = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class ParkState {

        private ParkState() {
            throw new RuntimeException("Stub!");
        }

        private static final int PARKED = 3; // 0x3

        private static final int PREEMPTIVELY_UNPARKED = 2; // 0x2

        private static final int UNPARKED = 1; // 0x1
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static enum State {
        NEW,
        RUNNABLE,
        BLOCKED,
        WAITING,
        TIMED_WAITING,
        TERMINATED;

        private State() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static interface UncaughtExceptionHandler {

        public void uncaughtException(java.lang.Thread t, java.lang.Throwable e);
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class WeakClassKey extends java.lang.ref.WeakReference<java.lang.Class<?>> {

        WeakClassKey(
                java.lang.Class<?> cl, java.lang.ref.ReferenceQueue<java.lang.Class<?>> refQueue) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        private final int hash;

        {
            hash = 0;
        }
    }
}
