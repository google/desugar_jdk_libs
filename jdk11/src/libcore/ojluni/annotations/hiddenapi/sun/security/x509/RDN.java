/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2002, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.util.*;
import sun.security.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class RDN {

    public RDN(java.lang.String name) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public RDN(java.lang.String name, java.util.Map<java.lang.String, java.lang.String> keywordMap)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    RDN(java.lang.String name, java.lang.String format) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    RDN(
            java.lang.String name,
            java.lang.String format,
            java.util.Map<java.lang.String, java.lang.String> keywordMap)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    RDN(sun.security.util.DerValue rdn) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    RDN(int i) {
        throw new RuntimeException("Stub!");
    }

    public RDN(sun.security.x509.AVA ava) {
        throw new RuntimeException("Stub!");
    }

    public RDN(sun.security.x509.AVA[] avas) {
        throw new RuntimeException("Stub!");
    }

    public java.util.List<sun.security.x509.AVA> avas() {
        throw new RuntimeException("Stub!");
    }

    public int size() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    sun.security.util.DerValue findAttribute(sun.security.util.ObjectIdentifier oid) {
        throw new RuntimeException("Stub!");
    }

    void encode(sun.security.util.DerOutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toRFC1779String() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toRFC1779String(
            java.util.Map<java.lang.String, java.lang.String> oidMap) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toRFC2253String() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toRFC2253String(
            java.util.Map<java.lang.String, java.lang.String> oidMap) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toRFC2253String(boolean canonical) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String toRFC2253StringInternal(
            boolean canonical, java.util.Map<java.lang.String, java.lang.String> oidMap) {
        throw new RuntimeException("Stub!");
    }

    final sun.security.x509.AVA[] assertion;

    {
        assertion = new sun.security.x509.AVA[0];
    }

    private volatile java.util.List<sun.security.x509.AVA> avaList;

    private volatile java.lang.String canonicalString;
}
