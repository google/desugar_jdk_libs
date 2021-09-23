/*
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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
public final class AccessDescription {

    public AccessDescription(
            sun.security.util.ObjectIdentifier accessMethod,
            sun.security.x509.GeneralName accessLocation) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public AccessDescription(sun.security.util.DerValue derValue) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.ObjectIdentifier getAccessMethod() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.x509.GeneralName getAccessLocation() {
        throw new RuntimeException("Stub!");
    }

    public void encode(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public static final sun.security.util.ObjectIdentifier Ad_CAISSUERS_Id;

    static {
        Ad_CAISSUERS_Id = null;
    }

    public static final sun.security.util.ObjectIdentifier Ad_CAREPOSITORY_Id;

    static {
        Ad_CAREPOSITORY_Id = null;
    }

    public static final sun.security.util.ObjectIdentifier Ad_OCSP_Id;

    static {
        Ad_OCSP_Id = null;
    }

    public static final sun.security.util.ObjectIdentifier Ad_TIMESTAMPING_Id;

    static {
        Ad_TIMESTAMPING_Id = null;
    }

    private sun.security.x509.GeneralName accessLocation;

    private sun.security.util.ObjectIdentifier accessMethod;

    private int myhash = -1; // 0xffffffff
}
