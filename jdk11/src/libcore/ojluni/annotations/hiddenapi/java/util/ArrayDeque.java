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
 * Written by Josh Bloch of Google Inc. and released to the public domain,
 * as explained at http://creativecommons.org/publicdomain/zero/1.0/.
 */

package java.util;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class ArrayDeque<E> extends java.util.AbstractCollection<E>
        implements java.util.Deque<E>, java.lang.Cloneable, java.io.Serializable {

    public ArrayDeque() {
        throw new RuntimeException("Stub!");
    }

    public ArrayDeque(int numElements) {
        throw new RuntimeException("Stub!");
    }

    public ArrayDeque(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    private void allocateElements(int numElements) {
        throw new RuntimeException("Stub!");
    }

    private void doubleCapacity() {
        throw new RuntimeException("Stub!");
    }

    public void addFirst(E e) {
        throw new RuntimeException("Stub!");
    }

    public void addLast(E e) {
        throw new RuntimeException("Stub!");
    }

    public boolean offerFirst(E e) {
        throw new RuntimeException("Stub!");
    }

    public boolean offerLast(E e) {
        throw new RuntimeException("Stub!");
    }

    public E removeFirst() {
        throw new RuntimeException("Stub!");
    }

    public E removeLast() {
        throw new RuntimeException("Stub!");
    }

    public E pollFirst() {
        throw new RuntimeException("Stub!");
    }

    public E pollLast() {
        throw new RuntimeException("Stub!");
    }

    public E getFirst() {
        throw new RuntimeException("Stub!");
    }

    public E getLast() {
        throw new RuntimeException("Stub!");
    }

    public E peekFirst() {
        throw new RuntimeException("Stub!");
    }

    public E peekLast() {
        throw new RuntimeException("Stub!");
    }

    public boolean removeFirstOccurrence(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public boolean removeLastOccurrence(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public boolean add(E e) {
        throw new RuntimeException("Stub!");
    }

    public boolean offer(E e) {
        throw new RuntimeException("Stub!");
    }

    public E remove() {
        throw new RuntimeException("Stub!");
    }

    public E poll() {
        throw new RuntimeException("Stub!");
    }

    public E element() {
        throw new RuntimeException("Stub!");
    }

    public E peek() {
        throw new RuntimeException("Stub!");
    }

    public void push(E e) {
        throw new RuntimeException("Stub!");
    }

    public E pop() {
        throw new RuntimeException("Stub!");
    }

    private void checkInvariants() {
        throw new RuntimeException("Stub!");
    }

    boolean delete(int i) {
        throw new RuntimeException("Stub!");
    }

    public int size() {
        throw new RuntimeException("Stub!");
    }

    public boolean isEmpty() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Iterator<E> iterator() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Iterator<E> descendingIterator() {
        throw new RuntimeException("Stub!");
    }

    public boolean contains(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public boolean remove(java.lang.Object o) {
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

    public java.util.ArrayDeque<E> clone() {
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

    private static final int MIN_INITIAL_CAPACITY = 8; // 0x8

    @UnsupportedAppUsage
    transient java.lang.Object[] elements;

    @UnsupportedAppUsage
    transient int head;

    private static final long serialVersionUID = 2340985798034038923L; // 0x207cda2e240da08bL

    @UnsupportedAppUsage
    transient int tail;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class DeqIterator implements java.util.Iterator<E> {

        private DeqIterator() {
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

        private int cursor;

        private int fence;

        private int lastRet = -1; // 0xffffffff
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class DeqSpliterator<E> implements java.util.Spliterator<E> {

        DeqSpliterator(java.util.ArrayDeque<E> deq, int origin, int fence) {
            throw new RuntimeException("Stub!");
        }

        private int getFence() {
            throw new RuntimeException("Stub!");
        }

        public java.util.ArrayDeque.DeqSpliterator<E> trySplit() {
            throw new RuntimeException("Stub!");
        }

        public void forEachRemaining(java.util.function.Consumer<? super E> consumer) {
            throw new RuntimeException("Stub!");
        }

        public boolean tryAdvance(java.util.function.Consumer<? super E> consumer) {
            throw new RuntimeException("Stub!");
        }

        public long estimateSize() {
            throw new RuntimeException("Stub!");
        }

        public int characteristics() {
            throw new RuntimeException("Stub!");
        }

        private final java.util.ArrayDeque<E> deq;

        {
            deq = null;
        }

        private int fence;

        private int index;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class DescendingIterator implements java.util.Iterator<E> {

        private DescendingIterator() {
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

        private int fence;

        private int lastRet = -1; // 0xffffffff
    }
}
