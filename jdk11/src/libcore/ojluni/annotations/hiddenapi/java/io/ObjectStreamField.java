/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class ObjectStreamField implements java.lang.Comparable<java.lang.Object> {

    public ObjectStreamField(java.lang.String name, java.lang.Class<?> type) {
        throw new RuntimeException("Stub!");
    }

    public ObjectStreamField(java.lang.String name, java.lang.Class<?> type, boolean unshared) {
        throw new RuntimeException("Stub!");
    }

    ObjectStreamField(java.lang.String name, java.lang.String signature, boolean unshared) {
        throw new RuntimeException("Stub!");
    }

    ObjectStreamField(java.lang.reflect.Field field, boolean unshared, boolean showType) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Class<?> getType() {
        throw new RuntimeException("Stub!");
    }

    public char getTypeCode() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getTypeString() {
        throw new RuntimeException("Stub!");
    }

    public int getOffset() {
        throw new RuntimeException("Stub!");
    }

    protected void setOffset(int offset) {
        throw new RuntimeException("Stub!");
    }

    public boolean isPrimitive() {
        throw new RuntimeException("Stub!");
    }

    public boolean isUnshared() {
        throw new RuntimeException("Stub!");
    }

    public int compareTo(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    java.lang.reflect.Field getField() {
        throw new RuntimeException("Stub!");
    }

    java.lang.String getSignature() {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String getClassSignature(java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    private final java.lang.reflect.Field field;

    {
        field = null;
    }

    private final java.lang.String name;

    {
        name = null;
    }

    private int offset = 0; // 0x0

    private final java.lang.String signature;

    {
        signature = null;
    }

    private final java.lang.Class<?> type;

    {
        type = null;
    }

    private final boolean unshared;

    {
        unshared = false;
    }
}
