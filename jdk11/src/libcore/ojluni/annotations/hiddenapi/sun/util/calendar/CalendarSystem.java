/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
public abstract class CalendarSystem {

    public CalendarSystem() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static sun.util.calendar.Gregorian getGregorianCalendar() {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static sun.util.calendar.CalendarSystem forName(java.lang.String calendarName) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Properties getCalendarProperties() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public abstract java.lang.String getName();

    public abstract sun.util.calendar.CalendarDate getCalendarDate();

    public abstract sun.util.calendar.CalendarDate getCalendarDate(long millis);

    public abstract sun.util.calendar.CalendarDate getCalendarDate(
            long millis, sun.util.calendar.CalendarDate date);

    public abstract sun.util.calendar.CalendarDate getCalendarDate(
            long millis, java.util.TimeZone zone);

    public abstract sun.util.calendar.CalendarDate newCalendarDate();

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public abstract sun.util.calendar.CalendarDate newCalendarDate(java.util.TimeZone zone);

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public abstract long getTime(sun.util.calendar.CalendarDate date);

    public abstract int getYearLength(sun.util.calendar.CalendarDate date);

    public abstract int getYearLengthInMonths(sun.util.calendar.CalendarDate date);

    public abstract int getMonthLength(sun.util.calendar.CalendarDate date);

    public abstract int getWeekLength();

    public abstract sun.util.calendar.Era getEra(java.lang.String eraName);

    public abstract sun.util.calendar.Era[] getEras();

    public abstract void setEra(sun.util.calendar.CalendarDate date, java.lang.String eraName);

    public abstract sun.util.calendar.CalendarDate getNthDayOfWeek(
            int nth, int dayOfWeek, sun.util.calendar.CalendarDate date);

    public abstract sun.util.calendar.CalendarDate setTimeOfDay(
            sun.util.calendar.CalendarDate date, int timeOfDay);

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public abstract boolean validate(sun.util.calendar.CalendarDate date);

    public abstract boolean normalize(sun.util.calendar.CalendarDate date);

    private static final sun.util.calendar.Gregorian GREGORIAN_INSTANCE;

    static {
        GREGORIAN_INSTANCE = null;
    }

    private static final java.util.concurrent.ConcurrentMap<
                    java.lang.String, sun.util.calendar.CalendarSystem>
            calendars;

    static {
        calendars = null;
    }

    private static final java.util.Map<java.lang.String, java.lang.Class<?>> names;

    static {
        names = null;
    }
}
