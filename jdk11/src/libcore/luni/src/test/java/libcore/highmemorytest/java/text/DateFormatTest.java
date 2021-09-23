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

package libcore.highmemorytest.java.text;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Tests {@link DateFormat} but runs in a separate java package because loading data in all locales
 * creates a lot of garbage / permanent heap growth in ICU4J and causes gcstress test failing
 * in ART test environment. See http://b/161420453. No known users/apps load all locale data like
 * this test.
 */
@RunWith(Parameterized.class)
public class DateFormatTest {

    @Parameterized.Parameters(name = "{0}")
    public static Locale[] getAllLocales() {
        return Locale.getAvailableLocales();
    }

    @Parameterized.Parameter(0)
    public Locale locale;

    // 1 January 2022 00:00:00 GMT+00:00
    private static final Date TEST_DATE = new Date(1640995200000L);

    /**
     * Test {@link DateFormat#format(Date)} does not crash on available locales.
     */
    @Test
    public void test_format_allLocales() {
        for (int formatStyle = DateFormat.FULL; formatStyle <= DateFormat.SHORT;
                formatStyle++) {
            try {
                assertNonEmpty(DateFormat.getDateInstance(formatStyle, locale).format(TEST_DATE));
                assertNonEmpty(DateFormat.getTimeInstance(formatStyle, locale).format(TEST_DATE));
                assertNonEmpty(DateFormat.getDateTimeInstance(formatStyle, formatStyle, locale)
                        .format(TEST_DATE));
            } catch (RuntimeException cause) {
                throw new RuntimeException("locale:" + locale +
                        " formatStyle:" + formatStyle, cause);
            }
        }
    }

    /**
     * Test {@link SimpleDateFormat#toPattern()} contains only supported symbols.
     */
    @Test
    public void test_toPattern_allLocales() {
        for (int formatStyle = DateFormat.FULL; formatStyle <= DateFormat.SHORT;
                formatStyle++) {
            try {
                assertSupportedSymbols(DateFormat.getDateInstance(formatStyle, locale), locale);
                assertSupportedSymbols(DateFormat.getTimeInstance(formatStyle, locale), locale);
                assertSupportedSymbols(DateFormat.getDateTimeInstance(
                        formatStyle, formatStyle, locale), locale);
            } catch (RuntimeException cause) {
                throw new RuntimeException("locale:" + locale +
                        " formatStyle:" + formatStyle, cause);
            }
        }
    }

    private static final Set<Character> SUPPORTED_SYMBOLS = "GyYMLwWDdFEuaHkKhmsSzZXLc".chars()
            .mapToObj(c -> (char)c)
            .collect(Collectors.toSet());

    private static void assertSupportedSymbols(DateFormat dateFormat, Locale locale) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) dateFormat;
        String pattern = simpleDateFormat.toPattern();
        // The string in the quotation is not interpreted.
        boolean inQuotation = false;
        for (int i = 0; i < pattern.length(); i++) {
            char curr = pattern.charAt(i);
            if (curr == '\'') {
                inQuotation = !inQuotation;
                continue;
            }
            if (inQuotation) {
                continue;
            }

            if ((curr >= 'a' && curr <= 'z') || (curr >= 'A' && curr <= 'Z')) { // ASCII alphabets
                assertTrue("Locale:" + locale + " Pattern:" + pattern + " has unsupported symbol "
                                + curr, SUPPORTED_SYMBOLS.contains(curr));
            }
        }
    }

    private static void assertNonEmpty(String s) {
        assertNotNull(s);
        assertTrue(s.length() > 0);
    }
}
