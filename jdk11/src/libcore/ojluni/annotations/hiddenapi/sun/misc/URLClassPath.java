/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1997, 2016, Oracle and/or its affiliates. All rights reserved.
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

package sun.misc;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class URLClassPath {

    public URLClassPath(
            java.net.URL[] urls,
            java.net.URLStreamHandlerFactory factory,
            java.security.AccessControlContext acc) {
        throw new RuntimeException("Stub!");
    }

    public URLClassPath(java.net.URL[] urls) {
        throw new RuntimeException("Stub!");
    }

    public URLClassPath(java.net.URL[] urls, java.security.AccessControlContext acc) {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.util.List<java.io.IOException> closeLoaders() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void addURL(java.net.URL url) {
        throw new RuntimeException("Stub!");
    }

    public java.net.URL[] getURLs() {
        throw new RuntimeException("Stub!");
    }

    public java.net.URL findResource(java.lang.String name, boolean check) {
        throw new RuntimeException("Stub!");
    }

    public sun.misc.Resource getResource(java.lang.String name, boolean check) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<java.net.URL> findResources(java.lang.String name, boolean check) {
        throw new RuntimeException("Stub!");
    }

    public sun.misc.Resource getResource(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<sun.misc.Resource> getResources(
            java.lang.String name, boolean check) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<sun.misc.Resource> getResources(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    synchronized void initLookupCache(java.lang.ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }

    static void disableAllLookupCaches() {
        throw new RuntimeException("Stub!");
    }

    private java.net.URL[] getLookupCacheURLs(java.lang.ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }

    private static int[] getLookupCacheForClassLoader(
            java.lang.ClassLoader loader, java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    private static boolean knownToNotExist0(
            java.lang.ClassLoader loader, java.lang.String className) {
        throw new RuntimeException("Stub!");
    }

    synchronized boolean knownToNotExist(java.lang.String className) {
        throw new RuntimeException("Stub!");
    }

    private synchronized int[] getLookupCache(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    private boolean ensureLoaderOpened(int index) {
        throw new RuntimeException("Stub!");
    }

    private synchronized void validateLookupCache(int index, java.lang.String urlNoFragString) {
        throw new RuntimeException("Stub!");
    }

    private synchronized sun.misc.URLClassPath.Loader getNextLoader(int[] cache, int index) {
        throw new RuntimeException("Stub!");
    }

    private synchronized sun.misc.URLClassPath.Loader getLoader(int index) {
        throw new RuntimeException("Stub!");
    }

    private sun.misc.URLClassPath.Loader getLoader(java.net.URL url) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void push(java.net.URL[] us) {
        throw new RuntimeException("Stub!");
    }

    public static java.net.URL[] pathToURLs(java.lang.String path) {
        throw new RuntimeException("Stub!");
    }

    public java.net.URL checkURL(java.net.URL url) {
        throw new RuntimeException("Stub!");
    }

    static void check(java.net.URL url) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static final boolean DEBUG;

    static {
        DEBUG = false;
    }

    private static final boolean DEBUG_LOOKUP_CACHE;

    static {
        DEBUG_LOOKUP_CACHE = false;
    }

    private static final boolean DISABLE_ACC_CHECKING;

    static {
        DISABLE_ACC_CHECKING = false;
    }

    private static final boolean DISABLE_JAR_CHECKING;

    static {
        DISABLE_JAR_CHECKING = false;
    }

    static final java.lang.String JAVA_VERSION;

    static {
        JAVA_VERSION = null;
    }

    static final java.lang.String USER_AGENT_JAVA_VERSION = "UA-Java-Version";

    private final java.security.AccessControlContext acc;

    {
        acc = null;
    }

    private boolean closed = false;

    private java.net.URLStreamHandler jarHandler;

    @UnsupportedAppUsage
    java.util.HashMap<java.lang.String, sun.misc.URLClassPath.Loader> lmap;

    @UnsupportedAppUsage
    java.util.ArrayList<sun.misc.URLClassPath.Loader> loaders;

    private static volatile boolean lookupCacheEnabled = false;

    private java.lang.ClassLoader lookupCacheLoader;

    private java.net.URL[] lookupCacheURLs;

    private java.util.ArrayList<java.net.URL> path;

    @UnsupportedAppUsage java.util.Stack<java.net.URL> urls;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class FileLoader extends sun.misc.URLClassPath.Loader {

        FileLoader(java.net.URL url) throws java.io.IOException {
            super(null);
            throw new RuntimeException("Stub!");
        }

        java.net.URL findResource(java.lang.String name, boolean check) {
            throw new RuntimeException("Stub!");
        }

        sun.misc.Resource getResource(java.lang.String name, boolean check) {
            throw new RuntimeException("Stub!");
        }

        private java.io.File dir;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class JarLoader extends sun.misc.URLClassPath.Loader {

        JarLoader(
                java.net.URL url,
                java.net.URLStreamHandler jarHandler,
                java.util.HashMap<java.lang.String, sun.misc.URLClassPath.Loader> loaderMap,
                java.security.AccessControlContext acc)
                throws java.io.IOException {
            super(null);
            throw new RuntimeException("Stub!");
        }

        public void close() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        @UnsupportedAppUsage
        java.util.jar.JarFile getJarFile() {
            throw new RuntimeException("Stub!");
        }

        private boolean isOptimizable(java.net.URL url) {
            throw new RuntimeException("Stub!");
        }

        private void ensureOpen() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        static java.util.jar.JarFile checkJar(java.util.jar.JarFile jar)
                throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private java.util.jar.JarFile getJarFile(java.net.URL url) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        sun.misc.JarIndex getIndex() {
            throw new RuntimeException("Stub!");
        }

        sun.misc.Resource checkResource(
                java.lang.String name, boolean check, java.util.jar.JarEntry entry) {
            throw new RuntimeException("Stub!");
        }

        boolean validIndex(java.lang.String name) {
            throw new RuntimeException("Stub!");
        }

        java.net.URL findResource(java.lang.String name, boolean check) {
            throw new RuntimeException("Stub!");
        }

        sun.misc.Resource getResource(java.lang.String name, boolean check) {
            throw new RuntimeException("Stub!");
        }

        sun.misc.Resource getResource(
                java.lang.String name, boolean check, java.util.Set<java.lang.String> visited) {
            throw new RuntimeException("Stub!");
        }

        java.net.URL[] getClassPath() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private void parseExtensionsDependencies() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private java.net.URL[] parseClassPath(java.net.URL base, java.lang.String value)
                throws java.net.MalformedURLException {
            throw new RuntimeException("Stub!");
        }

        private final java.security.AccessControlContext acc;

        {
            acc = null;
        }

        private boolean closed = false;

        private final java.net.URL csu;

        {
            csu = null;
        }

        private java.net.URLStreamHandler handler;

        private sun.misc.JarIndex index;

        private java.util.jar.JarFile jar;

        private final java.util.HashMap<java.lang.String, sun.misc.URLClassPath.Loader> lmap;

        {
            lmap = null;
        }

        private sun.misc.MetaIndex metaIndex;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class Loader implements java.io.Closeable {

        Loader(java.net.URL url) {
            throw new RuntimeException("Stub!");
        }

        java.net.URL getBaseURL() {
            throw new RuntimeException("Stub!");
        }

        java.net.URL findResource(java.lang.String name, boolean check) {
            throw new RuntimeException("Stub!");
        }

        sun.misc.Resource getResource(java.lang.String name, boolean check) {
            throw new RuntimeException("Stub!");
        }

        sun.misc.Resource getResource(java.lang.String name) {
            throw new RuntimeException("Stub!");
        }

        public void close() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        java.net.URL[] getClassPath() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private final java.net.URL base;

        {
            base = null;
        }

        private java.util.jar.JarFile jarfile;
    }
}
