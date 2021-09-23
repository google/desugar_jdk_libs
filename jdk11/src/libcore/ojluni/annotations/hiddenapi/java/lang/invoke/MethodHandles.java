/*
 * Copyright (c) 2008, 2017, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.invoke;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class MethodHandles {

    private MethodHandles() {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandles.Lookup lookup() {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandles.Lookup publicLookup() {
        throw new RuntimeException("Stub!");
    }

    public static <T extends java.lang.reflect.Member> T reflectAs(
            java.lang.Class<T> expected, java.lang.invoke.MethodHandle target) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.invoke.MethodHandleImpl getMethodHandleImpl(
            java.lang.invoke.MethodHandle target) {
        throw new RuntimeException("Stub!");
    }

    private static void checkClassIsArray(java.lang.Class<?> c) {
        throw new RuntimeException("Stub!");
    }

    private static void checkTypeIsViewable(java.lang.Class<?> componentType) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle arrayElementGetter(java.lang.Class<?> arrayClass)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    public static byte arrayElementGetter(byte[] array, int i) {
        throw new RuntimeException("Stub!");
    }

    public static boolean arrayElementGetter(boolean[] array, int i) {
        throw new RuntimeException("Stub!");
    }

    public static char arrayElementGetter(char[] array, int i) {
        throw new RuntimeException("Stub!");
    }

    public static short arrayElementGetter(short[] array, int i) {
        throw new RuntimeException("Stub!");
    }

    public static int arrayElementGetter(int[] array, int i) {
        throw new RuntimeException("Stub!");
    }

    public static long arrayElementGetter(long[] array, int i) {
        throw new RuntimeException("Stub!");
    }

    public static float arrayElementGetter(float[] array, int i) {
        throw new RuntimeException("Stub!");
    }

    public static double arrayElementGetter(double[] array, int i) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle arrayElementSetter(java.lang.Class<?> arrayClass)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    public static void arrayElementSetter(byte[] array, int i, byte val) {
        throw new RuntimeException("Stub!");
    }

    public static void arrayElementSetter(boolean[] array, int i, boolean val) {
        throw new RuntimeException("Stub!");
    }

    public static void arrayElementSetter(char[] array, int i, char val) {
        throw new RuntimeException("Stub!");
    }

    public static void arrayElementSetter(short[] array, int i, short val) {
        throw new RuntimeException("Stub!");
    }

    public static void arrayElementSetter(int[] array, int i, int val) {
        throw new RuntimeException("Stub!");
    }

    public static void arrayElementSetter(long[] array, int i, long val) {
        throw new RuntimeException("Stub!");
    }

    public static void arrayElementSetter(float[] array, int i, float val) {
        throw new RuntimeException("Stub!");
    }

    public static void arrayElementSetter(double[] array, int i, double val) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.VarHandle arrayElementVarHandle(java.lang.Class<?> arrayClass)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.VarHandle byteArrayViewVarHandle(
            java.lang.Class<?> viewArrayClass, java.nio.ByteOrder byteOrder)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.VarHandle byteBufferViewVarHandle(
            java.lang.Class<?> viewArrayClass, java.nio.ByteOrder byteOrder)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle spreadInvoker(
            java.lang.invoke.MethodType type, int leadingArgCount) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle exactInvoker(java.lang.invoke.MethodType type) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle invoker(java.lang.invoke.MethodType type) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.invoke.MethodHandle methodHandleForVarHandleAccessor(
            java.lang.invoke.VarHandle.AccessMode accessMode,
            java.lang.invoke.MethodType type,
            boolean isExactInvoker) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle varHandleExactInvoker(
            java.lang.invoke.VarHandle.AccessMode accessMode, java.lang.invoke.MethodType type) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle varHandleInvoker(
            java.lang.invoke.VarHandle.AccessMode accessMode, java.lang.invoke.MethodType type) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle explicitCastArguments(
            java.lang.invoke.MethodHandle target, java.lang.invoke.MethodType newType) {
        throw new RuntimeException("Stub!");
    }

    private static void explicitCastArgumentsChecks(
            java.lang.invoke.MethodHandle target, java.lang.invoke.MethodType newType) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle permuteArguments(
            java.lang.invoke.MethodHandle target,
            java.lang.invoke.MethodType newType,
            int... reorder) {
        throw new RuntimeException("Stub!");
    }

    private static boolean permuteArgumentChecks(
            int[] reorder,
            java.lang.invoke.MethodType newType,
            java.lang.invoke.MethodType oldType) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle constant(
            java.lang.Class<?> type, java.lang.Object value) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle identity(java.lang.Class<?> type) {
        throw new RuntimeException("Stub!");
    }

    public static byte identity(byte val) {
        throw new RuntimeException("Stub!");
    }

    public static boolean identity(boolean val) {
        throw new RuntimeException("Stub!");
    }

    public static char identity(char val) {
        throw new RuntimeException("Stub!");
    }

    public static short identity(short val) {
        throw new RuntimeException("Stub!");
    }

    public static int identity(int val) {
        throw new RuntimeException("Stub!");
    }

    public static long identity(long val) {
        throw new RuntimeException("Stub!");
    }

    public static float identity(float val) {
        throw new RuntimeException("Stub!");
    }

    public static double identity(double val) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle insertArguments(
            java.lang.invoke.MethodHandle target, int pos, java.lang.Object... values) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.Class<?>[] insertArgumentsChecks(
            java.lang.invoke.MethodHandle target, int insCount, int pos)
            throws java.lang.RuntimeException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle dropArguments(
            java.lang.invoke.MethodHandle target,
            int pos,
            java.util.List<java.lang.Class<?>> valueTypes) {
        throw new RuntimeException("Stub!");
    }

    private static java.util.List<java.lang.Class<?>> copyTypes(
            java.util.List<java.lang.Class<?>> types) {
        throw new RuntimeException("Stub!");
    }

    private static int dropArgumentChecks(
            java.lang.invoke.MethodType oldType,
            int pos,
            java.util.List<java.lang.Class<?>> valueTypes) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle dropArguments(
            java.lang.invoke.MethodHandle target, int pos, java.lang.Class<?>... valueTypes) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle filterArguments(
            java.lang.invoke.MethodHandle target,
            int pos,
            java.lang.invoke.MethodHandle... filters) {
        throw new RuntimeException("Stub!");
    }

    private static void filterArgumentsCheckArity(
            java.lang.invoke.MethodHandle target,
            int pos,
            java.lang.invoke.MethodHandle[] filters) {
        throw new RuntimeException("Stub!");
    }

    private static void filterArgumentChecks(
            java.lang.invoke.MethodHandle target, int pos, java.lang.invoke.MethodHandle filter)
            throws java.lang.RuntimeException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle collectArguments(
            java.lang.invoke.MethodHandle target, int pos, java.lang.invoke.MethodHandle filter) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.invoke.MethodType collectArgumentsChecks(
            java.lang.invoke.MethodHandle target, int pos, java.lang.invoke.MethodHandle filter)
            throws java.lang.RuntimeException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle filterReturnValue(
            java.lang.invoke.MethodHandle target, java.lang.invoke.MethodHandle filter) {
        throw new RuntimeException("Stub!");
    }

    private static void filterReturnValueChecks(
            java.lang.invoke.MethodType targetType, java.lang.invoke.MethodType filterType)
            throws java.lang.RuntimeException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle foldArguments(
            java.lang.invoke.MethodHandle target, java.lang.invoke.MethodHandle combiner) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.Class<?> foldArgumentChecks(
            int foldPos,
            java.lang.invoke.MethodType targetType,
            java.lang.invoke.MethodType combinerType) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle guardWithTest(
            java.lang.invoke.MethodHandle test,
            java.lang.invoke.MethodHandle target,
            java.lang.invoke.MethodHandle fallback) {
        throw new RuntimeException("Stub!");
    }

    static java.lang.RuntimeException misMatchedTypes(
            java.lang.String what, java.lang.invoke.MethodType t1, java.lang.invoke.MethodType t2) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle catchException(
            java.lang.invoke.MethodHandle target,
            java.lang.Class<? extends java.lang.Throwable> exType,
            java.lang.invoke.MethodHandle handler) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.invoke.MethodHandle throwException(
            java.lang.Class<?> returnType, java.lang.Class<? extends java.lang.Throwable> exType) {
        throw new RuntimeException("Stub!");
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static final class Lookup {

        Lookup(java.lang.Class<?> lookupClass) {
            throw new RuntimeException("Stub!");
        }

        @UnsupportedAppUsage
        private Lookup(java.lang.Class<?> lookupClass, int allowedModes) {
            throw new RuntimeException("Stub!");
        }

        private static int fixmods(int mods) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Class<?> lookupClass() {
            throw new RuntimeException("Stub!");
        }

        public int lookupModes() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandles.Lookup in(java.lang.Class<?> requestedLookupClass) {
            throw new RuntimeException("Stub!");
        }

        private static void checkUnprivilegedlookupClass(
                java.lang.Class<?> lookupClass, int allowedModes) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle findStatic(
                java.lang.Class<?> refc, java.lang.String name, java.lang.invoke.MethodType type)
                throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException {
            throw new RuntimeException("Stub!");
        }

        private java.lang.invoke.MethodHandle findVirtualForMH(
                java.lang.String name, java.lang.invoke.MethodType type) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.invoke.MethodHandle findVirtualForVH(
                java.lang.String name, java.lang.invoke.MethodType type) {
            throw new RuntimeException("Stub!");
        }

        private static java.lang.invoke.MethodHandle createMethodHandle(
                java.lang.reflect.Method method,
                int handleKind,
                java.lang.invoke.MethodType methodType) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle findVirtual(
                java.lang.Class<?> refc, java.lang.String name, java.lang.invoke.MethodType type)
                throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle findConstructor(
                java.lang.Class<?> refc, java.lang.invoke.MethodType type)
                throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException {
            throw new RuntimeException("Stub!");
        }

        private java.lang.invoke.MethodHandle createMethodHandleForConstructor(
                java.lang.reflect.Constructor constructor) {
            throw new RuntimeException("Stub!");
        }

        private static java.lang.invoke.MethodType initMethodType(
                java.lang.invoke.MethodType constructorType) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle findSpecial(
                java.lang.Class<?> refc,
                java.lang.String name,
                java.lang.invoke.MethodType type,
                java.lang.Class<?> specialCaller)
                throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException {
            throw new RuntimeException("Stub!");
        }

        private java.lang.invoke.MethodHandle findSpecial(
                java.lang.reflect.Method method,
                java.lang.invoke.MethodType type,
                java.lang.Class<?> refc,
                java.lang.Class<?> specialCaller)
                throws java.lang.IllegalAccessException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle findGetter(
                java.lang.Class<?> refc, java.lang.String name, java.lang.Class<?> type)
                throws java.lang.IllegalAccessException, java.lang.NoSuchFieldException {
            throw new RuntimeException("Stub!");
        }

        private java.lang.invoke.MethodHandle findAccessor(
                java.lang.Class<?> refc, java.lang.String name, java.lang.Class<?> type, int kind)
                throws java.lang.IllegalAccessException, java.lang.NoSuchFieldException {
            throw new RuntimeException("Stub!");
        }

        private java.lang.invoke.MethodHandle findAccessor(
                java.lang.reflect.Field field,
                java.lang.Class<?> refc,
                java.lang.Class<?> type,
                int kind,
                boolean performAccessChecks)
                throws java.lang.IllegalAccessException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle findSetter(
                java.lang.Class<?> refc, java.lang.String name, java.lang.Class<?> type)
                throws java.lang.IllegalAccessException, java.lang.NoSuchFieldException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.VarHandle findVarHandle(
                java.lang.Class<?> recv, java.lang.String name, java.lang.Class<?> type)
                throws java.lang.IllegalAccessException, java.lang.NoSuchFieldException {
            throw new RuntimeException("Stub!");
        }

        private java.lang.reflect.Field findFieldOfType(
                java.lang.Class<?> refc, java.lang.String name, java.lang.Class<?> type)
                throws java.lang.NoSuchFieldException {
            throw new RuntimeException("Stub!");
        }

        private void commonFieldChecks(
                java.lang.reflect.Field field,
                java.lang.Class<?> refc,
                java.lang.Class<?> type,
                boolean isStatic,
                boolean performAccessChecks)
                throws java.lang.IllegalAccessException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle findStaticGetter(
                java.lang.Class<?> refc, java.lang.String name, java.lang.Class<?> type)
                throws java.lang.IllegalAccessException, java.lang.NoSuchFieldException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle findStaticSetter(
                java.lang.Class<?> refc, java.lang.String name, java.lang.Class<?> type)
                throws java.lang.IllegalAccessException, java.lang.NoSuchFieldException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.VarHandle findStaticVarHandle(
                java.lang.Class<?> decl, java.lang.String name, java.lang.Class<?> type)
                throws java.lang.IllegalAccessException, java.lang.NoSuchFieldException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle bind(
                java.lang.Object receiver, java.lang.String name, java.lang.invoke.MethodType type)
                throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle unreflect(java.lang.reflect.Method m)
                throws java.lang.IllegalAccessException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle unreflectSpecial(
                java.lang.reflect.Method m, java.lang.Class<?> specialCaller)
                throws java.lang.IllegalAccessException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle unreflectConstructor(
                java.lang.reflect.Constructor<?> c) throws java.lang.IllegalAccessException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle unreflectGetter(java.lang.reflect.Field f)
                throws java.lang.IllegalAccessException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandle unreflectSetter(java.lang.reflect.Field f)
                throws java.lang.IllegalAccessException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.VarHandle unreflectVarHandle(java.lang.reflect.Field f)
                throws java.lang.IllegalAccessException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.invoke.MethodHandleInfo revealDirect(
                java.lang.invoke.MethodHandle target) {
            throw new RuntimeException("Stub!");
        }

        private boolean hasPrivateAccess() {
            throw new RuntimeException("Stub!");
        }

        void checkAccess(
                java.lang.Class<?> refc,
                java.lang.Class<?> defc,
                int mods,
                java.lang.String methName)
                throws java.lang.IllegalAccessException {
            throw new RuntimeException("Stub!");
        }

        java.lang.String accessFailedMessage(
                java.lang.Class<?> refc, java.lang.Class<?> defc, int mods) {
            throw new RuntimeException("Stub!");
        }

        private void checkSpecialCaller(java.lang.Class<?> specialCaller)
                throws java.lang.IllegalAccessException {
            throw new RuntimeException("Stub!");
        }

        private void throwMakeAccessException(java.lang.String message, java.lang.Object from)
                throws java.lang.IllegalAccessException {
            throw new RuntimeException("Stub!");
        }

        private void checkReturnType(
                java.lang.reflect.Method method, java.lang.invoke.MethodType methodType)
                throws java.lang.NoSuchMethodException {
            throw new RuntimeException("Stub!");
        }

        private static final int ALL_MODES = 15; // 0xf

        static final java.lang.invoke.MethodHandles.Lookup IMPL_LOOKUP;

        static {
            IMPL_LOOKUP = null;
        }

        public static final int PACKAGE = 8; // 0x8

        public static final int PRIVATE = 2; // 0x2

        public static final int PROTECTED = 4; // 0x4

        public static final int PUBLIC = 1; // 0x1

        static final java.lang.invoke.MethodHandles.Lookup PUBLIC_LOOKUP;

        static {
            PUBLIC_LOOKUP = null;
        }

        private final int allowedModes;

        {
            allowedModes = 0;
        }

        private final java.lang.Class<?> lookupClass;

        {
            lookupClass = null;
        }
    }
}
