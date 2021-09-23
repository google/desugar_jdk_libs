/*
 * Copyright (C) 2014 The Android Open Source Project
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

/*
 * (C) Copyright Taligent, Inc. 1996-1998 - All Rights Reserved
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

package java.util;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class Calendar
        implements java.io.Serializable,
                java.lang.Cloneable,
                java.lang.Comparable<java.util.Calendar> {

    protected Calendar() {
        throw new RuntimeException("Stub!");
    }

    protected Calendar(java.util.TimeZone zone, java.util.Locale aLocale) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Calendar getInstance() {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Calendar getInstance(java.util.TimeZone zone) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Calendar getInstance(java.util.Locale aLocale) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Calendar getInstance(
            java.util.TimeZone zone, java.util.Locale aLocale) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Calendar getJapaneseImperialInstance(
            java.util.TimeZone zone, java.util.Locale aLocale) {
        throw new RuntimeException("Stub!");
    }

    private static java.util.Calendar createCalendar(
            java.util.TimeZone zone, java.util.Locale aLocale) {
        throw new RuntimeException("Stub!");
    }

    public static synchronized java.util.Locale[] getAvailableLocales() {
        throw new RuntimeException("Stub!");
    }

    protected abstract void computeTime();

    protected abstract void computeFields();

    public final java.util.Date getTime() {
        throw new RuntimeException("Stub!");
    }

    public final void setTime(java.util.Date date) {
        throw new RuntimeException("Stub!");
    }

    public long getTimeInMillis() {
        throw new RuntimeException("Stub!");
    }

    public void setTimeInMillis(long millis) {
        throw new RuntimeException("Stub!");
    }

    public int get(int field) {
        throw new RuntimeException("Stub!");
    }

    protected final int internalGet(int field) {
        throw new RuntimeException("Stub!");
    }

    final void internalSet(int field, int value) {
        throw new RuntimeException("Stub!");
    }

    public void set(int field, int value) {
        throw new RuntimeException("Stub!");
    }

    public final void set(int year, int month, int date) {
        throw new RuntimeException("Stub!");
    }

    public final void set(int year, int month, int date, int hourOfDay, int minute) {
        throw new RuntimeException("Stub!");
    }

    public final void set(int year, int month, int date, int hourOfDay, int minute, int second) {
        throw new RuntimeException("Stub!");
    }

    public final void clear() {
        throw new RuntimeException("Stub!");
    }

    public final void clear(int field) {
        throw new RuntimeException("Stub!");
    }

    public final boolean isSet(int field) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getDisplayName(int field, int style, java.util.Locale locale) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Map<java.lang.String, java.lang.Integer> getDisplayNames(
            int field, int style, java.util.Locale locale) {
        throw new RuntimeException("Stub!");
    }

    private java.util.Map<java.lang.String, java.lang.Integer> getDisplayNamesImpl(
            int field, int style, java.util.Locale locale) {
        throw new RuntimeException("Stub!");
    }

    boolean checkDisplayNameParams(
            int field,
            int style,
            int minStyle,
            int maxStyle,
            java.util.Locale locale,
            int fieldMask) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String[] getFieldStrings(
            int field, int style, java.text.DateFormatSymbols symbols) {
        throw new RuntimeException("Stub!");
    }

    protected void complete() {
        throw new RuntimeException("Stub!");
    }

    final boolean isExternallySet(int field) {
        throw new RuntimeException("Stub!");
    }

    final int getSetStateFields() {
        throw new RuntimeException("Stub!");
    }

    final void setFieldsComputed(int fieldMask) {
        throw new RuntimeException("Stub!");
    }

    final void setFieldsNormalized(int fieldMask) {
        throw new RuntimeException("Stub!");
    }

    final boolean isPartiallyNormalized() {
        throw new RuntimeException("Stub!");
    }

    final boolean isFullyNormalized() {
        throw new RuntimeException("Stub!");
    }

    final void setUnnormalized() {
        throw new RuntimeException("Stub!");
    }

    static boolean isFieldSet(int fieldMask, int field) {
        throw new RuntimeException("Stub!");
    }

    final int selectFields() {
        throw new RuntimeException("Stub!");
    }

    int getBaseStyle(int style) {
        throw new RuntimeException("Stub!");
    }

    private int toStandaloneStyle(int style) {
        throw new RuntimeException("Stub!");
    }

    private boolean isStandaloneStyle(int style) {
        throw new RuntimeException("Stub!");
    }

    private boolean isNarrowStyle(int style) {
        throw new RuntimeException("Stub!");
    }

    private boolean isNarrowFormatStyle(int style) {
        throw new RuntimeException("Stub!");
    }

    private static int aggregateStamp(int stamp_a, int stamp_b) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.Set<java.lang.String> getAvailableCalendarTypes() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getCalendarType() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(java.lang.Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public boolean before(java.lang.Object when) {
        throw new RuntimeException("Stub!");
    }

    public boolean after(java.lang.Object when) {
        throw new RuntimeException("Stub!");
    }

    public int compareTo(java.util.Calendar anotherCalendar) {
        throw new RuntimeException("Stub!");
    }

    public abstract void add(int field, int amount);

    public abstract void roll(int field, boolean up);

    public void roll(int field, int amount) {
        throw new RuntimeException("Stub!");
    }

    public void setTimeZone(java.util.TimeZone value) {
        throw new RuntimeException("Stub!");
    }

    public java.util.TimeZone getTimeZone() {
        throw new RuntimeException("Stub!");
    }

    java.util.TimeZone getZone() {
        throw new RuntimeException("Stub!");
    }

    void setZoneShared(boolean shared) {
        throw new RuntimeException("Stub!");
    }

    public void setLenient(boolean lenient) {
        throw new RuntimeException("Stub!");
    }

    public boolean isLenient() {
        throw new RuntimeException("Stub!");
    }

    public void setFirstDayOfWeek(int value) {
        throw new RuntimeException("Stub!");
    }

    public int getFirstDayOfWeek() {
        throw new RuntimeException("Stub!");
    }

    public void setMinimalDaysInFirstWeek(int value) {
        throw new RuntimeException("Stub!");
    }

    public int getMinimalDaysInFirstWeek() {
        throw new RuntimeException("Stub!");
    }

    public boolean isWeekDateSupported() {
        throw new RuntimeException("Stub!");
    }

    public int getWeekYear() {
        throw new RuntimeException("Stub!");
    }

    public void setWeekDate(int weekYear, int weekOfYear, int dayOfWeek) {
        throw new RuntimeException("Stub!");
    }

    public int getWeeksInWeekYear() {
        throw new RuntimeException("Stub!");
    }

    public abstract int getMinimum(int field);

    public abstract int getMaximum(int field);

    public abstract int getGreatestMinimum(int field);

    public abstract int getLeastMaximum(int field);

    public int getActualMinimum(int field) {
        throw new RuntimeException("Stub!");
    }

    public int getActualMaximum(int field) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object clone() {
        throw new RuntimeException("Stub!");
    }

    static java.lang.String getFieldName(int field) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    private static void appendValue(
            java.lang.StringBuilder sb, java.lang.String item, boolean valid, long value) {
        throw new RuntimeException("Stub!");
    }

    private void setWeekCountData(java.util.Locale desiredLocale) {
        throw new RuntimeException("Stub!");
    }

    private void updateTime() {
        throw new RuntimeException("Stub!");
    }

    private int compareTo(long t) {
        throw new RuntimeException("Stub!");
    }

    private static long getMillisOf(java.util.Calendar calendar) {
        throw new RuntimeException("Stub!");
    }

    private void adjustStamp() {
        throw new RuntimeException("Stub!");
    }

    private void invalidateWeekFields() {
        throw new RuntimeException("Stub!");
    }

    private synchronized void writeObject(java.io.ObjectOutputStream stream)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final java.time.Instant toInstant() {
        throw new RuntimeException("Stub!");
    }

    static final int ALL_FIELDS = 131071; // 0x1ffff

    public static final int ALL_STYLES = 0; // 0x0

    public static final int AM = 0; // 0x0

    public static final int AM_PM = 9; // 0x9

    static final int AM_PM_MASK = 512; // 0x200

    public static final int APRIL = 3; // 0x3

    public static final int AUGUST = 7; // 0x7

    private static final int COMPUTED = 1; // 0x1

    public static final int DATE = 5; // 0x5

    static final int DATE_MASK = 32; // 0x20

    public static final int DAY_OF_MONTH = 5; // 0x5

    static final int DAY_OF_MONTH_MASK = 32; // 0x20

    public static final int DAY_OF_WEEK = 7; // 0x7

    public static final int DAY_OF_WEEK_IN_MONTH = 8; // 0x8

    static final int DAY_OF_WEEK_IN_MONTH_MASK = 256; // 0x100

    static final int DAY_OF_WEEK_MASK = 128; // 0x80

    public static final int DAY_OF_YEAR = 6; // 0x6

    static final int DAY_OF_YEAR_MASK = 64; // 0x40

    public static final int DECEMBER = 11; // 0xb

    public static final int DST_OFFSET = 16; // 0x10

    static final int DST_OFFSET_MASK = 65536; // 0x10000

    public static final int ERA = 0; // 0x0

    static final int ERA_MASK = 1; // 0x1

    public static final int FEBRUARY = 1; // 0x1

    public static final int FIELD_COUNT = 17; // 0x11

    private static final java.lang.String[] FIELD_NAME;

    static {
        FIELD_NAME = new java.lang.String[0];
    }

    public static final int FRIDAY = 6; // 0x6

    public static final int HOUR = 10; // 0xa

    static final int HOUR_MASK = 1024; // 0x400

    public static final int HOUR_OF_DAY = 11; // 0xb

    static final int HOUR_OF_DAY_MASK = 2048; // 0x800

    public static final int JANUARY = 0; // 0x0

    public static final int JULY = 6; // 0x6

    public static final int JUNE = 5; // 0x5

    public static final int LONG = 2; // 0x2

    public static final int LONG_FORMAT = 2; // 0x2

    public static final int LONG_STANDALONE = 32770; // 0x8002

    public static final int MARCH = 2; // 0x2

    public static final int MAY = 4; // 0x4

    public static final int MILLISECOND = 14; // 0xe

    static final int MILLISECOND_MASK = 16384; // 0x4000

    private static final int MINIMUM_USER_STAMP = 2; // 0x2

    public static final int MINUTE = 12; // 0xc

    static final int MINUTE_MASK = 4096; // 0x1000

    public static final int MONDAY = 2; // 0x2

    public static final int MONTH = 2; // 0x2

    static final int MONTH_MASK = 4; // 0x4

    public static final int NARROW_FORMAT = 4; // 0x4

    public static final int NARROW_STANDALONE = 32772; // 0x8004

    public static final int NOVEMBER = 10; // 0xa

    public static final int OCTOBER = 9; // 0x9

    public static final int PM = 1; // 0x1

    public static final int SATURDAY = 7; // 0x7

    public static final int SECOND = 13; // 0xd

    static final int SECOND_MASK = 8192; // 0x2000

    public static final int SEPTEMBER = 8; // 0x8

    public static final int SHORT = 1; // 0x1

    public static final int SHORT_FORMAT = 1; // 0x1

    public static final int SHORT_STANDALONE = 32769; // 0x8001

    static final int STANDALONE_MASK = 32768; // 0x8000

    public static final int SUNDAY = 1; // 0x1

    public static final int THURSDAY = 5; // 0x5

    public static final int TUESDAY = 3; // 0x3

    public static final int UNDECIMBER = 12; // 0xc

    private static final int UNSET = 0; // 0x0

    public static final int WEDNESDAY = 4; // 0x4

    public static final int WEEK_OF_MONTH = 4; // 0x4

    static final int WEEK_OF_MONTH_MASK = 16; // 0x10

    public static final int WEEK_OF_YEAR = 3; // 0x3

    static final int WEEK_OF_YEAR_MASK = 8; // 0x8

    public static final int YEAR = 1; // 0x1

    static final int YEAR_MASK = 2; // 0x2

    public static final int ZONE_OFFSET = 15; // 0xf

    static final int ZONE_OFFSET_MASK = 32768; // 0x8000

    transient boolean areAllFieldsSet;

    protected boolean areFieldsSet;

    private static final java.util.concurrent.ConcurrentMap<java.util.Locale, int[]>
            cachedLocaleData;

    static {
        cachedLocaleData = null;
    }

    static final int currentSerialVersion = 1; // 0x1

    protected int[] fields;

    private int firstDayOfWeek;

    protected boolean[] isSet;

    protected boolean isTimeSet;

    private boolean lenient = true;

    private int minimalDaysInFirstWeek;

    private int nextStamp = 2; // 0x2

    private int serialVersionOnStream = 1; // 0x1

    static final long serialVersionUID = -1807547505821590642L; // 0xe6ea4d1ec8dc5b8eL

    private transient boolean sharedZone = false;

    private transient int[] stamp;

    protected long time;

    @UnsupportedAppUsage
    private java.util.TimeZone zone;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class AvailableCalendarTypes {

        private AvailableCalendarTypes() {
            throw new RuntimeException("Stub!");
        }

        private static final java.util.Set<java.lang.String> SET;

        static {
            SET = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static class Builder {

        public Builder() {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder setInstant(long instant) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder setInstant(java.util.Date instant) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder set(int field, int value) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder setFields(int... fieldValuePairs) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder setDate(int year, int month, int dayOfMonth) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder setTimeOfDay(int hourOfDay, int minute, int second) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder setTimeOfDay(
                int hourOfDay, int minute, int second, int millis) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder setWeekDate(int weekYear, int weekOfYear, int dayOfWeek) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder setTimeZone(java.util.TimeZone zone) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder setLenient(boolean lenient) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder setCalendarType(java.lang.String type) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder setLocale(java.util.Locale locale) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar.Builder setWeekDefinition(
                int firstDayOfWeek, int minimalDaysInFirstWeek) {
            throw new RuntimeException("Stub!");
        }

        public java.util.Calendar build() {
            throw new RuntimeException("Stub!");
        }

        private void allocateFields() {
            throw new RuntimeException("Stub!");
        }

        private void internalSet(int field, int value) {
            throw new RuntimeException("Stub!");
        }

        private boolean isInstantSet() {
            throw new RuntimeException("Stub!");
        }

        private boolean isSet(int index) {
            throw new RuntimeException("Stub!");
        }

        private boolean isValidWeekParameter(int value) {
            throw new RuntimeException("Stub!");
        }

        private static final int NFIELDS = 18; // 0x12

        private static final int WEEK_YEAR = 17; // 0x11

        private int[] fields;

        private int firstDayOfWeek;

        private long instant;

        private boolean lenient = true;

        private java.util.Locale locale;

        private int maxFieldIndex;

        private int minimalDaysInFirstWeek;

        private int nextStamp;

        private java.lang.String type;

        private java.util.TimeZone zone;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class CalendarAccessControlContext {

        private CalendarAccessControlContext() {
            throw new RuntimeException("Stub!");
        }

        private static final java.security.AccessControlContext INSTANCE;

        static {
            INSTANCE = null;
        }
    }
}
