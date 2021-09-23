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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import libcore.java.util.MapDefaultMethodTester;

@RunWith(JUnit4.class)
public class ConcurrentHashMapTest {

    // Constants that dictate parallelism for 'reduce' calls. The value represents the number of
    // elements needed for the operation to be executed in parallel.
    static final long IN_PARALLEL = 1L;
    static final long SEQUENTIALLY = Long.MAX_VALUE;

    static final int MAP_SIZE = 100;

    static class SumKeys implements BiFunction<Map.Entry<Long,Long>,
                 Map.Entry<Long,Long>, Map.Entry<Long,Long>> {
        public Map.Entry<Long,Long> apply(Map.Entry<Long,Long> x, Map.Entry<Long,Long> y) {
            return new AbstractMap.SimpleEntry<Long,Long>
             (Long.valueOf(x.getKey().longValue() + y.getKey().longValue()),
              Long.valueOf(1L));
        }
    }

    static class IncrementKey implements Function<Map.Entry<Long, Long>, Map.Entry<Long, Long>> {
        public Map.Entry<Long, Long> apply(Map.Entry<Long, Long> in) {
            return new AbstractMap.SimpleEntry<Long, Long>
                (Long.valueOf(in.getKey().longValue() + 1),
                 Long.valueOf(1L));
        }
    }

    static class KeyAsDouble implements ToDoubleFunction<Map.Entry<Long, Long>> {
        public double applyAsDouble(Map.Entry<Long, Long> in) {
            return in.getKey().doubleValue();
        }
    }

    static class KeyAsInt implements ToIntFunction<Map.Entry<Long, Long>> {
        public int applyAsInt(Map.Entry<Long, Long> in) {
            return in.getKey().intValue();
        }
    }

    static class KeyAsLong implements ToLongFunction<Map.Entry<Long, Long>> {
        public long applyAsLong(Map.Entry<Long, Long> in) {
            return in.getKey().longValue();
        }
    }

    static class IncrementKeyToDouble implements ToDoubleBiFunction<Long, Long> {
        public double applyAsDouble(Long key, Long value) {
            return (key.doubleValue() + 1);
        }
    }

    static class IncrementKeyToInt implements ToIntBiFunction<Long, Long> {
        public int applyAsInt(Long key, Long value) {
            return (key.intValue() + 1);
        }
    }

    static class IncrementKeyToLong implements ToLongBiFunction<Long, Long> {
        public long applyAsLong(Long key, Long value) {
            return (key.longValue() + 1);
        }
    }

    static ConcurrentHashMap<Long, Long> createMap() {
        ConcurrentHashMap<Long, Long> map = new ConcurrentHashMap<Long, Long>(MAP_SIZE);
        for (int i = 0; i < MAP_SIZE; ++i) {
            map.put(Long.valueOf(i), Long.valueOf(-i));
        }
        return map;
    }

    @Test
    public void testReduceEntriesToDoubleSequentially() {
        ConcurrentHashMap<Long, Long> map = createMap();
        double result = map.reduceEntriesToDouble(SEQUENTIALLY,
                new KeyAsDouble(), 0.0, Double::sum);
        assertEquals((double)MAP_SIZE * (MAP_SIZE - 1) / 2, result, 0.5);
    }

    @Test
    public void testReduceEntriesToDoubleInParallel() {
        ConcurrentHashMap<Long, Long> map = createMap();
        double result = map.reduceEntriesToDouble(IN_PARALLEL,
                new KeyAsDouble(), 0.0, Double::sum);
        assertEquals((double)MAP_SIZE * (MAP_SIZE - 1) / 2, result, 0.5);
    }

    @Test
    public void testReduceEntriesToIntSequentially() {
        ConcurrentHashMap<Long, Long> map = createMap();
        int result = map.reduceEntriesToInt(SEQUENTIALLY, new KeyAsInt(), 0, Integer::sum);
        assertEquals((int)MAP_SIZE * (MAP_SIZE - 1) / 2, result);
    }

    @Test
    public void testReduceEntriesToIntInParallel() {
        ConcurrentHashMap<Long, Long> map = createMap();
        int result = map.reduceEntriesToInt(IN_PARALLEL, new KeyAsInt(), 0, Integer::sum);
        assertEquals(MAP_SIZE * (MAP_SIZE - 1) / 2, result);
    }

    @Test
    public void testReduceEntriesToLongSequentially() {
        ConcurrentHashMap<Long, Long> map = createMap();
        long result = map.reduceEntriesToLong(SEQUENTIALLY, new KeyAsLong(), 0L, Long::sum);
        assertEquals((long)MAP_SIZE * (MAP_SIZE - 1) / 2, result);
    }

