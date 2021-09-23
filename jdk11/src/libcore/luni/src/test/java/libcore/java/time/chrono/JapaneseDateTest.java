/*
 * Copyright (C) 2021 The Android Open Source Project
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

package libcore.java.time.chrono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static java.time.chrono.JapaneseEra.HEISEI;
import static java.time.chrono.JapaneseEra.REIWA;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.Period;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

@RunWith(JUnit4.class)
public class JapaneseDateTest {

    @Test
    public void minus_null_throwsNpe() {
        try {
            JapaneseDate.now().minus(null);
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }
    }

    @Test
    public void minus_days() {
        JapaneseDate date = JapaneseDate.of(REIWA, 3, 5, 21);

        assertEquals(JapaneseDate.of(REIWA, 3, 5, 20), date.minus(1, ChronoUnit.DAYS));
    }

    @Test
    public void minus_negative_days() {
        JapaneseDate date = JapaneseDate.of(REIWA, 3, 5, 21);

        assertEquals(JapaneseDate.of(REIWA, 3, 5, 22), date.minus(-1, ChronoUnit.DAYS));
    }

    @Test
    public void minus_days_crossEras() {
        JapaneseDate date = JapaneseDate.of(REIWA, 1, 5, 1);

        assertEquals(JapaneseDate.of(HEISEI, 31, 4, 30), date.minus(1, ChronoUnit.DAYS));
    }

    @Test
    public void minus_less_than_day_throwsUnsupportedTemporalTypeException() {
        try {
            JapaneseDate.now().minus(1, ChronoUnit.HOURS);
            fail();
        } catch (UnsupportedTemporalTypeException ignored) {
            // expected
        }

        try {
            JapaneseDate.now().minus(1, ChronoUnit.MINUTES);
            fail();
        } catch (UnsupportedTemporalTypeException ignored) {
            // expected
        }

        try {
            JapaneseDate.now().minus(1, ChronoUnit.SECONDS);
            fail();
        } catch (UnsupportedTemporalTypeException ignored) {
            // expected
        }
    }

    @Test
    public void minus_Duration_throws_UnsupportedTemporalTypeException() {
        try {
            JapaneseDate.now().minus(Duration.ofDays(1));
            fail();
        } catch (UnsupportedTemporalTypeException ignored) {
            // expected
        }
    }

    @Test
    public void minus_Period_throws_DateTimeException() {
        try {
            JapaneseDate.now().minus(Period.ofDays(1));
            fail();
        } catch (DateTimeException ignored) {
            // expected
        }
    }

    @Test
    public void minus_japanesePeriod() {
        JapaneseDate date = JapaneseDate.of(REIWA, 3, 5, 21);

        assertEquals(JapaneseDate.of(REIWA, 2, 4, 20),
                date.minus(JapaneseChronology.INSTANCE.period(1, 1, 1)));
    }

    @Test
    public void minus_japanesePeriod_withNegative() {
        JapaneseDate date = JapaneseDate.of(REIWA, 2, 5, 21);

        assertEquals(JapaneseDate.of(REIWA, 3, 8, 26),
                date.minus(JapaneseChronology.INSTANCE.period(-1, -3, -5)));
    }

}
