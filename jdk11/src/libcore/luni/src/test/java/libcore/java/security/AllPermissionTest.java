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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.security.AllPermission;

@RunWith(JUnit4.class)
public class AllPermissionTest {

    @Test
    public void testGetAction() {
        String action = "test action";
        AllPermission permission = new AllPermission("Test permission", action);
        // Action value is discarded and returns null here.
        assertNull(permission.getActions());
    }

    @Test
    public void testImplies() {
        AllPermission permission = new AllPermission();
        AllPermission permission2 = new AllPermission();
        assertTrue(permission.implies(null));
        assertTrue(permission.implies(permission));
        assertTrue(permission.implies(permission2));
    }
}
