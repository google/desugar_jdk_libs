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
public class Collections {

    private Collections() {
        throw new RuntimeException("Stub!");
    }

    public static <T extends java.lang.Comparable<? super T>> void sort(java.util.List<T> list) {
        throw new RuntimeException("Stub!");
    }

    public static <T> void sort(java.util.List<T> list, java.util.Comparator<? super T> c) {
        throw new RuntimeException("Stub!");
    }

    public static <T> int binarySearch(
            java.util.List<? extends java.lang.Comparable<? super T>> list, T key) {
        throw new RuntimeException("Stub!");
    }

    private static <T> int indexedBinarySearch(
            java.util.List<? extends java.lang.Comparable<? super T>> list, T key) {
        throw new RuntimeException("Stub!");
    }

    private static <T> int iteratorBinarySearch(
            java.util.List<? extends java.lang.Comparable<? super T>> list, T key) {
        throw new RuntimeException("Stub!");
    }

    private static <T> T get(java.util.ListIterator<? extends T> i, int index) {
        throw new RuntimeException("Stub!");
    }

    public static <T> int binarySearch(
            java.util.List<? extends T> list, T key, java.util.Comparator<? super T> c) {
        throw new RuntimeException("Stub!");
    }

    private static <T> int indexedBinarySearch(
            java.util.List<? extends T> l, T key, java.util.Comparator<? super T> c) {
        throw new RuntimeException("Stub!");
    }

    private static <T> int iteratorBinarySearch(
            java.util.List<? extends T> l, T key, java.util.Comparator<? super T> c) {
        throw new RuntimeException("Stub!");
    }

    public static void reverse(java.util.List<?> list) {
        throw new RuntimeException("Stub!");
    }

    public static void shuffle(java.util.List<?> list) {
        throw new RuntimeException("Stub!");
    }

    public static void shuffle(java.util.List<?> list, java.util.Random rnd) {
        throw new RuntimeException("Stub!");
    }

    public static void swap(java.util.List<?> list, int i, int j) {
        throw new RuntimeException("Stub!");
    }

    private static void swap(java.lang.Object[] arr, int i, int j) {
        throw new RuntimeException("Stub!");
    }

    public static <T> void fill(java.util.List<? super T> list, T obj) {
        throw new RuntimeException("Stub!");
    }

    public static <T> void copy(java.util.List<? super T> dest, java.util.List<? extends T> src) {
        throw new RuntimeException("Stub!");
    }

    public static <T extends java.lang.Object & java.lang.Comparable<? super T>> T min(
            java.util.Collection<? extends T> coll) {
        throw new RuntimeException("Stub!");
    }

    public static <T> T min(
            java.util.Collection<? extends T> coll, java.util.Comparator<? super T> comp) {
        throw new RuntimeException("Stub!");
    }

    public static <T extends java.lang.Object & java.lang.Comparable<? super T>> T max(
            java.util.Collection<? extends T> coll) {
        throw new RuntimeException("Stub!");
    }

    public static <T> T max(
            java.util.Collection<? extends T> coll, java.util.Comparator<? super T> comp) {
        throw new RuntimeException("Stub!");
    }

    public static void rotate(java.util.List<?> list, int distance) {
        throw new RuntimeException("Stub!");
    }

    private static <T> void rotate1(java.util.List<T> list, int distance) {
        throw new RuntimeException("Stub!");
    }

    private static void rotate2(java.util.List<?> list, int distance) {
        throw new RuntimeException("Stub!");
    }

    public static <T> boolean replaceAll(java.util.List<T> list, T oldVal, T newVal) {
        throw new RuntimeException("Stub!");
    }

    public static int indexOfSubList(java.util.List<?> source, java.util.List<?> target) {
        throw new RuntimeException("Stub!");
    }

    public static int lastIndexOfSubList(java.util.List<?> source, java.util.List<?> target) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Collection<T> unmodifiableCollection(
            java.util.Collection<? extends T> c) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Set<T> unmodifiableSet(java.util.Set<? extends T> s) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.SortedSet<T> unmodifiableSortedSet(java.util.SortedSet<T> s) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.NavigableSet<T> unmodifiableNavigableSet(
            java.util.NavigableSet<T> s) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.List<T> unmodifiableList(java.util.List<? extends T> list) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> java.util.Map<K, V> unmodifiableMap(
            java.util.Map<? extends K, ? extends V> m) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> java.util.SortedMap<K, V> unmodifiableSortedMap(
            java.util.SortedMap<K, ? extends V> m) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> java.util.NavigableMap<K, V> unmodifiableNavigableMap(
            java.util.NavigableMap<K, ? extends V> m) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Collection<T> synchronizedCollection(java.util.Collection<T> c) {
        throw new RuntimeException("Stub!");
    }

    static <T> java.util.Collection<T> synchronizedCollection(
            java.util.Collection<T> c, java.lang.Object mutex) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Set<T> synchronizedSet(java.util.Set<T> s) {
        throw new RuntimeException("Stub!");
    }

    static <T> java.util.Set<T> synchronizedSet(java.util.Set<T> s, java.lang.Object mutex) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.SortedSet<T> synchronizedSortedSet(java.util.SortedSet<T> s) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.NavigableSet<T> synchronizedNavigableSet(
            java.util.NavigableSet<T> s) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.List<T> synchronizedList(java.util.List<T> list) {
        throw new RuntimeException("Stub!");
    }

    static <T> java.util.List<T> synchronizedList(java.util.List<T> list, java.lang.Object mutex) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> java.util.Map<K, V> synchronizedMap(java.util.Map<K, V> m) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> java.util.SortedMap<K, V> synchronizedSortedMap(
            java.util.SortedMap<K, V> m) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> java.util.NavigableMap<K, V> synchronizedNavigableMap(
            java.util.NavigableMap<K, V> m) {
        throw new RuntimeException("Stub!");
    }

    public static <E> java.util.Collection<E> checkedCollection(
            java.util.Collection<E> c, java.lang.Class<E> type) {
        throw new RuntimeException("Stub!");
    }

    static <T> T[] zeroLengthArray(java.lang.Class<T> type) {
        throw new RuntimeException("Stub!");
    }

    public static <E> java.util.Queue<E> checkedQueue(
            java.util.Queue<E> queue, java.lang.Class<E> type) {
        throw new RuntimeException("Stub!");
    }

    public static <E> java.util.Set<E> checkedSet(java.util.Set<E> s, java.lang.Class<E> type) {
        throw new RuntimeException("Stub!");
    }

    public static <E> java.util.SortedSet<E> checkedSortedSet(
            java.util.SortedSet<E> s, java.lang.Class<E> type) {
        throw new RuntimeException("Stub!");
    }

    public static <E> java.util.NavigableSet<E> checkedNavigableSet(
            java.util.NavigableSet<E> s, java.lang.Class<E> type) {
        throw new RuntimeException("Stub!");
    }

