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

package libcore.java.util.stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.stream.LongStream;

@RunWith(JUnit4.class)
public class LongStreamTest {

    private static final int[] TEST_SIZES = {0, 1, 2, 10, 20, 100, 1000};

    @Test
    public void ofArraysWithDifferentSizes() {
        for (int size : TEST_SIZES) {
            long[] sourceArray = generate(size);
            LongStream stream = LongStream.of(sourceArray);

            long[] destArray = stream.toArray();

            assertFalse("By default stream should be sequential", stream.isParallel());
            assertNotSame("New array should be generated", sourceArray, destArray);
            assertArrayEquals(sourceArray, destArray);
        }
    }

    @Test
    public void ofNullArray_shouldThrowNPE() {
        try {
            LongStream.of((long[]) null);
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }
    }

    private long[] generate(int size) {
        long[] array = new long[size];

        for (int index = 0; index < size; ++index) {
            array[index] = index + 1;
        }

        return array;
    }
}
