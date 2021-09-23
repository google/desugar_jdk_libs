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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class DataInputStreamTest extends junit.framework.TestCase {

    private DataOutputStream os;

    private DataInputStream dis;

    private ByteArrayOutputStream bos;

    String unihw = "\u0048\u0065\u006C\u006C\u006F\u0020\u0057\u006F\u0072\u006C\u0064";

    public String fileString = "Test_All_Tests\nTest_java_io_BufferedInputStream\nTest_java_io_BufferedOutputStream\nTest_java_io_ByteArrayInputStream\nTest_java_io_ByteArrayOutputStream\nTest_DataInputStream\n";

    /**
     * java.io.DataInputStream#DataInputStream(java.io.InputStream)
     */
    public void test_ConstructorLjava_io_InputStream() throws IOException {
        try {
            os.writeChar('t');
            os.close();
            openDataInputStream();
        } finally {
            dis.close();
        }
    }

    /**
     * java.io.DataInputStream#read(byte[])
     */
    public void test_read$B() throws IOException {
        os.write(fileString.getBytes());
        os.close();
        openDataInputStream();
        byte rbytes[] = new byte[fileString.length()];
        dis.read(rbytes);
        assertTrue("Incorrect data read", new String(rbytes, 0, fileString
                .length()).equals(fileString));
    }

    /**
     * java.io.DataInputStream#read(byte[], int, int)
     */
    public void test_read$BII() throws IOException {
        os.write(fileString.getBytes());
        os.close();
        openDataInputStream();
        byte rbytes[] = new byte[fileString.length()];
        dis.read(rbytes, 0, rbytes.length);
        assertTrue("Incorrect data read", new String(rbytes, 0, fileString
                .length()).equals(fileString));
    }

    /**
     * java.io.DataInputStream#readBoolean()
     */
    public void test_readBoolean() throws IOException {
        os.writeBoolean(true);
        os.close();
        openDataInputStream();
        assertTrue("Incorrect boolean written", dis.readBoolean());
    }

    /**
     * java.io.DataInputStream#readByte()
     */
    public void test_readByte() throws IOException {
        os.writeByte((byte) 127);
        os.close();
        openDataInputStream();
        assertTrue("Incorrect byte read", dis.readByte() == (byte) 127);
    }

    /**
     * java.io.DataInputStream#readChar()
     */
    public void test_readChar() throws IOException {
        os.writeChar('t');
        os.close();
        openDataInputStream();
        assertEquals("Incorrect char read", 't', dis.readChar());
    }

    /**
     * java.io.DataInputStream#readDouble()
     */
    public void test_readDouble() throws IOException {
        os.writeDouble(2345.76834720202);
        os.close();
        openDataInputStream();
        assertEquals("Incorrect double read", 2345.76834720202, dis
                .readDouble());
    }

    /**
     * java.io.DataInputStream#readFloat()
     */
    public void test_readFloat() throws IOException {
        os.writeFloat(29.08764f);
        os.close();
        openDataInputStream();
        assertTrue("Incorrect float read", dis.readFloat() == 29.08764f);
    }

    /**
     * java.io.DataInputStream#readFully(byte[])
     */
    public void test_readFully$B() throws IOException {
        os.write(fileString.getBytes());
        os.close();
        openDataInputStream();
        byte rbytes[] = new byte[fileString.length()];
        dis.readFully(rbytes);
        assertTrue("Incorrect data read", new String(rbytes, 0, fileString
                .length()).equals(fileString));
    }

    /**
     * java.io.DataInputStream#readFully(byte[], int, int)
     */
    public void test_readFully$BII() throws IOException {
        os.write(fileString.getBytes());
        os.close();
        openDataInputStream();
        byte rbytes[] = new byte[fileString.length()];
        dis.readFully(rbytes, 0, fileString.length());
        assertTrue("Incorrect data read", new String(rbytes, 0, fileString
                .length()).equals(fileString));
    }

    /**
     * java.io.DataInputStream#readFully(byte[], int, int)
     */
    public void test_readFully$BII_Exception() throws IOException {
        DataInputStream is = new DataInputStream(new ByteArrayInputStream(
                new byte[fileString.length()]));

        byte[] byteArray = new byte[fileString.length()];

        try {
            is.readFully(byteArray, -1, -1);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        try {
            is.readFully(byteArray, 0, -1);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        try {
            is.readFully(byteArray, 1, -1);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        is.readFully(byteArray, -1, 0);
        is.readFully(byteArray, 0, 0);
        is.readFully(byteArray, 1, 0);

        try {
            is.readFully(byteArray, -1, 1);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        is.readFully(byteArray, 0, 1);
        is.readFully(byteArray, 1, 1);
        try {
            is.readFully(byteArray, 0, Integer.MAX_VALUE);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    /**
     * java.io.DataInputStream#readFully(byte[], int, int)
     */
    public void test_readFully$BII_NullArray() throws IOException {
        DataInputStream is = new DataInputStream(new ByteArrayInputStream(
                new byte[fileString.length()]));

        byte[] nullByteArray = null;

        try {
            is.readFully(nullByteArray, -1, -1);
            fail();
        } catch (NullPointerException expected) {
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            is.readFully(nullByteArray, 0, -1);
            fail();
        } catch (NullPointerException expected) {
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            is.readFully(nullByteArray, 1, -1);
            fail();
        } catch (NullPointerException expected) {
        } catch (IndexOutOfBoundsException expected) {
        }

        is.readFully(nullByteArray, -1, 0);
        is.readFully(nullByteArray, 0, 0);
        is.readFully(nullByteArray, 1, 0);

        try {
            is.readFully(nullByteArray, -1, 1);
            fail();
        } catch (NullPointerException expected) {
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            is.readFully(nullByteArray, 0, 1);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            is.readFully(nullByteArray, 1, 1);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            is.readFully(nullByteArray, 0, Integer.MAX_VALUE);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * java.io.DataInputStream#readFully(byte[], int, int)
     */
    public void test_readFully$BII_NullStream() throws IOException {
        DataInputStream is = new DataInputStream(null);
        byte[] byteArray = new byte[fileString.length()];

        try {
            is.readFully(byteArray, -1, -1);
            fail();
        } catch (NullPointerException expected) {
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            is.readFully(byteArray, 0, -1);
            fail();
        } catch (NullPointerException expected) {
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            is.readFully(byteArray, 1, -1);
            fail();
        } catch (NullPointerException expected) {
        } catch (IndexOutOfBoundsException expected) {
        }

        is.readFully(byteArray, -1, 0);
        is.readFully(byteArray, 0, 0);
        is.readFully(byteArray, 1, 0);

        try {
            is.readFully(byteArray, -1, 1);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            is.readFully(byteArray, 0, 1);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            is.readFully(byteArray, 1, 1);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            is.readFully(byteArray, 0, Integer.MAX_VALUE);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * java.io.DataInputStream#readFully(byte[], int, int)
     */
    public void test_readFully$BII_NullStream_NullArray() throws IOException {
        DataInputStream is = new DataInputStream(null);
        byte[] nullByteArray = null;

        try {
            is.readFully(nullByteArray, -1, -1);
            fail();
        } catch (NullPointerException expected) {
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            is.readFully(nullByteArray, 0, -1);
            fail();
        } catch (NullPointerException expected) {
        } catch (IndexOutOfBoundsException expected) {
        }

        try {
            is.readFully(nullByteArray, 1, -1);
            fail();
        } catch (NullPointerException expected) {
        } catch (IndexOutOfBoundsException expected) {
        }

        is.readFully(nullByteArray, -1, 0);
        is.readFully(nullByteArray, 0, 0);
        is.readFully(nullByteArray, 1, 0);

        try {
            is.readFully(nullByteArray, -1, 1);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            is.readFully(nullByteArray, 0, 1);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            is.readFully(nullByteArray, 1, 1);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            is.readFully(nullByteArray, 0, Integer.MAX_VALUE);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * java.io.DataInputStream#readInt()
     */
    public void test_readInt() throws IOException {
        os.writeInt(768347202);
        os.close();
        openDataInputStream();
        assertEquals("Incorrect int read", 768347202, dis.readInt());
    }

    /**
     * java.io.DataInputStream#readLine()
     */
    @SuppressWarnings("deprecation")
    public void test_readLine() throws IOException {
        os.writeBytes("Hello");
        os.close();
        openDataInputStream();
        String line = dis.readLine();
        assertTrue("Incorrect line read: " + line, line.equals("Hello"));
    }

    /**
     * java.io.DataInputStream#readLong()
     */
    public void test_readLong() throws IOException {
        os.writeLong(9875645283333L);
        os.close();
        openDataInputStream();
        assertEquals("Incorrect long read", 9875645283333L, dis.readLong());
    }

    /**
     * java.io.DataInputStream#readShort()
     */
    public void test_readShort() throws IOException {
        os.writeShort(9875);
        os.close();
        openDataInputStream();
        assertTrue("Incorrect short read", dis.readShort() == (short) 9875);
    }

    /**
     * java.io.DataInputStream#readUnsignedByte()
     */
    public void test_readUnsignedByte() throws IOException {
        os.writeByte((byte) -127);
        os.close();
        openDataInputStream();
        assertEquals("Incorrect byte read", 129, dis.readUnsignedByte());
    }

    /**
     * java.io.DataInputStream#readUnsignedShort()
     */
    public void test_readUnsignedShort() throws IOException {
        os.writeShort(9875);
        os.close();
        openDataInputStream();
        assertEquals("Incorrect short read", 9875, dis.readUnsignedShort());
    }

    /**
     * java.io.DataInputStream#readUTF()
     */
    public void test_readUTF() throws IOException {
        os.writeUTF(unihw);
        os.close();
        openDataInputStream();
        assertTrue("Failed to write string in UTF format",
                dis.available() == unihw.length() + 2);
        assertTrue("Incorrect string read", dis.readUTF().equals(unihw));
    }

    static class TestDataInputStream implements DataInput {
        public boolean readBoolean() throws IOException {
            return false;
        }

        public byte readByte() throws IOException {
            return (byte) 0;
        }

        public char readChar() throws IOException {
            return (char) 0;
        }

        public double readDouble() throws IOException {
            return 0.0;
        }

        public float readFloat() throws IOException {
            return (float) 0.0;
        }

        public void readFully(byte[] buffer) throws IOException {
        }

        public void readFully(byte[] buffer, int offset, int count)
                throws IOException {
        }

        public int readInt() throws IOException {
            return 0;
        }

        public String readLine() throws IOException {
            return null;
        }

        public long readLong() throws IOException {
            return (long) 0;
        }

        public short readShort() throws IOException {
            return (short) 0;
        }

        public int readUnsignedByte() throws IOException {
            return 0;
        }

        public int readUnsignedShort() throws IOException {
            return 0;
        }

        public String readUTF() throws IOException {
            return DataInputStream.readUTF(this);
        }

        public int skipBytes(int count) throws IOException {
            return 0;
        }
    }

    /**
     * java.io.DataInputStream#readUTF(java.io.DataInput)
     */
    public void test_readUTFLjava_io_DataInput() throws IOException {
        os.writeUTF(unihw);
        os.close();
        openDataInputStream();
        assertTrue("Failed to write string in UTF format",
                dis.available() == unihw.length() + 2);
        assertTrue("Incorrect string read", DataInputStream.readUTF(dis)
                .equals(unihw));

        // Regression test for HARMONY-5336
        new TestDataInputStream().readUTF();
    }

    /**
     * java.io.DataInputStream#skipBytes(int)
     */
    public void test_skipBytesI() throws IOException {
        byte fileBytes[] = fileString.getBytes();
        os.write(fileBytes);
        os.close();
        openDataInputStream();
        dis.skipBytes(100);
        byte rbytes[] = new byte[fileString.length()];
        dis.read(rbytes, 0, 50);
        dis.close();
        assertTrue("Incorrect data read", new String(rbytes, 0, 50)
                .equals(fileString.substring(100, 150)));

        int skipped = 0;
        openDataInputStream();
        try {
            skipped = dis.skipBytes(50000);
        } catch (EOFException e) {
        }
        assertTrue("Skipped should report " + fileString.length() + " not "
                + skipped, skipped == fileString.length());
    }

    // b/30268192 : Some apps rely on the exact calls that
    // DataInputStream makes on the wrapped InputStream. This
    // test is to prevent *unintentional* regressions but may
    // change in future releases.
    public void test_readShortUsesMultiByteRead() throws IOException {
        ThrowExceptionOnSingleByteReadInputStream
                is = new ThrowExceptionOnSingleByteReadInputStream();
        DataInputStream dis = new DataInputStream(is);
        dis.readShort();
        is.assertMultiByteReadWasCalled();
    }

    // b/30268192 : Some apps rely on the exact calls that
    // DataInputStream makes on the wrapped InputStream. This
    // test is to prevent *unintentional* regressions but may
    // change in future releases.
    public void test_readCharUsesMultiByteRead() throws IOException {
        ThrowExceptionOnSingleByteReadInputStream
                is = new ThrowExceptionOnSingleByteReadInputStream();
        DataInputStream dis = new DataInputStream(is);
        dis.readChar();
        is.assertMultiByteReadWasCalled();
    }

    // b/30268192 : Some apps rely on the exact calls that
    // DataInputStream makes on the wrapped InputStream. This
    // test is to prevent *unintentional* regressions but may
    // change in future releases.
    public void test_readIntUsesMultiByteRead() throws IOException {
        ThrowExceptionOnSingleByteReadInputStream
                is = new ThrowExceptionOnSingleByteReadInputStream();
        DataInputStream dis = new DataInputStream(is);
        dis.readInt();
        is.assertMultiByteReadWasCalled();
    }

    // b/30268192 : Some apps rely on the exact calls that
    // DataInputStream makes on the wrapped InputStream. This
    // test is to prevent *unintentional* regressions but may
    // change in future releases.
    public void test_readUnsignedShortUsesMultiByteRead() throws IOException {
        ThrowExceptionOnSingleByteReadInputStream
                is = new ThrowExceptionOnSingleByteReadInputStream();
        DataInputStream dis = new DataInputStream(is);
        dis.readUnsignedShort();
        is.assertMultiByteReadWasCalled();
    }

    private void openDataInputStream() throws IOException {
        dis = new DataInputStream(new ByteArrayInputStream(bos.toByteArray()));
    }

    /**
     * Sets up the fixture, for example, open a network connection. This method
     * is called before a test is executed.
     */
    protected void setUp() {
        bos = new ByteArrayOutputStream();
        os = new DataOutputStream(bos);
    }

    /**
     * Tears down the fixture, for example, close a network connection. This
     * method is called after a test is executed.
     */
    protected void tearDown() {
        try {
            os.close();
        } catch (Exception e) {
        }
        try {
            dis.close();
        } catch (Exception e) {
        }
    }

    public static class ThrowExceptionOnSingleByteReadInputStream extends InputStream {

        private boolean multiByteReadWasCalled = false;

        @Override
        public int read() throws IOException {
            fail("Should not call single byte read");
            return 0;
        }

        @Override
        public int read(byte[] b, int i, int j) throws IOException {
            multiByteReadWasCalled = true;
            return j;
        }

        public void assertMultiByteReadWasCalled() {
            if (!multiByteReadWasCalled) {
                fail("read(byte[], int, int) was not called");
            }
        }
    }
}
