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
public class LinkedBlockingQueue<E> extends java.util.AbstractQueue<E>
        implements java.util.concurrent.BlockingQueue<E>, java.io.Serializable {

    public LinkedBlockingQueue() {
        throw new RuntimeException("Stub!");
    }

    public LinkedBlockingQueue(int capacity) {
        throw new RuntimeException("Stub!");
    }

    public LinkedBlockingQueue(java.util.Collection<? extends E> c) {
        throw new RuntimeException("Stub!");
    }

    private void signalNotEmpty() {
        throw new RuntimeException("Stub!");
    }

    private void signalNotFull() {
        throw new RuntimeException("Stub!");
    }

    private void enqueue(java.util.concurrent.LinkedBlockingQueue.Node<E> node) {
        throw new RuntimeException("Stub!");
    }

    private E dequeue() {
        throw new RuntimeException("Stub!");
    }

    void fullyLock() {
        throw new RuntimeException("Stub!");
    }

    void fullyUnlock() {
        throw new RuntimeException("Stub!");
    }

    public int size() {
        throw new RuntimeException("Stub!");
    }

    public int remainingCapacity() {
        throw new RuntimeException("Stub!");
    }

    public void put(E e) throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public boolean offer(E e, long timeout, java.util.concurrent.TimeUnit unit)
            throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public boolean offer(E e) {
        throw new RuntimeException("Stub!");
    }

    public E take() throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public E poll(long timeout, java.util.concurrent.TimeUnit unit)
            throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public E poll() {
        throw new RuntimeException("Stub!");
    }

    public E peek() {
        throw new RuntimeException("Stub!");
    }

    void unlink(
            java.util.concurrent.LinkedBlockingQueue.Node<E> p,
            java.util.concurrent.LinkedBlockingQueue.Node<E> trail) {
        throw new RuntimeException("Stub!");
    }

    public boolean remove(java.lang.Object o) {
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

    public int drainTo(java.util.Collection<? super E> c) {
        throw new RuntimeException("Stub!");
    }

    public int drainTo(java.util.Collection<? super E> c, int maxElements) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Iterator<E> iterator() {
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

    @UnsupportedAppUsage
    private final int capacity;

    {
        capacity = 0;
    }

    private final java.util.concurrent.atomic.AtomicInteger count;

    {
        count = null;
    }

    @UnsupportedAppUsage
    transient java.util.concurrent.LinkedBlockingQueue.Node<E> head;

    private transient java.util.concurrent.LinkedBlockingQueue.Node<E> last;

    private final java.util.concurrent.locks.Condition notEmpty;

    {
        notEmpty = null;
    }

    private final java.util.concurrent.locks.Condition notFull;

    {
        notFull = null;
    }

    @UnsupportedAppUsage
    private final java.util.concurrent.locks.ReentrantLock putLock;

    {
        putLock = null;
    }

    private static final long serialVersionUID = -6903933977591709194L; // 0xa0304ca040e581f6L

    @UnsupportedAppUsage
    private final java.util.concurrent.locks.ReentrantLock takeLock;

    {
        takeLock = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class Itr implements java.util.Iterator<E> {

        Itr() {
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

        private java.util.concurrent.LinkedBlockingQueue.Node<E> current;

        private E currentElement;

        private java.util.concurrent.LinkedBlockingQueue.Node<E> lastRet;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class LBQSpliterator<E> implements java.util.Spliterator<E> {

        LBQSpliterator(java.util.concurrent.LinkedBlockingQueue<E> queue) {
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

        java.util.concurrent.LinkedBlockingQueue.Node<E> current;

        long est;

        boolean exhausted;

        final java.util.concurrent.LinkedBlockingQueue<E> queue;

        {
            queue = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class Node<E> {

        Node(E x) {
            throw new RuntimeException("Stub!");
        }

        E item;

        java.util.concurrent.LinkedBlockingQueue.Node<E> next;
    }
}
