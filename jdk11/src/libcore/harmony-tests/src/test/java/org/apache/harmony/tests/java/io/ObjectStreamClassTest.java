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

package org.apache.harmony.tests.java.io;

import dalvik.system.DexFile;
import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import libcore.junit.junit3.TestCaseWithRules;
import libcore.junit.util.SwitchTargetSdkVersionRule;
import libcore.junit.util.SwitchTargetSdkVersionRule.TargetSdkVersion;
import org.junit.Rule;
import org.junit.rules.TestRule;

public class ObjectStreamClassTest extends TestCaseWithRules {

    @Rule
    public TestRule switchTargetSdkVersionRule = SwitchTargetSdkVersionRule.getInstance();

    static class FakeClass implements Serializable {
        private static final long serialVersionUID = 999999999999999L;

        long bam = 999L;

        int ham = 9999;

        public static long getUID() {
            return serialVersionUID;
        }
    }

    /**
     * java.io.ObjectStreamClass#forClass()
     */
    public void test_forClass() {
        // Need to test during serialization to be sure an instance is
        // returned
        ObjectStreamClass osc = ObjectStreamClass.lookup(FakeClass.class);
        assertEquals("forClass returned an object: " + osc.forClass(),
                FakeClass.class, osc.forClass());
    }

    /**
     * java.io.ObjectStreamClass#getField(java.lang.String)
     */
    public void test_getFieldLjava_lang_String() {
        ObjectStreamClass osc = ObjectStreamClass.lookup(FakeClass.class);
        assertEquals("getField did not return correct field", 'J', osc
                .getField("bam").getTypeCode());
        assertNull("getField did not null for non-existent field", osc
                .getField("wham"));
    }

    /**
     * java.io.ObjectStreamClass#getFields()
     */
    public void test_getFields() {
        ObjectStreamClass osc = ObjectStreamClass.lookup(FakeClass.class);
        ObjectStreamField[] osfArray = osc.getFields();
        assertTrue(
                "Array of fields should be of length 2 but is instead of length: "
                        + osfArray.length, osfArray.length == 2);
    }

    /**
     * java.io.ObjectStreamClass#getName()
     */
    public void test_getName() {
        ObjectStreamClass osc = ObjectStreamClass.lookup(FakeClass.class);
        assertEquals(
                "getName returned incorrect name: " + osc.getName(),
                "org.apache.harmony.tests.java.io.ObjectStreamClassTest$FakeClass",
                osc.getName());
    }

    /**
     * java.io.ObjectStreamClass#getSerialVersionUID()
     */
    public void test_getSerialVersionUID() {
        ObjectStreamClass osc = ObjectStreamClass.lookup(FakeClass.class);
        assertTrue("getSerialversionUID returned incorrect uid: "
                + osc.getSerialVersionUID() + " instead of "
                + FakeClass.getUID(), osc.getSerialVersionUID() == FakeClass
                .getUID());
    }

    static class SyntheticTest implements Serializable {
        private int i;

        private class X implements Serializable {
            public int get() {
                return i;
            }
        }

        public X foo() {
            return new X();
        }
    }

    /**
     * java.io.ObjectStreamClass#lookup(java.lang.Class)
     */
    public void test_lookupLjava_lang_Class() {
        ObjectStreamClass osc = ObjectStreamClass.lookup(FakeClass.class);
        assertEquals(
                "lookup returned wrong class: " + osc.getName(),
                "org.apache.harmony.tests.java.io.ObjectStreamClassTest$FakeClass",
                osc.getName());
    }

    /**
     * java.io.ObjectStreamClass#toString()
     */
    public void test_toString() {
        ObjectStreamClass osc = ObjectStreamClass.lookup(FakeClass.class);
        String oscString = osc.toString();

        // The previous test was more specific than the spec so it was replaced
        // with the test below
        assertTrue("toString returned incorrect string: " + osc.toString(),
                oscString.indexOf("serialVersionUID") >= 0
                        && oscString.indexOf("999999999999999L") >= 0);
    }

    public void testSerialization() {
        ObjectStreamClass osc = ObjectStreamClass
                .lookup(ObjectStreamClass.class);
        assertEquals(0, osc.getFields().length);
    }

    public void test_specialTypes() {
        Class<?> proxyClass = Proxy.getProxyClass(this.getClass()
                .getClassLoader(), new Class[] { Runnable.class });

        ObjectStreamClass proxyStreamClass = ObjectStreamClass
                .lookup(proxyClass);

        assertEquals("Proxy classes should have zero serialVersionUID", 0,
                proxyStreamClass.getSerialVersionUID());
        ObjectStreamField[] proxyFields = proxyStreamClass.getFields();
        assertEquals("Proxy classes should have no serialized fields", 0,
                proxyFields.length);

        ObjectStreamClass enumStreamClass = ObjectStreamClass
                .lookup(Thread.State.class);

        assertEquals("Enum classes should have zero serialVersionUID", 0,
                enumStreamClass.getSerialVersionUID());
        ObjectStreamField[] enumFields = enumStreamClass.getFields();
        assertEquals("Enum classes should have no serialized fields", 0,
                enumFields.length);
    }

