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

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ReentrantReadWriteLockTest {

    class ReadLockRunnable implements Runnable {
        final ReentrantReadWriteLock lock;

        ReadLockRunnable(ReentrantReadWriteLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.readLock().lock();
            lock.readLock().unlock();
        }
    }

    class WriteLockRunnable implements Runnable {
        final ReentrantReadWriteLock lock;

        WriteLockRunnable(ReentrantReadWriteLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.writeLock().lock();
            lock.writeLock().unlock();
        }
    }

    static class DefaultReentrantReadWriteLock extends ReentrantReadWriteLock {

        DefaultReentrantReadWriteLock() {
            super();
        }

        @Override
        public Collection<Thread> getQueuedReaderThreads() {
            return super.getQueuedReaderThreads();
        }

        @Override
        public Collection<Thread> getQueuedWriterThreads() {
            return super.getQueuedWriterThreads();
        }
    }

    private void waitForQueuedThread(DefaultReentrantReadWriteLock lock, Thread thread) {
        if(!TestUtils.waitWhileTrueOrTimeout(Duration.ofSeconds(2),
                    () -> { return !lock.hasQueuedThread(thread); })) {
            fail("timed out waiting for queued thread");
        }
    }

    @Test
    public void testReadLockCondition() {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        try {
            final Condition condition = lock.readLock().newCondition();
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException success) {
        } catch (Throwable t) {
            fail("Unexpected exception: " + t.getMessage());
        }
    }

    @Test
    public void testGetQueuedReaderThreads() {
        final DefaultReentrantReadWriteLock lock = new DefaultReentrantReadWriteLock();
        Thread thread1 = new Thread(new ReadLockRunnable(lock));
        Thread thread2 = new Thread(new ReadLockRunnable(lock));
        lock.writeLock().lock();
        assertTrue(lock.getQueuedReaderThreads().isEmpty());
        thread1.start();
        waitForQueuedThread(lock, thread1);
        assertTrue(lock.getQueuedReaderThreads().contains(thread1));
        thread2.start();
        waitForQueuedThread(lock, thread2);
        assertTrue(lock.getQueuedReaderThreads().contains(thread2));
        lock.writeLock().unlock();
        TestUtils.joinThreadOrFail(2000, thread1);
        TestUtils.joinThreadOrFail(2000, thread2);
        assertTrue(lock.getQueuedReaderThreads().isEmpty());
    }

    @Test
    public void testGetQueuedWriterThreads() {
        final DefaultReentrantReadWriteLock lock = new DefaultReentrantReadWriteLock();
        Thread thread1 = new Thread(new WriteLockRunnable(lock));
        Thread thread2 = new Thread(new WriteLockRunnable(lock));
        lock.writeLock().lock();
        assertTrue(lock.getQueuedWriterThreads().isEmpty());
        thread1.start();
        waitForQueuedThread(lock, thread1);
        assertTrue(lock.getQueuedWriterThreads().contains(thread1));
        thread2.start();
        waitForQueuedThread(lock, thread2);
        assertTrue(lock.getQueuedWriterThreads().contains(thread2));
        lock.writeLock().unlock();
        TestUtils.joinThreadOrFail(2000, thread1);
        TestUtils.joinThreadOrFail(2000, thread2);
        assertTrue(lock.getQueuedWriterThreads().isEmpty());
    }

}
