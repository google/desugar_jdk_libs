/*
 * Copyright (C) 2016 The Android Open Source Project
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

import junit.framework.TestCase;

import java.util.Arrays;

public class StringBufferTest extends TestCase {

    public void testChars() {
        StringBuffer s = new StringBuffer("Hello\n\tworld");
        int[] expected = new int[s.length()];
        for (int i = 0; i < s.length(); ++i) {
            expected[i] = (int) s.charAt(i);
        }
        assertTrue(Arrays.equals(expected, s.chars().toArray()));

        // Surrogate code point
        char high = '\uD83D', low = '\uDE02';
        StringBuffer surrogateCP = new StringBuffer().append(new char[]{high, low, low});
        assertTrue(Arrays.equals(new int[]{high, low, low}, surrogateCP.chars().toArray()));
    }

    public void testCodePoints() {
        StringBuffer s = new StringBuffer("Hello\n\tworld");
        int[] expected = new int[s.length()];
        for (int i = 0; i < s.length(); ++i) {
            expected[i] = (int) s.charAt(i);
        }
        assertTrue(Arrays.equals(expected, s.codePoints().toArray()));

        // Surrogate code point
        char high = '\uD83D', low = '\uDE02';
        StringBuffer surrogateCP = new StringBuffer().append(new char[]{high, low, low, '0'});
        assertEquals(Character.toCodePoint(high, low), surrogateCP.codePoints().toArray()[0]);
        assertEquals((int) low, surrogateCP.codePoints().toArray()[1]); // Unmatched surrogate.
        assertEquals((int) '0', surrogateCP.codePoints().toArray()[2]);
    }
}
