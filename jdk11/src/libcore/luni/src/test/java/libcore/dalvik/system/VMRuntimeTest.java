/*
 * Copyright (C) 2014 The Android Open Source Project
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

package libcore.dalvik.system;

import java.lang.reflect.Array;
import junit.framework.TestCase;

import dalvik.system.VMRuntime;

/**
 * Test VMRuntime behavior.
 */
public final class VMRuntimeTest extends TestCase {

    private void doTestNewNonMovableArray(Class<?> componentType, int step, int maxLength) {
        // Can't create negative sized arrays.
        try {
            Object array = VMRuntime.getRuntime().newNonMovableArray(componentType, -1);
            assertTrue(false);
        } catch (NegativeArraySizeException expected) {
        }

        try {
            Object array = VMRuntime.getRuntime().newNonMovableArray(componentType, Integer.MIN_VALUE);
            assertTrue(false);
        } catch (NegativeArraySizeException expected) {
        }

        // Allocate arrays in a loop and check their properties.
        for (int i = 0; i <= maxLength; i += step) {
            Object array = VMRuntime.getRuntime().newNonMovableArray(componentType, i);
            assertTrue(array.getClass().isArray());
            assertEquals(array.getClass().getComponentType(), componentType);
            assertEquals(Array.getLength(array), i);
        }
    }

    public void testNewNonMovableArray() {
        // Can't create arrays with no component type.
        try {
            Object array = VMRuntime.getRuntime().newNonMovableArray(null, 0);
            assertTrue(false);
        } catch (NullPointerException expected) {
        }

        // Can't create arrays of void.
        try {
            Object array = VMRuntime.getRuntime().newNonMovableArray(void.class, 0);
            assertTrue(false);
        } catch (NoClassDefFoundError expected) {
        }

        int maxLengthForLoop = 16 * 1024;
        int step = 67;
        doTestNewNonMovableArray(boolean.class, step, maxLengthForLoop);
        doTestNewNonMovableArray(byte.class, step, maxLengthForLoop);
        doTestNewNonMovableArray(char.class, step, maxLengthForLoop);
        doTestNewNonMovableArray(short.class, step, maxLengthForLoop);
        doTestNewNonMovableArray(int.class, step, maxLengthForLoop);
        doTestNewNonMovableArray(long.class, step, maxLengthForLoop);
        doTestNewNonMovableArray(float.class, step, maxLengthForLoop);
        doTestNewNonMovableArray(double.class, step, maxLengthForLoop);
        doTestNewNonMovableArray(Object.class, step, maxLengthForLoop);
        doTestNewNonMovableArray(Number.class, step, maxLengthForLoop);
        doTestNewNonMovableArray(String.class, step, maxLengthForLoop);
        doTestNewNonMovableArray(Runnable.class, step, maxLengthForLoop);
    }

    private void doTestNewUnpaddedArray(Class<?> componentType, int step, int maxLength) {
         // Can't create negative sized arrays.
        try {
            Object array = VMRuntime.getRuntime().newUnpaddedArray(componentType, -1);
            assertTrue(false);
        } catch (NegativeArraySizeException expected) {
        }

        try {
            Object array = VMRuntime.getRuntime().newUnpaddedArray(componentType, Integer.MIN_VALUE);
            assertTrue(false);
        } catch (NegativeArraySizeException expected) {
        }

        // Allocate arrays in a loop and check their properties.
        for (int i = 0; i <= maxLength; i += step) {
            Object array = VMRuntime.getRuntime().newUnpaddedArray(componentType, i);
            assertTrue(array.getClass().isArray());
            assertEquals(array.getClass().getComponentType(), componentType);
            assertTrue(Array.getLength(array) >= i);
        }
    }

    public void testNewUnpaddedArray() {
        // Can't create arrays with no component type.
        try {
            Object array = VMRuntime.getRuntime().newUnpaddedArray(null, 0);
            assertTrue(false);
        } catch (NullPointerException expected) {
        }

        // Can't create arrays of void.
        try {
            Object array = VMRuntime.getRuntime().newUnpaddedArray(void.class, 0);
            assertTrue(false);
        } catch (NoClassDefFoundError expected) {
        }

        int maxLengthForLoop = 16 * 1024;
        int step = 67;
        doTestNewUnpaddedArray(boolean.class, step, maxLengthForLoop);
        doTestNewUnpaddedArray(byte.class, step, maxLengthForLoop);
        doTestNewUnpaddedArray(char.class, step, maxLengthForLoop);
        doTestNewUnpaddedArray(short.class, step, maxLengthForLoop);
        doTestNewUnpaddedArray(int.class, step, maxLengthForLoop);
        doTestNewUnpaddedArray(long.class, step, maxLengthForLoop);
        doTestNewUnpaddedArray(float.class, step, maxLengthForLoop);
        doTestNewUnpaddedArray(double.class, step, maxLengthForLoop);
        doTestNewUnpaddedArray(Object.class, step, maxLengthForLoop);
        doTestNewUnpaddedArray(Number.class, step, maxLengthForLoop);
        doTestNewUnpaddedArray(String.class, step, maxLengthForLoop);
        doTestNewUnpaddedArray(Runnable.class, step, maxLengthForLoop);
    }
}

