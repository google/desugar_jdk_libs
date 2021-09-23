/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class HttpCookie implements java.lang.Cloneable {

    public HttpCookie(java.lang.String name, java.lang.String value) {
        throw new RuntimeException("Stub!");
    }

    private HttpCookie(java.lang.String name, java.lang.String value, java.lang.String header) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.List<java.net.HttpCookie> parse(java.lang.String header) {
        throw new RuntimeException("Stub!");
    }

    private static java.util.List<java.net.HttpCookie> parse(
            java.lang.String header, boolean retainHeader) {
        throw new RuntimeException("Stub!");
    }

    public boolean hasExpired() {
        throw new RuntimeException("Stub!");
    }

    public void setComment(java.lang.String purpose) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getComment() {
        throw new RuntimeException("Stub!");
    }

    public void setCommentURL(java.lang.String purpose) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getCommentURL() {
        throw new RuntimeException("Stub!");
    }

    public void setDiscard(boolean discard) {
        throw new RuntimeException("Stub!");
    }

    public boolean getDiscard() {
        throw new RuntimeException("Stub!");
    }

    public void setPortlist(java.lang.String ports) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getPortlist() {
        throw new RuntimeException("Stub!");
    }

    public void setDomain(java.lang.String pattern) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getDomain() {
        throw new RuntimeException("Stub!");
    }

    public void setMaxAge(long expiry) {
        throw new RuntimeException("Stub!");
    }

    public long getMaxAge() {
        throw new RuntimeException("Stub!");
    }

    public void setPath(java.lang.String uri) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getPath() {
        throw new RuntimeException("Stub!");
    }

    public void setSecure(boolean flag) {
        throw new RuntimeException("Stub!");
    }

    public boolean getSecure() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public void setValue(java.lang.String newValue) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getValue() {
        throw new RuntimeException("Stub!");
    }

    public int getVersion() {
        throw new RuntimeException("Stub!");
    }

    public void setVersion(int v) {
        throw new RuntimeException("Stub!");
    }

    public boolean isHttpOnly() {
        throw new RuntimeException("Stub!");
    }

    public void setHttpOnly(boolean httpOnly) {
        throw new RuntimeException("Stub!");
    }

    public static boolean domainMatches(java.lang.String domain, java.lang.String host) {
        throw new RuntimeException("Stub!");
    }

    private static boolean isFullyQualifiedDomainName(java.lang.String s, int firstCharacter) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object clone() {
        throw new RuntimeException("Stub!");
    }

    private static boolean isToken(java.lang.String value) {
        throw new RuntimeException("Stub!");
    }

    private static java.net.HttpCookie parseInternal(
            java.lang.String header, boolean retainHeader) {
        throw new RuntimeException("Stub!");
    }

    private static void assignAttribute(
            java.net.HttpCookie cookie, java.lang.String attrName, java.lang.String attrValue) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String header() {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String toNetscapeHeaderString() {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String toRFC2965HeaderString() {
        throw new RuntimeException("Stub!");
    }

    private static int guessCookieVersion(java.lang.String header) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String stripOffSurroundingQuote(java.lang.String str) {
        throw new RuntimeException("Stub!");
    }

    private static boolean equalsIgnoreCase(java.lang.String s, java.lang.String t) {
        throw new RuntimeException("Stub!");
    }

    private static boolean startsWithIgnoreCase(java.lang.String s, java.lang.String start) {
        throw new RuntimeException("Stub!");
    }

    private static java.util.List<java.lang.String> splitMultiCookies(java.lang.String header) {
        throw new RuntimeException("Stub!");
    }

    static final java.util.TimeZone GMT;

    static {
        GMT = null;
    }

    private static final long MAX_AGE_UNSPECIFIED = -1L; // 0xffffffffffffffffL

    private static final java.util.Set<java.lang.String> RESERVED_NAMES;

    static {
        RESERVED_NAMES = null;
    }

    private static final java.lang.String SET_COOKIE = "set-cookie:";

    private static final java.lang.String SET_COOKIE2 = "set-cookie2:";

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    static final java.util.Map<java.lang.String, java.net.HttpCookie.CookieAttributeAssignor>
            assignors;

    static {
        assignors = null;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private java.lang.String comment;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private java.lang.String commentURL;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private java.lang.String domain;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private final java.lang.String header;

    {
        header = null;
    }

    @UnsupportedAppUsage(
        publicAlternatives = "Use {@link #setHttpOnly()}/{@link #isHttpOnly()} instead.")
    private boolean httpOnly;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private long maxAge = -1L; // 0xffffffffffffffffL

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private final java.lang.String name;

    {
        name = null;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private java.lang.String path;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private java.lang.String portlist;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private boolean secure;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private boolean toDiscard;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private static final java.lang.String tspecials = ",;= \t";

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private java.lang.String value;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private int version = 1; // 0x1

    @UnsupportedAppUsage
    private final long whenCreated;

    {
        whenCreated = 0;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static interface CookieAttributeAssignor {

        public void assign(
                java.net.HttpCookie cookie, java.lang.String attrName, java.lang.String attrValue);
    }
}
