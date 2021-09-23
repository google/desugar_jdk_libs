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

package sun.security.x509;

import sun.security.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class Extension implements java.security.cert.Extension {

    public Extension() {
        throw new RuntimeException("Stub!");
    }

    public Extension(sun.security.util.DerValue derVal) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public Extension(
            sun.security.util.ObjectIdentifier extensionId, boolean critical, byte[] extensionValue)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public Extension(sun.security.x509.Extension ext) {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.x509.Extension newExtension(
            sun.security.util.ObjectIdentifier extensionId,
            boolean critical,
            byte[] rawExtensionValue)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void encode(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void encode(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean isCritical() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.ObjectIdentifier getExtensionId() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getValue() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getExtensionValue() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getId() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object other) {
        throw new RuntimeException("Stub!");
    }

    protected boolean critical = false;

    protected sun.security.util.ObjectIdentifier extensionId;

    protected byte[] extensionValue;

    private static final int hashMagic = 31; // 0x1f
}