    @Test
    public void testReduceEntriesToLongInParallel() {
        ConcurrentHashMap<Long, Long> map = createMap();
        long result = map.reduceEntriesToLong(IN_PARALLEL, new KeyAsLong(), 0L, Long::sum);
        assertEquals((long)MAP_SIZE * (MAP_SIZE - 1) / 2, result);
    }

    @Test
    public void testTransformReduceEntriesSequentially() {
        ConcurrentHashMap<Long, Long> map = createMap();
        Map.Entry<Long, Long> result;
        result = map.reduceEntries(SEQUENTIALLY, new IncrementKey(), new SumKeys());
        assertEquals((long)MAP_SIZE * (MAP_SIZE + 1) / 2, result.getKey().longValue());
    }

    @Test
    public void testTransformReduceEntriesInParallel() {
        ConcurrentHashMap<Long, Long> map = createMap();
        Map.Entry<Long, Long> result;
        result = map.reduceEntries(IN_PARALLEL, new IncrementKey(), new SumKeys());
        assertEquals((long)MAP_SIZE * (MAP_SIZE + 1) / 2, result.getKey().longValue());
    }

    @Test
    public void testTransformReduceEntriesToDoubleSequentially() {
        ConcurrentHashMap<Long, Long> map = createMap();
        double result = map.reduceToDouble(SEQUENTIALLY,
                new IncrementKeyToDouble(), 0.0, Double::sum);
        assertEquals((double)MAP_SIZE * (MAP_SIZE + 1) / 2, result, 0.5);
    }

    @Test
    public void testTransformReduceEntriesToDoubleInParallel() {
        ConcurrentHashMap<Long, Long> map = createMap();
        double result = map.reduceToDouble(IN_PARALLEL,
                new IncrementKeyToDouble(), 0.0, Double::sum);
        assertEquals((double)MAP_SIZE * (MAP_SIZE + 1) / 2, result, 0.5);
    }

    @Test
    public void testTransformReduceEntriesToIntSequentially() {
        ConcurrentHashMap<Long, Long> map = createMap();
        int result = map.reduceToInt(SEQUENTIALLY, new IncrementKeyToInt(), 0, Integer::sum);
        assertEquals(MAP_SIZE * (MAP_SIZE + 1) / 2, result);
    }

    @Test
    public void testTransformReduceEntriesToIntInParallel() {
        ConcurrentHashMap<Long, Long> map = createMap();
        int result = map.reduceToInt(IN_PARALLEL, new IncrementKeyToInt(), 0, Integer::sum);
        assertEquals(MAP_SIZE * (MAP_SIZE + 1) / 2, result);
    }

    @Test
    public void testTransformReduceEntriesToLongSequentially() {
        ConcurrentHashMap<Long, Long> map = createMap();
        long result = map.reduceToLong(SEQUENTIALLY, new IncrementKeyToLong(), 0L, Long::sum);
        assertEquals((long)MAP_SIZE * (MAP_SIZE + 1) / 2, result);
    }

    @Test
    public void testTransformReduceEntriesToLongInParallel() {
        ConcurrentHashMap<Long, Long> map = createMap();
        long result = map.reduceToLong(IN_PARALLEL, new IncrementKeyToLong(), 0L, Long::sum);
        assertEquals((long)MAP_SIZE * (MAP_SIZE + 1) / 2, result);
    }

    @Test
    public void testNewKeySetWithCapacity() {
        final int capacity = 10;
        Set<Long> set = ConcurrentHashMap.<Long>newKeySet(capacity);
        assertTrue(set.isEmpty());
        for (long i = 0; i < capacity; ++i) {
            assertTrue(set.add(i));
        }
        assertFalse(set.isEmpty());
        assertEquals(capacity, set.size());
    }

    @Test
    public void testNewKeySetWithZeroCapacity() {
        final int capacity = 0;
        final int elements = 10;
        Set<Long> set = ConcurrentHashMap.<Long>newKeySet(capacity);
        assertTrue(set.isEmpty());
        for (long i = 0; i < elements; ++i) {
            assertTrue(set.add(i));
        }
        assertFalse(set.isEmpty());
        assertEquals(elements, set.size());
    }

    @Test
    public void testNewKeySetWithInvalidCapacity() {
        try {
            final int capacity = -10;
            Set<Long> set = ConcurrentHashMap.<Long>newKeySet(capacity);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        } catch (Throwable t) {
            fail("Unexpected exception: " + t.getMessage());
        }
    }

    @Test
    public void testGetOrDefault() {
        MapDefaultMethodTester.test_getOrDefault(new ConcurrentHashMap<>(),
                false /*doesNotAcceptNullKey*/, false /*doesNotAcceptNullValue*/,
                true /*getAcceptsAnyObject*/);
    }

    @Test
    public void testForEach() {
        MapDefaultMethodTester.test_forEach(new ConcurrentHashMap<>());
    }

