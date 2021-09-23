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

import java.util.Collections;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.JUnit4;

import dalvik.system.CloseGuard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class CloseGuardSupportTest {

    @Test
    public void testDoesReleaseResource() {
        List<Failure> failures = JUnitCore.runClasses(DoesReleaseResource.class).getFailures();
        assertEquals(Collections.emptyList(), failures);
    }

    public static class DoesReleaseResource {
        @Rule public TestRule rule = CloseGuardSupport.getRule();
        @Test public void test() {
            CloseGuard closeGuard = CloseGuard.get();
            closeGuard.open("test resource");
            closeGuard.close();
        }
    }

    @Test
    public void testDoesReleaseResourceTwice() {
        List<Failure> failures = JUnitCore.runClasses(DoesReleaseResourceTwice.class).getFailures();
        assertEquals(Collections.emptyList(), failures);
    }

    public static class DoesReleaseResourceTwice {
        @Rule public TestRule rule = CloseGuardSupport.getRule();
        @Test public void test() {
            CloseGuard closeGuard = CloseGuard.get();
            closeGuard.open("test resource");
            closeGuard.close();
            closeGuard.close();
        }
    }

    @Test
    public void testDoesNotReleaseResource() {
        List<Failure> failures = JUnitCore.runClasses(DoesNotReleaseResource.class).getFailures();
        assertEquals("Failure count", 1, failures.size());
        Failure failure = failures.get(0);
        checkResourceNotReleased(failure, "Unreleased resources found in test");
    }

    public static class DoesNotReleaseResource {
        @Rule public TestRule rule = CloseGuardSupport.getRule();
        @Test public void test() {
            CloseGuard closeGuard = CloseGuard.get();
            closeGuard.open("test resource");
        }
    }

    @Test
    public void testDoesNotReleaseResourceDueToFailure() {
        List<Failure> failures = JUnitCore
                .runClasses(DoesNotReleaseResourceDueToFailure.class)
                .getFailures();
        assertEquals("Failure count", 1, failures.size());
        Failure failure = failures.get(0);
        checkResourceNotReleased(failure, "failure");
    }

    public static class DoesNotReleaseResourceDueToFailure {
        @Rule public TestRule rule = CloseGuardSupport.getRule();
        @Test public void test() {
            CloseGuard closeGuard = CloseGuard.get();
            closeGuard.open("test resource");
            fail("failure");
        }
    }

    @Test
    public void testResourceOwnerDoesNotOverrideFinalize() {
        List<Failure> failures = JUnitCore
                .runClasses(ResourceOwnerDoesNotOverrideFinalize.class)
                .getFailures();
        assertEquals("Failure count", 1, failures.size());
        Failure failure = failures.get(0);
        assertEquals("Class java.lang.String does not have a finalize() method",
                failure.getMessage());
    }

    public static class ResourceOwnerDoesNotOverrideFinalize {
        @Rule public TestRule rule = CloseGuardSupport.getRule();
        @Test
        public void test() {
            CloseGuardSupport.getFinalizerChecker().accept("not resource owner", 0);
        }
    }

    @Test
    public void testResourceOwnerOverridesFinalizeButDoesNotReportLeak() {
        List<Failure> failures = JUnitCore
                .runClasses(ResourceOwnerOverridesFinalizeButDoesNotReportLeak.class)
                .getFailures();
        assertEquals("Failure count", 1, failures.size());
        Failure failure = failures.get(0);
        assertEquals("Expected 1 unreleased resources, found 0;"
                        + " see suppressed exceptions for details",
                failure.getMessage());
    }

    public static class ResourceOwnerOverridesFinalizeButDoesNotReportLeak {
        @Rule public TestRule rule = CloseGuardSupport.getRule();
        @Test
        public void test() {
            CloseGuardSupport.getFinalizerChecker().accept(new Object() {
                @Override
                protected void finalize() throws Throwable {
                    super.finalize();
                }
            }, 1);
        }
    }

    @Test
    public void testResourceOwnerOverridesFinalizeAndReportsLeak() {
        List<Failure> failures = JUnitCore
                .runClasses(ResourceOwnerOverridesFinalizeAndReportsLeak.class)
                .getFailures();
        assertEquals("Failure count", 1, failures.size());
        Failure failure = failures.get(0);
        checkResourceNotReleased(failure, "Unreleased resources found in test");
    }

    public static class ResourceOwnerOverridesFinalizeAndReportsLeak {
        @Rule public TestRule rule = CloseGuardSupport.getRule();
        @Test
        public void test() {
            CloseGuardSupport.getFinalizerChecker().accept(new Object() {
                private CloseGuard guard = CloseGuard.get();
                {
                    guard.open("test resource");
                }
                @Override
                protected void finalize() throws Throwable {
                    guard.warnIfOpen();
                    super.finalize();
                }
            }, 1);
        }
    }

    private void checkResourceNotReleased(Failure failure, String expectedMessage) {
        @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
        Throwable exception = failure.getException();
        assertEquals(expectedMessage, exception.getMessage());
        Throwable[] suppressed = exception.getSuppressed();
        assertEquals("Suppressed count", 1, suppressed.length);
        exception = suppressed[0];
        assertEquals("Explicit termination method 'test resource' not called",
                exception.getMessage());
    }
}
