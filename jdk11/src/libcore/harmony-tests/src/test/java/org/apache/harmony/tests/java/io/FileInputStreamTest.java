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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import libcore.junit.junit3.TestCaseWithRules;
import libcore.junit.util.ResourceLeakageDetector;
import org.junit.Rule;
import org.junit.rules.TestRule;

public class FileInputStreamTest extends TestCaseWithRules {
    @Rule
    public TestRule guardRule = ResourceLeakageDetector.getRule();

    private String fileName;

    private java.io.InputStream is;

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

    @Override
    protected void setUp() throws IOException {
        File f = File.createTempFile("FileInputStreamTest", "tst");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f.getAbsolutePath());
            fos.write(INPUT.getBytes(StandardCharsets.US_ASCII));
        } finally {
            fos.close();
        }

        fileName = f.getAbsolutePath();
    }

    /**
     * java.io.FileInputStream#FileInputStream(java.io.File)
     */
    public void test_ConstructorLjava_io_File() throws IOException {
        java.io.File f = new File(fileName);
        is = new FileInputStream(f);
        is.close();
    }

    /**
     * java.io.FileInputStream#FileInputStream(java.io.FileDescriptor)
     */
    public void test_ConstructorLjava_io_FileDescriptor() throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        FileInputStream fis = new FileInputStream(fos.getFD());
        fos.close();
        fis.close();
    }

    /**
     * java.io.FileInputStream#FileInputStream(java.lang.String)
     */
    public void test_ConstructorLjava_lang_String() throws IOException {
        is = new FileInputStream(fileName);
        is.close();
    }

    /**
     * java.io.FileInputStream#FileInputStream(java.lang.String)
     */
    public void test_ConstructorLjava_lang_String_I() throws IOException {
        try {
            is = new FileInputStream("");
            fail("should throw FileNotFoundException.");
        } catch (FileNotFoundException e) {
            // Expected
        } finally {
            if (is != null) {
                is.close();
            }
        }
        try {
            is = new FileInputStream(new File(""));
            fail("should throw FileNotFoundException.");
        } catch (FileNotFoundException e) {
            // Expected
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * java.io.FileInputStream#available()
     */
    public void test_available() throws IOException {
        try {
            is = new FileInputStream(fileName);
            assertTrue(is.available() == INPUT.length());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    public void test_close() throws IOException {
        is = new FileInputStream(fileName);
        is.close();
        try {
            is.read();
            fail();
        } catch (IOException expected) {
        }
    }

    public void test_close_shared_fd() throws IOException {
        // Regression test for HARMONY-6642
        FileInputStream fis1 = new FileInputStream(fileName);
        FileInputStream fis2 = new FileInputStream(fis1.getFD());

        try {
            fis2.close();
            // Should not throw, since the FD is owned by fis1.
            fis1.read();
        } finally {
            try {
                fis1.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * java.io.FileInputStream#getFD()
     */
    public void test_getFD() throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        assertTrue("Returned invalid fd", fis.getFD().valid());
        fis.close();
        assertTrue("Returned invalid fd", !fis.getFD().valid());
    }

    /**
     * java.io.FileInputStream#read()
     */
    public void test_read() throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName));
        int c = isr.read();
        isr.close();
        assertEquals(INPUT.charAt(0), c);
    }

    /**
     * java.io.FileInputStream#read(byte[])
     */
    public void test_read$B() throws IOException {
        byte[] buf1 = new byte[100];
        is = new FileInputStream(fileName);
        is.skip(500);
        is.read(buf1);
        is.close();
        assertEquals(INPUT.substring(500, 600),
                new String(buf1, 0, buf1.length, StandardCharsets.US_ASCII));
    }

    /**
     * java.io.FileInputStream#read(byte[], int, int)
     */
    public void test_read$BII() throws IOException {
        byte[] buf1 = new byte[100];
        is = new FileInputStream(fileName);
        is.skip(500);
        is.read(buf1, 0, buf1.length);
        is.close();
        assertEquals(INPUT.substring(500, 600),
                new String(buf1, 0, buf1.length, StandardCharsets.US_ASCII));

        // Regression test for HARMONY-285
        File tmpFile = File.createTempFile("FileOutputStream", "tmp");
        FileInputStream in = new FileInputStream(tmpFile);
        try {
            in.read(null, 0, 0);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected
        } finally {
            in.close();
            tmpFile.delete();
        }
    }

    /**
     * java.io.FileInputStream#read(byte[], int, int)
     */
    public void test_read_$BII_IOException() throws IOException {
        byte[] buf = new byte[1000];
        try {
            is = new FileInputStream(fileName);
            is.read(buf, -1, 0);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } finally {
            is.close();
        }

        try {
            is = new FileInputStream(fileName);
            is.read(buf, 0, -1);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } finally {
            is.close();
        }

        try {
            is = new FileInputStream(fileName);
            is.read(buf, -1, -1);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } finally {
            is.close();
        }

        try {
            is = new FileInputStream(fileName);
            is.read(buf, 0, 1001);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } finally {
            is.close();
        }

        try {
            is = new FileInputStream(fileName);
            is.read(buf, 1001, 0);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } finally {
            is.close();
        }

        try {
            is = new FileInputStream(fileName);
            is.read(buf, 500, 501);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } finally {
            is.close();
        }

        try {
            is = new FileInputStream(fileName);
            is.close();
            is.read(buf, 0, 100);
            fail("should throw IOException");
        } catch (IOException e) {
            // Expected
        } finally {
            is.close();
        }

        try {
            is = new FileInputStream(fileName);
            is.close();
            is.read(buf, 0, 0);
        } finally {
            is.close();
        }
    }

    /**
     * java.io.FileInputStream#read(byte[], int, int)
     */
    public void test_read_$BII_NullPointerException() throws IOException {
        byte[] buf = null;
        try {
            is = new FileInputStream(fileName);
            is.read(buf, -1, 0);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected
        } finally {
            is.close();
        }
    }

    /**
     * java.io.FileInputStream#read(byte[], int, int)
     */
    public void test_read_$BII_IndexOutOfBoundsException() throws IOException {
        byte[] buf = new byte[1000];
        try {
            is = new FileInputStream(fileName);
            is.close();
            is.read(buf, -1, -1);
            fail("should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } finally {
            is.close();
        }
    }

    /**
     * java.io.FileInputStream#skip(long)
     */
    public void test_skipJ() throws IOException {
        byte[] buf1 = new byte[10];
        is = new FileInputStream(fileName);
        is.skip(1000);
        is.read(buf1, 0, buf1.length);
        is.close();
        assertEquals(INPUT.substring(1000, 1010),
                new String(buf1, 0, buf1.length, StandardCharsets.US_ASCII));

    }

    /**
     * java.io.FileInputStream#read(byte[], int, int))
     */
    public void test_regressionNNN() throws IOException {
        // Regression for HARMONY-434
        FileInputStream fis = new FileInputStream(fileName);

        try {
            fis.read(new byte[1], -1, 1);
            fail("IndexOutOfBoundsException must be thrown if off <0");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }

        try {
            fis.read(new byte[1], 0, -1);
            fail("IndexOutOfBoundsException must be thrown if len <0");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }

        try {
            fis.read(new byte[1], 0, 5);
            fail("IndexOutOfBoundsException must be thrown if off+len > b.length");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }

        try {
            fis.read(new byte[10], Integer.MAX_VALUE, 5);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }

        try {
            fis.read(new byte[10], 5, Integer.MAX_VALUE);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
        fis.close();
    }

    /**
     * java.io.FileInputStream#skip(long)
     */
    public void test_skipNegativeArgumentJ() throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        try {
            fis.skip(-5);
            fail("IOException must be thrown if number of bytes to skip <0");
        } catch (IOException e) {
            // Expected IOException
        } finally {
            fis.close();
        }
    }

    public void test_getChannel() throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        assertEquals(0, fis.getChannel().position());
        int r;
        int count = 1;
        while ((r = fis.read()) != -1) {
            assertEquals(count++, fis.getChannel().position());
        }
        fis.close();

        try {
            fis.getChannel().position();
            fail("should throw ClosedChannelException");
        } catch (java.nio.channels.ClosedChannelException e) {
            // Expected
        }

        fis = new FileInputStream(fileName);
        assertEquals(0, fis.getChannel().position());
        byte[] bs = new byte[10];
        r = fis.read(bs);
        assertEquals(10, fis.getChannel().position());
        fis.close();

        fis = new FileInputStream(fileName);
        assertEquals(0, fis.getChannel().position());
        bs = new byte[10];
        fis.skip(100);
        assertEquals(100, fis.getChannel().position());
        r = fis.read(bs);
        assertEquals(110, fis.getChannel().position());
        fis.close();
    }
}
