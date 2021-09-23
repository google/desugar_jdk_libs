/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
public abstract class Executable extends java.lang.reflect.AccessibleObject
        implements java.lang.reflect.Member, java.lang.reflect.GenericDeclaration {

    Executable() {
        throw new RuntimeException("Stub!");
    }

    abstract boolean hasGenericInformation();

    boolean equalParamTypes(java.lang.Class<?>[] params1, java.lang.Class<?>[] params2) {
        throw new RuntimeException("Stub!");
    }

    void separateWithCommas(java.lang.Class<?>[] types, java.lang.StringBuilder sb) {
        throw new RuntimeException("Stub!");
    }

    void printModifiersIfNonzero(java.lang.StringBuilder sb, int mask, boolean isDefault) {
        throw new RuntimeException("Stub!");
    }

    java.lang.String sharedToString(
            int modifierMask,
            boolean isDefault,
            java.lang.Class<?>[] parameterTypes,
            java.lang.Class<?>[] exceptionTypes) {
        throw new RuntimeException("Stub!");
    }

    abstract void specificToStringHeader(java.lang.StringBuilder sb);

    java.lang.String sharedToGenericString(int modifierMask, boolean isDefault) {
        throw new RuntimeException("Stub!");
    }

    abstract void specificToGenericStringHeader(java.lang.StringBuilder sb);

    public abstract java.lang.Class<?> getDeclaringClass();

    public abstract java.lang.String getName();

    public abstract int getModifiers();

    public abstract java.lang.reflect.TypeVariable<?>[] getTypeParameters();

    public abstract java.lang.Class<?>[] getParameterTypes();

    public int getParameterCount() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.Type[] getGenericParameterTypes() {
        throw new RuntimeException("Stub!");
    }

    java.lang.reflect.Type[] getAllGenericParameterTypes() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.Parameter[] getParameters() {
        throw new RuntimeException("Stub!");
    }

    private java.lang.reflect.Parameter[] synthesizeAllParams() {
        throw new RuntimeException("Stub!");
    }

    private void verifyParameters(java.lang.reflect.Parameter[] parameters) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.reflect.Parameter[] privateGetParameters() {
        throw new RuntimeException("Stub!");
    }

    boolean hasRealParameterData() {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.reflect.Parameter[] getParameters0();

    public abstract java.lang.Class<?>[] getExceptionTypes();

    public java.lang.reflect.Type[] getGenericExceptionTypes() {
        throw new RuntimeException("Stub!");
    }

    public abstract java.lang.String toGenericString();

    public boolean isVarArgs() {
        throw new RuntimeException("Stub!");
    }

    public boolean isSynthetic() {
        throw new RuntimeException("Stub!");
    }

    public abstract java.lang.annotation.Annotation[][] getParameterAnnotations();

    public <T extends java.lang.annotation.Annotation> T getAnnotation(
            java.lang.Class<T> annotationClass) {
        throw new RuntimeException("Stub!");
    }

    private native <T extends java.lang.annotation.Annotation> T getAnnotationNative(
            java.lang.Class<T> annotationClass);

    public <T extends java.lang.annotation.Annotation> T[] getAnnotationsByType(
            java.lang.Class<T> annotationClass) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.annotation.Annotation[] getDeclaredAnnotations() {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.annotation.Annotation[] getDeclaredAnnotationsNative();

    private static int fixMethodFlags(int flags) {
        throw new RuntimeException("Stub!");
    }

    final int getModifiersInternal() {
        throw new RuntimeException("Stub!");
    }

    final java.lang.Class<?> getDeclaringClassInternal() {
        throw new RuntimeException("Stub!");
    }

    final native java.lang.Class<?>[] getParameterTypesInternal();

    final native int getParameterCountInternal();

    public final boolean isAnnotationPresent(
            java.lang.Class<? extends java.lang.annotation.Annotation> annotationType) {
        throw new RuntimeException("Stub!");
    }

    private native boolean isAnnotationPresentNative(
            java.lang.Class<? extends java.lang.annotation.Annotation> annotationType);

    final java.lang.annotation.Annotation[][] getParameterAnnotationsInternal() {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.annotation.Annotation[][] getParameterAnnotationsNative();

    public final int getAccessFlags() {
        throw new RuntimeException("Stub!");
    }

    public final long getArtMethod() {
        throw new RuntimeException("Stub!");
    }

    final boolean hasGenericInformationInternal() {
        throw new RuntimeException("Stub!");
    }

    final java.lang.reflect.Executable.GenericInfo getMethodOrConstructorGenericInfoInternal() {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String getSignatureAttribute() {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.String[] getSignatureAnnotation();

    final boolean equalNameAndParametersInternal(java.lang.reflect.Method m) {
        throw new RuntimeException("Stub!");
    }

    native int compareMethodParametersInternal(java.lang.reflect.Method meth);

    final native java.lang.String getMethodNameInternal();

    final native java.lang.Class<?> getMethodReturnTypeInternal();

    final boolean isDefaultMethodInternal() {
        throw new RuntimeException("Stub!");
    }

    final boolean isBridgeMethodInternal() {
        throw new RuntimeException("Stub!");
    }

    private int accessFlags;

    @UnsupportedAppUsage
    private long artMethod;

    private java.lang.Class<?> declaringClass;

    private java.lang.Class<?> declaringClassOfOverriddenMethod;

    private int dexMethodIndex;

    private transient volatile boolean hasRealParameterData;

    private transient volatile java.lang.reflect.Parameter[] parameters;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class GenericInfo {

        GenericInfo(
                libcore.reflect.ListOfTypes exceptions,
                libcore.reflect.ListOfTypes parameters,
                java.lang.reflect.Type ret,
                java.lang.reflect.TypeVariable<?>[] formal) {
            throw new RuntimeException("Stub!");
        }

        final java.lang.reflect.TypeVariable<?>[] formalTypeParameters;

        {
            formalTypeParameters = new java.lang.reflect.TypeVariable[0];
        }

        final libcore.reflect.ListOfTypes genericExceptionTypes;

        {
            genericExceptionTypes = null;
        }

        final libcore.reflect.ListOfTypes genericParameterTypes;

        {
            genericParameterTypes = null;
        }

        final java.lang.reflect.Type genericReturnType;

        {
            genericReturnType = null;
        }
    }
}
