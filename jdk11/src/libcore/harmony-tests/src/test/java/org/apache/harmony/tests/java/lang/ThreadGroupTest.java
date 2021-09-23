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

import java.util.Vector;

public class ThreadGroupTest extends junit.framework.TestCase {

    private TestThreadDefaultUncaughtExceptionHandler testThreadDefaultUncaughtExceptionHandler;
    private ThreadGroup rootThreadGroup;
    private ThreadGroup initialThreadGroup;
    private Thread.UncaughtExceptionHandler originalThreadDefaultUncaughtExceptionHandler;

    @Override
    protected void setUp() {
        initialThreadGroup = Thread.currentThread().getThreadGroup();
        rootThreadGroup = initialThreadGroup;
        while (rootThreadGroup.getParent() != null) {
            rootThreadGroup = rootThreadGroup.getParent();
        }

        // When running as a CTS test Android will by default treat an uncaught exception as a
        // fatal application error and kill the test. To avoid this the default
        // UncaughtExceptionHandler is replaced for the duration of the test (if one exists). It
        // also allows us to test that ultimately the default handler is called if a ThreadGroup's
        // UncaughtExceptionHandler doesn't handle an exception.
        originalThreadDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        testThreadDefaultUncaughtExceptionHandler = new TestThreadDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(testThreadDefaultUncaughtExceptionHandler);
    }

    @Override
    protected void tearDown() {
        // Reset the uncaughtExceptionHandler to what it was when the test began.
        Thread.setDefaultUncaughtExceptionHandler(originalThreadDefaultUncaughtExceptionHandler);
    }

    // Test for method java.lang.ThreadGroup(java.lang.String)
    public void test_ConstructorLjava_lang_String() {
        // Unfortunately we have to use other APIs as well as we test the constructor
        ThreadGroup initial = initialThreadGroup;
        final String name = "Test name";
        ThreadGroup newGroup = new ThreadGroup(name);
        assertTrue(
                "Has to be possible to create a subgroup of current group using simple constructor",
                newGroup.getParent() == initial);
        assertTrue("Name has to be correct", newGroup.getName().equals(name));

        // cleanup
        newGroup.destroy();
    }

    // Test for method java.lang.ThreadGroup(java.lang.ThreadGroup, java.lang.String)
    public void test_ConstructorLjava_lang_ThreadGroupLjava_lang_String() {
        // Unfortunately we have to use other APIs as well as we test the constructor
        ThreadGroup newGroup = null;
        try {
            newGroup = new ThreadGroup(null, null);
        } catch (NullPointerException e) {
        }
        assertNull("Can't create a ThreadGroup with a null parent", newGroup);

        newGroup = new ThreadGroup(initialThreadGroup, null);
        assertTrue("Has to be possible to create a subgroup of current group",
                newGroup.getParent() == Thread.currentThread().getThreadGroup());

        // Lets start all over
        newGroup.destroy();

        newGroup = new ThreadGroup(rootThreadGroup, "a name here");
        assertTrue("Has to be possible to create a subgroup of root group",
                newGroup.getParent() == rootThreadGroup);

        // Lets start all over
        newGroup.destroy();

        try {
            newGroup = new ThreadGroup(newGroup, "a name here");
        } catch (IllegalThreadStateException e) {
            newGroup = null;
        }
        assertNull("Can't create a subgroup of a destroyed group", newGroup);
    }

