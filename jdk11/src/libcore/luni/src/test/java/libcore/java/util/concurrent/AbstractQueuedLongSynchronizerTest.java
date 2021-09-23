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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AbstractQueuedLongSynchronizerTest {

    class DefaultMutex extends AbstractQueuedLongSynchronizer {

        @Override
        public boolean isHeldExclusively() {
            return super.isHeldExclusively();
        }

        @Override
        public boolean tryAcquire(long acquires) {
            return super.tryAcquire(acquires);
        }

        @Override
        public long tryAcquireShared(long acquires) {
            return super.tryAcquireShared(acquires);
        }

        @Override
        public boolean tryRelease(long releases) {
            return super.tryRelease(releases);
        }

        @Override
        public boolean tryReleaseShared(long releases) {
            return super.tryReleaseShared(releases);
        }

        public AbstractQueuedLongSynchronizer.ConditionObject newCondition() {
            return new AbstractQueuedLongSynchronizer.ConditionObject();
        }

    }

    @Test
    public void testDefaultIsHeldExclusivelyFails() {
        DefaultMutex mutex = new DefaultMutex();
        try {
            mutex.isHeldExclusively();
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        } catch (Throwable t) {
            fail("Unexpected exception: " + t.getMessage());
        }
    }

    @Test
    public void testDefaultTryAcquireFails() {
        DefaultMutex mutex = new DefaultMutex();
        try {
            mutex.tryAcquire(1);
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        } catch (Throwable t) {
            fail("Unexpected exception: " + t.getMessage());
        }
    }

    @Test
    public void testDefaultTryAcquireSharedFails() {
        DefaultMutex mutex = new DefaultMutex();
        try {
            mutex.tryAcquireShared(1);
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        } catch (Throwable t) {
            fail("Unexpected exception: " + t.getMessage());
        }
    }

    @Test
    public void testDefaultTryReleaseFails() {
        DefaultMutex mutex = new DefaultMutex();
        try {
            mutex.tryRelease(1);
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        } catch (Throwable t) {
            fail("Unexpected exception: " + t.getMessage());
        }
    }

    @Test
    public void testDefaultTryReleaseSharedFails() {
        DefaultMutex mutex = new DefaultMutex();
        try {
            mutex.tryReleaseShared(1);
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        } catch (Throwable t) {
            fail("Unexpected exception: " + t.getMessage());
        }
    }

    @Test
    public void testConditionObjectCreation() {
        DefaultMutex mutex = new DefaultMutex();
        AbstractQueuedLongSynchronizer.ConditionObject condition = mutex.newCondition();
        assertTrue(mutex.owns(condition));
    }

}
