/*
 * Copyright (C) 2021 The Android Open Source Project
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.Character.UnicodeScript;

@RunWith(JUnit4.class)
public class UnicodeScriptTest {

    @Test
    public void forName_shouldThrowIllegalArgumentException_whenNameIsUnknown() {
        try {
            UnicodeScript.forName("NON EXISTING SCRIPT NAME");
            fail();
        } catch (IllegalArgumentException ignored) {
            // expected
        }
    }

    @Test
    public void forName_shouldThrowNPE_whenNullIsPassed() {
        try {
            UnicodeScript.forName(null);
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }
    }

    @Test
    public void forName_shouldAcceptAllEnumValues() {
        for (UnicodeScript unicodeScript : UnicodeScript.values()) {
            assertEquals(unicodeScript, UnicodeScript.forName(unicodeScript.name()));
        }
    }

    @Test
    public void of_shouldThrowIllegalArgumentException_whenInvalidUnicodePointPassed() {
        try {
            UnicodeScript.of(-1);
            fail();
        } catch (IllegalArgumentException ignored) {
            // expected
        }

        try {
            UnicodeScript.of(Character.MAX_CODE_POINT + 1);
            fail();
        } catch (IllegalArgumentException ignored) {
            // expected
        }
    }

    @Test
    public void of_onWellKnownScripts() {
        int asciiZero = '\u0030';
        assertEquals(UnicodeScript.COMMON, UnicodeScript.of(asciiZero));
        int asciiA = '\u0041';
        assertEquals(UnicodeScript.LATIN, UnicodeScript.of(asciiA));
        int asciiTilda = '\u007E';
        assertEquals(UnicodeScript.COMMON, UnicodeScript.of(asciiTilda));
        int asciiGbp = '\u00A3';
        assertEquals(UnicodeScript.COMMON, UnicodeScript.of(asciiGbp));
        int greekCapitalAlpha = '\u0391';
        assertEquals(UnicodeScript.GREEK, UnicodeScript.of(greekCapitalAlpha));
        int cyrillicCapitalA = '\u0410';
        assertEquals(UnicodeScript.CYRILLIC, UnicodeScript.of(cyrillicCapitalA));
        assertEquals(UnicodeScript.HAN, UnicodeScript.of('\u4E00'));
    }
}
