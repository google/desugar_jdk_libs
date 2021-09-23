/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1996, 2016, Oracle and/or its affiliates. All rights reserved.
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

import sun.security.util.DerEncoder;
import sun.security.util.DerInputStream;
import sun.security.x509.X500Name;
import java.io.IOException;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
@SuppressWarnings({"unchecked", "deprecation", "all"})
public class SignerInfo implements sun.security.util.DerEncoder {

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public SignerInfo() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public SignerInfo(X500Name issuerName, java.math.BigInteger serial, sun.security.x509.AlgorithmId digestAlgorithmId, sun.security.x509.AlgorithmId digestEncryptionAlgorithmId, byte[] encryptedDigest) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public SignerInfo(X500Name issuerName, java.math.BigInteger serial, sun.security.x509.AlgorithmId digestAlgorithmId, PKCS9Attributes authenticatedAttributes, sun.security.x509.AlgorithmId digestEncryptionAlgorithmId, byte[] encryptedDigest, PKCS9Attributes unauthenticatedAttributes) { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public SignerInfo(DerInputStream derin) throws java.io.IOException, sun.security.pkcs.ParsingException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public SignerInfo(DerInputStream derin, boolean oldStyle) throws java.io.IOException, sun.security.pkcs.ParsingException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public void encode(DerOutputStream out) throws java.io.IOException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public void derEncode(java.io.OutputStream out) throws java.io.IOException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public java.security.cert.X509Certificate getCertificate(sun.security.pkcs.PKCS7 block) throws java.io.IOException { throw new RuntimeException("Stub!"); }

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
@libcore.api.Hide
public java.util.ArrayList<java.security.cert.X509Certificate> getCertificateChain(sun.security.pkcs.PKCS7 block) throws java.io.IOException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public java.math.BigInteger getVersion() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public sun.security.x509.X500Name getIssuerName() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public java.math.BigInteger getCertificateSerialNumber() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public sun.security.x509.AlgorithmId getDigestAlgorithmId() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public sun.security.pkcs.PKCS9Attributes getAuthenticatedAttributes() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public sun.security.x509.AlgorithmId getDigestEncryptionAlgorithmId() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public byte[] getEncryptedDigest() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public sun.security.pkcs.PKCS9Attributes getUnauthenticatedAttributes() { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public sun.security.pkcs.PKCS7 getTsToken() throws java.io.IOException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public java.security.Timestamp getTimestamp() throws java.security.cert.CertificateException, java.io.IOException, java.security.NoSuchAlgorithmException, java.security.SignatureException { throw new RuntimeException("Stub!"); }

@libcore.api.Hide
public java.lang.String toString() { throw new RuntimeException("Stub!"); }
}