    /**
     * @since 1.6
     */
    static class NonSerialzableClass {
        private static final long serialVersionUID = 1l;

        public static long getUID() {
            return serialVersionUID;
        }
    }

    /**
     * @since 1.6
     */
    static class ExternalizableClass implements Externalizable {

        private static final long serialVersionUID = -4285635779249689129L;

        public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
            throw new ClassNotFoundException();
        }

        public void writeExternal(ObjectOutput output) throws IOException {
            throw new IOException();
        }

    }

    /**
     * java.io.ObjectStreamClass#lookupAny(java.lang.Class)
     * @since 1.6
     */
    public void test_lookupAnyLjava_lang_Class() {
        // Test for method java.io.ObjectStreamClass
        // java.io.ObjectStreamClass.lookupAny(java.lang.Class)
        ObjectStreamClass osc = ObjectStreamClass.lookupAny(FakeClass.class);
        assertEquals("lookup returned wrong class: " + osc.getName(),
                "org.apache.harmony.tests.java.io.ObjectStreamClassTest$FakeClass", osc
                .getName());

        osc = ObjectStreamClass.lookupAny(NonSerialzableClass.class);
        assertEquals("lookup returned wrong class: " + osc.getName(),
                "org.apache.harmony.tests.java.io.ObjectStreamClassTest$NonSerialzableClass",
                osc.getName());

        osc = ObjectStreamClass.lookupAny(ExternalizableClass.class);
        assertEquals("lookup returned wrong class: " + osc.getName(),
                "org.apache.harmony.tests.java.io.ObjectStreamClassTest$ExternalizableClass",
                osc.getName());

        osc = ObjectStreamClass.lookup(NonSerialzableClass.class);
        assertNull(osc);
    }

    // http://b/28106822
    @TargetSdkVersion(24)
    public void testBug28106822_target24() throws Exception {
        // Assert behavior up to 24
        Method getConstructorId = getConstructorIdMethod();

        assertEquals(1189998819991197253L, getConstructorId.invoke(null, Object.class));
        assertEquals(1189998819991197253L, getConstructorId.invoke(null, String.class));

        Method newInstance = getNewInstanceMethod();

        Object obj = newInstance.invoke(null, String.class, 0 /* ignored */);
        assertNotNull(obj);
        assertTrue(obj instanceof String);
    }

    // http://b/28106822
    @TargetSdkVersion(25)
    public void testBug28106822_target25() throws Exception {
        // Assert behavior from API 25
        Method getConstructorId = getConstructorIdMethod();

        Method newInstance = getNewInstanceMethod();

        try {
            getConstructorId.invoke(null, Object.class);
            fail();
        } catch (InvocationTargetException expected) {
            assertTrue(expected.getCause() instanceof UnsupportedOperationException);
        }
        try {
            newInstance.invoke(null, String.class, 0 /* ignored */);
            fail();
        } catch (InvocationTargetException expected) {
            assertTrue(expected.getCause() instanceof UnsupportedOperationException);
        }
    }

    private Method getConstructorIdMethod() throws NoSuchMethodException {
        Method getConstructorId = ObjectStreamClass.class.getDeclaredMethod(
            "getConstructorId", Class.class);
        getConstructorId.setAccessible(true);
        return getConstructorId;
    }

    private Method getNewInstanceMethod() throws NoSuchMethodException {
        Method newInstance = ObjectStreamClass.class.getDeclaredMethod("newInstance",
            Class.class, Long.TYPE);
        newInstance.setAccessible(true);
        return newInstance;
    }

    // http://b/29721023
    public void testClassWithSameFieldName() throws Exception {
        // Load class from dex, it's not possible to create a class with same-named
        // fields in java (but it's allowed in dex).
        File sameFieldNames = File.createTempFile("sameFieldNames", ".dex");
        InputStream dexIs = this.getClass().getClassLoader().
            getResourceAsStream("tests/api/java/io/sameFieldNames.dex");
        assertNotNull(dexIs);

        try {
            Files.copy(dexIs, sameFieldNames.toPath(), StandardCopyOption.REPLACE_EXISTING);
            DexFile dexFile = new DexFile(sameFieldNames);
            Class<?> clazz = dexFile.loadClass("sameFieldNames", getClass().getClassLoader());
            ObjectStreamClass osc = ObjectStreamClass.lookup(clazz);
            assertEquals(4, osc.getFields().length);
            dexFile.close();
        } finally {
            if (sameFieldNames.exists()) {
                sameFieldNames.delete();
            }
        }
    }
}
