/*
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

package sun.security.x509;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class URIName implements sun.security.x509.GeneralNameInterface {

    public URIName(sun.security.util.DerValue derValue) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public URIName(java.lang.String name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    URIName(java.net.URI uri, java.lang.String host, sun.security.x509.DNSName hostDNS) {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.x509.URIName nameConstraint(sun.security.util.DerValue value)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int getType() {
        throw new RuntimeException("Stub!");
    }

    public void encode(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public java.net.URI getURI() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.lang.String getScheme() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getHost() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object getHostObject() {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public int constrains(sun.security.x509.GeneralNameInterface inputName)
            throws java.lang.UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }

    public int subtreeDepth() throws java.lang.UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String host;

    private sun.security.x509.DNSName hostDNS;

    private sun.security.x509.IPAddressName hostIP;

    private java.net.URI uri;
}
