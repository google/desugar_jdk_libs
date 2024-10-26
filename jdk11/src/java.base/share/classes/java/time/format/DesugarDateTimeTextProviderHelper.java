/*
 * Copyright (c) 2023 Google LLC
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Google designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Google in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package java.time.format;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/** Utilities for {@link DateTimeTextProvider}. */
public class DesugarDateTimeTextProviderHelper {

  public static void fillWithStandaloneMonthStyleMap(
      Map<TextStyle, Map<Long, String>> styleMapMap,
      DateFormatSymbols dateFormatSymbols,
      Locale loc) {

    int numMonth = dateFormatSymbols.getMonths().length;

    Map<Long, String> longStandAloneMap = new LinkedHashMap<>();
    Map<Long, String> narrowStandAloneMap = new LinkedHashMap<>();
    Map<Long, String> shortStandAloneMap = new LinkedHashMap<>();

    String longMonth = "LLLL";
    String shortMonth = "LLL";

    for (long i = 1; i <= numMonth; i++) {
      String longName = computeStandaloneMonthName(i, longMonth, loc);
      longStandAloneMap.put(i, longName);
      narrowStandAloneMap.put(i, firstCodePoint(longName));
      String shortName = computeStandaloneMonthName(i, shortMonth, loc);
      shortStandAloneMap.put(i, shortName);
    }

    if (numMonth > 0) {
      styleMapMap.put(TextStyle.FULL_STANDALONE, longStandAloneMap);
      styleMapMap.put(TextStyle.NARROW_STANDALONE, narrowStandAloneMap);
      styleMapMap.put(TextStyle.SHORT_STANDALONE, shortStandAloneMap);
    }
  }

  private static String computeStandaloneMonthName(long id, String standalonePattern, Locale loc) {
    TimeZone legacyUtc = TimeZone.getTimeZone("UTC");
    SimpleDateFormat writer = new SimpleDateFormat(standalonePattern, loc);
    writer.setTimeZone(legacyUtc);
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeZone(legacyUtc);
    calendar.set(0, (int) id, 0, 0, 0, 0);
    Date legacy = calendar.getTime();
    return writer.format(legacy);
  }

  private static String firstCodePoint(String string) {
    return string.substring(0, Character.charCount(string.codePointAt(0)));
  }

  private static String lastCodePoint(String rawText) {
    int n = rawText.length();
    int codePoint = rawText.codePointBefore(n);
    return new StringBuilder().appendCodePoint(codePoint).toString();
  }

  public static void fillWithStandaloneDayOfWeekStyleMap(
      Map<TextStyle, Map<Long, String>> styleMapMap,
      DateFormatSymbols dateFormatSymbols,
      Locale loc) {

    int numDaysOfWeek = dateFormatSymbols.getWeekdays().length;

    Map<Long, String> longStandAloneMap = new LinkedHashMap<>();
    Map<Long, String> narrowStandAloneMap = new LinkedHashMap<>();
    Map<Long, String> shortStandAloneMap = new LinkedHashMap<>();

    String longDay = "cccc";
    String shortDay = "ccc";

    boolean useLastCodePointAsNarrowName =
        loc == Locale.SIMPLIFIED_CHINESE || loc == Locale.TRADITIONAL_CHINESE;

    for (long i = 1; i <= numDaysOfWeek; i++) {
      String longName = computeStandaloneDayOfWeekName(i, longDay, loc);
      longStandAloneMap.put(i, longName);
      narrowStandAloneMap.put(
          i, useLastCodePointAsNarrowName ? lastCodePoint(longName) : firstCodePoint(longName));
      String shortName = computeStandaloneDayOfWeekName(i, shortDay, loc);
      shortStandAloneMap.put(i, shortName);
    }

    if (numDaysOfWeek > 0) {
      styleMapMap.put(TextStyle.FULL_STANDALONE, longStandAloneMap);
      styleMapMap.put(TextStyle.NARROW_STANDALONE, narrowStandAloneMap);
      styleMapMap.put(TextStyle.SHORT_STANDALONE, shortStandAloneMap);
    }
  }

  private static String computeStandaloneDayOfWeekName(
      long id, String standalonePattern, Locale loc) {
    TimeZone legacyUtc = TimeZone.getTimeZone("UTC");
    SimpleDateFormat writer = new SimpleDateFormat(standalonePattern, loc);
    writer.setTimeZone(legacyUtc);
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeZone(legacyUtc);
    // Jan 1st 2016 is a Monday.
    calendar.set(2016, 1, (int) id, 0, 0, 0);
    Date legacy = calendar.getTime();
    return writer.format(legacy);
  }

  private DesugarDateTimeTextProviderHelper() {}
}
