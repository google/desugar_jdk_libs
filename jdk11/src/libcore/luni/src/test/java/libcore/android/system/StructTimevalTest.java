/*
 * Copyright (C) 2019 The Android Open Source Project
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

package libcore.android.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import android.system.StructTimeval;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;


/**
 * Unit test for {@link StructTimeval}
 */
@RunWith(JUnitParamsRunner.class)
public class StructTimevalTest {

    private static final long MS_PER_SEC = 1000L;
    private static final long US_PER_MS = 1000L;
    private static final long US_PER_SEC = US_PER_MS * MS_PER_SEC;

    public static Object[][] interestingMillisValues() {
        // An array of { testMillisValue, expectedSeconds, expectedMicros }
        return new Object[][] {
                { 0L, 0L, 0L },
                { 1000L, 1L, 0L },
                { -1000L, -1L, 0L },

                // +ve and -ve cases close to zero seconds.
                { 23L, 0L, 23L * US_PER_MS /* 23000 */ },
                { -23L, -1L, US_PER_SEC - (23L * US_PER_MS) /* 977000 */ },

                // +ve and -ve cases with non-zero seconds.
                { 2003L, 2L, 3L * US_PER_MS /* 3000 */ },
                { -2003L, -3L, US_PER_SEC - (3L * US_PER_MS) /* 997000 */ },

                // Check for overflow.
                { Long.MAX_VALUE, /* 9223372036854775807 */
                        Long.MAX_VALUE / MS_PER_SEC, /* 9223372036854775 */
                        (Long.MAX_VALUE % MS_PER_SEC) * US_PER_MS, /* 807000 */
                },

                // Check for underflow. [Note: In Java (-ve % +ve) generates a -ve result]
                { Long.MIN_VALUE, /* -9223372036854775808 */
                        (Long.MIN_VALUE / MS_PER_SEC) - 1L, /* -9223372036854776 */
                        US_PER_SEC - (-(Long.MIN_VALUE % MS_PER_SEC) * US_PER_MS), /* 192000 */
                },
        };
    }

    @Parameters(method = "interestingMillisValues")
    @Test
    public void fromToMillis(long millisValue, long expectedSeconds, long expectedMicros) {
        StructTimeval val = StructTimeval.fromMillis(millisValue);

        assertEquals(expectedSeconds, val.tv_sec);
        assertEquals(expectedMicros, val.tv_usec);

        assertEquals(millisValue, val.toMillis());
    }

    @Test
    public void testEqualsAndHashcode() {
        Object[][] millisValues = interestingMillisValues();
        StructTimeval[] timeVals = new StructTimeval[millisValues.length];

        for (int i = 0; i < millisValues.length; i++) {
            long millisValue = (long) millisValues[i][0];
            StructTimeval value1 = StructTimeval.fromMillis(millisValue);
            StructTimeval value2 = StructTimeval.fromMillis(millisValue);

            assertEquals("value1.equals(value1)", value1, value1);
            assertEquals("value1.equals(value2)", value1, value2);
            assertEquals("value2.equals(value1)", value2, value1);

            assertEquals("value1.hashCode() == value2.hashCode()",
                    value1.hashCode(), value2.hashCode());

            timeVals[i] = value1;
        }

        for (int i = 0; i < millisValues.length; i++) {
            StructTimeval iVal = timeVals[i];
            for (int j = i + 1; j < millisValues.length; j++) {
                StructTimeval jVal = timeVals[j];
                assertFalse(iVal.equals(jVal));
                assertFalse(jVal.equals(iVal));
            }
        }
    }
}
