/*
 * Copyright (C) 2014 The Android Open Source Project
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

package java.lang.reflect;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class Proxy implements java.io.Serializable {

    private Proxy() {
        throw new RuntimeException("Stub!");
    }

    protected Proxy(java.lang.reflect.InvocationHandler h) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Class<?> getProxyClass(
            java.lang.ClassLoader loader, java.lang.Class<?>... interfaces)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.Class<?> getProxyClass0(
            java.lang.ClassLoader loader, java.lang.Class<?>... interfaces) {
        throw new RuntimeException("Stub!");
    }

    private static java.util.List<java.lang.Class<?>[]> deduplicateAndGetExceptions(
            java.util.List<java.lang.reflect.Method> methods) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.Class<?>[] intersectExceptions(
            java.lang.Class<?>[] aExceptions, java.lang.Class<?>[] bExceptions) {
        throw new RuntimeException("Stub!");
    }

    private static void validateReturnTypes(java.util.List<java.lang.reflect.Method> methods) {
        throw new RuntimeException("Stub!");
    }

    private static java.util.List<java.lang.reflect.Method> getMethods(
            java.lang.Class<?>[] interfaces) {
        throw new RuntimeException("Stub!");
    }

    private static void getMethodsRecursive(
            java.lang.Class<?>[] interfaces, java.util.List<java.lang.reflect.Method> methods) {
        throw new RuntimeException("Stub!");
    }

    private static native java.lang.Class<?> generateProxy(
            java.lang.String name,
            java.lang.Class<?>[] interfaces,
            java.lang.ClassLoader loader,
            java.lang.reflect.Method[] methods,
            java.lang.Class<?>[][] exceptions);

    public static java.lang.Object newProxyInstance(
            java.lang.ClassLoader loader,
            java.lang.Class<?>[] interfaces,
            java.lang.reflect.InvocationHandler h)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    public static boolean isProxyClass(java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.reflect.InvocationHandler getInvocationHandler(java.lang.Object proxy)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private static java.lang.Object invoke(
            java.lang.reflect.Proxy proxy, java.lang.reflect.Method method, java.lang.Object[] args)
            throws java.lang.Throwable {
        throw new RuntimeException("Stub!");
    }

    private static final java.util.Comparator<java.lang.reflect.Method>
            ORDER_BY_SIGNATURE_AND_SUBTYPE;

    static {
        ORDER_BY_SIGNATURE_AND_SUBTYPE = null;
    }

    private static final java.lang.Class<?>[] constructorParams;

    static {
        constructorParams = new java.lang.Class[0];
    }

    protected java.lang.reflect.InvocationHandler h;

    private static final java.lang.Object key0;

    static {
        key0 = null;
    }

    private static final java.lang.reflect.WeakCache<
                    java.lang.ClassLoader, java.lang.Class<?>[], java.lang.Class<?>>
            proxyClassCache;

    static {
        proxyClassCache = null;
    }

    private static final long serialVersionUID = -2222568056686623797L; // 0xe127da20cc1043cbL

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static final class Key1 extends java.lang.ref.WeakReference<java.lang.Class<?>> {

        Key1(java.lang.Class<?> intf) {
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

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static final class Key2 extends java.lang.ref.WeakReference<java.lang.Class<?>> {

        Key2(java.lang.Class<?> intf1, java.lang.Class<?> intf2) {
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

        private final java.lang.ref.WeakReference<java.lang.Class<?>> ref2;

        {
            ref2 = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static final class KeyFactory
            implements java.util.function.BiFunction<
                    java.lang.ClassLoader, java.lang.Class<?>[], java.lang.Object> {

        private KeyFactory() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object apply(
                java.lang.ClassLoader classLoader, java.lang.Class<?>[] interfaces) {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static final class KeyX {

        KeyX(java.lang.Class<?>[] interfaces) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        private static boolean equals(
                java.lang.ref.WeakReference<java.lang.Class<?>>[] refs1,
                java.lang.ref.WeakReference<java.lang.Class<?>>[] refs2) {
            throw new RuntimeException("Stub!");
        }

        private final int hash;

        {
            hash = 0;
        }

        private final java.lang.ref.WeakReference<java.lang.Class<?>>[] refs;

        {
            refs = new java.lang.ref.WeakReference[0];
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static final class ProxyClassFactory
            implements java.util.function.BiFunction<
                    java.lang.ClassLoader, java.lang.Class<?>[], java.lang.Class<?>> {

        private ProxyClassFactory() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Class<?> apply(
                java.lang.ClassLoader loader, java.lang.Class<?>[] interfaces) {
            throw new RuntimeException("Stub!");
        }

        private static final java.util.concurrent.atomic.AtomicLong nextUniqueNumber;

        static {
            nextUniqueNumber = null;
        }

        private static final java.lang.String proxyClassNamePrefix = "$Proxy";
    }
}
