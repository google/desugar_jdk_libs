/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1995, 2015, Oracle and/or its affiliates. All rights reserved.
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
import dalvik.annotation.compat.VersionCodes;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class InetAddress implements java.io.Serializable {

    InetAddress() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    java.net.InetAddress.InetAddressHolder holder() {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Object readResolve() throws java.io.ObjectStreamException {
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

    public boolean isSiteLocalAddress() {
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

    public boolean isReachable(int timeout) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean isReachable(java.net.NetworkInterface netif, int ttl, int timeout)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean isReachableByICMP(int timeout) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getHostName() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getCanonicalHostName() {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String getHostFromNameService(java.net.InetAddress addr) {
        throw new RuntimeException("Stub!");
    }

    public byte[] getAddress() {
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

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public static java.net.InetAddress getByAddress(java.lang.String host, byte[] addr)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    private static java.net.InetAddress getByAddress(
            java.lang.String host, byte[] addr, int scopeId) throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    public static java.net.InetAddress getByName(java.lang.String host)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    public static java.net.InetAddress[] getAllByName(java.lang.String host)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    public static java.net.InetAddress getLoopbackAddress() {
        throw new RuntimeException("Stub!");
    }

    public static java.net.InetAddress getByAddress(byte[] addr)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    public static java.net.InetAddress getLocalHost() throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    static java.net.InetAddress anyLocalAddress() {
        throw new RuntimeException("Stub!");
    }

    private void readObjectNoData(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = VersionCodes.P, trackingBug = 78686891,
        publicAlternatives = "Use {@code android.net.InetAddresses#isNumericAddress} "
        + "instead. There is a behavioural difference between the original method and its "
        + "replacement.")
    public static boolean isNumeric(java.lang.String address) {
        throw new RuntimeException("Stub!");
    }

    static java.net.InetAddress parseNumericAddressNoThrow(java.lang.String address) {
        throw new RuntimeException("Stub!");
    }

    static java.net.InetAddress disallowDeprecatedFormats(
            java.lang.String address, java.net.InetAddress inetAddress) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @deprecated Use {@code android.net.InetAddresses.parseNumericAddress(String)} instead.
     */
    @UnsupportedAppUsage(maxTargetSdk = VersionCodes.P, trackingBug = 78686891,
        publicAlternatives = "Use {@code android.net.InetAddresses#parseNumericAddress} "
        + "instead. There is a behavioural difference between the original method and its "
        + "replacement.")
    public static java.net.InetAddress parseNumericAddress(java.lang.String numericAddress) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public static void clearDnsCache() {
        throw new RuntimeException("Stub!");
    }

    public static java.net.InetAddress getByNameOnNet(java.lang.String host, int netId)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public static java.net.InetAddress[] getAllByNameOnNet(java.lang.String host, int netId)
            throws java.net.UnknownHostException {
        throw new RuntimeException("Stub!");
    }

    private static final java.lang.ClassLoader BOOT_CLASSLOADER;

    static {
        BOOT_CLASSLOADER = null;
    }

    static final int NETID_UNSET = 0; // 0x0

    private transient java.lang.String canonicalHostName;

    @UnsupportedAppUsage
    transient java.net.InetAddress.InetAddressHolder holder;

    static final java.net.InetAddressImpl impl;

    static {
        impl = null;
    }

    private static final sun.net.spi.nameservice.NameService nameService;

    static {
        nameService = null;
    }

    private static final java.io.ObjectStreamField[] serialPersistentFields;

    static {
        serialPersistentFields = new java.io.ObjectStreamField[0];
    }

    private static final long serialVersionUID = 3286316764910316507L; // 0x2d9b57af9fe3ebdbL

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class InetAddressHolder {

        InetAddressHolder() {
            throw new RuntimeException("Stub!");
        }

        InetAddressHolder(java.lang.String hostName, int address, int family) {
            throw new RuntimeException("Stub!");
        }

        void init(java.lang.String hostName, int family) {
            throw new RuntimeException("Stub!");
        }

        java.lang.String getHostName() {
            throw new RuntimeException("Stub!");
        }

        java.lang.String getOriginalHostName() {
            throw new RuntimeException("Stub!");
        }

        int getAddress() {
            throw new RuntimeException("Stub!");
        }

        int getFamily() {
            throw new RuntimeException("Stub!");
        }

        @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        int address;

        @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        int family;

        @UnsupportedAppUsage
        java.lang.String hostName;

        @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        java.lang.String originalHostName;
    }
}