    @Test
    public void testPutIfAbsent() {
        MapDefaultMethodTester
                .test_putIfAbsent(new ConcurrentHashMap<>(), false /*doesNotAcceptNullKey*/,
                        false /*doesNotAcceptNullValue*/);
    }

    @Test
    public void testRemove() {
        MapDefaultMethodTester
                .test_remove(new ConcurrentHashMap<>(), false /*doesNotAcceptNullKey*/,
                        false /*doesNotAcceptNullValue*/);
    }

    @Test
    public void testReplace$K$V$V() {
        MapDefaultMethodTester
                .test_replace$K$V$V(new ConcurrentHashMap<>(), false /*doesNotAcceptNullKey*/,
                        false /*doesNotAcceptNullValue*/);
    }

    @Test
    public void testReplace$K$V() {
        MapDefaultMethodTester.test_replace$K$V(new ConcurrentHashMap<>(),
                false /*doesNotAcceptNullKey*/, false /*doesNotAcceptNullValue*/);
    }

    @Test
    public void testComputeIfAbsent() {
        MapDefaultMethodTester.test_computeIfAbsent(new ConcurrentHashMap<>(),
                false /*doesNotAcceptNullKey*/, false /*doesNotAcceptNullValue*/);
    }

    @Test
    public void testComputeIfPresent() {
        MapDefaultMethodTester.test_computeIfPresent(new ConcurrentHashMap<>(),
                false /*doesNotAcceptNullKey*/);
    }

    @Test
    public void testCompute() {
        MapDefaultMethodTester
                .test_compute(new ConcurrentHashMap<>(), false /*doesNotAcceptNullKey*/);
    }

    @Test
    public void testMerge() {
        MapDefaultMethodTester.test_merge(new ConcurrentHashMap<>(),
                false /*doesNotAcceptNullKey*/);
    }

    private ConcurrentHashMap.KeySetView<String, Boolean> createKeySet() {
        final int count = 5;
        ConcurrentHashMap.KeySetView<String, Boolean> set =
            ConcurrentHashMap.<String>newKeySet(count);
        assertTrue(set.isEmpty());
        set.add("A");
        set.add("B");
        set.add("C");
        set.add("D");
        set.add("E");
        assertFalse(set.isEmpty());
        assertEquals(count, set.size());
        return set;
    }

    @Test
    public void testKeySetViewClear() {
        ConcurrentHashMap.KeySetView set = createKeySet();
        assertFalse(set.isEmpty());
        set.clear();
        assertEquals(0, set.size());
        assertTrue(set.isEmpty());
    }

    @Test
    public void testKeySetViewContainsAll() {
        ConcurrentHashMap.KeySetView set = createKeySet();
        assertTrue(set.containsAll(Arrays.asList()));
        assertTrue(set.containsAll(Arrays.asList("A")));
        assertTrue(set.containsAll(Arrays.asList("A", "E")));
        assertTrue(set.containsAll(Arrays.asList("A", "B", "C", "D", "E")));
        assertFalse(set.containsAll(Arrays.asList("A", "B", "F")));
        assertFalse(set.containsAll(Arrays.asList("F")));
    }

    @Test
    public void testKeySetViewForEach() {
        final int count = 8;
        ConcurrentHashMap.KeySetView<Integer, Boolean> set =
            ConcurrentHashMap.<Integer>newKeySet(count);
        for(int i = 0; i < count; ++i) {
            set.add(i+1);
        }
        LongAdder adder = new LongAdder();
        set.forEach((Integer x) -> adder.add(x.longValue()));
        // The size is small enough for the sum not to overflow
        assertEquals(set.size() * (set.size() + 1) / 2, adder.sum());
    }

    @Test
    public void testKeySetViewRemoveAll() {
        ConcurrentHashMap.KeySetView set = createKeySet();
        assertTrue(set.removeAll(Arrays.asList("A", "C")));
        assertEquals(set.size(), 3);
        assertTrue(set.containsAll(Arrays.asList("B", "D", "E")));
        assertFalse(set.removeAll(Arrays.asList("A", "C")));
        assertEquals(set.size(), 3);
        assertTrue(set.containsAll(Arrays.asList("B", "D", "E")));
    }

    @Test
    public void testKeySetViewRetainAll() {
        ConcurrentHashMap.KeySetView set = createKeySet();
        assertTrue(set.retainAll(Arrays.asList("A", "C")));
        assertEquals(set.size(), 2);
        assertTrue(set.containsAll(Arrays.asList("A", "C")));
        assertFalse(set.retainAll(Arrays.asList("A", "C")));
        assertEquals(set.size(), 2);
        assertTrue(set.containsAll(Arrays.asList("A", "C")));
    }

}
