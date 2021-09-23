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
import static org.junit.Assert.fail;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ForkJoinPoolTest {

    @Test
    public void testSubmit() {
        final ForkJoinPool pool = new ForkJoinPool();
        try (ExecutorServiceAutoCloseable cleaner = new ExecutorServiceAutoCloseable(pool)) {
            final AtomicInteger result = new AtomicInteger(0);
            ForkJoinTask task = pool.submit(() -> result.addAndGet(42), result);
            assertSame(result, task.get());
            assertEquals(42, result.get());
        } catch(Throwable t) {
            fail("Unexpected exception: " + t.getMessage());
        }
    }

    @Test
    public void testGetRunningThreadCount() {
        final ForkJoinPool pool = new ForkJoinPool();
        try (ExecutorServiceAutoCloseable cleaner = new ExecutorServiceAutoCloseable(pool)) {
            assertEquals(0, pool.getRunningThreadCount());

            final AtomicInteger value = new AtomicInteger(0);
            final AtomicBoolean stop = new AtomicBoolean(false);
            ForkJoinTask task = pool.submit(new Runnable() {
                    public void run() {
                        while(!stop.get()) {
                            value.incrementAndGet();
                        }
                        stop.set(false);
                    }
                });
            assertEquals(1, pool.getRunningThreadCount());
            stop.set(true);
            task.join();
            pool.awaitTermination(2000, TimeUnit.MILLISECONDS);
            assertEquals(0, pool.getRunningThreadCount());
        } catch(Throwable t) {
            fail("Unexpected exception: " + t.getMessage());
        }
    }
}
