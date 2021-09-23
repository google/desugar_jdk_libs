/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
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

package sun.net.www;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class URLConnection extends java.net.URLConnection {

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public URLConnection(java.net.URL u) {
        super(null);
        throw new RuntimeException("Stub!");
    }

    public sun.net.www.MessageHeader getProperties() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public void setProperties(sun.net.www.MessageHeader properties) {
        throw new RuntimeException("Stub!");
    }

    public void setRequestProperty(java.lang.String key, java.lang.String value) {
        throw new RuntimeException("Stub!");
    }

    public void addRequestProperty(java.lang.String key, java.lang.String value) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRequestProperty(java.lang.String key) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Map<java.lang.String, java.util.List<java.lang.String>>
            getRequestProperties() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getHeaderField(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getHeaderFieldKey(int n) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getHeaderField(int n) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getContentType() {
        throw new RuntimeException("Stub!");
    }

    public void setContentType(java.lang.String type) {
        throw new RuntimeException("Stub!");
    }

    public int getContentLength() {
        throw new RuntimeException("Stub!");
    }

    protected void setContentLength(int length) {
        throw new RuntimeException("Stub!");
    }

    public boolean canCache() {
        throw new RuntimeException("Stub!");
    }

    public void close() {
        throw new RuntimeException("Stub!");
    }

    public static synchronized void setProxiedHost(java.lang.String host) {
        throw new RuntimeException("Stub!");
    }

    public static synchronized boolean isProxiedHost(java.lang.String host) {
        throw new RuntimeException("Stub!");
    }

    private int contentLength = -1; // 0xffffffff

    private java.lang.String contentType;

    protected sun.net.www.MessageHeader properties;

    private static java.util.HashMap<java.lang.String, java.lang.Void> proxiedHosts;
}
