/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class Random implements java.io.Serializable {

    public Random() {
        throw new RuntimeException("Stub!");
    }

    public Random(long seed) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(trackingBug = 172313849)
    private static long seedUniquifier() {
        throw new RuntimeException("Stub!");
    }

    private static long initialScramble(long seed) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setSeed(long seed) {
        throw new RuntimeException("Stub!");
    }

    protected int next(int bits) {
        throw new RuntimeException("Stub!");
    }

    public void nextBytes(byte[] bytes) {
        throw new RuntimeException("Stub!");
    }

    final long internalNextLong(long origin, long bound) {
        throw new RuntimeException("Stub!");
    }

    final int internalNextInt(int origin, int bound) {
        throw new RuntimeException("Stub!");
    }

    final double internalNextDouble(double origin, double bound) {
        throw new RuntimeException("Stub!");
    }

    public int nextInt() {
        throw new RuntimeException("Stub!");
    }

    public int nextInt(int bound) {
        throw new RuntimeException("Stub!");
    }

    public long nextLong() {
        throw new RuntimeException("Stub!");
    }

    public boolean nextBoolean() {
        throw new RuntimeException("Stub!");
    }

    public float nextFloat() {
        throw new RuntimeException("Stub!");
    }

    public double nextDouble() {
        throw new RuntimeException("Stub!");
    }

    public synchronized double nextGaussian() {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.IntStream ints(long streamSize) {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.IntStream ints() {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.IntStream ints(
            long streamSize, int randomNumberOrigin, int randomNumberBound) {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.IntStream ints(int randomNumberOrigin, int randomNumberBound) {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.LongStream longs(long streamSize) {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.LongStream longs() {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.LongStream longs(
            long streamSize, long randomNumberOrigin, long randomNumberBound) {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.LongStream longs(long randomNumberOrigin, long randomNumberBound) {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.DoubleStream doubles(long streamSize) {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.DoubleStream doubles() {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.DoubleStream doubles(
            long streamSize, double randomNumberOrigin, double randomNumberBound) {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.DoubleStream doubles(
            double randomNumberOrigin, double randomNumberBound) {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private synchronized void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void resetSeed(long seedVal) {
        throw new RuntimeException("Stub!");
    }

    static final java.lang.String BadBound = "bound must be positive";

    static final java.lang.String BadRange = "bound must be greater than origin";

    static final java.lang.String BadSize = "size must be non-negative";

    private static final double DOUBLE_UNIT = 1.1102230246251565E-16;

    private static final long addend = 11L; // 0xbL

    private boolean haveNextNextGaussian = false;

    private static final long mask = 281474976710655L; // 0xffffffffffffL

    private static final long multiplier = 25214903917L; // 0x5deece66dL

    private double nextNextGaussian;

    private final java.util.concurrent.atomic.AtomicLong seed;

    {
        seed = null;
    }

    private static final long seedOffset;

    static {
        seedOffset = 0;
    }

    private static final java.util.concurrent.atomic.AtomicLong seedUniquifier;

    static {
        seedUniquifier = null;
    }

    private static final java.io.ObjectStreamField[] serialPersistentFields;

    static {
        serialPersistentFields = new java.io.ObjectStreamField[0];
    }

    static final long serialVersionUID = 3905348978240129619L; // 0x363296344bf00a53L

    private static final sun.misc.Unsafe unsafe;

    static {
        unsafe = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class RandomDoublesSpliterator implements java.util.Spliterator.OfDouble {

        RandomDoublesSpliterator(
                java.util.Random rng, long index, long fence, double origin, double bound) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Random.RandomDoublesSpliterator trySplit() {
            throw new RuntimeException("Stub!");
        }

        public long estimateSize() {
            throw new RuntimeException("Stub!");
        }

        public int characteristics() {
            throw new RuntimeException("Stub!");
        }

        public boolean tryAdvance(java.util.function.DoubleConsumer consumer) {
            throw new RuntimeException("Stub!");
        }

        public void forEachRemaining(java.util.function.DoubleConsumer consumer) {
            throw new RuntimeException("Stub!");
        }

        final double bound;

        {
            bound = 0;
        }

        final long fence;

        {
            fence = 0;
        }

        long index;

        final double origin;

        {
            origin = 0;
        }

        final java.util.Random rng;

        {
            rng = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class RandomIntsSpliterator implements java.util.Spliterator.OfInt {

        RandomIntsSpliterator(java.util.Random rng, long index, long fence, int origin, int bound) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Random.RandomIntsSpliterator trySplit() {
            throw new RuntimeException("Stub!");
        }

        public long estimateSize() {
            throw new RuntimeException("Stub!");
        }

        public int characteristics() {
            throw new RuntimeException("Stub!");
        }

        public boolean tryAdvance(java.util.function.IntConsumer consumer) {
            throw new RuntimeException("Stub!");
        }

        public void forEachRemaining(java.util.function.IntConsumer consumer) {
            throw new RuntimeException("Stub!");
        }

        final int bound;

        {
            bound = 0;
        }

        final long fence;

        {
            fence = 0;
        }

        long index;

        final int origin;

        {
            origin = 0;
        }

        final java.util.Random rng;

        {
            rng = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class RandomLongsSpliterator implements java.util.Spliterator.OfLong {

        RandomLongsSpliterator(
                java.util.Random rng, long index, long fence, long origin, long bound) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Random.RandomLongsSpliterator trySplit() {
            throw new RuntimeException("Stub!");
        }

        public long estimateSize() {
            throw new RuntimeException("Stub!");
        }

        public int characteristics() {
            throw new RuntimeException("Stub!");
        }

        public boolean tryAdvance(java.util.function.LongConsumer consumer) {
            throw new RuntimeException("Stub!");
        }

        public void forEachRemaining(java.util.function.LongConsumer consumer) {
            throw new RuntimeException("Stub!");
        }

        final long bound;

        {
            bound = 0;
        }

        final long fence;

        {
            fence = 0;
        }

        long index;

        final long origin;

        {
            origin = 0;
        }

        final java.util.Random rng;

        {
            rng = null;
        }
    }
}
