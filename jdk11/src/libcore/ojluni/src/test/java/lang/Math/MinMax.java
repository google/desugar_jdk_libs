/*
 * Copyright (c) 1997, Oracle and/or its affiliates. All rights reserved.
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

/* @test
   @bug 4010528 4010529
   @summary Math.min and Math.max should treat negative zero as strictly
            less than positive zero
 */
package test.java.lang.Math;

import org.testng.annotations.Test;
import org.testng.Assert;

public class MinMax {

    static void test(String what, float result, float correctResult) {
        final String resultString = Float.toString(result);
        final String correctString = Float.toString(correctResult);
        final String message = what + ": got " + result + ", expected " + correctResult;
        Assert.assertEquals(resultString, correctString, message);
    }

    static void test(String what, double result, double correctResult) {
        final String resultString = Double.toString(result);
        final String correctString = Double.toString(correctResult);
        final String message = what + ": got " + result + ", expected " + correctResult;
        Assert.assertEquals(resultString, correctString, message);
    }

    @Test
    public void testMinMax() {
        float fnz = -0.0f;
        float fpz = +0.0f;

        test("Math.min(fnz, fnz)", Math.min(fnz, fnz), fnz);
        test("Math.min(fnz, fpz)", Math.min(fnz, fpz), fnz);
        test("Math.min(fpz, fnz)", Math.min(fpz, fnz), fnz);
        test("Math.min(fpz, fpz)", Math.min(fpz, fpz), fpz);

        test("Math.min(-1.0f, fnz)", Math.min(-1.0f, fnz), -1.0f);
        test("Math.min(-1.0f, fpz)", Math.min(-1.0f, fpz), -1.0f);
        test("Math.min(+1.0f, fnz)", Math.min(+1.0f, fnz), fnz);
        test("Math.min(+1.0f, fpz)", Math.min(+1.0f, fpz), fpz);
        test("Math.min(-1.0f, +1.0f)", Math.min(-1.0f, +1.0f), -1.0f);
        test("Math.min(fnz, -1.0f)", Math.min(fnz, -1.0f), -1.0f);
        test("Math.min(fpz, -1.0f)", Math.min(fpz, -1.0f), -1.0f);
        test("Math.min(fnz, +1.0f)", Math.min(fnz, +1.0f), fnz);
        test("Math.min(fpz, +1.0f)", Math.min(fpz, +1.0f), fpz);
        test("Math.min(+1.0f, -1.0f)", Math.min(+1.0f, -1.0f), -1.0f);

        test("Math.max(fnz, fnz)", Math.max(fnz, fnz), fnz);
        test("Math.max(fnz, fpz)", Math.max(fnz, fpz), fpz);
        test("Math.max(fpz, fnz)", Math.max(fpz, fnz), fpz);
        test("Math.max(fpz, fpz)", Math.max(fpz, fpz), fpz);

        test("Math.max(-1.0f, fnz)", Math.max(-1.0f, fnz), fnz);
        test("Math.max(-1.0f, fpz)", Math.max(-1.0f, fpz), fpz);
        test("Math.max(+1.0f, fnz)", Math.max(+1.0f, fnz), +1.0f);
        test("Math.max(+1.0f, fpz)", Math.max(+1.0f, fpz), +1.0f);
        test("Math.max(-1.0f, +1.0f)", Math.max(-1.0f, +1.0f), +1.0f);
        test("Math.max(fnz, -1.0f)", Math.max(fnz, -1.0f), fnz);
        test("Math.max(fpz, -1.0f)", Math.max(fpz, -1.0f), fpz);
        test("Math.max(fnz, +1.0f)", Math.max(fnz, +1.0f), +1.0f);
        test("Math.max(fpz, +1.0f)", Math.max(fpz, +1.0f), +1.0f);
        test("Math.max(+1.0f, -1.0f)", Math.max(+1.0f, -1.0f), +1.0f);

        double dnz = -0.0d;
        double dpz = +0.0d;

        test("Math.min(dnz, dnz)", Math.min(dnz, dnz), dnz);
        test("Math.min(dnz, dpz)", Math.min(dnz, dpz), dnz);
        test("Math.min(dpz, dnz)", Math.min(dpz, dnz), dnz);
        test("Math.min(dpz, dpz)", Math.min(dpz, dpz), dpz);

        test("Math.min(-1.0d, dnz)", Math.min(-1.0d, dnz), -1.0d);
        test("Math.min(-1.0d, dpz)", Math.min(-1.0d, dpz), -1.0d);
        test("Math.min(+1.0d, dnz)", Math.min(+1.0d, dnz), dnz);
        test("Math.min(+1.0d, dpz)", Math.min(+1.0d, dpz), dpz);
        test("Math.min(-1.0d, +1.0d)", Math.min(-1.0d, +1.0d), -1.0d);
        test("Math.min(dnz, -1.0d)", Math.min(dnz, -1.0d), -1.0d);
        test("Math.min(dpz, -1.0d)", Math.min(dpz, -1.0d), -1.0d);
        test("Math.min(dnz, +1.0d)", Math.min(dnz, +1.0d), dnz);
        test("Math.min(dpz, +1.0d)", Math.min(dpz, +1.0d), dpz);
        test("Math.min(+1.0d, -1.0d)", Math.min(+1.0d, -1.0d), -1.0d);

        test("Math.max(dnz, dnz)", Math.max(dnz, dnz), dnz);
        test("Math.max(dnz, dpz)", Math.max(dnz, dpz), dpz);
        test("Math.max(dpz, dnz)", Math.max(dpz, dnz), dpz);
        test("Math.max(dpz, dpz)", Math.max(dpz, dpz), dpz);

        test("Math.max(-1.0d, dnz)", Math.max(-1.0d, dnz), dnz);
        test("Math.max(-1.0d, dpz)", Math.max(-1.0d, dpz), dpz);
        test("Math.max(+1.0d, dnz)", Math.max(+1.0d, dnz), +1.0d);
        test("Math.max(+1.0d, dpz)", Math.max(+1.0d, dpz), +1.0d);
        test("Math.max(-1.0d, +1.0d)", Math.max(-1.0d, +1.0d), +1.0d);
        test("Math.max(dnz, -1.0d)", Math.max(dnz, -1.0d), dnz);
        test("Math.max(dpz, -1.0d)", Math.max(dpz, -1.0d), dpz);
        test("Math.max(dnz, +1.0d)", Math.max(dnz, +1.0d), +1.0d);
        test("Math.max(dpz, +1.0d)", Math.max(dpz, +1.0d), +1.0d);
        test("Math.max(+1.0d, -1.0d)", Math.max(+1.0d, -1.0d), +1.0d);

    }

}
