/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2013, 2015, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class ClassLoader {

    private ClassLoader(java.lang.Void unused, java.lang.ClassLoader parent) {
        throw new RuntimeException("Stub!");
    }

    protected ClassLoader(java.lang.ClassLoader parent) {
        throw new RuntimeException("Stub!");
    }

    protected ClassLoader() {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.ClassLoader createSystemClassLoader() {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.Void checkCreateClassLoader() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Class<?> loadClass(java.lang.String name)
            throws java.lang.ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Class<?> loadClass(java.lang.String name, boolean resolve)
            throws java.lang.ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Class<?> findClass(java.lang.String name)
            throws java.lang.ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    protected final java.lang.Class<?> defineClass(byte[] b, int off, int len)
            throws java.lang.ClassFormatError {
        throw new RuntimeException("Stub!");
    }

    protected final java.lang.Class<?> defineClass(
            java.lang.String name, byte[] b, int off, int len) throws java.lang.ClassFormatError {
        throw new RuntimeException("Stub!");
    }

    protected final java.lang.Class<?> defineClass(
            java.lang.String name,
            byte[] b,
            int off,
            int len,
            java.security.ProtectionDomain protectionDomain)
            throws java.lang.ClassFormatError {
        throw new RuntimeException("Stub!");
    }

    protected final java.lang.Class<?> defineClass(
            java.lang.String name,
            java.nio.ByteBuffer b,
            java.security.ProtectionDomain protectionDomain)
            throws java.lang.ClassFormatError {
        throw new RuntimeException("Stub!");
    }

    protected final void resolveClass(java.lang.Class<?> c) {
        throw new RuntimeException("Stub!");
    }

    protected final java.lang.Class<?> findSystemClass(java.lang.String name)
            throws java.lang.ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Class<?> findBootstrapClassOrNull(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    protected final java.lang.Class<?> findLoadedClass(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    protected final void setSigners(java.lang.Class<?> c, java.lang.Object[] signers) {
        throw new RuntimeException("Stub!");
    }

    public java.net.URL getResource(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<java.net.URL> getResources(java.lang.String name)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected java.net.URL findResource(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    protected java.util.Enumeration<java.net.URL> findResources(java.lang.String name)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected static boolean registerAsParallelCapable() {
        throw new RuntimeException("Stub!");
    }

    public static java.net.URL getSystemResource(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Enumeration<java.net.URL> getSystemResources(java.lang.String name)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static java.net.URL getBootstrapResource(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    private static java.util.Enumeration<java.net.URL> getBootstrapResources(java.lang.String name)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.io.InputStream getResourceAsStream(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public static java.io.InputStream getSystemResourceAsStream(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.ClassLoader getParent() {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.ClassLoader getSystemClassLoader() {
        throw new RuntimeException("Stub!");
    }

    static java.lang.ClassLoader getClassLoader(java.lang.Class<?> caller) {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Package definePackage(
            java.lang.String name,
            java.lang.String specTitle,
            java.lang.String specVersion,
            java.lang.String specVendor,
            java.lang.String implTitle,
            java.lang.String implVersion,
            java.lang.String implVendor,
            java.net.URL sealBase)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Package getPackage(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Package[] getPackages() {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.String findLibrary(java.lang.String libname) {
        throw new RuntimeException("Stub!");
    }

    public void setDefaultAssertionStatus(boolean enabled) {
        throw new RuntimeException("Stub!");
    }

    public void setPackageAssertionStatus(java.lang.String packageName, boolean enabled) {
        throw new RuntimeException("Stub!");
    }

    public void setClassAssertionStatus(java.lang.String className, boolean enabled) {
        throw new RuntimeException("Stub!");
    }

    public void clearAssertionStatus() {
        throw new RuntimeException("Stub!");
    }

    private transient long allocator;

    private transient long classTable;

    private final java.util.HashMap<java.lang.String, java.lang.Package> packages;

    {
        packages = null;
    }

    @UnsupportedAppUsage
    private final java.lang.ClassLoader parent;

    {
        parent = null;
    }

    public final java.util.Map<java.util.List<java.lang.Class<?>>, java.lang.Class<?>> proxyCache;

    {
        proxyCache = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class SystemClassLoader {

        private SystemClassLoader() {
            throw new RuntimeException("Stub!");
        }

        public static java.lang.ClassLoader loader;
    }
}
