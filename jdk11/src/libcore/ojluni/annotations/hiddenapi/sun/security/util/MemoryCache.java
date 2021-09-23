/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sun.security.util;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
class MemoryCache<K, V> extends sun.security.util.Cache<K, V> {

    public MemoryCache(boolean soft, int maxSize) {
        throw new RuntimeException("Stub!");
    }

    public MemoryCache(boolean soft, int maxSize, int lifetime) {
        throw new RuntimeException("Stub!");
    }

    private void emptyQueue() {
        throw new RuntimeException("Stub!");
    }

    private void expungeExpiredEntries() {
        throw new RuntimeException("Stub!");
    }

    public synchronized int size() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void clear() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void put(K key, V value) {
        throw new RuntimeException("Stub!");
    }

    public synchronized V get(java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void remove(java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setCapacity(int size) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setTimeout(int timeout) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void accept(sun.security.util.Cache.CacheVisitor<K, V> visitor) {
        throw new RuntimeException("Stub!");
    }

    private java.util.Map<K, V> getCachedEntries() {
        throw new RuntimeException("Stub!");
    }

    protected sun.security.util.MemoryCache.CacheEntry<K, V> newEntry(
            K key, V value, long expirationTime, java.lang.ref.ReferenceQueue<V> queue) {
        throw new RuntimeException("Stub!");
    }

    private static final boolean DEBUG = false;

    private static final float LOAD_FACTOR = 0.75f;

    private final java.util.Map<K, sun.security.util.MemoryCache.CacheEntry<K, V>> cacheMap;

    {
        cacheMap = null;
    }

    private long lifetime;

    private int maxSize;

    private final java.lang.ref.ReferenceQueue<V> queue;

    {
        queue = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static interface CacheEntry<K, V> {

        public boolean isValid(long currentTime);

        public void invalidate();

        public K getKey();

        public V getValue();
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class HardCacheEntry<K, V>
            implements sun.security.util.MemoryCache.CacheEntry<K, V> {

        @UnsupportedAppUsage
        HardCacheEntry(K key, V value, long expirationTime) {
            throw new RuntimeException("Stub!");
        }

        public K getKey() {
            throw new RuntimeException("Stub!");
        }

        public V getValue() {
            throw new RuntimeException("Stub!");
        }

        public boolean isValid(long currentTime) {
            throw new RuntimeException("Stub!");
        }

        public void invalidate() {
            throw new RuntimeException("Stub!");
        }

        private long expirationTime;

        private K key;

        private V value;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class SoftCacheEntry<K, V> extends java.lang.ref.SoftReference<V>
            implements sun.security.util.MemoryCache.CacheEntry<K, V> {

        @UnsupportedAppUsage
        SoftCacheEntry(K key, V value, long expirationTime, java.lang.ref.ReferenceQueue<V> queue) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        public K getKey() {
            throw new RuntimeException("Stub!");
        }

        public V getValue() {
            throw new RuntimeException("Stub!");
        }

        public boolean isValid(long currentTime) {
            throw new RuntimeException("Stub!");
        }

        public void invalidate() {
            throw new RuntimeException("Stub!");
        }

        private long expirationTime;

        private K key;
    }
}
