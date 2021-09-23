/*
 * Copyright (c) 2003, 2017, Oracle and/or its affiliates. All rights reserved.
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
 * @library /test/lib
 * @build jdk.test.lib.RandomFactory
 * @run main IeeeRecommendedTests
 * @bug 4860891 4826732 4780454 4939441 4826652 8078672
 * @summary Tests for IEEE 754[R] recommended functions and similar methods (use -Dseed=X to set PRNG seed)
 * @author Joseph D. Darcy
 * @key randomness
 */
package test.java.lang.Math;

import java.util.Random;

import org.testng.annotations.Test;

public class IeeeRecommendedTests {

    private IeeeRecommendedTests() {
    }

    static final float NaNf = Float.NaN;
    static final double NaNd = Double.NaN;
    static final float infinityF = Float.POSITIVE_INFINITY;
    static final double infinityD = Double.POSITIVE_INFINITY;

    static final float Float_MAX_VALUEmm = 0x1.fffffcP+127f;
    static final float Float_MAX_SUBNORMAL = 0x0.fffffeP-126f;
    static final float Float_MAX_SUBNORMALmm = 0x0.fffffcP-126f;

    static final double Double_MAX_VALUEmm = 0x1.ffffffffffffeP+1023;
    static final double Double_MAX_SUBNORMAL = 0x0.fffffffffffffP-1022;
    static final double Double_MAX_SUBNORMALmm = 0x0.ffffffffffffeP-1022;

    // Initialize shared random number generator
    static java.util.Random rand = new Random();

    /**
     * Returns a floating-point power of two in the normal range.
     */
    static double powerOfTwoD(int n) {
        return Double.longBitsToDouble((((long) n + (long) Double.MAX_EXPONENT) <<
                (DoubleConsts.SIGNIFICAND_WIDTH - 1))
                & DoubleConsts.EXP_BIT_MASK);
    }

    /**
     * Returns a floating-point power of two in the normal range.
     */
    static float powerOfTwoF(int n) {
        return Float.intBitsToFloat(((n + Float.MAX_EXPONENT) <<
                (FloatConsts.SIGNIFICAND_WIDTH - 1))
                & FloatConsts.EXP_BIT_MASK);
    }

    /* ******************** getExponent tests ****************************** */

    /*
     * The tests for getExponent should test the special values (NaN, +/-
     * infinity, etc.), test the endpoints of each binade (set of
     * floating-point values with the same exponent), and for good
     * measure, test some random values within each binade.  Testing
     * the endpoints of each binade includes testing both positive and
     * negative numbers.  Subnormal values with different normalized
     * exponents should be tested too.  Both Math and StrictMath
     * methods should return the same results.
     */

    /*
     * Test Math.getExponent and StrictMath.getExponent with +d and -d.
     */
    static void testGetExponentCase(float f, int expected) {
        float minus_f = -f;

        Tests.test("Math.getExponent(float)", f,
                Math.getExponent(f), expected);
        Tests.test("Math.getExponent(float)", minus_f,
                Math.getExponent(minus_f), expected);

        Tests.test("StrictMath.getExponent(float)", f,
                StrictMath.getExponent(f), expected);
        Tests.test("StrictMath.getExponent(float)", minus_f,
                StrictMath.getExponent(minus_f), expected);
    }

    /*
     * Test Math.getExponent and StrictMath.getExponent with +d and -d.
     */
    static void testGetExponentCase(double d, int expected) {
        double minus_d = -d;

        Tests.test("Math.getExponent(double)", d,
                Math.getExponent(d), expected);
        Tests.test("Math.getExponent(double)", minus_d,
                Math.getExponent(minus_d), expected);

        Tests.test("StrictMath.getExponent(double)", d,
                StrictMath.getExponent(d), expected);
        Tests.test("StrictMath.getExponent(double)", minus_d,
                StrictMath.getExponent(minus_d), expected);
    }

    @Test
    public void testFloatGetExponent() {
        float[] specialValues = {NaNf,
                Float.POSITIVE_INFINITY,
                +0.0f,
                +1.0f,
                +2.0f,
                +16.0f,
                +Float.MIN_VALUE,
                +Float_MAX_SUBNORMAL,
                +Float.MIN_NORMAL,
                +Float.MAX_VALUE
        };

        int[] specialResults = {Float.MAX_EXPONENT + 1, // NaN results
                Float.MAX_EXPONENT + 1, // Infinite results
                Float.MIN_EXPONENT - 1, // Zero results
                0,
                1,
                4,
                Float.MIN_EXPONENT - 1,
                -Float.MAX_EXPONENT,
                Float.MIN_EXPONENT,
                Float.MAX_EXPONENT
        };

        // Special value tests
        for (int i = 0; i < specialValues.length; i++) {
            testGetExponentCase(specialValues[i], specialResults[i]);
        }

        // Normal exponent tests
        for (int i = Float.MIN_EXPONENT; i <= Float.MAX_EXPONENT; i++) {
            // Create power of two
            float po2 = powerOfTwoF(i);

            testGetExponentCase(po2, i);

            // Generate some random bit patterns for the significand
            for (int j = 0; j < 10; j++) {
                int randSignif = rand.nextInt();
                float randFloat;

                randFloat = Float.intBitsToFloat( // Exponent
                        (Float.floatToIntBits(po2) &
                                (~FloatConsts.SIGNIF_BIT_MASK)) |
                                // Significand
                                (randSignif &
                                        FloatConsts.SIGNIF_BIT_MASK));

                testGetExponentCase(randFloat, i);
            }

            if (i > Float.MIN_EXPONENT) {
                float po2minus = Math.nextAfter(po2, Float.NEGATIVE_INFINITY);
                testGetExponentCase(po2minus, i - 1);
            }
        }

        // Subnormal exponent tests

        /*
         * Start with MIN_VALUE, left shift, test high value, low
         * values, and random in between.
         *
         * Use nextAfter to calculate, high value of previous binade,
         * loop count i will indicate how many random bits, if any are
         * needed.
         */

        float top = Float.MIN_VALUE;
        for (int i = 1;
                i < FloatConsts.SIGNIFICAND_WIDTH;
                i++, top *= 2.0f) {

            testGetExponentCase(top, Float.MIN_EXPONENT - 1);

            // Test largest value in next smaller binade
            if (i >= 3) {// (i == 1) would test 0.0;
                // (i == 2) would just retest MIN_VALUE
                testGetExponentCase(Math.nextAfter(top, 0.0f), Float.MIN_EXPONENT - 1);

                if (i >= 10) {
                    // create a bit mask with (i-1) 1's in the low order
                    // bits
                    int mask = ~((~0) << (i - 1));
                    float randFloat = Float.intBitsToFloat( // Exponent
                            Float.floatToIntBits(top) |
                                    // Significand
                                    (rand.nextInt() & mask));

                    testGetExponentCase(randFloat, Float.MIN_EXPONENT - 1);
                }
            }
        }
    }

