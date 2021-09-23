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

// import com.android.dx.Local;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;

@RunWith(JUnit4.class)
public class ChronoPeriodTest {

    @Test
    public void between_withNull_throwsNpe() {
        try {
            ChronoPeriod.between(null, null);
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }

        try {
            ChronoPeriod.between(null, LocalDate.now());
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }

        try {
            ChronoPeriod.between(LocalDate.now(), null);
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }
    }

    @Test
    public void between_nonNull() {
        LocalDate now = LocalDate.of(2021, 5, 26);
        LocalDate tomorrow = LocalDate.of(2021, 5, 27);

        assertEquals(Period.ofDays(1), ChronoPeriod.between(now, tomorrow));
        assertEquals(HijrahChronology.INSTANCE.period(0, 0, 2),
                ChronoPeriod.between(HijrahDate.of(1442, 9, 10), HijrahDate.of(1442, 9, 12)));
    }

    @Test
    public void between_differentInterfaceImplementations() {
        LocalDate isoNow = LocalDate.of(2021, 5, 26);
        HijrahDate hijrahTomorrow = HijrahDate.of(1442, 10, 15);

        assertEquals(Period.ofDays(1), ChronoPeriod.between(isoNow, hijrahTomorrow));
        assertEquals(HijrahChronology.INSTANCE.period(0, 0, -1),
                ChronoPeriod.between(hijrahTomorrow, isoNow));
    }
}
