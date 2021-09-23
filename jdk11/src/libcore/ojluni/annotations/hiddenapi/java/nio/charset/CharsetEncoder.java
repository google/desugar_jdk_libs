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

// -- This file was mechanically generated: Do not edit! -- //

package java.nio.charset;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class CharsetEncoder {

    protected CharsetEncoder(
            java.nio.charset.Charset cs,
            float averageBytesPerChar,
            float maxBytesPerChar,
            byte[] replacement) {
        throw new RuntimeException("Stub!");
    }

    CharsetEncoder(
            java.nio.charset.Charset cs,
            float averageBytesPerChar,
            float maxBytesPerChar,
            byte[] replacement,
            boolean trusted) {
        throw new RuntimeException("Stub!");
    }

    protected CharsetEncoder(
            java.nio.charset.Charset cs, float averageBytesPerChar, float maxBytesPerChar) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.charset.Charset charset() {
        throw new RuntimeException("Stub!");
    }

    public final byte[] replacement() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.charset.CharsetEncoder replaceWith(byte[] newReplacement) {
        throw new RuntimeException("Stub!");
    }

    protected void implReplaceWith(byte[] newReplacement) {
        throw new RuntimeException("Stub!");
    }

    public boolean isLegalReplacement(byte[] repl) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.charset.CodingErrorAction malformedInputAction() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.charset.CharsetEncoder onMalformedInput(
            java.nio.charset.CodingErrorAction newAction) {
        throw new RuntimeException("Stub!");
    }

    protected void implOnMalformedInput(java.nio.charset.CodingErrorAction newAction) {
        throw new RuntimeException("Stub!");
    }

    public java.nio.charset.CodingErrorAction unmappableCharacterAction() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.charset.CharsetEncoder onUnmappableCharacter(
            java.nio.charset.CodingErrorAction newAction) {
        throw new RuntimeException("Stub!");
    }

    protected void implOnUnmappableCharacter(java.nio.charset.CodingErrorAction newAction) {
        throw new RuntimeException("Stub!");
    }

    public final float averageBytesPerChar() {
        throw new RuntimeException("Stub!");
    }

    public final float maxBytesPerChar() {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.charset.CoderResult encode(
            java.nio.CharBuffer in, java.nio.ByteBuffer out, boolean endOfInput) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.charset.CoderResult flush(java.nio.ByteBuffer out) {
        throw new RuntimeException("Stub!");
    }

    protected java.nio.charset.CoderResult implFlush(java.nio.ByteBuffer out) {
        throw new RuntimeException("Stub!");
    }

    public final java.nio.charset.CharsetEncoder reset() {
        throw new RuntimeException("Stub!");
    }

    protected void implReset() {
        throw new RuntimeException("Stub!");
    }

    protected abstract java.nio.charset.CoderResult encodeLoop(
            java.nio.CharBuffer in, java.nio.ByteBuffer out);

    public final java.nio.ByteBuffer encode(java.nio.CharBuffer in)
            throws java.nio.charset.CharacterCodingException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private boolean canEncode(java.nio.CharBuffer cb) {
        throw new RuntimeException("Stub!");
    }

    public boolean canEncode(char c) {
        throw new RuntimeException("Stub!");
    }

    public boolean canEncode(java.lang.CharSequence cs) {
        throw new RuntimeException("Stub!");
    }

    private void throwIllegalStateException(int from, int to) {
        throw new RuntimeException("Stub!");
    }

    private static final int ST_CODING = 1; // 0x1

    private static final int ST_END = 2; // 0x2

    private static final int ST_FLUSHED = 3; // 0x3

    private static final int ST_RESET = 0; // 0x0

    private final float averageBytesPerChar;

    {
        averageBytesPerChar = 0;
    }

    private java.lang.ref.WeakReference<java.nio.charset.CharsetDecoder> cachedDecoder;

    private final java.nio.charset.Charset charset;

    {
        charset = null;
    }

    private java.nio.charset.CodingErrorAction malformedInputAction;

    private final float maxBytesPerChar;

    {
        maxBytesPerChar = 0;
    }

    private byte[] replacement;

    private int state = 0; // 0x0

    private static java.lang.String[] stateNames;

    private java.nio.charset.CodingErrorAction unmappableCharacterAction;
}
