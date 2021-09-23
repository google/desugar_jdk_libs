/*
 * Copyright (C) 2021 The Android Open Source Project
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
package libcore.highmemorytest.java.time.format;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

/**
 * Additional tests for {@link DateTimeFormatter}. Runs in a separate java package because
 * loading data in all locales creates a lot of garbage / permanent heap growth in ICU4J and
 * causes gcstress test failing in ART test environment. See http://b/161420453.
 *
 * @see tck.java.time.format.TCKDateTimeFormatter
 * @see test.java.time.format.TestDateTimeFormatter
 * @see libcore.java.time.format.DateTimeFormatterTest
 */
@RunWith(Parameterized.class)
public class DateTimeFormatterTest {

    @Parameterized.Parameters(name = "{0}")
    public static Locale[] getAllLocales() {
        return Locale.getAvailableLocales();
    }

    @Parameterized.Parameter
    public Locale locale;

    // 1 January 2022 00:00:00 GMT+00:00
    private static final Instant TEST_INSTANT = Instant.ofEpochSecond(1640995200L);

    /**
     * Test {@link DateTimeFormatter#format(TemporalAccessor)} does not crash on available locales.
     */
    @Test
    public void test_format_allLocales() {
        for (FormatStyle formatStyle : FormatStyle.values()) {
            try {
                DateTimeFormatter.ofLocalizedTime(formatStyle)
                        .withLocale(locale)
                        .withZone(ZoneOffset.UTC)
                        .format(TEST_INSTANT);

                DateTimeFormatter.ofLocalizedDate(formatStyle)
                        .withLocale(locale)
                        .withZone(ZoneOffset.UTC)
                        .format(TEST_INSTANT);

                DateTimeFormatter.ofLocalizedDateTime(formatStyle)
                        .withLocale(locale)
                        .withZone(ZoneOffset.UTC)
                        .format(TEST_INSTANT);
            } catch (RuntimeException cause) {
                throw new RuntimeException("formatStyle:" + formatStyle.name(), cause);
            }
        }
    }

    /**
     * Test {@link DateTimeFormatter#format(TemporalAccessor)} does not crash on available locales
     * with all possible Chronologies.
     */
    @Test
    public void test_format_allLocales_allChronologies() {
        for (Chronology chronology : Chronology.getAvailableChronologies()) {
            for (FormatStyle formatStyle : FormatStyle.values()) {
                try {
                    DateTimeFormatter.ofLocalizedTime(formatStyle)
                            .withLocale(locale)
                            .withChronology(chronology)
                            .withZone(ZoneOffset.UTC)
                            .format(TEST_INSTANT);

                    DateTimeFormatter.ofLocalizedDate(formatStyle)
                            .withLocale(locale)
                            .withChronology(chronology)
                            .withZone(ZoneOffset.UTC)
                            .format(TEST_INSTANT);

                    DateTimeFormatter.ofLocalizedDateTime(formatStyle)
                            .withLocale(locale)
                            .withChronology(chronology)
                            .withZone(ZoneOffset.UTC)
                            .format(TEST_INSTANT);
                } catch (RuntimeException cause) {
                    throw new RuntimeException("Chronology:" + chronology +
                            " formatStyle:" + formatStyle.name(), cause);
                }
            }
        }
    }
}
