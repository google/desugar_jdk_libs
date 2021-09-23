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

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.IntraCoreApi
@libcore.api.Hide
@SuppressWarnings({"unchecked", "deprecation", "all"})
public class AlgorithmId implements java.io.Serializable {

@libcore.api.Hide
@Deprecated
public AlgorithmId() { throw new RuntimeException("Stub!"); }

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public AlgorithmId(sun.security.util.ObjectIdentifier oid) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public AlgorithmId(sun.security.util.ObjectIdentifier oid, java.security.AlgorithmParameters algparams) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
protected void decodeParams() throws java.io.IOException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public final void encode(DerOutputStream out) throws java.io.IOException { throw new RuntimeException("Stub!"); }

@libcore.api.IntraCoreApi
@libcore.api.Hide
public void derEncode(java.io.OutputStream out) throws java.io.IOException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public final byte[] encode() throws java.io.IOException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public final sun.security.util.ObjectIdentifier getOID() { throw new RuntimeException("Stub!"); }

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.IntraCoreApi
@libcore.api.Hide
public java.lang.String getName() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public java.security.AlgorithmParameters getParameters() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public byte[] getEncodedParams() throws java.io.IOException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public boolean equals(sun.security.x509.AlgorithmId other) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public boolean equals(java.lang.Object other) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public final boolean equals(sun.security.util.ObjectIdentifier id) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public int hashCode() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
protected java.lang.String paramsToString() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public java.lang.String toString() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static sun.security.x509.AlgorithmId parse(DerValue val) throws java.io.IOException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
@Deprecated
public static sun.security.x509.AlgorithmId getAlgorithmId(java.lang.String algname) throws java.security.NoSuchAlgorithmException { throw new RuntimeException("Stub!"); }

@libcore.api.IntraCoreApi
@libcore.api.Hide
public static sun.security.x509.AlgorithmId get(java.lang.String algname) throws java.security.NoSuchAlgorithmException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static sun.security.x509.AlgorithmId get(java.security.AlgorithmParameters algparams) throws java.security.NoSuchAlgorithmException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static java.lang.String makeSigAlg(java.lang.String digAlg, java.lang.String encAlg) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static java.lang.String getEncAlgFromSigAlg(java.lang.String signatureAlgorithm) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static java.lang.String getDigAlgFromSigAlg(java.lang.String signatureAlgorithm) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier AES_oid;
static { AES_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier DH_PKIX_oid;
static { DH_PKIX_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier DH_oid;
static { DH_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier DSA_OIW_oid;
static { DSA_OIW_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier DSA_oid;
static { DSA_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier ECDH_oid;
static { ECDH_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier EC_oid;
static { EC_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier MD2_oid;
static { MD2_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier MD5_oid;
static { MD5_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier RSAEncryption_oid;
static { RSAEncryption_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier RSA_oid;
static { RSA_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier SHA224_oid;
static { SHA224_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier SHA256_oid;
static { SHA256_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier SHA384_oid;
static { SHA384_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier SHA512_oid;
static { SHA512_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier SHA_oid;
static { SHA_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier md2WithRSAEncryption_oid;
static { md2WithRSAEncryption_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier md5WithRSAEncryption_oid;
static { md5WithRSAEncryption_oid = null; }

@libcore.api.Hide
protected DerValue params;

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier pbeWithMD5AndDES_oid;
static { pbeWithMD5AndDES_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier pbeWithMD5AndRC2_oid;
static { pbeWithMD5AndRC2_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier pbeWithSHA1AndDES_oid;
static { pbeWithSHA1AndDES_oid = null; }

@libcore.api.Hide
public static sun.security.util.ObjectIdentifier pbeWithSHA1AndDESede_oid;

@libcore.api.Hide
public static sun.security.util.ObjectIdentifier pbeWithSHA1AndRC2_40_oid;

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier pbeWithSHA1AndRC2_oid;
static { pbeWithSHA1AndRC2_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha1WithDSA_OIW_oid;
static { sha1WithDSA_OIW_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha1WithDSA_oid;
static { sha1WithDSA_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha1WithECDSA_oid;
static { sha1WithECDSA_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha1WithRSAEncryption_OIW_oid;
static { sha1WithRSAEncryption_OIW_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha1WithRSAEncryption_oid;
static { sha1WithRSAEncryption_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha224WithDSA_oid;
static { sha224WithDSA_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha224WithECDSA_oid;
static { sha224WithECDSA_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha224WithRSAEncryption_oid;
static { sha224WithRSAEncryption_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha256WithDSA_oid;
static { sha256WithDSA_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha256WithECDSA_oid;
static { sha256WithECDSA_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha256WithRSAEncryption_oid;
static { sha256WithRSAEncryption_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha384WithECDSA_oid;
static { sha384WithECDSA_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha384WithRSAEncryption_oid;
static { sha384WithRSAEncryption_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha512WithECDSA_oid;
static { sha512WithECDSA_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier sha512WithRSAEncryption_oid;
static { sha512WithRSAEncryption_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier shaWithDSA_OIW_oid;
static { shaWithDSA_OIW_oid = null; }

@libcore.api.Hide
public static final sun.security.util.ObjectIdentifier specifiedWithECDSA_oid;
static { specifiedWithECDSA_oid = null; }
}

