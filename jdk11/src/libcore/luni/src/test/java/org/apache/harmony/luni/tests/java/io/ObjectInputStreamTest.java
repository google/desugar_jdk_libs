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

package org.apache.harmony.luni.tests.java.io;

import junit.framework.TestCase;
import org.apache.harmony.testframework.serialization.SerializationTest;
import org.apache.harmony.testframework.serialization.SerializationTest.SerializableAssert;
import tests.support.Support_ASimpleInputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

@SuppressWarnings("serial")
public class ObjectInputStreamTest extends TestCase implements
        Serializable {

    public class SerializableTestHelper implements Serializable {

        public String aField1;

        public String aField2;

        SerializableTestHelper() {
            aField1 = null;
            aField2 = null;
        }

        SerializableTestHelper(String s, String t) {
            aField1 = s;
            aField2 = t;
        }

        private void readObject(ObjectInputStream ois) throws Exception {
            // note aField2 is not read
            ObjectInputStream.GetField fields = ois.readFields();
            aField1 = (String) fields.get("aField1", "Zap");
        }

        private void writeObject(ObjectOutputStream oos) throws IOException {
            // note aField2 is not written
            ObjectOutputStream.PutField fields = oos.putFields();
            fields.put("aField1", aField1);
            oos.writeFields();
        }

        public String getText1() {
            return aField1;
        }

        public void setText1(String s) {
            aField1 = s;
        }

        public String getText2() {
            return aField2;
        }

        public void setText2(String s) {
            aField2 = s;
        }
    }

    public static class A1 implements Serializable {

        private static final long serialVersionUID = 5942584913446079661L;

        B1 b1 = new B1();

        B1 b2 = b1;

        Vector v = new Vector();
    }

    public static class B1 implements Serializable {

        int i = 5;

        Hashtable h = new Hashtable();
    }

    static final long serialVersionUID = 1L;

    ObjectInputStream ois;

    ObjectOutputStream oos;

    ByteArrayOutputStream bao;

    boolean readStreamHeaderCalled;

    private final String testString = "Lorem ipsum...";

    private final int testLength = testString.length();

    public void test_ConstructorLjava_io_InputStream_IOException() throws IOException {
        oos.writeObject(testString);
        oos.close();

        Support_ASimpleInputStream sis = new Support_ASimpleInputStream(bao.toByteArray());
        sis.throwExceptionOnNextUse = true;
        try {
            ois = new ObjectInputStream(sis);
            fail("Test 1: IOException expected.");
        } catch (IOException e) {
            // Expected.
        }
        sis.throwExceptionOnNextUse = false;
    }

    public void test_ClassDescriptor() throws IOException,
            ClassNotFoundException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStreamWithWriteDesc oos = new ObjectOutputStreamWithWriteDesc(
                baos);
        oos.writeObject(String.class);
        oos.close();
        Class<?> cls = TestClassForSerialization.class;
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStreamWithReadDesc ois = new ObjectInputStreamWithReadDesc(
                bais, cls);
        Object obj = ois.readObject();
        ois.close();
        assertEquals(cls, obj);
    }

    public void test_available_IOException() throws IOException {
        oos.writeObject(testString);
        oos.close();

        Support_ASimpleInputStream sis = new Support_ASimpleInputStream(bao.toByteArray());
        ois = new ObjectInputStream(sis);
        sis.throwExceptionOnNextUse = true;
        try {
            ois.available();
            fail("Test 1: IOException expected.");
        } catch (IOException e) {
            // Expected.
        }
        sis.throwExceptionOnNextUse = false;
        ois.close();
    }

    public void test_close() throws Exception {
        // Test for method void java.io.ObjectInputStream.close()
        oos.writeObject(testString);
        oos.close();

        Support_ASimpleInputStream sis = new Support_ASimpleInputStream(bao.toByteArray());
        ois = new ObjectInputStream(sis);
        sis.throwExceptionOnNextUse = true;
        try {
            ois.close();
            fail("Test 1: IOException expected.");
        } catch (IOException e) {
            // Expected.
        }
        sis.throwExceptionOnNextUse = false;
        ois.close();
    }

    public void test_enableResolveObjectB() throws IOException {
        // Start testing without a SecurityManager.
        BasicObjectInputStream bois = new BasicObjectInputStream();
        assertFalse("Test 1: Object resolving must be disabled by default.",
                bois.enableResolveObject(true));

        assertTrue("Test 2: enableResolveObject did not return the previous value.",
                bois.enableResolveObject(false));
    }

    public void test_read_IOException() throws IOException {
        oos.writeObject(testString);
        oos.close();

        Support_ASimpleInputStream sis = new Support_ASimpleInputStream(bao.toByteArray());
        ois = new ObjectInputStream(sis);
        sis.throwExceptionOnNextUse = true;
        try {
            ois.read();
            fail("Test 1: IOException expected.");
        } catch (IOException e) {
            // Expected.
        }
        sis.throwExceptionOnNextUse = false;
        ois.close();
    }

    public void test_read$BII_Exception() throws IOException {
        byte[] buf = new byte[testLength];
        oos.writeObject(testString);
        oos.close();

        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        try {
            ois.read(buf, 0, -1);
            fail("IndexOutOfBoundsException was not thrown.");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
        try {
            ois.read(buf, -1,1);
            fail("IndexOutOfBoundsException was not thrown.");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
        try {
            ois.read(buf, testLength, 1);
            fail("IndexOutOfBoundsException was not thrown.");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
        ois.close();


        Support_ASimpleInputStream sis = new Support_ASimpleInputStream(bao.toByteArray());
        ois = new ObjectInputStream(sis);
        sis.throwExceptionOnNextUse = true;
        try {
            ois.read(buf, 0, testLength);
            fail("Test 1: IOException expected.");
        } catch (IOException e) {
            // Expected.
        }
        sis.throwExceptionOnNextUse = false;
        ois.close();
    }

    public void test_readFully$B() throws IOException {
        byte[] buf = new byte[testLength];
        oos.writeBytes(testString);
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        ois.readFully(buf);
        assertEquals("Test 1: Incorrect bytes read;",
                testString, new String(buf));
        ois.close();

        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        ois.read();
        try {
            ois.readFully(buf);
            fail("Test 2: EOFException expected.");
        } catch (EOFException e) {
            // Expected.
        }
    }

    public void test_readFully$B_Exception() throws IOException {
        byte[] buf = new byte[testLength];
        oos.writeObject(testString);
        oos.close();

        Support_ASimpleInputStream sis = new Support_ASimpleInputStream(bao.toByteArray());
        ois = new ObjectInputStream(sis);
        sis.throwExceptionOnNextUse = true;
        try {
            ois.readFully(buf);
            fail("Test 1: IOException expected.");
        } catch (IOException e) {
            // Expected.
        }
        sis.throwExceptionOnNextUse = false;
        ois.close();
    }

    public void test_readFully$BII() throws IOException {
        // Test for method void java.io.ObjectInputStream.readFully(byte [],
        // int, int)
        byte[] buf = new byte[testLength];
        oos.writeBytes(testString);
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        ois.readFully(buf, 0, testLength);
        assertEquals("Read incorrect bytes", testString, new String(buf));
        ois.close();

        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        ois.read();
        try {
            ois.readFully(buf);
            fail("Test 2: EOFException expected.");
        } catch (EOFException e) {
            // Expected.
        }
    }

    public void test_readFully$BII_Exception() throws IOException {
        byte[] buf = new byte[testLength];
        oos.writeObject(testString);
        oos.close();

        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        try {
            ois.readFully(buf, 0, -1);
            fail("IndexOutOfBoundsException was not thrown.");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
        try {
            ois.readFully(buf, -1,1);
            fail("IndexOutOfBoundsException was not thrown.");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
        try {
            ois.readFully(buf, testLength, 1);
            fail("IndexOutOfBoundsException was not thrown.");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
        ois.close();

        Support_ASimpleInputStream sis = new Support_ASimpleInputStream(bao.toByteArray());
        ois = new ObjectInputStream(sis);
        sis.throwExceptionOnNextUse = true;
        try {
            ois.readFully(buf, 0, 1);
            fail("Test 1: IOException expected.");
        } catch (IOException e) {
            // Expected.
        }
        sis.throwExceptionOnNextUse = false;
        ois.close();
    }

    public void test_readLine_IOException() throws IOException {
        oos.writeObject(testString);
        oos.close();

        Support_ASimpleInputStream sis = new Support_ASimpleInputStream(bao.toByteArray());
        ois = new ObjectInputStream(sis);
        sis.throwExceptionOnNextUse = true;
        try {
            ois.readLine();
            fail("Test 1: IOException expected.");
        } catch (IOException e) {
            // Expected.
        }
        sis.throwExceptionOnNextUse = false;
        ois.close();
    }

    private void fillStreamHeader(byte[] buffer) {
        short magic = java.io.ObjectStreamConstants.STREAM_MAGIC;
        short version = java.io.ObjectStreamConstants.STREAM_VERSION;

        if (buffer.length < 4) {
            throw new IllegalArgumentException("The buffer's minimal length must be 4.");
        }

        // Initialize the buffer with the correct header for object streams
        buffer[0] = (byte) (magic >> 8);
        buffer[1] = (byte) magic;
        buffer[2] = (byte) (version >> 8);
        buffer[3] = (byte) (version);
    }

    public void test_readObjectOverride() throws Exception {
        byte[] buffer = new byte[4];

        // Initialize the buffer with the correct header for object streams
        fillStreamHeader(buffer);

        // Test 1: Check that readObjectOverride() returns null if there
        // is no input stream.
        BasicObjectInputStream bois = new BasicObjectInputStream();
        assertNull("Test 1:", bois.readObjectOverride());

        // Test 2: Check that readObjectOverride() returns null if it isn't overriden.
        //
        // On Android M and below, this method threw an IOException.
        bois = new BasicObjectInputStream(new ByteArrayInputStream(buffer));
        assertNull(bois.readObjectOverride());
        bois.close();
    }

    public void test_readObjectMissingClasses() throws Exception {
        SerializationTest.verifySelf(new A1(), new SerializableAssert() {
            public void assertDeserialized(Serializable initial,
                    Serializable deserialized) {
                assertEquals(5, ((A1) deserialized).b1.i);
            }
        });
    }

    public void test_readStreamHeader() throws IOException {
        String testString = "Lorem ipsum";
        BasicObjectInputStream bois;
        short magic = java.io.ObjectStreamConstants.STREAM_MAGIC;
        short version = java.io.ObjectStreamConstants.STREAM_VERSION;
        byte[] buffer = new byte[20];

        // Initialize the buffer with the correct header for object streams
        fillStreamHeader(buffer);
        System.arraycopy(testString.getBytes(), 0, buffer, 4, testString.length());

        // Test 1: readStreamHeader should not throw a StreamCorruptedException.
        // It should get called by the ObjectInputStream constructor.
        try {
            readStreamHeaderCalled = false;
            bois = new BasicObjectInputStream(new ByteArrayInputStream(buffer));
            bois.close();
        } catch (StreamCorruptedException e) {
            fail("Test 1: Unexpected StreamCorruptedException.");
        }
        assertTrue("Test 1: readStreamHeader() has not been called.",
                    readStreamHeaderCalled);

        // Test 2: Make the stream magic number invalid and check that
        // readStreamHeader() throws an exception.
        buffer[0] = (byte)magic;
        buffer[1] = (byte)(magic >> 8);
        try {
            readStreamHeaderCalled = false;
            bois = new BasicObjectInputStream(new ByteArrayInputStream(buffer));
            fail("Test 2: StreamCorruptedException expected.");
            bois.close();
        } catch (StreamCorruptedException e) {
        }
        assertTrue("Test 2: readStreamHeader() has not been called.",
                    readStreamHeaderCalled);

        // Test 3: Make the stream version invalid and check that
        // readStreamHeader() throws an exception.
        buffer[0] = (byte)(magic >> 8);
        buffer[1] = (byte)magic;
        buffer[2] = (byte)(version);
        buffer[3] = (byte)(version >> 8);
        try {
            readStreamHeaderCalled = false;
            bois = new BasicObjectInputStream(new ByteArrayInputStream(buffer));
            fail("Test 3: StreamCorruptedException expected.");
            bois.close();
        } catch (StreamCorruptedException e) {
        }
        assertTrue("Test 3: readStreamHeader() has not been called.",
                    readStreamHeaderCalled);
    }

    public void test_readUnsignedByte() throws IOException {
        oos.writeByte(-1);
        oos.close();

        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertEquals("Test 1: Incorrect unsigned byte written or read.",
                255, ois.readUnsignedByte());

        try {
            ois.readUnsignedByte();
            fail("Test 2: EOFException expected.");
        } catch (EOFException e) {
            // Expected.
        }

        ois.close();
        try {
            ois.readUnsignedByte();
            fail("Test 3: IOException expected.");
        } catch (IOException e) {
            // Expected.
        }
    }

    public void test_readUnsignedShort() throws IOException {
        // Test for method int java.io.ObjectInputStream.readUnsignedShort()
        oos.writeShort(-1);
        oos.close();

        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertEquals("Test 1: Incorrect unsigned short written or read.",
                65535, ois.readUnsignedShort());

        try {
            ois.readUnsignedShort();
            fail("Test 2: EOFException expected.");
        } catch (EOFException e) {
            // Expected.
        }

        ois.close();
        try {
            ois.readUnsignedShort();
            fail("Test 3: IOException expected.");
        } catch (IOException e) {
            // Expected.
        }
    }

    public void test_skipBytesI_IOException() throws IOException {
        oos.writeObject(testString);
        oos.close();

        Support_ASimpleInputStream sis = new Support_ASimpleInputStream(bao.toByteArray());
        ois = new ObjectInputStream(sis);
        sis.throwExceptionOnNextUse = true;
        try {
            ois.skipBytes(5);
            fail("Test 1: IOException expected.");
        } catch (IOException e) {
            // Expected.
        }
        sis.throwExceptionOnNextUse = false;
        ois.close();
    }

    public static class A implements Serializable {

        private static final long serialVersionUID = 11L;

        public String name = "name";
    }

    public static class B extends A {}

    public static class C extends B {

        private static final long serialVersionUID = 33L;
    }

    class BasicObjectInputStream extends ObjectInputStream {
        public BasicObjectInputStream() throws IOException, SecurityException {
            super();
        }

        public BasicObjectInputStream(InputStream input) throws IOException {
            super(input);
        }

        public boolean enableResolveObject(boolean enable)
                throws SecurityException {
            return super.enableResolveObject(enable);
        }

        public Object readObjectOverride() throws ClassNotFoundException, IOException {
            return super.readObjectOverride();
        }

        public void readStreamHeader() throws IOException {
            readStreamHeaderCalled = true;
            super.readStreamHeader();
        }

        public Class<?> resolveProxyClass(String[] interfaceNames)
                throws IOException, ClassNotFoundException {
            return super.resolveProxyClass(interfaceNames);
        }
    }

    public static class ObjectInputStreamWithReadDesc extends
            ObjectInputStream {
        private Class returnClass;

        public ObjectInputStreamWithReadDesc(InputStream is, Class returnClass)
                throws IOException {
            super(is);
            this.returnClass = returnClass;
        }

        public ObjectStreamClass readClassDescriptor() throws IOException,
                ClassNotFoundException {
            return ObjectStreamClass.lookup(returnClass);

        }
    }

    static class TestClassForSerialization implements Serializable {
        private static final long serialVersionUID = 1L;
    }

    public void test_ConstructorLjava_io_InputStream() throws IOException {
        oos.writeDouble(Double.MAX_VALUE);
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        ois.close();
        oos.close();

        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(new byte[90]));
            fail("StreamCorruptedException expected");
        } catch (StreamCorruptedException e) {
            // Expected
        }
    }

    /**
     * {@link java.io.ObjectInputStream#resolveProxyClass(String[])}
     */
    public void test_resolveProxyClass() throws IOException,
            ClassNotFoundException {
        oos.writeBytes("HelloWorld");
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        MockObjectInputStream mockIn = new MockObjectInputStream(
                new ByteArrayInputStream(bao.toByteArray()));
        Class[] clazzs = { java.io.ObjectInputStream.class,
                java.io.Reader.class };
        for (int i = 0; i < clazzs.length; i++) {
            Class clazz = clazzs[i];
            Class[] interfaceNames = clazz.getInterfaces();
            String[] interfaces = new String[interfaceNames.length];
            int index = 0;
            for (Class c : interfaceNames) {
                interfaces[index] = c.getName();
                index++;
            }
            Class<?> s = mockIn.resolveProxyClass(interfaces);

            if (Proxy.isProxyClass(s)) {
                Class[] implementedInterfaces = s.getInterfaces();
                for (index = 0; index < implementedInterfaces.length; index++) {
                    assertEquals(interfaceNames[index],
                            implementedInterfaces[index]);
                }
            } else {
                fail("Should return a proxy class that implements the interfaces named in a proxy class descriptor");
            }
        }
        mockIn.close();
    }

    class MockObjectInputStream extends ObjectInputStream {

        public MockObjectInputStream(InputStream input)
                throws StreamCorruptedException, IOException {
            super(input);
        }

        @Override
        public Class<?> resolveProxyClass(String[] interfaceNames) throws IOException, ClassNotFoundException {
            return super.resolveProxyClass(interfaceNames);
        }

    }

    public void test_available() throws IOException {
        oos.writeBytes("HelloWorld");
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertEquals("Read incorrect bytes", 10, ois.available());
        ois.close();
    }

    /**
     * java.io.ObjectInputStream#defaultReadObject()
     */
    public void test_defaultReadObject() throws Exception {
        // SM. This method may as well be private, as if called directly it
        // throws an exception.
        String s = "HelloWorld";
        oos.writeObject(s);
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        try {
            ois.defaultReadObject();
            fail("NotActiveException expected");
        } catch (NotActiveException e) {
            // Desired behavior
        } finally {
            ois.close();
        }
    }

    /**
     * java.io.ObjectInputStream#read()
     */
    public void test_read() throws IOException {
        oos.write('T');
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertEquals("Read incorrect byte value", 'T', ois.read());
        ois.close();
    }

    /**
     * java.io.ObjectInputStream#read(byte[], int, int)
     */
    public void test_read$BII() throws IOException {
        byte[] buf = new byte[10];
        oos.writeBytes("HelloWorld");
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        ois.read(buf, 0, 10);
        ois.close();
        assertEquals("Read incorrect bytes", "HelloWorld", new String(buf, 0,
                10, "UTF-8"));
    }

    /**
     * java.io.ObjectInputStream#readBoolean()
     */
    public void test_readBoolean() throws IOException {
        oos.writeBoolean(true);
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertTrue("Read incorrect boolean value", ois.readBoolean());
        ois.close();
    }

    /**
     * java.io.ObjectInputStream#readByte()
     */
    public void test_readByte() throws IOException {
        oos.writeByte(127);
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertEquals("Read incorrect byte value", 127, ois.readByte());
        ois.close();
    }

    /**
     * java.io.ObjectInputStream#readChar()
     */
    public void test_readChar() throws IOException {
        oos.writeChar('T');
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertEquals("Read incorrect char value", 'T', ois.readChar());
        ois.close();
    }

    /**
     * java.io.ObjectInputStream#readDouble()
     */
    public void test_readDouble() throws IOException {
        oos.writeDouble(Double.MAX_VALUE);
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertTrue("Read incorrect double value",
                ois.readDouble() == Double.MAX_VALUE);
        ois.close();
    }

    /**
     * java.io.ObjectInputStream#readFields()
     */
    public void test_readFields() throws Exception {

        SerializableTestHelper sth;

        /*
         * "SerializableTestHelper" is an object created for these tests with
         * two fields (Strings) and simple implementations of readObject and
         * writeObject which simply read and write the first field but not the
         * second
         */

        oos.writeObject(new SerializableTestHelper("Gabba", "Jabba"));
        oos.flush();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        sth = (SerializableTestHelper) (ois.readObject());
        assertEquals("readFields / writeFields failed--first field not set",
                "Gabba", sth.getText1());
        assertNull(
                "readFields / writeFields failed--second field should not have been set",
                sth.getText2());
    }

    /**
     * java.io.ObjectInputStream#readFloat()
     */
    public void test_readFloat() throws IOException {
        oos.writeFloat(Float.MAX_VALUE);
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertTrue("Read incorrect float value",
                ois.readFloat() == Float.MAX_VALUE);
        ois.close();
    }

    /**
     * java.io.ObjectInputStream#readInt()
     */
    public void test_readInt() throws IOException {
        oos.writeInt(Integer.MAX_VALUE);
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertTrue("Read incorrect int value",
                ois.readInt() == Integer.MAX_VALUE);
        ois.close();
    }

    /**
     * java.io.ObjectInputStream#readLine()
     */
    @SuppressWarnings("deprecation")
    public void test_readLine() throws IOException {
        oos.writeBytes("HelloWorld\nSecondLine");
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        ois.readLine();
        assertEquals("Read incorrect string value", "SecondLine", ois
                .readLine());
        ois.close();
    }

    /**
     * java.io.ObjectInputStream#readLong()
     */
    public void test_readLong() throws IOException {
        oos.writeLong(Long.MAX_VALUE);
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertTrue("Read incorrect long value",
                ois.readLong() == Long.MAX_VALUE);
        ois.close();
    }

    /**
     * java.io.ObjectInputStream#readObject()
     */
    public void test_readObject() throws Exception {
        String s = "HelloWorld";
        oos.writeObject(s);
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertEquals("Read incorrect Object value", s, ois.readObject());
        ois.close();

        // Regression for HARMONY-91
        // dynamically create serialization byte array for the next hierarchy:
        // - class A implements Serializable
        // - class C extends A

        byte[] cName = C.class.getName().getBytes("UTF-8");
        byte[] aName = A.class.getName().getBytes("UTF-8");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] begStream = new byte[] { (byte) 0xac, (byte) 0xed, // STREAM_MAGIC
                (byte) 0x00, (byte) 0x05, // STREAM_VERSION
                (byte) 0x73, // TC_OBJECT
                (byte) 0x72, // TC_CLASSDESC
                (byte) 0x00, // only first byte for C class name length
        };

        out.write(begStream, 0, begStream.length);
        out.write(cName.length); // second byte for C class name length
        out.write(cName, 0, cName.length); // C class name

        byte[] midStream = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x21, // serialVersionUID = 33L
                (byte) 0x02, // flags
                (byte) 0x00, (byte) 0x00, // fields : none
                (byte) 0x78, // TC_ENDBLOCKDATA
                (byte) 0x72, // Super class for C: TC_CLASSDESC for A class
                (byte) 0x00, // only first byte for A class name length
        };

        out.write(midStream, 0, midStream.length);
        out.write(aName.length); // second byte for A class name length
        out.write(aName, 0, aName.length); // A class name

        byte[] endStream = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x0b, // serialVersionUID = 11L
                (byte) 0x02, // flags
                (byte) 0x00, (byte) 0x01, // fields

                (byte) 0x4c, // field description: type L (object)
                (byte) 0x00, (byte) 0x04, // length
                // field = 'name'
                (byte) 0x6e, (byte) 0x61, (byte) 0x6d, (byte) 0x65,

                (byte) 0x74, // className1: TC_STRING
                (byte) 0x00, (byte) 0x12, // length
                //
                (byte) 0x4c, (byte) 0x6a, (byte) 0x61, (byte) 0x76,
                (byte) 0x61, (byte) 0x2f, (byte) 0x6c, (byte) 0x61,
                (byte) 0x6e, (byte) 0x67, (byte) 0x2f, (byte) 0x53,
                (byte) 0x74, (byte) 0x72, (byte) 0x69, (byte) 0x6e,
                (byte) 0x67, (byte) 0x3b,

                (byte) 0x78, // TC_ENDBLOCKDATA
                (byte) 0x70, // NULL super class for A class

                // classdata
                (byte) 0x74, // TC_STRING
                (byte) 0x00, (byte) 0x04, // length
                (byte) 0x6e, (byte) 0x61, (byte) 0x6d, (byte) 0x65, // value
        };

        out.write(endStream, 0, endStream.length);
        out.flush();

        // read created serial. form
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(
                out.toByteArray()));
        Object o = ois.readObject();
        assertEquals(C.class, o.getClass());

		// Regression for HARMONY-846
        assertNull(new ObjectInputStream() {}.readObject());
    }

    /**
     * java.io.ObjectInputStream#readObject()
     */
    public void test_readObjectCorrupt() throws IOException, ClassNotFoundException {
        byte[] bytes = { 00, 00, 00, 0x64, 0x43, 0x48, (byte) 0xFD, 0x71, 00,
                00, 0x0B, (byte) 0xB8, 0x4D, 0x65 };
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream in = new ObjectInputStream(bin);
            in.readObject();
            fail("Unexpected read of corrupted stream");
        } catch (StreamCorruptedException e) {
            // Expected
        }
    }

    /**
     * java.io.ObjectInputStream#readShort()
     */
    public void test_readShort() throws IOException {
        oos.writeShort(Short.MAX_VALUE);
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertTrue("Read incorrect short value",
                ois.readShort() == Short.MAX_VALUE);
        ois.close();
    }

    /**
     * java.io.ObjectInputStream#readUTF()
     */
    public void test_readUTF() throws IOException {
        oos.writeUTF("HelloWorld");
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        assertEquals("Read incorrect utf value", "HelloWorld", ois.readUTF());
        ois.close();
    }

    /**
     * java.io.ObjectInputStream#skipBytes(int)
     */
    public void test_skipBytesI() throws IOException {
        byte[] buf = new byte[10];
        oos.writeBytes("HelloWorld");
        oos.close();
        ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        ois.skipBytes(5);
        ois.read(buf, 0, 5);
        ois.close();
        assertEquals("Skipped incorrect bytes", "World", new String(buf, 0, 5, "UTF-8"));

        // Regression for HARMONY-844
        try {
            new ObjectInputStream() {
            }.skipBytes(0);
            fail("NullPointerException expected");
        } catch (NullPointerException e) {
        }
    }

    // Regression Test for JIRA 2192
    public void test_readObject_withPrimitiveClass() throws Exception {
        File file = File.createTempFile("ObjectInputStreamTest", ".ser");
        Test test = new Test();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
                file));
        out.writeObject(test);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        Test another = (Test) in.readObject();
        in.close();
        assertEquals(test, another);
    }

    //Regression Test for JIRA-2249
    public static class ObjectOutputStreamWithWriteDesc extends
            ObjectOutputStream {
        public ObjectOutputStreamWithWriteDesc(OutputStream os)
                throws IOException {
            super(os);
        }

        @Override
        public void writeClassDescriptor(ObjectStreamClass desc)
                throws IOException {
        }
    }

    // Regression Test for JIRA-2340
    public static class ObjectOutputStreamWithWriteDesc1 extends
            ObjectOutputStream {
        public ObjectOutputStreamWithWriteDesc1(OutputStream os)
                throws IOException {
            super(os);
        }

        @Override
        public void writeClassDescriptor(ObjectStreamClass desc)
                throws IOException {
            super.writeClassDescriptor(desc);
        }
    }

    public static class ObjectInputStreamWithReadDesc1 extends
            ObjectInputStream {

        public ObjectInputStreamWithReadDesc1(InputStream is)
                throws IOException {
            super(is);
        }

        @Override
        public ObjectStreamClass readClassDescriptor() throws IOException,
                ClassNotFoundException {
            return super.readClassDescriptor();
        }
    }

    // Regression test for Harmony-1921
    public static class ObjectInputStreamWithResolve extends ObjectInputStream {
        public ObjectInputStreamWithResolve(InputStream in) throws IOException {
            super(in);
        }

        @Override
        @SuppressWarnings("unchecked")
        protected Class resolveClass(ObjectStreamClass desc)
                throws IOException, ClassNotFoundException {
            if (desc.getName().equals(
                    "org.apache.harmony.luni.tests.pkg1.TestClass")) {
                return org.apache.harmony.luni.tests.pkg2.TestClass.class;
            }
            return super.resolveClass(desc);
        }
    }

    public void test_resolveClass() throws Exception {
        org.apache.harmony.luni.tests.pkg1.TestClass to1 = new org.apache.harmony.luni.tests.pkg1.TestClass();
        to1.i = 555;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(to1);
        oos.flush();
        byte[] bytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStreamWithResolve(bais);
        org.apache.harmony.luni.tests.pkg2.TestClass to2 = (org.apache.harmony.luni.tests.pkg2.TestClass) ois
                .readObject();

        if (to2.i != to1.i) {
            fail("Wrong object read. Expected val: " + to1.i + ", got: "
                    + to2.i);
        }
    }

    static class ObjectInputStreamWithResolveObject extends ObjectInputStream {

        public static Integer intObj = Integer.valueOf(1000);

        public ObjectInputStreamWithResolveObject(InputStream in) throws IOException {
            super(in);
            enableResolveObject(true);
        }

        @Override
        protected Object resolveObject(Object obj) throws IOException {
            if (obj instanceof Integer) {
                obj = intObj;
            }
            return super.resolveObject(obj);
        }
    }

    /**
     * java.io.ObjectInputStream#resolveObject(Object)
     */
    public void test_resolveObjectLjava_lang_Object() throws Exception {
        // Write an Integer object into memory
        Integer original = new Integer(10);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(original);
        oos.flush();
        oos.close();

        // Read the object from memory
        byte[] bytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStreamWithResolveObject ois =
                new ObjectInputStreamWithResolveObject(bais);
        Integer actual = (Integer) ois.readObject();
        ois.close();

        // object should be resolved from 10 to 1000
        assertEquals(ObjectInputStreamWithResolveObject.intObj, actual);
    }

    public void test_readClassDescriptor() throws IOException,
            ClassNotFoundException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStreamWithWriteDesc1 oos = new ObjectOutputStreamWithWriteDesc1(
                baos);
        ObjectStreamClass desc = ObjectStreamClass
                .lookup(TestClassForSerialization.class);
        oos.writeClassDescriptor(desc);
        oos.close();

        byte[] bytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStreamWithReadDesc1 ois = new ObjectInputStreamWithReadDesc1(
                bais);
        Object obj = ois.readClassDescriptor();
        ois.close();
        assertEquals(desc.getClass(), obj.getClass());

        //eof
        bais = new ByteArrayInputStream(bytes);
        ExceptionalBufferedInputStream bis = new ExceptionalBufferedInputStream(
                bais);
        ois = new ObjectInputStreamWithReadDesc1(bis);

        bis.setEOF(true);

        try {
            obj = ois.readClassDescriptor();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            ois.close();
        }

        //throw exception
        bais = new ByteArrayInputStream(bytes);
        bis = new ExceptionalBufferedInputStream(bais);
        ois = new ObjectInputStreamWithReadDesc1(bis);

        bis.setException(new IOException());

        try {
            obj = ois.readClassDescriptor();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            ois.close();
        }

        //corrupt
        bais = new ByteArrayInputStream(bytes);
        bis = new ExceptionalBufferedInputStream(bais);
        ois = new ObjectInputStreamWithReadDesc1(bis);

        bis.setCorrupt(true);

        try {
            obj = ois.readClassDescriptor();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            ois.close();
        }
    }

    static class ExceptionalBufferedInputStream extends BufferedInputStream {
        private boolean eof = false;
        private IOException exception = null;
        private boolean corrupt = false;

        public ExceptionalBufferedInputStream(InputStream in) {
            super(in);
        }

        @Override
        public int read() throws IOException {
            if (exception != null) {
                throw exception;
            }

            if (eof) {
                return -1;
            }

            if (corrupt) {
                return 0;
            }
            return super.read();
        }

        public void setEOF(boolean eof) {
            this.eof = eof;
        }

        public void setException(IOException exception) {
            this.exception = exception;
        }

        public void setCorrupt(boolean corrupt) {
            this.corrupt = corrupt;
        }
    }

    public static class ObjectIutputStreamWithReadDesc2 extends
            ObjectInputStream {
        private Class returnClass;

        public ObjectIutputStreamWithReadDesc2(InputStream is, Class returnClass)
                throws IOException {
            super(is);
            this.returnClass = returnClass;
        }

        @Override
        public ObjectStreamClass readClassDescriptor() throws IOException,
                ClassNotFoundException {
            ObjectStreamClass osc = super.readClassDescriptor();

            if (osc.getName().equals(returnClass.getName())) {
                return ObjectStreamClass.lookup(returnClass);
            }
            return osc;
        }
    }

    /*
     * Testing classDescriptor replacement with the value generated by
     * ObjectStreamClass.lookup() method.
     * Regression test for HARMONY-4638
     */
    public void test_readClassDescriptor_1() throws IOException, ClassNotFoundException {
        A a = new A();
        a.name = "It's a test";
        PipedOutputStream pout = new PipedOutputStream();
        PipedInputStream pin = new PipedInputStream(pout);
        ObjectOutputStream out = new ObjectOutputStream(pout);
        ObjectInputStream in = new ObjectIutputStreamWithReadDesc2(pin, A.class);

        // test single object
        out.writeObject(a);
        A a1 = (A) in.readObject();
        assertEquals("Single case: incorrectly read the field of A", a.name, a1.name);

        // test cyclic reference
        HashMap m = new HashMap();
        a = new A();
        a.name = "It's a test 0";
        a1 = new A();
        a1.name = "It's a test 1";
        m.put("0", a);
        m.put("1", a1);
        out.writeObject(m);
        HashMap m1 = (HashMap) in.readObject();
        assertEquals("Incorrectly read the field of A", a.name, ((A) m1.get("0")).name);
        assertEquals("Incorrectly read the field of A1", a1.name, ((A) m1.get("1")).name);
    }

    public void test_registerValidation() throws Exception {
        // Regression Test for Harmony-2402
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ObjectOutputStream(baos);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(baos.toByteArray()));

        try {
            ois.registerValidation(null, 256);
            fail("NotActiveException should be thrown");
        } catch (NotActiveException nae) {
            // expected
        }

        // Regression Test for Harmony-3916
        baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(new RegisterValidationClass());
        oos.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream fis = new ObjectInputStream(bais);
        // should not throw NotActiveException
        fis.readObject();
    }

    private static class RegisterValidationClass implements Serializable {
        @SuppressWarnings("unused")
        private A a = new A();
        private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            stream.registerValidation(new MockObjectInputValidation(), 0);
        }
    }

    private static class MockObjectInputValidation implements ObjectInputValidation {
        public void validateObject() throws InvalidObjectException {

        }
    }

    //Regression Test for HARMONY-3726
    public void test_readObject_array() throws Exception {
        final String resourcePrefix = "serialization/org/apache/harmony/luni/tests/java/io";
        ObjectInputStream oin = new ObjectInputStream(this.getClass().getClassLoader().getResourceAsStream(
                resourcePrefix + "/test_array_strings.ser"));
        org.apache.harmony.luni.tests.java.io.TestArray testArray = (TestArray) oin.readObject();
        String[] strings = new String[] { "AAA", "BBB" };
        assertTrue(java.util.Arrays.equals(strings, testArray.array));

        oin = new ObjectInputStream(this.getClass().getClassLoader().getResourceAsStream(
                resourcePrefix + "/test_array_integers.ser"));
        testArray = (TestArray) oin.readObject();
        Integer[] integers = new Integer[] { 10, 20 };
        assertTrue(java.util.Arrays.equals(integers, testArray.array));
    }

    public static class TestExtObject implements Externalizable {
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeInt(10);
        }

        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            in.readInt();
        }
    }

    static class TestObjectOutputStream extends ObjectOutputStream {
        private ObjectStreamClass[] objs;
        private int pos = 0;

        public TestObjectOutputStream(OutputStream out, ObjectStreamClass[] objs) throws IOException {
            super(out);
            this.objs = objs;
        }

        @Override
        protected void writeClassDescriptor(ObjectStreamClass osc) throws IOException {
            objs[pos++] = osc;
        }
    }

    static class TestObjectInputStream extends ObjectInputStream {
        private ObjectStreamClass[] objs;
        private int pos = 0;

        public TestObjectInputStream(InputStream in, ObjectStreamClass[] objs) throws IOException {
            super(in);
            this.objs = objs;
        }

        @Override
        protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
            return objs[pos++];
        }
    }

    // Regression test for HARMONY-4996
    public void test_readObject_replacedClassDescriptor() throws Exception {
        ObjectStreamClass[] objs = new ObjectStreamClass[1000];
        PipedOutputStream pout = new PipedOutputStream();
        PipedInputStream pin = new PipedInputStream(pout);
        ObjectOutputStream oout = new TestObjectOutputStream(pout, objs);
        oout.writeObject(new TestExtObject());
        oout.writeObject("test");
        oout.close();
        ObjectInputStream oin = new TestObjectInputStream(pin, objs);
        oin.readObject();
        oin.readObject();
    }

    public void test_readObject_replacedClassField() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        FieldReplacementTestClass obj = new FieldReplacementTestClass(1234);
        oos.writeObject(obj);
        out.flush();
        out.close();

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(in);

        try {
            FieldReplacementTestClass result =
                    (FieldReplacementTestClass) ois.readObject();
            fail("should throw ClassCastException");
        } catch (ClassCastException e) {
            // expected
        }
        ois.close();
    }

    /**
     * Sets up the fixture, for example, open a network connection. This method
     * is called before a test is executed.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        oos = new ObjectOutputStream(bao = new ByteArrayOutputStream());
    }

    public static class FieldReplacementTestClass implements Serializable {
        private FieldClass c;

        public FieldReplacementTestClass(int i) {
            super();
            c = new FieldClass(i);
        }
    }

    public static class FieldClass implements Serializable {
        private int i;

        public FieldClass(int i) {
            super();
            this.i = i;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return new ReplacementFieldClass(i);
        }
    }

    public static class ReplacementFieldClass implements Serializable {
        private int i;

        public ReplacementFieldClass(int i) {
            super();
            this.i = i;
        }
    }
}

class TestArray implements Serializable {
    private static final long serialVersionUID = 1L;

    public Object[] array;

    public TestArray(Object[] array) {
        this.array = array;
    }
}

class Test implements Serializable {
    private static final long serialVersionUID = 1L;

    Class<?> classes[] = new Class[] { byte.class, short.class, int.class,
            long.class, boolean.class, char.class, float.class, double.class };

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Test)) {
            return false;
        }
        return Arrays.equals(classes, ((Test) o).classes);
    }
}
