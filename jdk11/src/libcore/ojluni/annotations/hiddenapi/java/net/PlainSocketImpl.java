/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2007, 2008, Oracle and/or its affiliates. All rights reserved.
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
class PlainSocketImpl extends java.net.AbstractPlainSocketImpl {

    @android.compat.annotation.UnsupportedAppUsage
    PlainSocketImpl() {
        throw new RuntimeException("Stub!");
    }

    PlainSocketImpl(java.io.FileDescriptor fd) {
        throw new RuntimeException("Stub!");
    }

    protected <T> void setOption(java.net.SocketOption<T> name, T value)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected <T> T getOption(java.net.SocketOption<T> name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void socketSetOption(int opt, java.lang.Object val) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    void socketCreate(boolean isStream) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    void socketConnect(java.net.InetAddress address, int port, int timeout)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    void socketBind(java.net.InetAddress address, int port) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    void socketListen(int count) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    void socketAccept(java.net.SocketImpl s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    int socketAvailable() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    void socketClose0(boolean useDeferredClose) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.io.FileDescriptor getMarkerFD() throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    void socketShutdown(int howto) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    void socketSetOption0(int cmd, java.lang.Object value) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    java.lang.Object socketGetOption(int opt) throws java.net.SocketException {
        throw new RuntimeException("Stub!");
    }

    void socketSendUrgentData(int data) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }
}
