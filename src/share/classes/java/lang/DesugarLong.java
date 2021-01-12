/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.math.*;


/**
 * The {@code Long} class wraps a value of the primitive type {@code
 * long} in an object. An object of type {@code Long} contains a
 * single field whose type is {@code long}.
 *
 * <p> In addition, this class provides several methods for converting
 * a {@code long} to a {@code String} and a {@code String} to a {@code
 * long}, as well as other constants and methods useful when dealing
 * with a {@code long}.
 *
 * <p>Implementation note: The implementations of the "bit twiddling"
 * methods (such as {@link #highestOneBit(long) highestOneBit} and
 * {@link #numberOfTrailingZeros(long) numberOfTrailingZeros}) are
 * based on material from Henry S. Warren, Jr.'s <i>Hacker's
 * Delight</i>, (Addison Wesley, 2002).
 *
 * @author  Lee Boynton
 * @author  Arthur van Hoff
 * @author  Josh Bloch
 * @author  Joseph D. Darcy
 * @since   JDK1.0
 */
// for desugar: select static members introduced with 1.7 and 1.8
public final class DesugarLong {

    private DesugarLong() {} // for desugar: prevent instantiation

    /**
     * Return a BigInteger equal to the unsigned value of the
     * argument.
     */
    private static BigInteger toUnsignedBigInteger(long i) {
        if (i >= 0L)
            return BigInteger.valueOf(i);
        else {
            int upper = (int) (i >>> 32);
            int lower = (int) i;

            // return (upper << 32) + lower
            return (BigInteger.valueOf(Integer.toUnsignedLong(upper))).shiftLeft(32).
                add(BigInteger.valueOf(Integer.toUnsignedLong(lower)));
        }
    }

    /**
     * Returns a hash code for a {@code long} value; compatible with
     * {@code Long.hashCode()}.
     *
     * @param value the value to hash
     * @return a hash code value for a {@code long} value.
     * @since 1.8
     */
    public static int hashCode(long value) {
        return (int)(value ^ (value >>> 32));
    }

    /**
     * Compares two {@code long} values numerically.
     * The value returned is identical to what would be returned by:
     * <pre>
     *    Long.valueOf(x).compareTo(Long.valueOf(y))
     * </pre>
     *
     * @param  x the first {@code long} to compare
     * @param  y the second {@code long} to compare
     * @return the value {@code 0} if {@code x == y};
     *         a value less than {@code 0} if {@code x < y}; and
     *         a value greater than {@code 0} if {@code x > y}
     * @since 1.7
     */
    public static int compare(long x, long y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    /**
     * Compares two {@code long} values numerically treating the values
     * as unsigned.
     *
     * @param  x the first {@code long} to compare
     * @param  y the second {@code long} to compare
     * @return the value {@code 0} if {@code x == y}; a value less
     *         than {@code 0} if {@code x < y} as unsigned values; and
     *         a value greater than {@code 0} if {@code x > y} as
     *         unsigned values
     * @since 1.8
     */
    public static int compareUnsigned(long x, long y) {
        return compare(x + Long.MIN_VALUE, y + Long.MIN_VALUE);
    }

    /**
     * Returns the unsigned quotient of dividing the first argument by
     * the second where each argument and the result is interpreted as
     * an unsigned value.
     *
     * <p>Note that in two's complement arithmetic, the three other
     * basic arithmetic operations of add, subtract, and multiply are
     * bit-wise identical if the two operands are regarded as both
     * being signed or both being unsigned.  Therefore separate {@code
     * addUnsigned}, etc. methods are not provided.
     *
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned quotient of the first argument divided by
     * the second argument
     * @see #remainderUnsigned
     * @since 1.8
     */
    public static long divideUnsigned(long dividend, long divisor) {
        if (divisor < 0L) { // signed comparison
            // Answer must be 0 or 1 depending on relative magnitude
            // of dividend and divisor.
            return (compareUnsigned(dividend, divisor)) < 0 ? 0L :1L;
        }

        if (dividend > 0) //  Both inputs non-negative
            return dividend/divisor;
        else {
            /*
             * For simple code, leveraging BigInteger.  Longer and faster
             * code written directly in terms of operations on longs is
             * possible; see "Hacker's Delight" for divide and remainder
             * algorithms.
             */
            return toUnsignedBigInteger(dividend).
                divide(toUnsignedBigInteger(divisor)).longValue();
        }
    }

    /**
     * Returns the unsigned remainder from dividing the first argument
     * by the second where each argument and the result is interpreted
     * as an unsigned value.
     *
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned remainder of the first argument divided by
     * the second argument
     * @see #divideUnsigned
     * @since 1.8
     */
    public static long remainderUnsigned(long dividend, long divisor) {
        if (dividend > 0 && divisor > 0) { // signed comparisons
            return dividend % divisor;
        } else {
            if (compareUnsigned(dividend, divisor) < 0) // Avoid explicit check for 0 divisor
                return dividend;
            else
                return toUnsignedBigInteger(dividend).
                    remainder(toUnsignedBigInteger(divisor)).longValue();
        }
    }

    /**
     * Adds two {@code long} values together as per the + operator.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the sum of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static long sum(long a, long b) {
        return a + b;
    }

    /**
     * Returns the greater of two {@code long} values
     * as if by calling {@link Math#max(long, long) Math.max}.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the greater of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static long max(long a, long b) {
        return Math.max(a, b);
    }

    /**
     * Returns the smaller of two {@code long} values
     * as if by calling {@link Math#min(long, long) Math.min}.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the smaller of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static long min(long a, long b) {
        return Math.min(a, b);
    }
}
