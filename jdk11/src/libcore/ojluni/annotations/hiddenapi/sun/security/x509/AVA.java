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

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class AVA implements sun.security.util.DerEncoder {

    @UnsupportedAppUsage
    public AVA(sun.security.util.ObjectIdentifier type, sun.security.util.DerValue val) {
        throw new RuntimeException("Stub!");
    }

    AVA(java.io.Reader in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    AVA(java.io.Reader in, java.util.Map<java.lang.String, java.lang.String> keywordMap)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    AVA(java.io.Reader in, int format) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    AVA(java.io.Reader in, int format, java.util.Map<java.lang.String, java.lang.String> keywordMap)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    AVA(sun.security.util.DerValue derval) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    AVA(sun.security.util.DerInputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public sun.security.util.ObjectIdentifier getObjectIdentifier() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public sun.security.util.DerValue getDerValue() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public java.lang.String getValueString() {
        throw new RuntimeException("Stub!");
    }

    private static sun.security.util.DerValue parseHexString(java.io.Reader in, int format)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private sun.security.util.DerValue parseQuotedString(
            java.io.Reader in, java.lang.StringBuilder temp) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private sun.security.util.DerValue parseString(
            java.io.Reader in, int c, int format, java.lang.StringBuilder temp)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.Byte getEmbeddedHexPair(int c1, java.io.Reader in)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String getEmbeddedHexString(java.util.List<java.lang.Byte> hexList)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static boolean isTerminator(int ch, int format) {
        throw new RuntimeException("Stub!");
    }

    private static int readChar(java.io.Reader in, java.lang.String errMsg)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static boolean trailingSpace(java.io.Reader in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public void encode(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void derEncode(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String toKeyword(
            int format, java.util.Map<java.lang.String, java.lang.String> oidMap) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toRFC1779String() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toRFC1779String(
            java.util.Map<java.lang.String, java.lang.String> oidMap) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toRFC2253String() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toRFC2253String(
            java.util.Map<java.lang.String, java.lang.String> oidMap) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public java.lang.String toRFC2253CanonicalString() {
        throw new RuntimeException("Stub!");
    }

    private static boolean isDerString(sun.security.util.DerValue value, boolean canonical) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    boolean hasRFC2253Keyword() {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String toKeywordValueString(java.lang.String keyword) {
        throw new RuntimeException("Stub!");
    }

    static final int DEFAULT = 1; // 0x1

    private static final boolean PRESERVE_OLD_DC_ENCODING;

    static {
        PRESERVE_OLD_DC_ENCODING = false;
    }

    static final int RFC1779 = 2; // 0x2

    static final int RFC2253 = 3; // 0x3

    private static final sun.security.util.Debug debug;

    static {
        debug = null;
    }

    private static final java.lang.String escapedDefault = ",+<>;\"";

    private static final java.lang.String hexDigits = "0123456789ABCDEF";

    final sun.security.util.ObjectIdentifier oid;

    {
        oid = null;
    }

    private static final java.lang.String specialChars1779 = ",=\n+<>#;\\\"";

    private static final java.lang.String specialChars2253 = ",=+<>#;\\\"";

    private static final java.lang.String specialCharsDefault = ",=\n+<>#;\\\" ";

    final sun.security.util.DerValue value;

    {
        value = null;
    }
}
