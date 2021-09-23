/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package libcore.android.system;

import android.system.StructTimespec;

import junit.framework.TestCase;

/**
 * Unit test for {@link StructTimespec}
 */
public class StructTimespecTest extends TestCase {
    public void testConstructor() {
        StructTimespec val = new StructTimespec(Long.MIN_VALUE, 0);
        assertEquals(Long.MIN_VALUE, val.tv_sec);
        assertEquals(0, val.tv_nsec);

        val = new StructTimespec(-23, 23);
        assertEquals(-23, val.tv_sec);
        assertEquals(23, val.tv_nsec);

        val = new StructTimespec(0, 42);
        assertEquals(0, val.tv_sec);
        assertEquals(42, val.tv_nsec);

        val = new StructTimespec(23, 91);
        assertEquals(23, val.tv_sec);
        assertEquals(91, val.tv_nsec);

        val = new StructTimespec(Long.MAX_VALUE, 999_999_999);
        assertEquals(Long.MAX_VALUE, val.tv_sec);
        assertEquals(999_999_999, val.tv_nsec);
    }

    public void testConstructorInvalidNsec() {
        try {
            new StructTimespec(0, 1_000_000_000);
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            new StructTimespec(0, -1);
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void testCompare() {
        StructTimespec[] specs = new StructTimespec[]{new StructTimespec(Long.MIN_VALUE, 0),
                new StructTimespec(-24, 91),
                new StructTimespec(-23, 23),
                new StructTimespec(0, 41),
                new StructTimespec(0, 42),
                new StructTimespec(22, 91),
                new StructTimespec(23, 23),
                new StructTimespec(24, 91),
                new StructTimespec(Long.MAX_VALUE, 999_999_999)};

        for (int a = 0; a < specs.length; a++) {
            for (int b = 0; b < specs.length; b++) {
                if (a < b) {
                    assertTrue(a + "<" + b, specs[a].compareTo(specs[b]) < 0);
                } else if (a == b) {
                    assertTrue(a + "==" + b, specs[a].compareTo(specs[b]) == 0);
                } else {
                    assertTrue(a + ">" + b,  specs[a].compareTo(specs[b]) > 0);
                }
            }
        }
    }

    public void testEquals() {
        StructTimespec allZero1 = new StructTimespec(0, 0);
        StructTimespec allZero2 = new StructTimespec(0, 0);

        StructTimespec val1 = new StructTimespec(23, 42);
        StructTimespec val2 = new StructTimespec(23, 42);

        StructTimespec secZero1 = new StructTimespec(0, 42);
        StructTimespec secZero2 = new StructTimespec(0, 42);

        StructTimespec nsecZero1 = new StructTimespec(23, 0);
        StructTimespec nsecZero2 = new StructTimespec(23, 0);

        assertTrue(allZero1.equals(allZero2));
        assertTrue(val1.equals(val2));
        assertTrue(secZero1.equals(secZero2));
        assertTrue(nsecZero1.equals(nsecZero2));

        // Compare equals in both directions
        assertFalse(allZero1.equals(val1));
        assertFalse(allZero1.equals(secZero1));
        assertFalse(allZero1.equals(nsecZero1));

        assertFalse(val1.equals(secZero1));
        assertFalse(val1.equals(nsecZero1));
        assertFalse(val1.equals(allZero1));

        assertFalse(secZero1.equals(nsecZero1));
        assertFalse(secZero1.equals(allZero1));
        assertFalse(secZero1.equals(val1));

        assertFalse(nsecZero1.equals(allZero1));
        assertFalse(nsecZero1.equals(val1));
        assertFalse(nsecZero1.equals(secZero1));
    }

    public void testHashcode() {
        StructTimespec allZero1 = new StructTimespec(0, 0);
        StructTimespec allZero2 = new StructTimespec(0, 0);

        StructTimespec val1 = new StructTimespec(23, 42);
        StructTimespec val2 = new StructTimespec(23, 42);

        StructTimespec secZero1 = new StructTimespec(0, 42);
        StructTimespec secZero2 = new StructTimespec(0, 42);

        StructTimespec nsecZero1 = new StructTimespec(23, 0);
        StructTimespec nsecZero2 = new StructTimespec(23, 0);

        // Equal objects should have same hash code
        assertEquals(allZero1.hashCode(), allZero2.hashCode());
        assertEquals(val1.hashCode(), val2.hashCode());
        assertEquals(secZero1.hashCode(), secZero2.hashCode());
        assertEquals(nsecZero1.hashCode(), nsecZero2.hashCode());
    }
}
