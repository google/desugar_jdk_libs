/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class InetSocketAddress extends java.net.SocketAddress {

    public InetSocketAddress() {
        throw new RuntimeException("Stub!");
    }

    public InetSocketAddress(int port) {
        throw new RuntimeException("Stub!");
    }

    public InetSocketAddress(java.net.InetAddress addr, int port) {
        throw new RuntimeException("Stub!");
    }

    public InetSocketAddress(java.lang.String hostname, int port) {
        throw new RuntimeException("Stub!");
    }

    private InetSocketAddress(int port, java.lang.String hostname) {
        throw new RuntimeException("Stub!");
    }

    private static int checkPort(int port) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String checkHost(java.lang.String hostname) {
        throw new RuntimeException("Stub!");
    }

    public static java.net.InetSocketAddress createUnresolved(java.lang.String host, int port) {
        throw new RuntimeException("Stub!");
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream in)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObjectNoData() throws java.io.ObjectStreamException {
        throw new RuntimeException("Stub!");
    }

    public final int getPort() {
        throw new RuntimeException("Stub!");
    }

    public final java.net.InetAddress getAddress() {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.String getHostName() {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.String getHostString() {
        throw new RuntimeException("Stub!");
    }

    public final boolean isUnresolved() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public final boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public final int hashCode() {
        throw new RuntimeException("Stub!");
    }

    private static final long FIELDS_OFFSET;

    static {
        FIELDS_OFFSET = 0;
    }

    private static final sun.misc.Unsafe UNSAFE;

    static {
        UNSAFE = null;
    }

    @UnsupportedAppUsage
    private final transient java.net.InetSocketAddress.InetSocketAddressHolder holder;

    {
        holder = null;
    }

    private static final java.io.ObjectStreamField[] serialPersistentFields;

    static {
        serialPersistentFields = new java.io.ObjectStreamField[0];
    }

    private static final long serialVersionUID = 5076001401234631237L; // 0x467194616ff9aa45L

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class InetSocketAddressHolder {

        private InetSocketAddressHolder(
                java.lang.String hostname, java.net.InetAddress addr, int port) {
            throw new RuntimeException("Stub!");
        }

        private int getPort() {
            throw new RuntimeException("Stub!");
        }

        private java.net.InetAddress getAddress() {
            throw new RuntimeException("Stub!");
        }

        private java.lang.String getHostName() {
            throw new RuntimeException("Stub!");
        }

        private java.lang.String getHostString() {
            throw new RuntimeException("Stub!");
        }

        private boolean isUnresolved() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        public final boolean equals(java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        public final int hashCode() {
            throw new RuntimeException("Stub!");
        }

        private java.net.InetAddress addr;

        private java.lang.String hostname;

        private int port;
    }
}
