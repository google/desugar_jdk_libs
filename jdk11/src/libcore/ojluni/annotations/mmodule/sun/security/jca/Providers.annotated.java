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

import java.security.Provider;
import java.util.Set;
import java.security.NoSuchAlgorithmException;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
@SuppressWarnings({"unchecked", "deprecation", "all"})
public class Providers {

@libcore.api.Hide
Providers() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static java.security.Provider getSunProvider() { throw new RuntimeException("Stub!"); }

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public static java.lang.Object startJarVerification() { throw new RuntimeException("Stub!"); }

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public static void stopJarVerification(java.lang.Object obj) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static sun.security.jca.ProviderList getProviderList() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static void setProviderList(ProviderList newList) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static sun.security.jca.ProviderList getFullProviderList() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static sun.security.jca.ProviderList getThreadProviderList() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static synchronized sun.security.jca.ProviderList beginThreadProviderList(ProviderList list) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static synchronized void endThreadProviderList(ProviderList list) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static void setMaximumAllowableApiLevelForBcDeprecation(int targetApiLevel) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static int getMaximumAllowableApiLevelForBcDeprecation() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static synchronized void checkBouncyCastleDeprecation(java.lang.String provider, java.lang.String service, java.lang.String algorithm) throws java.security.NoSuchAlgorithmException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static synchronized void checkBouncyCastleDeprecation(java.security.Provider provider, java.lang.String service, java.lang.String algorithm) throws java.security.NoSuchAlgorithmException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static final int DEFAULT_MAXIMUM_ALLOWABLE_TARGET_API_LEVEL_FOR_BC_DEPRECATION = 27; // 0x1b
}
