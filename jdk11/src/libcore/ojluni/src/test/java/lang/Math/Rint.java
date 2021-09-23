/*
 * Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4101566 4831589
 * @summary Check for correct implementation of Math.rint(double)
 */
package test.java.lang.Math;

import org.testng.annotations.Test;

public class Rint {

    static void testRintCase(double input, double expected) {
        Tests.test("Math.rint", input, Math.rint(input), expected);
        Tests.test("Math.rint", -input, Math.rint(-input), -expected);
        Tests.test("StrictMath.rint",
                input, StrictMath.rint(input), expected);
        Tests.test("StrictMath.rint", -input,
                StrictMath.rint(-input), -expected);
    }

    @Test
    public void testRint() {
        double twoToThe52 = Math.scalb(1.0, 52); // 2^52

        double[][] testCases = {
                {0.0, 0.0},
                {Double.MIN_VALUE, 0.0},
                {Math.nextDown(Double.MIN_NORMAL), 0.0},
                {Double.MIN_NORMAL, 0.0},

                {0.2, 0.0},

                {Math.nextDown(0.5), 0.0},
                {0.5, 0.0},
                {Math.nextUp(0.5), 1.0},

                {0.7, 1.0},
                {Math.nextDown(1.0), 1.0},
                {1.0, 1.0},
                {Math.nextUp(1.0), 1.0},

                {Math.nextDown(1.5), 1.0},
                {1.5, 2.0},
                {Math.nextUp(1.5), 2.0},

                {4.2, 4.0},
                {4.5, 4.0},
                {4.7, 5.0},

                {7.5, 8.0},
                {7.2, 7.0},
                {7.7, 8.0},

                {150000.75, 150001.0},
                {300000.5, 300000.0},
                {Math.nextUp(300000.5), 300001.0},
                {Math.nextDown(300000.75), 300001.0},
                {300000.75, 300001.0},
                {Math.nextUp(300000.75), 300001.0},
                {300000.99, 300001.0},
                {262144.75, 262145.0}, //(2^18 ) + 0.75
                {499998.75, 499999.0},
                {524287.75, 524288.0}, //(2^19 -1) + 0.75
                {524288.75, 524289.0},

                {Math.nextDown(twoToThe52), twoToThe52},
                {twoToThe52, twoToThe52},
                {Math.nextUp(twoToThe52), Math.nextUp(twoToThe52)},

                {Double.MAX_VALUE, Double.MAX_VALUE},
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
                {Double.NaN, Double.NaN}

        };

        for (double[] testCase : testCases) {
            testRintCase(testCase[0], testCase[1]);
        }

        // Test values throughout exponent range
        for (double d = Double.MIN_VALUE;
                d < Double.POSITIVE_INFINITY; d *= 2) {
            testRintCase(d, ((d <= 0.5) ? 0.0 : d));
        }
    }
}
