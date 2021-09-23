/*
 * Copyright (C) 2010 The Android Open Source Project
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

package libcore.java.security;

import static org.junit.Assert.assertEquals;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.DomainCombiner;
import java.security.Permission;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import junit.framework.AssertionFailedError;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Android doesn't fully support access controller. This tests that actions are
 * passed through without permission enforcement.
 */
@RunWith(JUnit4.class)
public final class AccessControllerTest {

    @Test
    public void testDoPrivilegedWithCombiner() throws Exception {
        final DomainCombiner union = new DomainCombiner() {
            @Override
            public ProtectionDomain[] combine(ProtectionDomain[] a, ProtectionDomain[] b) {
                throw new AssertionFailedError("Expected combiner to be unused");
            }
        };

        ProtectionDomain protectionDomain = new ProtectionDomain(null, new Permissions());
        AccessControlContext accessControlContext = new AccessControlContext(
                new AccessControlContext(new ProtectionDomain[] { protectionDomain }), union);

        assertActionRun(action -> AccessController.doPrivileged(
                (PrivilegedAction<Void>) action::get));

        assertActionRun(action -> AccessController.doPrivileged(
                (PrivilegedAction<Void>) action::get, accessControlContext));

        assertActionRun(action -> {
            try {
                AccessController.doPrivileged((PrivilegedExceptionAction<Void>) action::get);
            } catch (PrivilegedActionException e) {
                throw new RuntimeException(e);
            }
        });

        assertActionRun(action -> {
            try {
                AccessController.doPrivileged(
                        (PrivilegedExceptionAction<Void>) action::get, accessControlContext);
            } catch (PrivilegedActionException e) {
                throw new RuntimeException(e);
            }
        });

        assertActionRun(action -> AccessController.doPrivilegedWithCombiner(
                (PrivilegedAction<Void>) action::get));

        assertActionRun(action -> {
            try {
                AccessController.doPrivilegedWithCombiner(
                        (PrivilegedExceptionAction<Void>) action::get);
            } catch (PrivilegedActionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void assertActionRun(Consumer<Supplier<Void>> runner) {
        final Permission permission = new RuntimePermission("do stuff");

        final AtomicInteger actionCount = new AtomicInteger();

        runner.accept(() -> {
            assertEquals(null, AccessController.getContext().getDomainCombiner());
            AccessController.getContext().checkPermission(permission);

            // Calling doPrivileged again would have exercised the combiner
            runner.accept(() -> {
                actionCount.incrementAndGet();
                assertEquals(null, AccessController.getContext().getDomainCombiner());
                AccessController.getContext().checkPermission(permission);
                return null;
            });

            return null;
        });

        assertEquals(1, actionCount.get());
    }
}
