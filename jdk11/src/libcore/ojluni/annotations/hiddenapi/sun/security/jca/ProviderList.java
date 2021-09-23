/*
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

import java.security.*;
import java.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class ProviderList {

    private ProviderList(sun.security.jca.ProviderConfig[] configs, boolean allLoaded) {
        throw new RuntimeException("Stub!");
    }

    private ProviderList() {
        throw new RuntimeException("Stub!");
    }

    static sun.security.jca.ProviderList fromSecurityProperties() {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.jca.ProviderList add(
            sun.security.jca.ProviderList providerList, java.security.Provider p) {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.jca.ProviderList insertAt(
            sun.security.jca.ProviderList providerList, java.security.Provider p, int position) {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.jca.ProviderList remove(
            sun.security.jca.ProviderList providerList, java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.jca.ProviderList newList(java.security.Provider... providers) {
        throw new RuntimeException("Stub!");
    }

    sun.security.jca.ProviderList getJarList(java.lang.String[] jarClassNames) {
        throw new RuntimeException("Stub!");
    }

    public int size() {
        throw new RuntimeException("Stub!");
    }

    java.security.Provider getProvider(int index) {
        throw new RuntimeException("Stub!");
    }

    public java.util.List<java.security.Provider> providers() {
        throw new RuntimeException("Stub!");
    }

    private sun.security.jca.ProviderConfig getProviderConfig(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.security.Provider getProvider(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public int getIndex(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    private int loadAll() {
        throw new RuntimeException("Stub!");
    }

    sun.security.jca.ProviderList removeInvalid() {
        throw new RuntimeException("Stub!");
    }

    public java.security.Provider[] toArray() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.security.Provider.Service getService(java.lang.String type, java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.util.List<java.security.Provider.Service> getServices(
            java.lang.String type, java.lang.String algorithm) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public java.util.List<java.security.Provider.Service> getServices(
            java.lang.String type, java.util.List<java.lang.String> algorithms) {
        throw new RuntimeException("Stub!");
    }

    public java.util.List<java.security.Provider.Service> getServices(
            java.util.List<sun.security.jca.ServiceId> ids) {
        throw new RuntimeException("Stub!");
    }

    static final sun.security.jca.ProviderList EMPTY;

    static {
        EMPTY = null;
    }

    private static final java.security.Provider EMPTY_PROVIDER;

    static {
        EMPTY_PROVIDER = null;
    }

    private static final java.security.Provider[] P0;

    static {
        P0 = new java.security.Provider[0];
    }

    private static final sun.security.jca.ProviderConfig[] PC0;

    static {
        PC0 = new sun.security.jca.ProviderConfig[0];
    }

    private volatile boolean allLoaded;

    private final sun.security.jca.ProviderConfig[] configs;

    {
        configs = new sun.security.jca.ProviderConfig[0];
    }

    static final sun.security.util.Debug debug;

    static {
        debug = null;
    }

    private final java.util.List<java.security.Provider> userList;

    {
        userList = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private final class ServiceList extends java.util.AbstractList<java.security.Provider.Service> {

        ServiceList(java.lang.String type, java.lang.String algorithm) {
            throw new RuntimeException("Stub!");
        }

        ServiceList(java.util.List<sun.security.jca.ServiceId> ids) {
            throw new RuntimeException("Stub!");
        }

        private void addService(java.security.Provider.Service s) {
            throw new RuntimeException("Stub!");
        }

        private java.security.Provider.Service tryGet(int index) {
            throw new RuntimeException("Stub!");
        }

        public java.security.Provider.Service get(int index) {
            throw new RuntimeException("Stub!");
        }

        public int size() {
            throw new RuntimeException("Stub!");
        }

        public boolean isEmpty() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Iterator<java.security.Provider.Service> iterator() {
            throw new RuntimeException("Stub!");
        }

        private final java.lang.String algorithm;

        {
            algorithm = null;
        }

        private java.security.Provider.Service firstService;

        private final java.util.List<sun.security.jca.ServiceId> ids;

        {
            ids = null;
        }

        private int providerIndex;

        private java.util.List<java.security.Provider.Service> services;

        private final java.lang.String type;

        {
            type = null;
        }
    }
}
