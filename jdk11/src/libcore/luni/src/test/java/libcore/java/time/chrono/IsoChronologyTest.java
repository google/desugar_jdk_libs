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
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;
import java.time.chrono.IsoEra;
import java.time.temporal.ChronoField;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Additional tests for {@link IsoChronology}.
 *
 * @see tck.java.time.chrono.TCKIsoChronology
 */
@RunWith(JUnit4.class)
public class IsoChronologyTest {

    @Test
    public void test_dateYear() {
        int[][] allValues = new int[][] {
                // proleptic Year, dayOfYear, expectedYear, expectedMonth, expectedDayOfMonth
                { 2017, 1, 2017, 1, 1 },
                { 1, 365, 1, 12, 31 },
                { 0, 32, 1, 2, 1 },
                { -100, 1, 101, 1, 1 },
                { 2000, 61, 2000, 3, 1 },
                { 2000, 366, 2000, 12, 31 },
                { Year.MAX_VALUE, 365, Year.MAX_VALUE, 12, 31 },
                { Year.MIN_VALUE, 365, -Year.MIN_VALUE + 1, 12, 31 },
        };

        for (int[] values : allValues) {
            LocalDate localDate = IsoChronology.INSTANCE.dateYearDay(values[0], values[1]);
            IsoEra expectedEra = values[0] <= 0 ? IsoEra.BCE : IsoEra.CE;
            assertEquals(expectedEra, localDate.getEra());
            assertEquals(values[0], localDate.getYear());
            assertEquals(values[1], localDate.getDayOfYear());
            assertEquals(values[2], localDate.get(ChronoField.YEAR_OF_ERA));
            assertEquals(values[3], localDate.getMonthValue());
            assertEquals(values[4], localDate.getDayOfMonth());
        }
    }

    @Test
    public void test_dateYear_invalidValues() {
        int[][] invalidValues = new int[][] {
                { Year.MAX_VALUE + 1, 1 },
                { Year.MIN_VALUE - 1, 1 },
                { Integer.MAX_VALUE, 1 },
                { Integer.MIN_VALUE, 1 },
                { 2001, 366 },
                { 2000, 367 },
                { 2017, 0 },
                { 2017, -1 },
        };

        for (int[] values : invalidValues) {
            try {
                LocalDate localDate = IsoChronology.INSTANCE.dateYearDay(values[0], values[1]);
                fail(values[0] + "/" + values[1] + " should have failed, but produced "
                        + localDate);
            } catch (DateTimeException expected) {
            }
        }
    }

    @Test
    public void test_range() {
        for (ChronoField field : ChronoField.values()) {
            // IsoChronology ranges should by definition be equal to the default ranges.
            assertEquals(field.range(), IsoChronology.INSTANCE.range(field));
        }
    }

    @Test
    public void test_zonedDateTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime
                .of(/* year */ 2017, /* month */ 4, /* dayOfMonth */ 1,
                        /* hour */ 15, /* minute */ 14, /* second */ 13, /* nanoOfSecond */ 12,
                        ZoneId.of("Europe/London"));

        ZonedDateTime result = IsoChronology.INSTANCE
                .zonedDateTime(zonedDateTime.toInstant(), zonedDateTime.getZone());
        assertEquals(LocalDate.of(2017, 4, 1), result.toLocalDate());
        assertEquals(LocalTime.of(15, 14, 13, 12), result.toLocalTime());
        assertEquals(ZoneOffset.ofHours(1), result.getOffset());
    }

    @Test(expected = NullPointerException.class)
    public void test_zonedDateTime_nullInstant() {
        IsoChronology.INSTANCE.zonedDateTime(null, ZoneOffset.UTC);
    }

    @Test(expected = NullPointerException.class)
    public void test_zonedDateTime_nullZone() {
        IsoChronology.INSTANCE.zonedDateTime(Instant.EPOCH, null);
    }
}
