/*
 * Copyright (c) 1998, 2015, Oracle and/or its affiliates. All rights reserved.
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
import sun.security.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class NetscapeCertTypeExtension extends sun.security.x509.Extension
        implements sun.security.x509.CertAttrSet<java.lang.String> {

    @android.compat.annotation.UnsupportedAppUsage
    public NetscapeCertTypeExtension(byte[] bitString) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public NetscapeCertTypeExtension(boolean[] bitString) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public NetscapeCertTypeExtension(java.lang.Boolean critical, java.lang.Object value)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public NetscapeCertTypeExtension() {
        throw new RuntimeException("Stub!");
    }

    private static int getPosition(java.lang.String name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void encodeThis() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private boolean isSet(int position) {
        throw new RuntimeException("Stub!");
    }

    private void set(int position, boolean val) {
        throw new RuntimeException("Stub!");
    }

    public void set(java.lang.String name, java.lang.Object obj) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public java.lang.Boolean get(java.lang.String name) throws java.io.IOException {
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

    public java.util.Enumeration<java.lang.String> getElements() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public boolean[] getKeyUsageMappedBits() {
        throw new RuntimeException("Stub!");
    }

    private static final int[] CertType_data;

    static {
        CertType_data = new int[0];
    }

    public static final java.lang.String IDENT = "x509.info.extensions.NetscapeCertType";

    public static final java.lang.String NAME = "NetscapeCertType";

    public static sun.security.util.ObjectIdentifier NetscapeCertType_Id;

    public static final java.lang.String OBJECT_SIGNING = "object_signing";

    public static final java.lang.String OBJECT_SIGNING_CA = "object_signing_ca";

    public static final java.lang.String SSL_CA = "ssl_ca";

    public static final java.lang.String SSL_CLIENT = "ssl_client";

    public static final java.lang.String SSL_SERVER = "ssl_server";

    public static final java.lang.String S_MIME = "s_mime";

    public static final java.lang.String S_MIME_CA = "s_mime_ca";

    private boolean[] bitString;

    private static final java.util.Vector<java.lang.String> mAttributeNames;

    static {
        mAttributeNames = null;
    }

    private static sun.security.x509.NetscapeCertTypeExtension.MapEntry[] mMapData;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class MapEntry {

        MapEntry(java.lang.String name, int position) {
            throw new RuntimeException("Stub!");
        }

        java.lang.String mName;

        int mPosition;
    }
}
