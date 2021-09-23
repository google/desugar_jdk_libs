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

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import junit.framework.TestCase;

public class FilterInputStreamTest extends TestCase {

    static class MyFilterInputStream extends FilterInputStream {
        public MyFilterInputStream(InputStream is) {
            super(is);
        }
    }

    private String fileName;
    private InputStream is;
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

    /**
     * Sets up the fixture, for example, open a network connection. This method
     * is called before a test is executed.
     */
    @Override
    protected void setUp() throws IOException {
        File temp = File.createTempFile("FilterInputStreamTest", "tst");
        fileName = temp.getAbsolutePath();
        OutputStream fos = new FileOutputStream(temp.getAbsolutePath());
        fos.write(INPUT.getBytes(StandardCharsets.US_ASCII));
        fos.close();
        is = new MyFilterInputStream(new java.io.FileInputStream(fileName));
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
            // Ignored
        }
        new File(fileName).delete();
    }

    /**
     * java.io.FilterInputStream#available()
     */
    public void test_available() throws IOException {
        assertTrue("Returned incorrect number of available bytes", is
                .available() == INPUT.length());
    }

    /**
     * java.io.FilterInputStream#close()
     */
    public void test_close() throws IOException {
        is.close();

        try {
            is.read();
            fail("Able to read from closed stream");
        } catch (java.io.IOException e) {
            // Expected
        }
    }

    /**
     * java.io.FilterInputStream#mark(int)
     */
    public void test_markI() {
        assertTrue("Mark not supported by parent InputStream", true);
    }

    /**
     * java.io.FilterInputStream#markSupported()
     */
    public void test_markSupported() {
        assertTrue("markSupported returned true", !is.markSupported());
    }

    /**
     * java.io.FilterInputStream#read()
     */
    public void test_read() throws Exception {
        int c = is.read();
        assertTrue("read returned incorrect char", c == INPUT.charAt(0));
    }

    /**
     * java.io.FilterInputStream#read(byte[])
     */
    public void test_read$B() throws Exception {
        byte[] buf1 = new byte[100];
        is.read(buf1);
        assertTrue("Failed to read correct data", new String(buf1, 0,
                buf1.length, "UTF-8").equals(INPUT.substring(0, 100)));
    }

    /**
     * java.io.FilterInputStream#read(byte[], int, int)
     */
    public void test_read$BII() throws Exception {
        byte[] buf1 = new byte[100];
        is.skip(500);
        is.mark(1000);
        is.read(buf1, 0, buf1.length);
        assertTrue("Failed to read correct data", new String(buf1, 0,
                buf1.length, "UTF-8").equals(INPUT.substring(500, 600)));
    }

    /**
     * java.io.FilterInputStream#reset()
     */
    public void test_reset() {
        try {
            is.reset();
            fail("should throw IOException");
        } catch (IOException e) {
            // expected
        }
    }

    /**
     * java.io.FilterInputStream#skip(long)
     */
    public void test_skipJ() throws Exception {
        byte[] buf1 = new byte[10];
        is.skip(1000);
        is.read(buf1, 0, buf1.length);
        assertTrue("Failed to skip to correct position", new String(buf1, 0,
                buf1.length, "UTF-8").equals(INPUT.substring(1000, 1010)));
    }
}
