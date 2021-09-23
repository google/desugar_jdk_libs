/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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


@SuppressWarnings({"unchecked", "deprecation", "all"})
public class Providers {

    private Providers() {
        throw new RuntimeException("Stub!");
    }

    public static java.security.Provider getSunProvider() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static java.lang.Object startJarVerification() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static void stopJarVerification(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static sun.security.jca.ProviderList getProviderList() {
        throw new RuntimeException("Stub!");
    }

    public static void setProviderList(sun.security.jca.ProviderList newList) {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.jca.ProviderList getFullProviderList() {
        throw new RuntimeException("Stub!");
    }

    private static sun.security.jca.ProviderList getSystemProviderList() {
        throw new RuntimeException("Stub!");
    }

    private static void setSystemProviderList(sun.security.jca.ProviderList list) {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.jca.ProviderList getThreadProviderList() {
        throw new RuntimeException("Stub!");
    }

    private static void changeThreadProviderList(sun.security.jca.ProviderList list) {
        throw new RuntimeException("Stub!");
    }

    public static synchronized sun.security.jca.ProviderList beginThreadProviderList(
            sun.security.jca.ProviderList list) {
        throw new RuntimeException("Stub!");
    }

    public static synchronized void endThreadProviderList(sun.security.jca.ProviderList list) {
        throw new RuntimeException("Stub!");
    }

    public static void setMaximumAllowableApiLevelForBcDeprecation(int targetApiLevel) {
        throw new RuntimeException("Stub!");
    }

    public static int getMaximumAllowableApiLevelForBcDeprecation() {
        throw new RuntimeException("Stub!");
    }

    public static synchronized void checkBouncyCastleDeprecation(
            java.lang.String provider, java.lang.String service, java.lang.String algorithm)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    public static synchronized void checkBouncyCastleDeprecation(
            java.security.Provider provider, java.lang.String service, java.lang.String algorithm)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    private static void checkBouncyCastleDeprecation(
            java.lang.String service, java.lang.String algorithm)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    private static final java.lang.String BACKUP_PROVIDER_CLASSNAME =
            "sun.security.provider.VerificationProvider";

    public static final int DEFAULT_MAXIMUM_ALLOWABLE_TARGET_API_LEVEL_FOR_BC_DEPRECATION =
            27; // 0x1b

    private static final java.util.Set<java.lang.String> DEPRECATED_ALGORITHMS;

    static {
        DEPRECATED_ALGORITHMS = null;
    }

    private static volatile java.security.Provider SYSTEM_BOUNCY_CASTLE_PROVIDER;

    private static final java.lang.String[] jarVerificationProviders;

    static {
        jarVerificationProviders = new java.lang.String[0];
    }

    private static int maximumAllowableApiLevelForBcDeprecation = 27; // 0x1b

    private static volatile sun.security.jca.ProviderList providerList;

    private static final java.lang.ThreadLocal<sun.security.jca.ProviderList> threadLists;

    static {
        threadLists = null;
    }

    private static volatile int threadListsUsed;
}
