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
public class LinkedBlockingDeque<E> extends java.util.AbstractQueue<E>
        implements java.util.concurrent.BlockingDeque<E>, java.io.Serializable {

    public LinkedBlockingDeque() {
        throw new RuntimeException("Stub!");
    }

    public LinkedBlockingDeque(int capacity) {
        throw new RuntimeException("Stub!");
    }

    public LinkedBlockingDeque(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    private boolean linkFirst(Node<E> node) {
        throw new RuntimeException("Stub!");
    }

    private boolean linkLast(Node<E> node) {
        throw new RuntimeException("Stub!");
    }

    private E unlinkFirst() {
        throw new RuntimeException("Stub!");
    }

    private E unlinkLast() {
        throw new RuntimeException("Stub!");
    }

    void unlink(Node<E> x) {
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

    public void putFirst(E e) throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public void putLast(E e) throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public boolean offerFirst(E e, long timeout, java.util.concurrent.TimeUnit unit)
            throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public boolean offerLast(E e, long timeout, java.util.concurrent.TimeUnit unit)
            throws java.lang.InterruptedException {
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

    public E takeFirst() throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public E takeLast() throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public E pollFirst(long timeout, java.util.concurrent.TimeUnit unit)
            throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public E pollLast(long timeout, java.util.concurrent.TimeUnit unit)
            throws java.lang.InterruptedException {
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

    public void put(E e) throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public boolean offer(E e, long timeout, java.util.concurrent.TimeUnit unit)
            throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public E remove() {
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

    public E element() {
        throw new RuntimeException("Stub!");
    }

    public E peek() {
        throw new RuntimeException("Stub!");
    }

    public int remainingCapacity() {
        throw new RuntimeException("Stub!");
    }

    public int drainTo(java.util.Collection<? super E> c) {
        throw new RuntimeException("Stub!");
    }

    public int drainTo(java.util.Collection<? super E> c, int maxElements) {
        throw new RuntimeException("Stub!");
    }

    public void push(E e) {
        throw new RuntimeException("Stub!");
    }

    public E pop() {
        throw new RuntimeException("Stub!");
    }

    public boolean remove(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public int size() {
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

    public void clear() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Iterator<E> iterator() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Iterator<E> descendingIterator() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Spliterator<E> spliterator() {
        throw new RuntimeException("Stub!");
    }

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private final int capacity;

    {
        capacity = 0;
    }

    private transient int count;

    @UnsupportedAppUsage
    transient Node<E> first;

    transient Node<E> last;

    @UnsupportedAppUsage
    final java.util.concurrent.locks.ReentrantLock lock;

    {
        lock = null;
    }

    private final java.util.concurrent.locks.Condition notEmpty;

    {
        notEmpty = null;
    }

    private final java.util.concurrent.locks.Condition notFull;

    {
        notFull = null;
    }

    private static final long serialVersionUID = -387911632671998426L; // 0xfa9ddc6ce257fe26L

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private abstract class AbstractItr implements java.util.Iterator<E> {

        AbstractItr() {
            throw new RuntimeException("Stub!");
        }

        abstract Node<E> firstNode();

        abstract Node<E> nextNode(
                Node<E> n);

        private Node<E> succ(
                Node<E> n) {
            throw new RuntimeException("Stub!");
        }

        void advance() {
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

        private Node<E> lastRet;

        Node<E> next;

        E nextItem;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class DescendingItr extends AbstractItr {

        DescendingItr() {
            throw new RuntimeException("Stub!");
        }

        Node<E> firstNode() {
            throw new RuntimeException("Stub!");
        }

        Node<E> nextNode(
                Node<E> n) {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class Itr extends AbstractItr {

        Itr() {
            throw new RuntimeException("Stub!");
        }

        Node<E> firstNode() {
            throw new RuntimeException("Stub!");
        }

        Node<E> nextNode(
                Node<E> n) {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class LBDSpliterator<E> implements java.util.Spliterator<E> {

        LBDSpliterator(java.util.concurrent.LinkedBlockingDeque<E> queue) {
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

        static final int MAX_BATCH = 33554432; // 0x2000000

        int batch;

        Node<E> current;

        long est;

        boolean exhausted;

        final java.util.concurrent.LinkedBlockingDeque<E> queue;

        {
            queue = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class Node<E> {

        Node(E x) {
            throw new RuntimeException("Stub!");
        }

        E item;

        Node<E> next;

        Node<E> prev;
    }
}
