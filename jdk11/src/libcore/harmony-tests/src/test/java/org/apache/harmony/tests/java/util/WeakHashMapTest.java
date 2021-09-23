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

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.WeakHashMap;
import libcore.java.lang.ref.FinalizationTester;

import libcore.java.util.SpliteratorTester;
import tests.support.Support_MapTest2;

public class WeakHashMapTest extends junit.framework.TestCase {
    class MockMap extends AbstractMap {
        public Set entrySet() {
            return null;
        }

        public int size() {
            return 0;
        }
    }

    Object[] keyArray = new Object[100];

    Object[] valueArray = new Object[100];

    WeakHashMap whm;

    /**
     * java.util.WeakHashMap#WeakHashMap()
     */
    public void test_Constructor() {
        // Test for method java.util.WeakHashMap()
        new Support_MapTest2(new WeakHashMap()).runTest();

        whm = new WeakHashMap();
        for (int i = 0; i < 100; i++)
            whm.put(keyArray[i], valueArray[i]);
        for (int i = 0; i < 100; i++)
            assertTrue("Incorrect value retrieved", whm.get(keyArray[i]) == valueArray[i]);

    }

    /**
     * java.util.WeakHashMap#WeakHashMap(int)
     */
    public void test_ConstructorI() {
        // Test for method java.util.WeakHashMap(int)
        whm = new WeakHashMap(50);
        for (int i = 0; i < 100; i++)
            whm.put(keyArray[i], valueArray[i]);
        for (int i = 0; i < 100; i++)
            assertTrue("Incorrect value retrieved", whm.get(keyArray[i]) == valueArray[i]);

        WeakHashMap empty = new WeakHashMap(0);
        assertNull("Empty weakhashmap access", empty.get("nothing"));
        empty.put("something", "here");
        assertTrue("cannot get element", empty.get("something") == "here");

        try {
            new WeakHashMap(-50);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * java.util.WeakHashMap#WeakHashMap(int, float)
     */
    public void test_ConstructorIF() {
        // Test for method java.util.WeakHashMap(int, float)
        whm = new WeakHashMap(50, 0.5f);
        for (int i = 0; i < 100; i++)
            whm.put(keyArray[i], valueArray[i]);
        for (int i = 0; i < 100; i++)
            assertTrue("Incorrect value retrieved", whm.get(keyArray[i]) == valueArray[i]);

        WeakHashMap empty = new WeakHashMap(0, 0.75f);
        assertNull("Empty hashtable access", empty.get("nothing"));
        empty.put("something", "here");
        assertTrue("cannot get element", empty.get("something") == "here");

        try {
            new WeakHashMap(50, -0.5f);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * java.util.WeakHashMap#WeakHashMap(java.util.Map)
     */
    public void test_ConstructorLjava_util_Map() {
        Map mockMap = new MockMap();
        WeakHashMap map = new WeakHashMap(mockMap);
        assertEquals("Size should be 0", 0, map.size());

        try {
            new WeakHashMap(null);
            fail("NullPointerException expected");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * java.util.WeakHashMap#clear()
     */
    public void test_clear() {
        // Test for method boolean java.util.WeakHashMap.clear()
        whm = new WeakHashMap();
        for (int i = 0; i < 100; i++)
            whm.put(keyArray[i], valueArray[i]);
        whm.clear();
        assertTrue("Cleared map should be empty", whm.isEmpty());
        for (int i = 0; i < 100; i++)
            assertNull("Cleared map should only return null", whm.get(keyArray[i]));

    }

    /**
     * java.util.WeakHashMap#containsKey(java.lang.Object)
     */
    public void test_containsKeyLjava_lang_Object() {
        // Test for method boolean java.util.WeakHashMap.containsKey()
        whm = new WeakHashMap();
        for (int i = 0; i < 100; i++)
            whm.put(keyArray[i], valueArray[i]);
        for (int i = 0; i < 100; i++)
            assertTrue("Should contain referenced key", whm.containsKey(keyArray[i]));
        keyArray[25] = null;
        keyArray[50] = null;
    }

    /**
     * java.util.WeakHashMap#containsValue(java.lang.Object)
     */
    public void test_containsValueLjava_lang_Object() {
        // Test for method boolean java.util.WeakHashMap.containsValue()
        whm = new WeakHashMap();
        for (int i = 0; i < 100; i++)
            whm.put(keyArray[i], valueArray[i]);
        for (int i = 0; i < 100; i++)
            assertTrue("Should contain referenced value", whm.containsValue(valueArray[i]));
        keyArray[25] = null;
        keyArray[50] = null;
    }

    /**
     * java.util.WeakHashMap#entrySet()
     */
    public void test_entrySet() {
        // Test for method java.util.Set java.util.WeakHashMap.entrySet()
        whm = new WeakHashMap();
        for (int i = 0; i < 100; i++)
            whm.put(keyArray[i], valueArray[i]);
        List keys = Arrays.asList(keyArray);
        List values = Arrays.asList(valueArray);
        Set entrySet = whm.entrySet();
        assertTrue("Incorrect number of entries returned--wanted 100, got: " + entrySet.size(),
            entrySet.size() == 100);
        Iterator it = entrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            assertTrue("Invalid map entry returned--bad key", keys.contains(entry.getKey()));
            assertTrue("Invalid map entry returned--bad key", values.contains(entry.getValue()));
        }
        keys = null;
        values = null;
        keyArray[50] = null;

        FinalizationTester.induceFinalization();
        long startTime = System.currentTimeMillis();
        // We use a busy wait loop here since we cannot know when the ReferenceQueue
        // daemon will enqueue the cleared references on their internal reference
        // queues. The current timeout is 5 seconds.
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        } while (entrySet.size() != 99 &&
                 System.currentTimeMillis() - startTime < 5000);

        assertEquals("Incorrect number of keys returned after gc,", 99, entrySet.size());
    }

    /**
     * java.util.WeakHashMap#isEmpty()
     */
    public void test_isEmpty() {
        // Test for method boolean java.util.WeakHashMap.isEmpty()
        whm = new WeakHashMap();
        assertTrue("New map should be empty", whm.isEmpty());
        Object myObject = new Object();
        whm.put(myObject, myObject);
        assertTrue("Map should not be empty", !whm.isEmpty());
        whm.remove(myObject);
        assertTrue("Map with elements removed should be empty", whm.isEmpty());
    }

    /**
     * java.util.WeakHashMap#put(java.lang.Object, java.lang.Object)
     */
    public void test_putLjava_lang_ObjectLjava_lang_Object() {
        // Test for method java.lang.Object
        // java.util.WeakHashMap.put(java.lang.Object, java.lang.Object)
        WeakHashMap map = new WeakHashMap();
        map.put(null, "value"); // add null key
        System.gc();
        System.gc();
        FinalizationTester.induceFinalization();
        map.remove("nothing"); // Cause objects in queue to be removed
        assertEquals("null key was removed", 1, map.size());
    }

    /**
     * java.util.WeakHashMap#putAll(java.util.Map)
     */
    public void test_putAllLjava_util_Map() {
        Map mockMap = new MockMap();
        WeakHashMap map = new WeakHashMap();
        map.putAll(mockMap);
        assertEquals("Size should be 0", 0, map.size());

        try {
            map.putAll(null);
            fail("NullPointerException exected");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * java.util.WeakHashMap#remove(java.lang.Object)
     */
    public void test_removeLjava_lang_Object() {
        // Test for method java.lang.Object
        // java.util.WeakHashMap.remove(java.lang.Object)
        whm = new WeakHashMap();
        for (int i = 0; i < 100; i++)
            whm.put(keyArray[i], valueArray[i]);

        assertTrue("Remove returned incorrect value", whm.remove(keyArray[25]) == valueArray[25]);
        assertNull("Remove returned incorrect value", whm.remove(keyArray[25]));
        assertEquals("Size should be 99 after remove", 99, whm.size());
    }

    /**
     * java.util.WeakHashMap#size()
     */
    public void test_size() {
        whm = new WeakHashMap();
        assertEquals(0, whm.size());
    }

    /**
     * java.util.WeakHashMap#keySet()
     */
    public void test_keySet() {
        // Test for method java.util.Set java.util.WeakHashMap.keySet()
        whm = new WeakHashMap();
        for (int i = 0; i < 100; i++)
            whm.put(keyArray[i], valueArray[i]);

        List keys = Arrays.asList(keyArray);
        List values = Arrays.asList(valueArray);

        Set keySet = whm.keySet();
        assertEquals("Incorrect number of keys returned,", 100, keySet.size());
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            Object key = it.next();
            assertTrue("Invalid map entry returned--bad key", keys.contains(key));
        }
        keys = null;
        values = null;
        keyArray[50] = null;

        FinalizationTester.induceFinalization();
        long startTime = System.currentTimeMillis();
        // We use a busy wait loop here since we cannot know when the ReferenceQueue
        // daemon will enqueue the cleared references on their internal reference
        // queues.
        // The timeout after which the reference should be cleared. This test used to
        // be flaky when it was set to 5 seconds. Daemons.MAX_FINALIZE_NANOS is
        // currently 10 seconds so that seems like the correct value.
        // We allow an extra 500msec buffer to minimize races between finalizer,
        // keySet.size() evaluation and time check.
        long timeout = 10000 + 500;
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        } while (keySet.size() != 99 &&
                 System.currentTimeMillis() - startTime < timeout);

        assertEquals("Incorrect number of keys returned after gc,", 99, keySet.size());
    }

    /**
     * Regression test for HARMONY-3883
     *
     * java.util.WeakHashMap#keySet()
     */
    public void test_keySet_hasNext() {
        WeakHashMap map = new WeakHashMap();
        ConstantHashClass cl = new ConstantHashClass(2);
        map.put(new ConstantHashClass(1), null);
        map.put(cl, null);
        map.put(new ConstantHashClass(3), null);
        Iterator iter = map.keySet().iterator();
        iter.next();
        iter.next();
        int count = 0;
        do {
            System.gc();
            System.gc();
            FinalizationTester.induceFinalization();
            count++;
        } while (count <= 5);
        assertFalse("Wrong hasNext() value", iter.hasNext());
    }

    static class ConstantHashClass {
        private int id = 0;

        public ConstantHashClass(int id) {
            this.id = id;
        }

        public int hashCode() {
            return 0;
        }

        public String toString() {
            return "ConstantHashClass[id=" + id + "]";
        }
    }


    /**
     * java.util.WeakHashMap#values()
     */
    public void test_values() {
        // Test for method java.util.Set java.util.WeakHashMap.values()
        whm = new WeakHashMap();
        for (int i = 0; i < 100; i++)
            whm.put(keyArray[i], valueArray[i]);

        List keys = Arrays.asList(keyArray);
        List values = Arrays.asList(valueArray);

        Collection valuesCollection = whm.values();
        assertEquals("Incorrect number of keys returned,", 100, valuesCollection.size());
        Iterator it = valuesCollection.iterator();
        while (it.hasNext()) {
            Object value = it.next();
            assertTrue("Invalid map entry returned--bad value", values.contains(value));
        }
        keys = null;
        values = null;
        keyArray[50] = null;

        FinalizationTester.induceFinalization();
        long startTime = System.currentTimeMillis();
        // We use a busy wait loop here since we cannot know when the ReferenceQueue
        // daemon will enqueue the cleared references on their internal reference
        // queues. The current timeout is 5 seconds.
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        } while (valuesCollection.size() != 99 &&
                 System.currentTimeMillis() - startTime < 5000);

        assertEquals("Incorrect number of keys returned after gc,", 99, valuesCollection.size());
    }

    public void test_forEach() throws Exception {
        WeakHashMap map = new WeakHashMap();
        for (int i = 0; i < 100; i++)
            map.put(keyArray[i], valueArray[i]);

        WeakHashMap output = new WeakHashMap();
        map.forEach((k, v) -> output.put(k,v));
        assertEquals(map, output);

        HashSet setOutput = new HashSet();
        map.keySet().forEach((k) -> setOutput.add(k));
        assertEquals(map.keySet(), setOutput);

        setOutput.clear();
        map.values().forEach((v) -> setOutput.add(v));
        assertEquals(new HashSet(map.values()), setOutput);

        HashSet entrySetOutput = new HashSet();
        map.entrySet().forEach((v) -> entrySetOutput.add(v));
        assertEquals(map.entrySet(), entrySetOutput);
    }

    public void test_forEach_NPE() throws Exception {
        WeakHashMap map = new WeakHashMap();
        try {
            map.forEach(null);
            fail();
        } catch(NullPointerException expected) {}

        try {
            map.keySet().forEach(null);
            fail();
        } catch(NullPointerException expected) {}

        try {
            map.values().forEach(null);
            fail();
        } catch(NullPointerException expected) {}

        try {
            map.entrySet().forEach(null);
            fail();
        } catch(NullPointerException expected) {}

    }

    public void test_forEach_CME() throws Exception {
        WeakHashMap map = new WeakHashMap();
        for (int i = 0; i < 100; i++)
            map.put(keyArray[i], valueArray[i]);
        ArrayList<Object> processed = new ArrayList<>();
        try {
            map.forEach(new java.util.function.BiConsumer<Object, Object>() {
                    @Override
                    public void accept(Object k, Object v) {
                        processed.add(k);
                        map.put("foo", v);
                    }
                });
            fail();
        } catch(ConcurrentModificationException expected) {}
        // We should get a CME and DO NOT continue forEach evaluation
        assertEquals(1, processed.size());

        processed.clear();
        try {
            map.keySet().forEach(new java.util.function.Consumer<Object>() {
                    @Override
                    public void accept(Object k) {
                        processed.add(k);
                        map.put("foo2", "boo");
                    }
                });
            fail();
        } catch(ConcurrentModificationException expected) {}
        // We should get a CME and DO NOT continue forEach evaluation
        assertEquals(1, processed.size());

        processed.clear();
        try {
            map.values().forEach(new java.util.function.Consumer<Object>() {
                    @Override
                    public void accept(Object k) {
                        processed.add(k);
                        map.put("foo3", "boo");
                    }
                });
            fail();
        } catch(ConcurrentModificationException expected) {}
        // We should get a CME and DO NOT continue forEach evaluation
        assertEquals(1, processed.size());

        processed.clear();
        try {
            map.entrySet().forEach(new java.util.function.Consumer<Map.Entry<Object, Object>>() {
                    @Override
                    public void accept(Map.Entry<Object, Object> k) {
                        processed.add(k.getKey());
                        map.put("foo4", "boo");
                    }
                });
            fail();
        } catch(ConcurrentModificationException expected) {}
        // We should get a CME and DO NOT continue forEach evaluation
        assertEquals(1, processed.size());
    }

    public void test_spliterator_keySet() {
        WeakHashMap<String, String> hashMap = new WeakHashMap<>();
        hashMap.put("a", "1");
        hashMap.put("b", "2");
        hashMap.put("c", "3");
        hashMap.put("d", "4");
        hashMap.put("e", "5");
        hashMap.put("f", "6");
        hashMap.put("g", "7");
        hashMap.put("h", "8");
        hashMap.put("i", "9");
        hashMap.put("j", "10");
        hashMap.put("k", "11");
        hashMap.put("l", "12");
        hashMap.put("m", "13");
        hashMap.put("n", "14");
        hashMap.put("o", "15");
        hashMap.put("p", "16");

        Set<String> keys = hashMap.keySet();
        ArrayList<String> expectedKeys = new ArrayList<>(keys);

        SpliteratorTester.runBasicIterationTests_unordered(keys.spliterator(), expectedKeys,
                String::compareTo);
        SpliteratorTester.runBasicSplitTests(keys, expectedKeys);
        SpliteratorTester.testSpliteratorNPE(keys.spliterator());

        assertTrue(keys.spliterator().hasCharacteristics(Spliterator.DISTINCT));

        SpliteratorTester.runDistinctTests(keys);
        SpliteratorTester.assertSupportsTrySplit(keys);
    }

    public void test_spliterator_valueSet() {
        WeakHashMap<String, String> hashMap = new WeakHashMap<>();
        hashMap.put("a", "1");
        hashMap.put("b", "2");
        hashMap.put("c", "3");
        hashMap.put("d", "4");
        hashMap.put("e", "5");
        hashMap.put("f", "6");
        hashMap.put("g", "7");
        hashMap.put("h", "8");
        hashMap.put("i", "9");
        hashMap.put("j", "10");
        hashMap.put("k", "11");
        hashMap.put("l", "12");
        hashMap.put("m", "13");
        hashMap.put("n", "14");
        hashMap.put("o", "15");
        hashMap.put("p", "16");

        Collection<String> values = hashMap.values();
        ArrayList<String> expectedValues = new ArrayList<>(values);

        SpliteratorTester.runBasicIterationTests_unordered(
                values.spliterator(), expectedValues, String::compareTo);
        SpliteratorTester.runBasicSplitTests(values, expectedValues);
        SpliteratorTester.testSpliteratorNPE(values.spliterator());
    }

    public void test_spliterator_entrySet() {
        WeakHashMap<String, String> hashMap = new WeakHashMap<>();
        hashMap.put("a", "1");
        hashMap.put("b", "2");
        hashMap.put("c", "3");
        hashMap.put("d", "4");
        hashMap.put("e", "5");
        hashMap.put("f", "6");
        hashMap.put("g", "7");
        hashMap.put("h", "8");
        hashMap.put("i", "9");
        hashMap.put("j", "10");
        hashMap.put("k", "11");
        hashMap.put("l", "12");
        hashMap.put("m", "13");
        hashMap.put("n", "14");
        hashMap.put("o", "15");
        hashMap.put("p", "16");

        Set<Map.Entry<String, String>> values = hashMap.entrySet();
        ArrayList<Map.Entry<String, String>> expectedValues = new ArrayList<>(values);

        Comparator<Map.Entry<String, String>> comparator =
                (a, b) -> (a.getKey().compareTo(b.getKey()));

        SpliteratorTester.runBasicIterationTests_unordered(values.spliterator(), expectedValues,
                (a, b) -> (a.getKey().compareTo(b.getKey())));
        SpliteratorTester.runBasicSplitTests(values, expectedValues, comparator);
        SpliteratorTester.testSpliteratorNPE(values.spliterator());

        assertTrue(values.spliterator().hasCharacteristics(Spliterator.DISTINCT));

        SpliteratorTester.runDistinctTests(values);
        SpliteratorTester.assertSupportsTrySplit(values);
    }

    /**
     * Sets up the fixture, for example, open a network connection. This method
     * is called before a test is executed.
     */
    protected void setUp() {
        for (int i = 0; i < 100; i++) {
            keyArray[i] = new Object();
            valueArray[i] = new Object();
        }

    }

    /**
     * Tears down the fixture, for example, close a network connection. This
     * method is called after a test is executed.
     */
    protected void tearDown() {
    }
}
