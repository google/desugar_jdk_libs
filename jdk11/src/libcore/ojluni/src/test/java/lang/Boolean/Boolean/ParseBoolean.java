/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4253773
 * @summary test Boolean.parseBoolean
 */
package test.java.lang.Boolean;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseBoolean {

    @Test
    public void testParseBoolean() {
        Assert.assertTrue(Boolean.parseBoolean("TRUE"));
        Assert.assertTrue(Boolean.parseBoolean("true"));
        Assert.assertTrue(Boolean.parseBoolean("TrUe"));

        Assert.assertFalse(Boolean.parseBoolean("false"));
        Assert.assertFalse(Boolean.parseBoolean("FALSE"));
        Assert.assertFalse(Boolean.parseBoolean("FaLse"));
        Assert.assertFalse(Boolean.parseBoolean(null));
        Assert.assertFalse(Boolean.parseBoolean("garbage"));
        Assert.assertFalse(Boolean.parseBoolean("TRUEE"));
    }
}
