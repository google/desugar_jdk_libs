/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package libcore.java.text;

import java.text.AttributedCharacterIterator;
import java.text.Bidi;
import junit.framework.TestCase;

public class OldBidiTest extends TestCase {

    public void testToString() {
        try {
            Bidi bd = new Bidi("bidi", 173);
            assertNotNull("Bidi representation is null", bd.toString());
        } catch (Exception e) {
            fail("Unexpected exception " + e.toString());
        }
    }

    public void testCreateLineBidi_AndroidFailure() {
        // This is a difference between ICU4C and the RI. ICU4C insists that 'limit' is strictly
        // greater than 'start'. We have to paper over this in our Java code.
        Bidi bidi = new Bidi("str", Bidi.DIRECTION_RIGHT_TO_LEFT);
        bidi.createLineBidi(2, 2);
    }

    public void testGetRunLevelLInt() {
        Bidi bd = new Bidi("text", Bidi.DIRECTION_LEFT_TO_RIGHT);
        try {
            assertEquals(0, bd.getRunLevel(0));
            assertEquals(0, bd.getRunLevel(bd.getRunCount()));
        } catch (Exception e) {
            fail("Unexpected exception: " + e);
        }

        bd = new Bidi("text", Bidi.DIRECTION_RIGHT_TO_LEFT);
        try {
            assertEquals(2, bd.getRunLevel(0));
        } catch (Exception e) {
            fail("Unexpected exception: " + e);
        }

        bd = new Bidi("text", Bidi.DIRECTION_DEFAULT_RIGHT_TO_LEFT);
        try {
            assertEquals(0, bd.getRunLevel(0));
        } catch (Exception e) {
            fail("Unexpected exception: " + e);
        }
    }

    public void testGetRunStart() {
        Bidi bd = new Bidi(new char[] { 's', 's', 's' }, 0, new byte[] { (byte) -7,
                (byte) -2, (byte) 3 }, 0, 3,
                Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        assertEquals(0, bd.getRunStart(0));
        assertEquals(1, bd.getRunStart(1));
        assertEquals(2, bd.getRunStart(2));
    }

    public void testReorderVisuallyIllegalArguments() {
        // Negative index.
        try {
            Bidi.reorderVisually(new byte[] {}, -1, new Object[] {}, 0, 0);
            fail();
        } catch (IllegalArgumentException  expected) {
            // Expected.
        }

        try {
            Bidi.reorderVisually(new byte[] {}, 0, new Object[] {}, -1, 0);
            fail();
        } catch (IllegalArgumentException  expected) {
            // Expected.
        }

        try {
            Bidi.reorderVisually(new byte[] {}, 0, new Object[] {}, 0, -1);
            fail();
        } catch (IllegalArgumentException  expected) {
            // Expected.
        }

        // Count > levels.length.
        try {
            Bidi.reorderVisually(new byte[] {}, 0, new Object[] {}, 0, 1);
            fail();
        } catch (IllegalArgumentException  expected) {
            // Expected.
        }

        // Count > levels.length - levelStart.
        try {
            Bidi.reorderVisually(new byte[] {1, 2, 3}, 2, new Object[] {}, 0, 2);
            fail();
        } catch (IllegalArgumentException  expected) {
            // Expected.
        }
    }

    public void testRequiresBidiIllegalArguments() {
        // Negative param.
        try {
            Bidi.requiresBidi(new char[] {}, 0, -1);
        } catch (IllegalArgumentException expected) {
            // Expected.
        }

        try {
            Bidi.requiresBidi(new char[] {}, -1, 0);
        } catch (IllegalArgumentException expected) {
            // Expected.
        }

        // Limit > start.
        try {
            Bidi.requiresBidi(new char[] {}, 1, 0);
        } catch (IllegalArgumentException expected) {
            // Expected.
        }

        // Limit > text.length.
        try {
            Bidi.requiresBidi(new char[] {'a', 'b', 'c'}, 0, 4);
        } catch (IllegalArgumentException expected) {
            // Expected.
        }
    }

    public void testCreateLineBidiIllegalArguments() {
        Bidi bidi = new Bidi("test", Bidi.DIRECTION_LEFT_TO_RIGHT);

        try {
            bidi.createLineBidi(-1, 0);
            fail();
        } catch (IllegalArgumentException expected) {
            // Expected.
        }

        try {
            bidi.createLineBidi(0, -1);
            fail();
        } catch (IllegalArgumentException expected) {
            // Expected.
        }

        // Linelimit > getLength().
        try {
            bidi.createLineBidi(0, 5);
            fail();
        } catch (IllegalArgumentException expected) {
            // Expected.
        }

        // lineStart > lineLimit.
         try {
            bidi.createLineBidi(2, 1);
            fail();
        } catch (IllegalArgumentException expected) {
            // Expected.
        }
    }

    public void testConstructorIllegalArguments() {
        try {
            new Bidi(null, Bidi.DIRECTION_LEFT_TO_RIGHT);
            fail();
        } catch (IllegalArgumentException expected) {
            // Expected.
        }

        // text.length - textStart < paragraphLength.
        try {
            new Bidi(new char[] {'a', 'b', 'c', 'd', 'e'}, 1, new byte[] {}, 0, 5,
                    Bidi.DIRECTION_LEFT_TO_RIGHT);
            fail();
        } catch (IllegalArgumentException expected) {
            // Expected.
        }
    }

    // http://b/30652865
    public void testUnicode9EmojisAreLtrNeutral() {
        String callMeHand = "\uD83E\uDD19"; // U+1F919 in UTF-16
        String hebrewAndEmoji = "\u05e9\u05dc" + callMeHand + "\u05d5\u05dd";
        String latinAndEmoji = "Hel" + callMeHand + "lo";
        Bidi hebrew = new Bidi(hebrewAndEmoji, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        assertFalse("Hebrew bidi is mixed: " + hebrew, hebrew.isMixed());
        assertTrue("Hebrew bidi is not right to left: " + hebrew, hebrew.isRightToLeft());
        Bidi latin = new Bidi(latinAndEmoji, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        assertFalse("Latin bidi is mixed: " + latin, latin.isMixed());
        assertTrue("latin bidi is not left to right: " + latin, latin.isLeftToRight());
    }

    // Regression test for http://b/34320622 - when specifying "embeddings" of 0 with RTL characters
    // and a default of LTR we should not get an exception.
    public void testEmbeddings() throws Exception {
        char[] text = new char[2];
        text[0] = '\u202d'; // LEFT-TO-RIGHT OVERRIDE
        text[1] = '\u05d0'; // HEBREW LETTER ALEF

        byte[] embeddings = new byte[2];
        embeddings[0] = 0;
        embeddings[1] = 0;
        int flags = Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT;

        // This threw an exception on N.
        Bidi bidi = new Bidi(text, 0, embeddings, 0, 2, flags);

        // Assert properties of the resulting Bidi and embeddings array. The results are somewhat
        // arbitrary and it's hard to know the user's expectations for the characters chosen, but
        // a failure will identify unintended behavior changes.
        assertEquals(text.length, bidi.getLength());
        assertEquals(1, bidi.getBaseLevel());
        assertEquals(1, bidi.getRunCount());
        assertEquals(1, bidi.getLevelAt(0));
        assertEquals(1, bidi.getLevelAt(1));
        assertEquals(1, bidi.getRunLevel(0));
        assertEquals(0, bidi.getRunStart(0));
        assertEquals(2, bidi.getRunLimit(0));

        assertEquals(0, embeddings[0]);
        assertEquals(0, embeddings[1]);
    }
}
