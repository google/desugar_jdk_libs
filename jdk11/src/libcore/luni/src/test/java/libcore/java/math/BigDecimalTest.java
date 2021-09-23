/*
 * Copyright (C) 2010 The Android Open Source Project
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

package libcore.java.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Locale;

import junit.framework.TestCase;

import static java.math.BigDecimal.valueOf;

public final class BigDecimalTest extends TestCase {

    public void testGetPrecision() {
        assertPrecision(1, "0");
        assertPrecision(1, "0.9");
        assertPrecision(16, "0.9999999999999999");
        assertPrecision(16, "9999999999999999");
        assertPrecision(19, "1000000000000000000");
        assertPrecision(19, "1000000000000000001");
        assertPrecision(19, "-1000000000000000001");
        assertPrecision(19, "-1000000000000000000");

        String tenNines = "9999999999";
        String fiftyNines = tenNines + tenNines + tenNines + tenNines + tenNines;
        assertPrecision(10, "0." + tenNines);
        assertPrecision(50, "0." + fiftyNines);
        assertPrecision(250, "0." + fiftyNines + fiftyNines + fiftyNines + fiftyNines + fiftyNines);
        assertPrecision(10, tenNines);
        assertPrecision(50, fiftyNines);
        assertPrecision(250, fiftyNines + fiftyNines + fiftyNines + fiftyNines + fiftyNines);

        // test these special cases because we know precision() uses longs internally
        String maxLong = Long.toString(Long.MAX_VALUE);
        assertPrecision(maxLong.length(), maxLong);
        String minLong = Long.toString(Long.MIN_VALUE);
        assertPrecision(minLong.length() - 1, minLong);
    }

    private void assertPrecision(int expectedPrecision, String value) {
        BigDecimal parsed = new BigDecimal(value);
        assertEquals("Unexpected precision for parsed value " + value,
                expectedPrecision, parsed.precision());

        BigDecimal computed = parsed.divide(BigDecimal.ONE);
        assertEquals("Unexpected precision for computed value " + value,
                expectedPrecision, computed.precision());
    }

    public void testRound() {
        BigDecimal bigDecimal = new BigDecimal("0.999999999999999");
        BigDecimal rounded = bigDecimal.round(new MathContext(2, RoundingMode.FLOOR));
        assertEquals("0.99", rounded.toString());
    }

    // https://code.google.com/p/android/issues/detail?id=43480
    public void testPrecisionFromString() {
        BigDecimal a = new BigDecimal("-0.011111111111111111111");
        BigDecimal b = a.multiply(BigDecimal.ONE);

        assertEquals("-0.011111111111111111111", a.toString());
        assertEquals("-0.011111111111111111111", b.toString());

        assertEquals(20, a.precision());
        assertEquals(20, b.precision());

        assertEquals(21, a.scale());
        assertEquals(21, b.scale());

        assertEquals("-11111111111111111111", a.unscaledValue().toString());
        assertEquals("-11111111111111111111", b.unscaledValue().toString());

        assertEquals(a, b);
        assertEquals(b, a);

        assertEquals(0, a.subtract(b).signum());
        assertEquals(0, a.compareTo(b));
    }

    public void testPrecisionFromString_simplePowersOfTen() {
        assertEquals(new BigDecimal(BigInteger.valueOf(-10), 1), new BigDecimal("-1.0"));
        assertEquals(new BigDecimal(BigInteger.valueOf(-1), 1), new BigDecimal("-0.1"));
        assertEquals(new BigDecimal(BigInteger.valueOf(-1), -1), new BigDecimal("-1E+1"));

        assertEquals(new BigDecimal(BigInteger.valueOf(10), 1), new BigDecimal("1.0"));
        assertEquals(new BigDecimal(BigInteger.valueOf(1), 0), new BigDecimal("1"));
        assertFalse(new BigDecimal("1.0").equals(new BigDecimal("1")));
    }

    // https://code.google.com/p/android/issues/detail?id=54580
    public void test54580() {
        BigDecimal a = new BigDecimal("1.200002");
        assertEquals("1.200002", a.toPlainString());
        assertEquals("1.20", a.abs(new MathContext(3,RoundingMode.HALF_UP)).toPlainString());
        assertEquals("1.200002", a.toPlainString());
    }

    // https://code.google.com/p/android/issues/detail?id=191227
    public void test191227() {
        BigDecimal zero = BigDecimal.ZERO;
        zero = zero.setScale(2, RoundingMode.HALF_EVEN);

        BigDecimal other = valueOf(999999998000000001.00);
        other = other.setScale(2, RoundingMode.HALF_EVEN);

        assertFalse(zero.equals(other));
        assertFalse(other.equals(zero));
    }

    private static void checkDivide(String expected, long n, long d, int scale, RoundingMode rm) {
        assertEquals(String.format(Locale.US, "%d/%d [%d, %s]", n, d, scale, rm.name()),
                new BigDecimal(expected),
                new BigDecimal(n).divide(new BigDecimal(d), scale, rm));
    }

    public void testDivideRounding() {
        // checkDivide(expected, dividend, divisor, scale, roundingMode)
        checkDivide("0", 1, Long.MIN_VALUE, 0, RoundingMode.DOWN);
        checkDivide("-1", 1, Long.MIN_VALUE, 0, RoundingMode.UP);
        checkDivide("-1", 1, Long.MIN_VALUE, 0, RoundingMode.FLOOR);
        checkDivide("0", 1, Long.MIN_VALUE, 0, RoundingMode.CEILING);
        checkDivide("0", 1, Long.MIN_VALUE, 0, RoundingMode.HALF_EVEN);
        checkDivide("0", 1, Long.MIN_VALUE, 0, RoundingMode.HALF_UP);
        checkDivide("0", 1, Long.MIN_VALUE, 0, RoundingMode.HALF_DOWN);

        checkDivide("1", Long.MAX_VALUE, Long.MAX_VALUE / 2 + 1, 0, RoundingMode.DOWN);
        checkDivide("2", Long.MAX_VALUE, Long.MAX_VALUE / 2, 0, RoundingMode.DOWN);
        checkDivide("0.50", Long.MAX_VALUE / 2, Long.MAX_VALUE, 2, RoundingMode.HALF_UP);
        checkDivide("0.50", Long.MIN_VALUE / 2, Long.MIN_VALUE, 2, RoundingMode.HALF_UP);
        checkDivide("0.5000", Long.MIN_VALUE / 2, Long.MIN_VALUE, 4, RoundingMode.HALF_UP);
        // (-2^62 + 1) / (-2^63) = (2^62 - 1) / 2^63 = 0.5 - 2^-63
        checkDivide("0", Long.MIN_VALUE / 2 + 1, Long.MIN_VALUE, 0, RoundingMode.HALF_UP);
        checkDivide("1", Long.MIN_VALUE / 2, Long.MIN_VALUE, 0, RoundingMode.HALF_UP);
        checkDivide("0", Long.MIN_VALUE / 2, Long.MIN_VALUE, 0, RoundingMode.HALF_DOWN);
        // (-2^62 - 1) / (-2^63) = (2^62 + 1) / 2^63 = 0.5 + 2^-63
        checkDivide("1", Long.MIN_VALUE / 2 - 1, Long.MIN_VALUE, 0, RoundingMode.HALF_DOWN);
    }

    /**
     * Test a bunch of pairings with even/odd dividend and divisor whose
     * result is near +/- 0.5.
     */
    public void testDivideRounding_sign() {
        // checkDivide(expected, dividend, divisor, scale, roundingMode)
        // positive dividend and divisor, even/odd values
        checkDivide("0", 49, 100, 0, RoundingMode.HALF_UP);
        checkDivide("1", 50, 100, 0, RoundingMode.HALF_UP);
        checkDivide("1", 51, 101, 0, RoundingMode.HALF_UP);
        checkDivide("0", 50, 101, 0, RoundingMode.HALF_UP);
        checkDivide("0", Long.MAX_VALUE / 2, Long.MAX_VALUE, 0, RoundingMode.HALF_UP);

        // Same with negative dividend and divisor
        checkDivide("0", -49, -100, 0, RoundingMode.HALF_UP);
        checkDivide("1", -50, -100, 0, RoundingMode.HALF_UP);
        checkDivide("1", -51, -101, 0, RoundingMode.HALF_UP);
        checkDivide("0", -50, -101, 0, RoundingMode.HALF_UP);
        checkDivide("0", -(Long.MAX_VALUE / 2), -Long.MAX_VALUE, 0, RoundingMode.HALF_UP);

        // Same with negative dividend
        checkDivide("0", -49, 100, 0, RoundingMode.HALF_UP);
        checkDivide("-1", -50, 100, 0, RoundingMode.HALF_UP);
        checkDivide("-1", -51, 101, 0, RoundingMode.HALF_UP);
        checkDivide("0", -50, 101, 0, RoundingMode.HALF_UP);
        checkDivide("0", -(Long.MAX_VALUE / 2), Long.MAX_VALUE, 0, RoundingMode.HALF_UP);

        // Same with negative divisor
        checkDivide("0", 49, -100, 0, RoundingMode.HALF_UP);
        checkDivide("-1", 50, -100, 0, RoundingMode.HALF_UP);
        checkDivide("-1", 51, -101, 0, RoundingMode.HALF_UP);
        checkDivide("0", 50, -101, 0, RoundingMode.HALF_UP);
        checkDivide("0", Long.MAX_VALUE / 2, -Long.MAX_VALUE, 0, RoundingMode.HALF_UP);
    }

    public void testDivideByOne() {
        long[] dividends = new long[] {
                Long.MIN_VALUE,
                Long.MIN_VALUE + 1,
                Long.MAX_VALUE,
                Long.MAX_VALUE - 1,
                0,
                -1,
                1,
                10, 43, 314159265358979323L, // arbitrary values
        };
        for (long dividend : dividends) {
            String expected = Long.toString(dividend);
            checkDivide(expected, dividend, 1, 0, RoundingMode.UNNECESSARY);
        }
    }

    public void testNegate() {
        checkNegate(valueOf(0), valueOf(0));
        checkNegate(valueOf(1), valueOf(-1));
        checkNegate(valueOf(43), valueOf(-43));
        checkNegate(valueOf(Long.MAX_VALUE), valueOf(-Long.MAX_VALUE));
        checkNegate(new BigDecimal("9223372036854775808"), valueOf(Long.MIN_VALUE));
        // arbitrary large decimal
        checkNegate(new BigDecimal("342343243546465623424321423112321.43243434343412321"),
                new BigDecimal("-342343243546465623424321423112321.43243434343412321"));
    }

    private static void checkNegate(BigDecimal a, BigDecimal b) {
        if (!a.toString().equals("0")) {
            assertFalse(a.equals(b));
        }
        assertEquals(a.negate(), b);
        assertEquals(a, b.negate());
        assertEquals(a, a.negate().negate());
    }

    public void testAddAndSubtract_near64BitOverflow() throws Exception {
        // Check that the test is set up correctly - these values should be MIN_VALUE and MAX_VALUE
        assertEquals("-9223372036854775808", Long.toString(Long.MIN_VALUE));
        assertEquals("9223372036854775807", Long.toString(Long.MAX_VALUE));

        // Exactly MIN_VALUE and MAX_VALUE
        assertSum("-9223372036854775808", -(1L << 62L), -(1L << 62L));
        assertSum("9223372036854775807", (1L << 62L) - 1L, 1L << 62L);

        // One beyond MIN_VALUE and MAX_VALUE
        assertSum("-9223372036854775809", -(1L << 62L), -(1L << 62L) - 1);
        assertSum("-9223372036854775809", Long.MIN_VALUE + 1, -2);
        assertSum("9223372036854775808", 1L << 62L, 1L << 62L);
        assertSum("9223372036854775808", Long.MAX_VALUE, 1);
    }

    /**
     * Assert that {@code (a + b), (b + a), (a - (-b)) and (b - (-a))} all have the same
     * expected result in BigDecimal arithmetic.
     */
    private static void assertSum(String expectedSumAsString, long a, long b) {
        if (a == Long.MIN_VALUE || b == Long.MIN_VALUE) {
            // - (Long.MIN_VALUE) can't be represented as a long, so don't allow it here.
            throw new IllegalArgumentException("Long.MIN_VALUE not allowed");
        }
        BigDecimal bigA = valueOf(a);
        BigDecimal bigB = valueOf(b);
        BigDecimal bigMinusB = valueOf(-b);
        BigDecimal bigMinusA = valueOf(-a);

        assertEquals("a + b", expectedSumAsString, bigA.add(bigB).toString());
        assertEquals("b + a", expectedSumAsString, bigB.add(bigA).toString());
        assertEquals("a - (-b)", expectedSumAsString, bigA.subtract(bigMinusB).toString());
        assertEquals("b - (-a)", expectedSumAsString, bigB.subtract(bigMinusA).toString());
    }

    /**
     * Tests that Long.MIN_VALUE / -1 doesn't overflow back to Long.MIN_VALUE,
     * like it would in long arithmetic.
     */
    // https://code.google.com/p/android/issues/detail?id=196555
    public void testDivideAvoids64bitOverflow() throws Exception {
        BigDecimal minLong = new BigDecimal("-9223372036854775808");
        assertEquals("9223372036854775808/(-1)",
                new BigDecimal("9223372036854775808"),
                minLong.divide(new BigDecimal("-1"), /* scale = */ 0, RoundingMode.UNNECESSARY));

        assertEquals("922337203685477580.8/(-0.1)",
                new BigDecimal("9223372036854775808"),
                new BigDecimal("-922337203685477580.8")
                        .divide(new BigDecimal("-0.1"), /* scale = */ 0, RoundingMode.UNNECESSARY));

        assertEquals("92233720368547758080/(-1E+1)",
                new BigDecimal("9223372036854775808"),
                new BigDecimal("-92233720368547758080")
                        .divide(new BigDecimal("-1E+1"), /* scale = */ 0, RoundingMode.UNNECESSARY));

        assertEquals("9223372036854775808/(-10) with one decimal of precision",
                new BigDecimal("922337203685477580.8"),
                minLong.divide(new BigDecimal("-1E+1"), /* scale = */ 1, RoundingMode.UNNECESSARY));

        // cases that request adjustment of the result scale, i.e. (diffScale != 0)
        // i.e. result scale != (dividend.scale - divisor.scale)
        assertEquals("9223372036854775808/(-1) with one decimal of precision",//
                new BigDecimal("9223372036854775808.0"),
                minLong.divide(new BigDecimal("-1"), /* scale = */ 1, RoundingMode.UNNECESSARY));

        assertEquals("9223372036854775808/(-1.0)",//
                new BigDecimal("9223372036854775808"),
                minLong.divide(new BigDecimal("-1.0"), /* scale = */ 0, RoundingMode.UNNECESSARY));

        assertEquals("9223372036854775808/(-1.0) with one decimal of precision",//
                new BigDecimal("9223372036854775808.0"),
                minLong.divide(new BigDecimal("-1.0"), /* scale = */ 1, RoundingMode.UNNECESSARY));

        // another arbitrary calculation that results in Long.MAX_VALUE + 1
        // via a different route
        assertEquals("4611686018427387904/(-5E-1)",//
                new BigDecimal("9223372036854775808"),
                new BigDecimal("-4611686018427387904").divide(
                        new BigDecimal("-5E-1"), /* scale = */ 0, RoundingMode.UNNECESSARY));
    }

    /**
     * Tests addition, subtraction, multiplication and division involving a range of
     * even long values and 1/2 of that value.
     */
    public void testCommonOperations_halfOfEvenLongValue() {
        checkCommonOperations(0);
        checkCommonOperations(2);
        checkCommonOperations(-2);
        checkCommonOperations(Long.MIN_VALUE);
        checkCommonOperations(1L << 62L);
        checkCommonOperations(-(1L << 62L));
        checkCommonOperations(1L << 62L + 1 << 30 + 1 << 10);
        checkCommonOperations(Long.MAX_VALUE - 1);
    }

    private static void checkCommonOperations(long value) {
        if (value % 2 != 0) {
            throw new IllegalArgumentException("Expected even value, got " + value);
        }
        BigDecimal bigHalfValue = valueOf(value / 2);
        BigDecimal bigValue = valueOf(value);
        BigDecimal two = valueOf(2);

        assertEquals(bigValue, bigHalfValue.multiply(two));
        assertEquals(bigValue, bigHalfValue.add(bigHalfValue));
        assertEquals(bigHalfValue, bigValue.subtract(bigHalfValue));
        assertEquals(bigHalfValue, bigValue.divide(two, RoundingMode.UNNECESSARY));
        if (value != 0) {
            assertEquals(two, bigValue.divide(bigHalfValue, RoundingMode.UNNECESSARY));
        }
    }

    /**
     * Tests that when long multiplication doesn't overflow, its result is consistent with
     * BigDecimal multiplication.
     */
    public void testMultiply_consistentWithLong() {
        checkMultiply_consistentWithLong(0, 0);
        checkMultiply_consistentWithLong(0, 1);
        checkMultiply_consistentWithLong(1, 1);
        checkMultiply_consistentWithLong(2, 3);
        checkMultiply_consistentWithLong(123, 456);
        checkMultiply_consistentWithLong(9, 9);
        checkMultiply_consistentWithLong(34545, 3423421);
        checkMultiply_consistentWithLong(5465653, 342343234568L);
        checkMultiply_consistentWithLong(Integer.MAX_VALUE, Integer.MAX_VALUE);
        checkMultiply_consistentWithLong((1L << 40) + 454L, 34324);
    }

    private void checkMultiply_consistentWithLong(long a, long b) {
        // Guard against the test using examples that overflow. This condition here is
        // not meant to be exact, it'll reject some values that wouldn't overflow.
        if (a != 0 && b != 0 && Math.abs(Long.MAX_VALUE / a) <= Math.abs(b)) {
            throw new IllegalArgumentException("Multiplication might overflow: " + a + " * " + b);
        }
        long expectedResult = a * b;
        // check the easy case with no decimals
        assertEquals(Long.toString(expectedResult),
                valueOf(a).multiply(valueOf(b)).toString());
        // number with 2 decimals * number with 3 decimals => number with 5 decimals
        // E.g. 9E-2 * 2E-3 == 18E-5 == 0.00018
        // valueOf(unscaledValue, scale) corresponds to {@code unscaledValue * 10<sup>-scale</sup>}
        assertEquals(valueOf(expectedResult, 5), valueOf(a, 2).multiply(valueOf(b, 3)));
    }

    public void testMultiply_near64BitOverflow_scaled() {
        // -((2^31) / 100) * (-2/10) == (2^64)/1000
        assertEquals("9223372036854775.808",
                valueOf(-(1L << 62L), 2).multiply(valueOf(-2, 1)).toString());

        // -((2^31) / 100) * (2/10) == -(2^64)/1000
        assertEquals("-9223372036854775.808",
                valueOf(-(1L << 62L), 2).multiply(valueOf(2, 1)).toString());

        // -((2^31) * 100) * (-2/10) == (2^64) * 10
        assertEquals(new BigDecimal("9223372036854775808E1"),
                valueOf(-(1L << 62L), -2).multiply(valueOf(-2, 1)));
    }

    /** Tests multiplications whose result is near 2^63 (= Long.MAX_VALUE + 1). */
    public void testMultiply_near64BitOverflow_positive() {
        // Results of exactly +2^63, which doesn't fit into a long even though -2^63 does
        assertEquals("9223372036854775808", bigMultiply(Long.MIN_VALUE, -1).toString());
        assertEquals("9223372036854775808", bigMultiply(Long.MIN_VALUE / 2, -2).toString());
        assertEquals("9223372036854775808", bigMultiply(-(Long.MIN_VALUE / 2), 2).toString());
        assertEquals("9223372036854775808", bigMultiply(1L << 31, 1L << 32).toString());
        assertEquals("9223372036854775808", bigMultiply(-(1L << 31), -(1L << 32)).toString());

        // Results near but not exactly +2^63
        assertEquals("9223372036854775806", bigMultiply(2147483647, 4294967298L).toString());
        assertEquals("9223372036854775807", bigMultiply(Long.MAX_VALUE, 1).toString());
        assertEquals("9223372036854775807", bigMultiply(42128471623L, 218934409L).toString());
        assertEquals("9223372036854775809", bigMultiply(77158673929L, 119537721L).toString());
        assertEquals("9223372036854775810", bigMultiply((1L << 62L) + 1, 2).toString());
    }

    /** Tests multiplications whose result is near -2^63 (= Long.MIN_VALUE). */
    public void testMultiply_near64BitOverflow_negative() {
        assertEquals("-9223372036854775808", bigMultiply(Long.MIN_VALUE, 1).toString());
        assertEquals("-9223372036854775808", bigMultiply(Long.MIN_VALUE / 2, 2).toString());
        assertEquals("-9223372036854775808", bigMultiply(-(1L << 31), 1L << 32).toString());
        assertEquals("-9223372036854775807", bigMultiply(-42128471623L, 218934409L).toString());
        assertEquals("-9223372036854775810", bigMultiply(-(Long.MIN_VALUE / 2) + 1, -2).toString());
    }

    private static BigDecimal bigMultiply(long a, long b) {
        BigDecimal bigA = valueOf(a);
        BigDecimal bigB = valueOf(b);
        BigDecimal result = bigA.multiply(bigB);
        assertEquals("Multiplication should be commutative", result, bigB.multiply(bigA));
        return result;
    }

}
