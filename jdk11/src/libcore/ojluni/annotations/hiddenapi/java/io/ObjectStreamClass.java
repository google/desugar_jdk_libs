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
public class ObjectStreamClass implements java.io.Serializable {

    private ObjectStreamClass(java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    ObjectStreamClass() {
        throw new RuntimeException("Stub!");
    }

    public static java.io.ObjectStreamClass lookup(java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    public static java.io.ObjectStreamClass lookupAny(java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public long getSerialVersionUID() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Class<?> forClass() {
        throw new RuntimeException("Stub!");
    }

    public java.io.ObjectStreamField[] getFields() {
        throw new RuntimeException("Stub!");
    }

    public java.io.ObjectStreamField getField(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    static java.io.ObjectStreamClass lookup(java.lang.Class<?> cl, boolean all) {
        throw new RuntimeException("Stub!");
    }

    void initProxy(
            java.lang.Class<?> cl,
            java.lang.ClassNotFoundException resolveEx,
            java.io.ObjectStreamClass superDesc)
            throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    void initNonProxy(
            java.io.ObjectStreamClass model,
            java.lang.Class<?> cl,
            java.lang.ClassNotFoundException resolveEx,
            java.io.ObjectStreamClass superDesc)
            throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    void readNonProxy(java.io.ObjectInputStream in)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    void writeNonProxy(java.io.ObjectOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    java.lang.ClassNotFoundException getResolveException() {
        throw new RuntimeException("Stub!");
    }

    private final void requireInitialized() {
        throw new RuntimeException("Stub!");
    }

    void checkDeserialize() throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    void checkSerialize() throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    void checkDefaultSerialize() throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    java.io.ObjectStreamClass getSuperDesc() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    java.io.ObjectStreamClass getLocalDesc() {
        throw new RuntimeException("Stub!");
    }

    java.io.ObjectStreamField[] getFields(boolean copy) {
        throw new RuntimeException("Stub!");
    }

    java.io.ObjectStreamField getField(java.lang.String name, java.lang.Class<?> type) {
        throw new RuntimeException("Stub!");
    }

    boolean isProxy() {
        throw new RuntimeException("Stub!");
    }

    boolean isEnum() {
        throw new RuntimeException("Stub!");
    }

    boolean isExternalizable() {
        throw new RuntimeException("Stub!");
    }

    boolean isSerializable() {
        throw new RuntimeException("Stub!");
    }

    boolean hasBlockExternalData() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    boolean hasWriteObjectData() {
        throw new RuntimeException("Stub!");
    }

    boolean isInstantiable() {
        throw new RuntimeException("Stub!");
    }

    boolean hasWriteObjectMethod() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    boolean hasReadObjectMethod() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    boolean hasReadObjectNoDataMethod() {
        throw new RuntimeException("Stub!");
    }

    boolean hasWriteReplaceMethod() {
        throw new RuntimeException("Stub!");
    }

    boolean hasReadResolveMethod() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    java.lang.Object newInstance()
            throws java.lang.InstantiationException, java.lang.reflect.InvocationTargetException,
                    java.lang.UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }

    void invokeWriteObject(java.lang.Object obj, java.io.ObjectOutputStream out)
            throws java.io.IOException, java.lang.UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }

    void invokeReadObject(java.lang.Object obj, java.io.ObjectInputStream in)
            throws java.lang.ClassNotFoundException, java.io.IOException,
                    java.lang.UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }

    void invokeReadObjectNoData(java.lang.Object obj)
            throws java.io.IOException, java.lang.UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }

    java.lang.Object invokeWriteReplace(java.lang.Object obj)
            throws java.io.IOException, java.lang.UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }

