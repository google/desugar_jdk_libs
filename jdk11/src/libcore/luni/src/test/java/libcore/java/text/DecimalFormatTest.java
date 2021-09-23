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

import dalvik.system.VMRuntime;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Currency;
import java.util.Locale;

public class DecimalFormatTest extends junit.framework.TestCase {
    public void test_exponentSeparator() throws Exception {
        DecimalFormat df = new DecimalFormat("0E0");
        assertEquals("1E4", df.format(12345.));

        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setExponentSeparator("-useless-api-");
        df.setDecimalFormatSymbols(dfs);
        assertEquals("1-useless-api-4", df.format(12345.));
    }

    public void test_setMaximumFractionDigitsAffectsRoundingMode() throws Exception {
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
        df.setMaximumFractionDigits(0);
        df.setRoundingMode(RoundingMode.HALF_UP);
        assertEquals("-0", df.format(-0.2));
        df.setMaximumFractionDigits(1);
        assertEquals("-0.2", df.format(-0.2));
    }

    // Android fails this test, truncating to 127 digits.
    public void test_setMaximumIntegerDigits() throws Exception {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumIntegerDigits(400);
        // The RI's documentation suggests that the int should be formatted to 309 characters --
        // a magic number they don't explain -- but the BigInteger should be formatted to the 400
        // characters we asked for. In practice, the RI uses 309 in both cases.
        assertEquals(309, numberFormat.format(123).length());
        assertEquals(309, numberFormat.format(BigInteger.valueOf(123)).length());
    }

    // Regression test for http://b/1897917: BigDecimal does not take into account multiplier.
    public void testBigDecimalBug1897917() {
        // For example. the BigDecimal 0.17 formatted in PercentInstance is 0% instead of 17%:
        NumberFormat pf = NumberFormat.getPercentInstance();
        assertEquals("17%", pf.format(BigDecimal.valueOf(0.17)));

        // Test long decimal formatted in PercentInstance with various fractions.
        String longDec = "11.2345678901234567890123456789012345678901234567890";
        BigDecimal bd = new BigDecimal(longDec);
        assertBigDecimalWithFraction(bd, "1,123.46%", 2);
        assertBigDecimalWithFraction(bd, "1,123.45678901%", 8);
        assertBigDecimalWithFraction(bd, "1,123.4567890123%", 10);
        assertBigDecimalWithFraction(bd, "1,123.45678901234567890123%", 20);
        assertBigDecimalWithFraction(bd, "1,123.456789012345678901234567890123%", 30);

        // Test trailing zeros.
        assertDecFmtWithMultiplierAndFraction("3333.33333333", 3, 4, "10,000");
        assertDecFmtWithMultiplierAndFraction("3333.33333333", -3, 4, "-10,000");
        assertDecFmtWithMultiplierAndFraction("0.00333333", 3, 4, "0.01");

        assertDecFmtWithMultiplierAndFractionByLocale("3330000000000000000000000000000000", 3, 4,
                    Locale.US, "9,990,000,000,000,000,000,000,000,000,000,000");

        Locale en_IN = new Locale("en", "IN");
        assertDecFmtWithMultiplierAndFractionByLocale("3330000000000000000000000000000000", 3, 4,
                en_IN, "9,99,00,00,00,00,00,00,00,00,00,00,00,00,00,00,000");
    }

    public void testBigDecimalTestBigIntWithMultiplier() {
        // Big integer tests.
        assertDecFmtWithMultiplierAndFractionByLocale("123456789012345", 10, 0,
                Locale.US, "1,234,567,890,123,450");
        assertDecFmtWithMultiplierAndFractionByLocale("12345678901234567890", 10, 0,
                Locale.US, "123,456,789,012,345,678,900");
        assertDecFmtWithMultiplierAndFractionByLocale("98765432109876543210987654321", 10, 0,
                Locale.US, "987,654,321,098,765,432,109,876,543,210");

        assertDecFmtWithMultiplierAndFractionByLocale("123456789012345", -10, 0,
                Locale.US, "-1,234,567,890,123,450");
        assertDecFmtWithMultiplierAndFractionByLocale("12345678901234567890", -10, 0,
                Locale.US, "-123,456,789,012,345,678,900");
        assertDecFmtWithMultiplierAndFractionByLocale("98765432109876543210987654321", -10, 0,
                Locale.US, "-987,654,321,098,765,432,109,876,543,210");

        Locale en_IN = new Locale("en", "IN");
        assertDecFmtWithMultiplierAndFractionByLocale("123456789012345", 10, 0,
                en_IN, "1,23,45,67,89,01,23,450");
        assertDecFmtWithMultiplierAndFractionByLocale("12345678901234567890", 10, 0,
                en_IN, "12,34,56,78,90,12,34,56,78,900");
        assertDecFmtWithMultiplierAndFractionByLocale("98765432109876543210987654321", 10, 0,
                en_IN, "9,87,65,43,21,09,87,65,43,21,09,87,65,43,210");

        assertDecFmtWithMultiplierAndFractionByLocale("123456789012345", -10, 0,
                en_IN, "-1,23,45,67,89,01,23,450");
        assertDecFmtWithMultiplierAndFractionByLocale("12345678901234567890", -10, 0,
                en_IN, "-12,34,56,78,90,12,34,56,78,900");
        assertDecFmtWithMultiplierAndFractionByLocale("98765432109876543210987654321", -10, 0,
                en_IN, "-9,87,65,43,21,09,87,65,43,21,09,87,65,43,210");
    }

