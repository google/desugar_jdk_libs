/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.harmony.tests.java.lang;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.security.AccessController;
import java.security.BasicPermission;
import java.security.DomainCombiner;
import java.security.Permission;
import java.security.ProtectionDomain;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;
import java.util.function.Function;

public class ClassTest extends junit.framework.TestCase {

    // Relative resource paths.
    private static final String SHARP_RESOURCE_RELATIVE_NAME = "test#.properties";
    private static final String QUERY_RESOURCE_RELATIVE_NAME = "test?.properties";
    private static final String RESOURCE_RELATIVE_NAME = "test.properties";

    // Absolute resource paths.
    private static final String ABS_PATH =
            ClassTest.class.getPackage().getName().replace('.', '/');
    public static final String SHARP_RESOURCE_ABS_NAME =
            ABS_PATH + "/" + SHARP_RESOURCE_RELATIVE_NAME;
    public static final String QUERY_RESOURCE_ABS_NAME =
            ABS_PATH + "/" + QUERY_RESOURCE_RELATIVE_NAME;
    public static final String RESOURCE_ABS_NAME = ABS_PATH + "/" + RESOURCE_RELATIVE_NAME;

    public static class TestClass {
        @SuppressWarnings("unused")
        private int privField = 1;

        public int pubField = 2;

        private Object cValue = null;

        public Object ack = new Object();

        @SuppressWarnings("unused")
        private int privMethod() {
            return 1;
        }

        public int pubMethod() {
            return 2;
        }

        public Object cValue() {
            return cValue;
        }

        public TestClass() {
        }

        @SuppressWarnings("unused")
        private TestClass(Object o) {
        }
    }

    public static class SubTestClass extends TestClass {
    }

    /**
     * java.lang.Class#forName(java.lang.String)
     */
    public void test_forNameLjava_lang_String() throws Exception {
        assertSame("Class for name failed for java.lang.Object",
                Object.class, Class.forName("java.lang.Object"));
        assertSame("Class for name failed for [[Ljava.lang.Object;",
                Object[][].class, Class.forName("[[Ljava.lang.Object;"));

        assertSame("Class for name failed for [I",
                int[].class, Class.forName("[I"));

        try {
            Class.forName("int");
            fail();
        } catch (ClassNotFoundException e) {
        }

        try {
            Class.forName("byte");
            fail();
        } catch (ClassNotFoundException e) {
        }
        try {
            Class.forName("char");
            fail();
        } catch (ClassNotFoundException e) {
        }

        try {
            Class.forName("void");
            fail();
        } catch (ClassNotFoundException e) {
        }

        try {
            Class.forName("short");
            fail();
        } catch (ClassNotFoundException e) {
        }
        try {
            Class.forName("long");
            fail();
        } catch (ClassNotFoundException e) {
        }

        try {
            Class.forName("boolean");
            fail();
        } catch (ClassNotFoundException e) {
        }
        try {
            Class.forName("float");
            fail();
        } catch (ClassNotFoundException e) {
        }
        try {
            Class.forName("double");
            fail();
        } catch (ClassNotFoundException e) {
        }

        //regression test for JIRA 2162
        try {
            Class.forName("%");
            fail("should throw ClassNotFoundException.");
        } catch (ClassNotFoundException e) {
        }

        //Regression Test for HARMONY-3332
        String securityProviderClassName;
        int count = 1;
        while ((securityProviderClassName = Security
                .getProperty("security.provider." + count++)) != null) {
            Class.forName(securityProviderClassName);
        }
    }

    /**
     * java.lang.Class#getClasses()
     */
    public void test_getClasses() {
        assertEquals("Incorrect class array returned",
                2, ClassTest.class.getClasses().length);
    }

    /**
     * java.lang.Class#getClasses()
     */
    public void test_getClasses_subtest0() {
        final Permission privCheckPermission = new BasicPermission("Privilege check") {
            private static final long serialVersionUID = 1L;
        };

        class MyCombiner implements DomainCombiner {
            boolean combine;

            public ProtectionDomain[] combine(ProtectionDomain[] executionDomains,
                    ProtectionDomain[] parentDomains) {
                combine = true;
                return new ProtectionDomain[0];
            }

            private boolean recurring = false;

            public boolean isPriviledged() {
                if (recurring) {
                    return true;
                }
                try {
                    recurring = true;
                    combine = false;
                    try {
                        AccessController.checkPermission(privCheckPermission);
                    } catch (SecurityException e) {
                    }
                    return !combine;
                } finally {
                    recurring = false;
                }
            }
        }
    }

