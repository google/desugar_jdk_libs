/*
 * Copyright (c) 2016, 2017, Oracle and/or its affiliates. All rights reserved.
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

import java.math.BigInteger;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

// Android-removed: Private constructor, main() method, randomness tests.
public class MultiplicationTests {

    // Calculate high 64 bits of 128 product using BigInteger.
    private static long multiplyHighBigInt(long x, long y) {
        return BigInteger.valueOf(x).multiply(BigInteger.valueOf(y))
                .shiftRight(64).longValue();
    }

    @Test
    public void testMultiplyHigh() {
        // check some boundary cases
        long[][] v = new long[][]{
                {0L, 0L},
                {-1L, 0L},
                {0L, -1L},
                {1L, 0L},
                {0L, 1L},
                {-1L, -1L},
                {-1L, 1L},
                {1L, -1L},
                {1L, 1L},
                {Long.MAX_VALUE, Long.MAX_VALUE},
                {Long.MAX_VALUE, -Long.MAX_VALUE},
                {-Long.MAX_VALUE, Long.MAX_VALUE},
                {-Long.MAX_VALUE, -Long.MAX_VALUE},
                {Long.MAX_VALUE, Long.MIN_VALUE},
                {Long.MIN_VALUE, Long.MAX_VALUE},
                {Long.MIN_VALUE, Long.MIN_VALUE}
        };

        for (long[] xy : v) {
            long x = xy[0];
            long y = xy[1];
            long p1 = multiplyHighBigInt(x, y);
            long p2 = Math.multiplyHigh(x, y);
            assertEquals(p1, p2);
        }
    }

}