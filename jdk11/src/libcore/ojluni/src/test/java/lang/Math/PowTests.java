/*
 * Copyright (c) 2004, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 4984407 5033578 8134795
 * @summary Tests for {Math, StrictMath}.pow
 * @author Joseph D. Darcy
 */
package test.java.lang.Math;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PowTests {

    private PowTests() {
    }

    static final double infinityD = Double.POSITIVE_INFINITY;

    static void testPowCase(double input1, double input2, double expected) {
        Tests.test("StrictMath.pow(double, double)", input1, input2,
                StrictMath.pow(input1, input2), expected);

        // BEGIN Android-added: Document that the results from Math are based on libm's behavior.
        // The cases known to differ with libm's pow():
        //   If the first argument is 1.0 then result is always 1.0 (not NaN).
        //   If the first argument is -1.0 and the second argument is infinite, the result is 1.0 (not
        //   NaN).
        // Tests.test("Math.pow(double, double)", input1, input2,
        //         Math.pow(input1, input2), expected);
        double strictMathExpected = expected;
        if (input1 == 1.0) {
            strictMathExpected = 1.0;
        } else if (input1 == -1.0 && Double.isInfinite(input2)) {
            strictMathExpected = 1.0;
        }
        Tests.test("Math.pow(double, double)", input1, input2,
                Math.pow(input1, input2), strictMathExpected);
        // END Android-added: Document that the results from Math are based on libm's behavior.
    }


    static void testStrictPowCase(double input1, double input2, double expected) {
        Tests.test("StrictMath.pow(double, double)", input1, input2,
                StrictMath.pow(input1, input2), expected);
    }

    static void testNonstrictPowCase(double input1, double input2, double expected) {
        Tests.test("Math.pow(double, double)", input1, input2,
                Math.pow(input1, input2), expected);
    }

    /*
     * Test for bad negation implementation.
     */
    @Test
    public void testPow() {
        double[][] testCases = {
                {-0.0, 3.0, -0.0},
                {-0.0, 4.0, 0.0},
                {-infinityD, -3.0, -0.0},
                {-infinityD, -4.0, 0.0},
        };

        for (double[] testCase : testCases) {
            testPowCase(testCase[0], testCase[1], testCase[2]);
        }
    }

    /*
     * Test cross-product of different kinds of arguments.
     */
    @Test
    public void testCrossProduct() {
        double[] testData = {
                Double.NEGATIVE_INFINITY,
/* > -oo */                     -Double.MAX_VALUE,
/**/                            (double) Long.MIN_VALUE,
/**/                            (double) -((1L << 53) + 2L),
                                -0x1.0p65,
                                -0x1.0000000000001p64,
                                -0x1.0p64,
/**/                            (double) -((1L << 53)),
/**/                            (double) -((1L << 53) - 1L),
/**/                            -((double) Integer.MAX_VALUE + 4.0),
/**/                            (double) Integer.MIN_VALUE - 1.0,
/**/                            (double) Integer.MIN_VALUE,
/**/                            (double) Integer.MIN_VALUE + 1.0,
                                -0x1.0p31 + 2.0,
                                -0x1.0p31 + 1.0,
                                -0x1.0000000000001p31,
                                -0x1.0p31,
/**/                            -Math.PI,
/**/                            -3.0,
/**/                            -Math.E,
/**/                            -2.0,
/**/                            -1.0000000000000004,
/* < -1.0 */                    -1.0000000000000002, // nextAfter(-1.0, -oo)
                                -1.0,
/* > -1.0 */                    -0.9999999999999999, // nextAfter(-1.0, +oo)
/* > -1.0 */                    -0.9999999999999998,
                                -0x1.fffffp-1,
                                -0x1.ffffeffffffffp-1,
/**/                            -0.5,
/**/                            -1.0 / 3.0,
/* < 0.0 */                     -Double.MIN_VALUE,
                                -0.0,
                                +0.0,
/* > 0.0 */                     +Double.MIN_VALUE,
/**/                            +1.0 / 3.0,
/**/                            +0.5,
                                +0x1.ffffeffffffffp-1,
                                +0x1.fffffp-1,
/**/                            +0.9999999999999998,
/* < +1.0 */                    +0.9999999999999999, // nextAfter(-1.0, +oo)
                                +1.0,
/* > 1.0 */                     +1.0000000000000002, // nextAfter(+1.0, +oo)
/**/                            +1.0000000000000004,
/**/                            +2.0,
/**/                            +Math.E,
/**/                            +3.0,
/**/                            +Math.PI,
                                0x1.0p31,
                                0x1.0000000000001p31,
                                0x1.0p31 + 1.0,
                                0x1.0p31 + 2.0,
/**/                            -(double) Integer.MIN_VALUE - 1.0,
/**/                            -(double) Integer.MIN_VALUE,
/**/                            -(double) Integer.MIN_VALUE + 1.0,
/**/                            (double) Integer.MAX_VALUE + 4.0,
/**/                            (double) ((1L << 53) - 1L),
/**/                            (double) ((1L << 53)),
/**/                            (double) ((1L << 53) + 2L),
                                0x1.0p64,
                                0x1.0000000000001p64,
                                0x1.0p65,
/**/                            -(double) Long.MIN_VALUE,
/* < oo */                      Double.MAX_VALUE,
                                Double.POSITIVE_INFINITY,
                                Double.NaN
        };

        double NaN = Double.NaN;
        for (double x : testData) {
            for (double y : testData) {
                double expected = NaN;
                double actual;

                // First, switch on y
                if (Double.isNaN(y)) {
                    expected = NaN;
                } else if (y == 0.0) {
                    expected = 1.0;
                } else if (Double.isInfinite(y)) {
                    if (y > 0) { // x ^ (+oo)
                        if (Math.abs(x) > 1.0) {
                            expected = Double.POSITIVE_INFINITY;
                        } else if (Math.abs(x) == 1.0) {
                            expected = NaN;
                        } else if (Math.abs(x) < 1.0) {
                            expected = +0.0;
                        } else { // x is NaN
                            assert Double.isNaN(x);
                            expected = NaN;
                        }
                    } else { // x ^ (-oo)
                        if (Math.abs(x) > 1.0) {
                            expected = +0.0;
                        } else if (Math.abs(x) == 1.0) {
                            expected = NaN;
                        } else if (Math.abs(x) < 1.0) {
                            expected = Double.POSITIVE_INFINITY;
                        } else { // x is NaN
                            assert Double.isNaN(x);
                            expected = NaN;
                        }
                    } /* end Double.isInfinite(y) */
                } else if (y == 1.0) {
                    expected = x;
                } else if (Double.isNaN(x)) { // Now start switching on x
                    Assert.assertNotEquals(y, 0.0);
                    expected = NaN;
                } else if (x == Double.NEGATIVE_INFINITY) {
                    expected = (y < 0.0) ? f2(y) : f1(y);
                } else if (x == Double.POSITIVE_INFINITY) {
                    expected = (y < 0.0) ? +0.0 : Double.POSITIVE_INFINITY;
                } else if (equivalent(x, +0.0)) {
                    Assert.assertNotEquals(y, 0.0);
                    expected = (y < 0.0) ? Double.POSITIVE_INFINITY : +0.0;
                } else if (equivalent(x, -0.0)) {
                    Assert.assertNotEquals(y, 0.0);
                    expected = (y < 0.0) ? f1(y) : f2(y);
                } else if (x < 0.0) {
                    Assert.assertNotEquals(y, 0.0);
                    testStrictPowCase(x, y, f3(x, y));
                    testNonstrictPowCase(x, y, f3ns(x, y));
                    continue;
                } else {
                    // go to next iteration
                    expected = NaN;
                    continue;
                }

                testPowCase(x, y, expected);
            } // y
        } // x
    }

    static boolean equivalent(double a, double b) {
        return Double.compare(a, b) == 0;
    }

    static double f1(double y) {
        return (intClassify(y) == 1) ?
                Double.NEGATIVE_INFINITY :
                Double.POSITIVE_INFINITY;
    }

    static double f2(double y) {
        return (intClassify(y) == 1) ? -0.0 : 0.0;
    }

    static double f3(double x, double y) {
        switch (intClassify(y)) {
            case 0:
                return StrictMath.pow(Math.abs(x), y);
            // break;

            case 1:
                return -StrictMath.pow(Math.abs(x), y);
            // break;

            case -1:
                return Double.NaN;
            // break;

            default:
                throw new AssertionError("Bad classification.");
                // break;
        }
    }

    static double f3ns(double x, double y) {
        switch (intClassify(y)) {
            case 0:
                return Math.pow(Math.abs(x), y);
            // break;

            case 1:
                return -Math.pow(Math.abs(x), y);
            // break;

            case -1:
                return Double.NaN;
            // break;

            default:
                throw new AssertionError("Bad classification.");
                // break;
        }
    }

    static boolean isFinite(double a) {
        return (0.0 * a == 0);
    }

    /**
     * Return classification of argument: -1 for non-integers, 0 for even integers, 1 for odd
     * integers.
     */
    static int intClassify(double a) {
        if (!isFinite(a) || // NaNs and infinities
                (a != Math.floor(a))) { // only integers are fixed-points of floor
            return -1;
        } else {
            // Determine if argument is an odd or even integer.

            a = StrictMath.abs(a); // absolute value doesn't affect odd/even

            if (a + 1.0 == a) { // a > maximum odd floating-point integer
                return 0; // Large integers are all even
            } else { // Convert double -> long and look at low-order bit
                long ell = (long) a;
                return ((ell & 0x1L) == (long) 1) ? 1 : 0;
            }
        }
    }
}
