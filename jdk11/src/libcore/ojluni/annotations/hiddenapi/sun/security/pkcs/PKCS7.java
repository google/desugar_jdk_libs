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

package sun.security.pkcs;

import java.io.*;
import java.security.*;
import java.util.*;
import sun.security.timestamp.*;
import sun.security.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class PKCS7 {

    public PKCS7(java.io.InputStream in)
            throws java.io.IOException, sun.security.pkcs.ParsingException {
        throw new RuntimeException("Stub!");
    }

    public PKCS7(sun.security.util.DerInputStream derin) throws sun.security.pkcs.ParsingException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public PKCS7(byte[] bytes) throws sun.security.pkcs.ParsingException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public PKCS7(
            sun.security.x509.AlgorithmId[] digestAlgorithmIds,
            sun.security.pkcs.ContentInfo contentInfo,
            java.security.cert.X509Certificate[] certificates,
            java.security.cert.X509CRL[] crls,
            sun.security.pkcs.SignerInfo[] signerInfos) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public PKCS7(
            sun.security.x509.AlgorithmId[] digestAlgorithmIds,
            sun.security.pkcs.ContentInfo contentInfo,
            java.security.cert.X509Certificate[] certificates,
            sun.security.pkcs.SignerInfo[] signerInfos) {
        throw new RuntimeException("Stub!");
    }

    private void parse(sun.security.util.DerInputStream derin)
            throws sun.security.pkcs.ParsingException {
        throw new RuntimeException("Stub!");
    }

    private void parse(sun.security.util.DerInputStream derin, boolean oldStyle)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void parseNetscapeCertChain(sun.security.util.DerValue val)
            throws java.io.IOException, sun.security.pkcs.ParsingException {
        throw new RuntimeException("Stub!");
    }

    private void parseSignedData(sun.security.util.DerValue val)
            throws java.io.IOException, sun.security.pkcs.ParsingException {
        throw new RuntimeException("Stub!");
    }

    private void parseOldSignedData(sun.security.util.DerValue val)
            throws java.io.IOException, sun.security.pkcs.ParsingException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void encodeSignedData(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void encodeSignedData(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.pkcs.SignerInfo verify(sun.security.pkcs.SignerInfo info, byte[] bytes)
            throws java.security.NoSuchAlgorithmException, java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.pkcs.SignerInfo verify(
            sun.security.pkcs.SignerInfo info, java.io.InputStream dataInputStream)
            throws java.io.IOException, java.security.NoSuchAlgorithmException,
                    java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.pkcs.SignerInfo[] verify(byte[] bytes)
            throws java.security.NoSuchAlgorithmException, java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.pkcs.SignerInfo[] verify()
            throws java.security.NoSuchAlgorithmException, java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public java.math.BigInteger getVersion() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.AlgorithmId[] getDigestAlgorithmIds() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.pkcs.ContentInfo getContentInfo() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.security.cert.X509Certificate[] getCertificates() {
        throw new RuntimeException("Stub!");
    }

    public java.security.cert.X509CRL[] getCRLs() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.pkcs.SignerInfo[] getSignerInfos() {
        throw new RuntimeException("Stub!");
    }

    public java.security.cert.X509Certificate getCertificate(
            java.math.BigInteger serial, sun.security.x509.X500Name issuerName) {
        throw new RuntimeException("Stub!");
    }

    private void populateCertIssuerNames() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public boolean isOldStyle() {
        throw new RuntimeException("Stub!");
    }

    private java.security.Principal[] certIssuerNames;

    private java.security.cert.X509Certificate[] certificates;

    private sun.security.pkcs.ContentInfo contentInfo;

    private sun.security.util.ObjectIdentifier contentType;

    private java.security.cert.X509CRL[] crls;

    private sun.security.x509.AlgorithmId[] digestAlgorithmIds;

    private boolean oldStyle = false;

    private sun.security.pkcs.SignerInfo[] signerInfos;

    private java.math.BigInteger version;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class VerbatimX509Certificate
            extends sun.security.pkcs.PKCS7.WrappedX509Certificate {

        public VerbatimX509Certificate(
                java.security.cert.X509Certificate wrapped, byte[] encodedVerbatim) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        public byte[] getEncoded() throws java.security.cert.CertificateEncodingException {
            throw new RuntimeException("Stub!");
        }

        private byte[] encodedVerbatim;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class WrappedX509Certificate extends java.security.cert.X509Certificate {

        public WrappedX509Certificate(java.security.cert.X509Certificate wrapped) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Set<java.lang.String> getCriticalExtensionOIDs() {
            throw new RuntimeException("Stub!");
        }

        public byte[] getExtensionValue(java.lang.String oid) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Set<java.lang.String> getNonCriticalExtensionOIDs() {
            throw new RuntimeException("Stub!");
        }

        public boolean hasUnsupportedCriticalExtension() {
            throw new RuntimeException("Stub!");
        }

        public void checkValidity()
                throws java.security.cert.CertificateExpiredException,
                        java.security.cert.CertificateNotYetValidException {
            throw new RuntimeException("Stub!");
        }

        public void checkValidity(java.util.Date date)
                throws java.security.cert.CertificateExpiredException,
                        java.security.cert.CertificateNotYetValidException {
            throw new RuntimeException("Stub!");
        }

        public int getVersion() {
            throw new RuntimeException("Stub!");
        }

        public java.math.BigInteger getSerialNumber() {
            throw new RuntimeException("Stub!");
        }

        public java.security.Principal getIssuerDN() {
            throw new RuntimeException("Stub!");
        }

        public java.security.Principal getSubjectDN() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Date getNotBefore() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Date getNotAfter() {
            throw new RuntimeException("Stub!");
        }

        public byte[] getTBSCertificate() throws java.security.cert.CertificateEncodingException {
            throw new RuntimeException("Stub!");
        }

        public byte[] getSignature() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String getSigAlgName() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String getSigAlgOID() {
            throw new RuntimeException("Stub!");
        }

        public byte[] getSigAlgParams() {
            throw new RuntimeException("Stub!");
        }

        public boolean[] getIssuerUniqueID() {
            throw new RuntimeException("Stub!");
        }

        public boolean[] getSubjectUniqueID() {
            throw new RuntimeException("Stub!");
        }

        public boolean[] getKeyUsage() {
            throw new RuntimeException("Stub!");
        }

        public int getBasicConstraints() {
            throw new RuntimeException("Stub!");
        }

        public byte[] getEncoded() throws java.security.cert.CertificateEncodingException {
            throw new RuntimeException("Stub!");
        }

        public void verify(java.security.PublicKey key)
                throws java.security.cert.CertificateException, java.security.InvalidKeyException,
                        java.security.NoSuchAlgorithmException,
                        java.security.NoSuchProviderException, java.security.SignatureException {
            throw new RuntimeException("Stub!");
        }

        public void verify(java.security.PublicKey key, java.lang.String sigProvider)
                throws java.security.cert.CertificateException, java.security.InvalidKeyException,
                        java.security.NoSuchAlgorithmException,
                        java.security.NoSuchProviderException, java.security.SignatureException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        public java.security.PublicKey getPublicKey() {
            throw new RuntimeException("Stub!");
        }

        public java.util.List<java.lang.String> getExtendedKeyUsage()
                throws java.security.cert.CertificateParsingException {
            throw new RuntimeException("Stub!");
        }

        public java.util.Collection<java.util.List<?>> getIssuerAlternativeNames()
                throws java.security.cert.CertificateParsingException {
            throw new RuntimeException("Stub!");
        }

        public javax.security.auth.x500.X500Principal getIssuerX500Principal() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Collection<java.util.List<?>> getSubjectAlternativeNames()
                throws java.security.cert.CertificateParsingException {
            throw new RuntimeException("Stub!");
        }

        public javax.security.auth.x500.X500Principal getSubjectX500Principal() {
            throw new RuntimeException("Stub!");
        }

        public void verify(java.security.PublicKey key, java.security.Provider sigProvider)
                throws java.security.cert.CertificateException, java.security.InvalidKeyException,
                        java.security.NoSuchAlgorithmException, java.security.SignatureException {
            throw new RuntimeException("Stub!");
        }

        private final java.security.cert.X509Certificate wrapped;

        {
            wrapped = null;
        }
    }
}
