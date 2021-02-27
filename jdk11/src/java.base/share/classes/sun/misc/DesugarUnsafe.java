/*
 * Copyright (c) 2000, 2019, Oracle and/or its affiliates. All rights reserved.
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

package sun.misc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * A collection of methods for performing low-level, unsafe operations.
 * Although the class and all methods are public, use of this class is
 * limited because only trusted code can obtain instances of it.
 *
 * <em>Note:</em> It is the resposibility of the caller to make sure
 * arguments are checked before methods of this class are
 * called. While some rudimentary checks are performed on the input,
 * the checks are best effort and when performance is an overriding
 * priority, as when methods of this class are optimized by the
 * runtime compiler, some or all checks (if any) may be elided. Hence,
 * the caller must not rely on the checks and corresponding
 * exceptions!
 *
 * @author John R. Rose
 * @see #getUnsafe
 */

// For desugar: JDK11 wrapper to access to Unsafe
public final class DesugarUnsafe {

    private static final DesugarUnsafe theUnsafeWrapper;
    static {
        Field field = getUnsafeField();
        field.setAccessible(true);
        try {
            theUnsafeWrapper = new DesugarUnsafe((Unsafe) field.get(null));
        } catch (IllegalAccessException e) {
            throw new AssertionError("Couldn't get the Unsafe", e);
        }
    }

    // For desugar: the Unsafe delegate.
    // private static final Unsafe theUnsafe = new Unsafe();
    private final Unsafe theUnsafe;

    DesugarUnsafe(Unsafe theUnsafe) {
        this.theUnsafe = theUnsafe;
    }

    private static Field getUnsafeField() {
        try {
            return Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            for (Field f : Unsafe.class.getDeclaredFields()) {
                if (Modifier.isStatic(f.getModifiers())
                    && Unsafe.class.isAssignableFrom(f.getType())) {
                    return f;
                }
            }
            throw new AssertionError("Couldn't find the Unsafe", e);
        }
    }

