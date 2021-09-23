/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
public final class URI implements java.lang.Comparable<java.net.URI>, java.io.Serializable {

    private URI() {
        throw new RuntimeException("Stub!");
    }

    public URI(java.lang.String str) throws java.net.URISyntaxException {
        throw new RuntimeException("Stub!");
    }

    public URI(
            java.lang.String scheme,
            java.lang.String userInfo,
            java.lang.String host,
            int port,
            java.lang.String path,
            java.lang.String query,
            java.lang.String fragment)
            throws java.net.URISyntaxException {
        throw new RuntimeException("Stub!");
    }

    public URI(
            java.lang.String scheme,
            java.lang.String authority,
            java.lang.String path,
            java.lang.String query,
            java.lang.String fragment)
            throws java.net.URISyntaxException {
        throw new RuntimeException("Stub!");
    }

    public URI(
            java.lang.String scheme,
            java.lang.String host,
            java.lang.String path,
            java.lang.String fragment)
            throws java.net.URISyntaxException {
        throw new RuntimeException("Stub!");
    }

    public URI(java.lang.String scheme, java.lang.String ssp, java.lang.String fragment)
            throws java.net.URISyntaxException {
        throw new RuntimeException("Stub!");
    }

    public static java.net.URI create(java.lang.String str) {
        throw new RuntimeException("Stub!");
    }

    public java.net.URI parseServerAuthority() throws java.net.URISyntaxException {
        throw new RuntimeException("Stub!");
    }

    public java.net.URI normalize() {
        throw new RuntimeException("Stub!");
    }

    public java.net.URI resolve(java.net.URI uri) {
        throw new RuntimeException("Stub!");
    }

    public java.net.URI resolve(java.lang.String str) {
        throw new RuntimeException("Stub!");
    }

    public java.net.URI relativize(java.net.URI uri) {
        throw new RuntimeException("Stub!");
    }

    public java.net.URL toURL() throws java.net.MalformedURLException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getScheme() {
        throw new RuntimeException("Stub!");
    }

    public boolean isAbsolute() {
        throw new RuntimeException("Stub!");
    }

    public boolean isOpaque() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRawSchemeSpecificPart() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getSchemeSpecificPart() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRawAuthority() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getAuthority() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRawUserInfo() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getUserInfo() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getHost() {
        throw new RuntimeException("Stub!");
    }

