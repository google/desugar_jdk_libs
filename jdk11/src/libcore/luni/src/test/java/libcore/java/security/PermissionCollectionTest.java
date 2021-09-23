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

import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;

@RunWith(JUnit4.class)
public class PermissionCollectionTest {

    private static class TestPermissionCollection extends PermissionCollection {

        @Override
        public void add(Permission permission) {
        }

        @Override
        public boolean implies(Permission permission) {
            return true;
        }

        @Override
        public Enumeration<Permission> elements() {
            return null;
        }
    }

    @Test
    public void testSetReadOnly() {
        PermissionCollection permissionCollection = new TestPermissionCollection();
        // isReadOnly() always return true
        assertTrue(permissionCollection.isReadOnly());
        permissionCollection.setReadOnly();
        assertTrue(permissionCollection.isReadOnly());
    }
}
