/*
 * Copyright (c) 1997, 2016, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.util;


@SuppressWarnings({"unchecked", "deprecation", "all"})
public class SignatureFileVerifier {

    public SignatureFileVerifier(
            java.util.ArrayList<java.security.CodeSigner[]> signerCache,
            sun.security.util.ManifestDigester md,
            java.lang.String name,
            byte[] rawBytes)
            throws java.security.cert.CertificateException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean needSignatureFileBytes() {
        throw new RuntimeException("Stub!");
    }

    public boolean needSignatureFile(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public void setSignatureFile(byte[] sfBytes) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static boolean isBlockOrSF(java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    public static boolean isSigningRelated(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    private java.security.MessageDigest getDigest(java.lang.String algorithm)
            throws java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    public void process(
            java.util.Hashtable<java.lang.String, java.security.CodeSigner[]> signers,
            java.util.List<java.lang.Object> manifestDigests)
            throws java.security.cert.CertificateException, java.io.IOException,
                    java.util.jar.JarException, java.security.NoSuchAlgorithmException,
                    java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    private void processImpl(
            java.util.Hashtable<java.lang.String, java.security.CodeSigner[]> signers,
            java.util.List<java.lang.Object> manifestDigests)
            throws java.security.cert.CertificateException, java.io.IOException,
                    java.util.jar.JarException, java.security.NoSuchAlgorithmException,
                    java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    private boolean verifyManifestHash(
            java.util.jar.Manifest sf,
            sun.security.util.ManifestDigester md,
            java.util.List<java.lang.Object> manifestDigests)
            throws java.io.IOException, java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    private boolean verifyManifestMainAttrs(
            java.util.jar.Manifest sf, sun.security.util.ManifestDigester md)
            throws java.io.IOException, java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    private boolean verifySection(
            java.util.jar.Attributes sfAttr,
            java.lang.String name,
            sun.security.util.ManifestDigester md)
            throws java.io.IOException, java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    private java.security.CodeSigner[] getSigners(
            sun.security.pkcs.SignerInfo[] infos, sun.security.pkcs.PKCS7 block)
            throws java.security.cert.CertificateException, java.io.IOException,
                    java.security.NoSuchAlgorithmException, java.security.SignatureException {
        throw new RuntimeException("Stub!");
    }

    static java.lang.String toHex(byte[] data) {
        throw new RuntimeException("Stub!");
    }

    static boolean contains(java.security.CodeSigner[] set, java.security.CodeSigner signer) {
        throw new RuntimeException("Stub!");
    }

    static boolean isSubSet(java.security.CodeSigner[] subset, java.security.CodeSigner[] set) {
        throw new RuntimeException("Stub!");
    }

    static boolean matches(
            java.security.CodeSigner[] signers,
            java.security.CodeSigner[] oldSigners,
            java.security.CodeSigner[] newSigners) {
        throw new RuntimeException("Stub!");
    }

    void updateSigners(
            java.security.CodeSigner[] newSigners,
            java.util.Hashtable<java.lang.String, java.security.CodeSigner[]> signers,
            java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    private static final java.lang.String ATTR_DIGEST;

    static {
        ATTR_DIGEST = null;
    }

    private static final java.util.Set<java.security.CryptoPrimitive> DIGEST_PRIMITIVE_SET;

    static {
        DIGEST_PRIMITIVE_SET = null;
    }

    private static final sun.security.util.DisabledAlgorithmConstraints JAR_DISABLED_CHECK;

    static {
        JAR_DISABLED_CHECK = null;
    }

    private sun.security.pkcs.PKCS7 block;

    private java.security.cert.CertificateFactory certificateFactory;

    private java.util.HashMap<java.lang.String, java.security.MessageDigest> createdDigests;

    private static final sun.security.util.Debug debug;

    static {
        debug = null;
    }

    private static final char[] hexc;

    static {
        hexc = new char[0];
    }

    private sun.security.util.ManifestDigester md;

    private java.lang.String name;

    private byte[] sfBytes;

    private java.util.ArrayList<java.security.CodeSigner[]> signerCache;

    private boolean workaround = false;
}
