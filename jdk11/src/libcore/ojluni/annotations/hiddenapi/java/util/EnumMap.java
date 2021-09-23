/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2003, 2012, Oracle and/or its affiliates. All rights reserved.
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
public class EnumMap<K extends java.lang.Enum<K>, V> extends java.util.AbstractMap<K, V>
        implements java.io.Serializable, java.lang.Cloneable {

    public EnumMap(java.lang.Class<K> keyType) {
        throw new RuntimeException("Stub!");
    }

    public EnumMap(java.util.EnumMap<K, ? extends V> m) {
        throw new RuntimeException("Stub!");
    }

    public EnumMap(java.util.Map<K, ? extends V> m) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Object maskNull(java.lang.Object value) {
        throw new RuntimeException("Stub!");
    }

    private V unmaskNull(java.lang.Object value) {
        throw new RuntimeException("Stub!");
    }

    public int size() {
        throw new RuntimeException("Stub!");
    }

    public boolean containsValue(java.lang.Object value) {
        throw new RuntimeException("Stub!");
    }

    public boolean containsKey(java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    private boolean containsMapping(java.lang.Object key, java.lang.Object value) {
        throw new RuntimeException("Stub!");
    }

    public V get(java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    public V put(K key, V value) {
        throw new RuntimeException("Stub!");
    }

    public V remove(java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    private boolean removeMapping(java.lang.Object key, java.lang.Object value) {
        throw new RuntimeException("Stub!");
    }

    private boolean isValidKey(java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    public void putAll(java.util.Map<? extends K, ? extends V> m) {
        throw new RuntimeException("Stub!");
    }

    public void clear() {
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

    public boolean equals(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    private boolean equals(java.util.EnumMap<?, ?> em) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    private int entryHashCode(int index) {
        throw new RuntimeException("Stub!");
    }

    public java.util.EnumMap<K, V> clone() {
        throw new RuntimeException("Stub!");
    }

    private void typeCheck(K key) {
        throw new RuntimeException("Stub!");
    }

    private static <K extends java.lang.Enum<K>> K[] getKeyUniverse(java.lang.Class<K> keyType) {
        throw new RuntimeException("Stub!");
    }

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static final java.lang.Object NULL;

    static {
        NULL = null;
    }

    private static final java.lang.Enum<?>[] ZERO_LENGTH_ENUM_ARRAY;

    static {
        ZERO_LENGTH_ENUM_ARRAY = new java.lang.Enum[0];
    }

    private transient java.util.Set<java.util.Map.Entry<K, V>> entrySet;

    @UnsupportedAppUsage
    private final java.lang.Class<K> keyType;

    {
        keyType = null;
    }

    private transient K[] keyUniverse;

    private static final long serialVersionUID = 458661240069192865L; // 0x65d7df7be907ca1L

    private transient int size = 0; // 0x0

    private transient java.lang.Object[] vals;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class EntryIterator
            extends EnumMapIterator<java.util.Map.Entry<K, V>> {

        private EntryIterator() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Map.Entry<K, V> next() {
            throw new RuntimeException("Stub!");
        }

        public void remove() {
            throw new RuntimeException("Stub!");
        }

        private java.util.EnumMap.EntryIterator.Entry lastReturnedEntry;

        @SuppressWarnings({"unchecked", "deprecation", "all"})
        private class Entry implements java.util.Map.Entry<K, V> {

            private Entry(int index) {
                throw new RuntimeException("Stub!");
            }

            public K getKey() {
                throw new RuntimeException("Stub!");
            }

            public V getValue() {
                throw new RuntimeException("Stub!");
            }

            public V setValue(V value) {
                throw new RuntimeException("Stub!");
            }

            public boolean equals(java.lang.Object o) {
                throw new RuntimeException("Stub!");
            }

            public int hashCode() {
                throw new RuntimeException("Stub!");
            }

            public java.lang.String toString() {
                throw new RuntimeException("Stub!");
            }

            private void checkIndexForEntryUse() {
                throw new RuntimeException("Stub!");
            }

            private int index;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class EntrySet extends java.util.AbstractSet<java.util.Map.Entry<K, V>> {

        private EntrySet() {
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

        public int size() {
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

        private java.lang.Object[] fillEntryArray(java.lang.Object[] a) {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private abstract class EnumMapIterator<T> implements java.util.Iterator<T> {

        private EnumMapIterator() {
            throw new RuntimeException("Stub!");
        }

        public boolean hasNext() {
            throw new RuntimeException("Stub!");
        }

        public void remove() {
            throw new RuntimeException("Stub!");
        }

        private void checkLastReturnedIndex() {
            throw new RuntimeException("Stub!");
        }

        int index = 0; // 0x0

        int lastReturnedIndex = -1; // 0xffffffff
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class KeyIterator extends EnumMapIterator<K> {

        private KeyIterator() {
            throw new RuntimeException("Stub!");
        }

        public K next() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class KeySet extends java.util.AbstractSet<K> {

        private KeySet() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<K> iterator() {
            throw new RuntimeException("Stub!");
        }

        public int size() {
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
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class ValueIterator extends EnumMapIterator<V> {

        private ValueIterator() {
            throw new RuntimeException("Stub!");
        }

        public V next() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class Values extends java.util.AbstractCollection<V> {

        private Values() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<V> iterator() {
            throw new RuntimeException("Stub!");
        }

        public int size() {
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
    }
}
