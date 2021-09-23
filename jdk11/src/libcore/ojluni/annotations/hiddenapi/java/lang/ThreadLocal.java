/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
public class ThreadLocal<T> {

    public ThreadLocal() {
        throw new RuntimeException("Stub!");
    }

    private static int nextHashCode() {
        throw new RuntimeException("Stub!");
    }

    protected T initialValue() {
        throw new RuntimeException("Stub!");
    }

    public static <S> java.lang.ThreadLocal<S> withInitial(
            java.util.function.Supplier<? extends S> supplier) {
        throw new RuntimeException("Stub!");
    }

    public T get() {
        throw new RuntimeException("Stub!");
    }

    private T setInitialValue() {
        throw new RuntimeException("Stub!");
    }

    public void set(T value) {
        throw new RuntimeException("Stub!");
    }

    public void remove() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    java.lang.ThreadLocal.ThreadLocalMap getMap(java.lang.Thread t) {
        throw new RuntimeException("Stub!");
    }

    void createMap(java.lang.Thread t, T firstValue) {
        throw new RuntimeException("Stub!");
    }

    static java.lang.ThreadLocal.ThreadLocalMap createInheritedMap(
            java.lang.ThreadLocal.ThreadLocalMap parentMap) {
        throw new RuntimeException("Stub!");
    }

    T childValue(T parentValue) {
        throw new RuntimeException("Stub!");
    }

    private static final int HASH_INCREMENT = 1640531527; // 0x61c88647

    private static java.util.concurrent.atomic.AtomicInteger nextHashCode;

    private final int threadLocalHashCode;

    {
        threadLocalHashCode = 0;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class SuppliedThreadLocal<T> extends java.lang.ThreadLocal<T> {

        SuppliedThreadLocal(java.util.function.Supplier<? extends T> supplier) {
            throw new RuntimeException("Stub!");
        }

        protected T initialValue() {
            throw new RuntimeException("Stub!");
        }

        private final java.util.function.Supplier<? extends T> supplier;

        {
            supplier = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class ThreadLocalMap {

        ThreadLocalMap(java.lang.ThreadLocal<?> firstKey, java.lang.Object firstValue) {
            throw new RuntimeException("Stub!");
        }

        private ThreadLocalMap(java.lang.ThreadLocal.ThreadLocalMap parentMap) {
            throw new RuntimeException("Stub!");
        }

        private void setThreshold(int len) {
            throw new RuntimeException("Stub!");
        }

        private static int nextIndex(int i, int len) {
            throw new RuntimeException("Stub!");
        }

        private static int prevIndex(int i, int len) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.ThreadLocal.ThreadLocalMap.Entry getEntry(java.lang.ThreadLocal<?> key) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.ThreadLocal.ThreadLocalMap.Entry getEntryAfterMiss(
                java.lang.ThreadLocal<?> key, int i, java.lang.ThreadLocal.ThreadLocalMap.Entry e) {
            throw new RuntimeException("Stub!");
        }

        private void set(java.lang.ThreadLocal<?> key, java.lang.Object value) {
            throw new RuntimeException("Stub!");
        }

        private void remove(java.lang.ThreadLocal<?> key) {
            throw new RuntimeException("Stub!");
        }

        private void replaceStaleEntry(
                java.lang.ThreadLocal<?> key, java.lang.Object value, int staleSlot) {
            throw new RuntimeException("Stub!");
        }

        private int expungeStaleEntry(int staleSlot) {
            throw new RuntimeException("Stub!");
        }

        private boolean cleanSomeSlots(int i, int n) {
            throw new RuntimeException("Stub!");
        }

        private void rehash() {
            throw new RuntimeException("Stub!");
        }

        private void resize() {
            throw new RuntimeException("Stub!");
        }

        private void expungeStaleEntries() {
            throw new RuntimeException("Stub!");
        }

        private static final int INITIAL_CAPACITY = 16; // 0x10

        private int size = 0; // 0x0

        private java.lang.ThreadLocal.ThreadLocalMap.Entry[] table;

        private int threshold;

        @SuppressWarnings({"unchecked", "deprecation", "all"})
        static class Entry extends java.lang.ref.WeakReference<java.lang.ThreadLocal<?>> {

            Entry(java.lang.ThreadLocal<?> k, java.lang.Object v) {
                super(null);
                throw new RuntimeException("Stub!");
            }

            java.lang.Object value;
        }
    }
}
