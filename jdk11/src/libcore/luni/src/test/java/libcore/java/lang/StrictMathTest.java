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
 * limitations under the License.
 */

package libcore.java.lang;

import junit.framework.TestCase;

import java.math.BigInteger;

public class StrictMathTest extends TestCase {

    public void testIntExact() {
        testIntExact(123, 456);
        testIntExact(-456, 456);
        testIntExact(0, 0);
        testIntExact(Integer.MAX_VALUE, 1);
        testIntExact(Integer.MAX_VALUE, -1);
        testIntExact(Integer.MIN_VALUE, 1);
        testIntExact(Integer.MIN_VALUE, -1);
        testIntExact(Integer.MAX_VALUE, Integer.MAX_VALUE);
        testIntExact(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    private void testIntExact(int a, int b) {
        testAddExactI(a, b);
        testSubtractExactI(a, b);
        testMultiplyExactI(a, b);
    }

    private void testAddExactI(int a, int b) {
        long expected = (long) a + (long) b;
        try {
            assertEquals(expected, StrictMath.addExact(a, b));
        } catch (ArithmeticException e) {
            if (expected == a + b) {
                fail(); // This is not an overflow
            }
        }
    }

    private void testSubtractExactI(int a, int b) {
        long expected = (long) a - (long) b;
        try {
            assertEquals(expected, StrictMath.subtractExact(a, b));
        } catch (ArithmeticException e) {
            long result = (long) a - (long) b;
            if (expected == a - b) {
                fail(); // This is not an overflow
            }
        }
    }

    private void testMultiplyExactI(int a, int b) {
        long expected = (long) a * (long) b;
        try {
            assertEquals(expected, StrictMath.multiplyExact(a, b));
        } catch (ArithmeticException e) {
            if (expected == a * b) {
                fail(); // This is not an overflow
            }
        }
    }

    public void testLongExact() {
        testLongExact(123, 456);
        testLongExact(-456, 456);
        testLongExact(0, 0);
        testLongExact(Long.MAX_VALUE, 1);
        testLongExact(Long.MAX_VALUE, -1);
        testLongExact(Long.MIN_VALUE, 1);
        testLongExact(Long.MIN_VALUE, -1);
        testLongExact(Long.MAX_VALUE, Long.MAX_VALUE);
        testLongExact(Long.MIN_VALUE, Long.MIN_VALUE);
    }

    private void testLongExact(long a, long b) {
        testAddExactL(a, b);
        testSubtractExactL(a, b);
        testMultiplyExactL(a, b);
        testToIntExactL(a);
    }

    private void testAddExactL(long a, long b) {
        BigInteger expected = BigInteger.valueOf(a).add(BigInteger.valueOf(b));
        try {
            assertEquals(expected, BigInteger.valueOf(StrictMath.addExact(a, b)));
        } catch (ArithmeticException e) {
            if (expected.equals(BigInteger.valueOf(a + b))) {
                fail(); // This is not an overflow
            }
        }
    }

    private void testSubtractExactL(long a, long b) {
        BigInteger expected = BigInteger.valueOf(a).subtract(BigInteger.valueOf(b));
        try {
            assertEquals(expected, BigInteger.valueOf(StrictMath.subtractExact(a, b)));
        } catch (ArithmeticException e) {
            if (expected.equals(BigInteger.valueOf(a - b))) {
                fail(); // This is not an overflow
            }
        }
    }

    private void testMultiplyExactL(long a, long b) {
        BigInteger expected = BigInteger.valueOf(a).multiply(BigInteger.valueOf(b));
        try {
            assertEquals(expected, BigInteger.valueOf(StrictMath.multiplyExact(a, b)));
        } catch (ArithmeticException e) {
            if (expected.equals(BigInteger.valueOf(a * b))) {
                fail(); // This is not an overflow
            }
        }
    }

    private void testToIntExactL(long a) {
        try {
            assertEquals((int) a, StrictMath.toIntExact(a));
            assertEquals(a, StrictMath.toIntExact(a)); // Is not exact, should throw AE.
        } catch (ArithmeticException e) {
            if (a <= Integer.MAX_VALUE && a >= Integer.MIN_VALUE) {
                fail(); // This is not an overflow
            }
        }
    }

    public void testIntFloorDivMod() {
        testFloorDivModI(123, 456);
        testFloorDivModI(456, 123);
        testFloorDivModI(369, 123);
        testFloorDivModI(1, 0);
        testFloorDivModI(Integer.MAX_VALUE, 1);
        testFloorDivModI(Integer.MAX_VALUE, -1);
        testFloorDivModI(Integer.MIN_VALUE, 1);
        testFloorDivModI(Integer.MIN_VALUE, -1);
    }

    private void testFloorDivModI(int a, int b) {
        testFloorDivI(a, b);
        testFloorModI(a, b);
    }

    private void testFloorDivI(int a, int b) {
        try {
            int floorDiv = StrictMath.floorDiv(a, b);
            int expected = a / b;
            if (expected < 0 && a % b != 0) {
                --expected;
            }
            assertEquals(expected, floorDiv);
        } catch (ArithmeticException e) {
            if (b != 0) {
                fail(); // Should only throw AE when b is zero.
            }
        }
    }

    private void testFloorModI(int a, int b) {
        try {
            int floorMod = StrictMath.floorMod(a, b);
            int expected = a % b;
            if ((a ^ b) < 0 && expected != 0) {
                expected = b - expected;
            }
            assertEquals(expected, floorMod);
        } catch (ArithmeticException e) {
            if (b != 0) {
                fail(); // Should only throw AE when b is zero.
            }
        }
    }

    public void testLongFloorDivMod() {
        testFloorDivModL(123L, 456L);
        testFloorDivModL(456L, 123L);
        testFloorDivModL(369L, 123L);
        testFloorDivModL(1L, 0L);
        testFloorDivModL(Long.MAX_VALUE, 1L);
        testFloorDivModL(Long.MAX_VALUE, -1L);
        testFloorDivModL(Long.MIN_VALUE, 1L);
        testFloorDivModL(Long.MIN_VALUE, -1L);
    }

    private void testFloorDivModL(long a, long b) {
        testFloorDivL(a, b);
        testFloorModL(a, b);
    }

    private void testFloorDivL(long a, long b) {
        try {
            long floorDiv = StrictMath.floorDiv(a, b);
            long expected = a / b;
            if (expected < 0 && a % b != 0) {
                --expected;
            }
            assertEquals(expected, floorDiv);
        } catch (ArithmeticException e) {
            if (b != 0) {
                fail(); // Should only throw AE when b is zero.
            }
        }
    }

    private void testFloorModL(long a, long b) {
        try {
            long floorMod = StrictMath.floorMod(a, b);
            long expected = a % b;
            if ((a ^ b) < 0 && expected != 0) {
                expected = b - expected;
            }
            assertEquals(expected, floorMod);
        } catch (ArithmeticException e) {
            if (b != 0) {
                fail(); // Should only throw AE when b is zero.
            }
        }
    }

    private static long multiplyFullBigInt(int x, int y) {
        return BigInteger.valueOf(x).multiply(BigInteger.valueOf(y)).longValue();
    }

    public void testMultiplyFull() {
        int[][] v = new int[][]{
            {0, 0},
            {-1, 0},
            {0, -1},
            {1, 0},
            {0, 1},
            {-1, -1},
            {-1, 1},
            {1, -1},
            {1, 1},
            {Integer.MAX_VALUE, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, -Integer.MAX_VALUE},
            {-Integer.MAX_VALUE, Integer.MAX_VALUE},
            {-Integer.MAX_VALUE, -Integer.MAX_VALUE},
            {Integer.MAX_VALUE, Integer.MIN_VALUE},
            {Integer.MIN_VALUE, Integer.MAX_VALUE},
            {Integer.MIN_VALUE, Integer.MIN_VALUE}
        };

        for (int[] xy : v) {
            int x = xy[0];
            int y = xy[1];
            long p1 = multiplyFullBigInt(x, y);
            long p2 = StrictMath.multiplyFull(x, y);
            assertEquals(p1, p2);
        }
    }
}
