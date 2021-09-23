/*
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

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class ThreadPoolExecutor extends java.util.concurrent.AbstractExecutorService {

    public ThreadPoolExecutor(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            java.util.concurrent.TimeUnit unit,
            java.util.concurrent.BlockingQueue<java.lang.Runnable> workQueue) {
        throw new RuntimeException("Stub!");
    }

    public ThreadPoolExecutor(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            java.util.concurrent.TimeUnit unit,
            java.util.concurrent.BlockingQueue<java.lang.Runnable> workQueue,
            java.util.concurrent.ThreadFactory threadFactory) {
        throw new RuntimeException("Stub!");
    }

    public ThreadPoolExecutor(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            java.util.concurrent.TimeUnit unit,
            java.util.concurrent.BlockingQueue<java.lang.Runnable> workQueue,
            java.util.concurrent.RejectedExecutionHandler handler) {
        throw new RuntimeException("Stub!");
    }

    public ThreadPoolExecutor(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            java.util.concurrent.TimeUnit unit,
            java.util.concurrent.BlockingQueue<java.lang.Runnable> workQueue,
            java.util.concurrent.ThreadFactory threadFactory,
            java.util.concurrent.RejectedExecutionHandler handler) {
        throw new RuntimeException("Stub!");
    }

    private static int runStateOf(int c) {
        throw new RuntimeException("Stub!");
    }

    private static int workerCountOf(int c) {
        throw new RuntimeException("Stub!");
    }

    private static int ctlOf(int rs, int wc) {
        throw new RuntimeException("Stub!");
    }

    private static boolean runStateLessThan(int c, int s) {
        throw new RuntimeException("Stub!");
    }

    private static boolean runStateAtLeast(int c, int s) {
        throw new RuntimeException("Stub!");
    }

    private static boolean isRunning(int c) {
        throw new RuntimeException("Stub!");
    }

    private boolean compareAndIncrementWorkerCount(int expect) {
        throw new RuntimeException("Stub!");
    }

    private boolean compareAndDecrementWorkerCount(int expect) {
        throw new RuntimeException("Stub!");
    }

    private void decrementWorkerCount() {
        throw new RuntimeException("Stub!");
    }

    private void advanceRunState(int targetState) {
        throw new RuntimeException("Stub!");
    }

    final void tryTerminate() {
        throw new RuntimeException("Stub!");
    }

    private void checkShutdownAccess() {
        throw new RuntimeException("Stub!");
    }

    private void interruptWorkers() {
        throw new RuntimeException("Stub!");
    }

    private void interruptIdleWorkers(boolean onlyOne) {
        throw new RuntimeException("Stub!");
    }

    private void interruptIdleWorkers() {
        throw new RuntimeException("Stub!");
    }

    final void reject(java.lang.Runnable command) {
        throw new RuntimeException("Stub!");
    }

    void onShutdown() {
        throw new RuntimeException("Stub!");
    }

    final boolean isRunningOrShutdown(boolean shutdownOK) {
        throw new RuntimeException("Stub!");
    }

    private java.util.List<java.lang.Runnable> drainQueue() {
        throw new RuntimeException("Stub!");
    }

    private boolean addWorker(java.lang.Runnable firstTask, boolean core) {
        throw new RuntimeException("Stub!");
    }

    private void addWorkerFailed(java.util.concurrent.ThreadPoolExecutor.Worker w) {
        throw new RuntimeException("Stub!");
    }

    private void processWorkerExit(
            java.util.concurrent.ThreadPoolExecutor.Worker w, boolean completedAbruptly) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Runnable getTask() {
        throw new RuntimeException("Stub!");
    }

    final void runWorker(java.util.concurrent.ThreadPoolExecutor.Worker w) {
        throw new RuntimeException("Stub!");
    }

    public void execute(java.lang.Runnable command) {
        throw new RuntimeException("Stub!");
    }

    public void shutdown() {
        throw new RuntimeException("Stub!");
    }

    public java.util.List<java.lang.Runnable> shutdownNow() {
        throw new RuntimeException("Stub!");
    }

    public boolean isShutdown() {
        throw new RuntimeException("Stub!");
    }

    public boolean isTerminating() {
        throw new RuntimeException("Stub!");
    }

    public boolean isTerminated() {
        throw new RuntimeException("Stub!");
    }

    public boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit)
            throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    protected void finalize() {
        throw new RuntimeException("Stub!");
    }

    public void setThreadFactory(java.util.concurrent.ThreadFactory threadFactory) {
        throw new RuntimeException("Stub!");
    }

    public java.util.concurrent.ThreadFactory getThreadFactory() {
        throw new RuntimeException("Stub!");
    }

    public void setRejectedExecutionHandler(java.util.concurrent.RejectedExecutionHandler handler) {
        throw new RuntimeException("Stub!");
    }

    public java.util.concurrent.RejectedExecutionHandler getRejectedExecutionHandler() {
        throw new RuntimeException("Stub!");
    }

    public void setCorePoolSize(int corePoolSize) {
        throw new RuntimeException("Stub!");
    }

    public int getCorePoolSize() {
        throw new RuntimeException("Stub!");
    }

    public boolean prestartCoreThread() {
        throw new RuntimeException("Stub!");
    }

    void ensurePrestart() {
        throw new RuntimeException("Stub!");
    }

    public int prestartAllCoreThreads() {
        throw new RuntimeException("Stub!");
    }

    public boolean allowsCoreThreadTimeOut() {
        throw new RuntimeException("Stub!");
    }

    public void allowCoreThreadTimeOut(boolean value) {
        throw new RuntimeException("Stub!");
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        throw new RuntimeException("Stub!");
    }

    public int getMaximumPoolSize() {
        throw new RuntimeException("Stub!");
    }

    public void setKeepAliveTime(long time, java.util.concurrent.TimeUnit unit) {
        throw new RuntimeException("Stub!");
    }

    public long getKeepAliveTime(java.util.concurrent.TimeUnit unit) {
        throw new RuntimeException("Stub!");
    }

    public java.util.concurrent.BlockingQueue<java.lang.Runnable> getQueue() {
        throw new RuntimeException("Stub!");
    }

    public boolean remove(java.lang.Runnable task) {
        throw new RuntimeException("Stub!");
    }

    public void purge() {
        throw new RuntimeException("Stub!");
    }

    public int getPoolSize() {
        throw new RuntimeException("Stub!");
    }

    public int getActiveCount() {
        throw new RuntimeException("Stub!");
    }

    public int getLargestPoolSize() {
        throw new RuntimeException("Stub!");
    }

    public long getTaskCount() {
        throw new RuntimeException("Stub!");
    }

    public long getCompletedTaskCount() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    protected void beforeExecute(java.lang.Thread t, java.lang.Runnable r) {
        throw new RuntimeException("Stub!");
    }

    protected void afterExecute(java.lang.Runnable r, java.lang.Throwable t) {
        throw new RuntimeException("Stub!");
    }

    protected void terminated() {
        throw new RuntimeException("Stub!");
    }

    private static final int CAPACITY = 536870911; // 0x1fffffff

    private static final int COUNT_BITS = 29; // 0x1d

    private static final boolean ONLY_ONE = true;

    private static final int RUNNING = -536870912; // 0xe0000000

    private static final int SHUTDOWN = 0; // 0x0

    private static final int STOP = 536870912; // 0x20000000

    private static final int TERMINATED = 1610612736; // 0x60000000

    private static final int TIDYING = 1073741824; // 0x40000000

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private volatile boolean allowCoreThreadTimeOut;

    private long completedTaskCount;

    private volatile int corePoolSize;

    @UnsupportedAppUsage
    private final java.util.concurrent.atomic.AtomicInteger ctl;

    {
        ctl = null;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private static final java.util.concurrent.RejectedExecutionHandler defaultHandler;

    static {
        defaultHandler = null;
    }

    private volatile java.util.concurrent.RejectedExecutionHandler handler;

    private volatile long keepAliveTime;

    private int largestPoolSize;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private final java.util.concurrent.locks.ReentrantLock mainLock;

    {
        mainLock = null;
    }

    private volatile int maximumPoolSize;

    private static final java.lang.RuntimePermission shutdownPerm;

    static {
        shutdownPerm = null;
    }

    private final java.util.concurrent.locks.Condition termination;

    {
        termination = null;
    }

    private volatile java.util.concurrent.ThreadFactory threadFactory;

    private final java.util.concurrent.BlockingQueue<java.lang.Runnable> workQueue;

    {
        workQueue = null;
    }

    private final java.util.HashSet<java.util.concurrent.ThreadPoolExecutor.Worker> workers;

    {
        workers = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static class AbortPolicy implements java.util.concurrent.RejectedExecutionHandler {

        public AbortPolicy() {
            throw new RuntimeException("Stub!");
        }

        public void rejectedExecution(
                java.lang.Runnable r, java.util.concurrent.ThreadPoolExecutor e) {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static class CallerRunsPolicy implements java.util.concurrent.RejectedExecutionHandler {

        public CallerRunsPolicy() {
            throw new RuntimeException("Stub!");
        }

        public void rejectedExecution(
                java.lang.Runnable r, java.util.concurrent.ThreadPoolExecutor e) {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static class DiscardOldestPolicy
            implements java.util.concurrent.RejectedExecutionHandler {

        public DiscardOldestPolicy() {
            throw new RuntimeException("Stub!");
        }

        public void rejectedExecution(
                java.lang.Runnable r, java.util.concurrent.ThreadPoolExecutor e) {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static class DiscardPolicy implements java.util.concurrent.RejectedExecutionHandler {

        public DiscardPolicy() {
            throw new RuntimeException("Stub!");
        }

        public void rejectedExecution(
                java.lang.Runnable r, java.util.concurrent.ThreadPoolExecutor e) {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private final class Worker extends java.util.concurrent.locks.AbstractQueuedSynchronizer
            implements java.lang.Runnable {

        Worker(java.lang.Runnable firstTask) {
            throw new RuntimeException("Stub!");
        }

        public void run() {
            throw new RuntimeException("Stub!");
        }

        protected boolean isHeldExclusively() {
            throw new RuntimeException("Stub!");
        }

        protected boolean tryAcquire(int unused) {
            throw new RuntimeException("Stub!");
        }

        protected boolean tryRelease(int unused) {
            throw new RuntimeException("Stub!");
        }

        public void lock() {
            throw new RuntimeException("Stub!");
        }

        public boolean tryLock() {
            throw new RuntimeException("Stub!");
        }

        public void unlock() {
            throw new RuntimeException("Stub!");
        }

        public boolean isLocked() {
            throw new RuntimeException("Stub!");
        }

        void interruptIfStarted() {
            throw new RuntimeException("Stub!");
        }

        volatile long completedTasks;

        java.lang.Runnable firstTask;

        private static final long serialVersionUID = 6138294804551838833L; // 0x552f9a9a47f02c71L

        final java.lang.Thread thread;

        {
            thread = null;
        }
    }
}
