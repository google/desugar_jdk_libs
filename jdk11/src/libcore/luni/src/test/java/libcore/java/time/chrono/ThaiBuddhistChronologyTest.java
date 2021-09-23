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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono.ThaiBuddhistEra;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Additional tests for {@link ThaiBuddhistDate}.
 *
 * @see tck.java.time.chrono.TCKThaiBuddhistChronology
 */
@RunWith(JUnit4.class)
public class ThaiBuddhistChronologyTest {

    // year 2543 in Thai Buddhist calendar is 2000 in ISO calendar.
    private static final int YEARS_AHEAD = 543;

    @Test
    public void test_range() {
        for (ChronoField field : ChronoField.values()) {
            ValueRange expected;
            switch (field) {
                case PROLEPTIC_MONTH:
                    // Proleptic month values are shifted by YEARS_AHEAD * 12.
                    expected = ValueRange.of(
                            ChronoField.PROLEPTIC_MONTH.range().getMinimum() + YEARS_AHEAD * 12L,
                            ChronoField.PROLEPTIC_MONTH.range().getMaximum() + YEARS_AHEAD * 12L);
                    break;
                case YEAR_OF_ERA:
                    // range for era BE is 1..<yearRange.max - OFFSET>
                    // range for era before BE is 1..<-yearRange.min + 1 + OFFSET>
                    expected = ValueRange
                            .of(1, -ChronoField.YEAR.range().getMinimum() + 1 - YEARS_AHEAD,
                                    ChronoField.YEAR.range().getMaximum() + YEARS_AHEAD);
                    break;
                case YEAR:
                    // Proleptic year values are shifted by YEAR.
                    expected = ValueRange.of(ChronoField.YEAR.range().getMinimum() + YEARS_AHEAD,
                            ChronoField.YEAR.range().getMaximum() + YEARS_AHEAD);
                    break;
                default:
                    // All other fields have the same ranges as ISO.
                    expected = field.range();
                    break;
            }
            assertEquals("Range of " + field, expected,
                    ThaiBuddhistChronology.INSTANCE.range(field));
        }
    }

    @Test
    public void test_ThaiBuddhistDate_getChronology() {
        assertSame(ThaiBuddhistChronology.INSTANCE, ThaiBuddhistDate.now().getChronology());
    }

    @Test
    public void test_ThaiBuddhistDate_getEra() {
        assertEquals(ThaiBuddhistEra.BEFORE_BE, ThaiBuddhistDate.of(-1, 1, 1).getEra());
        assertEquals(ThaiBuddhistEra.BE, ThaiBuddhistDate.of(1, 1, 1).getEra());
    }

    @Test
    public void test_ThaiBuddhistDate_range() {
        ThaiBuddhistDate dates[] = new ThaiBuddhistDate[] {
                ThaiBuddhistDate.from(LocalDate.of(2000, 2, 1)), //February of a leap year
                ThaiBuddhistDate.from(LocalDate.of(2001, 2, 1)), //February of a non-leap year
                ThaiBuddhistDate.of(1, 2, 3),
                ThaiBuddhistDate.of(4, 5, 6),
                ThaiBuddhistDate.of(-7, 8, 9)
        };

        for (ThaiBuddhistDate date : dates) {
            // only these three ChronoFields and YEAR_OF_ERA (below) have date-dependent ranges.
            assertEquals(LocalDate.from(date).range(ChronoField.DAY_OF_MONTH),
                    date.range(ChronoField.DAY_OF_MONTH));
            assertEquals(LocalDate.from(date).range(ChronoField.DAY_OF_YEAR),
                    date.range(ChronoField.DAY_OF_YEAR));
            assertEquals(LocalDate.from(date).range(ChronoField.ALIGNED_WEEK_OF_MONTH),
                    date.range(ChronoField.ALIGNED_WEEK_OF_MONTH));
        }
    }

    @Test
    public void test_ThaiBuddhistDate_range_yeaOfEra() {
        // YEAR_OF_ERA is the big difference to a LocalDate, all other ranges are the same.
        assertEquals(ValueRange.of(1, ChronoField.YEAR.range().getMaximum() + YEARS_AHEAD),
                ThaiBuddhistDate.of(1, 1, 1).range(ChronoField.YEAR_OF_ERA));
        assertEquals(ValueRange.of(1, -ChronoField.YEAR.range().getMinimum() + 1 - YEARS_AHEAD),
                ThaiBuddhistDate.of(-1, 1, 1).range(ChronoField.YEAR_OF_ERA));
    }

    @Test
    public void test_ThaiBuddhistDate_getLong() {
        ThaiBuddhistDate date = ThaiBuddhistDate.of(10, 2, 5);
        assertEquals(10, date.getLong(ChronoField.YEAR_OF_ERA));
        assertEquals(10, date.getLong(ChronoField.YEAR));
        assertEquals(2, date.getLong(ChronoField.MONTH_OF_YEAR));
        assertEquals(10 * 12 + 2 - 1, date.getLong(ChronoField.PROLEPTIC_MONTH));
        assertEquals(5, date.getLong(ChronoField.DAY_OF_MONTH));
        assertEquals(31 + 5, date.getLong(ChronoField.DAY_OF_YEAR));
        assertEquals(date.toEpochDay(), date.getLong(ChronoField.EPOCH_DAY));
    }
}
