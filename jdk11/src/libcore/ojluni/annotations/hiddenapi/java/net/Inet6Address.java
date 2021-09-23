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
public final class Inet6Address extends java.net.InetAddress {

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    Inet6Address() {
        throw new RuntimeException("Stub!");
    }

    Inet6Address(java.lang.String hostName, byte[] addr, int scope_id) {
        throw new RuntimeException("Stub!");
    }

    Inet6Address(java.lang.String hostName, byte[] addr) {
        throw new RuntimeException("Stub!");
    }

    Inet6Address(java.lang.String hostName, byte[] addr, java.net.NetworkInterface nif)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    Inet6Address(java.lang.String hostName, byte[] addr, java.lang.String ifname)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    public static java.net.Inet6Address getByAddress(
            java.lang.String host, byte[] addr, java.net.NetworkInterface nif)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    public static java.net.Inet6Address getByAddress(
            java.lang.String host, byte[] addr, int scope_id) throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    private void initstr(java.lang.String hostName, byte[] addr, java.lang.String ifname)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    private void initif(java.lang.String hostName, byte[] addr, java.net.NetworkInterface nif)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    private static boolean isDifferentLocalAddressType(byte[] thisAddr, byte[] otherAddr) {
        throw new RuntimeException("Stub!");
    }

    private static int deriveNumericScope(byte[] thisAddr, java.net.NetworkInterface ifc)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    private int deriveNumericScope(java.lang.String ifname) throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private synchronized void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean isMulticastAddress() {
        throw new RuntimeException("Stub!");
    }

    public boolean isAnyLocalAddress() {
        throw new RuntimeException("Stub!");
    }

    public boolean isLoopbackAddress() {
        throw new RuntimeException("Stub!");
    }

    public boolean isLinkLocalAddress() {
        throw new RuntimeException("Stub!");
    }

    static boolean isLinkLocalAddress(byte[] ipaddress) {
        throw new RuntimeException("Stub!");
    }

    public boolean isSiteLocalAddress() {
        throw new RuntimeException("Stub!");
    }

    static boolean isSiteLocalAddress(byte[] ipaddress) {
        throw new RuntimeException("Stub!");
    }

    public boolean isMCGlobal() {
        throw new RuntimeException("Stub!");
    }

    public boolean isMCNodeLocal() {
        throw new RuntimeException("Stub!");
    }

    public boolean isMCLinkLocal() {
        throw new RuntimeException("Stub!");
    }

    public boolean isMCSiteLocal() {
        throw new RuntimeException("Stub!");
    }

    public boolean isMCOrgLocal() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getAddress() {
        throw new RuntimeException("Stub!");
    }

    public int getScopeId() {
        throw new RuntimeException("Stub!");
    }

    public java.net.NetworkInterface getScopedInterface() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getHostAddress() {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public boolean isIPv4CompatibleAddress() {
        throw new RuntimeException("Stub!");
    }

    static java.lang.String numericToTextFormat(byte[] src) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static final java.net.InetAddress ANY;

    static {
        ANY = null;
    }

    private static final long FIELDS_OFFSET;

    static {
        FIELDS_OFFSET = 0;
    }

    static final int INADDRSZ = 16; // 0x10

    private static final int INT16SZ = 2; // 0x2

    public static final java.net.InetAddress LOOPBACK;

    static {
        LOOPBACK = null;
    }

    private static final sun.misc.Unsafe UNSAFE;

    static {
        UNSAFE = null;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private final transient java.net.Inet6Address.Inet6AddressHolder holder6;

    {
        holder6 = null;
    }

    private static final java.io.ObjectStreamField[] serialPersistentFields;

    static {
        serialPersistentFields = new java.io.ObjectStreamField[0];
    }

    private static final long serialVersionUID = 6880410070516793377L; // 0x5f7c2081522c8021L

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class Inet6AddressHolder {

        private Inet6AddressHolder() {
            throw new RuntimeException("Stub!");
        }

        private Inet6AddressHolder(
                byte[] ipaddress,
                int scope_id,
                boolean scope_id_set,
                java.net.NetworkInterface ifname,
                boolean scope_ifname_set) {
            throw new RuntimeException("Stub!");
        }

        void setAddr(byte[] addr) {
            throw new RuntimeException("Stub!");
        }

        void init(byte[] addr, int scope_id) {
            throw new RuntimeException("Stub!");
        }

        void init(byte[] addr, java.net.NetworkInterface nif) throws java.net.UnknownHostException {
            throw new RuntimeException("Stub!");
        }

        public boolean equals(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        boolean isIPv4CompatibleAddress() {
            throw new RuntimeException("Stub!");
        }

        boolean isMulticastAddress() {
            throw new RuntimeException("Stub!");
        }

        boolean isAnyLocalAddress() {
            throw new RuntimeException("Stub!");
        }

        boolean isLoopbackAddress() {
            throw new RuntimeException("Stub!");
        }

        boolean isLinkLocalAddress() {
            throw new RuntimeException("Stub!");
        }

        boolean isSiteLocalAddress() {
            throw new RuntimeException("Stub!");
        }

        boolean isMCGlobal() {
            throw new RuntimeException("Stub!");
        }

        boolean isMCNodeLocal() {
            throw new RuntimeException("Stub!");
        }

        boolean isMCLinkLocal() {
            throw new RuntimeException("Stub!");
        }

        boolean isMCSiteLocal() {
            throw new RuntimeException("Stub!");
        }

        boolean isMCOrgLocal() {
            throw new RuntimeException("Stub!");
        }

        @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        byte[] ipaddress;

        @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        int scope_id;

        @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        boolean scope_id_set;

        @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        java.net.NetworkInterface scope_ifname;

        boolean scope_ifname_set;
    }
}