    /**
     * java.lang.Class#getComponentType()
     */
    public void test_getComponentType() {
        assertSame("int array does not have int component type", int.class, int[].class
                .getComponentType());
        assertSame("Object array does not have Object component type", Object.class,
                Object[].class.getComponentType());
        assertNull("Object has non-null component type", Object.class.getComponentType());
    }

    /**
     * java.lang.Class#getConstructor(java.lang.Class[])
     */
    public void test_getConstructor$Ljava_lang_Class()
            throws NoSuchMethodException {
        TestClass.class.getConstructor(new Class[0]);
        try {
            TestClass.class.getConstructor(Object.class);
            fail("Found private constructor");
        } catch (NoSuchMethodException e) {
            // Correct - constructor with obj is private
        }
    }

    /**
     * java.lang.Class#getConstructors()
     */
    public void test_getConstructors() throws Exception {
        Constructor[] c = TestClass.class.getConstructors();
        assertEquals("Incorrect number of constructors returned", 1, c.length);
    }

    /**
     * java.lang.Class#getDeclaredClasses()
     */
    public void test_getDeclaredClasses() {
        assertEquals("Incorrect class array returned", 2, ClassTest.class.getClasses().length);
    }

    /**
     * java.lang.Class#getDeclaredConstructor(java.lang.Class[])
     */
    public void test_getDeclaredConstructor$Ljava_lang_Class() throws Exception {
        Constructor<TestClass> c = TestClass.class.getDeclaredConstructor(new Class[0]);
        assertNull("Incorrect constructor returned", c.newInstance().cValue());
        c = TestClass.class.getDeclaredConstructor(Object.class);
    }

    /**
     * java.lang.Class#getDeclaredConstructors()
     */
    public void test_getDeclaredConstructors() throws Exception {
        Constructor[] c = TestClass.class.getDeclaredConstructors();
        assertEquals("Incorrect number of constructors returned", 2, c.length);
    }

    /**
     * java.lang.Class#getDeclaredField(java.lang.String)
     */
    public void test_getDeclaredFieldLjava_lang_String() throws Exception {
        Field f = TestClass.class.getDeclaredField("pubField");
        assertEquals("Returned incorrect field", 2, f.getInt(new TestClass()));
    }

    /**
     * java.lang.Class#getDeclaredFields()
     */
    public void test_getDeclaredFields() throws Exception {
        Field[] f = TestClass.class.getDeclaredFields();
        assertEquals("Returned incorrect number of fields", 4, f.length);
        f = SubTestClass.class.getDeclaredFields();
        // Declared fields do not include inherited
        assertEquals("Returned incorrect number of fields", 0, f.length);
    }

    /**
     * java.lang.Class#getDeclaredMethod(java.lang.String,
     *java.lang.Class[])
     */
    public void test_getDeclaredMethodLjava_lang_String$Ljava_lang_Class() throws Exception {
        Method m = TestClass.class.getDeclaredMethod("pubMethod", new Class[0]);
        assertEquals("Returned incorrect method", 2, ((Integer) (m.invoke(new TestClass())))
                .intValue());
        m = TestClass.class.getDeclaredMethod("privMethod", new Class[0]);
    }

    /**
     * java.lang.Class#getDeclaredMethods()
     */
    public void test_getDeclaredMethods() throws Exception {
        Method[] m = TestClass.class.getDeclaredMethods();
        assertEquals("Returned incorrect number of methods", 3, m.length);
        m = SubTestClass.class.getDeclaredMethods();
        assertEquals("Returned incorrect number of methods", 0, m.length);
    }

    /**
     * java.lang.Class#getDeclaringClass()
     */
    public void test_getDeclaringClass() {
        assertEquals(ClassTest.class, TestClass.class.getDeclaringClass());
    }

    /**
     * java.lang.Class#getField(java.lang.String)
     */
    public void test_getFieldLjava_lang_String() throws Exception {
        Field f = TestClass.class.getField("pubField");
        assertEquals("Returned incorrect field", 2, f.getInt(new TestClass()));
        try {
            f = TestClass.class.getField("privField");
            fail("Private field access failed to throw exception");
        } catch (NoSuchFieldException e) {
            // Correct
        }
    }

