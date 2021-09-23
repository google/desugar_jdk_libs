/*
 * Copyright (C) 2012 The Android Open Source Project
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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.icu.text.DateTimePatternGenerator;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import libcore.icu.LocaleData;
import libcore.junit.util.SwitchTargetSdkVersionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LocaleDataTest {

  @Rule
  public TestRule switchTargetSdkVersionRule = SwitchTargetSdkVersionRule.getInstance();

  @Test
  public void test_en_US() throws Exception {
    LocaleData l = LocaleData.get(Locale.US);
    assertEquals("AM", l.amPm[0]);

    assertEquals("BC", l.eras[0]);

    assertEquals("January", l.longMonthNames[0]);
    assertEquals("Jan", l.shortMonthNames[0]);
    assertEquals("J", l.tinyMonthNames[0]);

    assertEquals("January", l.longStandAloneMonthNames[0]);
    assertEquals("Jan", l.shortStandAloneMonthNames[0]);
    assertEquals("J", l.tinyStandAloneMonthNames[0]);

    assertEquals("Sunday", l.longWeekdayNames[1]);
    assertEquals("Sun", l.shortWeekdayNames[1]);
    assertEquals("S", l.tinyWeekdayNames[1]);

    assertEquals("Sunday", l.longStandAloneWeekdayNames[1]);
    assertEquals("Sun", l.shortStandAloneWeekdayNames[1]);
    assertEquals("S", l.tinyStandAloneWeekdayNames[1]);

  }

  @Test
  public void test_cs_CZ() throws Exception {
    LocaleData l = LocaleData.get(new Locale("cs", "CZ"));

    assertEquals("ledna", l.longMonthNames[0]);
    assertEquals("led", l.shortMonthNames[0]);
    assertEquals("1", l.tinyMonthNames[0]);

    assertEquals("leden", l.longStandAloneMonthNames[0]);
    assertEquals("led", l.shortStandAloneMonthNames[0]);
    assertEquals("1", l.tinyStandAloneMonthNames[0]);
  }

  @Test
  public void test_ru_RU() throws Exception {
    LocaleData l = LocaleData.get(new Locale("ru", "RU"));

    assertEquals("воскресенье", l.longWeekdayNames[1]);
    assertEquals("вс", l.shortWeekdayNames[1]);
    assertEquals("вс", l.tinyWeekdayNames[1]);

    // Russian stand-alone weekday names have no initial capital since CLDR 28/ICU 56.
    assertEquals("воскресенье", l.longStandAloneWeekdayNames[1]);
    assertEquals("вс", l.shortStandAloneWeekdayNames[1]);
    assertEquals("В", l.tinyStandAloneWeekdayNames[1]);
  }

  // http://code.google.com/p/android/issues/detail?id=38844
  @Test
  public void testDecimalFormatSymbols_es() throws Exception {
    LocaleData es = LocaleData.get(new Locale("es"));
    assertEquals(',', es.decimalSeparator);
    assertEquals('.', es.groupingSeparator);

    LocaleData es_419 = LocaleData.get(new Locale("es", "419"));
    assertEquals('.', es_419.decimalSeparator);
    assertEquals(',', es_419.groupingSeparator);

    LocaleData es_US = LocaleData.get(new Locale("es", "US"));
    assertEquals('.', es_US.decimalSeparator);
    assertEquals(',', es_US.groupingSeparator);

    LocaleData es_MX = LocaleData.get(new Locale("es", "MX"));
    assertEquals('.', es_MX.decimalSeparator);
    assertEquals(',', es_MX.groupingSeparator);

    LocaleData es_AR = LocaleData.get(new Locale("es", "AR"));
    assertEquals(',', es_AR.decimalSeparator);
    assertEquals('.', es_AR.groupingSeparator);
  }

  // http://b/7924970
  @Test
  public void testTimeFormat12And24() {
    Boolean originalSetting = DateFormat.is24Hour;
    try {
      LocaleData en_US = LocaleData.get(Locale.US);
      DateFormat.is24Hour = false;
      assertEquals("h:mm a", en_US.getTimeFormat(DateFormat.SHORT));
      DateFormat.is24Hour = true;
      assertEquals("HH:mm", en_US.getTimeFormat(DateFormat.SHORT));

      LocaleData ja_JP = LocaleData.get(Locale.JAPAN);
      DateFormat.is24Hour = false;
      assertEquals("aK:mm", ja_JP.getTimeFormat(DateFormat.SHORT));
      DateFormat.is24Hour = true;
      assertEquals("H:mm", ja_JP.getTimeFormat(DateFormat.SHORT));
    } finally {
      DateFormat.is24Hour = originalSetting;
    }
  }

  // http://b/26397197
  @Test
  public void testPatternWithOverride() throws Exception {
    LocaleData haw = LocaleData.get(new Locale("haw"));
    assertFalse(haw.shortDateFormat.isEmpty());
  }

  /**
   * Check that LocaleData.get() does not throw when the input locale is invalid.
   * http://b/129070579
   */
  @Test
  public void testInvalidLocale() {
    LocaleData.get(new Locale("invalidLocale"));
  }

  // Test for b/159514442 when targetSdkVersion == current
  @Test
  public void test_rootLocale_icu4jConsistency() {
    assertRootDataEqualsToTargetLocaleData(Locale.ROOT);
  }

  // Test for b/159514442
  @Test
  @SwitchTargetSdkVersionRule.TargetSdkVersion(30)
  public void test_rootLocale_useRealRootLocaleData() {
    assertRootDataEqualsToTargetLocaleData(Locale.ROOT);

    // Regression test as in b/159514442.
    SimpleDateFormat df = new SimpleDateFormat("MMM", Locale.ROOT);
    df.setTimeZone(TimeZone.getTimeZone("GMT"));
    assertEquals("M07", df.format(new Date(1594255915217L)));
  }

  // Test for b/159514442
  @Test
  @SwitchTargetSdkVersionRule.TargetSdkVersion(29)
  public void test_rootLocale_notUseRealRootLocaleData() {
    Locale LOCALE_EN_US_POSIX = new Locale("en", "US", "POSIX");
    assertRootDataEqualsToTargetLocaleData(LOCALE_EN_US_POSIX);

    // Regression test as in b/159514442.
    SimpleDateFormat df = new SimpleDateFormat("MMM", Locale.ROOT);
    df.setTimeZone(TimeZone.getTimeZone("GMT"));
    assertEquals("Jul", df.format(new Date(1594255915217L)));
  }

  private static void assertRootDataEqualsToTargetLocaleData(Locale targetLocale) {
    LocaleData localeData = LocaleData.get(Locale.ROOT);
    Calendar calendar = Calendar.getInstance(Locale.ROOT);
    android.icu.util.Calendar icuCalendar = android.icu.util.Calendar.getInstance(targetLocale);
    DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(Locale.ROOT);
    android.icu.text.DateFormatSymbols icuDateFormatSymbols =
        android.icu.text.DateFormatSymbols.getInstance(targetLocale);
    DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.ROOT);
    android.icu.text.DecimalFormatSymbols icuDecimalFormatSymbols =
        android.icu.text.DecimalFormatSymbols.getInstance(targetLocale);
    DateTimePatternGenerator dtpg = DateTimePatternGenerator.getInstance(Locale.ROOT);

    assertEquals(localeData.firstDayOfWeek, (Integer) icuCalendar.getFirstDayOfWeek());
    assertEquals(localeData.minimalDaysInFirstWeek,
        (Integer) icuCalendar.getMinimalDaysInFirstWeek());

    assertArrayEquals(localeData.amPm, icuDateFormatSymbols.getAmPmStrings());
    assertArrayEquals(localeData.eras, icuDateFormatSymbols.getEras());
    assertArrayEquals(localeData.longMonthNames, icuDateFormatSymbols.getMonths(
        android.icu.text.DateFormatSymbols.FORMAT, android.icu.text.DateFormatSymbols.WIDE));
    assertArrayEquals(localeData.tinyMonthNames, icuDateFormatSymbols.getMonths(
        android.icu.text.DateFormatSymbols.FORMAT, android.icu.text.DateFormatSymbols.NARROW));
    assertArrayEquals(localeData.shortMonthNames, icuDateFormatSymbols.getMonths(
        android.icu.text.DateFormatSymbols.FORMAT, android.icu.text.DateFormatSymbols.ABBREVIATED));
    assertArrayEquals(localeData.longStandAloneMonthNames, icuDateFormatSymbols.getMonths(
        android.icu.text.DateFormatSymbols.STANDALONE, android.icu.text.DateFormatSymbols.WIDE));
    assertArrayEquals(localeData.tinyStandAloneMonthNames, icuDateFormatSymbols.getMonths(
        android.icu.text.DateFormatSymbols.STANDALONE, android.icu.text.DateFormatSymbols.NARROW));
    assertArrayEquals(localeData.shortStandAloneMonthNames, icuDateFormatSymbols.getMonths(
        android.icu.text.DateFormatSymbols.STANDALONE,
        android.icu.text.DateFormatSymbols.ABBREVIATED));
    assertArrayEquals(localeData.longWeekdayNames, icuDateFormatSymbols.getWeekdays(
        android.icu.text.DateFormatSymbols.FORMAT, android.icu.text.DateFormatSymbols.WIDE));
    assertArrayEquals(localeData.tinyWeekdayNames, icuDateFormatSymbols.getWeekdays(
        android.icu.text.DateFormatSymbols.FORMAT, android.icu.text.DateFormatSymbols.NARROW));
    assertArrayEquals(localeData.shortWeekdayNames, icuDateFormatSymbols.getWeekdays(
        android.icu.text.DateFormatSymbols.FORMAT, android.icu.text.DateFormatSymbols.ABBREVIATED));
    assertArrayEquals(localeData.longStandAloneWeekdayNames, icuDateFormatSymbols.getWeekdays(
        android.icu.text.DateFormatSymbols.STANDALONE, android.icu.text.DateFormatSymbols.WIDE));
    assertArrayEquals(localeData.tinyStandAloneWeekdayNames, icuDateFormatSymbols.getWeekdays(
        android.icu.text.DateFormatSymbols.STANDALONE, android.icu.text.DateFormatSymbols.NARROW));
    assertArrayEquals(localeData.shortStandAloneWeekdayNames, icuDateFormatSymbols.getWeekdays(
        android.icu.text.DateFormatSymbols.STANDALONE,
        android.icu.text.DateFormatSymbols.ABBREVIATED));

    // ICU DecimalFormatSymbols has data slightly different from LocaleData, but infinity is known
    // to be the same, but caused the bug b/68318492 in old Android version.
    assertEquals(localeData.infinity, icuDecimalFormatSymbols.getInfinity());
    assertEquals(decimalFormatSymbols.getInfinity(), icuDecimalFormatSymbols.getInfinity());

    // Explicitly test Calendar and DateFormatSymbols here because they are known to
    // cache some part of LocaleData.
    assertEquals(calendar.getFirstDayOfWeek(), icuCalendar.getFirstDayOfWeek());
    assertEquals(calendar.getMinimalDaysInFirstWeek(), icuCalendar.getMinimalDaysInFirstWeek());
    assertArrayEquals(dateFormatSymbols.getAmPmStrings(), icuDateFormatSymbols.getAmPmStrings());
    assertArrayEquals(dateFormatSymbols.getEras(), icuDateFormatSymbols.getEras());

    assertArrayEquals(dateFormatSymbols.getMonths(), icuDateFormatSymbols.getMonths(
        android.icu.text.DateFormatSymbols.FORMAT, android.icu.text.DateFormatSymbols.WIDE));
    assertArrayEquals(dateFormatSymbols.getShortMonths(), icuDateFormatSymbols.getMonths(
        android.icu.text.DateFormatSymbols.FORMAT, android.icu.text.DateFormatSymbols.ABBREVIATED));
    assertArrayEquals(dateFormatSymbols.getWeekdays(), icuDateFormatSymbols.getWeekdays(
        android.icu.text.DateFormatSymbols.FORMAT, android.icu.text.DateFormatSymbols.WIDE));
    assertArrayEquals(dateFormatSymbols.getShortWeekdays(), icuDateFormatSymbols.getWeekdays(
        android.icu.text.DateFormatSymbols.FORMAT, android.icu.text.DateFormatSymbols.ABBREVIATED));
  }
}