    // Test for method int java.lang.ThreadGroup.activeCount()
    public void test_activeCount() {
        ThreadGroup tg = new ThreadGroup("activeCount");
        Thread t1 = new Thread(tg, new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
            }
        });
        int count = tg.activeCount();
        assertTrue("wrong active count: " + count, count == 0);
        t1.start();
        count = tg.activeCount();
        assertTrue("wrong active count: " + count, count == 1);
        t1.interrupt();
        try {
            t1.join();
        } catch (InterruptedException e) {
        }
        // cleanup
        tg.destroy();
    }

    // Test for method void java.lang.ThreadGroup.destroy()
    public void test_destroy() {
        final ThreadGroup originalCurrent = initialThreadGroup;
        ThreadGroup testRoot = new ThreadGroup(originalCurrent, "Test group");
        final int DEPTH = 4;
        final Vector<ThreadGroup> subgroups = buildRandomTreeUnder(testRoot, DEPTH);

        // destroy them all
        testRoot.destroy();

        for (int i = 0; i < subgroups.size(); i++) {
            ThreadGroup child = subgroups.elementAt(i);
            assertEquals("Destroyed child can't have children", 0, child.activeCount());
            boolean passed = false;
            try {
                child.destroy();
            } catch (IllegalThreadStateException e) {
                passed = true;
            }
            assertTrue("Destroyed child can't be destroyed again", passed);
        }

        testRoot = new ThreadGroup(originalCurrent, "Test group (daemon)");
        testRoot.setDaemon(true);

        ThreadGroup child = new ThreadGroup(testRoot, "daemon child");

        // If we destroy the last daemon's child, the daemon should get destroyed
        // as well
        child.destroy();

        boolean passed = false;
        try {
            child.destroy();
        } catch (IllegalThreadStateException e) {
            passed = true;
        }
        assertTrue("Daemon should have been destroyed already", passed);

        passed = false;
        try {
            testRoot.destroy();
        } catch (IllegalThreadStateException e) {
            passed = true;
        }
        assertTrue("Daemon parent should have been destroyed automatically",
                passed);

        assertTrue(
                "Destroyed daemon's child should not be in daemon's list anymore",
                !arrayIncludes(groups(testRoot), child));
        assertTrue("Destroyed daemon should not be in parent's list anymore",
                !arrayIncludes(groups(originalCurrent), testRoot));

        testRoot = new ThreadGroup(originalCurrent, "Test group (daemon)");
        testRoot.setDaemon(true);
        Thread noOp = new Thread(testRoot, null, "no-op thread") {
            @Override
            public void run() {
            }
        };
        noOp.start();

        // Wait for the no-op thread to run inside daemon ThreadGroup
        waitForThreadToDieUninterrupted(noOp);

        passed = false;
        try {
            child.destroy();
        } catch (IllegalThreadStateException e) {
            passed = true;
        }
        assertTrue("Daemon group should have been destroyed already when last thread died", passed);

        testRoot = new ThreadGroup(originalCurrent, "Test group (daemon)");
        noOp = new Thread(testRoot, null, "no-op thread") {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    fail("Should not be interrupted");
                }
            }
        };

        // Has to execute the next lines in an interval < the sleep interval of the no-op thread
        noOp.start();
        passed = false;
        try {
            testRoot.destroy();
        } catch (IllegalThreadStateException its) {
            passed = true;
        }
        assertTrue("Can't destroy a ThreadGroup that has threads", passed);

        // But after the thread dies, we have to be able to destroy the thread group
        waitForThreadToDieUninterrupted(noOp);
        passed = true;
        try {
            testRoot.destroy();
        } catch (IllegalThreadStateException its) {
            passed = false;
        }
        assertTrue("Should be able to destroy a ThreadGroup that has no threads", passed);
    }

    // Test for method java.lang.ThreadGroup.destroy()
    @SuppressWarnings("DeadThread")
    public void test_destroy_subtest0() {
        ThreadGroup group1 = new ThreadGroup("test_destroy_subtest0");
        group1.destroy();
        try {
            new Thread(group1, "test_destroy_subtest0");
            fail("should throw IllegalThreadStateException");
        } catch (IllegalThreadStateException e) {
        }
    }

    // Test for method int java.lang.ThreadGroup.getMaxPriority()
    public void test_getMaxPriority() {
        final ThreadGroup originalCurrent = initialThreadGroup;
        ThreadGroup testRoot = new ThreadGroup(originalCurrent, "Test group");

        boolean passed = true;
        try {
            testRoot.setMaxPriority(Thread.MIN_PRIORITY);
        } catch (IllegalArgumentException iae) {
            passed = false;
        }
        assertTrue("Should be able to set priority", passed);

        assertTrue("New value should be the same as we set",
                testRoot.getMaxPriority() == Thread.MIN_PRIORITY);

        testRoot.destroy();
    }

    // Test for method java.lang.String java.lang.ThreadGroup.getName()
    public void test_getName() {
        final ThreadGroup originalCurrent = initialThreadGroup;
        final String name = "Test group";
        final ThreadGroup testRoot = new ThreadGroup(originalCurrent, name);

        assertTrue("Setting a name&getting does not work", testRoot.getName().equals(name));

        testRoot.destroy();
    }

    // Test for method java.lang.ThreadGroup java.lang.ThreadGroup.getParent()
    public void test_getParent() {
        final ThreadGroup originalCurrent = initialThreadGroup;
        ThreadGroup testRoot = new ThreadGroup(originalCurrent, "Test group");

        assertTrue("Parent is wrong", testRoot.getParent() == originalCurrent);

        // Create some groups, nested some levels.
        final int TOTAL_DEPTH = 5;
        ThreadGroup current = testRoot;
        Vector<ThreadGroup> groups = new Vector<ThreadGroup>();
        // To maintain the invariant that a thread in the Vector is parent
        // of the next one in the collection (and child of the previous one)
        groups.addElement(testRoot);

        for (int i = 0; i < TOTAL_DEPTH; i++) {
            current = new ThreadGroup(current, "level " + i);
            groups.addElement(current);
        }

        // Now we walk the levels down, checking if parent is ok
        for (int i = 1; i < groups.size(); i++) {
            current = groups.elementAt(i);
            ThreadGroup previous = groups.elementAt(i - 1);
            assertTrue("Parent is wrong", current.getParent() == previous);
        }

        testRoot.destroy();
    }

    // Test for method void java.lang.ThreadGroup.list()
    public void test_list() {
        final ThreadGroup originalCurrent = initialThreadGroup;
        final ThreadGroup testRoot = new ThreadGroup(originalCurrent, "Test group");

        // First save the original System.out
        java.io.PrintStream originalOut = System.out;

        try {
            java.io.ByteArrayOutputStream contentsStream = new java.io.ByteArrayOutputStream(100);
            java.io.PrintStream newOut = new java.io.PrintStream(contentsStream);

            // We have to "redirect" System.out to test the method 'list'
            System.setOut(newOut);

            originalCurrent.list();

            /*
             * The output has to look like this:
             *
             * java.lang.ThreadGroup[name=main,maxpri=10] Thread[main,5,main]
             * java.lang.ThreadGroup[name=Test group,maxpri=10]
             */
            String contents = new String(contentsStream.toByteArray());
            boolean passed = (contents.indexOf("ThreadGroup[name=main") != -1) &&
                    (contents.indexOf("Thread[") != -1) &&
                    (contents.indexOf("ThreadGroup[name=Test group") != -1);
            assertTrue("'list()' does not print expected contents. "
                    + "Result from list: "
                    + contents, passed);
            // Do proper cleanup
            testRoot.destroy();

        } finally {
            // No matter what, we need to restore the original System.out
            System.setOut(originalOut);
        }
    }

    // Test for method boolean java.lang.ThreadGroup.parentOf(java.lang.ThreadGroup)
    public void test_parentOfLjava_lang_ThreadGroup() {
        final ThreadGroup originalCurrent = initialThreadGroup;
        final ThreadGroup testRoot = new ThreadGroup(originalCurrent,
                "Test group");
        final int DEPTH = 4;
        buildRandomTreeUnder(testRoot, DEPTH);

        final ThreadGroup[] allChildren = allGroups(testRoot);
        for (ThreadGroup element : allChildren) {
            assertTrue("Have to be parentOf all children", testRoot.parentOf(element));
        }

        assertTrue("Have to be parentOf itself", testRoot.parentOf(testRoot));

        testRoot.destroy();
        assertTrue("Parent can't have test group as subgroup anymore",
                !arrayIncludes(groups(testRoot.getParent()), testRoot));
    }

    // Test for method boolean java.lang.ThreadGroup.isDaemon() and
    // void java.lang.ThreadGroup.setDaemon(boolean)
    public void test_setDaemon_isDaemon() {
        final ThreadGroup originalCurrent = initialThreadGroup;
        final ThreadGroup testRoot = new ThreadGroup(originalCurrent,
                "Test group");

        testRoot.setDaemon(true);
        assertTrue("Setting daemon&getting does not work", testRoot.isDaemon());

        testRoot.setDaemon(false);
        assertTrue("Setting daemon&getting does not work", !testRoot.isDaemon());

        testRoot.destroy();
    }

    /*
     * java.lang.ThreadGroupt#setDaemon(boolean)
     */
    public void test_setDaemon_Parent_Child() {
        ThreadGroup ptg = new ThreadGroup("Parent");
        ThreadGroup ctg = new ThreadGroup(ptg, "Child");

        ctg.setDaemon(true);
        assertTrue(ctg.isDaemon());

        ctg.setDaemon(false);
        assertFalse(ctg.isDaemon());

        ptg.setDaemon(true);
        assertFalse(ctg.isDaemon());

        ptg.setDaemon(false);
        assertFalse(ctg.isDaemon());
    }

    // Test for method void java.lang.ThreadGroup.setMaxPriority(int)
    public void test_setMaxPriorityI() {
        final ThreadGroup originalCurrent = initialThreadGroup;
        ThreadGroup testRoot = new ThreadGroup(originalCurrent, "Test group");

        boolean passed;

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        int currentMax = testRoot.getMaxPriority();
        testRoot.setMaxPriority(Thread.MAX_PRIORITY + 1);
        passed = testRoot.getMaxPriority() == currentMax;
        assertTrue(
                "setMaxPriority: Any value higher than the current one is ignored. Before: "
                        + currentMax + " , after: " + testRoot.getMaxPriority(),
                passed);

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        currentMax = testRoot.getMaxPriority();
        testRoot.setMaxPriority(Thread.MIN_PRIORITY - 1);
        passed = testRoot.getMaxPriority() == Thread.MIN_PRIORITY;
        assertTrue(
                "setMaxPriority: Any value smaller than MIN_PRIORITY is adjusted to MIN_PRIORITY. Before: "
                        + currentMax + " , after: " + testRoot.getMaxPriority(), passed);

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        testRoot.destroy();
        testRoot = new ThreadGroup(originalCurrent, "Test group");

        // Create some groups, nested some levels. Each level will have maxPrio
        // 1 unit smaller than the parent's. However, there can't be a group
        // with priority < Thread.MIN_PRIORITY
        final int TOTAL_DEPTH = testRoot.getMaxPriority() - Thread.MIN_PRIORITY
                - 2;
        ThreadGroup current = testRoot;
        for (int i = 0; i < TOTAL_DEPTH; i++) {
            current = new ThreadGroup(current, "level " + i);
        }

        // Now we walk the levels down, changing the maxPrio and later verifying
        // that the value is indeed 1 unit smaller than the parent's maxPrio.
        int maxPrio, parentMaxPrio;
        current = testRoot;

        // To maintain the invariant that when we are to modify a child,
        // its maxPriority is always 1 unit smaller than its parent's.
        // We have to set it for the root manually, and the loop does the rest
        // for all the other sub-levels
        current.setMaxPriority(current.getParent().getMaxPriority() - 1);

        for (int i = 0; i < TOTAL_DEPTH; i++) {
            maxPrio = current.getMaxPriority();
            parentMaxPrio = current.getParent().getMaxPriority();

            ThreadGroup[] children = groups(current);
            assertEquals("Can only have 1 subgroup", 1, children.length);
            current = children[0];
            assertTrue(
                    "Had to be 1 unit smaller than parent's priority in iteration="
                            + i + " checking->" + current,
                    maxPrio == parentMaxPrio - 1);
            current.setMaxPriority(maxPrio - 1);

            // The next test is sort of redundant, since in next iteration it
            // will be the parent tGroup, so the test will be done.
            assertTrue("Had to be possible to change max priority", current
                    .getMaxPriority() == maxPrio - 1);
        }

        assertTrue(
                "Priority of leaf child group has to be much smaller than original root group",
                current.getMaxPriority() == testRoot.getMaxPriority() - TOTAL_DEPTH);

        testRoot.destroy();

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        passed = true;
        testRoot = new ThreadGroup(originalCurrent, "Test group");
        try {
            testRoot.setMaxPriority(Thread.MAX_PRIORITY);
        } catch (IllegalArgumentException iae) {
            passed = false;
        }
        assertTrue(
                "Max Priority = Thread.MAX_PRIORITY should be possible if the test is run with default system ThreadGroup as root",
                passed);
        testRoot.destroy();
    }

    /*
     * Test for method void java.lang.ThreadGroup.uncaughtException(java.lang.Thread,
     * java.lang.Throwable)
     * Tests if a Thread tells its ThreadGroup about ThreadDeath.
     */
    public void test_uncaughtException_threadDeath() {
        final boolean[] passed = new boolean[1];

        ThreadGroup testRoot = new ThreadGroup(rootThreadGroup,
                "Test Forcing a throw of ThreadDeath") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (e instanceof ThreadDeath) {
                    passed[0] = true;
                }
                // always forward, any exception
                super.uncaughtException(t, e);
            }
        };

        final ThreadDeath threadDeath = new ThreadDeath();
        Thread thread = new Thread(testRoot, null, "suicidal thread") {
            @Override
            public void run() {
                throw threadDeath;
            }
        };
        thread.start();
        waitForThreadToDieUninterrupted(thread);
        testThreadDefaultUncaughtExceptionHandler.assertWasCalled(thread, threadDeath);

        testRoot.destroy();
        assertTrue(
                "Any thread should notify its ThreadGroup about its own death, even if suicide:"
                        + testRoot, passed[0]);
    }

    /*
     * Test for method void java.lang.ThreadGroup.uncaughtException(java.lang.Thread,
     * java.lang.Throwable)
     * Test if a Thread tells its ThreadGroup about a natural (non-exception) death.
     */
    public void test_uncaughtException_naturalDeath() {
        final boolean[] failed = new boolean[1];

        ThreadGroup testRoot = new ThreadGroup(initialThreadGroup, "Test ThreadDeath") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                failed[0] = true;

                // always forward any exception
                super.uncaughtException(t, e);
            }
        };

        Thread thread = new Thread(testRoot, null, "no-op thread");
        thread.start();
        waitForThreadToDieUninterrupted(thread);
        testThreadDefaultUncaughtExceptionHandler.assertWasNotCalled();
        testRoot.destroy();
        assertFalse("A thread should not call uncaughtException when it dies:"
                + testRoot, failed[0]);
    }

    /*
     * Test for method void java.lang.ThreadGroup.uncaughtException(java.lang.Thread,
     * java.lang.Throwable)
     * Test if a Thread tells its ThreadGroup about an Exception
     */
    public void test_uncaughtException_runtimeException() {
        // Our own exception class
        class TestException extends RuntimeException {
            private static final long serialVersionUID = 1L;
        }

        final boolean[] passed = new boolean[1];

        ThreadGroup testRoot = new ThreadGroup(initialThreadGroup, "Test other Exception") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (e instanceof TestException) {
                    passed[0] = true;
                }
                // always forward any exception
                super.uncaughtException(t, e);
            }
        };

        final TestException testException = new TestException();
        Thread thread = new Thread(testRoot, null, "RuntimeException thread") {
            @Override
            public void run() {
                throw testException;
            }
        };
        thread.start();
        waitForThreadToDieUninterrupted(thread);
        testThreadDefaultUncaughtExceptionHandler.assertWasCalled(thread, testException);
        testRoot.destroy();
        assertTrue(
                "Any thread should notify its ThreadGroup about an uncaught exception:"
                        + testRoot, passed[0]);
    }

    /*
     * Test for method void java.lang.ThreadGroup.uncaughtException(java.lang.Thread,
     * java.lang.Throwable)
     * Test if a handler doesn't pass on the exception to super.uncaughtException that's ok.
     */
    public void test_uncaughtException_exceptionHandledByHandler() {
        // Our own exception class
        class TestException extends RuntimeException {
            private static final long serialVersionUID = 1L;
        }

        ThreadGroup testRoot = new ThreadGroup(initialThreadGroup, "Test other Exception") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                // Swallow TestException and always forward any other exception
                if (!(e instanceof TestException)) {
                    super.uncaughtException(t, e);
                }
            }
        };

        final TestException testException = new TestException();
        Thread thread = new Thread(testRoot, null, "RuntimeException thread") {
            @Override
            public void run() {
                throw testException;
            }
        };
        thread.start();
        waitForThreadToDieUninterrupted(thread);
        testThreadDefaultUncaughtExceptionHandler.assertWasNotCalled();
        testRoot.destroy();
    }

    /*
     * Test for method void java.lang.ThreadGroup.uncaughtException(java.lang.Thread,
     * java.lang.Throwable)
     * Tests an exception thrown by the handler itself.
     */
    public void test_uncaughtException_exceptionInUncaughtException() {
        // Our own uncaught exception classes
        class UncaughtException extends RuntimeException {
            private static final long serialVersionUID = 1L;
        }

        ThreadGroup testRoot = new ThreadGroup(initialThreadGroup,
                "Test Exception in uncaught exception") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                // This should be no-op according to the spec
                throw new UncaughtException();
            }
        };

        Thread thread = new Thread(testRoot, null, "no-op thread") {
            @Override
            public void run() {
                throw new RuntimeException();
            }
        };
        thread.start();
        waitForThreadToDieUninterrupted(thread);
        testThreadDefaultUncaughtExceptionHandler.assertWasNotCalled();
        testRoot.destroy();
    }

    private static ThreadGroup[] allGroups(ThreadGroup parent) {
        int count = parent.activeGroupCount();
        ThreadGroup[] all = new ThreadGroup[count];
        parent.enumerate(all, true);
        return all;
    }

    private static void asyncBuildRandomTreeUnder(final ThreadGroup aGroup,
            final int depth, final Vector<ThreadGroup> allCreated) {
        if (depth <= 0) {
            return;
        }

        final int maxImmediateSubgroups = random(3);
        for (int i = 0; i < maxImmediateSubgroups; i++) {
            final int iClone = i;
            final String name = " Depth = " + depth + ",N = " + iClone
                    + ",Vector size at creation: " + allCreated.size();
            // Use concurrency to maximize chance of exposing concurrency bugs
            // in ThreadGroups
            Thread t = new Thread(aGroup, name) {
                @Override
                public void run() {
                    ThreadGroup newGroup = new ThreadGroup(aGroup, name);
                    allCreated.addElement(newGroup);
                    asyncBuildRandomTreeUnder(newGroup, depth - 1, allCreated);
                }
            };
            t.start();
        }

    }

    private static Vector<ThreadGroup> asyncBuildRandomTreeUnder(final ThreadGroup aGroup,
            final int depth) {
        Vector<ThreadGroup> result = new Vector<ThreadGroup>();
        asyncBuildRandomTreeUnder(aGroup, depth, result);
        return result;

    }

    private static ThreadGroup[] groups(ThreadGroup parent) {
        // No API to get the count of immediate children only ?
        int count = parent.activeGroupCount();
        ThreadGroup[] all = new ThreadGroup[count];
        parent.enumerate(all, false);
        // Now we may have nulls in the array, we must find the actual size
        int actualSize = 0;
        for (; actualSize < all.length; actualSize++) {
            if (all[actualSize] == null) {
                break;
            }
        }
        ThreadGroup[] result;
        if (actualSize == all.length) {
            result = all;
        } else {
            result = new ThreadGroup[actualSize];
            System.arraycopy(all, 0, result, 0, actualSize);
        }

        return result;

    }

    private static int random(int max) {
        return 1 + ((new Object()).hashCode() % max);
    }

    private static Vector<ThreadGroup> buildRandomTreeUnder(ThreadGroup aGroup, int depth) {
        Vector<ThreadGroup> result = asyncBuildRandomTreeUnder(aGroup, depth);
        while (true) {
            int sizeBefore = result.size();
            try {
                Thread.sleep(1000);
                int sizeAfter = result.size();
                // If no activity for a while, we assume async building may be
                // done.
                if (sizeBefore == sizeAfter) {
                    // It can only be done if no more threads. Unfortunately we
                    // are relying on this API to work as well.
                    // If it does not, we may loop forever.
                    if (aGroup.activeCount() == 0) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
            }
        }
        return result;

    }

    private static boolean arrayIncludes(Object[] array, Object toTest) {
        for (Object element : array) {
            if (element == toTest) {
                return true;
            }
        }
        return false;
    }

    private static void waitForThreadToDieUninterrupted(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException ie) {
            fail("Should not have been interrupted");
        }
    }

    private static class TestThreadDefaultUncaughtExceptionHandler
            implements Thread.UncaughtExceptionHandler {

        private boolean called;
        private Throwable ex;
        private Thread thread;

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            this.called = true;
            this.thread = thread;
            this.ex = ex;
        }

        public void assertWasCalled(Thread thread, Throwable ex) {
            assertTrue(called);
            assertSame(this.thread, thread);
            assertSame(this.ex, ex);
        }

        public void assertWasNotCalled() {
            assertFalse(called);
        }
    }

}
