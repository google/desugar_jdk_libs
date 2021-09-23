/*
 * Copyright (c) 1999, 2016, Oracle and/or its affiliates. All rights reserved.
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

import java.io.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class JarIndex {

    public JarIndex() {
        throw new RuntimeException("Stub!");
    }

    public JarIndex(java.io.InputStream is) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public JarIndex(java.lang.String[] files) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public static sun.misc.JarIndex getJarIndex(java.util.jar.JarFile jar)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public static sun.misc.JarIndex getJarIndex(
            java.util.jar.JarFile jar, sun.misc.MetaIndex metaIndex) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String[] getJarFiles() {
        throw new RuntimeException("Stub!");
    }

    private void addToList(
            java.lang.String key,
            java.lang.String value,
            java.util.HashMap<java.lang.String, java.util.LinkedList<java.lang.String>> t) {
        throw new RuntimeException("Stub!");
    }

    public java.util.LinkedList<java.lang.String> get(java.lang.String fileName) {
        throw new RuntimeException("Stub!");
    }

    public void add(java.lang.String fileName, java.lang.String jarName) {
        throw new RuntimeException("Stub!");
    }

    private void addMapping(java.lang.String jarItem, java.lang.String jarName) {
        throw new RuntimeException("Stub!");
    }

    private void parseJars(java.lang.String[] files) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void write(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void read(java.io.InputStream is) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void merge(sun.misc.JarIndex toIndex, java.lang.String path) {
        throw new RuntimeException("Stub!");
    }

    public static final java.lang.String INDEX_NAME = "META-INF/INDEX.LIST";

    private java.util.HashMap<java.lang.String, java.util.LinkedList<java.lang.String>> indexMap;

    private java.lang.String[] jarFiles;

    private java.util.HashMap<java.lang.String, java.util.LinkedList<java.lang.String>> jarMap;

    private static final boolean metaInfFilenames;

    static {
        metaInfFilenames = false;
    }
}
