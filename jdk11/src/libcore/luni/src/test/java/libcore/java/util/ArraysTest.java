/*
 * Copyright (C) 2016 The Android Open Source Project
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

package libcore.java.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ArraysTest {
    private static final int[] TEST_ARRAY_SIZES = { 0, 1, 2, 10, 100, 1000 };


    /**
     * java.util.Arrays#setAll(int[], java.util.function.IntUnaryOperator)
     */
    @Test
    public void setAll$I() {
        int[] list = new int[3];
        list[0] = 0;
        list[1] = 1;
        list[2] = 2;

        Arrays.setAll(list, x -> x + 1);
        assertEquals(1, list[0]);
        assertEquals(2, list[1]);
        assertEquals(3, list[2]);

        try {
            Arrays.setAll(list, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.setAll((int[]) null, (x -> x + 1));
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * java.util.Arrays#parallelSetAll(int[], java.util.function.IntUnaryOperator)
     */
    @Test
    public void parallelSetAll$I() {
        int[] list = new int[3];
        list[0] = 0;
        list[1] = 1;
        list[2] = 2;

        Arrays.parallelSetAll(list, x -> x + 1);
        assertEquals(1, list[0]);
        assertEquals(2, list[1]);
        assertEquals(3, list[2]);

        try {
            Arrays.parallelSetAll(list, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelSetAll((int[]) null, (x -> x + 1));
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * java.util.Arrays#setAll(long[], java.util.function.IntToLongFunction)
     */
    @Test
    public void setAll$L() {
        long[] list = new long[3];
        list[0] = 0;
        list[1] = 1;
        list[2] = 2;

        Arrays.setAll(list, x -> x + 1);
        assertEquals(1, list[0]);
        assertEquals(2, list[1]);
        assertEquals(3, list[2]);

        try {
            Arrays.setAll(list, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.setAll((long[]) null, (x -> x + 1));
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * java.util.Arrays#parallelSetAll(long[], java.util.function.IntToLongFunction)
     */
    @Test
    public void parallelSetAll$L() {
        long[] list = new long[3];
        list[0] = 0;
        list[1] = 1;
        list[2] = 2;

        Arrays.parallelSetAll(list, x -> x + 1);
        assertEquals(1, list[0]);
        assertEquals(2, list[1]);
        assertEquals(3, list[2]);

        try {
            Arrays.parallelSetAll(list, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelSetAll((long[]) null, (x -> x + 1));
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * java.util.Arrays#setAll(double[], java.util.function.IntToDoubleFunction)
     */
    @Test
    public void setAll$D() {
        double[] list = new double[3];
        list[0] = 0.0d;
        list[1] = 1.0d;
        list[2] = 2.0d;

        Arrays.setAll(list, x -> x + 0.5);
        assertEquals(0.5d, list[0], 0.0);
        assertEquals(1.5d, list[1], 0.0);
        assertEquals(2.5d, list[2], 0.0);

        try {
            Arrays.setAll(list, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.setAll((double[]) null, x -> x + 0.5);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * java.util.Arrays#parallelSetAll(double[], java.util.function.IntToDoubleFunction)
     */
    @Test
    public void parallelSetAll$D() {
        double[] list = new double[3];
        list[0] = 0.0d;
        list[1] = 1.0d;
        list[2] = 2.0d;

        Arrays.parallelSetAll(list, x -> x + 0.5);
        assertEquals(0.5d, list[0], 0.0);
        assertEquals(1.5d, list[1], 0.0);
        assertEquals(2.5d, list[2], 0.0);

        try {
            Arrays.parallelSetAll(list, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelSetAll((double[]) null, x -> x + 0.5);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * java.util.Array#setAll(T[], java.util.function.IntFunction<\? extends T>)
     */
    @Test
    public void setAll$T() {
        String[] strings = new String[3];
        strings[0] = "a";
        strings[0] = "b";
        strings[0] = "c";

        Arrays.setAll(strings, x -> "a" + x);
        assertEquals("a0", strings[0]);
        assertEquals("a1", strings[1]);
        assertEquals("a2", strings[2]);

        try {
            Arrays.setAll(strings, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.setAll((String[]) null, x -> "a" + x);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * java.util.Array#parallelSetAll(T[], java.util.function.IntFunction<\? extends T>)
     */
    @Test
    public void parallelSetAll$T() {
        String[] strings = new String[3];
        strings[0] = "a";
        strings[0] = "b";
        strings[0] = "c";

        Arrays.parallelSetAll(strings, x -> "a" + x);
        assertEquals("a0", strings[0]);
        assertEquals("a1", strings[1]);
        assertEquals("a2", strings[2]);

        try {
            Arrays.parallelSetAll(strings, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelSetAll((String[]) null, x -> "a" + x);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * java.util.Array#parallelPrefix(int[], java.util.function.IntBinaryOperator)
     */
    @Test
    public void parallelPrefix$I() {
        // Get an arbitrary array of ints.
        Random rand = new Random(0);
        int[] list = new int[1000];
        for(int i = 0; i < list.length; ++i) {
            list[i] = rand.nextInt() % 1000; // Prevent overflow
        }

        int[] seqResult = list.clone();

        // Sequential solution
        for(int i = 0; i < seqResult.length - 1; ++i) {
            seqResult[i + 1] += seqResult[i];
        }

        Arrays.parallelPrefix(list, (x, y) -> x + y);
        assertArrayEquals(seqResult, list);

        try {
            Arrays.parallelPrefix(list, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelPrefix((int[]) null, (x, y) -> x + y);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * java.util.Array#parallelPrefix(int[], int, int, java.util.function.IntBinaryOperator)
     */
    @Test
    public void parallelPrefix$III() {
        // Get an arbitrary array of ints.
        Random rand = new Random(0);
        int[] list = new int[1000];
        for(int i = 0; i < list.length; ++i) {
            list[i] = rand.nextInt() % 1000; // Prevent overflow
        }

        int begin = 100, end = 500;
        int[] seqResult = list.clone();

        // Sequential solution
        for(int i = begin; i < end - 1; ++i) {
            seqResult[i + 1] += seqResult[i];
        }

        Arrays.parallelPrefix(list, begin, end, (x, y) -> x + y);
        assertArrayEquals(seqResult, list);

        try {
            Arrays.parallelPrefix(list, begin, end, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelPrefix((int[]) null, begin, end, (x, y) -> x + y);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelPrefix(list, end, begin, (x, y) -> x + y);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    /**
     * java.util.Array#parallelPrefix(long[], java.util.function.LongBinaryOperator)
     */
    @Test
    public void parallelPrefix$L() {
        // Get an arbitrary array of ints.
        Random rand = new Random(0);
        long[] list = new long[1000];
        for(int i = 0; i < list.length; ++i) {
            list[i] = rand.nextLong() % 1000000; // Prevent overflow
        }

        long[] seqResult = list.clone();

        // Sequential solution
        for(int i = 0; i < seqResult.length - 1; ++i) {
            seqResult[i + 1] += seqResult[i];
        }

        Arrays.parallelPrefix(list, (x, y) -> x + y);
        assertArrayEquals(seqResult, list);

        try {
            Arrays.parallelPrefix(list, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelPrefix((long[]) null, (x, y) -> x + y);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * java.util.Array#parallelPrefix(long[], int, int, java.util.function.LongBinaryOperator)
     */
    @Test
    public void parallelPrefix$LII() {
        // Get an arbitrary array of ints.
        Random rand = new Random(0);
        long[] list = new long[1000];
        for(int i = 0; i < list.length; ++i) {
            list[i] = rand.nextLong() % 1000000; // Prevent overflow
        }

        int begin = 100, end = 500;
        long[] seqResult = list.clone();

        // Sequential solution
        for(int i = begin; i < end - 1; ++i) {
            seqResult[i + 1] += seqResult[i];
        }

        Arrays.parallelPrefix(list, begin, end, (x, y) -> x + y);
        assertArrayEquals(seqResult, list);

        try {
            Arrays.parallelPrefix(list, begin, end, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelPrefix((long[]) null, begin, end, (x, y) -> x + y);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelPrefix(list, end, begin, (x, y) -> x + y);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    /**
     * java.util.Array#parallelPrefix(double[], java.util.function.DoubleBinaryOperator)
     */
    @Test
    public void parallelPrefix$D() {
        // Get an arbitrary array of ints.
        Random rand = new Random(0);
        double[] list = new double[1000];
        for(int i = 0; i < list.length; ++i) {
            list[i] = rand.nextDouble() * 1000;
        }

        double[] seqResult = list.clone();

        // Sequential solution
        for(int i = 0; i < seqResult.length - 1; ++i) {
            seqResult[i + 1] += seqResult[i];
        }

        Arrays.parallelPrefix(list, (x, y) -> x + y);

        // Parallel double arithmetic contains error, reduce to integer for comparison.
        int[] listInInt = Arrays.stream(list).mapToInt(x -> (int) x).toArray();
        int[] seqResultInInt = Arrays.stream(seqResult).mapToInt(x -> (int) x).toArray();
        assertArrayEquals(seqResultInInt, listInInt);

        try {
            Arrays.parallelPrefix(list, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelPrefix((double[]) null, (x, y) -> x + y);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * java.util.Array#parallelPrefix(double[], int, int, java.util.function.DoubleBinaryOperator)
     */
    @Test
    public void parallelPrefix$DII() {
        // Get an arbitrary array of ints.
        Random rand = new Random(0);
        double[] list = new double[1000];
        for(int i = 0; i < list.length; ++i) {
            list[i] = rand.nextDouble() * 1000;
        }

        int begin = 100, end = 500;
        double[] seqResult = list.clone();

        // Sequential solution
        for(int i = begin; i < end - 1; ++i) {
            seqResult[i + 1] += seqResult[i];
        }

        Arrays.parallelPrefix(list, begin, end, (x, y) -> x + y);

        // Parallel double arithmetic contains error, reduce to integer for comparison.
        int[] listInInt = Arrays.stream(list).mapToInt(x -> (int) x).toArray();
        int[] seqResultInInt = Arrays.stream(seqResult).mapToInt(x -> (int) x).toArray();
        assertArrayEquals(seqResultInInt, listInInt);

        try {
            Arrays.parallelPrefix(list, begin, end, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelPrefix((double[]) null, begin, end, (x, y) -> x + y);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelPrefix(list, end, begin, (x, y) -> x + y);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    /**
     * java.util.Array#parallelPrefix(T[], java.util.function.BinaryOperator<T>)
     */
    @Test
    public void parallelPrefix$T() {
        String[] strings = new String[3];
        strings[0] = "a";
        strings[1] = "b";
        strings[2] = "c";

        Arrays.parallelPrefix(strings, (x, y) -> x + y);
        assertEquals("a", strings[0]);
        assertEquals("ab", strings[1]);
        assertEquals("abc", strings[2]);

        try {
            Arrays.parallelPrefix(strings, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelPrefix((String[]) null, (x, y) -> x + y);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * java.util.Array#parallelPrefix(T[], int, int, java.util.function.BinaryOperator<T>)
     */
    @Test
    public void parallelPrefix$TII() {
        String[] strings = new String[5];
        strings[0] = "a";
        strings[1] = "b";
        strings[2] = "c";
        strings[3] = "d";
        strings[4] = "e";
        int begin = 1, end = 4;

        Arrays.parallelPrefix(strings, begin, end, (x, y) -> x + y);
        assertEquals("a", strings[0]);
        assertEquals("b", strings[1]);
        assertEquals("bc", strings[2]);
        assertEquals("bcd", strings[3]);
        assertEquals("e", strings[4]);

        try {
            Arrays.parallelPrefix(strings, begin, end, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelPrefix((String[]) null, begin, end, (x, y) -> x + y);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.parallelPrefix(strings, end, begin, (x, y) -> x + y);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    // http://b/74236526
    @Test
    public void deepEquals_nestedArraysOfDifferentTypesButEqualValues() {
        assertTrue(Arrays.deepEquals(
            new Object[] { new Object[] { "Hello", "world" } },
            new Object[] { new String[] { "Hello", "world" } }));
    }

    @Test
    public void streamInt() {
        for (int size : TEST_ARRAY_SIZES) {
            int[] sourceArray = intTestArray(size);

            // Stream, map, accumulate
            int sum = Arrays.stream(sourceArray)
                .map(i -> i + i)
                .sum();
            assertEquals(size * (size - 1), sum);

            // Stream, collect as array again
            int[] destArray = Arrays.stream(sourceArray)
                .toArray();
            assertArrayEquals(sourceArray, destArray);
            assertNotSame(sourceArray, destArray);

            // Stream, box, collect as list
            List<Integer> destList = Arrays.stream(sourceArray)
                .boxed()
                .collect(Collectors.toList());

            assertEquals(size, destList.size());
            for (int i = 0; i < size; i++) {
                assertEquals((int) destList.get(i), i);
            }
        }
    }

    @Test
    public void streamIntStartEnd() {
        final int size = 10;
        int[] sourceArray = intTestArray(size);
        for (int start = 0; start < size - 1; start++) {
            for (int end = start; end < size; end++) {
                int[] destArray = Arrays.stream(sourceArray, start, end)
                    .toArray();
                int len = end - start;
                assertEquals(len, destArray.length);
                if (len > 0) {
                    assertEquals(start, destArray[0]);
                    assertEquals(end - 1, destArray[len - 1]);
                }
            }
        }
    }

    @Test
    public void streamIntStartEnd_Exceptions() {
        int[] sourceArray = intTestArray(10);
        try {
            int unused = Arrays.stream(sourceArray, -1, 9)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            int unused = Arrays.stream(sourceArray, 0, 11)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            int unused = Arrays.stream(sourceArray, 11, 11)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            int unused = Arrays.stream(sourceArray, 0, -1)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            int unused = Arrays.stream(sourceArray, 4, 3)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
    }

    @Test
    public void streamLong() {
        for (int size : TEST_ARRAY_SIZES) {
            long[] sourceArray = longTestArray(size);

            // Stream, map, accumulate
            long sum = Arrays.stream(sourceArray)
                .map(i -> i + i)
                .sum();
            assertEquals(size * (size - 1), sum);

            // Stream, collect as array again
            long[] destArray = Arrays.stream(sourceArray)
                .toArray();
            assertArrayEquals(sourceArray, destArray);
            assertNotSame(sourceArray, destArray);

            // Stream, box, collect as list
            List<Long> destList = Arrays.stream(sourceArray)
                .boxed()
                .collect(Collectors.toList());

            assertEquals(size, destList.size());
            for (int i = 0; i < size; i++) {
                assertEquals((long) destList.get(i), i);
            }
        }
    }

    @Test
    public void streamLongStartEnd() {
        final int size = 10;
        long[] sourceArray = longTestArray(size);
        for (int start = 0; start < size - 1; start++) {
            for (int end = start; end < size; end++) {
                long[] destArray = Arrays.stream(sourceArray, start, end)
                    .toArray();
                int len = end - start;
                assertEquals(len, destArray.length);
                if (len > 0) {
                    assertEquals(start, destArray[0]);
                    assertEquals(end - 1, destArray[len - 1]);
                }
            }
        }
    }

    @Test
    public void streamLongStartEnd_Exceptions() {
        long[] sourceArray = longTestArray(10);
        try {
            long unused = Arrays.stream(sourceArray, -1, 9)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            long unused = Arrays.stream(sourceArray, 0, 11)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            long unused = Arrays.stream(sourceArray, 11, 11)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            long unused = Arrays.stream(sourceArray, 0, -1)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            long unused = Arrays.stream(sourceArray, 4, 3)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
    }

    @Test
    public void streamDouble() {
        for (int size : TEST_ARRAY_SIZES) {
            double[] sourceArray = doubleTestArray(size);

            // Stream, map, accumulate
            double sum = Arrays.stream(sourceArray)
                .map(i -> i + i)
                .sum();
            assertEquals(size * (size - 1), sum, 0.001);

            // Stream, collect as array again
            double[] destArray = Arrays.stream(sourceArray)
                .toArray();
            assertArrayEquals(sourceArray, destArray, 0.001);
            assertNotSame(sourceArray, destArray);

            // Stream, box, collect as list
            List<Double> destList = Arrays.stream(sourceArray)
                .boxed()
                .collect(Collectors.toList());

            assertEquals(size, destList.size());
            for (int i = 0; i < size; i++) {
                assertEquals(destList.get(i), i, 0.001);
            }
        }
    }

    @Test
    public void streamDoubleStartEnd() {
        final int size = 10;
        double[] sourceArray = doubleTestArray(size);
        for (int start = 0; start < size - 1; start++) {
            for (int end = start; end < size; end++) {
                double[] destArray = Arrays.stream(sourceArray, start, end)
                    .toArray();
                int len = end - start;
                assertEquals(len, destArray.length);
                if (len > 0) {
                    assertEquals(start, destArray[0], 0.0);
                    assertEquals(end - 1, destArray[len - 1], 0.0);
                }
            }
        }
    }

    @Test
    public void streamDoubleStartEnd_Exceptions() {
        double[] sourceArray = doubleTestArray(10);
        try {
            double unused = Arrays.stream(sourceArray, -1, 9)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            double unused = Arrays.stream(sourceArray, 0, 11)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            double unused = Arrays.stream(sourceArray, 11, 11)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            double unused = Arrays.stream(sourceArray, 0, -1)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            double unused = Arrays.stream(sourceArray, 4, 3)
                .sum();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
    }

    @Test
    public void streamObject() {
      for (int size : TEST_ARRAY_SIZES) {
        String[] sourceArray = stringTestArray(size);

        // Stream, map, accumulate
        int sum = Arrays.stream(sourceArray)
            .mapToInt(i -> Integer.parseInt(i) * 2)
            .sum();
        assertEquals(size * (size - 1), sum);

        // Stream, collect as array again
        String[] destArray = Arrays.stream(sourceArray)
            .toArray(String[]::new);
        assertArrayEquals(sourceArray, destArray);
        assertNotSame(sourceArray, destArray);

        // Stream, collect as list
        List<String> destList = Arrays.stream(sourceArray)
            .collect(Collectors.toList());

        assertEquals(size, destList.size());
        for (int i = 0; i < size; i++) {
          assertSame(destList.get(i), sourceArray[i]);
        }
      }
    }

    @Test
    public void streamObjectStartEnd() {
        final int size = 10;
        String[] sourceArray = stringTestArray(size);
        for (int start = 0; start < size - 1; start++) {
            for (int end = start; end < size; end++) {
                String[] destArray = Arrays.stream(sourceArray, start, end)
                    .toArray(String[]::new);
                int len = end - start;
                assertEquals(len, destArray.length);
                if (len > 0) {
                    assertSame(sourceArray[start], destArray[0]);
                    assertSame(sourceArray[end - 1], destArray[len - 1]);
                }
            }
        }
    }

    @Test
    public void streamObjectStartEnd_Exceptions() {
        String[] sourceArray = stringTestArray(10);
        try {
            long unused = Arrays.stream(sourceArray, -1, 9)
                .count();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            long unused = Arrays.stream(sourceArray, 0, 11)
                .count();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            long unused = Arrays.stream(sourceArray, 11, 11)
                .count();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            long unused = Arrays.stream(sourceArray, 0, -1)
                .count();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
        try {
            long unused = Arrays.stream(sourceArray, 4, 3)
                .count();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
    }

    private int[] intTestArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    private long[] longTestArray(int size) {
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    private double[] doubleTestArray(int size) {
        double[] array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    private String[] stringTestArray(int size) {
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = String.valueOf(i);
        }
        return array;
    }
}
