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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import static java.time.temporal.ChronoField.ERA;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.time.chrono.Era;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.EnumSet;

@RunWith(JUnit4.class)
public class EraTest {

    @Test
    public void getLong_returnsValue_forEra() {
        assertEquals(10, TestEra.INSTANCE.getLong(ERA));
    }

    @Test
    public void getLong_throws_forNonERA_chronoFields() {
        EnumSet<ChronoField> unsupportedFields = EnumSet.complementOf(EnumSet.of(ERA));

        for (ChronoField unsupportedField : unsupportedFields) {
            try {
                TestEra.INSTANCE.getLong(unsupportedField);
                fail("getLong(" + unsupportedField + ") should throw exception");
            } catch (UnsupportedTemporalTypeException ignored) {
                // expected
            }
        }
    }

    @Test
    public void getLong_defersToArgument_forNonChronoFieldInstances() {
        TestTemporalField temporalField = new TestTemporalField();

        TestEra.INSTANCE.getLong(temporalField);

        temporalField.assertGetFromWasCalledWith(TestEra.INSTANCE);
    }

    private enum TestEra implements Era {
        INSTANCE;

        @Override
        public int getValue() {
            return 10;
        }
    }

    private static class TestTemporalField implements TemporalField {

        TemporalAccessor getFromArgument = null;

        @Override
        public TemporalUnit getBaseUnit() {
            throw new UnsupportedOperationException();
        }

        @Override
        public TemporalUnit getRangeUnit() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ValueRange range() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isDateBased() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isTimeBased() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isSupportedBy(TemporalAccessor temporal) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getFrom(TemporalAccessor temporal) {
            if (getFromArgument != null) {
                throw new IllegalStateException("getFrom was called more than once");
            }
            getFromArgument = temporal;
            return 0;
        }

        @Override
        public <R extends Temporal> R adjustInto(R temporal, long newValue) {
            throw new UnsupportedOperationException();
        }

        public void assertGetFromWasCalledWith(TemporalAccessor temporal) {
            assertSame(temporal, getFromArgument);
        }
    }


}
