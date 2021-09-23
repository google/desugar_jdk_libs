/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
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

package sun.util.calendar;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class Era {

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public Era(java.lang.String name, java.lang.String abbr, long since, boolean localTime) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getDisplayName(java.util.Locale locale) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public java.lang.String getAbbreviation() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getDiaplayAbbreviation(java.util.Locale locale) {
        throw new RuntimeException("Stub!");
    }

    public long getSince(java.util.TimeZone zone) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public sun.util.calendar.CalendarDate getSinceDate() {
        throw new RuntimeException("Stub!");
    }

    public boolean isLocalTime() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object o) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    private final java.lang.String abbr;

    {
        abbr = null;
    }

    private int hash = 0; // 0x0

    private final boolean localTime;

    {
        localTime = false;
    }

    private final java.lang.String name;

    {
        name = null;
    }

    private final long since;

    {
        since = 0;
    }

    private final sun.util.calendar.CalendarDate sinceDate;

    {
        sinceDate = null;
    }
}
