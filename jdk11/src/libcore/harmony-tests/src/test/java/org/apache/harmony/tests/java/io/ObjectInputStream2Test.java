/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.harmony.tests.java.io;

import dalvik.system.DexFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.harmony.testframework.serialization.SerializationTest;

public class ObjectInputStream2Test extends TestCase {

    public void test_readUnshared() throws IOException, ClassNotFoundException {
        // Regression test for HARMONY-819
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject("abc");
            oos.writeObject("abc");
            oos.close();

            ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(baos.toByteArray()));
            ois.readUnshared();
            ois.readObject();
            ois.close();
            fail("Expected ObjectStreamException");
        } catch (ObjectStreamException e) {
            // expected
        }
    }

    /**
     * Micro-scenario of de/serialization of an object with non-serializable
     * superclass. The super-constructor only should be invoked on the
     * deserialized instance.
     */
    public void test_readObject_Hierarchy() throws IOException,
            ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(new B());
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(
                baos.toByteArray()));
        B b = (B) ois.readObject();
        ois.close();

        assertTrue("should construct super", A.list.contains(b));
        assertFalse("should not construct self", B.list.contains(b));
        assertEquals("super field A.s", A.DEFAULT, ((A) b).s);
        assertNull("transient field B.s", b.s);
    }

    /**
     * {@link java.io.ObjectInputStream#readNewLongString()}
     */
    public void test_readNewLongString() throws Exception {
        LongString longString = new LongString();
        SerializationTest.verifySelf(longString);
    }

    @SuppressWarnings("serial")
    private static class LongString implements Serializable {
        String lString;

        public LongString() {
            StringBuilder builder = new StringBuilder();
            // construct a string whose length > 64K
            for (int i = 0; i < 65636; i++) {
                builder.append('1');
            }
            lString = builder.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof LongString) {
                LongString l = (LongString) o;
                return l.lString.equals(l.lString);
            }
            return true;
        }

        @Override
        public int hashCode() {
            return lString.hashCode();
        }
    }

    static class A {
        static final ArrayList<A> list = new ArrayList<A>();
        String s;
        public static final String DEFAULT = "aaa";

        public A() {
            s = DEFAULT;
            list.add(this);
        }
    }

    static class B extends A implements Serializable {
        private static final long serialVersionUID = 1L;
        static final ArrayList<A> list = new ArrayList<A>();
        transient String s;

        public B() {
            s = "bbb";
            list.add(this);
        }
    }

    class OIS extends ObjectInputStream {

        OIS() throws IOException {
            super();
        }

        void test() throws ClassNotFoundException, IOException {
            readClassDescriptor();
        }

    }

    public void test_readClassDescriptor() throws ClassNotFoundException,
            IOException {
        try {
            new OIS().test();
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static class TestObjectInputStream extends ObjectInputStream {
        public TestObjectInputStream(InputStream in) throws IOException {
            super(in);
        }

        @Override
        @SuppressWarnings("unchecked")
        protected Class resolveClass(ObjectStreamClass desc)
                throws IOException, ClassNotFoundException {
            if (desc.getName().endsWith("ObjectInputStream2Test$TestClass1")) {
                return TestClass2.class;
            }
            return super.resolveClass(desc);
        }
    }

    static class TestClass1 implements Serializable {
        private static final long serialVersionUID = 11111L;
        int i = 0;
    }

    static class TestClass2 implements Serializable {
        private static final long serialVersionUID = 11111L;
        int i = 0;
    }

    public void test_resolveClass_invalidClassName() throws Exception {
        // Regression test for HARMONY-1920
        TestClass1 to1 = new TestClass1();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        ByteArrayInputStream bais;
        ObjectInputStream ois;

        to1.i = 555;
        oos.writeObject(to1);
        oos.flush();
        byte[] bytes = baos.toByteArray();
        bais = new ByteArrayInputStream(bytes);
        ois = new TestObjectInputStream(bais);

        try {
            ois.readObject();
            fail("Should throw InvalidClassException");
        } catch (InvalidClassException ice) {
            // Excpected
        }
    }

    // http://b/29721023
    public void test_sameName() throws Exception {
        // Load class from dex, it's not possible to create a class with same-named
        // fields in java (but it's allowed in dex).
        File sameFieldNames = File.createTempFile("sameFieldNames", ".dex");
        InputStream dexIs = this.getClass().getClassLoader().
            getResourceAsStream("tests/api/java/io/sameFieldNames.dex");
        assertNotNull(dexIs);

        Class<?> clazz = null;

        // Get the class object
        try {
            Files.copy(dexIs, sameFieldNames.toPath(), StandardCopyOption.REPLACE_EXISTING);
            DexFile dexFile = new DexFile(sameFieldNames);
            clazz = dexFile.loadClass("sameFieldNames", getClass().getClassLoader());
            dexFile.close();
        } finally {
            if (sameFieldNames.exists()) {
                sameFieldNames.delete();
            }
        }

        // Create class instance, fill it with content
        Object o1 = clazz.getConstructor().newInstance();
        int v = 123;
        for(Field f : clazz.getFields()) {
            if (f.getType() == Integer.class) {
                f.set(o1, new Integer(v++));
            } else if (f.getType() == Long.class) {
                f.set(o1, new Long(v++));
            }
        }

        // Serialize and deserialize
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o1);
        oos.close();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(
                baos.toByteArray()));
        Object o2 = ois.readObject();
        ois.close();

        // Compare content
        for(Field f : clazz.getFields()) {
            assertEquals(f.get(o1), f.get(o2));
        }
    }
}
