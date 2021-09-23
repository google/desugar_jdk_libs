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

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class InetAddress implements java.io.Serializable {

InetAddress() { throw new RuntimeException("Stub!"); }

public boolean isMulticastAddress() { throw new RuntimeException("Stub!"); }

public boolean isAnyLocalAddress() { throw new RuntimeException("Stub!"); }

public boolean isLoopbackAddress() { throw new RuntimeException("Stub!"); }

public boolean isLinkLocalAddress() { throw new RuntimeException("Stub!"); }

public boolean isSiteLocalAddress() { throw new RuntimeException("Stub!"); }

public boolean isMCGlobal() { throw new RuntimeException("Stub!"); }

public boolean isMCNodeLocal() { throw new RuntimeException("Stub!"); }

public boolean isMCLinkLocal() { throw new RuntimeException("Stub!"); }

public boolean isMCSiteLocal() { throw new RuntimeException("Stub!"); }

public boolean isMCOrgLocal() { throw new RuntimeException("Stub!"); }

public boolean isReachable(int timeout) throws java.io.IOException { throw new RuntimeException("Stub!"); }

public boolean isReachable(@libcore.util.Nullable java.net.NetworkInterface netif, int ttl, int timeout) throws java.io.IOException { throw new RuntimeException("Stub!"); }

@libcore.util.NonNull public java.lang.String getHostName() { throw new RuntimeException("Stub!"); }

@libcore.util.NonNull public java.lang.String getCanonicalHostName() { throw new RuntimeException("Stub!"); }

public byte[] getAddress() { throw new RuntimeException("Stub!"); }

@libcore.util.Nullable public java.lang.String getHostAddress() { throw new RuntimeException("Stub!"); }

public int hashCode() { throw new RuntimeException("Stub!"); }

public boolean equals(@libcore.util.Nullable java.lang.Object obj) { throw new RuntimeException("Stub!"); }

@libcore.util.NonNull public java.lang.String toString() { throw new RuntimeException("Stub!"); }

@libcore.util.NonNull public static java.net.InetAddress getByAddress(@libcore.util.Nullable java.lang.String host, byte[] addr) throws java.net.UnknownHostException { throw new RuntimeException("Stub!"); }

@libcore.util.NonNull public static java.net.InetAddress getByName(@libcore.util.Nullable java.lang.String host) throws java.net.UnknownHostException { throw new RuntimeException("Stub!"); }

public static java.net.InetAddress[] getAllByName(@libcore.util.Nullable java.lang.String host) throws java.net.UnknownHostException { throw new RuntimeException("Stub!"); }

@libcore.util.NonNull public static java.net.InetAddress getLoopbackAddress() { throw new RuntimeException("Stub!"); }

@libcore.util.NonNull public static java.net.InetAddress getByAddress(byte[] addr) throws java.net.UnknownHostException { throw new RuntimeException("Stub!"); }

@libcore.util.NonNull public static java.net.InetAddress getLocalHost() throws java.net.UnknownHostException { throw new RuntimeException("Stub!"); }

@libcore.util.NonNull public static java.net.InetAddress getByNameOnNet(@libcore.util.Nullable java.lang.String host, int netId) throws java.net.UnknownHostException { throw new RuntimeException("Stub!"); }

@libcore.util.NonNull public static java.net.InetAddress[] getAllByNameOnNet(@libcore.util.Nullable java.lang.String host, int netId) throws java.net.UnknownHostException { throw new RuntimeException("Stub!"); }
}

