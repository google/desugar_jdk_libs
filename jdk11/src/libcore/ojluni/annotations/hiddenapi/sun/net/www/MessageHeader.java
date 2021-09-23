/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

/*-
 *      news stream opener
 */

package sun.net.www;

import java.io.*;
import java.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class MessageHeader {

    @android.compat.annotation.UnsupportedAppUsage
    public MessageHeader() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public MessageHeader(java.io.InputStream is) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.String getHeaderNamesInList() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void reset() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public synchronized java.lang.String findValue(java.lang.String k) {
        throw new RuntimeException("Stub!");
    }

    public synchronized int getKey(java.lang.String k) {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.String getKey(int n) {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.String getValue(int n) {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.String findNextValue(java.lang.String k, java.lang.String v) {
        throw new RuntimeException("Stub!");
    }

    public boolean filterNTLMResponses(java.lang.String k) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Iterator<java.lang.String> multiValueIterator(java.lang.String k) {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.util.Map<java.lang.String, java.util.List<java.lang.String>>
            getHeaders() {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.util.Map<java.lang.String, java.util.List<java.lang.String>>
            getHeaders(java.lang.String[] excludeList) {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.util.Map<java.lang.String, java.util.List<java.lang.String>>
            filterAndAddHeaders(
                    java.lang.String[] excludeList,
                    java.util.Map<java.lang.String, java.util.List<java.lang.String>> include) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public synchronized void print(java.io.PrintStream p) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public synchronized void add(java.lang.String k, java.lang.String v) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public synchronized void prepend(java.lang.String k, java.lang.String v) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void set(int i, java.lang.String k, java.lang.String v) {
        throw new RuntimeException("Stub!");
    }

    private void grow() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void remove(java.lang.String k) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public synchronized void set(java.lang.String k, java.lang.String v) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setIfNotSet(java.lang.String k, java.lang.String v) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String canonicalID(java.lang.String id) {
        throw new RuntimeException("Stub!");
    }

    public void parseHeader(java.io.InputStream is) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void mergeHeader(java.io.InputStream is) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String[] keys;

    private int nkeys;

    private java.lang.String[] values;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    class HeaderIterator implements java.util.Iterator<java.lang.String> {

        public HeaderIterator(java.lang.String k, java.lang.Object lock) {
            throw new RuntimeException("Stub!");
        }

        public boolean hasNext() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String next() {
            throw new RuntimeException("Stub!");
        }

        public void remove() {
            throw new RuntimeException("Stub!");
        }

        boolean haveNext = false;

        int index = 0; // 0x0

        java.lang.String key;

        java.lang.Object lock;

        int next = -1; // 0xffffffff
    }
}
