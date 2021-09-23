/*
 * Copyright (c) 1997, 2015, Oracle and/or its affiliates. All rights reserved.
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
public class URLClassLoader extends java.security.SecureClassLoader implements java.io.Closeable {

    public URLClassLoader(java.net.URL[] urls, java.lang.ClassLoader parent) {
        throw new RuntimeException("Stub!");
    }

    URLClassLoader(
            java.net.URL[] urls,
            java.lang.ClassLoader parent,
            java.security.AccessControlContext acc) {
        throw new RuntimeException("Stub!");
    }

    public URLClassLoader(java.net.URL[] urls) {
        throw new RuntimeException("Stub!");
    }

    URLClassLoader(java.net.URL[] urls, java.security.AccessControlContext acc) {
        throw new RuntimeException("Stub!");
    }

    public URLClassLoader(
            java.net.URL[] urls,
            java.lang.ClassLoader parent,
            java.net.URLStreamHandlerFactory factory) {
        throw new RuntimeException("Stub!");
    }

    public java.io.InputStream getResourceAsStream(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public void close() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void addURL(java.net.URL url) {
        throw new RuntimeException("Stub!");
    }

    public java.net.URL[] getURLs() {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Class<?> findClass(java.lang.String name)
            throws java.lang.ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Package getAndVerifyPackage(
            java.lang.String pkgname, java.util.jar.Manifest man, java.net.URL url) {
        throw new RuntimeException("Stub!");
    }

    private void definePackageInternal(
            java.lang.String pkgname, java.util.jar.Manifest man, java.net.URL url) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Class<?> defineClass(java.lang.String name, sun.misc.Resource res)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Package definePackage(
            java.lang.String name, java.util.jar.Manifest man, java.net.URL url)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    private boolean isSealed(java.lang.String name, java.util.jar.Manifest man) {
        throw new RuntimeException("Stub!");
    }

    public java.net.URL findResource(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<java.net.URL> findResources(java.lang.String name)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected java.security.PermissionCollection getPermissions(
            java.security.CodeSource codesource) {
        throw new RuntimeException("Stub!");
    }

    public static java.net.URLClassLoader newInstance(
            java.net.URL[] urls, java.lang.ClassLoader parent) {
        throw new RuntimeException("Stub!");
    }

    public static java.net.URLClassLoader newInstance(java.net.URL[] urls) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private final java.security.AccessControlContext acc;

    {
        acc = null;
    }

    private java.util.WeakHashMap<java.io.Closeable, java.lang.Void> closeables;

    @UnsupportedAppUsage
    private final sun.misc.URLClassPath ucp;

    {
        ucp = null;
    }
}
