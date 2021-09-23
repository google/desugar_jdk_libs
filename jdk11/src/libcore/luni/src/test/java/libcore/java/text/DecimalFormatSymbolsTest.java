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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;

public class DecimalFormatSymbolsTest extends junit.framework.TestCase {
    public void test_getInstance_unknown_or_invalid_locale() throws Exception {
        // http://b/17374604: this test passes on the host but fails on the target.
        // ICU uses setlocale(3) to determine its default locale, and glibc (on my box at least)
        // returns "en_US.UTF-8". bionic before L returned NULL and in L returns "C.UTF-8", both
        // of which get treated as "en_US_POSIX". What that means for this test is that you get
        // "INF" for infinity instead of "\u221e".
        // On the RI, this test fails for a different reason: their DecimalFormatSymbols.equals
        // appears to be broken. It could be that they're accidentally checking the Locale field?
        checkLocaleIsEquivalentToRoot(new Locale("xx", "XX"));
        checkLocaleIsEquivalentToRoot(new Locale("not exist language", "not exist country"));
    }
    private void checkLocaleIsEquivalentToRoot(Locale locale) {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(locale);
        assertEquals(DecimalFormatSymbols.getInstance(Locale.ROOT), dfs);
    }

    // http://code.google.com/p/android/issues/detail?id=14495
    public void testSerialization() throws Exception {
        DecimalFormatSymbols originalDfs = DecimalFormatSymbols.getInstance(Locale.GERMANY);

        // Serialize...
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        new ObjectOutputStream(out).writeObject(originalDfs);
        byte[] bytes = out.toByteArray();

        // Deserialize...
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        DecimalFormatSymbols deserializedDfs = (DecimalFormatSymbols) in.readObject();
        assertEquals(-1, in.read());

        // The two objects should claim to be equal.
        assertEquals(originalDfs, deserializedDfs);
    }

    // https://code.google.com/p/android/issues/detail?id=79925
    public void testSetSameCurrency() throws Exception {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        dfs.setCurrency(Currency.getInstance("USD"));
        assertEquals("$", dfs.getCurrencySymbol());
        dfs.setCurrencySymbol("poop");
        assertEquals("poop", dfs.getCurrencySymbol());
        dfs.setCurrency(Currency.getInstance("USD"));
        assertEquals("$", dfs.getCurrencySymbol());
    }

    public void testSetNulInternationalCurrencySymbol() throws Exception {
        Currency usd = Currency.getInstance("USD");

        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        dfs.setCurrency(usd);
        assertEquals(usd, dfs.getCurrency());
        assertEquals("$", dfs.getCurrencySymbol());
        assertEquals("USD", dfs.getInternationalCurrencySymbol());

        // Setting the international currency symbol to null sets the currency to null too,
        // but not the currency symbol.
        dfs.setInternationalCurrencySymbol(null);
        assertEquals(null, dfs.getCurrency());
        assertEquals("$", dfs.getCurrencySymbol());
        assertEquals(null, dfs.getInternationalCurrencySymbol());
    }

    // https://code.google.com/p/android/issues/detail?id=170718
    public void testSerializationOfMultiCharNegativeAndPercentage() throws Exception {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.forLanguageTag("ar-AR"));
        // TODO(narayan): Investigate.
        // assertTrue(dfs.getMinusSignString().length() > 1);
        // assertTrue(dfs.getPercentString().length() > 1);

        // Serialize...
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        new ObjectOutputStream(out).writeObject(dfs);
        byte[] bytes = out.toByteArray();

        // Deserialize...
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        DecimalFormatSymbols deserializedDfs = (DecimalFormatSymbols) in.readObject();
        assertEquals(-1, in.read());

