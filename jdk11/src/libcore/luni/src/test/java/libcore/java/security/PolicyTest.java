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
 * limitations under the License.
 */

package libcore.java.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.security.CodeSource;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.security.Provider;

/**
 * Test the stub implementation of {@link Policy}.
 */
@RunWith(JUnit4.class)
public class PolicyTest {

    private static class TestPolicy extends Policy {
    }

    private final Policy p = new TestPolicy();

    @Test
    public void testGetInstance_alwaysReturnNull() throws Exception {
        assertNull(Policy.getInstance(null, null));
        assertNull(Policy.getInstance(null, null, (String) null));
        assertNull(Policy.getInstance(null, null, (Provider) null));

        Policy.setPolicy(p);

        // Still return null after setPolicy()
        assertNull(Policy.getInstance(null, null));
        assertNull(Policy.getInstance(null, null, (String) null));
        assertNull(Policy.getInstance(null, null, (Provider) null));
    }

    @Test
    public void testGetParameters() {
        assertNull(p.getParameters());
    }

    @Test
    public void testGetPermissions() {
        assertNull(p.getPermissions((CodeSource) null));
        assertNull(p.getPermissions((ProtectionDomain) null));
    }

    @Test
    public void testGetProvider() {
        assertNull(p.getProvider());
    }

    @Test
    public void testGetType() {
        assertNull(p.getType());
    }

    @Test
    public void testImplies() {
        assertTrue(p.implies(null, null));
    }
}
