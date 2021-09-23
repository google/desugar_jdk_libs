/*
 * Copyright (c) 1997, 2002, Oracle and/or its affiliates. All rights reserved.
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

import sun.security.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class SerialNumber {

    public SerialNumber(java.math.BigInteger num) {
        throw new RuntimeException("Stub!");
    }

    public SerialNumber(int num) {
        throw new RuntimeException("Stub!");
    }

    public SerialNumber(sun.security.util.DerInputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public SerialNumber(sun.security.util.DerValue val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public SerialNumber(java.io.InputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void construct(sun.security.util.DerValue derVal) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public void encode(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.math.BigInteger getNumber() {
        throw new RuntimeException("Stub!");
    }

    private java.math.BigInteger serialNum;
}
