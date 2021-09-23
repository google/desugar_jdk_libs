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
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.security.AccessControlException;
import java.security.AllPermission;
import java.security.Permission;

@RunWith(JUnit4.class)
public class AccessControlExceptionTest {

    @Test
    public void testConstructor() {
        String msg = "test message";
        Permission permission = new AllPermission();

        try {
            throw new AccessControlException(msg);
        } catch (AccessControlException e) {
            assertEquals(msg, e.getMessage());
        }

        try {
            throw new AccessControlException(msg, permission);
        } catch (AccessControlException e) {
            assertEquals(msg, e.getMessage());
            assertSame(permission, e.getPermission());
        }
    }
}
