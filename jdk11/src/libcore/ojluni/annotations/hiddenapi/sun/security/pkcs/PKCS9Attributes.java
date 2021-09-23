/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.pkcs;


@SuppressWarnings({"unchecked", "deprecation", "all"})
public class PKCS9Attributes {

    public PKCS9Attributes(
            sun.security.util.ObjectIdentifier[] permittedAttributes,
            sun.security.util.DerInputStream in)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public PKCS9Attributes(sun.security.util.DerInputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public PKCS9Attributes(sun.security.util.DerInputStream in, boolean ignoreUnsupportedAttributes)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public PKCS9Attributes(sun.security.pkcs.PKCS9Attribute[] attribs)
            throws java.io.IOException, java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    private byte[] decode(sun.security.util.DerInputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void encode(byte tag, java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private byte[] generateDerEncoding() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public byte[] getDerEncoding() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.pkcs.PKCS9Attribute getAttribute(sun.security.util.ObjectIdentifier oid) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public sun.security.pkcs.PKCS9Attribute getAttribute(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public sun.security.pkcs.PKCS9Attribute[] getAttributes() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.lang.Object getAttributeValue(sun.security.util.ObjectIdentifier oid)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object getAttributeValue(java.lang.String name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    static sun.security.util.DerEncoder[] castToDerEncoder(java.lang.Object[] objs) {
        throw new RuntimeException("Stub!");
    }

    private final java.util.Hashtable<
                    sun.security.util.ObjectIdentifier, sun.security.pkcs.PKCS9Attribute>
            attributes;

    {
        attributes = null;
    }

    private final byte[] derEncoding;

    {
        derEncoding = new byte[0];
    }

    private boolean ignoreUnsupportedAttributes = false;

    private final java.util.Hashtable<
                    sun.security.util.ObjectIdentifier, sun.security.util.ObjectIdentifier>
            permittedAttributes;

    {
        permittedAttributes = null;
    }
}
