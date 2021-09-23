/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.harmony.tests.java.util;

import junit.framework.TestCase;
import org.apache.harmony.testframework.serialization.SerializationTest;
import tests.util.SerializationTester;
import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class SimpleImmutableEntryTest extends TestCase {
    public void test_SimpleImmutableEntry_Constructor_K_V() throws Exception {
        new AbstractMap.SimpleImmutableEntry<Integer, String>(1, "test");
        new AbstractMap.SimpleImmutableEntry(null, null);
    }

    static class NullEntry implements Entry {

        public Object getKey() {
            return null;
        }

        public Object getValue() {
            return null;
        }

        public Object setValue(Object object) {
            return null;
        }
    }

    public void test_SimpleImmutableEntry_Constructor_LEntry() throws Exception {
        Map map = new TreeMap();
        map.put(1, "test");
        Entry entryToPut = (Entry) map.entrySet().iterator().next();
        Entry testEntry = new AbstractMap.SimpleImmutableEntry(entryToPut);
        assertEquals(1, testEntry.getKey());
        assertEquals("test", testEntry.getValue());
        map.clear();

        testEntry = new AbstractMap.SimpleImmutableEntry(new NullEntry());
        assertNull(testEntry.getKey());
        assertNull(testEntry.getValue());
        try {
            new AbstractMap.SimpleImmutableEntry(null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

    }

    public void test_SimpleImmutableEntry_getKey() throws Exception {
        Entry entry = new AbstractMap.SimpleImmutableEntry<Integer, String>(1, "test");
        assertEquals(1, entry.getKey());
        entry = new AbstractMap.SimpleImmutableEntry(null, null);
        assertNull(entry.getKey());
    }

    public void test_SimpleImmutableEntry_getValue() throws Exception {
        Entry entry = new AbstractMap.SimpleImmutableEntry<Integer, String>(1, "test");
        assertEquals("test", entry.getValue());
        entry = new AbstractMap.SimpleImmutableEntry(null, null);
        assertNull(entry.getValue());
    }

    public void test_SimpleImmutableEntry_setValue() throws Exception {
        Entry entry = new AbstractMap.SimpleImmutableEntry<Integer, String>(1, "test");
        assertEquals("test", entry.getValue());
        try {
            entry.setValue("Another String");
            fail("should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        assertEquals("test", entry.getValue());
        try {
            entry.setValue(null);
            fail("should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    public void test_SimpleImmutableEntry_equals() throws Exception {
        Entry entry = new AbstractMap.SimpleImmutableEntry<Integer, String>(1, "test");
        Map map = new TreeMap();
        map.put(1, "test");
        Entry entryToPut = (Entry) map.entrySet().iterator().next();
        Entry testEntry = new AbstractMap.SimpleImmutableEntry(entryToPut);
        assertEquals(entry, testEntry);
    }

    public void test_SimpleImmutableEntry_hashCode() throws Exception {
        Entry e = new AbstractMap.SimpleImmutableEntry<Integer, String>(1, "test");
        assertEquals((e.getKey() == null ? 0 : e.getKey().hashCode())
                ^ (e.getValue() == null ? 0 : e.getValue().hashCode()), e
                .hashCode());
    }

    public void test_SimpleImmutableEntry_toString() throws Exception {
        Entry e = new AbstractMap.SimpleImmutableEntry<Integer, String>(1, "test");
        assertEquals(e.getKey() + "=" + e.getValue(), e.toString());
        Object array = Array.newInstance((byte[].class).getComponentType(), 10);
        assertEquals(10, ((byte[]) array).length);
    }

    /**
     * serialization/deserialization.
     */
    @SuppressWarnings({ "unchecked", "boxing" })
    public void testSerializationSelf_SimpleImmutableEntry() throws Exception {
        Entry e = new AbstractMap.SimpleImmutableEntry<Integer, String>(1, "test");
        SerializationTest.verifySelf(e);
    }

    /**
     * serialization/deserialization compatibility with RI.
     */
    @SuppressWarnings({ "unchecked", "boxing" })
    public void testSerializationCompatibility_SimpleImmutableEntry() throws Exception {
        SimpleImmutableEntry e = new AbstractMap.SimpleImmutableEntry<Integer, String>(1, "test");
        if (!(SerializationTester.readObject(e, "serialization/org/apache/harmony/tests/java/util/AbstractMapTest_SimpleImmutableEntry.golden.ser") instanceof SimpleImmutableEntry)) {
            fail("should be SimpleImmutableEntry");
        }
        SerializationTester.assertCompabilityEquals(e, "serialization/org/apache/harmony/tests/java/util/AbstractMapTest_SimpleImmutableEntry.golden.ser");
    }
}
