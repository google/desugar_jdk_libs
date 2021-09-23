/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
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
public class Arrays {

    private Arrays() {
        throw new RuntimeException("Stub!");
    }

    private static void rangeCheck(int arrayLength, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(int[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(int[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(long[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(long[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(short[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(short[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(char[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(char[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(byte[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(byte[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(float[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(float[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(double[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(double[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(byte[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(byte[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(char[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(char[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(short[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(short[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(int[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(int[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(long[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(long[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(float[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(float[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(double[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSort(double[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static <T extends java.lang.Comparable<? super T>> void parallelSort(T[] a) {
        throw new RuntimeException("Stub!");
    }

    public static <T extends java.lang.Comparable<? super T>> void parallelSort(
            T[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public static <T> void parallelSort(T[] a, java.util.Comparator<? super T> cmp) {
        throw new RuntimeException("Stub!");
    }

    public static <T> void parallelSort(
            T[] a, int fromIndex, int toIndex, java.util.Comparator<? super T> cmp) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(java.lang.Object[] a) {
        throw new RuntimeException("Stub!");
    }

    public static void sort(java.lang.Object[] a, int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    private static void mergeSort(
            java.lang.Object[] src, java.lang.Object[] dest, int low, int high, int off) {
        throw new RuntimeException("Stub!");
    }

    private static void swap(java.lang.Object[] x, int a, int b) {
        throw new RuntimeException("Stub!");
    }

    public static <T> void sort(T[] a, java.util.Comparator<? super T> c) {
        throw new RuntimeException("Stub!");
    }

    public static <T> void sort(
            T[] a, int fromIndex, int toIndex, java.util.Comparator<? super T> c) {
        throw new RuntimeException("Stub!");
    }

    public static <T> void parallelPrefix(T[] array, java.util.function.BinaryOperator<T> op) {
        throw new RuntimeException("Stub!");
    }

    public static <T> void parallelPrefix(
            T[] array, int fromIndex, int toIndex, java.util.function.BinaryOperator<T> op) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelPrefix(long[] array, java.util.function.LongBinaryOperator op) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelPrefix(
            long[] array, int fromIndex, int toIndex, java.util.function.LongBinaryOperator op) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelPrefix(double[] array, java.util.function.DoubleBinaryOperator op) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelPrefix(
            double[] array,
            int fromIndex,
            int toIndex,
            java.util.function.DoubleBinaryOperator op) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelPrefix(int[] array, java.util.function.IntBinaryOperator op) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelPrefix(
            int[] array, int fromIndex, int toIndex, java.util.function.IntBinaryOperator op) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(long[] a, long key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(long[] a, int fromIndex, int toIndex, long key) {
        throw new RuntimeException("Stub!");
    }

    private static int binarySearch0(long[] a, int fromIndex, int toIndex, long key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(int[] a, int key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(int[] a, int fromIndex, int toIndex, int key) {
        throw new RuntimeException("Stub!");
    }

    private static int binarySearch0(int[] a, int fromIndex, int toIndex, int key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(short[] a, short key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(short[] a, int fromIndex, int toIndex, short key) {
        throw new RuntimeException("Stub!");
    }

    private static int binarySearch0(short[] a, int fromIndex, int toIndex, short key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(char[] a, char key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(char[] a, int fromIndex, int toIndex, char key) {
        throw new RuntimeException("Stub!");
    }

    private static int binarySearch0(char[] a, int fromIndex, int toIndex, char key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(byte[] a, byte key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(byte[] a, int fromIndex, int toIndex, byte key) {
        throw new RuntimeException("Stub!");
    }

    private static int binarySearch0(byte[] a, int fromIndex, int toIndex, byte key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(double[] a, double key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(double[] a, int fromIndex, int toIndex, double key) {
        throw new RuntimeException("Stub!");
    }

    private static int binarySearch0(double[] a, int fromIndex, int toIndex, double key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(float[] a, float key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(float[] a, int fromIndex, int toIndex, float key) {
        throw new RuntimeException("Stub!");
    }

    private static int binarySearch0(float[] a, int fromIndex, int toIndex, float key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(java.lang.Object[] a, java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    public static int binarySearch(
            java.lang.Object[] a, int fromIndex, int toIndex, java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    private static int binarySearch0(
            java.lang.Object[] a, int fromIndex, int toIndex, java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    public static <T> int binarySearch(T[] a, T key, java.util.Comparator<? super T> c) {
        throw new RuntimeException("Stub!");
    }

    public static <T> int binarySearch(
            T[] a, int fromIndex, int toIndex, T key, java.util.Comparator<? super T> c) {
        throw new RuntimeException("Stub!");
    }

    private static <T> int binarySearch0(
            T[] a, int fromIndex, int toIndex, T key, java.util.Comparator<? super T> c) {
        throw new RuntimeException("Stub!");
    }

    public static boolean equals(long[] a, long[] a2) {
        throw new RuntimeException("Stub!");
    }

    public static boolean equals(int[] a, int[] a2) {
        throw new RuntimeException("Stub!");
    }

    public static boolean equals(short[] a, short[] a2) {
        throw new RuntimeException("Stub!");
    }

    public static boolean equals(char[] a, char[] a2) {
        throw new RuntimeException("Stub!");
    }

    public static boolean equals(byte[] a, byte[] a2) {
        throw new RuntimeException("Stub!");
    }

    public static boolean equals(boolean[] a, boolean[] a2) {
        throw new RuntimeException("Stub!");
    }

    public static boolean equals(double[] a, double[] a2) {
        throw new RuntimeException("Stub!");
    }

    public static boolean equals(float[] a, float[] a2) {
        throw new RuntimeException("Stub!");
    }

    public static boolean equals(java.lang.Object[] a, java.lang.Object[] a2) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(long[] a, long val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(long[] a, int fromIndex, int toIndex, long val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(int[] a, int val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(int[] a, int fromIndex, int toIndex, int val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(short[] a, short val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(short[] a, int fromIndex, int toIndex, short val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(char[] a, char val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(char[] a, int fromIndex, int toIndex, char val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(byte[] a, byte val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(byte[] a, int fromIndex, int toIndex, byte val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(boolean[] a, boolean val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(boolean[] a, int fromIndex, int toIndex, boolean val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(double[] a, double val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(double[] a, int fromIndex, int toIndex, double val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(float[] a, float val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(float[] a, int fromIndex, int toIndex, float val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(java.lang.Object[] a, java.lang.Object val) {
        throw new RuntimeException("Stub!");
    }

    public static void fill(
            java.lang.Object[] a, int fromIndex, int toIndex, java.lang.Object val) {
        throw new RuntimeException("Stub!");
    }

    public static <T> T[] copyOf(T[] original, int newLength) {
        throw new RuntimeException("Stub!");
    }

    public static <T, U> T[] copyOf(
            U[] original, int newLength, java.lang.Class<? extends T[]> newType) {
        throw new RuntimeException("Stub!");
    }

    public static byte[] copyOf(byte[] original, int newLength) {
        throw new RuntimeException("Stub!");
    }

    public static short[] copyOf(short[] original, int newLength) {
        throw new RuntimeException("Stub!");
    }

    public static int[] copyOf(int[] original, int newLength) {
        throw new RuntimeException("Stub!");
    }

    public static long[] copyOf(long[] original, int newLength) {
        throw new RuntimeException("Stub!");
    }

    public static char[] copyOf(char[] original, int newLength) {
        throw new RuntimeException("Stub!");
    }

    public static float[] copyOf(float[] original, int newLength) {
        throw new RuntimeException("Stub!");
    }

    public static double[] copyOf(double[] original, int newLength) {
        throw new RuntimeException("Stub!");
    }

    public static boolean[] copyOf(boolean[] original, int newLength) {
        throw new RuntimeException("Stub!");
    }

    public static <T> T[] copyOfRange(T[] original, int from, int to) {
        throw new RuntimeException("Stub!");
    }

    public static <T, U> T[] copyOfRange(
            U[] original, int from, int to, java.lang.Class<? extends T[]> newType) {
        throw new RuntimeException("Stub!");
    }

    public static byte[] copyOfRange(byte[] original, int from, int to) {
        throw new RuntimeException("Stub!");
    }

    public static short[] copyOfRange(short[] original, int from, int to) {
        throw new RuntimeException("Stub!");
    }

    public static int[] copyOfRange(int[] original, int from, int to) {
        throw new RuntimeException("Stub!");
    }

    public static long[] copyOfRange(long[] original, int from, int to) {
        throw new RuntimeException("Stub!");
    }

    public static char[] copyOfRange(char[] original, int from, int to) {
        throw new RuntimeException("Stub!");
    }

    public static float[] copyOfRange(float[] original, int from, int to) {
        throw new RuntimeException("Stub!");
    }

    public static double[] copyOfRange(double[] original, int from, int to) {
        throw new RuntimeException("Stub!");
    }

    public static boolean[] copyOfRange(boolean[] original, int from, int to) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.List<T> asList(T... a) {
        throw new RuntimeException("Stub!");
    }

    public static int hashCode(long[] a) {
        throw new RuntimeException("Stub!");
    }

    public static int hashCode(int[] a) {
        throw new RuntimeException("Stub!");
    }

    public static int hashCode(short[] a) {
        throw new RuntimeException("Stub!");
    }

    public static int hashCode(char[] a) {
        throw new RuntimeException("Stub!");
    }

    public static int hashCode(byte[] a) {
        throw new RuntimeException("Stub!");
    }

    public static int hashCode(boolean[] a) {
        throw new RuntimeException("Stub!");
    }

    public static int hashCode(float[] a) {
        throw new RuntimeException("Stub!");
    }

    public static int hashCode(double[] a) {
        throw new RuntimeException("Stub!");
    }

    public static int hashCode(java.lang.Object[] a) {
        throw new RuntimeException("Stub!");
    }

    public static int deepHashCode(java.lang.Object[] a) {
        throw new RuntimeException("Stub!");
    }

    public static boolean deepEquals(java.lang.Object[] a1, java.lang.Object[] a2) {
        throw new RuntimeException("Stub!");
    }

    static boolean deepEquals0(java.lang.Object e1, java.lang.Object e2) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(long[] a) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(int[] a) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(short[] a) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(char[] a) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(byte[] a) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(boolean[] a) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(float[] a) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(double[] a) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String toString(java.lang.Object[] a) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String deepToString(java.lang.Object[] a) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private static void deepToString(
            java.lang.Object[] a,
            java.lang.StringBuilder buf,
            java.util.Set<java.lang.Object[]> dejaVu) {
        throw new RuntimeException("Stub!");
    }

    public static <T> void setAll(
            T[] array, java.util.function.IntFunction<? extends T> generator) {
        throw new RuntimeException("Stub!");
    }

    public static <T> void parallelSetAll(
            T[] array, java.util.function.IntFunction<? extends T> generator) {
        throw new RuntimeException("Stub!");
    }

    public static void setAll(int[] array, java.util.function.IntUnaryOperator generator) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSetAll(int[] array, java.util.function.IntUnaryOperator generator) {
        throw new RuntimeException("Stub!");
    }

    public static void setAll(long[] array, java.util.function.IntToLongFunction generator) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSetAll(
            long[] array, java.util.function.IntToLongFunction generator) {
        throw new RuntimeException("Stub!");
    }

    public static void setAll(double[] array, java.util.function.IntToDoubleFunction generator) {
        throw new RuntimeException("Stub!");
    }

    public static void parallelSetAll(
            double[] array, java.util.function.IntToDoubleFunction generator) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Spliterator<T> spliterator(T[] array) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Spliterator<T> spliterator(
            T[] array, int startInclusive, int endExclusive) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Spliterator.OfInt spliterator(int[] array) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Spliterator.OfInt spliterator(
            int[] array, int startInclusive, int endExclusive) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Spliterator.OfLong spliterator(long[] array) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Spliterator.OfLong spliterator(
            long[] array, int startInclusive, int endExclusive) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Spliterator.OfDouble spliterator(double[] array) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Spliterator.OfDouble spliterator(
            double[] array, int startInclusive, int endExclusive) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.stream.Stream<T> stream(T[] array) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.stream.Stream<T> stream(
            T[] array, int startInclusive, int endExclusive) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.stream.IntStream stream(int[] array) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.stream.IntStream stream(
            int[] array, int startInclusive, int endExclusive) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.stream.LongStream stream(long[] array) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.stream.LongStream stream(
            long[] array, int startInclusive, int endExclusive) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.stream.DoubleStream stream(double[] array) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.stream.DoubleStream stream(
            double[] array, int startInclusive, int endExclusive) {
        throw new RuntimeException("Stub!");
    }

    private static final int INSERTIONSORT_THRESHOLD = 7; // 0x7

    public static final int MIN_ARRAY_SORT_GRAN = 8192; // 0x2000

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class ArrayList<E> extends java.util.AbstractList<E>
            implements java.util.RandomAccess, java.io.Serializable {

        ArrayList(E[] array) {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object[] toArray() {
            throw new RuntimeException("Stub!");
        }

        public <T> T[] toArray(T[] a) {
            throw new RuntimeException("Stub!");
        }

        public E get(int index) {
            throw new RuntimeException("Stub!");
        }

        public E set(int index, E element) {
            throw new RuntimeException("Stub!");
        }

        public int indexOf(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public void replaceAll(java.util.function.UnaryOperator<E> operator) {
            throw new RuntimeException("Stub!");
        }

        public void sort(java.util.Comparator<? super E> c) {
            throw new RuntimeException("Stub!");
        }

        @UnsupportedAppUsage
        private final E[] a;

        {
            a = null;
        }

        private static final long serialVersionUID = -2764017481108945198L; // 0xd9a43cbecd8806d2L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class NaturalOrder implements java.util.Comparator<java.lang.Object> {

        NaturalOrder() {
            throw new RuntimeException("Stub!");
        }

        public int compare(java.lang.Object first, java.lang.Object second) {
            throw new RuntimeException("Stub!");
        }

        static final java.util.Arrays.NaturalOrder INSTANCE;

        static {
            INSTANCE = null;
        }
    }
}
