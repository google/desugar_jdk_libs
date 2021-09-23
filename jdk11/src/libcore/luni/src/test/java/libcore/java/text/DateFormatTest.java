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

package libcore.java.text;

import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatTest extends TestCase {

    // Regression test for http://b/31762542. If this test fails it implies that changes to
    // DateFormat.is24Hour will not be effective.
    public void testIs24Hour_notCached() throws Exception {
        Boolean originalIs24Hour = DateFormat.is24Hour;
        try {
            // These tests hardcode expectations for Locale.US.
            DateFormat.is24Hour = null; // null == locale default (12 hour for US)
            checkTimePattern(DateFormat.SHORT, "h:mm a");
            checkTimePattern(DateFormat.MEDIUM, "h:mm:ss a");

            DateFormat.is24Hour = true; // Explicit 24 hour.
            checkTimePattern(DateFormat.SHORT, "HH:mm");
            checkTimePattern(DateFormat.MEDIUM, "HH:mm:ss");

            DateFormat.is24Hour = false; // Explicit 12 hour.
            checkTimePattern(DateFormat.SHORT, "h:mm a");
            checkTimePattern(DateFormat.MEDIUM, "h:mm:ss a");
        } finally {
            DateFormat.is24Hour = originalIs24Hour;
        }
    }

    private static void checkTimePattern(int style, String expectedPattern) {
        final Locale locale = Locale.US;
        final Date current = new Date(1468250177000L); // 20160711 15:16:17 GMT
        DateFormat format = DateFormat.getTimeInstance(style, locale);
        String actualDateString = format.format(current);
        SimpleDateFormat sdf = new SimpleDateFormat(expectedPattern, locale);
        String expectedDateString = sdf.format(current);
        assertEquals(expectedDateString, actualDateString);
    }
}