    @Test
    public void testDoubleGetExponent() {
        double[] specialValues = {NaNd,
                infinityD,
                +0.0,
                +1.0,
                +2.0,
                +16.0,
                +Double.MIN_VALUE,
                +Double_MAX_SUBNORMAL,
                +Double.MIN_NORMAL,
                +Double.MAX_VALUE
        };

        int[] specialResults = {Double.MAX_EXPONENT + 1, // NaN results
                Double.MAX_EXPONENT + 1, // Infinite results
                Double.MIN_EXPONENT - 1, // Zero results
                0,
                1,
                4,
                Double.MIN_EXPONENT - 1,
                -Double.MAX_EXPONENT,
                Double.MIN_EXPONENT,
                Double.MAX_EXPONENT
        };

        // Special value tests
        for (int i = 0; i < specialValues.length; i++) {
            testGetExponentCase(specialValues[i], specialResults[i]);
        }

        // Normal exponent tests
        for (int i = Double.MIN_EXPONENT; i <= Double.MAX_EXPONENT; i++) {
            // Create power of two
            double po2 = powerOfTwoD(i);

            testGetExponentCase(po2, i);

            // Generate some random bit patterns for the significand
            for (int j = 0; j < 10; j++) {
                long randSignif = rand.nextLong();
                double randFloat;

                randFloat = Double.longBitsToDouble( // Exponent
                        (Double.doubleToLongBits(po2) &
                                (~DoubleConsts.SIGNIF_BIT_MASK)) |
                                // Significand
                                (randSignif &
                                        DoubleConsts.SIGNIF_BIT_MASK));

                testGetExponentCase(randFloat, i);
            }

            if (i > Double.MIN_EXPONENT) {
                double po2minus = Math.nextAfter(po2,
                        Double.NEGATIVE_INFINITY);
                testGetExponentCase(po2minus, i - 1);
            }
        }

        // Subnormal exponent tests

        /*
         * Start with MIN_VALUE, left shift, test high value, low
         * values, and random in between.
         *
         * Use nextAfter to calculate, high value of previous binade;
         * loop count i will indicate how many random bits, if any are
         * needed.
         */

        double top = Double.MIN_VALUE;
        for (int i = 1;
                i < DoubleConsts.SIGNIFICAND_WIDTH;
                i++, top *= 2.0f) {

            testGetExponentCase(top,
                    Double.MIN_EXPONENT - 1);

            // Test largest value in next smaller binade
            if (i >= 3) {// (i == 1) would test 0.0;
                // (i == 2) would just retest MIN_VALUE
                testGetExponentCase(Math.nextAfter(top, 0.0),
                        Double.MIN_EXPONENT - 1);

                if (i >= 10) {
                    // create a bit mask with (i-1) 1's in the low order
                    // bits
                    long mask = ~((~0L) << (i - 1));
                    double randFloat = Double.longBitsToDouble( // Exponent
                            Double.doubleToLongBits(top) |
                                    // Significand
                                    (rand.nextLong() & mask));

                    testGetExponentCase(randFloat,
                            Double.MIN_EXPONENT - 1);
                }
            }
        }
    }


    /* ******************** nextAfter tests ****************************** */

    static void testNextAfterCase(float start, double direction, float expected) {
        float minus_start = -start;
        double minus_direction = -direction;
        float minus_expected = -expected;

        Tests.test("Math.nextAfter(float,double)", start, direction,
                Math.nextAfter(start, direction), expected);
        Tests.test("Math.nextAfter(float,double)", minus_start, minus_direction,
                Math.nextAfter(minus_start, minus_direction), minus_expected);

        Tests.test("StrictMath.nextAfter(float,double)", start, direction,
                StrictMath.nextAfter(start, direction), expected);
        Tests.test("StrictMath.nextAfter(float,double)", minus_start, minus_direction,
                StrictMath.nextAfter(minus_start, minus_direction), minus_expected);
    }

    static void testNextAfterCase(double start, double direction, double expected) {
        double minus_start = -start;
        double minus_direction = -direction;
        double minus_expected = -expected;

        Tests.test("Math.nextAfter(double,double)", start, direction,
                Math.nextAfter(start, direction), expected);
        Tests.test("Math.nextAfter(double,double)", minus_start, minus_direction,
                Math.nextAfter(minus_start, minus_direction), minus_expected);

        Tests.test("StrictMath.nextAfter(double,double)", start, direction,
                StrictMath.nextAfter(start, direction), expected);
        Tests.test("StrictMath.nextAfter(double,double)", minus_start, minus_direction,
                StrictMath.nextAfter(minus_start, minus_direction), minus_expected);
    }

    @Test
    public void testFloatNextAfter() {
        /*
         * Each row of the testCases matrix represents one test case
         * for nexAfter; given the input of the first two columns, the
         * result in the last column is expected.
         */
        float[][] testCases = {
                {NaNf, NaNf, NaNf},
                {NaNf, 0.0f, NaNf},
                {0.0f, NaNf, NaNf},
                {NaNf, infinityF, NaNf},
                {infinityF, NaNf, NaNf},

                {infinityF, infinityF, infinityF},
                {infinityF, -infinityF, Float.MAX_VALUE},
                {infinityF, 0.0f, Float.MAX_VALUE},

                {Float.MAX_VALUE, infinityF, infinityF},
                {Float.MAX_VALUE, -infinityF, Float_MAX_VALUEmm},
                {Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE},
                {Float.MAX_VALUE, 0.0f, Float_MAX_VALUEmm},

                {Float_MAX_VALUEmm, Float.MAX_VALUE, Float.MAX_VALUE},
                {Float_MAX_VALUEmm, infinityF, Float.MAX_VALUE},
                {Float_MAX_VALUEmm, Float_MAX_VALUEmm, Float_MAX_VALUEmm},

                {Float.MIN_NORMAL, infinityF, Float.MIN_NORMAL +
                        Float.MIN_VALUE},
                {Float.MIN_NORMAL, -infinityF, Float_MAX_SUBNORMAL},
                {Float.MIN_NORMAL, 1.0f, Float.MIN_NORMAL +
                        Float.MIN_VALUE},
                {Float.MIN_NORMAL, -1.0f, Float_MAX_SUBNORMAL},
                {Float.MIN_NORMAL, Float.MIN_NORMAL, Float.MIN_NORMAL},

                {Float_MAX_SUBNORMAL, Float.MIN_NORMAL, Float.MIN_NORMAL},
                {Float_MAX_SUBNORMAL, Float_MAX_SUBNORMAL, Float_MAX_SUBNORMAL},
                {Float_MAX_SUBNORMAL, 0.0f, Float_MAX_SUBNORMALmm},

                {Float_MAX_SUBNORMALmm, Float_MAX_SUBNORMAL, Float_MAX_SUBNORMAL},
                {Float_MAX_SUBNORMALmm, 0.0f, Float_MAX_SUBNORMALmm - Float.MIN_VALUE},
                {Float_MAX_SUBNORMALmm, Float_MAX_SUBNORMALmm, Float_MAX_SUBNORMALmm},

                {Float.MIN_VALUE, 0.0f, 0.0f},
                {-Float.MIN_VALUE, 0.0f, -0.0f},
                {Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE},
                {Float.MIN_VALUE, 1.0f, 2 * Float.MIN_VALUE},

                // Make sure zero behavior is tested
                {0.0f, 0.0f, 0.0f},
                {0.0f, -0.0f, -0.0f},
                {-0.0f, 0.0f, 0.0f},
                {-0.0f, -0.0f, -0.0f},
                {0.0f, infinityF, Float.MIN_VALUE},
                {0.0f, -infinityF, -Float.MIN_VALUE},
                {-0.0f, infinityF, Float.MIN_VALUE},
                {-0.0f, -infinityF, -Float.MIN_VALUE},
                {0.0f, Float.MIN_VALUE, Float.MIN_VALUE},
                {0.0f, -Float.MIN_VALUE, -Float.MIN_VALUE},
                {-0.0f, Float.MIN_VALUE, Float.MIN_VALUE},
                {-0.0f, -Float.MIN_VALUE, -Float.MIN_VALUE}
        };

        for (float[] testCase : testCases) {
            testNextAfterCase(testCase[0], testCase[1], testCase[2]);
        }
    }

