/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.util;

import java.security.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class ManifestDigester {

    @android.compat.annotation.UnsupportedAppUsage
    public ManifestDigester(byte[] bytes) {
        throw new RuntimeException("Stub!");
    }

    private boolean findSection(int offset, sun.security.util.ManifestDigester.Position pos) {
        throw new RuntimeException("Stub!");
    }

    private boolean isNameAttr(byte[] bytes, int start) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.ManifestDigester.Entry get(java.lang.String name, boolean oldStyle) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public byte[] manifestDigest(java.security.MessageDigest md) {
        throw new RuntimeException("Stub!");
    }

    public static final java.lang.String MF_MAIN_ATTRS = "Manifest-Main-Attributes";

    private java.util.HashMap<java.lang.String, sun.security.util.ManifestDigester.Entry> entries;

    private byte[] rawBytes;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static class Entry {

        public Entry(int offset, int length, int lengthWithBlankLine, byte[] rawBytes) {
            throw new RuntimeException("Stub!");
        }

        @android.compat.annotation.UnsupportedAppUsage
        public byte[] digest(java.security.MessageDigest md) {
            throw new RuntimeException("Stub!");
        }

        private void doOldStyle(
                java.security.MessageDigest md, byte[] bytes, int offset, int length) {
            throw new RuntimeException("Stub!");
        }

        @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        public byte[] digestWorkaround(java.security.MessageDigest md) {
            throw new RuntimeException("Stub!");
        }

        int length;

        int lengthWithBlankLine;

        int offset;

        boolean oldStyle;

        byte[] rawBytes;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class Position {

        Position() {
            throw new RuntimeException("Stub!");
        }

        int endOfFirstLine;

        int endOfSection;

        int startOfNext;
    }
}
