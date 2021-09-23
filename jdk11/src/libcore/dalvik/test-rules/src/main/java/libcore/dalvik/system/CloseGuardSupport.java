/*
 * Copyright (C) 2016 The Android Open Source Project
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
package libcore.dalvik.system;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import dalvik.system.CloseGuard;

/**
 * Provides support for testing classes that use {@link CloseGuard} in order to detect resource
 * leakages.
 *
 * <p>This class should not be used directly by tests as that will prevent them from being
 * compilable and testable on OpenJDK platform. Instead they should use
 * {@code libcore.junit.util.ResourceLeakageDetector} which accesses the capabilities of this using
 * reflection and if it cannot find it (because it is running on OpenJDK) then it will just skip
 * leakage detection.
 *
 * <p>This provides two entry points that are accessed reflectively:
 * <ul>
 * <li>
 * <p>The {@link #getRule()} method. This returns a {@link TestRule} that will fail a test if it
 * detects any resources that were allocated during the test but were not released.
 *
 * <p>This only tracks resources that were allocated on the test thread, although it does not care
 * what thread they were released on. This avoids flaky false positives where a background thread
 * allocates a resource during a test but releases it after the test.
 *
 * <p>It is still possible to have a false positive in the case where the test causes a caching
 * mechanism to open a resource and hold it open past the end of the test. In that case if there is
 * no way to clear the cached data then it should be relatively simple to move the code that invokes
 * the caching mechanism to outside the scope of this rule. i.e.
 *
 * <pre>{@code
 *     @Rule
 *     public final TestRule ruleChain = org.junit.rules.RuleChain
 *         .outerRule(new ...invoke caching mechanism...)
 *         .around(CloseGuardSupport.getRule());
 * }</pre>
 * </li>
 * <li>
 * <p>The {@link #getFinalizerChecker()} method. This returns a {@link BiConsumer} that takes an
 * object that owns resources and an expected number of unreleased resources. It will call the
 * {@link Object#finalize()} method on the object using reflection and throw an
 * {@link AssertionError} if the number of reported unreleased resources does not match the
 * expected number.
 * </li>
 * </ul>
 */
public class CloseGuardSupport {

    private static final TestRule CLOSE_GUARD_RULE = new FailTestWhenResourcesNotClosedRule();

    /**
     * Get a {@link TestRule} that will detect when resources that use the {@link CloseGuard}
     * mechanism are not cleaned up properly by a test.
     *
     * <p>If the {@link CloseGuard} mechanism is not supported, e.g. on OpenJDK, then the returned
     * rule does nothing.
     */
    public static TestRule getRule() {
        return CLOSE_GUARD_RULE;
    }

    private CloseGuardSupport() {
    }

