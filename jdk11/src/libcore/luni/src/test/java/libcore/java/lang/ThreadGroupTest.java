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
 * limitations under the License.
 */

package libcore.java.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class ThreadGroupTest {

    private CountDownLatch mLatch;

    @Before
    public void setup() {
        mLatch = new CountDownLatch(2);
    }

    @Test
    public void interrupt_shouldInterruptAllThreadsInAGroup() throws Exception {
        ThreadGroup group = new ThreadGroup("group under test");

        Thread first = createHangThread(group);
        Thread second = createHangThread(group);

        first.start();
        second.start();

        group.interrupt();

        assertTrue("Some thread was not interrupted", mLatch.await(5, TimeUnit.SECONDS));
    }

    @Test
    public void interrupt_shouldInterruptThreadsInSubgroups() throws Exception {
        ThreadGroup parentGroup = new ThreadGroup("parent thread group");
        ThreadGroup childGroup = new ThreadGroup(parentGroup, "child thread group");

        Thread first = createHangThread(parentGroup);
        Thread second = createHangThread(childGroup);

        first.start();
        second.start();

        parentGroup.interrupt();

        assertTrue("Some thread was not interrupted", mLatch.await(5, TimeUnit.SECONDS));
    }

    @Test
    public void interrupt_shouldNotInterruptThreadsInParentGroup() throws Exception {
        ThreadGroup parentGroup = new ThreadGroup("parent thread group");
        ThreadGroup childGroup = new ThreadGroup(parentGroup, "child thread group");

        Thread first = createHangThread(parentGroup);
        Thread second = createHangThread(childGroup);

        first.start();
        second.start();

        childGroup.interrupt();

        assertFalse("Both threads were interrupted", mLatch.await(5, TimeUnit.SECONDS));

        assertEquals("Thread from parent group was interrupted", 1, mLatch.getCount());
    }

    @Test
    public void suspend_shouldThrowUnsupportedOperationException() throws Exception {
        ThreadGroup threadGroup = new ThreadGroup("test group");

        Thread thread = createHangThread(threadGroup);
        thread.start();

        try {
            threadGroup.suspend();
            fail("suspend() didn't throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ignored) {
            // expected
        } finally {
            thread.join();
        }
    }

    @Test
    public void stop_shouldThrowUnsupportedOperationException() throws Exception {
        ThreadGroup threadGroup = new ThreadGroup("test group");

        Thread thread = createHangThread(threadGroup);
        thread.start();

        try {
            threadGroup.stop();
            fail("stop() didn't throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ignored) {
            // expected
        } finally {
            thread.join();
        }
    }

    private Thread createHangThread(ThreadGroup threadGroup) {
        return new Thread(threadGroup, () -> {
            try {
                Thread.sleep(20_000);
            } catch (InterruptedException e) {
                mLatch.countDown();
            }
        });
    }

}
