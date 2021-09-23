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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import junit.framework.TestCase;

public class BufferedInputStreamTest extends TestCase {

    private static final String INPUT =
             "Test_All_Tests\n" +
             "Test_BufferedInputStream\n" +
             "Test_java_io_BufferedOutputStream\n" +
             "Test_java_io_ByteArrayInputStream\n" +
             "Test_java_io_ByteArrayOutputStream\n" +
             "Test_java_io_DataInputStream\n" +
             "Test_java_io_File\n" +
             "Test_java_io_FileDescriptor\n" +
             "Test_java_io_FileInputStream\n" +
             "Test_java_io_FileNotFoundException\n" +
             "Test_java_io_FileOutputStream\n" +
             "Test_java_io_FilterInputStream\n" +
             "Test_java_io_FilterOutputStream\n" +
             "Test_java_io_InputStream\n" +
             "Test_java_io_IOException\n" +
             "Test_java_io_OutputStream\n" +
             "Test_java_io_PrintStream\n" +
             "Test_java_io_RandomAccessFile\n" +
             "Test_java_io_SyncFailedException\n" +
             "Test_java_lang_AbstractMethodError\n" +
             "Test_java_lang_ArithmeticException\n" +
             "Test_java_lang_ArrayIndexOutOfBoundsException\n" +
             "Test_java_lang_ArrayStoreException\n" +
             "Test_java_lang_Boolean\n" +
             "Test_java_lang_Byte\n" +
             "Test_java_lang_Character\n" +
             "Test_All_Tests\n" +
             "Test_BufferedInputStream\n" +
             "Test_java_io_BufferedOutputStream\n" +
             "Test_java_io_ByteArrayInputStream\n" +
             "Test_java_io_ByteArrayOutputStream\n" +
             "Test_java_io_DataInputStream\n" +
             "Test_java_io_File\n" +
             "Test_java_io_FileDescriptor\n" +
             "Test_java_io_FileInputStream\n" +
             "Test_java_io_FileNotFoundException\n" +
             "Test_java_io_FileOutputStream\n" +
             "Test_java_io_FilterInputStream\n" +
             "Test_java_io_FilterOutputStream\n" +
             "Test_java_io_InputStream\n" +
             "Test_java_io_IOException\n" +
             "Test_java_io_OutputStream\n" +
             "Test_java_io_PrintStream\n" +
             "Test_java_io_RandomAccessFile\n" +
             "Test_java_io_SyncFailedException\n" +
             "Test_java_lang_AbstractMethodError\n" +
             "Test_java_lang_ArithmeticException\n" +
             "Test_java_lang_ArrayIndexOutOfBoundsException\n" +
             "Test_java_lang_ArrayStoreException\n" +
             "Test_java_lang_Boolean\n" +
             "Test_java_lang_Byte\n" +
             "Test_java_lang_Character\n";

    private BufferedInputStream is;
    private InputStream isBytes;


    /*
     * java.io.BufferedInputStream(InputStream)
     */
    public void test_ConstructorLjava_io_InputStream() {
        try {
            BufferedInputStream str = new BufferedInputStream(null);
            str.read();
            fail("Expected an IOException");
        } catch (IOException e) {
            // Expected
        }
    }

    /*
     * java.io.BufferedInputStream(InputStream)
     */
    public void test_ConstructorLjava_io_InputStreamI() throws IOException {
        try {
            BufferedInputStream str = new BufferedInputStream(null, 1);
            str.read();
            fail("Expected an IOException");
        } catch (IOException e) {
            // Expected
        }

        // Test for method java.io.BufferedInputStream(java.io.InputStream, int)

        // Create buffer with hald size of file and fill it.
        int bufferSize = INPUT.length() / 2;
        is = new BufferedInputStream(isBytes, bufferSize);
        // Ensure buffer gets filled by evaluating one read
        is.read();
        // Close underlying FileInputStream, all but 1 buffered bytes should
        // still be available.
        isBytes.close();
        // Read the remaining buffered characters, no IOException should
        // occur.
        is.skip(bufferSize - 2);
        is.read();
        try {
            // is.read should now throw an exception because it will have to
            // be filled.
            is.read();
            fail("Exception should have been triggered by read()");
        } catch (IOException e) {
            // Expected
        }

        // regression test for harmony-2407
        new MockBufferedInputStream(null);
        assertNotNull(MockBufferedInputStream.buf);
        MockBufferedInputStream.buf = null;
        new MockBufferedInputStream(null, 100);
        assertNotNull(MockBufferedInputStream.buf);
    }