    /**
     * java.lang.Class#getFields()
     */
    public void test_getFields() throws Exception {
        Field[] f = TestClass.class.getFields();
        assertEquals("Incorrect number of fields", 2, f.length);
        f = SubTestClass.class.getFields();
        // Check inheritance of pub fields
        assertEquals("Incorrect number of fields", 2, f.length);
    }

    /**
     * java.lang.Class#getInterfaces()
     */
    public void test_getInterfaces() {
        Class[] interfaces;
        List<?> interfaceList;
        interfaces = Object.class.getInterfaces();
        assertEquals("Incorrect interface list for Object", 0, interfaces.length);
        interfaceList = Arrays.asList(Vector.class.getInterfaces());
        assertTrue("Incorrect interface list for Vector", interfaceList
                .contains(Cloneable.class)
                && interfaceList.contains(Serializable.class)
                && interfaceList.contains(List.class));
    }

    /**
     * java.lang.Class#getMethod(java.lang.String, java.lang.Class[])
     */
    public void test_getMethodLjava_lang_String$Ljava_lang_Class() throws Exception {
        Method m = TestClass.class.getMethod("pubMethod", new Class[0]);
        assertEquals("Returned incorrect method", 2, ((Integer) (m.invoke(new TestClass())))
                .intValue());
        try {
            m = TestClass.class.getMethod("privMethod", new Class[0]);
            fail("Failed to throw exception accessing private method");
        } catch (NoSuchMethodException e) {
            // Correct
            return;
        }
    }

    /**
     * java.lang.Class#getMethods()
     */
    public void test_getMethods() throws Exception {
        Method[] m = TestClass.class.getMethods();
        assertEquals("Returned incorrect number of methods",
                2 + Object.class.getMethods().length, m.length);
        m = SubTestClass.class.getMethods();
        assertEquals("Returned incorrect number of sub-class methods",
                2 + Object.class.getMethods().length, m.length);
        // Number of inherited methods
    }

    private static final class PrivateClass {
    }

    /**
     * java.lang.Class#getModifiers()
     */
    public void test_getModifiers() {
        int dcm = PrivateClass.class.getModifiers();
        assertFalse("default class is public", Modifier.isPublic(dcm));
        assertFalse("default class is protected", Modifier.isProtected(dcm));
        assertTrue("default class is not private", Modifier.isPrivate(dcm));

        int ocm = Object.class.getModifiers();
        assertTrue("public class is not public", Modifier.isPublic(ocm));
        assertFalse("public class is protected", Modifier.isProtected(ocm));
        assertFalse("public class is private", Modifier.isPrivate(ocm));
    }

    /**
     * java.lang.Class#getName()
     */
    public void test_getName() throws Exception {
        String className = Class.forName("java.lang.Object").getName();
        assertNotNull(className);

        assertEquals("Class getName printed wrong value", "java.lang.Object", className);
        assertEquals("Class getName printed wrong value", "int", int.class.getName());
        className = Class.forName("[I").getName();
        assertNotNull(className);
        assertEquals("Class getName printed wrong value", "[I", className);

        className = Class.forName("[Ljava.lang.Object;").getName();
        assertNotNull(className);

        assertEquals("Class getName printed wrong value", "[Ljava.lang.Object;", className);
    }

    /**
     * java.lang.Class#getResource(java.lang.String)
     */
    public void test_getResourceLjava_lang_String() {
        final String name = "/resources/test_resource.txt";
        URL res = getClass().getResource(name);
        assertNotNull(res);
    }

    /**
     * java.lang.Class#getResourceAsStream(java.lang.String)
     */
    public void test_getResourceAsStreamLjava_lang_String() throws Exception {
        final String name = "/resources/test_resource.txt";
        InputStream str2 = getClass().getResourceAsStream(name);
        assertNotNull("the file " + name + " can not be found in this directory", str2);

        final String nameBadURI = "org/apache/harmony/luni/tests/test_resource.txt";
        assertNull("the file " + nameBadURI + " should not be found in this directory",
                getClass().getResourceAsStream(nameBadURI));

        assertTrue("Cannot read single byte", str2.read() != -1);
        assertEquals("Cannot read multiple bytes", 5, str2.read(new byte[5]));
        str2.close();
    }

