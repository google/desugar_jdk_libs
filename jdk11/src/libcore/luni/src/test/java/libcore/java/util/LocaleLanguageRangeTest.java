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

package libcore.java.util;

import static java.util.Locale.LanguageRange.MAX_WEIGHT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale.LanguageRange;
import java.util.Map;
import java.util.stream.Collectors;

import junit.framework.TestCase;

/**
 * Tests {@link LanguageRange}.
 */
public class LocaleLanguageRangeTest extends TestCase {

    /**
     * Checks that the constants for min/max weight don't accidentally change.
     */
    public void testWeight_constantValues() {
        assertEquals(0.0, LanguageRange.MIN_WEIGHT);
        assertEquals(1.0, MAX_WEIGHT);
    }

    public void testConstructor_defaultsToMaxWeight() {
        assertEquals(MAX_WEIGHT, new LanguageRange("de-DE").getWeight());
    }

    public void testConstructor_invalidWeight() {
        try {
            new LanguageRange("de-DE", -0.00000001);
            fail();
        } catch (IllegalArgumentException expected) {

        }
        try {
            new LanguageRange("de-DE", 1.00000001);
            fail();
        } catch (IllegalArgumentException expected) {
        }
        // These work:
        new LanguageRange("de-DE", 0);
        new LanguageRange("de-DE", 1);
    }

    public void testConstructor_nullRange() {
        try {
            new LanguageRange(null);
            fail();
        } catch (NullPointerException expected) {
        }
        try {
            new LanguageRange(null, MAX_WEIGHT);
            fail();
        } catch (NullPointerException expected) {
        }
        new LanguageRange("de-DE", MAX_WEIGHT); // works
    }

