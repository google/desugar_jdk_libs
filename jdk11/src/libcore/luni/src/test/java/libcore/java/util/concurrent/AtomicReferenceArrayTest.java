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
import static org.junit.Assert.assertSame;

import java.util.concurrent.atomic.AtomicReferenceArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AtomicReferenceArrayTest {

    private void checkArrayAsExpected(Integer[] expected, AtomicReferenceArray<Integer> actual) {
        assertEquals(expected.length, actual.length());
        for (int i = 0; i < expected.length; ++i) {
            assertSame(expected[i], actual.get(i));
        }
    }

    @Test
    public void testCompareAndExchange() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            Integer val = Integer.valueOf(i+1);
            assertEquals(expectedArray[i], arr.compareAndExchange(i, Integer.valueOf(0), val));
            checkArrayAsExpected(expectedArray, arr);

            assertEquals(expectedArray[i], arr.compareAndExchange(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i+1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(-1);
            assertEquals(expectedArray[i], arr.compareAndExchange(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(-1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(i*2);
            assertEquals(expectedArray[i], arr.compareAndExchange(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i*2, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testCompareAndExchangeAcquire() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            Integer val = Integer.valueOf(i+1);
            assertEquals(expectedArray[i], arr.compareAndExchangeAcquire(i, Integer.valueOf(0), val));
            checkArrayAsExpected(expectedArray, arr);

            assertEquals(expectedArray[i], arr.compareAndExchangeAcquire(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i+1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(-1);
            assertEquals(expectedArray[i], arr.compareAndExchangeAcquire(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(-1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(i*2);
            assertEquals(expectedArray[i], arr.compareAndExchangeAcquire(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i*2, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testCompareAndExchangeRelease() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            Integer val = Integer.valueOf(i+1);
            assertEquals(expectedArray[i], arr.compareAndExchangeRelease(i, Integer.valueOf(0), val));
            checkArrayAsExpected(expectedArray, arr);

            assertEquals(expectedArray[i], arr.compareAndExchangeRelease(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i+1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(-1);
            assertEquals(expectedArray[i], arr.compareAndExchangeRelease(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(-1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(i*2);
            assertEquals(expectedArray[i], arr.compareAndExchangeRelease(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i*2, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testGetAcquire() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            assertSame(expectedArray[i], arr.getAcquire(i));
            arr.set(i, Integer.valueOf(i+1));
            assertEquals(i+1, arr.getAcquire(i).intValue());
        }
    }

    @Test
    public void testGetOpaque() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            assertSame(expectedArray[i], arr.getOpaque(i));
            arr.set(i, Integer.valueOf(i+1));
            assertEquals(i+1, arr.getOpaque(i).intValue());
        }
    }

    @Test
    public void testGetPlain() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            assertSame(expectedArray[i], arr.getPlain(i));
            arr.set(i, Integer.valueOf(i+1));
            assertEquals(i+1, arr.getPlain(i).intValue());
        }
    }

    @Test
    public void testSetOpaque() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            Integer val = Integer.valueOf(i+1);
            arr.setOpaque(i, val);
            assertSame(val, arr.get(i));
            assertEquals(i+1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(i*2);
            arr.setOpaque(i, val);
            assertSame(val, arr.get(i));
            assertEquals(i*2, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testSetPlain() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            Integer val = Integer.valueOf(i+1);
            arr.setPlain(i, val);
            assertSame(val, arr.get(i));
            assertEquals(i+1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(i*2);
            arr.setPlain(i, val);
            assertSame(val, arr.get(i));
            assertEquals(i*2, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testSetRelease() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            Integer val = Integer.valueOf(i+1);
            arr.setRelease(i, val);
            assertSame(val, arr.get(i));
            assertEquals(i+1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(i*2);
            arr.setRelease(i, val);
            assertSame(val, arr.get(i));
            assertEquals(i*2, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testWeakCompareAndSetAcquire() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            for (int r = 0; r < 10; ++r) {
                assertFalse(arr.weakCompareAndSetAcquire(i, Integer.valueOf(0), Integer.valueOf(-1)));
                checkArrayAsExpected(expectedArray, arr);
            }
            Integer val = Integer.valueOf(i+1);
            do { } while (!arr.weakCompareAndSetAcquire(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i+1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(i*2);
            do { } while (!arr.weakCompareAndSetAcquire(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i*2, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testWeakCompareAndSetPlain() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            for (int r = 0; r < 10; ++r) {
                assertFalse(arr.weakCompareAndSetPlain(i, Integer.valueOf(0), Integer.valueOf(-1)));
                checkArrayAsExpected(expectedArray, arr);
            }
            Integer val = Integer.valueOf(i+1);
            do { } while (!arr.weakCompareAndSetPlain(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i+1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(i*2);
            do { } while (!arr.weakCompareAndSetPlain(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i*2, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testWeakCompareAndSetRelease() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            for (int r = 0; r < 10; ++r) {
                assertFalse(arr.weakCompareAndSetRelease(i, Integer.valueOf(0), Integer.valueOf(-1)));
                checkArrayAsExpected(expectedArray, arr);
            }
            Integer val = Integer.valueOf(i+1);
            do { } while (!arr.weakCompareAndSetRelease(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i+1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(i*2);
            do { } while (!arr.weakCompareAndSetRelease(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i*2, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

    @Test
    public void testWeakCompareAndSetVolatile() {
        Integer[] expectedArray = {
            Integer.valueOf(42),
            Integer.valueOf(43),
            Integer.valueOf(44),
            Integer.valueOf(45)
        };
        AtomicReferenceArray<Integer> arr = new AtomicReferenceArray(expectedArray);
        for (int i = 0; i < arr.length(); ++i) {
            for (int r = 0; r < 10; ++r) {
                assertFalse(arr.weakCompareAndSetVolatile(i, Integer.valueOf(0), Integer.valueOf(-1)));
                checkArrayAsExpected(expectedArray, arr);
            }
            Integer val = Integer.valueOf(i+1);
            do { } while (!arr.weakCompareAndSetVolatile(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i+1, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);

            val = Integer.valueOf(i*2);
            do { } while (!arr.weakCompareAndSetVolatile(i, expectedArray[i], val));
            assertSame(val, arr.get(i));
            assertEquals(i*2, arr.get(i).intValue());
            expectedArray[i] = val;
            checkArrayAsExpected(expectedArray, arr);
        }
    }

}
