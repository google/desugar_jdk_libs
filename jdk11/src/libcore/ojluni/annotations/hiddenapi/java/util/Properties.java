/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class Properties extends java.util.Hashtable<java.lang.Object, java.lang.Object> {

    public Properties() {
        throw new RuntimeException("Stub!");
    }

    public Properties(java.util.Properties defaults) {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.Object setProperty(java.lang.String key, java.lang.String value) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void load(java.io.Reader reader) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void load(java.io.InputStream inStream) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void load0(java.util.Properties.LineReader lr) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String loadConvert(char[] in, int off, int len, char[] convtBuf) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private java.lang.String saveConvert(
            java.lang.String theString, boolean escapeSpace, boolean escapeUnicode) {
        throw new RuntimeException("Stub!");
    }

    private static void writeComments(java.io.BufferedWriter bw, java.lang.String comments)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public void save(java.io.OutputStream out, java.lang.String comments) {
        throw new RuntimeException("Stub!");
    }

    public void store(java.io.Writer writer, java.lang.String comments) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void store(java.io.OutputStream out, java.lang.String comments)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void store0(java.io.BufferedWriter bw, java.lang.String comments, boolean escUnicode)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public synchronized void loadFromXML(java.io.InputStream in)
            throws java.io.IOException, java.util.InvalidPropertiesFormatException {
        throw new RuntimeException("Stub!");
    }

    public void storeToXML(java.io.OutputStream os, java.lang.String comment)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void storeToXML(
            java.io.OutputStream os, java.lang.String comment, java.lang.String encoding)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getProperty(java.lang.String key) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getProperty(java.lang.String key, java.lang.String defaultValue) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<?> propertyNames() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Set<java.lang.String> stringPropertyNames() {
        throw new RuntimeException("Stub!");
    }

    public void list(java.io.PrintStream out) {
        throw new RuntimeException("Stub!");
    }

    public void list(java.io.PrintWriter out) {
        throw new RuntimeException("Stub!");
    }

    private synchronized void enumerate(java.util.Hashtable<java.lang.String, java.lang.Object> h) {
        throw new RuntimeException("Stub!");
    }

    private synchronized void enumerateStringProperties(
            java.util.Hashtable<java.lang.String, java.lang.String> h) {
        throw new RuntimeException("Stub!");
    }

    private static char toHex(int nibble) {
        throw new RuntimeException("Stub!");
    }

    protected java.util.Properties defaults;

    private static final char[] hexDigit;

    static {
        hexDigit = new char[0];
    }

    private static final long serialVersionUID = 4112578634029874840L; // 0x3912d07a70363e98L

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    class LineReader {

        public LineReader(java.io.InputStream inStream) {
            throw new RuntimeException("Stub!");
        }

        public LineReader(java.io.Reader reader) {
            throw new RuntimeException("Stub!");
        }

        int readLine() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        byte[] inByteBuf;

        char[] inCharBuf;

        int inLimit = 0; // 0x0

        int inOff = 0; // 0x0

        java.io.InputStream inStream;

        char[] lineBuf;

        java.io.Reader reader;
    }
}
