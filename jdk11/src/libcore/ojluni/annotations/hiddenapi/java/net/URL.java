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

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class URL implements java.io.Serializable {

    public URL(java.lang.String protocol, java.lang.String host, int port, java.lang.String file)
            throws java.net.MalformedURLException {
        throw new RuntimeException("Stub!");
    }

    public URL(java.lang.String protocol, java.lang.String host, java.lang.String file)
            throws java.net.MalformedURLException {
        throw new RuntimeException("Stub!");
    }

    public URL(
            java.lang.String protocol,
            java.lang.String host,
            int port,
            java.lang.String file,
            java.net.URLStreamHandler handler)
            throws java.net.MalformedURLException {
        throw new RuntimeException("Stub!");
    }

    public URL(java.lang.String spec) throws java.net.MalformedURLException {
        throw new RuntimeException("Stub!");
    }

    public URL(java.net.URL context, java.lang.String spec) throws java.net.MalformedURLException {
        throw new RuntimeException("Stub!");
    }

    public URL(java.net.URL context, java.lang.String spec, java.net.URLStreamHandler handler)
            throws java.net.MalformedURLException {
        throw new RuntimeException("Stub!");
    }

    private boolean isValidProtocol(java.lang.String protocol) {
        throw new RuntimeException("Stub!");
    }

    private void checkSpecifyHandler(java.lang.SecurityManager sm) {
        throw new RuntimeException("Stub!");
    }

    void set(
            java.lang.String protocol,
            java.lang.String host,
            int port,
            java.lang.String file,
            java.lang.String ref) {
        throw new RuntimeException("Stub!");
    }

    void set(
            java.lang.String protocol,
            java.lang.String host,
            int port,
            java.lang.String authority,
            java.lang.String userInfo,
            java.lang.String path,
            java.lang.String query,
            java.lang.String ref) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getQuery() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getPath() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getUserInfo() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getAuthority() {
        throw new RuntimeException("Stub!");
    }

    public int getPort() {
        throw new RuntimeException("Stub!");
    }

    public int getDefaultPort() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getProtocol() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getHost() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getFile() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRef() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public synchronized int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public boolean sameFile(java.net.URL other) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toExternalForm() {
        throw new RuntimeException("Stub!");
    }

    public java.net.URI toURI() throws java.net.URISyntaxException {
        throw new RuntimeException("Stub!");
    }

    public java.net.URLConnection openConnection() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.net.URLConnection openConnection(java.net.Proxy proxy) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.io.InputStream openStream() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object getContent() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object getContent(java.lang.Class[] classes) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public static void setURLStreamHandlerFactory(java.net.URLStreamHandlerFactory fac) {
        throw new RuntimeException("Stub!");
    }

    static java.net.URLStreamHandler getURLStreamHandler(java.lang.String protocol) {
        throw new RuntimeException("Stub!");
    }

    private static java.net.URLStreamHandler createBuiltinHandler(java.lang.String protocol)
            throws java.lang.ClassNotFoundException, java.lang.IllegalAccessException,
                    java.lang.InstantiationException {
        throw new RuntimeException("Stub!");
    }

    private static java.util.Set<java.lang.String> createBuiltinHandlerClassNames() {
        throw new RuntimeException("Stub!");
    }

    private synchronized void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private synchronized void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Object readResolve() throws java.io.ObjectStreamException {
        throw new RuntimeException("Stub!");
    }

    private java.net.URL setDeserializedFields(java.net.URLStreamHandler handler) {
        throw new RuntimeException("Stub!");
    }

    private java.net.URL fabricateNewURL() throws java.io.InvalidObjectException {
        throw new RuntimeException("Stub!");
    }

    private boolean isBuiltinStreamHandler(java.lang.String handlerClassName) {
        throw new RuntimeException("Stub!");
    }

    private void resetState() {
        throw new RuntimeException("Stub!");
    }

    private void setSerializedHashCode(int hc) {
        throw new RuntimeException("Stub!");
    }

    private static final java.util.Set<java.lang.String> BUILTIN_HANDLER_CLASS_NAMES;

    static {
        BUILTIN_HANDLER_CLASS_NAMES = null;
    }

    private java.lang.String authority;

    @UnsupportedAppUsage
    static java.net.URLStreamHandlerFactory factory;

    private java.lang.String file;

    @UnsupportedAppUsage
    transient java.net.URLStreamHandler handler;

    @UnsupportedAppUsage
    static java.util.Hashtable<java.lang.String, java.net.URLStreamHandler> handlers;

    private int hashCode = -1; // 0xffffffff

    private java.lang.String host;

    transient java.net.InetAddress hostAddress;

    private transient java.lang.String path;

    private int port = -1; // 0xffffffff

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private java.lang.String protocol;

    private static final java.lang.String protocolPathProp = "java.protocol.handler.pkgs";

    private transient java.lang.String query;

    private java.lang.String ref;

    private static final java.io.ObjectStreamField[] serialPersistentFields;

    static {
        serialPersistentFields = new java.io.ObjectStreamField[0];
    }

    static final long serialVersionUID = -7627629688361524110L; // 0x962537361afce472L

    private static java.lang.Object streamHandlerLock;

    private transient java.lang.String userInfo;
}