    @Test
    public void testDoubleNextAfter() {
        /*
         * Each row of the testCases matrix represents one test case
         * for nexAfter; given the input of the first two columns, the
         * result in the last column is expected.
         */
        double[][] testCases = {
                {NaNd, NaNd, NaNd},
                {NaNd, 0.0d, NaNd},
                {0.0d, NaNd, NaNd},
                {NaNd, infinityD, NaNd},
                {infinityD, NaNd, NaNd},

                {infinityD, infinityD, infinityD},
                {infinityD, -infinityD, Double.MAX_VALUE},
                {infinityD, 0.0d, Double.MAX_VALUE},

                {Double.MAX_VALUE, infinityD, infinityD},
                {Double.MAX_VALUE, -infinityD, Double_MAX_VALUEmm},
                {Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE},
                {Double.MAX_VALUE, 0.0d, Double_MAX_VALUEmm},

                {Double_MAX_VALUEmm, Double.MAX_VALUE, Double.MAX_VALUE},
                {Double_MAX_VALUEmm, infinityD, Double.MAX_VALUE},
                {Double_MAX_VALUEmm, Double_MAX_VALUEmm, Double_MAX_VALUEmm},

                {Double.MIN_NORMAL, infinityD, Double.MIN_NORMAL +
                        Double.MIN_VALUE},
                {Double.MIN_NORMAL, -infinityD, Double_MAX_SUBNORMAL},
                {Double.MIN_NORMAL, 1.0f, Double.MIN_NORMAL +
                        Double.MIN_VALUE},
                {Double.MIN_NORMAL, -1.0f, Double_MAX_SUBNORMAL},
                {Double.MIN_NORMAL, Double.MIN_NORMAL, Double.MIN_NORMAL},

                {Double_MAX_SUBNORMAL, Double.MIN_NORMAL, Double.MIN_NORMAL},
                {Double_MAX_SUBNORMAL, Double_MAX_SUBNORMAL, Double_MAX_SUBNORMAL},
                {Double_MAX_SUBNORMAL, 0.0d, Double_MAX_SUBNORMALmm},

                {Double_MAX_SUBNORMALmm, Double_MAX_SUBNORMAL, Double_MAX_SUBNORMAL},
                {Double_MAX_SUBNORMALmm, 0.0d, Double_MAX_SUBNORMALmm - Double.MIN_VALUE},
                {Double_MAX_SUBNORMALmm, Double_MAX_SUBNORMALmm, Double_MAX_SUBNORMALmm},

                {Double.MIN_VALUE, 0.0d, 0.0d},
                {-Double.MIN_VALUE, 0.0d, -0.0d},
                {Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE},
                {Double.MIN_VALUE, 1.0f, 2 * Double.MIN_VALUE},

                // Make sure zero behavior is tested
                {0.0d, 0.0d, 0.0d},
                {0.0d, -0.0d, -0.0d},
                {-0.0d, 0.0d, 0.0d},
                {-0.0d, -0.0d, -0.0d},
                {0.0d, infinityD, Double.MIN_VALUE},
                {0.0d, -infinityD, -Double.MIN_VALUE},
                {-0.0d, infinityD, Double.MIN_VALUE},
                {-0.0d, -infinityD, -Double.MIN_VALUE},
                {0.0d, Double.MIN_VALUE, Double.MIN_VALUE},
                {0.0d, -Double.MIN_VALUE, -Double.MIN_VALUE},
                {-0.0d, Double.MIN_VALUE, Double.MIN_VALUE},
                {-0.0d, -Double.MIN_VALUE, -Double.MIN_VALUE}
        };

        for (double[] testCase : testCases) {
            testNextAfterCase(testCase[0], testCase[1], testCase[2]);
        }
    }

    /* ******************** nextUp tests ********************************* */

    @Test
    public void testFloatNextUp() {
        /*
         * Each row of testCases represents one test case for nextUp;
         * the first column is the input and the second column is the
         * expected result.
         */
        float[][] testCases = {
                {NaNf, NaNf},
                {-infinityF, -Float.MAX_VALUE},
                {-Float.MAX_VALUE, -Float_MAX_VALUEmm},
                {-Float.MIN_NORMAL, -Float_MAX_SUBNORMAL},
                {-Float_MAX_SUBNORMAL, -Float_MAX_SUBNORMALmm},
                {-Float.MIN_VALUE, -0.0f},
                {-0.0f, Float.MIN_VALUE},
                {+0.0f, Float.MIN_VALUE},
                {Float.MIN_VALUE, Float.MIN_VALUE * 2},
                {Float_MAX_SUBNORMALmm, Float_MAX_SUBNORMAL},
                {Float_MAX_SUBNORMAL, Float.MIN_NORMAL},
                {Float.MIN_NORMAL, Float.MIN_NORMAL + Float.MIN_VALUE},
                {Float_MAX_VALUEmm, Float.MAX_VALUE},
                {Float.MAX_VALUE, infinityF},
                {infinityF, infinityF}
        };

        for (float[] testCase : testCases) {
            Tests.test("Math.nextUp(float)",
                    testCase[0], Math.nextUp(testCase[0]), testCase[1]);

            Tests.test("StrictMath.nextUp(float)",
                    testCase[0], StrictMath.nextUp(testCase[0]), testCase[1]);
        }
    }

    @Test
    public void testDoubleNextUp() {
        /*
         * Each row of testCases represents one test case for nextUp;
         * the first column is the input and the second column is the
         * expected result.
         */
        double[][] testCases = {
                {NaNd, NaNd},
                {-infinityD, -Double.MAX_VALUE},
                {-Double.MAX_VALUE, -Double_MAX_VALUEmm},
                {-Double.MIN_NORMAL, -Double_MAX_SUBNORMAL},
                {-Double_MAX_SUBNORMAL, -Double_MAX_SUBNORMALmm},
                {-Double.MIN_VALUE, -0.0d},
                {-0.0d, Double.MIN_VALUE},
                {+0.0d, Double.MIN_VALUE},
                {Double.MIN_VALUE, Double.MIN_VALUE * 2},
                {Double_MAX_SUBNORMALmm, Double_MAX_SUBNORMAL},
                {Double_MAX_SUBNORMAL, Double.MIN_NORMAL},
                {Double.MIN_NORMAL, Double.MIN_NORMAL + Double.MIN_VALUE},
                {Double_MAX_VALUEmm, Double.MAX_VALUE},
                {Double.MAX_VALUE, infinityD},
                {infinityD, infinityD}
        };

        for (double[] testCase : testCases) {
            Tests.test("Math.nextUp(double)",
                    testCase[0], Math.nextUp(testCase[0]), testCase[1]);

            Tests.test("StrictMath.nextUp(double)",
                    testCase[0], StrictMath.nextUp(testCase[0]), testCase[1]);
        }
    }