    static class MockBufferedInputStream extends BufferedInputStream {
        static byte[] buf;

        MockBufferedInputStream(InputStream is) throws IOException {
            super(is);
            buf = super.buf;
        }

        MockBufferedInputStream(InputStream is, int size) throws IOException {
            super(is, size);
            buf = super.buf;
        }
    }

    /**
     * java.io.BufferedInputStream#available()
     */
    public void test_available() throws IOException {
        assertTrue("Returned incorrect number of available bytes", is
                .available() == INPUT.length());

        // Test that a closed stream throws an IOE for available()
        BufferedInputStream bis = new BufferedInputStream(
                new ByteArrayInputStream(new byte[] { 'h', 'e', 'l', 'l', 'o',
                        ' ', 't', 'i', 'm' }));
        int available = bis.available();
        bis.close();
        assertTrue(available != 0);

        try {
            bis.available();
            fail("Expected test to throw IOE.");
        } catch (IOException ex) {
            // expected
        }
    }

    /**
     * java.io.BufferedInputStream#close()
     */
    public void test_close() throws IOException {
        new BufferedInputStream(isBytes).close();

        // regression for HARMONY-667
        BufferedInputStream buf = new BufferedInputStream(null, 5);
        buf.close();

        InputStream in = new InputStream() {
            Object lock = new Object();

            @Override
            public int read() {
                return 1;
            }

            @Override
            public int read(byte[] buf, int offset, int length) {
                synchronized (lock) {
                    try {
                        lock.wait(3000);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                }
                return 1;
            }

            @Override
            public void close() {
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        };
        final BufferedInputStream bufin = new BufferedInputStream(in);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    bufin.close();
                } catch (Exception e) {
                    // Ignored
                }
            }
        });
        thread.start();
        try {
            bufin.read(new byte[100], 0, 99);
            fail("Should throw IOException");
        } catch (IOException e) {
            // Expected
        }
    }

    /**
     * java.io.BufferedInputStream#mark(int)
     */
    public void test_markI() throws IOException {
        byte[] buf1 = new byte[100];
        byte[] buf2 = new byte[100];
        is.skip(50);
        is.mark(500);
        is.read(buf1, 0, buf1.length);
        is.reset();
        is.read(buf2, 0, buf2.length);
        is.reset();
        assertTrue("Failed to mark correct position", new String(buf1, 0,
                buf1.length).equals(new String(buf2, 0, buf2.length)));

        byte[] bytes = new byte[256];
        for (int i = 0; i < 256; i++) {
            bytes[i] = (byte) i;
        }
        InputStream in = new BufferedInputStream(
                new ByteArrayInputStream(bytes), 12);
        in.skip(6);
        in.mark(14);
        in.read(new byte[14], 0, 14);
        in.reset();
        assertTrue("Wrong bytes", in.read() == 6 && in.read() == 7);

        in = new BufferedInputStream(new ByteArrayInputStream(bytes), 12);
        in.skip(6);
        in.mark(8);
        in.skip(7);
        in.reset();
        assertTrue("Wrong bytes 2", in.read() == 6 && in.read() == 7);

        BufferedInputStream buf = new BufferedInputStream(
                new ByteArrayInputStream(new byte[] { 0, 1, 2, 3, 4 }), 2);
        buf.mark(3);
        bytes = new byte[3];
        int result = buf.read(bytes);
        assertEquals(3, result);
        assertEquals("Assert 0:", 0, bytes[0]);
        assertEquals("Assert 1:", 1, bytes[1]);
        assertEquals("Assert 2:", 2, bytes[2]);
        assertEquals("Assert 3:", 3, buf.read());

        buf = new BufferedInputStream(new ByteArrayInputStream(new byte[] { 0,
                1, 2, 3, 4 }), 2);
        buf.mark(3);
        bytes = new byte[4];
        result = buf.read(bytes);
        assertEquals(4, result);
        assertEquals("Assert 4:", 0, bytes[0]);
        assertEquals("Assert 5:", 1, bytes[1]);
        assertEquals("Assert 6:", 2, bytes[2]);
        assertEquals("Assert 7:", 3, bytes[3]);
        assertEquals("Assert 8:", 4, buf.read());
        assertEquals("Assert 9:", -1, buf.read());

        buf = new BufferedInputStream(new ByteArrayInputStream(new byte[] { 0,
                1, 2, 3, 4 }), 2);
        buf.mark(Integer.MAX_VALUE);
        buf.read();
        buf.close();
    }

    /**
     * java.io.BufferedInputStream#markSupported()
     */
    public void test_markSupported() {
        assertTrue("markSupported returned incorrect value", is.markSupported());
    }

    /**
     * java.io.BufferedInputStream#read()
     */
    public void test_read() throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        int c = isr.read();
        assertEquals(INPUT.charAt(0), c);

        byte[] bytes = new byte[256];
        for (int i = 0; i < 256; i++) {
            bytes[i] = (byte) i;
        }
        InputStream in = new BufferedInputStream(
                new ByteArrayInputStream(bytes), 12);
        assertEquals("Wrong initial byte", 0, in.read()); // Fill the
        // buffer
        byte[] buf = new byte[14];
        in.read(buf, 0, 14); // Read greater than the buffer
        assertTrue("Wrong block read data", new String(buf, 0, 14)
                .equals(new String(bytes, 1, 14)));
        assertEquals("Wrong bytes", 15, in.read()); // Check next byte
    }

    /**
     * java.io.BufferedInputStream#read(byte[], int, int)
     */
    public void test_read$BII_Exception() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(null);
        try {
            bis.read(null, -1, -1);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            bis.read(new byte[0], -1, -1);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        try {
            bis.read(new byte[0], 1, -1);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        try {
            bis.read(new byte[0], 1, 1);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        bis.close();

        try {
            bis.read(null, -1, -1);
            fail("should throw IOException");
        } catch (IOException e) {
            // expected
        }
    }

    /**
     * java.io.BufferedInputStream#read(byte[], int, int)
     */
    public void test_read$BII() throws IOException {
        byte[] buf1 = new byte[100];
        is.skip(500);
        is.mark(500);
        is.read(buf1, 0, buf1.length);
        assertTrue("Failed to read correct data", new String(buf1, 0,
                buf1.length).equals(INPUT.substring(500, 600)));

        BufferedInputStream bufin = new BufferedInputStream(new InputStream() {
            int size = 2
                    ,
                    pos = 0;

            byte[] contents = new byte[size];

            @Override
            public int read() throws IOException {
                if (pos >= size) {
                    throw new IOException("Read past end of data");
                }
                return contents[pos++];
            }

            @Override
            public int read(byte[] buf, int off, int len) throws IOException {
                if (pos >= size) {
                    throw new IOException("Read past end of data");
                }
                int toRead = len;
                if (toRead > available()) {
                    toRead = available();
                }
                System.arraycopy(contents, pos, buf, off, toRead);
                pos += toRead;
                return toRead;
            }

            @Override
            public int available() {
                return size - pos;
            }
        });
        bufin.read();
        int result = bufin.read(new byte[2], 0, 2);
        assertTrue("Incorrect result: " + result, result == 1);
    }

    /**
     * java.io.BufferedInputStream#reset()
     */
    public void test_reset() throws IOException {
        byte[] buf1 = new byte[10];
        byte[] buf2 = new byte[10];
        is.mark(2000);
        is.read(buf1, 0, 10);
        is.reset();
        is.read(buf2, 0, 10);
        is.reset();
        assertTrue("Reset failed", new String(buf1, 0, buf1.length)
                .equals(new String(buf2, 0, buf2.length)));

        BufferedInputStream bIn = new BufferedInputStream(
                new ByteArrayInputStream("1234567890".getBytes()));
        bIn.mark(10);
        for (int i = 0; i < 11; i++) {
            bIn.read();
        }
        bIn.reset();
    }

    /**
     * java.io.BufferedInputStream#reset()
     */
    public void test_reset_Exception() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(null);

        // throws IOException with message "Mark has been invalidated"
        try {
            bis.reset();
            fail("should throw IOException");
        } catch (IOException e) {
            // expected
        }

        // does not throw IOException
        bis.mark(1);
        bis.reset();

        bis.close();

        // throws IOException with message "stream is closed"
        try {
            bis.reset();
            fail("should throw IOException");
        } catch (IOException e) {
            // expected
        }
    }

    /**
     * java.io.BufferedInputStream#reset()
     */
    public void test_reset_scenario1() throws IOException {
        byte[] input = "12345678900".getBytes();
        BufferedInputStream buffis = new BufferedInputStream(
                new ByteArrayInputStream(input));
        buffis.read();
        buffis.mark(5);
        buffis.skip(5);
        buffis.reset();
    }

    /**
     * java.io.BufferedInputStream#reset()
     */
    public void test_reset_scenario2() throws IOException {
        byte[] input = "12345678900".getBytes();
        BufferedInputStream buffis = new BufferedInputStream(
                new ByteArrayInputStream(input));
        buffis.mark(5);
        buffis.skip(6);
        buffis.reset();
    }

    /**
     * java.io.BufferedInputStream#skip(long)
     */
    public void test_skipJ() throws IOException {
        byte[] buf1 = new byte[10];
        is.mark(2000);
        // This fails with OpenJdk. The first call to skip() will skip |bufferSize|
        // bytes, and the second call will potentially resize the buffer. Users need
        // to be aware. The API behaviour is correct, obviously, but it remains to
        // be seen whether anybody blindly expects skip to always skip the number
        // of bytes requested.
        //
        // assertEquals(1000, is.skip(1000));
        int bytesLeft = 1000;
        while (bytesLeft > 0) {
            bytesLeft -= is.skip(bytesLeft);
        }

        assertEquals(buf1.length, is.read(buf1, 0, buf1.length));
        is.reset();
        assertEquals("Failed to skip to correct position", new String(buf1, 0,
                buf1.length), INPUT.substring(1000, 1010));

        // regression for HARMONY-667
        try {
            BufferedInputStream buf = new BufferedInputStream(null, 5);
            buf.skip(10);
            fail("Should throw IOException");
        } catch (IOException e) {
            // Expected
        }
    }

    /**
     * java.io.BufferedInputStream#skip(long)
     */
    public void test_skip_NullInputStream() throws IOException {
        BufferedInputStream buf = new BufferedInputStream(null, 5);
        assertEquals(0, buf.skip(0));
    }

    /**
     * Sets up the fixture, for example, open a network connection. This method
     * is called before a test is executed.
     */
    @Override
    protected void setUp() throws IOException {
        File f = File.createTempFile("BufferedInputStreamTest", "tst");
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(INPUT.getBytes(StandardCharsets.US_ASCII));
        fos.close();

        isBytes = new FileInputStream(f.getAbsolutePath());
        is = new BufferedInputStream(isBytes, INPUT.length() / 2);
    }

    /**
     * Tears down the fixture, for example, close a network connection. This
     * method is called after a test is executed.
     */
    @Override
    protected void tearDown() {
        try {
            is.close();
        } catch (Exception e) {
        }
    }
}
