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

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

@RunWith(JUnit4.class)
public class ChronoZonedDateTimeTest {

    @Test
    public void isSupported_returnsFalse_forForever() {
        ChronoZonedDateTime chronoZonedDateTime = new TestChronoZonedDateTime();
        assertFalse(chronoZonedDateTime.isSupported(ChronoUnit.FOREVER));
    }

    @Test
    public void isSupported_returnsFalse_forNull() {
        ChronoZonedDateTime chronoZonedDateTime = new TestChronoZonedDateTime();
        assertFalse(chronoZonedDateTime.isSupported((TemporalUnit) null));
    }

    @Test
    public void isSupported_defersToArgument() {
        ChronoZonedDateTime chronoZonedDateTime = new TestChronoZonedDateTime();
        TemporalUnit temporalUnit = mock(TemporalUnit.class);

        chronoZonedDateTime.isSupported(temporalUnit);

        verify(temporalUnit).isSupportedBy(chronoZonedDateTime);
    }

    /* Used to test default methods. */
    private static class TestChronoZonedDateTime implements ChronoZonedDateTime {

        @Override
        public ChronoLocalDateTime toLocalDateTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ZoneOffset getOffset() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ZoneId getZone() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChronoZonedDateTime withEarlierOffsetAtOverlap() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChronoZonedDateTime withLaterOffsetAtOverlap() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChronoZonedDateTime withZoneSameLocal(ZoneId zone) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChronoZonedDateTime withZoneSameInstant(ZoneId zone) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isSupported(TemporalField field) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChronoZonedDateTime with(TemporalField field, long newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChronoZonedDateTime plus(long amountToAdd, TemporalUnit unit) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long until(Temporal endExclusive, TemporalUnit unit) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(Object o) {
            throw new UnsupportedOperationException();
        }
    }

}