    /* ******************** nextDown tests ********************************* */

    @Test
    public void testFloatNextDown() {
        /*
         * Each row of testCases represents one test case for nextDown;
         * the first column is the input and the second column is the
         * expected result.
         */
        float[][] testCases = {
                {NaNf, NaNf},
                {-infinityF, -infinityF},
                {-Float.MAX_VALUE, -infinityF},
                {-Float_MAX_VALUEmm, -Float.MAX_VALUE},
                {-Float_MAX_SUBNORMAL, -Float.MIN_NORMAL},
                {-Float_MAX_SUBNORMALmm, -Float_MAX_SUBNORMAL},
                {-0.0f, -Float.MIN_VALUE},
                {+0.0f, -Float.MIN_VALUE},
                {Float.MIN_VALUE, 0.0f},
                {Float.MIN_VALUE * 2, Float.MIN_VALUE},
                {Float_MAX_SUBNORMAL, Float_MAX_SUBNORMALmm},
                {Float.MIN_NORMAL, Float_MAX_SUBNORMAL},
                {Float.MIN_NORMAL +
                        Float.MIN_VALUE, Float.MIN_NORMAL},
                {Float.MAX_VALUE, Float_MAX_VALUEmm},
                {infinityF, Float.MAX_VALUE},
        };

        for (float[] testCase : testCases) {
            Tests.test("Math.nextDown(float)",
                    testCase[0], Math.nextDown(testCase[0]), testCase[1]);

            Tests.test("StrictMath.nextDown(float)",
                    testCase[0], StrictMath.nextDown(testCase[0]), testCase[1]);
        }
    }

    @Test
    public void testDoubleNextDown() {
        /*
         * Each row of testCases represents one test case for nextDown;
         * the first column is the input and the second column is the
         * expected result.
         */
        double[][] testCases = {
                {NaNd, NaNd},
                {-infinityD, -infinityD},
                {-Double.MAX_VALUE, -infinityD},
                {-Double_MAX_VALUEmm, -Double.MAX_VALUE},
                {-Double_MAX_SUBNORMAL, -Double.MIN_NORMAL},
                {-Double_MAX_SUBNORMALmm, -Double_MAX_SUBNORMAL},
                {-0.0d, -Double.MIN_VALUE},
                {+0.0d, -Double.MIN_VALUE},
                {Double.MIN_VALUE, 0.0d},
                {Double.MIN_VALUE * 2, Double.MIN_VALUE},
                {Double_MAX_SUBNORMAL, Double_MAX_SUBNORMALmm},
                {Double.MIN_NORMAL, Double_MAX_SUBNORMAL},
                {Double.MIN_NORMAL +
                        Double.MIN_VALUE, Double.MIN_NORMAL},
                {Double.MAX_VALUE, Double_MAX_VALUEmm},
                {infinityD, Double.MAX_VALUE},
        };

        for (double[] testCase : testCases) {
            Tests.test("Math.nextDown(double)",
                    testCase[0], Math.nextDown(testCase[0]), testCase[1]);

            Tests.test("StrictMath.nextDown(double)",
                    testCase[0], StrictMath.nextDown(testCase[0]), testCase[1]);
        }
    }


    /* ********************** boolean tests ****************************** */

    /*
     * Combined tests for boolean functions, isFinite, isInfinite,
     * isNaN, isUnordered.
     */
    @Test
    public void testFloatBooleanMethods() {
        float[] testCases = {
                NaNf,
                -infinityF,
                infinityF,
                -Float.MAX_VALUE,
                -3.0f,
                -1.0f,
                -Float.MIN_NORMAL,
                -Float_MAX_SUBNORMALmm,
                -Float_MAX_SUBNORMAL,
                -Float.MIN_VALUE,
                -0.0f,
                +0.0f,
                Float.MIN_VALUE,
                Float_MAX_SUBNORMALmm,
                Float_MAX_SUBNORMAL,
                Float.MIN_NORMAL,
                1.0f,
                3.0f,
                Float_MAX_VALUEmm,
                Float.MAX_VALUE
        };

        for (int i = 0; i < testCases.length; i++) {
            // isNaN
            Tests.test("Float.isNaN(float)", testCases[i],
                    Float.isNaN(testCases[i]), (i == 0));

            // isFinite
            Tests.test("Float.isFinite(float)", testCases[i],
                    Float.isFinite(testCases[i]), (i >= 3));

            // isInfinite
            Tests.test("Float.isInfinite(float)", testCases[i],
                    Float.isInfinite(testCases[i]), (i == 1 || i == 2));

            // isUnorderd
            for (int j = 0; j < testCases.length; j++) {
                Tests.test("Tests.isUnordered(float, float)", testCases[i], testCases[j],
                        Tests.isUnordered(testCases[i], testCases[j]), (i == 0 || j == 0));
            }
        }
    }

    @Test
    public void testDoubleBooleanMethods() {
        double[] testCases = {
                NaNd,
                -infinityD,
                infinityD,
                -Double.MAX_VALUE,
                -3.0d,
                -1.0d,
                -Double.MIN_NORMAL,
                -Double_MAX_SUBNORMALmm,
                -Double_MAX_SUBNORMAL,
                -Double.MIN_VALUE,
                -0.0d,
                +0.0d,
                Double.MIN_VALUE,
                Double_MAX_SUBNORMALmm,
                Double_MAX_SUBNORMAL,
                Double.MIN_NORMAL,
                1.0d,
                3.0d,
                Double_MAX_VALUEmm,
                Double.MAX_VALUE
        };

        for (int i = 0; i < testCases.length; i++) {
            // isNaN
            Tests.test("Double.isNaN(double)", testCases[i],
                    Double.isNaN(testCases[i]), (i == 0));

            // isFinite
            Tests.test("Double.isFinite(double)", testCases[i],
                    Double.isFinite(testCases[i]), (i >= 3));

            // isInfinite
            Tests.test("Double.isInfinite(double)", testCases[i],
                    Double.isInfinite(testCases[i]), (i == 1 || i == 2));

            // isUnorderd
            for (int j = 0; j < testCases.length; j++) {
                Tests.test("Tests.isUnordered(double, double)", testCases[i], testCases[j],
                        Tests.isUnordered(testCases[i], testCases[j]), (i == 0 || j == 0));
            }
        }
    }

