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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.PolicySpi;
import java.security.ProtectionDomain;

@RunWith(JUnit4.class)
public class PolicySpiTest {

    private static class TestPolicySpi extends PolicySpi {

        @Override
        protected boolean engineImplies(ProtectionDomain domain, Permission permission) {
            return false;
        }

        @Override
        public void engineRefresh() {
            super.engineRefresh();
        }

        @Override
        public PermissionCollection engineGetPermissions(CodeSource codesource) {
            return super.engineGetPermissions(codesource);
        }

        @Override
        public PermissionCollection engineGetPermissions(ProtectionDomain domain) {
            return super.engineGetPermissions(domain);
        }
    }

    private static final TestPolicySpi spi = new TestPolicySpi();

    @Test
    public void testEngineRefresh_doesntThrow() {
        spi.engineRefresh();
    }

    @Test
    public void testEngineGetPermissions() {
        assertEquals(Policy.UNSUPPORTED_EMPTY_COLLECTION,
                spi.engineGetPermissions((CodeSource) null));
        assertEquals(Policy.UNSUPPORTED_EMPTY_COLLECTION,
                spi.engineGetPermissions((ProtectionDomain) null));
    }
}
