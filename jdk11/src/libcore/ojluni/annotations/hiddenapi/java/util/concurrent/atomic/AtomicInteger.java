/*
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

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent.atomic;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class AtomicInteger extends java.lang.Number implements java.io.Serializable {

    public AtomicInteger(int initialValue) {
        throw new RuntimeException("Stub!");
    }

    public AtomicInteger() {
        throw new RuntimeException("Stub!");
    }

    public final int get() {
        throw new RuntimeException("Stub!");
    }

    public final void set(int newValue) {
        throw new RuntimeException("Stub!");
    }

    public final void lazySet(int newValue) {
        throw new RuntimeException("Stub!");
    }

    public final int getAndSet(int newValue) {
        throw new RuntimeException("Stub!");
    }

    public final boolean compareAndSet(int expect, int update) {
        throw new RuntimeException("Stub!");
    }

    public final boolean weakCompareAndSet(int expect, int update) {
        throw new RuntimeException("Stub!");
    }

    public final int getAndIncrement() {
        throw new RuntimeException("Stub!");
    }

    public final int getAndDecrement() {
        throw new RuntimeException("Stub!");
    }

    public final int getAndAdd(int delta) {
        throw new RuntimeException("Stub!");
    }

    public final int incrementAndGet() {
        throw new RuntimeException("Stub!");
    }

    public final int decrementAndGet() {
        throw new RuntimeException("Stub!");
    }

    public final int addAndGet(int delta) {
        throw new RuntimeException("Stub!");
    }

    public final int getAndUpdate(java.util.function.IntUnaryOperator updateFunction) {
        throw new RuntimeException("Stub!");
    }

    public final int updateAndGet(java.util.function.IntUnaryOperator updateFunction) {
        throw new RuntimeException("Stub!");
    }

    public final int getAndAccumulate(
            int x, java.util.function.IntBinaryOperator accumulatorFunction) {
        throw new RuntimeException("Stub!");
    }

    public final int accumulateAndGet(
            int x, java.util.function.IntBinaryOperator accumulatorFunction) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public int intValue() {
        throw new RuntimeException("Stub!");
    }

    public long longValue() {
        throw new RuntimeException("Stub!");
    }

    public float floatValue() {
        throw new RuntimeException("Stub!");
    }

    public double doubleValue() {
        throw new RuntimeException("Stub!");
    }

    private static final sun.misc.Unsafe U;

    static {
        U = null;
    }

    private static final long VALUE;

    static {
        VALUE = 0;
    }

    private static final long serialVersionUID = 6214790243416807050L; // 0x563f5ecc8c6c168aL

    @UnsupportedAppUsage
    private volatile int value;
}