    /**
     * java.lang.Class#getSuperclass()
     */
    public void test_getSuperclass() {
        assertNull("Object has a superclass???", Object.class.getSuperclass());
        assertSame("Normal class has bogus superclass", InputStream.class,
                FileInputStream.class.getSuperclass());
        assertSame("Array class has bogus superclass", Object.class, FileInputStream[].class
                .getSuperclass());
        assertNull("Base class has a superclass", int.class.getSuperclass());
        assertNull("Interface class has a superclass", Cloneable.class.getSuperclass());
    }

    /**
     * java.lang.Class#isArray()
     */
    public void test_isArray() throws ClassNotFoundException {
        assertTrue("Non-array type claims to be.", !int.class.isArray());
        Class<?> clazz = null;
        clazz = Class.forName("[I");
        assertTrue("int Array type claims not to be.", clazz.isArray());

        clazz = Class.forName("[Ljava.lang.Object;");
        assertTrue("Object Array type claims not to be.", clazz.isArray());

        clazz = Class.forName("java.lang.Object");
        assertTrue("Non-array Object type claims to be.", !clazz.isArray());
    }

    /**
     * java.lang.Class#isAssignableFrom(java.lang.Class)
     */
    public void test_isAssignableFromLjava_lang_Class() {
        Class<?> clazz1 = null;
        Class<?> clazz2 = null;

        clazz1 = Object.class;
        clazz2 = Class.class;
        assertTrue("returned false for superclass", clazz1.isAssignableFrom(clazz2));

        clazz1 = TestClass.class;
        assertTrue("returned false for same class", clazz1.isAssignableFrom(clazz1));

        clazz1 = Runnable.class;
        clazz2 = Thread.class;
        assertTrue("returned false for implemented interface", clazz1.isAssignableFrom(clazz2));
    }

    /**
     * java.lang.Class#isInterface()
     */
    public void test_isInterface() throws ClassNotFoundException {
        assertTrue("Prim type claims to be interface.", !int.class.isInterface());
        Class<?> clazz = null;
        clazz = Class.forName("[I");
        assertTrue("Prim Array type claims to be interface.", !clazz.isInterface());

        clazz = Class.forName("java.lang.Runnable");
        assertTrue("Interface type claims not to be interface.", clazz.isInterface());
        clazz = Class.forName("java.lang.Object");
        assertTrue("Object type claims to be interface.", !clazz.isInterface());

        clazz = Class.forName("[Ljava.lang.Object;");
        assertTrue("Array type claims to be interface.", !clazz.isInterface());
    }

    /**
     * java.lang.Class#isPrimitive()
     */
    public void test_isPrimitive() {
        assertFalse("Interface type claims to be primitive.", Runnable.class.isPrimitive());
        assertFalse("Object type claims to be primitive.", Object.class.isPrimitive());
        assertFalse("Prim Array type claims to be primitive.", int[].class.isPrimitive());
        assertFalse("Array type claims to be primitive.", Object[].class.isPrimitive());
        assertTrue("Prim type claims not to be primitive.", int.class.isPrimitive());
        assertFalse("Object type claims to be primitive.", Object.class.isPrimitive());
    }

    /**
     * java.lang.Class#newInstance()
     */
    public void test_newInstance() throws Exception {
        Class<?> clazz = null;
        clazz = Class.forName("java.lang.Object");
        assertNotNull("new object instance was null", clazz.newInstance());

        clazz = Class.forName("java.lang.Throwable");
        assertSame("new Throwable instance was not a throwable",
                clazz, clazz.newInstance().getClass());

        clazz = Class.forName("java.lang.Integer");
        try {
            clazz.newInstance();
            fail("Exception for instantiating a newInstance with no default constructor is not thrown");
        } catch (InstantiationException e) {
            // expected
        }
    }

    // Regression Test for JIRA-2047
    public void test_getResourceAsStream_withSharpChar() throws Exception {
        // Class.getResourceAsStream() requires a leading "/" for absolute paths.
        assertNull(getClass().getResourceAsStream(SHARP_RESOURCE_ABS_NAME));
        assertResourceExists("/" + SHARP_RESOURCE_ABS_NAME);
        assertResourceExists(SHARP_RESOURCE_RELATIVE_NAME);

        InputStream in =
                this.getClass().getClassLoader().getResourceAsStream(SHARP_RESOURCE_ABS_NAME);
        assertNotNull(in);
        in.close();
    }

