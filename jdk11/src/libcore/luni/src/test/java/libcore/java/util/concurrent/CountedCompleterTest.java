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

import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CountedCompleterTest {

    /**
     * Exercises the completion of all tasks once one of them has a result.
     *
     * Instead of needing all tasks to be performed to get a result, this will only expect one of
     * them to provide it. That task, given by choiceIndex, will just complete the root completer.
     * All other tasks will remain "unfinished".
     *
     * The result is passed through setRawResult().
     */
    private static int chooseOne(Integer[] array, int choiceIndex) {
        class Task extends CountedCompleter<Integer> {
            final int lo;
            final int hi;
            AtomicInteger ai = new AtomicInteger(0);

            Task(Task parent, int lo, int hi) {
                super(parent);
                this.lo = lo;
                this.hi = hi;
            }

            @Override
            public void compute() {
                if (hi - lo >= 2) {
                    int mid = (lo + hi) >>> 1;
                    // must set pending count before fork
                    setPendingCount(2);
                    new Task(this, mid, hi).fork(); // right child
                    new Task(this, lo, mid).fork(); // left child
                } else if (hi > lo) {
                    if (choiceIndex == lo) {
                        final CountedCompleter root = getRoot();
                        final Integer val = Integer.valueOf(array[lo]);
                        if (root != null) {
                            root.complete(val);
                        } else {
                            complete(val); // the current task is the root
                        }
                    }
                }
            }

            public Integer getRawResult() {
                return new Integer(ai.intValue());
            }

            protected void setRawResult(Integer val) {
                ai.addAndGet(val.intValue());
            }
        }
        return new Task(null, 0, array.length).invoke().intValue();
    }

    /**
     * complete marks a task as complete regardless of the pending count.
     *
     * The test will only require one task to complete.
     */
    @Test
    public void testRecursiveChoice() {
        int n = 7;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = i + 1;
        }
        for (int chosenOne = 0; chosenOne < n; ++chosenOne) {
            final int result = chooseOne(a, chosenOne);
            assertEquals(chosenOne + 1, result);
        }
    }

    /**
     * Forces a task to complete all its children by running them its own pool.
     *
     * After a parent task adds its children tasks to it's own pool queue, it uses helpComplete to
     * ensure that those tasks are run before it returns. As all tasks are queued to the same pool
     * and the parallelism is set to 1, this will make the pool execute the children tasks from
     * within the execution of the parent.
     */
    private static void completeAllChildren(Integer[] array, Consumer<Integer> action) {

        /**
         * Leaf task that just runs the action and then completes.
         */
        class Task extends CountedCompleter<Integer> {
            final int idx;

            Task(CountedCompleter<Integer> parent, int idx) {
                super(parent);
                this.idx = idx;
            }

            @Override
            public void compute() {
                action.accept(array[idx]);
                tryComplete();
            }
        }

        /**
         * The parent task that creates and queues its children, then executes them on its own pool
         * before completing.
         */
        class MainTask extends CountedCompleter<Integer> {
            final ForkJoinPool pool;
            final int lo;
            final int hi;

            MainTask(ForkJoinPool pool, int lo, int hi) {
                super(null);
                this.pool = pool;
                this.lo = lo;
                this.hi = hi;
            }

            @Override
            public void compute() {
                final int count = hi - lo;
                setPendingCount(count);

                for (int idx = lo; idx < hi; ++idx) {
                    // Do not fork the task, rather add it to the parent's pool so that it is
                    // guaranteed not to be running before this compute() returns, unless
                    // helpComplete() is called.
                    pool.submit(new Task(this, idx));
                }

                // Make the pool run all the children tasks before moving one
                helpComplete(count);

                // If helpComplete() worked properly, by this point the pending count should be back
                // to 0, so the tryComplete will terminate this task. Otherwise, the task will not
                // get completed.
                if (getPendingCount() == 0) {
                    tryComplete();
                }
            }
        }

        // Use a pool with parallelism set to 1 so the children tasks cannot run before the main
        // task, unless helpComplete is used.
        ForkJoinPool pool = new ForkJoinPool(1);
        MainTask task = new MainTask(pool, 0, array.length);
        pool.submit(task);
        task.join();
    }

    /**
     * helpComplete attempts to process at most a given number of unprocessed children tasks.
     */
    @Test
    public void testHelpComplete() {
        int n = 7;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = i + 1;
        }
        AtomicInteger ai = new AtomicInteger(0);
        // Use an atomic add as the action for each task. This will add all the elements of the
        // array into the ai variable. Since the elements are between 1 and 7, the number does not
        // overflow.
        completeAllChildren(a, ai::addAndGet);
        assertEquals(n * (n + 1) / 2, ai.get());
    }
}
