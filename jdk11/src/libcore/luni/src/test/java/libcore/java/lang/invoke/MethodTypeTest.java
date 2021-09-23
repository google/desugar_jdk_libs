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

package libcore.java.lang.invoke;

import junit.framework.TestCase;

import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.List;

public class MethodTypeTest extends TestCase {
    private static final Class<?>[] LARGE_PARAMETER_ARRAY;

    static {
        LARGE_PARAMETER_ARRAY = new Class<?>[254];
        for (int i = 0; i < 254; ++i) {
            LARGE_PARAMETER_ARRAY[i] = Object.class;
        }
    }

    public void test_methodType_basicTestsReturnTypeAndParameterClassArray() {
        MethodType mt = MethodType.methodType(int.class,
                new Class<?>[] { String.class, long.class});

        assertEquals(int.class, mt.returnType());
        assertParameterTypes(mt, String.class, long.class);

        try {
            MethodType.methodType(null, new Class<?>[] { String.class });
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            MethodType.methodType(int.class, (Class<?>[]) null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            MethodType.methodType(int.class, new Class<?>[] {void.class});
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void test_methodType_basicTestsReturnTypeAndParameterClassList() {
        MethodType mt = MethodType.methodType(int.class, Arrays.asList(String.class, long.class));

        assertEquals(int.class, mt.returnType());
        assertParameterTypes(mt, String.class, long.class);

        try {
            MethodType.methodType(null, Arrays.asList(String.class));
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            MethodType.methodType(int.class, (List<Class<?>>) null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            MethodType.methodType(int.class, Arrays.asList(void.class));
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void test_methodType_basicTestsReturnTypeAndVarargsParameters() {
        MethodType mt = MethodType.methodType(int.class, String.class, long.class);

        assertEquals(int.class, mt.returnType());
        assertParameterTypes(mt, String.class, long.class);

        try {
            MethodType.methodType(null, String.class);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            MethodType.methodType(int.class, String.class, (Class<?>) null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            MethodType.methodType(int.class, void.class, String.class);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void test_methodType_basicTestsReturnTypeOnly() {
        MethodType mt = MethodType.methodType(int.class);

        assertEquals(int.class, mt.returnType());
        assertEquals(0, mt.parameterCount());

        try {
            MethodType.methodType(null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void test_methodType_basicTestsReturnTypeAndSingleParameter() {
        MethodType mt = MethodType.methodType(int.class, long.class);

        assertEquals(int.class, mt.returnType());
        assertParameterTypes(mt, long.class);

        try {
            MethodType.methodType(null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            MethodType.methodType(null, String.class);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            MethodType.methodType(int.class, (Class<?>) null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            MethodType.methodType(int.class, void.class);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void test_methodType_basicTestsReturnTypeAndMethodTypeParameters() {
        MethodType mt = MethodType.methodType(int.class, long.class, String.class);
        assertEquals(int.class, mt.returnType());

        MethodType mt2 = MethodType.methodType(long.class, mt);

        assertEquals(long.class, mt2.returnType());
        assertEquals(long.class, mt2.parameterType(0));
        assertEquals(String.class, mt2.parameterType(1));

        try {
            MethodType.methodType(int.class, (MethodType) null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void testGenericMethodType() {
        MethodType mt = MethodType.genericMethodType(0);
        assertEquals(0, mt.parameterCount());
        assertEquals(Object.class, mt.returnType());

        mt = MethodType.genericMethodType(3);
        assertEquals(Object.class, mt.returnType());

        assertEquals(3, mt.parameterCount());
        assertParameterTypes(mt, Object.class, Object.class, Object.class);

        try {
            MethodType.genericMethodType(-1);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            MethodType.genericMethodType(256);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testGenericMethodTypeWithTrailingArray() {
        MethodType mt = MethodType.genericMethodType(3, false /* finalArray */);
        assertEquals(Object.class, mt.returnType());
        assertParameterTypes(mt, Object.class, Object.class, Object.class);

        mt = MethodType.genericMethodType(0, true /* finalArray */);
        assertEquals(Object.class, mt.returnType());
        assertParameterTypes(mt, Object[].class);

        mt = MethodType.genericMethodType(2, true /* finalArray */);
        assertEquals(Object.class, mt.returnType());
        assertParameterTypes(mt, Object.class, Object.class, Object[].class);

        try {
            MethodType.genericMethodType(-1, true);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            MethodType.genericMethodType(255, true);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testChangeParameterType() {
        // int method(String, Object, List);
        MethodType mt = MethodType.methodType(int.class, String.class, Object.class, List.class);
        assertEquals(Object.class, mt.parameterType(1));

        MethodType changed = mt.changeParameterType(1, String.class);
        assertEquals(String.class, changed.parameterType(1));

        // Assert that the return types and the other parameter types haven't changed.
        assertEquals(mt.parameterCount(), changed.parameterCount());
        assertEquals(mt.returnType(), changed.returnType());
        assertEquals(mt.parameterType(0), changed.parameterType(0));
        assertEquals(mt.parameterType(2), changed.parameterType(2));

        try {
            mt.changeParameterType(-1, String.class);
            fail();
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        try {
            mt.changeParameterType(3, String.class);
            fail();
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        try {
            mt.changeParameterType(1, void.class);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            mt.changeParameterType(1, null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void testInsertParameterTypes_varargs() {
        MethodType mt = MethodType.methodType(int.class, String.class, Object.class);

        MethodType insert0 = mt.insertParameterTypes(0, Integer.class, Long.class);
        assertEquals(int.class, insert0.returnType());
        assertParameterTypes(insert0, Integer.class, Long.class, String.class, Object.class);

        MethodType insert1 = mt.insertParameterTypes(1, Integer.class, Long.class);
        assertParameterTypes(insert1, String.class, Integer.class, Long.class, Object.class);

        MethodType insert2 = mt.insertParameterTypes(2, Integer.class, Long.class);
        assertParameterTypes(insert2, String.class, Object.class, Integer.class, Long.class);

        try {
            mt.insertParameterTypes(1, LARGE_PARAMETER_ARRAY);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            mt.insertParameterTypes(1, void.class);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            mt.insertParameterTypes(1, (Class<?>) null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            mt.insertParameterTypes(-1, String.class);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            mt.insertParameterTypes(3, String.class);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    public void testInsertParameterTypes_list() {
        MethodType mt = MethodType.methodType(int.class, String.class, Object.class);

        MethodType insert0 = mt.insertParameterTypes(0, Arrays.asList(Integer.class, Long.class));
        assertEquals(int.class, insert0.returnType());
        assertParameterTypes(insert0, Integer.class, Long.class, String.class, Object.class);

        MethodType insert1 = mt.insertParameterTypes(1, Arrays.asList(Integer.class, Long.class));
        assertParameterTypes(insert1, String.class, Integer.class, Long.class, Object.class);

        MethodType insert2 = mt.insertParameterTypes(2, Arrays.asList(Integer.class, Long.class));
        assertParameterTypes(insert2, String.class, Object.class, Integer.class, Long.class);

        try {
            mt.insertParameterTypes(1, Arrays.asList(LARGE_PARAMETER_ARRAY));
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            mt.insertParameterTypes(1, Arrays.asList(void.class));
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            mt.insertParameterTypes(1, (List<Class<?>>) null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            mt.insertParameterTypes(1, Arrays.asList((Class<?>) null));
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            mt.insertParameterTypes(-1, Arrays.asList(String.class));
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            mt.insertParameterTypes(3, Arrays.asList(String.class));
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    public void testAppendParameterTypes_varargs() {
        MethodType mt = MethodType.methodType(int.class, String.class, String.class);

        MethodType appended = mt.appendParameterTypes(List.class, Integer.class);
        assertEquals(int.class, appended.returnType());
        assertParameterTypes(appended, String.class, String.class, List.class, Integer.class);

        try {
            mt.appendParameterTypes(LARGE_PARAMETER_ARRAY);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            mt.appendParameterTypes(void.class);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            mt.appendParameterTypes((Class<?>) null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void testAppendParameterTypes_list() {
        MethodType mt = MethodType.methodType(int.class, String.class, String.class);

        MethodType appended = mt.appendParameterTypes(Arrays.asList(List.class, Integer.class));
        assertEquals(int.class, appended.returnType());
        assertParameterTypes(appended, String.class, String.class, List.class, Integer.class);

        try {
            mt.appendParameterTypes(Arrays.asList(LARGE_PARAMETER_ARRAY));
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            mt.appendParameterTypes(Arrays.asList(void.class));
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            mt.appendParameterTypes((List<Class<?>>) null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            mt.appendParameterTypes(Arrays.asList((Class<?>) null));
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void testDropParameterTypes() {
        MethodType mt = MethodType.methodType(int.class, String.class, List.class, Object.class);

        MethodType dropNone = mt.dropParameterTypes(0, 0);
        assertEquals(int.class, dropNone.returnType());
        assertParameterTypes(dropNone, String.class, List.class, Object.class);

        MethodType dropFirst = mt.dropParameterTypes(0, 1);
        assertEquals(int.class, dropFirst.returnType());
        assertParameterTypes(dropFirst, List.class, Object.class);

        MethodType dropAll = mt.dropParameterTypes(0, 3);
        assertEquals(0, dropAll.parameterCount());
        assertEquals(int.class, dropAll.returnType());

        try {
            mt.dropParameterTypes(-1, 1);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            mt.dropParameterTypes(1, 4);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            mt.dropParameterTypes(2, 1);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    public void testChangeReturnType() {
        MethodType mt = MethodType.methodType(int.class, String.class);

        MethodType changed = mt.changeReturnType(long.class);
        assertEquals(long.class, changed.returnType());
        assertParameterTypes(changed, String.class);

        try {
            mt.changeReturnType(null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void testHasPrimitives() {
        MethodType mt = MethodType.methodType(Integer.class, Object.class, String.class);
        assertFalse(mt.hasPrimitives());

        mt = MethodType.methodType(int.class, Object.class);
        assertTrue(mt.hasPrimitives());

        mt = MethodType.methodType(Integer.class, long.class);
        assertTrue(mt.hasPrimitives());

        mt = MethodType.methodType(Integer.class, int[].class);
        assertFalse(mt.hasPrimitives());

        mt = MethodType.methodType(void.class);
        assertTrue(mt.hasPrimitives());
    }

    public void testHasWrappers() {
        MethodType mt = MethodType.methodType(Integer.class);
        assertTrue(mt.hasWrappers());

        mt = MethodType.methodType(String.class, Integer.class);
        assertTrue(mt.hasWrappers());

        mt = MethodType.methodType(int.class, long.class);
        assertFalse(mt.hasWrappers());
    }

    public void testErase() {
        // String mt(int, String, Object) should be erased to Object mt(int, Object, Object);
        MethodType mt = MethodType.methodType(String.class, int.class, String.class, Object.class);

        MethodType erased = mt.erase();
        assertEquals(Object.class, erased.returnType());
        assertParameterTypes(erased, int.class, Object.class, Object.class);

        // Void returns must be left alone.
        mt = MethodType.methodType(void.class, int.class);
        erased = mt.erase();
        assertEquals(mt, erased);
    }

    public void testGeneric() {
        // String mt(int, String, Object) should be generified to Object mt(Object, Object, Object).
        // In other words, it must be equal to genericMethodType(3 /* parameterCount */);
        MethodType mt = MethodType.methodType(String.class, int.class, String.class, Object.class);

        MethodType generic = mt.generic();

        assertEquals(generic, MethodType.genericMethodType(mt.parameterCount()));
        assertEquals(generic, mt.wrap().erase());

        assertEquals(Object.class, generic.returnType());
        assertParameterTypes(generic, Object.class, Object.class, Object.class);

        // Primitive return types must also become Object.
        generic = MethodType.methodType(int.class).generic();
        assertEquals(Object.class, generic.returnType());

        // void returns get converted to object returns (the same as wrap).
        generic = MethodType.methodType(void.class).generic();
        assertEquals(Object.class, generic.returnType());
    }

    public void testWrap() {
        // int mt(String, int, long, float, double, short, char, byte) should be wrapped to
        // Integer mt(String, Integer, Long, Float, Double, Short, Character, Byte);
        MethodType mt = MethodType.methodType(int.class, String.class, int.class, long.class,
                float.class, double.class, short.class, char.class, byte.class);

        MethodType wrapped = mt.wrap();
        assertFalse(wrapped.hasPrimitives());
        assertTrue(wrapped.hasWrappers());

        assertEquals(Integer.class, wrapped.returnType());
        assertParameterTypes(wrapped, String.class, Integer.class, Long.class, Float.class,
                Double.class, Short.class, Character.class, Byte.class);

        // (semi) special case - void return types get wrapped to Void.
        wrapped = MethodType.methodType(void.class, int.class).wrap();
        assertEquals(Void.class, wrapped.returnType());
    }

    public void testUnwrap() {
        // Integer mt(String, Integer, Long, Float, Double, Short, Character, Byte);
        // should be unwrapped to :
        // int mt(String, int, long, float, double, short, char, byte).
        MethodType mt = MethodType.methodType(Integer.class, String.class, Integer.class,
                Long.class, Float.class, Double.class, Short.class, Character.class, Byte.class);

        MethodType unwrapped = mt.unwrap();
        assertTrue(unwrapped.hasPrimitives());
        assertFalse(unwrapped.hasWrappers());

        assertEquals(int.class, unwrapped.returnType());
        assertParameterTypes(unwrapped, String.class, int.class, long.class, float.class,
                double.class, short.class, char.class, byte.class);

        // (semi) special case - void return types get wrapped to Void.
        unwrapped = MethodType.methodType(Void.class, int.class).unwrap();
        assertEquals(void.class, unwrapped.returnType());
    }

    public void testParameterListAndArray() {
        MethodType mt = MethodType.methodType(String.class, int.class, String.class, Object.class);

        List<Class<?>> paramsList = mt.parameterList();
        Class<?>[] paramsArray = mt.parameterArray();

        assertEquals(3, mt.parameterCount());

        for (int i = 0; i < 3; ++i) {
            Class<?> param = mt.parameterType(i);
            assertEquals(param, paramsList.get(i));
            assertEquals(param, paramsArray[i]);
        }

        mt = MethodType.methodType(int.class);
        assertEquals(0, mt.parameterCount());

        paramsList = mt.parameterList();
        paramsArray = mt.parameterArray();

        assertEquals(0, paramsList.size());
        assertEquals(0, paramsArray.length);
    }

    public void testEquals() {
        MethodType mt = MethodType.methodType(int.class, String.class);
        MethodType mt2 = MethodType.methodType(int.class, String.class);

        assertEquals(mt, mt2);
        assertEquals(mt, mt);

        assertFalse(mt.equals(null));
        assertFalse(mt.equals(MethodType.methodType(Integer.class, String.class)));
    }

    public void testHashCode() {
        MethodType mt = MethodType.methodType(int.class, String.class, Object.class);
        int hashCode = mt.hashCode();

        // The hash code should change if we change the return type or any of the parameters,
        // or if we add or remove parameters from the list.
        assertFalse(hashCode == mt.changeReturnType(long.class).hashCode());
        assertFalse(hashCode == mt.changeParameterType(0, Object.class).hashCode());
        assertFalse(hashCode == mt.appendParameterTypes(List.class).hashCode());
        assertFalse(hashCode == mt.dropParameterTypes(0, 1).hashCode());
    }

    public void testToString() {
        assertEquals("(String,Object)int",
                MethodType.methodType(int.class, String.class, Object.class).toString());
        assertEquals("()int", MethodType.methodType(int.class).toString());
        assertEquals("()void", MethodType.methodType(void.class).toString());
        assertEquals("()int[]", MethodType.methodType(int[].class).toString());
    }

    public void testFromMethodDescriptorString() {
        assertEquals(
                MethodType.methodType(int.class, String.class, Object.class),
                MethodType.fromMethodDescriptorString("(Ljava/lang/String;Ljava/lang/Object;)I", null));

        assertEquals(MethodType.fromMethodDescriptorString("()I", null),
                MethodType.methodType(int.class));
        assertEquals(MethodType.fromMethodDescriptorString("()[I", null),
                MethodType.methodType(int[].class));
        assertEquals(MethodType.fromMethodDescriptorString("([I)V", null),
                MethodType.methodType(void.class, int[].class));

        try {
            MethodType.fromMethodDescriptorString(null, null);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            MethodType.fromMethodDescriptorString("(a/b/c)I", null);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            MethodType.fromMethodDescriptorString("(A)I", null);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            MethodType.fromMethodDescriptorString("(Ljava/lang/String)I", null);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            MethodType.fromMethodDescriptorString("(Ljava/lang/String;)", null);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            MethodType.fromMethodDescriptorString("(Ljava/lang/NonExistentString;)I", null);
            fail();
        } catch (TypeNotPresentException expected) {
        }
    }

    public void testToMethodDescriptorString() {
        assertEquals("(Ljava/lang/String;Ljava/lang/Object;)I", MethodType.methodType(
                int.class, String.class, Object.class).toMethodDescriptorString());

        assertEquals("()I", MethodType.methodType(int.class).toMethodDescriptorString());
        assertEquals("()[I", MethodType.methodType(int[].class).toMethodDescriptorString());

        assertEquals("([I)V", MethodType.methodType(void.class, int[].class)
                .toMethodDescriptorString());
    }

    private static void assertParameterTypes(MethodType type, Class<?>... params) {
        assertEquals(params.length, type.parameterCount());

        List<Class<?>> paramsList = type.parameterList();
        for (int i = 0; i < params.length; ++i) {
            assertEquals(params[i], type.parameterType(i));
            assertEquals(params[i], paramsList.get(i));
        }

        assertTrue(Arrays.equals(params, type.parameterArray()));
    }
}