    /* ******************** copySign tests******************************** */
    @Test
    public void testFloatCopySign() {
        // testCases[0] are logically positive numbers;
        // testCases[1] are negative numbers.
        float[][] testCases = {
                {+0.0f,
                        Float.MIN_VALUE,
                        Float_MAX_SUBNORMALmm,
                        Float_MAX_SUBNORMAL,
                        Float.MIN_NORMAL,
                        1.0f,
                        3.0f,
                        Float_MAX_VALUEmm,
                        Float.MAX_VALUE,
                        infinityF,
                },
                {-infinityF,
                        -Float.MAX_VALUE,
                        -3.0f,
                        -1.0f,
                        -Float.MIN_NORMAL,
                        -Float_MAX_SUBNORMALmm,
                        -Float_MAX_SUBNORMAL,
                        -Float.MIN_VALUE,
                        -0.0f}
        };

        float[] NaNs = {Float.intBitsToFloat(0x7fc00000),       // "positive" NaN
                Float.intBitsToFloat(0xFfc00000)};      // "negative" NaN

        // Tests shared between raw and non-raw versions
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int m = 0; m < testCases[i].length; m++) {
                    for (int n = 0; n < testCases[j].length; n++) {
                        // copySign(magnitude, sign)
                        Tests.test("Math.copySign(float,float)",
                                testCases[i][m], testCases[j][n],
                                Math.copySign(testCases[i][m], testCases[j][n]),
                                (j == 0 ? 1.0f : -1.0f) * Math.abs(testCases[i][m]));

                        Tests.test("StrictMath.copySign(float,float)",
                                testCases[i][m], testCases[j][n],
                                StrictMath.copySign(testCases[i][m], testCases[j][n]),
                                (j == 0 ? 1.0f : -1.0f) * Math.abs(testCases[i][m]));
                    }
                }
            }
        }

        // For rawCopySign, NaN may effectively have either sign bit
        // while for copySign NaNs are treated as if they always have
        // a zero sign bit (i.e. as positive numbers)
        for (int i = 0; i < 2; i++) {
            for (float naN : NaNs) {
                for (int m = 0; m < testCases[i].length; m++) {
                    Tests.test("StrictMath.copySign(float,float)",
                            testCases[i][m], naN,
                            StrictMath.copySign(testCases[i][m], naN),
                            Math.abs(testCases[i][m]));
                }
            }
        }
    }

    @Test
    public void testDoubleCopySign() {
        // testCases[0] are logically positive numbers;
        // testCases[1] are negative numbers.
        double[][] testCases = {
                {+0.0d,
                        Double.MIN_VALUE,
                        Double_MAX_SUBNORMALmm,
                        Double_MAX_SUBNORMAL,
                        Double.MIN_NORMAL,
                        1.0d,
                        3.0d,
                        Double_MAX_VALUEmm,
                        Double.MAX_VALUE,
                        infinityD,
                },
                {-infinityD,
                        -Double.MAX_VALUE,
                        -3.0d,
                        -1.0d,
                        -Double.MIN_NORMAL,
                        -Double_MAX_SUBNORMALmm,
                        -Double_MAX_SUBNORMAL,
                        -Double.MIN_VALUE,
                        -0.0d}
        };

        double[] NaNs = {Double.longBitsToDouble(0x7ff8000000000000L),  // "positive" NaN
                Double.longBitsToDouble(0xfff8000000000000L),  // "negative" NaN
                Double.longBitsToDouble(0x7FF0000000000001L),
                Double.longBitsToDouble(0xFFF0000000000001L),
                Double.longBitsToDouble(0x7FF8555555555555L),
                Double.longBitsToDouble(0xFFF8555555555555L),
                Double.longBitsToDouble(0x7FFFFFFFFFFFFFFFL),
                Double.longBitsToDouble(0xFFFFFFFFFFFFFFFFL),
                Double.longBitsToDouble(0x7FFDeadBeef00000L),
                Double.longBitsToDouble(0xFFFDeadBeef00000L),
                Double.longBitsToDouble(0x7FFCafeBabe00000L),
                Double.longBitsToDouble(0xFFFCafeBabe00000L)};

        // Tests shared between Math and StrictMath versions
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int m = 0; m < testCases[i].length; m++) {
                    for (int n = 0; n < testCases[j].length; n++) {
                        // copySign(magnitude, sign)
                        Tests.test("Math.copySign(double,double)",
                                testCases[i][m], testCases[j][n],
                                Math.copySign(testCases[i][m], testCases[j][n]),
                                (j == 0 ? 1.0f : -1.0f) * Math.abs(testCases[i][m]));

                        Tests.test("StrictMath.copySign(double,double)",
                                testCases[i][m], testCases[j][n],
                                StrictMath.copySign(testCases[i][m], testCases[j][n]),
                                (j == 0 ? 1.0f : -1.0f) * Math.abs(testCases[i][m]));
                    }
                }
            }
        }

        // For Math.copySign, NaN may effectively have either sign bit
        // while for StrictMath.copySign NaNs are treated as if they
        // always have a zero sign bit (i.e. as positive numbers)
        for (int i = 0; i < 2; i++) {
            for (double naN : NaNs) {
                for (int m = 0; m < testCases[i].length; m++) {
                    Tests.test("StrictMath.copySign(double,double)",
                            testCases[i][m], naN,
                            StrictMath.copySign(testCases[i][m], naN),
                            Math.abs(testCases[i][m]));
                }
            }
        }
    }

    /* ************************ scalb tests ******************************* */

    static void testScalbCase(float value, int scale_factor, float expected) {
        Tests.test("Math.scalb(float,int)",
                value, scale_factor,
                Math.scalb(value, scale_factor), expected);

        Tests.test("Math.scalb(float,int)",
                -value, scale_factor,
                Math.scalb(-value, scale_factor), -expected);

        Tests.test("StrictMath.scalb(float,int)",
                value, scale_factor,
                StrictMath.scalb(value, scale_factor), expected);

        Tests.test("StrictMath.scalb(float,int)",
                -value, scale_factor,
                StrictMath.scalb(-value, scale_factor), -expected);
    }

    @Test
    public void testFloatScalb() {
        int MAX_SCALE = Float.MAX_EXPONENT + -Float.MIN_EXPONENT +
                FloatConsts.SIGNIFICAND_WIDTH + 1;

        // Arguments x, where scalb(x,n) is x for any n.
        float[] identityTestCases = {NaNf,
                -0.0f,
                +0.0f,
                infinityF,
                -infinityF
        };

        float[] subnormalTestCases = {
                Float.MIN_VALUE,
                3.0f * Float.MIN_VALUE,
                Float_MAX_SUBNORMALmm,
                Float_MAX_SUBNORMAL
        };

        float[] someTestCases = {
                Float.MIN_VALUE,
                3.0f * Float.MIN_VALUE,
                Float_MAX_SUBNORMALmm,
                Float_MAX_SUBNORMAL,
                Float.MIN_NORMAL,
                1.0f,
                2.0f,
                3.0f,
                (float) Math.PI,
                Float_MAX_VALUEmm,
                Float.MAX_VALUE
        };

        int[] oneMultiplyScalingFactors = {
                Float.MIN_EXPONENT,
                Float.MIN_EXPONENT + 1,
                -3,
                -2,
                -1,
                0,
                1,
                2,
                3,
                Float.MAX_EXPONENT - 1,
                Float.MAX_EXPONENT
        };

        int[] manyScalingFactors = {
                Integer.MIN_VALUE,
                Integer.MIN_VALUE + 1,
                -MAX_SCALE - 1,
                -MAX_SCALE,
                -MAX_SCALE + 1,

                2 * Float.MIN_EXPONENT - 1,       // -253
                2 * Float.MIN_EXPONENT,         // -252
                2 * Float.MIN_EXPONENT + 1,       // -251

                Float.MIN_EXPONENT - FloatConsts.SIGNIFICAND_WIDTH,
                FloatConsts.MIN_SUB_EXPONENT,
                -Float.MAX_EXPONENT,          // -127
                Float.MIN_EXPONENT,           // -126

                -2,
                -1,
                0,
                1,
                2,

                Float.MAX_EXPONENT - 1,         // 126
                Float.MAX_EXPONENT,           // 127
                Float.MAX_EXPONENT + 1,         // 128

                2 * Float.MAX_EXPONENT - 1,       // 253
                2 * Float.MAX_EXPONENT,         // 254
                2 * Float.MAX_EXPONENT + 1,       // 255

                MAX_SCALE - 1,
                MAX_SCALE,
                MAX_SCALE + 1,
                Integer.MAX_VALUE - 1,
                Integer.MAX_VALUE
        };

        // Test cases where scaling is always a no-op
        for (float identityTestCase : identityTestCases) {
            for (int manyScalingFactor : manyScalingFactors) {
                testScalbCase(identityTestCase, manyScalingFactor, identityTestCase);
            }
        }

        // Test cases where result is 0.0 or infinity due to magnitude
        // of the scaling factor
        for (float someTestCase : someTestCases) {
            for (int scaleFactor : manyScalingFactors) {
                if (Math.abs(scaleFactor) >= MAX_SCALE) {
                    testScalbCase(someTestCase,
                            scaleFactor,
                            Math.copySign((scaleFactor > 0 ? infinityF : 0.0f), someTestCase));
                }
            }
        }

        // Test cases that could be done with one floating-point
        // multiply.
        for (float someTestCase : someTestCases) {
            for (int scaleFactor : oneMultiplyScalingFactors) {
                testScalbCase(someTestCase,
                        scaleFactor,
                        someTestCase * powerOfTwoF(scaleFactor));
            }
        }

        // Create 2^MAX_EXPONENT
        float twoToTheMaxExp = 1.0f; // 2^0
        for (int i = 0; i < Float.MAX_EXPONENT; i++) {
            twoToTheMaxExp *= 2.0f;
        }

        // Scale-up subnormal values until they all overflow
        for (float subnormalTestCase : subnormalTestCases) {
            float scale = 1.0f; // 2^j

            for (int scaleFactor = Float.MAX_EXPONENT * 2; scaleFactor < MAX_SCALE;
                    scaleFactor++) {// MAX_SCALE -1 should cause overflow

                testScalbCase(subnormalTestCase,
                        scaleFactor,
                        (Tests.ilogb(subnormalTestCase) + scaleFactor > Float.MAX_EXPONENT) ?
                                Math.copySign(infinityF, subnormalTestCase) : // overflow
                                // calculate right answer
                                twoToTheMaxExp * (twoToTheMaxExp * (scale * subnormalTestCase)));
                scale *= 2.0f;
            }
        }

        // Scale down a large number until it underflows.  By scaling
        // down MAX_NORMALmm, the first subnormal result will be exact
        // but the next one will round -- all those results can be
        // checked by halving a separate value in the loop.  Actually,
        // we can keep halving and checking until the product is zero
        // since:
        //
        // 1. If the scalb of MAX_VALUEmm is subnormal and *not* exact
        // it will round *up*
        //
        // 2. When rounding first occurs in the expected product, it
        // too rounds up, to 2^-MAX_EXPONENT.
        //
        // Halving expected after rounding happends to give the same
        // result as the scalb operation.
        float expected = Float_MAX_VALUEmm * 0.5f;
        for (int i = -1; i > -MAX_SCALE; i--) {
            testScalbCase(Float_MAX_VALUEmm, i, expected);

            expected *= 0.5f;
        }

        // Tricky rounding tests:
        // Scale down a large number into subnormal range such that if
        // scalb is being implemented with multiple floating-point
        // multiplies, the value would round twice if the multiplies
        // were done in the wrong order.

        float value = 0x8.0000bP-5f;
        expected = 0x1.00001p-129f;

        for (int i = 0; i < 129; i++) {
            testScalbCase(value,
                    -127 - i,
                    expected);
            value *= 2.0f;
        }
    }

    static void testScalbCase(double value, int scale_factor, double expected) {
        Tests.test("Math.scalb(double,int)",
                value, scale_factor,
                Math.scalb(value, scale_factor), expected);

        Tests.test("Math.scalb(double,int)",
                -value, scale_factor,
                Math.scalb(-value, scale_factor), -expected);

        Tests.test("StrictMath.scalb(double,int)",
                value, scale_factor,
                StrictMath.scalb(value, scale_factor), expected);

        Tests.test("StrictMath.scalb(double,int)",
                -value, scale_factor,
                StrictMath.scalb(-value, scale_factor), -expected);
    }

    @Test
    public void testDoubleScalb() {
        int MAX_SCALE = Double.MAX_EXPONENT + -Double.MIN_EXPONENT +
                DoubleConsts.SIGNIFICAND_WIDTH + 1;

        // Arguments x, where scalb(x,n) is x for any n.
        double[] identityTestCases = {NaNd,
                -0.0,
                +0.0,
                infinityD,
        };

        double[] subnormalTestCases = {
                Double.MIN_VALUE,
                3.0d * Double.MIN_VALUE,
                Double_MAX_SUBNORMALmm,
                Double_MAX_SUBNORMAL
        };

        double[] someTestCases = {
                Double.MIN_VALUE,
                3.0d * Double.MIN_VALUE,
                Double_MAX_SUBNORMALmm,
                Double_MAX_SUBNORMAL,
                Double.MIN_NORMAL,
                1.0d,
                2.0d,
                3.0d,
                Math.PI,
                Double_MAX_VALUEmm,
                Double.MAX_VALUE
        };

        int[] oneMultiplyScalingFactors = {
                Double.MIN_EXPONENT,
                Double.MIN_EXPONENT + 1,
                -3,
                -2,
                -1,
                0,
                1,
                2,
                3,
                Double.MAX_EXPONENT - 1,
                Double.MAX_EXPONENT
        };

        int[] manyScalingFactors = {
                Integer.MIN_VALUE,
                Integer.MIN_VALUE + 1,
                -MAX_SCALE - 1,
                -MAX_SCALE,
                -MAX_SCALE + 1,

                2 * Double.MIN_EXPONENT - 1,      // -2045
                2 * Double.MIN_EXPONENT,        // -2044
                2 * Double.MIN_EXPONENT + 1,      // -2043

                Double.MIN_EXPONENT,          // -1022
                Double.MIN_EXPONENT - DoubleConsts.SIGNIFICAND_WIDTH,
                DoubleConsts.MIN_SUB_EXPONENT,
                -Double.MAX_EXPONENT,         // -1023
                Double.MIN_EXPONENT,          // -1022

                -2,
                -1,
                0,
                1,
                2,

                Double.MAX_EXPONENT - 1,        // 1022
                Double.MAX_EXPONENT,          // 1023
                Double.MAX_EXPONENT + 1,        // 1024

                2 * Double.MAX_EXPONENT - 1,      // 2045
                2 * Double.MAX_EXPONENT,        // 2046
                2 * Double.MAX_EXPONENT + 1,      // 2047

                MAX_SCALE - 1,
                MAX_SCALE,
                MAX_SCALE + 1,
                Integer.MAX_VALUE - 1,
                Integer.MAX_VALUE
        };

        // Test cases where scaling is always a no-op
        for (double identityTestCase : identityTestCases) {
            for (int manyScalingFactor : manyScalingFactors) {
                testScalbCase(identityTestCase,
                        manyScalingFactor,
                        identityTestCase);
            }
        }

        // Test cases where result is 0.0 or infinity due to magnitude
        // of the scaling factor
        for (double someTestCase : someTestCases) {
            for (int scaleFactor : manyScalingFactors) {
                if (Math.abs(scaleFactor) >= MAX_SCALE) {
                    testScalbCase(someTestCase,
                            scaleFactor,
                            Math.copySign((scaleFactor > 0 ? infinityD : 0.0), someTestCase));
                }
            }
        }

        // Test cases that could be done with one floating-point
        // multiply.
        for (double someTestCase : someTestCases) {
            for (int scaleFactor : oneMultiplyScalingFactors) {
                testScalbCase(someTestCase,
                        scaleFactor,
                        someTestCase * powerOfTwoD(scaleFactor));
            }
        }

        // Create 2^MAX_EXPONENT
        double twoToTheMaxExp = 1.0; // 2^0
        for (int i = 0; i < Double.MAX_EXPONENT; i++) {
            twoToTheMaxExp *= 2.0;
        }

        // Scale-up subnormal values until they all overflow
        for (double subnormalTestCase : subnormalTestCases) {
            double scale = 1.0; // 2^j

            for (int scaleFactor = Double.MAX_EXPONENT * 2; scaleFactor < MAX_SCALE;
                    scaleFactor++) { // MAX_SCALE -1 should cause overflow

                testScalbCase(subnormalTestCase,
                        scaleFactor,
                        (Tests.ilogb(subnormalTestCase) + scaleFactor > Double.MAX_EXPONENT) ?
                                Math.copySign(infinityD, subnormalTestCase) : // overflow
                                // calculate right answer
                                twoToTheMaxExp * (twoToTheMaxExp * (scale * subnormalTestCase)));
                scale *= 2.0;
            }
        }

        // Scale down a large number until it underflows.  By scaling
        // down MAX_NORMALmm, the first subnormal result will be exact
        // but the next one will round -- all those results can be
        // checked by halving a separate value in the loop.  Actually,
        // we can keep halving and checking until the product is zero
        // since:
        //
        // 1. If the scalb of MAX_VALUEmm is subnormal and *not* exact
        // it will round *up*
        //
        // 2. When rounding first occurs in the expected product, it
        // too rounds up, to 2^-MAX_EXPONENT.
        //
        // Halving expected after rounding happends to give the same
        // result as the scalb operation.
        double expected = Double_MAX_VALUEmm * 0.5f;
        for (int i = -1; i > -MAX_SCALE; i--) {
            testScalbCase(Double_MAX_VALUEmm, i, expected);

            expected *= 0.5;
        }

        // Tricky rounding tests:
        // Scale down a large number into subnormal range such that if
        // scalb is being implemented with multiple floating-point
        // multiplies, the value would round twice if the multiplies
        // were done in the wrong order.

        double value = 0x1.000000000000bP-1;
        expected = 0x0.2000000000001P-1022;
        for (int i = 0; i < Double.MAX_EXPONENT + 2; i++) {
            testScalbCase(value,
                    -1024 - i,
                    expected);
            value *= 2.0;
        }
    }

    /* ************************* ulp tests ******************************* */


    /*
     * Test Math.ulp and StrictMath.ulp with +d and -d.
     */
    static void testUlpCase(float f, float expected) {
        float minus_f = -f;

        Tests.test("Math.ulp(float)", f,
                Math.ulp(f), expected);
        Tests.test("Math.ulp(float)", minus_f,
                Math.ulp(minus_f), expected);
        Tests.test("StrictMath.ulp(float)", f,
                StrictMath.ulp(f), expected);
        Tests.test("StrictMath.ulp(float)", minus_f,
                StrictMath.ulp(minus_f), expected);
    }

    static void testUlpCase(double d, double expected) {
        double minus_d = -d;

        Tests.test("Math.ulp(double)", d,
                Math.ulp(d), expected);
        Tests.test("Math.ulp(double)", minus_d,
                Math.ulp(minus_d), expected);
        Tests.test("StrictMath.ulp(double)", d,
                StrictMath.ulp(d), expected);
        Tests.test("StrictMath.ulp(double)", minus_d,
                StrictMath.ulp(minus_d), expected);
    }

    @Test
    public void testFloatUlp() {
        float[] specialValues = {NaNf,
                Float.POSITIVE_INFINITY,
                +0.0f,
                +1.0f,
                +2.0f,
                +16.0f,
                +Float.MIN_VALUE,
                +Float_MAX_SUBNORMAL,
                +Float.MIN_NORMAL,
                +Float.MAX_VALUE
        };

        float[] specialResults = {NaNf,
                Float.POSITIVE_INFINITY,
                Float.MIN_VALUE,
                powerOfTwoF(-23),
                powerOfTwoF(-22),
                powerOfTwoF(-19),
                Float.MIN_VALUE,
                Float.MIN_VALUE,
                Float.MIN_VALUE,
                powerOfTwoF(104)
        };

        // Special value tests
        for (int i = 0; i < specialValues.length; i++) {
            testUlpCase(specialValues[i], specialResults[i]);
        }

        // Normal exponent tests
        for (int i = Float.MIN_EXPONENT; i <= Float.MAX_EXPONENT; i++) {
            float expected;

            // Create power of two
            float po2 = powerOfTwoF(i);
            expected = Math.scalb(1.0f, i - (FloatConsts.SIGNIFICAND_WIDTH - 1));

            testUlpCase(po2, expected);

            // Generate some random bit patterns for the significand
            for (int j = 0; j < 10; j++) {
                int randSignif = rand.nextInt();
                float randFloat;

                randFloat = Float.intBitsToFloat( // Exponent
                        (Float.floatToIntBits(po2) &
                                (~FloatConsts.SIGNIF_BIT_MASK)) |
                                // Significand
                                (randSignif &
                                        FloatConsts.SIGNIF_BIT_MASK));

                testUlpCase(randFloat, expected);
            }

            if (i > Float.MIN_EXPONENT) {
                float po2minus = Math.nextAfter(po2,
                        Float.NEGATIVE_INFINITY);
                testUlpCase(po2minus, expected / 2.0f);
            }
        }

        // Subnormal tests

        /*
         * Start with MIN_VALUE, left shift, test high value, low
         * values, and random in between.
         *
         * Use nextAfter to calculate, high value of previous binade,
         * loop count i will indicate how many random bits, if any are
         * needed.
         */

        float top = Float.MIN_VALUE;
        for (int i = 1;
                i < FloatConsts.SIGNIFICAND_WIDTH;
                i++, top *= 2.0f) {

            testUlpCase(top, Float.MIN_VALUE);

            // Test largest value in next smaller binade
            if (i >= 3) {// (i == 1) would test 0.0;
                // (i == 2) would just retest MIN_VALUE
                testUlpCase(Math.nextAfter(top, 0.0f),
                        Float.MIN_VALUE);

                if (i >= 10) {
                    // create a bit mask with (i-1) 1's in the low order
                    // bits
                    int mask = ~((~0) << (i - 1));
                    float randFloat = Float.intBitsToFloat( // Exponent
                            Float.floatToIntBits(top) |
                                    // Significand
                                    (rand.nextInt() & mask));

                    testUlpCase(randFloat, Float.MIN_VALUE);
                }
            }
        }
    }

    @Test
    public void testDoubleUlp() {
        double[] specialValues = {NaNd,
                Double.POSITIVE_INFINITY,
                +0.0d,
                +1.0d,
                +2.0d,
                +16.0d,
                +Double.MIN_VALUE,
                +Double_MAX_SUBNORMAL,
                +Double.MIN_NORMAL,
                +Double.MAX_VALUE
        };

        double[] specialResults = {NaNf,
                Double.POSITIVE_INFINITY,
                Double.MIN_VALUE,
                powerOfTwoD(-52),
                powerOfTwoD(-51),
                powerOfTwoD(-48),
                Double.MIN_VALUE,
                Double.MIN_VALUE,
                Double.MIN_VALUE,
                powerOfTwoD(971)
        };

        // Special value tests
        for (int i = 0; i < specialValues.length; i++) {
            testUlpCase(specialValues[i], specialResults[i]);
        }

        // Normal exponent tests
        for (int i = Double.MIN_EXPONENT; i <= Double.MAX_EXPONENT; i++) {
            double expected;

            // Create power of two
            double po2 = powerOfTwoD(i);
            expected = Math.scalb(1.0, i - (DoubleConsts.SIGNIFICAND_WIDTH - 1));

            testUlpCase(po2, expected);

            // Generate some random bit patterns for the significand
            for (int j = 0; j < 10; j++) {
                long randSignif = rand.nextLong();
                double randDouble;

                randDouble = Double.longBitsToDouble( // Exponent
                        (Double.doubleToLongBits(po2) &
                                (~DoubleConsts.SIGNIF_BIT_MASK)) |
                                // Significand
                                (randSignif &
                                        DoubleConsts.SIGNIF_BIT_MASK));

                testUlpCase(randDouble, expected);
            }

            if (i > Double.MIN_EXPONENT) {
                double po2minus = Math.nextAfter(po2,
                        Double.NEGATIVE_INFINITY);
                testUlpCase(po2minus, expected / 2.0f);
            }
        }

        // Subnormal tests

        /*
         * Start with MIN_VALUE, left shift, test high value, low
         * values, and random in between.
         *
         * Use nextAfter to calculate, high value of previous binade,
         * loop count i will indicate how many random bits, if any are
         * needed.
         */

        double top = Double.MIN_VALUE;
        for (int i = 1;
                i < DoubleConsts.SIGNIFICAND_WIDTH;
                i++, top *= 2.0f) {

            testUlpCase(top, Double.MIN_VALUE);

            // Test largest value in next smaller binade
            if (i >= 3) {// (i == 1) would test 0.0;
                // (i == 2) would just retest MIN_VALUE
                testUlpCase(Math.nextAfter(top, 0.0f),
                        Double.MIN_VALUE);

                if (i >= 10) {
                    // create a bit mask with (i-1) 1's in the low order
                    // bits
                    int mask = ~((~0) << (i - 1));
                    double randDouble = Double.longBitsToDouble( // Exponent
                            Double.doubleToLongBits(top) |
                                    // Significand
                                    (rand.nextLong() & mask));

                    testUlpCase(randDouble, Double.MIN_VALUE);
                }
            }
        }
    }

    @Test
    public void testFloatSignum() {
        float[][] testCases = {
                {NaNf, NaNf},
                {-infinityF, -1.0f},
                {-Float.MAX_VALUE, -1.0f},
                {-Float.MIN_NORMAL, -1.0f},
                {-1.0f, -1.0f},
                {-2.0f, -1.0f},
                {-Float_MAX_SUBNORMAL, -1.0f},
                {-Float.MIN_VALUE, -1.0f},
                {-0.0f, -0.0f},
                {+0.0f, +0.0f},
                {Float.MIN_VALUE, 1.0f},
                {Float_MAX_SUBNORMALmm, 1.0f},
                {Float_MAX_SUBNORMAL, 1.0f},
                {Float.MIN_NORMAL, 1.0f},
                {1.0f, 1.0f},
                {2.0f, 1.0f},
                {Float_MAX_VALUEmm, 1.0f},
                {Float.MAX_VALUE, 1.0f},
                {infinityF, 1.0f}
        };

        for (float[] testCase : testCases) {
            Tests.test("Math.signum(float)",
                    testCase[0], Math.signum(testCase[0]), testCase[1]);
            Tests.test("StrictMath.signum(float)",
                    testCase[0], StrictMath.signum(testCase[0]), testCase[1]);
        }
    }

    @Test
    public void testDoubleSignum() {
        double[][] testCases = {
                {NaNd, NaNd},
                {-infinityD, -1.0},
                {-Double.MAX_VALUE, -1.0},
                {-Double.MIN_NORMAL, -1.0},
                {-1.0, -1.0},
                {-2.0, -1.0},
                {-Double_MAX_SUBNORMAL, -1.0},
                {-Double.MIN_VALUE, -1.0d},
                {-0.0d, -0.0d},
                {+0.0d, +0.0d},
                {Double.MIN_VALUE, 1.0},
                {Double_MAX_SUBNORMALmm, 1.0},
                {Double_MAX_SUBNORMAL, 1.0},
                {Double.MIN_NORMAL, 1.0},
                {1.0, 1.0},
                {2.0, 1.0},
                {Double_MAX_VALUEmm, 1.0},
                {Double.MAX_VALUE, 1.0},
                {infinityD, 1.0}
        };

        for (double[] testCase : testCases) {
            Tests.test("Math.signum(double)",
                    testCase[0], Math.signum(testCase[0]), testCase[1]);
            Tests.test("StrictMath.signum(double)",
                    testCase[0], StrictMath.signum(testCase[0]), testCase[1]);
        }
    }
}
