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
import static org.junit.Assert.fail;

import java.time.Duration;
import java.util.concurrent.RecursiveTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RecursiveTaskTest {

    static final long MILLIS_TO_NANO = (1000 * 1000);

    final class SumTask extends RecursiveTask<Integer> {
        final int value;

        SumTask(int value) {
            this.value = value;
        }

        @Override
        protected Integer compute() {
            if (value <= 1)
                return value;
            SumTask subTask = new SumTask(value - 1);
            subTask.fork();
            return subTask.join() + value;
        }

        public void waitForCompletion(Integer forceResult) {
            if(!TestUtils.waitWhileTrueOrTimeout(Duration.ofSeconds(10),
                        () -> { return !isDone(); })) {
                fail("timed out waiting for task completion");
            }
            if (forceResult != null) {
                super.setRawResult(forceResult);
            }
        }
    }

    @Test
    public void testSetRawResult() {
        SumTask task = new SumTask(10);
        final Integer expected = Integer.valueOf(-1);
        task.fork();
        task.waitForCompletion(expected);
        Integer result = task.join();
        assertSame(expected, result);
    }
}
