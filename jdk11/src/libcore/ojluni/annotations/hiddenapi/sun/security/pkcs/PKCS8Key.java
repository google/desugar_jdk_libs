/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.pkcs;

import java.io.*;
import sun.security.util.*;
import sun.security.x509.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class PKCS8Key implements java.security.PrivateKey {

    @android.compat.annotation.UnsupportedAppUsage
    public PKCS8Key() {
        throw new RuntimeException("Stub!");
    }

    private PKCS8Key(sun.security.x509.AlgorithmId algid, byte[] key)
            throws java.security.InvalidKeyException {
        throw new RuntimeException("Stub!");
    }

    public static sun.security.pkcs.PKCS8Key parse(sun.security.util.DerValue in)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public static java.security.PrivateKey parseKey(sun.security.util.DerValue in)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void parseKeyBits() throws java.io.IOException, java.security.InvalidKeyException {
        throw new RuntimeException("Stub!");
    }

    static java.security.PrivateKey buildPKCS8Key(sun.security.x509.AlgorithmId algid, byte[] key)
            throws java.io.IOException, java.security.InvalidKeyException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getAlgorithm() {
        throw new RuntimeException("Stub!");
    }

    public sun.security.x509.AlgorithmId getAlgorithmId() {
        throw new RuntimeException("Stub!");
    }

    public final void encode(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public synchronized byte[] getEncoded() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getFormat() {
        throw new RuntimeException("Stub!");
    }

    public byte[] encode() throws java.security.InvalidKeyException {
        throw new RuntimeException("Stub!");
    }

    public void decode(java.io.InputStream in) throws java.security.InvalidKeyException {
        throw new RuntimeException("Stub!");
    }

    public void decode(byte[] encodedKey) throws java.security.InvalidKeyException {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Object writeReplace() throws java.io.ObjectStreamException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    static void encode(
            sun.security.util.DerOutputStream out, sun.security.x509.AlgorithmId algid, byte[] key)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object object) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage protected sun.security.x509.AlgorithmId algid;

    @android.compat.annotation.UnsupportedAppUsage protected byte[] encodedKey;

    @android.compat.annotation.UnsupportedAppUsage protected byte[] key;

    private static final long serialVersionUID = -3836890099307167124L; // 0xcac0a0c88c95426cL

    public static final java.math.BigInteger version;

    static {
        version = null;
    }
}
