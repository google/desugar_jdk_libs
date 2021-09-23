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

package libcore.sun.net.util;

import junit.framework.TestCase;

import java.util.Arrays;

import static sun.net.util.IPAddressUtil.textToNumericFormatV4;

public class IPAddressUtilTest extends TestCase {

    public void test_textToNumericFormatV4_valid() {
        assertBytesEquals(0, 0, 0, 0, textToNumericFormatV4("0.0.0.0"));
        assertBytesEquals(1, 2, 3, 4, textToNumericFormatV4("1.2.3.4"));
        assertBytesEquals(255, 255, 255, 255, textToNumericFormatV4("255.255.255.255"));
        assertBytesEquals(123, 23, 255, 37, textToNumericFormatV4("123.23.255.37"));
        assertBytesEquals(192, 168, 0, 42, textToNumericFormatV4("192.168.0.42"));
    }

    public void test_textToNumericFormatV4_invalid() {
        // Wrong number of components
        assertNull(textToNumericFormatV4("1"));
        assertNull(textToNumericFormatV4("1.2"));
        assertNull(textToNumericFormatV4("1.2.3"));
        assertNull(textToNumericFormatV4("1.2.3.4.5"));

        // Extra dots in various places
        assertNull(textToNumericFormatV4("1..3.4"));
        assertNull(textToNumericFormatV4("1..2.3.4"));
        assertNull(textToNumericFormatV4(".1.2.3"));
        assertNull(textToNumericFormatV4(".1.2.3.4"));
        assertNull(textToNumericFormatV4("1.2.3.4."));

        // Out of bounds values
        assertNull(textToNumericFormatV4("256.2.3.4"));
        assertNull(textToNumericFormatV4("1.256.3.4"));
        assertNull(textToNumericFormatV4("1.2.256.4"));
        assertNull(textToNumericFormatV4("1.2.3.256"));
        assertNull(textToNumericFormatV4("1.-2.3.4"));
    }

    private static void assertBytesEquals(int a, int b, int c, int d, byte[] actual) {
        byte[] expected = new byte[] { (byte) a, (byte) b, (byte) c, (byte) d };
        assertTrue("Expected " + Arrays.toString(expected) + ", got " + Arrays.toString(actual),
                Arrays.equals(expected, actual));
    }

}
