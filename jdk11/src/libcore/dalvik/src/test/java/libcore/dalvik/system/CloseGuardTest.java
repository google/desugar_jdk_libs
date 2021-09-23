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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import dalvik.system.CloseGuard;

/**
 * Tests {@link CloseGuard}.
 */
public class CloseGuardTest {

    /**
     * Resets the {@link CloseGuard#ENABLED} state back to the value it had when the test started.
     */
    @Rule
    public TestRule rule = this::preserveEnabledState;

    private Statement preserveEnabledState(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                boolean oldEnabledState = CloseGuard.isEnabled();
                try {
                    base.evaluate();
                } finally {
                    CloseGuard.setEnabled(oldEnabledState);
                }
            }
        };
    }

    @Test
    public void testEnabled_NotOpen() throws Throwable {
        CloseGuard.setEnabled(true);
        ResourceOwner owner = new ResourceOwner();
        assertUnreleasedResources(owner, 0);
    }

    @Test
    public void testEnabled_OpenNotClosed() throws Throwable {
        CloseGuard.setEnabled(true);
        ResourceOwner owner = new ResourceOwner();
        owner.open();
        assertUnreleasedResources(owner, 1);
    }

    @Test
    public void testEnabled_OpenWithCallsiteNotClosed() throws Throwable {
        CloseGuard.setEnabled(true);
        ResourceOwner owner = new ResourceOwner();
        owner.openWithCallsite("testEnabled_OpoenWIthCallsiteNotClosed");
        assertUnreleasedResources(owner, 1);
    }

    @Test
    public void testEnabled_OpenThenClosed() throws Throwable {
        CloseGuard.setEnabled(true);
        ResourceOwner owner = new ResourceOwner();
        owner.open();
        owner.close();
        assertUnreleasedResources(owner, 0);
    }

    @Test
    public void testEnabled_OpenWithCallsiteThenClosed() throws Throwable {
        CloseGuard.setEnabled(true);
        ResourceOwner owner = new ResourceOwner();
        owner.openWithCallsite("testEnabled_OpenWithCallsiteThenClosed");
        owner.close();
        assertUnreleasedResources(owner, 0);
    }

    @Test
    public void testEnabledWhenCreated_DisabledWhenOpen() throws Throwable {
        CloseGuard.setEnabled(true);
        ResourceOwner owner = new ResourceOwner();
        CloseGuard.setEnabled(false);
        owner.open();

        // Although the resource was not released it should not report it because CloseGuard was
        // not enabled when the CloseGuard was opened.
        assertUnreleasedResources(owner, 0);
    }

    @Test
    public void testEnabledWhenOpened_DisabledWhenFinalized() throws Throwable {
        CloseGuard.setEnabled(true);
        ResourceOwner owner = new ResourceOwner();
        owner.open();
        CloseGuard.setEnabled(false);

        // We report if CloseGuard was enabled on open.
        assertUnreleasedResources(owner, 1);
    }

    @Test
    public void testDisabled_NotOpen() throws Throwable {
        CloseGuard.setEnabled(false);
        ResourceOwner owner = new ResourceOwner();
        assertUnreleasedResources(owner, 0);
    }

    @Test
    public void testDisabled_OpenNotClosed() throws Throwable {
        CloseGuard.setEnabled(false);
        ResourceOwner owner = new ResourceOwner();
        owner.open();
        assertUnreleasedResources(owner, 0);
    }

    @Test
    public void testDisabled_OpenWithCallsiteNotClosed() throws Throwable {
        CloseGuard.setEnabled(false);
        ResourceOwner owner = new ResourceOwner();
        owner.openWithCallsite("testDisabled_OpenWithCallsiteNotClosed");
        assertUnreleasedResources(owner, 0);
    }

    @Test
    public void testDisabled_OpenThenClosed() throws Throwable {
        CloseGuard.setEnabled(false);
        ResourceOwner owner = new ResourceOwner();
        owner.open();
        owner.close();
        assertUnreleasedResources(owner, 0);
    }

    @Test
    public void testDisabled_OpenWithCallsiteThenClosed() throws Throwable {
        CloseGuard.setEnabled(false);
        ResourceOwner owner = new ResourceOwner();
        owner.openWithCallsite("testDisabled_OpenWithCallsiteThenClosed");
        owner.close();
        assertUnreleasedResources(owner, 0);
    }

    @Test
    public void testDisabledWhenCreated_EnabledWhenOpen() throws Throwable {
        CloseGuard.setEnabled(false);
        ResourceOwner owner = new ResourceOwner();
        CloseGuard.setEnabled(true);
        owner.open();

        // The enabled state only matters during the open call.
        assertUnreleasedResources(owner, 1);
    }

    private void assertUnreleasedResources(ResourceOwner owner, int expectedCount)
            throws Throwable {
        try {
            CloseGuardSupport.getFinalizerChecker().accept(owner, expectedCount);
        } finally {
            // Close the resource so that CloseGuard does not generate a warning for real when it
            // is actually finalized.
            owner.close();
        }
    }

    /**
     * A test user of {@link CloseGuard}.
     */
    private static class ResourceOwner {

        private final CloseGuard closeGuard;

        ResourceOwner() {
            closeGuard = CloseGuard.get();
        }

        public void open() {
            closeGuard.open("close");
        }

        public void openWithCallsite(String callsite) {
            closeGuard.openWithCallSite("close", callsite);
        }

        public void close() {
            closeGuard.close();
        }

        /**
         * Make finalize public so that it can be tested directly without relying on garbage
         * collection to trigger it.
         */
        @Override
        public void finalize() throws Throwable {
            closeGuard.warnIfOpen();
            super.finalize();
        }
    }
}
