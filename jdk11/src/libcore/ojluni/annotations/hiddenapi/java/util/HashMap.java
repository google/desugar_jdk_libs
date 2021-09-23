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
public class HashMap<K, V> extends java.util.AbstractMap<K, V>
        implements java.util.Map<K, V>, java.lang.Cloneable, java.io.Serializable {

    public HashMap(int initialCapacity, float loadFactor) {
        throw new RuntimeException("Stub!");
    }

    public HashMap(int initialCapacity) {
        throw new RuntimeException("Stub!");
    }

    public HashMap() {
        throw new RuntimeException("Stub!");
    }

    public HashMap(java.util.Map<? extends K, ? extends V> m) {
        throw new RuntimeException("Stub!");
    }

    static final int hash(java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    static java.lang.Class<?> comparableClassFor(java.lang.Object x) {
        throw new RuntimeException("Stub!");
    }

    static int compareComparables(java.lang.Class<?> kc, java.lang.Object k, java.lang.Object x) {
        throw new RuntimeException("Stub!");
    }

    static final int tableSizeFor(int cap) {
        throw new RuntimeException("Stub!");
    }

    final void putMapEntries(java.util.Map<? extends K, ? extends V> m, boolean evict) {
        throw new RuntimeException("Stub!");
    }

    public int size() {
        throw new RuntimeException("Stub!");
    }

    public boolean isEmpty() {
        throw new RuntimeException("Stub!");
    }

    public V get(java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    final java.util.HashMap.Node<K, V> getNode(int hash, java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    public boolean containsKey(java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    public V put(K key, V value) {
        throw new RuntimeException("Stub!");
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        throw new RuntimeException("Stub!");
    }

    final java.util.HashMap.Node<K, V>[] resize() {
        throw new RuntimeException("Stub!");
    }

    final void treeifyBin(java.util.HashMap.Node<K, V>[] tab, int hash) {
        throw new RuntimeException("Stub!");
    }

    public void putAll(java.util.Map<? extends K, ? extends V> m) {
        throw new RuntimeException("Stub!");
    }

    public V remove(java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    final java.util.HashMap.Node<K, V> removeNode(
            int hash,
            java.lang.Object key,
            java.lang.Object value,
            boolean matchValue,
            boolean movable) {
        throw new RuntimeException("Stub!");
    }

    public void clear() {
        throw new RuntimeException("Stub!");
    }

    public boolean containsValue(java.lang.Object value) {
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

    public V getOrDefault(java.lang.Object key, V defaultValue) {
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
            java.util.function.BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new RuntimeException("Stub!");
    }

    public V compute(
            K key,
            java.util.function.BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new RuntimeException("Stub!");
    }

    public V merge(
            K key,
            V value,
            java.util.function.BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        throw new RuntimeException("Stub!");
    }

    public void forEach(java.util.function.BiConsumer<? super K, ? super V> action) {
        throw new RuntimeException("Stub!");
    }

    public void replaceAll(
            java.util.function.BiFunction<? super K, ? super V, ? extends V> function) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object clone() {
        throw new RuntimeException("Stub!");
    }

    final float loadFactor() {
        throw new RuntimeException("Stub!");
    }

    final int capacity() {
        throw new RuntimeException("Stub!");
    }

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    java.util.HashMap.Node<K, V> newNode(
            int hash, K key, V value, java.util.HashMap.Node<K, V> next) {
        throw new RuntimeException("Stub!");
    }

    java.util.HashMap.Node<K, V> replacementNode(
            java.util.HashMap.Node<K, V> p, java.util.HashMap.Node<K, V> next) {
        throw new RuntimeException("Stub!");
    }

    java.util.HashMap.TreeNode<K, V> newTreeNode(
            int hash, K key, V value, java.util.HashMap.Node<K, V> next) {
        throw new RuntimeException("Stub!");
    }

    java.util.HashMap.TreeNode<K, V> replacementTreeNode(
            java.util.HashMap.Node<K, V> p, java.util.HashMap.Node<K, V> next) {
        throw new RuntimeException("Stub!");
    }

    void reinitialize() {
        throw new RuntimeException("Stub!");
    }

    void afterNodeAccess(java.util.HashMap.Node<K, V> p) {
        throw new RuntimeException("Stub!");
    }

    void afterNodeInsertion(boolean evict) {
        throw new RuntimeException("Stub!");
    }

    void afterNodeRemoval(java.util.HashMap.Node<K, V> p) {
        throw new RuntimeException("Stub!");
    }

    void internalWriteEntries(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    static final int DEFAULT_INITIAL_CAPACITY = 16; // 0x10

    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    static final int MAXIMUM_CAPACITY = 1073741824; // 0x40000000

    static final int MIN_TREEIFY_CAPACITY = 64; // 0x40

    static final int TREEIFY_THRESHOLD = 8; // 0x8

    static final int UNTREEIFY_THRESHOLD = 6; // 0x6

    transient java.util.Set<java.util.Map.Entry<K, V>> entrySet;

    final float loadFactor;

    {
        loadFactor = 0;
    }

    @UnsupportedAppUsage
    transient int modCount;

    private static final long serialVersionUID = 362498820763181265L; // 0x507dac1c31660d1L

    transient int size;

    @UnsupportedAppUsage
    transient java.util.HashMap.Node<K, V>[] table;

    int threshold;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class EntryIterator extends java.util.HashMap.HashIterator
            implements java.util.Iterator<java.util.Map.Entry<K, V>> {

        @UnsupportedAppUsage(trackingBug = 122551864)
        public java.util.Map.Entry<K, V> next() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class EntrySet extends java.util.AbstractSet<java.util.Map.Entry<K, V>> {

        EntrySet() {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<java.util.Map.Entry<K, V>> iterator() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<java.util.Map.Entry<K, V>> spliterator() {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super java.util.Map.Entry<K, V>> action) {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class EntrySpliterator<K, V> extends java.util.HashMap.HashMapSpliterator<K, V>
            implements java.util.Spliterator<java.util.Map.Entry<K, V>> {

        EntrySpliterator(
                java.util.HashMap<K, V> m, int origin, int fence, int est, int expectedModCount) {
            super(null, 0, 0, 0, 0);
            throw new RuntimeException("Stub!");
        }

        public java.util.HashMap.EntrySpliterator<K, V> trySplit() {
            throw new RuntimeException("Stub!");
        }

        public void forEachRemaining(
                java.util.function.Consumer<? super java.util.Map.Entry<K, V>> action) {
            throw new RuntimeException("Stub!");
        }

        public boolean tryAdvance(
                java.util.function.Consumer<? super java.util.Map.Entry<K, V>> action) {
            throw new RuntimeException("Stub!");
        }

        public int characteristics() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    abstract class HashIterator {

        HashIterator() {
            throw new RuntimeException("Stub!");
        }

        public final boolean hasNext() {
            throw new RuntimeException("Stub!");
        }

        final java.util.HashMap.Node<K, V> nextNode() {
            throw new RuntimeException("Stub!");
        }

        public final void remove() {
            throw new RuntimeException("Stub!");
        }

        java.util.HashMap.Node<K, V> current;

        int expectedModCount;

        int index;

        java.util.HashMap.Node<K, V> next;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class HashMapSpliterator<K, V> {

        HashMapSpliterator(
                java.util.HashMap<K, V> m, int origin, int fence, int est, int expectedModCount) {
            throw new RuntimeException("Stub!");
        }

        final int getFence() {
            throw new RuntimeException("Stub!");
        }

        public final long estimateSize() {
            throw new RuntimeException("Stub!");
        }

        java.util.HashMap.Node<K, V> current;

        int est;

        int expectedModCount;

        int fence;

        int index;

        final java.util.HashMap<K, V> map;

        {
            map = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class KeyIterator extends java.util.HashMap.HashIterator
            implements java.util.Iterator<K> {

        public K next() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class KeySet extends java.util.AbstractSet<K> {

        KeySet() {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<K> iterator() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public boolean remove(java.lang.Object key) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<K> spliterator() {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super K> action) {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class KeySpliterator<K, V> extends java.util.HashMap.HashMapSpliterator<K, V>
            implements java.util.Spliterator<K> {

        KeySpliterator(
                java.util.HashMap<K, V> m, int origin, int fence, int est, int expectedModCount) {
            super(null, 0, 0, 0, 0);
            throw new RuntimeException("Stub!");
        }

        public java.util.HashMap.KeySpliterator<K, V> trySplit() {
            throw new RuntimeException("Stub!");
        }

        public void forEachRemaining(java.util.function.Consumer<? super K> action) {
            throw new RuntimeException("Stub!");
        }

        public boolean tryAdvance(java.util.function.Consumer<? super K> action) {
            throw new RuntimeException("Stub!");
        }

        public int characteristics() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class Node<K, V> implements java.util.Map.Entry<K, V> {

        Node(int hash, K key, V value, java.util.HashMap.Node<K, V> next) {
            throw new RuntimeException("Stub!");
        }

        public final K getKey() {
            throw new RuntimeException("Stub!");
        }

        public final V getValue() {
            throw new RuntimeException("Stub!");
        }

        public final java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        public final int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public final V setValue(V newValue) {
            throw new RuntimeException("Stub!");
        }

        public final boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        final int hash;

        {
            hash = 0;
        }

        @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        final K key;

        {
            key = null;
        }

        @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        java.util.HashMap.Node<K, V> next;

        @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        V value;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class TreeNode<K, V> extends java.util.LinkedHashMap.LinkedHashMapEntry<K, V> {

        TreeNode(int hash, K key, V val, java.util.HashMap.Node<K, V> next) {
            super(0, null, null, null);
            throw new RuntimeException("Stub!");
        }

        java.util.HashMap.TreeNode<K, V> root() {
            throw new RuntimeException("Stub!");
        }

        static <K, V> void moveRootToFront(
                java.util.HashMap.Node<K, V>[] tab, java.util.HashMap.TreeNode<K, V> root) {
            throw new RuntimeException("Stub!");
        }

        java.util.HashMap.TreeNode<K, V> find(int h, java.lang.Object k, java.lang.Class<?> kc) {
            throw new RuntimeException("Stub!");
        }

        java.util.HashMap.TreeNode<K, V> getTreeNode(int h, java.lang.Object k) {
            throw new RuntimeException("Stub!");
        }

        static int tieBreakOrder(java.lang.Object a, java.lang.Object b) {
            throw new RuntimeException("Stub!");
        }

        void treeify(java.util.HashMap.Node<K, V>[] tab) {
            throw new RuntimeException("Stub!");
        }

        java.util.HashMap.Node<K, V> untreeify(java.util.HashMap<K, V> map) {
            throw new RuntimeException("Stub!");
        }

        java.util.HashMap.TreeNode<K, V> putTreeVal(
                java.util.HashMap<K, V> map, java.util.HashMap.Node<K, V>[] tab, int h, K k, V v) {
            throw new RuntimeException("Stub!");
        }

        void removeTreeNode(
                java.util.HashMap<K, V> map, java.util.HashMap.Node<K, V>[] tab, boolean movable) {
            throw new RuntimeException("Stub!");
        }

        void split(
                java.util.HashMap<K, V> map,
                java.util.HashMap.Node<K, V>[] tab,
                int index,
                int bit) {
            throw new RuntimeException("Stub!");
        }

        static <K, V> java.util.HashMap.TreeNode<K, V> rotateLeft(
                java.util.HashMap.TreeNode<K, V> root, java.util.HashMap.TreeNode<K, V> p) {
            throw new RuntimeException("Stub!");
        }

        static <K, V> java.util.HashMap.TreeNode<K, V> rotateRight(
                java.util.HashMap.TreeNode<K, V> root, java.util.HashMap.TreeNode<K, V> p) {
            throw new RuntimeException("Stub!");
        }

        static <K, V> java.util.HashMap.TreeNode<K, V> balanceInsertion(
                java.util.HashMap.TreeNode<K, V> root, java.util.HashMap.TreeNode<K, V> x) {
            throw new RuntimeException("Stub!");
        }

        static <K, V> java.util.HashMap.TreeNode<K, V> balanceDeletion(
                java.util.HashMap.TreeNode<K, V> root, java.util.HashMap.TreeNode<K, V> x) {
            throw new RuntimeException("Stub!");
        }

        static <K, V> boolean checkInvariants(java.util.HashMap.TreeNode<K, V> t) {
            throw new RuntimeException("Stub!");
        }

        java.util.HashMap.TreeNode<K, V> left;

        java.util.HashMap.TreeNode<K, V> parent;

        java.util.HashMap.TreeNode<K, V> prev;

        boolean red;

        java.util.HashMap.TreeNode<K, V> right;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class ValueIterator extends java.util.HashMap.HashIterator
            implements java.util.Iterator<V> {

        public V next() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class ValueSpliterator<K, V> extends java.util.HashMap.HashMapSpliterator<K, V>
            implements java.util.Spliterator<V> {

        ValueSpliterator(
                java.util.HashMap<K, V> m, int origin, int fence, int est, int expectedModCount) {
            super(null, 0, 0, 0, 0);
            throw new RuntimeException("Stub!");
        }

        public java.util.HashMap.ValueSpliterator<K, V> trySplit() {
            throw new RuntimeException("Stub!");
        }

        public void forEachRemaining(java.util.function.Consumer<? super V> action) {
            throw new RuntimeException("Stub!");
        }

        public boolean tryAdvance(java.util.function.Consumer<? super V> action) {
            throw new RuntimeException("Stub!");
        }

        public int characteristics() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class Values extends java.util.AbstractCollection<V> {

        Values() {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<V> iterator() {
            throw new RuntimeException("Stub!");
        }

        public boolean contains(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Spliterator<V> spliterator() {
            throw new RuntimeException("Stub!");
        }

        public void forEach(java.util.function.Consumer<? super V> action) {
            throw new RuntimeException("Stub!");
        }
    }
}
