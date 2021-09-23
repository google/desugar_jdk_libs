/*
 * Copyright (c) 1998, 2007, Oracle and/or its affiliates. All rights reserved.
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

package sun.net.www;


@SuppressWarnings({"unchecked", "deprecation", "all"})
public class ParseUtil {

    public ParseUtil() {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String encodePath(java.lang.String path) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static java.lang.String encodePath(java.lang.String path, boolean flag) {
        throw new RuntimeException("Stub!");
    }

    private static int escape(char[] cc, char c, int index) {
        throw new RuntimeException("Stub!");
    }

    private static byte unescape(java.lang.String s, int i) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static java.lang.String decode(java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String canonizeString(java.lang.String file) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static java.net.URL fileToEncodedURL(java.io.File file)
            throws java.net.MalformedURLException {
        throw new RuntimeException("Stub!");
    }

    public static java.net.URI toURI(java.net.URL url) {
        throw new RuntimeException("Stub!");
    }

    private static java.net.URI createURI(
            java.lang.String scheme,
            java.lang.String authority,
            java.lang.String path,
            java.lang.String query,
            java.lang.String fragment)
            throws java.net.URISyntaxException {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String toString(
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

    private static void appendSchemeSpecificPart(
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

    private static void appendAuthority(
            java.lang.StringBuffer sb,
            java.lang.String authority,
            java.lang.String userInfo,
            java.lang.String host,
            int port) {
        throw new RuntimeException("Stub!");
    }

    private static void appendFragment(java.lang.StringBuffer sb, java.lang.String fragment) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String quote(java.lang.String s, long lowMask, long highMask) {
        throw new RuntimeException("Stub!");
    }

    private static boolean isEscaped(java.lang.String s, int pos) {
        throw new RuntimeException("Stub!");
    }

    private static void appendEncoded(java.lang.StringBuffer sb, char c) {
        throw new RuntimeException("Stub!");
    }

    private static void appendEscape(java.lang.StringBuffer sb, byte b) {
        throw new RuntimeException("Stub!");
    }

    private static boolean match(char c, long lowMask, long highMask) {
        throw new RuntimeException("Stub!");
    }

    private static void checkPath(
            java.lang.String s, java.lang.String scheme, java.lang.String path)
            throws java.net.URISyntaxException {
        throw new RuntimeException("Stub!");
    }

    private static long lowMask(char first, char last) {
        throw new RuntimeException("Stub!");
    }

    private static long lowMask(java.lang.String chars) {
        throw new RuntimeException("Stub!");
    }

    private static long highMask(char first, char last) {
        throw new RuntimeException("Stub!");
    }

    private static long highMask(java.lang.String chars) {
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

    private static final long H_ESCAPED = 0L; // 0x0L

    private static final long H_HEX;

    static {
        H_HEX = 0;
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

    private static final long H_SERVER;

    static {
        H_SERVER = 0;
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

    private static final long L_ESCAPED = 1L; // 0x1L

    private static final long L_HEX;

    static {
        L_HEX = 0;
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

    private static final long L_SERVER;

    static {
        L_SERVER = 0;
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

    private static final long L_USERINFO;

    static {
        L_USERINFO = 0;
    }

    static java.util.BitSet encodedInPath;

    private static final char[] hexDigits;

    static {
        hexDigits = new char[0];
    }
}
