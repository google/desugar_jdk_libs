/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
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

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class X509CRLEntryImpl extends java.security.cert.X509CRLEntry
        implements java.lang.Comparable<sun.security.x509.X509CRLEntryImpl> {

    public X509CRLEntryImpl(java.math.BigInteger num, java.util.Date date) {
        throw new RuntimeException("Stub!");
    }

    public X509CRLEntryImpl(
            java.math.BigInteger num,
            java.util.Date date,
            sun.security.x509.CRLExtensions crlEntryExts) {
        throw new RuntimeException("Stub!");
    }

    public X509CRLEntryImpl(byte[] revokedCert) throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    public X509CRLEntryImpl(sun.security.util.DerValue derValue)
            throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    public boolean hasExtensions() {
        throw new RuntimeException("Stub!");
    }

    public void encode(sun.security.util.DerOutputStream outStrm)
            throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    public byte[] getEncoded() throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    private byte[] getEncoded0() throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    public javax.security.auth.x500.X500Principal getCertificateIssuer() {
        throw new RuntimeException("Stub!");
    }

    void setCertificateIssuer(
            javax.security.auth.x500.X500Principal crlIssuer,
            javax.security.auth.x500.X500Principal certIssuer) {
        throw new RuntimeException("Stub!");
    }

    public java.math.BigInteger getSerialNumber() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Date getRevocationDate() {
        throw new RuntimeException("Stub!");
    }

    public java.security.cert.CRLReason getRevocationReason() {
        throw new RuntimeException("Stub!");
    }

    public static java.security.cert.CRLReason getRevocationReason(
            java.security.cert.X509CRLEntry crlEntry) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Integer getReasonCode() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public boolean hasUnsupportedCriticalExtension() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Set<java.lang.String> getCriticalExtensionOIDs() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Set<java.lang.String> getNonCriticalExtensionOIDs() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getExtensionValue(java.lang.String oid) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.x509.Extension getExtension(sun.security.util.ObjectIdentifier oid) {
        throw new RuntimeException("Stub!");
    }

    private void parse(sun.security.util.DerValue derVal)
            throws java.security.cert.CRLException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.x509.X509CRLEntryImpl toImpl(java.security.cert.X509CRLEntry entry)
            throws java.security.cert.CRLException {
        throw new RuntimeException("Stub!");
    }

    sun.security.x509.CertificateIssuerExtension getCertificateIssuerExtension() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Map<java.lang.String, java.security.cert.Extension> getExtensions() {
        throw new RuntimeException("Stub!");
    }

    public int compareTo(sun.security.x509.X509CRLEntryImpl that) {
        throw new RuntimeException("Stub!");
    }

    private static final long YR_2050 = 2524636800000L; // 0x24bd0146400L

    private javax.security.auth.x500.X500Principal certIssuer;

    private sun.security.x509.CRLExtensions extensions;

    private static final boolean isExplicit = false;

    private java.util.Date revocationDate;

    private byte[] revokedCert;

    private sun.security.x509.SerialNumber serialNumber;
}
