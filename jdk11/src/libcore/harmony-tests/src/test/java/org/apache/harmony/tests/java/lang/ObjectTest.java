/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.harmony.tests.java.lang;

import java.util.ArrayList;
import java.util.List;

public class ObjectTest extends junit.framework.TestCase {

    final Object lock = new Object();

    // Helpers for test_notify() and test_notifyAll(). Access is guarded by {@code lock}'s monitor.
    int ready = 0;
    int finished = 0;
    int outstandingNotifications = 0;
    int spuriousNotifications = 0;
    Throwable backgroundException;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        synchronized (lock) {
            backgroundException = null;
        }
    }

    @Override
    protected void tearDown() throws Exception {
        synchronized (lock) {
            if (backgroundException != null) {
                fail("Encountered " + backgroundException);
            }
        }
        super.tearDown();
    }

    /**
     * java.lang.Object#Object()
     */
    public void test_constructor() {
        // Test for method java.lang.Object()
        assertNotNull("Constructor failed", new Object());
    }

    /**
     * java.lang.Object#equals(java.lang.Object)
     */
    public void test_equalsLjava_lang_Object() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        // Test for method boolean java.lang.Object.equals(java.lang.Object)
        assertTrue("Same object should be equal", obj1.equals(obj1));
        assertTrue("Different objects should not be equal", !obj1.equals(obj2));
    }

    /**
     * java.lang.Object#getClass()
     */
    public void test_getClass() throws Exception {
        // Test for method java.lang.Class java.lang.Object.getClass()
        String[] classNames = { "java.lang.Object", "java.lang.Throwable",
                "java.lang.StringBuffer" };
        for (String className : classNames) {
            final Class<?> classToTest = Class.forName(className);
            final Object instanceToTest = classToTest.newInstance();
            assertSame("Instance didn't match creator class.", instanceToTest.getClass(),
                    classToTest);
            assertSame("Instance didn't match class with matching name.", instanceToTest.getClass(),
                    Class.forName(className));
        }
    }

    /**
     * java.lang.Object#hashCode()
     */
    public void test_hashCode() {
        // Test for method int java.lang.Object.hashCode()
        Object obj1 = new Object();
        Object obj2;
        int origHashCodeForObj1 = obj1.hashCode();
        // Force obj1 lock inflation.
        synchronized (obj1) {
            obj2 = new Object();
        }
        assertEquals("Same object should have same hash.", obj1.hashCode(), obj1.hashCode());
        assertEquals("Lock inflation shouldn't change hash code.",
                obj1.hashCode(), origHashCodeForObj1);
        assertEquals("Same object should have same hash.", obj2.hashCode(), obj2.hashCode());
        Runtime.getRuntime().gc();
        assertEquals("Gc shouldn't change hash code.", obj1.hashCode(), origHashCodeForObj1);
    }

    /**
     * java.lang.Object#notify()
     */
    public void test_notify() {
        // Test for method void java.lang.Object.notify()
        class TestThread extends Thread {
            public TestThread(String name) {
                super(name);
            }

            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        ready++;
                        lock.wait(); // Wait to be notified.
                        while (outstandingNotifications <= 0) {
                            spuriousNotifications++;
                            lock.wait();
                        }
                        outstandingNotifications--;
                    } catch (InterruptedException ex) {
                        backgroundException = ex;
                    }
                }
            }
        }

        // Warning:
        // This code relies on each thread getting serviced within
        // 400 msec of when it is notified. Although this
        // seems reasonable, it could lead to false-failures.

        ready = 0;
        outstandingNotifications = 0;
        spuriousNotifications = 0;
        final int readyWaitSecs = 3;

        final int threadCount = 20;
        List<TestThread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; ++i) {
            TestThread thread = new TestThread("TestThread " + i);
            threads.add(thread);
            thread.start();
        }
        synchronized (lock) {
            try {
                // Wait up to readyWaitSeconds for all threads to be waiting on
                // monitor
                for (int i = 0; i < 10 * readyWaitSecs; i++) {
                    lock.wait(100, 0);
                    if (ready == threadCount) {
                        break;
                    }
                }
                assertEquals("Not all launched threads are waiting. (ready=" + ready + ")",
                        ready, threadCount);
                for (int i = 1; i <= threadCount; ++i) {
                    outstandingNotifications++;
                    lock.notify();
                    for (int j = 0; j < 10 && outstandingNotifications > 0; ++j) {
                        lock.wait(100);  // Sleep for 100 msecs, releasing lock.
                    }
                    assertEquals("Notification #" + i + "  took too long to wake a thread.",
                            0, outstandingNotifications);
                    // Spurious notifications are allowed, but should be very rare.
                    assertTrue("Too many spurious notifications: " + spuriousNotifications,
                            spuriousNotifications <= 1);
                }
            } catch (InterruptedException ex) {
                fail("Unexpectedly got an InterruptedException.");
            }
        }
    }

    /**
     * java.lang.Object#notifyAll()
     */
    public void test_notifyAll() {
        // Test for method void java.lang.Object.notifyAll()

        // Inner class to run test thread.
        class TestThread implements Runnable {
            public void run() {
                synchronized (lock) {
                    try {
                        ready += 1;
                        lock.wait();// Wait forever.
                        finished += 1;
                    } catch (InterruptedException ex) {
                        backgroundException = ex;
                    }
                }
            }
        }
        ;

        // Start of test code.

        // Warning:
        // This code relies on all threads getting serviced within
        // 5 seconds of when they are notified. Although this
        // seems reasonable, it could lead to false-failures.

        ready = 0;
        finished = 0;
        final int readyWaitSecs = 3;
        final int finishedWaitSecs = 5;
        final int threadCount = 20;
        for (int i = 0; i < threadCount; ++i) {
            new Thread(new TestThread()).start();
        }

        synchronized (lock) {

            try {
                // Wait up to readyWaitSeconds for all threads to be waiting on
                // monitor
                for (int i = 0; i < readyWaitSecs; i++) {
                    lock.wait(1000, 0);
                    if (ready == threadCount) {
                        break;
                    }
                }

                // Check pre-conditions of testing notifyAll
                assertEquals("Not all launched threads are waiting.", threadCount, ready);
                // This assumes no spurious wakeups. If we ever see any, we might check for at
                // most one finished thread instead.
                assertEquals("At least one thread woke too early.", 0, finished);

                lock.notifyAll();

                for (int i = 0; finished < threadCount && i < finishedWaitSecs; ++i) {
                  lock.wait(1000, 0);
                }

                assertEquals("At least one thread did not get notified.", threadCount, finished);

            } catch (InterruptedException ex) {
                fail("Unexpectedly got an InterruptedException. (finished = " + finished + ")");
            }

        }
    }

    /**
     * java.lang.Object#toString()
     */
    public void test_toString() {
        // Test for method java.lang.String java.lang.Object.toString()
        assertNotNull("Object toString returned null.", lock.toString());
    }

    /**
     * java.lang.Object#wait()
     */
    public void test_wait() {
        // Test for method void java.lang.Object.wait()

        // Inner class to run test thread.
        class TestThread extends Thread {
            int status;

            public void run() {
                synchronized (lock) {
                    try {
                        do {
                            lock.wait(); // Wait to be notified.
                        } while (outstandingNotifications <= 0);
                        outstandingNotifications--;
                        status = 1;
                    } catch (InterruptedException ex) {
                        backgroundException = ex;
                    }
                }
            }
        }

        // Start of test code.

        // Warning:
        // This code relies on threads getting serviced within
        // 1 second of when they are notified. Although this
        // seems reasonable, it could lead to false-failures.

        TestThread thread = new TestThread();
        synchronized (lock) {
            thread.status = 0;
        }
        thread.start();
        synchronized (lock) {
            try {
                lock.wait(1000, 0);
                assertEquals("Thread woke too early. (status=" + thread.status + ")",
                        0, thread.status);
                outstandingNotifications = 1;
                lock.notifyAll();
                lock.wait(1000, 0);
                assertEquals("Thread did not get notified. (status=" + thread.status + ")",
                        1, thread.status);
            } catch (InterruptedException ex) {
                fail("Unexpectedly got an InterruptedException. (status=" + thread.status + ")");
            }
        }
    }

    /**
     * java.lang.Object#wait(long)
     */
    public void test_waitJ() {
        // Test for method void java.lang.Object.wait(long)

        // Start of test code.

        final int loopCount = 20;
        final int allowableError = 100; // milliseconds
        final int delay = 200; // milliseconds
        synchronized (lock) {
            try {
                int count = 0;
                long[][] toLong = new long[3][3];
                for (int i = 0; i < loopCount; ++i) {
                    long before = System.currentTimeMillis();
                    lock.wait(delay, 0);
                    long after = System.currentTimeMillis();
                    long error = (after - before - delay);
                    if (error < 0)
                        error = -error;
                    if (i > 0 && error > allowableError) {
                        // Allow jit to warm up before testing
                        if (count < toLong.length) {
                            toLong[count][0] = i;
                            toLong[count][1] = before;
                            toLong[count][2] = after;
                            count++;
                        }
                        if (error > (1000 + delay) || count == toLong.length) {
                            StringBuilder sb = new StringBuilder();
                            for (int j = 0; j < count; j++) {
                                sb.append("wakeup time too inaccurate, iteration ");
                                sb.append(toLong[j][0]);
                                sb.append(", before: ");
                                sb.append(toLong[j][1]);
                                sb.append(" after: ");
                                sb.append(toLong[j][2]);
                                sb.append(" diff: ");
                                sb.append(toLong[j][2] - toLong[j][1]);
                                sb.append("\n");
                            }
                            fail(sb.toString());
                        }
                    }
                }
            } catch (InterruptedException ex) {
                fail("Unexpectedly got an InterruptedException.");
            }
        }
    }

    /**
     * java.lang.Object#wait(long, int)
     */
    public void test_waitJI() {
        // Test for method void java.lang.Object.wait(long, int)

        // Inner class to run test thread.
        class TestThread extends Thread {
            int status;

            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait(0, 1); // Don't wait very long.
                        status = 1;
                        do {
                            lock.wait(0, 0); // Wait to be notified.
                        } while (outstandingNotifications <= 0);
                        outstandingNotifications--;
                        status = 2;
                    } catch (InterruptedException ex) {
                        backgroundException = ex;
                    }
                }
            }
        }

        // Start of test code.

        // Warning:
        // This code relies on threads getting serviced within
        // 1 second of when they are notified. Although this
        // seems reasonable, it could lead to false-failures.

        TestThread thread = new TestThread();
        synchronized (lock) {
            thread.status = 0;
        }
        thread.start();
        synchronized (lock) {
            try {
                lock.wait(1000, 0);
                assertEquals("Thread did not wake after 1sec. (status=" + thread.status + ")",
                        1, thread.status);
                outstandingNotifications++;
                lock.notifyAll();
                lock.wait(1000, 0);
                assertEquals("Thread did not get notified. (status=" +
                        thread.status + ")", 2, thread.status);
            } catch (InterruptedException ex) {
                fail("Unexpectedly got an InterruptedException. (status = " +
                        thread.status + ")");
            }
        }
    }
}
