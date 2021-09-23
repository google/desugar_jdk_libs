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
 * limitations under the License
 */

package libcore.java.util.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentNavigableMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import libcore.java.util.MapDefaultMethodTester;

@RunWith(JUnit4.class)
public class ConcurrentSkipListMapTest {

    @Test
    public void testGetOrDefault() {
        MapDefaultMethodTester.test_getOrDefault(new ConcurrentSkipListMap<>(),
                false /*doesNotAcceptNullKey*/, false /*doesNotAcceptNullValue*/,
                false /*getAcceptsAnyObject*/);
    }

    @Test
    public void testForEach() {
        MapDefaultMethodTester.test_forEach(new ConcurrentSkipListMap<>());
    }

    @Test
    public void testPutIfAbsent() {
        MapDefaultMethodTester
                .test_putIfAbsent(new ConcurrentSkipListMap<>(), false /*doesNotAcceptNullKey*/,
                        false /*doesNotAcceptNullValue*/);
    }

    @Test
    public void testRemove() {
        MapDefaultMethodTester
                .test_remove(new ConcurrentSkipListMap<>(), false /*doesNotAcceptNullKey*/,
                        false /*doesNotAcceptNullValue*/);
    }

    @Test
    public void testReplace$K$V$V() {
        MapDefaultMethodTester
                .test_replace$K$V$V(new ConcurrentSkipListMap<>(), false /*doesNotAcceptNullKey*/,
                        false /*doesNotAcceptNullValue*/);
    }

    @Test
    public void testReplace$K$V() {
        MapDefaultMethodTester.test_replace$K$V(new ConcurrentSkipListMap<>(),
                false /*doesNotAcceptNullKey*/, false /*doesNotAcceptNullValue*/);
    }

    @Test
    public void testComputeIfAbsent() {
        MapDefaultMethodTester.test_computeIfAbsent(new ConcurrentSkipListMap<>(),
                false /*doesNotAcceptNullKey*/, false /*doesNotAcceptNullValue*/);
    }

    @Test
    public void testComputeIfPresent() {
        MapDefaultMethodTester.test_computeIfPresent(new ConcurrentSkipListMap<>(),
                false /*doesNotAcceptNullKey*/);
    }

    @Test
    public void testCompute() {
        MapDefaultMethodTester.test_compute(new ConcurrentSkipListMap<>(),
                false /*doesNotAcceptNullKey*/);
    }

    @Test
    public void testMerge() {
        MapDefaultMethodTester.test_merge(new ConcurrentSkipListMap<>(),
                false /*doesNotAcceptNullKey*/);
    }

    private ConcurrentSkipListMap createPopulatedMap() {
        ConcurrentSkipListMap map = new ConcurrentSkipListMap();
        map.put("A", "a");
        map.put("B", "b");
        map.put("C", "c");
        map.put("D", "d");
        map.put("E", "e");
        map.put("F", "f");
        assertFalse(map.isEmpty());
        return map;
    }

    @Test
    public void testCloneFromSorted() {
        ConcurrentSkipListMap map = createPopulatedMap();
        ConcurrentSkipListMap mapClone = map.clone();
        assertNotSame(map, mapClone);
        Set set = map.entrySet();
        Set setOfClone = mapClone.entrySet();
        Iterator it = set.iterator();
        Iterator itOfClone = setOfClone.iterator();
        while ( it.hasNext() && itOfClone.hasNext() ) {
            Map.Entry entry = (Map.Entry) it.next();
            Map.Entry entryOfClone = (Map.Entry) itOfClone.next();
            assertSame(entry.getKey(), entryOfClone.getKey());
            assertSame(entry.getValue(), entryOfClone.getValue());
        }
        assertFalse(it.hasNext());
        assertFalse(itOfClone.hasNext());
    }

    @Test
    public void testFirstEntry() {
        ConcurrentSkipListMap map = createPopulatedMap();
        assertEquals("A", map.firstEntry().getKey());
        assertEquals("a", map.firstEntry().getValue());
    }

    @Test
    public void testLastEntry() {
        ConcurrentSkipListMap map = createPopulatedMap();
        assertEquals("F", map.lastEntry().getKey());
        assertEquals("f", map.lastEntry().getValue());
    }

    @Test
    public void testSubMap() {
        ConcurrentSkipListMap map = createPopulatedMap();
        ConcurrentNavigableMap subMap = map.subMap("B", "D");
        assertEquals(2, subMap.size());
        assertFalse(subMap.containsKey("A"));
        assertTrue(subMap.containsKey("B"));
        assertTrue(subMap.containsKey("C"));
        assertFalse(subMap.containsKey("D"));
        assertFalse(subMap.containsKey("E"));
        assertFalse(subMap.containsKey("F"));
    }

    @Test
    public void testSubMapEmpty() {
        ConcurrentSkipListMap map = createPopulatedMap();
        ConcurrentNavigableMap subMap = map.subMap("A", "A");
        assertTrue(subMap.isEmpty());
        assertFalse(subMap.containsKey("A"));
        assertFalse(subMap.containsKey("B"));
        assertFalse(subMap.containsKey("C"));
        assertFalse(subMap.containsKey("D"));
        assertFalse(subMap.containsKey("E"));
        assertFalse(subMap.containsKey("F"));
    }

    @Test
    public void testHeadMap() {
        ConcurrentSkipListMap map = createPopulatedMap();
        ConcurrentNavigableMap subMap = map.headMap("D");
        assertEquals(3, subMap.size());
        assertTrue(subMap.containsKey("A"));
        assertTrue(subMap.containsKey("B"));
        assertTrue(subMap.containsKey("C"));
        assertFalse(subMap.containsKey("D"));
        assertFalse(subMap.containsKey("E"));
        assertFalse(subMap.containsKey("F"));
    }

    @Test
    public void testHeadMapEmpty() {
        ConcurrentSkipListMap map = createPopulatedMap();
        ConcurrentNavigableMap subMap = map.headMap("A");
        assertTrue(subMap.isEmpty());
        assertFalse(subMap.containsKey("A"));
        assertFalse(subMap.containsKey("B"));
        assertFalse(subMap.containsKey("C"));
        assertFalse(subMap.containsKey("D"));
        assertFalse(subMap.containsKey("E"));
        assertFalse(subMap.containsKey("F"));
    }

    @Test
    public void testTailMap() {
        ConcurrentSkipListMap map = createPopulatedMap();
        ConcurrentNavigableMap subMap = map.tailMap("C");
        assertEquals(4, subMap.size());
        assertFalse(subMap.containsKey("A"));
        assertFalse(subMap.containsKey("B"));
        assertTrue(subMap.containsKey("C"));
        assertTrue(subMap.containsKey("D"));
        assertTrue(subMap.containsKey("E"));
        assertTrue(subMap.containsKey("F"));
    }

    @Test
    public void testTailMapSingleElement() {
        ConcurrentSkipListMap map = createPopulatedMap();
        ConcurrentNavigableMap subMap = map.tailMap("F");
        assertEquals(1, subMap.size());
        assertFalse(subMap.containsKey("A"));
        assertFalse(subMap.containsKey("B"));
        assertFalse(subMap.containsKey("C"));
        assertFalse(subMap.containsKey("D"));
        assertFalse(subMap.containsKey("E"));
        assertTrue(subMap.containsKey("F"));
    }

}
