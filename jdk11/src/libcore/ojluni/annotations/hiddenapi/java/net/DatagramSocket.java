/*
 * Copyright (C) 2014 The Android Open Source Project
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

package java.net;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class DatagramSocket implements java.io.Closeable {

    public DatagramSocket() throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    protected DatagramSocket(java.net.DatagramSocketImpl impl) {
        throw new RuntimeException("Stub!");
    }

    public DatagramSocket(java.net.SocketAddress bindaddr) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public DatagramSocket(int port) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public DatagramSocket(int port, java.net.InetAddress laddr) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    private synchronized void connectInternal(java.net.InetAddress address, int port)
            throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    private void checkOldImpl() {
        throw new RuntimeException("Stub!");
    }

    void createImpl() throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    java.net.DatagramSocketImpl getImpl() throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void bind(java.net.SocketAddress addr) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    void checkAddress(java.net.InetAddress addr, java.lang.String op) {
        throw new RuntimeException("Stub!");
    }

    public void connect(java.net.InetAddress address, int port) {
        throw new RuntimeException("Stub!");
    }

    public void connect(java.net.SocketAddress addr) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public void disconnect() {
        throw new RuntimeException("Stub!");
    }

    public boolean isBound() {
        throw new RuntimeException("Stub!");
    }

    public boolean isConnected() {
        throw new RuntimeException("Stub!");
    }

    public java.net.InetAddress getInetAddress() {
        throw new RuntimeException("Stub!");
    }

    public int getPort() {
        throw new RuntimeException("Stub!");
    }

    public java.net.SocketAddress getRemoteSocketAddress() {
        throw new RuntimeException("Stub!");
    }

    public java.net.SocketAddress getLocalSocketAddress() {
        throw new RuntimeException("Stub!");
    }

    public void send(java.net.DatagramPacket p) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void receive(java.net.DatagramPacket p) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private boolean checkFiltering(java.net.DatagramPacket p) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public java.net.InetAddress getLocalAddress() {
        throw new RuntimeException("Stub!");
    }

    public int getLocalPort() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setSoTimeout(int timeout) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public synchronized int getSoTimeout() throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setSendBufferSize(int size) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public synchronized int getSendBufferSize() throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setReceiveBufferSize(int size) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public synchronized int getReceiveBufferSize() throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setReuseAddress(boolean on) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public synchronized boolean getReuseAddress() throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setBroadcast(boolean on) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public synchronized boolean getBroadcast() throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setTrafficClass(int tc) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public synchronized int getTrafficClass() throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    public void close() {
        throw new RuntimeException("Stub!");
    }

    public boolean isClosed() {
        throw new RuntimeException("Stub!");
    }

    public java.nio.channels.DatagramChannel getChannel() {
        throw new RuntimeException("Stub!");
    }

    public static synchronized void setDatagramSocketImplFactory(
            java.net.DatagramSocketImplFactory fac) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public java.io.FileDescriptor getFileDescriptor$() {
        throw new RuntimeException("Stub!");
    }

    static final int ST_CONNECTED = 1; // 0x1

    static final int ST_CONNECTED_NO_IMPL = 2; // 0x2

    static final int ST_NOT_CONNECTED = 0; // 0x0

    private boolean bound = false;

    private int bytesLeftToFilter;

    private java.lang.Object closeLock;

    private boolean closed = false;

    int connectState = 0; // 0x0

    java.net.InetAddress connectedAddress;

    int connectedPort = -1; // 0xffffffff

    private boolean created = false;

    private boolean explicitFilter = false;

    static java.net.DatagramSocketImplFactory factory;

    @UnsupportedAppUsage
    java.net.DatagramSocketImpl impl;

    static java.lang.Class<?> implClass;

    boolean oldImpl = false;

    private java.net.SocketException pendingConnectException;
}
