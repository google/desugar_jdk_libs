/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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

package sun.reflect;

import java.lang.reflect.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class Reflection {

    public Reflection() {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Class<?> getCallerClass() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static void ensureMemberAccess(
            java.lang.Class<?> currentClass,
            java.lang.Class<?> memberClass,
            java.lang.Object target,
            int modifiers)
            throws java.lang.IllegalAccessException {
        throw new RuntimeException("Stub!");
    }

    public static boolean verifyMemberAccess(
            java.lang.Class<?> currentClass,
            java.lang.Class<?> memberClass,
            java.lang.Object target,
            int modifiers) {
        throw new RuntimeException("Stub!");
    }

    private static boolean isSameClassPackage(java.lang.Class<?> c1, java.lang.Class<?> c2) {
        throw new RuntimeException("Stub!");
    }

    private static boolean isSameClassPackage(
            java.lang.ClassLoader loader1,
            java.lang.String name1,
            java.lang.ClassLoader loader2,
            java.lang.String name2) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    static boolean isSubclassOf(java.lang.Class<?> queryClass, java.lang.Class<?> ofClass) {
        throw new RuntimeException("Stub!");
    }
}
