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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ForkJoinTaskTest {

    /**
     * adapt uses a Runnable to perform a task, returning as the result the provided object
     */
    @Test
    public void testAdaptToRunnableWithResult() {

        final AtomicInteger result = new AtomicInteger(0);
        final ForkJoinTask task = ForkJoinTask.adapt(() -> result.addAndGet(42), result);
        final ForkJoinPool pool = new ForkJoinPool(1);
        try (ExecutorServiceAutoCloseable cleaner = new ExecutorServiceAutoCloseable(pool)) {
            pool.execute(task);
            assertSame(result, task.join());
            assertEquals(42, result.get());
        } catch(Throwable t) {
            fail("Unexpected exception: " + t.getMessage());
        }
    }

    /**
     * adapt uses a Callable to perform a task, returning as the result the value returned from the
     * call() function
     */
    @Test
    public void testAdaptToCallable() {

        Callable callable = () -> { return Integer.valueOf(42); };
        final ForkJoinTask task = ForkJoinTask.adapt(callable);
        final ForkJoinPool pool = new ForkJoinPool(1);
        try (ExecutorServiceAutoCloseable cleaner = new ExecutorServiceAutoCloseable(pool)) {
            pool.execute(task);
            Integer result = (Integer)task.join();
            assertEquals(42, result.intValue());
        } catch(Throwable t) {
            fail("Unexpected exception: " + t.getMessage());
        }
    }

    /**
     * adapt uses a Callable to perform a task, converting any checked exception into
     * RuntimeException
     */
    @Test
    public void testAdaptToCallableThrowsException() {

        Callable callable = () -> { throw new Exception("Test passed"); };
        final ForkJoinTask task = ForkJoinTask.adapt(callable);
        final ForkJoinPool pool = new ForkJoinPool(1);
        try (ExecutorServiceAutoCloseable cleaner = new ExecutorServiceAutoCloseable(pool)) {
            pool.execute(task);
            Integer result = (Integer)task.join();
            fail("Expected RuntimeException");
        } catch(RuntimeException e) {
            assertTrue(e.getMessage().contains("Test passed"));
        } catch(Throwable t) {
            fail("Unexpected exception: " + t.getMessage());
        }
    }
}
