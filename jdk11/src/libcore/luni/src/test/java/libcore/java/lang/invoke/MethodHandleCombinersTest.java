/*
 * Copyright (C) 2017 The Android Open Source Project
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

package libcore.java.lang.invoke;

import java.lang.Thread;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

public class MethodHandleCombinersTest extends TestCase {

    static final int TEST_THREAD_ITERATIONS = 1000;

    public static void testThrowException() throws Throwable {
        MethodHandle handle = MethodHandles.throwException(String.class,
                IllegalArgumentException.class);

        if (handle.type().returnType() != String.class) {
            fail("Unexpected return type for handle: " + handle +
                    " [ " + handle.type() + "]");
        }

        final IllegalArgumentException iae = new IllegalArgumentException("boo!");
        try {
            handle.invoke(iae);
            fail("Expected an exception of type: java.lang.IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            if (expected != iae) {
                fail("Wrong exception: expected " + iae + " but was " + expected);
            }
        }
    }

    public static void dropArguments_delegate(String message, long message2) {
        assertEquals("foo", message);
        assertEquals(42l, message2);
    }

    public static void testDropArguments() throws Throwable {
        MethodHandle delegate = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                "dropArguments_delegate",
                MethodType.methodType(void.class, new Class<?>[]{String.class, long.class}));

        MethodHandle transform = MethodHandles.dropArguments(
                delegate, 0, int.class, Object.class);

        // The transformer will accept two additional arguments at position zero.
        try {
            transform.invokeExact("foo", 42l);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        transform.invokeExact(45, new Object(), "foo", 42l);
        transform.invoke(45, new Object(), "foo", 42l);

        // Additional arguments at position 1.
        transform = MethodHandles.dropArguments(delegate, 1, int.class, Object.class);
        transform.invokeExact("foo", 45, new Object(), 42l);
        transform.invoke("foo", 45, new Object(), 42l);

        // Additional arguments at position 2.
        transform = MethodHandles.dropArguments(delegate, 2, int.class, Object.class);
        transform.invokeExact("foo", 42l, 45, new Object());
        transform.invoke("foo", 42l, 45, new Object());

        // Note that we still perform argument conversions even for the arguments that
        // are subsequently dropped.
        try {
            transform.invoke("foo", 42l, 45l, new Object());
            fail();
        } catch (WrongMethodTypeException expected) {
        } catch (IllegalArgumentException expected) {
            // TODO(narayan): We currently throw the wrong type of exception here,
            // it's IAE and should be WMTE instead.
        }

        // Check that asType works as expected.
        transform = MethodHandles.dropArguments(delegate, 0, int.class, Object.class);
        transform = transform.asType(MethodType.methodType(void.class,
                new Class<?>[]{short.class, Object.class, String.class, long.class}));
        transform.invokeExact((short) 45, new Object(), "foo", 42l);

        // Invalid argument location, should not be allowed.
        try {
            MethodHandles.dropArguments(delegate, -1, int.class, Object.class);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        // Invalid argument location, should not be allowed.
        try {
            MethodHandles.dropArguments(delegate, 3, int.class, Object.class);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            MethodHandles.dropArguments(delegate, 1, void.class);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public static void testDropArguments_List() throws Throwable {
        MethodHandle delegate = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                "dropArguments_delegate",
                MethodType.methodType(void.class, new Class<?>[]{String.class, long.class}));

        MethodHandle transform = MethodHandles.dropArguments(
                delegate, 0, Arrays.asList(int.class, Object.class));

        transform.invokeExact(45, new Object(), "foo", 42l);
        transform.invoke(45, new Object(), "foo", 42l);

        // Check that asType works as expected.
        transform = transform.asType(MethodType.methodType(void.class,
                new Class<?>[]{short.class, Object.class, String.class, long.class}));
        transform.invokeExact((short) 45, new Object(), "foo", 42l);
    }

    public static String testCatchException_target(String arg1, long arg2, String exceptionMessage)
            throws Throwable {
        if (exceptionMessage != null) {
            throw new IllegalArgumentException(exceptionMessage);
        }

        assertEquals(null, exceptionMessage);
        assertEquals(42l, arg2);
        return "target";
    }

    public static String testCatchException_handler(IllegalArgumentException iae, String arg1,
                                                    long arg2,
                                                    String exMsg) {
        // Check that the thrown exception has the right message.
        assertEquals("exceptionMessage", iae.getMessage());
        // Check the other arguments.
        assertEquals("foo", arg1);
        assertEquals(42, arg2);
        assertEquals("exceptionMessage", exMsg);

        return "handler1";
    }

    public static String testCatchException_handler2(IllegalArgumentException iae, String arg1) {
        assertEquals("exceptionMessage", iae.getMessage());
        assertEquals("foo", arg1);

        return "handler2";
    }

    public static void testCatchException() throws Throwable {
        MethodHandle target = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                "testCatchException_target",
                MethodType
                        .methodType(String.class, new Class<?>[]{String.class, long.class, String.class}));

        MethodHandle handler = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                "testCatchException_handler",
                MethodType.methodType(String.class, new Class<?>[]{IllegalArgumentException.class,
                        String.class, long.class, String.class}));

        MethodHandle adapter = MethodHandles.catchException(target, IllegalArgumentException.class,
                handler);

        String returnVal = null;

        // These two should end up calling the target always. We're passing a null exception
        // message here, which means the target will not throw.
        returnVal = (String) adapter.invoke("foo", 42, null);
        assertEquals("target", returnVal);
        returnVal = (String) adapter.invokeExact("foo", 42l, (String) null);
        assertEquals("target", returnVal);

        // We're passing a non-null exception message here, which means the target will throw,
        // which in turn means that the handler must be called for the next two invokes.
        returnVal = (String) adapter.invoke("foo", 42, "exceptionMessage");
        assertEquals("handler1", returnVal);
        returnVal = (String) adapter.invokeExact("foo", 42l, "exceptionMessage");
        assertEquals("handler1", returnVal);

        handler = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                "testCatchException_handler2",
                MethodType.methodType(String.class, new Class<?>[]{IllegalArgumentException.class,
                        String.class}));
        adapter = MethodHandles.catchException(target, IllegalArgumentException.class, handler);

        returnVal = (String) adapter.invoke("foo", 42, "exceptionMessage");
        assertEquals("handler2", returnVal);
        returnVal = (String) adapter.invokeExact("foo", 42l, "exceptionMessage");
        assertEquals("handler2", returnVal);

        // Test that the type of the invoke doesn't matter. Here we call
        // IllegalArgumentException.toString() on the exception that was thrown by
        // the target.
        handler = MethodHandles.lookup().findVirtual(IllegalArgumentException.class,
                "toString", MethodType.methodType(String.class));
        adapter = MethodHandles.catchException(target, IllegalArgumentException.class, handler);

        returnVal = (String) adapter.invoke("foo", 42, "exceptionMessage");
        assertEquals("java.lang.IllegalArgumentException: exceptionMessage", returnVal);
        returnVal = (String) adapter.invokeExact("foo", 42l, "exceptionMessage");
        assertEquals("java.lang.IllegalArgumentException: exceptionMessage", returnVal);

        // Check that asType works as expected.
        adapter = MethodHandles.catchException(target, IllegalArgumentException.class,
                handler);
        adapter = adapter.asType(MethodType.methodType(String.class,
                new Class<?>[]{String.class, int.class, String.class}));
        returnVal = (String) adapter.invokeExact("foo", 42, "exceptionMessage");
        assertEquals("java.lang.IllegalArgumentException: exceptionMessage", returnVal);
    }

    public static boolean testGuardWithTest_test(String arg1, long arg2) {
        return "target".equals(arg1) && 42 == arg2;
    }

    public static String testGuardWithTest_target(String arg1, long arg2, int arg3) {
        // Make sure that the test passed.
        assertTrue(testGuardWithTest_test(arg1, arg2));
        // Make sure remaining arguments were passed through unmodified.
        assertEquals(56, arg3);

        return "target";
    }

    public static String testGuardWithTest_fallback(String arg1, long arg2, int arg3) {
        // Make sure that the test failed.
        assertTrue(!testGuardWithTest_test(arg1, arg2));
        // Make sure remaining arguments were passed through unmodified.
        assertEquals(56, arg3);

        return "fallback";
    }

    public static void testGuardWithTest() throws Throwable {
        MethodHandle test = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                "testGuardWithTest_test",
                MethodType.methodType(boolean.class, new Class<?>[]{String.class, long.class}));

        final MethodType type = MethodType.methodType(String.class,
                new Class<?>[]{String.class, long.class, int.class});

        final MethodHandle target = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                "testGuardWithTest_target", type);
        final MethodHandle fallback = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                "testGuardWithTest_fallback", type);

        MethodHandle adapter = MethodHandles.guardWithTest(test, target, fallback);

        String returnVal = null;

        returnVal = (String) adapter.invoke("target", 42, 56);
        assertEquals("target", returnVal);
        returnVal = (String) adapter.invokeExact("target", 42l, 56);
        assertEquals("target", returnVal);

        returnVal = (String) adapter.invoke("fallback", 42l, 56);
        assertEquals("fallback", returnVal);
        returnVal = (String) adapter.invoke("target", 46l, 56);
        assertEquals("fallback", returnVal);
        returnVal = (String) adapter.invokeExact("target", 42l, 56);
        assertEquals("target", returnVal);

        // Check that asType works as expected.
        adapter = adapter.asType(MethodType.methodType(String.class,
                new Class<?>[]{String.class, int.class, int.class}));
        returnVal = (String) adapter.invokeExact("target", 42, 56);
        assertEquals("target", returnVal);
    }

    public static void testArrayElementGetter() throws Throwable {
        MethodHandle getter = MethodHandles.arrayElementGetter(int[].class);

        {
            int[] array = new int[1];
            array[0] = 42;
            int value = (int) getter.invoke(array, 0);
            assertEquals(42, value);

            try {
                value = (int) getter.invoke(array, -1);
                fail();
            } catch (ArrayIndexOutOfBoundsException expected) {
            }

            try {
                value = (int) getter.invoke(null, -1);
                fail();
            } catch (NullPointerException expected) {
            }
        }

        {
            getter = MethodHandles.arrayElementGetter(long[].class);
            long[] array = new long[1];
            array[0] = 42;
            long value = (long) getter.invoke(array, 0);
            assertEquals(42l, value);
        }

        {
            getter = MethodHandles.arrayElementGetter(short[].class);
            short[] array = new short[1];
            array[0] = 42;
            short value = (short) getter.invoke(array, 0);
            assertEquals((short) 42, value);
        }

        {
            getter = MethodHandles.arrayElementGetter(char[].class);
            char[] array = new char[1];
            array[0] = 42;
            char value = (char) getter.invoke(array, 0);
            assertEquals((char) 42, value);
        }

        {
            getter = MethodHandles.arrayElementGetter(byte[].class);
            byte[] array = new byte[1];
            array[0] = (byte) 0x8;
            byte value = (byte) getter.invoke(array, 0);
            assertEquals((byte) 0x8, value);
        }

        {
            getter = MethodHandles.arrayElementGetter(boolean[].class);
            boolean[] array = new boolean[1];
            array[0] = true;
            boolean value = (boolean) getter.invoke(array, 0);
            assertTrue(value);
        }

        {
            getter = MethodHandles.arrayElementGetter(float[].class);
            float[] array = new float[1];
            array[0] = 42.0f;
            float value = (float) getter.invoke(array, 0);
            assertEquals(42.0f, value);
        }

        {
            getter = MethodHandles.arrayElementGetter(double[].class);
            double[] array = new double[1];
            array[0] = 42.0;
            double value = (double) getter.invoke(array, 0);
            assertEquals(42.0, value);
        }

        {
            getter = MethodHandles.arrayElementGetter(String[].class);
            String[] array = new String[3];
            array[0] = "42";
            array[1] = "48";
            array[2] = "54";
            String value = (String) getter.invoke(array, 0);
            assertEquals("42", value);
            value = (String) getter.invoke(array, 1);
            assertEquals("48", value);
            value = (String) getter.invoke(array, 2);
            assertEquals("54", value);
        }
    }

    public static void testArrayElementSetter() throws Throwable {
        MethodHandle setter = MethodHandles.arrayElementSetter(int[].class);

        {
            int[] array = new int[2];
            setter.invoke(array, 0, 42);
            setter.invoke(array, 1, 43);

            assertEquals(42, array[0]);
            assertEquals(43, array[1]);

            try {
                setter.invoke(array, -1, 42);
                fail();
            } catch (ArrayIndexOutOfBoundsException expected) {
            }

            try {
                setter.invoke(null, 0, 42);
                fail();
            } catch (NullPointerException expected) {
            }
        }

        {
            setter = MethodHandles.arrayElementSetter(long[].class);
            long[] array = new long[1];
            setter.invoke(array, 0, 42l);
            assertEquals(42l, array[0]);
        }

        {
            setter = MethodHandles.arrayElementSetter(short[].class);
            short[] array = new short[1];
            setter.invoke(array, 0, (short) 42);
            assertEquals((short) 42, array[0]);
        }

        {
            setter = MethodHandles.arrayElementSetter(char[].class);
            char[] array = new char[1];
            setter.invoke(array, 0, (char) 42);
            assertEquals((char) 42, array[0]);
        }

        {
            setter = MethodHandles.arrayElementSetter(byte[].class);
            byte[] array = new byte[1];
            setter.invoke(array, 0, (byte) 0x8);
            assertEquals((byte) 0x8, array[0]);
        }

        {
            setter = MethodHandles.arrayElementSetter(boolean[].class);
            boolean[] array = new boolean[1];
            setter.invoke(array, 0, true);
            assertTrue(array[0]);
        }

        {
            setter = MethodHandles.arrayElementSetter(float[].class);
            float[] array = new float[1];
            setter.invoke(array, 0, 42.0f);
            assertEquals(42.0f, array[0]);
        }

        {
            setter = MethodHandles.arrayElementSetter(double[].class);
            double[] array = new double[1];
            setter.invoke(array, 0, 42.0);
            assertEquals(42.0, array[0]);
        }

        {
            setter = MethodHandles.arrayElementSetter(String[].class);
            String[] array = new String[3];
            setter.invoke(array, 0, "42");
            setter.invoke(array, 1, "48");
            setter.invoke(array, 2, "54");
            assertEquals("42", array[0]);
            assertEquals("48", array[1]);
            assertEquals("54", array[2]);
        }
    }

    public static void testIdentity() throws Throwable {
        {
            MethodHandle identity = MethodHandles.identity(boolean.class);
            boolean value = (boolean) identity.invoke(false);
            assertFalse(value);
        }

        {
            MethodHandle identity = MethodHandles.identity(byte.class);
            byte value = (byte) identity.invoke((byte) 0x8);
            assertEquals((byte) 0x8, value);
        }

        {
            MethodHandle identity = MethodHandles.identity(char.class);
            char value = (char) identity.invoke((char) -56);
            assertEquals((char) -56, value);
        }

        {
            MethodHandle identity = MethodHandles.identity(short.class);
            short value = (short) identity.invoke((short) -59);
            assertEquals((short) -59, value);
        }

        {
            MethodHandle identity = MethodHandles.identity(int.class);
            int value = (int) identity.invoke(52);
            assertEquals((int) 52, value);
        }

        {
            MethodHandle identity = MethodHandles.identity(long.class);
            long value = (long) identity.invoke(-76l);
            assertEquals(-76l, value);
        }

        {
            MethodHandle identity = MethodHandles.identity(float.class);
            float value = (float) identity.invoke(56.0f);
            assertEquals(56.0f, value);
        }

        {
            MethodHandle identity = MethodHandles.identity(double.class);
            double value = (double) identity.invoke((double) 72.0);
            assertEquals(72.0, value);
        }

        {
            MethodHandle identity = MethodHandles.identity(String.class);
            String value = (String) identity.invoke("bazman");
            assertEquals("bazman", value);
        }
    }

    public static void testConstant() throws Throwable {
        // int constants.
        {
            MethodHandle constant = MethodHandles.constant(int.class, 56);
            assertEquals(56, (int) constant.invoke());

            // short constant values are converted to int.
            constant = MethodHandles.constant(int.class, (short) 52);
            assertEquals(52, (int) constant.invoke());

            // char constant values are converted to int.
            constant = MethodHandles.constant(int.class, (char) 'b');
            assertEquals('b', (int) constant.invoke());

            // int constant values are converted to int.
            constant = MethodHandles.constant(int.class, (byte) 0x1);
            assertEquals(0x1, (int) constant.invoke());

            // boolean, float, double and long primitive constants are not convertible
            // to int, so the handle creation must fail with a CCE.
            try {
                MethodHandles.constant(int.class, false);
                fail();
            } catch (ClassCastException expected) {
            }

            try {
                MethodHandles.constant(int.class, 0.1f);
                fail();
            } catch (ClassCastException expected) {
            }

            try {
                MethodHandles.constant(int.class, 0.2);
                fail();
            } catch (ClassCastException expected) {
            }

            try {
                MethodHandles.constant(int.class, 73l);
                fail();
            } catch (ClassCastException expected) {
            }
        }

        // long constants.
        {
            MethodHandle constant = MethodHandles.constant(long.class, 56l);
            assertEquals(56l, (long) constant.invoke());

            constant = MethodHandles.constant(long.class, (int) 56);
            assertEquals(56l, (long) constant.invoke());
        }

        // byte constants.
        {
            MethodHandle constant = MethodHandles.constant(byte.class, (byte) 0x12);
            assertEquals((byte) 0x12, (byte) constant.invoke());
        }

        // boolean constants.
        {
            MethodHandle constant = MethodHandles.constant(boolean.class, true);
            assertTrue((boolean) constant.invoke());
        }

        // char constants.
        {
            MethodHandle constant = MethodHandles.constant(char.class, 'f');
            assertEquals('f', (char) constant.invoke());
        }

        // short constants.
        {
            MethodHandle constant = MethodHandles.constant(short.class, (short) 123);
            assertEquals((short) 123, (short) constant.invoke());
        }

        // float constants.
        {
            MethodHandle constant = MethodHandles.constant(float.class, 56.0f);
            assertEquals(56.0f, (float) constant.invoke());
        }

        // double constants.
        {
            MethodHandle constant = MethodHandles.constant(double.class, 256.0);
            assertEquals(256.0, (double) constant.invoke());
        }

        // reference constants.
        {
            MethodHandle constant = MethodHandles.constant(String.class, "256.0");
            assertEquals("256.0", (String) constant.invoke());
        }
    }

    public static void testBindTo() throws Throwable {
        MethodHandle stringCharAt = MethodHandles.lookup().findVirtual(
                String.class, "charAt", MethodType.methodType(char.class, int.class));

        char value = (char) stringCharAt.invoke("foo", 0);
        if (value != 'f') {
            fail("Unexpected value: " + value);
        }

        MethodHandle bound = stringCharAt.bindTo("foo");
        value = (char) bound.invoke(0);
        if (value != 'f') {
            fail("Unexpected value: " + value);
        }

        try {
            stringCharAt.bindTo(new Object());
            fail();
        } catch (ClassCastException expected) {
        }

        bound = stringCharAt.bindTo(null);
        try {
            bound.invoke(0);
            fail();
        } catch (NullPointerException expected) {
        }

        MethodHandle integerParseInt = MethodHandles.lookup().findStatic(
                Integer.class, "parseInt", MethodType.methodType(int.class, String.class));

        bound = integerParseInt.bindTo("78452");
        int intValue = (int) bound.invoke();
        if (intValue != 78452) {
            fail("Unexpected value: " + intValue);
        }
    }

    public static String filterReturnValue_target(int a) {
        return "ReturnValue" + a;
    }

    public static boolean filterReturnValue_filter(String value) {
        return value.indexOf("42") != -1;
    }

    public static int filterReturnValue_intTarget(String a) {
        return Integer.parseInt(a);
    }

    public static int filterReturnValue_intFilter(int b) {
        return b + 1;
    }

    public static void filterReturnValue_voidTarget() {
    }

    public static int filterReturnValue_voidFilter() {
        return 42;
    }

    public static void testFilterReturnValue() throws Throwable {
        // A target that returns a reference.
        {
            final MethodHandle target = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                    "filterReturnValue_target", MethodType.methodType(String.class, int.class));
            final MethodHandle filter = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                    "filterReturnValue_filter", MethodType.methodType(boolean.class, String.class));

            MethodHandle adapter = MethodHandles.filterReturnValue(target, filter);

            boolean value = (boolean) adapter.invoke((int) 42);
            if (!value) {
                fail("Unexpected value: " + value);
            }
            value = (boolean) adapter.invoke((int) 43);
            if (value) {
                fail("Unexpected value: " + value);
            }
        }

        // A target that returns a primitive.
        {
            final MethodHandle target = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                    "filterReturnValue_intTarget", MethodType.methodType(int.class, String.class));
            final MethodHandle filter = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                    "filterReturnValue_intFilter", MethodType.methodType(int.class, int.class));

            MethodHandle adapter = MethodHandles.filterReturnValue(target, filter);

            int value = (int) adapter.invoke("56");
            if (value != 57) {
                fail("Unexpected value: " + value);
            }
        }

        // A target that returns void.
        {
            final MethodHandle target = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                    "filterReturnValue_voidTarget", MethodType.methodType(void.class));
            final MethodHandle filter = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                    "filterReturnValue_voidFilter", MethodType.methodType(int.class));

            MethodHandle adapter = MethodHandles.filterReturnValue(target, filter);

            int value = (int) adapter.invoke();
            if (value != 42) {
                fail("Unexpected value: " + value);
            }
        }
    }

    public static void permuteArguments_callee(boolean a, byte b, char c,
                                               short d, int e, long f, float g, double h) {
        assertTrue(a);
        assertEquals((byte) 'b', b);
        assertEquals('c', c);
        assertEquals((short) 56, d);
        assertEquals(78, e);
        assertEquals(97l, f);
        assertEquals(98.0f, g);
        assertEquals(97.0, h);
    }

    public static void permuteArguments_boxingCallee(boolean a, Integer b) {
        assertTrue(a);
        assertEquals(Integer.valueOf(42), b);
    }

    public static void testPermuteArguments() throws Throwable {
        {
            final MethodHandle target = MethodHandles.lookup().findStatic(
                    MethodHandleCombinersTest.class, "permuteArguments_callee",
                    MethodType.methodType(void.class, new Class<?>[]{
                            boolean.class, byte.class, char.class, short.class, int.class,
                            long.class, float.class, double.class}));

            final MethodType newType = MethodType.methodType(void.class, new Class<?>[]{
                    double.class, float.class, long.class, int.class, short.class, char.class,
                    byte.class, boolean.class});

            final MethodHandle permutation = MethodHandles.permuteArguments(
                    target, newType, new int[]{7, 6, 5, 4, 3, 2, 1, 0});

            permutation.invoke((double) 97.0, (float) 98.0f, (long) 97, 78,
                    (short) 56, 'c', (byte) 'b', (boolean) true);

            // The permutation array was not of the right length.
            try {
                MethodHandles.permuteArguments(target, newType,
                        new int[]{7});
                fail();
            } catch (IllegalArgumentException expected) {
            }

            // The permutation array has an element that's out of bounds
            // (there's no argument with idx == 8).
            try {
                MethodHandles.permuteArguments(target, newType,
                        new int[]{8, 6, 5, 4, 3, 2, 1, 0});
                fail();
            } catch (IllegalArgumentException expected) {
            }

            // The permutation array maps to an incorrect type.
            try {
                MethodHandles.permuteArguments(target, newType,
                        new int[]{7, 7, 5, 4, 3, 2, 1, 0});
                fail();
            } catch (IllegalArgumentException expected) {
            }
        }

        // Tests for reference arguments as well as permutations that
        // repeat arguments.
        {
            final MethodHandle target = MethodHandles.lookup().findVirtual(
                    String.class, "concat", MethodType.methodType(String.class, String.class));

            final MethodType newType = MethodType.methodType(String.class, String.class,
                    String.class);

            assertEquals("foobar", (String) target.invoke("foo", "bar"));

            MethodHandle permutation = MethodHandles.permuteArguments(target,
                    newType, new int[]{1, 0});
            assertEquals("barfoo", (String) permutation.invoke("foo", "bar"));

            permutation = MethodHandles.permuteArguments(target, newType, new int[]{0, 0});
            assertEquals("foofoo", (String) permutation.invoke("foo", "bar"));

            permutation = MethodHandles.permuteArguments(target, newType, new int[]{1, 1});
            assertEquals("barbar", (String) permutation.invoke("foo", "bar"));
        }

        // Tests for boxing and unboxing.
        {
            final MethodHandle target = MethodHandles.lookup().findStatic(
                    MethodHandleCombinersTest.class, "permuteArguments_boxingCallee",
                    MethodType.methodType(void.class, new Class<?>[]{boolean.class, Integer.class}));

            final MethodType newType = MethodType.methodType(void.class,
                    new Class<?>[]{Integer.class, boolean.class});

            MethodHandle permutation = MethodHandles.permuteArguments(target,
                    newType, new int[]{1, 0});

            permutation.invoke(42, true);
            permutation.invoke(42, Boolean.TRUE);
            permutation.invoke(Integer.valueOf(42), true);
            permutation.invoke(Integer.valueOf(42), Boolean.TRUE);
        }
    }

    private static Object returnBar() {
        return "bar";
    }

    public static void testInvokers() throws Throwable {
        final MethodType targetType = MethodType.methodType(String.class, String.class);
        final MethodHandle target = MethodHandles.lookup().findVirtual(
                String.class, "concat", targetType);

        MethodHandle invoker = MethodHandles.invoker(target.type());
        assertEquals("barbar", (String) invoker.invoke(target, "bar", "bar"));
        assertEquals("barbar", (String) invoker.invoke(target, (Object) returnBar(), "bar"));
        try {
            String foo = (String) invoker.invoke(target, "bar", "bar", 24);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        MethodHandle exactInvoker = MethodHandles.exactInvoker(target.type());
        assertEquals("barbar", (String) exactInvoker.invoke(target, "bar", "bar"));
        try {
            String foo = (String) exactInvoker.invoke(target, (Object) returnBar(), "bar");
            fail();
        } catch (WrongMethodTypeException expected) {
        }
        try {
            String foo = (String) exactInvoker.invoke(target, "bar", "bar", 24);
            fail();
        } catch (WrongMethodTypeException expected) {
        }
    }

    public static int spreadReferences(String a, String b, String c) {
        assertEquals("a", a);
        assertEquals("b", b);
        assertEquals("c", c);
        return 42;
    }

    public static int spreadReferences_Unbox(String a, int b) {
        assertEquals("a", a);
        assertEquals(43, b);
        return 43;
    }

    public static void testSpreaders_reference() throws Throwable {
        MethodType methodType = MethodType.methodType(int.class,
                new Class<?>[]{String.class, String.class, String.class});
        MethodHandle delegate = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "spreadReferences", methodType);

        // Basic checks on array lengths.
        //
        // Array size = 0
        MethodHandle mhAsSpreader = delegate.asSpreader(String[].class, 0);
        int ret = (int) mhAsSpreader.invoke("a", "b", "c", new String[]{});
        assertEquals(42, ret);
        // Array size = 1
        mhAsSpreader = delegate.asSpreader(String[].class, 1);
        ret = (int) mhAsSpreader.invoke("a", "b", new String[]{"c"});
        assertEquals(42, ret);
        // Array size = 2
        mhAsSpreader = delegate.asSpreader(String[].class, 2);
        ret = (int) mhAsSpreader.invoke("a", new String[]{"b", "c"});
        assertEquals(42, ret);
        // Array size = 3
        mhAsSpreader = delegate.asSpreader(String[].class, 3);
        ret = (int) mhAsSpreader.invoke(new String[]{"a", "b", "c"});
        assertEquals(42, ret);

        // Exception case, array size = 4 is illegal.
        try {
            delegate.asSpreader(String[].class, 4);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        // Exception case, calling with an arg of the wrong size.
        // Array size = 3
        mhAsSpreader = delegate.asSpreader(String[].class, 3);
        try {
            ret = (int) mhAsSpreader.invoke(new String[]{"a", "b"});
        } catch (IllegalArgumentException expected) {
        }

        // Various other hijinks, pass as Object[] arrays, Object etc.
        mhAsSpreader = delegate.asSpreader(Object[].class, 2);
        ret = (int) mhAsSpreader.invoke("a", new String[]{"b", "c"});
        assertEquals(42, ret);

        mhAsSpreader = delegate.asSpreader(Object[].class, 2);
        ret = (int) mhAsSpreader.invoke("a", new Object[]{"b", "c"});
        assertEquals(42, ret);

        mhAsSpreader = delegate.asSpreader(Object[].class, 2);
        ret = (int) mhAsSpreader.invoke("a", (Object) new Object[]{"b", "c"});
        assertEquals(42, ret);

        // Test implicit unboxing.
        MethodType methodType2 = MethodType.methodType(int.class,
                new Class<?>[]{String.class, int.class});
        MethodHandle delegate2 = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "spreadReferences_Unbox", methodType2);

        // .. with an Integer[] array.
        mhAsSpreader = delegate2.asSpreader(Integer[].class, 1);
        ret = (int) mhAsSpreader.invoke("a", new Integer[]{43});
        assertEquals(43, ret);

        // .. with an Integer[] array declared as an Object[] argument type.
        mhAsSpreader = delegate2.asSpreader(Object[].class, 1);
        ret = (int) mhAsSpreader.invoke("a", new Integer[]{43});
        assertEquals(43, ret);

        // .. with an Object[] array.
        mhAsSpreader = delegate2.asSpreader(Object[].class, 1);
        ret = (int) mhAsSpreader.invoke("a", new Object[]{Integer.valueOf(43)});
        assertEquals(43, ret);

        // -- Part 2--
        // Run a subset of these tests on MethodHandles.spreadInvoker, which only accepts
        // a trailing argument type of Object[].
        MethodHandle spreadInvoker = MethodHandles.spreadInvoker(methodType2, 1);
        ret = (int) spreadInvoker.invoke(delegate2, "a", new Object[]{Integer.valueOf(43)});
        assertEquals(43, ret);

        ret = (int) spreadInvoker.invoke(delegate2, "a", new Integer[]{43});
        assertEquals(43, ret);

        // NOTE: Annoyingly, the second argument here is leadingArgCount and not
        // arrayLength.
        spreadInvoker = MethodHandles.spreadInvoker(methodType, 3);
        ret = (int) spreadInvoker.invoke(delegate, "a", "b", "c", new String[]{});
        assertEquals(42, ret);

        spreadInvoker = MethodHandles.spreadInvoker(methodType, 0);
        ret = (int) spreadInvoker.invoke(delegate, new String[]{"a", "b", "c"});
        assertEquals(42, ret);

        // Exact invokes: Double check that the expected parameter type is
        // Object[] and not T[].
        try {
            spreadInvoker.invokeExact(delegate, new String[]{"a", "b", "c"});
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        ret = (int) spreadInvoker.invoke(delegate, new Object[]{"a", "b", "c"});
        assertEquals(42, ret);
    }

    public static int spreadBoolean(String a, Boolean b, boolean c) {
        assertEquals("a", a);
        assertSame(Boolean.TRUE, b);
        assertFalse(c);

        return 44;
    }

    public static int spreadByte(String a, Byte b, byte c,
                                 short d, int e, long f, float g, double h) {
        assertEquals("a", a);
        assertEquals(Byte.valueOf((byte) 1), b);
        assertEquals((byte) 2, c);
        assertEquals((short) 3, d);
        assertEquals(4, e);
        assertEquals(5l, f);
        assertEquals(6.0f, g);
        assertEquals(7.0, h);

        return 45;
    }

    public static int spreadChar(String a, Character b, char c,
                                 int d, long e, float f, double g) {
        assertEquals("a", a);
        assertEquals(Character.valueOf('1'), b);
        assertEquals('2', c);
        assertEquals((short) '3', d);
        assertEquals('4', e);
        assertEquals((float) '5', f);
        assertEquals((double) '6', g);

        return 46;
    }

    public static int spreadShort(String a, Short b, short c,
                                  int d, long e, float f, double g) {
        assertEquals("a", a);
        assertEquals(Short.valueOf((short) 1), b);
        assertEquals(2, c);
        assertEquals(3, d);
        assertEquals(4l, e);
        assertEquals(5.0f, f);
        assertEquals(6.0, g);

        return 47;
    }

    public static int spreadInt(String a, Integer b, int c,
                                long d, float e, double f) {
        assertEquals("a", a);
        assertEquals(Integer.valueOf(1), b);
        assertEquals(2, c);
        assertEquals(3l, d);
        assertEquals(4.0f, e);
        assertEquals(5.0, f);

        return 48;
    }

    public static int spreadLong(String a, Long b, long c, float d, double e) {
        assertEquals("a", a);
        assertEquals(Long.valueOf(1), b);
        assertEquals(2l, c);
        assertEquals(3.0f, d);
        assertEquals(4.0, e);

        return 49;
    }

    public static int spreadFloat(String a, Float b, float c, double d) {
        assertEquals("a", a);
        assertEquals(Float.valueOf(1.0f), b);
        assertEquals(2.0f, c);
        assertEquals(3.0, d);

        return 50;
    }

    public static int spreadDouble(String a, Double b, double c) {
        assertEquals("a", a);
        assertEquals(Double.valueOf(1.0), b);
        assertEquals(2.0, c);

        return 51;
    }

    public static void testSpreaders_primitive() throws Throwable {
        // boolean[]
        // ---------------------
        MethodType type = MethodType.methodType(int.class,
                new Class<?>[]{String.class, Boolean.class, boolean.class});
        MethodHandle delegate = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "spreadBoolean", type);

        MethodHandle spreader = delegate.asSpreader(boolean[].class, 2);
        int ret = (int) spreader.invokeExact("a", new boolean[]{true, false});
        assertEquals(44, ret);
        ret = (int) spreader.invoke("a", new boolean[]{true, false});
        assertEquals(44, ret);

        // boolean can't be cast to String (the first argument to the method).
        try {
            delegate.asSpreader(boolean[].class, 3);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // int can't be cast to boolean to supply the last argument to the method.
        try {
            delegate.asSpreader(int[].class, 1);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // byte[]
        // ---------------------
        type = MethodType.methodType(int.class,
                new Class<?>[]{
                        String.class, Byte.class, byte.class,
                        short.class, int.class, long.class,
                        float.class, double.class});
        delegate = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "spreadByte", type);

        spreader = delegate.asSpreader(byte[].class, 7);
        ret = (int) spreader.invokeExact("a",
                new byte[]{0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7});
        assertEquals(45, ret);
        ret = (int) spreader.invoke("a",
                new byte[]{0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7});
        assertEquals(45, ret);

        // char[]
        // ---------------------
        type = MethodType.methodType(int.class,
                new Class<?>[]{
                        String.class, Character.class, char.class,
                        int.class, long.class, float.class, double.class});
        delegate = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "spreadChar", type);

        spreader = delegate.asSpreader(char[].class, 6);
        ret = (int) spreader.invokeExact("a",
                new char[]{'1', '2', '3', '4', '5', '6'});
        assertEquals(46, ret);
        ret = (int) spreader.invokeExact("a",
                new char[]{'1', '2', '3', '4', '5', '6'});
        assertEquals(46, ret);

        // short[]
        // ---------------------
        type = MethodType.methodType(int.class,
                new Class<?>[]{
                        String.class, Short.class, short.class,
                        int.class, long.class, float.class, double.class});
        delegate = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "spreadShort", type);

        spreader = delegate.asSpreader(short[].class, 6);
        ret = (int) spreader.invokeExact("a",
                new short[]{0x1, 0x2, 0x3, 0x4, 0x5, 0x6});
        assertEquals(47, ret);
        ret = (int) spreader.invoke("a",
                new short[]{0x1, 0x2, 0x3, 0x4, 0x5, 0x6});
        assertEquals(47, ret);

        // int[]
        // ---------------------
        type = MethodType.methodType(int.class,
                new Class<?>[]{
                        String.class, Integer.class, int.class,
                        long.class, float.class, double.class});
        delegate = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "spreadInt", type);

        spreader = delegate.asSpreader(int[].class, 5);
        ret = (int) spreader.invokeExact("a", new int[]{1, 2, 3, 4, 5});
        assertEquals(48, ret);
        ret = (int) spreader.invokeExact("a", new int[]{1, 2, 3, 4, 5});
        assertEquals(48, ret);

        // long[]
        // ---------------------
        type = MethodType.methodType(int.class,
                new Class<?>[]{
                        String.class, Long.class, long.class, float.class, double.class});
        delegate = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "spreadLong", type);

        spreader = delegate.asSpreader(long[].class, 4);
        ret = (int) spreader.invokeExact("a",
                new long[]{0x1, 0x2, 0x3, 0x4});
        assertEquals(49, ret);
        ret = (int) spreader.invoke("a",
                new long[]{0x1, 0x2, 0x3, 0x4});
        assertEquals(49, ret);

        // float[]
        // ---------------------
        type = MethodType.methodType(int.class,
                new Class<?>[]{
                        String.class, Float.class, float.class, double.class});
        delegate = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "spreadFloat", type);

        spreader = delegate.asSpreader(float[].class, 3);
        ret = (int) spreader.invokeExact("a",
                new float[]{1.0f, 2.0f, 3.0f});
        assertEquals(50, ret);
        ret = (int) spreader.invokeExact("a",
                new float[]{1.0f, 2.0f, 3.0f});
        assertEquals(50, ret);

        // double[]
        // ---------------------
        type = MethodType.methodType(int.class,
                new Class<?>[]{String.class, Double.class, double.class});
        delegate = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "spreadDouble", type);

        spreader = delegate.asSpreader(double[].class, 2);
        ret = (int) spreader.invokeExact("a", new double[]{1.0, 2.0});
        assertEquals(51, ret);
        ret = (int) spreader.invokeExact("a", new double[]{1.0, 2.0});
        assertEquals(51, ret);
    }

    public static void testInvokeWithArguments() throws Throwable {
        MethodType methodType = MethodType.methodType(int.class,
                new Class<?>[]{String.class, String.class, String.class});
        MethodHandle handle = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "spreadReferences", methodType);

        Object ret = handle.invokeWithArguments(new Object[]{"a", "b", "c"});
        assertEquals(42, (int) ret);
        ret = handle.invokeWithArguments((Object[]) new String[]{"a", "b", "c"});
        assertEquals(42, (int) ret);

        // Also test the versions that take a List<?> instead of an array.
        ret = handle.invokeWithArguments(Arrays.asList(new Object[] {"a", "b", "c"}));
        assertEquals(42, (int) ret);
        ret = handle.invokeWithArguments(Arrays.asList(new String[]{"a", "b", "c"}));
        assertEquals(42, (int) ret);

        // Pass in an array that's too small. Should throw an IAE.
        try {
            handle.invokeWithArguments(new Object[]{"a", "b"});
            fail();
        } catch (IllegalArgumentException expected) {
        } catch (WrongMethodTypeException expected) {
        }

        try {
            handle.invokeWithArguments(Arrays.asList(new Object[]{"a", "b"}));
            fail();
        } catch (IllegalArgumentException expected) {
        } catch (WrongMethodTypeException expected) {
        }


        // Test implicit unboxing.
        MethodType methodType2 = MethodType.methodType(int.class,
                new Class<?>[]{String.class, int.class});
        MethodHandle handle2 = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "spreadReferences_Unbox", methodType2);

        ret = (int) handle2.invokeWithArguments(new Object[]{"a", 43});
        assertEquals(43, (int) ret);
    }

    public static int collectBoolean(String a, boolean[] b) {
        assertEquals("a", a);
        assertTrue(b[0]);
        assertFalse(b[1]);

        return 44;
    }

    public static int collectByte(String a, byte[] b) {
        assertEquals("a", a);
        assertEquals((byte) 1, b[0]);
        assertEquals((byte) 2, b[1]);
        return 45;
    }

    public static int collectChar(String a, char[] b) {
        assertEquals("a", a);
        assertEquals('a', b[0]);
        assertEquals('b', b[1]);
        return 46;
    }

    public static int collectShort(String a, short[] b) {
        assertEquals("a", a);
        assertEquals((short) 3, b[0]);
        assertEquals((short) 4, b[1]);

        return 47;
    }

    public static int collectInt(String a, int[] b) {
        assertEquals("a", a);
        assertEquals(42, b[0]);
        assertEquals(43, b[1]);

        return 48;
    }

    public static int collectLong(String a, long[] b) {
        assertEquals("a", a);
        assertEquals(100l, b[0]);
        assertEquals(99l, b[1]);

        return 49;
    }

    public static int collectFloat(String a, float[] b) {
        assertEquals("a", a);
        assertEquals(8.9f, b[0]);
        assertEquals(9.1f, b[1]);

        return 50;
    }

    public static int collectDouble(String a, double[] b) {
        assertEquals("a", a);
        assertEquals(6.7, b[0]);
        assertEquals(7.8, b[1]);

        return 51;
    }

    public static int collectCharSequence(String a, CharSequence[] b) {
        assertEquals("a", a);
        assertEquals("b", b[0]);
        assertEquals("c", b[1]);
        return 99;
    }

    public static void testAsCollector() throws Throwable {
        // Reference arrays.
        // -------------------
        MethodHandle trailingRef = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "collectCharSequence",
                MethodType.methodType(int.class, String.class, CharSequence[].class));

        // int[] is not convertible to CharSequence[].class.
        try {
            trailingRef.asCollector(int[].class, 1);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        // Object[] is not convertible to CharSequence[].class.
        try {
            trailingRef.asCollector(Object[].class, 1);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        // String[].class is convertible to CharSequence.class
        MethodHandle collector = trailingRef.asCollector(String[].class, 2);
        assertEquals(99, (int) collector.invoke("a", "b", "c"));

        // Too few arguments should fail with a WMTE.
        try {
            collector.invoke("a", "b");
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // Too many arguments should fail with a WMTE.
        try {
            collector.invoke("a", "b", "c", "d");
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // Checks on other array types.

        MethodHandle target = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "collectBoolean",
                MethodType.methodType(int.class, String.class, boolean[].class));
        assertEquals(44, (int) target.asCollector(boolean[].class, 2).invoke("a", true, false));

        target = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "collectByte",
                MethodType.methodType(int.class, String.class, byte[].class));
        assertEquals(45, (int) target.asCollector(byte[].class, 2).invoke("a", (byte) 1, (byte) 2));

        target = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "collectChar",
                MethodType.methodType(int.class, String.class, char[].class));
        assertEquals(46, (int) target.asCollector(char[].class, 2).invoke("a", 'a', 'b'));

        target = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "collectShort",
                MethodType.methodType(int.class, String.class, short[].class));
        assertEquals(47, (int) target.asCollector(short[].class, 2).invoke("a", (short) 3, (short) 4));

        target = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "collectInt",
                MethodType.methodType(int.class, String.class, int[].class));
        assertEquals(48, (int) target.asCollector(int[].class, 2).invoke("a", 42, 43));

        target = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "collectLong",
                MethodType.methodType(int.class, String.class, long[].class));
        assertEquals(49, (int) target.asCollector(long[].class, 2).invoke("a", 100, 99));

        target = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "collectFloat",
                MethodType.methodType(int.class, String.class, float[].class));
        assertEquals(50, (int) target.asCollector(float[].class, 2).invoke("a", 8.9f, 9.1f));

        target = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "collectDouble",
                MethodType.methodType(int.class, String.class, double[].class));
        assertEquals(51, (int) target.asCollector(double[].class, 2).invoke("a", 6.7, 7.8));
    }

    public static String filter1(char a) {
        return String.valueOf(a);
    }

    public static char filter2(String b) {
        return b.charAt(0);
    }

    public static String badFilter1(char a, char b) {
        return "bad";
    }

    public static int filterTarget(String a, char b, String c, char d) {
        assertEquals("a", a);
        assertEquals('b', b);
        assertEquals("c", c);
        assertEquals('d', d);
        return 56;
    }

    public static void testFilterArguments() throws Throwable {
        MethodHandle filter1 = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "filter1", MethodType.methodType(String.class, char.class));
        MethodHandle filter2 = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "filter2", MethodType.methodType(char.class, String.class));

        MethodHandle target = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "filterTarget", MethodType.methodType(int.class,
                        String.class, char.class, String.class, char.class));

        // In all the cases below, the values printed will be 'a', 'b', 'c', 'd'.

        // Filter arguments [0, 1] - all other arguments are passed through
        // as is.
        MethodHandle adapter = MethodHandles.filterArguments(
                target, 0, filter1, filter2);
        assertEquals(56, (int) adapter.invokeExact('a', "bXXXX", "c", 'd'));

        // Filter arguments [1, 2].
        adapter = MethodHandles.filterArguments(target, 1, filter2, filter1);
        assertEquals(56, (int) adapter.invokeExact("a", "bXXXX", 'c', 'd'));

        // Filter arguments [2, 3].
        adapter = MethodHandles.filterArguments(target, 2, filter1, filter2);
        assertEquals(56, (int) adapter.invokeExact("a", 'b', 'c', "dXXXXX"));

        // Try out a few error cases :

        // The return types of the filter doesn't align with the expected argument
        // type of the target.
        try {
            adapter = MethodHandles.filterArguments(target, 2, filter2, filter1);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        // There are more filters than arguments.
        try {
            adapter = MethodHandles.filterArguments(target, 3, filter2, filter1);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        // We pass in an obviously bogus position.
        try {
            adapter = MethodHandles.filterArguments(target, -1, filter2, filter1);
            fail();
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        // We pass in a function that has more than one argument.
        MethodHandle badFilter1 = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "badFilter1",
                MethodType.methodType(String.class, char.class, char.class));

        try {
            adapter = MethodHandles.filterArguments(target, 0, badFilter1, filter2);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    static void voidFilter(char a, char b) {
    }

    static String filter(char a, char b) {
        return String.valueOf(a) + "+" + b;
    }

    static char badFilter(char a, char b) {
        return 0;
    }

    static String target(String a, String b, String c) {
        return ("a: " + a + ", b: " + b + ", c: " + c);
    }

    public static void testCollectArguments() throws Throwable {
        // Test non-void filters.
        MethodHandle filter = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "filter",
                MethodType.methodType(String.class, char.class, char.class));

        MethodHandle target = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "target",
                MethodType.methodType(String.class, String.class, String.class, String.class));

        // Filter at position 0.
        MethodHandle adapter = MethodHandles.collectArguments(target, 0, filter);
        assertEquals("a: a+b, b: c, c: d",
                (String) adapter.invokeExact('a', 'b', "c", "d"));

        // Filter at position 1.
        adapter = MethodHandles.collectArguments(target, 1, filter);
        assertEquals("a: a, b: b+c, c: d",
                (String) adapter.invokeExact("a", 'b', 'c', "d"));

        // Filter at position 2.
        adapter = MethodHandles.collectArguments(target, 2, filter);
        assertEquals("a: a, b: b, c: c+d",
                (String) adapter.invokeExact("a", "b", 'c', 'd'));

        // Test void filters. Note that we're passing in one more argument
        // than usual because the filter returns nothing - we have to invoke with
        // the full set of filter args and the full set of target args.
        filter = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class, "voidFilter",
                MethodType.methodType(void.class, char.class, char.class));
        adapter = MethodHandles.collectArguments(target, 0, filter);
        assertEquals("a: a, b: b, c: c",
                (String) adapter.invokeExact('a', 'b', "a", "b", "c"));

        adapter = MethodHandles.collectArguments(target, 1, filter);
        assertEquals("a: a, b: b, c: c",
                (String) adapter.invokeExact("a", 'a', 'b', "b", "c"));

        // Test out a few failure cases.
        filter = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "filter",
                MethodType.methodType(String.class, char.class, char.class));

        // Bogus filter position.
        try {
            adapter = MethodHandles.collectArguments(target, 3, filter);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }

        // Mismatch in filter return type.
        filter = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "badFilter",
                MethodType.methodType(char.class, char.class, char.class));
        try {
            adapter = MethodHandles.collectArguments(target, 0, filter);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    static int insertReceiver(String a, int b, Integer c, String d) {
        assertEquals("foo", a);
        assertEquals(56, b);
        assertEquals(Integer.valueOf(57), c);
        assertEquals("bar", d);

        return 73;
    }

    public static void testInsertArguments() throws Throwable {
        MethodHandle target = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "insertReceiver",
                MethodType.methodType(int.class,
                        String.class, int.class, Integer.class, String.class));

        // Basic single element array inserted at position 0.
        MethodHandle adapter = MethodHandles.insertArguments(
                target, 0, new Object[]{"foo"});
        assertEquals(73, (int) adapter.invokeExact(56, Integer.valueOf(57), "bar"));

        // Exercise unboxing.
        adapter = MethodHandles.insertArguments(
                target, 1, new Object[]{Integer.valueOf(56), 57});
        assertEquals(73, (int) adapter.invokeExact("foo", "bar"));

        // Exercise a widening conversion.
        adapter = MethodHandles.insertArguments(
                target, 1, new Object[]{(short) 56, Integer.valueOf(57)});
        assertEquals(73, (int) adapter.invokeExact("foo", "bar"));

        // Insert an argument at the last position.
        adapter = MethodHandles.insertArguments(
                target, 3, new Object[]{"bar"});
        assertEquals(73, (int) adapter.invokeExact("foo", 56, Integer.valueOf(57)));

        // Exercise a few error cases.

        // A reference type that can't be cast to another reference type.
        try {
            MethodHandles.insertArguments(target, 3, new Object[]{new Object()});
            fail();
        } catch (ClassCastException expected) {
        }

        // A boxed type that can't be unboxed correctly.
        try {
            MethodHandles.insertArguments(target, 1, new Object[]{Long.valueOf(56)});
            fail();
        } catch (ClassCastException expected) {
        }
    }

    public static String foldFilter(char a, char b) {
        return String.valueOf(a) + "+" + b;
    }

    public static void voidFoldFilter(String e, char a, char b) {
        assertEquals("a", e);
        assertEquals('c', a);
        assertEquals('d', b);
    }

    public static String foldTarget(String a, char b, char c, String d) {
        return ("a: " + a + " ,b:" + b + " ,c:" + c + " ,d:" + d);
    }

    public static void mismatchedVoidFilter(Integer a) {
    }

    public static Integer mismatchedNonVoidFilter(char a, char b) {
        return null;
    }

    public static void testFoldArguments() throws Throwable {
        // Test non-void filters.
        MethodHandle filter = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "foldFilter",
                MethodType.methodType(String.class, char.class, char.class));

        MethodHandle target = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "foldTarget",
                MethodType.methodType(String.class, String.class,
                        char.class, char.class, String.class));

        // Folder with a non-void type.
        MethodHandle adapter = MethodHandles.foldArguments(target, filter);
        assertEquals("a: c+d ,b:c ,c:d ,d:e",
                (String) adapter.invokeExact('c', 'd', "e"));

        // Folder with a void type.
        filter = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "voidFoldFilter",
                MethodType.methodType(void.class, String.class, char.class, char.class));
        adapter = MethodHandles.foldArguments(target, filter);
        assertEquals("a: a ,b:c ,c:d ,d:e",
                (String) adapter.invokeExact("a", 'c', 'd', "e"));

        // Test a few erroneous cases.

        filter = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "mismatchedVoidFilter",
                MethodType.methodType(void.class, Integer.class));
        try {
            adapter = MethodHandles.foldArguments(target, filter);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        filter = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "mismatchedNonVoidFilter",
                MethodType.methodType(Integer.class, char.class, char.class));
        try {
            adapter = MethodHandles.foldArguments(target, filter);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    // An exception thrown on worker threads and re-thrown on the main thread.
    static Throwable workerException = null;

    private static void invokeMultiThreaded(final MethodHandle mh) throws Throwable {
        // Create enough worker threads to be oversubscribed in bid to force some parallelism.
        final int threadCount = Runtime.getRuntime().availableProcessors() + 1;
        final Thread threads [] = new Thread [threadCount];

        // Launch worker threads and iterate invoking method handle.
        for (int i = 0; i < threadCount; ++i) {
            threads[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int j = 0; j < TEST_THREAD_ITERATIONS; ++j) {
                                mh.invoke();
                            }
                        } catch (Throwable t) {
                            workerException = t;
                            fail("Unexpected exception " + workerException);
                        }
                    }});
            threads[i].start();
        }

        // Wait for completion
        for (int i = 0; i < threadCount; ++i) {
            threads[i].join();
        }

        // Fail on main thread to avoid test appearing to complete successfully.
        Throwable t = workerException;
        workerException = null;
        if (t != null) {
            throw t;
        }
    }

    public static void testDropInsertArgumentsMultithreaded() throws Throwable {
        MethodHandle delegate = MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                "dropArguments_delegate",
                MethodType.methodType(void.class, new Class<?>[]{String.class, long.class}));
        MethodHandle mh = MethodHandles.dropArguments(delegate, 0, int.class, Object.class);
        mh = MethodHandles.insertArguments(mh, 0, 3333, "bogon", "foo", 42);
        invokeMultiThreaded(mh);
    }

    private static void exceptionHandler_delegate(NumberFormatException e, int x, int y, long z)
            throws Throwable {
        assertEquals(e.getClass(), NumberFormatException.class);
        assertEquals(e.getMessage(), "fake");
        assertEquals(x, 66);
        assertEquals(y, 51);
        assertEquals(z, 20000000000l);
    }

    public static void testThrowCatchExceptionMultiThreaded() throws Throwable {
        MethodHandle thrower = MethodHandles.throwException(void.class,
                                                            NumberFormatException.class);
        thrower = MethodHandles.dropArguments(thrower, 0, int.class, int.class, long.class);
        MethodHandle handler = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "exceptionHandler_delegate",
            MethodType.methodType(void.class, NumberFormatException.class,
                                  int.class, int.class, long.class));
        MethodHandle catcher =
            MethodHandles.catchException(thrower, NumberFormatException.class, handler);
        MethodHandle caller = MethodHandles.insertArguments(catcher, 0, 66, 51, 20000000000l,
                                                            new NumberFormatException("fake"));
        invokeMultiThreaded(caller);
    }

    private static void testTargetAndFallback_delegate(MethodHandle mh) throws Throwable {
        String actual = (String) mh.invoke("target", 42, 56);
        assertEquals("target", actual);
        actual = (String) mh.invoke("blah", 41, 56);
        assertEquals("fallback", actual);
    }

    public static void testGuardWithTestMultiThreaded() throws Throwable {
        MethodHandle test =
                MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                                                  "testGuardWithTest_test",
                                                  MethodType.methodType(boolean.class,
                                                                        new Class<?>[]{String.class,
                                                                                    long.class}));
        final MethodType type = MethodType.methodType(String.class,
                new Class<?>[]{String.class, long.class, int.class});
        final MethodHandle target =
                MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                                                  "testGuardWithTest_target", type);
        final MethodHandle fallback =
                MethodHandles.lookup().findStatic(MethodHandleCombinersTest.class,
                                                  "testGuardWithTest_fallback", type);
        MethodHandle adapter = MethodHandles.guardWithTest(test, target, fallback);
        MethodHandle tester = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class,
            "testTargetAndFallback_delegate",
            MethodType.methodType(void.class, MethodHandle.class));
        invokeMultiThreaded(MethodHandles.insertArguments(tester, 0, adapter));
    }

    private static void arrayElementSetterGetter_delegate(MethodHandle getter,
                                                          MethodHandle setter,
                                                          int [] values)
            throws Throwable{
        for (int i = 0; i < values.length; ++i) {
            int value = i * 13;
            setter.invoke(values, i, value);
            assertEquals(values[i], value);
            assertEquals(getter.invoke(values, i), values[i]);
        }
    }

    public static void testReferenceArrayGetterMultiThreaded() throws Throwable {
        MethodHandle getter = MethodHandles.arrayElementGetter(int[].class);
        MethodHandle setter = MethodHandles.arrayElementSetter(int[].class);
        MethodHandle mh = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class,
            "arrayElementSetterGetter_delegate",
            MethodType.methodType(void.class, MethodHandle.class, MethodHandle.class, int[].class));
        mh = MethodHandles.insertArguments(mh, 0, getter, setter,
                                           new int[] { 1, 2, 3, 5, 7, 11, 13, 17, 19, 23 });
        invokeMultiThreaded(mh);
    }

    private static void checkConstant_delegate(MethodHandle mh, double value) throws Throwable {
        assertEquals(mh.invoke(), value);
    }

    public static void testConstantMultithreaded() throws Throwable {
        final double value = 7.77e77;
        MethodHandle constant = MethodHandles.constant(double.class, value);
        MethodHandle mh = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkConstant_delegate",
            MethodType.methodType(void.class, MethodHandle.class, double.class));
        mh = MethodHandles.insertArguments(mh, 0, constant, value);
        invokeMultiThreaded(mh);
    }

    private static void checkIdentity_delegate(MethodHandle mh, char value) throws Throwable {
        assertEquals(mh.invoke(value), value);
    }

    public static void testIdentityMultiThreaded() throws Throwable {
        final char value = 'z';
        MethodHandle identity = MethodHandles.identity(char.class);
        MethodHandle mh = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkIdentity_delegate",
            MethodType.methodType(void.class, MethodHandle.class, char.class));
        mh = MethodHandles.insertArguments(mh, 0, identity, value);
        invokeMultiThreaded(mh);
    }

    private static int multiplyByTwo(int x) { return x * 2; }
    private static int divideByTwo(int x) { return x / 2; }
    private static void assertMethodHandleInvokeEquals(MethodHandle mh, int value) throws Throwable{
        assertEquals(mh.invoke(value), value);
    }

    public static void testFilterReturnValueMultiThreaded() throws Throwable {
        MethodHandle target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "multiplyByTwo",
            MethodType.methodType(int.class, int.class));
        MethodHandle filter = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "divideByTwo",
            MethodType.methodType(int.class, int.class));
        MethodHandle filtered = MethodHandles.filterReturnValue(target, filter);
        assertEquals(filtered.invoke(33), 33);
        MethodHandle mh = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "assertMethodHandleInvokeEquals",
            MethodType.methodType(void.class, MethodHandle.class, int.class));
        invokeMultiThreaded(MethodHandles.insertArguments(mh, 0, filtered, 77));
    }

    public static void compareStringAndFloat(String s, float f) {
        assertEquals(s, Float.toString(f));
    }

    public static void testPermuteArgumentsMultiThreaded() throws Throwable {
        MethodHandle mh = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "compareStringAndFloat",
            MethodType.methodType(void.class, String.class, float.class));
        mh = MethodHandles.permuteArguments(
            mh, MethodType.methodType(void.class, float.class, String.class), 1, 0);
        invokeMultiThreaded(MethodHandles.insertArguments(mh, 0, 2.22f, "2.22"));
    }

    public static void testSpreadInvokerMultiThreaded() throws Throwable {
        MethodType methodType = MethodType.methodType(
            int.class, new Class<?>[]{String.class, String.class, String.class});
        MethodHandle delegate = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "spreadReferences", methodType);
        MethodHandle mh = delegate.asSpreader(String[].class, 3);
        mh = MethodHandles.insertArguments(mh, 0, new Object[] { new String [] { "a", "b", "c" }});
        invokeMultiThreaded(mh);
    }

    public static void testCollectorMultiThreaded() throws Throwable {
        MethodHandle trailingRef = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "collectCharSequence",
                MethodType.methodType(int.class, String.class, CharSequence[].class));
        MethodHandle mh = trailingRef.asCollector(String[].class, 2);
        mh = MethodHandles.insertArguments(mh, 0, "a", "b", "c");
        invokeMultiThreaded(mh);
    }

    public static void testFilterArgumentsMultiThreaded() throws Throwable {
        MethodHandle filter1 = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "filter1",
            MethodType.methodType(String.class, char.class));
        MethodHandle filter2 = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "filter2",
            MethodType.methodType(char.class, String.class));
        MethodHandle target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "filterTarget",
            MethodType.methodType(int.class, String.class, char.class, String.class, char.class));
        MethodHandle adapter = MethodHandles.filterArguments(target, 2, filter1, filter2);
        invokeMultiThreaded(MethodHandles.insertArguments(adapter, 0, "a", 'b', 'c', "dXXXXX"));
    }

    private static void checkStringResult_delegate(MethodHandle mh,
                                                   String expected) throws Throwable {
        assertEquals(mh.invoke(), expected);
    }

    public static void testCollectArgumentsMultiThreaded() throws Throwable {
        MethodHandle filter = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "filter",
            MethodType.methodType(String.class, char.class, char.class));
        MethodHandle target = MethodHandles.lookup().findStatic(
                MethodHandleCombinersTest.class, "target",
                MethodType.methodType(String.class, String.class, String.class, String.class));
        MethodHandle collect = MethodHandles.collectArguments(target, 2, filter);
        collect = MethodHandles.insertArguments(collect, 0, "a", "b", 'c', 'd');
        MethodHandle mh = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkStringResult_delegate",
            MethodType.methodType(void.class, MethodHandle.class, String.class));
        invokeMultiThreaded(MethodHandles.insertArguments(mh, 0, collect, "a: a, b: b, c: c+d"));
    }

    public static void testFoldArgumentsMultiThreaded() throws Throwable {
        MethodHandle target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "foldTarget",
            MethodType.methodType(String.class, String.class,
                                  char.class, char.class, String.class));
        MethodHandle filter = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "foldFilter",
            MethodType.methodType(String.class, char.class, char.class));
        MethodHandle adapter = MethodHandles.foldArguments(target, filter);
        adapter = MethodHandles.insertArguments(adapter, 0, 'c', 'd', "e");
        MethodHandle mh = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkStringResult_delegate",
            MethodType.methodType(void.class, MethodHandle.class, String.class));
        invokeMultiThreaded(MethodHandles.insertArguments(mh, 0, adapter, "a: c+d ,b:c ,c:d ,d:e"));
    }

    private static void checkBooleanCast_delegate(boolean expected, boolean z,  boolean b,
                                                  boolean c, boolean s, boolean i, boolean j,
                                                  boolean f, boolean d, boolean l) {
        assertEquals(expected, z);
        assertEquals(expected, b);
        assertEquals(expected, c);
        assertEquals(expected, s);
        assertEquals(expected, i);
        assertEquals(expected, j);
        assertEquals(expected, f);
        assertEquals(expected, d);
        assertEquals(expected, l);
    }

    private static void checkByteCast_delegate(byte expected, byte z, byte b, byte c, byte s,
                                               byte i, byte j, byte f, byte d, byte l) {
        int mask = 0xff;
        assertEquals(expected & 1, z);
        assertEquals(expected, b);
        assertEquals(expected, c & mask);
        assertEquals(expected, s & mask);
        assertEquals(expected, i & mask);
        assertEquals(expected, j & mask);
        assertEquals(expected, f & mask);
        assertEquals(expected, d & mask);
        assertEquals(expected, l);
    }

    private static void checkCharCast_delegate(char expected, char z, char b, char c, char s,
                                               char i, char j, char f, char d, char l) {
        int mask = 0xffff;
        assertEquals(expected & 1, z);
        assertEquals(expected & 0xff, b);
        assertEquals(expected, c);
        assertEquals(expected, s & mask);
        assertEquals(expected, i & mask);
        assertEquals(expected, j & mask);
        assertEquals(expected, f & mask);
        assertEquals(expected, d & mask);
        assertEquals(expected, l);
    }

    private static void checkShortCast_delegate(short expected, short z, short b, short c, short s,
                                                short i, short j, short f, short d, short l) {
        int mask = 0xffff;
        assertEquals(expected & 1, z);
        assertEquals(expected & 0xff, b);
        assertEquals(expected, c & mask);
        assertEquals(expected, s);
        assertEquals(expected, i & mask);
        assertEquals(expected, j & mask);
        assertEquals(expected, f & mask);
        assertEquals(expected, d & mask);
        assertEquals(expected, l);
    }

    private static void checkIntCast_delegate(int expected, int z, int b, int c, int s, int i,
                                              int j, int f, int d, int l) {
        int mask = 0xffffffff;
        assertEquals(expected & 1, z);
        assertEquals(expected & 0xff, b);
        assertEquals(expected & 0xffff, c);
        assertEquals(expected & 0xffff, s);
        assertEquals(expected, i & mask);
        assertEquals(expected, j & mask);
        assertEquals(expected, f & mask);
        assertEquals(expected, d & mask);
        assertEquals(expected, l);
    }

    private static void checkLongCast_delegate(long expected, long z, long b, long c, long s,
                                               long i, long j, long f, long d, long l) {
        long mask = 0xffffffffl;
        assertEquals(expected & 1, z);
        assertEquals(expected & 0xff, b);
        assertEquals(expected & 0xffff, c);
        assertEquals(expected & 0xffff, s);
        assertEquals(expected & mask, i & mask);
        assertEquals(expected, j);
        assertEquals(expected & mask, f & mask);
        assertEquals(expected, d);
        assertEquals(expected, l);
    }

    private static void checkFloatCast_delegate(float expected, float z, float b, float c, float s,
                                                float i, float j, float f, float d, float l) {
        assertEquals((byte) expected & 1, (int) z);
        assertEquals((byte) expected, (int) b & 0xff);
        assertEquals((char) expected & 0xffff, (int) c& 0xffff);
        assertEquals((short) expected & 0xffff, (int) s & 0xffff);
        assertEquals((int) expected, (int) i);
        assertEquals((long) expected, (long) j);
        assertEquals(expected, f);
        assertEquals(expected, d);
        assertEquals(expected, l);
    }

    private static void checkDoubleCast_delegate(double expected, double z, double b, double c,
                                                 double s, double i, double j, double f, double d,
                                                 double l) {
        assertEquals((byte) expected & 1, (int) z);
        assertEquals((byte) expected & 0xff, (int) b & 0xff);
        assertEquals((int) expected & 0xffff, (int) c & 0xffff);
        assertEquals((int) expected & 0xffff, (int) s & 0xffff);
        assertEquals((int) expected, (int) i);
        assertEquals((long) expected, (long) j);
        assertEquals((float) expected, (float) f);
        assertEquals(expected, d);
        assertEquals(expected, l);
    }

    private static void checkBoxingCasts_delegate(boolean expected, Boolean z, Byte b, Character c,
                                                  Short s, Integer i, Long j, Float f, Double d) {
        int v = expected ? 1 : 0;
        assertEquals(Boolean.valueOf(expected ? true : false), z);
        assertEquals(Byte.valueOf((byte) v), b);
        assertEquals(Character.valueOf((char) v), c);
        assertEquals(Short.valueOf((short) v), s);
        assertEquals(Integer.valueOf(v), i);
        assertEquals(Long.valueOf(v), j);
        assertEquals(Float.valueOf(v), f);
        assertEquals(Double.valueOf(v), d);
    }

    public static void testExplicitCastArguments() throws Throwable {
        MethodHandle target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkBooleanCast_delegate",
            MethodType.methodType(void.class, boolean.class, boolean.class, boolean.class,
                                  boolean.class, boolean.class, boolean.class, boolean.class,
                                  boolean.class, boolean.class, boolean.class));
        MethodHandle mh = MethodHandles.explicitCastArguments(
            target, MethodType.methodType(void.class, boolean.class, boolean.class, byte.class,
                                          char.class, short.class, int.class, long.class,
                                          float.class, double.class, Boolean.class));
        mh.invokeExact(false, false, (byte) 0, (char) 0, (short) 0, 0, 0l, 0.0f, 0.0,
                       Boolean.valueOf(false));
        mh.invokeExact(false, false, (byte) 2, (char) 2, (short) 2, 2, 2l, 2.2f, 2.2,
                       Boolean.valueOf(false));
        mh.invokeExact(true, true, (byte) 1, (char) 1, (short) 1, 1, 1l, 1.0f, 1.0,
                       Boolean.valueOf(true));
        mh.invokeExact(true, true, (byte) 51, (char) 51, (short) 51, 51, 51l, 51.0f, 51.0,
                       Boolean.valueOf(true));
        MethodHandles.explicitCastArguments(
            target, MethodType.methodType(void.class, boolean.class, boolean.class, byte.class,
                                          char.class, short.class, int.class, long.class,
                                          float.class, double.class, String.class));
        try {
            mh.invoke(true, true, (byte) 51, (char) 51, (short) 51, 51, 51l, 51.0f, 51.0,
                      "ClassCastException here!");
            fail();
        } catch (ClassCastException e) {
        }

        target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkByteCast_delegate",
            MethodType.methodType(void.class, byte.class, byte.class, byte.class,
                                  byte.class, byte.class, byte.class, byte.class,
                                  byte.class, byte.class, byte.class));
        mh = MethodHandles.explicitCastArguments(
            target, MethodType.methodType(void.class, byte.class, boolean.class, byte.class,
                                          char.class, short.class, int.class, long.class,
                                          float.class, double.class, Byte.class));
        mh.invokeExact((byte) 0x5a, false, (byte) 0x5a, (char) 0x5a5a, (short) 0x5a5a, (int) 0x5a5a,
                       (long) 0x5a5a, (float) 0x5a5a, (double) 0x5a5a, Byte.valueOf((byte) 0x5a));
        try {
            mh.invoke((byte) 0x5a, false, (byte) 0x5a, (char) 0x5a5a, (short) 0x5a5a, (int) 0x5a5a,
                      (long) 0x5a5a, (float) 0x5a5a, (double) 0x5a5a,
                      Short.valueOf((short) 0x5a5a));
            fail();
        } catch (ClassCastException e) {
        }

        target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkCharCast_delegate",
            MethodType.methodType(void.class, char.class, char.class, char.class,
                                  char.class, char.class, char.class, char.class,
                                  char.class, char.class, char.class));
        mh = MethodHandles.explicitCastArguments(
            target, MethodType.methodType(void.class, char.class, boolean.class, byte.class,
                                          char.class, short.class, int.class, long.class,
                                          float.class, double.class, Character.class));
        mh.invokeExact((char) 0x5555, true, (byte) 0x5555, (char) 0x5555, (short) 0x5555,
                       (int) 0x5555, (long) 0x5555, (float) 0x5555, (double) 0x5555,
                       Character.valueOf((char) 0x5555));
        try {
            mh.invoke((char) 0x5555, false, (byte) 0x5555, (char) 0x5555, (short) 0x5555,
                      (int) 0x5555, (long) 0x5555, (float) 0x5555, (double) 0x5555,
                      Integer.valueOf((int) 0x5555));
            fail();
        } catch (ClassCastException e) {
        }

        target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkShortCast_delegate",
            MethodType.methodType(void.class, short.class, short.class, short.class,
                                  short.class, short.class, short.class, short.class,
                                  short.class, short.class, short.class));
        mh = MethodHandles.explicitCastArguments(
            target, MethodType.methodType(void.class, short.class, boolean.class, byte.class,
                                          char.class, short.class, int.class, long.class,
                                          float.class, double.class, Short.class));
        mh.invokeExact((short) 0x3773, true, (byte) 0x3773, (char) 0x3773, (short) 0x3773,
                       (int) 0x3773, (long) 0x3773, (float) 0x3773, (double) 0x3773,
                       Short.valueOf((short) 0x3773));
        try {
            mh.invoke((short) 0x3773, true, (byte) 0x3773, (char) 0x3773, (short) 0x3773,
                      (int) 0x3773, (long) 0x3773, (float) 0x3773, (double) 0x3773,
                      Long.valueOf((long) 0x3773));
            fail();
        } catch (ClassCastException e) {
        }

        target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkIntCast_delegate",
            MethodType.methodType(void.class, int.class, int.class, int.class,
                                  int.class, int.class, int.class, int.class,
                                  int.class, int.class, int.class));
        mh = MethodHandles.explicitCastArguments(
            target, MethodType.methodType(void.class, int.class, boolean.class, byte.class,
                                          char.class, short.class, int.class, long.class,
                                          float.class, double.class, Integer.class));
        mh.invokeExact((int) 0x3773470, false, (byte) 0x3773470, (char) 0x3773470,
                       (short) 0x3773470, (int) 0x3773470, (long) 0x3773470, (float) 0x3773470,
                       (double) 0x3773470, Integer.valueOf(0x3773470));
        try {
            mh.invoke((int) 0x3773470, false, (byte) 0x3773470, (char) 0x3773470,
                      (short) 0x3773470, (int) 0x3773470, (long) 0x3773470, (float) 0x3773470,
                      (double) 0x3773470, Long.valueOf((long) 0x3773470));
            fail();
        } catch (ClassCastException e) {
        }

        target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkLongCast_delegate",
            MethodType.methodType(void.class, long.class, long.class, long.class,
                                  long.class, long.class, long.class, long.class,
                                  long.class, long.class, long.class));
        mh = MethodHandles.explicitCastArguments(
            target, MethodType.methodType(void.class, long.class, boolean.class, byte.class,
                                          char.class, short.class, int.class, long.class,
                                          float.class, double.class, Long.class));
        long longValue = 0x770000000l;
        mh.invokeExact((long) longValue, false, (byte) longValue, (char) longValue,
                       (short) longValue, (int) longValue, (long) longValue, (float) longValue,
                       (double) longValue, Long.valueOf(longValue));
        try {
            mh.invoke((long) longValue, false, (byte) longValue, (char) longValue,
                      (short) longValue, (int) longValue, (long) longValue, (float) longValue,
                      (double) longValue, Integer.valueOf(3));
            fail();
        } catch (ClassCastException e) {
        }

        target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkFloatCast_delegate",
            MethodType.methodType(void.class, float.class, float.class, float.class,
                                  float.class, float.class, float.class, float.class,
                                  float.class, float.class, float.class));
        mh = MethodHandles.explicitCastArguments(
            target, MethodType.methodType(void.class, float.class, boolean.class, byte.class,
                                          char.class, short.class, int.class, long.class,
                                          float.class, double.class, Float.class));
        float floatValue = 33333.141f;
        mh.invokeExact(floatValue, true, (byte) floatValue, (char) floatValue, (short) floatValue,
                       (int) floatValue, (long) floatValue, floatValue, (double) floatValue,
                       Float.valueOf(floatValue));
        try {
            mh.invoke(floatValue, true, (byte) floatValue, (char) floatValue,
                      (short) floatValue, (int) floatValue, (long) floatValue, floatValue,
                      (double) floatValue, Integer.valueOf((int) floatValue));
            fail();
        } catch (ClassCastException e) {
        }

        target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkDoubleCast_delegate",
            MethodType.methodType(void.class, double.class, double.class, double.class,
                                  double.class, double.class, double.class, double.class,
                                  double.class, double.class, double.class));
        mh = MethodHandles.explicitCastArguments(
            target, MethodType.methodType(void.class, double.class, boolean.class, byte.class,
                                          char.class, short.class, int.class, long.class,
                                          float.class, double.class, Double.class));
        double doubleValue = 33333333333.141;
        mh.invokeExact(doubleValue, true, (byte) doubleValue, (char) doubleValue,
                       (short) doubleValue, (int) doubleValue, (long) doubleValue,
                       (float) doubleValue, doubleValue, Double.valueOf(doubleValue));
        try {
            mh.invoke(doubleValue, true, (byte) doubleValue, (char) doubleValue,
                      (short) doubleValue, (int) doubleValue, (long) doubleValue,
                      (float) doubleValue, (double) doubleValue,
                      Integer.valueOf((int) doubleValue));
            fail();
        } catch (ClassCastException e) {
        }

        target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "checkBoxingCasts_delegate",
            MethodType.methodType(void.class, boolean.class, Boolean.class, Byte.class,
                                  Character.class, Short.class, Integer.class, Long.class,
                                  Float.class, Double.class));
        mh = MethodHandles.explicitCastArguments(
            target, MethodType.methodType(void.class, boolean.class, boolean.class, byte.class,
                                          char.class, short.class, int.class, long.class,
                                          float.class, double.class));
        mh.invokeExact(false, false, (byte) 0, (char) 0, (short) 0, 0, 0l, 0.0f, 0.0);
        mh.invokeExact(true, true, (byte) 1, (char) 1, (short) 1, 1, 1l, 1.0f, 1.0);
        mh.invoke(Boolean.valueOf(false), Boolean.valueOf(false), Byte.valueOf((byte) 0),
                  Character.valueOf((char) 0), Short.valueOf((short) 0), Integer.valueOf(0),
                  Long.valueOf(0l), Float.valueOf(0.0f), Double.valueOf(0.0));
        mh.invoke(Boolean.valueOf(true), Boolean.valueOf(true), Byte.valueOf((byte) 1),
                  Character.valueOf((char) 1), Short.valueOf((short) 1), Integer.valueOf(1),
                  Long.valueOf(1l), Float.valueOf(1.0f), Double.valueOf(1.0));
        mh = MethodHandles.explicitCastArguments(
            target, MethodType.methodType(void.class, double.class, boolean.class, byte.class,
                                          char.class, short.class, int.class, long.class,
                                          float.class, double.class));
        mh.invokeExact(0.0, false, (byte) 0, (char) 0, (short) 0, 0, 0l, 0.0f, 0.0);
    }

    static void returnVoid() {}

    static boolean returnBoolean(boolean b) { return b; }

    static Boolean returnBooleanObject(boolean b) { return b; }

    public static void testExplicitCastReturnValues() throws Throwable {
        MethodHandle target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "returnVoid", MethodType.methodType(void.class));
        assertEquals(false,
                     MethodHandles
                     .explicitCastArguments(target, MethodType.methodType(boolean.class))
                     .invoke());
        assertEquals(null,
                     MethodHandles
                     .explicitCastArguments(target, MethodType.methodType(Boolean.class))
                     .invoke());
        assertEquals(0l,
                     MethodHandles
                     .explicitCastArguments(target, MethodType.methodType(long.class))
                     .invoke());
        assertEquals(null,
                     MethodHandles
                     .explicitCastArguments(target, MethodType.methodType(Long.class))
                     .invoke());

        target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "returnBoolean",
            MethodType.methodType(boolean.class, boolean.class));
        assertEquals(false,
                     MethodHandles
                     .explicitCastArguments(target,
                                            MethodType.methodType(boolean.class, boolean.class))
                     .invoke(false));
        assertEquals(true,
                     MethodHandles
                     .explicitCastArguments(target,
                                            MethodType.methodType(boolean.class, boolean.class))
                     .invoke(true));
        assertEquals(Boolean.valueOf(false),
                     MethodHandles
                     .explicitCastArguments(target,
                                            MethodType.methodType(Boolean.class, boolean.class))
                     .invoke(false));
        assertEquals(Boolean.valueOf(true),
                     MethodHandles
                     .explicitCastArguments(target,
                                            MethodType.methodType(Boolean.class, boolean.class))
                     .invoke(true));
        assertEquals((byte) 0,
                     MethodHandles
                     .explicitCastArguments(target,
                                            MethodType.methodType(byte.class, boolean.class))
                     .invoke(false));
        assertEquals((byte) 1,
                     MethodHandles
                     .explicitCastArguments(target,
                                            MethodType.methodType(byte.class, boolean.class))
                     .invoke(true));
        try {
            assertEquals(Byte.valueOf((byte) 0),
                         MethodHandles
                         .explicitCastArguments(target,
                                                MethodType.methodType(Byte.class, boolean.class))
                         .invoke(false));
            fail();
        } catch (ClassCastException e) {
        }

        try {
            assertEquals(Byte.valueOf((byte) 1),
                         MethodHandles
                         .explicitCastArguments(target,
                                                MethodType.methodType(Byte.class, boolean.class))
                         .invoke(true));
        } catch (ClassCastException e) {
        }

        target = MethodHandles.lookup().findStatic(
            MethodHandleCombinersTest.class, "returnBooleanObject",
            MethodType.methodType(Boolean.class, boolean.class));
        assertEquals(false,
                     (boolean) MethodHandles
                     .explicitCastArguments(target,
                                            MethodType.methodType(boolean.class, boolean.class))
                     .invokeExact(false));
        assertEquals(true,
                     (boolean) MethodHandles
                     .explicitCastArguments(target,
                                            MethodType.methodType(boolean.class, boolean.class))
                     .invokeExact(true));
    }
}
