/*
 * Copyright (c) 2001, 2005, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4408489 4826652
 * @summary Testing values of Float.{MIN_VALUE, MIN_NORMAL, MAX_VALUE}
 * @author Joseph D. Darcy
 */
package test.java.lang.Float;

import org.testng.annotations.Test;
import org.testng.Assert;

public class ExtremaTest {
    @Test
    public void testExtremalValues() throws Exception {
        Assert.assertEquals(Float.intBitsToFloat(0x1), Float.MIN_VALUE,
            "Float.MIN_VALUE is not equal to intBitsToFloat(0x1).");

        Assert.assertEquals(Float.intBitsToFloat(0x00800000), Float.MIN_NORMAL,
            "Float.MIN_NORMAL is not equal to intBitsToFloat(0x00800000).");

        Assert.assertEquals(Float.intBitsToFloat(0x7f7fffff), Float.MAX_VALUE,
            "Float.MAX_VALUE is not equal to intBitsToFloat(0x7f7fffff).");
    }
}
