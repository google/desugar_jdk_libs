/*
 * Copyright (C) 2011 The Android Open Source Project
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

package libcore.libcore.reflect;

import junit.framework.TestCase;

import libcore.reflect.InternalNames;

public final class InternalNamesTest extends TestCase {
    private final ClassLoader loader = InternalNames.class.getClassLoader();

    public void testGetClassNull() {
        try {
            InternalNames.getClass(loader, null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void testGetInternalNameNull() {
        try {
            InternalNames.getInternalName(null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void testInternalNameToPrimitive() {
        assertEquals(byte.class, InternalNames.getClass(loader, "B"));
        assertEquals(char.class, InternalNames.getClass(loader, "C"));
        assertEquals(double.class, InternalNames.getClass(loader, "D"));
        assertEquals(float.class, InternalNames.getClass(loader, "F"));
        assertEquals(int.class, InternalNames.getClass(loader, "I"));
        assertEquals(long.class, InternalNames.getClass(loader, "J"));
        assertEquals(short.class, InternalNames.getClass(loader, "S"));
        assertEquals(boolean.class, InternalNames.getClass(loader, "Z"));
        assertEquals(void.class, InternalNames.getClass(loader, "V"));
    }

    public void testPrimitiveToInternalName() {
        assertEquals("B", InternalNames.getInternalName(byte.class));
        assertEquals("C", InternalNames.getInternalName(char.class));
        assertEquals("D", InternalNames.getInternalName(double.class));
        assertEquals("F", InternalNames.getInternalName(float.class));
        assertEquals("I", InternalNames.getInternalName(int.class));
        assertEquals("J", InternalNames.getInternalName(long.class));
        assertEquals("S", InternalNames.getInternalName(short.class));
        assertEquals("Z", InternalNames.getInternalName(boolean.class));
        assertEquals("V", InternalNames.getInternalName(void.class));
    }

    public void testInternalNameToClass() {
        assertEquals(String.class, InternalNames.getClass(loader, "Ljava/lang/String;"));
    }

    public void testClassToInternalName() {
        assertEquals("Ljava/lang/String;", InternalNames.getInternalName(String.class));
    }

    public void testInternalNameToPrimitiveArray() {
        assertEquals(int[].class, InternalNames.getClass(loader, "[I"));
        assertEquals(int[][][][].class, InternalNames.getClass(loader, "[[[[I"));
    }

    public void testInternalNameToObjectArray() {
        assertEquals(String[].class, InternalNames.getClass(loader, "[Ljava/lang/String;"));
        assertEquals(String[][][][].class,
                InternalNames.getClass(loader, "[[[[Ljava/lang/String;"));
    }
}
