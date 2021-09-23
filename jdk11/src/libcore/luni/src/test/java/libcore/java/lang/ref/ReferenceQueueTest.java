/*
 * Copyright (C) 2011 The Android Open Source Project
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

package libcore.java.lang.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;
import sun.misc.Cleaner;

public final class ReferenceQueueTest extends TestCase {

    public void testRemoveWithInvalidTimeout() throws Exception {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
        try {
            referenceQueue.remove(-1);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    /**
     * Check that an actual execution time in msecs is "close enough" to expected.
     * In general things should not finish early, so we allow little slop in that direction.
     * However things can easily get delayed, so we allow more slop in that direction.
     */
    private void checkDuration(long expected, long actual) {
        // The main need for slack is because tasks may not get scheduled right
        // away. This is independent of the sleep/wait time, so we're using
        // absolute rather than relative tolerances here.
        assertTrue("Duration too short: " + actual + "ms", actual > expected - 10);
        assertTrue("Duration too long: " + actual + "ms", actual < expected + 450);
    }

    public void testRemoveWithVeryLargeTimeout() throws Exception {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
        enqueueLater(referenceQueue, 500);
        referenceQueue.remove(Long.MAX_VALUE);
    }

    public void testRemoveWithSpuriousNotify() throws Exception {
        final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();

        runLater(new Runnable() {
            @Override public void run() {
                synchronized (referenceQueue) {
                    referenceQueue.notifyAll();
                }
            }
        }, 500);

        final long startNanos = System.nanoTime();
        final long timeoutMsec = 1000L;
        referenceQueue.remove(timeoutMsec);
        final long durationNanos = System.nanoTime() - startNanos;
        final long durationMillis = TimeUnit.NANOSECONDS.toMillis(durationNanos);
        checkDuration(timeoutMsec, durationMillis);
    }

    public void testRemoveWithImmediateResultAndNoTimeout() throws Exception {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
        enqueue(referenceQueue);
        assertNotNull(referenceQueue.remove());
    }

    public void testRemoveWithImmediateResultAndTimeout() throws Exception {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
        enqueue(referenceQueue);
        assertNotNull(referenceQueue.remove(1000));
    }

    public void testRemoveWithDelayedResultAndNoTimeout() throws Exception {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
        final long enqueueDelayMsec = 500L;
        final long startNanos = System.nanoTime();
        enqueueLater(referenceQueue, enqueueDelayMsec);
        Object result = referenceQueue.remove();
        assertNotNull(result);
        final long durationNanos = System.nanoTime() - startNanos;
        final long durationMillis = TimeUnit.NANOSECONDS.toMillis(durationNanos);
        checkDuration(enqueueDelayMsec, durationMillis);
    }

    public void testRemoveWithDelayedResultAndTimeout() throws Exception {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
        final long enqueueDelayMsec = 500L;
        final long startNanos = System.nanoTime();
        enqueueLater(referenceQueue, enqueueDelayMsec);
        Object result = referenceQueue.remove(1000);
        if (result == null) {
          // Also report the actual queue status.
          assertNotNull(referenceQueue.poll());
          assertNotNull(result);
        }
        final long durationNanos = System.nanoTime() - startNanos;
        final long durationMillis = TimeUnit.NANOSECONDS.toMillis(durationNanos);
        checkDuration(enqueueDelayMsec, durationMillis);
    }

    public void testCleanersCleaned() {
        Object object = new Object();
        final CountDownLatch cdl = new CountDownLatch(1);

        Cleaner cleaner = Cleaner.create(object, new Runnable() {
            @Override
            public void run() {
                cdl.countDown();
            }
        });

        boolean countedDown = false;
        object = null;
        for (int i = 0; i < 5; ++i) {
            Runtime.getRuntime().gc();
            try {
                countedDown = cdl.await(1000, TimeUnit.MILLISECONDS);
                if (countedDown) {
                    break;
                }
            } catch (InterruptedException ie) {
                fail();
            }
        }

        assertTrue(countedDown);
    }

    private void runLater(Runnable runnable, long delayMillis) {
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(runnable, delayMillis, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    private void enqueueLater(final ReferenceQueue<Object> queue, long delayMillis) {
        runLater(new Runnable() {
            @Override public void run() {
                enqueue(queue);
            }
        }, delayMillis);
    }

    private void enqueue(ReferenceQueue<Object> queue) {
        new WeakReference<Object>(new Object(), queue).enqueue();
    }
}