        assertEquals(dfs.getMinusSign(), deserializedDfs.getMinusSign());
        assertEquals(dfs.getPercent(), deserializedDfs.getPercent());
    }

    // http://b/18785260
    public void testMultiCharMinusSignAndPercentage() {
        DecimalFormatSymbols dfs;

        // There have during the years been numerous bugs and workarounds around the decimal format
        // symbols used for Arabic and Farsi. Most of the problems have had to do with bidi control
        // characters and the Unicode bidi algorithm, which have not worked well together with code
        // assuming that these symbols can be represented as a single Java char.
        //
        // This test case exists to verify that java.text.DecimalFormatSymbols in Android gets some
        // kind of sensible values for these symbols (and not, as bugs have caused in the past,
        // empty strings or only bidi control characters without any actual symbols).
        //
        // It is expected that the symbols may change with future CLDR updates.

        dfs = new DecimalFormatSymbols(Locale.forLanguageTag("ar"));
        assertEquals('٪', dfs.getPercent());
        assertEquals('-', dfs.getMinusSign());

        dfs = new DecimalFormatSymbols(Locale.forLanguageTag("fa"));
        assertEquals('٪', dfs.getPercent());
        assertEquals('−', dfs.getMinusSign());
    }


    /**
     * This class exists to allow the test to access the protected methods
     * getIcuDecimalFormatSymbols and fromIcuInstance on the real DecimalFormatSymbols class.
     */
    private static class DFSForTests extends DecimalFormatSymbols {
        public DFSForTests(Locale locale) {
            super(locale);
        }

        @Override
        public android.icu.text.DecimalFormatSymbols getIcuDecimalFormatSymbols() {
            return super.getIcuDecimalFormatSymbols();
        }

        protected static DecimalFormatSymbols fromIcuInstance(
                android.icu.text.DecimalFormatSymbols dfs) {
            return DecimalFormatSymbols.fromIcuInstance(dfs);
        }
    }

    public void compareDfs(DecimalFormatSymbols dfs,
                           android.icu.text.DecimalFormatSymbols icuSymb) {
        // Check currency code is the same because ICU returns its own currency class.
        assertEquals(dfs.getCurrency().getCurrencyCode(), icuSymb.getCurrency().getCurrencyCode());
        assertEquals(dfs.getCurrencySymbol(), icuSymb.getCurrencySymbol());
        assertEquals(dfs.getDecimalSeparator(), icuSymb.getDecimalSeparator());
        assertEquals(dfs.getDigit(), icuSymb.getDigit());
        assertEquals(dfs.getExponentSeparator(), icuSymb.getExponentSeparator());
        assertEquals(dfs.getGroupingSeparator(), icuSymb.getGroupingSeparator());
        assertEquals(dfs.getInfinity(), icuSymb.getInfinity());
        assertEquals(dfs.getInternationalCurrencySymbol(),
                icuSymb.getInternationalCurrencySymbol());
        assertEquals(dfs.getMinusSign(), icuSymb.getMinusSign());
        assertEquals(dfs.getMonetaryDecimalSeparator(), icuSymb.getMonetaryDecimalSeparator());
        assertEquals(dfs.getPatternSeparator(), icuSymb.getPatternSeparator());
        assertEquals(dfs.getPercent(), icuSymb.getPercent());
        assertEquals(dfs.getPerMill(), icuSymb.getPerMill());
        assertEquals(dfs.getZeroDigit(), icuSymb.getZeroDigit());
    }

    // Test the methods to convert to and from the ICU DecimalFormatSymbols
    public void testToIcuDecimalFormatSymbols() {
        DFSForTests dfs = new DFSForTests(Locale.US);
        android.icu.text.DecimalFormatSymbols icuSymb = dfs.getIcuDecimalFormatSymbols();
        compareDfs(dfs, icuSymb);
    }

    public void testFromIcuDecimalFormatSymbols() {
        android.icu.text.DecimalFormatSymbols icuSymb = new android.icu.text.DecimalFormatSymbols();
        DecimalFormatSymbols dfs = DFSForTests.fromIcuInstance(icuSymb);
        compareDfs(dfs, icuSymb);
    }

    // http://b/36562145
    public void testMaybeStripMarkers() {
        final char ltr = '\u200E';
        final char rtl = '\u200F';
        final char alm = '\u061C';
        final char fallback = 'F';
        assertEquals(fallback, DecimalFormatSymbols.maybeStripMarkers("", fallback));
        assertEquals(fallback, DecimalFormatSymbols.maybeStripMarkers("XY", fallback));
        assertEquals(fallback, DecimalFormatSymbols.maybeStripMarkers("" + ltr, fallback));
        assertEquals(fallback, DecimalFormatSymbols.maybeStripMarkers("" + rtl, fallback));
        assertEquals(fallback, DecimalFormatSymbols.maybeStripMarkers("" + alm, fallback));
        assertEquals(fallback,
                DecimalFormatSymbols.maybeStripMarkers("X" + ltr + rtl + alm + "Y", fallback));
        assertEquals(fallback,
                DecimalFormatSymbols.maybeStripMarkers("" + ltr + rtl + alm, fallback));
        assertEquals(fallback, DecimalFormatSymbols.maybeStripMarkers(alm + "XY" + rtl, fallback));
        assertEquals('X', DecimalFormatSymbols.maybeStripMarkers("X", fallback));
        assertEquals('X', DecimalFormatSymbols.maybeStripMarkers("X" + ltr, fallback));
        assertEquals('X', DecimalFormatSymbols.maybeStripMarkers("X" + rtl, fallback));
        assertEquals('X', DecimalFormatSymbols.maybeStripMarkers(alm + "X", fallback));
        assertEquals('X', DecimalFormatSymbols.maybeStripMarkers(alm + "X" + rtl, fallback));
    }
}
