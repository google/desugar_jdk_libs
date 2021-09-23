/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
public abstract class Authenticator {

    public Authenticator() {
        throw new RuntimeException("Stub!");
    }

    private void reset() {
        throw new RuntimeException("Stub!");
    }

    public static synchronized void setDefault(java.net.Authenticator a) {
        throw new RuntimeException("Stub!");
    }

    public static java.net.PasswordAuthentication requestPasswordAuthentication(
            java.net.InetAddress addr,
            int port,
            java.lang.String protocol,
            java.lang.String prompt,
            java.lang.String scheme) {
        throw new RuntimeException("Stub!");
    }

    public static java.net.PasswordAuthentication requestPasswordAuthentication(
            java.lang.String host,
            java.net.InetAddress addr,
            int port,
            java.lang.String protocol,
            java.lang.String prompt,
            java.lang.String scheme) {
        throw new RuntimeException("Stub!");
    }

    public static java.net.PasswordAuthentication requestPasswordAuthentication(
            java.lang.String host,
            java.net.InetAddress addr,
            int port,
            java.lang.String protocol,
            java.lang.String prompt,
            java.lang.String scheme,
            java.net.URL url,
            java.net.Authenticator.RequestorType reqType) {
        throw new RuntimeException("Stub!");
    }

    protected final java.lang.String getRequestingHost() {
        throw new RuntimeException("Stub!");
    }

    protected final java.net.InetAddress getRequestingSite() {
        throw new RuntimeException("Stub!");
    }

    protected final int getRequestingPort() {
        throw new RuntimeException("Stub!");
    }

    protected final java.lang.String getRequestingProtocol() {
        throw new RuntimeException("Stub!");
    }

    protected final java.lang.String getRequestingPrompt() {
        throw new RuntimeException("Stub!");
    }

    protected final java.lang.String getRequestingScheme() {
        throw new RuntimeException("Stub!");
    }

    protected java.net.PasswordAuthentication getPasswordAuthentication() {
        throw new RuntimeException("Stub!");
    }

    protected java.net.URL getRequestingURL() {
        throw new RuntimeException("Stub!");
    }

    protected java.net.Authenticator.RequestorType getRequestorType() {
        throw new RuntimeException("Stub!");
    }

    private java.net.Authenticator.RequestorType requestingAuthType;

    private java.lang.String requestingHost;

    private int requestingPort;

    private java.lang.String requestingPrompt;

    private java.lang.String requestingProtocol;

    private java.lang.String requestingScheme;

    private java.net.InetAddress requestingSite;

    private java.net.URL requestingURL;

    @UnsupportedAppUsage
    private static java.net.Authenticator theAuthenticator;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static enum RequestorType {
        PROXY,
        SERVER;

        private RequestorType() {
            throw new RuntimeException("Stub!");
        }
    }
}
