/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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


@SuppressWarnings({"unchecked", "deprecation", "all"})
public class PKCS9Attribute implements sun.security.util.DerEncoder {

    @android.compat.annotation.UnsupportedAppUsage
    public PKCS9Attribute(sun.security.util.ObjectIdentifier oid, java.lang.Object value)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public PKCS9Attribute(java.lang.String name, java.lang.Object value)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public PKCS9Attribute(sun.security.util.DerValue derVal) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void init(sun.security.util.ObjectIdentifier oid, java.lang.Object value)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void derEncode(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean isKnown() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.lang.Object getValue() {
        throw new RuntimeException("Stub!");
    }

    public boolean isSingleValued() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.util.ObjectIdentifier getOID() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.util.ObjectIdentifier getOID(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String getName(sun.security.util.ObjectIdentifier oid) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    static int indexOf(java.lang.Object obj, java.lang.Object[] a, int start) {
        throw new RuntimeException("Stub!");
    }

    private void throwSingleValuedException() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void throwTagException(java.lang.Byte tag) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static final java.lang.Class<?> BYTE_ARRAY_CLASS;

    static {
        BYTE_ARRAY_CLASS = null;
    }

    public static final sun.security.util.ObjectIdentifier CHALLENGE_PASSWORD_OID;

    static {
        CHALLENGE_PASSWORD_OID = null;
    }

    public static final java.lang.String CHALLENGE_PASSWORD_STR = "ChallengePassword";

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier CONTENT_TYPE_OID;

    static {
        CONTENT_TYPE_OID = null;
    }

    public static final java.lang.String CONTENT_TYPE_STR = "ContentType";

    public static final sun.security.util.ObjectIdentifier COUNTERSIGNATURE_OID;

    static {
        COUNTERSIGNATURE_OID = null;
    }

    public static final java.lang.String COUNTERSIGNATURE_STR = "Countersignature";

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier EMAIL_ADDRESS_OID;

    static {
        EMAIL_ADDRESS_OID = null;
    }

    public static final java.lang.String EMAIL_ADDRESS_STR = "EmailAddress";

    public static final sun.security.util.ObjectIdentifier EXTENDED_CERTIFICATE_ATTRIBUTES_OID;

    static {
        EXTENDED_CERTIFICATE_ATTRIBUTES_OID = null;
    }

    public static final java.lang.String EXTENDED_CERTIFICATE_ATTRIBUTES_STR =
            "ExtendedCertificateAttributes";

    public static final sun.security.util.ObjectIdentifier EXTENSION_REQUEST_OID;

    static {
        EXTENSION_REQUEST_OID = null;
    }

    public static final java.lang.String EXTENSION_REQUEST_STR = "ExtensionRequest";

    public static final sun.security.util.ObjectIdentifier ISSUER_SERIALNUMBER_OID;

    static {
        ISSUER_SERIALNUMBER_OID = null;
    }

    public static final java.lang.String ISSUER_SERIALNUMBER_STR = "IssuerAndSerialNumber";

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier MESSAGE_DIGEST_OID;

    static {
        MESSAGE_DIGEST_OID = null;
    }

    public static final java.lang.String MESSAGE_DIGEST_STR = "MessageDigest";

    private static final java.util.Hashtable<java.lang.String, sun.security.util.ObjectIdentifier>
            NAME_OID_TABLE;

    static {
        NAME_OID_TABLE = null;
    }

    private static final java.util.Hashtable<sun.security.util.ObjectIdentifier, java.lang.String>
            OID_NAME_TABLE;

    static {
        OID_NAME_TABLE = null;
    }

    static final sun.security.util.ObjectIdentifier[] PKCS9_OIDS;

    static {
        PKCS9_OIDS = new sun.security.util.ObjectIdentifier[0];
    }

    private static final java.lang.Byte[][] PKCS9_VALUE_TAGS;

    static {
        PKCS9_VALUE_TAGS = new java.lang.Byte[0][];
    }

    private static final java.lang.String RSA_PROPRIETARY_STR = "RSAProprietary";

    public static final sun.security.util.ObjectIdentifier SIGNATURE_TIMESTAMP_TOKEN_OID;

    static {
        SIGNATURE_TIMESTAMP_TOKEN_OID = null;
    }

    public static final java.lang.String SIGNATURE_TIMESTAMP_TOKEN_STR = "SignatureTimestampToken";

    public static final sun.security.util.ObjectIdentifier SIGNING_CERTIFICATE_OID;

    static {
        SIGNING_CERTIFICATE_OID = null;
    }

    public static final java.lang.String SIGNING_CERTIFICATE_STR = "SigningCertificate";

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier SIGNING_TIME_OID;

    static {
        SIGNING_TIME_OID = null;
    }

    public static final java.lang.String SIGNING_TIME_STR = "SigningTime";

    private static final boolean[] SINGLE_VALUED;

    static {
        SINGLE_VALUED = new boolean[0];
    }

    public static final sun.security.util.ObjectIdentifier SMIME_CAPABILITY_OID;

    static {
        SMIME_CAPABILITY_OID = null;
    }

    public static final java.lang.String SMIME_CAPABILITY_STR = "SMIMECapability";

    private static final java.lang.String SMIME_SIGNING_DESC_STR = "SMIMESigningDesc";

    public static final sun.security.util.ObjectIdentifier UNSTRUCTURED_ADDRESS_OID;

    static {
        UNSTRUCTURED_ADDRESS_OID = null;
    }

    public static final java.lang.String UNSTRUCTURED_ADDRESS_STR = "UnstructuredAddress";

    public static final sun.security.util.ObjectIdentifier UNSTRUCTURED_NAME_OID;

    static {
        UNSTRUCTURED_NAME_OID = null;
    }

    public static final java.lang.String UNSTRUCTURED_NAME_STR = "UnstructuredName";

    private static final java.lang.Class<?>[] VALUE_CLASSES;

    static {
        VALUE_CLASSES = new java.lang.Class[0];
    }

    private static final sun.security.util.Debug debug;

    static {
        debug = null;
    }

    private int index;

    private sun.security.util.ObjectIdentifier oid;

    private java.lang.Object value;
}
