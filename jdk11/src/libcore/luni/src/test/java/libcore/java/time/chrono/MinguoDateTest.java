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
import java.time.chrono.MinguoChronology;
import java.time.chrono.MinguoDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

@RunWith(JUnit4.class)
public class MinguoDateTest {

    @Test
    public void minus_null_throwsNpe() {
        try {
            MinguoDate.now().minus(null);
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }
    }

    @Test
    public void minus_days() {
        MinguoDate date = MinguoDate.of(110, 1, 1);

        assertEquals(MinguoDate.of(109, 12, 31), date.minus(1, ChronoUnit.DAYS));
    }

    @Test
    public void minus_Duration_throws_UnsupportedTemporalTypeException() {
        try {
            MinguoDate.now().minus(Duration.ofDays(1));
            fail();
        } catch (UnsupportedTemporalTypeException ignored) {
            // expected
        }
    }

    @Test
    public void minus_Period_throws_DateTimeException() {
       try {
           MinguoDate.now().minus(Period.ofDays(1));
           fail();
       } catch (DateTimeException ignored) {
           // expected
       }
    }

    @Test
    public void minus_minguoPeriod() {
        MinguoDate date = MinguoDate.of(110, 1, 1);

        assertEquals(MinguoDate.of(108, 11, 30),
                date.minus(MinguoChronology.INSTANCE.period(1, 1, 1)));
    }

    @Test
    public void minus_minguoPeriod_withNegative() {
        MinguoDate date = MinguoDate.of(110, 2, 28);

        assertEquals(MinguoDate.of(109, 3, 29),
                date.minus(MinguoChronology.INSTANCE.period(1, -1, -1)));

        assertEquals(MinguoDate.of(110, 3, 29),
                date.minus(MinguoChronology.INSTANCE.period(0, -1, -1)));
    }
}
