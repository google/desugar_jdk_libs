/*
 * Copyright (C) 2014 The Android Open Source Project
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

package java.util.jar;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class JarFile extends java.util.zip.ZipFile {

    public JarFile(java.lang.String name) throws java.io.IOException {
        super((java.lang.String) null);
        throw new RuntimeException("Stub!");
    }

    public JarFile(java.lang.String name, boolean verify) throws java.io.IOException {
        super((java.lang.String) null);
        throw new RuntimeException("Stub!");
    }

    public JarFile(java.io.File file) throws java.io.IOException {
        super((java.lang.String) null);
        throw new RuntimeException("Stub!");
    }

    public JarFile(java.io.File file, boolean verify) throws java.io.IOException {
        super((java.lang.String) null);
        throw new RuntimeException("Stub!");
    }

    public JarFile(java.io.File file, boolean verify, int mode) throws java.io.IOException {
        super((java.lang.String) null);
        throw new RuntimeException("Stub!");
    }

    public java.util.jar.Manifest getManifest() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private synchronized java.util.jar.Manifest getManifestFromReference()
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private native java.lang.String[] getMetaInfEntryNames();

    public java.util.jar.JarEntry getJarEntry(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.util.zip.ZipEntry getEntry(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<java.util.jar.JarEntry> entries() {
        throw new RuntimeException("Stub!");
    }

    public java.util.stream.Stream<java.util.jar.JarEntry> stream() {
        throw new RuntimeException("Stub!");
    }

    private void maybeInstantiateVerifier() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void initializeVerifier() {
        throw new RuntimeException("Stub!");
    }

    private byte[] getBytes(java.util.zip.ZipEntry ze) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.io.InputStream getInputStream(java.util.zip.ZipEntry ze)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private synchronized java.util.jar.JarEntry getManEntry() {
        throw new RuntimeException("Stub!");
    }

    public boolean hasClassPathAttribute() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private boolean match(char[] src, byte[] b, int[] lastOcc, int[] optoSft) {
        throw new RuntimeException("Stub!");
    }

    private void checkForSpecialAttributes() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    java.util.jar.JarEntry newEntry(java.util.zip.ZipEntry ze) {
        throw new RuntimeException("Stub!");
    }

    private static final char[] CLASSPATH_CHARS;

    static {
        CLASSPATH_CHARS = new char[0];
    }

    private static final int[] CLASSPATH_LASTOCC;

    static {
        CLASSPATH_LASTOCC = new int[0];
    }

    private static final int[] CLASSPATH_OPTOSFT;

    static {
        CLASSPATH_OPTOSFT = new int[0];
    }

    public static final java.lang.String MANIFEST_NAME = "META-INF/MANIFEST.MF";

    private volatile boolean hasCheckedSpecialAttributes;

    private boolean hasClassPathAttribute;

    private java.util.jar.JarVerifier jv;

    private boolean jvInitialized;

    private java.util.jar.JarEntry manEntry;

    @UnsupportedAppUsage
    private java.util.jar.Manifest manifest;

    private boolean verify;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class JarEntryIterator
            implements java.util.Enumeration<java.util.jar.JarEntry>,
                    java.util.Iterator<java.util.jar.JarEntry> {

        private JarEntryIterator() {
            throw new RuntimeException("Stub!");
        }

        public boolean hasNext() {
            throw new RuntimeException("Stub!");
        }

        public java.util.jar.JarEntry next() {
            throw new RuntimeException("Stub!");
        }

        public boolean hasMoreElements() {
            throw new RuntimeException("Stub!");
        }

        public java.util.jar.JarEntry nextElement() {
            throw new RuntimeException("Stub!");
        }

        final java.util.Enumeration<? extends java.util.zip.ZipEntry> e;

        {
            e = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class JarFileEntry extends java.util.jar.JarEntry {

        JarFileEntry(java.util.zip.ZipEntry ze) {
            super((java.lang.String) null);
            throw new RuntimeException("Stub!");
        }

        public java.util.jar.Attributes getAttributes() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public java.security.cert.Certificate[] getCertificates() {
            throw new RuntimeException("Stub!");
        }

        public java.security.CodeSigner[] getCodeSigners() {
            throw new RuntimeException("Stub!");
        }
    }
}