    public void test_getResource_withSharpChar() throws Exception {
        // Class.getResourceAsStream() requires a leading "/" for absolute paths.
        assertNull(getClass().getResource(SHARP_RESOURCE_ABS_NAME));
        URL absoluteURL = getClass().getResource("/" + SHARP_RESOURCE_ABS_NAME);

        // Make sure the name has been encoded.
        assertEquals(ABS_PATH + "/test%23.properties",
                absoluteURL.getFile().replaceAll("^.*!/", ""));

        // Make sure accessing it via an absolute and relative path produces the same result.
        URL relativeURL = getClass().getResource(SHARP_RESOURCE_RELATIVE_NAME);
        assertEquals(absoluteURL, relativeURL);
    }

    public void test_getResourceAsStream_withQueryChar() throws Exception {
        // Class.getResourceAsStream() requires a leading "/" for absolute paths.
        assertNull(getClass().getResourceAsStream(QUERY_RESOURCE_ABS_NAME));
        assertResourceExists("/" + QUERY_RESOURCE_ABS_NAME);
        assertResourceExists(QUERY_RESOURCE_RELATIVE_NAME);

        InputStream in =
                this.getClass().getClassLoader().getResourceAsStream(QUERY_RESOURCE_ABS_NAME);
        assertNotNull(in);
        in.close();
    }

    public void test_getResource_withQueryChar() throws Exception {
        // Class.getResourceAsStream() requires a leading "/" for absolute paths.
        assertNull(getClass().getResource(QUERY_RESOURCE_ABS_NAME));
        URL absoluteURL = getClass().getResource("/" + QUERY_RESOURCE_ABS_NAME);

        // Make sure the name has been encoded.
        assertEquals(ABS_PATH + "/test%3f.properties",
                absoluteURL.getFile().replaceAll("^.*!/", ""));

        // Make sure accessing it via an absolute and relative path produces the same result.
        URL relativeURL = getClass().getResource(QUERY_RESOURCE_RELATIVE_NAME);
        assertEquals(absoluteURL, relativeURL);
    }

    public void test_getResourceAsStream() throws Exception {
        // Class.getResourceAsStream() requires a leading "/" for absolute paths.
        assertNull(getClass().getResourceAsStream(RESOURCE_ABS_NAME));
        assertResourceExists("/" + RESOURCE_ABS_NAME);
        assertResourceExists(RESOURCE_RELATIVE_NAME);

        InputStream in = this.getClass().getClassLoader().getResourceAsStream(RESOURCE_ABS_NAME);
        assertNotNull(in);
        in.close();
    }

    private void assertResourceExists(String resourceName) throws IOException {
        InputStream in = getClass().getResourceAsStream(resourceName);
        assertNotNull(in);
        in.close();
    }

    /*
    * Regression test for HARMONY-2644:
    * Load system and non-system array classes via Class.forName()
    */
    public void test_forName_arrays() throws Exception {
        Class c1 = getClass();
        String s = c1.getName();
        Class a1 = Class.forName("[L" + s + ";");
        Class a2 = Class.forName("[[L" + s + ";");
        assertSame(c1, a1.getComponentType());
        assertSame(a1, a2.getComponentType());
        Class l4 = Class.forName("[[[[[J");
        assertSame(long[][][][][].class, l4);

        try {
            System.out.println(Class.forName("[;"));
            fail("1");
        } catch (ClassNotFoundException ok) {
        }
        try {
            System.out.println(Class.forName("[["));
            fail("2");
        } catch (ClassNotFoundException ok) {
        }
        try {
            System.out.println(Class.forName("[L"));
            fail("3");
        } catch (ClassNotFoundException ok) {
        }
        try {
            System.out.println(Class.forName("[L;"));
            fail("4");
        } catch (ClassNotFoundException ok) {
        }
        try {
            System.out.println(Class.forName(";"));
            fail("5");
        } catch (ClassNotFoundException ok) {
        }
        try {
            System.out.println(Class.forName(""));
            fail("6");
        } catch (ClassNotFoundException ok) {
        }
    }
}
