/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class AlgorithmId implements java.io.Serializable, sun.security.util.DerEncoder {

    @Deprecated
    @UnsupportedAppUsage
    public AlgorithmId() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public AlgorithmId(sun.security.util.ObjectIdentifier oid) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public AlgorithmId(
            sun.security.util.ObjectIdentifier oid, java.security.AlgorithmParameters algparams) {
        throw new RuntimeException("Stub!");
    }

    private AlgorithmId(sun.security.util.ObjectIdentifier oid, sun.security.util.DerValue params)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void decodeParams() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public final void encode(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public void derEncode(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public final byte[] encode() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public final sun.security.util.ObjectIdentifier getOID() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public java.security.AlgorithmParameters getParameters() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public byte[] getEncodedParams() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public boolean equals(sun.security.x509.AlgorithmId other) {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object other) {
        throw new RuntimeException("Stub!");
    }

    public final boolean equals(sun.security.util.ObjectIdentifier id) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.String paramsToString() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public static sun.security.x509.AlgorithmId parse(sun.security.util.DerValue val)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    @UnsupportedAppUsage
    public static sun.security.x509.AlgorithmId getAlgorithmId(java.lang.String algname)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public static sun.security.x509.AlgorithmId get(java.lang.String algname)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.x509.AlgorithmId get(java.security.AlgorithmParameters algparams)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    private static sun.security.util.ObjectIdentifier algOID(java.lang.String name)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static void reinitializeMappingTableLocked() {
        throw new RuntimeException("Stub!");
    }

    private static sun.security.util.ObjectIdentifier oid(int... values) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String makeSigAlg(java.lang.String digAlg, java.lang.String encAlg) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public static java.lang.String getEncAlgFromSigAlg(java.lang.String signatureAlgorithm) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public static java.lang.String getDigAlgFromSigAlg(java.lang.String signatureAlgorithm) {
        throw new RuntimeException("Stub!");
    }

    public static final sun.security.util.ObjectIdentifier AES_oid;

    static {
        AES_oid = null;
    }

    private static final int[] DH_PKIX_data;

    static {
        DH_PKIX_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier DH_PKIX_oid;

    static {
        DH_PKIX_oid = null;
    }

    private static final int[] DH_data;

    static {
        DH_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier DH_oid;

    static {
        DH_oid = null;
    }

    private static final int[] DSA_OIW_data;

    static {
        DSA_OIW_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier DSA_OIW_oid;

    static {
        DSA_OIW_oid = null;
    }

    private static final int[] DSA_PKIX_data;

    static {
        DSA_PKIX_data = new int[0];
    }

    @UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier DSA_oid;

    static {
        DSA_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier ECDH_oid;

    static {
        ECDH_oid = null;
    }

    @UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier EC_oid;

    static {
        EC_oid = null;
    }

    @UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier MD2_oid;

    static {
        MD2_oid = null;
    }

    @UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier MD5_oid;

    static {
        MD5_oid = null;
    }

    private static final int[] RSAEncryption_data;

    static {
        RSAEncryption_data = new int[0];
    }

    @UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier RSAEncryption_oid;

    static {
        RSAEncryption_oid = null;
    }

    private static final int[] RSA_data;

    static {
        RSA_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier RSA_oid;

    static {
        RSA_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier SHA224_oid;

    static {
        SHA224_oid = null;
    }

    @UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier SHA256_oid;

    static {
        SHA256_oid = null;
    }

    @UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier SHA384_oid;

    static {
        SHA384_oid = null;
    }

    @UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier SHA512_oid;

    static {
        SHA512_oid = null;
    }

    @UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier SHA_oid;

    static {
        SHA_oid = null;
    }

    private java.security.AlgorithmParameters algParams;

    private sun.security.util.ObjectIdentifier algid;

    private boolean constructedFromDer = true;

    private static final int[] dsaWithSHA1_PKIX_data;

    static {
        dsaWithSHA1_PKIX_data = new int[0];
    }

    private static int initOidTableVersion = -1; // 0xffffffff

    private static final int[] md2WithRSAEncryption_data;

    static {
        md2WithRSAEncryption_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier md2WithRSAEncryption_oid;

    static {
        md2WithRSAEncryption_oid = null;
    }

    private static final int[] md5WithRSAEncryption_data;

    static {
        md5WithRSAEncryption_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier md5WithRSAEncryption_oid;

    static {
        md5WithRSAEncryption_oid = null;
    }

    private static final java.util.Map<sun.security.util.ObjectIdentifier, java.lang.String>
            nameTable;

    static {
        nameTable = null;
    }

    private static final java.util.Map<java.lang.String, sun.security.util.ObjectIdentifier>
            oidTable;

    static {
        oidTable = null;
    }

    @UnsupportedAppUsage
    protected sun.security.util.DerValue params;

    public static final sun.security.util.ObjectIdentifier pbeWithMD5AndDES_oid;

    static {
        pbeWithMD5AndDES_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier pbeWithMD5AndRC2_oid;

    static {
        pbeWithMD5AndRC2_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier pbeWithSHA1AndDES_oid;

    static {
        pbeWithSHA1AndDES_oid = null;
    }

    public static sun.security.util.ObjectIdentifier pbeWithSHA1AndDESede_oid;

    public static sun.security.util.ObjectIdentifier pbeWithSHA1AndRC2_40_oid;

    public static final sun.security.util.ObjectIdentifier pbeWithSHA1AndRC2_oid;

    static {
        pbeWithSHA1AndRC2_oid = null;
    }

    private static final long serialVersionUID = 7205873507486557157L; // 0x640067c6d62263e5L

    private static final int[] sha1WithDSA_OIW_data;

    static {
        sha1WithDSA_OIW_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier sha1WithDSA_OIW_oid;

    static {
        sha1WithDSA_OIW_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier sha1WithDSA_oid;

    static {
        sha1WithDSA_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier sha1WithECDSA_oid;

    static {
        sha1WithECDSA_oid = null;
    }

    private static final int[] sha1WithRSAEncryption_OIW_data;

    static {
        sha1WithRSAEncryption_OIW_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier sha1WithRSAEncryption_OIW_oid;

    static {
        sha1WithRSAEncryption_OIW_oid = null;
    }

    private static final int[] sha1WithRSAEncryption_data;

    static {
        sha1WithRSAEncryption_data = new int[0];
    }

    @UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier sha1WithRSAEncryption_oid;

    static {
        sha1WithRSAEncryption_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier sha224WithDSA_oid;

    static {
        sha224WithDSA_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier sha224WithECDSA_oid;

    static {
        sha224WithECDSA_oid = null;
    }

    private static final int[] sha224WithRSAEncryption_data;

    static {
        sha224WithRSAEncryption_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier sha224WithRSAEncryption_oid;

    static {
        sha224WithRSAEncryption_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier sha256WithDSA_oid;

    static {
        sha256WithDSA_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier sha256WithECDSA_oid;

    static {
        sha256WithECDSA_oid = null;
    }

    private static final int[] sha256WithRSAEncryption_data;

    static {
        sha256WithRSAEncryption_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier sha256WithRSAEncryption_oid;

    static {
        sha256WithRSAEncryption_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier sha384WithECDSA_oid;

    static {
        sha384WithECDSA_oid = null;
    }

    private static final int[] sha384WithRSAEncryption_data;

    static {
        sha384WithRSAEncryption_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier sha384WithRSAEncryption_oid;

    static {
        sha384WithRSAEncryption_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier sha512WithECDSA_oid;

    static {
        sha512WithECDSA_oid = null;
    }

    private static final int[] sha512WithRSAEncryption_data;

    static {
        sha512WithRSAEncryption_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier sha512WithRSAEncryption_oid;

    static {
        sha512WithRSAEncryption_oid = null;
    }

    private static final int[] shaWithDSA_OIW_data;

    static {
        shaWithDSA_OIW_data = new int[0];
    }

    public static final sun.security.util.ObjectIdentifier shaWithDSA_OIW_oid;

    static {
        shaWithDSA_OIW_oid = null;
    }

    public static final sun.security.util.ObjectIdentifier specifiedWithECDSA_oid;

    static {
        specifiedWithECDSA_oid = null;
    }
}
