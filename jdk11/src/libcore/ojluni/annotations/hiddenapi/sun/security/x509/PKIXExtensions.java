/*
 * Copyright (c) 1997, 2009, Oracle and/or its affiliates. All rights reserved.
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

import java.io.*;
import sun.security.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class PKIXExtensions {

    public PKIXExtensions() {
        throw new RuntimeException("Stub!");
    }

    public static final sun.security.util.ObjectIdentifier AuthInfoAccess_Id;

    static {
        AuthInfoAccess_Id = null;
    }

    private static final int[] AuthInfoAccess_data;

    static {
        AuthInfoAccess_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier AuthorityKey_Id;

    static {
        AuthorityKey_Id = null;
    }

    private static final int[] AuthorityKey_data;

    static {
        AuthorityKey_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier BasicConstraints_Id;

    static {
        BasicConstraints_Id = null;
    }

    private static final int[] BasicConstraints_data;

    static {
        BasicConstraints_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier CRLDistributionPoints_Id;

    static {
        CRLDistributionPoints_Id = null;
    }

    private static final int[] CRLDistributionPoints_data;

    static {
        CRLDistributionPoints_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier CRLNumber_Id;

    static {
        CRLNumber_Id = null;
    }

    private static final int[] CRLNumber_data;

    static {
        CRLNumber_data = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier CertificateIssuer_Id;

    static {
        CertificateIssuer_Id = null;
    }

    private static final int[] CertificateIssuer_data;

    static {
        CertificateIssuer_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier CertificatePolicies_Id;

    static {
        CertificatePolicies_Id = null;
    }

    private static final int[] CertificatePolicies_data;

    static {
        CertificatePolicies_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier DeltaCRLIndicator_Id;

    static {
        DeltaCRLIndicator_Id = null;
    }

    private static final int[] DeltaCRLIndicator_data;

    static {
        DeltaCRLIndicator_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier ExtendedKeyUsage_Id;

    static {
        ExtendedKeyUsage_Id = null;
    }

    private static final int[] ExtendedKeyUsage_data;

    static {
        ExtendedKeyUsage_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier FreshestCRL_Id;

    static {
        FreshestCRL_Id = null;
    }

    private static final int[] FreshestCRL_data;

    static {
        FreshestCRL_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier HoldInstructionCode_Id;

    static {
        HoldInstructionCode_Id = null;
    }

    private static final int[] HoldInstructionCode_data;

    static {
        HoldInstructionCode_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier InhibitAnyPolicy_Id;

    static {
        InhibitAnyPolicy_Id = null;
    }

    private static final int[] InhibitAnyPolicy_data;

    static {
        InhibitAnyPolicy_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier InvalidityDate_Id;

    static {
        InvalidityDate_Id = null;
    }

    private static final int[] InvalidityDate_data;

    static {
        InvalidityDate_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier IssuerAlternativeName_Id;

    static {
        IssuerAlternativeName_Id = null;
    }

    private static final int[] IssuerAlternativeName_data;

    static {
        IssuerAlternativeName_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier IssuingDistributionPoint_Id;

    static {
        IssuingDistributionPoint_Id = null;
    }

    private static final int[] IssuingDistributionPoint_data;

    static {
        IssuingDistributionPoint_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier KeyUsage_Id;

    static {
        KeyUsage_Id = null;
    }

    private static final int[] KeyUsage_data;

    static {
        KeyUsage_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier NameConstraints_Id;

    static {
        NameConstraints_Id = null;
    }

    private static final int[] NameConstraints_data;

    static {
        NameConstraints_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier OCSPNoCheck_Id;

    static {
        OCSPNoCheck_Id = null;
    }

    private static final int[] OCSPNoCheck_data;

    static {
        OCSPNoCheck_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier PolicyConstraints_Id;

    static {
        PolicyConstraints_Id = null;
    }

    private static final int[] PolicyConstraints_data;

    static {
        PolicyConstraints_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier PolicyMappings_Id;

    static {
        PolicyMappings_Id = null;
    }

    private static final int[] PolicyMappings_data;

    static {
        PolicyMappings_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier PrivateKeyUsage_Id;

    static {
        PrivateKeyUsage_Id = null;
    }

    private static final int[] PrivateKeyUsage_data;

    static {
        PrivateKeyUsage_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier ReasonCode_Id;

    static {
        ReasonCode_Id = null;
    }

    private static final int[] ReasonCode_data;

    static {
        ReasonCode_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier SubjectAlternativeName_Id;

    static {
        SubjectAlternativeName_Id = null;
    }

    private static final int[] SubjectAlternativeName_data;

    static {
        SubjectAlternativeName_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier SubjectDirectoryAttributes_Id;

    static {
        SubjectDirectoryAttributes_Id = null;
    }

    private static final int[] SubjectDirectoryAttributes_data;

    static {
        SubjectDirectoryAttributes_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier SubjectInfoAccess_Id;

    static {
        SubjectInfoAccess_Id = null;
    }

    private static final int[] SubjectInfoAccess_data;

    static {
        SubjectInfoAccess_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier SubjectKey_Id;

    static {
        SubjectKey_Id = null;
    }

    private static final int[] SubjectKey_data;

    static {
        SubjectKey_data = new int[0];
    }
}
