/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.security.*;
import java.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class GetInstance {

    private GetInstance() {
        throw new RuntimeException("Stub!");
    }

    public static java.security.Provider.Service getService(
            java.lang.String type, java.lang.String algorithm)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    public static java.security.Provider.Service getService(
            java.lang.String type, java.lang.String algorithm, java.lang.String provider)
            throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        throw new RuntimeException("Stub!");
    }

    public static java.security.Provider.Service getService(
            java.lang.String type, java.lang.String algorithm, java.security.Provider provider)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    public static java.util.List<java.security.Provider.Service> getServices(
            java.lang.String type, java.lang.String algorithm) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public static java.util.List<java.security.Provider.Service> getServices(
            java.lang.String type, java.util.List<java.lang.String> algorithms) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.List<java.security.Provider.Service> getServices(
            java.util.List<sun.security.jca.ServiceId> ids) {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.jca.GetInstance.Instance getInstance(
            java.lang.String type, java.lang.Class<?> clazz, java.lang.String algorithm)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static sun.security.jca.GetInstance.Instance getInstance(
            java.lang.String type,
            java.lang.Class<?> clazz,
            java.lang.String algorithm,
            java.lang.Object param)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.jca.GetInstance.Instance getInstance(
            java.lang.String type,
            java.lang.Class<?> clazz,
            java.lang.String algorithm,
            java.lang.String provider)
            throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static sun.security.jca.GetInstance.Instance getInstance(
            java.lang.String type,
            java.lang.Class<?> clazz,
            java.lang.String algorithm,
            java.lang.Object param,
            java.lang.String provider)
            throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.jca.GetInstance.Instance getInstance(
            java.lang.String type,
            java.lang.Class<?> clazz,
            java.lang.String algorithm,
            java.security.Provider provider)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static sun.security.jca.GetInstance.Instance getInstance(
            java.lang.String type,
            java.lang.Class<?> clazz,
            java.lang.String algorithm,
            java.lang.Object param,
            java.security.Provider provider)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.jca.GetInstance.Instance getInstance(
            java.security.Provider.Service s, java.lang.Class<?> clazz)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.jca.GetInstance.Instance getInstance(
            java.security.Provider.Service s, java.lang.Class<?> clazz, java.lang.Object param)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    public static void checkSuperClass(
            java.security.Provider.Service s,
            java.lang.Class<?> subClass,
            java.lang.Class<?> superClass)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static final class Instance {

        private Instance(java.security.Provider provider, java.lang.Object impl) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object[] toArray() {
            throw new RuntimeException("Stub!");
        }

        @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553) public final java.lang.Object impl;

        {
            impl = null;
        }

        @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553) public final java.security.Provider provider;

        {
            provider = null;
        }
    }
}