    java.lang.Object invokeReadResolve(java.lang.Object obj)
            throws java.io.IOException, java.lang.UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }

    java.io.ObjectStreamClass.ClassDataSlot[] getClassDataLayout()
            throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    private java.io.ObjectStreamClass.ClassDataSlot[] getClassDataLayout0()
            throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    int getPrimDataSize() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    int getNumObjFields() {
        throw new RuntimeException("Stub!");
    }

    void getPrimFieldValues(java.lang.Object obj, byte[] buf) {
        throw new RuntimeException("Stub!");
    }

    void setPrimFieldValues(java.lang.Object obj, byte[] buf) {
        throw new RuntimeException("Stub!");
    }

    void getObjFieldValues(java.lang.Object obj, java.lang.Object[] vals) {
        throw new RuntimeException("Stub!");
    }

    void setObjFieldValues(java.lang.Object obj, java.lang.Object[] vals) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private void computeFieldOffsets() throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    private java.io.ObjectStreamClass getVariantFor(java.lang.Class<?> cl)
            throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.reflect.Constructor<?> getExternalizableConstructor(
            java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.reflect.Constructor<?> getSerializableConstructor(
            java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.reflect.Method getInheritableMethod(
            java.lang.Class<?> cl,
            java.lang.String name,
            java.lang.Class<?>[] argTypes,
            java.lang.Class<?> returnType) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.reflect.Method getPrivateMethod(
            java.lang.Class<?> cl,
            java.lang.String name,
            java.lang.Class<?>[] argTypes,
            java.lang.Class<?> returnType) {
        throw new RuntimeException("Stub!");
    }

    private static boolean packageEquals(java.lang.Class<?> cl1, java.lang.Class<?> cl2) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String getPackageName(java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    private static boolean classNamesEqual(java.lang.String name1, java.lang.String name2) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String getClassSignature(java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String getMethodSignature(
            java.lang.Class<?>[] paramTypes, java.lang.Class<?> retType) {
        throw new RuntimeException("Stub!");
    }

    private static void throwMiscException(java.lang.Throwable th) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static java.io.ObjectStreamField[] getSerialFields(java.lang.Class<?> cl)
            throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    private static java.io.ObjectStreamField[] getDeclaredSerialFields(java.lang.Class<?> cl)
            throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    private static java.io.ObjectStreamField[] getDefaultSerialFields(java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.Long getDeclaredSUID(java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private static long computeDefaultSUID(java.lang.Class<?> cl) {
        throw new RuntimeException("Stub!");
    }

    private static native boolean hasStaticInitializer(
            java.lang.Class<?> cl, boolean checkSuperclass);

    private static java.io.ObjectStreamClass.FieldReflector getReflector(
            java.io.ObjectStreamField[] fields, java.io.ObjectStreamClass localDesc)
            throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    private static java.io.ObjectStreamField[] matchFields(
            java.io.ObjectStreamField[] fields, java.io.ObjectStreamClass localDesc)
            throws java.io.InvalidClassException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private static long getConstructorId(java.lang.Class<?> clazz) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private static java.lang.Object newInstance(java.lang.Class<?> clazz, long constructorId) {
        throw new RuntimeException("Stub!");
    }

    static void processQueue(
            java.lang.ref.ReferenceQueue<java.lang.Class<?>> queue,
            java.util.concurrent.ConcurrentMap<
                            ? extends java.lang.ref.WeakReference<java.lang.Class<?>>, ?>
                    map) {
        throw new RuntimeException("Stub!");
    }

    static final int MAX_SDK_TARGET_FOR_CLINIT_UIDGEN_WORKAROUND = 23; // 0x17

    public static final java.io.ObjectStreamField[] NO_FIELDS;

    static {
        NO_FIELDS = new java.io.ObjectStreamField[0];
    }

    private java.lang.Class<?> cl;

    private java.lang.reflect.Constructor<?> cons;

    private volatile java.io.ObjectStreamClass.ClassDataSlot[] dataLayout;

    private java.io.ObjectStreamClass.ExceptionInfo defaultSerializeEx;

    private java.io.ObjectStreamClass.ExceptionInfo deserializeEx;

    private boolean externalizable;

    private java.io.ObjectStreamClass.FieldReflector fieldRefl;

    @UnsupportedAppUsage
    private java.io.ObjectStreamField[] fields;

    private boolean hasBlockExternalData = true;

    private boolean hasWriteObjectData;

    private boolean initialized;

    private boolean isEnum;

    private boolean isProxy;

    private java.io.ObjectStreamClass localDesc;

    private java.lang.String name;

    private int numObjFields;

    private int primDataSize;

    private java.lang.reflect.Method readObjectMethod;

    private java.lang.reflect.Method readObjectNoDataMethod;

    private java.lang.reflect.Method readResolveMethod;

    private java.lang.ClassNotFoundException resolveEx;

    private static final java.io.ObjectStreamField[] serialPersistentFields;

    static {
        serialPersistentFields = new java.io.ObjectStreamField[0];
    }

    private static final long serialVersionUID = -6120832682080437368L; // 0xab0e6f1aeefe7b88L

    private boolean serializable;

    private java.io.ObjectStreamClass.ExceptionInfo serializeEx;

    private volatile java.lang.Long suid;

    private java.io.ObjectStreamClass superDesc;

    private java.lang.reflect.Method writeObjectMethod;

    private java.lang.reflect.Method writeReplaceMethod;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class Caches {

        private Caches() {
            throw new RuntimeException("Stub!");
        }

        static final java.util.concurrent.ConcurrentMap<
                        java.io.ObjectStreamClass.WeakClassKey, java.lang.ref.Reference<?>>
                localDescs;

        static {
            localDescs = null;
        }

        private static final java.lang.ref.ReferenceQueue<java.lang.Class<?>> localDescsQueue;

        static {
            localDescsQueue = null;
        }

        static final java.util.concurrent.ConcurrentMap<
                        java.io.ObjectStreamClass.FieldReflectorKey, java.lang.ref.Reference<?>>
                reflectors;

        static {
            reflectors = null;
        }

        private static final java.lang.ref.ReferenceQueue<java.lang.Class<?>> reflectorsQueue;

        static {
            reflectorsQueue = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class ClassDataSlot {

        ClassDataSlot(java.io.ObjectStreamClass desc, boolean hasData) {
            throw new RuntimeException("Stub!");
        }

        final java.io.ObjectStreamClass desc;

        {
            desc = null;
        }

        final boolean hasData;

        {
            hasData = false;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class EntryFuture {

        private EntryFuture() {
            throw new RuntimeException("Stub!");
        }

        synchronized boolean set(java.lang.Object entry) {
            throw new RuntimeException("Stub!");
        }

        synchronized java.lang.Object get() {
            throw new RuntimeException("Stub!");
        }

        java.lang.Thread getOwner() {
            throw new RuntimeException("Stub!");
        }

        private java.lang.Object entry;

        private final java.lang.Thread owner;

        {
            owner = null;
        }

        private static final java.lang.Object unset;

        static {
            unset = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class ExceptionInfo {

        ExceptionInfo(java.lang.String cn, java.lang.String msg) {
            throw new RuntimeException("Stub!");
        }

        java.io.InvalidClassException newInvalidClassException() {
            throw new RuntimeException("Stub!");
        }

        private final java.lang.String className;

        {
            className = null;
        }

        private final java.lang.String message;

        {
            message = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class FieldReflector {

        FieldReflector(java.io.ObjectStreamField[] fields) {
            throw new RuntimeException("Stub!");
        }

        java.io.ObjectStreamField[] getFields() {
            throw new RuntimeException("Stub!");
        }

        void getPrimFieldValues(java.lang.Object obj, byte[] buf) {
            throw new RuntimeException("Stub!");
        }

        void setPrimFieldValues(java.lang.Object obj, byte[] buf) {
            throw new RuntimeException("Stub!");
        }

        void getObjFieldValues(java.lang.Object obj, java.lang.Object[] vals) {
            throw new RuntimeException("Stub!");
        }

        void setObjFieldValues(java.lang.Object obj, java.lang.Object[] vals) {
            throw new RuntimeException("Stub!");
        }

        private final java.io.ObjectStreamField[] fields;

        {
            fields = new java.io.ObjectStreamField[0];
        }

        private final int numPrimFields;

        {
            numPrimFields = 0;
        }

        private final int[] offsets;

        {
            offsets = new int[0];
        }

        private final long[] readKeys;

        {
            readKeys = new long[0];
        }

        private final char[] typeCodes;

        {
            typeCodes = new char[0];
        }

        private final java.lang.Class<?>[] types;

        {
            types = new java.lang.Class[0];
        }

        private static final sun.misc.Unsafe unsafe;

        static {
            unsafe = null;
        }

        private final long[] writeKeys;

        {
            writeKeys = new long[0];
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class FieldReflectorKey extends java.lang.ref.WeakReference<java.lang.Class<?>> {

        FieldReflectorKey(
                java.lang.Class<?> cl,
                java.io.ObjectStreamField[] fields,
                java.lang.ref.ReferenceQueue<java.lang.Class<?>> queue) {
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

        private final boolean nullClass;

        {
            nullClass = false;
        }

        private final java.lang.String sigs;

        {
            sigs = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class MemberSignature {

        public MemberSignature(java.lang.reflect.Field field) {
            throw new RuntimeException("Stub!");
        }

        public MemberSignature(java.lang.reflect.Constructor<?> cons) {
            throw new RuntimeException("Stub!");
        }

        public MemberSignature(java.lang.reflect.Method meth) {
            throw new RuntimeException("Stub!");
        }

        public final java.lang.reflect.Member member;

        {
            member = null;
        }

        public final java.lang.String name;

        {
            name = null;
        }

        public final java.lang.String signature;

        {
            signature = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class WeakClassKey extends java.lang.ref.WeakReference<java.lang.Class<?>> {

        WeakClassKey(
                java.lang.Class<?> cl, java.lang.ref.ReferenceQueue<java.lang.Class<?>> refQueue) {
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
}