    // Test for http://b/168304209
    public void testFieldPosition() {
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat currencyDf = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        DecimalFormat percentDf = (DecimalFormat) NumberFormat.getPercentInstance(Locale.US);
        DecimalFormat milledf = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        milledf.applyPattern("#,##0\u2030;-#,##0\u2030");
        DecimalFormat scientificDf = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        scientificDf.applyPattern("0.###E0");

        // Reference behaviors of different field positions when formatting a simple integer.
        assertFieldPosition4Types(df, 123, NumberFormat.Field.INTEGER, "123", 0, 3);
        assertFieldPosition4Types(df, 123, NumberFormat.Field.FRACTION, "123", 3, 3);
        assertFieldPosition4Types(df, 123, NumberFormat.Field.CURRENCY, "123", 0, 0);
        assertFieldPosition4Types(df, 123, NumberFormat.Field.DECIMAL_SEPARATOR, "123", 0, 0);
        assertFieldPosition4Types(df, 123, NumberFormat.Field.EXPONENT, "123", 0, 0);
        assertFieldPosition4Types(df, 123, NumberFormat.Field.EXPONENT_SIGN, "123", 0, 0);
        assertFieldPosition4Types(df, 123, NumberFormat.Field.EXPONENT_SYMBOL, "123", 0, 0);
        assertFieldPosition4Types(df, 123, NumberFormat.Field.GROUPING_SEPARATOR, "123", 0, 0);
        assertFieldPosition4Types(df, 123, NumberFormat.Field.PERCENT, "123", 0, 0);
        assertFieldPosition4Types(df, 123, NumberFormat.Field.PERMILLE, "123", 0, 0);
        assertFieldPosition4Types(df, 123, NumberFormat.Field.SIGN, "123", 0, 0);

        assertFieldPosition4Types(currencyDf, 123, NumberFormat.Field.CURRENCY, "$123.00", 0, 1);
        assertFieldPosition(df, 123.4, NumberFormat.Field.DECIMAL_SEPARATOR, "123.4", 3, 4);
        assertFieldPosition4Types(scientificDf, 123, NumberFormat.Field.EXPONENT, "1.23E2", 5, 6);
        assertFieldPosition(scientificDf, 0.123, NumberFormat.Field.EXPONENT_SIGN, "1.23E-1", 5, 6);
        assertFieldPosition4Types(scientificDf, 123, NumberFormat.Field.EXPONENT_SYMBOL, "1.23E2",
                4, 5);
        assertFieldPosition4Types(df, 1234, NumberFormat.Field.GROUPING_SEPARATOR, "1,234", 1, 2);
        assertFieldPosition4Types(percentDf, 12, NumberFormat.Field.PERCENT, "1,200%", 5, 6);
        assertFieldPosition4Types(milledf, 12, NumberFormat.Field.PERMILLE, "12,000\u2030", 6, 7);
        assertFieldPosition4Types(df, -123, NumberFormat.Field.SIGN, "-123", 0, 1);

        BigInteger bigInteger = new BigInteger("999999999999999999999999"); // 24 of '9';
        // Assert this large number is larger than the max possible long value.
        assertEquals(1, bigInteger.compareTo(BigInteger.valueOf(Long.MAX_VALUE)));
        String expectedStr = "999,999,999,999,999,999,999,999";
        assertFieldPosition(df, bigInteger, NumberFormat.Field.INTEGER, expectedStr, 0,
                expectedStr.length());
        expectedStr = "$" + expectedStr + ".00";
        assertFieldPosition(currencyDf, bigInteger, NumberFormat.Field.INTEGER, expectedStr, 1,
                expectedStr.length() - 3);
        assertFieldPosition(currencyDf, bigInteger, NumberFormat.Field.CURRENCY, expectedStr, 0, 1);

        BigDecimal bigDecimal = BigDecimal.valueOf(123.45);
        assertFieldPosition(df, bigDecimal, NumberFormat.Field.INTEGER, "123.45", 0, 3);
        assertFieldPosition(currencyDf, bigDecimal, NumberFormat.Field.CURRENCY, "$123.45", 0, 1);
    }

