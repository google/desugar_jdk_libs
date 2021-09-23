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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.security.UnresolvedPermission;
import java.security.cert.Certificate;

@RunWith(JUnit4.class)
public class UnresolvedPermissionTest {

    private final UnresolvedPermission p = new UnresolvedPermission("type", "name", "action",
            new Certificate[0]);

    @Test
    public void testGetName() {
        assertEquals("", p.getName());
    }

    @Test
    public void testGetActions() {
        assertNull(p.getActions());
    }

    @Test
    public void testUnresolvedType() {
        assertNull(p.getUnresolvedType());
    }

    @Test
    public void testGetUnresolvedCertssss() {
        assertNull(p.getUnresolvedCerts());
    }

    @Test
    public void testGetUnresolvedName() {
        assertNull(p.getUnresolvedName());
    }

    @Test
    public void testGetUnresolvedActions() {
        assertNull(p.getUnresolvedActions());
    }

    @Test
    public void testImplies() {
        assertFalse(p.implies(null));
        assertFalse(p.implies(p));
    }
}
