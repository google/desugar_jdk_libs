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

package libcore.dalvik.system;

import junit.framework.TestCase;

import java.lang.invoke.MethodType;

import dalvik.system.EmulatedStackFrame;

public class EmulatedStackFrameTest extends TestCase {

    public void testReaderWriter_allParamTypes() {
        EmulatedStackFrame stackFrame = EmulatedStackFrame.create(MethodType.methodType(
                void.class,
                new Class<?>[] { boolean.class, char.class, short.class, int.class, long.class,
                        float.class, double.class, String.class }));

        EmulatedStackFrame.StackFrameWriter writer = new EmulatedStackFrame.StackFrameWriter();
        writer.attach(stackFrame);

        writer.putNextBoolean(true);
        writer.putNextChar('a');
        writer.putNextShort((short) 42);
        writer.putNextInt(43);
        writer.putNextLong(56);
        writer.putNextFloat(42.0f);
        writer.putNextDouble(52.0);
        writer.putNextReference("foo", String.class);

        EmulatedStackFrame.StackFrameReader reader = new EmulatedStackFrame.StackFrameReader();
        reader.attach(stackFrame);

        assertTrue(reader.nextBoolean());
        assertEquals('a', reader.nextChar());
        assertEquals((short) 42, reader.nextShort());
        assertEquals(43, reader.nextInt());
        assertEquals(56, reader.nextLong());
        assertEquals(42.0f, reader.nextFloat());
        assertEquals(52.0, reader.nextDouble());
        assertEquals("foo", reader.nextReference(String.class));
    }

    public void testReaderWriter_allReturnTypes() {
        EmulatedStackFrame stackFrame = EmulatedStackFrame.create(
                MethodType.methodType(boolean.class));

        EmulatedStackFrame.StackFrameWriter writer = new EmulatedStackFrame.StackFrameWriter();
        writer.attach(stackFrame).makeReturnValueAccessor();

        EmulatedStackFrame.StackFrameReader reader = new EmulatedStackFrame.StackFrameReader();
        reader.attach(stackFrame).makeReturnValueAccessor();

        writer.putNextBoolean(true);
        assertTrue(reader.nextBoolean());

        stackFrame = EmulatedStackFrame.create(MethodType.methodType(char.class));
        writer.attach(stackFrame).makeReturnValueAccessor();
        reader.attach(stackFrame).makeReturnValueAccessor();

        writer.putNextChar('a');
        assertEquals('a', reader.nextChar());

        stackFrame = EmulatedStackFrame.create(MethodType.methodType(short.class));
        writer.attach(stackFrame).makeReturnValueAccessor();
        reader.attach(stackFrame).makeReturnValueAccessor();

        writer.putNextShort((short) 52);
        assertEquals((short) 52, reader.nextShort());

        stackFrame = EmulatedStackFrame.create(MethodType.methodType(int.class));
        writer.attach(stackFrame).makeReturnValueAccessor();
        reader.attach(stackFrame).makeReturnValueAccessor();
        writer.putNextInt(64);
        assertEquals(64, reader.nextInt());

        stackFrame = EmulatedStackFrame.create(MethodType.methodType(long.class));
        writer.attach(stackFrame).makeReturnValueAccessor();
        reader.attach(stackFrame).makeReturnValueAccessor();
        writer.putNextLong(72);
        assertEquals(72, reader.nextLong());

        stackFrame = EmulatedStackFrame.create(MethodType.methodType(float.class));
        writer.attach(stackFrame).makeReturnValueAccessor();
        reader.attach(stackFrame).makeReturnValueAccessor();
        writer.putNextFloat(52.0f);
        assertEquals(52.0f, reader.nextFloat());

        stackFrame = EmulatedStackFrame.create(MethodType.methodType(double.class));
        writer.attach(stackFrame).makeReturnValueAccessor();
        reader.attach(stackFrame).makeReturnValueAccessor();
        writer.putNextDouble(73.0);
        assertEquals(73.0, reader.nextDouble());

        stackFrame = EmulatedStackFrame.create(MethodType.methodType(String.class));
        writer.attach(stackFrame).makeReturnValueAccessor();
        reader.attach(stackFrame).makeReturnValueAccessor();
        writer.putNextReference("foo", String.class);
        assertEquals("foo", reader.nextReference(String.class));
    }

    public void testReaderWriter_assignableTypes() {
        EmulatedStackFrame stackFrame = EmulatedStackFrame.create(
            MethodType.methodType(Object.class, Object.class));

        EmulatedStackFrame.StackFrameWriter writer = new EmulatedStackFrame.StackFrameWriter();
        writer.attach(stackFrame);
        writer.putNextReference(Boolean.TRUE, Object.class);
        writer.makeReturnValueAccessor();
        writer.putNextReference(Boolean.FALSE, Object.class);

        EmulatedStackFrame.StackFrameReader reader = new EmulatedStackFrame.StackFrameReader();
        reader.attach(stackFrame);
        assertEquals(Boolean.TRUE, reader.nextReference(Object.class));
        reader.makeReturnValueAccessor();
        assertEquals(Boolean.FALSE, reader.nextReference(Object.class));
    }

    public void testReaderWriter_wrongTypes() {
        EmulatedStackFrame stackFrame = EmulatedStackFrame.create(
                MethodType.methodType(boolean.class, String.class));

        EmulatedStackFrame.StackFrameReader reader = new EmulatedStackFrame.StackFrameReader();
        reader.attach(stackFrame);

        try {
            reader.nextInt();
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            reader.nextDouble();
            fail();
        } catch (IllegalArgumentException expected) {
        }

        assertNull(reader.nextReference(String.class));

        try {
            reader.nextDouble();
            fail();
        } catch (IllegalArgumentException expected) {
        }

        EmulatedStackFrame.StackFrameWriter writer = new EmulatedStackFrame.StackFrameWriter();
        writer.attach(stackFrame);

        try {
            writer.putNextInt(0);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            writer.putNextDouble(0);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        writer.putNextReference(null, String.class);

        try {
            writer.putNextDouble(0);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testReturnValueReaderWriter_wrongTypes() {
        EmulatedStackFrame stackFrame = EmulatedStackFrame.create(
                MethodType.methodType(boolean.class, String.class));

        EmulatedStackFrame.StackFrameReader reader = new EmulatedStackFrame.StackFrameReader();
        reader.attach(stackFrame);
        reader.makeReturnValueAccessor();

        try {
            reader.nextInt();
            fail();
        } catch (IllegalArgumentException expected) {
        }

        // Should succeeed.
        assertFalse(reader.nextBoolean());

        // The next attempt should fail.
        try {
            reader.nextBoolean();
            fail();
        } catch (IllegalArgumentException expected) {
        }

        EmulatedStackFrame.StackFrameWriter writer = new EmulatedStackFrame.StackFrameWriter();
        writer.attach(stackFrame);
        writer.makeReturnValueAccessor();

        try {
            writer.putNextInt(0);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        // Should succeeed.
        writer.putNextBoolean(true);

        // The next attempt should fail.
        try {
            writer.putNextBoolean(false);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }
}
