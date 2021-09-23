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

import static org.junit.Assert.fail;

import java.time.Duration;
import java.util.function.BooleanSupplier;

class TestUtils {

    public static final long MILLIS_TO_NANO = (1000 * 1000);

    public static void joinThreadOrFail(long timeoutMillis, Thread thread) {
        try {
            thread.join(timeoutMillis);
        } catch (InterruptedException fail) {
            fail("InterruptedException when joining thread");
        } finally {
            if (thread.getState() != Thread.State.TERMINATED) {
                thread.interrupt();
                fail("timed out waiting to join thread");
            }
        }
    }

    public static boolean waitWhileTrueOrTimeout(Duration timeout, BooleanSupplier conditionFn) {
        Duration startTime = Duration.ofNanos(System.nanoTime());
        while (conditionFn.getAsBoolean()) {
            Duration now = Duration.ofNanos(System.nanoTime());
            if (now.minus(startTime).compareTo(timeout) >= 0) {
                return false;
            }
            Thread.yield();
        }
        return true;
    }

}
