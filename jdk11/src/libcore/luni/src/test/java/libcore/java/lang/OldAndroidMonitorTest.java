/*
 * Copyright (C) 2007 The Android Open Source Project
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

import junit.framework.TestCase;

import java.util.concurrent.CountDownLatch;

public class OldAndroidMonitorTest extends TestCase {

    public void testWaitArgumentsTest() throws Exception {
            /* Try some valid arguments.  These should all
             * return very quickly.
             */
            try {
                synchronized (this) {
                    /* millisecond version */
                    wait(1);
                    wait(10);

                    /* millisecond + nanosecond version */
                    wait(0, 1);
                    wait(0, 999999);
                    wait(1, 1);
                    wait(1, 999999);
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException("good Object.wait() interrupted",
                        ex);
            } catch (Exception ex) {
                throw new RuntimeException("Unexpected exception when calling" +
                        "Object.wait() with good arguments", ex);
            }

            /* Try some invalid arguments.
             */
            boolean sawException = false;
            try {
                synchronized (this) {
                    wait(-1);
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException("bad Object.wait() interrupted", ex);
            } catch (IllegalArgumentException ex) {
                sawException = true;
            } catch (Exception ex) {
                throw new RuntimeException("Unexpected exception when calling" +
                        "Object.wait() with bad arguments", ex);
            }
            if (!sawException) {
                throw new RuntimeException("bad call to Object.wait() should " +
                        "have thrown IllegalArgumentException");
            }

            sawException = false;
            try {
                synchronized (this) {
                    wait(0, -1);
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException("bad Object.wait() interrupted", ex);
            } catch (IllegalArgumentException ex) {
                sawException = true;
            } catch (Exception ex) {
                throw new RuntimeException("Unexpected exception when calling" +
                        "Object.wait() with bad arguments", ex);
            }
            if (!sawException) {
                throw new RuntimeException("bad call to Object.wait() should " +
                        "have thrown IllegalArgumentException");
            }

            sawException = false;
            try {
                synchronized (this) {
                    /* The legal range of nanos is 0-999999. */
                    wait(0, 1000000);
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException("bad Object.wait() interrupted", ex);
            } catch (IllegalArgumentException ex) {
                sawException = true;
            } catch (Exception ex) {
                throw new RuntimeException("Unexpected exception when calling" +
                        "Object.wait() with bad arguments", ex);
            }
            if (!sawException) {
                throw new RuntimeException("bad call to Object.wait() should " +
                        "have thrown IllegalArgumentException");
            }
    }

    /**
     * A thread that blocks forever on {@code wait()} until it's interrupted.
     */
    static class Waiter extends Thread {
        private final Object lock;
        private final CountDownLatch cdl;
        private boolean wasInterrupted;

        public Waiter(Object lock, CountDownLatch cdl) {
            this.lock = lock;
            this.cdl = cdl;
            wasInterrupted = false;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    cdl.countDown();
                    while (true) {
                        lock.wait();
                    }
                } catch (InterruptedException ex) {
                    wasInterrupted = true;
                }
            }
        }

        public boolean wasInterrupted() {
            synchronized (lock) {
                return wasInterrupted;
            }
        }
    }

    public void testInterrupt() throws Exception {
        final Object lock = new Object();
        final CountDownLatch cdl = new CountDownLatch(1);
        final Waiter waiter = new Waiter(lock, cdl);

        waiter.start();

        // Wait for the "waiter" to start and acquire |lock| for the first time.
        try {
            cdl.await();
        } catch (InterruptedException ie) {
            fail();
        }

        // Interrupt |waiter| after we acquire |lock|. This ensures that |waiter| is
        // currently blocked on a call to "wait".
        synchronized (lock) {
            waiter.interrupt();
        }

        // Wait for the waiter to complete.
        try {
            waiter.join();
        } catch (InterruptedException ie) {
            fail();
        }

        // Assert than an InterruptedException was thrown.
        assertTrue(waiter.wasInterrupted());
    }
}
