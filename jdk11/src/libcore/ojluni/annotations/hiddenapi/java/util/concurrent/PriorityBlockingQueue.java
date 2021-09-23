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

package java.util.concurrent;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class PriorityBlockingQueue<E> extends java.util.AbstractQueue<E>
        implements java.util.concurrent.BlockingQueue<E>, java.io.Serializable {

    public PriorityBlockingQueue() {
        throw new RuntimeException("Stub!");
    }

    public PriorityBlockingQueue(int initialCapacity) {
        throw new RuntimeException("Stub!");
    }

    public PriorityBlockingQueue(int initialCapacity, java.util.Comparator<? super E> comparator) {
        throw new RuntimeException("Stub!");
    }

    public PriorityBlockingQueue(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    private void tryGrow(java.lang.Object[] array, int oldCap) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private E dequeue() {
        throw new RuntimeException("Stub!");
    }

    private static <T> void siftUpComparable(int k, T x, java.lang.Object[] array) {
        throw new RuntimeException("Stub!");
    }

    private static <T> void siftUpUsingComparator(
            int k, T x, java.lang.Object[] array, java.util.Comparator<? super T> cmp) {
        throw new RuntimeException("Stub!");
    }

    private static <T> void siftDownComparable(int k, T x, java.lang.Object[] array, int n) {
        throw new RuntimeException("Stub!");
    }

    private static <T> void siftDownUsingComparator(
            int k, T x, java.lang.Object[] array, int n, java.util.Comparator<? super T> cmp) {
        throw new RuntimeException("Stub!");
    }

    private void heapify() {
        throw new RuntimeException("Stub!");
    }

    public boolean add(E e) {
        throw new RuntimeException("Stub!");
    }

    public boolean offer(E e) {
        throw new RuntimeException("Stub!");
    }

    public void put(E e) {
        throw new RuntimeException("Stub!");
    }

    public boolean offer(E e, long timeout, java.util.concurrent.TimeUnit unit) {
        throw new RuntimeException("Stub!");
    }

    public E poll() {
        throw new RuntimeException("Stub!");
    }

    public E take() throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public E poll(long timeout, java.util.concurrent.TimeUnit unit)
            throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public E peek() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Comparator<? super E> comparator() {
        throw new RuntimeException("Stub!");
    }

    public int size() {
        throw new RuntimeException("Stub!");
    }

    public int remainingCapacity() {
        throw new RuntimeException("Stub!");
    }

    private int indexOf(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    private void removeAt(int i) {
        throw new RuntimeException("Stub!");
    }

    public boolean remove(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    void removeEQ(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public boolean contains(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public int drainTo(java.util.Collection<? super E> c) {
        throw new RuntimeException("Stub!");
    }

    public int drainTo(java.util.Collection<? super E> c, int maxElements) {
        throw new RuntimeException("Stub!");
    }

    public void clear() {
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

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.Spliterator<E> spliterator() {
        throw new RuntimeException("Stub!");
    }

    private static final long ALLOCATIONSPINLOCK;

    static {
        ALLOCATIONSPINLOCK = 0;
    }

    private static final int DEFAULT_INITIAL_CAPACITY = 11; // 0xb

    private static final int MAX_ARRAY_SIZE = 2147483639; // 0x7ffffff7

    private static final sun.misc.Unsafe U;

    static {
        U = null;
    }

    private transient volatile int allocationSpinLock;

    private transient java.util.Comparator<? super E> comparator;

    @UnsupportedAppUsage
    private final java.util.concurrent.locks.ReentrantLock lock;

    {
        lock = null;
    }

    @UnsupportedAppUsage
    private final java.util.concurrent.locks.Condition notEmpty;

    {
        notEmpty = null;
    }

    private java.util.PriorityQueue<E> q;

    private transient java.lang.Object[] queue;

    private static final long serialVersionUID = 5595510919245408276L; // 0x4da73f88e6712814L

    private transient int size;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class Itr implements java.util.Iterator<E> {

        Itr(java.lang.Object[] array) {
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

        final java.lang.Object[] array;

        {
            array = new java.lang.Object[0];
        }

        int cursor;

        int lastRet;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class PBQSpliterator<E> implements java.util.Spliterator<E> {

        PBQSpliterator(
                java.util.concurrent.PriorityBlockingQueue<E> queue,
                java.lang.Object[] array,
                int index,
                int fence) {
            throw new RuntimeException("Stub!");
        }

        int getFence() {
            throw new RuntimeException("Stub!");
        }

        public java.util.concurrent.PriorityBlockingQueue.PBQSpliterator<E> trySplit() {
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

        java.lang.Object[] array;

        int fence;

        int index;

        final java.util.concurrent.PriorityBlockingQueue<E> queue;

        {
            queue = null;
        }
    }
}