    public void testConstructor_checksForAtLeastOneSubtag() {
        assertRangeMalformed("");
        // The fact that ArrayIndexOutOfBoundsException instead of
        // IllegalArgumentException is thrown here is somewhat
        // inconsistent; the checks below ensure that we're aware
        // if we change the behavior in future.
        try {
            new LanguageRange("-");
            fail();
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
        try {
            new LanguageRange("--");
            fail();
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
    }

    public void testConstructor_checksForWellFormedSubtags() {
        // first subtag must not have digits
        assertRangeMalformed("012-xx");
        assertRangeMalformed("b0b-xx");
        new LanguageRange("bob-xx"); // okay
        new LanguageRange("bob-01"); // okay

        // subtags must be <= 8 characters
        assertRangeMalformed("de-abcdefghi-xx");
        new LanguageRange("de-abcdefgh-xx"); // okay

        // "-" only between subtags and only one in a row
        assertRangeMalformed("-de");
        assertRangeMalformed("de-");
        assertRangeMalformed("de--DE");
        new LanguageRange("de-DE"); // okay
        new LanguageRange("de"); // okay
    }

    public void testConstructor_acceptsWildcardSubtags() {
        new LanguageRange("de-*");
        new LanguageRange("*-DE");
        new LanguageRange("de-*-DE");
        new LanguageRange("*");
    }

    public void testEqualsAndHashCode() {
        checkEqual(new LanguageRange("en-US"), new LanguageRange("en-US"));
        checkNotEqual(new LanguageRange("en-US"), new LanguageRange("en-AU"));

        checkEqual(new LanguageRange("en-US"),
                new LanguageRange("en-US", LanguageRange.MAX_WEIGHT));
        checkNotEqual(new LanguageRange("en-US"), new LanguageRange("en-US", 0.4));

        checkEqual(new LanguageRange("en-US", 0.3), new LanguageRange("en-US", 0.3));
        checkNotEqual(new LanguageRange("en-US", 0.3), new LanguageRange("en-US", 0.4));
        checkNotEqual(new LanguageRange("ja-JP", 0.5), new LanguageRange("de-DE", 0.5));
    }

    private static <T> void checkEqual(T a, T b) {
        assertEquals(a, b);
        assertEquals(b, a);
        assertEquals(a.hashCode(), b.hashCode());
    }

    private static <T> void checkNotEqual(T a, T b) {
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
        assertTrue(a.hashCode() != b.hashCode());
    }

    public void testGetRange() {
        assertEquals("de-de", new LanguageRange("de-DE", 0.12345).getRange());
    }

    public void testGetWeight() {
        assertEquals(0.12345, new LanguageRange("de-DE", 0.12345).getWeight());
    }

    public void testMapEquivalents_emptyList() {
        List<LanguageRange> noRange = Collections.emptyList();
        assertEquals(noRange, LanguageRange.mapEquivalents(noRange, Collections.emptyMap()));
        assertEquals(noRange, LanguageRange.mapEquivalents(noRange,
                Collections.singletonMap("en-US", Arrays.asList("en-US", "en-AU", "en-UK"))));
    }

    public void testMapEquivalents_emptyMap_createsModifiableCopy() {
        List<LanguageRange> inputRanges = Collections.unmodifiableList(Arrays.asList(
                new LanguageRange("de-DE"),
                new LanguageRange("ja-JP")));
        List<LanguageRange> outputRanges =
                LanguageRange.mapEquivalents(inputRanges, Collections.emptyMap());
        assertEquals(inputRanges, outputRanges);
        assertNotSame(inputRanges, outputRanges);
        // result is modifiable
        outputRanges.add(new LanguageRange("fr-FR"));
        outputRanges.clear();
    }

    /**
     * Tests the example from the {@link LanguageRange#mapEquivalents(List, Map)} documentation.
     */
    public void testMapEquivalents_exampleFromDocumentation() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("zh", Collections.unmodifiableList(Arrays.asList("zh", "zh-Hans")));
        map.put("zh-HK", Collections.singletonList("zh-HK"));
        map.put("zh-TW", Collections.singletonList("zh-TW"));

        List<LanguageRange> inputPriorityList = Arrays.asList(
                new LanguageRange("zh"),
                new LanguageRange("zh-CN"),
                new LanguageRange("en"),
                new LanguageRange("zh-TW"),
                new LanguageRange("zh-TW")
        );
        List<LanguageRange> expectedOutput = Arrays.asList(
                new LanguageRange("zh"),
                new LanguageRange("zh-Hans"),
                new LanguageRange("zh-CN"),
                new LanguageRange("zh-Hans-CN"),
                new LanguageRange("en"),
                new LanguageRange("zh-TW"),
                new LanguageRange("zh-TW")
        );
        List<LanguageRange> outputProrityList = LanguageRange
                .mapEquivalents(inputPriorityList, map);
        assertEquals(expectedOutput, outputProrityList);
    }

    public void testMapEquivalents_nullList() {
        try {
            LanguageRange.mapEquivalents(null, Collections.emptyMap());
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * The documentation doesn't specify whether {@code mapEquivalents()} accepts a
     * null map, but the current behavior is the same as for an empty map. This test
     * ensures that we're aware if this behavior changse.
     */
    public void testMapEquivalents_nullMap() {
        List<LanguageRange> priorityList = Collections.unmodifiableList(Arrays.asList(
                new LanguageRange("de-DE"),
                new LanguageRange("en-UK"),
                new LanguageRange("zh-CN")));
        assertEquals(priorityList, LanguageRange.mapEquivalents(priorityList, null));
    }

    /** Tests {@link LanguageRange#parse(String, Map)}. */
    public void testMapEquivalents() {
        List<LanguageRange> expected = Arrays.asList(
                new LanguageRange("de-de", 1.0),
                new LanguageRange("en-us", 0.7),
                new LanguageRange("en-au", 0.7)
        );
        Map<String, List<String>> map = new HashMap<>();
        map.put("fr", Arrays.asList("de-DE"));
        map.put("en", Arrays.asList("en-US", "en-AU"));
        String ranges = "Accept-Language: fr,en;q=0.7";
        assertEquals(expected, LanguageRange.parse(ranges, map));
        // Per the documentation, this should be equivalent
        assertEquals(expected, LanguageRange.mapEquivalents(LanguageRange.parse(ranges), map));
    }

    /**
     * Because {@code mapEquivalents(ranges, map)} behaves identically
     * to {@code mapEquivalents(parse(ranges), map}, any equivalent
     * locales from {@link sun.util.locale.LocaleEquivalentMaps},
     * such as {@code "iw" -> "he"}, are expanded before the mapping
     * from {@code map} is applied.
     */
    public void testParse_map_localeEquivalent() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("iw", Arrays.asList("de-DE"));
        map.put("en", Arrays.asList("en-US", "en-AU"));

        List<LanguageRange> expectedOutput = Arrays.asList(
                new LanguageRange("de-de", 1.0), // iw -> de-de (map)
                new LanguageRange("he", 1.0), // iw -> he (LocaleEquivalentMaps)
                new LanguageRange("en-us", 0.7), // en -> en-us (map)
                new LanguageRange("en-au", 0.7)); // en -> en-au (map)

        String ranges = "Accept-Language: iw,en;q=0.7";
        assertEquals(expectedOutput, LanguageRange.parse(ranges, map));
        // Per the documentation, this should be equivalent
        assertEquals(expectedOutput,
                LanguageRange.mapEquivalents(LanguageRange.parse(ranges), map));
    }

    /**
     * Tests the example from the {@link LanguageRange#parse(String)} documentation.
     */
    public void testParse_acceptLanguage_exampleFromDocumentation() {
        List<LanguageRange> expected = Arrays.asList(
                new LanguageRange("iw", 1.0),
                new LanguageRange("he", 1.0),
                new LanguageRange("en-us", 0.7),
                new LanguageRange("en", 0.3)
        );
        assertEquals(expected, LanguageRange.parse("Accept-Language: iw,en-us;q=0.7,en;q=0.3"));
    }

    /**
     * Tests parsing the example from RFC 2616 section 14.4.
     */
    public void testParse_acceptLanguage_exampleFromRfc2616() {
        List<LanguageRange> expected = Arrays.asList(
                new LanguageRange("da", 1.0),
                new LanguageRange("en-gb", 0.8),
                new LanguageRange("en", 0.7)
        );
        assertEquals(expected, LanguageRange.parse("Accept-Language: da, en-gb;q=0.8, en;q=0.7"));
    }

    public void testParse_acceptLanguage_malformed() {
        try {
            LanguageRange.parse("Accept-Language: fr,en-us;q=1;q=0.5");
            fail();
        } catch (IllegalArgumentException expected) {
        }
        try {
            LanguageRange.parse("Accept-Language: q=0.5");
            fail();
        } catch (IllegalArgumentException expected) {
        }
        try {
            LanguageRange.parse("Accept-Language: ;q=0.5");
            fail();
        } catch (IllegalArgumentException expected) {
        }
        try {
            LanguageRange.parse("Accept-Language: thislanguagetagistoolong;q=0.5");
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    /**
     * The current implementation doesn't require a ' ' after the "Accept-Language:".
     * This test ensures that we're aware if this behavior changes.
     */
    public void testParse_acceptLanguage_missingSpaceAfterColon() {
        List<LanguageRange> languageRanges = Arrays.asList(
                new LanguageRange("fr"),
                new LanguageRange("en-us", 1)
        );
        assertEquals(languageRanges, LanguageRange.parse("Accept-Language:fr,en-us;q=1"));
    }

    public void testParse_acceptLanguage_wildCards() {
        List<LanguageRange> expected = Arrays.asList(
                new LanguageRange("da", 1.0),
                new LanguageRange("en-*", 0.8),
                new LanguageRange("*", 0.7)
        );
        assertEquals(expected, LanguageRange.parse("Accept-Language: da, en-*;q=0.8, *;q=0.7"));
    }

    public void testParse_acceptLanguage_weightValid() {
        LanguageRange fr = new LanguageRange("fr");
        assertEquals(Arrays.asList(fr, new LanguageRange("en-us", 1.0)),
                LanguageRange.parse("Accept-Language: fr,en-us;q=1"));
        assertEquals(Arrays.asList(fr, new LanguageRange("en-us", 0.1)),
                LanguageRange.parse("Accept-Language: fr,en-us;q=.1"));
        assertEquals(Arrays.asList(fr, new LanguageRange("en-us", 0.12345678901234567890)),
                LanguageRange.parse("Accept-Language: fr,en-us;q=0.12345678901234567890"));
        assertEquals(Arrays.asList(fr, new LanguageRange("en-us", 0)),
                LanguageRange.parse("Accept-Language: fr,en-us;q=0"));
    }

    public void testParse_acceptLanguage_weightInvalid() {
        try {
            LanguageRange.parse("Accept-Language: iw,en-us;q=1.1");
            fail();
        } catch (IllegalArgumentException expected) {
        }
        try {
            LanguageRange.parse("Accept-Language: iw,en-us;q=-0.1");
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    // Based on a test case that was contributed back to upstream maintainers through
    // https://bugs.openjdk.java.net/browse/JDK-8166994
    public void testParse_multiEquivalent_consistency() {
        List<String> parsed = rangesToStrings(LanguageRange.parse("ccq-xx"));

        assertEquals(parsed, rangesToStrings(LanguageRange.parse("ccq-xx"))); // consistency
        assertEquals(Arrays.asList("ccq-xx", "ybd-xx", "rki-xx"), parsed); // expected result
    }

    /**
     * Tests parsing a Locale range matching an entry from
     * {@link sun.util.locale.LocaleEquivalentMaps#singleEquivMap}.
     */
    public void testParse_singleEquivalent() {
        assertParseRanges("art-lojban", "jbo"); // example from RFC 4647 section 3.2
        assertParseRanges("yue", "zh-yue");
        assertParseRanges("yue-xx", "zh-yue-xx");
    }

    /**
     * Tests parsing a Locale range matching an entry from
     * {@link sun.util.locale.LocaleEquivalentMaps#multiEquivsMap}.
     */
    public void testParse_multiEquivalent() {
        assertParseRanges("mst", "myt", "mry");
        assertParseRanges("i-hak", "zh-hakka", "hak");
    }

    /**
     * Tests parsing a Locale range matching an entry from
     * {@link sun.util.locale.LocaleEquivalentMaps#regionVariantEquivMap}.
     */
    public void testParse_regionEquivalent() {
        // Region ("-de" or "-dd") matches the end
        assertParseRanges("de-de", "de-dd");
        assertParseRanges("xx-dd", "xx-de");

        // Region ("-de" or "-dd") matches the middle
        assertParseRanges("xx-de-yy", "xx-dd-yy");
        assertParseRanges("xx-dd-yy", "xx-de-yy");

        assertParseRanges("xx-bu", "xx-mm");
        assertParseRanges("xx-mm", "xx-bu");
    }

    /**
     * Tests parsing a Locale range matching entries from both
     * {@link sun.util.locale.LocaleEquivalentMaps#singleEquivMap} and
     * {@link sun.util.locale.LocaleEquivalentMaps#regionVariantEquivMap}.
     */
    public void testParse_singleAndRegionEquivalent() {
        assertParseRanges("sgn-ch-de", "sgg", "sgn-ch-dd");
        assertParseRanges("sgn-ch-de-xx", "sgg-xx", "sgn-ch-dd-xx");
    }

    /**
     * Asserts that {@code LanguageRange(ranges)} returns LanguageRanges whose
     * {@link LanguageRange#getRange() Range string}s are {@code ranges} and
     * {@code expectedAdditional}, in order.
     */
    private static void assertParseRanges(String ranges, String... expectedAdditional) {
        List<String> expected = new ArrayList<>();
        expected.add(ranges);
        expected.addAll(Arrays.asList(expectedAdditional));

        List<String> actual = rangesToStrings(LanguageRange.parse(ranges));

        assertEquals(expected, actual);
    }

    private static List<String> rangesToStrings(List<LanguageRange> languageRanges) {
        return languageRanges.stream().map(LanguageRange::getRange).collect(Collectors.toList());
    }

    private void assertRangeMalformed(String range) {
        try {
            new LanguageRange(range);
            fail("Range should be recognized as malformed: " + range);
        } catch (IllegalArgumentException expected) {
            // Check for the exception that is thrown when a malformed subtag is detected.
            // The exception message used here may change in future.
            assertEquals("range=" + range.toLowerCase(), expected.getMessage());
        }
    }

}
