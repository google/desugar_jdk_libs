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
public class CRLNumberExtension extends sun.security.x509.Extension
        implements sun.security.x509.CertAttrSet<java.lang.String> {

    public CRLNumberExtension(int crlNum) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public CRLNumberExtension(java.math.BigInteger crlNum) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected CRLNumberExtension(
            sun.security.util.ObjectIdentifier extensionId,
            boolean isCritical,
            java.math.BigInteger crlNum,
            java.lang.String extensionName,
            java.lang.String extensionLabel)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public CRLNumberExtension(java.lang.Boolean critical, java.lang.Object value)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected CRLNumberExtension(
            sun.security.util.ObjectIdentifier extensionId,
            java.lang.Boolean critical,
            java.lang.Object value,
            java.lang.String extensionName,
            java.lang.String extensionLabel)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    private void encodeThis() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void set(java.lang.String name, java.lang.Object obj) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public java.math.BigInteger get(java.lang.String name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void delete(java.lang.String name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public void encode(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void encode(
            java.io.OutputStream out,
            sun.security.util.ObjectIdentifier extensionId,
            boolean isCritical)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<java.lang.String> getElements() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    private static final java.lang.String LABEL = "CRL Number";

    public static final java.lang.String NAME = "CRLNumber";

    public static final java.lang.String NUMBER = "value";

    private java.math.BigInteger crlNumber;

    private java.lang.String extensionLabel;

    private java.lang.String extensionName;
}
