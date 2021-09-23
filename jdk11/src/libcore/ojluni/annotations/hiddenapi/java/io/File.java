/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class File implements java.io.Serializable, java.lang.Comparable<java.io.File> {

    private File(java.lang.String pathname, int prefixLength) {
        throw new RuntimeException("Stub!");
    }

    private File(java.lang.String child, java.io.File parent) {
        throw new RuntimeException("Stub!");
    }

    public File(java.lang.String pathname) {
        throw new RuntimeException("Stub!");
    }

    public File(java.lang.String parent, java.lang.String child) {
        throw new RuntimeException("Stub!");
    }

    public File(java.io.File parent, java.lang.String child) {
        throw new RuntimeException("Stub!");
    }

    public File(java.net.URI uri) {
        throw new RuntimeException("Stub!");
    }

    final boolean isInvalid() {
        throw new RuntimeException("Stub!");
    }

    int getPrefixLength() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getParent() {
        throw new RuntimeException("Stub!");
    }

    public java.io.File getParentFile() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getPath() {
        throw new RuntimeException("Stub!");
    }

    public boolean isAbsolute() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getAbsolutePath() {
        throw new RuntimeException("Stub!");
    }

    public java.io.File getAbsoluteFile() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getCanonicalPath() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.io.File getCanonicalFile() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String slashify(java.lang.String path, boolean isDirectory) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public java.net.URL toURL() throws java.net.MalformedURLException {
        throw new RuntimeException("Stub!");
    }

    public java.net.URI toURI() {
        throw new RuntimeException("Stub!");
    }

    public boolean canRead() {
        throw new RuntimeException("Stub!");
    }

    public boolean canWrite() {
        throw new RuntimeException("Stub!");
    }

    public boolean exists() {
        throw new RuntimeException("Stub!");
    }

    public boolean isDirectory() {
        throw new RuntimeException("Stub!");
    }

    public boolean isFile() {
        throw new RuntimeException("Stub!");
    }

    public boolean isHidden() {
        throw new RuntimeException("Stub!");
    }

    public long lastModified() {
        throw new RuntimeException("Stub!");
    }

    public long length() {
        throw new RuntimeException("Stub!");
    }

    public boolean createNewFile() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean delete() {
        throw new RuntimeException("Stub!");
    }

    public void deleteOnExit() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String[] list() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String[] list(java.io.FilenameFilter filter) {
        throw new RuntimeException("Stub!");
    }

    public java.io.File[] listFiles() {
        throw new RuntimeException("Stub!");
    }

    public java.io.File[] listFiles(java.io.FilenameFilter filter) {
        throw new RuntimeException("Stub!");
    }

    public java.io.File[] listFiles(java.io.FileFilter filter) {
        throw new RuntimeException("Stub!");
    }

    public boolean mkdir() {
        throw new RuntimeException("Stub!");
    }

    public boolean mkdirs() {
        throw new RuntimeException("Stub!");
    }

    public boolean renameTo(java.io.File dest) {
        throw new RuntimeException("Stub!");
    }

    public boolean setLastModified(long time) {
        throw new RuntimeException("Stub!");
    }

    public boolean setReadOnly() {
        throw new RuntimeException("Stub!");
    }

    public boolean setWritable(boolean writable, boolean ownerOnly) {
        throw new RuntimeException("Stub!");
    }

    public boolean setWritable(boolean writable) {
        throw new RuntimeException("Stub!");
    }

    public boolean setReadable(boolean readable, boolean ownerOnly) {
        throw new RuntimeException("Stub!");
    }

    public boolean setReadable(boolean readable) {
        throw new RuntimeException("Stub!");
    }

    public boolean setExecutable(boolean executable, boolean ownerOnly) {
        throw new RuntimeException("Stub!");
    }

    public boolean setExecutable(boolean executable) {
        throw new RuntimeException("Stub!");
    }

    public boolean canExecute() {
        throw new RuntimeException("Stub!");
    }

    public static java.io.File[] listRoots() {
        throw new RuntimeException("Stub!");
    }

    public long getTotalSpace() {
        throw new RuntimeException("Stub!");
    }

    public long getFreeSpace() {
        throw new RuntimeException("Stub!");
    }

    public long getUsableSpace() {
        throw new RuntimeException("Stub!");
    }

    public static java.io.File createTempFile(
            java.lang.String prefix, java.lang.String suffix, java.io.File directory)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public static java.io.File createTempFile(java.lang.String prefix, java.lang.String suffix)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int compareTo(java.io.File pathname) {
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

    private synchronized void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private synchronized void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.nio.file.Path toPath() {
        throw new RuntimeException("Stub!");
    }

    private static final long PATH_OFFSET;

    static {
        PATH_OFFSET = 0;
    }

    private static final long PREFIX_LENGTH_OFFSET;

    static {
        PREFIX_LENGTH_OFFSET = 0;
    }

    private static final sun.misc.Unsafe UNSAFE;

    static {
        UNSAFE = null;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private transient volatile java.nio.file.Path filePath;

    @UnsupportedAppUsage
    private static final java.io.FileSystem fs;

    static {
        fs = null;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private final java.lang.String path;

    {
        path = null;
    }

    public static final java.lang.String pathSeparator;

    static {
        pathSeparator = null;
    }

    public static final char pathSeparatorChar;

    static {
        pathSeparatorChar = 0;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private final transient int prefixLength;

    {
        prefixLength = 0;
    }

    public static final java.lang.String separator;

    static {
        separator = null;
    }

    public static final char separatorChar;

    static {
        separatorChar = 0;
    }

    private static final long serialVersionUID = 301077366599181567L; // 0x42da4450e0de4ffL

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private transient java.io.File.PathStatus status;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static enum PathStatus {
        INVALID,
        CHECKED;

        private PathStatus() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class TempDirectory {

        private TempDirectory() {
            throw new RuntimeException("Stub!");
        }

        static java.io.File generateFile(
                java.lang.String prefix, java.lang.String suffix, java.io.File dir)
                throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }
    }
}
