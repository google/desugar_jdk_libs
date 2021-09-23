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

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.security.AllPermission;
import java.security.BasicPermission;

@RunWith(JUnit4.class)
public class BasicPermissionTest {

    private static class TestPermission extends BasicPermission {
        public TestPermission(String name) {
            super(name);
        }
    }

    @Test
    public void testImplies() {
        BasicPermission permission = new TestPermission("name");
        AllPermission permission2 = new AllPermission();
        assertTrue(permission.implies(null));
        assertTrue(permission.implies(permission));
        assertTrue(permission.implies(permission2));
    }

    @Test
    public void testCheckGuard_doesntThrow() {
        BasicPermission permission = new TestPermission("name");
        permission.checkGuard(null);
        permission.checkGuard(new Object());
        permission.checkGuard(permission);
    }
}
