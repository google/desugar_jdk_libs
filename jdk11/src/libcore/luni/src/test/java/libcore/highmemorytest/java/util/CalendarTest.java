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

package libcore.highmemorytest.java.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Runs in a separate java package because loading data in all locales creates a lot of garbage /
 * permanent heap growth in ICU4J and causes gcstress test failing in ART test environment.
 * See http://b/161420453.
 */
@RunWith(Parameterized.class)
public class CalendarTest {

    @Parameterized.Parameters(name = "{0}")
    public static Locale[] getAllLocales() {
        return Locale.getAvailableLocales();
    }

    @Parameterized.Parameter(0)
    public Locale locale;

    /**
     * Ensures that Gregorian is the default Calendar for all Locales in Android. This is the
     * historic behavior on Android; this test exists to avoid unintentional regressions.
     * http://b/80294184
     */
    @Test
    public void testAllDefaultCalendar_Gregorian() {
        assertTrue("Default calendar should be Gregorian: " + locale,
                Calendar.getInstance(locale) instanceof GregorianCalendar);
    }

}
