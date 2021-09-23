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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AbstractExecutorServiceTest {

    static class TestExecutorService extends AbstractExecutorService {

        public volatile boolean isRunning = true;

        public boolean awaitTermination(long timeout, TimeUnit unit) {
            return isShutdown();
        }

        public boolean isShutdown() {
            return !isRunning;
        }

        public boolean isTerminated() {
            return isShutdown();
        }

        public void shutdown() {
            isRunning = false;
        }

        public List<Runnable> shutdownNow() {
            shutdown();
            return Collections.emptyList();
        }

        public void execute(Runnable command) {
            command.run();
        }

        public <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
            return super.newTaskFor(runnable, value);
        }
    }

    @Test
    public void testSubmitRunnableWithValue() throws Exception {
        Integer value = Integer.valueOf(42);
        ExecutorService service = new TestExecutorService();
        AtomicBoolean didRun = new AtomicBoolean(false);
        Future<Integer> future = service.submit(() -> didRun.set(true), value);
        Integer result = future.get();
        assertSame(value, result);
        assertTrue(didRun.get());
    }
}
