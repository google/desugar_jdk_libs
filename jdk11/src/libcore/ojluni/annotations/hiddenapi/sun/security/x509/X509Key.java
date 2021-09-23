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

package sun.security.x509;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class X509Key implements java.security.PublicKey {

    @android.compat.annotation.UnsupportedAppUsage
    public X509Key() {
        throw new RuntimeException("Stub!");
    }

    private X509Key(sun.security.x509.AlgorithmId algid, sun.security.util.BitArray key)
            throws java.security.InvalidKeyException {
        throw new RuntimeException("Stub!");
    }

    protected void setKey(sun.security.util.BitArray key) {
        throw new RuntimeException("Stub!");
    }

    protected sun.security.util.BitArray getKey() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static java.security.PublicKey parse(sun.security.util.DerValue in)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void parseKeyBits() throws java.io.IOException, java.security.InvalidKeyException {
        throw new RuntimeException("Stub!");
    }

    static java.security.PublicKey buildX509Key(
            sun.security.x509.AlgorithmId algid, sun.security.util.BitArray key)
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

    public byte[] getEncoded() {
        throw new RuntimeException("Stub!");
    }

    public byte[] getEncodedInternal() throws java.security.InvalidKeyException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getFormat() {
        throw new RuntimeException("Stub!");
    }

    public byte[] encode() throws java.security.InvalidKeyException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public void decode(java.io.InputStream in) throws java.security.InvalidKeyException {
        throw new RuntimeException("Stub!");
    }

    public void decode(byte[] encodedKey) throws java.security.InvalidKeyException {
        throw new RuntimeException("Stub!");
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    static void encode(
            sun.security.util.DerOutputStream out,
            sun.security.x509.AlgorithmId algid,
            sun.security.util.BitArray key)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage protected sun.security.x509.AlgorithmId algid;

    private sun.security.util.BitArray bitStringKey;

    @android.compat.annotation.UnsupportedAppUsage protected byte[] encodedKey;

    @Deprecated @android.compat.annotation.UnsupportedAppUsage protected byte[] key;

    private static final long serialVersionUID = -5359250853002055002L; // 0xb5a01dbe649a72a6L

    @Deprecated @android.compat.annotation.UnsupportedAppUsage private int unusedBits = 0; // 0x0
}
