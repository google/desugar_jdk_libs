/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
public class LinkedList<E> extends java.util.AbstractSequentialList<E>
        implements java.util.List<E>,
                java.util.Deque<E>,
                java.lang.Cloneable,
                java.io.Serializable {

    public LinkedList() {
        throw new RuntimeException("Stub!");
    }

    public LinkedList(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    private void linkFirst(E e) {
        throw new RuntimeException("Stub!");
    }

    void linkLast(E e) {
        throw new RuntimeException("Stub!");
    }

    void linkBefore(E e, java.util.LinkedList.Node<E> succ) {
        throw new RuntimeException("Stub!");
    }

    private E unlinkFirst(java.util.LinkedList.Node<E> f) {
        throw new RuntimeException("Stub!");
    }

    private E unlinkLast(java.util.LinkedList.Node<E> l) {
        throw new RuntimeException("Stub!");
    }

    E unlink(java.util.LinkedList.Node<E> x) {
        throw new RuntimeException("Stub!");
    }

    public E getFirst() {
        throw new RuntimeException("Stub!");
    }

    public E getLast() {
        throw new RuntimeException("Stub!");
    }

    public E removeFirst() {
        throw new RuntimeException("Stub!");
    }

    public E removeLast() {
        throw new RuntimeException("Stub!");
    }

    public void addFirst(E e) {
        throw new RuntimeException("Stub!");
    }

    public void addLast(E e) {
        throw new RuntimeException("Stub!");
    }

    public boolean contains(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public int size() {
        throw new RuntimeException("Stub!");
    }

    public boolean add(E e) {
        throw new RuntimeException("Stub!");
    }

    public boolean remove(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public boolean addAll(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    public boolean addAll(int index, java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    public void clear() {
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

    private boolean isElementIndex(int index) {
        throw new RuntimeException("Stub!");
    }

    private boolean isPositionIndex(int index) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String outOfBoundsMsg(int index) {
        throw new RuntimeException("Stub!");
    }

    private void checkElementIndex(int index) {
        throw new RuntimeException("Stub!");
    }

    private void checkPositionIndex(int index) {
        throw new RuntimeException("Stub!");
    }

    java.util.LinkedList.Node<E> node(int index) {
        throw new RuntimeException("Stub!");
    }

    public int indexOf(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public int lastIndexOf(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public E peek() {
        throw new RuntimeException("Stub!");
    }

    public E element() {
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

    public boolean offerFirst(E e) {
        throw new RuntimeException("Stub!");
    }

    public boolean offerLast(E e) {
        throw new RuntimeException("Stub!");
    }

    public E peekFirst() {
        throw new RuntimeException("Stub!");
    }

    public E peekLast() {
        throw new RuntimeException("Stub!");
    }

    public E pollFirst() {
        throw new RuntimeException("Stub!");
    }

    public E pollLast() {
        throw new RuntimeException("Stub!");
    }

    public void push(E e) {
        throw new RuntimeException("Stub!");
    }

    public E pop() {
        throw new RuntimeException("Stub!");
    }

    public boolean removeFirstOccurrence(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public boolean removeLastOccurrence(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public java.util.ListIterator<E> listIterator(int index) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Iterator<E> descendingIterator() {
        throw new RuntimeException("Stub!");
    }

    private java.util.LinkedList<E> superClone() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object clone() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object[] toArray() {
        throw new RuntimeException("Stub!");
    }

    public <T> T[] toArray(T[] a) {
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

    @UnsupportedAppUsage
    transient java.util.LinkedList.Node<E> first;

    transient java.util.LinkedList.Node<E> last;

    private static final long serialVersionUID = 876323262645176354L; // 0xc29535d4a608822L

    @UnsupportedAppUsage
    transient int size = 0; // 0x0

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

        private final java.util.LinkedList.ListItr itr;

        {
            itr = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class LLSpliterator<E> implements java.util.Spliterator<E> {

        LLSpliterator(java.util.LinkedList<E> list, int est, int expectedModCount) {
            throw new RuntimeException("Stub!");
        }

        int getEst() {
            throw new RuntimeException("Stub!");
        }

        public long estimateSize() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> trySplit() {
            throw new RuntimeException("Stub!");
        }

        public void forEachRemaining(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public boolean tryAdvance(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public int characteristics() {
            throw new RuntimeException("Stub!");
        }

        static final int BATCH_UNIT = 1024; // 0x400

        static final int MAX_BATCH = 33554432; // 0x2000000

        int batch;

        java.util.LinkedList.Node<E> current;

        int est;

        int expectedModCount;

        final java.util.LinkedList<E> list;

        {
            list = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class ListItr implements java.util.ListIterator<E> {

        ListItr(int index) {
            throw new RuntimeException("Stub!");
        }

        public boolean hasNext() {
            throw new RuntimeException("Stub!");
        }

        public E next() {
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

        public void remove() {
            throw new RuntimeException("Stub!");
        }

        public void set(E e) {
            throw new RuntimeException("Stub!");
        }

        public void add(E e) {
            throw new RuntimeException("Stub!");
        }

        public void forEachRemaining(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        final void checkForComodification() {
            throw new RuntimeException("Stub!");
        }

        private int expectedModCount;

        private java.util.LinkedList.Node<E> lastReturned;

        private java.util.LinkedList.Node<E> next;

        private int nextIndex;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class Node<E> {

        Node(java.util.LinkedList.Node<E> prev, E element, java.util.LinkedList.Node<E> next) {
            throw new RuntimeException("Stub!");
        }

        @UnsupportedAppUsage
        E item;

        @UnsupportedAppUsage
        java.util.LinkedList.Node<E> next;

        java.util.LinkedList.Node<E> prev;
    }
}
