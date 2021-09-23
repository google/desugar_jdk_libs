/*
 * Copyright (C) 2011 The Android Open Source Project
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

package libcore.java.lang;

public class ShortTest extends junit.framework.TestCase {
    public void test_compare() throws Exception {
        final short min = Short.MIN_VALUE;
        final short zero = 0;
        final short max = Short.MAX_VALUE;
        assertTrue(Short.compare(max,  max)  == 0);
        assertTrue(Short.compare(min,  min)  == 0);
        assertTrue(Short.compare(zero, zero) == 0);
        assertTrue(Short.compare(max,  zero) > 0);
        assertTrue(Short.compare(max,  min)  > 0);
        assertTrue(Short.compare(zero, max)  < 0);
        assertTrue(Short.compare(zero, min)  > 0);
        assertTrue(Short.compare(min,  zero) < 0);
        assertTrue(Short.compare(min,  max)  < 0);
    }

    public void testStaticHashCode() {
        assertEquals(Short.valueOf((short) 567).hashCode(), Short.hashCode((short) 567));
    }

    public void testBYTES() {
        assertEquals(2, Short.BYTES);
    }

    public void testToUnsignedInt() {
        for(int i = Short.MIN_VALUE; i < Short.MAX_VALUE; i++) {
            final short b = (short) i;
            final int ui = Short.toUnsignedInt(b);
            assertEquals(0, ui >>> Short.BYTES * 8);
            assertEquals(b, Integer.valueOf(b).shortValue());
        }
    }

    public void testToUnsignedLong() {
        for(int i = Short.MIN_VALUE; i < Short.MAX_VALUE; i++) {
            final short b = (short) i;
            final long ul = Short.toUnsignedLong(b);
            assertEquals(0, ul >>> Short.BYTES * 8);
            assertEquals(b, Long.valueOf(b).shortValue());
        }
    }

    public void testCompareUnsigned() {
        // Ascending order of unsigned(value)
        final short a = 0;
        final short b = 3;
        final short y = -2; // 65534
        final short z = -1; // 65535

        assertTrue(Short.compareUnsigned(a, b) < 0);
        assertTrue(Short.compareUnsigned(a, y) < 0);
        assertTrue(Short.compareUnsigned(a, z) < 0);
        assertTrue(Short.compareUnsigned(b, y) < 0);
        assertTrue(Short.compareUnsigned(b, z) < 0);
        assertTrue(Short.compareUnsigned(y, z) < 0);

        assertTrue(Short.compareUnsigned(b, a) > 0);
        assertTrue(Short.compareUnsigned(y, a) > 0);
        assertTrue(Short.compareUnsigned(y, b) > 0);
        assertTrue(Short.compareUnsigned(z, a) > 0);
        assertTrue(Short.compareUnsigned(z, b) > 0);
        assertTrue(Short.compareUnsigned(z, y) > 0);

        assertTrue(Short.compareUnsigned(a, a) == 0);
        assertTrue(Short.compareUnsigned(b, b) == 0);
        assertTrue(Short.compareUnsigned(y, y) == 0);
        assertTrue(Short.compareUnsigned(z, z) == 0);

        assertTrue(Short.compareUnsigned(Short.MIN_VALUE, (short)32768) == 0);
        assertTrue(Short.compareUnsigned(Short.MAX_VALUE, (short)32767) == 0);
        assertTrue(Short.compareUnsigned(Short.MIN_VALUE, Short.MAX_VALUE) > 0);
        assertTrue(Short.compareUnsigned(Short.MIN_VALUE, z) < 0);
        assertTrue(Short.compareUnsigned(Short.MAX_VALUE, z) < 0);
    }
}
