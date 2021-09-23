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

import java.util.Arrays;

public class StringBuilderTest extends junit.framework.TestCase {
    // See https://code.google.com/p/android/issues/detail?id=60639
    public void test_deleteChatAt_lastRange() {
        StringBuilder sb = new StringBuilder("oarFish_");
        sb.append('a');
        String oarFishA = sb.toString();

        sb.deleteCharAt(sb.length() - 1);
        sb.append('b');
        String oarFishB = sb.toString();

        assertEquals("oarFish_a", oarFishA);
        assertEquals("oarFish_b", oarFishB);
    }

    // See https://code.google.com/p/android/issues/detail?id=60639
    public void test_deleteCharAt_lastChar() {
        StringBuilder sb = new StringBuilder();
        sb.append('a');
        String a = sb.toString();

        sb.deleteCharAt(0);
        sb.append('b');
        String b = sb.toString();

        assertEquals("a", a);
        assertEquals("b", b);
    }

    // See https://code.google.com/p/android/issues/detail?id=60639
    public void test_delete_endsAtLastChar() {
        StringBuilder sb = new StringBuilder("newGuineaSinging");
        sb.append("Dog");
        String dog = sb.toString();

        sb.delete(sb.length() - 3, sb.length());
        sb.append("Cat");
        String cat = sb.toString();

        // NOTE: It's important that these asserts stay at the end of this test.
        // We're trying to make sure that replacing chars in the builder does not
        // change strings that have already been returned from it.
        assertEquals("newGuineaSingingDog", dog);
        assertEquals("newGuineaSingingCat", cat);
    }

    public void test_deleteCharAt_boundsChecks() {
        StringBuilder sb = new StringBuilder("yeti");

        try {
            sb.deleteCharAt(sb.length());
            fail();
        } catch (StringIndexOutOfBoundsException expected) {
        }

        try {
            sb.deleteCharAt(-1);
            fail();
        } catch (StringIndexOutOfBoundsException expected) {
        }
    }

    public void test_delete_boundsChecks() throws Exception {
        StringBuilder sb = new StringBuilder("yeti");

        // The cases below ahould not throw (even though they are clearly invalid
        // ranges), because we promise not to throw if start == count as long as
        // end >= start.
        sb.delete(sb.length(), sb.length() + 2);
        sb.delete(sb.length(), sb.length());

        sb.delete(2, 2);
        assertEquals("yeti", sb.toString());

        // We must throw if start > count....
        try {
            sb.delete(sb.length() + 2, sb.length() + 3);
            fail();
        } catch (StringIndexOutOfBoundsException expected) {
        }

        // ... even if the length of the range is 0.
        try {
            sb.delete(sb.length() + 2, sb.length() + 2);
            fail();
        } catch (StringIndexOutOfBoundsException expected) {
        }

        // Must throw if start < 0.
        try {
            sb.delete(-1, sb.length() -1);
            fail();
        } catch (StringIndexOutOfBoundsException expected) {
        }

        // A few commonly used specializations: sb.delete(0, 0) on an empty
        // builder is a particularly common pattern.
        StringBuilder sb2 = new StringBuilder();
        sb2.delete(0, sb2.length());
        sb2.delete(0, 12);
    }

    // We shouldn't throw if the end index is > count, we should clamp it
    // instead.
    public void test_delete_clampsEnd() throws Exception {
        StringBuilder sb = new StringBuilder("mogwai");

        sb.delete(sb.length() - 1 , sb.length() + 2);
        assertEquals("mogwa", sb.toString());

        sb.delete(sb.length() - 1, sb.length());
        assertEquals("mogw", sb.toString());
    }

    public void testChars() {
        StringBuilder s = new StringBuilder("Hello\n\tworld");
        int[] expected = new int[s.length()];
        for (int i = 0; i < s.length(); ++i) {
            expected[i] = (int) s.charAt(i);
        }
        assertTrue(Arrays.equals(expected, s.chars().toArray()));

        // Surrogate code point
        char high = '\uD83D', low = '\uDE02';
        StringBuilder surrogateCP = new StringBuilder().append(new char[]{high, low, low});
        assertTrue(Arrays.equals(new int[]{high, low, low}, surrogateCP.chars().toArray()));
    }

    public void testCodePoints() {
        StringBuilder s = new StringBuilder("Hello\n\tworld");
        int[] expected = new int[s.length()];
        for (int i = 0; i < s.length(); ++i) {
            expected[i] = (int) s.charAt(i);
        }
        assertTrue(Arrays.equals(expected, s.codePoints().toArray()));

        // Surrogate code point
        char high = '\uD83D', low = '\uDE02';
        StringBuilder surrogateCP = new StringBuilder().append(new char[]{high, low, low, '0'});
        assertEquals(Character.toCodePoint(high, low), surrogateCP.codePoints().toArray()[0]);
        assertEquals((int) low, surrogateCP.codePoints().toArray()[1]); // Unmatched surrogate.
        assertEquals((int) '0', surrogateCP.codePoints().toArray()[2]);
    }
}
