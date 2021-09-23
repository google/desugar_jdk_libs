/*
 * Copyright (C) 2014 The Android Open Source Project
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
public class ArrayList<E> extends java.util.AbstractList<E>
        implements java.util.List<E>,
                java.util.RandomAccess,
                java.lang.Cloneable,
                java.io.Serializable {

    public ArrayList(int initialCapacity) {
        throw new RuntimeException("Stub!");
    }

    public ArrayList() {
        throw new RuntimeException("Stub!");
    }

    public ArrayList(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    public void trimToSize() {
        throw new RuntimeException("Stub!");
    }

    public void ensureCapacity(int minCapacity) {
        throw new RuntimeException("Stub!");
    }

    private void ensureCapacityInternal(int minCapacity) {
        throw new RuntimeException("Stub!");
    }

    private void ensureExplicitCapacity(int minCapacity) {
        throw new RuntimeException("Stub!");
    }

    private void grow(int minCapacity) {
        throw new RuntimeException("Stub!");
    }

    private static int hugeCapacity(int minCapacity) {
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

    public int indexOf(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public int lastIndexOf(java.lang.Object o) {
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

    private void fastRemove(int index) {
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

    protected void removeRange(int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String outOfBoundsMsg(int index) {
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

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.ListIterator<E> listIterator(int index) {
        throw new RuntimeException("Stub!");
    }

    public java.util.ListIterator<E> listIterator() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Iterator<E> iterator() {
        throw new RuntimeException("Stub!");
    }

    public java.util.List<E> subList(int fromIndex, int toIndex) {
        throw new RuntimeException("Stub!");
    }

    static void subListRangeCheck(int fromIndex, int toIndex, int size) {
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

    public void replaceAll(java.util.function.UnaryOperator<E> operator) {
        throw new RuntimeException("Stub!");
    }

    public void sort(java.util.Comparator<? super E> c) {
        throw new RuntimeException("Stub!");
    }

    private static final java.lang.Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA;

    static {
        DEFAULTCAPACITY_EMPTY_ELEMENTDATA = new java.lang.Object[0];
    }

    private static final int DEFAULT_CAPACITY = 10; // 0xa

    private static final java.lang.Object[] EMPTY_ELEMENTDATA;

    static {
        EMPTY_ELEMENTDATA = new java.lang.Object[0];
    }

    private static final int MAX_ARRAY_SIZE = 2147483639; // 0x7ffffff7

    @UnsupportedAppUsage
    transient java.lang.Object[] elementData;

    private static final long serialVersionUID = 8683452581122892189L; // 0x7881d21d99c7619dL

    @UnsupportedAppUsage
    private int size;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class ArrayListSpliterator<E> implements java.util.Spliterator<E> {

        ArrayListSpliterator(
                java.util.ArrayList<E> list, int origin, int fence, int expectedModCount) {
            throw new RuntimeException("Stub!");
        }

        private int getFence() {
            throw new RuntimeException("Stub!");
        }

        public java.util.ArrayList.ArrayListSpliterator<E> trySplit() {
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

        private int expectedModCount;

        private int fence;

        private int index;

        private final java.util.ArrayList<E> list;

        {
            list = null;
        }
    }

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

        public void forEachRemaining(java.util.function.Consumer<? super E> consumer) {
            throw new RuntimeException("Stub!");
        }

        int cursor;

        int expectedModCount;

        int lastRet = -1; // 0xffffffff

        protected int limit;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class ListItr extends Itr implements java.util.ListIterator<E> {

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
    private class SubList extends java.util.AbstractList<E> implements java.util.RandomAccess {

        SubList(java.util.AbstractList<E> parent, int offset, int fromIndex, int toIndex) {
            throw new RuntimeException("Stub!");
        }

        public E set(int index, E e) {
            throw new RuntimeException("Stub!");
        }

        public E get(int index) {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public void add(int index, E e) {
            throw new RuntimeException("Stub!");
        }

        public E remove(int index) {
            throw new RuntimeException("Stub!");
        }

        protected void removeRange(int fromIndex, int toIndex) {
            throw new RuntimeException("Stub!");
        }

        public boolean addAll(java.util.Collection<? extends E> c) {
            throw new RuntimeException("Stub!");
        }

        public boolean addAll(int index, java.util.Collection<? extends E> c) {
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

        private java.lang.String outOfBoundsMsg(int index) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<E> spliterator() {
            throw new RuntimeException("Stub!");
        }

        @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        private final int offset;

        {
            offset = 0;
        }

        @UnsupportedAppUsage
        private final java.util.AbstractList<E> parent;

        {
            parent = null;
        }

        @UnsupportedAppUsage
        private final int parentOffset;

        {
            parentOffset = 0;
        }

        @UnsupportedAppUsage(publicAlternatives = "Please update to a current version of the "
        + "{@code Streamsupport} library; older versions of {@code Streamsupport} do not "
        + "support current versions of Android.")
        int size;
    }
}
