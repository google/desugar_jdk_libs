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

package java.io;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class Console implements java.io.Flushable {

    private Console() {
        throw new RuntimeException("Stub!");
    }

    private Console(java.io.InputStream inStream, java.io.OutputStream outStream) {
        throw new RuntimeException("Stub!");
    }

    public java.io.PrintWriter writer() {
        throw new RuntimeException("Stub!");
    }

    public java.io.Reader reader() {
        throw new RuntimeException("Stub!");
    }

    public java.io.Console format(java.lang.String fmt, java.lang.Object... args) {
        throw new RuntimeException("Stub!");
    }

    public java.io.Console printf(java.lang.String format, java.lang.Object... args) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String readLine(java.lang.String fmt, java.lang.Object... args) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String readLine() {
        throw new RuntimeException("Stub!");
    }

    public char[] readPassword(java.lang.String fmt, java.lang.Object... args) {
        throw new RuntimeException("Stub!");
    }

    public char[] readPassword() {
        throw new RuntimeException("Stub!");
    }

    public void flush() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private static native java.lang.String encoding();

    private static native boolean echo(boolean on) throws java.io.IOException;

    private char[] readline(boolean zeroOut) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private char[] grow() {
        throw new RuntimeException("Stub!");
    }

    public static java.io.Console console() {
        throw new RuntimeException("Stub!");
    }

    private static native boolean istty();

    private static java.io.Console cons;

    private java.nio.charset.Charset cs;

    private static boolean echoOff;

    private java.util.Formatter formatter;

    private java.io.Writer out;

    private java.io.PrintWriter pw;

    private char[] rcb;

    private java.lang.Object readLock;

    private java.io.Reader reader;

    private java.lang.Object writeLock;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    class LineReader extends java.io.Reader {

        LineReader(java.io.Reader in) {
            throw new RuntimeException("Stub!");
        }

        public void close() {
            throw new RuntimeException("Stub!");
        }

        public boolean ready() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int read(char[] cbuf, int offset, int length) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private char[] cb;

        private java.io.Reader in;

        boolean leftoverLF;

        private int nChars;

        private int nextChar;
    }
}