    public int getPort() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRawPath() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getPath() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRawQuery() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getQuery() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getRawFragment() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getFragment() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object ob) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public int compareTo(java.net.URI that) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toASCIIString() {
        throw new RuntimeException("Stub!");
    }

    private void writeObject(java.io.ObjectOutputStream os) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream is)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static int toLower(char c) {
        throw new RuntimeException("Stub!");
    }

    private static int toUpper(char c) {
        throw new RuntimeException("Stub!");
    }

    private static boolean equal(java.lang.String s, java.lang.String t) {
        throw new RuntimeException("Stub!");
    }

    private static boolean equalIgnoringCase(java.lang.String s, java.lang.String t) {
        throw new RuntimeException("Stub!");
    }

    private static int hash(int hash, java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    private static int normalizedHash(int hash, java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    private static int hashIgnoringCase(int hash, java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    private static int compare(java.lang.String s, java.lang.String t) {
        throw new RuntimeException("Stub!");
    }

    private static int compareIgnoringCase(java.lang.String s, java.lang.String t) {
        throw new RuntimeException("Stub!");
    }

    private static void checkPath(
            java.lang.String s, java.lang.String scheme, java.lang.String path)
            throws java.net.URISyntaxException {
        throw new RuntimeException("Stub!");
    }

    private void appendAuthority(
            java.lang.StringBuffer sb,
            java.lang.String authority,
            java.lang.String userInfo,
            java.lang.String host,
            int port) {
        throw new RuntimeException("Stub!");
    }

    private void appendSchemeSpecificPart(
            java.lang.StringBuffer sb,
            java.lang.String opaquePart,
            java.lang.String authority,
            java.lang.String userInfo,
            java.lang.String host,
            int port,
            java.lang.String path,
            java.lang.String query) {
        throw new RuntimeException("Stub!");
    }

    private void appendFragment(java.lang.StringBuffer sb, java.lang.String fragment) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String toString(
            java.lang.String scheme,
            java.lang.String opaquePart,
            java.lang.String authority,
            java.lang.String userInfo,
            java.lang.String host,
            int port,
            java.lang.String path,
            java.lang.String query,
            java.lang.String fragment) {
        throw new RuntimeException("Stub!");
    }

    private void defineSchemeSpecificPart() {
        throw new RuntimeException("Stub!");
    }

    private void defineString() {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String resolvePath(
            java.lang.String base, java.lang.String child, boolean absolute) {
        throw new RuntimeException("Stub!");
    }

    private static java.net.URI resolve(java.net.URI base, java.net.URI child) {
        throw new RuntimeException("Stub!");
    }

    private static java.net.URI normalize(java.net.URI u) {
        throw new RuntimeException("Stub!");
    }

    private static java.net.URI relativize(java.net.URI base, java.net.URI child) {
        throw new RuntimeException("Stub!");
    }

    private static int needsNormalization(java.lang.String path) {
        throw new RuntimeException("Stub!");
    }

    private static void split(char[] path, int[] segs) {
        throw new RuntimeException("Stub!");
    }

    private static int join(char[] path, int[] segs) {
        throw new RuntimeException("Stub!");
    }

    private static void removeDots(char[] path, int[] segs, boolean removeLeading) {
        throw new RuntimeException("Stub!");
    }

    private static void maybeAddLeadingDot(char[] path, int[] segs) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String normalize(java.lang.String ps) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String normalize(java.lang.String ps, boolean removeLeading) {
        throw new RuntimeException("Stub!");
    }

    private static long lowMask(java.lang.String chars) {
        throw new RuntimeException("Stub!");
    }

    private static long highMask(java.lang.String chars) {
        throw new RuntimeException("Stub!");
    }

    private static long lowMask(char first, char last) {
        throw new RuntimeException("Stub!");
    }

    private static long highMask(char first, char last) {
        throw new RuntimeException("Stub!");
    }

    private static boolean match(char c, long lowMask, long highMask) {
        throw new RuntimeException("Stub!");
    }

    private static void appendEscape(java.lang.StringBuffer sb, byte b) {
        throw new RuntimeException("Stub!");
    }

    private static void appendEncoded(java.lang.StringBuffer sb, char c) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String quote(java.lang.String s, long lowMask, long highMask) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String encode(java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    private static int decode(char c) {
        throw new RuntimeException("Stub!");
    }

    private static byte decode(char c1, char c2) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String decode(java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    private static final long H_ALPHA;

    static {
        H_ALPHA = 0;
    }

    private static final long H_ALPHANUM;

    static {
        H_ALPHANUM = 0;
    }

    private static final long H_DASH;

    static {
        H_DASH = 0;
    }

    private static final long H_DIGIT = 0L; // 0x0L

    private static final long H_DOT;

    static {
        H_DOT = 0;
    }

    private static final long H_ESCAPED = 0L; // 0x0L

    private static final long H_HEX;

    static {
        H_HEX = 0;
    }

    private static final long H_LEFT_BRACKET;

    static {
        H_LEFT_BRACKET = 0;
    }

    private static final long H_LOWALPHA;

    static {
        H_LOWALPHA = 0;
    }

    private static final long H_MARK;

    static {
        H_MARK = 0;
    }

    private static final long H_PATH;

    static {
        H_PATH = 0;
    }

    private static final long H_PCHAR;

    static {
        H_PCHAR = 0;
    }

    private static final long H_REG_NAME;

    static {
        H_REG_NAME = 0;
    }

    private static final long H_RESERVED;

    static {
        H_RESERVED = 0;
    }

    private static final long H_SCHEME;

    static {
        H_SCHEME = 0;
    }

    private static final long H_SERVER;

    static {
        H_SERVER = 0;
    }

    private static final long H_SERVER_PERCENT;

    static {
        H_SERVER_PERCENT = 0;
    }

    private static final long H_UNDERSCORE;

    static {
        H_UNDERSCORE = 0;
    }

    private static final long H_UNRESERVED;

    static {
        H_UNRESERVED = 0;
    }

    private static final long H_UPALPHA;

    static {
        H_UPALPHA = 0;
    }

    private static final long H_URIC;

    static {
        H_URIC = 0;
    }

    private static final long H_URIC_NO_SLASH;

    static {
        H_URIC_NO_SLASH = 0;
    }

    private static final long H_USERINFO;

    static {
        H_USERINFO = 0;
    }

    private static final long L_ALPHA = 0L; // 0x0L

    private static final long L_ALPHANUM;

    static {
        L_ALPHANUM = 0;
    }

    private static final long L_DASH;

    static {
        L_DASH = 0;
    }

    private static final long L_DIGIT;

    static {
        L_DIGIT = 0;
    }

    private static final long L_DOT;

    static {
        L_DOT = 0;
    }

    private static final long L_ESCAPED = 1L; // 0x1L

    private static final long L_HEX;

    static {
        L_HEX = 0;
    }

    private static final long L_LEFT_BRACKET;

    static {
        L_LEFT_BRACKET = 0;
    }

    private static final long L_LOWALPHA = 0L; // 0x0L

    private static final long L_MARK;

    static {
        L_MARK = 0;
    }

    private static final long L_PATH;

    static {
        L_PATH = 0;
    }

    private static final long L_PCHAR;

    static {
        L_PCHAR = 0;
    }

    private static final long L_REG_NAME;

    static {
        L_REG_NAME = 0;
    }

    private static final long L_RESERVED;

    static {
        L_RESERVED = 0;
    }

    private static final long L_SCHEME;

    static {
        L_SCHEME = 0;
    }

    private static final long L_SERVER;

    static {
        L_SERVER = 0;
    }

    private static final long L_SERVER_PERCENT;

    static {
        L_SERVER_PERCENT = 0;
    }

    private static final long L_UNDERSCORE;

    static {
        L_UNDERSCORE = 0;
    }

    private static final long L_UNRESERVED;

    static {
        L_UNRESERVED = 0;
    }

    private static final long L_UPALPHA = 0L; // 0x0L

    private static final long L_URIC;

    static {
        L_URIC = 0;
    }

    private static final long L_URIC_NO_SLASH;

    static {
        L_URIC_NO_SLASH = 0;
    }

    private static final long L_USERINFO;

    static {
        L_USERINFO = 0;
    }

    private transient java.lang.String authority;

    private transient volatile java.lang.String decodedAuthority;

    private transient volatile java.lang.String decodedFragment;

    private transient volatile java.lang.String decodedPath;

    private transient volatile java.lang.String decodedQuery;

    private transient volatile java.lang.String decodedSchemeSpecificPart;

    private transient volatile java.lang.String decodedUserInfo;

    @UnsupportedAppUsage
    private transient java.lang.String fragment;

    private transient volatile int hash;

    private static final char[] hexDigits;

    static {
        hexDigits = new char[0];
    }

    @UnsupportedAppUsage
    private transient java.lang.String host;

    private transient java.lang.String path;

    @UnsupportedAppUsage
    private transient int port = -1; // 0xffffffff

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private transient java.lang.String query;

    private transient java.lang.String scheme;

    private transient volatile java.lang.String schemeSpecificPart;

    static final long serialVersionUID = -6052424284110960213L; // 0xac01782e439e49abL

    @UnsupportedAppUsage
    private volatile java.lang.String string;

    private transient java.lang.String userInfo;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class Parser {

        Parser(java.lang.String s) {
            throw new RuntimeException("Stub!");
        }

        private void fail(java.lang.String reason) throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private void fail(java.lang.String reason, int p) throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private void failExpecting(java.lang.String expected, int p)
                throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private void failExpecting(java.lang.String expected, java.lang.String prior, int p)
                throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private java.lang.String substring(int start, int end) {
            throw new RuntimeException("Stub!");
        }

        private char charAt(int p) {
            throw new RuntimeException("Stub!");
        }

        private boolean at(int start, int end, char c) {
            throw new RuntimeException("Stub!");
        }

        private boolean at(int start, int end, java.lang.String s) {
            throw new RuntimeException("Stub!");
        }

        private int scan(int start, int end, char c) {
            throw new RuntimeException("Stub!");
        }

        private int scan(int start, int end, java.lang.String err, java.lang.String stop) {
            throw new RuntimeException("Stub!");
        }

        private int scanEscape(int start, int n, char first) throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private int scan(int start, int n, long lowMask, long highMask)
                throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private void checkChars(
                int start, int end, long lowMask, long highMask, java.lang.String what)
                throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private void checkChar(int p, long lowMask, long highMask, java.lang.String what)
                throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        void parse(boolean rsa) throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private int parseHierarchical(int start, int n) throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private int parseAuthority(int start, int n) throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private int parseServer(int start, int n) throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private int scanByte(int start, int n) throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private int scanIPv4Address(int start, int n, boolean strict)
                throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private int takeIPv4Address(int start, int n, java.lang.String expected)
                throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private int parseIPv4Address(int start, int n) {
            throw new RuntimeException("Stub!");
        }

        private int parseHostname(int start, int n) throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private int parseIPv6Reference(int start, int n) throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private int scanHexPost(int start, int n) throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private int scanHexSeq(int start, int n) throws java.net.URISyntaxException {
            throw new RuntimeException("Stub!");
        }

        private java.lang.String input;

        private int ipv6byteCount = 0; // 0x0

        private boolean requireServerAuthority = false;
    }
}
