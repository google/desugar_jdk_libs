/*
 * Copyright (C) 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package libcore.java.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import junit.framework.TestCase;
import libcore.icu.ICU;

public class LocaleInternalsTest extends TestCase {
/*
    public void test_serializeExtensions() {
        Map<Character, String> extensions = new TreeMap<Character, String>();

        extensions.put('x', "fooo-baar-baaz");
        assertEquals("x-fooo-baar-baaz", Locale.serializeExtensions(extensions));

        extensions.put('y', "gaaa-caar-caaz");
        // Must show up in lexical order.
        assertEquals("x-fooo-baar-baaz-y-gaaa-caar-caaz",
                Locale.serializeExtensions(extensions));
    }

    public void test_parseSerializedExtensions() {
        Map<Character, String> extensions = new HashMap<Character, String>();

        Locale.parseSerializedExtensions("x-foo", extensions);
        assertEquals("foo", extensions.get('x'));

        extensions.clear();
        Locale.parseSerializedExtensions("x-foo-y-bar-z-baz", extensions);
        assertEquals("foo", extensions.get('x'));
        assertEquals("bar", extensions.get('y'));
        assertEquals("baz", extensions.get('z'));

        extensions.clear();
        Locale.parseSerializedExtensions("x-fooo-baar-baaz", extensions);
        assertEquals("fooo-baar-baaz", extensions.get('x'));

        extensions.clear();
        Locale.parseSerializedExtensions("x-fooo-baar-baaz-y-gaaa-caar-caaz", extensions);
        assertEquals("fooo-baar-baaz", extensions.get('x'));
        assertEquals("gaaa-caar-caaz", extensions.get('y'));
    }

    public void test_parseUnicodeExtension() {
        Map<String, String> keywords = new HashMap<String, String>();
        Set<String> attributes = new HashSet<String>();

        // Only attributes.
        Locale.parseUnicodeExtension("foooo".split("-"), keywords, attributes);
        assertTrue(attributes.contains("foooo"));
        assertEquals(Collections.EMPTY_SET, keywords.keySet());

        attributes.clear();
        keywords.clear();
        Locale.parseUnicodeExtension("foooo-baa-baaabaaa".split("-"),
                keywords, attributes);
        assertTrue(attributes.contains("foooo"));
        assertTrue(attributes.contains("baa"));
        assertTrue(attributes.contains("baaabaaa"));
        assertEquals(Collections.EMPTY_SET, keywords.keySet());

        // Only keywords
        attributes.clear();
        keywords.clear();
        Locale.parseUnicodeExtension("ko-koko".split("-"), keywords, attributes);
        assertTrue(attributes.isEmpty());
        assertEquals("koko", keywords.get("ko"));

        attributes.clear();
        keywords.clear();
        Locale.parseUnicodeExtension("ko-koko-kokoko".split("-"), keywords, attributes);
        assertTrue(attributes.isEmpty());
        assertEquals("koko-kokoko", keywords.get("ko"));

        attributes.clear();
        keywords.clear();
        Locale.parseUnicodeExtension("ko-koko-kokoko-ba-baba-bababa".split("-"),
                keywords, attributes);
        assertTrue(attributes.isEmpty());
        assertEquals("koko-kokoko", keywords.get("ko"));
        assertEquals("baba-bababa", keywords.get("ba"));

        // A mixture of attributes and keywords.
        attributes.clear();
        keywords.clear();
        Locale.parseUnicodeExtension("attri1-attri2-k1-type1-type1-k2-type2".split("-"),
                keywords, attributes);
        assertTrue(attributes.contains("attri1"));
        assertTrue(attributes.contains("attri2"));
        assertEquals("type1-type1", keywords.get("k1"));
        assertEquals("type2", keywords.get("k2"));
    }

    public void test_setDefault_setsICUDefaultLocale() {
        Locale.setDefault(Locale.GERMANY);
        assertEquals("de_DE", ICU.getDefaultLocale());

        try {
            Locale.setDefault(null);
            fail();
        } catch (NullPointerException expected) {
            assertEquals(Locale.GERMANY, Locale.getDefault());
        }

        Locale.setDefault(new Locale("bogus", "LOCALE"));
        assertEquals("und", ICU.getDefaultLocale());
    } */
}