    /**
     * Fails a test when resources are not cleaned up properly.
     */
    private static class FailTestWhenResourcesNotClosedRule implements TestRule {
        /**
         * Returns a {@link Statement} that will fail the test if it ends with unreleased resources.
         * @param base the test to be run.
         */
        public Statement apply(Statement base, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    // Get the previous tracker so that it can be restored afterwards.
                    CloseGuard.Tracker previousTracker = CloseGuard.getTracker();
                    // Get the previous enabled state so that it can be restored afterwards.
                    boolean previousEnabled = CloseGuard.isEnabled();
                    TestCloseGuardTracker tracker = new TestCloseGuardTracker();
                    Throwable thrown = null;
                    try {
                        // Set the test tracker and enable close guard detection.
                        CloseGuard.setTracker(tracker);
                        CloseGuard.setEnabled(true);
                        base.evaluate();
                    } catch (Throwable throwable) {
                        // Catch and remember the throwable so that it can be rethrown in the
                        // finally block.
                        thrown = throwable;
                    } finally {
                        // Restore the previous tracker and enabled state.
                        CloseGuard.setEnabled(previousEnabled);
                        CloseGuard.setTracker(previousTracker);

                        Collection<Throwable> allocationSites =
                                tracker.getAllocationSitesForUnreleasedResources();
                        if (!allocationSites.isEmpty()) {
                            if (thrown == null) {
                                thrown = new IllegalStateException(
                                        "Unreleased resources found in test");
                            }
                            for (Throwable allocationSite : allocationSites) {
                                thrown.addSuppressed(allocationSite);
                            }
                        }
                        if (thrown != null) {
                            throw thrown;
                        }
                    }
                }
            };
        }
    }

    /**
     * A tracker that keeps a record of the allocation sites for all resources allocated but not
     * yet released.
     *
     * <p>It only tracks resources allocated for the test thread.
     */
    private static class TestCloseGuardTracker implements CloseGuard.Tracker {

        /**
         * A set would be preferable but this is the closest that matches the concurrency
         * requirements for the use case which prioritise speed of addition and removal over
         * iteration and access.
         */
        private final Set<Throwable> allocationSites =
                Collections.newSetFromMap(new ConcurrentHashMap<>());

        private final Thread testThread = Thread.currentThread();

        @Override
        public void open(Throwable allocationSite) {
            if (Thread.currentThread() == testThread) {
                allocationSites.add(allocationSite);
            }
        }

        @Override
        public void close(Throwable allocationSite) {
            // Closing the resource twice could pass null into here.
            if (allocationSite != null) {
                allocationSites.remove(allocationSite);
            }
        }

        /**
         * Get the collection of allocation sites for any unreleased resources.
         */
        Collection<Throwable> getAllocationSitesForUnreleasedResources() {
            return new ArrayList<>(allocationSites);
        }
    }

    private static final BiConsumer<Object, Integer> FINALIZER_CHECKER
            = new BiConsumer<Object, Integer>() {
        @Override
        public void accept(Object resourceOwner, Integer expectedCount) {
            finalizerChecker(resourceOwner, expectedCount);
        }
    };

    /**
     * Get access to a {@link BiConsumer} that will determine how many unreleased resources the
     * first parameter owns and throw a {@link AssertionError} if that does not match the
     * expected number of resources specified by the second parameter.
     *
     * <p>This uses a {@link BiConsumer} as it is a standard interface that is available in all
     * environments. That helps avoid the caller from having compile time dependencies on this
     * class which will not be available on OpenJDK.
     */
    public static BiConsumer<Object, Integer> getFinalizerChecker() {
        return FINALIZER_CHECKER;
    }

    /**
     * Checks that the supplied {@code resourceOwner} has overridden the {@link Object#finalize()}
     * method and uses {@link CloseGuard#warnIfOpen()} correctly to detect when the resource is
     * not released.
     *
     * @param resourceOwner the owner of the resource protected by {@link CloseGuard}.
     * @param expectedCount the expected number of unreleased resources to be held by the owner.
     *
     */
    private static void finalizerChecker(Object resourceOwner, int expectedCount) {
        Class<?> clazz = resourceOwner.getClass();
        Method finalizer = null;
        while (clazz != null && clazz != Object.class) {
            try {
                finalizer = clazz.getDeclaredMethod("finalize");
                break;
            } catch (NoSuchMethodException e) {
                // Carry on up the class hierarchy.
                clazz = clazz.getSuperclass();
            }
        }

        if (finalizer == null) {
            // No finalizer method could be found.
            throw new AssertionError("Class " + resourceOwner.getClass().getName()
                    + " does not have a finalize() method");
        }

        // Make the method accessible.
        finalizer.setAccessible(true);

        CloseGuard.Reporter oldReporter = CloseGuard.getReporter();
        try {
            CollectingReporter reporter = new CollectingReporter();
            CloseGuard.setReporter(reporter);

            // Invoke the finalizer to cause it to get CloseGuard to report a problem if it has
            // not yet been closed.
            try {
                finalizer.invoke(resourceOwner);
            } catch (ReflectiveOperationException e) {
                throw new AssertionError(
                        "Could not invoke the finalizer() method on " + resourceOwner, e);
            }

            reporter.assertUnreleasedResources(expectedCount);
        } finally {
            CloseGuard.setReporter(oldReporter);
        }
    }

    /**
     * A {@link CloseGuard.Reporter} that collects any reports about unreleased resources.
     */
    private static class CollectingReporter implements CloseGuard.Reporter {

        private final Thread callingThread = Thread.currentThread();

        private final List<Throwable> unreleasedResourceAllocationSites = new ArrayList<>();
        private final List<String> unreleasedResourceAllocationCallsites = new ArrayList<>();

        @Override
        public void report(String message, Throwable allocationSite) {
            // Only care about resources that are not reported on this thread.
            if (callingThread == Thread.currentThread()) {
                unreleasedResourceAllocationSites.add(allocationSite);
            }
        }

        @Override
        public void report(String message) {
            // Only care about resources that are not reported on this thread.
            if (callingThread == Thread.currentThread()) {
                unreleasedResourceAllocationCallsites.add(message);
            }
        }

        void assertUnreleasedResources(int expectedCount) {
            int unreleasedResourceCount = unreleasedResourceAllocationSites.size()
                    + unreleasedResourceAllocationCallsites.size();
            if (unreleasedResourceCount == expectedCount) {
                return;
            }

            AssertionError error = new AssertionError(
                    "Expected " + expectedCount + " unreleased resources, found "
                            + unreleasedResourceCount + "; see suppressed exceptions for details");
            for (Throwable unreleasedResourceAllocationSite : unreleasedResourceAllocationSites) {
                error.addSuppressed(unreleasedResourceAllocationSite);
            }
            for (String unreleasedResourceAllocationCallsite : unreleasedResourceAllocationCallsites) {
                error.addSuppressed(new Throwable(unreleasedResourceAllocationCallsite));
            }
            throw error;
        }
    }
}