    /**
     * Run the test with {@param num} in 4 different types, i.e. long, double, BigInteger
     * and BigDecimal to increase the test coverage, because the 4 types have 4 different code paths
     * internally.
     */
    private void assertFieldPosition4Types(DecimalFormat df, long num, NumberFormat.Field field,
            String expectedStr, int expectedBeginIndex, int expectedEndIndex) {
        assertFieldPosition(df, num, field, expectedStr, expectedBeginIndex, expectedEndIndex);
        assertFieldPosition(df, (double) num, field, expectedStr, expectedBeginIndex,
                expectedEndIndex);
        assertFieldPosition(df, BigInteger.valueOf(num), field, expectedStr, expectedBeginIndex,
                expectedEndIndex);
        assertFieldPosition(df, BigDecimal.valueOf(num), field, expectedStr, expectedBeginIndex,
                expectedEndIndex);
    }

    private void assertFieldPosition(DecimalFormat df, long num, NumberFormat.Field field,
            String expectedStr, int expectedBeginIndex, int expectedEndIndex) {
        FieldPosition fp = new FieldPosition(field);
        StringBuffer stringBuffer = new StringBuffer();
        df.format(num, stringBuffer, fp);
        assertEquals(expectedStr, stringBuffer.toString());
        assertEquals(expectedBeginIndex, fp.getBeginIndex());
        assertEquals(expectedEndIndex, fp.getEndIndex());
    }

    private void assertFieldPosition(DecimalFormat df, double num, NumberFormat.Field field,
            String expectedStr, int expectedBeginIndex, int expectedEndIndex) {
        FieldPosition fp = new FieldPosition(field);
        StringBuffer stringBuffer = new StringBuffer();
        df.format(num, stringBuffer, fp);
        assertEquals(expectedStr, stringBuffer.toString());
        assertEquals(expectedBeginIndex, fp.getBeginIndex());
        assertEquals(expectedEndIndex, fp.getEndIndex());
    }

    private void assertFieldPosition(DecimalFormat df, BigInteger num, NumberFormat.Field field,
            String expectedStr, int expectedBeginIndex, int expectedEndIndex) {
        FieldPosition fp = new FieldPosition(field);
        StringBuffer stringBuffer = new StringBuffer();
        df.format(num, stringBuffer, fp);
        assertEquals(expectedStr, stringBuffer.toString());
        assertEquals(expectedBeginIndex, fp.getBeginIndex());
        assertEquals(expectedEndIndex, fp.getEndIndex());
    }

    private void assertFieldPosition(DecimalFormat df, BigDecimal num, NumberFormat.Field field,
            String expectedStr, int expectedBeginIndex, int expectedEndIndex) {
        FieldPosition fp = new FieldPosition(field);
        StringBuffer stringBuffer = new StringBuffer();
        df.format(num, stringBuffer, fp);
        assertEquals(expectedStr, stringBuffer.toString());
        assertEquals(expectedBeginIndex, fp.getBeginIndex());
        assertEquals(expectedEndIndex, fp.getEndIndex());
    }

    public void testBigDecimalICUConsistency() {
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        df.setMaximumFractionDigits(2);
        df.setMultiplier(2);
        assertEquals(df.format(BigDecimal.valueOf(0.16)),
                df.format(BigDecimal.valueOf(0.16).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(0.0293)),
                df.format(BigDecimal.valueOf(0.0293).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(0.006)),
                df.format(BigDecimal.valueOf(0.006).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(0.00283)),
                df.format(BigDecimal.valueOf(0.00283).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(1.60)),
        df.format(BigDecimal.valueOf(1.60).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(15)),
                df.format(BigDecimal.valueOf(15).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(170)),
                df.format(BigDecimal.valueOf(170).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(234.56)),
                df.format(BigDecimal.valueOf(234.56).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(0)),
        df.format(BigDecimal.valueOf(0).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(-1)),
        df.format(BigDecimal.valueOf(-1).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(-10000)),
        df.format(BigDecimal.valueOf(-10000).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(-0.001)),
                df.format(BigDecimal.valueOf(-0.001).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(1234567890.1234567)),
                df.format(BigDecimal.valueOf(1234567890.1234567).doubleValue()));
        assertEquals(df.format(BigDecimal.valueOf(1.234567E100)),
                df.format(BigDecimal.valueOf(1.234567E100).doubleValue()));
    }

    private void assertBigDecimalWithFraction(BigDecimal bd, String expectedResult, int fraction) {
        NumberFormat pf = NumberFormat.getPercentInstance();
        pf.setMaximumFractionDigits(fraction);
        assertEquals(expectedResult, pf.format(bd));
    }

    private void assertDecFmtWithMultiplierAndFraction(String value, int multiplier, int fraction, String expectedResult) {
        DecimalFormat df = (DecimalFormat)NumberFormat.getInstance();
        df.setMultiplier(multiplier);
        df.setMaximumFractionDigits(fraction);
        BigDecimal d = new BigDecimal(value);
        assertEquals(expectedResult, df.format(d));
    }

