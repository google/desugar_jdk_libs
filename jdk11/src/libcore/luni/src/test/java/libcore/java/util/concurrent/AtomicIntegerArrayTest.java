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
 * limitations under the License
 */

package libcore.java.util.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.concurrent.atomic.AtomicIntegerArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AtomicIntegerArrayTest {

    private void checkArrayAsExpected(int[] expected, AtomicIntegerArray actual) {
        assertEquals(expected.length, actual.length());
        for (int i = 0; i < expected.length; ++i) {
            assertEquals(expected[i], actual.get(i));
        }
    }

    @Test
    public void testCompareAndExchange() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            assertEquals(expectedArray[i], arr.compareAndExchange(i, 0, i+1));
            checkArrayAsExpected(expectedArray, arr);

            assertEquals(expectedArray[i], arr.compareAndExchange(i, expectedArray[i], i+1));
            assertEquals(i+1, arr.get(i));
            expectedArray[i] = i+1;
            checkArrayAsExpected(expectedArray, arr);

            assertEquals(i+1, arr.compareAndExchange(i, i+1, -1));
            assertEquals(-1, arr.get(i));
            expectedArray[i] = -1;
            checkArrayAsExpected(expectedArray, arr);

            assertEquals(-1, arr.compareAndExchange(i, -1, i*2));
            assertEquals(i*2, arr.get(i));
            expectedArray[i] = i*2;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testCompareAndExchangeAcquire() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            assertEquals(expectedArray[i], arr.compareAndExchangeAcquire(i, 0, i+1));
            checkArrayAsExpected(expectedArray, arr);

            assertEquals(expectedArray[i], arr.compareAndExchangeAcquire(i, expectedArray[i], i+1));
            assertEquals(i+1, arr.get(i));
            expectedArray[i] = i+1;
            checkArrayAsExpected(expectedArray, arr);

            assertEquals(i+1, arr.compareAndExchangeAcquire(i, i+1, -1));
            assertEquals(-1, arr.get(i));
            expectedArray[i] = -1;
            checkArrayAsExpected(expectedArray, arr);

            assertEquals(-1, arr.compareAndExchangeAcquire(i, -1, i*2));
            assertEquals(i*2, arr.get(i));
            expectedArray[i] = i*2;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testCompareAndExchangeRelease() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            assertEquals(expectedArray[i], arr.compareAndExchangeRelease(i, 0, i+1));
            checkArrayAsExpected(expectedArray, arr);

            assertEquals(expectedArray[i], arr.compareAndExchangeRelease(i, expectedArray[i], i+1));
            assertEquals(i+1, arr.get(i));
            expectedArray[i] = i+1;
            checkArrayAsExpected(expectedArray, arr);

            assertEquals(i+1, arr.compareAndExchangeRelease(i, i+1, -1));
            assertEquals(-1, arr.get(i));
            expectedArray[i] = -1;
            checkArrayAsExpected(expectedArray, arr);

            assertEquals(-1, arr.compareAndExchangeRelease(i, -1, i*2));
            assertEquals(i*2, arr.get(i));
            expectedArray[i] = i*2;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testGetAcquire() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            assertEquals(expectedArray[i], arr.getAcquire(i));
            arr.set(i, i+1);
            assertEquals(i+1, arr.getAcquire(i));
        }
    }

    @Test
    public void testGetOpaque() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            assertEquals(expectedArray[i], arr.getOpaque(i));
            arr.set(i, i+1);
            assertEquals(i+1, arr.getOpaque(i));
        }
    }

    @Test
    public void testGetPlain() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            assertEquals(expectedArray[i], arr.getPlain(i));
            arr.set(i, i+1);
            assertEquals(i+1, arr.getPlain(i));
        }
    }

    @Test
    public void testSetOpaque() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            arr.setOpaque(i, i+1);
            assertEquals(i+1, arr.get(i));
            expectedArray[i] = i+1;
            checkArrayAsExpected(expectedArray, arr);

            arr.setOpaque(i, i*2);
            assertEquals(i*2, arr.get(i));
            expectedArray[i] = i*2;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testSetPlain() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            arr.setPlain(i, i+1);
            assertEquals(i+1, arr.get(i));
            expectedArray[i] = i+1;
            checkArrayAsExpected(expectedArray, arr);

            arr.setPlain(i, i*2);
            assertEquals(i*2, arr.get(i));
            expectedArray[i] = i*2;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testSetRelease() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            arr.setRelease(i, i+1);
            assertEquals(i+1, arr.get(i));
            expectedArray[i] = i+1;
            checkArrayAsExpected(expectedArray, arr);

            arr.setRelease(i, i*2);
            assertEquals(i*2, arr.get(i));
            expectedArray[i] = i*2;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testWeakCompareAndSetAcquire() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            for (int r = 0; r < 10; ++r) {
                assertFalse(arr.weakCompareAndSetAcquire(i, 0, -1));
                checkArrayAsExpected(expectedArray, arr);
            }
            do { } while (!arr.weakCompareAndSetAcquire(i, expectedArray[i], i+1));
            assertEquals(i+1, arr.get(i));
            expectedArray[i] = i+1;
            checkArrayAsExpected(expectedArray, arr);

            do { } while (!arr.weakCompareAndSetAcquire(i, expectedArray[i], i*2));
            assertEquals(i*2, arr.get(i));
            expectedArray[i] = i*2;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testWeakCompareAndSetPlain() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            for (int r = 0; r < 10; ++r) {
                assertFalse(arr.weakCompareAndSetPlain(i, 0, -1));
                checkArrayAsExpected(expectedArray, arr);
            }
            do { } while (!arr.weakCompareAndSetPlain(i, expectedArray[i], i+1));
            assertEquals(i+1, arr.get(i));
            expectedArray[i] = i+1;
            checkArrayAsExpected(expectedArray, arr);

            do { } while (!arr.weakCompareAndSetPlain(i, expectedArray[i], i*2));
            assertEquals(i*2, arr.get(i));
            expectedArray[i] = i*2;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testWeakCompareAndSetRelease() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            for (int r = 0; r < 10; ++r) {
                assertFalse(arr.weakCompareAndSetRelease(i, 0, -1));
                checkArrayAsExpected(expectedArray, arr);
            }
            do { } while (!arr.weakCompareAndSetRelease(i, expectedArray[i], i+1));
            assertEquals(i+1, arr.get(i));
            expectedArray[i] = i+1;
            checkArrayAsExpected(expectedArray, arr);

            do { } while (!arr.weakCompareAndSetRelease(i, expectedArray[i], i*2));
            assertEquals(i*2, arr.get(i));
            expectedArray[i] = i*2;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testWeakCompareAndSetVolatile() {
        int[] expectedArray = { 42, 43, 44, 45 };
        AtomicIntegerArray arr = new AtomicIntegerArray(expectedArray);
        for (int i = arr.length() - 1; i >= 0; --i) {
            for (int r = 0; r < 10; ++r) {
                assertFalse(arr.weakCompareAndSetVolatile(i, 0, -1));
                checkArrayAsExpected(expectedArray, arr);
            }

            do { } while (!arr.weakCompareAndSetVolatile(i, expectedArray[i], i+1));
            assertEquals(i+1, arr.get(i));
            expectedArray[i] = i+1;
            checkArrayAsExpected(expectedArray, arr);

            do { } while (!arr.weakCompareAndSetVolatile(i, expectedArray[i], i*2));
            assertEquals(i*2, arr.get(i));
            expectedArray[i] = i*2;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

}
