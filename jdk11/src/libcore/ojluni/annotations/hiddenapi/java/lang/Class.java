/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1994, 2014, Oracle and/or its affiliates. All rights reserved.
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
public final class Class<T>
        implements java.io.Serializable,
                java.lang.reflect.GenericDeclaration,
                java.lang.reflect.Type,
                java.lang.reflect.AnnotatedElement {

    @UnsupportedAppUsage
    private Class() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toGenericString() {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Class<?> forName(java.lang.String className)
            throws java.lang.ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Class<?> forName(
            java.lang.String name, boolean initialize, java.lang.ClassLoader loader)
            throws java.lang.ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }

    static native java.lang.Class<?> classForName(
            java.lang.String className, boolean shouldInitialize, java.lang.ClassLoader classLoader)
            throws java.lang.ClassNotFoundException;

    public native T newInstance()
            throws java.lang.IllegalAccessException, java.lang.InstantiationException;

    public boolean isInstance(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public boolean isAssignableFrom(java.lang.Class<?> cls) {
        throw new RuntimeException("Stub!");
    }

    public boolean isInterface() {
        throw new RuntimeException("Stub!");
    }

    public boolean isArray() {
        throw new RuntimeException("Stub!");
    }

    public boolean isPrimitive() {
        throw new RuntimeException("Stub!");
    }

    public boolean isFinalizable() {
        throw new RuntimeException("Stub!");
    }

    public boolean isAnnotation() {
        throw new RuntimeException("Stub!");
    }

    public boolean isSynthetic() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.String getNameNative();

    public java.lang.ClassLoader getClassLoader() {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.reflect.TypeVariable<java.lang.Class<T>>[] getTypeParameters() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Class<? super T> getSuperclass() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.Type getGenericSuperclass() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Package getPackage() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getPackageName$() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Class<?>[] getInterfaces() {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.Class<?>[] getInterfacesInternal();

    public java.lang.reflect.Type[] getGenericInterfaces() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Class<?> getComponentType() {
        throw new RuntimeException("Stub!");
    }

    public int getModifiers() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object[] getSigners() {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.reflect.Method getEnclosingMethodNative();

    public java.lang.reflect.Method getEnclosingMethod() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.Constructor<?> getEnclosingConstructor() {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.reflect.Constructor<?> getEnclosingConstructorNative();

    private boolean classNameImpliesTopLevel() {
        throw new RuntimeException("Stub!");
    }

    public native java.lang.Class<?> getDeclaringClass();

    public native java.lang.Class<?> getEnclosingClass();

    public java.lang.String getSimpleName() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getTypeName() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getCanonicalName() {
        throw new RuntimeException("Stub!");
    }

    public native boolean isAnonymousClass();

    public boolean isLocalClass() {
        throw new RuntimeException("Stub!");
    }

    public boolean isMemberClass() {
        throw new RuntimeException("Stub!");
    }

    private boolean isLocalOrAnonymousClass() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Class<?>[] getClasses() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.Field[] getFields() throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    private void getPublicFieldsRecursive(java.util.List<java.lang.reflect.Field> result) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.Method[] getMethods() throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    private void getPublicMethodsInternal(java.util.List<java.lang.reflect.Method> result) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.Constructor<?>[] getConstructors() throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.Field getField(java.lang.String name)
            throws java.lang.NoSuchFieldException {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.reflect.Field getPublicFieldRecursive(java.lang.String name);

    public java.lang.reflect.Method getMethod(
            java.lang.String name, java.lang.Class<?>... parameterTypes)
            throws java.lang.NoSuchMethodException, java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.Constructor<T> getConstructor(java.lang.Class<?>... parameterTypes)
            throws java.lang.NoSuchMethodException, java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public native java.lang.Class<?>[] getDeclaredClasses();

    public native java.lang.reflect.Field[] getDeclaredFields();

    public native java.lang.reflect.Field[] getDeclaredFieldsUnchecked(boolean publicOnly);

    public java.lang.reflect.Method[] getDeclaredMethods() throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public native java.lang.reflect.Method[] getDeclaredMethodsUnchecked(boolean publicOnly);

    public java.lang.reflect.Constructor<?>[] getDeclaredConstructors()
            throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.reflect.Constructor<?>[] getDeclaredConstructorsInternal(
            boolean publicOnly);

    public native java.lang.reflect.Field getDeclaredField(java.lang.String name)
            throws java.lang.NoSuchFieldException;

    private native java.lang.reflect.Field[] getPublicDeclaredFields();

    public java.lang.reflect.Method getDeclaredMethod(
            java.lang.String name, java.lang.Class<?>... parameterTypes)
            throws java.lang.NoSuchMethodException, java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private java.lang.reflect.Method getMethod(
            java.lang.String name,
            java.lang.Class<?>[] parameterTypes,
            boolean recursivePublicMethods)
            throws java.lang.NoSuchMethodException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.reflect.Method getPublicMethodRecursive(
            java.lang.String name, java.lang.Class<?>[] parameterTypes) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.Method getInstanceMethod(
            java.lang.String name, java.lang.Class<?>[] parameterTypes)
            throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.reflect.Method findInterfaceMethod(
            java.lang.String name, java.lang.Class<?>[] parameterTypes) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.reflect.Constructor<T> getDeclaredConstructor(
            java.lang.Class<?>... parameterTypes)
            throws java.lang.NoSuchMethodException, java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public java.io.InputStream getResourceAsStream(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.net.URL getResource(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.security.ProtectionDomain getProtectionDomain() {
        throw new RuntimeException("Stub!");
    }

    static native java.lang.Class<?> getPrimitiveClass(java.lang.String name);

    private java.lang.String resolveName(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.reflect.Constructor<T> getConstructor0(
            java.lang.Class<?>[] parameterTypes, int which) throws java.lang.NoSuchMethodException {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.reflect.Constructor<T> getDeclaredConstructorInternal(
            java.lang.Class<?>[] args);

    public boolean desiredAssertionStatus() {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.String getInnerClassName();

    private native int getInnerClassFlags(int defaultValue);

    public boolean isEnum() {
        throw new RuntimeException("Stub!");
    }

    public T[] getEnumConstants() {
        throw new RuntimeException("Stub!");
    }

    public T[] getEnumConstantsShared() {
        throw new RuntimeException("Stub!");
    }

    public T cast(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String cannotCastMsg(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public <U> java.lang.Class<? extends U> asSubclass(java.lang.Class<U> clazz) {
        throw new RuntimeException("Stub!");
    }

    public <A extends java.lang.annotation.Annotation> A getAnnotation(
            java.lang.Class<A> annotationClass) {
        throw new RuntimeException("Stub!");
    }

    public boolean isAnnotationPresent(
            java.lang.Class<? extends java.lang.annotation.Annotation> annotationClass) {
        throw new RuntimeException("Stub!");
    }

    public <A extends java.lang.annotation.Annotation> A[] getAnnotationsByType(
            java.lang.Class<A> annotationClass) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.annotation.Annotation[] getAnnotations() {
        throw new RuntimeException("Stub!");
    }

    public native <A extends java.lang.annotation.Annotation> A getDeclaredAnnotation(
            java.lang.Class<A> annotationClass);

    public native java.lang.annotation.Annotation[] getDeclaredAnnotations();

    private native boolean isDeclaredAnnotationPresent(
            java.lang.Class<? extends java.lang.annotation.Annotation> annotationClass);

    private java.lang.String getSignatureAttribute() {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.String[] getSignatureAnnotation();

    public boolean isProxy() {
        throw new RuntimeException("Stub!");
    }

    public int getAccessFlags() {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.reflect.Method getDeclaredMethodInternal(
            java.lang.String name, java.lang.Class<?>[] args);

    private static final int ANNOTATION = 8192; // 0x2000

    private static final int ENUM = 16384; // 0x4000

    private static final int FINALIZABLE = -2147483648; // 0x80000000

    private static final int SYNTHETIC = 4096; // 0x1000

    @UnsupportedAppUsage
    private transient int accessFlags;

    private transient int classFlags;

    @UnsupportedAppUsage
    private transient java.lang.ClassLoader classLoader;

    private transient int classSize;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private transient int clinitThreadId;

    private transient java.lang.Class<?> componentType;

    private transient short copiedMethodsOffset;

    @UnsupportedAppUsage
    private transient java.lang.Object dexCache;

    @UnsupportedAppUsage
    private transient int dexClassDefIndex;

    private transient volatile int dexTypeIndex;

    private transient dalvik.system.ClassExt extData;

    private transient long iFields;

    @UnsupportedAppUsage
    private transient java.lang.Object[] ifTable;

    private transient long methods;

    @UnsupportedAppUsage
    private transient java.lang.String name;

    private transient int numReferenceInstanceFields;

    private transient int numReferenceStaticFields;

    @UnsupportedAppUsage
    private transient int objectSize;

    private transient int objectSizeAllocFastPath;

    private transient int primitiveType;

    private transient int referenceInstanceOffsets;

    private transient long sFields;

    private static final long serialVersionUID = 3206093459760846163L; // 0x2c7e5503d9bf9553L

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private transient int status;

    private transient java.lang.Class<? super T> superClass;

    private transient short virtualMethodsOffset;

    private transient java.lang.Object vtable;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class Caches {

        private Caches() {
            throw new RuntimeException("Stub!");
        }

        private static final libcore.util.BasicLruCache<java.lang.Class, java.lang.reflect.Type[]>
                genericInterfaces;

        static {
            genericInterfaces = null;
        }
    }
}