    /**
     * Provides the caller with the capability of performing unsafe
     * operations.
     *
     * <p>The returned {@code Unsafe} object should be carefully guarded
     * by the caller, since it can be used to read and write data at arbitrary
     * memory addresses.  It must never be passed to untrusted code.
     *
     * <p>Most methods in this class are very low-level, and correspond to a
     * small number of hardware instructions (on typical machines).  Compilers
     * are encouraged to optimize these methods accordingly.
     *
     * <p>Here is a suggested idiom for using unsafe operations:
     *
     * <pre> {@code
     * class MyTrustedClass {
     *   private static final Unsafe unsafe = Unsafe.getUnsafe();
     *   ...
     *   private long myCountAddress = ...;
     *   public int getCount() { return unsafe.getByte(myCountAddress); }
     * }}</pre>
     *
     * (It may assist compilers to make the local variable {@code final}.)
     */
    public static DesugarUnsafe getUnsafe() {
        // Class<?> caller = Reflection.getCallerClass();
        // if (!VM.isSystemDomainLoader(caller.getClassLoader()))
        //     throw new SecurityException("Unsafe");
        return theUnsafeWrapper;
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
    public int getAndAddInt(Object o, long offset, int delta) {
        int v;
        do {
            v = theUnsafe.getIntVolatile(o, offset);
        } while (!theUnsafe.compareAndSwapInt(o, offset, v, v + delta));
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
    public long getAndAddLong(Object o, long offset, long delta) {
        long v;
        do {
            v = theUnsafe.getLongVolatile(o, offset);
        } while (!theUnsafe.compareAndSwapLong(o, offset, v, v + delta));
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
    public int getAndSetInt(Object o, long offset, int newValue) {
        int v;
        do {
            v = theUnsafe.getIntVolatile(o, offset);
        } while (!theUnsafe.compareAndSwapInt(o, offset, v, newValue));
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
    public long getAndSetLong(Object o, long offset, long newValue) {
        long v;
        do {
            v = theUnsafe.getLongVolatile(o, offset);
        } while (!theUnsafe.compareAndSwapLong(o, offset, v, newValue));
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
    public Object getAndSetObject(Object o, long offset, Object newValue) {
        Object v;
        do {
            v = theUnsafe.getObjectVolatile(o, offset);
        } while (!theUnsafe.compareAndSwapObject(o, offset, v, newValue));
        return v;
    }

    /**
     * Reports the location of a given field in the storage allocation of its
     * class.  Do not expect to perform any sort of arithmetic on this offset;
     * it is just a cookie which is passed to the unsafe heap memory accessors.
     *
     * <p>Any given field will always have the same offset and base, and no
     * two distinct fields of the same class will ever have the same offset
     * and base.
     *
     * <p>As of 1.4.1, offsets for fields are represented as long values,
     * although the Sun JVM does not use the most significant 32 bits.
     * However, JVM implementations which store static fields at absolute
     * addresses can use long offsets and null base pointers to express
     * the field locations in a form usable by {@link #getInt(Object,long)}.
     * Therefore, code which will be ported to such JVMs on 64-bit platforms
     * must preserve all bits of static field offsets.
     * @see #getInt(Object, long)
     */
    public long objectFieldOffset(Field f) {
        return theUnsafe.objectFieldOffset(f);
    }

    /**
     * Reports the location of the field with a given name in the storage
     * allocation of its class.
     *
     * @throws NullPointerException if any parameter is {@code null}.
     * @throws InternalError if there is no field named {@code name} declared
     *         in class {@code c}, i.e., if {@code c.getDeclaredField(name)}
     *         would throw {@code java.lang.NoSuchFieldException}.
     *
     * @see #objectFieldOffset(Field)
     */
    public long objectFieldOffset(Class<?> c, String name) {
        if (c == null || name == null) {
            throw new NullPointerException();
        }

        try {
            return objectFieldOffset(c.getDeclaredField(name));
        } catch (NoSuchFieldException e) {
            throw new AssertionError("Cannot find field:", e);
        }
    }

    /**
     * Reports the offset of the first element in the storage allocation of a
     * given array class.  If {@link #arrayIndexScale} returns a non-zero value
     * for the same class, you may use that scale factor, together with this
     * base offset, to form new offsets to access elements of arrays of the
     * given class.
     *
     * @see #getInt(Object, long)
     * @see #putInt(Object, long, int)
     */
    public int arrayBaseOffset(Class<?> arrayClass) {
        return theUnsafe.arrayBaseOffset(arrayClass);
    }

    /**
     * Reports the scale factor for addressing elements in the storage
     * allocation of a given array class.  However, arrays of "narrow" types
     * will generally not work properly with accessors like {@link
     * #getByte(Object, long)}, so the scale factor for such classes is reported
     * as zero.
     *
     * @see #arrayBaseOffset
     * @see #getInt(Object, long)
     * @see #putInt(Object, long, int)
     */
    public int arrayIndexScale(Class<?> arrayClass) {
        return theUnsafe.arrayIndexScale(arrayClass);
    }

    /** Acquire version of {@link Unsafe#getObjectVolatile(Object, long)} */
    public Object getObjectAcquire(Object o, long offset) {
        return theUnsafe.getObjectVolatile(o, offset);
    }

    /** Release version of {@link Unsafe#putObjectVolatile(Object, long, Object)} */
    public void putObjectRelease(Object o, long offset, Object x) {
        theUnsafe.putObjectVolatile(o, offset, x);
    }

    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public boolean compareAndSetInt(Object o, long offset, int expected, int x) {
        return theUnsafe.compareAndSwapInt(o, offset, expected, x);
    }

    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public boolean compareAndSetLong(Object o, long offset, long expected, long x) {
        return theUnsafe.compareAndSwapLong(o, offset, expected, x);
    }

    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public boolean compareAndSetObject(Object o, long offset, Object expected, Object x) {
        return theUnsafe.compareAndSwapObject(o, offset, expected, x);
    }
}
