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
public class LinkedHashMap<K, V> extends java.util.HashMap<K, V> implements java.util.Map<K, V> {

    public LinkedHashMap(int initialCapacity, float loadFactor) {
        throw new RuntimeException("Stub!");
    }

    public LinkedHashMap(int initialCapacity) {
        throw new RuntimeException("Stub!");
    }

    public LinkedHashMap() {
        throw new RuntimeException("Stub!");
    }

    public LinkedHashMap(java.util.Map<? extends K, ? extends V> m) {
        throw new RuntimeException("Stub!");
    }

    public LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        throw new RuntimeException("Stub!");
    }

    private void linkNodeLast(java.util.LinkedHashMap.LinkedHashMapEntry<K, V> p) {
        throw new RuntimeException("Stub!");
    }

    private void transferLinks(
            java.util.LinkedHashMap.LinkedHashMapEntry<K, V> src,
            java.util.LinkedHashMap.LinkedHashMapEntry<K, V> dst) {
        throw new RuntimeException("Stub!");
    }

    void reinitialize() {
        throw new RuntimeException("Stub!");
    }

    java.util.HashMap.Node<K, V> newNode(int hash, K key, V value, java.util.HashMap.Node<K, V> e) {
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

    void afterNodeRemoval(java.util.HashMap.Node<K, V> e) {
        throw new RuntimeException("Stub!");
    }

    void afterNodeInsertion(boolean evict) {
        throw new RuntimeException("Stub!");
    }

    void afterNodeAccess(java.util.HashMap.Node<K, V> e) {
        throw new RuntimeException("Stub!");
    }

    void internalWriteEntries(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean containsValue(java.lang.Object value) {
        throw new RuntimeException("Stub!");
    }

    public V get(java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    public V getOrDefault(java.lang.Object key, V defaultValue) {
        throw new RuntimeException("Stub!");
    }

    public void clear() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public java.util.Map.Entry<K, V> eldest() {
        throw new RuntimeException("Stub!");
    }

    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
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

    public void forEach(java.util.function.BiConsumer<? super K, ? super V> action) {
        throw new RuntimeException("Stub!");
    }

    public void replaceAll(
            java.util.function.BiFunction<? super K, ? super V, ? extends V> function) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    final boolean accessOrder;

    {
        accessOrder = false;
    }

    transient java.util.LinkedHashMap.LinkedHashMapEntry<K, V> head;

    private static final long serialVersionUID = 3801124242820219131L; // 0x34c04e5c106cc0fbL

    transient java.util.LinkedHashMap.LinkedHashMapEntry<K, V> tail;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class LinkedEntryIterator extends java.util.LinkedHashMap.LinkedHashIterator
            implements java.util.Iterator<java.util.Map.Entry<K, V>> {

        public java.util.Map.Entry<K, V> next() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class LinkedEntrySet extends java.util.AbstractSet<java.util.Map.Entry<K, V>> {

        LinkedEntrySet() {
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
    abstract class LinkedHashIterator {

        LinkedHashIterator() {
            throw new RuntimeException("Stub!");
        }

        public final boolean hasNext() {
            throw new RuntimeException("Stub!");
        }

        final java.util.LinkedHashMap.LinkedHashMapEntry<K, V> nextNode() {
            throw new RuntimeException("Stub!");
        }

        public final void remove() {
            throw new RuntimeException("Stub!");
        }

        java.util.LinkedHashMap.LinkedHashMapEntry<K, V> current;

        int expectedModCount;

        java.util.LinkedHashMap.LinkedHashMapEntry<K, V> next;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class LinkedHashMapEntry<K, V> extends java.util.HashMap.Node<K, V> {

        LinkedHashMapEntry(int hash, K key, V value, java.util.HashMap.Node<K, V> next) {
            super(0, null, null, null);
            throw new RuntimeException("Stub!");
        }

        java.util.LinkedHashMap.LinkedHashMapEntry<K, V> after;

        java.util.LinkedHashMap.LinkedHashMapEntry<K, V> before;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class LinkedKeyIterator extends java.util.LinkedHashMap.LinkedHashIterator
            implements java.util.Iterator<K> {

        public K next() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class LinkedKeySet extends java.util.AbstractSet<K> {

        LinkedKeySet() {
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
    final class LinkedValueIterator extends java.util.LinkedHashMap.LinkedHashIterator
            implements java.util.Iterator<V> {

        public V next() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class LinkedValues extends java.util.AbstractCollection<V> {

        LinkedValues() {
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