    private void assertDecFmtWithMultiplierAndFractionByLocale(String value, int multiplier, int fraction, Locale locale, String expectedResult) {
        DecimalFormat df = (DecimalFormat)NumberFormat.getIntegerInstance(locale);
        df.setMultiplier(multiplier);
        df.setMaximumFractionDigits(fraction);
        BigDecimal d = new BigDecimal(value);
        assertEquals(expectedResult, df.format(d));
    }

    public void testSetZeroDigitForPattern() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setZeroDigit('a');
        DecimalFormat formatter = new DecimalFormat();
        formatter.setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.applyLocalizedPattern("#.aa");
        assertEquals("e.fa", formatter.format(4.50));
    }

    public void testSetZeroDigitForFormatting() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setZeroDigit('a');
        DecimalFormat formatter = new DecimalFormat();
        formatter.setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.applyLocalizedPattern("#");
        assertEquals("eadacab", formatter.format(4030201));
    }

    public void testBug9087737() throws Exception {
        DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        // These shouldn't make valgrind unhappy.
        df.setCurrency(Currency.getInstance("CHF"));
        df.setCurrency(Currency.getInstance("GBP"));
    }

    // Check we don't crash on null inputs.
    public void testBug15081434() throws Exception {
      DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
      try {
        df.parse(null);
        fail();
      } catch (NullPointerException expected) {
      }

      try {
        df.applyLocalizedPattern(null);
        fail();
      } catch (NullPointerException expected) {
      }

      try {
        df.applyPattern(null);
        fail();
      } catch (NullPointerException expected) {
      }

      try {
        df.applyPattern(null);
        fail();
      } catch (NullPointerException expected) {
      }

      try {
        df.format(null, new StringBuffer(), new FieldPosition(0));
        fail();
      } catch (IllegalArgumentException expected) {
      }

      try {
        df.parse(null, new ParsePosition(0));
        fail();
      } catch (NullPointerException expected) {
      }

      // This just ignores null.
      df.setDecimalFormatSymbols(null);

      try {
        df.setCurrency(null);
        fail();
      } catch (NullPointerException expected) {
      }

      try {
        df.setNegativePrefix(null);
        fail();
      } catch (NullPointerException expected) {
      }

      try {
        df.setNegativeSuffix(null);
        fail();
      } catch (NullPointerException expected) {
      }

      try {
        df.setPositivePrefix(null);
        fail();
      } catch (NullPointerException expected) {
      }

      try {
        df.setPositiveSuffix(null);
        fail();
      } catch (NullPointerException expected) {
      }

      try {
        df.setRoundingMode(null);
        fail();
      } catch (NullPointerException expected) {
      }
    }

    // Confirm the fraction digits do not change when the currency is changed.
    public void testBug71369() {
        final String nonBreakingSpace = "\u00A0";

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMAN);
        numberFormat.setCurrency(Currency.getInstance("USD"));

        assertEquals("2,01" + nonBreakingSpace + "$", numberFormat.format(2.01));

        numberFormat.setMinimumFractionDigits(0);
        numberFormat.setMaximumFractionDigits(0);

        String expected = "2" + nonBreakingSpace + "$";
        assertEquals(expected, numberFormat.format(2.01));

        // Changing the currency must not reset the digits.
        numberFormat.setCurrency(Currency.getInstance("EUR"));
        numberFormat.setCurrency(Currency.getInstance("USD"));

        assertEquals(expected, numberFormat.format(2.01));
    }

    // http://b/27855939
    public void testBug27855939() {
        DecimalFormat df = new DecimalFormat("00");
        assertEquals("01", df.format(BigDecimal.ONE));
        assertEquals("00", df.format(BigDecimal.ZERO));
    }

    // Confirm the currency symbol used by a format is determined by the locale of the format
    // not the current default Locale.
    public void testSetCurrency_symbolOrigin() {
        Currency currency = Currency.getInstance("CNY");
        Locale locale1 = Locale.CHINA;
        Locale locale2 = Locale.US;
        String locale1Symbol = currency.getSymbol(locale1);
        String locale2Symbol = currency.getSymbol(locale2);
        // This test only works if we can tell where the symbol came from, which requires they are
        // different across the two locales chosen.
        assertFalse(locale1Symbol.equals(locale2Symbol));

        Locale originalLocale = Locale.getDefault();
        try {
            Locale.setDefault(locale1);
            String amountDefaultLocale1 =
                    formatArbitraryCurrencyAmountInLocale(currency, locale2);

            Locale.setDefault(locale2);
            String amountDefaultLocale2 =
                    formatArbitraryCurrencyAmountInLocale(currency, locale2);

            // This used to fail because Currency.getSymbol() was used without providing the
            // format's locale.
            assertEquals(amountDefaultLocale1, amountDefaultLocale2);
        } finally {
            Locale.setDefault(originalLocale);
        }
    }

    // Test that overriding the currency symbol survives a roundrip through the
    // DecimalFormat constructor.
    // http://b/28732330
    public void testSetCurrencySymbol() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.US);
        decimalFormatSymbols.setCurrencySymbol("¥");
        DecimalFormat decimalFormat = new DecimalFormat("¤#,##0.00", decimalFormatSymbols);
        assertEquals("¥", decimalFormat.getDecimalFormatSymbols().getCurrencySymbol());
    }

    private String formatArbitraryCurrencyAmountInLocale(Currency currency, Locale locale) {
        NumberFormat localeCurrencyFormat = NumberFormat.getCurrencyInstance(locale);
        localeCurrencyFormat.setCurrency(currency);
        return localeCurrencyFormat.format(1000);
    }

    /**
     * DecimalFormat doesn't support different group separator for currency and non-currency
     * number formats. Ensure normal group separator is used, and ignore monetary group separator
     * when formatting currency. http://b/37135768
     */
    public void testLocaleGroupingSeparator() {
        // CLDR uses '.' for currency and U+00a0 for non-currency number formats in de_AT
        // Assert ICU is using these characters
        Locale locale = new Locale("de", "AT");
        android.icu.text.DecimalFormatSymbols icuDfs =
                new android.icu.text.DecimalFormatSymbols(locale);
        assertEquals(icuDfs.getGroupingSeparator(), '\u00a0');
        assertEquals(icuDfs.getMonetaryGroupingSeparator(), '.');

        // In this class, only U+00a0 should be used for both cases.
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(locale);
        // Assert CLDR uses U+00a0 as grouping separator
        assertEquals(dfs.getGroupingSeparator(), '\u00a0');

        // Test non-currency number formats
        assertEquals("1\u00a0234,00", new DecimalFormat("#,##0.00", dfs).format(1234));
        // Test currency format
        assertEquals("\u20ac1\u00a0234,00", new DecimalFormat("¤#,##0.00", dfs).format(1234));
    }

    /**
     * Test {@link DecimalFormatSymbols#setGroupingSeparator(char)} for currency and non-currency
     * number formats. http://b/37135768
     */
    public void testSetGroupingSeparator() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        dfs.setGroupingSeparator(' ');
        // Test non-currency number formats
        assertEquals("1 234.00", new DecimalFormat("#,##0.00", dfs).format(1234));
        // Test currency format
        assertEquals("$1 234.00", new DecimalFormat("¤#,##0.00", dfs).format(1234));
    }

    /**
     * PerMill should be truncated into one char. http://b/67034519
     */
    public void testPerMill() {
        String pattern = "0\u2030";
        double number = 0.1;
        Locale locale;

        // Test US locale behavior: java.text perMill char is expected to be \u2030.
        locale = Locale.US;
        {
            android.icu.text.DecimalFormat df = new android.icu.text.DecimalFormat(pattern,
                    new android.icu.text.DecimalFormatSymbols(locale));
            assertEquals("100\u2030", df.format(number));
        }
        for (DecimalFormat df : createDecimalFormatInstances(locale, pattern)) {
            assertEquals("100\u2030", df.format(number));
        }

        // Test setPerMill() works
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(locale);
        dfs.setPerMill(';');
        assertEquals("100;", new DecimalFormat(pattern, dfs).format(number));

        // Confirm ICU and java.text agree. Test PerMill is localized.
        locale = new Locale("ar");
        {
            android.icu.text.DecimalFormat df = new android.icu.text.DecimalFormat(pattern,
                    new android.icu.text.DecimalFormatSymbols(locale));
            // Confirm the "correct" perMill sign uses a single non-ascii char.
            // ICU's perMill string for ar is known to use single non-ascii char U+0609
            assertEquals("\u0609", df.getDecimalFormatSymbols().getPerMillString());
            assertEquals("\u0661\u0660\u0660\u0609", df.format(number));
        }
        for (DecimalFormat df : createDecimalFormatInstances(locale, pattern)) {
            assertEquals('\u0609', df.getDecimalFormatSymbols().getPerMill());
            assertEquals("\u0661\u0660\u0660\u0609", df.format(number));
        }

        // Confirm ICU and java.text disagree.
        // java.text doesn't localize PerMill and fallback to default char U+2030
        // when PerMill in that locale has more than one visible characters.
        locale = Locale.forLanguageTag("en-US-POSIX");
        {
            android.icu.text.DecimalFormat df = new android.icu.text.DecimalFormat(pattern,
                    new android.icu.text.DecimalFormatSymbols(locale));
            // Confirm the "correct" perMill sign requires more than one char.
            // ICU's perMill string for en_US_POSIX is known to have more than one visible chars.
            assertEquals("0/00", df.getDecimalFormatSymbols().getPerMillString());
            assertEquals("1000/00", df.format(number));
        }
        for (DecimalFormat df : createDecimalFormatInstances(locale, pattern)) {
            assertEquals('\u2030', df.getDecimalFormatSymbols().getPerMill());
            assertEquals("100\u2030", df.format(number));
        }
    }

    /**
     * Percent should be truncated into one char.
     */
    public void testPercent() {
        String pattern = "0%";
        double number = 0.1;
        Locale locale;

        // Test US locale behavior: java.text percent char is expected to be '%'.
        locale = Locale.US;
        {
            android.icu.text.DecimalFormat df = new android.icu.text.DecimalFormat(pattern,
                    new android.icu.text.DecimalFormatSymbols(locale));
            assertEquals("10%", df.format(number));
        }
        for (DecimalFormat df : createDecimalFormatInstances(locale, pattern)) {
            assertEquals("10%", df.format(number));
        }

        // Test setPercent() works
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(locale);
        dfs.setPercent(';');
        assertEquals("10;", new DecimalFormat(pattern, dfs).format(number));

        // Confirm ICU and java.text disagree because java.text strips out bidi marker
        locale = new Locale("ar");
        {
            android.icu.text.DecimalFormat df = new android.icu.text.DecimalFormat(pattern,
                    new android.icu.text.DecimalFormatSymbols(locale));
            // Confirm the "correct" percent sign requires more than one char.
            // ICU's percent string for ar is known to have a bidi marker.
            assertEquals("\u066a\u061c", df.getDecimalFormatSymbols().getPercentString());
            assertEquals("\u0661\u0660\u066a\u061c", df.format(number));
        }
        for (DecimalFormat df : createDecimalFormatInstances(locale, pattern)) {
            // Confirm that java.text.DecimalFormat strips bidi marker characters.
            // so the ar percent can fit in a single char.
            assertEquals('\u066a', df.getDecimalFormatSymbols().getPercent());
            assertEquals("\u0661\u0660\u066a", df.format(number));
        }
    }

    /**
     * Minus sign should be truncated into one char.
     */
    public void testMinusSign() {
        String pattern = "0;-0";
        double number = -123;
        Locale locale;

        // Test US locale behavior: java.text percent char is expected to be '-'.
        locale = Locale.US;
        assertEquals("-123", new DecimalFormat(pattern, new DecimalFormatSymbols(locale))
                .format(number));
        assertEquals("-123", new android.icu.text.DecimalFormat(pattern,
                new android.icu.text.DecimalFormatSymbols(locale)).format(number));

        // Confirm ICU and java.text agree. Minus sign is localized
        locale = new Locale("lt");
        {
            android.icu.text.DecimalFormat df = new android.icu.text.DecimalFormat(pattern,
                    new android.icu.text.DecimalFormatSymbols(locale));
            // Confirm the "correct" minus sign uses a single non-ascii char.
            // ICU's minus string for ar is known to use single non-ascii char U+2212
            assertEquals("\u2212", df.getDecimalFormatSymbols().getMinusSignString());
            assertEquals("\u2212123", df.format(number));
        }
        for (DecimalFormat df : createDecimalFormatInstances(locale, pattern)) {
            assertEquals('\u2212', df.getDecimalFormatSymbols().getMinusSign());
            assertEquals("\u2212123", df.format(number));
        }

        // Confirm ICU and java.text disagree because java.text strips out bidi marker
        locale = new Locale("ar");
        {
            android.icu.text.DecimalFormat df = new android.icu.text.DecimalFormat(pattern,
                    new android.icu.text.DecimalFormatSymbols(locale));
            // Confirm the "correct" minus sign requires more than one char.
            // ICU's minus string for ar is known to have a bidi marker.
            assertEquals("\u061c\u002d", df.getDecimalFormatSymbols().getMinusSignString());
            assertEquals("\u061c\u002d\u0661\u0662\u0663", df.format(number));
        }
        for (DecimalFormat df : createDecimalFormatInstances(locale, pattern)) {
            assertEquals('\u002d', df.getDecimalFormatSymbols().getMinusSign());
            assertEquals("\u002d\u0661\u0662\u0663", df.format(number));
        }
    }

    /**
     * Plus sign should not be localized. http://b/67034519
     */
    public void testPlusSign() {
        // Test US Locale
        String pattern = "+0;-0";
        assertEquals("+123", new DecimalFormat(pattern, new DecimalFormatSymbols(Locale.US))
                .format(123));

        // Confirm ICU and java.text disagree because java.text doesn't localize plus sign.
        Locale locale = new Locale("ar");
        {
            android.icu.text.DecimalFormat df = new android.icu.text.DecimalFormat(pattern,
                    new android.icu.text.DecimalFormatSymbols(locale));
            // Confirm the "correct" plus sign requires more than one char.
            // ICU's plus string for ar is known to have a bidi marker.
            assertEquals("\u061c\u002b", df.getDecimalFormatSymbols().getPlusSignString());
            assertEquals("\u061c\u002b\u0661\u0662\u0663", df.format(123));
        }
        for (DecimalFormat df : createDecimalFormatInstances(locale, pattern)) {
            assertEquals("\u002b\u0661\u0662\u0663", df.format(123));
        }
    }

    /**
     * Pattern separator should be localized. http://b/112080617
     */
    public void testPatternSeparator() {
        // Test US locale
        {
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            assertEquals(';', df.getDecimalFormatSymbols().getPatternSeparator());
            // toLocalizedPattern() returns no pattern separator when input pattern has no prefix.
            // Add prefixes 'AAA'/'BBB' to force pattern separator in output pattern.
            df.applyLocalizedPattern("'AAA'+0;'BBB'-0");
            assertEquals("'AAA'+0;'BBB'-0", df.toLocalizedPattern());
            assertEquals("BBB-123", df.format(-123));
        }
        // Test a locale using non-ascii-semi-colon pattern separator.
        {
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(
                Locale.forLanguageTag("ar-EG"));
            assertEquals('\u061b', df.getDecimalFormatSymbols().getPatternSeparator());
            df.applyLocalizedPattern("'AAA'+\u0660\u061b'BBB'-\u0660");
            assertEquals("'AAA'+\u0660\u061b'BBB'-\u0660", df.toLocalizedPattern());
            assertEquals("BBB-\u0661\u0662\u0663", df.format(-123));
        }
    }

    /**
     * Returns DecimalFormat instances created in different ways:
     * <ol>
     * <li>Using an implicit DecimalFormatSymbols created using the default locale.</li>
     * <li>Using an explicit DecimalFormatSymbols object.
     * </ol>
     * This is to confirm the behavior is currently the same. In future we may choose to improve the
     * behavior when the caller doesn't provide an explicit DecimalFormatSymbols:
     * in that case we wouldn't have to pretend that some symbols fit into single char; because the
     * caller hasn't explicitly specified the symbols they want us to use and we'd be under no
     * obligation to use the limited char-based public API on DecimalFormatSymbols.
     */
    private static DecimalFormat[] createDecimalFormatInstances(Locale locale, String pattern) {
        Locale originalLocale = Locale.getDefault();
        Locale.setDefault(locale);
        DecimalFormat[] instances = new DecimalFormat[] {
                new DecimalFormat(pattern),
                new DecimalFormat(pattern, new DecimalFormatSymbols(locale))
        };
        Locale.setDefault(originalLocale);
        return instances;
    }

    // http://b/68143370
    public void testWhitespaceTolerated() {
        // Trailing space is tolerated, but not consumed.
        assertParsed("0", "1 ", 1, 1);
        // Digits after trailing space are ignored.
        assertParsed("0", "1 2", 1, 1);
        // Space after decimal point is treated as end of input.
        assertParsed("0", "1. 1", 1, 2);
        // Space before decimal point is treated as end of input.
        assertParsed("0", "1 .1", 1, 1);
        // Space after decimal digit is treated as end of input.
        assertParsed("0", "1.2 3", 1.2d, 3);
        // Leading space treated as part of negative prefix
        assertParsed("0; 0", " 1 ", -1, 2);
        // Leading space in prefix is accepted.
        assertParsed(" 0", " 1 ", 1, 2);
    }

    // http://b/68143370
    public void testWhitespaceError() {
        // Space before currency symbol is not tolerated.
        assertParseError("¤0", " $1");
        // Space after currency symbol is not tolerated.
        assertParseError("¤0", "$ 1");
        // Space before positive prefix is not tolerated.
        assertParseError("+0", " +1");
        // Space after positive prefix is not tolerated.
        assertParseError("+0", "+ 1");
        // Leading space is not tolerated.
        assertParseError("0", " 1");
        // Space in prefix is expected to be present.
        assertParseError(" 0", "1");
        // Extra space after prefix with space is not tolerated.
        assertParseError(" 0", "  1 ");
    }

    // Test that Bidi control character is not tolerated
    public void testParseBidi() {
        assertParseError("0", "\u200e1");
        assertParsed("0", "1\u200e", 1, 1);
        assertParseError("0%", "\u200e1%");
    }

    public void testParseGroupingSeparator() {
        // Test that grouping separator is optional when the group separator is specified
        assertParsedAndConsumedAll("#,##0", "9,999", 9999);
        assertParsedAndConsumedAll("#,##0", "9999", 9999);
        assertParsedAndConsumedAll("#,###0", "9,9999", 99999);

        // Test that grouping size doesn't affect parsing at all
        assertParsedAndConsumedAll("#,##0", "9,9999", 99999);
        assertParsedAndConsumedAll("#,###0", "99,999", 99999);

        assertParsedAndConsumedAll("###0", "9999", 9999);
        assertParsedAndConsumedAll("###0", "99999", 99999);

        // Test that grouping separator must not be present when the group separator is NOT specified
        // Only the 1st character in front of separator , should be consumed.
        assertParsed("###0", "9,9999", 9, 1);
        assertParsed("###0", "9,999", 9, 1);
    }

    public void testParseScienificNotation() {
        assertParsedAndConsumedAll("0.###E0", "1E-3", 0.001);
        assertParsedAndConsumedAll("0.###E0", "1E0", 1);
        assertParsedAndConsumedAll("0.###E0", "1E3", 1000);
        assertParsedAndConsumedAll("0.###E0", "1.111E3", 1111);
        assertParsedAndConsumedAll("0.###E0", "1.1E3", 1100);

        // "0.###E0" is engineering notation, i.e. the exponent should be a multiple of 3
        // for formatting. But it shouldn't affect parsing.
        assertParsedAndConsumedAll("0.###E0", "1E1", 10);

        // Test that exponent is not required for parsing
        assertParsedAndConsumedAll("0.###E0", "1.1", 1.1);
        assertParsedAndConsumedAll("0.###E0", "1100", 1100);

        // Test that the max of fraction, integer or signficant digits don't affect parsing
        // Note that the max of signficant digits is 4 = min integer digits (1)
        //   + max fraction digits (3)
        assertParsedAndConsumedAll("0.###E0", "1111.4E3", 1111400);
        assertParsedAndConsumedAll("0.###E0", "1111.9999E3", 1111999.9);
    }

    /*
     * ISO currency code parsing is case insensitive. http://b/112469513
     */
    public void testParseCurrencyIsoCode() {
        assertParsedAndConsumedAll("¤¤0", "USD10", 10);
        assertParsedAndConsumedAll("¤¤0", "usd10", 10);
        assertParsedAndConsumedAll("¤¤0", "Usd10", 10);
        assertParsedAndConsumedAll("¤¤0", "Usd10", 10);

        // DecimalFormat.parse is only required to parse the local currency for the Locale
        // associated with the DecimalFormat. assertParseError() uses Locale.US so it is expected to
        // fail to parse valid ISO codes except for USD.
        assertParseError("¤¤0", "GBP10");
    }

    // Test that getMaximumIntegerDigits should return value >= 309 by default, even though a
    // leading optional digit # is provided in the input pattern. 309 is chosen because
    // it is the upper limit of integer digits when formatting numbers other than BigInteger
    // and BigDecimal.
    public void testDefaultGetMaximumIntegerDigits() {
        Locale originalLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.US);
            DecimalFormat df = new DecimalFormat();
            int maxIntegerDigits = df.getMaximumIntegerDigits();
            assertTrue("getMaximumIntegerDigits should be >= 309, but returns " + maxIntegerDigits,
                    maxIntegerDigits >= 309);

            String[] patterns = new String[] { "0", "#0", "#.", "#", ".#", "#.#", "#,##0.00",
                    "#,##0.00%", "#,##0.00%", "¤#,##0.00%", "#00.00", "#,#00.00"
            };
            for (String pattern : patterns) {
                df = new DecimalFormat(pattern);
                maxIntegerDigits = df.getMaximumIntegerDigits();
                assertTrue("getMaximumIntegerDigits should be >= 309, but returns "
                                + maxIntegerDigits, maxIntegerDigits >= 309);
            }
        } finally {
            Locale.setDefault(originalLocale);
        }
    }


    private void assertParseError(String pattern, String input) {
        ParsePosition pos = new ParsePosition(0);
        DecimalFormat df = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(Locale.US));
        Number result = df.parse(input, pos);
        if (result != null) {
            fail(String.format("Parsed <%s> using <%s>, should have failed: %s",
                    input, pattern, describeParseResult(result, pos)));
        }
    }

    /**
     * Assert the expected number and the whole input string is consumed and parsed successfully.
     */
    private static void assertParsedAndConsumedAll(String pattern, String input, Number expected) {
        assertParsed(pattern, input, expected, input.length());
    }

    private static void assertParsed(String pattern, String input, Number expected,
            int expectedIndex) {
        ParsePosition expectedPos = new ParsePosition(expectedIndex);
        ParsePosition pos = new ParsePosition(0);
        DecimalFormat df = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(Locale.US));
        Number result = df.parse(input, pos);
        assertEquals("Parse <" + input + "> using <" + pattern + ">.",
                describeParseResult(expected, expectedPos), describeParseResult(result, pos));
    }

    private static String describeParseResult(Number result, ParsePosition pos) {
        return String.format("%s, index=%d, errorIndex=%d",
                result, pos.getIndex(), pos.getErrorIndex());
    }
}
