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

package java.util;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class Vector<E> extends java.util.AbstractList<E>
        implements java.util.List<E>,
                java.util.RandomAccess,
                java.lang.Cloneable,
                java.io.Serializable {

    public Vector(int initialCapacity, int capacityIncrement) {
        throw new RuntimeException("Stub!");
    }

    public Vector(int initialCapacity) {
        throw new RuntimeException("Stub!");
    }

    public Vector() {
        throw new RuntimeException("Stub!");
    }

    public Vector(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void copyInto(java.lang.Object[] anArray) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void trimToSize() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void ensureCapacity(int minCapacity) {
        throw new RuntimeException("Stub!");
    }

    private void ensureCapacityHelper(int minCapacity) {
        throw new RuntimeException("Stub!");
    }

    private void grow(int minCapacity) {
        throw new RuntimeException("Stub!");
    }

    private static int hugeCapacity(int minCapacity) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setSize(int newSize) {
        throw new RuntimeException("Stub!");
    }

    public synchronized int capacity() {
        throw new RuntimeException("Stub!");
    }

    public synchronized int size() {
        throw new RuntimeException("Stub!");
    }

    public synchronized boolean isEmpty() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<E> elements() {
        throw new RuntimeException("Stub!");
    }

    public boolean contains(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public int indexOf(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public synchronized int indexOf(java.lang.Object o, int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized int lastIndexOf(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public synchronized int lastIndexOf(java.lang.Object o, int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized E elementAt(int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized E firstElement() {
        throw new RuntimeException("Stub!");
    }

    public synchronized E lastElement() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setElementAt(E obj, int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void removeElementAt(int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void insertElementAt(E obj, int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void addElement(E obj) {
        throw new RuntimeException("Stub!");
    }

    public synchronized boolean removeElement(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void removeAllElements() {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.Object clone() {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.Object[] toArray() {
        throw new RuntimeException("Stub!");
    }

    public synchronized <T> T[] toArray(T[] a) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    E elementData(int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized E get(int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized E set(int index, E element) {
        throw new RuntimeException("Stub!");
    }

    public synchronized boolean add(E e) {
        throw new RuntimeException("Stub!");
    }

    public boolean remove(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public void add(int index, E element) {
        throw new RuntimeException("Stub!");
    }

    public synchronized E remove(int index) {
        throw new RuntimeException("Stub!");
    }

    public void clear() {
        throw new RuntimeException("Stub!");
    }

    public synchronized boolean containsAll(java.util.Collection<?> c) {
        throw new RuntimeException("Stub!");
    }

    public synchronized boolean addAll(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    public synchronized boolean removeAll(java.util.Collection<?> c) {
        throw new RuntimeException("Stub!");
    }

    public synchronized boolean retainAll(java.util.Collection<?> c) {
        throw new RuntimeException("Stub!");
    }

    public synchronized boolean addAll(int index, java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    public synchronized boolean equals(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public synchronized int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.util.List<E> subList(int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    protected synchronized void removeRange(int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.util.ListIterator<E> listIterator(int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.util.ListIterator<E> listIterator() {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.util.Iterator<E> iterator() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void forEach(java.util.function.Consumer<? super E> action) {
        throw new RuntimeException("Stub!");
    }

    public synchronized boolean removeIf(java.util.function.Predicate<? super E> filter) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void replaceAll(java.util.function.UnaryOperator<E> operator) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void sort(java.util.Comparator<? super E> c) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Spliterator<E> spliterator() {
        throw new RuntimeException("Stub!");
    }

    private static final int MAX_ARRAY_SIZE = 2147483639; // 0x7ffffff7

    protected int capacityIncrement;

    protected int elementCount;

    protected java.lang.Object[] elementData;

    private static final long serialVersionUID = -2767605614048989439L; // 0xd9977d5b803baf01L

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class Itr implements java.util.Iterator<E> {

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

        public void forEachRemaining(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        final void checkForComodification() {
            throw new RuntimeException("Stub!");
        }

        int cursor;

        int expectedModCount;

        int lastRet = -1; // 0xffffffff

        protected int limit;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class ListItr extends Itr implements java.util.ListIterator<E> {

        ListItr(int index) {
            throw new RuntimeException("Stub!");
        }

        public boolean hasPrevious() {
            throw new RuntimeException("Stub!");
        }

        public int nextIndex() {
            throw new RuntimeException("Stub!");
        }

        public int previousIndex() {
            throw new RuntimeException("Stub!");
        }

        public E previous() {
            throw new RuntimeException("Stub!");
        }

        public void set(E e) {
            throw new RuntimeException("Stub!");
        }

        public void add(E e) {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class VectorSpliterator<E> implements java.util.Spliterator<E> {

        VectorSpliterator(
                java.util.Vector<E> list,
                java.lang.Object[] array,
                int origin,
                int fence,
                int expectedModCount) {
            throw new RuntimeException("Stub!");
        }

        private int getFence() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> trySplit() {
            throw new RuntimeException("Stub!");
        }

        public boolean tryAdvance(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public void forEachRemaining(java.util.function.Consumer<? super E> action) {
            throw new RuntimeException("Stub!");
        }

        public long estimateSize() {
            throw new RuntimeException("Stub!");
        }

        public int characteristics() {
            throw new RuntimeException("Stub!");
        }

        private java.lang.Object[] array;

        private int expectedModCount;

        private int fence;

        private int index;

        private final java.util.Vector<E> list;

        {
            list = null;
        }
    }
}
