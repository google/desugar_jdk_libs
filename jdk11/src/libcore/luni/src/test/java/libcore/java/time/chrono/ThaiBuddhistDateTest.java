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
import java.time.Period;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono.ThaiBuddhistDate;

@RunWith(JUnit4.class)
public class ThaiBuddhistDateTest {

    @Test
    public void minus_thaiBuddhistChronologyPeriod() {
        ThaiBuddhistDate date = ThaiBuddhistDate.of(2484, 1, 1);

        ChronoPeriod period = ThaiBuddhistChronology.INSTANCE.period(1, 1, 1);

        assertEquals(ThaiBuddhistDate.of(2482, 11, 30), date.minus(period));
    }

    @Test
    public void minus_thaiBuddhistChronologyPeriod_withNegative() {
        ThaiBuddhistDate date = ThaiBuddhistDate.of(2484, 1, 1);

        ChronoPeriod period = ThaiBuddhistChronology.INSTANCE.period(1, -1, -1);

        assertEquals(ThaiBuddhistDate.of(2483, 2, 2), date.minus(period));
    }

    @Test
    public void minus_javaTimePeriod_shouldThrowDateTimeException() {
        ThaiBuddhistDate date = ThaiBuddhistDate.of(2484, 1, 1);

        try {
            date.minus(Period.ofDays(1));
            fail();
        } catch (DateTimeException ignored) {
            // expected
        }
    }

    @Test
    public void minus_javaTimeDuration_shouldThrowDateTimeException() {
        ThaiBuddhistDate date = ThaiBuddhistDate.of(2484, 1, 1);

        try {
            date.minus(Duration.ofDays(1));
            fail();
        } catch (DateTimeException ignored) {
            // excepted
        }
    }

    @Test
    public void minusNull_shouldThrowNpe() {
        try {
            ThaiBuddhistDate.now().minus(null);
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }
    }
}
