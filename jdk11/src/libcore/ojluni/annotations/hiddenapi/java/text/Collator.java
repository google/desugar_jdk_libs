/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

/*
 * (C) Copyright Taligent, Inc. 1996-1998 -  All Rights Reserved
 * (C) Copyright IBM Corp. 1996-1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 */

package java.text;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class Collator
        implements java.util.Comparator<java.lang.Object>, java.lang.Cloneable {

    protected Collator() {
        throw new RuntimeException("Stub!");
    }

    Collator(android.icu.text.Collator icuColl) {
        throw new RuntimeException("Stub!");
    }

    public static synchronized java.text.Collator getInstance() {
        throw new RuntimeException("Stub!");
    }

    public static synchronized java.text.Collator getInstance(java.util.Locale desiredLocale) {
        throw new RuntimeException("Stub!");
    }

    public abstract int compare(java.lang.String source, java.lang.String target);

    public int compare(java.lang.Object o1, java.lang.Object o2) {
        throw new RuntimeException("Stub!");
    }

    public abstract java.text.CollationKey getCollationKey(java.lang.String source);

    public boolean equals(java.lang.String source, java.lang.String target) {
        throw new RuntimeException("Stub!");
    }

    public synchronized int getStrength() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setStrength(int newStrength) {
        throw new RuntimeException("Stub!");
    }

    public synchronized int getDecomposition() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setDecomposition(int decompositionMode) {
        throw new RuntimeException("Stub!");
    }

    public static synchronized java.util.Locale[] getAvailableLocales() {
        throw new RuntimeException("Stub!");
    }

    private int decompositionMode_Java_ICU(int mode) {
        throw new RuntimeException("Stub!");
    }

    private int decompositionMode_ICU_Java(int mode) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object clone() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object that) {
        throw new RuntimeException("Stub!");
    }

    public abstract int hashCode();

    public static final int CANONICAL_DECOMPOSITION = 1; // 0x1

    public static final int FULL_DECOMPOSITION = 2; // 0x2

    public static final int IDENTICAL = 3; // 0x3

    public static final int NO_DECOMPOSITION = 0; // 0x0

    public static final int PRIMARY = 0; // 0x0

    public static final int SECONDARY = 1; // 0x1

    public static final int TERTIARY = 2; // 0x2

    @UnsupportedAppUsage
    android.icu.text.Collator icuColl;
}
