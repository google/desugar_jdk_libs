/*
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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
public abstract class BaseCalendar extends sun.util.calendar.AbstractCalendar {

    public BaseCalendar() {
        throw new RuntimeException("Stub!");
    }

    public boolean validate(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    public boolean normalize(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    void normalizeMonth(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    public int getYearLength(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    public int getYearLengthInMonths(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    public int getMonthLength(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    private int getMonthLength(int year, int month) {
        throw new RuntimeException("Stub!");
    }

    public long getDayOfYear(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    final long getDayOfYear(int year, int month, int dayOfMonth) {
        throw new RuntimeException("Stub!");
    }

    public long getFixedDate(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    public long getFixedDate(
            int year, int month, int dayOfMonth, sun.util.calendar.BaseCalendar.Date cache) {
        throw new RuntimeException("Stub!");
    }

    public void getCalendarDateFromFixedDate(sun.util.calendar.CalendarDate date, long fixedDate) {
        throw new RuntimeException("Stub!");
    }

    public int getDayOfWeek(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    public static final int getDayOfWeekFromFixedDate(long fixedDate) {
        throw new RuntimeException("Stub!");
    }

    public int getYearFromFixedDate(long fixedDate) {
        throw new RuntimeException("Stub!");
    }

    final int getGregorianYearFromFixedDate(long fixedDate) {
        throw new RuntimeException("Stub!");
    }

    protected boolean isLeapYear(sun.util.calendar.CalendarDate date) {
        throw new RuntimeException("Stub!");
    }

    boolean isLeapYear(int normalizedYear) {
        throw new RuntimeException("Stub!");
    }

    static final int[] ACCUMULATED_DAYS_IN_MONTH;

    static {
        ACCUMULATED_DAYS_IN_MONTH = new int[0];
    }

    static final int[] ACCUMULATED_DAYS_IN_MONTH_LEAP;

    static {
        ACCUMULATED_DAYS_IN_MONTH_LEAP = new int[0];
    }

    public static final int APRIL = 4; // 0x4

    public static final int AUGUST = 8; // 0x8

    private static final int BASE_YEAR = 1970; // 0x7b2

    static final int[] DAYS_IN_MONTH;

    static {
        DAYS_IN_MONTH = new int[0];
    }

    public static final int DECEMBER = 12; // 0xc

    public static final int FEBRUARY = 2; // 0x2

    private static final int[] FIXED_DATES;

    static {
        FIXED_DATES = new int[0];
    }

    public static final int FRIDAY = 6; // 0x6

    public static final int JANUARY = 1; // 0x1

    public static final int JULY = 7; // 0x7

    public static final int JUNE = 6; // 0x6

    public static final int MARCH = 3; // 0x3

    public static final int MAY = 5; // 0x5

    public static final int MONDAY = 2; // 0x2

    public static final int NOVEMBER = 11; // 0xb

    public static final int OCTOBER = 10; // 0xa

    public static final int SATURDAY = 7; // 0x7

    public static final int SEPTEMBER = 9; // 0x9

    public static final int SUNDAY = 1; // 0x1

    public static final int THURSDAY = 5; // 0x5

    public static final int TUESDAY = 3; // 0x3

    public static final int WEDNESDAY = 4; // 0x4

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public abstract static class Date extends sun.util.calendar.CalendarDate {

        protected Date() {
            throw new RuntimeException("Stub!");
        }

        protected Date(java.util.TimeZone zone) {
            throw new RuntimeException("Stub!");
        }

        public sun.util.calendar.BaseCalendar.Date setNormalizedDate(
                int normalizedYear, int month, int dayOfMonth) {
            throw new RuntimeException("Stub!");
        }

        @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        public abstract int getNormalizedYear();

        @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
        public abstract void setNormalizedYear(int normalizedYear);

        protected final boolean hit(int year) {
            throw new RuntimeException("Stub!");
        }

        protected final boolean hit(long fixedDate) {
            throw new RuntimeException("Stub!");
        }

        protected int getCachedYear() {
            throw new RuntimeException("Stub!");
        }

        protected long getCachedJan1() {
            throw new RuntimeException("Stub!");
        }

        protected void setCache(int year, long jan1, int len) {
            throw new RuntimeException("Stub!");
        }

        long cachedFixedDateJan1 = 731581L; // 0xb29bdL

        long cachedFixedDateNextJan1;

        int cachedYear = 2004; // 0x7d4
    }
}
