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
 * limitations under the License
 */

package libcore.sun.invoke.util;


import junit.framework.TestCase;
import sun.invoke.util.VerifyAccess;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Locale;
import java.util.Vector;


public class VerifyAccessTest extends TestCase {
    public void testIsClassAccessible() {
        // Always returns false when allowedModes == 0. Note that the "modes" allowed here
        // are different from the ones used in MethodHandles.
        assertFalse(VerifyAccess.isClassAccessible(Inner1.class, Inner2.class, 0));

        // Classes in the same package are accessible when Lookup.PACKAGE is specified.
        assertTrue(VerifyAccess.isClassAccessible(Inner1.class, Inner2.class,
                MethodHandles.Lookup.PACKAGE));
        assertTrue(VerifyAccess.isClassAccessible(Inner1.class, Sibling.class,
                MethodHandles.Lookup.PACKAGE));

        // Public classes are always accessible.
        assertTrue(VerifyAccess.isClassAccessible(String.class, Inner1.class,
                MethodHandles.Lookup.PACKAGE));
    }

    public static class Inner1 {
    }

    public static class Inner2 {
    }

    public void testIsSamePackageMember() {
        assertTrue(VerifyAccess.isSamePackageMember(Inner1.class, Inner2.class));
        assertTrue(VerifyAccess.isSamePackageMember(Inner1.class, VerifyAccessTest.class));

        assertFalse(VerifyAccess.isSamePackageMember(Sibling.class, Inner1.class));
    }

    public void testIsSamePackage() {
        // Both classes are in package java.util.
        assertTrue(VerifyAccess.isSamePackage(Vector.class, List.class));
        // Make sure this works for inner classes.
        assertTrue(VerifyAccess.isSamePackage(Vector.class, Locale.Builder.class));
        // Differing packages: java.lang vs java.util.
        assertFalse(VerifyAccess.isSamePackage(Vector.class, String.class));

        try {
            VerifyAccess.isSamePackage(String[].class, List.class);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }
}

class Sibling {
}
