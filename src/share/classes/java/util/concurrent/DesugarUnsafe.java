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

package java.util.concurrent;

import java.lang.reflect.*;


/**
 * A collection of methods for performing low-level, unsafe operations.
 * Although the class and all methods are public, use of this class is
 * limited because only trusted code can obtain instances of it.
 *
 * @author John R. Rose
 * @see #getUnsafe
 */
// For desugar: JDK8 additions and access to Unsafe
final class DesugarUnsafe {

    // For desugar: avoid protected Unsafe.getUnsafe
    // private static final Unsafe theUnsafe = new Unsafe();
    private static final sun.misc.Unsafe theUnsafe;
    static {
        Field field = getField();
        field.setAccessible(true);
        try {
            theUnsafe = (sun.misc.Unsafe) field.get(null);
        } catch (IllegalAccessException e) {
            throw new Error("Couldn't get the Unsafe", e);
        }
    }

    private static Field getField() {
        try {
            return sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            for (Field f : sun.misc.Unsafe.class.getDeclaredFields()) {
                if (Modifier.isStatic(f.getModifiers())
                    && sun.misc.Unsafe.class.isAssignableFrom(f.getType())) {
                    return f;
                }
            }
            throw new Error("Couldn't find the Unsafe", e);
        }
    }

    /**
     * Provides the caller with the capability of performing unsafe
     * operations.
     *
     * <p> The returned <code>Unsafe</code> object should be carefully guarded
     * by the caller, since it can be used to read and write data at arbitrary
     * memory addresses.  It must never be passed to untrusted code.
     *
     * <p> Most methods in this class are very low-level, and correspond to a
     * small number of hardware instructions (on typical machines).  Compilers
     * are encouraged to optimize these methods accordingly.
     *
     * <p> Here is a suggested idiom for using unsafe operations:
     *
     * <blockquote><pre>
     * class MyTrustedClass {
     *   private static final Unsafe unsafe = Unsafe.getUnsafe();
     *   ...
     *   private long myCountAddress = ...;
     *   public int getCount() { return unsafe.getByte(myCountAddress); }
     * }
     * </pre></blockquote>
     *
     * (It may assist compilers to make the local variable be
     * <code>final</code>.)
     *
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPropertiesAccess</code> method doesn't allow
     *             access to the system properties.
     */
    // For desugar: avoid protected Unsafe.getUnsafe
    // @CallerSensitive
    public static sun.misc.Unsafe getUnsafe() {
        // Class<?> caller = Reflection.getCallerClass();
        // if (!VM.isSystemDomainLoader(caller.getClassLoader()))
        //     throw new SecurityException("Unsafe");
        return theUnsafe;
    }

    // The following contain CAS-based Java implementations used on
    // platforms not supporting native instructions

    /**
     * Atomically adds the given value to the current value of a field
     * or array element within the given object <code>o</code>
     * at the given <code>offset</code>.
     *
     * @param o object/array to update the field/element in
     * @param offset field/element offset
     * @param delta the value to add
     * @return the previous value
     * @since 1.8
     */
    // For desugar: static so method can exist outside original class
    public static final int getAndAddInt(sun.misc.Unsafe unsafe, Object o, long offset, int delta) {
        int v;
        do {
            v = unsafe.getIntVolatile(o, offset);
        } while (!unsafe.compareAndSwapInt(o, offset, v, v + delta));
        return v;
    }

    /**
     * Atomically adds the given value to the current value of a field
     * or array element within the given object <code>o</code>
     * at the given <code>offset</code>.
     *
     * @param o object/array to update the field/element in
     * @param offset field/element offset
     * @param delta the value to add
     * @return the previous value
     * @since 1.8
     */
    // For desugar: static so method can exist outside original class
    public static final long getAndAddLong(sun.misc.Unsafe unsafe, Object o, long offset, long delta) {
        long v;
        do {
            v = unsafe.getLongVolatile(o, offset);
        } while (!unsafe.compareAndSwapLong(o, offset, v, v + delta));
        return v;
    }

    /**
     * Atomically exchanges the given value with the current value of
     * a field or array element within the given object <code>o</code>
     * at the given <code>offset</code>.
     *
     * @param o object/array to update the field/element in
     * @param offset field/element offset
     * @param newValue new value
     * @return the previous value
     * @since 1.8
     */
    // For desugar: static so method can exist outside original class
    public static final int getAndSetInt(sun.misc.Unsafe unsafe, Object o, long offset, int newValue) {
        int v;
        do {
            v = unsafe.getIntVolatile(o, offset);
        } while (!unsafe.compareAndSwapInt(o, offset, v, newValue));
        return v;
    }

    /**
     * Atomically exchanges the given value with the current value of
     * a field or array element within the given object <code>o</code>
     * at the given <code>offset</code>.
     *
     * @param o object/array to update the field/element in
     * @param offset field/element offset
     * @param newValue new value
     * @return the previous value
     * @since 1.8
     */
    // For desugar: static so method can exist outside original class
    public static final long getAndSetLong(sun.misc.Unsafe unsafe, Object o, long offset, long newValue) {
        long v;
        do {
            v = unsafe.getLongVolatile(o, offset);
        } while (!unsafe.compareAndSwapLong(o, offset, v, newValue));
        return v;
    }

    /**
     * Atomically exchanges the given reference value with the current
     * reference value of a field or array element within the given
     * object <code>o</code> at the given <code>offset</code>.
     *
     * @param o object/array to update the field/element in
     * @param offset field/element offset
     * @param newValue new value
     * @return the previous value
     * @since 1.8
     */
    // For desugar: static so method can exist outside original class
    public static final Object getAndSetObject(sun.misc.Unsafe unsafe, Object o, long offset, Object newValue) {
        Object v;
        do {
            v = unsafe.getObjectVolatile(o, offset);
        } while (!unsafe.compareAndSwapObject(o, offset, v, newValue));
        return v;
    }
}
