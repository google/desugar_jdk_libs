/*
 * Copyright (C) 2017 The Android Open Source Project
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
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.chrono.HijrahEra;
import java.time.temporal.ChronoField;

/**
 * Additional tests for {@link HijrahDate}.
 *
 * @see tck.java.time.chrono.TCKHijrahChronology
 */
@RunWith(JUnit4.class)
public class HijrahChronologyTest {
    @Test
    public void test_HijrahDate_getEra() {
        // HijrahChronology has only one valid era.
        assertEquals(HijrahEra.AH, HijrahDate.of(1300, 1, 1).getEra());
    }

    @Test
    public void test_HijrahDate_getLong() {
        // 1300 is the first year in the HijrahChronology in the umalqura configuration.
        HijrahDate date = HijrahDate.of(1300, 2, 5);
        assertEquals(1300, date.getLong(ChronoField.YEAR_OF_ERA));
        assertEquals(1300, date.getLong(ChronoField.YEAR));
        assertEquals(2, date.getLong(ChronoField.MONTH_OF_YEAR));
        // Proleptic month starts with 0 for the first month of the proleptic year 0.
        assertEquals(1300 * 12 + 2 - 1, date.getLong(ChronoField.PROLEPTIC_MONTH));
        assertEquals(5, date.getLong(ChronoField.DAY_OF_MONTH));
        // first month of the year 1300 has 30 days.
        assertEquals(30 + 5, date.getLong(ChronoField.DAY_OF_YEAR));
        assertEquals(date.toEpochDay(), date.getLong(ChronoField.EPOCH_DAY));
    }

    @Test
    public void test_HijrahDate_withVariant_same() {
        // There is currently no way of creating an alternative HijrahChronology, so only this
        // case and the null case are tested.
        HijrahDate date1 = HijrahDate.now();
        HijrahDate date2 = date1.withVariant(HijrahChronology.INSTANCE);
        assertSame(date1, date2);
    }

    @Test(expected = NullPointerException.class)
    public void test_HijrahDate_withVariant_null() {
        HijrahDate.now().withVariant(null);
    }

    @Test
    public void dateEpochDay() {
        // 18766th Epoch Day is 19 May 2021
        assertEquals(HijrahDate.of(1442, 10, 7), HijrahChronology.INSTANCE.dateEpochDay(18766));
        assertEquals(HijrahDate.of(1389, 10, 22), HijrahChronology.INSTANCE.dateEpochDay(0));
    }

}
