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
import jdk.internal.vm.annotation.ForceInline;

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
   * Atomically adds the given value to the current value of a field or array element within the
   * given object <code>o</code> at the given <code>offset</code>.
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

  /**
   * Allocates a new block of native memory, of the given size in bytes. The contents of the memory
   * are uninitialized; they will generally be garbage. The resulting native pointer will never be
   * zero, and will be aligned for all value types. Dispose of this memory by calling {@link
   * #freeMemory}, or resize it with {@link #reallocateMemory}.
   *
   * @throws IllegalArgumentException if the size is negative or too large for the native size_t
   *     type
   * @throws OutOfMemoryError if the allocation is refused by the system
   * @see #getByte(long)
   * @see #putByte(long, byte)
   */
  public long allocateMemory(long bytes) {
    return theUnsafe.allocateMemory(bytes);
  }

  /**
   * Disposes of a block of native memory, as obtained from {@link #allocateMemory} or {@link
   * #reallocateMemory}. The address passed to this method may be null, in which case no action is
   * taken.
   *
   * @see #allocateMemory
   */
  public void freeMemory(long address) {
    theUnsafe.allocateMemory(address);
  }

  /**
   * Sets all bytes in a given block of memory to a fixed value (usually zero). This provides a
   * <em>single-register</em> addressing mode, as discussed in {@link #getInt(Object,long)}.
   *
   * <p>Equivalent to <code>setMemory(null, address, bytes, value)</code>.
   */
  public void setMemory(long address, long bytes, byte value) {
    theUnsafe.setMemory(address, bytes, value);
  }

  /**
   * Sets all bytes in a given block of memory to a copy of another block. This provides a
   * <em>single-register</em> addressing mode, as discussed in {@link #getInt(Object,long)}.
   *
   * <p>Equivalent to <code>copyMemory(null, srcAddress, null, destAddress, bytes)</code>.
   */
  public void copyMemory(long srcAddr, long dstAddr, long bytes) {
    theUnsafe.copyMemory(srcAddr, dstAddr, bytes);
  }

  /**
   * Sets all bytes in a given block of memory to a copy of another
   * block.
   *
   * <p>This method determines each block's base address by means of two parameters,
   * and so it provides (in effect) a <em>double-register</em> addressing mode,
   * as discussed in {@link #getInt(Object,long)}.  When the object reference is null,
   * the offset supplies an absolute base address.
   *
   * <p>The transfers are in coherent (atomic) units of a size determined
   * by the address and length parameters.  If the effective addresses and
   * length are all even modulo 8, the transfer takes place in 'long' units.
   * If the effective addresses and length are (resp.) even modulo 4 or 2,
   * the transfer takes place in units of 'int' or 'short'.
   *
   * @since 1.7
   */
  public void copyMemory(Object srcBase, long srcOffset,
                         Object destBase, long destOffset,
                         long bytes) {
    // For desugar: custom implementation.
    // TODO(b/197162880): Look into an efficient solution instead of byte-to-byte copying.
    for (int i = 0; i < bytes; i++) {
      byte value = srcBase == null ? getByte(i + srcOffset) : getByte(srcBase, i + srcOffset);
      if (destBase == null) {
        putByte(i + destOffset, value);
      } else {
        putByte(destBase, i + destOffset, value);
      }
    }
  }

  /**
   * Fetches a value from a given Java variable. More specifically, fetches a field or array element
   * within the given object {@code o} at the given offset, or (if {@code o} is null) from the
   * memory address whose numerical value is the given offset.
   *
   * <p>The results are undefined unless one of the following cases is true:
   *
   * <ul>
   *   <li>The offset was obtained from {@link #objectFieldOffset} on the {@link Field} of some Java
   *       field and the object referred to by {@code o} is of a class compatible with that field's
   *       class.
   *   <li>The offset and object reference {@code o} (either null or non-null) were both obtained
   *       via {@link #staticFieldOffset} and {@link #staticFieldBase} (respectively) from the
   *       reflective {@link Field} representation of some Java field.
   *   <li>The object referred to by {@code o} is an array, and the offset is an integer of the form
   *       {@code B+N*S}, where {@code N} is a valid index into the array, and {@code B} and {@code
   *       S} are the values obtained by {@link #arrayBaseOffset} and {@link #arrayIndexScale}
   *       (respectively) from the array's class. The value referred to is the {@code N}<em>th</em>
   *       element of the array.
   * </ul>
   *
   * <p>If one of the above cases is true, the call references a specific Java variable (field or
   * array element). However, the results are undefined if that variable is not in fact of the type
   * returned by this method.
   *
   * <p>This method refers to a variable by means of two parameters, and so it provides (in effect)
   * a <em>double-register</em> addressing mode for Java variables. When the object reference is
   * null, this method uses its offset as an absolute address. This is similar in operation to
   * methods such as {@link #getInt(long)}, which provide (in effect) a <em>single-register</em>
   * addressing mode for non-Java variables. However, because Java variables may have a different
   * layout in memory from non-Java variables, programmers should not assume that these two
   * addressing modes are ever equivalent. Also, programmers should remember that offsets from the
   * double-register addressing mode cannot be portably confused with longs used in the
   * single-register addressing mode.
   *
   * @param o Java heap object in which the variable resides, if any, else null
   * @param offset indication of where the variable resides in a Java heap object, if any, else a
   *     memory address locating the variable statically
   * @return the value fetched from the indicated Java variable
   * @throws RuntimeException No defined exceptions are thrown, not even {@link
   *     NullPointerException}
   */
  @ForceInline
  public int getInt(Object o, long offset) {
    return theUnsafe.getInt(o, offset);
  }

  /**
   * Stores a value into a given Java variable.
   *
   * <p>The first two parameters are interpreted exactly as with {@link #getInt(Object, long)} to
   * refer to a specific Java variable (field or array element). The given value is stored into that
   * variable.
   *
   * <p>The variable must be of the same type as the method parameter {@code x}.
   *
   * @param o Java heap object in which the variable resides, if any, else null
   * @param offset indication of where the variable resides in a Java heap object, if any, else a
   *     memory address locating the variable statically
   * @param x the value to store into the indicated Java variable
   * @throws RuntimeException No defined exceptions are thrown, not even {@link
   *     NullPointerException}
   */
  @ForceInline
  public void putInt(Object o, long offset, int x) {
    theUnsafe.putInt(o, offset, x);
  }

  /**
   * Fetches a reference value from a given Java variable.
   *
   * @see #getInt(Object, long)
   */
  @ForceInline
  public Object getObject(Object o, long offset) {
    return theUnsafe.getObject(o, offset);
  }

  /**
   * Stores a reference value into a given Java variable.
   *
   * <p>Unless the reference {@code x} being stored is either null or matches the field type, the
   * results are undefined. If the reference {@code o} is non-null, card marks or other store
   * barriers for that object (if the VM requires them) are updated.
   *
   * @see #putInt(Object, long, int)
   */
  @ForceInline
  public void putObject(Object o, long offset, Object x) {
    theUnsafe.putObject(o, offset, x);
  }

  /** @see #getInt(Object, long) */
  @ForceInline
  public boolean getBoolean(Object o, long offset) {
    return theUnsafe.getBoolean(o, offset);
  }

  /** @see #putInt(Object, long, int) */
  @ForceInline
  public void putBoolean(Object o, long offset, boolean x) {
    theUnsafe.putBoolean(o, offset, x);
  }

  /** @see #getInt(Object, long) */
  @ForceInline
  public byte getByte(Object o, long offset) {
    return theUnsafe.getByte(o, offset);
  }

  /** @see #putInt(Object, long, int) */
  @ForceInline
  public void putByte(Object o, long offset, byte x) {
    theUnsafe.putByte(o, offset, x);
  }

  /** @see #getInt(Object, long) */
  @ForceInline
  public short getShort(Object o, long offset) {
    return theUnsafe.getShort(o, offset);
  }

  /** @see #putInt(Object, long, int) */
  @ForceInline
  public void putShort(Object o, long offset, short x) {
    theUnsafe.putShort(o, offset, x);
  }

  /** @see #getInt(Object, long) */
  @ForceInline
  public char getChar(Object o, long offset) {
    return theUnsafe.getChar(o, offset);
  }

  /** @see #putInt(Object, long, int) */
  @ForceInline
  public void putChar(Object o, long offset, char x) {
    theUnsafe.putChar(o, offset, x);
  }

  /** @see #getInt(Object, long) */
  @ForceInline
  public long getLong(Object o, long offset) {
    return theUnsafe.getLong(o, offset);
  }

  /** @see #putInt(Object, long, int) */
  @ForceInline
  public void putLong(Object o, long offset, long x) {
    theUnsafe.putLong(o, offset, x);
  }

  /** @see #getInt(Object, long) */
  @ForceInline
  public float getFloat(Object o, long offset) {
    return theUnsafe.getFloat(o, offset);
  }

  /** @see #putInt(Object, long, int) */
  @ForceInline
  public void putFloat(Object o, long offset, float x) {
    theUnsafe.putFloat(o, offset, x);
  }

  /** @see #getInt(Object, long) */
  @ForceInline
  public double getDouble(Object o, long offset) {
    return theUnsafe.getDouble(o, offset);
  }

  /** @see #putInt(Object, long, int) */
  @ForceInline
  public void putDouble(Object o, long offset, double x) {
    theUnsafe.putDouble(o, offset, x);
  }

  /**
   * Fetches a value from a given memory address. If the address is zero, or does not point into a
   * block obtained from {@link #allocateMemory}, the results are undefined.
   *
   * @see #allocateMemory
   */
  @ForceInline
  public byte getByte(long address) {
    return theUnsafe.getByte(address);
  }

  /**
   * Stores a value into a given memory address. If the address is zero, or does not point into a
   * block obtained from {@link #allocateMemory}, the results are undefined.
   *
   * @see #getByte(long)
   */
  @ForceInline
  public void putByte(long address, byte x) {
    theUnsafe.putByte(address, x);
  }

  /** @see #getByte(long) */
  @ForceInline
  public short getShort(long address) {
    return theUnsafe.getShort(address);
  }

  /** @see #putByte(long, byte) */
  @ForceInline
  public void putShort(long address, short x) {
    theUnsafe.putShort(address, x);
  }

  /** @see #getByte(long) */
  @ForceInline
  public char getChar(long address) {
    return theUnsafe.getChar(address);
  }

  /** @see #putByte(long, byte) */
  @ForceInline
  public void putChar(long address, char x) {
    theUnsafe.putChar(address, x);
  }

  /** @see #getByte(long) */
  @ForceInline
  public int getInt(long address) {
    return theUnsafe.getInt(address);
  }

  /** @see #putByte(long, byte) */
  @ForceInline
  public void putInt(long address, int x) {
    theUnsafe.putInt(address, x);
  }

  /** @see #getByte(long) */
  @ForceInline
  public long getLong(long address) {
    return theUnsafe.getLong(address);
  }

  /** @see #putByte(long, byte) */
  @ForceInline
  public void putLong(long address, long x) {
    theUnsafe.putLong(address, x);
  }

  /** @see #getByte(long) */
  @ForceInline
  public float getFloat(long address) {
    return theUnsafe.getFloat(address);
  }

  /** @see #putByte(long, byte) */
  @ForceInline
  public void putFloat(long address, float x) {
    theUnsafe.putFloat(address, x);
  }

  /** @see #getByte(long) */
  @ForceInline
  public double getDouble(long address) {
    return theUnsafe.getDouble(address);
  }

  /** @see #putByte(long, byte) */
  @ForceInline
  public void putDouble(long address, double x) {
    theUnsafe.putDouble(address, x);
  }

  /**
   * Reports the size in bytes of a native pointer, as stored via {@link #putAddress}. This value
   * will be either 4 or 8. Note that the sizes of other primitive types (as stored in native memory
   * blocks) is determined fully by their information content.
   */
  @ForceInline
  public int addressSize() {
    return theUnsafe.addressSize();
  }

  /**
   * Reports the size in bytes of a native memory page (whatever that is). This value will always be
   * a power of two.
   */
  @ForceInline
  public int pageSize() {
    return theUnsafe.pageSize();
  }

  /**
   * Allocates an instance but does not run any constructor. Initializes the class if it has not yet
   * been.
   */
  @ForceInline
  public Object allocateInstance(Class<?> cls) throws InstantiationException {
    return theUnsafe.allocateInstance(cls);
  }

  /**
   * Atomically updates Java variable to {@code x} if it is currently holding {@code expected}.
   *
   * <p>This operation has memory semantics of a {@code volatile} read and write. Corresponds to C11
   * atomic_compare_exchange_strong.
   *
   * @return {@code true} if successful
   */
  @ForceInline
  public boolean compareAndSwapObject(Object o, long offset, Object expected, Object x) {
    return theUnsafe.compareAndSwapObject(o, offset, expected, x);
  }

  /**
   * Atomically updates Java variable to {@code x} if it is currently holding {@code expected}.
   *
   * <p>This operation has memory semantics of a {@code volatile} read and write. Corresponds to C11
   * atomic_compare_exchange_strong.
   *
   * @return {@code true} if successful
   */
  @ForceInline
  public boolean compareAndSwapInt(Object o, long offset, int expected, int x) {
    return theUnsafe.compareAndSwapInt(o, offset, expected, x);
  }

  /**
   * Atomically updates Java variable to {@code x} if it is currently holding {@code expected}.
   *
   * <p>This operation has memory semantics of a {@code volatile} read and write. Corresponds to C11
   * atomic_compare_exchange_strong.
   *
   * @return {@code true} if successful
   */
  @ForceInline
  public boolean compareAndSwapLong(Object o, long offset, long expected, long x) {
    return theUnsafe.compareAndSwapLong(o, offset, expected, x);
  }

  /**
   * Fetches a reference value from a given Java variable, with volatile load semantics. Otherwise
   * identical to {@link #getObject(Object, long)}
   */
  @ForceInline
  public Object getObjectVolatile(Object o, long offset) {
    return theUnsafe.getObjectVolatile(o, offset);
  }

  /**
   * Stores a reference value into a given Java variable, with volatile store semantics. Otherwise
   * identical to {@link #putObject(Object, long, Object)}
   */
  @ForceInline
  public void putObjectVolatile(Object o, long offset, Object x) {
    theUnsafe.putObjectVolatile(o, offset, x);
  }

  /** Volatile version of {@link #getInt(Object, long)} */
  @ForceInline
  public int getIntVolatile(Object o, long offset) {
    return theUnsafe.getIntVolatile(o, offset);
  }

  /** Volatile version of {@link #putInt(Object, long, int)} */
  @ForceInline
  public void putIntVolatile(Object o, long offset, int x) {
    theUnsafe.putIntVolatile(o, offset, x);
  }

  /** Volatile version of {@link #getLong(Object, long)} */
  @ForceInline
  public long getLongVolatile(Object o, long offset) {
    return theUnsafe.getLongVolatile(o, offset);
  }

  /** Volatile version of {@link #putLong(Object, long, long)} */
  @ForceInline
  public void putLongVolatile(Object o, long offset, long x) {
    theUnsafe.putLongVolatile(o, offset, x);
  }

  /**
   * Version of {@link #putObjectVolatile(Object, long, Object)} that does not guarantee immediate
   * visibility of the store to other threads. This method is generally only useful if the
   * underlying field is a Java volatile (or if an array cell, one that is otherwise only accessed
   * using volatile accesses).
   *
   * <p>Corresponds to C11 atomic_store_explicit(..., memory_order_release).
   */
  @ForceInline
  public void putOrderedObject(Object o, long offset, Object x) {
    theUnsafe.putOrderedObject(o, offset, x);
  }

  /** Ordered/Lazy version of {@link #putIntVolatile(Object, long, int)} */
  @ForceInline
  public void putOrderedInt(Object o, long offset, int x) {
    theUnsafe.putOrderedInt(o, offset, x);
  }

  /** Ordered/Lazy version of {@link #putLongVolatile(Object, long, long)} */
  @ForceInline
  public void putOrderedLong(Object o, long offset, long x) {
    theUnsafe.putOrderedLong(o, offset, x);
  }

  /**
   * Unblocks the given thread blocked on {@code park}, or, if it is not blocked, causes the
   * subsequent call to {@code park} not to block. Note: this operation is "unsafe" solely because
   * the caller must somehow ensure that the thread has not been destroyed. Nothing special is
   * usually required to ensure this when called from Java (in which there will ordinarily be a live
   * reference to the thread) but this is not nearly-automatically so when calling from native code.
   *
   * @param thread the thread to unpark.
   */
  @ForceInline
  public void unpark(Object thread) {
    theUnsafe.unpark(thread);
  }

  /**
   * Blocks current thread, returning when a balancing {@code unpark} occurs, or a balancing {@code
   * unpark} has already occurred, or the thread is interrupted, or, if not absolute and time is not
   * zero, the given time nanoseconds have elapsed, or if absolute, the given deadline in
   * milliseconds since Epoch has passed, or spuriously (i.e., returning for no "reason"). Note:
   * This operation is in the Unsafe class only because {@code unpark} is, so it would be strange to
   * place it elsewhere.
   */
  @ForceInline
  public void park(boolean isAbsolute, long time) {
    theUnsafe.park(isAbsolute, time);
  }

  /**
   * Ensures the given class has been initialized. This is often needed in conjunction with
   * obtaining the static field base of a class.
   */
  @ForceInline
  public void ensureClassInitialized(Class<?> c) {
    // For desugar: Use Android SDK implementation instead.
    try {
      Class.forName(c.getName());
    } catch (ClassNotFoundException e) {
      // Throw if FileDescriptor class is not found. Something wrong in runtime / libcore.
      throw new RuntimeException(e);
    }
  }

}
