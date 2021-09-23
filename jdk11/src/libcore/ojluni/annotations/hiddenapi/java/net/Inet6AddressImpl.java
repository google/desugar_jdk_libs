/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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

@SuppressWarnings({"unchecked", "deprecation", "all"})
class Inet6AddressImpl implements java.net.InetAddressImpl {

    Inet6AddressImpl() {
        throw new RuntimeException("Stub!");
    }

    public java.net.InetAddress[] lookupAllHostAddr(java.lang.String host, int netId)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    private static java.net.InetAddress[] lookupHostByName(java.lang.String host, int netId)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getHostByAddr(byte[] addr) throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    public void clearAddressCache() {
        throw new RuntimeException("Stub!");
    }

    public boolean isReachable(
            java.net.InetAddress addr, int timeout, java.net.NetworkInterface netif, int ttl)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private boolean tcpEcho(
            java.net.InetAddress addr, int timeout, java.net.InetAddress sourceAddr, int ttl)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected boolean icmpEcho(
            java.net.InetAddress addr, int timeout, java.net.InetAddress sourceAddr, int ttl)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.net.InetAddress anyLocalAddress() {
        throw new RuntimeException("Stub!");
    }

    public java.net.InetAddress[] loopbackAddresses() {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String getHostByAddr0(byte[] addr) throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    private static final java.net.AddressCache addressCache;

    static {
        addressCache = null;
    }

    private static java.net.InetAddress anyLocalAddress;

    private static java.net.InetAddress[] loopbackAddresses;
}
