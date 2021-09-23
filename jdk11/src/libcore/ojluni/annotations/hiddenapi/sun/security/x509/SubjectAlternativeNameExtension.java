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
public class SubjectAlternativeNameExtension extends sun.security.x509.Extension
        implements sun.security.x509.CertAttrSet<java.lang.String> {

    public SubjectAlternativeNameExtension(sun.security.x509.GeneralNames names)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public SubjectAlternativeNameExtension(
            java.lang.Boolean critical, sun.security.x509.GeneralNames names)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public SubjectAlternativeNameExtension() {
        throw new RuntimeException("Stub!");
    }

    public SubjectAlternativeNameExtension(java.lang.Boolean critical, java.lang.Object value)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void encodeThis() throws java.io.IOException {
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

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public sun.security.x509.GeneralNames get(java.lang.String name) throws java.io.IOException {
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

    public static final java.lang.String IDENT = "x509.info.extensions.SubjectAlternativeName";

    public static final java.lang.String NAME = "SubjectAlternativeName";

    public static final java.lang.String SUBJECT_NAME = "subject_name";

    sun.security.x509.GeneralNames names;
}
