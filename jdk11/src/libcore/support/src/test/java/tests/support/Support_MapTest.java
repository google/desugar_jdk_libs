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

package tests.support;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import junit.framework.TestCase;

public class Support_MapTest extends TestCase {

    // must be a map containing the string keys "0"-"99" paired with the Integer
    // values Integer(0) to Integer(99)
    private final Map<String, Integer> modifiableMap;
    private final Map<String, Integer> unmodifiableMap;

    public Support_MapTest(String p1, Map<String, Integer> modifiableMap) {
        super(p1);
        this.modifiableMap = modifiableMap;
        unmodifiableMap = Collections.unmodifiableMap(modifiableMap);
    }

    @Override
    public void runTest() {
        testContents(modifiableMap);
        testContents(unmodifiableMap);

        // values()
        new Support_UnmodifiableCollectionTest("values() from map test", modifiableMap.values())
                .runTest();
        new Support_UnmodifiableCollectionTest("values() from unmodifiable map test",
                unmodifiableMap.values()).runTest();

        // entrySet()
        testEntrySet(modifiableMap.entrySet(), unmodifiableMap.entrySet());

        // keySet()
        testKeySet(modifiableMap.keySet(), unmodifiableMap.keySet());
    }

    private void testContents(Map<String, Integer> map) {
        // size
        assertTrue("Size should return 100, returned: " + map.size(), map.size() == 100);

        // containsKey
        assertTrue("Should contain the key \"0\"", map.containsKey("0"));
        assertTrue("Should contain the key \"50\"", map.containsKey("50"));
        assertTrue("Should not contain the key \"100\"", !map.containsKey("100"));

        // containsValue
        assertTrue("Should contain the value 0", map.containsValue(0));
        assertTrue("Should contain the value 50", map.containsValue(50));
        assertTrue("Should not contain value 100", !map.containsValue(100));

        // get
        assertTrue("getting \"0\" didn't return 0", map.get("0") == 0);
        assertTrue("getting \"50\" didn't return 50", map.get("50") == 50);
        assertNull("getting \"100\" didn't return null", map.get("100"));

        // isEmpty
        assertTrue("should have returned false to isEmpty", !map.isEmpty());
    }

    private static void testEntrySet(
            Set<Map.Entry<String, Integer>> referenceEntrySet,
            Set<Map.Entry<String, Integer>> entrySet) {
        // entrySet should be a set of mappings {"0", 0}, {"1",1}... {"99", 99}
        assertEquals(100, referenceEntrySet.size());
        assertEquals(100, entrySet.size());

        // The ordering may be undefined for a map implementation but the ordering must be the
        // same across iterator(), toArray() and toArray(T[]) for a given map *and* the same for the
        // modifiable and unmodifiable map.
        crossCheckOrdering(referenceEntrySet, entrySet, Map.Entry.class);
    }

    private static void testKeySet(Set<String> referenceKeySet, Set<String> keySet) {
        // keySet should be a set of the strings "0" to "99"
        testKeySetContents(referenceKeySet);
        testKeySetContents(keySet);

        // The ordering may be undefined for a map implementation but the ordering must be the
        // same across iterator(), toArray() and toArray(T[]) for a given map *and* the same for the
        // modifiable and unmodifiable map.
        crossCheckOrdering(referenceKeySet, keySet, String.class);
    }

    private static void testKeySetContents(Set<String> keySet) {
        // contains
        assertTrue("should contain \"0\"", keySet.contains("0"));
        assertTrue("should contain \"50\"", keySet.contains("50"));
        assertTrue("should not contain \"100\"", !keySet.contains("100"));

        // containsAll
        HashSet<String> hs = new HashSet<String>();
        hs.add("0");
        hs.add("25");
        hs.add("99");
        assertTrue("Should contain set of \"0\", \"25\", and \"99\"", keySet.containsAll(hs));
        hs.add("100");
        assertTrue("Should not contain set of \"0\", \"25\", \"99\" and \"100\"",
                !keySet.containsAll(hs));

        // isEmpty
        assertTrue("Should not be empty", !keySet.isEmpty());

        // size
        assertEquals("Returned wrong size.", 100, keySet.size());
    }

    private static <T> void crossCheckOrdering(Set<T> set1, Set<T> set2, Class<?> elementType) {
        Iterator<T> set1Iterator = set1.iterator();
        Iterator<T> set2Iterator = set2.iterator();

        T[] zeroLengthArray = createArray(elementType, 0);
        T[] set1TypedArray1 = set1.toArray(zeroLengthArray);
        assertEquals(set1.size(), set1TypedArray1.length);

        // Compare set1.iterator(), set2.iterator() and set1.toArray(new T[0])
        int entryCount = 0;
        while (set1Iterator.hasNext()) {
            T set1Entry = set1Iterator.next();
            T set2Entry = set2Iterator.next();

            // Compare set1 with set2
            assertEquals(set1Entry, set2Entry);

            // Compare the iterator with the array. The arrays will be checked against each other.
            assertEquals(set1Entry, set1TypedArray1[entryCount]);

            entryCount++;
        }
        assertFalse(set2Iterator.hasNext());
        assertEquals(set1.size(), entryCount);

        // Compare the various arrays with each other.

        // set1.toArray(new T[size])
        T[] parameterArray1 = createArray(elementType, set1.size());
        T[] set1TypedArray2 = set1.toArray(parameterArray1);
        assertSame(set1TypedArray2, parameterArray1);
        assertArrayEquals(set1TypedArray1, set1TypedArray2);

        // set1.toArray()
        Object[] set1UntypedArray = set1.toArray();
        assertEquals(set1.size(), set1UntypedArray.length);
        assertArrayEquals(set1TypedArray1, set1UntypedArray);

        // set2.toArray(new T[0])
        T[] set2TypedArray1 = set2.toArray(zeroLengthArray);
        assertEquals(set1.size(), set2TypedArray1.length);
        assertArrayEquals(set1TypedArray1, set2TypedArray1);

        // set2.toArray(new T[size])
        T[] parameterArray2 = createArray(elementType, set2.size());
        T[] set2TypedArray2 = set1.toArray(parameterArray2);
        assertSame(set2TypedArray2, parameterArray2);
        assertArrayEquals(set1TypedArray1, set1TypedArray2);

        // set2.toArray()
        Object[] set2UntypedArray = set2.toArray();
        assertArrayEquals(set1TypedArray1, set2UntypedArray);
    }

    private static <T> void assertArrayEquals(T[] array1, T[] array2) {
        assertTrue(Arrays.equals(array1, array2));
    }

    private static <T> T[] createArray(Class<?> elementType, int size) {
        return (T[]) Array.newInstance(elementType, size);
    }
}
