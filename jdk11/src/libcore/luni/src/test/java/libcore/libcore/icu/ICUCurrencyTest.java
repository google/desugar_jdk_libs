/*
 * Copyright (C) 2019 The Android Open Source Project
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

package libcore.libcore.icu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Currency;
import java.util.Locale;
import org.junit.Test;

public class ICUCurrencyTest {

  /**
   * Regression test that ensures CLDR root data has U+00A4 ¤ as the
   * symbol for unknown currency XXX.
   * http://b/113149899
   */
  @Test
  public void test_fallbackCurrencySymbolForUnknownLocale() {
    final String unknownCurrencySymbol = "\u00a4"; // "¤"

    Currency c = Currency.getInstance("XXX");
    assertNotNull(c);
    assertEquals(unknownCurrencySymbol, c.getSymbol(Locale.ROOT));

    android.icu.util.Currency ac = android.icu.util.Currency.getInstance("XXX");
    assertNotNull(ac);
    assertEquals(unknownCurrencySymbol, ac.getSymbol(android.icu.util.ULocale.ROOT));
  }

}
