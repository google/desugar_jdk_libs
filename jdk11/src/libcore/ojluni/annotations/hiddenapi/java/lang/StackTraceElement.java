/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
public final class StackTraceElement implements java.io.Serializable {

    public StackTraceElement(
            java.lang.String declaringClass,
            java.lang.String methodName,
            java.lang.String fileName,
            int lineNumber) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getFileName() {
        throw new RuntimeException("Stub!");
    }

    public int getLineNumber() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getClassName() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getMethodName() {
        throw new RuntimeException("Stub!");
    }

    public boolean isNativeMethod() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private java.lang.String declaringClass;

    @UnsupportedAppUsage
    private java.lang.String fileName;

    @UnsupportedAppUsage
    private int lineNumber;

    @UnsupportedAppUsage
    private java.lang.String methodName;

    private static final long serialVersionUID = 6992337162326171013L; // 0x6109c59a2636dd85L
}