    public static <E> java.util.List<E> checkedList(
            java.util.List<E> list, java.lang.Class<E> type) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> java.util.Map<K, V> checkedMap(
            java.util.Map<K, V> m, java.lang.Class<K> keyType, java.lang.Class<V> valueType) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> java.util.SortedMap<K, V> checkedSortedMap(
            java.util.SortedMap<K, V> m, java.lang.Class<K> keyType, java.lang.Class<V> valueType) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> java.util.NavigableMap<K, V> checkedNavigableMap(
            java.util.NavigableMap<K, V> m,
            java.lang.Class<K> keyType,
            java.lang.Class<V> valueType) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Iterator<T> emptyIterator() {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.ListIterator<T> emptyListIterator() {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Enumeration<T> emptyEnumeration() {
        throw new RuntimeException("Stub!");
    }

    public static final <T> java.util.Set<T> emptySet() {
        throw new RuntimeException("Stub!");
    }

    public static <E> java.util.SortedSet<E> emptySortedSet() {
        throw new RuntimeException("Stub!");
    }

    public static <E> java.util.NavigableSet<E> emptyNavigableSet() {
        throw new RuntimeException("Stub!");
    }

    public static final <T> java.util.List<T> emptyList() {
        throw new RuntimeException("Stub!");
    }

    public static final <K, V> java.util.Map<K, V> emptyMap() {
        throw new RuntimeException("Stub!");
    }

    public static final <K, V> java.util.SortedMap<K, V> emptySortedMap() {
        throw new RuntimeException("Stub!");
    }

    public static final <K, V> java.util.NavigableMap<K, V> emptyNavigableMap() {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Set<T> singleton(T o) {
        throw new RuntimeException("Stub!");
    }

    static <E> java.util.Iterator<E> singletonIterator(E e) {
        throw new RuntimeException("Stub!");
    }

    static <T> java.util.Spliterator<T> singletonSpliterator(T element) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.List<T> singletonList(T o) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> java.util.Map<K, V> singletonMap(K key, V value) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.List<T> nCopies(int n, T o) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Comparator<T> reverseOrder() {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Comparator<T> reverseOrder(java.util.Comparator<T> cmp) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Enumeration<T> enumeration(java.util.Collection<T> c) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.ArrayList<T> list(java.util.Enumeration<T> e) {
        throw new RuntimeException("Stub!");
    }

    static boolean eq(java.lang.Object o1, java.lang.Object o2) {
        throw new RuntimeException("Stub!");
    }

    public static int frequency(java.util.Collection<?> c, java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public static boolean disjoint(java.util.Collection<?> c1, java.util.Collection<?> c2) {
        throw new RuntimeException("Stub!");
    }

    public static <T> boolean addAll(java.util.Collection<? super T> c, T... elements) {
        throw new RuntimeException("Stub!");
    }

    public static <E> java.util.Set<E> newSetFromMap(java.util.Map<E, java.lang.Boolean> map) {
        throw new RuntimeException("Stub!");
    }

    public static <T> java.util.Queue<T> asLifoQueue(java.util.Deque<T> deque) {
        throw new RuntimeException("Stub!");
    }

    private static final int BINARYSEARCH_THRESHOLD = 5000; // 0x1388

    private static final int COPY_THRESHOLD = 10; // 0xa

    public static final java.util.List EMPTY_LIST;

    static {
        EMPTY_LIST = null;
    }

    public static final java.util.Map EMPTY_MAP;

    static {
        EMPTY_MAP = null;
    }

    public static final java.util.Set EMPTY_SET;

    static {
        EMPTY_SET = null;
    }

    private static final int FILL_THRESHOLD = 25; // 0x19

    private static final int INDEXOFSUBLIST_THRESHOLD = 35; // 0x23

    private static final int REPLACEALL_THRESHOLD = 11; // 0xb

    private static final int REVERSE_THRESHOLD = 18; // 0x12

    private static final int ROTATE_THRESHOLD = 100; // 0x64

    private static final int SHUFFLE_THRESHOLD = 5; // 0x5

    private static java.util.Random r;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class AsLIFOQueue<E> extends java.util.AbstractQueue<E>
            implements java.util.Queue<E>, java.io.Serializable {

        AsLIFOQueue(java.util.Deque<E> q) {
            throw new RuntimeException("Stub!");
        }

        public boolean add(E e) {
            throw new RuntimeException("Stub!");
        }

        public boolean offer(E e) {
            throw new RuntimeException("Stub!");
        }

        public E poll() {
            throw new RuntimeException("Stub!");
        }

        public E remove() {
            throw new RuntimeException("Stub!");
        }

        public E peek() {
            throw new RuntimeException("Stub!");
        }

        public E element() {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> iterator() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object[] toArray() {
            throw new RuntimeException("Stub!");
        }

        public <T> T[] toArray(T[] a) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        public boolean containsAll(java.util.Collection<?> c) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeAll(java.util.Collection<?> c) {
            throw new RuntimeException("Stub!");
        }

        public boolean retainAll(java.util.Collection<?> c) {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeIf(java.util.function.Predicate<? super E> filter) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.stream.Stream<E> stream() {
            throw new RuntimeException("Stub!");
        }

        public java.util.stream.Stream<E> parallelStream() {
            throw new RuntimeException("Stub!");
        }

        private final java.util.Deque<E> q;

        {
            q = null;
        }

        private static final long serialVersionUID = 1802017725587941708L; // 0x19020d92eca0694cL
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class CheckedCollection<E> implements java.util.Collection<E>, java.io.Serializable {

        CheckedCollection(java.util.Collection<E> c, java.lang.Class<E> type) {
            throw new RuntimeException("Stub!");
        }

        E typeCheck(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.String badElementMsg(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object[] toArray() {
            throw new RuntimeException("Stub!");
        }

        public <T> T[] toArray(T[] a) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        public boolean containsAll(java.util.Collection<?> coll) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeAll(java.util.Collection<?> coll) {
            throw new RuntimeException("Stub!");
        }

        public boolean retainAll(java.util.Collection<?> coll) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> iterator() {
            throw new RuntimeException("Stub!");
        }

        public boolean add(E e) {
            throw new RuntimeException("Stub!");
        }

        private E[] zeroLengthElementArray() {
            throw new RuntimeException("Stub!");
        }

        java.util.Collection<E> checkedCopyOf(java.util.Collection<? extends E> coll) {
            throw new RuntimeException("Stub!");
        }

        public boolean addAll(java.util.Collection<? extends E> coll) {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeIf(java.util.function.Predicate<? super E> filter) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.stream.Stream<E> stream() {
            throw new RuntimeException("Stub!");
        }

        public java.util.stream.Stream<E> parallelStream() {
            throw new RuntimeException("Stub!");
        }

        final java.util.Collection<E> c;

        {
            c = null;
        }

        private static final long serialVersionUID = 1578914078182001775L; // 0x15e96dfd18e6cc6fL

        final java.lang.Class<E> type;

        {
            type = null;
        }

        private E[] zeroLengthElementArray;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class CheckedList<E> extends java.util.Collections.CheckedCollection<E>
            implements java.util.List<E> {

        CheckedList(java.util.List<E> list, java.lang.Class<E> type) {
            super(null, null);
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public E get(int index) {
            throw new RuntimeException("Stub!");
        }

        public E remove(int index) {
            throw new RuntimeException("Stub!");
        }

        public int indexOf(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int lastIndexOf(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public E set(int index, E element) {
            throw new RuntimeException("Stub!");
        }

        public void add(int index, E element) {
            throw new RuntimeException("Stub!");
        }

        public boolean addAll(int index, java.util.Collection<? extends E> c) {
            throw new RuntimeException("Stub!");
        }

        public java.util.ListIterator<E> listIterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.ListIterator<E> listIterator(int index) {
            throw new RuntimeException("Stub!");
        }

        public java.util.List<E> subList(int fromIndex, int toIndex) {
            throw new RuntimeException("Stub!");
        }

        public void replaceAll(java.util.function.UnaryOperator<E> operator) {
            throw new RuntimeException("Stub!");
        }

        public void sort(java.util.Comparator<? super E> c) {
            throw new RuntimeException("Stub!");
        }

        final java.util.List<E> list;

        {
            list = null;
        }

        private static final long serialVersionUID = 65247728283967356L; // 0xe7ce7692c45f7cL
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class CheckedMap<K, V> implements java.util.Map<K, V>, java.io.Serializable {

        CheckedMap(
                java.util.Map<K, V> m, java.lang.Class<K> keyType, java.lang.Class<V> valueType) {
            throw new RuntimeException("Stub!");
        }

        private void typeCheck(java.lang.Object key, java.lang.Object value) {
            throw new RuntimeException("Stub!");
        }

        private java.util.function.BiFunction<? super K, ? super V, ? extends V> typeCheck(
                java.util.function.BiFunction<? super K, ? super V, ? extends V> func) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.String badKeyMsg(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.String badValueMsg(java.lang.Object value) {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public boolean containsKey(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public boolean containsValue(java.lang.Object v) {
            throw new RuntimeException("Stub!");
        }

        public V get(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public V remove(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Set<K> keySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Collection<V> values() {
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        public V put(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public void putAll(java.util.Map<? extends K, ? extends V> t) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.BiConsumer<? super K, ? super V> action) {
            throw new RuntimeException("Stub!");
        }

        public void replaceAll(
                java.util.function.BiFunction<? super K, ? super V, ? extends V> function) {
            throw new RuntimeException("Stub!");
        }

        public V putIfAbsent(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object key, java.lang.Object value) {
            throw new RuntimeException("Stub!");
        }

        public boolean replace(K key, V oldValue, V newValue) {
            throw new RuntimeException("Stub!");
        }

        public V replace(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public V computeIfAbsent(
                K key, java.util.function.Function<? super K, ? extends V> mappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V computeIfPresent(
                K key,
                java.util.function.BiFunction<? super K, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V compute(
                K key,
                java.util.function.BiFunction<? super K, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V merge(
                K key,
                V value,
                java.util.function.BiFunction<? super V, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        private transient java.util.Set<java.util.Map.Entry<K, V>> entrySet;

        final java.lang.Class<K> keyType;

        {
            keyType = null;
        }

        private final java.util.Map<K, V> m;

        {
            m = null;
        }

        private static final long serialVersionUID = 5742860141034234728L; // 0x4fb2bcdf0d186368L

        final java.lang.Class<V> valueType;

        {
            valueType = null;
        }

        @SuppressWarnings({"unchecked", "deprecation", "all"})
        static class CheckedEntrySet<K, V> implements java.util.Set<java.util.Map.Entry<K, V>> {

            CheckedEntrySet(
                    java.util.Set<java.util.Map.Entry<K, V>> s, java.lang.Class<V> valueType) {
                throw new RuntimeException("Stub!");
            }

            public int size() {
                throw new RuntimeException("Stub!");
            }

            public boolean isEmpty() {
                throw new RuntimeException("Stub!");
            }

            public java.lang.String toString() {
                throw new RuntimeException("Stub!");
            }

            public int hashCode() {
                throw new RuntimeException("Stub!");
            }

            public void clear() {
                throw new RuntimeException("Stub!");
            }

            public boolean add(java.util.Map.Entry<K, V> e) {
                throw new RuntimeException("Stub!");
            }

            public boolean addAll(java.util.Collection<? extends java.util.Map.Entry<K, V>> coll) {
                throw new RuntimeException("Stub!");
            }

            public java.util.Iterator<java.util.Map.Entry<K, V>> iterator() {
                throw new RuntimeException("Stub!");
            }

            public java.lang.Object[] toArray() {
                throw new RuntimeException("Stub!");
            }

            public <T> T[] toArray(T[] a) {
                throw new RuntimeException("Stub!");
            }

            public boolean contains(java.lang.Object o) {
                throw new RuntimeException("Stub!");
            }

            public boolean containsAll(java.util.Collection<?> c) {
                throw new RuntimeException("Stub!");
            }

            public boolean remove(java.lang.Object o) {
                throw new RuntimeException("Stub!");
            }

            public boolean removeAll(java.util.Collection<?> c) {
                throw new RuntimeException("Stub!");
            }

            public boolean retainAll(java.util.Collection<?> c) {
                throw new RuntimeException("Stub!");
            }

            private boolean batchRemove(java.util.Collection<?> c, boolean complement) {
                throw new RuntimeException("Stub!");
            }

            public boolean equals(java.lang.Object o) {
                throw new RuntimeException("Stub!");
            }

            static <K, V, T>
                    java.util.Collections.CheckedMap.CheckedEntrySet.CheckedEntry<K, V, T>
                            checkedEntry(
                                    java.util.Map.Entry<K, V> e, java.lang.Class<T> valueType) {
                throw new RuntimeException("Stub!");
            }

            private final java.util.Set<java.util.Map.Entry<K, V>> s;

            {
                s = null;
            }

            private final java.lang.Class<V> valueType;

            {
                valueType = null;
            }

            @SuppressWarnings({"unchecked", "deprecation", "all"})
            private static class CheckedEntry<K, V, T> implements java.util.Map.Entry<K, V> {

                CheckedEntry(java.util.Map.Entry<K, V> e, java.lang.Class<T> valueType) {
                    throw new RuntimeException("Stub!");
                }

                public K getKey() {
                    throw new RuntimeException("Stub!");
                }

                public V getValue() {
                    throw new RuntimeException("Stub!");
                }

                public int hashCode() {
                    throw new RuntimeException("Stub!");
                }

                public java.lang.String toString() {
                    throw new RuntimeException("Stub!");
                }

                public V setValue(V value) {
                    throw new RuntimeException("Stub!");
                }

                private java.lang.String badValueMsg(java.lang.Object value) {
                    throw new RuntimeException("Stub!");
                }

                public boolean equals(java.lang.Object o) {
                    throw new RuntimeException("Stub!");
                }

                private final java.util.Map.Entry<K, V> e;

                {
                    e = null;
                }

                private final java.lang.Class<T> valueType;

                {
                    valueType = null;
                }
            }
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class CheckedNavigableMap<K, V> extends java.util.Collections.CheckedSortedMap<K, V>
            implements java.util.NavigableMap<K, V>, java.io.Serializable {

        CheckedNavigableMap(
                java.util.NavigableMap<K, V> m,
                java.lang.Class<K> keyType,
                java.lang.Class<V> valueType) {
            super(null, null, null);
            throw new RuntimeException("Stub!");
        }

        public java.util.Comparator<? super K> comparator() {
            throw new RuntimeException("Stub!");
        }

        public K firstKey() {
            throw new RuntimeException("Stub!");
        }

        public K lastKey() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> lowerEntry(K key) {
            throw new RuntimeException("Stub!");
        }

        public K lowerKey(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> floorEntry(K key) {
            throw new RuntimeException("Stub!");
        }

        public K floorKey(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> ceilingEntry(K key) {
            throw new RuntimeException("Stub!");
        }

        public K ceilingKey(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> higherEntry(K key) {
            throw new RuntimeException("Stub!");
        }

        public K higherKey(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> firstEntry() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> lastEntry() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> pollFirstEntry() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> pollLastEntry() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> descendingMap() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<K> keySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<K> navigableKeySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<K> descendingKeySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> subMap(K fromKey, K toKey) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> headMap(K toKey) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> tailMap(K fromKey) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> subMap(
                K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            throw new RuntimeException("Stub!");
        }

        private final java.util.NavigableMap<K, V> nm;

        {
            nm = null;
        }

        private static final long serialVersionUID = -4852462692372534096L; // 0xbca896e4074cacb0L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class CheckedNavigableSet<E> extends java.util.Collections.CheckedSortedSet<E>
            implements java.util.NavigableSet<E>, java.io.Serializable {

        CheckedNavigableSet(java.util.NavigableSet<E> s, java.lang.Class<E> type) {
            super(null, null);
            throw new RuntimeException("Stub!");
        }

        public E lower(E e) {
            throw new RuntimeException("Stub!");
        }

        public E floor(E e) {
            throw new RuntimeException("Stub!");
        }

        public E ceiling(E e) {
            throw new RuntimeException("Stub!");
        }

        public E higher(E e) {
            throw new RuntimeException("Stub!");
        }

        public E pollFirst() {
            throw new RuntimeException("Stub!");
        }

        public E pollLast() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> descendingSet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> descendingIterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> subSet(E fromElement, E toElement) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> headSet(E toElement) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> tailSet(E fromElement) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> subSet(
                E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> headSet(E toElement, boolean inclusive) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
            throw new RuntimeException("Stub!");
        }

        private final java.util.NavigableSet<E> ns;

        {
            ns = null;
        }

        private static final long serialVersionUID = -5429120189805438922L; // 0xb4a7e3f3bbbed836L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class CheckedQueue<E> extends java.util.Collections.CheckedCollection<E>
            implements java.util.Queue<E>, java.io.Serializable {

        CheckedQueue(java.util.Queue<E> queue, java.lang.Class<E> elementType) {
            super(null, null);
            throw new RuntimeException("Stub!");
        }

        public E element() {
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public E peek() {
            throw new RuntimeException("Stub!");
        }

        public E poll() {
            throw new RuntimeException("Stub!");
        }

        public E remove() {
            throw new RuntimeException("Stub!");
        }

        public boolean offer(E e) {
            throw new RuntimeException("Stub!");
        }

        final java.util.Queue<E> queue;

        {
            queue = null;
        }

        private static final long serialVersionUID = 1433151992604707767L; // 0x13e39424e458cbb7L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class CheckedRandomAccessList<E> extends java.util.Collections.CheckedList<E>
            implements java.util.RandomAccess {

        CheckedRandomAccessList(java.util.List<E> list, java.lang.Class<E> type) {
            super(null, null);
            throw new RuntimeException("Stub!");
        }

        public java.util.List<E> subList(int fromIndex, int toIndex) {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = 1638200125423088369L; // 0x16bc0e55a2d7f2f1L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class CheckedSet<E> extends java.util.Collections.CheckedCollection<E>
            implements java.util.Set<E>, java.io.Serializable {

        CheckedSet(java.util.Set<E> s, java.lang.Class<E> elementType) {
            super(null, null);
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = 4694047833775013803L; // 0x41249ba27ad9ffabL
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class CheckedSortedMap<K, V> extends java.util.Collections.CheckedMap<K, V>
            implements java.util.SortedMap<K, V>, java.io.Serializable {

        CheckedSortedMap(
                java.util.SortedMap<K, V> m,
                java.lang.Class<K> keyType,
                java.lang.Class<V> valueType) {
            super(null, null, null);
            throw new RuntimeException("Stub!");
        }

        public java.util.Comparator<? super K> comparator() {
            throw new RuntimeException("Stub!");
        }

        public K firstKey() {
            throw new RuntimeException("Stub!");
        }

        public K lastKey() {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedMap<K, V> subMap(K fromKey, K toKey) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedMap<K, V> headMap(K toKey) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedMap<K, V> tailMap(K fromKey) {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = 1599671320688067438L; // 0x16332c973afe036eL

        private final java.util.SortedMap<K, V> sm;

        {
            sm = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class CheckedSortedSet<E> extends java.util.Collections.CheckedSet<E>
            implements java.util.SortedSet<E>, java.io.Serializable {

        CheckedSortedSet(java.util.SortedSet<E> s, java.lang.Class<E> type) {
            super(null, null);
            throw new RuntimeException("Stub!");
        }

        public java.util.Comparator<? super E> comparator() {
            throw new RuntimeException("Stub!");
        }

        public E first() {
            throw new RuntimeException("Stub!");
        }

        public E last() {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedSet<E> subSet(E fromElement, E toElement) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedSet<E> headSet(E toElement) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedSet<E> tailSet(E fromElement) {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = 1599911165492914959L; // 0x163406ba7362eb0fL

        private final java.util.SortedSet<E> ss;

        {
            ss = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class CopiesList<E> extends java.util.AbstractList<E>
            implements java.util.RandomAccess, java.io.Serializable {

        CopiesList(int n, E e) {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        public int indexOf(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int lastIndexOf(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public E get(int index) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object[] toArray() {
            throw new RuntimeException("Stub!");
        }

        public <T> T[] toArray(T[] a) {
            throw new RuntimeException("Stub!");
        }

        public java.util.List<E> subList(int fromIndex, int toIndex) {
            throw new RuntimeException("Stub!");
        }

        public java.util.stream.Stream<E> stream() {
            throw new RuntimeException("Stub!");
        }

        public java.util.stream.Stream<E> parallelStream() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        final E element;

        {
            element = null;
        }

        final int n;

        {
            n = 0;
        }

        private static final long serialVersionUID = 2739099268398711800L; // 0x26033c45b17003f8L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class EmptyEnumeration<E> implements java.util.Enumeration<E> {

        private EmptyEnumeration() {
            throw new RuntimeException("Stub!");
        }

        public boolean hasMoreElements() {
            throw new RuntimeException("Stub!");
        }

        public E nextElement() {
            throw new RuntimeException("Stub!");
        }

        static final java.util.Collections.EmptyEnumeration<java.lang.Object> EMPTY_ENUMERATION;

        static {
            EMPTY_ENUMERATION = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class EmptyIterator<E> implements java.util.Iterator<E> {

        private EmptyIterator() {
            throw new RuntimeException("Stub!");
        }

        public boolean hasNext() {
            throw new RuntimeException("Stub!");
        }

        public E next() {
            throw new RuntimeException("Stub!");
        }

        public void remove() {
            throw new RuntimeException("Stub!");
        }

        public void forEachRemaining(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        static final java.util.Collections.EmptyIterator<java.lang.Object> EMPTY_ITERATOR;

        static {
            EMPTY_ITERATOR = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class EmptyList<E> extends java.util.AbstractList<E>
            implements java.util.RandomAccess, java.io.Serializable {

        @UnsupportedAppUsage
        private EmptyList() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> iterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.ListIterator<E> listIterator() {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        public boolean containsAll(java.util.Collection<?> c) {
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

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public boolean removeIf(java.util.function.Predicate<? super E> filter) {
            throw new RuntimeException("Stub!");
        }

        public void replaceAll(java.util.function.UnaryOperator<E> operator) {
            throw new RuntimeException("Stub!");
        }

        public void sort(java.util.Comparator<? super E> c) {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        private java.lang.Object readResolve() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = 8842843931221139166L; // 0x7ab817b43ca79edeL
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class EmptyListIterator<E> extends java.util.Collections.EmptyIterator<E>
            implements java.util.ListIterator<E> {

        private EmptyListIterator() {
            throw new RuntimeException("Stub!");
        }

        public boolean hasPrevious() {
            throw new RuntimeException("Stub!");
        }

        public E previous() {
            throw new RuntimeException("Stub!");
        }

        public int nextIndex() {
            throw new RuntimeException("Stub!");
        }

        public int previousIndex() {
            throw new RuntimeException("Stub!");
        }

        public void set(E e) {
            throw new RuntimeException("Stub!");
        }

        public void add(E e) {
            throw new RuntimeException("Stub!");
        }

        static final java.util.Collections.EmptyListIterator<java.lang.Object> EMPTY_ITERATOR;

        static {
            EMPTY_ITERATOR = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class EmptyMap<K, V> extends java.util.AbstractMap<K, V>
            implements java.io.Serializable {

        @UnsupportedAppUsage
        private EmptyMap() {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public boolean containsKey(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public boolean containsValue(java.lang.Object value) {
            throw new RuntimeException("Stub!");
        }

        public V get(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Set<K> keySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Collection<V> values() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public V getOrDefault(java.lang.Object k, V defaultValue) {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.BiConsumer<? super K, ? super V> action) {
            throw new RuntimeException("Stub!");
        }

        public void replaceAll(
                java.util.function.BiFunction<? super K, ? super V, ? extends V> function) {
            throw new RuntimeException("Stub!");
        }

        public V putIfAbsent(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object key, java.lang.Object value) {
            throw new RuntimeException("Stub!");
        }

        public boolean replace(K key, V oldValue, V newValue) {
            throw new RuntimeException("Stub!");
        }

        public V replace(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public V computeIfAbsent(
                K key, java.util.function.Function<? super K, ? extends V> mappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V computeIfPresent(
                K key,
                java.util.function.BiFunction<? super K, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V compute(
                K key,
                java.util.function.BiFunction<? super K, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V merge(
                K key,
                V value,
                java.util.function.BiFunction<? super V, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.Object readResolve() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = 6428348081105594320L; // 0x593614855adce7d0L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class EmptySet<E> extends java.util.AbstractSet<E>
            implements java.io.Serializable {

        private EmptySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> iterator() {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        public boolean containsAll(java.util.Collection<?> c) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object[] toArray() {
            throw new RuntimeException("Stub!");
        }

        public <T> T[] toArray(T[] a) {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeIf(java.util.function.Predicate<? super E> filter) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        private java.lang.Object readResolve() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = 1582296315990362920L; // 0x15f5721db403cb28L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class ReverseComparator
            implements java.util.Comparator<java.lang.Comparable<java.lang.Object>>,
                    java.io.Serializable {

        private ReverseComparator() {
            throw new RuntimeException("Stub!");
        }

        public int compare(
                java.lang.Comparable<java.lang.Object> c1,
                java.lang.Comparable<java.lang.Object> c2) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.Object readResolve() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Comparator<java.lang.Comparable<java.lang.Object>> reversed() {
            throw new RuntimeException("Stub!");
        }

        static final java.util.Collections.ReverseComparator REVERSE_ORDER;

        static {
            REVERSE_ORDER = null;
        }

        private static final long serialVersionUID = 7207038068494060240L; // 0x64048af0534e4ad0L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class ReverseComparator2<T>
            implements java.util.Comparator<T>, java.io.Serializable {

        ReverseComparator2(java.util.Comparator<T> cmp) {
            throw new RuntimeException("Stub!");
        }

        public int compare(T t1, T t2) {
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Comparator<T> reversed() {
            throw new RuntimeException("Stub!");
        }

        final java.util.Comparator<T> cmp;

        {
            cmp = null;
        }

        private static final long serialVersionUID = 4374092139857L; // 0x3fa6c354d51L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class SetFromMap<E> extends java.util.AbstractSet<E>
            implements java.util.Set<E>, java.io.Serializable {

        SetFromMap(java.util.Map<E, java.lang.Boolean> map) {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public boolean add(E e) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> iterator() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object[] toArray() {
            throw new RuntimeException("Stub!");
        }

        public <T> T[] toArray(T[] a) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public boolean containsAll(java.util.Collection<?> c) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeAll(java.util.Collection<?> c) {
            throw new RuntimeException("Stub!");
        }

        public boolean retainAll(java.util.Collection<?> c) {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeIf(java.util.function.Predicate<? super E> filter) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.stream.Stream<E> stream() {
            throw new RuntimeException("Stub!");
        }

        public java.util.stream.Stream<E> parallelStream() {
            throw new RuntimeException("Stub!");
        }

        private void readObject(java.io.ObjectInputStream stream)
                throws java.lang.ClassNotFoundException, java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private final java.util.Map<E, java.lang.Boolean> m;

        {
            m = null;
        }

        private transient java.util.Set<E> s;

        private static final long serialVersionUID = 2454657854757543876L; // 0x2210b25045f21fc4L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class SingletonList<E> extends java.util.AbstractList<E>
            implements java.util.RandomAccess, java.io.Serializable {

        SingletonList(E obj) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> iterator() {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        public E get(int index) {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeIf(java.util.function.Predicate<? super E> filter) {
            throw new RuntimeException("Stub!");
        }

        public void replaceAll(java.util.function.UnaryOperator<E> operator) {
            throw new RuntimeException("Stub!");
        }

        public void sort(java.util.Comparator<? super E> c) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        private final E element;

        {
            element = null;
        }

        private static final long serialVersionUID = 3093736618740652951L; // 0x2aef29103ca79b97L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class SingletonMap<K, V> extends java.util.AbstractMap<K, V>
            implements java.io.Serializable {

        SingletonMap(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public boolean containsKey(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public boolean containsValue(java.lang.Object value) {
            throw new RuntimeException("Stub!");
        }

        public V get(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Set<K> keySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Collection<V> values() {
            throw new RuntimeException("Stub!");
        }

        public V getOrDefault(java.lang.Object key, V defaultValue) {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.BiConsumer<? super K, ? super V> action) {
            throw new RuntimeException("Stub!");
        }

        public void replaceAll(
                java.util.function.BiFunction<? super K, ? super V, ? extends V> function) {
            throw new RuntimeException("Stub!");
        }

        public V putIfAbsent(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object key, java.lang.Object value) {
            throw new RuntimeException("Stub!");
        }

        public boolean replace(K key, V oldValue, V newValue) {
            throw new RuntimeException("Stub!");
        }

        public V replace(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public V computeIfAbsent(
                K key, java.util.function.Function<? super K, ? extends V> mappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V computeIfPresent(
                K key,
                java.util.function.BiFunction<? super K, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V compute(
                K key,
                java.util.function.BiFunction<? super K, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V merge(
                K key,
                V value,
                java.util.function.BiFunction<? super V, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        private transient java.util.Set<java.util.Map.Entry<K, V>> entrySet;

        private final K k;

        {
            k = null;
        }

        private transient java.util.Set<K> keySet;

        private static final long serialVersionUID = -6979724477215052911L; // 0x9f230991717f6b91L

        private final V v;

        {
            v = null;
        }

        private transient java.util.Collection<V> values;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class SingletonSet<E> extends java.util.AbstractSet<E>
            implements java.io.Serializable {

        SingletonSet(E e) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> iterator() {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        public boolean removeIf(java.util.function.Predicate<? super E> filter) {
            throw new RuntimeException("Stub!");
        }

        private final E element;

        {
            element = null;
        }

        private static final long serialVersionUID = 3193687207550431679L; // 0x2c52419829c0b1bfL
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class SynchronizedCollection<E>
            implements java.util.Collection<E>, java.io.Serializable {

        SynchronizedCollection(java.util.Collection<E> c) {
            throw new RuntimeException("Stub!");
        }

        SynchronizedCollection(java.util.Collection<E> c, java.lang.Object mutex) {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object[] toArray() {
            throw new RuntimeException("Stub!");
        }

        public <T> T[] toArray(T[] a) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> iterator() {
            throw new RuntimeException("Stub!");
        }

        public boolean add(E e) {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public boolean containsAll(java.util.Collection<?> coll) {
            throw new RuntimeException("Stub!");
        }

        public boolean addAll(java.util.Collection<? extends E> coll) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeAll(java.util.Collection<?> coll) {
            throw new RuntimeException("Stub!");
        }

        public boolean retainAll(java.util.Collection<?> coll) {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super E> consumer) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeIf(java.util.function.Predicate<? super E> filter) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.stream.Stream<E> stream() {
            throw new RuntimeException("Stub!");
        }

        public java.util.stream.Stream<E> parallelStream() {
            throw new RuntimeException("Stub!");
        }

        private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        @UnsupportedAppUsage
        final java.util.Collection<E> c;

        {
            c = null;
        }

        final java.lang.Object mutex;

        {
            mutex = null;
        }

        private static final long serialVersionUID = 3053995032091335093L; // 0x2a61f84d099c99b5L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class SynchronizedList<E> extends java.util.Collections.SynchronizedCollection<E>
            implements java.util.List<E> {

        SynchronizedList(java.util.List<E> list) {
            super((java.util.Collection) null);
            throw new RuntimeException("Stub!");
        }

        SynchronizedList(java.util.List<E> list, java.lang.Object mutex) {
            super((java.util.Collection) null);
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public E get(int index) {
            throw new RuntimeException("Stub!");
        }

        public E set(int index, E element) {
            throw new RuntimeException("Stub!");
        }

        public void add(int index, E element) {
            throw new RuntimeException("Stub!");
        }

        public E remove(int index) {
            throw new RuntimeException("Stub!");
        }

        public int indexOf(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int lastIndexOf(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public boolean addAll(int index, java.util.Collection<? extends E> c) {
            throw new RuntimeException("Stub!");
        }

        public java.util.ListIterator<E> listIterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.ListIterator<E> listIterator(int index) {
            throw new RuntimeException("Stub!");
        }

        public java.util.List<E> subList(int fromIndex, int toIndex) {
            throw new RuntimeException("Stub!");
        }

        public void replaceAll(java.util.function.UnaryOperator<E> operator) {
            throw new RuntimeException("Stub!");
        }

        public void sort(java.util.Comparator<? super E> c) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.Object readResolve() {
            throw new RuntimeException("Stub!");
        }

        @UnsupportedAppUsage
        final java.util.List<E> list;

        {
            list = null;
        }

        private static final long serialVersionUID = -7754090372962971524L; // 0x9463efe38344107cL
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class SynchronizedMap<K, V>
            implements java.util.Map<K, V>, java.io.Serializable {

        SynchronizedMap(java.util.Map<K, V> m) {
            throw new RuntimeException("Stub!");
        }

        SynchronizedMap(java.util.Map<K, V> m, java.lang.Object mutex) {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public boolean containsKey(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public boolean containsValue(java.lang.Object value) {
            throw new RuntimeException("Stub!");
        }

        public V get(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public V put(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public V remove(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public void putAll(java.util.Map<? extends K, ? extends V> map) {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Set<K> keySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Collection<V> values() {
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        public V getOrDefault(java.lang.Object k, V defaultValue) {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.BiConsumer<? super K, ? super V> action) {
            throw new RuntimeException("Stub!");
        }

        public void replaceAll(
                java.util.function.BiFunction<? super K, ? super V, ? extends V> function) {
            throw new RuntimeException("Stub!");
        }

        public V putIfAbsent(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object key, java.lang.Object value) {
            throw new RuntimeException("Stub!");
        }

        public boolean replace(K key, V oldValue, V newValue) {
            throw new RuntimeException("Stub!");
        }

        public V replace(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public V computeIfAbsent(
                K key, java.util.function.Function<? super K, ? extends V> mappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V computeIfPresent(
                K key,
                java.util.function.BiFunction<? super K, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V compute(
                K key,
                java.util.function.BiFunction<? super K, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V merge(
                K key,
                V value,
                java.util.function.BiFunction<? super V, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private transient java.util.Set<java.util.Map.Entry<K, V>> entrySet;

        private transient java.util.Set<K> keySet;

        @UnsupportedAppUsage
        private final java.util.Map<K, V> m;

        {
            m = null;
        }

        final java.lang.Object mutex;

        {
            mutex = null;
        }

        private static final long serialVersionUID = 1978198479659022715L; // 0x1b73f9094b4b397bL

        private transient java.util.Collection<V> values;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class SynchronizedNavigableMap<K, V>
            extends java.util.Collections.SynchronizedSortedMap<K, V>
            implements java.util.NavigableMap<K, V> {

        SynchronizedNavigableMap(java.util.NavigableMap<K, V> m) {
            super((java.util.SortedMap) null);
            throw new RuntimeException("Stub!");
        }

        SynchronizedNavigableMap(java.util.NavigableMap<K, V> m, java.lang.Object mutex) {
            super((java.util.SortedMap) null);
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> lowerEntry(K key) {
            throw new RuntimeException("Stub!");
        }

        public K lowerKey(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> floorEntry(K key) {
            throw new RuntimeException("Stub!");
        }

        public K floorKey(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> ceilingEntry(K key) {
            throw new RuntimeException("Stub!");
        }

        public K ceilingKey(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> higherEntry(K key) {
            throw new RuntimeException("Stub!");
        }

        public K higherKey(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> firstEntry() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> lastEntry() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> pollFirstEntry() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> pollLastEntry() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> descendingMap() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<K> keySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<K> navigableKeySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<K> descendingKeySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedMap<K, V> subMap(K fromKey, K toKey) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedMap<K, V> headMap(K toKey) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedMap<K, V> tailMap(K fromKey) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> subMap(
                K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            throw new RuntimeException("Stub!");
        }

        private final java.util.NavigableMap<K, V> nm;

        {
            nm = null;
        }

        private static final long serialVersionUID = 699392247599746807L; // 0x9b4bd8b2cd84ef7L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class SynchronizedNavigableSet<E> extends java.util.Collections.SynchronizedSortedSet<E>
            implements java.util.NavigableSet<E> {

        SynchronizedNavigableSet(java.util.NavigableSet<E> s) {
            super((java.util.SortedSet) null);
            throw new RuntimeException("Stub!");
        }

        SynchronizedNavigableSet(java.util.NavigableSet<E> s, java.lang.Object mutex) {
            super((java.util.SortedSet) null);
            throw new RuntimeException("Stub!");
        }

        public E lower(E e) {
            throw new RuntimeException("Stub!");
        }

        public E floor(E e) {
            throw new RuntimeException("Stub!");
        }

        public E ceiling(E e) {
            throw new RuntimeException("Stub!");
        }

        public E higher(E e) {
            throw new RuntimeException("Stub!");
        }

        public E pollFirst() {
            throw new RuntimeException("Stub!");
        }

        public E pollLast() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> descendingSet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> descendingIterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> subSet(E fromElement, E toElement) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> headSet(E toElement) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> tailSet(E fromElement) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> subSet(
                E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> headSet(E toElement, boolean inclusive) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
            throw new RuntimeException("Stub!");
        }

        private final java.util.NavigableSet<E> ns;

        {
            ns = null;
        }

        private static final long serialVersionUID = -5505529816273629798L; // 0xb3986dcd38b04d9aL
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class SynchronizedRandomAccessList<E> extends java.util.Collections.SynchronizedList<E>
            implements java.util.RandomAccess {

        SynchronizedRandomAccessList(java.util.List<E> list) {
            super((java.util.List) null);
            throw new RuntimeException("Stub!");
        }

        SynchronizedRandomAccessList(java.util.List<E> list, java.lang.Object mutex) {
            super((java.util.List) null);
            throw new RuntimeException("Stub!");
        }

        public java.util.List<E> subList(int fromIndex, int toIndex) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.Object writeReplace() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = 1530674583602358482L; // 0x153e0c6c865668d2L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class SynchronizedSet<E> extends java.util.Collections.SynchronizedCollection<E>
            implements java.util.Set<E> {

        SynchronizedSet(java.util.Set<E> s) {
            super((java.util.Collection) null);
            throw new RuntimeException("Stub!");
        }

        SynchronizedSet(java.util.Set<E> s, java.lang.Object mutex) {
            super((java.util.Collection) null);
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = 487447009682186044L; // 0x6c3c27902eedf3cL
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class SynchronizedSortedMap<K, V> extends java.util.Collections.SynchronizedMap<K, V>
            implements java.util.SortedMap<K, V> {

        SynchronizedSortedMap(java.util.SortedMap<K, V> m) {
            super((java.util.Map) null);
            throw new RuntimeException("Stub!");
        }

        SynchronizedSortedMap(java.util.SortedMap<K, V> m, java.lang.Object mutex) {
            super((java.util.Map) null);
            throw new RuntimeException("Stub!");
        }

        public java.util.Comparator<? super K> comparator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedMap<K, V> subMap(K fromKey, K toKey) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedMap<K, V> headMap(K toKey) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedMap<K, V> tailMap(K fromKey) {
            throw new RuntimeException("Stub!");
        }

        public K firstKey() {
            throw new RuntimeException("Stub!");
        }

        public K lastKey() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = -8798146769416483793L; // 0x85e6b420b72e0c2fL

        private final java.util.SortedMap<K, V> sm;

        {
            sm = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class SynchronizedSortedSet<E> extends java.util.Collections.SynchronizedSet<E>
            implements java.util.SortedSet<E> {

        SynchronizedSortedSet(java.util.SortedSet<E> s) {
            super((java.util.Set) null);
            throw new RuntimeException("Stub!");
        }

        SynchronizedSortedSet(java.util.SortedSet<E> s, java.lang.Object mutex) {
            super((java.util.Set) null);
            throw new RuntimeException("Stub!");
        }

        public java.util.Comparator<? super E> comparator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedSet<E> subSet(E fromElement, E toElement) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedSet<E> headSet(E toElement) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedSet<E> tailSet(E fromElement) {
            throw new RuntimeException("Stub!");
        }

        public E first() {
            throw new RuntimeException("Stub!");
        }

        public E last() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = 8695801310862127406L; // 0x78adb1384b50312eL

        private final java.util.SortedSet<E> ss;

        {
            ss = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class UnmodifiableCollection<E>
            implements java.util.Collection<E>, java.io.Serializable {

        UnmodifiableCollection(java.util.Collection<? extends E> c) {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object[] toArray() {
            throw new RuntimeException("Stub!");
        }

        public <T> T[] toArray(T[] a) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> iterator() {
            throw new RuntimeException("Stub!");
        }

        public boolean add(E e) {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public boolean containsAll(java.util.Collection<?> coll) {
            throw new RuntimeException("Stub!");
        }

        public boolean addAll(java.util.Collection<? extends E> coll) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeAll(java.util.Collection<?> coll) {
            throw new RuntimeException("Stub!");
        }

        public boolean retainAll(java.util.Collection<?> coll) {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeIf(java.util.function.Predicate<? super E> filter) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.stream.Stream<E> stream() {
            throw new RuntimeException("Stub!");
        }

        public java.util.stream.Stream<E> parallelStream() {
            throw new RuntimeException("Stub!");
        }

        @UnsupportedAppUsage
        final java.util.Collection<? extends E> c;

        {
            c = null;
        }

        private static final long serialVersionUID = 1820017752578914078L; // 0x19420080cb5ef71eL
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class UnmodifiableList<E> extends java.util.Collections.UnmodifiableCollection<E>
            implements java.util.List<E> {

        UnmodifiableList(java.util.List<? extends E> list) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public E get(int index) {
            throw new RuntimeException("Stub!");
        }

        public E set(int index, E element) {
            throw new RuntimeException("Stub!");
        }

        public void add(int index, E element) {
            throw new RuntimeException("Stub!");
        }

        public E remove(int index) {
            throw new RuntimeException("Stub!");
        }

        public int indexOf(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int lastIndexOf(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public boolean addAll(int index, java.util.Collection<? extends E> c) {
            throw new RuntimeException("Stub!");
        }

        public void replaceAll(java.util.function.UnaryOperator<E> operator) {
            throw new RuntimeException("Stub!");
        }

        public void sort(java.util.Comparator<? super E> c) {
            throw new RuntimeException("Stub!");
        }

        public java.util.ListIterator<E> listIterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.ListIterator<E> listIterator(int index) {
            throw new RuntimeException("Stub!");
        }

        public java.util.List<E> subList(int fromIndex, int toIndex) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.Object readResolve() {
            throw new RuntimeException("Stub!");
        }

        final java.util.List<? extends E> list;

        {
            list = null;
        }

        private static final long serialVersionUID = -283967356065247728L; // 0xfc0f2531b5ec8e10L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class UnmodifiableMap<K, V>
            implements java.util.Map<K, V>, java.io.Serializable {

        UnmodifiableMap(java.util.Map<? extends K, ? extends V> m) {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public boolean containsKey(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public boolean containsValue(java.lang.Object val) {
            throw new RuntimeException("Stub!");
        }

        public V get(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public V put(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public V remove(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public void putAll(java.util.Map<? extends K, ? extends V> m) {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Set<K> keySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Collection<V> values() {
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        public V getOrDefault(java.lang.Object k, V defaultValue) {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.BiConsumer<? super K, ? super V> action) {
            throw new RuntimeException("Stub!");
        }

        public void replaceAll(
                java.util.function.BiFunction<? super K, ? super V, ? extends V> function) {
            throw new RuntimeException("Stub!");
        }

        public V putIfAbsent(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object key, java.lang.Object value) {
            throw new RuntimeException("Stub!");
        }

        public boolean replace(K key, V oldValue, V newValue) {
            throw new RuntimeException("Stub!");
        }

        public V replace(K key, V value) {
            throw new RuntimeException("Stub!");
        }

        public V computeIfAbsent(
                K key, java.util.function.Function<? super K, ? extends V> mappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V computeIfPresent(
                K key,
                java.util.function.BiFunction<? super K, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V compute(
                K key,
                java.util.function.BiFunction<? super K, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        public V merge(
                K key,
                V value,
                java.util.function.BiFunction<? super V, ? super V, ? extends V>
                        remappingFunction) {
            throw new RuntimeException("Stub!");
        }

        private transient java.util.Set<java.util.Map.Entry<K, V>> entrySet;

        private transient java.util.Set<K> keySet;

        @UnsupportedAppUsage
        private final java.util.Map<? extends K, ? extends V> m;

        {
            m = null;
        }

        private static final long serialVersionUID = -1034234728574286014L; // 0xf1a5a8fe74f50742L

        private transient java.util.Collection<V> values;

        @SuppressWarnings({"unchecked", "deprecation", "all"})
        static class UnmodifiableEntrySet<K, V>
                extends java.util.Collections.UnmodifiableSet<java.util.Map.Entry<K, V>> {

            UnmodifiableEntrySet(
                    java.util.Set<? extends java.util.Map.Entry<? extends K, ? extends V>> s) {
                super(null);
                throw new RuntimeException("Stub!");
            }

            static <K, V> java.util.function.Consumer<java.util.Map.Entry<K, V>> entryConsumer(
                    java.util.function.Consumer<? super java.util.Map.Entry<K, V>> action) {
                throw new RuntimeException("Stub!");
            }

            public void forEach(
                    java.util.function.Consumer<? super java.util.Map.Entry<K, V>> action) {
                throw new RuntimeException("Stub!");
            }

            public java.util.Spliterator<java.util.Map.Entry<K, V>> spliterator() {
                throw new RuntimeException("Stub!");
            }

            public java.util.stream.Stream<java.util.Map.Entry<K, V>> stream() {
                throw new RuntimeException("Stub!");
            }

            public java.util.stream.Stream<java.util.Map.Entry<K, V>> parallelStream() {
                throw new RuntimeException("Stub!");
            }

            public java.util.Iterator<java.util.Map.Entry<K, V>> iterator() {
                throw new RuntimeException("Stub!");
            }

            public java.lang.Object[] toArray() {
                throw new RuntimeException("Stub!");
            }

            public <T> T[] toArray(T[] a) {
                throw new RuntimeException("Stub!");
            }

            public boolean contains(java.lang.Object o) {
                throw new RuntimeException("Stub!");
            }

            public boolean containsAll(java.util.Collection<?> coll) {
                throw new RuntimeException("Stub!");
            }

            public boolean equals(java.lang.Object o) {
                throw new RuntimeException("Stub!");
            }

            private static final long serialVersionUID =
                    7854390611657943733L; // 0x6d0066a59f08eab5L

            @SuppressWarnings({"unchecked", "deprecation", "all"})
            private static class UnmodifiableEntry<K, V> implements java.util.Map.Entry<K, V> {

                UnmodifiableEntry(java.util.Map.Entry<? extends K, ? extends V> e) {
                    throw new RuntimeException("Stub!");
                }

                public K getKey() {
                    throw new RuntimeException("Stub!");
                }

                public V getValue() {
                    throw new RuntimeException("Stub!");
                }

                public V setValue(V value) {
                    throw new RuntimeException("Stub!");
                }

                public int hashCode() {
                    throw new RuntimeException("Stub!");
                }

                public boolean equals(java.lang.Object o) {
                    throw new RuntimeException("Stub!");
                }

                public java.lang.String toString() {
                    throw new RuntimeException("Stub!");
                }

                private java.util.Map.Entry<? extends K, ? extends V> e;
            }

            @SuppressWarnings({"unchecked", "deprecation", "all"})
            static final class UnmodifiableEntrySetSpliterator<K, V>
                    implements java.util.Spliterator<java.util.Map.Entry<K, V>> {

                UnmodifiableEntrySetSpliterator(
                        java.util.Spliterator<java.util.Map.Entry<K, V>> s) {
                    throw new RuntimeException("Stub!");
                }

                public boolean tryAdvance(
                        java.util.function.Consumer<? super java.util.Map.Entry<K, V>> action) {
                    throw new RuntimeException("Stub!");
                }

                public void forEachRemaining(
                        java.util.function.Consumer<? super java.util.Map.Entry<K, V>> action) {
                    throw new RuntimeException("Stub!");
                }

                public java.util.Spliterator<java.util.Map.Entry<K, V>> trySplit() {
                    throw new RuntimeException("Stub!");
                }

                public long estimateSize() {
                    throw new RuntimeException("Stub!");
                }

                public long getExactSizeIfKnown() {
                    throw new RuntimeException("Stub!");
                }

                public int characteristics() {
                    throw new RuntimeException("Stub!");
                }

                public boolean hasCharacteristics(int characteristics) {
                    throw new RuntimeException("Stub!");
                }

                public java.util.Comparator<? super java.util.Map.Entry<K, V>> getComparator() {
                    throw new RuntimeException("Stub!");
                }

                final java.util.Spliterator<java.util.Map.Entry<K, V>> s;

                {
                    s = null;
                }
            }
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class UnmodifiableNavigableMap<K, V>
            extends java.util.Collections.UnmodifiableSortedMap<K, V>
            implements java.util.NavigableMap<K, V>, java.io.Serializable {

        UnmodifiableNavigableMap(java.util.NavigableMap<K, ? extends V> m) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        public K lowerKey(K key) {
            throw new RuntimeException("Stub!");
        }

        public K floorKey(K key) {
            throw new RuntimeException("Stub!");
        }

        public K ceilingKey(K key) {
            throw new RuntimeException("Stub!");
        }

        public K higherKey(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> lowerEntry(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> floorEntry(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> ceilingEntry(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> higherEntry(K key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> firstEntry() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> lastEntry() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> pollFirstEntry() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> pollLastEntry() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> descendingMap() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<K> navigableKeySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<K> descendingKeySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> subMap(
                K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            throw new RuntimeException("Stub!");
        }

        private static final java.util.Collections.UnmodifiableNavigableMap.EmptyNavigableMap<?, ?>
                EMPTY_NAVIGABLE_MAP;

        static {
            EMPTY_NAVIGABLE_MAP = null;
        }

        private final java.util.NavigableMap<K, ? extends V> nm;

        {
            nm = null;
        }

        private static final long serialVersionUID = -4858195264774772197L; // 0xbc943925819d6a1bL

        @SuppressWarnings({"unchecked", "deprecation", "all"})
        private static class EmptyNavigableMap<K, V>
                extends java.util.Collections.UnmodifiableNavigableMap<K, V>
                implements java.io.Serializable {

            EmptyNavigableMap() {
                super(null);
                throw new RuntimeException("Stub!");
            }

            public java.util.NavigableSet<K> navigableKeySet() {
                throw new RuntimeException("Stub!");
            }

            private java.lang.Object readResolve() {
                throw new RuntimeException("Stub!");
            }

            private static final long serialVersionUID =
                    -2239321462712562324L; // 0xe0ec54fe7d1c0d6cL
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class UnmodifiableNavigableSet<E> extends java.util.Collections.UnmodifiableSortedSet<E>
            implements java.util.NavigableSet<E>, java.io.Serializable {

        UnmodifiableNavigableSet(java.util.NavigableSet<E> s) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        public E lower(E e) {
            throw new RuntimeException("Stub!");
        }

        public E floor(E e) {
            throw new RuntimeException("Stub!");
        }

        public E ceiling(E e) {
            throw new RuntimeException("Stub!");
        }

        public E higher(E e) {
            throw new RuntimeException("Stub!");
        }

        public E pollFirst() {
            throw new RuntimeException("Stub!");
        }

        public E pollLast() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> descendingSet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> descendingIterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> subSet(
                E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> headSet(E toElement, boolean inclusive) {
            throw new RuntimeException("Stub!");
        }

        public java.util.NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
            throw new RuntimeException("Stub!");
        }

        private static final java.util.NavigableSet<?> EMPTY_NAVIGABLE_SET;

        static {
            EMPTY_NAVIGABLE_SET = null;
        }

        private final java.util.NavigableSet<E> ns;

        {
            ns = null;
        }

        private static final long serialVersionUID = -6027448201786391929L; // 0xac5a33cb96748287L

        @SuppressWarnings({"unchecked", "deprecation", "all"})
        private static class EmptyNavigableSet<E>
                extends java.util.Collections.UnmodifiableNavigableSet<E>
                implements java.io.Serializable {

            public EmptyNavigableSet() {
                super(null);
                throw new RuntimeException("Stub!");
            }

            private java.lang.Object readResolve() {
                throw new RuntimeException("Stub!");
            }

            private static final long serialVersionUID =
                    -6291252904449939134L; // 0xa8b0fad0de1de942L
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class UnmodifiableRandomAccessList<E> extends java.util.Collections.UnmodifiableList<E>
            implements java.util.RandomAccess {

        UnmodifiableRandomAccessList(java.util.List<? extends E> list) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        public java.util.List<E> subList(int fromIndex, int toIndex) {
            throw new RuntimeException("Stub!");
        }

        private java.lang.Object writeReplace() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = -2542308836966382001L; // 0xdcb7e7951f48464fL
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class UnmodifiableSet<E> extends java.util.Collections.UnmodifiableCollection<E>
            implements java.util.Set<E>, java.io.Serializable {

        UnmodifiableSet(java.util.Set<? extends E> s) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = -9215047833775013803L; // 0x801d92d18f9b8055L
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class UnmodifiableSortedMap<K, V> extends java.util.Collections.UnmodifiableMap<K, V>
            implements java.util.SortedMap<K, V>, java.io.Serializable {

        UnmodifiableSortedMap(java.util.SortedMap<K, ? extends V> m) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        public java.util.Comparator<? super K> comparator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedMap<K, V> subMap(K fromKey, K toKey) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedMap<K, V> headMap(K toKey) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedMap<K, V> tailMap(K fromKey) {
            throw new RuntimeException("Stub!");
        }

        public K firstKey() {
            throw new RuntimeException("Stub!");
        }

        public K lastKey() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = -8806743815996713206L; // 0x85c82928d3a5d70aL

        private final java.util.SortedMap<K, ? extends V> sm;

        {
            sm = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class UnmodifiableSortedSet<E> extends java.util.Collections.UnmodifiableSet<E>
            implements java.util.SortedSet<E>, java.io.Serializable {

        UnmodifiableSortedSet(java.util.SortedSet<E> s) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        public java.util.Comparator<? super E> comparator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedSet<E> subSet(E fromElement, E toElement) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedSet<E> headSet(E toElement) {
            throw new RuntimeException("Stub!");
        }

        public java.util.SortedSet<E> tailSet(E fromElement) {
            throw new RuntimeException("Stub!");
        }

        public E first() {
            throw new RuntimeException("Stub!");
        }

        public E last() {
            throw new RuntimeException("Stub!");
        }

        private static final long serialVersionUID = -4929149591599911165L; // 0xbb98248febecef03L

        private final java.util.SortedSet<E> ss;

        {
            ss = null;
        }
    }
}
