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

package libcore.java.awt.font;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.awt.font.NumericShaper;
import java.awt.font.NumericShaper.Range;
import java.util.EnumSet;

@RunWith(JUnit4.class)
public class NumericShaperTest {

    @Test
    public void testShape() {
        NumericShaper ns = NumericShaper.getShaper(Range.ARABIC);
        String input = "abc 123";
        String expected = "abc \u0661\u0662\u0663";

        char[] chars = input.toCharArray();
        ns.shape(chars, 0, chars.length);
        assertEquals(expected, new String(chars));

        chars = input.toCharArray();
        ns.shape(chars, 0, chars.length, NumericShaper.EUROPEAN);
        assertEquals(expected, new String(chars));

        chars = input.toCharArray();
        ns.shape(chars, 0, chars.length, Range.EUROPEAN);
        assertEquals(expected, new String(chars));
    }

    @Test
    public void testGetRanges() {
        NumericShaper ns = NumericShaper.getContextualShaper(NumericShaper.ARABIC);
        assertEquals(NumericShaper.ARABIC, ns.getRanges());
    }

    @Test
    public void testGetRangeSet() {
        NumericShaper ns = NumericShaper.getContextualShaper(
                NumericShaper.ARABIC | NumericShaper.DEVANAGARI);
        assertEquals(EnumSet.of(Range.ARABIC, Range.DEVANAGARI), ns.getRangeSet());
    }
}
