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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

@RunWith(JUnit4.class)
public class HijrahDateTest {

    @Test
    public void minus_null_throws_NPE() {
        try {
            HijrahDate.now().minus(/* amount= */ null);
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }
    }

    @Test
    public void minus_Duration_throws_DateTimeException() {
        try {
            HijrahDate.now().minus(Duration.ofDays(1));
          fail();
        } catch (DateTimeException ignored) {
            // expected
        }
    }

    @Test
    public void minus_Period_throws_DateTimeException() {
        try {
            HijrahDate.now().minus(Period.ofDays(1));
            fail();
        } catch (DateTimeException ignored) {
            // expected
        }
    }

    @Test
    public void minus_hijrah_period() {
        // 26 May 2021
        HijrahDate now = HijrahDate.of(1442, 10, 14);

        assertEquals(HijrahDate.of(1442, 10, 13),
                now.minus(HijrahChronology.INSTANCE.period(0, 0, 1)));
        assertEquals(HijrahDate.of(1441, 10, 14),
                now.minus(HijrahChronology.INSTANCE.period(1, 0, 0)));
        assertEquals(HijrahDate.of(1443, 11, 15),
                now.minus(HijrahChronology.INSTANCE.period(-1, -1, -1)));
        assertEquals(HijrahDate.of(1442, 9, 30),
                now.minus(HijrahChronology.INSTANCE.period(0, 0, 14)));
        assertEquals(HijrahDate.of(1442, 11, 1),
                now.minus(HijrahChronology.INSTANCE.period(0, 0, -16)));
    }

    @Test
    public void until_isoDates() {
        // 26 May 2021
        HijrahDate hijrahNow = HijrahDate.of(1442, 10, 14);
        LocalDate isoNow = LocalDate.of(2021, 5, 26);

        assertEquals(0, hijrahNow.until(isoNow, ChronoUnit.DAYS));
        assertEquals(0, hijrahNow.until(isoNow.atTime(10, 10), ChronoUnit.DAYS));
    }

    @Test
    public void until_hijrahDates() {
        // 26 May 2021
        HijrahDate hijrahNow = HijrahDate.of(1442, 10, 14);
        HijrahDate hijrahTomorrow = HijrahDate.of(1442, 10, 15);

        assertEquals(-1, hijrahTomorrow.until(hijrahNow, ChronoUnit.DAYS));
        assertEquals(1, hijrahNow.until(HijrahDate.of(1442, 11, 14), ChronoUnit.MONTHS));
    }

    @Test
    public void until_isoDates_unsupportedTypes() {
        // 26 May 2021
        HijrahDate hijrahNow = HijrahDate.of(1442, 10, 14);
        LocalDate isoNow = LocalDate.of(2021, 5, 26);

        try {
            hijrahNow.until(isoNow, ChronoUnit.HOURS);
            fail();
        } catch (UnsupportedTemporalTypeException ignored) {
            // expected
        }

        try {
            hijrahNow.until(Year.of(2021), ChronoUnit.YEARS);
            fail();
        } catch (UnsupportedTemporalTypeException ignored) {
            // expected
        }

        try {
            hijrahNow.until(Instant.ofEpochSecond(1622113481L), ChronoUnit.DAYS);
            fail();
        } catch (UnsupportedTemporalTypeException ignored) {
            // expected
        }
    }
}
