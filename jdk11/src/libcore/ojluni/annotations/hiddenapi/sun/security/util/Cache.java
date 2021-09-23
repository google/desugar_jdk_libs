/*
 * Copyright (c) 2002, 2011, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.util;

import java.lang.ref.*;
import java.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class Cache<K, V> {

    @android.compat.annotation.UnsupportedAppUsage
    protected Cache() {
        throw new RuntimeException("Stub!");
    }

    public abstract int size();

    @android.compat.annotation.UnsupportedAppUsage
    public abstract void clear();

    @android.compat.annotation.UnsupportedAppUsage
    public abstract void put(K key, V value);

    @android.compat.annotation.UnsupportedAppUsage
    public abstract V get(java.lang.Object key);

    public abstract void remove(java.lang.Object key);

    public abstract void setCapacity(int size);

    public abstract void setTimeout(int timeout);

    public abstract void accept(sun.security.util.Cache.CacheVisitor<K, V> visitor);

    public static <K, V> sun.security.util.Cache<K, V> newSoftMemoryCache(int size) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> sun.security.util.Cache<K, V> newSoftMemoryCache(int size, int timeout) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static <K, V> sun.security.util.Cache<K, V> newHardMemoryCache(int size) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> sun.security.util.Cache<K, V> newNullCache() {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> sun.security.util.Cache<K, V> newHardMemoryCache(int size, int timeout) {
        throw new RuntimeException("Stub!");
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static interface CacheVisitor<K, V> {

        public void visit(java.util.Map<K, V> map);
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static class EqualByteArray {

        public EqualByteArray(byte[] b) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        private final byte[] b;

        {
            b = new byte[0];
        }

        private volatile int hash;
    }
}
