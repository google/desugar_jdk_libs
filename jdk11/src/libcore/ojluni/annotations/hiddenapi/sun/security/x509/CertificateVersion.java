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
public class CertificateVersion implements sun.security.x509.CertAttrSet<java.lang.String> {

    public CertificateVersion() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public CertificateVersion(int version) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public CertificateVersion(sun.security.util.DerInputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public CertificateVersion(java.io.InputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public CertificateVersion(sun.security.util.DerValue val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private int getVersion() {
        throw new RuntimeException("Stub!");
    }

    private void construct(sun.security.util.DerValue derVal) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public void encode(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void set(java.lang.String name, java.lang.Object obj) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Integer get(java.lang.String name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void delete(java.lang.String name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<java.lang.String> getElements() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public int compare(int vers) {
        throw new RuntimeException("Stub!");
    }

    public static final java.lang.String IDENT = "x509.info.version";

    public static final java.lang.String NAME = "version";

    public static final int V1 = 0; // 0x0

    public static final int V2 = 1; // 0x1

    public static final int V3 = 2; // 0x2

    public static final java.lang.String VERSION = "number";

    int version = 0; // 0x0
}
