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

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ExecutorsTest {

    class TestRunnable implements Runnable {
        public void run() { }
    }

    @Test
    public void testNewWorkStealingPoolDefault() {
        final ExecutorService e = Executors.newWorkStealingPool();
        try (ExecutorServiceAutoCloseable cleaner = new ExecutorServiceAutoCloseable(e)) {
            e.execute(new TestRunnable());
            e.execute(new TestRunnable());
            e.execute(new TestRunnable());
        }
    }

    @Test
    public void testNewWorkStealingPoolWithParallelism() {
        final ExecutorService e = Executors.newWorkStealingPool(2);
        try (ExecutorServiceAutoCloseable cleaner = new ExecutorServiceAutoCloseable(e)) {
            e.execute(new TestRunnable());
            e.execute(new TestRunnable());
            e.execute(new TestRunnable());
        }
    }
}
