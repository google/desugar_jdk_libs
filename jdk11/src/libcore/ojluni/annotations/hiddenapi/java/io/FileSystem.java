/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
abstract class FileSystem {

    FileSystem() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public abstract char getSeparator();

    @UnsupportedAppUsage
    public abstract char getPathSeparator();

    @UnsupportedAppUsage
    public abstract java.lang.String normalize(java.lang.String path);

    @UnsupportedAppUsage
    public abstract int prefixLength(java.lang.String path);

    @UnsupportedAppUsage
    public abstract java.lang.String resolve(java.lang.String parent, java.lang.String child);

    @UnsupportedAppUsage
    public abstract java.lang.String getDefaultParent();

    @UnsupportedAppUsage
    public abstract java.lang.String fromURIPath(java.lang.String path);

    @UnsupportedAppUsage
    public abstract boolean isAbsolute(java.io.File f);

    @UnsupportedAppUsage
    public abstract java.lang.String resolve(java.io.File f);

    @UnsupportedAppUsage
    public abstract java.lang.String canonicalize(java.lang.String path) throws java.io.IOException;

    @UnsupportedAppUsage
    public abstract int getBooleanAttributes(java.io.File f);

    @UnsupportedAppUsage
    public abstract boolean checkAccess(java.io.File f, int access);

    @UnsupportedAppUsage
    public abstract boolean setPermission(
            java.io.File f, int access, boolean enable, boolean owneronly);

    @UnsupportedAppUsage
    public abstract long getLastModifiedTime(java.io.File f);

    @UnsupportedAppUsage
    public abstract long getLength(java.io.File f);

    @UnsupportedAppUsage
    public abstract boolean createFileExclusively(java.lang.String pathname)
            throws java.io.IOException;

    @UnsupportedAppUsage
    public abstract boolean delete(java.io.File f);

    @UnsupportedAppUsage
    public abstract java.lang.String[] list(java.io.File f);

    @UnsupportedAppUsage
    public abstract boolean createDirectory(java.io.File f);

    @UnsupportedAppUsage
    public abstract boolean rename(java.io.File f1, java.io.File f2);

    @UnsupportedAppUsage
    public abstract boolean setLastModifiedTime(java.io.File f, long time);

    @UnsupportedAppUsage
    public abstract boolean setReadOnly(java.io.File f);

    @UnsupportedAppUsage
    public abstract java.io.File[] listRoots();

    @UnsupportedAppUsage
    public abstract long getSpace(java.io.File f, int t);

    @UnsupportedAppUsage
    public abstract int compare(java.io.File f1, java.io.File f2);

    @UnsupportedAppUsage
    public abstract int hashCode(java.io.File f);

    private static boolean getBooleanProperty(java.lang.String prop, boolean defaultVal) {
        throw new RuntimeException("Stub!");
    }

    public static final int ACCESS_EXECUTE = 1; // 0x1

    public static final int ACCESS_OK = 8; // 0x8

    public static final int ACCESS_READ = 4; // 0x4

    public static final int ACCESS_WRITE = 2; // 0x2

    public static final int BA_DIRECTORY = 4; // 0x4

    public static final int BA_EXISTS = 1; // 0x1

    public static final int BA_HIDDEN = 8; // 0x8

    public static final int BA_REGULAR = 2; // 0x2

    public static final int SPACE_FREE = 1; // 0x1

    public static final int SPACE_TOTAL = 0; // 0x0

    public static final int SPACE_USABLE = 2; // 0x2

    static boolean useCanonCaches = false;

    static boolean useCanonPrefixCache = false;
}
