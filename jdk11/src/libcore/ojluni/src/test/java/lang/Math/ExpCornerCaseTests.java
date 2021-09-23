/*
 * Copyright (c) 2011,2020, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 8255368
 * @summary Tests corner cases of Math.exp
 */
package test.java.lang.Math;

import org.testng.annotations.Test;
import org.testng.Assert;

public class ExpCornerCaseTests {

    private ExpCornerCaseTests() {
    }

    @Test
    public void testExpCornerCases() {
        double[][] testCases = {
                {+0x4.0p8, Double.POSITIVE_INFINITY},
                {+0x2.71p12, Double.POSITIVE_INFINITY},
        };

        for (double[] testCase : testCases) {
            testExp(testCase[0], testCase[1]);
        }
    }

    private static void testExp(double input, double expected) {
        Tests.test("StrictMath.exp", input, StrictMath.exp(input), expected);
        Tests.test("Math.exp", input, Math.exp(input), expected);
    }
}
