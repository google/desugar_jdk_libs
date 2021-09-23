/*
 * Copyright (C) 2014 The Android Open Source Project
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

package java.lang.reflect;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class Constructor<T> extends java.lang.reflect.Executable {

    private Constructor() {
        throw new RuntimeException("Stub!");
    }

    private Constructor(
            java.lang.Class<?> serializationCtor, java.lang.Class<?> serializationClass) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public java.lang.reflect.Constructor<T> serializationCopy(
            java.lang.Class<?> ctor, java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    boolean hasGenericInformation() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Class<T> getDeclaringClass() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public int getModifiers() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.TypeVariable<java.lang.reflect.Constructor<T>>[] getTypeParameters() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Class<?>[] getParameterTypes() {
        throw new RuntimeException("Stub!");
    }

    public int getParameterCount() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.Type[] getGenericParameterTypes() {
        throw new RuntimeException("Stub!");
    }

    public native java.lang.Class<?>[] getExceptionTypes();

    public java.lang.reflect.Type[] getGenericExceptionTypes() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    void specificToStringHeader(java.lang.StringBuilder sb) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toGenericString() {
        throw new RuntimeException("Stub!");
    }

    void specificToGenericStringHeader(java.lang.StringBuilder sb) {
        throw new RuntimeException("Stub!");
    }

    public T newInstance(java.lang.Object... initargs)
            throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException,
                    java.lang.InstantiationException, java.lang.reflect.InvocationTargetException {
        throw new RuntimeException("Stub!");
    }

    private static native java.lang.Object newInstanceFromSerialization(
            java.lang.Class<?> ctorClass, java.lang.Class<?> allocClass)
            throws java.lang.IllegalArgumentException, java.lang.InstantiationException,
                    java.lang.reflect.InvocationTargetException;

    private native T newInstance0(java.lang.Object... args)
            throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException,
                    java.lang.InstantiationException, java.lang.reflect.InvocationTargetException;

    public boolean isVarArgs() {
        throw new RuntimeException("Stub!");
    }

    public boolean isSynthetic() {
        throw new RuntimeException("Stub!");
    }

    public <T extends java.lang.annotation.Annotation> T getAnnotation(
            java.lang.Class<T> annotationClass) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.annotation.Annotation[] getDeclaredAnnotations() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.annotation.Annotation[][] getParameterAnnotations() {
        throw new RuntimeException("Stub!");
    }

    private static final java.util.Comparator<java.lang.reflect.Method> ORDER_BY_SIGNATURE;

    static {
        ORDER_BY_SIGNATURE = null;
    }

    private final java.lang.Class<?> serializationClass;

    {
        serializationClass = null;
    }

    private final java.lang.Class<?> serializationCtor;

    {
        serializationCtor = null;
    }
}
