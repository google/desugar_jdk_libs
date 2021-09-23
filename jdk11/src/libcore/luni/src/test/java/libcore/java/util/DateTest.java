/*
 * Copyright (C) 2010 The Android Open Source Project
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

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import junit.framework.TestCase;

public class DateTest extends TestCase {
    // http://code.google.com/p/android/issues/detail?id=6013
    public void test_toString_us() throws Exception {
        // Ensure that no matter where this is run, we know what time zone to expect.
        Locale.setDefault(Locale.US);
        TimeZone.setDefault(TimeZone.getTimeZone("America/Chicago"));
        assertEquals("Wed Dec 31 18:00:00 CST 1969", new Date(0).toString());
    }

    // https://code.google.com/p/android/issues/detail?id=81924
    public void test_toString_nonUs() {
        // The string for the timezone depends on what the default locale is. Not every locale
        // has a short-name for America/Chicago -> CST.
        Locale.setDefault(Locale.CHINA);
        TimeZone.setDefault(TimeZone.getTimeZone("America/Chicago"));
        assertEquals("Wed Dec 31 18:00:00 CST 1969", new Date(0).toString());
    }

    public void test_toGMTString_us() throws Exception {
        // Based on https://issues.apache.org/jira/browse/HARMONY-501
        TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
        Locale.setDefault(Locale.US);

        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR, 21);
        assertEquals("Wed Jan 01 00:00:00 PST 21", c.getTime().toString());
        String actual21GmtString = c.getTime().toGMTString();
        // zic <= 2014b data produces -08:00:00, later ones produce -07:52:58 instead.
        assertEquals("1 Jan 21 07:52:58 GMT", actual21GmtString);

        c.set(Calendar.YEAR, 321);
        assertEquals("Sun Jan 01 00:00:00 PST 321", c.getTime().toString());
        String actual321GmtString = c.getTime().toGMTString();
        // zic <= 2014b data produces -08:00:00, later ones produce -07:52:58 instead.
        assertEquals("1 Jan 321 07:52:58 GMT", actual321GmtString);
    }

    public void test_toGMTString_nonUs() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
        Locale.setDefault(Locale.UK);

        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR, 21);
        assertEquals("Wed Jan 01 00:00:00 PST 21", c.getTime().toString());
        String actual21GmtString = c.getTime().toGMTString();
        // zic <= 2014b data produces -08:00:00, later ones produce -07:52:58 instead.
        assertEquals("1 Jan 21 07:52:58 GMT", actual21GmtString);

        c.set(Calendar.YEAR, 321);
        assertEquals("Sun Jan 01 00:00:00 PST 321", c.getTime().toString());
        String actual321GmtString = c.getTime().toGMTString();
        // zic <= 2014b data produces -08:00:00, later ones produce -07:52:58 instead.
        assertEquals("1 Jan 321 07:52:58 GMT", actual321GmtString);
    }

    public void test_parse_timezones() {
       assertEquals(
               Date.parse("Wed, 06 Jan 2016 11:55:59 GMT+05:00"),
               Date.parse("Wed, 06 Jan 2016 11:55:59 GMT+0500"));

        assertEquals(
                Date.parse("Wed, 06 Jan 2016 11:55:59 GMT+05:00"),
                Date.parse("Wed, 06 Jan 2016 11:55:59 GMT+05"));
    }

    /**
     * Test that conversion between Date and Instant works when the
     * Instant is based on a millisecond value (and thus can be
     * represented as a Date).
     */
    public void test_convertFromAndToInstant_milliseconds() {
        check_convertFromAndToInstant_milliseconds(Long.MIN_VALUE);
        check_convertFromAndToInstant_milliseconds(Long.MAX_VALUE);

        check_convertFromAndToInstant_milliseconds(-1);
        check_convertFromAndToInstant_milliseconds(0);
        check_convertFromAndToInstant_milliseconds(123456789);
    }

    private static void check_convertFromAndToInstant_milliseconds(long millis) {
        assertEquals(new Date(millis), Date.from(Instant.ofEpochMilli(millis)));
        assertEquals(new Date(millis).toInstant(), Instant.ofEpochMilli(millis));
    }

    /**
     * Checks the minimum/maximum Instant values (based on seconds and
     * nanos) that can be converted to a Date, i.e. that can be converted
     * to milliseconds without overflowing a long. Note that the rounding
     * is such that the lower bound is exactly Long.MIN_VALUE msec whereas
     * the upper bound is 999,999 nanos beyond Long.MAX_VALUE msec. This
     * makes some sense in that the magnitude of the upper/lower bound
     * nanos differ only by 1, just like the magnitude of Long.MIN_VALUE /
     * MAX_VALUE differ only by 1.
     */
    public void test_convertFromInstant_secondsAndNanos() {
        // Documentation for how the below bounds relate to long boundaries for milliseconds
        assertEquals(-808, Long.MIN_VALUE % 1000);
        assertEquals(807, Long.MAX_VALUE % 1000);

        // Lower bound
        long minSecond = Long.MIN_VALUE / 1000;
        Date.from(Instant.ofEpochSecond(minSecond));
        // This instant exactly corresponds to Long.MIN_VALUE msec because
        // Long.MIN_VALUE % 1000 == -808 == (-1000 + 192)
        Date.from(Instant.ofEpochSecond(minSecond - 1, 192000000));
        assertArithmeticOverflowDateFrom(Instant.ofEpochSecond(minSecond - 1, 0));
        assertArithmeticOverflowDateFrom(Instant.ofEpochSecond(minSecond - 1, 191999999));

        // Upper bound
        long maxSecond = Long.MAX_VALUE / 1000;
        Date.from(Instant.ofEpochSecond(maxSecond, 0));
        // This Instant is 999,999 nanos beyond Long.MAX_VALUE msec because
        // (Long.MAX_VALUE % 1000) == 807
        Date.from(Instant.ofEpochSecond(maxSecond, 807999999));
        assertArithmeticOverflowDateFrom(Instant.ofEpochSecond(maxSecond + 1, 0));
        assertArithmeticOverflowDateFrom(Instant.ofEpochSecond(maxSecond, 808000000));
    }

    private static void assertArithmeticOverflowDateFrom(Instant instant) {
        try {
            Date.from(instant);
            fail(instant + " should not have been convertible to Date");
        } catch (IllegalArgumentException expected) {
        }
    }

    /**
     * Checks conversion between long, Date and Instant.
     */
    public void test_convertToInstantAndBack() {
        check_convertToInstantAndBack(0);
        check_convertToInstantAndBack(-1);
        check_convertToInstantAndBack( 999999999);
        check_convertToInstantAndBack(1000000000);
        check_convertToInstantAndBack(1000000001);
        check_convertToInstantAndBack(1000000002);
        check_convertToInstantAndBack(1000000499);
        check_convertToInstantAndBack(1000000500);
        check_convertToInstantAndBack(1000000999);
        check_convertToInstantAndBack(1000001000);
        check_convertToInstantAndBack(Long.MIN_VALUE + 808); // minimum ofEpochMilli argument
        check_convertToInstantAndBack(Long.MAX_VALUE);
        check_convertToInstantAndBack(System.currentTimeMillis());
        check_convertToInstantAndBack(Date.parse("Wed, 06 Jan 2016 11:55:59 GMT+0500"));
    }

    private static void check_convertToInstantAndBack(long millis) {
        Date date = new Date(millis);
        Instant instant = date.toInstant();
        assertEquals(date, Date.from(instant));

        assertEquals(instant, Instant.ofEpochMilli(millis));
        assertEquals("Millis should be a millions of nanos", 0, instant.getNano() % 1000000);

        assertEquals(millis, date.getTime());
        assertEquals(millis, instant.toEpochMilli());
    }
}
