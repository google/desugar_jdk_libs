/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2005, 2013 Oracle and/or its affiliates. All rights reserved.
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

package sun.reflect.misc;


@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class ReflectUtil {

    private ReflectUtil() {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Class<?> forName(java.lang.String name)
            throws java.lang.ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Object newInstance(java.lang.Class<?> cls)
            throws java.lang.IllegalAccessException, java.lang.InstantiationException {
        throw new RuntimeException("Stub!");
    }

    public static void ensureMemberAccess(
            java.lang.Class<?> currentClass,
            java.lang.Class<?> memberClass,
            java.lang.Object target,
            int modifiers)
            throws java.lang.IllegalAccessException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    private static boolean isSubclassOf(java.lang.Class<?> queryClass, java.lang.Class<?> ofClass) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static void checkPackageAccess(java.lang.Class<?> clazz) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static void checkPackageAccess(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static boolean isPackageAccessible(java.lang.Class<?> clazz) {
        throw new RuntimeException("Stub!");
    }

    private static boolean isAncestor(java.lang.ClassLoader p, java.lang.ClassLoader cl) {
        throw new RuntimeException("Stub!");
    }

    public static boolean needsPackageAccessCheck(
            java.lang.ClassLoader from, java.lang.ClassLoader to) {
        throw new RuntimeException("Stub!");
    }

    public static void checkProxyPackageAccess(java.lang.Class<?> clazz) {
        throw new RuntimeException("Stub!");
    }

    public static void checkProxyPackageAccess(
            java.lang.ClassLoader ccl, java.lang.Class<?>... interfaces) {
        throw new RuntimeException("Stub!");
    }

    public static boolean isNonPublicProxyClass(java.lang.Class<?> cls) {
        throw new RuntimeException("Stub!");
    }
}
