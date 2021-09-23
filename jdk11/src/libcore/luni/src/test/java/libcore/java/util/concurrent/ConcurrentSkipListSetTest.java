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
 * limitations under the License
 */

package libcore.java.util.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ConcurrentSkipListSetTest {

    private ConcurrentSkipListSet createPopulatedSet() {
        ConcurrentSkipListSet set = new ConcurrentSkipListSet();
        set.add("A");
        set.add("B");
        set.add("C");
        set.add("D");
        set.add("E");
        set.add("F");
        assertFalse(set.isEmpty());
        return set;
    }

    @Test
    public void testConstructorFromSortedSet() {
        TreeSet treeSet = new TreeSet();
        treeSet.add("A");
        treeSet.add("B");
        treeSet.add("C");
        treeSet.add("D");
        treeSet.add("E");
        treeSet.add("F");
        ConcurrentSkipListSet skipListSet = new ConcurrentSkipListSet(treeSet);
        assertEquals(treeSet.size(), skipListSet.size());
        while (!treeSet.isEmpty()) {
            assertFalse(skipListSet.isEmpty());
            assertEquals(treeSet.pollFirst(), skipListSet.pollFirst());
        }
    }

    @Test
    public void testClone() {
        ConcurrentSkipListSet set = createPopulatedSet();
        ConcurrentSkipListSet setClone = set.clone();
        assertNotSame(set, setClone);
        Iterator it = set.iterator();
        Iterator itOfClone = setClone.iterator();
        while (it.hasNext() && itOfClone.hasNext()) {
            String entry = (String) it.next();
            String entryOfClone = (String) itOfClone.next();
            assertSame(entry, entryOfClone);
        }
        assertFalse(it.hasNext());
        assertFalse(itOfClone.hasNext());
    }

    @Test
    public void testDescendingIterator() {
        ConcurrentSkipListSet set = createPopulatedSet();
        int size = set.size();
        int i;
        Iterator it = set.descendingIterator();
        String lastVal = null;
        for (i = 0; it.hasNext(); i++) {
            String val = (String) it.next();
            assertTrue(set.contains(val));
            if(lastVal != null) {
                assertTrue(0 <= lastVal.compareTo(val));
            }
            lastVal = val;
        }
        assertEquals(i, size);
    }

}
