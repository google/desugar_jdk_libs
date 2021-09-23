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
public final class Inet4Address extends java.net.InetAddress {

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    Inet4Address() {
        throw new RuntimeException("Stub!");
    }

    Inet4Address(java.lang.String hostName, byte[] addr) {
        throw new RuntimeException("Stub!");
    }

    Inet4Address(java.lang.String hostName, int address) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Object writeReplace() throws java.io.ObjectStreamException {
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

    static java.lang.String numericToTextFormat(byte[] src) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static final java.net.InetAddress ALL;

    static {
        ALL = null;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static final java.net.InetAddress ANY;

    static {
        ANY = null;
    }

    static final int INADDRSZ = 4; // 0x4

    public static final java.net.InetAddress LOOPBACK;

    static {
        LOOPBACK = null;
    }

    private static final long serialVersionUID = 3286316764910316507L; // 0x2d9b57af9fe3ebdbL
}
