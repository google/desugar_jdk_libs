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
package test.java.util.Map;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/*
 * @test
 * @bug 8048330
 * @summary Test convenience static factory methods on Map.
 * @run testng MapFactories
 */

public class MapFactories {

    Map<Integer, String> genMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        return map;
    }

    @Test
    public void copyOfResultsEqual() {
        Map<Integer, String> orig = genMap();
        Map<Integer, String> copy = Map.copyOf(orig);

        assertEquals(orig, copy);
        assertEquals(copy, orig);
    }

    @Test
    public void copyOfModifiedUnequal() {
        Map<Integer, String> orig = genMap();
        Map<Integer, String> copy = Map.copyOf(orig);
        orig.put(4, "d");

        assertNotEquals(orig, copy);
        assertNotEquals(copy, orig);
    }

    @Test
    public void copyOfIdentity() {
        Map<Integer, String> orig = genMap();
        Map<Integer, String> copy1 = Map.copyOf(orig);
        Map<Integer, String> copy2 = Map.copyOf(copy1);

        assertNotSame(orig, copy1);
        assertSame(copy1, copy2);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void copyOfRejectsNullMap() {
        Map<Integer, String> map = Map.copyOf(null);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void copyOfRejectsNullKey() {
        Map<Integer, String> map = genMap();
        map.put(null, "x");
        Map<Integer, String> copy = Map.copyOf(map);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void copyOfRejectsNullValue() {
        Map<Integer, String> map = genMap();
        map.put(-1, null);
        Map<Integer, String> copy = Map.copyOf(map);
    }
}
