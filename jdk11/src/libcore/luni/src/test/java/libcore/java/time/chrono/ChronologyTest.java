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
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Additional tests for {@link Chronology}.
 *
 * @see tck.java.time.chrono.TCKChronology
 */
@RunWith(JUnit4.class)
public class ChronologyTest {

    @Test
    public void test_compareTo() {
        Set<Chronology> chronologies = new LinkedHashSet<>(Chronology.getAvailableChronologies());
        chronologies.add(new FakeChronology("aaa", "z aaa"));
        chronologies.add(new FakeChronology("zzz", "a zzz"));

        // Check for comparison of each chronology with each other (including itself).
        for (Chronology c1 : chronologies) {
            for (Chronology c2 : chronologies) {
                assertComparesAccordingToId(c1, c2);
            }
        }
    }

    private static void assertComparesAccordingToId(Chronology c1, Chronology c2) {
        int chronologyResult = c1.compareTo(c2);
        int idResult = c1.getId().compareTo(c2.getId());
        // note that this message is not strictly true: if two chronologies with the same id but
        // different parameters exist, then they should return non-zero for compareTo(). That is not
        // possible with any of the chronologies we currently ship (as of early 2017), though.
        assertEquals("compareTo() must match getId().compareTo()",
                (int) Math.signum(chronologyResult), (int) Math.signum(idResult));
        assertEquals(c1 + " and " + c2 + " compare as equal.",
                chronologyResult == 0, c1.equals(c2));
    }

    @Test(expected = NullPointerException.class)
    public void test_compareTo_null() {
        IsoChronology.INSTANCE.compareTo(null);
    }

    /** Fake chronology that supports only returning an id and a type. */
    private static class FakeChronology extends AbstractChronology {

        private final String id;

        private final String type;

        public FakeChronology(String id, String type) {
            this.id = id;
            this.type = type;
        }


        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getCalendarType() {
            return type;
        }

        @Override
        public ChronoLocalDate date(int prolepticYear, int month, int dayOfMonth) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChronoLocalDate dateYearDay(int prolepticYear, int dayOfYear) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChronoLocalDate dateEpochDay(long epochDay) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChronoLocalDate date(TemporalAccessor temporal) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isLeapYear(long prolepticYear) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int prolepticYear(Era era, int yearOfEra) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Era eraOf(int eraValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Era> eras() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ValueRange range(ChronoField field) {
            throw new UnsupportedOperationException();
        }
    }
}
