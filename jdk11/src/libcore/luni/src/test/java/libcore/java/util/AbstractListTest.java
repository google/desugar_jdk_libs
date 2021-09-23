/*
 * Copyright (C) 2020 The Android Open Source Project
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

import junit.framework.AssertionFailedError;

import org.junit.Test;

import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AbstractListTest {
    @Test public void sublist_outOfBounds() {
        assertSubListOutOfBounds(new RandomAccessList(/* size */ 0), 0, 1);
        assertSubListOutOfBounds(new RandomAccessList(/* size */ 10), 0, 11);
        assertSubListOutOfBounds(new RandomAccessList(/* size */ 10), -1, 10);
        assertSubListOutOfBounds(new RandomAccessList(/* size */ 10), -1, 11);

        assertSubListOutOfBounds(new SequentialList(/* elements */), 0, 1);
        assertSubListOutOfBounds(new SequentialList(/* elements */ 10, 20, 30), 0, 4);
        assertSubListOutOfBounds(new SequentialList(/* elements */ 10, 20, 30), -1, 3);

        // These ones work
        new RandomAccessList(/* size */ 0).subList(0, 0);
        new RandomAccessList(/* size */ 10).subList(0, 10);
        new RandomAccessList(/* size */ 10).subList(2, 5);
        new SequentialList(/* elements */).subList(0, 0);
        new SequentialList(/* elements */ 10, 20, 30).subList(0, 3);
    }

    private static<T> void assertSubListOutOfBounds(List<T> list, int startIndex, int endIndex) {
        try {
            list.subList(startIndex, endIndex);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    /** Checks that list.spliterator() is late-binding. */
    @Test public void spliterator_lateBinding() {
        List<Integer> list = new RandomAccessList(50);
        Spliterator<Integer> spliterator = list.spliterator();
        Integer newFirstValue = list.get(0) + 3000;
        list.add(0, newFirstValue); // prepend
        AtomicReference<Integer> receivedValue = new AtomicReference<>(null);
        // No ConcurrentModificationException because of late-binding Spliterator
        boolean didAdvance = spliterator.tryAdvance(value -> receivedValue.set(value));
        // Expect the values at index 0 from after the add(), not from after the spliterator().
        assertEquals(newFirstValue, receivedValue.get());
        assertTrue(didAdvance);
    }

    @Test public void spliterator_modification_failFast() {
        List<Integer> list = new RandomAccessList(50);
        Integer expectedValue = list.get(2);
        Spliterator<Integer> spliterator = list.spliterator();
        // We need to perform at least one action on the spliterator because only then does
        // it initialize its internal expectedModCount.
        assertTrue(spliterator.tryAdvance(value -> {}));
        assertTrue(spliterator.tryAdvance(value -> {}));
        list.add(42); // concurrent modification
        AtomicReference<Integer> receivedValue = new AtomicReference<>(null);
        try {
            spliterator.tryAdvance(value -> receivedValue.set(value));
            fail();
        } catch (ConcurrentModificationException expected) {
        }
        // I believe it would also be within spec for this to remain unset (null),
        // but the current implementation checks for concurrent modification only after
        // delivering the value. We're asserting that so that we notice if the behavior
        // ever changes in future.
        assertEquals(expectedValue, receivedValue.get());
    }

    @Test public void spliteratorOfRandomAccessList_usesOnlyRandomAccess() {
        checkSpliteratorOfOnlyRandomAccessList_usesOnlyRandomAccess(0);
        checkSpliteratorOfOnlyRandomAccessList_usesOnlyRandomAccess(1);
        checkSpliteratorOfOnlyRandomAccessList_usesOnlyRandomAccess(2);
        checkSpliteratorOfOnlyRandomAccessList_usesOnlyRandomAccess(100);
    }

    private static void checkSpliteratorOfOnlyRandomAccessList_usesOnlyRandomAccess(int listSize) {
        checkSpliteratorOfRandomAccessList(listSize,
                () -> new OnlyRandomAccessList(listSize));
        int subListSize = listSize / 2;
        checkSpliteratorOfRandomAccessList(
                subListSize,
                () -> new OnlyRandomAccessList(listSize).subList(0, subListSize));
    }

    /**
     * Checks basic operations on supplied RandomAccess Lists. If the supplied Lists are
     * {@link OnlyRandomAccessList} or its sublists, then that has the side-effect of
     * checking that only random-access operations are attempted.
     */
    // We're using a Supplier<List> to keep constructing new Lists because some of the testers
    // have the side effect of sorting the List.
    private static void checkSpliteratorOfRandomAccessList(int size,
            Supplier<List<Integer>> listSupplier) {
        assertTrue(listSupplier.get() instanceof RandomAccess);
        assertEquals(size, listSupplier.get().size());

        Supplier<Spliterator<Integer>> spliteratorSupplier = () -> listSupplier.get().spliterator();
        List<Integer> expectedList = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            expectedList.add(RandomAccessList.initialValue(index));
        }

        SpliteratorTester.runBasicIterationTests(spliteratorSupplier.get(), expectedList);
        SpliteratorTester.runBasicSplitTests(spliteratorSupplier.get(), expectedList,
                Comparator.naturalOrder());
        SpliteratorTester.testSpliteratorNPE(spliteratorSupplier.get());

        assertTrue(spliteratorSupplier.get().hasCharacteristics(
                Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED));

        SpliteratorTester.runSizedTests(spliteratorSupplier.get(), size /* expected size */);
        if (spliteratorSupplier.get().trySplit() != null) {
            SpliteratorTester.runSubSizedTests(spliteratorSupplier.get(), size);
        } else {
            assertTrue(size <= 1); // trySplit() should work for lists sized > 1.
        }
    }

    static class SequentialList extends AbstractSequentialList<Integer> {
        private final List<Integer> delegate;

        public SequentialList(Integer... elements) {
            this.delegate = Collections.unmodifiableList(Arrays.asList(elements));
        }

        @Override public ListIterator<Integer> listIterator(int index) {
            return delegate.listIterator(index);
        }

        @Override public int size() { return delegate.size(); }
    }

    /**
     * A simple list that allows random-access {@code get()} and modifications. This
     * implementation increases {@link AbstractList#modCount} during {@code add()}
     * and {@code remove()} to provide fail-fast Spliterators.
     */
    static class RandomAccessList extends AbstractList<Integer> implements RandomAccess {
        protected final List<Integer> delegate;

        public RandomAccessList(int size) {
            this.delegate = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                delegate.add(initialValue(i));
            }
        }

        static Integer initialValue(int index) { return 31337 ^ index; }

        @Override public Integer get(int index) { return delegate.get(index); }
        @Override public int size() { return delegate.size(); }
        @Override public Integer set(int index, Integer v) { return delegate.set(index, v); }
        @Override public void add(int index, Integer element) {
            modCount++;
            delegate.add(index, element);
        }
        @Override public Integer remove(int index) {
            modCount++;
            return delegate.remove(index);
        }
    }

    /**
     * A {@link RandomAccessList} that throws if a caller attempts {@link #iterator()
     * iterative access}.
     */
    static class OnlyRandomAccessList extends RandomAccessList {
        public OnlyRandomAccessList(int size) {
            super(size);
        }
        @Override public Iterator<Integer> iterator() {
            throw new AssertionFailedError();
        }

        @Override public ListIterator<Integer> listIterator(int index) {
            throw new AssertionFailedError();
        }

    }

}
