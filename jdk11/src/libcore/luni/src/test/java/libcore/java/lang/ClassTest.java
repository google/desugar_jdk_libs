/*
 * Copyright (C) 2013 The Android Open Source Project
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
package libcore.java.lang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.TestCase;

import dalvik.system.PathClassLoader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;

public class ClassTest extends TestCase {

    interface Foo {
        public void foo();
    }

    interface ParameterizedFoo<T> {
        public void foo(T param);
    }

    interface ParameterizedBar<T> extends ParameterizedFoo<T> {
        public void bar(T param);
    }

    interface ParameterizedBaz extends ParameterizedFoo<String> {

    }

    public void test_getGenericSuperclass_nullReturnCases() {
        // Should always return null for interfaces.
        assertNull(Foo.class.getGenericSuperclass());
        assertNull(ParameterizedFoo.class.getGenericSuperclass());
        assertNull(ParameterizedBar.class.getGenericSuperclass());
        assertNull(ParameterizedBaz.class.getGenericSuperclass());

        assertNull(Object.class.getGenericSuperclass());
        assertNull(void.class.getGenericSuperclass());
        assertNull(int.class.getGenericSuperclass());
    }

    public void test_getGenericSuperclass_returnsObjectForArrays() {
        assertSame(Object.class, (new Integer[0]).getClass().getGenericSuperclass());
    }

    public void test_b28833829() throws Exception {
        File f = File.createTempFile("temp_b28833829", ".dex");
        try (InputStream is =
            getClass().getClassLoader().getResourceAsStream("TestBug28833829.dex");
            OutputStream os = new FileOutputStream(f)) {
            byte[] buffer = new byte[8192];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) >= 0) {
                os.write(buffer, 0, bytesRead);
            }
        }

        PathClassLoader pcl = new PathClassLoader(f.getAbsolutePath(), null);
        Class<?> cl = pcl.loadClass(
            "libcore.java.lang.TestBadInnerClass_Outer$ClassTestBadInnerClass_InnerClass");

        // Note that getName() and getSimpleName() are inconsistent here because for
        // inner classes,  the latter is fetched directly from the InnerClass
        // annotation in the dex file. We do not perform any sort of consistency
        // checks with the class name or the enclosing class name. Unfortunately, applications
        // have come to rely on this behaviour.
        assertEquals("libcore.java.lang.TestBadInnerClass_Outer$ClassTestBadInnerClass_InnerClass",
            cl.getName());
        assertEquals("TestBadInnerClass_InnerXXXXX", cl.getSimpleName());
    }

    interface A {
        public static String name = "A";
    }
    interface B {
        public static String name = "B";
    }
    class X implements A { }
    class Y extends X implements B { }
    public void test_getField() {
        try {
            assertEquals(A.class.getField("name"), X.class.getField("name"));
        } catch (NoSuchFieldException e) {
            fail("Got exception");
        }
        try {
            assertEquals(B.class.getField("name"), Y.class.getField("name"));
        } catch (NoSuchFieldException e) {
            fail("Got exception");
        }
    }

    interface C {
        void foo();
    }
    interface D extends C {
        void foo();
    }
    abstract class Z implements D { }

    public void test_getMethod() {
      try {
          assertEquals(Z.class.getMethod("foo"), D.class.getMethod("foo"));
      } catch (NoSuchMethodException e) {
          fail("Got exception");
      }
    }

    public void test_getPrimitiveType_null() throws Throwable {
        try {
            getPrimitiveType(null);
            fail();
        } catch (NullPointerException expected) {
            assertNull(expected.getMessage());
        }
    }

    public void test_getPrimitiveType_invalid() throws Throwable {
        List<String> invalidNames = Arrays.asList("", "java.lang.Object", "invalid",
                "Boolean", "java.lang.Boolean", "java/lang/Boolean", "Ljava/lang/Boolean;");
        for (String name : invalidNames) {
            try {
                getPrimitiveType(name);
                fail("Invalid type should be rejected: " + name);
            } catch (ClassNotFoundException expected) {
                assertEquals(name, expected.getMessage());
            }
        }
    }

    public void test_getPrimitiveType_valid() throws Throwable {
        checkPrimitiveType("boolean", boolean.class, Boolean.TYPE,
            boolean[].class.getComponentType());
        checkPrimitiveType("byte", byte.class, Byte.TYPE, byte[].class.getComponentType());
        checkPrimitiveType("char", char.class, Character.TYPE, char[].class.getComponentType());
        checkPrimitiveType("double", double.class, Double.TYPE, double[].class.getComponentType());
        checkPrimitiveType("float", float.class, Float.TYPE, float[].class.getComponentType());
        checkPrimitiveType("int", int.class, Integer.TYPE, int[].class.getComponentType());
        checkPrimitiveType("long", long.class, Long.TYPE, long[].class.getComponentType());
        checkPrimitiveType("short", short.class, Short.TYPE, short[].class.getComponentType());
        checkPrimitiveType("void", void.class, Void.TYPE);
    }

    private static void checkPrimitiveType(String name, Class expected, Class... expectedEqual)
            throws Throwable {
        Class clazz = getPrimitiveType(name);
        assertEquals(name, clazz.getName());
        assertTrue(clazz.isPrimitive());
        assertEquals(expected, clazz);
        for (Class c : expectedEqual) {
            assertEquals(expected, c);
        }
    }

    /** Calls {@link Class#getPrimitiveClass(String)} via reflection. */
    private static Class getPrimitiveType(String name) throws Throwable {
        try {
            Method method = Class.class.getDeclaredMethod("getPrimitiveClass", String.class);
            method.setAccessible(true);
            return (Class) method.invoke(null, name);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Throwable unexpected) {
            // no other kinds of throwables are expected to happen
            fail(unexpected.toString());
            return null;
        }
    }

    public static class TestGetVirtualMethod_Super {
        protected String protectedMethod() {
            return "protectedMethod";
        }

        public String publicMethod() {
            return "publicMethod";
        }

        /* package */ String packageMethod() {
            return "packageMethod";
        }
    }

    public static class TestGetVirtualMethod extends TestGetVirtualMethod_Super {
        public static void staticMethod(String foo) {
        }

        public String publicMethod2() {
            return "publicMethod2";
        }

        protected String protectedMethod2() {
            return "protectedMethod2";
        }

        private String privateMethod() {
            return "privateMethod";
        }

        /* package */ String packageMethod2() {
            return "packageMethod2";
        }
    }

    public void test_getVirtualMethod() throws Exception {
        final Class<?>[] noArgs = new Class<?>[] { };

        TestGetVirtualMethod instance = new TestGetVirtualMethod();
        TestGetVirtualMethod_Super super_instance = new TestGetVirtualMethod_Super();

        // Package private methods from the queried class as well as super classes
        // must be returned.
        Method m = TestGetVirtualMethod.class.getInstanceMethod("packageMethod2", noArgs);
        assertNotNull(m);
        assertEquals("packageMethod2", m.invoke(instance));
        m = TestGetVirtualMethod.class.getInstanceMethod("packageMethod", noArgs);
        assertNotNull(m);
        assertEquals("packageMethod", m.invoke(instance));

        // Protected methods from both the queried class as well as super classes must
        // be returned.
        m = TestGetVirtualMethod.class.getInstanceMethod("protectedMethod2", noArgs);
        assertNotNull(m);
        assertEquals("protectedMethod2", m.invoke(instance));
        m = TestGetVirtualMethod.class.getInstanceMethod("protectedMethod", noArgs);
        assertNotNull(m);
        assertEquals("protectedMethod", m.invoke(instance));

        // Public methods from the queried classes and all its super classes must be
        // returned.
        m = TestGetVirtualMethod.class.getInstanceMethod("publicMethod2", noArgs);
        assertNotNull(m);
        assertEquals("publicMethod2", m.invoke(instance));
        m = TestGetVirtualMethod.class.getInstanceMethod("publicMethod", noArgs);
        assertNotNull(m);
        assertEquals("publicMethod", m.invoke(instance));

        m = TestGetVirtualMethod.class.getInstanceMethod("privateMethod", noArgs);
        assertNotNull(m);

        assertNull(TestGetVirtualMethod.class.getInstanceMethod("staticMethod", noArgs));
    }

    public void test_toString() throws Exception {
        final String outerClassName = getClass().getName();
        final String packageProtectedClassName = PackageProtectedClass.class.getName();

        assertToString("int", int.class);
        assertToString("class [I", int[].class);
        assertToString("class java.lang.Object", Object.class);
        assertToString("class [Ljava.lang.Object;", Object[].class);
        assertToString("class java.lang.Integer", Integer.class);
        assertToString("interface java.util.function.Function", Function.class);
        assertToString(
                "class " + outerClassName + "$PublicStaticInnerClass",
                PublicStaticInnerClass.class);
        assertToString(
                "class " + outerClassName + "$DefaultStaticInnerClass",
                DefaultStaticInnerClass.class);
        assertToString(
                "interface " + outerClassName + "$PublicInnerInterface",
                PublicInnerInterface.class);
        assertToString(
                "class " + packageProtectedClassName,
                PackageProtectedClass.class);
        assertToString(
                "class " + outerClassName + "$PrivateStaticInnerClass",
                PrivateStaticInnerClass.class);
        assertToString("interface java.lang.annotation.Retention", Retention.class);
        assertToString("class java.lang.annotation.RetentionPolicy", RetentionPolicy.class);
        assertToString("class java.util.TreeMap", TreeMap.class);
        assertToString(
                "interface " + outerClassName + "$WildcardInterface",
                WildcardInterface.class);
    }

    private static void assertToString(String expected, Class<?> clazz) {
        assertEquals(expected, clazz.toString());
    }

    public void test_getTypeName() throws Exception {
        final String outerClassName = getClass().getName();
        final String packageProtectedClassName = PackageProtectedClass.class.getName();

        assertGetTypeName("int", int.class);
        assertGetTypeName("int[]", int[].class);
        assertGetTypeName("java.lang.Object", Object.class);
        assertGetTypeName("java.lang.Object[]", Object[].class);
        assertGetTypeName("java.lang.Integer", Integer.class);
        assertGetTypeName("java.util.function.Function", Function.class);
        assertGetTypeName(outerClassName + "$PublicStaticInnerClass", PublicStaticInnerClass.class);
        assertGetTypeName(
                outerClassName + "$DefaultStaticInnerClass",
                DefaultStaticInnerClass.class);
        assertGetTypeName(outerClassName + "$PublicInnerInterface", PublicInnerInterface.class);
        assertGetTypeName(packageProtectedClassName, PackageProtectedClass.class);
        assertGetTypeName(
                outerClassName + "$PrivateStaticInnerClass",
                PrivateStaticInnerClass.class);
        assertGetTypeName("java.lang.annotation.Retention", Retention.class);
        assertGetTypeName("java.lang.annotation.RetentionPolicy", RetentionPolicy.class);
        assertGetTypeName("java.util.TreeMap", TreeMap.class);
        assertGetTypeName(outerClassName + "$WildcardInterface", WildcardInterface.class);
    }

    private void assertGetTypeName(String expected, Class<?> clazz) {
        assertEquals(expected, clazz.getTypeName());
    }

    public void test_toGenericString() throws Exception {
        final String outerClassName = getClass().getName();
        final String packageProtectedClassName = PackageProtectedClass.class.getName();

        assertToGenericString("int", int.class);
        assertToGenericString("public abstract final class [I", int[].class);
        assertToGenericString("public class java.lang.Object", Object.class);
        assertToGenericString("public abstract final class [Ljava.lang.Object;", Object[].class);
        assertToGenericString("public final class java.lang.Integer", Integer.class);
        assertToGenericString(
                "public abstract interface java.util.function.Function<T,R>",
                Function.class);
        assertToGenericString("public static class " + outerClassName + "$PublicStaticInnerClass",
                PublicStaticInnerClass.class);
        assertToGenericString("static class " + outerClassName + "$DefaultStaticInnerClass",
                DefaultStaticInnerClass.class);
        assertToGenericString(
                "public abstract static interface " + outerClassName + "$PublicInnerInterface",
                PublicInnerInterface.class);
        assertToGenericString("class " + packageProtectedClassName, PackageProtectedClass.class);
        assertToGenericString(
                "private static class " + outerClassName + "$PrivateStaticInnerClass",
                PrivateStaticInnerClass.class);
        assertToGenericString(
                "public abstract @interface java.lang.annotation.Retention", Retention.class);
        assertToGenericString("public final enum java.lang.annotation.RetentionPolicy",
                RetentionPolicy.class);
        assertToGenericString("public class java.util.TreeMap<K,V>", TreeMap.class);
        assertToGenericString(
                "abstract static interface " + outerClassName + "$WildcardInterface<T,U>",
                WildcardInterface.class);
    }

    private static void assertToGenericString(String expected, Class<?> clazz) {
        assertEquals(expected, clazz.toGenericString());
    }

    private static class PrivateStaticInnerClass {}
    static class DefaultStaticInnerClass {}
    public static class PublicStaticInnerClass {}
    public interface PublicInnerInterface {}
    interface WildcardInterface<
            T extends Number,
            U extends Function<? extends Number, ? super Number>>
            extends Comparable<T> {}
}
