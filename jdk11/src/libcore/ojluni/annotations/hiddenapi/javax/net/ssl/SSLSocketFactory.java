/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.net.ssl;

import android.compat.annotation.UnsupportedAppUsage;
import dalvik.annotation.compat.VersionCodes;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class SSLSocketFactory extends javax.net.SocketFactory {

    public SSLSocketFactory() {
        throw new RuntimeException("Stub!");
    }

    private static void log(java.lang.String msg) {
        throw new RuntimeException("Stub!");
    }

    public static synchronized javax.net.SocketFactory getDefault() {
        throw new RuntimeException("Stub!");
    }

    static java.lang.String getSecurityProperty(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public abstract java.lang.String[] getDefaultCipherSuites();

    public abstract java.lang.String[] getSupportedCipherSuites();

    public abstract java.net.Socket createSocket(
            java.net.Socket s, java.lang.String host, int port, boolean autoClose)
            throws java.io.IOException;

    @UnsupportedAppUsage
    public java.net.Socket createSocket(
            java.net.Socket s, java.io.InputStream consumed, boolean autoClose)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    static final boolean DEBUG;

    static {
        DEBUG = false;
    }

    /**
     * @deprecated Use {@link #getDefault()} to read the current default; from Android API
     * level 21 onwards, apps should have no need to ever write this value because it is
     * automatically recomputed when the set of {@link java.security.Provider security providers}
     * changes.
     */
    @UnsupportedAppUsage(maxTargetSdk = VersionCodes.P,
            trackingBug = 118741276,
            publicAlternatives = "Use {@link #getDefault()} to read the current default; from "
            + "Android API level 21 onwards, apps should have no need to ever write this value "
            + "because it is automatically recomputed when the set of "
            + "{@link java.security.Provider} security providers changes.")
    private static javax.net.ssl.SSLSocketFactory defaultSocketFactory;

    private static int lastVersion = -1; // 0xffffffff
}
