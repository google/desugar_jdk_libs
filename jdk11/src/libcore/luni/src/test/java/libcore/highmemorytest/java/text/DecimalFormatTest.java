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

package libcore.highmemorytest.java.text;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;

/**
 * Runs in a separate java package because loading data in all locales creates a lot of garbage /
 * permanent heap growth in ICU4J and causes gcstress test failing in ART test environment.
 * See http://b/161420453.
 */
@RunWith(Parameterized.class)
public class DecimalFormatTest {

    @Parameterized.Parameters(name = "{0}")
    public static Locale[] getAllLocales() {
        return Locale.getAvailableLocales();
    }

    @Parameterized.Parameter(0)
    public Locale locale;

    /**
     * Test no extra spacing between currency symbol and the numeric amount
     */
    @Test
    public void testCurrencySymbolSpacing() {
        Currency currency = Currency.getInstance(Locale.US);
        for (Locale locale : Locale.getAvailableLocales()) {
            DecimalFormatSymbols dfs = new DecimalFormatSymbols(locale);
            String formattedZero = new DecimalFormat("0", dfs).format(0);

            assertCurrencyFormat("USD" + formattedZero, "\u00a4\u00a40", dfs, currency, locale);
            assertCurrencyFormat(formattedZero + "USD", "0\u00a4\u00a4", dfs, currency, locale);
            assertCurrencyFormat(currency.getSymbol(locale) + formattedZero, "\u00a40", dfs,
                    currency, locale);
            assertCurrencyFormat(formattedZero + currency.getSymbol(locale), "0\u00a4", dfs,
                    currency, locale);
        }
    }

    private static void assertCurrencyFormat(String expected, String pattern,
            DecimalFormatSymbols dfs, Currency currency, Locale locale) {
        DecimalFormat df = new DecimalFormat(pattern, dfs);
        df.setCurrency(currency);
        df.setMaximumFractionDigits(0);
        assertEquals("Not formatted as expected with pattern " + pattern + " in locale " + locale,
                expected, df.format(0));
    }
}
