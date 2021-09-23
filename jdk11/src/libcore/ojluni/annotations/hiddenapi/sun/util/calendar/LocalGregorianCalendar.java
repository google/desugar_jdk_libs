/*
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

package sun.util.calendar;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class LocalGregorianCalendar extends sun.util.calendar.BaseCalendar {

    private LocalGregorianCalendar(java.lang.String name, sun.util.calendar.Era[] eras) {
        throw new RuntimeException("Stub!");
    }

    static sun.util.calendar.LocalGregorianCalendar getLocalGregorianCalendar(
            java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public sun.util.calendar.LocalGregorianCalendar.Date getCalendarDate() {
        throw new RuntimeException("Stub!");
    }

    public sun.util.calendar.LocalGregorianCalendar.Date getCalendarDate(long millis) {
        throw new RuntimeException("Stub!");
    }

    public sun.util.calendar.LocalGregorianCalendar.Date getCalendarDate(
            long millis, java.util.TimeZone zone) {
        throw new RuntimeException("Stub!");
    }

    public sun.util.calendar.LocalGregorianCalendar.Date getCalendarDate(
            long millis, sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    private sun.util.calendar.LocalGregorianCalendar.Date adjustYear(
            sun.util.calendar.LocalGregorianCalendar.Date ldate, long millis, int zoneOffset) {
        throw new RuntimeException("Stub!");
    }

    public sun.util.calendar.LocalGregorianCalendar.Date newCalendarDate() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public sun.util.calendar.LocalGregorianCalendar.Date newCalendarDate(java.util.TimeZone zone) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public boolean validate(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    private boolean validateEra(sun.util.calendar.Era era) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public boolean normalize(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    void normalizeMonth(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    void normalizeYear(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    public boolean isLeapYear(int gregorianYear) {
        throw new RuntimeException("Stub!");
    }

    public boolean isLeapYear(sun.util.calendar.Era era, int year) {
        throw new RuntimeException("Stub!");
    }

    public void getCalendarDateFromFixedDate(sun.util.calendar.CalendarDate date, long fixedDate) {
        throw new RuntimeException("Stub!");
    }

    private sun.util.calendar.Era[] eras;

    private java.lang.String name;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static class Date extends sun.util.calendar.BaseCalendar.Date {

        protected Date() {
            throw new RuntimeException("Stub!");
        }

        protected Date(java.util.TimeZone zone) {
            throw new RuntimeException("Stub!");
        }

        @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        public sun.util.calendar.LocalGregorianCalendar.Date setEra(sun.util.calendar.Era era) {
            throw new RuntimeException("Stub!");
        }

        public sun.util.calendar.LocalGregorianCalendar.Date addYear(int localYear) {
            throw new RuntimeException("Stub!");
        }

        @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        public sun.util.calendar.LocalGregorianCalendar.Date setYear(int localYear) {
            throw new RuntimeException("Stub!");
        }

        @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        public int getNormalizedYear() {
            throw new RuntimeException("Stub!");
        }

        @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        public void setNormalizedYear(int normalizedYear) {
            throw new RuntimeException("Stub!");
        }

        void setLocalEra(sun.util.calendar.Era era) {
            throw new RuntimeException("Stub!");
        }

        void setLocalYear(int year) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        private int gregorianYear = -2147483648; // 0x80000000
    }
}
