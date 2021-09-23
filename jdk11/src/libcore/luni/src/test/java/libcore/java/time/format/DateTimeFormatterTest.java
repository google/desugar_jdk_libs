/*
 * Copyright (C) 2017 The Android Open Source Project
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
package libcore.java.time.format;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Additional tests for {@link DateTimeFormatter}.
 *
 * @see tck.java.time.format.TCKDateTimeFormatter
 * @see test.java.time.format.TestDateTimeFormatter
 */
@RunWith(JUnit4.class)
public class DateTimeFormatterTest {

    @Ignore("b/183067147")
    @Test
    public void test_getDecimalStyle() {
        Locale arLocale = Locale.forLanguageTag("ar");
        DateTimeFormatter[] formatters = new DateTimeFormatter[] {
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                new DateTimeFormatterBuilder().toFormatter(),
                new DateTimeFormatterBuilder().toFormatter(Locale.ROOT),
                new DateTimeFormatterBuilder().toFormatter(Locale.ENGLISH),
                new DateTimeFormatterBuilder().toFormatter(arLocale),
        };

        DecimalStyle arDecimalStyle = DecimalStyle.of(arLocale);
        // Verify that the Locale ar returns a DecimalStyle other than STANDARD.
        assertNotEquals(DecimalStyle.STANDARD, arDecimalStyle);

        for (DateTimeFormatter formatter : formatters) {
            // All DateTimeFormatters should use the standard style, unless explicitly changed.
            assertEquals(formatter.toString(), DecimalStyle.STANDARD, formatter.getDecimalStyle());

            DateTimeFormatter arStyleFormatter = formatter.withDecimalStyle(arDecimalStyle);
            assertEquals(arStyleFormatter.toString(),
                    arDecimalStyle, arStyleFormatter.getDecimalStyle());

            // Verify that calling withDecimalStyle() doesn't modify the original formatter.
            assertEquals(formatter.toString(), DecimalStyle.STANDARD, formatter.getDecimalStyle());
        }
    }

    @Ignore("b/183067147")
    // Regression test for http://b/170717042.
    @Test
    public void test_format_locale_agq() {
        Locale locale = new Locale("agq");
        ZonedDateTime zonedDateTime = Instant.EPOCH.atZone(ZoneId.of("UTC"));
        assertEquals("kɨbâ kɨ 1",
                formatWithPattern(locale, "qqqq"/* standalone full quarter */, zonedDateTime));
    }

    @Ignore("b/183067147")
    @Test
    public void test_format_locale_en_US() {
        Locale locale = Locale.US;
        ZonedDateTime zonedDateTime = Instant.EPOCH.atZone(ZoneId.of("UTC"));
        assertEquals("1st quarter",
                formatWithPattern(locale, "qqqq"/* standalone full quarter */, zonedDateTime));
    }

    private static String formatWithPattern(Locale l, String pattern, TemporalAccessor datetime) {
        return DateTimeFormatter.ofPattern(pattern, l).format(datetime);
    }

    // 1 January 2022 00:00:00 GMT+00:00
    private static final Instant TEST_INSTANT = Instant.ofEpochSecond(1640995200L);

    @Ignore("b/183067147")
    // Regression test for http://b/174804526 when DateTimeFormatter fetches symbol 'B' from ICU.
    @Test
    public void test_format_locale_my_MM() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                .withLocale(new Locale("my", "MM"))
                .withZone(ZoneOffset.UTC);
        assertEquals("0:00", dateTimeFormatter.format(TEST_INSTANT));
        TemporalAccessor accessor = dateTimeFormatter.parse("23:59");
        assertEquals(23, accessor.getLong(ChronoField.HOUR_OF_DAY));
        assertEquals(59, accessor.getLong(ChronoField.MINUTE_OF_HOUR));
    }
}
