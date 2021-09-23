/*
 * Copyright (c) 2002, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class CRLDistributionPointsExtension extends sun.security.x509.Extension
        implements sun.security.x509.CertAttrSet<java.lang.String> {

    public CRLDistributionPointsExtension(
            java.util.List<sun.security.x509.DistributionPoint> distributionPoints)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public CRLDistributionPointsExtension(
            boolean isCritical,
            java.util.List<sun.security.x509.DistributionPoint> distributionPoints)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected CRLDistributionPointsExtension(
            sun.security.util.ObjectIdentifier extensionId,
            boolean isCritical,
            java.util.List<sun.security.x509.DistributionPoint> distributionPoints,
            java.lang.String extensionName)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public CRLDistributionPointsExtension(java.lang.Boolean critical, java.lang.Object value)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected CRLDistributionPointsExtension(
            sun.security.util.ObjectIdentifier extensionId,
            java.lang.Boolean critical,
            java.lang.Object value,
            java.lang.String extensionName)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
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

    public void set(java.lang.String name, java.lang.Object obj) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.List<sun.security.x509.DistributionPoint> get(java.lang.String name)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void delete(java.lang.String name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<java.lang.String> getElements() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    private void encodeThis() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public static final java.lang.String IDENT = "x509.info.extensions.CRLDistributionPoints";

    public static final java.lang.String NAME = "CRLDistributionPoints";

    public static final java.lang.String POINTS = "points";

    private java.util.List<sun.security.x509.DistributionPoint> distributionPoints;

    private java.lang.String extensionName;
}
