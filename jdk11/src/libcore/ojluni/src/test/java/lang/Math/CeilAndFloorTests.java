/*
 * Copyright (c) 2009, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 6908131
 * @summary Check for correct implementation of Math.ceil and Math.floor
 */
package test.java.lang.Math;

import org.testng.annotations.Test;

public class CeilAndFloorTests {

    private static void testCeilCase(double input, double expected) {
        Tests.test("Math.ceil", input, Math.ceil(input), expected);
        Tests.test("StrictMath.ceil", input, StrictMath.ceil(input), expected);
    }

    private static void testFloorCase(double input, double expected) {
        Tests.test("Math.floor", input, Math.floor(input), expected);
        Tests.test("StrictMath.floor", input, StrictMath.floor(input), expected);
    }

    @Test
    private void nearIntegerTests() {
        double[] fixedPoints = {
                -0.0,
                0.0,
                -1.0,
                1.0,
                -0x1.0p52,
                0x1.0p52,
                -Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY,
                Double.NaN,
        };

        for (double fixedPoint : fixedPoints) {
            testCeilCase(fixedPoint, fixedPoint);
            testFloorCase(fixedPoint, fixedPoint);
        }

        for (int i = Double.MIN_EXPONENT; i <= Double.MAX_EXPONENT; i++) {
            double powerOfTwo = Math.scalb(1.0, i);
            double neighborDown = Math.nextDown(powerOfTwo);
            double neighborUp = Math.nextUp(powerOfTwo);

            if (i < 0) {
                testCeilCase(powerOfTwo, 1.0);
                testCeilCase(-powerOfTwo, -0.0);

                testFloorCase(powerOfTwo, 0.0);
                testFloorCase(-powerOfTwo, -1.0);

                testCeilCase(neighborDown, 1.0);
                testCeilCase(-neighborDown, -0.0);

                testFloorCase(neighborUp, 0.0);
                testFloorCase(-neighborUp, -1.0);
            } else {
                testCeilCase(powerOfTwo, powerOfTwo);
                testFloorCase(powerOfTwo, powerOfTwo);

                if (neighborDown == Math.rint(neighborDown)) {
                    testCeilCase(neighborDown, neighborDown);
                    testCeilCase(-neighborDown, -neighborDown);

                    testFloorCase(neighborDown, neighborDown);
                    testFloorCase(-neighborDown, -neighborDown);
                } else {
                    testCeilCase(neighborDown, powerOfTwo);
                    testFloorCase(-neighborDown, -powerOfTwo);
                }

                if (neighborUp == Math.rint(neighborUp)) {
                    testCeilCase(neighborUp, neighborUp);
                    testCeilCase(-neighborUp, -neighborUp);

                    testFloorCase(neighborUp, neighborUp);
                    testFloorCase(-neighborUp, -neighborUp);
                } else {
                    testFloorCase(neighborUp, powerOfTwo);
                    testCeilCase(-neighborUp, -powerOfTwo);
                }
            }
        }

        for (int i = -(0x10000); i <= 0x10000; i++) {
            double d = (double) i;
            double neighborDown = Math.nextDown(d);
            double neighborUp = Math.nextUp(d);

            testCeilCase(d, d);
            testCeilCase(-d, -d);

            testFloorCase(d, d);
            testFloorCase(-d, -d);

            if (Math.abs(d) > 1.0) {
                testCeilCase(neighborDown, d);
                testCeilCase(-neighborDown, -d + 1);

                testFloorCase(neighborUp, d);
                testFloorCase(-neighborUp, -d - 1);
            }
        }
    }

    @Test
    public void roundingTests() {
        double[][] testCases = {
                {Double.MIN_VALUE, 1.0},
                {-Double.MIN_VALUE, -0.0},
                {Math.nextDown(Double.MIN_NORMAL), 1.0},
                {-Math.nextDown(Double.MIN_NORMAL), -0.0},
                {Double.MIN_NORMAL, 1.0},
                {-Double.MIN_NORMAL, -0.0},

                {0.1, 1.0},
                {-0.1, -0.0},

                {0.5, 1.0},
                {-0.5, -0.0},

                {1.5, 2.0},
                {-1.5, -1.0},

                {2.5, 3.0},
                {-2.5, -2.0},

                {Math.nextDown(1.0), 1.0},
                {Math.nextDown(-1.0), -1.0},

                {Math.nextUp(1.0), 2.0},
                {Math.nextUp(-1.0), -0.0},

                {0x1.0p51, 0x1.0p51},
                {-0x1.0p51, -0x1.0p51},

                {Math.nextDown(0x1.0p51), 0x1.0p51},
                {-Math.nextUp(0x1.0p51), -0x1.0p51},

                {Math.nextUp(0x1.0p51), 0x1.0p51 + 1},
                {-Math.nextDown(0x1.0p51), -0x1.0p51 + 1},

                {Math.nextDown(0x1.0p52), 0x1.0p52},
                {-Math.nextUp(0x1.0p52), -0x1.0p52 - 1.0},

                {Math.nextUp(0x1.0p52), 0x1.0p52 + 1.0},
                {-Math.nextDown(0x1.0p52), -0x1.0p52 + 1.0},
        };

        for (double[] testCase : testCases) {
            testCeilCase(testCase[0], testCase[1]);
            testFloorCase(-testCase[0], -testCase[1]);
        }
    }
}
