/*
 * Copyright (C) 2014 The Android Open Source Project
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

import java.util.*;
import sun.security.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class OIDMap {

    private OIDMap() {
        throw new RuntimeException("Stub!");
    }

    private static void addInternal(
            java.lang.String name, sun.security.util.ObjectIdentifier oid, java.lang.Class clazz) {
        throw new RuntimeException("Stub!");
    }

    public static void addAttribute(
            java.lang.String name, java.lang.String oid, java.lang.Class<?> clazz)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String getName(sun.security.util.ObjectIdentifier oid) {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.util.ObjectIdentifier getOID(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.Class<?> getClass(java.lang.String name)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static java.lang.Class<?> getClass(sun.security.util.ObjectIdentifier oid)
            throws java.security.cert.CertificateException {
        throw new RuntimeException("Stub!");
    }

    private static final java.lang.String AUTH_INFO_ACCESS =
            "x509.info.extensions.AuthorityInfoAccess";

    private static final java.lang.String AUTH_KEY_IDENTIFIER =
            "x509.info.extensions.AuthorityKeyIdentifier";

    private static final java.lang.String BASIC_CONSTRAINTS =
            "x509.info.extensions.BasicConstraints";

    private static final java.lang.String CERT_ISSUER = "x509.info.extensions.CertificateIssuer";

    private static final java.lang.String CERT_POLICIES =
            "x509.info.extensions.CertificatePolicies";

    private static final java.lang.String CRL_DIST_POINTS =
            "x509.info.extensions.CRLDistributionPoints";

    private static final java.lang.String CRL_NUMBER = "x509.info.extensions.CRLNumber";

    private static final java.lang.String CRL_REASON = "x509.info.extensions.CRLReasonCode";

    private static final java.lang.String DELTA_CRL_INDICATOR =
            "x509.info.extensions.DeltaCRLIndicator";

    private static final java.lang.String EXT_KEY_USAGE = "x509.info.extensions.ExtendedKeyUsage";

    private static final java.lang.String FRESHEST_CRL = "x509.info.extensions.FreshestCRL";

    private static final java.lang.String INHIBIT_ANY_POLICY =
            "x509.info.extensions.InhibitAnyPolicy";

    private static final java.lang.String ISSUER_ALT_NAME =
            "x509.info.extensions.IssuerAlternativeName";

    private static final java.lang.String ISSUING_DIST_POINT =
            "x509.info.extensions.IssuingDistributionPoint";

    private static final java.lang.String KEY_USAGE = "x509.info.extensions.KeyUsage";

    private static final java.lang.String NAME_CONSTRAINTS = "x509.info.extensions.NameConstraints";

    private static final java.lang.String NETSCAPE_CERT = "x509.info.extensions.NetscapeCertType";

    private static final int[] NetscapeCertType_data;

    static {
        NetscapeCertType_data = new int[0];
    }

    private static final java.lang.String OCSPNOCHECK = "x509.info.extensions.OCSPNoCheck";

    private static final java.lang.String POLICY_CONSTRAINTS =
            "x509.info.extensions.PolicyConstraints";

    private static final java.lang.String POLICY_MAPPINGS = "x509.info.extensions.PolicyMappings";

    private static final java.lang.String PRIVATE_KEY_USAGE =
            "x509.info.extensions.PrivateKeyUsage";

    private static final java.lang.String ROOT = "x509.info.extensions";

    private static final java.lang.String SUBJECT_INFO_ACCESS =
            "x509.info.extensions.SubjectInfoAccess";

    private static final java.lang.String SUB_ALT_NAME =
            "x509.info.extensions.SubjectAlternativeName";

    private static final java.lang.String SUB_KEY_IDENTIFIER =
            "x509.info.extensions.SubjectKeyIdentifier";

    @android.compat.annotation.UnsupportedAppUsage
    private static final java.util.Map<java.lang.String, sun.security.x509.OIDMap.OIDInfo> nameMap;

    static {
        nameMap = null;
    }

    @android.compat.annotation.UnsupportedAppUsage
    private static final java.util.Map<
                    sun.security.util.ObjectIdentifier, sun.security.x509.OIDMap.OIDInfo>
            oidMap;

    static {
        oidMap = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class OIDInfo {

        OIDInfo(
                java.lang.String name,
                sun.security.util.ObjectIdentifier oid,
                java.lang.Class<?> clazz) {
            throw new RuntimeException("Stub!");
        }

        java.lang.Class<?> getClazz() throws java.security.cert.CertificateException {
            throw new RuntimeException("Stub!");
        }

        @android.compat.annotation.UnsupportedAppUsage private volatile java.lang.Class<?> clazz;

        final java.lang.String name;

        {
            name = null;
        }

        final sun.security.util.ObjectIdentifier oid;

        {
            oid = null;
        }
    }
}
