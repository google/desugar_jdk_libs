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

package java.util;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class UUID implements java.io.Serializable, java.lang.Comparable<java.util.UUID> {

    private UUID(byte[] data) {
        throw new RuntimeException("Stub!");
    }

    public UUID(long mostSigBits, long leastSigBits) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.UUID randomUUID() {
        throw new RuntimeException("Stub!");
    }

    public static java.util.UUID nameUUIDFromBytes(byte[] name) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.UUID fromString(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public long getLeastSignificantBits() {
        throw new RuntimeException("Stub!");
    }

    public long getMostSignificantBits() {
        throw new RuntimeException("Stub!");
    }

    public int version() {
        throw new RuntimeException("Stub!");
    }

    public int variant() {
        throw new RuntimeException("Stub!");
    }

    public long timestamp() {
        throw new RuntimeException("Stub!");
    }

    public int clockSequence() {
        throw new RuntimeException("Stub!");
    }

    public long node() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.String digits(long val, int digits) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int compareTo(java.util.UUID val) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private final long leastSigBits;

    {
        leastSigBits = 0;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private final long mostSigBits;

    {
        mostSigBits = 0;
    }

    private static final long serialVersionUID = -4856846361193249489L; // 0xbc9903f7986d852fL

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class Holder {

        private Holder() {
            throw new RuntimeException("Stub!");
        }

        static final java.security.SecureRandom numberGenerator;

        static {
            numberGenerator = null;
        }
    }
}
