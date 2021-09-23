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
package test.java.util.Collection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/*
 * @test
 * @bug 8048330
 * @summary Test convenience static factory methods on Set.
 * @run testng SetFactories
 */

@Test
public class SetFactories {

    Set<Integer> genSet() {
        return new HashSet<>(Arrays.asList(1, 2, 3));
    }

    @Test
    public void copyOfResultsEqual() {
        Set<Integer> orig = genSet();
        Set<Integer> copy = Set.copyOf(orig);

        assertEquals(orig, copy);
        assertEquals(copy, orig);
    }

    @Test
    public void copyOfModifiedUnequal() {
        Set<Integer> orig = genSet();
        Set<Integer> copy = Set.copyOf(orig);
        orig.add(4);

        assertNotEquals(orig, copy);
        assertNotEquals(copy, orig);
    }

    @Test
    public void copyOfIdentity() {
        Set<Integer> orig = genSet();
        Set<Integer> copy1 = Set.copyOf(orig);
        Set<Integer> copy2 = Set.copyOf(copy1);

        assertNotSame(orig, copy1);
        assertSame(copy1, copy2);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void copyOfRejectsNullCollection() {
        Set<Integer> set = Set.copyOf(null);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void copyOfRejectsNullElements() {
        Set<Integer> set = Set.copyOf(Arrays.asList(1, null, 3));
    }

    @Test
    public void copyOfAcceptsDuplicates() {
        Set<Integer> set = Set.copyOf(Arrays.asList(1, 1, 2, 3, 3, 3));
        assertEquals(set, Set.of(1, 2, 3));
    }
}
