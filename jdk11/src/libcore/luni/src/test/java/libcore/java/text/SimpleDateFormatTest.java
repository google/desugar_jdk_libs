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

package libcore.java.text;

import libcore.util.Nullable;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class SimpleDateFormatTest extends junit.framework.TestCase {

    private static final TimeZone AMERICA_LOS_ANGELES = TimeZone.getTimeZone("America/Los_Angeles");
    private static final TimeZone AUSTRALIA_LORD_HOWE = TimeZone.getTimeZone("Australia/Lord_Howe");
    private static final TimeZone UTC = TimeZone.getTimeZone("Etc/UTC");

    /**
     * The list of time zone ids formatted as "UTC".
     */
    private static final String[] UTC_ZONE_IDS = new String[] {
            "Etc/UCT", "Etc/UTC", "Etc/Universal", "Etc/Zulu", "UCT", "UTC", "Universal", "Zulu"
    };


    private Locale defaultLocale;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        defaultLocale = Locale.getDefault();
        // Locale affects timezone names / abbreviations so can affect formatting and parsing.
        Locale.setDefault(Locale.US);
    }

    @Override
    public void tearDown() throws Exception {
        Locale.setDefault(defaultLocale);
        super.tearDown();
    }

    /**
     * Tests that the default constructor uses the data in the default locale
     */
    public void testDefaultConstructor_localeUS() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        DateFormat referencedDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT, DateFormat.SHORT, Locale.US);
        Date date = new Date(0);

        assertEquals(referencedDateFormat.format(date), sdf.format(date));
    }

    // The RI fails this test.
    public void test2DigitYearStartIsCloned() throws Exception {
        // Test that get2DigitYearStart returns a clone.
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(UTC);

        Date originalDate = sdf.get2DigitYearStart();
        assertNotSame(sdf.get2DigitYearStart(), originalDate);
        assertEquals(sdf.get2DigitYearStart(), originalDate);
        originalDate.setTime(0);
        assertFalse(sdf.get2DigitYearStart().equals(originalDate));
        // Test that set2DigitYearStart takes a clone.
        Date newDate = new Date();
        sdf.set2DigitYearStart(newDate);
        assertNotSame(sdf.get2DigitYearStart(), newDate);
        assertEquals(sdf.get2DigitYearStart(), newDate);
        newDate.setTime(0);
        assertFalse(sdf.get2DigitYearStart().equals(newDate));
    }

    // The RI fails this test because this is an ICU-compatible Android extension.
    // Necessary for correct localization in various languages (http://b/2633414).
    public void testStandAloneNames() throws Exception {
        Locale en = Locale.ENGLISH;
        Locale pl = new Locale("pl");
        Locale ru = new Locale("ru");

        assertEquals("January", formatDateUtc(en, "MMMM"));
        assertEquals("January", formatDateUtc(en, "LLLL"));
        assertEquals("stycznia", formatDateUtc(pl, "MMMM"));
        assertEquals("stycze\u0144", formatDateUtc(pl, "LLLL"));

        assertEquals("Thursday", formatDateUtc(en, "EEEE"));
        assertEquals("Thursday", formatDateUtc(en, "cccc"));
        assertEquals("\u0447\u0435\u0442\u0432\u0435\u0440\u0433", formatDateUtc(ru, "EEEE"));
        assertEquals("\u0447\u0435\u0442\u0432\u0435\u0440\u0433", formatDateUtc(ru, "cccc"));

        assertEquals(Calendar.JUNE, parseDateUtc(en, "yyyy-MMMM-dd", "1980-June-12").get(Calendar.MONTH));
        assertEquals(Calendar.JUNE, parseDateUtc(en, "yyyy-LLLL-dd", "1980-June-12").get(Calendar.MONTH));
        assertEquals(Calendar.JUNE, parseDateUtc(pl, "yyyy-MMMM-dd", "1980-czerwca-12").get(Calendar.MONTH));
        assertEquals(Calendar.JUNE, parseDateUtc(pl, "yyyy-LLLL-dd", "1980-czerwiec-12").get(Calendar.MONTH));

        assertEquals(Calendar.TUESDAY, parseDateUtc(en, "EEEE", "Tuesday").get(Calendar.DAY_OF_WEEK));
        assertEquals(Calendar.TUESDAY, parseDateUtc(en, "cccc", "Tuesday").get(Calendar.DAY_OF_WEEK));
        assertEquals(Calendar.TUESDAY, parseDateUtc(ru, "EEEE", "\u0432\u0442\u043e\u0440\u043d\u0438\u043a").get(Calendar.DAY_OF_WEEK));
        assertEquals(Calendar.TUESDAY, parseDateUtc(ru, "cccc", "\u0412\u0442\u043e\u0440\u043d\u0438\u043a").get(Calendar.DAY_OF_WEEK));
    }

    // The RI fails this test because it doesn't fully support UTS #35.
    // https://code.google.com/p/android/issues/detail?id=39616
    public void testFiveCount_parsing() throws Exception {
      // It's pretty silly to try to parse the shortest names, because they're almost always
      // ambiguous.
      assertCannotParse(Locale.ENGLISH, "MMMMM", "J");
      assertCannotParse(Locale.ENGLISH, "LLLLL", "J");
      assertCannotParse(Locale.ENGLISH, "EEEEE", "T");
      assertCannotParse(Locale.ENGLISH, "ccccc", "T");
    }

    // The RI fails this test because it doesn't fully support UTS #35.
    // https://code.google.com/p/android/issues/detail?id=39616
    public void testFiveCount_M() throws Exception {
      assertEquals("1", formatDateUtc(Locale.ENGLISH, "M"));
      assertEquals("01", formatDateUtc(Locale.ENGLISH, "MM"));
      assertEquals("Jan", formatDateUtc(Locale.ENGLISH, "MMM"));
      assertEquals("January", formatDateUtc(Locale.ENGLISH, "MMMM"));
      assertEquals("J", formatDateUtc(Locale.ENGLISH, "MMMMM"));
    }

    // The RI fails this test because it doesn't fully support UTS #35.
    // https://code.google.com/p/android/issues/detail?id=39616
    public void testFiveCount_L() throws Exception {
      assertEquals("1", formatDateUtc(Locale.ENGLISH, "L"));
      assertEquals("01", formatDateUtc(Locale.ENGLISH, "LL"));
      assertEquals("Jan", formatDateUtc(Locale.ENGLISH, "LLL"));
      assertEquals("January", formatDateUtc(Locale.ENGLISH, "LLLL"));
      assertEquals("J", formatDateUtc(Locale.ENGLISH, "LLLLL"));
    }

    // The RI fails this test because it doesn't fully support UTS #35.
    // https://code.google.com/p/android/issues/detail?id=39616
    public void testFiveCount_E() throws Exception {
      assertEquals("Thu", formatDateUtc(Locale.ENGLISH, "E"));
      assertEquals("Thu", formatDateUtc(Locale.ENGLISH, "EE"));
      assertEquals("Thu", formatDateUtc(Locale.ENGLISH, "EEE"));
      assertEquals("Thursday", formatDateUtc(Locale.ENGLISH, "EEEE"));
      assertEquals("T", formatDateUtc(Locale.ENGLISH, "EEEEE"));
      // assertEquals("Th", formatDate(Locale.ENGLISH, "EEEEEE")); // icu4c doesn't support 6.
    }

    // The RI fails this test because it doesn't fully support UTS #35.
    // https://code.google.com/p/android/issues/detail?id=39616
    public void testFiveCount_c() throws Exception {
      assertEquals("Thu", formatDateUtc(Locale.ENGLISH, "c"));
      assertEquals("Thu", formatDateUtc(Locale.ENGLISH, "cc"));
      assertEquals("Thu", formatDateUtc(Locale.ENGLISH, "ccc"));
      assertEquals("Thursday", formatDateUtc(Locale.ENGLISH, "cccc"));
      assertEquals("T", formatDateUtc(Locale.ENGLISH, "ccccc"));
      // assertEquals("Th", formatDate(Locale.ENGLISH, "cccccc")); // icu4c doesn't support 6.
    }

    // The RI fails this test because it doesn't fully support UTS #35.
    // https://code.google.com/p/android/issues/detail?id=39616
    public void testFiveCount_Z() throws Exception {
      assertEquals("+0000", formatDateUtc(Locale.ENGLISH, "Z"));
      assertEquals("+0000", formatDateUtc(Locale.ENGLISH, "ZZ"));
      assertEquals("+0000", formatDateUtc(Locale.ENGLISH, "ZZZ"));
      assertEquals("GMT+00:00", formatDateUtc(Locale.ENGLISH, "ZZZZ"));
      assertEquals("+00:00", formatDateUtc(Locale.ENGLISH, "ZZZZZ"));

      TimeZone tz = AMERICA_LOS_ANGELES;
      assertEquals("-0800", formatDate(Locale.ENGLISH, "Z", tz));
      assertEquals("-0800", formatDate(Locale.ENGLISH, "ZZ", tz));
      assertEquals("-0800", formatDate(Locale.ENGLISH, "ZZZ", tz));
      assertEquals("GMT-08:00", formatDate(Locale.ENGLISH, "ZZZZ", tz));
      assertEquals("-08:00", formatDate(Locale.ENGLISH, "ZZZZZ", tz));
    }

    // The RI fails this test because it doesn't fully support UTS #35.
    // https://code.google.com/p/android/issues/detail?id=39616
    public void test_parsing_Z() throws Exception {
      assertEquals(1325421240000L, parseTimeUtc("yyyy-MM-dd' 'Z", "2012-01-01 -1234"));
      assertEquals(1325421240000L, parseTimeUtc("yyyy-MM-dd' 'ZZ", "2012-01-01 -1234"));
      assertEquals(1325421240000L, parseTimeUtc("yyyy-MM-dd' 'ZZZ", "2012-01-01 -1234"));
      assertEquals(1325421240000L, parseTimeUtc("yyyy-MM-dd' 'ZZZZ", "2012-01-01 GMT-12:34"));
      assertEquals(1325421240000L, parseTimeUtc("yyyy-MM-dd' 'ZZZZZ", "2012-01-01 -12:34"));
    }

    private static long parseTimeUtc(String fmt, String value) {
      return parseDateUtc(Locale.ENGLISH, fmt, value).getTime().getTime();
    }

    public void test2038() {
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US);
        format.setTimeZone(UTC);

        assertEquals("Sun Nov 24 17:31:44 1833",
                format.format(new Date(((long) Integer.MIN_VALUE + Integer.MIN_VALUE) * 1000L)));
        assertEquals("Fri Dec 13 20:45:52 1901",
                format.format(new Date(Integer.MIN_VALUE * 1000L)));
        assertEquals("Thu Jan 01 00:00:00 1970",
                format.format(new Date(0L)));
        assertEquals("Tue Jan 19 03:14:07 2038",
                format.format(new Date(Integer.MAX_VALUE * 1000L)));
        assertEquals("Sun Feb 07 06:28:16 2106",
                format.format(new Date((2L + Integer.MAX_VALUE + Integer.MAX_VALUE) * 1000L)));
    }

    private String formatDateUtc(Locale l, String fmt) {
        return formatDate(l, fmt, UTC);
    }

    private String formatDate(Locale l, String fmt, TimeZone tz) {
        DateFormat dateFormat = new SimpleDateFormat(fmt, l);
        dateFormat.setTimeZone(tz);
        return dateFormat.format(new Date(0));
    }

    private static void assertCannotParse(Locale l, String fmt, String value) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt, l);
        sdf.setTimeZone(UTC);
        ParsePosition pp = new ParsePosition(0);
        Date d = sdf.parse(value, pp);
        assertNull("Value " + value + " must not parse in locale " + l + " with format " + fmt, d);
    }

    /**
     * Parse a date with a SimpleDateFormat set to use UTC. If fmt contains a pattern for zone the
     * use of UTC should have no effect, but in other cases it can affect the outcome. The returned
     * calendar will also be set to UTC.
     */
    private static Calendar parseDateUtc(Locale l, String fmt, String value) {
        return parseDate(l, fmt, value, UTC);
    }

    private static Calendar parseDate(Locale l, String fmt, String value, TimeZone tz) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt, l);
        sdf.setTimeZone(tz);
        ParsePosition pp = new ParsePosition(0);
        Date d = sdf.parse(value, pp);
        if (d == null) {
            fail(pp.toString());
        }
        if (pp.getIndex() != value.length()) {
            fail("Value " + value + " must be fully consumed: " +  pp.toString());
        }
        Calendar c = Calendar.getInstance(tz);
        c.setTime(d);
        return c;
    }

    // http://code.google.com/p/android/issues/detail?id=13420
    public void testParsingUncommonTimeZoneAbbreviations() {
        String fmt = "yyyy-MM-dd HH:mm:ss.SSS z";
        String date = "2010-12-23 12:44:57.0 CET";
        // ICU considers "CET" (Central European Time) to be common in Britain...
        assertEquals(1293104697000L, parseDateUtc(Locale.UK, fmt, date).getTimeInMillis());
        // ...but not in the US.
        assertCannotParse(Locale.US, fmt, date);
    }

    // In Honeycomb, only one Olson id was associated with CET (or any other "uncommon"
    // abbreviation). This was changed after KitKat to avoid Java hacks on top of ICU data.
    // ICU data only provides abbreviations for timezones in the locales where they would
    // not be ambiguous to most people of that locale.
    public void testFormattingUncommonTimeZoneAbbreviations() {
        String fmt = "yyyy-MM-dd HH:mm:ss.SSS z";
        String unambiguousDate = "1970-01-01 01:00:00.000 CET";
        String ambiguousDate = "1970-01-01 01:00:00.000 GMT+01:00";

        // The locale to use when formatting. Not every Locale renders "Europe/Berlin" as "CET". The
        // UK is one that does, the US is one that does not.
        Locale cetUnambiguousLocale = Locale.UK;
        Locale cetAmbiguousLocale = Locale.US;
        TimeZone europeBerlin = TimeZone.getTimeZone("Europe/Berlin");
        TimeZone europeZurich = TimeZone.getTimeZone("Europe/Zurich");

        SimpleDateFormat sdf = new SimpleDateFormat(fmt, cetUnambiguousLocale);
        sdf.setTimeZone(europeBerlin);
        assertEquals(unambiguousDate, sdf.format(new Date(0)));
        sdf = new SimpleDateFormat(fmt, cetUnambiguousLocale);
        sdf.setTimeZone(europeZurich);
        assertEquals(unambiguousDate, sdf.format(new Date(0)));

        sdf = new SimpleDateFormat(fmt, cetAmbiguousLocale);
        sdf.setTimeZone(europeBerlin);
        assertEquals(ambiguousDate, sdf.format(new Date(0)));
        sdf = new SimpleDateFormat(fmt, cetAmbiguousLocale);
        sdf.setTimeZone(europeZurich);
        assertEquals(ambiguousDate, sdf.format(new Date(0)));
    }

    // http://code.google.com/p/android/issues/detail?id=8258
    public void testTimeZoneFormatting() throws Exception {
        Date epoch = new Date(0);

        // Create a SimpleDateFormat that defaults to America/Chicago...
        TimeZone americaChicago = TimeZone.getTimeZone("America/Chicago");
        TimeZone.setDefault(americaChicago);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        assertEquals(americaChicago, sdf.getTimeZone());

        // We should see something appropriate to America/Chicago...
        assertEquals("1969-12-31 18:00:00 -0600", sdf.format(epoch));
        // We can set any TimeZone we want:
        sdf.setTimeZone(AMERICA_LOS_ANGELES);
        assertEquals("1969-12-31 16:00:00 -0800", sdf.format(epoch));
        sdf.setTimeZone(UTC);
        assertEquals("1970-01-01 00:00:00 +0000", sdf.format(epoch));

        // A new SimpleDateFormat will default to America/Chicago...
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        assertEquals(americaChicago, sdf.getTimeZone());

        // ...and parsing an America/Los_Angeles time will *not* change that...
        sdf.parse("2010-12-03 00:00:00 -0800");
        assertEquals(americaChicago, sdf.getTimeZone());

        // ...so our time zone here is "America/Chicago":
        assertEquals("1969-12-31 18:00:00 -0600", sdf.format(epoch));
        // We can set any TimeZone we want:
        sdf.setTimeZone(AMERICA_LOS_ANGELES);
        assertEquals("1969-12-31 16:00:00 -0800", sdf.format(epoch));
        sdf.setTimeZone(UTC);
        assertEquals("1970-01-01 00:00:00 +0000", sdf.format(epoch));

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(UTC);
        Date date = sdf.parse("2010-07-08 02:44:48");
        assertEquals(UTC, sdf.getTimeZone());
        assertEquals(1278557088000L, date.getTime());

        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        sdf.setTimeZone(AMERICA_LOS_ANGELES);
        assertEquals("2010-07-07T19:44:48-0700", sdf.format(date));
        assertEquals(AMERICA_LOS_ANGELES, sdf.getTimeZone());
        sdf.setTimeZone(UTC);
        assertEquals("2010-07-08T02:44:48+0000", sdf.format(date));
        assertEquals(UTC, sdf.getTimeZone());
    }

    public void testDstZoneNameWithNonDstTimestamp() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm zzzz", Locale.US);
        Calendar calendar = new GregorianCalendar(AMERICA_LOS_ANGELES);
        calendar.setTime(format.parse("2011-06-21T10:00 Pacific Standard Time")); // 18:00 GMT-8
        assertEquals(11, calendar.get(Calendar.HOUR_OF_DAY)); // 18:00 GMT-7
        assertEquals(0, calendar.get(Calendar.MINUTE));
    }

    public void testNonDstZoneNameWithDstTimestamp() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm zzzz", Locale.US);
        Calendar calendar = new GregorianCalendar(AMERICA_LOS_ANGELES);
        calendar.setTime(format.parse("2010-12-21T10:00 Pacific Daylight Time")); // 17:00 GMT-7
        assertEquals(9, calendar.get(Calendar.HOUR_OF_DAY)); // 17:00 GMT-8
        assertEquals(0, calendar.get(Calendar.MINUTE));
    }

    // http://b/4723412
    public void testDstZoneWithNonDstTimestampForNonHourDstZone() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm zzzz", Locale.US);
        Calendar calendar = new GregorianCalendar(AUSTRALIA_LORD_HOWE);
        calendar.setTime(format.parse("2011-06-21T20:00 Lord Howe Daylight Time")); // 9:00 GMT+11
        assertEquals(19, calendar.get(Calendar.HOUR_OF_DAY)); // 9:00 GMT+10:30
        assertEquals(30, calendar.get(Calendar.MINUTE));
    }

    public void testNonDstZoneWithDstTimestampForNonHourDstZone() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm zzzz", Locale.US);
        Calendar calendar = new GregorianCalendar(AUSTRALIA_LORD_HOWE);
        calendar.setTime(format.parse("2010-12-21T19:30 Lord Howe Standard Time")); //9:00 GMT+10:30
        assertEquals(20, calendar.get(Calendar.HOUR_OF_DAY)); // 9:00 GMT+11:00
        assertEquals(0, calendar.get(Calendar.MINUTE));
    }
    // http://code.google.com/p/android/issues/detail?id=14963
    public void testParseTimezoneOnly() throws Exception {
        new SimpleDateFormat("z", Locale.FRANCE).parse("UTC");
        new SimpleDateFormat("z", Locale.US).parse("UTC");
    }

    public void testParseMetazoneFallbacksToLocale() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm zzzz", Locale.US);
        Date date = format.parse("2021-02-08T14:14 Pacific Standard Time"); // 22:14 GMT-8:00

        assertEquals(1612822440000L, date.getTime());
    }

    // http://code.google.com/p/android/issues/detail?id=36689
    public void testParseArabic() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("ar", "EG"));
        sdf.setTimeZone(AMERICA_LOS_ANGELES);

        // Can we parse an ASCII-formatted date in an Arabic locale?
        Date d = sdf.parse("2012-08-29 10:02:45");
        assertEquals(1346259765000L, d.getTime());

        // Can we format a date correctly in an Arabic locale?
        String formatted = sdf.format(d);
        assertEquals("٢٠١٢-٠٨-٢٩ ١٠:٠٢:٤٥", formatted);

        // Can we parse the Arabic-formatted date in an Arabic locale, and get the same date
        // we started with?
        Date d2 = sdf.parse(formatted);
        assertEquals(d, d2);
    }

    public void test_59383() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("d. MMM yyyy H:mm", Locale.GERMAN);
        sdf.setTimeZone(AMERICA_LOS_ANGELES);
        assertEquals(1376927400000L, sdf.parse("19. Aug 2013 8:50").getTime());
        assertEquals(1376927400000L, sdf.parse("19. Aug. 2013 8:50").getTime());
    }

    // http://b/16969112
    public void test_fractionalSeconds() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        sdf.setTimeZone(UTC);
        assertEquals("1970-01-02 02:17:36.7", sdf.format(sdf.parse("1970-01-02 02:17:36.7")));

        // We only have millisecond precision for Date objects, so we'll lose
        // information from the fractional seconds section of the string presentation.
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        sdf.setTimeZone(UTC);
        assertEquals("1970-01-02 02:17:36.789000", sdf.format(sdf.parse("1970-01-02 02:17:36.789564")));
    }

    public void test_nullLocales() {
        try {
            SimpleDateFormat.getDateInstance(DateFormat.SHORT, null);
            fail();
        } catch (NullPointerException expected) {}

        try {
            SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, null);
            fail();
        } catch (NullPointerException expected) {}

        try {
            SimpleDateFormat.getTimeInstance(DateFormat.SHORT, null);
            fail();
        } catch (NullPointerException expected) {}
    }

    // http://b/17431155
    public void test_sl_dates() throws Exception {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("sl"));
        assertEquals(TimeZone.getDefault(), df.getTimeZone());
        df.setTimeZone(UTC);
        assertEquals("1. 1. 70", df.format(0L));
    }

    public void testLenientParsingForZ() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = sdf.parse("2016-01-06T23:05:49.480+00:00");
        Calendar calendar = Calendar.getInstance(UTC);
        calendar.setTime(date);
        assertEquals(11, calendar.get(Calendar.HOUR));
        assertEquals(5, calendar.get(Calendar.MINUTE));
        assertEquals(49, calendar.get(Calendar.SECOND));

        Date date2 = sdf.parse("2016-01-06T23:05:49.480+00:00");
        assertEquals(date, date2);

        try {
            date = sdf.parse("2016-01-06T23:05:49.480+00pissoff");
            fail();
        } catch (ParseException expected) {
        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date3 = sdf2.parse("2016-01-06T23:05:49.480+00:00");
        assertEquals(date, date3);
        try {
            sdf2.parse("2016-01-06T23:05:49.480+0000");
            fail();
        } catch (ParseException expected) {
        }
    }

    // http://b/27760434
    public void testTimeZoneNotChangedByParse() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss zzz");
        df.setTimeZone(UTC);
        df.parse("22 Jul 1977 12:23:45 HST");
        assertEquals(UTC, df.getTimeZone());
    }

    public void testZoneStringsUsedForParsingWhenPresent() throws ParseException {
        DateFormatSymbols symbols = DateFormatSymbols.getInstance(Locale.ENGLISH);
        String[][] zoneStrings = symbols.getZoneStrings();
        TimeZone tz = TimeZone.getTimeZone(zoneStrings[0][0]);
        zoneStrings[0][1] = "CustomTimeZone";
        symbols.setZoneStrings(zoneStrings);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm zzz", symbols);

        Date gmtDate = sdf.parse("1 1 2000 12:00 GMT");
        Date customDate = sdf.parse("1 1 2000 12:00 CustomTimeZone");
        assertEquals(tz.getOffset(gmtDate.getTime()), customDate.getTime() - gmtDate.getTime());
    }

    public void testTimeZoneFormattingRespectsSetZoneStrings() throws ParseException {
        DateFormatSymbols symbols = DateFormatSymbols.getInstance(Locale.ENGLISH);
        String[][] zoneStrings = symbols.getZoneStrings();
        TimeZone tz = TimeZone.getTimeZone(zoneStrings[0][0]);
        String originalTzName = zoneStrings[0][1];
        symbols.setZoneStrings(zoneStrings);
        SimpleDateFormat sdf = new SimpleDateFormat("zzzz", symbols);
        sdf.setTimeZone(tz);

        // just re-setting the default values
        assertEquals(originalTzName, sdf.format(new Date(1376927400000L)));

        // providing a custom name
        zoneStrings[0][1] = "CustomTimeZone";
        symbols.setZoneStrings(zoneStrings);
        sdf = new SimpleDateFormat("zzzz", symbols);
        sdf.setTimeZone(tz);
        assertEquals("CustomTimeZone", sdf.format(new Date(1376927400000L)));

        // setting the name to null should format as GMT[+-]...
        zoneStrings[0][1] = null;
        symbols.setZoneStrings(zoneStrings);
        sdf = new SimpleDateFormat("zzzz", symbols);
        sdf.setTimeZone(tz);
        assertTrue(sdf.format(new Date(1376927400000L)).startsWith("GMT"));
    }

    // http://b/30323478
    public void testStandaloneWeekdayParsing() throws Exception {
        Locale fi = new Locale("fi"); // Finnish has separate standalone weekday names
        // tiistaina = Tuesday (regular)
        // tiistai = Tuesday (standalone)
        assertEquals(Calendar.TUESDAY,
                parseDateUtc(fi, "cccc yyyy", "tiistai 2000").get(Calendar.DAY_OF_WEEK));
        assertEquals(Calendar.TUESDAY,
                parseDateUtc(fi, "EEEE yyyy", "tiistaina 2000").get(Calendar.DAY_OF_WEEK));
        assertCannotParse(fi, "cccc yyyy", "tiistaina 2000");
        assertCannotParse(fi, "EEEE yyyy", "tiistai 2000");
    }

    // http://b/30323478
    public void testStandaloneWeekdayFormatting() throws Exception {
        Locale fi = new Locale("fi"); // Finnish has separate standalone weekday names
        assertEquals("torstai", formatDateUtc(fi, "cccc"));
        assertEquals("torstaina", formatDateUtc(fi, "EEEE"));
    }

    public void testDayNumberOfWeek() throws Exception {
        Locale en = Locale.ENGLISH;
        Locale pl = new Locale("pl");

        assertEquals("4", formatDateUtc(en, "u"));
        assertEquals("04", formatDateUtc(en, "uu"));
        assertEquals("4", formatDateUtc(pl, "u"));
        assertEquals("04", formatDateUtc(pl, "uu"));

        assertEquals(Calendar.THURSDAY, parseDateUtc(en, "u", "4").get(Calendar.DAY_OF_WEEK));
        assertEquals(Calendar.MONDAY, parseDateUtc(en, "uu", "1").get(Calendar.DAY_OF_WEEK));
    }

    // Tests that Android's SimpleDateFormat provides localized short strings for UTC
    // (http://b/36337342) i.e. it does not fall back to "GMT" or "GMT+00:00".
    public void testFormatUtcShort() {
        String timeZonePattern = "z";
        int timeZoneStyle = TimeZone.SHORT;

        doTestFormat(Locale.ENGLISH, timeZoneStyle, timeZonePattern, "UTC");
        doTestFormat(Locale.FRANCE, timeZoneStyle, timeZonePattern, "UTC");
        doTestFormat(Locale.SIMPLIFIED_CHINESE, timeZoneStyle, timeZonePattern, "UTC");
    }

    // Tests that Android's SimpleDateFormat provides localized long strings for UTC
    // (http://b/36337342)
    public void testFormatUtcLong() {
        String timeZonePattern = "zzzz";
        int timeZoneStyle = TimeZone.LONG;
        doTestFormat(Locale.ENGLISH, timeZoneStyle, timeZonePattern, "Coordinated Universal Time");
        doTestFormat(Locale.FRANCE, timeZoneStyle, timeZonePattern, "Temps universel coordonné");
        doTestFormat(Locale.SIMPLIFIED_CHINESE, timeZoneStyle, timeZonePattern, "协调世界时");
    }

    private static void doTestFormat(Locale locale, int timeZoneStyle, String timeZonePattern,
            String expectedString) {
        DateFormat dateFormat = new SimpleDateFormat(timeZonePattern, locale);
        for (String timeZoneId : UTC_ZONE_IDS) {
            TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);

            // Confirm the time zone ID was recognized and we didn't just get "GMT".
            assertEquals(timeZoneId, timeZone.getID());

            dateFormat.setTimeZone(timeZone);
            String timeZoneString = dateFormat.format(new Date(0));
            assertEquals(timeZone.getDisplayName(
                    false /* daylight */, timeZoneStyle, locale), timeZoneString);

            assertEquals(expectedString, timeZoneString);
        }
    }

    // Tests that Android's SimpleDateFormat can parse localized short strings for UTC
    // (http://b/36337342)
    public void testParseUtcShort() throws Exception {
        String timeZonePattern = "z";
        int timeZoneStyle = TimeZone.SHORT;
        doUtcParsingTest(Locale.ENGLISH, timeZonePattern, timeZoneStyle, "UTC");
        doUtcParsingTest(Locale.FRENCH, timeZonePattern, timeZoneStyle, "UTC");
        doUtcParsingTest(Locale.SIMPLIFIED_CHINESE, timeZonePattern, timeZoneStyle, "UTC");
    }

    // Tests that Android's SimpleDateFormat can parse localized long strings for UTC
    // (http://b/36337342)
    public void testParseUtcLong() throws Exception {
        String timeZonePattern = "zzzz";
        int timeZoneStyle = TimeZone.LONG;
        doUtcParsingTest(Locale.ENGLISH, timeZonePattern, timeZoneStyle,
                "Coordinated Universal Time");
        doUtcParsingTest(Locale.FRENCH, timeZonePattern, timeZoneStyle,
                "Temps universel coordonné");
        doUtcParsingTest(Locale.SIMPLIFIED_CHINESE, timeZonePattern, timeZoneStyle,
                "协调世界时");
    }

    private static void doUtcParsingTest(Locale locale, String timeZonePattern, int timeZoneStyle,
            String timeZoneString) throws Exception {
        String basePattern = "yyyyMMdd HH:mm:ss.SSS";
        String fullPattern = basePattern + " " + timeZonePattern;

        TimeZone nonUtcZone = TimeZone.getTimeZone("America/Los_Angeles");

        DateFormat formatter = new SimpleDateFormat(basePattern, locale);
        DateFormat parser = new SimpleDateFormat(fullPattern, locale);

        for (String timeZoneId : UTC_ZONE_IDS) {
            TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);

            // Confirm the time zone ID was recognized and we didn't just get "GMT".
            assertEquals(timeZoneId, timeZone.getID());

            assertEquals(timeZoneString,
                    timeZone.getDisplayName(false /* daylight */, timeZoneStyle, locale));

            // Format an arbitrary instant in the chosen time zone. We should get something like
            // "20180126 13:23:34.456".
            Date dateToFormat = new Date();

            formatter.setTimeZone(timeZone);
            String dateTimeString = formatter.format(dateToFormat);

            // Append the time zone. e.g. "20180126 13:23:34.456 Coordinated Universal Time".
            String dateTimeStringWithTimeZone = dateTimeString + " " + timeZoneString;

            // Androidism: The formatter always resets the time zone of the formatter after parsing
            // but we set it here to make it very clear the parser must be using a non-UTC time
            // zone by default even though the string provides all the time zone information.
            parser.setTimeZone(nonUtcZone);

            // Parse the date with time zone back, which should be interpreted as being in UTC.
            Date parsedDate = parser.parse(dateTimeStringWithTimeZone);

            // The original instant should be returned, which means the formatter / parser were able
            // to understand the time zone in the string.
            assertEquals(dateToFormat, parsedDate);
        }
    }

    // http://b/35134326
    public void testTimeZoneParsingErrorIndex() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy z", Locale.ENGLISH);

        checkTimeZoneParsingErrorIndex(dateFormat);
    }

    // http://b/35134326
    public void testTimeZoneParsingErrorIndexWithZoneStrings() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy z", Locale.ENGLISH);
        // Force legacy code path by using zone strings.
        DateFormatSymbols dfs = dateFormat.getDateFormatSymbols();
        dfs.setZoneStrings(dfs.getZoneStrings());
        dateFormat.setDateFormatSymbols(dfs);

        checkTimeZoneParsingErrorIndex(dateFormat);
    }

    // Tests that 'b' and 'B' pattern symbols are silently ignored so that CLDR 32 patterns
    // can be used. http://b/68139386
    public void testDayPeriodFormat() throws Exception {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = isoFormat.parse("2017-01-01T08:00:00");

        for (Locale locale : new Locale[] { Locale.US, Locale.FRANCE }) {
            // Pattern letter 'b'
            assertDayPeriodFormat("HHb", date, "08", locale);
            assertDayPeriodFormat("HHbb", date, "08", locale);
            assertDayPeriodFormat("HHbbb", date, "08", locale);
            assertDayPeriodFormat("HHbbbb", date, "08", locale);
            assertDayPeriodFormat("HHbbbbb", date, "08", locale);

            // Pattern letter 'B'
            assertDayPeriodFormat("HHB", date, "08", locale);
            assertDayPeriodFormat("HHBB", date, "08", locale);
            assertDayPeriodFormat("HHBBB", date, "08", locale);
            assertDayPeriodFormat("HHBBBB", date, "08", locale);
            assertDayPeriodFormat("HHBBBBB", date, "08", locale);
        }
    }

    // Tests that SimpleDateFormat with 'b' and 'B' pattern symbols can't parse any date
    public void testDayPeriodParse() {
        assertDayPeriodParseFailure("b", "");
        assertDayPeriodParseFailure("HHb", "1");
        assertDayPeriodParseFailure("HHb", "12");
        assertDayPeriodParseFailure("HH b", "12 AM");
        assertDayPeriodParseFailure("HH b", "12 midnight");

        assertDayPeriodParseFailure("B", "");
        assertDayPeriodParseFailure("HHB", "8");
        assertDayPeriodParseFailure("HHB", "08");
        assertDayPeriodParseFailure("HH B", "08 AM");
        assertDayPeriodParseFailure("HH B", "08 in the morning");
    }

    public void testContextSensitiveMonth() {
        Locale ru = new Locale("ru");
        assertEquals("1", formatDateUtc(ru, "M"));
        assertEquals("01", formatDateUtc(ru, "MM"));
        assertEquals("янв.", formatDateUtc(ru, "MMM"));
        assertEquals("января", formatDateUtc(ru, "MMMM"));
        assertEquals("Я", formatDateUtc(ru, "MMMMM"));
    }

    /*
     * Tests that forced standalone form is not used on Android. In some languages, e.g. Russian,
     * Polish or Czech, in some cases the month name form depends on the usage context. For example,
     * January in Russian is "Январь" (nominative case), but if you say 11th of January
     * (genitive case), it will look like "11 Января" (notice the difference in the last letter).
     * Five Ms format makes month name independent on the context (in this case "11 Январь").
     */
    public void testContextSensitiveMonth_nonGregorianCalendar() {
        final Locale ru = new Locale("ru");
        final Locale cs = new Locale("cs");

        // The RI forces standalone form here, which would be "январь".
        // Android does not force standalone form. http://b/66411240#comment7
        assertEquals("янв.", formatDateNonGregorianCalendar("MMM", ru));
        assertEquals("января", formatDateNonGregorianCalendar("MMMM", ru));
        assertEquals("января", formatDateNonGregorianCalendar("MMMMM", ru));
        assertEquals("led", formatDateNonGregorianCalendar("MMM", cs));
        assertEquals("ledna", formatDateNonGregorianCalendar("MMMM", cs));
        assertEquals("ledna", formatDateNonGregorianCalendar("MMMMM", cs));

        // Ensure that Android standalone form is used for Ls format strings
        assertEquals("янв.", formatDateNonGregorianCalendar("LLL", ru));
        assertEquals("январь", formatDateNonGregorianCalendar("LLLL", ru));
        assertEquals("январь", formatDateNonGregorianCalendar("LLLLL", ru));
        assertEquals("led", formatDateNonGregorianCalendar("LLL", cs));
        assertEquals("leden", formatDateNonGregorianCalendar("LLLL", cs));
        assertEquals("leden", formatDateNonGregorianCalendar("LLLLL", cs));
    }

    /**
     * This test demonstrates that optimization with special case handling of
     * {@link java.util.SortedMap} in
     * {@link SimpleDateFormat#matchString(java.lang.String, int, int, java.util.Map, java.text.CalendarBuilder)}
     * is buggy. It makes use of {@link NonGregorianCalendarWithTreeMapDisplayNames}
     * which returns {@link TreeMap} instead of {@link HashMap}.
     *
     * @see NonGregorianCalendarWithTreeMapDisplayNames
     * @see http://b/119913354
     */
    public void testMatchStringSortedMap_treeMap() {
        Calendar cal = new NonGregorianCalendarWithTreeMapDisplayNames(null);
        checkMatchStringSortedMap_formatParseCzechJuly(cal);
    }

    /**
     * Same as {@link SimpleDateFormatTest#testMatchStringSortedMap_treeMap()} but also using
     * reverse order comparator for a {@link TreeMap} returned from
     * {@link Calendar#getDisplayNames(int, int, java.util.Locale)}.
     *
     * Reverse order comparator demonstrates that even iterating key set in descending order may
     * still behave incorrectly.
     */
    public void testMatchStringSortedMap_treeMapWithReverseOrderComparator() {
        Calendar cal = new NonGregorianCalendarWithTreeMapDisplayNames(Comparator.reverseOrder());
        checkMatchStringSortedMap_formatParseCzechJuly(cal);
    }

    /**
     * Helper method for {@link #testMatchStringSortedMap_treeMap()} and
     * {@link #testMatchStringSortedMap_treeMapWithReverseOrderComparator()}
     * that formats "15 July 1997" using Czech locale. June in Czech (Červen) is a
     * prefix of July (Červenec) which can break the logic that relies on matching
     * first rather than longest match when parsing months names.
     *
     * For example, imagine matching "15 července 1997" which is "15 June 1997" in Czech.
     * When {@link SimpleDateFormat} parses the whole string it tries to match all field names
     * with "current" text chunk (in case of month this would match againsg "července").
     * This set of field names in this case would contain month names in nominative and genitive
     * cases so it contains "července" (July in genitive) and "červen" (July in nominative).
     * Since the real answer is "července" but "červen" would still match, {@link SimpleDateFormat}
     * picks the wrong shorter field. The next parsed field is year but parsing is attempted
     * from "ce 1997" chunk, not from " 1997", so it fails.
     */
    private static void checkMatchStringSortedMap_formatParseCzechJuly(Calendar calendar) {
        DateFormat fmt = new SimpleDateFormat("dd MMMM yyyy", new Locale("cs", "", ""));
        calendar.clear();
        calendar.setTimeZone(UTC);
        fmt.setCalendar(calendar);

        final String julyStr = fmt.format(new Date(97, Calendar.JULY, 15));

        try {
            Date d = fmt.parse(julyStr);
            String s = fmt.format(d);
            int month = d.getMonth();
            assertEquals(Calendar.JULY, month);
        } catch (ParseException e) {
            fail("Exception occurred: " + e);
        }
    }

    private void assertDayPeriodParseFailure(String pattern, String source) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
        ParsePosition parsePosition = new ParsePosition(0);
        Date d = simpleDateFormat.parse(source, parsePosition);
        assertNull(d);
        assertEquals(0, parsePosition.getIndex());
    }

    private void assertDayPeriodFormat(String pattern, Date date, String expected, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals(expected, simpleDateFormat.format(date));
    }

    private void checkTimeZoneParsingErrorIndex(SimpleDateFormat dateFormat) {
        ParsePosition pos = new ParsePosition(0);
        Date parsed;
        parsed = dateFormat.parse("2000 foobar", pos);
        assertNull(parsed);
        assertEquals("Wrong error index", 5, pos.getErrorIndex());
    }

    // http://b/38396219
    public void testDisplayNamesOnNonGregorianCalendar() {
        assertEquals("Jan", formatDateNonGregorianCalendar("MMM", Locale.US)); // MONTH
        assertEquals("Jan", formatDateNonGregorianCalendar("LLL", Locale.US)); // MONTH_STANDALONE
        assertEquals("Thu", formatDateNonGregorianCalendar("EEE", Locale.US)); // DAY_OF_WEEK
        assertEquals("Thu", formatDateNonGregorianCalendar("ccc", Locale.US)); // STANDALONE_DAY_OF_WEEK
    }

    /*
     * This is a regression test to ensure that month name can't be parsed using narrow format.
     * There are still some cases when parsing can succeed, because on some locales
     * (e.g. "ko", "th" and others) long, short and narrow forms of month are equal.
     * There are two locales tested: AK and UK. AK locale has all months names unique
     * (i.e. 01, 02, 03, ...), so there is no ambiguity and theoretically it is possible to parse
     * them, however SimpleDateFormat doesn't allow it. UK locale is ambiguous
     * (i.e. J, F, M, A, M, J, J, ...) so it is impossible to distinguish January, June or July in
     * narrow form.
     */
    public void testParseNarrowFormat_throws() {
        // narrow format for months is not ambiguous (01, 02, 03, ...)
        checkParseNarrowFormat_throws(Locale.forLanguageTag("ak"));
        // narrow format for months is ambiguous (J, F, M, A, M, J, J, ...).
        checkParseNarrowFormat_throws(Locale.UK);
    }

    private static void checkParseNarrowFormat_throws(Locale locale) {
        final Date date = new Date(0);
        final TimeZone tz = TimeZone.getTimeZone("UTC");

        // Format the date with DateFormatSymbol data and parsing it with Calendar data
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMM", locale);
        sdf.setTimeZone(tz);
        String formattedDate = sdf.format(date);

        // non-gregorian calendar to trigger getDisplayNamesMap() call while
        // parsing narrow format date
        sdf.setCalendar(new NonGregorianCalendar(tz, locale));

        try {
            sdf.parse(formattedDate);
            fail(String.format("Parsed unparseable date on %s locale", locale.toLanguageTag()));
        } catch (ParseException expected) {
        }
    }

    /**
     * Format a date using a "non-gregorian" calendar. This means that we use a calendar that is not
     * exactly {@code java.util.GregorianCalendar} as checked by
     * {@link SimpleDateFormat#useDateFormatSymbols()}.
     */
    private static String formatDateNonGregorianCalendar(String fmt, Locale locale) {
        DateFormat dateFormat = new SimpleDateFormat(fmt, locale);
        NonGregorianCalendar cal = new NonGregorianCalendar();
        cal.clear();
        cal.setTimeZone(UTC);
        dateFormat.setCalendar(cal);
        return dateFormat.format(new Date(0));
    }

    /**
     * Calendar that pretends that it's not a GregorianCalendar, for {@link
     * #testDisplayNamesOnNonGregorianCalendar()}.
     */
    private static class NonGregorianCalendar extends GregorianCalendar {
        NonGregorianCalendar() {
            super();
        }

        NonGregorianCalendar(TimeZone timeZone, Locale locale) {
            super(timeZone, locale);
        }
    }

    /**
     * A GregorianCalendar whose {@code getDisplayNames(int, int, Locale)} returns a
     * {@link NavigableMap} which is needed to make {@link SimpleDateFormat#useDateFormatSymbols}
     * explicitly use date format symbols for {@link #testMatchStringSortedMap_treeMap()} and
     * {@link #testMatchStringSortedMap_treeMapWithReverseOrderComparator()} which test for usage of
     * instances of {@link java.util.SortedMap} in
     * {@link SimpleDateFormat#matchString(java.lang.String, int, int, java.util.Map, java.text.CalendarBuilder)}.
     *
     * @see http://b/119913354
     */
    private static class NonGregorianCalendarWithTreeMapDisplayNames extends GregorianCalendar {

        private Comparator<String> mapComparator;

        NonGregorianCalendarWithTreeMapDisplayNames(@Nullable Comparator<String> comparator) {
            this.mapComparator = comparator;
        }

        @Override
        public Map<String, Integer> getDisplayNames(int field, int style, Locale locale) {
            Map<String, Integer> result = super.getDisplayNames(field, style, locale);
            TreeMap<String, Integer> treeMap = new TreeMap<>(this.mapComparator);
            treeMap.putAll(result);
            return treeMap;
        }
    }

}
