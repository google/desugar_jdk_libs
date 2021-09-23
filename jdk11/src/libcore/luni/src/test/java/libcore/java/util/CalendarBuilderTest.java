/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package libcore.java.util;

import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests {@link java.util.Calendar.Builder}.
 */
public class CalendarBuilderTest {


    @Test
    public void test_default_values() {
        Calendar.Builder builder = new Calendar.Builder();
        GregorianCalendar expected = new GregorianCalendar();
        expected.clear();
        assertEquals(expected, builder.build());
    }

    @Test
    public void test_setCalendarType_iso8601() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setCalendarType("iso8601");
        // ISO 8601 represents a gregorian calendar with a specific configuration
        GregorianCalendar expected = new GregorianCalendar();
        expected.clear();
        expected.setGregorianChange(new Date(Long.MIN_VALUE));
        expected.setFirstDayOfWeek(Calendar.MONDAY);
        expected.setMinimalDaysInFirstWeek(4);
        assertEquals(expected, builder.build());
    }

    @Test
    public void test_setCalendarType_invalid() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setCalendarType(null);
            fail("Should have thrown NPE");
        } catch (NullPointerException expected) {}

        for (String unsupported : new String[] { "buddhist", "japanese", "notACalendarType" }) {
            try {
                // not supported
                builder.setCalendarType(unsupported);
                fail("Unsupported calendar type " + unsupported + " should have thrown.");
            } catch (IllegalArgumentException expected) {}
        }
    }

    @Test
    public void test_setCalendarType_reset() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setCalendarType("gregorian");
        try {
            builder.setCalendarType("iso8601");
            fail("Should not accept second setCalendarType() call");
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void test_setDate() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setDate(2000, Calendar.FEBRUARY, 3);
        GregorianCalendar expected = new GregorianCalendar();
        expected.clear();
        expected.set(2000, Calendar.FEBRUARY, 3);
        assertEquals(expected, builder.build());
    }

    @Test
    public void test_setTimeOfDay() {
        Calendar.Builder builder = new Calendar.Builder();
        GregorianCalendar expected = new GregorianCalendar();
        expected.clear();
        builder.setTimeOfDay(10, 11, 12);
        expected.set(1970, Calendar.JANUARY, 1, 10, 11, 12);
        assertEquals(expected, builder.build());
        builder.setTimeOfDay(10, 11, 12, 13);
        expected.set(Calendar.MILLISECOND, 13);
        assertEquals(expected, builder.build());
    }

    @Test
    public void test_setWeekDate() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setWeekDate(1, 2000, Calendar.TUESDAY);
        GregorianCalendar expected = new GregorianCalendar();
        expected.clear();
        expected.setWeekDate(1, 2000, Calendar.TUESDAY);
        assertEquals(expected, builder.build());
    }

    @Test
    public void test_setLenient() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.set(Calendar.HOUR_OF_DAY, 25);
        builder.setLenient(false);
        try {
            builder.build();
            fail("Should have failed to build.");
        } catch (IllegalArgumentException expected) {}
        builder.setLenient(true);
        GregorianCalendar expected = new GregorianCalendar();
        expected.clear();
        expected.setLenient(true);
        expected.set(Calendar.HOUR_OF_DAY, 25);
        assertEquals(expected, builder.build());
    }

    @Test
    public void test_setLocale() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setLocale(Locale.GERMANY);
        GregorianCalendar expected = new GregorianCalendar(Locale.GERMANY);
        expected.clear();
        assertEquals(expected, builder.build());
    }

    @Test
    public void test_setLocale_thTH() {
        // See http://b/35138741
        Calendar.Builder builder = new Calendar.Builder();
        Locale th = new Locale("th", "TH");
        builder.setLocale(th);
        GregorianCalendar expected = new GregorianCalendar(th);
        expected.clear();
        assertEquals(expected, builder.build());
    }

    @Test
    public void test_set() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.set(Calendar.YEAR, 2000);
        GregorianCalendar expected = new GregorianCalendar();
        expected.clear();
        expected.set(Calendar.YEAR, 2000);
        assertEquals(expected, builder.build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_set_negative_field() {
        new Calendar.Builder().set(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_set_field_too_high() {
        new Calendar.Builder().set(Calendar.FIELD_COUNT, 1);
    }

    @Test
    public void test_set_after_setInstant() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setInstant(0L);
        try {
            builder.set(Calendar.YEAR, 2000);
            fail("Setting a field after setInstant should fail.");
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void test_setFields() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setFields(Calendar.YEAR, 2000, Calendar.MONTH, Calendar.FEBRUARY);
        GregorianCalendar expected = new GregorianCalendar();
        expected.clear();
        expected.set(Calendar.YEAR, 2000);
        expected.set(Calendar.MONTH, Calendar.FEBRUARY);
        assertEquals(expected, builder.build());

        // field values can be re-set and order of fields matter
        builder.setFields(Calendar.DAY_OF_WEEK_IN_MONTH, 1,
                Calendar.DAY_OF_MONTH, 20, // this will effectively be ignored
                Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        expected.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        expected.set(Calendar.DAY_OF_MONTH, 20);
        expected.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        assertEquals(expected, builder.build());
        // 20th February 2000 would have been a Sunday, but we set the DOW last.
        assertEquals(Calendar.WEDNESDAY, builder.build().get(Calendar.DAY_OF_WEEK));
    }

    @Test(expected = NullPointerException.class)
    public void test_setFields_null() {
        new Calendar.Builder().setFields(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_setFields_oddNumberOfArguments() {
        new Calendar.Builder().setFields(Calendar.YEAR);
    }

    @Test
    public void test_setFields_after_setInstant() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setInstant(0L);
        try {
            builder.setFields(Calendar.YEAR, 2000);
            fail("Setting a field after setInstant should fail.");
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void test_setInstant() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setInstant(Long.MIN_VALUE);
        GregorianCalendar expected = new GregorianCalendar();
        expected.clear();
        expected.setTimeInMillis(Long.MIN_VALUE);
        assertEquals(expected, builder.build());
    }

    @Test
    public void test_setInstant_after_set() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.set(Calendar.YEAR, 2000);
        try {
            builder.setInstant(0L);
            fail("Setting the instant after setting a field should fail.");
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void test_setInstant_Date() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setInstant(new Date(Long.MAX_VALUE));
        GregorianCalendar expected = new GregorianCalendar();
        expected.clear();
        expected.setTimeInMillis(Long.MAX_VALUE);
        assertEquals(expected, builder.build());
    }

    @Test(expected = NullPointerException.class)
    public void test_setInstant_Date_null() {
        new Calendar.Builder().setInstant(null);
    }

    @Test
    public void test_setTimeZone() {
        TimeZone london = TimeZone.getTimeZone("Europe/London");
        Calendar.Builder builder = new Calendar.Builder();
        builder.setTimeZone(london);
        GregorianCalendar expected = new GregorianCalendar();
        expected.clear();
        expected.setTimeZone(london);
        assertEquals(expected, builder.build());
    }

    @Test(expected = NullPointerException.class)
    public void test_setTimeZone_null() {
        new Calendar.Builder().setTimeZone(null);
    }

    @Test
    public void test_setWeekDefinition() {
        Calendar.Builder builder = new Calendar.Builder();
        builder.setWeekDefinition(Calendar.TUESDAY, 7);
        GregorianCalendar expected = new GregorianCalendar();
        expected.clear();
        expected.setFirstDayOfWeek(Calendar.TUESDAY);
        expected.setMinimalDaysInFirstWeek(7);
        assertEquals(expected, builder.build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_setWeekDefinition_invalid_first_dow() {
        new Calendar.Builder().setWeekDefinition(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_setWeekDefinition_invalid_minimum_days() {
        new Calendar.Builder().setWeekDefinition(Calendar.WEDNESDAY, 8);
    }

}
