/*
 * Copyright (c) 2015, 2017, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 8139688
 * @key randomness
 * @library /test/lib
 * @build jdk.test.lib.RandomFactory
 * @build Tests
 * @build FdlibmTranslit
 * @build ExpTests
 * @run main ExpTests
 * @summary Tests specifically for StrictMath.exp
 */
package test.java.lang.StrictMath;

import java.util.Random;

import org.testng.annotations.Test;

/**
 * The role of this test is to verify that the FDLIBM exp algorithm is being used by running golden
 * file style tests on values that may vary from one conforming exponential implementation to
 * another.
 */

public class ExpTests {

    private ExpTests() {
    }

    // From the fdlibm source, the overflow threshold in hex is:
    // 0x4086_2E42_FEFA_39EF.
    static final double EXP_OVERFLOW_THRESH = Double.longBitsToDouble(0x4086_2E42_FEFA_39EFL);

    // From the fdlibm source, the underflow threshold in hex is:
    // 0xc087_4910_D52D_3051L.
    static final double EXP_UNDERFLOW_THRESH = Double.longBitsToDouble(0xc087_4910_D52D_3051L);

    @Test
    public void testExp() {
        double[][] testCases = {
                // Some of these could be moved to common Math/StrictMath exp testing.
                {Double.NaN, Double.NaN},
                {Double.MAX_VALUE, Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
                {Double.NEGATIVE_INFINITY, +0.0},
                {EXP_OVERFLOW_THRESH, 0x1.ffff_ffff_fff2ap1023},
                {Math.nextUp(EXP_OVERFLOW_THRESH), Double.POSITIVE_INFINITY},
                {Math.nextDown(EXP_UNDERFLOW_THRESH), +0.0},
                {EXP_UNDERFLOW_THRESH, +Double.MIN_VALUE},
        };

        for (double[] testCase : testCases) {
            testExpCase(testCase[0], testCase[1]);
        }
    }

    static void testExpCase(double input, double expected) {
        Tests.test("StrictMath.exp(double)", input,
                StrictMath.exp(input), expected);
    }

    // Initialize shared random number generator
    private static final java.util.Random random = new Random();

    /**
     * Test StrictMath.exp against transliteration port of exp.
     */
    @Test
    public void testAgainstTranslit() {
        double[] decisionPoints = {
                // Near overflow threshold
                EXP_OVERFLOW_THRESH - 512 * Math.ulp(EXP_OVERFLOW_THRESH),

                // Near underflow threshold
                EXP_UNDERFLOW_THRESH - 512 * Math.ulp(EXP_UNDERFLOW_THRESH),

                // Straddle algorithm conditional checks
                Double.longBitsToDouble(0x4086_2E42_0000_0000L - 512L),
                Double.longBitsToDouble(0x3fd6_2e42_0000_0000L - 512L),
                Double.longBitsToDouble(0x3FF0_A2B2_0000_0000L - 512L),
                Double.longBitsToDouble(0x3e30_0000_0000_0000L - 512L),

                // Other notable points
                Double.MIN_NORMAL - Math.ulp(Double.MIN_NORMAL) * 512,
                -Double.MIN_VALUE * 512,
        };

        for (double decisionPoint : decisionPoints) {
            double ulp = Math.ulp(decisionPoint);
            testRange(decisionPoint - 1024 * ulp, ulp, 1_024);
        }

        // Try out some random values
        for (int i = 0; i < 100; i++) {
            double x = Tests.createRandomDouble(random);
            testRange(x, Math.ulp(x), 100);
        }
    }

    private static void testRange(double start, double increment, int count) {
        double x = start;
        for (int i = 0; i < count; i++, x += increment) {
            testExpCase(x, FdlibmTranslit.Exp.compute(x));
        }
    }
}
