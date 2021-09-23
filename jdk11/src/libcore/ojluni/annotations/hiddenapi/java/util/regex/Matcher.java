/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util.regex;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class Matcher implements java.util.regex.MatchResult {

    Matcher(java.util.regex.Pattern parent, java.lang.CharSequence text) {
        throw new RuntimeException("Stub!");
    }

    public java.util.regex.Pattern pattern() {
        throw new RuntimeException("Stub!");
    }

    public java.util.regex.MatchResult toMatchResult() {
        throw new RuntimeException("Stub!");
    }

    public java.util.regex.Matcher usePattern(java.util.regex.Pattern newPattern) {
        throw new RuntimeException("Stub!");
    }

    public java.util.regex.Matcher reset() {
        throw new RuntimeException("Stub!");
    }

    public java.util.regex.Matcher reset(java.lang.CharSequence input) {
        throw new RuntimeException("Stub!");
    }

    public int start() {
        throw new RuntimeException("Stub!");
    }

    public int start(int group) {
        throw new RuntimeException("Stub!");
    }

    public int start(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public int end() {
        throw new RuntimeException("Stub!");
    }

    public int end(int group) {
        throw new RuntimeException("Stub!");
    }

    public int end(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String group() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String group(int group) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String group(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public int groupCount() {
        throw new RuntimeException("Stub!");
    }

    public boolean matches() {
        throw new RuntimeException("Stub!");
    }

    public boolean find() {
        throw new RuntimeException("Stub!");
    }

    public boolean find(int start) {
        throw new RuntimeException("Stub!");
    }

    public boolean lookingAt() {
        throw new RuntimeException("Stub!");
    }

    public static java.lang.String quoteReplacement(java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    public java.util.regex.Matcher appendReplacement(
            java.lang.StringBuffer sb, java.lang.String replacement) {
        throw new RuntimeException("Stub!");
    }

    private void appendEvaluated(java.lang.StringBuffer buffer, java.lang.String s) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.StringBuffer appendTail(java.lang.StringBuffer sb) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String replaceAll(java.lang.String replacement) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String replaceFirst(java.lang.String replacement) {
        throw new RuntimeException("Stub!");
    }

    public java.util.regex.Matcher region(int start, int end) {
        throw new RuntimeException("Stub!");
    }

    public int regionStart() {
        throw new RuntimeException("Stub!");
    }

    public int regionEnd() {
        throw new RuntimeException("Stub!");
    }

    public boolean hasTransparentBounds() {
        throw new RuntimeException("Stub!");
    }

    public java.util.regex.Matcher useTransparentBounds(boolean b) {
        throw new RuntimeException("Stub!");
    }

    public boolean hasAnchoringBounds() {
        throw new RuntimeException("Stub!");
    }

    public java.util.regex.Matcher useAnchoringBounds(boolean b) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public boolean hitEnd() {
        throw new RuntimeException("Stub!");
    }

    public boolean requireEnd() {
        throw new RuntimeException("Stub!");
    }

    int getTextLength() {
        throw new RuntimeException("Stub!");
    }

    java.lang.CharSequence getSubSequence(int beginIndex, int endIndex) {
        throw new RuntimeException("Stub!");
    }

    private java.util.regex.Matcher reset(java.lang.CharSequence input, int start, int end) {
        throw new RuntimeException("Stub!");
    }

    private void resetForInput() {
        throw new RuntimeException("Stub!");
    }

    private void ensureMatch() {
        throw new RuntimeException("Stub!");
    }

    private int getMatchedGroupIndex(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    private static native int getMatchedGroupIndex0(long patternAddr, java.lang.String name);

    private static native boolean findImpl(long addr, int startIndex, int[] offsets);

    private static native boolean findNextImpl(long addr, int[] offsets);

    private static native long getNativeFinalizer();

    private static native int groupCountImpl(long addr);

    private static native boolean hitEndImpl(long addr);

    private static native boolean lookingAtImpl(long addr, int[] offsets);

    private static native boolean matchesImpl(long addr, int[] offsets);

    private static native int nativeSize();

    private static native long openImpl(long patternAddr);

    private static native boolean requireEndImpl(long addr);

    private static native void setInputImpl(long addr, java.lang.String s, int start, int end);

    private static native void useAnchoringBoundsImpl(long addr, boolean value);

    private static native void useTransparentBoundsImpl(long addr, boolean value);

    private long address;

    boolean anchoringBounds = true;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    int appendPos = 0; // 0x0

    int from;

    int[] groups;

    private boolean matchFound;

    private java.lang.Runnable nativeFinalizer;

    private java.lang.CharSequence originalInput;

    private java.util.regex.Pattern parentPattern;

    private static final libcore.util.NativeAllocationRegistry registry;

    static {
        registry = null;
    }

    java.lang.String text;

    int to;

    boolean transparentBounds = false;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static final class OffsetBasedMatchResult implements java.util.regex.MatchResult {

        OffsetBasedMatchResult(java.lang.String input, int[] offsets) {
            throw new RuntimeException("Stub!");
        }

        public int start() {
            throw new RuntimeException("Stub!");
        }

        public int start(int group) {
            throw new RuntimeException("Stub!");
        }

        public int end() {
            throw new RuntimeException("Stub!");
        }

        public int end(int group) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String group() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String group(int group) {
            throw new RuntimeException("Stub!");
        }

        public int groupCount() {
            throw new RuntimeException("Stub!");
        }

        private final java.lang.String input;

        {
            input = null;
        }

        private final int[] offsets;

        {
            offsets = new int[0];
        }
    }
}
