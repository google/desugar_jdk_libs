/*
 * Copyright (c) 2003, 2016, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.jca;

import java.lang.reflect.*;
import java.security.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
final class ProviderConfig {

    ProviderConfig(java.lang.String className, java.lang.String argument) {
        throw new RuntimeException("Stub!");
    }

    ProviderConfig(java.lang.String className) {
        throw new RuntimeException("Stub!");
    }

    ProviderConfig(java.security.Provider provider) {
        throw new RuntimeException("Stub!");
    }

    private void checkSunPKCS11Solaris() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private boolean hasArgument() {
        throw new RuntimeException("Stub!");
    }

    private boolean shouldLoad() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private void disableLoad() {
        throw new RuntimeException("Stub!");
    }

    boolean isLoaded() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    synchronized java.security.Provider getProvider() {
        throw new RuntimeException("Stub!");
    }

    private java.security.Provider doLoadProvider() {
        throw new RuntimeException("Stub!");
    }

    private java.security.Provider initProvider(
            java.lang.String className, java.lang.ClassLoader cl) throws java.lang.Exception {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String expand(java.lang.String value) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553) private static final java.lang.Class[] CL_STRING;

    static {
        CL_STRING = new java.lang.Class[0];
    }

    private static final int MAX_LOAD_TRIES = 30; // 0x1e

    private static final java.lang.String P11_SOL_ARG =
            "${java.home}/lib/security/sunpkcs11-solaris.cfg";

    private static final java.lang.String P11_SOL_NAME = "sun.security.pkcs11.SunPKCS11";

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553) private final java.lang.String argument;

    {
        argument = null;
    }

    private final java.lang.String className;

    {
        className = null;
    }

    private static final sun.security.util.Debug debug;

    static {
        debug = null;
    }

    private boolean isLoading;

    private volatile java.security.Provider provider;

    private int tries;
}
