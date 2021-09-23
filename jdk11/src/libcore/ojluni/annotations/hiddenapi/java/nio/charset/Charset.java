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

package java.nio.charset;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class Charset implements java.lang.Comparable<java.nio.charset.Charset> {

    protected Charset(java.lang.String canonicalName, java.lang.String[] aliases) {
        throw new RuntimeException("Stub!");
    }

    static boolean atBugLevel(java.lang.String bl) {
        throw new RuntimeException("Stub!");
    }

    private static void checkName(java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    private static void cache(java.lang.String charsetName, java.nio.charset.Charset cs) {
        throw new RuntimeException("Stub!");
    }

    private static java.util.Iterator<java.nio.charset.spi.CharsetProvider> providers() {
        throw new RuntimeException("Stub!");
    }

    private static java.nio.charset.Charset lookupViaProviders(java.lang.String charsetName) {
        throw new RuntimeException("Stub!");
    }

    private static java.nio.charset.Charset lookup(java.lang.String charsetName) {
        throw new RuntimeException("Stub!");
    }

    private static java.nio.charset.Charset lookup2(java.lang.String charsetName) {
        throw new RuntimeException("Stub!");
    }

    public static boolean isSupported(java.lang.String charsetName) {
        throw new RuntimeException("Stub!");
    }

    public static java.nio.charset.Charset forName(java.lang.String charsetName) {
        throw new RuntimeException("Stub!");
    }

    public static java.nio.charset.Charset forNameUEE(java.lang.String charsetName)
            throws java.io.UnsupportedEncodingException {
        throw new RuntimeException("Stub!");
    }

    private static void put(
            java.util.Iterator<java.nio.charset.Charset> i,
            java.util.Map<java.lang.String, java.nio.charset.Charset> m) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.SortedMap<java.lang.String, java.nio.charset.Charset>
            availableCharsets() {
        throw new RuntimeException("Stub!");
    }

    public static java.nio.charset.Charset defaultCharset() {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.String name() {
        throw new RuntimeException("Stub!");
    }

    public final java.util.Set<java.lang.String> aliases() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String displayName() {
        throw new RuntimeException("Stub!");
    }

    public final boolean isRegistered() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String displayName(java.util.Locale locale) {
        throw new RuntimeException("Stub!");
    }

    public abstract boolean contains(java.nio.charset.Charset cs);

    public abstract java.nio.charset.CharsetDecoder newDecoder();

    public abstract java.nio.charset.CharsetEncoder newEncoder();

    public boolean canEncode() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.CharBuffer decode(java.nio.ByteBuffer bb) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer encode(java.nio.CharBuffer cb) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.ByteBuffer encode(java.lang.String str) {
        throw new RuntimeException("Stub!");
    }

    public final int compareTo(java.nio.charset.Charset that) {
        throw new RuntimeException("Stub!");
    }

    public final int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public final boolean equals(java.lang.Object ob) {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    private java.util.Set<java.lang.String> aliasSet;

    private final java.lang.String[] aliases;

    {
        aliases = new java.lang.String[0];
    }

    private static volatile java.lang.String bugLevel;

    private static volatile java.util.Map.Entry<java.lang.String, java.nio.charset.Charset> cache1;

    private static final java.util.HashMap<java.lang.String, java.nio.charset.Charset> cache2;

    static {
        cache2 = null;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private static java.nio.charset.Charset defaultCharset;

    private static java.lang.ThreadLocal<java.lang.ThreadLocal<?>> gate;

    private final java.lang.String name;

    {
        name = null;
    }
}
