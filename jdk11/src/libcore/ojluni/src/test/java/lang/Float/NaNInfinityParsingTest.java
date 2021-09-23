/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4428772
 * @summary Testing recognition of "NaN" and "Infinity" strings
 * @author Joseph D. Darcy
 */
package test.java.lang.Float;

import org.testng.annotations.Test;
import org.testng.Assert;

public class NaNInfinityParsingTest {
    /*
     * Regression tests for:
     * 4428772 -- Establish invariant for Float & Double classes and
     * their string representations
     *
     * Added capability for parse{Float, Double} and related methods
     * to recognize "NaN" and "Infinity" strings so that
     * parseFloat(toString(d)) will always return the original
     * floating-point value.
     */

    static String NaNStrings[] = {
        "NaN",
        "+NaN",
        "-NaN"
    };

    static String infinityStrings[] = {
        "Infinity",
        "+Infinity",
        "-Infinity",
    };

    static String invalidStrings[] = {
        "+",
        "-",
        "@",
        "N",
        "Na",
        "Nan",
        "NaNf",
        "NaNd",
        "NaNF",
        "NaND",
        "+N",
        "+Na",
        "+Nan",
        "+NaNf",
        "+NaNd",
        "+NaNF",
        "+NaND",
        "-N",
        "-Na",
        "-Nan",
        "-NaNf",
        "-NaNd",
        "-NaNF",
        "-NaND",
        "I",
        "In",
        "Inf",
        "Infi",
        "Infin",
        "Infini",
        "Infinit",
        "InfinitY",
        "Infinityf",
        "InfinityF",
        "Infinityd",
        "InfinityD",
        "+I",
        "+In",
        "+Inf",
        "+Infi",
        "+Infin",
        "+Infini",
        "+Infinit",
        "+InfinitY",
        "+Infinityf",
        "+InfinityF",
        "+Infinityd",
        "+InfinityD",
        "-I",
        "-In",
        "-Inf",
        "-Infi",
        "-Infin",
        "-Infini",
        "-Infinit",
        "-InfinitY",
        "-Infinityf",
        "-InfinityF",
        "-Infinityd",
        "-InfinityD",
        "NaNInfinity",
        "InfinityNaN",
        "nan",
        "infinity"
    };

    public static void main(String [] argv) throws Exception {
        int i;
        float d;
    }

    @Test
    public void testValidNaNStrings() {
        for (String naNString : NaNStrings) {
            float d = Float.parseFloat(naNString);
            Assert.assertTrue(Float.isNaN(d), "NaN string ``" + naNString
                    + "'' did not parse as a NaN; returned " +
                    d + " instead.");
        }
    }

    @Test
    public void testValidInfinityStrings() {
        for (String infinityString : infinityStrings) {
            float d = Float.parseFloat(infinityString);
            Assert.assertTrue(Float.isInfinite(d), "Infinity string ``" +
                infinityString +
                "'' did not parse as infinity; returned " +
                d + "instead.");
            // check sign of result

            boolean negative = (infinityString.charAt(0) == '-');
            Assert.assertEquals(d, (negative ? Float.NEGATIVE_INFINITY :
                Float.POSITIVE_INFINITY), "Infinity has wrong sign;" +
                    (negative ? "positive instead of negative." :
                        "negative instead of positive."));
        }
    }

    @Test
    public void testAlmostValidStrings() {
        for (String invalidString : invalidStrings) {
            try {
                float d = Float.parseFloat(invalidString);
                Assert.fail("Invalid string ``" + invalidString + "'' parsed as " + d + ".");
            } catch (NumberFormatException e) {
                // expected
            }
        }
    }
}
