/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 5037596
 * @summary Verify bitwise conversion works for non-canonical NaN values
 * @library ../Math
 * @build DoubleConsts
 * @run main BitwiseConversion
 * @author Joseph D. Darcy
 */
package test.java.lang.Double;

import static java.lang.Double.*;

import jdk.internal.math.DoubleConsts;

import org.testng.annotations.Test;
import org.testng.Assert;

public class BitwiseConversionTest {
    static void testNanCase(long x) {
        // Strip out sign and exponent bits
        long y = x & DoubleConsts.SIGNIF_BIT_MASK;

        double[] values = {
            longBitsToDouble(DoubleConsts.EXP_BIT_MASK | y),
            longBitsToDouble(DoubleConsts.SIGN_BIT_MASK | DoubleConsts.EXP_BIT_MASK | y)
        };

        for(double value : values) {
            Assert.assertTrue(isNaN(value), "Invalid input " + y + "yielded non-NaN" + value);

            long converted = doubleToLongBits(value);
            Assert.assertEquals(converted, 0x7ff8000000000000L,
                String.format("Non-canonical NaN bits returned: %x%n", converted));
        }
    }

    @Test
    public void testNanCases() {
        for (int i = 0; i < DoubleConsts.SIGNIFICAND_WIDTH - 1; i++) {
            testNanCase(1L << i);
        }
    }

    @Test
    public void testDoubleToLongBits() {
        Assert.assertEquals(doubleToLongBits(Double.POSITIVE_INFINITY), 0x7ff0000000000000L,
            "Bad conversion for +infinity.");

        Assert.assertEquals(doubleToLongBits(Double.NEGATIVE_INFINITY), 0xfff0000000000000L,
            "Bad conversion for -infinity.");
    }
}
