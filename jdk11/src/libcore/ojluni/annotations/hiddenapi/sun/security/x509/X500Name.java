/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1996, 2011, Oracle and/or its affiliates. All rights reserved.
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
public class X500Name implements sun.security.x509.GeneralNameInterface, java.security.Principal {

    @android.compat.annotation.UnsupportedAppUsage
    public X500Name(java.lang.String dname) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public X500Name(
            java.lang.String dname, java.util.Map<java.lang.String, java.lang.String> keywordMap)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X500Name(java.lang.String dname, java.lang.String format) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public X500Name(
            java.lang.String commonName,
            java.lang.String organizationUnit,
            java.lang.String organizationName,
            java.lang.String country)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public X500Name(
            java.lang.String commonName,
            java.lang.String organizationUnit,
            java.lang.String organizationName,
            java.lang.String localityName,
            java.lang.String stateName,
            java.lang.String country)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public X500Name(sun.security.x509.RDN[] rdnArray) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X500Name(sun.security.util.DerValue value) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X500Name(sun.security.util.DerInputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public X500Name(byte[] name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.util.List<sun.security.x509.RDN> rdns() {
        throw new RuntimeException("Stub!");
    }

    public int size() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.util.List<sun.security.x509.AVA> allAvas() {
        throw new RuntimeException("Stub!");
    }

    public int avaSize() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public boolean isEmpty() {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String getString(sun.security.util.DerValue attribute)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int getType() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getCountry() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getOrganization() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getOrganizationalUnit() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.lang.String getCommonName() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getLocality() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getState() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getDomain() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getDNQualifier() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getSurname() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getGivenName() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getInitials() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getGeneration() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getIP() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRFC1779Name() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRFC1779Name(java.util.Map<java.lang.String, java.lang.String> oidMap)
            throws java.lang.IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRFC2253Name() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRFC2253Name(
            java.util.Map<java.lang.String, java.lang.String> oidMap) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String generateRFC2253DN(
            java.util.Map<java.lang.String, java.lang.String> oidMap) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRFC2253CanonicalName() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    private sun.security.util.DerValue findAttribute(sun.security.util.ObjectIdentifier attribute) {
        throw new RuntimeException("Stub!");
    }

    public sun.security.util.DerValue findMostSpecificAttribute(
            sun.security.util.ObjectIdentifier attribute) {
        throw new RuntimeException("Stub!");
    }

    private void parseDER(sun.security.util.DerInputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public void emit(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public void encode(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public byte[] getEncodedInternal() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public byte[] getEncoded() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void parseDN(
            java.lang.String input, java.util.Map<java.lang.String, java.lang.String> keywordMap)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void checkNoNewLinesNorTabsAtBeginningOfDN(java.lang.String input) {
        throw new RuntimeException("Stub!");
    }

    private void parseRFC2253DN(java.lang.String dnString) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    static int countQuotes(java.lang.String string, int from, int to) {
        throw new RuntimeException("Stub!");
    }

    private static boolean escaped(int rdnEnd, int searchOffset, java.lang.String dnString) {
        throw new RuntimeException("Stub!");
    }

    private void generateDN() {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String generateRFC1779DN(
            java.util.Map<java.lang.String, java.lang.String> oidMap) {
        throw new RuntimeException("Stub!");
    }

    static sun.security.util.ObjectIdentifier intern(sun.security.util.ObjectIdentifier oid) {
        throw new RuntimeException("Stub!");
    }

    public int constrains(sun.security.x509.GeneralNameInterface inputName)
            throws java.lang.UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }

    private boolean isWithinSubtree(sun.security.x509.X500Name other) {
        throw new RuntimeException("Stub!");
    }

    public int subtreeDepth() throws java.lang.UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.X500Name commonAncestor(sun.security.x509.X500Name other) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public javax.security.auth.x500.X500Principal asX500Principal() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static sun.security.x509.X500Name asX500Name(javax.security.auth.x500.X500Principal p) {
        throw new RuntimeException("Stub!");
    }

    private static final int[] DNQUALIFIER_DATA;

    static {
        DNQUALIFIER_DATA = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier DNQUALIFIER_OID;

    static {
        DNQUALIFIER_OID = null;
    }

    private static final int[] DOMAIN_COMPONENT_DATA;

    static {
        DOMAIN_COMPONENT_DATA = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier DOMAIN_COMPONENT_OID;

    static {
        DOMAIN_COMPONENT_OID = null;
    }

    private static final int[] GENERATIONQUALIFIER_DATA;

    static {
        GENERATIONQUALIFIER_DATA = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier GENERATIONQUALIFIER_OID;

    static {
        GENERATIONQUALIFIER_OID = null;
    }

    private static final int[] GIVENNAME_DATA;

    static {
        GIVENNAME_DATA = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier GIVENNAME_OID;

    static {
        GIVENNAME_OID = null;
    }

    private static final int[] INITIALS_DATA;

    static {
        INITIALS_DATA = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier INITIALS_OID;

    static {
        INITIALS_OID = null;
    }

    private static final int[] SERIALNUMBER_DATA;

    static {
        SERIALNUMBER_DATA = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier SERIALNUMBER_OID;

    static {
        SERIALNUMBER_OID = null;
    }

    private static final int[] SURNAME_DATA;

    static {
        SURNAME_DATA = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier SURNAME_OID;

    static {
        SURNAME_OID = null;
    }

    private volatile java.util.List<sun.security.x509.AVA> allAvaList;

    private java.lang.String canonicalDn;

    private static final int[] commonName_data;

    static {
        commonName_data = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier commonName_oid;

    static {
        commonName_oid = null;
    }

    private static final int[] countryName_data;

    static {
        countryName_data = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier countryName_oid;

    static {
        countryName_oid = null;
    }

    private java.lang.String dn;

    private byte[] encoded;

    private static final java.util.Map<
                    sun.security.util.ObjectIdentifier, sun.security.util.ObjectIdentifier>
            internedOIDs;

    static {
        internedOIDs = null;
    }

    private static final int[] ipAddress_data;

    static {
        ipAddress_data = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier ipAddress_oid;

    static {
        ipAddress_oid = null;
    }

    private static final int[] localityName_data;

    static {
        localityName_data = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier localityName_oid;

    static {
        localityName_oid = null;
    }

    private sun.security.x509.RDN[] names;

    private static final int[] orgName_data;

    static {
        orgName_data = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier orgName_oid;

    static {
        orgName_oid = null;
    }

    private static final int[] orgUnitName_data;

    static {
        orgUnitName_data = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier orgUnitName_oid;

    static {
        orgUnitName_oid = null;
    }

    private static final java.lang.reflect.Constructor<javax.security.auth.x500.X500Principal>
            principalConstructor;

    static {
        principalConstructor = null;
    }

    private static final java.lang.reflect.Field principalField;

    static {
        principalField = null;
    }

    private volatile java.util.List<sun.security.x509.RDN> rdnList;

    private java.lang.String rfc1779Dn;

    private java.lang.String rfc2253Dn;

    private static final int[] stateName_data;

    static {
        stateName_data = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier stateName_oid;

    static {
        stateName_oid = null;
    }

    private static final int[] streetAddress_data;

    static {
        streetAddress_data = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier streetAddress_oid;

    static {
        streetAddress_oid = null;
    }

    private static final int[] title_data;

    static {
        title_data = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier title_oid;

    static {
        title_oid = null;
    }

    private static final int[] userid_data;

    static {
        userid_data = new int[0];
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final sun.security.util.ObjectIdentifier userid_oid;

    static {
        userid_oid = null;
    }

    private javax.security.auth.x500.X500Principal x500Principal;
}
