/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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
public class PriorityQueue<E> extends java.util.AbstractQueue<E> implements java.io.Serializable {

    public PriorityQueue() {
        throw new RuntimeException("Stub!");
    }

    public PriorityQueue(int initialCapacity) {
        throw new RuntimeException("Stub!");
    }

    public PriorityQueue(java.util.Comparator<? super E> comparator) {
        throw new RuntimeException("Stub!");
    }

    public PriorityQueue(int initialCapacity, java.util.Comparator<? super E> comparator) {
        throw new RuntimeException("Stub!");
    }

    public PriorityQueue(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    public PriorityQueue(java.util.PriorityQueue<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    public PriorityQueue(java.util.SortedSet<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    private void initFromPriorityQueue(java.util.PriorityQueue<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    private void initElementsFromCollection(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    private void initFromCollection(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    private void grow(int minCapacity) {
        throw new RuntimeException("Stub!");
    }

    private static int hugeCapacity(int minCapacity) {
        throw new RuntimeException("Stub!");
    }

    public boolean add(E e) {
        throw new RuntimeException("Stub!");
    }

    public boolean offer(E e) {
        throw new RuntimeException("Stub!");
    }

    public E peek() {
        throw new RuntimeException("Stub!");
    }

    private int indexOf(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public boolean remove(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    boolean removeEq(java.lang.Object o) {
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

    public int size() {
        throw new RuntimeException("Stub!");
    }

    public void clear() {
        throw new RuntimeException("Stub!");
    }

    public E poll() {
        throw new RuntimeException("Stub!");
    }

    E removeAt(int i) {
        throw new RuntimeException("Stub!");
    }

    private void siftUp(int k, E x) {
        throw new RuntimeException("Stub!");
    }

    private void siftUpComparable(int k, E x) {
        throw new RuntimeException("Stub!");
    }

    private void siftUpUsingComparator(int k, E x) {
        throw new RuntimeException("Stub!");
    }

    private void siftDown(int k, E x) {
        throw new RuntimeException("Stub!");
    }

    private void siftDownComparable(int k, E x) {
        throw new RuntimeException("Stub!");
    }

    private void siftDownUsingComparator(int k, E x) {
        throw new RuntimeException("Stub!");
    }

    private void heapify() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Comparator<? super E> comparator() {
        throw new RuntimeException("Stub!");
    }

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final java.util.Spliterator<E> spliterator() {
        throw new RuntimeException("Stub!");
    }

    private static final int DEFAULT_INITIAL_CAPACITY = 11; // 0xb

    private static final int MAX_ARRAY_SIZE = 2147483639; // 0x7ffffff7

    private final java.util.Comparator<? super E> comparator;

    {
        comparator = null;
    }

    @UnsupportedAppUsage
    transient int modCount;

    @UnsupportedAppUsage
    transient java.lang.Object[] queue;

    private static final long serialVersionUID = -7720805057305804111L; // 0x94da30b4fb3f82b1L

    @UnsupportedAppUsage
    int size;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private final class Itr implements java.util.Iterator<E> {

        private Itr() {
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

        private int cursor;

        private int expectedModCount;

        private java.util.ArrayDeque<E> forgetMeNot;

        private int lastRet = -1; // 0xffffffff

        private E lastRetElt;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class PriorityQueueSpliterator<E> implements java.util.Spliterator<E> {

        PriorityQueueSpliterator(
                java.util.PriorityQueue<E> pq, int origin, int fence, int expectedModCount) {
            throw new RuntimeException("Stub!");
        }

        private int getFence() {
            throw new RuntimeException("Stub!");
        }

        public java.util.PriorityQueue.PriorityQueueSpliterator<E> trySplit() {
            throw new RuntimeException("Stub!");
        }

        public void forEachRemaining(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public boolean tryAdvance(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public long estimateSize() {
            throw new RuntimeException("Stub!");
        }

        public int characteristics() {
            throw new RuntimeException("Stub!");
        }

        private int expectedModCount;

        private int fence;

        private int index;

        private final java.util.PriorityQueue<E> pq;

        {
            pq = null;
        }
    }
}
