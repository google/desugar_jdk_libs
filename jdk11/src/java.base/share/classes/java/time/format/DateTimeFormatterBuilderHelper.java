/*
 * Copyright (c) 2020 Google LLC
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
 */

package java.time.format;

/**
 * For desugar: This class customizes the behavior of {@link DateTimeFormatterBuilder} on Android.
 * This class does not exist in the upstream OpenJDK yet.
 */
final class DateTimeFormatterBuilderHelper {
    private DateTimeFormatterBuilderHelper() {}

    /**
     * {@link java.time.format.DateTimeFormatter} does not handle some date symbols, e.g. 'B' / 'b',
     * and thus we use a heuristic algorithm to remove the symbol.
     *
     * <p>Rewrite the date/time pattern coming Android's {@link
     * java.text.SimpleDateFormat#toPattern()} to be consumed by java.time classes.
     *
     * <p>It's an ideal place to rewrite the pattern entirely when multiple symbols not digested by
     * java.time need to be removed/processed. Rewriting in single place could be more efficient in
     * a small or constant number of scans instead of scanning for every symbol.
     */
     static String transformAndroidJavaTextDateTimePattern(String pattern) {
        if (pattern == null) {
          return null;
        }

        // On Android, the patterns provided by SimpleDateFormat comes from ICU/CLDR, and some
        // date/time symbols is supported by ICU/CLDR, but not java.time.
        //
        // For details about the different symbols, see
        // http://cldr.unicode.org/translation/date-time-1/date-time-patterns#TOC-Day-period-patterns
        // The symbols B means "Day periods with locale-specific ranges".
        // English example: 2:00 at night, 10:00 in the morning, 12:00 in the afternoon.
        boolean containsCharB = pattern.indexOf('B') != -1;
        // AM, PM, noon and midnight. English example: 10:00 AM, 12:00 noon, 7:00 PM
        boolean containsCharb = pattern.indexOf('b') != -1;

        // Remove all 'B' and 'b' symbols because java.text.SimpleDateFormat.toPattern returns
        // unsupported symbol 'b' and 'B' accidentally on old Android releases.
        // The only patterns causing the bug uses 'B' with 24-hour format 'H', and the example is
        // "B H:mm". Dropping the 'B' and the pattern becomes "H:mm" and it should still be
        // understandable.
        if (containsCharB || containsCharb) {
             pattern = rewriteIcuDateTimePattern(pattern);
        }
        return pattern;
    }

    /**
     * Rewrite pattern with heuristics. It's known to
     * - Remove 'b' and 'B' from simple patterns, e.g. "B H:mm" and "dd-MM-yy B HH:mm:ss" only.
     * - (Append the new heuristics)
     */
    private static String rewriteIcuDateTimePattern(String pattern) {
        // The below implementation can likely be replaced by a regular expression via
        // String.replaceAll(). However, it's known that regex implementation is more
        // memory-intensive, and the below implementation is likely cheaper, but it's not yet
        // measured.
        StringBuilder sb = new StringBuilder(pattern.length());
        char prev = ' '; // the initial value is not used.
        for (int i = 0; i < pattern.length(); i++) {
            char curr = pattern.charAt(i);
            switch (curr) {
                case 'B':
                case 'b':
                    // Ignore 'B' and 'b'
                    break;
                case ' ': // Ascii whitespace
                    // caveat: Ideally it's a case for all Unicode whitespaces by
                    // UCharacter.isUWhiteSpace(c) but checking ascii whitespace only is enough for
                    // the CLDR data when this is written.
                    if (i != 0 && (prev == 'B' || prev == 'b')) {
                        // Ignore the whitespace behind the symbol 'B'/'b' because it's likely a
                        // whitespace to separate the day period with the next text.
                    } else {
                        sb.append(curr);
                    }
                    break;
                default:
                    sb.append(curr);
                    break;
            }
            prev = curr;
        }

        // Remove the trailing whitespace which is likely following the symbol 'B'/'b' in the
        // original pattern, e.g. "hh:mm B" (12:00 in the afternoon).
        int lastIndex = sb.length() - 1;
        if (lastIndex >= 0 && sb.charAt(lastIndex) == ' ') {
            sb.deleteCharAt(lastIndex);
        }
        return sb.toString();
    }
}
