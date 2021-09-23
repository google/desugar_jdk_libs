/*
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
class SocksSocketImpl extends java.net.PlainSocketImpl implements java.net.SocksConsts {

    @UnsupportedAppUsage
    SocksSocketImpl() {
        throw new RuntimeException("Stub!");
    }

    SocksSocketImpl(java.lang.String server, int port) {
        throw new RuntimeException("Stub!");
    }

    SocksSocketImpl(java.net.Proxy proxy) {
        throw new RuntimeException("Stub!");
    }

    void setV4() {
        throw new RuntimeException("Stub!");
    }

    private synchronized void privilegedConnect(java.lang.String host, int port, int timeout)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void superConnectServer(java.lang.String host, int port, int timeout)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static int remainingMillis(long deadlineMillis) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private int readSocksReply(java.io.InputStream in, byte[] data) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private int readSocksReply(java.io.InputStream in, byte[] data, long deadlineMillis)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private boolean authenticate(
            byte method, java.io.InputStream in, java.io.BufferedOutputStream out)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private boolean authenticate(
            byte method,
            java.io.InputStream in,
            java.io.BufferedOutputStream out,
            long deadlineMillis)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void connectV4(
            java.io.InputStream in,
            java.io.OutputStream out,
            java.net.InetSocketAddress endpoint,
            long deadlineMillis)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void connect(java.net.SocketAddress endpoint, int timeout)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected java.net.InetAddress getInetAddress() {
        throw new RuntimeException("Stub!");
    }

    protected int getPort() {
        throw new RuntimeException("Stub!");
    }

    protected int getLocalPort() {
        throw new RuntimeException("Stub!");
    }

    protected void close() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String getUserName() {
        throw new RuntimeException("Stub!");
    }

    private boolean applicationSetProxy;

    private java.io.InputStream cmdIn;

    private java.io.OutputStream cmdOut;

    private java.net.Socket cmdsock;

    private java.net.InetSocketAddress external_address;

    private java.lang.String server;

    private int serverPort = 1080; // 0x438

    private boolean useV4 = false;
}
