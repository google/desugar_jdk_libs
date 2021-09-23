/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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
package test.java.lang.Math;

import org.testng.annotations.Test;

/*
 * @test
 * @bug 6430675 8010430
 * @summary Check for correct implementation of {Math, StrictMath}.round
 */
public class RoundTests {

    @Test
    public void testNearDoubleHalfCases() {
        double[][] testCases = {
                {+0x1.fffffffffffffp-2, 0.0},
                {+0x1.0p-1, 1.0}, // +0.5
                {+0x1.0000000000001p-1, 1.0},

                {-0x1.fffffffffffffp-2, 0.0},
                {-0x1.0p-1, 0.0}, // -0.5
                {-0x1.0000000000001p-1, -1.0},
        };

        for (double[] testCase : testCases) {
            testNearHalfCases(testCase[0], (long) testCase[1]);
        }
    }

    private static void testNearHalfCases(double input, double expected) {
        Tests.test("Math.round", input, Math.round(input), expected);
        Tests.test("StrictMath.round", input, StrictMath.round(input), expected);
    }

    @Test
    public void testNearFloatHalfCases() {
        float[][] testCases = {
                {+0x1.fffffep-2f, 0.0f},
                {+0x1.0p-1f, 1.0f}, // +0.5
                {+0x1.000002p-1f, 1.0f},

                {-0x1.fffffep-2f, 0.0f},
                {-0x1.0p-1f, 0.0f}, // -0.5
                {-0x1.000002p-1f, -1.0f},
        };

        for (float[] testCase : testCases) {
            testNearHalfCases(testCase[0], (int) testCase[1]);
        }
    }

    private static void testNearHalfCases(float input, float expected) {
        Tests.test("Math.round", input, Math.round(input), expected);
        Tests.test("StrictMath.round", input, StrictMath.round(input), expected);
    }

    @Test
    public void testSpecialCases() {
        Tests.test("Math.round", Float.NaN, Math.round(Float.NaN), 0.0F);
        Tests.test("Math.round", Float.POSITIVE_INFINITY,
                Math.round(Float.POSITIVE_INFINITY), Integer.MAX_VALUE);
        Tests.test("Math.round", Float.NEGATIVE_INFINITY,
                Math.round(Float.NEGATIVE_INFINITY), Integer.MIN_VALUE);
        Tests.test("Math.round", -(float) Integer.MIN_VALUE,
                Math.round(-(float) Integer.MIN_VALUE), Integer.MAX_VALUE);
        Tests.test("Math.round", (float) Integer.MIN_VALUE,
                Math.round((float) Integer.MIN_VALUE), Integer.MIN_VALUE);
        Tests.test("Math.round", 0F, Math.round(0F), 0.0F);
        Tests.test("Math.round", Float.MIN_VALUE,
                Math.round(Float.MIN_VALUE), 0.0F);
        Tests.test("Math.round", -Float.MIN_VALUE,
                Math.round(-Float.MIN_VALUE), 0.0F);

        Tests.test("Math.round", Double.NaN, Math.round(Double.NaN), 0.0);
        Tests.test("Math.round", Double.POSITIVE_INFINITY,
                Math.round(Double.POSITIVE_INFINITY), Long.MAX_VALUE);
        Tests.test("Math.round", Double.NEGATIVE_INFINITY,
                Math.round(Double.NEGATIVE_INFINITY), Long.MIN_VALUE);
        Tests.test("Math.round", -(double) Long.MIN_VALUE,
                Math.round(-(double) Long.MIN_VALUE), Long.MAX_VALUE);
        Tests.test("Math.round", (double) Long.MIN_VALUE,
                Math.round((double) Long.MIN_VALUE), Long.MIN_VALUE);
        Tests.test("Math.round", 0, Math.round(0), 0.0);
        Tests.test("Math.round", Double.MIN_VALUE,
                Math.round(Double.MIN_VALUE), 0.0);
        Tests.test("Math.round", -Double.MIN_VALUE,
                Math.round(-Double.MIN_VALUE), 0.0);
    }

    @Test
    public void testUnityULPCases() {
        for (float sign : new float[]{-1, 1}) {
            for (float v1 : new float[]{1 << 23, 1 << 24}) {
                for (int k = -5; k <= 5; k++) {
                    float value = (v1 + k) * sign;
                    float actual = Math.round(value);
                    Tests.test("Math.round", value, actual, value);
                }
            }
        }

        for (double sign : new double[]{-1, 1}) {
            for (double v1 : new double[]{1L << 52, 1L << 53}) {
                for (int k = -5; k <= 5; k++) {
                    double value = (v1 + k) * sign;
                    double actual = Math.round(value);
                    Tests.test("Math.round", value, actual, value);
                }
            }
        }
    }
}
