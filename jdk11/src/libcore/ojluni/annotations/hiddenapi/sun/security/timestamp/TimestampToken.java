/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.timestamp;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class TimestampToken {

    @android.compat.annotation.UnsupportedAppUsage
    public TimestampToken(byte[] timestampTokenInfo) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public java.util.Date getDate() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.security.x509.AlgorithmId getHashAlgorithm() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public byte[] getHashedMessage() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.math.BigInteger getNonce() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getPolicyID() {
        throw new RuntimeException("Stub!");
    }

    public java.math.BigInteger getSerialNumber() {
        throw new RuntimeException("Stub!");
    }

    private void parse(byte[] timestampTokenInfo) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.util.Date genTime;

    private sun.security.x509.AlgorithmId hashAlgorithm;

    private byte[] hashedMessage;

    private java.math.BigInteger nonce;

    private sun.security.util.ObjectIdentifier policy;

    private java.math.BigInteger serialNumber;

    private int version;
}
