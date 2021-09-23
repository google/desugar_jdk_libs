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
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group.  Adapted and released, under explicit permission,
 * from JDK ArrayList.java which carries the following copyright:
 *
 * Copyright 1997 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 */

package java.util.concurrent;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class CopyOnWriteArrayList<E>
        implements java.util.List<E>,
                java.util.RandomAccess,
                java.lang.Cloneable,
                java.io.Serializable {

    public CopyOnWriteArrayList() {
        throw new RuntimeException("Stub!");
    }

    public CopyOnWriteArrayList(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    public CopyOnWriteArrayList(E[] toCopyIn) {
        throw new RuntimeException("Stub!");
    }

    final java.lang.Object[] getArray() {
        throw new RuntimeException("Stub!");
    }

    final void setArray(java.lang.Object[] a) {
        throw new RuntimeException("Stub!");
    }

    public int size() {
        throw new RuntimeException("Stub!");
    }

    public boolean isEmpty() {
        throw new RuntimeException("Stub!");
    }

    private static int indexOf(
            java.lang.Object o, java.lang.Object[] elements, int index, int fence) {
        throw new RuntimeException("Stub!");
    }

    private static int lastIndexOf(java.lang.Object o, java.lang.Object[] elements, int index) {
        throw new RuntimeException("Stub!");
    }

    public boolean contains(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public int indexOf(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public int indexOf(E e, int index) {
        throw new RuntimeException("Stub!");
    }

    public int lastIndexOf(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public int lastIndexOf(E e, int index) {
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

    private E get(java.lang.Object[] a, int index) {
        throw new RuntimeException("Stub!");
    }

    static java.lang.String outOfBounds(int index, int size) {
        throw new RuntimeException("Stub!");
    }

    public E get(int index) {
        throw new RuntimeException("Stub!");
    }

    public E set(int index, E element) {
        throw new RuntimeException("Stub!");
    }

    public boolean add(E e) {
        throw new RuntimeException("Stub!");
    }

    public void add(int index, E element) {
        throw new RuntimeException("Stub!");
    }

    public E remove(int index) {
        throw new RuntimeException("Stub!");
    }

    public boolean remove(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    private boolean remove(java.lang.Object o, java.lang.Object[] snapshot, int index) {
        throw new RuntimeException("Stub!");
    }

    void removeRange(int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    public boolean addIfAbsent(E e) {
        throw new RuntimeException("Stub!");
    }

    private boolean addIfAbsent(E e, java.lang.Object[] snapshot) {
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

    public int addAllAbsent(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    public void clear() {
        throw new RuntimeException("Stub!");
    }

    public boolean addAll(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    public boolean addAll(int index, java.util.Collection<? extends E> c) {
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

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Iterator<E> iterator() {
        throw new RuntimeException("Stub!");
    }

    public java.util.ListIterator<E> listIterator() {
        throw new RuntimeException("Stub!");
    }

    public java.util.ListIterator<E> listIterator(int index) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Spliterator<E> spliterator() {
        throw new RuntimeException("Stub!");
    }

    public java.util.List<E> subList(int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    private void resetLock() {
        throw new RuntimeException("Stub!");
    }

    private static final long LOCK;

    static {
        LOCK = 0;
    }

    private static final sun.misc.Unsafe U;

    static {
        U = null;
    }

    @UnsupportedAppUsage
    private transient volatile java.lang.Object[] elements;

    final transient java.lang.Object lock;

    {
        lock = null;
    }

    private static final long serialVersionUID = 8673264195747942595L; // 0x785d9fd546ab90c3L

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class COWIterator<E> implements java.util.ListIterator<E> {

        COWIterator(java.lang.Object[] elements, int initialCursor) {
            throw new RuntimeException("Stub!");
        }

        public boolean hasNext() {
            throw new RuntimeException("Stub!");
        }

        public boolean hasPrevious() {
            throw new RuntimeException("Stub!");
        }

        public E next() {
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

        private int cursor;

        private final java.lang.Object[] snapshot;

        {
            snapshot = new java.lang.Object[0];
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class COWSubList<E> extends java.util.AbstractList<E>
            implements java.util.RandomAccess {

        COWSubList(java.util.concurrent.CopyOnWriteArrayList<E> list, int fromIndex, int toIndex) {
            throw new RuntimeException("Stub!");
        }

        private void checkForComodification() {
            throw new RuntimeException("Stub!");
        }

        private void rangeCheck(int index) {
            throw new RuntimeException("Stub!");
        }

        public E set(int index, E element) {
            throw new RuntimeException("Stub!");
        }

        public E get(int index) {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public void add(int index, E element) {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        public E remove(int index) {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<E> iterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.ListIterator<E> listIterator(int index) {
            throw new RuntimeException("Stub!");
        }

        public java.util.List<E> subList(int fromIndex, int toIndex) {
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

        public boolean removeAll(java.util.Collection<?> c) {
            throw new RuntimeException("Stub!");
        }

        public boolean retainAll(java.util.Collection<?> c) {
            throw new RuntimeException("Stub!");
        }

        public boolean removeIf(java.util.function.Predicate<? super E> filter) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        private java.lang.Object[] expectedArray;

        private final java.util.concurrent.CopyOnWriteArrayList<E> l;

        {
            l = null;
        }

        private final int offset;

        {
            offset = 0;
        }

        private int size;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class COWSubListIterator<E> implements java.util.ListIterator<E> {

        COWSubListIterator(java.util.List<E> l, int index, int offset, int size) {
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

        private final java.util.ListIterator<E> it;

        {
            it = null;
        }

        private final int offset;

        {
            offset = 0;
        }

        private final int size;

        {
            size = 0;
        }
    }
}
