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
public class Executors {

    private Executors() {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ExecutorService newFixedThreadPool(int nThreads) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ExecutorService newWorkStealingPool(int parallelism) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ExecutorService newWorkStealingPool() {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ExecutorService newFixedThreadPool(
            int nThreads, java.util.concurrent.ThreadFactory threadFactory) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ExecutorService newSingleThreadExecutor() {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ExecutorService newSingleThreadExecutor(
            java.util.concurrent.ThreadFactory threadFactory) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ExecutorService newCachedThreadPool() {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ExecutorService newCachedThreadPool(
            java.util.concurrent.ThreadFactory threadFactory) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ScheduledExecutorService newSingleThreadScheduledExecutor() {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ScheduledExecutorService newSingleThreadScheduledExecutor(
            java.util.concurrent.ThreadFactory threadFactory) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ScheduledExecutorService newScheduledThreadPool(
            int corePoolSize) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ScheduledExecutorService newScheduledThreadPool(
            int corePoolSize, java.util.concurrent.ThreadFactory threadFactory) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ExecutorService unconfigurableExecutorService(
            java.util.concurrent.ExecutorService executor) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ScheduledExecutorService
            unconfigurableScheduledExecutorService(
                    java.util.concurrent.ScheduledExecutorService executor) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ThreadFactory defaultThreadFactory() {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.ThreadFactory privilegedThreadFactory() {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.concurrent.Callable<T> callable(java.lang.Runnable task, T result) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.Callable<java.lang.Object> callable(
            java.lang.Runnable task) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.Callable<java.lang.Object> callable(
            java.security.PrivilegedAction<?> action) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.concurrent.Callable<java.lang.Object> callable(
            java.security.PrivilegedExceptionAction<?> action) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.concurrent.Callable<T> privilegedCallable(
            java.util.concurrent.Callable<T> callable) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.concurrent.Callable<T> privilegedCallableUsingCurrentClassLoader(
            java.util.concurrent.Callable<T> callable) {
        throw new RuntimeException("Stub!");
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class DefaultThreadFactory implements java.util.concurrent.ThreadFactory {

        DefaultThreadFactory() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Thread newThread(java.lang.Runnable r) {
            throw new RuntimeException("Stub!");
        }

        private final java.lang.ThreadGroup group;

        {
            group = null;
        }

        private final java.lang.String namePrefix;

        {
            namePrefix = null;
        }

        private static final java.util.concurrent.atomic.AtomicInteger poolNumber;

        static {
            poolNumber = null;
        }

        private final java.util.concurrent.atomic.AtomicInteger threadNumber;

        {
            threadNumber = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class DelegatedExecutorService
            extends java.util.concurrent.AbstractExecutorService {

        DelegatedExecutorService(java.util.concurrent.ExecutorService executor) {
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

        public boolean isTerminated() {
            throw new RuntimeException("Stub!");
        }

        public boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit)
                throws java.lang.InterruptedException {
            throw new RuntimeException("Stub!");
        }

        public java.util.concurrent.Future<?> submit(java.lang.Runnable task) {
            throw new RuntimeException("Stub!");
        }

        public <T> java.util.concurrent.Future<T> submit(java.util.concurrent.Callable<T> task) {
            throw new RuntimeException("Stub!");
        }

        public <T> java.util.concurrent.Future<T> submit(java.lang.Runnable task, T result) {
            throw new RuntimeException("Stub!");
        }

        public <T> java.util.List<java.util.concurrent.Future<T>> invokeAll(
                java.util.Collection<? extends java.util.concurrent.Callable<T>> tasks)
                throws java.lang.InterruptedException {
            throw new RuntimeException("Stub!");
        }

        public <T> java.util.List<java.util.concurrent.Future<T>> invokeAll(
                java.util.Collection<? extends java.util.concurrent.Callable<T>> tasks,
                long timeout,
                java.util.concurrent.TimeUnit unit)
                throws java.lang.InterruptedException {
            throw new RuntimeException("Stub!");
        }

        public <T> T invokeAny(
                java.util.Collection<? extends java.util.concurrent.Callable<T>> tasks)
                throws java.util.concurrent.ExecutionException, java.lang.InterruptedException {
            throw new RuntimeException("Stub!");
        }

        public <T> T invokeAny(
                java.util.Collection<? extends java.util.concurrent.Callable<T>> tasks,
                long timeout,
                java.util.concurrent.TimeUnit unit)
                throws java.util.concurrent.ExecutionException, java.lang.InterruptedException,
                        java.util.concurrent.TimeoutException {
            throw new RuntimeException("Stub!");
        }

        private final java.util.concurrent.ExecutorService e;

        {
            e = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class DelegatedScheduledExecutorService
            extends java.util.concurrent.Executors.DelegatedExecutorService
            implements java.util.concurrent.ScheduledExecutorService {

        DelegatedScheduledExecutorService(java.util.concurrent.ScheduledExecutorService executor) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        public java.util.concurrent.ScheduledFuture<?> schedule(
                java.lang.Runnable command, long delay, java.util.concurrent.TimeUnit unit) {
            throw new RuntimeException("Stub!");
        }

        public <V> java.util.concurrent.ScheduledFuture<V> schedule(
                java.util.concurrent.Callable<V> callable,
                long delay,
                java.util.concurrent.TimeUnit unit) {
            throw new RuntimeException("Stub!");
        }

        public java.util.concurrent.ScheduledFuture<?> scheduleAtFixedRate(
                java.lang.Runnable command,
                long initialDelay,
                long period,
                java.util.concurrent.TimeUnit unit) {
            throw new RuntimeException("Stub!");
        }

        public java.util.concurrent.ScheduledFuture<?> scheduleWithFixedDelay(
                java.lang.Runnable command,
                long initialDelay,
                long delay,
                java.util.concurrent.TimeUnit unit) {
            throw new RuntimeException("Stub!");
        }

        private final java.util.concurrent.ScheduledExecutorService e;

        {
            e = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class FinalizableDelegatedExecutorService
            extends java.util.concurrent.Executors.DelegatedExecutorService {

        FinalizableDelegatedExecutorService(java.util.concurrent.ExecutorService executor) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        protected void finalize() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static final class PrivilegedCallable<T> implements java.util.concurrent.Callable<T> {

        PrivilegedCallable(java.util.concurrent.Callable<T> task) {
            throw new RuntimeException("Stub!");
        }

        public T call() throws java.lang.Exception {
            throw new RuntimeException("Stub!");
        }

        final java.security.AccessControlContext acc;

        {
            acc = null;
        }

        final java.util.concurrent.Callable<T> task;

        {
            task = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static final class PrivilegedCallableUsingCurrentClassLoader<T>
            implements java.util.concurrent.Callable<T> {

        PrivilegedCallableUsingCurrentClassLoader(java.util.concurrent.Callable<T> task) {
            throw new RuntimeException("Stub!");
        }

        public T call() throws java.lang.Exception {
            throw new RuntimeException("Stub!");
        }

        final java.security.AccessControlContext acc;

        {
            acc = null;
        }

        final java.lang.ClassLoader ccl;

        {
            ccl = null;
        }

        final java.util.concurrent.Callable<T> task;

        {
            task = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class PrivilegedThreadFactory
            extends java.util.concurrent.Executors.DefaultThreadFactory {

        PrivilegedThreadFactory() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Thread newThread(java.lang.Runnable r) {
            throw new RuntimeException("Stub!");
        }

        final java.security.AccessControlContext acc;

        {
            acc = null;
        }

        final java.lang.ClassLoader ccl;

        {
            ccl = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static final class RunnableAdapter<T> implements java.util.concurrent.Callable<T> {

        RunnableAdapter(java.lang.Runnable task, T result) {
            throw new RuntimeException("Stub!");
        }

        public T call() {
            throw new RuntimeException("Stub!");
        }

        private final T result;

        {
            result = null;
        }

        @UnsupportedAppUsage
        private final java.lang.Runnable task;

        {
            task = null;
        }
    }
}
