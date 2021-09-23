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

/*
 * @test
 * @library /test/lib
 * @build jdk.test.lib.RandomFactory
 * @run main MultiplicationTests
 * @bug 5100935
 * @summary Tests for multiplication methods (use -Dseed=X to set PRNG seed)
 * @key randomness
 */
package test.java.lang.StrictMath;

import java.math.BigInteger;
// Android-removed: main(), randomness
// import jdk.test.lib.RandomFactory;

// Android-added: testng.
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class MultiplicationTests {

    private MultiplicationTests() {
    }

    // BEGIN Android-removed: randomness, main().
    /*
    // Number of random products to test.
    private static final int COUNT = 1 << 16;

    // Initialize shared random number generator
    private static java.util.Random rnd = RandomFactory.getRandom();
    */
    // END Android-removed: randomness, main().

    // Calculate high 64 bits of 128 product using BigInteger.
    private static long multiplyHighBigInt(long x, long y) {
        return BigInteger.valueOf(x).multiply(BigInteger.valueOf(y))
                .shiftRight(64).longValue();
    }

    // Check Math.multiplyHigh(x,y) against multiplyHighBigInt(x,y)
    private static boolean check(long x, long y) {
        long p1 = multiplyHighBigInt(x, y);
        long p2 = StrictMath.multiplyHigh(x, y);
        if (p1 != p2) {
            // Android-changed: use testng fail().
            //System.err.printf("Error - x:%d y:%d p1:%d p2:%d\n", x, y, p1, p2);
            fail(String.format("Error - x:%d y:%d p1:%d p2:%d\n", x, y, p1, p2));
            return false;
        } else {
            return true;
        }
    }

    // Android-changed: testng, return void instead of int.
    @Test
    public static void testMultiplyHigh() {
        int failures = 0;

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
                {Long.MAX_VALUE, Long.MIN_VALUE},
                {Long.MIN_VALUE, Long.MAX_VALUE},
                {Long.MIN_VALUE, Long.MIN_VALUE}
        };

        for (long[] xy : v) {
            if (!check(xy[0], xy[1])) {
                failures++;
            }
        }

        // Android-added: assert error counter.
        Assert.assertEquals(failures, 0);

        // BEGIN Android-removed: error counter, randomness, main().
        /*
        // check some random values
        for (int i = 0; i < COUNT; i++) {
            if (!check(rnd.nextLong(), rnd.nextLong())) {
                failures++;
            }
        }

        return failures;
        */
        // END Android-removed: error counter, randomness, main().
    }

    // BEGIN Android-removed: error counter, randomness, main().
    /*
    public static void main(String argv[]) {
        int failures = testMultiplyHigh();

        if (failures > 0) {
            System.err.println("Multiplication testing encountered "
                    + failures + " failures.");
            throw new RuntimeException();
        } else {
            System.out.println("MultiplicationTests succeeded");
        }
    }
    */
}