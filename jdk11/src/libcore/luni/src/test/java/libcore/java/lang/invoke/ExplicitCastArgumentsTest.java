/*
 * Copyright (C) 2019 The Android Open Source Project
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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ExplicitCastArgumentsTest {
    //
    // Constants for value casts.
    //
    private static final Boolean[] BOOLEAN_VALUES = new Boolean[]{ Boolean.TRUE, Boolean.FALSE };

    private static final Byte[] BYTE_VALUES = new Byte[]{
        Byte.valueOf((byte) 0), Byte.valueOf((byte) 1), Byte.valueOf((byte) 2),
        Byte.valueOf((byte) -1), Byte.valueOf((byte) -2), Byte.MIN_VALUE, Byte.MAX_VALUE
    };

    private static final Character[] CHARACTER_VALUES = new Character[]{
        Character.MIN_VALUE, Character.MAX_VALUE, Character.valueOf('A'), Character.valueOf('B'),
    };

    private static final Short[] SHORT_VALUES = new Short[]{
        Short.valueOf((short) 0), Short.valueOf((short) 1), Short.valueOf((short) 130),
        Short.valueOf((short) -1), Short.valueOf((short) -130), Short.MIN_VALUE, Short.MAX_VALUE
    };

    private static final Integer[] INTEGER_VALUES = new Integer[]{
        Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(130), Integer.valueOf(32768),
        Integer.valueOf(-1), Integer.valueOf(-130), Integer.valueOf(-32769),
        Integer.MIN_VALUE, Integer.MAX_VALUE
    };

    private static final Long[] LONG_VALUES = new Long[]{
        Long.valueOf(0L), Long.valueOf(1L), Long.valueOf(130L), Long.valueOf(32768L),
        Long.valueOf(0x800000000L), Long.valueOf(0x800000001L),
        Long.valueOf(-1l), Long.valueOf(-130l), Long.valueOf(-32769l), Long.valueOf(-0x800000000L),
        Long.valueOf(-0x800000001L), Long.MIN_VALUE, Long.MAX_VALUE
    };

    private static final Float[] FLOAT_VALUES = new Float[]{
        Float.valueOf(0.0f), Float.valueOf(0.5f), Float.valueOf(1.0f), Float.valueOf(2.0f),
        Float.valueOf(3.141f), Float.valueOf(-0.5f), Float.valueOf(-1.0f), Float.valueOf(-2.0f),
        Float.valueOf(-3.141f), Float.MIN_VALUE, Float.MAX_VALUE
    };

    private static final Double[] DOUBLE_VALUES = new Double[]{
        Double.valueOf(0.0), Double.valueOf(0.5), Double.valueOf(1.0), Double.valueOf(2.0),
        Double.valueOf(3.141), Double.valueOf(-0.5), Double.valueOf(-1.0), Double.valueOf(-2.0),
        Double.valueOf(-3.141), Double.MIN_VALUE, Double.MAX_VALUE,
    };

    // Conversions to boolean for explicitCastArgument().
    private static boolean toBooleanValue(byte v) { return (v & 1) != 0; }
    private static boolean toBooleanValue(char v) { return toBooleanValue((byte) v); }
    private static boolean toBooleanValue(short v) { return toBooleanValue((byte) v); }
    private static boolean toBooleanValue(int v) { return toBooleanValue((byte) v); }
    private static boolean toBooleanValue(long v) { return toBooleanValue((byte) v); }
    private static boolean toBooleanValue(float v) { return toBooleanValue((long) v); }
    private static boolean toBooleanValue(double v) { return toBooleanValue((long) v); }

    // Conversions from boolean for explicitCastArgument().
    private static byte byteFromBooleanValue(boolean v) { return v ? (byte) 1 : (byte) 0; }
    private static char charFromBooleanValue(boolean v) { return v ? (char) 1 : (char) 0; }
    private static short shortFromBooleanValue(boolean v) { return v ? (short) 1 : (short) 0; }
    private static int intFromBooleanValue(boolean v) { return v ? 1 : 0; }
    private static long longFromBooleanValue(boolean v) { return v ? 1L : 0L; }
    private static float floatFromBooleanValue(boolean v) { return v ? 1.0f : 0.0f; }
    private static double doubleFromBooleanValue(boolean v) { return v ? 1.0 : 0.0; }

    // Helper constructing a MethodHandle of type (identityClass, argClass) for testing
    // explicit casts applied to the argument of the MethodHandle invocation.
    private static MethodHandle explicitCastArgumentToIdentity(Class identityClass,
                                                               Class argClass) {
        MethodHandle identity = MethodHandles.identity(identityClass);
        MethodType mt = MethodType.methodType(identityClass, argClass);
        return MethodHandles.explicitCastArguments(identity, mt);
    }

    // Helper constructing a MethodHandle of type (argClass, identity) for testing
    // explicit casts applied to the return value from the MethodHandle invocation.
    private static MethodHandle explicitCastReturnValueFromIdentity(Class identityClass,
                                                                    Class retClass) {
        MethodHandle identity = MethodHandles.identity(identityClass);
        MethodType mt = MethodType.methodType(retClass, identityClass);
        return MethodHandles.explicitCastArguments(identity, mt);
    }

    // Helper for constructing a typed null constant with aan explicit cast to a primitive
    // type.
    private static MethodHandle nullConstantExplicitCastToPrimitive(Class constantType,
                                                                    Class primitiveType) {
        return MethodHandles.explicitCastArguments(MethodHandles.constant(constantType, null),
                                                   MethodType.methodType(primitiveType));
    }

    // Helper returning void.
    public static void voidFunction() {
    }

    // Helper for constructing an explicit cast from void to type return value.
    private static MethodHandle explicitCastVoidReturnValue(Class toType) throws Throwable {
        MethodHandle m =
            MethodHandles.publicLookup().findStatic(ExplicitCastArgumentsTest.class,
                                                    "voidFunction",
                                                    MethodType.methodType(void.class));
        return MethodHandles.explicitCastArguments(m, MethodType.methodType(toType));
    }

    // Helper classes and interfaces for reference checks.
    interface ParentInterface {
        public static String name = "ParentInterface";
    }

    interface ChildInterface {
        public static String name = "ChildInterface";
    }

    class Parent implements ParentInterface {}

    class Child extends Parent implements ChildInterface {}

    // Explicit casting of arguments and return values for reference types.
    @Test
    public void explicitCastArgumentParentToChild() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Child.class, Parent.class);
        try {
            Child c = (Child) mh.invokeExact(new Parent());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentNullParentToChild() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Child.class, Parent.class);
        Child c = (Child) mh.invokeExact((Parent) null);
        assertNull(c);
    }

    @Test
    public void explicitCastArgumentChildToParent() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Parent.class, Child.class);
        Parent p = (Parent) mh.invokeExact(new Child());
        assertTrue(p instanceof Child);
    }

    @Test
    public void explicitCastArgumentNullChildToParent() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Parent.class, Child.class);
        Parent p = (Parent) mh.invokeExact((Child) null);
        assertNull(p);
    }

    @Test
    public void explicitCastReturnValueNullParentToChild() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Parent.class, Child.class);
        Child c = (Child) mh.invokeExact((Parent) null);
        assertNull(c);
    }

    @Test
    public void explicitCastReturnValueParentToChild() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Parent.class, Child.class);
        try {
            Child c = (Child) mh.invokeExact(new Parent());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueNullChildToParent() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Child.class, Parent.class);
        Parent p = (Parent) mh.invokeExact((Child) null);
        assertNull(p);
    }

    @Test
    public void explicitCastReturnValueChildToParent() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Child.class, Parent.class);
        Parent x = (Parent) mh.invokeExact(new Child());
        assertTrue(x instanceof Child);
    }

    @Test
    public void explicitCastArgumentOfInterfaceType_doesNotThrow() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(ParentInterface.class, Parent.class);
        ParentInterface pi = (ParentInterface) mh.invokeExact(new Parent());
        assertTrue(pi instanceof ParentInterface);
        pi = (ParentInterface) mh.invokeExact((Parent) null);
        assertNull(pi);

        MethodHandle mh1 = explicitCastArgumentToIdentity(ParentInterface.class, Child.class);
        pi = (ParentInterface) mh1.invokeExact(new Child());
        assertTrue(pi instanceof ParentInterface);
        pi = (ParentInterface) mh1.invokeExact((Child) null);
        assertNull(pi);

        MethodHandle mh2 = explicitCastArgumentToIdentity(ParentInterface.class, Integer.class);
        pi = (ParentInterface) mh2.invokeExact(Integer.valueOf(3));
        assertFalse(pi instanceof ParentInterface);
        pi = (ParentInterface) mh2.invokeExact((Integer) null);
        assertNull(pi);
    }

    @Test
    public void explicitCastReturnValueOfInterfaceType_doesNotThrow() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Parent.class,
                                                              ParentInterface.class);
        ParentInterface pi = (ParentInterface) mh.invokeExact(new Parent());
        assertTrue(pi instanceof ParentInterface);
        pi = (ParentInterface) mh.invokeExact((Parent) null);
        assertNull(pi);

        MethodHandle mh1 = explicitCastReturnValueFromIdentity(Child.class,
                                                               ParentInterface.class);
        pi = (ParentInterface) mh1.invokeExact(new Child());
        assertTrue(pi instanceof ParentInterface);
        pi = (ParentInterface) mh1.invokeExact((Child) null);
        assertNull(pi);

        MethodHandle mh2 = explicitCastReturnValueFromIdentity(Integer.class,
                                                               ParentInterface.class);
        pi = (ParentInterface) mh2.invokeExact(Integer.valueOf(42));
        assertFalse(pi instanceof ParentInterface);
        pi = (ParentInterface) mh2.invokeExact((Integer) null);
        assertNull(pi);
    }

    @Test
    public void originalTypeAndNewTypeEqual() throws Throwable {
        MethodHandle mh = MethodHandles.identity(Integer.class);
        assertEquals(mh, MethodHandles.explicitCastArguments(mh, mh.type()));
        assertEquals(mh,
                     MethodHandles.explicitCastArguments(mh,
                                                         MethodType.methodType(Integer.class,
                                                                               Integer.class)));
    }

    //
    // Explicit casting of arguments between the primitive types and their
    // reference type counterparts.
    //
    @Test
    public void explicitCastArgumentZToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, boolean.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(v, (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentZToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, boolean.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(byteFromBooleanValue(v), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentZToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, boolean.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(charFromBooleanValue(v), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentZToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, boolean.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(shortFromBooleanValue(v), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentZToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, boolean.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(intFromBooleanValue(v), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentZToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, boolean.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(longFromBooleanValue(v), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentZToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, boolean.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(floatFromBooleanValue(v), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentZToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, boolean.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(doubleFromBooleanValue(v), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentZToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, boolean.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(Boolean.valueOf(v), (Boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentZToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, boolean.class);
        try {
            Byte o = (Byte) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentZToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, boolean.class);
        try {
            Character o = (Character) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentZToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, boolean.class);
        try {
            Short o = (Short) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentZToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, boolean.class);
        try {
            Integer o = (Integer) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentZToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, boolean.class);
        try {
            Long o = (Long) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentZToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, boolean.class);
        try {
            Float o = (Float) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentZToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, boolean.class);
        try {
            Double o = (Double) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, byte.class);
        for (byte v : BYTE_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, byte.class);
        for (byte v : BYTE_VALUES) {
            assertEquals(v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, byte.class);
        for (byte v : BYTE_VALUES) {
            assertEquals((char) v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, byte.class);
        for (byte v : BYTE_VALUES) {
            assertEquals((short) v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, byte.class);
        for (byte v : BYTE_VALUES) {
            assertEquals((int) v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, byte.class);
        for (byte v : BYTE_VALUES) {
            assertEquals((long) v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, byte.class);
        for (byte v : BYTE_VALUES) {
            assertEquals((float) v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentBToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, byte.class);
        for (byte v : BYTE_VALUES) {
            assertEquals((double) v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentBToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, byte.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, byte.class);
        for (byte v : BYTE_VALUES) {
            assertEquals(Byte.valueOf(v), (Byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, byte.class);
        try {
            Character o = (Character) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, byte.class);
        try {
            Short o = (Short) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, byte.class);
        try {
            Integer o = (Integer) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, byte.class);
        try {
            Long o = (Long) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, byte.class);
        try {
            Float o = (Float) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, byte.class);
        try {
            Double o = (Double) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, char.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, char.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals((byte) v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, char.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals(v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, char.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals((short) v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, char.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals((int) v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, char.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals((long) v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, char.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals((float) v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentCToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, char.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals((double) v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentCToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, char.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, char.class);
        try {
            Byte o = (Byte) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, char.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals(Character.valueOf(v), (Character) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, char.class);
        try {
            Short o = (Short) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, char.class);
        try {
            Integer o = (Integer) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, char.class);
        try {
            Long o = (Long) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, char.class);
        try {
            Float o = (Float) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, char.class);
        try {
            Double o = (Double) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentSToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, short.class);
        for (short v : SHORT_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentSToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, short.class);
        for (short v : SHORT_VALUES) {
            assertEquals((byte) v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentSToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, short.class);
        for (short v : SHORT_VALUES) {
            assertEquals((char) v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentSToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, short.class);
        for (short v : SHORT_VALUES) {
            assertEquals(v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentSToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, short.class);
        for (short v : SHORT_VALUES) {
            assertEquals((int) v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentSToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, short.class);
        for (short v : SHORT_VALUES) {
            assertEquals((long) v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentSToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, short.class);
        for (short v : SHORT_VALUES) {
            assertEquals((float) v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentSToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, short.class);
        for (short v : SHORT_VALUES) {
            assertEquals((double) v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentSToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, short.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentSToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, short.class);
        try {
            Byte o = (Byte) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentSToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, short.class);
        try {
            Character o = (Character) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentSToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, short.class);
        for (short v : SHORT_VALUES) {
            assertEquals(Short.valueOf(v), (Short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentSToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, short.class);
        try {
            Integer o = (Integer) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentSToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, short.class);
        try {
            Long o = (Long) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentSToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, short.class);
        try {
            Float o = (Float) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentSToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, short.class);
        try {
            Double o = (Double) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, int.class);
        for (int v : INTEGER_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, int.class);
        for (int v : INTEGER_VALUES) {
            assertEquals((byte) v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, int.class);
        for (int v : INTEGER_VALUES) {
            assertEquals((char) v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, int.class);
        for (int v : INTEGER_VALUES) {
            assertEquals((short) v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, int.class);
        for (int v : INTEGER_VALUES) {
            assertEquals(v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, int.class);
        for (int v : INTEGER_VALUES) {
            assertEquals((long) v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, int.class);
        for (int v : INTEGER_VALUES) {
            assertEquals((float) v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentIToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, int.class);
        for (int v : INTEGER_VALUES) {
            assertEquals((double) v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentIToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, int.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, int.class);
        try {
            Byte o = (Byte) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, int.class);
        try {
            Character o = (Character) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, int.class);
        try {
            Short o = (Short) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, int.class);
        for (int v : INTEGER_VALUES) {
            assertEquals(Integer.valueOf(v), (Integer) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, int.class);
        try {
            Long o = (Long) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, int.class);
        try {
            Float o = (Float) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, int.class);
        try {
            Double o = (Double) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentJToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, long.class);
        for (long v : LONG_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentJToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, long.class);
        for (long v : LONG_VALUES) {
            assertEquals((byte) v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentJToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, long.class);
        for (long v : LONG_VALUES) {
            assertEquals((char) v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentJToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, long.class);
        for (long v : LONG_VALUES) {
            assertEquals((short) v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentJToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, long.class);
        for (long v : LONG_VALUES) {
            assertEquals((int) v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentJToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, long.class);
        for (long v : LONG_VALUES) {
            assertEquals(v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentJToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, long.class);
        for (long v : LONG_VALUES) {
            assertEquals((float) v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentJToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, long.class);
        for (long v : LONG_VALUES) {
            assertEquals((double) v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentJToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, long.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentJToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, long.class);
        try {
            Byte o = (Byte) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentJToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, long.class);
        try {
            Character o = (Character) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentJToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, long.class);
        try {
            Short o = (Short) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentJToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, long.class);
        try {
            Integer o = (Integer) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentJToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, long.class);
        for (long v : LONG_VALUES) {
            assertEquals(Long.valueOf(v), (Long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentJToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, long.class);
        try {
            Float o = (Float) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentJToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, long.class);
        try {
            Double o = (Double) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, float.class);
        for (float v : FLOAT_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentFToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, float.class);
        for (float v : FLOAT_VALUES) {
            assertEquals((byte) v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentFToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, float.class);
        for (float v : FLOAT_VALUES) {
            assertEquals((char) v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentFToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, float.class);
        for (float v : FLOAT_VALUES) {
            assertEquals((short) v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentFToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, float.class);
        for (float v : FLOAT_VALUES) {
            assertEquals((int) v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentFToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, float.class);
        for (float v : FLOAT_VALUES) {
            assertEquals((long) v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentFToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, float.class);
        for (float v : FLOAT_VALUES) {
            assertEquals(v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentFToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, float.class);
        for (float v : FLOAT_VALUES) {
            assertEquals((double) v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentFToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, float.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, float.class);
        try {
            Byte o = (Byte) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, float.class);
        try {
            Character o = (Character) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, float.class);
        try {
            Short o = (Short) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, float.class);
        try {
            Integer o = (Integer) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, float.class);
        try {
            Long o = (Long) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, float.class);
        for (float v : FLOAT_VALUES) {
            assertEquals(Float.valueOf(v), (Float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentFToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, float.class);
        try {
            Double o = (Double) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, double.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentDToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, double.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals((byte) v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentDToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, double.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals((char) v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentDToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, double.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals((short) v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentDToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, double.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals((int) v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentDToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, double.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals((long) v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentDToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, double.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals((float) v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentDToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, double.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals(v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentDToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, double.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, double.class);
        try {
            Byte o = (Byte) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, double.class);
        try {
            Character o = (Character) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, double.class);
        try {
            Short o = (Short) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, double.class);
        try {
            Integer o = (Integer) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, double.class);
        try {
            Long o = (Long) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, double.class);
        try {
            Float o = (Float) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, double.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals(Double.valueOf(v), (Double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentBooleanToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, Boolean.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(v.booleanValue(), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBooleanToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, Boolean.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(byteFromBooleanValue(v.booleanValue()), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBooleanToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, Boolean.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(charFromBooleanValue(v.booleanValue()), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBooleanToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, Boolean.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(shortFromBooleanValue(v.booleanValue()), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBooleanToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, Boolean.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(intFromBooleanValue(v.booleanValue()), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBooleanToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, Boolean.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(longFromBooleanValue(v.booleanValue()), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBooleanToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, Boolean.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(floatFromBooleanValue(v.booleanValue()), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentBooleanToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, Boolean.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(doubleFromBooleanValue(v.booleanValue()), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentBooleanToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, Boolean.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(v, (Boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentBooleanToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, Boolean.class);
        try {
            Byte o = (Byte) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBooleanToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, Boolean.class);
        try {
            Character o = (Character) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBooleanToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, Boolean.class);
        try {
            Short o = (Short) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBooleanToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, Boolean.class);
        try {
            Integer o = (Integer) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBooleanToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, Boolean.class);
        try {
            Long o = (Long) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBooleanToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, Boolean.class);
        try {
            Float o = (Float) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentBooleanToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, Boolean.class);
        try {
            Double o = (Double) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentByteToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, Byte.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals(toBooleanValue(v.byteValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentByteToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, Byte.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals(v.byteValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentByteToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, Byte.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals((char) v.byteValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentByteToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, Byte.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals((short) v.byteValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentByteToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, Byte.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals((int) v.byteValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentByteToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, Byte.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals((long) v.byteValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentByteToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, Byte.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals((float) v.byteValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentByteToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, Byte.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals((double) v.byteValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentByteToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, Byte.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentByteToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, Byte.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals(v, (Byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentByteToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, Byte.class);
        try {
            Character o = (Character) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentByteToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, Byte.class);
        try {
            Short o = (Short) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentByteToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, Byte.class);
        try {
            Integer o = (Integer) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentByteToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, Byte.class);
        try {
            Long o = (Long) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentByteToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, Byte.class);
        try {
            Float o = (Float) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentByteToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, Byte.class);
        try {
            Double o = (Double) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCharacterToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, Character.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals(toBooleanValue(v.charValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCharacterToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, Character.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals((byte) v.charValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCharacterToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, Character.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals(v.charValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCharacterToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, Character.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals((short) v.charValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCharacterToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, Character.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals((int) v.charValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCharacterToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, Character.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals((long) v.charValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCharacterToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, Character.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals((float) v.charValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentCharacterToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, Character.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals((double) v.charValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentCharacterToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, Character.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCharacterToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, Character.class);
        try {
            Byte o = (Byte) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCharacterToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, Character.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals(v, (Character) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentCharacterToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, Character.class);
        try {
            Short o = (Short) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCharacterToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, Character.class);
        try {
            Integer o = (Integer) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCharacterToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, Character.class);
        try {
            Long o = (Long) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCharacterToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, Character.class);
        try {
            Float o = (Float) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentCharacterToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, Character.class);
        try {
            Double o = (Double) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentShortToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, Short.class);
        for (Short v : SHORT_VALUES) {
            assertEquals(toBooleanValue(v.shortValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentShortToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, Short.class);
        for (Short v : SHORT_VALUES) {
            assertEquals((byte) v.shortValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentShortToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, Short.class);
        for (Short v : SHORT_VALUES) {
            assertEquals((char) v.shortValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentShortToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, Short.class);
        for (Short v : SHORT_VALUES) {
            assertEquals(v.shortValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentShortToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, Short.class);
        for (Short v : SHORT_VALUES) {
            assertEquals((int) v.shortValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentShortToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, Short.class);
        for (Short v : SHORT_VALUES) {
            assertEquals((long) v.shortValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentShortToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, Short.class);
        for (Short v : SHORT_VALUES) {
            assertEquals((float) v.shortValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentShortToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, Short.class);
        for (Short v : SHORT_VALUES) {
            assertEquals((double) v.shortValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentShortToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, Short.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentShortToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, Short.class);
        try {
            Byte o = (Byte) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentShortToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, Short.class);
        try {
            Character o = (Character) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentShortToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, Short.class);
        for (Short v : SHORT_VALUES) {
            assertEquals(v, (Short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentShortToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, Short.class);
        try {
            Integer o = (Integer) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentShortToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, Short.class);
        try {
            Long o = (Long) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentShortToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, Short.class);
        try {
            Float o = (Float) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentShortToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, Short.class);
        try {
            Double o = (Double) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIntegerToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, Integer.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals(toBooleanValue(v.intValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIntegerToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, Integer.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals((byte) v.intValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIntegerToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, Integer.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals((char) v.intValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIntegerToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, Integer.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals((short) v.intValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIntegerToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, Integer.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals(v.intValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIntegerToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, Integer.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals((long) v.intValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIntegerToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, Integer.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals((float) v.intValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentIntegerToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, Integer.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals((double) v.intValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentIntegerToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, Integer.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIntegerToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, Integer.class);
        try {
            Byte o = (Byte) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIntegerToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, Integer.class);
        try {
            Character o = (Character) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIntegerToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, Integer.class);
        try {
            Short o = (Short) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIntegerToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, Integer.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals(v, (Integer) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentIntegerToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, Integer.class);
        try {
            Long o = (Long) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIntegerToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, Integer.class);
        try {
            Float o = (Float) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentIntegerToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, Integer.class);
        try {
            Double o = (Double) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentLongToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, Long.class);
        for (Long v : LONG_VALUES) {
            assertEquals(toBooleanValue(v.longValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentLongToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, Long.class);
        for (Long v : LONG_VALUES) {
            assertEquals((byte) v.longValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentLongToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, Long.class);
        for (Long v : LONG_VALUES) {
            assertEquals((char) v.longValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentLongToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, Long.class);
        for (Long v : LONG_VALUES) {
            assertEquals((short) v.longValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentLongToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, Long.class);
        for (Long v : LONG_VALUES) {
            assertEquals((int) v.longValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentLongToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, Long.class);
        for (Long v : LONG_VALUES) {
            assertEquals(v.longValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentLongToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, Long.class);
        for (Long v : LONG_VALUES) {
            assertEquals((float) v.longValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentLongToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, Long.class);
        for (Long v : LONG_VALUES) {
            assertEquals((double) v.longValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentLongToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, Long.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentLongToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, Long.class);
        try {
            Byte o = (Byte) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentLongToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, Long.class);
        try {
            Character o = (Character) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentLongToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, Long.class);
        try {
            Short o = (Short) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentLongToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, Long.class);
        try {
            Integer o = (Integer) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentLongToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, Long.class);
        for (Long v : LONG_VALUES) {
            assertEquals(v, (Long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentLongToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, Long.class);
        try {
            Float o = (Float) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentLongToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, Long.class);
        try {
            Double o = (Double) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFloatToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, Float.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals(toBooleanValue(v.floatValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentFloatToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, Float.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals((byte) v.floatValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentFloatToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, Float.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals((char) v.floatValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentFloatToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, Float.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals((short) v.floatValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentFloatToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, Float.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals((int) v.floatValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentFloatToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, Float.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals((long) v.floatValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentFloatToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, Float.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals(v.floatValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentFloatToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, Float.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals((double) v.floatValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentFloatToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, Float.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFloatToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, Float.class);
        try {
            Byte o = (Byte) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFloatToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, Float.class);
        try {
            Character o = (Character) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFloatToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, Float.class);
        try {
            Short o = (Short) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFloatToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, Float.class);
        try {
            Integer o = (Integer) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFloatToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, Float.class);
        try {
            Long o = (Long) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentFloatToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, Float.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals(v, (Float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentFloatToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, Float.class);
        try {
            Double o = (Double) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDoubleToZ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(boolean.class, Double.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals(toBooleanValue(v.doubleValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentDoubleToB() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(byte.class, Double.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals((byte) v.doubleValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentDoubleToC() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(char.class, Double.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals((char) v.doubleValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentDoubleToS() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(short.class, Double.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals((short) v.doubleValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentDoubleToI() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(int.class, Double.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals((int) v.doubleValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentDoubleToJ() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(long.class, Double.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals((long) v.doubleValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastArgumentDoubleToF() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(float.class, Double.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals((float) v.doubleValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentDoubleToD() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(double.class, Double.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals(v.doubleValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastArgumentDoubleToBoolean() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Boolean.class, Double.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDoubleToByte() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Byte.class, Double.class);
        try {
            Byte o = (Byte) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDoubleToCharacter() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Character.class, Double.class);
        try {
            Character o = (Character) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDoubleToShort() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Short.class, Double.class);
        try {
            Short o = (Short) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDoubleToInteger() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Integer.class, Double.class);
        try {
            Integer o = (Integer) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDoubleToLong() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Long.class, Double.class);
        try {
            Long o = (Long) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDoubleToFloat() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Float.class, Double.class);
        try {
            Float o = (Float) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastArgumentDoubleToDouble() throws Throwable {
        MethodHandle mh = explicitCastArgumentToIdentity(Double.class, Double.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals(v, (Double) mh.invokeExact(v), 0.0);
        }
    }

    //
    // Explicit casting of return values between the primitive types and their
    // reference type counterparts.
    //
    @Test
    public void explicitCastReturnValueZToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, boolean.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(v, (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, boolean.class);
        for (byte v : BYTE_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, boolean.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueSToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, boolean.class);
        for (short v : SHORT_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, boolean.class);
        for (int v : INTEGER_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueJToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, boolean.class);
        for (long v : LONG_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, boolean.class);
        for (float v : FLOAT_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueDToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, boolean.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals(toBooleanValue(v), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBooleanToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, boolean.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(v.booleanValue(), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueByteToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, boolean.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals(toBooleanValue(v.byteValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCharacterToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, boolean.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals(toBooleanValue(v.charValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueShortToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, boolean.class);
        for (Short v : SHORT_VALUES) {
            assertEquals(toBooleanValue(v.shortValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIntegerToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, boolean.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals(toBooleanValue(v.intValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueLongToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, boolean.class);
        for (Long v : LONG_VALUES) {
            assertEquals(toBooleanValue(v.longValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFloatToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, boolean.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals(toBooleanValue(v.floatValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueDoubleToZ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, boolean.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals(toBooleanValue(v.doubleValue()), (boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueZToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, byte.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(byteFromBooleanValue(v), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, byte.class);
        for (byte v : BYTE_VALUES) {
            assertEquals(v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, byte.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals((byte) v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueSToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, byte.class);
        for (short v : SHORT_VALUES) {
            assertEquals((byte) v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, byte.class);
        for (int v : INTEGER_VALUES) {
            assertEquals((byte) v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueJToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, byte.class);
        for (long v : LONG_VALUES) {
            assertEquals((byte) v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, byte.class);
        for (float v : FLOAT_VALUES) {
            assertEquals((byte) v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueDToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, byte.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals((byte) v, (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBooleanToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, byte.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(byteFromBooleanValue(v.booleanValue()), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueByteToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, byte.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals(v.byteValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCharacterToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, byte.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals((byte) v.charValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueShortToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, byte.class);
        for (Short v : SHORT_VALUES) {
            assertEquals((byte) v.shortValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIntegerToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, byte.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals((byte) v.intValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueLongToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, byte.class);
        for (Long v : LONG_VALUES) {
            assertEquals((byte) v.longValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFloatToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, byte.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals((byte) v.floatValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueDoubleToB() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, byte.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals((byte) v.doubleValue(), (byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueZToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, char.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(charFromBooleanValue(v), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, char.class);
        for (byte v : BYTE_VALUES) {
            assertEquals((char) v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, char.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals(v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueSToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, char.class);
        for (short v : SHORT_VALUES) {
            assertEquals((char) v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, char.class);
        for (int v : INTEGER_VALUES) {
            assertEquals((char) v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueJToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, char.class);
        for (long v : LONG_VALUES) {
            assertEquals((char) v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, char.class);
        for (float v : FLOAT_VALUES) {
            assertEquals((char) v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueDToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, char.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals((char) v, (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBooleanToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, char.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(charFromBooleanValue(v.booleanValue()), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueByteToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, char.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals((char) v.byteValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCharacterToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, char.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals(v.charValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueShortToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, char.class);
        for (Short v : SHORT_VALUES) {
            assertEquals((char) v.shortValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIntegerToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, char.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals((char) v.intValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueLongToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, char.class);
        for (Long v : LONG_VALUES) {
            assertEquals((char) v.longValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFloatToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, char.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals((char) v.floatValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueDoubleToC() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, char.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals((char) v.doubleValue(), (char) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueZToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, short.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(shortFromBooleanValue(v), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, short.class);
        for (byte v : BYTE_VALUES) {
            assertEquals((short) v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, short.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals((short) v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueSToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, short.class);
        for (short v : SHORT_VALUES) {
            assertEquals(v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, short.class);
        for (int v : INTEGER_VALUES) {
            assertEquals((short) v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueJToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, short.class);
        for (long v : LONG_VALUES) {
            assertEquals((short) v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, short.class);
        for (float v : FLOAT_VALUES) {
            assertEquals((short) v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueDToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, short.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals((short) v, (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBooleanToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, short.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(shortFromBooleanValue(v.booleanValue()), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueByteToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, short.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals((short) v.byteValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCharacterToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, short.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals((short) v.charValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueShortToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, short.class);
        for (Short v : SHORT_VALUES) {
            assertEquals(v.shortValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIntegerToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, short.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals((short) v.intValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueLongToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, short.class);
        for (Long v : LONG_VALUES) {
            assertEquals((short) v.longValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFloatToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, short.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals((short) v.floatValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueDoubleToS() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, short.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals((short) v.doubleValue(), (short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueZToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, int.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(intFromBooleanValue(v), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, int.class);
        for (byte v : BYTE_VALUES) {
            assertEquals((int) v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, int.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals((int) v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueSToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, int.class);
        for (short v : SHORT_VALUES) {
            assertEquals((int) v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, int.class);
        for (int v : INTEGER_VALUES) {
            assertEquals(v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueJToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, int.class);
        for (long v : LONG_VALUES) {
            assertEquals((int) v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, int.class);
        for (float v : FLOAT_VALUES) {
            assertEquals((int) v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueDToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, int.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals((int) v, (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBooleanToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, int.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(intFromBooleanValue(v.booleanValue()), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueByteToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, int.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals((int) v.byteValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCharacterToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, int.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals((int) v.charValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueShortToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, int.class);
        for (Short v : SHORT_VALUES) {
            assertEquals((int) v.shortValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIntegerToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, int.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals(v.intValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueLongToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, int.class);
        for (Long v : LONG_VALUES) {
            assertEquals((int) v.longValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFloatToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, int.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals((int) v.floatValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueDoubleToI() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, int.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals((int) v.doubleValue(), (int) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueZToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, long.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(longFromBooleanValue(v), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, long.class);
        for (byte v : BYTE_VALUES) {
            assertEquals((long) v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, long.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals((long) v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueSToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, long.class);
        for (short v : SHORT_VALUES) {
            assertEquals((long) v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, long.class);
        for (int v : INTEGER_VALUES) {
            assertEquals((long) v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueJToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, long.class);
        for (long v : LONG_VALUES) {
            assertEquals(v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, long.class);
        for (float v : FLOAT_VALUES) {
            assertEquals((long) v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueDToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, long.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals((long) v, (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBooleanToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, long.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(longFromBooleanValue(v.booleanValue()), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueByteToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, long.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals((long) v.byteValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCharacterToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, long.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals((long) v.charValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueShortToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, long.class);
        for (Short v : SHORT_VALUES) {
            assertEquals((long) v.shortValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIntegerToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, long.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals((long) v.intValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueLongToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, long.class);
        for (Long v : LONG_VALUES) {
            assertEquals(v.longValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFloatToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, long.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals((long) v.floatValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueDoubleToJ() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, long.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals((long) v.doubleValue(), (long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueZToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, float.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(floatFromBooleanValue(v), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueBToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, float.class);
        for (byte v : BYTE_VALUES) {
            assertEquals((float) v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueCToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, float.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals((float) v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueSToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, float.class);
        for (short v : SHORT_VALUES) {
            assertEquals((float) v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueIToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, float.class);
        for (int v : INTEGER_VALUES) {
            assertEquals((float) v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueJToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, float.class);
        for (long v : LONG_VALUES) {
            assertEquals((float) v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueFToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, float.class);
        for (float v : FLOAT_VALUES) {
            assertEquals(v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueDToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, float.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals((float) v, (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueBooleanToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, float.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(floatFromBooleanValue(v.booleanValue()), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueByteToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, float.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals((float) v.byteValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueCharacterToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, float.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals((float) v.charValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueShortToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, float.class);
        for (Short v : SHORT_VALUES) {
            assertEquals((float) v.shortValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueIntegerToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, float.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals((float) v.intValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueLongToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, float.class);
        for (Long v : LONG_VALUES) {
            assertEquals((float) v.longValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueFloatToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, float.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals(v.floatValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueDoubleToF() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, float.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals((float) v.doubleValue(), (float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueZToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, double.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(doubleFromBooleanValue(v), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueBToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, double.class);
        for (byte v : BYTE_VALUES) {
            assertEquals((double) v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueCToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, double.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals((double) v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueSToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, double.class);
        for (short v : SHORT_VALUES) {
            assertEquals((double) v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueIToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, double.class);
        for (int v : INTEGER_VALUES) {
            assertEquals((double) v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueJToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, double.class);
        for (long v : LONG_VALUES) {
            assertEquals((double) v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueFToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, double.class);
        for (float v : FLOAT_VALUES) {
            assertEquals((double) v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueDToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, double.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals(v, (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueBooleanToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, double.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(doubleFromBooleanValue(v.booleanValue()), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueByteToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, double.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals((double) v.byteValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueCharacterToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, double.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals((double) v.charValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueShortToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, double.class);
        for (Short v : SHORT_VALUES) {
            assertEquals((double) v.shortValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueIntegerToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, double.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals((double) v.intValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueLongToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, double.class);
        for (Long v : LONG_VALUES) {
            assertEquals((double) v.longValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueFloatToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, double.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals((double) v.floatValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueDoubleToD() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, double.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals(v.doubleValue(), (double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueZToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, Boolean.class);
        for (boolean v : BOOLEAN_VALUES) {
            assertEquals(Boolean.valueOf(v), (Boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueBToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueSToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueJToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBooleanToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, Boolean.class);
        for (Boolean v : BOOLEAN_VALUES) {
            assertEquals(v, (Boolean) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueByteToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCharacterToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueShortToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIntegerToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueLongToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFloatToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDoubleToBoolean() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, Boolean.class);
        try {
            Boolean o = (Boolean) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueZToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, Byte.class);
        for (byte v : BYTE_VALUES) {
            assertEquals(Byte.valueOf(v), (Byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueSToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueJToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBooleanToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueByteToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, Byte.class);
        for (Byte v : BYTE_VALUES) {
            assertEquals(v, (Byte) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueCharacterToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueShortToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIntegerToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueLongToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFloatToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDoubleToByte() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, Byte.class);
        try {
            Byte o = (Byte) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueZToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, Character.class);
        for (char v : CHARACTER_VALUES) {
            assertEquals(Character.valueOf(v), (Character) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueSToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueJToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBooleanToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueByteToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCharacterToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, Character.class);
        for (Character v : CHARACTER_VALUES) {
            assertEquals(v, (Character) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueShortToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIntegerToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueLongToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFloatToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDoubleToCharacter() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, Character.class);
        try {
            Character o = (Character) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueZToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueSToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, Short.class);
        for (short v : SHORT_VALUES) {
            assertEquals(Short.valueOf(v), (Short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueJToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBooleanToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueByteToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCharacterToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueShortToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, Short.class);
        for (Short v : SHORT_VALUES) {
            assertEquals(v, (Short) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueIntegerToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueLongToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFloatToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDoubleToShort() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, Short.class);
        try {
            Short o = (Short) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueZToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueSToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, Integer.class);
        for (int v : INTEGER_VALUES) {
            assertEquals(Integer.valueOf(v), (Integer) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueJToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBooleanToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueByteToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCharacterToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueShortToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIntegerToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, Integer.class);
        for (Integer v : INTEGER_VALUES) {
            assertEquals(v, (Integer) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueLongToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFloatToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDoubleToInteger() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, Integer.class);
        try {
            Integer o = (Integer) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueZToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueSToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueJToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, Long.class);
        for (long v : LONG_VALUES) {
            assertEquals(Long.valueOf(v), (Long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBooleanToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueByteToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCharacterToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueShortToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIntegerToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueLongToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, Long.class);
        for (Long v : LONG_VALUES) {
            assertEquals(v, (Long) mh.invokeExact(v));
        }
    }

    @Test
    public void explicitCastReturnValueFloatToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDoubleToLong() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, Long.class);
        try {
            Long o = (Long) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueZToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueSToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueJToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, Float.class);
        for (float v : FLOAT_VALUES) {
            assertEquals(Float.valueOf(v), (Float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueDToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(DOUBLE_VALUES[0].doubleValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBooleanToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueByteToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCharacterToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueShortToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIntegerToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueLongToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFloatToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, Float.class);
        for (Float v : FLOAT_VALUES) {
            assertEquals(v, (Float) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueDoubleToFloat() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, Float.class);
        try {
            Float o = (Float) mh.invokeExact(DOUBLE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueZToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(boolean.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(BOOLEAN_VALUES[0].booleanValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueBToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(byte.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(BYTE_VALUES[0].byteValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(char.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(CHARACTER_VALUES[0].charValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueSToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(short.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(SHORT_VALUES[0].shortValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(int.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(INTEGER_VALUES[0].intValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueJToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(long.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(LONG_VALUES[0].longValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(float.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(FLOAT_VALUES[0].floatValue());
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(double.class, Double.class);
        for (double v : DOUBLE_VALUES) {
            assertEquals(Double.valueOf(v), (Double) mh.invokeExact(v), 0.0);
        }
    }

    @Test
    public void explicitCastReturnValueBooleanToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Boolean.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(BOOLEAN_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueByteToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Byte.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(BYTE_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueCharacterToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Character.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(CHARACTER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueShortToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Short.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(SHORT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueIntegerToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Integer.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(INTEGER_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueLongToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Long.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(LONG_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueFloatToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Float.class, Double.class);
        try {
            Double o = (Double) mh.invokeExact(FLOAT_VALUES[0]);
            fail("Expected CCE");
        } catch (ClassCastException expected) {}
    }

    @Test
    public void explicitCastReturnValueDoubleToDouble() throws Throwable {
        MethodHandle mh = explicitCastReturnValueFromIdentity(Double.class, Double.class);
        for (Double v : DOUBLE_VALUES) {
            assertEquals(v, (Double) mh.invokeExact(v), 0.0);
        }
    }

    //
    // Explicit casting of null valued arguments to primitive type values
    // (zero/false).
    //
    @Test
    public void nullBooleanArgumentToZ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(boolean.class, Boolean.class);
        assertEquals(false, (boolean) m.invokeExact((Boolean) null));
    }

    @Test
    public void nullBooleanArgumentToB() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(byte.class, Boolean.class);
        assertEquals((byte) 0, (byte) m.invokeExact((Boolean) null));
    }

    @Test
    public void nullBooleanArgumentToC() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(char.class, Boolean.class);
        assertEquals((char) 0, (char) m.invokeExact((Boolean) null));
    }

    @Test
    public void nullBooleanArgumentToS() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(short.class, Boolean.class);
        assertEquals((short) 0, (short) m.invokeExact((Boolean) null));
    }

    @Test
    public void nullBooleanArgumentToI() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(int.class, Boolean.class);
        assertEquals((int) 0, (int) m.invokeExact((Boolean) null));
    }

    @Test
    public void nullBooleanArgumentToJ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(long.class, Boolean.class);
        assertEquals((long) 0, (long) m.invokeExact((Boolean) null));
    }

    @Test
    public void nullBooleanArgumentToF() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(float.class, Boolean.class);
        assertEquals((float) 0, (float) m.invokeExact((Boolean) null), 0.0);
    }

    @Test
    public void nullBooleanArgumentToD() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(double.class, Boolean.class);
        assertEquals((double) 0, (double) m.invokeExact((Boolean) null), 0.0);
    }

    @Test
    public void nullByteArgumentToZ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(boolean.class, Byte.class);
        assertEquals(false, (boolean) m.invokeExact((Byte) null));
    }

    @Test
    public void nullByteArgumentToB() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(byte.class, Byte.class);
        assertEquals((byte) 0, (byte) m.invokeExact((Byte) null));
    }

    @Test
    public void nullByteArgumentToC() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(char.class, Byte.class);
        assertEquals((char) 0, (char) m.invokeExact((Byte) null));
    }

    @Test
    public void nullByteArgumentToS() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(short.class, Byte.class);
        assertEquals((short) 0, (short) m.invokeExact((Byte) null));
    }

    @Test
    public void nullByteArgumentToI() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(int.class, Byte.class);
        assertEquals((int) 0, (int) m.invokeExact((Byte) null));
    }

    @Test
    public void nullByteArgumentToJ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(long.class, Byte.class);
        assertEquals((long) 0, (long) m.invokeExact((Byte) null));
    }

    @Test
    public void nullByteArgumentToF() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(float.class, Byte.class);
        assertEquals((float) 0, (float) m.invokeExact((Byte) null), 0.0);
    }

    @Test
    public void nullByteArgumentToD() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(double.class, Byte.class);
        assertEquals((double) 0, (double) m.invokeExact((Byte) null), 0.0);
    }

    @Test
    public void nullCharacterArgumentToZ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(boolean.class, Character.class);
        assertEquals(false, (boolean) m.invokeExact((Character) null));
    }

    @Test
    public void nullCharacterArgumentToB() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(byte.class, Character.class);
        assertEquals((byte) 0, (byte) m.invokeExact((Character) null));
    }

    @Test
    public void nullCharacterArgumentToC() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(char.class, Character.class);
        assertEquals((char) 0, (char) m.invokeExact((Character) null));
    }

    @Test
    public void nullCharacterArgumentToS() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(short.class, Character.class);
        assertEquals((short) 0, (short) m.invokeExact((Character) null));
    }

    @Test
    public void nullCharacterArgumentToI() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(int.class, Character.class);
        assertEquals((int) 0, (int) m.invokeExact((Character) null));
    }

    @Test
    public void nullCharacterArgumentToJ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(long.class, Character.class);
        assertEquals((long) 0, (long) m.invokeExact((Character) null));
    }

    @Test
    public void nullCharacterArgumentToF() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(float.class, Character.class);
        assertEquals((float) 0, (float) m.invokeExact((Character) null), 0.0);
    }

    @Test
    public void nullCharacterArgumentToD() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(double.class, Character.class);
        assertEquals((double) 0, (double) m.invokeExact((Character) null), 0.0);
    }

    @Test
    public void nullShortArgumentToZ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(boolean.class, Short.class);
        assertEquals(false, (boolean) m.invokeExact((Short) null));
    }

    @Test
    public void nullShortArgumentToB() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(byte.class, Short.class);
        assertEquals((byte) 0, (byte) m.invokeExact((Short) null));
    }

    @Test
    public void nullShortArgumentToC() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(char.class, Short.class);
        assertEquals((char) 0, (char) m.invokeExact((Short) null));
    }

    @Test
    public void nullShortArgumentToS() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(short.class, Short.class);
        assertEquals((short) 0, (short) m.invokeExact((Short) null));
    }

    @Test
    public void nullShortArgumentToI() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(int.class, Short.class);
        assertEquals((int) 0, (int) m.invokeExact((Short) null));
    }

    @Test
    public void nullShortArgumentToJ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(long.class, Short.class);
        assertEquals((long) 0, (long) m.invokeExact((Short) null));
    }

    @Test
    public void nullShortArgumentToF() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(float.class, Short.class);
        assertEquals((float) 0, (float) m.invokeExact((Short) null), 0.0);
    }

    @Test
    public void nullShortArgumentToD() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(double.class, Short.class);
        assertEquals((double) 0, (double) m.invokeExact((Short) null), 0.0);
    }

    @Test
    public void nullIntegerArgumentToZ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(boolean.class, Integer.class);
        assertEquals(false, (boolean) m.invokeExact((Integer) null));
    }

    @Test
    public void nullIntegerArgumentToB() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(byte.class, Integer.class);
        assertEquals((byte) 0, (byte) m.invokeExact((Integer) null));
    }

    @Test
    public void nullIntegerArgumentToC() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(char.class, Integer.class);
        assertEquals((char) 0, (char) m.invokeExact((Integer) null));
    }

    @Test
    public void nullIntegerArgumentToS() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(short.class, Integer.class);
        assertEquals((short) 0, (short) m.invokeExact((Integer) null));
    }

    @Test
    public void nullIntegerArgumentToI() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(int.class, Integer.class);
        assertEquals((int) 0, (int) m.invokeExact((Integer) null));
    }

    @Test
    public void nullIntegerArgumentToJ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(long.class, Integer.class);
        assertEquals((long) 0, (long) m.invokeExact((Integer) null));
    }

    @Test
    public void nullIntegerArgumentToF() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(float.class, Integer.class);
        assertEquals((float) 0, (float) m.invokeExact((Integer) null), 0.0);
    }

    @Test
    public void nullIntegerArgumentToD() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(double.class, Integer.class);
        assertEquals((double) 0, (double) m.invokeExact((Integer) null), 0.0);
    }

    @Test
    public void nullLongArgumentToZ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(boolean.class, Long.class);
        assertEquals(false, (boolean) m.invokeExact((Long) null));
    }

    @Test
    public void nullLongArgumentToB() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(byte.class, Long.class);
        assertEquals((byte) 0, (byte) m.invokeExact((Long) null));
    }

    @Test
    public void nullLongArgumentToC() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(char.class, Long.class);
        assertEquals((char) 0, (char) m.invokeExact((Long) null));
    }

    @Test
    public void nullLongArgumentToS() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(short.class, Long.class);
        assertEquals((short) 0, (short) m.invokeExact((Long) null));
    }

    @Test
    public void nullLongArgumentToI() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(int.class, Long.class);
        assertEquals((int) 0, (int) m.invokeExact((Long) null));
    }

    @Test
    public void nullLongArgumentToJ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(long.class, Long.class);
        assertEquals((long) 0, (long) m.invokeExact((Long) null));
    }

    @Test
    public void nullLongArgumentToF() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(float.class, Long.class);
        assertEquals((float) 0, (float) m.invokeExact((Long) null), 0.0);
    }

    @Test
    public void nullLongArgumentToD() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(double.class, Long.class);
        assertEquals((double) 0, (double) m.invokeExact((Long) null), 0.0);
    }

    @Test
    public void nullFloatArgumentToZ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(boolean.class, Float.class);
        assertEquals(false, (boolean) m.invokeExact((Float) null));
    }

    @Test
    public void nullFloatArgumentToB() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(byte.class, Float.class);
        assertEquals((byte) 0, (byte) m.invokeExact((Float) null));
    }

    @Test
    public void nullFloatArgumentToC() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(char.class, Float.class);
        assertEquals((char) 0, (char) m.invokeExact((Float) null));
    }

    @Test
    public void nullFloatArgumentToS() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(short.class, Float.class);
        assertEquals((short) 0, (short) m.invokeExact((Float) null));
    }

    @Test
    public void nullFloatArgumentToI() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(int.class, Float.class);
        assertEquals((int) 0, (int) m.invokeExact((Float) null));
    }

    @Test
    public void nullFloatArgumentToJ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(long.class, Float.class);
        assertEquals((long) 0, (long) m.invokeExact((Float) null));
    }

    @Test
    public void nullFloatArgumentToF() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(float.class, Float.class);
        assertEquals((float) 0, (float) m.invokeExact((Float) null), 0.0);
    }

    @Test
    public void nullFloatArgumentToD() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(double.class, Float.class);
        assertEquals((double) 0, (double) m.invokeExact((Float) null), 0.0);
    }

    @Test
    public void nullDoubleArgumentToZ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(boolean.class, Double.class);
        assertEquals(false, (boolean) m.invokeExact((Double) null));
    }

    @Test
    public void nullDoubleArgumentToB() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(byte.class, Double.class);
        assertEquals((byte) 0, (byte) m.invokeExact((Double) null));
    }

    @Test
    public void nullDoubleArgumentToC() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(char.class, Double.class);
        assertEquals((char) 0, (char) m.invokeExact((Double) null));
    }

    @Test
    public void nullDoubleArgumentToS() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(short.class, Double.class);
        assertEquals((short) 0, (short) m.invokeExact((Double) null));
    }

    @Test
    public void nullDoubleArgumentToI() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(int.class, Double.class);
        assertEquals((int) 0, (int) m.invokeExact((Double) null));
    }

    @Test
    public void nullDoubleArgumentToJ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(long.class, Double.class);
        assertEquals((long) 0, (long) m.invokeExact((Double) null));
    }

    @Test
    public void nullDoubleArgumentToF() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(float.class, Double.class);
        assertEquals((float) 0, (float) m.invokeExact((Double) null), 0.0);
    }

    @Test
    public void nullDoubleArgumentToD() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(double.class, Double.class);
        assertEquals((double) 0, (double) m.invokeExact((Double) null), 0.0);
    }

    @Test
    public void nullObjectArgumentToZ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(boolean.class, Object.class);
        assertEquals(false, (boolean) m.invokeExact((Object) null));
    }

    @Test
    public void nullObjectArgumentToB() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(byte.class, Object.class);
        assertEquals((byte) 0, (byte) m.invokeExact((Object) null));
    }

    @Test
    public void nullObjectArgumentToC() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(char.class, Object.class);
        assertEquals((char) 0, (char) m.invokeExact((Object) null));
    }

    @Test
    public void nullObjectArgumentToS() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(short.class, Object.class);
        assertEquals((short) 0, (short) m.invokeExact((Object) null));
    }

    @Test
    public void nullObjectArgumentToI() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(int.class, Object.class);
        assertEquals((int) 0, (int) m.invokeExact((Object) null));
    }

    @Test
    public void nullObjectArgumentToJ() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(long.class, Object.class);
        assertEquals((long) 0, (long) m.invokeExact((Object) null));
    }

    @Test
    public void nullObjectArgumentToF() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(float.class, Object.class);
        assertEquals((float) 0, (float) m.invokeExact((Object) null), 0.0);
    }

    @Test
    public void nullObjectArgumentToD() throws Throwable {
        MethodHandle m = explicitCastArgumentToIdentity(double.class, Object.class);
        assertEquals((double) 0, (double) m.invokeExact((Object) null), 0.0);
    }

    //
    // Explicit casting of null valued return values to primitive type values
    // (zero/false).
    //
    @Test
    public void returnValueNullBooleanToZ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Boolean.class, boolean.class);
        assertEquals(false, (boolean) m.invokeExact());
    }

    @Test
    public void returnValueNullBooleanToB() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Boolean.class, byte.class);
        assertEquals((byte) 0, (byte) m.invokeExact());
    }

    @Test
    public void returnValueNullBooleanToC() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Boolean.class, char.class);
        assertEquals((char) 0, (char) m.invokeExact());
    }

    @Test
    public void returnValueNullBooleanToS() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Boolean.class, short.class);
        assertEquals((short) 0, (short) m.invokeExact());
    }

    @Test
    public void returnValueNullBooleanToI() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Boolean.class, int.class);
        assertEquals((int) 0, (int) m.invokeExact());
    }

    @Test
    public void returnValueNullBooleanToJ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Boolean.class, long.class);
        assertEquals((long) 0, (long) m.invokeExact());
    }

    @Test
    public void returnValueNullBooleanToF() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Boolean.class, float.class);
        assertEquals((float) 0, (float) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullBooleanToD() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Boolean.class, double.class);
        assertEquals((double) 0, (double) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullByteToZ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Byte.class, boolean.class);
        assertEquals(false, (boolean) m.invokeExact());
    }

    @Test
    public void returnValueNullByteToB() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Byte.class, byte.class);
        assertEquals((byte) 0, (byte) m.invokeExact());
    }

    @Test
    public void returnValueNullByteToC() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Byte.class, char.class);
        assertEquals((char) 0, (char) m.invokeExact());
    }

    @Test
    public void returnValueNullByteToS() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Byte.class, short.class);
        assertEquals((short) 0, (short) m.invokeExact());
    }

    @Test
    public void returnValueNullByteToI() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Byte.class, int.class);
        assertEquals((int) 0, (int) m.invokeExact());
    }

    @Test
    public void returnValueNullByteToJ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Byte.class, long.class);
        assertEquals((long) 0, (long) m.invokeExact());
    }

    @Test
    public void returnValueNullByteToF() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Byte.class, float.class);
        assertEquals((float) 0, (float) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullByteToD() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Byte.class, double.class);
        assertEquals((double) 0, (double) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullCharacterToZ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Character.class, boolean.class);
        assertEquals(false, (boolean) m.invokeExact());
    }

    @Test
    public void returnValueNullCharacterToB() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Character.class, byte.class);
        assertEquals((byte) 0, (byte) m.invokeExact());
    }

    @Test
    public void returnValueNullCharacterToC() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Character.class, char.class);
        assertEquals((char) 0, (char) m.invokeExact());
    }

    @Test
    public void returnValueNullCharacterToS() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Character.class, short.class);
        assertEquals((short) 0, (short) m.invokeExact());
    }

    @Test
    public void returnValueNullCharacterToI() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Character.class, int.class);
        assertEquals((int) 0, (int) m.invokeExact());
    }

    @Test
    public void returnValueNullCharacterToJ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Character.class, long.class);
        assertEquals((long) 0, (long) m.invokeExact());
    }

    @Test
    public void returnValueNullCharacterToF() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Character.class, float.class);
        assertEquals((float) 0, (float) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullCharacterToD() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Character.class, double.class);
        assertEquals((double) 0, (double) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullShortToZ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Short.class, boolean.class);
        assertEquals(false, (boolean) m.invokeExact());
    }

    @Test
    public void returnValueNullShortToB() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Short.class, byte.class);
        assertEquals((byte) 0, (byte) m.invokeExact());
    }

    @Test
    public void returnValueNullShortToC() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Short.class, char.class);
        assertEquals((char) 0, (char) m.invokeExact());
    }

    @Test
    public void returnValueNullShortToS() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Short.class, short.class);
        assertEquals((short) 0, (short) m.invokeExact());
    }

    @Test
    public void returnValueNullShortToI() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Short.class, int.class);
        assertEquals((int) 0, (int) m.invokeExact());
    }

    @Test
    public void returnValueNullShortToJ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Short.class, long.class);
        assertEquals((long) 0, (long) m.invokeExact());
    }

    @Test
    public void returnValueNullShortToF() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Short.class, float.class);
        assertEquals((float) 0, (float) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullShortToD() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Short.class, double.class);
        assertEquals((double) 0, (double) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullIntegerToZ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Integer.class, boolean.class);
        assertEquals(false, (boolean) m.invokeExact());
    }

    @Test
    public void returnValueNullIntegerToB() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Integer.class, byte.class);
        assertEquals((byte) 0, (byte) m.invokeExact());
    }

    @Test
    public void returnValueNullIntegerToC() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Integer.class, char.class);
        assertEquals((char) 0, (char) m.invokeExact());
    }

    @Test
    public void returnValueNullIntegerToS() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Integer.class, short.class);
        assertEquals((short) 0, (short) m.invokeExact());
    }

    @Test
    public void returnValueNullIntegerToI() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Integer.class, int.class);
        assertEquals((int) 0, (int) m.invokeExact());
    }

    @Test
    public void returnValueNullIntegerToJ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Integer.class, long.class);
        assertEquals((long) 0, (long) m.invokeExact());
    }

    @Test
    public void returnValueNullIntegerToF() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Integer.class, float.class);
        assertEquals((float) 0, (float) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullIntegerToD() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Integer.class, double.class);
        assertEquals((double) 0, (double) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullLongToZ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Long.class, boolean.class);
        assertEquals(false, (boolean) m.invokeExact());
    }

    @Test
    public void returnValueNullLongToB() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Long.class, byte.class);
        assertEquals((byte) 0, (byte) m.invokeExact());
    }

    @Test
    public void returnValueNullLongToC() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Long.class, char.class);
        assertEquals((char) 0, (char) m.invokeExact());
    }

    @Test
    public void returnValueNullLongToS() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Long.class, short.class);
        assertEquals((short) 0, (short) m.invokeExact());
    }

    @Test
    public void returnValueNullLongToI() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Long.class, int.class);
        assertEquals((int) 0, (int) m.invokeExact());
    }

    @Test
    public void returnValueNullLongToJ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Long.class, long.class);
        assertEquals((long) 0, (long) m.invokeExact());
    }

    @Test
    public void returnValueNullLongToF() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Long.class, float.class);
        assertEquals((float) 0, (float) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullLongToD() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Long.class, double.class);
        assertEquals((double) 0, (double) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullFloatToZ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Float.class, boolean.class);
        assertEquals(false, (boolean) m.invokeExact());
    }

    @Test
    public void returnValueNullFloatToB() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Float.class, byte.class);
        assertEquals((byte) 0, (byte) m.invokeExact());
    }

    @Test
    public void returnValueNullFloatToC() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Float.class, char.class);
        assertEquals((char) 0, (char) m.invokeExact());
    }

    @Test
    public void returnValueNullFloatToS() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Float.class, short.class);
        assertEquals((short) 0, (short) m.invokeExact());
    }

    @Test
    public void returnValueNullFloatToI() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Float.class, int.class);
        assertEquals((int) 0, (int) m.invokeExact());
    }

    @Test
    public void returnValueNullFloatToJ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Float.class, long.class);
        assertEquals((long) 0, (long) m.invokeExact());
    }

    @Test
    public void returnValueNullFloatToF() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Float.class, float.class);
        assertEquals((float) 0, (float) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullFloatToD() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Float.class, double.class);
        assertEquals((double) 0, (double) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullDoubleToZ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Double.class, boolean.class);
        assertEquals(false, (boolean) m.invokeExact());
    }

    @Test
    public void returnValueNullDoubleToB() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Double.class, byte.class);
        assertEquals((byte) 0, (byte) m.invokeExact());
    }

    @Test
    public void returnValueNullDoubleToC() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Double.class, char.class);
        assertEquals((char) 0, (char) m.invokeExact());
    }

    @Test
    public void returnValueNullDoubleToS() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Double.class, short.class);
        assertEquals((short) 0, (short) m.invokeExact());
    }

    @Test
    public void returnValueNullDoubleToI() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Double.class, int.class);
        assertEquals((int) 0, (int) m.invokeExact());
    }

    @Test
    public void returnValueNullDoubleToJ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Double.class, long.class);
        assertEquals((long) 0, (long) m.invokeExact());
    }

    @Test
    public void returnValueNullDoubleToF() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Double.class, float.class);
        assertEquals((float) 0, (float) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullDoubleToD() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Double.class, double.class);
        assertEquals((double) 0, (double) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullObjectToZ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Object.class, boolean.class);
        assertEquals(false, (boolean) m.invokeExact());
    }

    @Test
    public void returnValueNullObjectToB() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Object.class, byte.class);
        assertEquals((byte) 0, (byte) m.invokeExact());
    }

    @Test
    public void returnValueNullObjectToC() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Object.class, char.class);
        assertEquals((char) 0, (char) m.invokeExact());
    }

    @Test
    public void returnValueNullObjectToS() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Object.class, short.class);
        assertEquals((short) 0, (short) m.invokeExact());
    }

    @Test
    public void returnValueNullObjectToI() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Object.class, int.class);
        assertEquals((int) 0, (int) m.invokeExact());
    }

    @Test
    public void returnValueNullObjectToJ() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Object.class, long.class);
        assertEquals((long) 0, (long) m.invokeExact());
    }

    @Test
    public void returnValueNullObjectToF() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Object.class, float.class);
        assertEquals((float) 0, (float) m.invokeExact(), 0.0);
    }

    @Test
    public void returnValueNullObjectToD() throws Throwable
    {
        MethodHandle m = nullConstantExplicitCastToPrimitive(Object.class, double.class);
        assertEquals((double) 0, (double) m.invokeExact(), 0.0);
    }

    //
    // Check asType() behaviour of void return value cast to primitives yields
    // zero/false/null.
    //
    @Test
    public void testVoidReturnToZ() throws Throwable {
        assertFalse((boolean) explicitCastVoidReturnValue(boolean.class).invokeExact());
    }

    @Test
    public void testVoidReturnToB() throws Throwable {
        assertEquals((byte) 0, (byte) explicitCastVoidReturnValue(byte.class).invokeExact());
    }

    @Test
    public void testVoidReturnToC() throws Throwable {
        assertEquals((char) 0, (char) explicitCastVoidReturnValue(char.class).invokeExact());
    }

    @Test
    public void testVoidReturnToS() throws Throwable {
        assertEquals((short) 0, (short) explicitCastVoidReturnValue(short.class).invokeExact());
    }

    @Test
    public void testVoidReturnToI() throws Throwable {
        assertEquals((int) 0, (int) explicitCastVoidReturnValue(int.class).invokeExact());
    }

    @Test
    public void testVoidReturnToJ() throws Throwable {
        assertEquals((long) 0, (long) explicitCastVoidReturnValue(long.class).invokeExact());
    }

    @Test
    public void testVoidReturnToF() throws Throwable {
        assertEquals((float) 0, (float) explicitCastVoidReturnValue(float.class).invokeExact(), 0.0);
    }

    @Test
    public void testVoidReturnToD() throws Throwable {
        assertEquals((double) 0, (double) explicitCastVoidReturnValue(double.class).invokeExact(), 0.0);
    }

    @Test
    public void testVoidReturnToBoolean() throws Throwable {
        assertNull((Boolean) explicitCastVoidReturnValue(Boolean.class).invokeExact());
    }

    @Test
    public void testVoidReturnToByte() throws Throwable {
        assertNull((Byte) explicitCastVoidReturnValue(Byte.class).invokeExact());
    }

    @Test
    public void testVoidReturnToCharacter() throws Throwable {
        assertNull((Character) explicitCastVoidReturnValue(Character.class).invokeExact());
    }

    @Test
    public void testVoidReturnToShort() throws Throwable {
        assertNull((Short) explicitCastVoidReturnValue(Short.class).invokeExact());
    }

    @Test
    public void testVoidReturnToInteger() throws Throwable {
        assertNull((Integer) explicitCastVoidReturnValue(Integer.class).invokeExact());
    }

    @Test
    public void testVoidReturnToLong() throws Throwable {
        assertNull((Long) explicitCastVoidReturnValue(Long.class).invokeExact());
    }

    @Test
    public void testVoidReturnToFloat() throws Throwable {
        assertNull((Float) explicitCastVoidReturnValue(Float.class).invokeExact());
    }

    @Test
    public void testVoidReturnToDouble() throws Throwable {
        assertNull((Double) explicitCastVoidReturnValue(Double.class).invokeExact());
    }

    @Test
    public void testVoidReturnToObject() throws Throwable {
        assertNull((Object) explicitCastVoidReturnValue(Object.class).invokeExact());
    }

    //
    // Check asType() behaviour of returning a value cast to void.
    //
    @Test
    public void testReturnZToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(boolean.class, void.class);
        m.invokeExact((boolean) BOOLEAN_VALUES[0]);
    }

    @Test
    public void testReturnBToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(byte.class, void.class);
        m.invokeExact((byte) BYTE_VALUES[0]);
    }

    @Test
    public void testReturnCToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(char.class, void.class);
        m.invokeExact((char) CHARACTER_VALUES[0]);
    }

    @Test
    public void testReturnSToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(short.class, void.class);
        m.invokeExact((short) SHORT_VALUES[0]);
    }

    @Test
    public void testReturnIToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(int.class, void.class);
        m.invokeExact((int) INTEGER_VALUES[0]);
    }

    @Test
    public void testReturnJToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(long.class, void.class);
        m.invokeExact((long) LONG_VALUES[0]);
    }

    @Test
    public void testReturnFToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(float.class, void.class);
        m.invokeExact((float) FLOAT_VALUES[0]);
    }

    @Test
    public void testReturnDToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(double.class, void.class);
        m.invokeExact((double) DOUBLE_VALUES[0]);
    }

    @Test
    public void testReturnBooleanToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(Boolean.class, void.class);
        m.invokeExact((Boolean) BOOLEAN_VALUES[0]);
    }

    @Test
    public void testReturnByteToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(Byte.class, void.class);
        m.invokeExact((Byte) BYTE_VALUES[0]);
    }

    @Test
    public void testReturnCharacterToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(Character.class, void.class);
        m.invokeExact((Character) CHARACTER_VALUES[0]);
    }

    @Test
    public void testReturnShortToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(Short.class, void.class);
        m.invokeExact((Short) SHORT_VALUES[0]);
    }

    @Test
    public void testReturnIntegerToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(Integer.class, void.class);
        m.invokeExact((Integer) INTEGER_VALUES[0]);
    }

    @Test
    public void testReturnLongToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(Long.class, void.class);
        m.invokeExact((Long) LONG_VALUES[0]);
    }

    @Test
    public void testReturnFloatToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(Float.class, void.class);
        m.invokeExact((Float) FLOAT_VALUES[0]);
    }

    @Test
    public void testReturnDoubleToVoid() throws Throwable {
        MethodHandle m = explicitCastReturnValueFromIdentity(Double.class, void.class);
        m.invokeExact((Double) DOUBLE_VALUES[0]);
    }

}
