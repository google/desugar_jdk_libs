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
package libcore.junit.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Provides support for testing classes that own resources (using {@code CloseGuard} mechanism)
 * which must not leak.
 *
 * <p><strong>This will not detect any resource leakages in OpenJDK</strong></p>
 *
 * <p>Typical usage for developers that want to ensure that their tests do not leak resources:
 * <pre>
 * public class ResourceTest {
 *  {@code @Rule}
 *   public LeakageDetectorRule leakageDetectorRule = ResourceLeakageDetector.getRule();
 *
 *  ...
 * }
 * </pre>
 *
 * <p>Developers that need to test the resource itself to ensure it is properly protected can
 * use {@link LeakageDetectorRule#assertUnreleasedResourceCount(Object, int)
 * assertUnreleasedResourceCount(Object, int)}.
 */
public class ResourceLeakageDetector {
    private static final LeakageDetectorRule LEAKAGE_DETECTOR_RULE;
    private static final BiConsumer<Object, Integer> FINALIZER_CHECKER;

    static {
        LeakageDetectorRule leakageDetectorRule;
        BiConsumer<Object, Integer> finalizerChecker;
        try {
            // Make sure that the CloseGuard class exists; this ensures that this is not
            // running on a RI JVM.
            Class.forName("dalvik.system.CloseGuard");

            // Access the underlying support class using reflection in order to prevent any compile
            // time dependencies on it so as to allow this to compile on OpenJDK.
            Class<?> closeGuardSupportClass = Class.forName(
                    "libcore.dalvik.system.CloseGuardSupport");
            Method method = closeGuardSupportClass.getMethod("getRule");
            leakageDetectorRule = new LeakageDetectorRule((TestRule) method.invoke(null));

            finalizerChecker = getFinalizerChecker(closeGuardSupportClass);

        } catch (ReflectiveOperationException e) {
            System.err.println("Resource leakage will not be detected; "
                    + "this is expected in the reference implementation");
            e.printStackTrace(System.err);

            // Could not access the class for some reason so have a rule that does nothing and a
            // finalizer checker that checks nothing. This should ensure that tests work properly
            // on OpenJDK even though it does not support CloseGuard.
            leakageDetectorRule = new LeakageDetectorRule(RuleChain.emptyRuleChain());
            finalizerChecker = new BiConsumer<Object, Integer>() {
                @Override
                public void accept(Object o, Integer integer) {
                    // Do nothing.
                }
            };
        }

        LEAKAGE_DETECTOR_RULE = leakageDetectorRule;
        FINALIZER_CHECKER = finalizerChecker;
    }

    @SuppressWarnings("unchecked")
    private static BiConsumer<Object, Integer> getFinalizerChecker(Class<?> closeGuardSupportClass)
            throws ReflectiveOperationException {
        Method method = closeGuardSupportClass.getMethod("getFinalizerChecker");
        return (BiConsumer<Object, Integer>) method.invoke(null);
    }

    /**
     * @return the {@link LeakageDetectorRule}
     */
    public static LeakageDetectorRule getRule() {
       return LEAKAGE_DETECTOR_RULE;
    }

    /**
     * A {@link TestRule} that will fail a test if it detects any resources that were allocated
     * during the test but were not released.
     *
     * <p>This only tracks resources that were allocated on the test thread, although it does not
     * care what thread they were released on. This avoids flaky false positives where a background
     * thread allocates a resource during a test but releases it after the test.
     *
     * <p>It is still possible to have a false positive in the case where the test causes a caching
     * mechanism to open a resource and hold it open past the end of the test. In that case if there
     * is no way to clear the cached data then it should be relatively simple to move the code that
     * invokes the caching mechanism to outside the scope of this rule. i.e.
     *
     * <pre>
     *    {@code @Rule}
     *     public final TestRule ruleChain = org.junit.rules.RuleChain
     *         .outerRule(new ...invoke caching mechanism...)
     *         .around(ResourceLeakageDetector.getRule());
     * </pre>
     */
    public static class LeakageDetectorRule implements TestRule {

        private final TestRule leakageDetectorRule;
        private boolean leakageDetectionEnabledForTest;

        private LeakageDetectorRule(TestRule leakageDetectorRule) {
            this.leakageDetectorRule = leakageDetectorRule;
        }

        @Override
        public Statement apply(Statement base, Description description) {
            // Make the resource leakage detector rule optional based on the presence of an
            // annotation.
            if (description.getAnnotation(DisableResourceLeakageDetection.class) != null) {
                leakageDetectionEnabledForTest = false;
                return base;
            } else {
                leakageDetectionEnabledForTest = true;
                return leakageDetectorRule.apply(base, description);
            }
        }

        /**
         * Ensure that when the supplied object is finalized that it detects the expected number of
         * unreleased resources.
         *
         * <p>This helps ensure that classes which own resources protected using {@code CloseGuard}
         * support leakage detection.
         *
         * <p>This must only be called as part of the currently running test and the test must not
         * be annotated with {@link DisableResourceLeakageDetection} as that will disable leakage
         * detection. Attempting to use it with leakage detection disabled by the annotation will
         * result in a test failure.
         *
         * <p>Use as follows, 'open' and 'close' refer to the methods in {@code CloseGuard}:
         * <pre>
         * public class ResourceTest {
         *  {@code @Rule}
         *   public LeakageDetectorRule leakageDetectorRule = ResourceLeakageDetector.getRule();
         *
         *  {@code @Test}
         *   public void testAutoCloseableResourceIsProtected() {
         *     try (AutoCloseable object = ...open a protected resource...) {
         *       leakageDetectorRule.assertUnreleasedResourceCount(object, 1);
         *     }
         *   }
         *
         *  {@code @Test}
         *   public void testResourceIsProtected() {
         *     NonAutoCloseable object = ...open a protected resource...;
         *     leakageDetectorRule.assertUnreleasedResourceCount(object, 1);
         *     object.release();
         *   }
         * }
         * </pre>
         *
         * <p>There are two test method templates, the one to use depends on whether the resource is
         * {@link AutoCloseable} or not. Each method tests the following:</p>
         * <ul>
         * <li>The {@code @Rule} will ensure that the test method does not leak any resources. That
         * will make sure that if it actually is protected by {@code CloseGuard} that it correctly
         * closes it. It does not actually ensure that it is protected.
         * <li>The call to this method will ensure that the resource is actually protected by
         * {@code CloseGuard}.
         * </ul>
         *
         * <p>The above tests will work on the reference implementation as this method does nothing
         * when {@code CloseGuard} is not supported.
         *
         * @param owner the object that owns the resource and uses {@code CloseGuard} object to
         *         detect when the resource is not released.
         * @param expectedCount the expected number of unreleased resources, i.e. the number of
         *         {@code CloseGuard} objects owned by the resource, and on which it calls
         *         {@code CloseGuard.warnIfOpen()} in its {@link #finalize()} method; usually 1.
         */
        public void assertUnreleasedResourceCount(Object owner, int expectedCount) {
            if (leakageDetectionEnabledForTest) {
                FINALIZER_CHECKER.accept(owner, expectedCount);
            } else {
                throw new IllegalStateException(
                        "Does not work when leakage detection has been disabled; remove the "
                                + "@DisableResourceLeakageDetection from the test method");
            }
        }
    }

    /**
     * An annotation that indicates that the test should not be run with resource leakage detection
     * enabled.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface DisableResourceLeakageDetection {

        /**
         * The explanation as to why resource leakage detection is disabled for this test.
         */
        String why();

        /**
         * The bug reference to the bug that was opened to fix the issue.
         */
        String bug();
    }
}
