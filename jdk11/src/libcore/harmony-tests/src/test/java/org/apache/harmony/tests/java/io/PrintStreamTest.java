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
import java.io.FilterOutputStream;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;
import libcore.io.IoUtils;

public class PrintStreamTest extends junit.framework.TestCase {
    private static final String UNICODE_STRING =
            "K\u03B1\u03BB\u03B7\u00B5\u03B5\u00B4\u03C1\u03B1 \u03BA\u03BF\u00B4\u03C3\u00B5\u03B5";

    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    byte[] ibuf = new byte[4096];

    private File testFile = null;

    private String testFilePath = null;

    public String fileString = "Test_All_Tests\nTest_java_io_BufferedInputStream\nTest_java_io_BufferedOutputStream\nTest_java_io_ByteArrayInputStream\nTest_java_io_ByteArrayOutputStream\nTest_java_io_DataInputStream\nTest_java_io_File\nTest_java_io_FileDescriptor\nTest_java_io_FileInputStream\nTest_java_io_FileNotFoundException\nTest_java_io_FileOutputStream\nTest_java_io_FilterInputStream\nTest_java_io_FilterOutputStream\nTest_java_io_InputStream\nTest_java_io_IOException\nTest_java_io_OutputStream\nTest_PrintStream\nTest_java_io_RandomAccessFile\nTest_java_io_SyncFailedException\nTest_java_lang_AbstractMethodError\nTest_java_lang_ArithmeticException\nTest_java_lang_ArrayIndexOutOfBoundsException\nTest_java_lang_ArrayStoreException\nTest_java_lang_Boolean\nTest_java_lang_Byte\nTest_java_lang_Character\nTest_java_lang_Class\nTest_java_lang_ClassCastException\nTest_java_lang_ClassCircularityError\nTest_java_lang_ClassFormatError\nTest_java_lang_ClassLoader\nTest_java_lang_ClassNotFoundException\nTest_java_lang_CloneNotSupportedException\nTest_java_lang_Double\nTest_java_lang_Error\nTest_java_lang_Exception\nTest_java_lang_ExceptionInInitializerError\nTest_java_lang_Float\nTest_java_lang_IllegalAccessError\nTest_java_lang_IllegalAccessException\nTest_java_lang_IllegalArgumentException\nTest_java_lang_IllegalMonitorStateException\nTest_java_lang_IllegalThreadStateException\nTest_java_lang_IncompatibleClassChangeError\nTest_java_lang_IndexOutOfBoundsException\nTest_java_lang_InstantiationError\nTest_java_lang_InstantiationException\nTest_java_lang_Integer\nTest_java_lang_InternalError\nTest_java_lang_InterruptedException\nTest_java_lang_LinkageError\nTest_java_lang_Long\nTest_java_lang_Math\nTest_java_lang_NegativeArraySizeException\nTest_java_lang_NoClassDefFoundError\nTest_java_lang_NoSuchFieldError\nTest_java_lang_NoSuchMethodError\nTest_java_lang_NullPointerException\nTest_java_lang_Number\nTest_java_lang_NumberFormatException\nTest_java_lang_Object\nTest_java_lang_OutOfMemoryError\nTest_java_lang_RuntimeException\nTest_java_lang_SecurityManager\nTest_java_lang_Short\nTest_java_lang_StackOverflowError\nTest_java_lang_String\nTest_java_lang_StringBuffer\nTest_java_lang_StringIndexOutOfBoundsException\nTest_java_lang_System\nTest_java_lang_Thread\nTest_java_lang_ThreadDeath\nTest_java_lang_ThreadGroup\nTest_java_lang_Throwable\nTest_java_lang_UnknownError\nTest_java_lang_UnsatisfiedLinkError\nTest_java_lang_VerifyError\nTest_java_lang_VirtualMachineError\nTest_java_lang_vm_Image\nTest_java_lang_vm_MemorySegment\nTest_java_lang_vm_ROMStoreException\nTest_java_lang_vm_VM\nTest_java_lang_Void\nTest_java_net_BindException\nTest_java_net_ConnectException\nTest_java_net_DatagramPacket\nTest_java_net_DatagramSocket\nTest_java_net_DatagramSocketImpl\nTest_java_net_InetAddress\nTest_java_net_NoRouteToHostException\nTest_java_net_PlainDatagramSocketImpl\nTest_java_net_PlainSocketImpl\nTest_java_net_Socket\nTest_java_net_SocketException\nTest_java_net_SocketImpl\nTest_java_net_SocketInputStream\nTest_java_net_SocketOutputStream\nTest_java_net_UnknownHostException\nTest_java_util_ArrayEnumerator\nTest_java_util_Date\nTest_java_util_EventObject\nTest_java_util_HashEnumerator\nTest_java_util_Hashtable\nTest_java_util_Properties\nTest_java_util_ResourceBundle\nTest_java_util_tm\nTest_java_util_Vector\n";

    private static class MockPrintStream extends PrintStream {

        public MockPrintStream(String fileName) throws FileNotFoundException {
            super(fileName);
        }

        public MockPrintStream(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
            super(fileName, csn);
        }

        public MockPrintStream(OutputStream os) {
            super(os);
        }

        @Override
        public void clearError() {
            super.clearError();
        }

        @Override
        public void setError() {
            super.setError();
        }
    }

    /**
     * {@link java.io.PrintStream#PrintStream(String)}
     */
    public void test_Constructor_Ljava_lang_String() throws IOException {
        PrintStream os = new PrintStream(testFilePath);
        os.print(UNICODE_STRING);
        os.close();
        assertFileContents(UNICODE_STRING.getBytes(Charset.defaultCharset()), testFile);
    }

    /**
     * {@link java.io.PrintStream#PrintStream(String, String)}
     */
    public void test_Constructor_Ljava_lang_String_Ljava_lang_String() throws Exception {
        // Test that a bogus charset is mentioned in the exception
        try {
            new PrintStream(testFilePath, "Bogus");
            fail("Exception expected");
        } catch (UnsupportedEncodingException e) {
            assertNotNull(e.getMessage());
        }

        {
            PrintStream os = new PrintStream(testFilePath, "utf-8");
            os.print(UNICODE_STRING);
            os.close();
            assertFileContents(UNICODE_STRING.getBytes(StandardCharsets.UTF_8), testFile);
        }

        {
            PrintStream os = new PrintStream(testFilePath, "utf-16");
            os.print(UNICODE_STRING);
            os.close();
            assertFileContents(UNICODE_STRING.getBytes(StandardCharsets.UTF_16), testFile);
        }
    }

    /**
     * java.io.PrintStream#PrintStream(java.io.OutputStream)
     */
    public void test_ConstructorLjava_io_OutputStream() throws Exception {
        // Test for method java.io.PrintStream(java.io.OutputStream)
        PrintStream os = new PrintStream(bos);
        os.print(2345.76834720202);
        os.close();

        // regression for HARMONY-1195
        try {
            os = new PrintStream(bos, true, null);
            fail("Should throw NPE");
        } catch (NullPointerException e) {
        }
    }

    /**
     * java.io.PrintStream#PrintStream(java.io.OutputStream, boolean)
     */
    public void test_ConstructorLjava_io_OutputStreamZ() {
        // Test for method java.io.PrintStream(java.io.OutputStream, boolean)
        PrintStream os = new PrintStream(bos);
        os.println(2345.76834720202);
        os.flush();
        assertTrue("Bytes not written", bos.size() > 0);
        os.close();
    }

    /**
     * java.io.PrintStream#PrintStream(java.io.OutputStream, boolean, String)
     */
    public void test_ConstructorLjava_io_OutputStreamZLjava_lang_String() throws Exception {
        try {
            new PrintStream(new ByteArrayOutputStream(), false,
                    "%Illegal_name!");
            fail("Expected UnsupportedEncodingException");
        } catch (UnsupportedEncodingException e) {
            // expected
        }

        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(bos, true /* autoFlush */, "utf-8");
            printStream.print(UNICODE_STRING);
            printStream.close();
            assertByteArraysEqual(UNICODE_STRING.getBytes(StandardCharsets.UTF_8),
                    bos.toByteArray());
        }

        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(bos, true /* autoFlush */, "utf-16");
            printStream.print(UNICODE_STRING);
            printStream.close();
            assertByteArraysEqual(UNICODE_STRING.getBytes(StandardCharsets.UTF_16),
                    bos.toByteArray());
        }
    }

    /**
     * java.io.PrintStream#checkError()
     */
    public void test_checkError() throws Exception {
        // Test for method boolean java.io.PrintStream.checkError()
        PrintStream os = new PrintStream(new OutputStream() {

            public void write(int b) throws IOException {
                throw new IOException();
            }

            public void write(byte[] b, int o, int l) throws IOException {
                throw new IOException();
            }
        });
        os.print(fileString.substring(0, 501));

        assertTrue("Checkerror should return true", os.checkError());
    }

    /**
     * {@link java.io.PrintStream#clearError()}
     */
    public void test_clearError() throws FileNotFoundException {
        MockPrintStream os = new MockPrintStream(testFilePath);
        assertFalse(os.checkError());
        os.setError();
        assertTrue(os.checkError());
        os.clearError();
        assertFalse(os.checkError());
        os.close();
    }

    /**
     * java.io.PrintStream#close()
     */
    public void test_close() throws Exception {
        // Test for method void java.io.PrintStream.close()
        PrintStream os = new PrintStream(bos);
        os.close();
        bos.close();
    }

    /**
     * java.io.PrintStream#flush()
     */
    public void test_flush() throws Exception {
        // Test for method void java.io.PrintStream.flush()
        PrintStream os = new PrintStream(bos);
        os.print(fileString.substring(0, 501));
        os.flush();
        assertEquals("Bytes not written after flush", 501, bos.size());
        bos.close();
        os.close();
    }

    /**
     * java.io.PrintStream#print(char[])
     */
    public void test_print$C() {
        // Test for method void java.io.PrintStream.print(char [])
        PrintStream os = new PrintStream(bos, true);
        try {
            os.print((char[]) null);
            fail("NPE expected");
        } catch (NullPointerException ok) {
        }

        os = new PrintStream(bos, true);
        char[] sc = new char[4000];
        fileString.getChars(0, fileString.length(), sc, 0);
        os.print(sc);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        os.close();

        byte[] rbytes = new byte[4000];
        bis.read(rbytes, 0, fileString.length());
        assertEquals("Incorrect char[] written", fileString, new String(rbytes,
                0, fileString.length()));
    }

    /**
     * java.io.PrintStream#print(char)
     */
    public void test_printC() throws Exception {
        // Test for method void java.io.PrintStream.print(char)
        PrintStream os = new PrintStream(bos, true);
        os.print('t');
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStreamReader isr = new InputStreamReader(bis);
        assertEquals("Incorrect char written", 't', isr.read());
    }

    /**
     * java.io.PrintStream#print(double)
     */
    public void test_printD() {
        // Test for method void java.io.PrintStream.print(double)
        byte[] rbuf = new byte[100];
        PrintStream os = new PrintStream(bos, true);
        os.print(2345.76834720202);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        bis.read(rbuf, 0, 16);
        assertEquals("Incorrect double written", "2345.76834720202",
                new String(rbuf, 0, 16));
    }

    /**
     * java.io.PrintStream#print(float)
     */
    public void test_printF() {
        // Test for method void java.io.PrintStream.print(float)
        PrintStream os = new PrintStream(bos, true);
        byte rbuf[] = new byte[10];
        os.print(29.08764f);
        os.flush();
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        bis.read(rbuf, 0, 8);
        assertEquals("Incorrect float written", "29.08764", new String(rbuf, 0,
                8));

    }

    /**
     * java.io.PrintStream#print(int)
     */
    public void test_printI() {
        // Test for method void java.io.PrintStream.print(int)
        PrintStream os = new PrintStream(bos, true);
        os.print(768347202);
        byte[] rbuf = new byte[18];
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        bis.read(rbuf, 0, 9);
        assertEquals("Incorrect int written", "768347202", new String(rbuf, 0,
                9));
    }

    /**
     * java.io.PrintStream#print(long)
     */
    public void test_printJ() {
        // Test for method void java.io.PrintStream.print(long)
        byte[] rbuf = new byte[100];
        PrintStream os = new PrintStream(bos, true);
        os.print(9875645283333L);
        os.close();
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        bis.read(rbuf, 0, 13);
        assertEquals("Incorrect long written", "9875645283333", new String(
                rbuf, 0, 13));
    }

    /**
     * java.io.PrintStream#print(java.lang.Object)
     */
    public void test_printLjava_lang_Object() throws Exception {
        // Test for method void java.io.PrintStream.print(java.lang.Object)
        PrintStream os = new PrintStream(bos, true);
        os.print((Object) null);
        os.flush();
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        byte[] nullbytes = new byte[4];
        bis.read(nullbytes, 0, 4);
        assertEquals("null should be written", "null", new String(nullbytes, 0,
                4));

        bis.close();
        bos.close();
        os.close();

        ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        os = new PrintStream(bos1, true);
        os.print(new java.util.Vector());
        bis = new ByteArrayInputStream(bos1.toByteArray());
        byte[] rbytes = new byte[2];
        bis.read(rbytes, 0, 2);
        assertEquals("Incorrect Object written", "[]", new String(rbytes, 0, 2));
    }

    /**
     * java.io.PrintStream#print(java.lang.String)
     */
    public void test_printLjava_lang_String() throws Exception {
        // Test for method void java.io.PrintStream.print(java.lang.String)
        PrintStream os = new PrintStream(bos, true);
        os.print((String) null);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        byte[] nullbytes = new byte[4];
        bis.read(nullbytes, 0, 4);
        assertEquals("null should be written", "null", new String(nullbytes, 0,
                4));

        bis.close();
        bos.close();
        os.close();

        ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        os = new PrintStream(bos1, true);
        os.print("Hello World");
        bis = new ByteArrayInputStream(bos1.toByteArray());
        byte rbytes[] = new byte[100];
        bis.read(rbytes, 0, 11);
        assertEquals("Incorrect string written", "Hello World", new String(
                rbytes, 0, 11));
    }

    /**
     * java.io.PrintStream#print(boolean)
     */
    public void test_printZ() throws Exception {
        // Test for method void java.io.PrintStream.print(boolean)
        PrintStream os = new PrintStream(bos, true);
        os.print(true);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bos
                .toByteArray()));

        assertTrue("Incorrect boolean written", dis.readBoolean());
    }

    /**
     * java.io.PrintStream#println()
     */
    public void test_println() throws Exception {
        // Test for method void java.io.PrintStream.println()
        char c;
        PrintStream os = new PrintStream(bos, true);
        os.println("");
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStreamReader isr = new InputStreamReader(bis);
        assertTrue("Newline not written", (c = (char) isr.read()) == '\r'
                || c == '\n');
    }

    /**
     * java.io.PrintStream#println(char[])
     */
    public void test_println$C() throws Exception {
        // Test for method void java.io.PrintStream.println(char [])
        PrintStream os = new PrintStream(bos, true);
        char[] sc = new char[4000];
        fileString.getChars(0, fileString.length(), sc, 0);
        os.println(sc);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStreamReader isr = new InputStreamReader(bis);
        byte[] rbytes = new byte[4000];
        bis.read(rbytes, 0, fileString.length());
        assertEquals("Incorrect char[] written", fileString, new String(rbytes,
                0, fileString.length()));

        // In this particular test method, the end of data is not immediately
        // followed by newLine separator in the reading buffer, instead its
        // followed by zeros. The newline is written as the last entry
        // in the inputStream buffer. Therefore, we must keep reading until we
        // hit a new line.
        int r;
        boolean newline = false;
        while ((r = isr.read()) != -1) {
            if (r == '\r' || r == '\n')
                newline = true;
        }
        assertTrue("Newline not written", newline);
    }

    /**
     * java.io.PrintStream#println(char)
     */
    public void test_printlnC() throws Exception {
        // Test for method void java.io.PrintStream.println(char)
        int c;
        PrintStream os = new PrintStream(bos, true);
        os.println('t');
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStreamReader isr = new InputStreamReader(bis);
        assertEquals("Incorrect char written", 't', isr.read());
        assertTrue("Newline not written", (c = isr.read()) == '\r' || c == '\n');
    }

    /**
     * java.io.PrintStream#println(double)
     */
    public void test_printlnD() throws Exception {
        // Test for method void java.io.PrintStream.println(double)
        int c;
        PrintStream os = new PrintStream(bos, true);
        os.println(2345.76834720202);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStreamReader isr = new InputStreamReader(bis);
        byte[] rbuf = new byte[100];
        bis.read(rbuf, 0, 16);
        assertEquals("Incorrect double written", "2345.76834720202",
                new String(rbuf, 0, 16));
        assertTrue("Newline not written", (c = isr.read()) == '\r' || c == '\n');
    }

    /**
     * java.io.PrintStream#println(float)
     */
    public void test_printlnF() throws Exception {
        // Test for method void java.io.PrintStream.println(float)
        int c;
        byte[] rbuf = new byte[100];
        PrintStream os = new PrintStream(bos, true);
        os.println(29.08764f);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStreamReader isr = new InputStreamReader(bis);
        bis.read(rbuf, 0, 8);
        assertEquals("Incorrect float written", "29.08764", new String(rbuf, 0,
                8));
        assertTrue("Newline not written", (c = isr.read()) == '\r' || c == '\n');
    }

    /**
     * java.io.PrintStream#println(int)
     */
    public void test_printlnI() throws Exception {
        // Test for method void java.io.PrintStream.println(int)
        int c;
        PrintStream os = new PrintStream(bos, true);
        os.println(768347202);
        byte[] rbuf = new byte[100];
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStreamReader isr = new InputStreamReader(bis);
        bis.read(rbuf, 0, 9);
        assertEquals("Incorrect int written", "768347202", new String(rbuf, 0,
                9));
        assertTrue("Newline not written", (c = isr.read()) == '\r' || c == '\n');
    }

    /**
     * java.io.PrintStream#println(long)
     */
    public void test_printlnJ() throws Exception {
        // Test for method void java.io.PrintStream.println(long)
        int c;
        PrintStream os = new PrintStream(bos, true);
        os.println(9875645283333L);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStreamReader isr = new InputStreamReader(bis);
        byte[] rbuf = new byte[100];
        bis.read(rbuf, 0, 13);
        assertEquals("Incorrect long written", "9875645283333", new String(
                rbuf, 0, 13));
        assertTrue("Newline not written", (c = isr.read()) == '\r' || c == '\n');
    }

    /**
     * java.io.PrintStream#println(java.lang.Object)
     */
    public void test_printlnLjava_lang_Object() throws Exception {
        // Test for method void java.io.PrintStream.println(java.lang.Object)
        char c;
        PrintStream os = new PrintStream(bos, true);
        os.println(new java.util.Vector());
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStreamReader isr = new InputStreamReader(bis);
        byte[] rbytes = new byte[2];
        bis.read(rbytes, 0, 2);
        assertEquals("Incorrect Vector written", "[]", new String(rbytes, 0, 2));
        assertTrue("Newline not written", (c = (char) isr.read()) == '\r'
                || c == '\n');
    }

    /**
     * java.io.PrintStream#println(java.lang.String)
     */
    public void test_printlnLjava_lang_String() throws Exception {
        // Test for method void java.io.PrintStream.println(java.lang.String)
        char c;
        PrintStream os = new PrintStream(bos, true);
        os.println("Hello World");
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStreamReader isr = new InputStreamReader(bis);
        byte rbytes[] = new byte[100];
        bis.read(rbytes, 0, 11);
        assertEquals("Incorrect string written", "Hello World", new String(
                rbytes, 0, 11));
        assertTrue("Newline not written", (c = (char) isr.read()) == '\r'
                || c == '\n');
    }

    /**
     * java.io.PrintStream#println(boolean)
     */
    public void test_printlnZ() throws Exception {
        // Test for method void java.io.PrintStream.println(boolean)
        int c;
        PrintStream os = new PrintStream(bos, true);
        os.println(true);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        InputStreamReader isr = new InputStreamReader(bis);
        byte[] rbuf = new byte[100];
        bis.read(rbuf, 0, 4);
        assertEquals("Incorrect boolean written", "true",
                new String(rbuf, 0, 4));
        assertTrue("Newline not written", (c = isr.read()) == '\r' || c == '\n');
    }

    /**
     * java.io.PrintStream#write(byte[], int, int)
     */
    public void test_write$BII() {
        // Test for method void java.io.PrintStream.write(byte [], int, int)
        PrintStream os = new PrintStream(bos, true);
        os.write(fileString.getBytes(), 0, fileString.length());
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        byte rbytes[] = new byte[4000];
        bis.read(rbytes, 0, fileString.length());
        assertTrue("Incorrect bytes written", new String(rbytes, 0, fileString
                .length()).equals(fileString));
    }

    /**
     * java.io.PrintStream#write(int)
     */
    public void test_writeI() {
        // Test for method void java.io.PrintStream.write(int)
        PrintStream os = new PrintStream(bos, true);
        os.write('t');
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        assertEquals("Incorrect char written", 't', bis.read());
    }

    /**
     * java.io.PrintStream#append(char)
     */
    public void test_appendChar() throws IOException {
        char testChar = ' ';
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        printStream.append(testChar);
        printStream.flush();
        assertEquals(String.valueOf(testChar), out.toString());
        printStream.close();
    }

    /**
     * java.io.PrintStream#append(CharSequence)
     */
    public void test_appendCharSequence() {
        String testString = "My Test String";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        printStream.append(testString);
        printStream.flush();
        assertEquals(testString, out.toString());
        printStream.close();
    }

    /**
     * java.io.PrintStream#append(CharSequence, int, int)
     */
    public void test_appendCharSequenceIntInt() {
        String testString = "My Test String";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        printStream.append(testString, 1, 3);
        printStream.flush();
        assertEquals(testString.substring(1, 3), out.toString());
        printStream.close();
    }

    /**
     * java.io.PrintStream#format(java.lang.String, java.lang.Object...)
     */
    public void test_formatLjava_lang_String$Ljava_lang_Object() {
        PrintStream os = new PrintStream(bos, false);
        os.format("%s %s", "Hello", "World");
        os.flush();
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        byte[] rbytes = new byte[11];
        bis.read(rbytes, 0, rbytes.length);
        assertEquals("Wrote incorrect string", "Hello World",
                new String(rbytes));

    }

    /**
     * java.io.PrintStream#format(java.util.Locale, java.lang.String,
     *java.lang.Object...)
     */
    public void test_formatLjava_util_Locale_Ljava_lang_String_$Ljava_lang_Object() {
        PrintStream os = new PrintStream(bos, false);
        os.format(Locale.US, "%s %s", "Hello", "World");
        os.flush();
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        byte[] rbytes = new byte[11];
        bis.read(rbytes, 0, rbytes.length);
        assertEquals("Wrote incorrect string", "Hello World",
                new String(rbytes));
    }

    /**
     * Tests that a PrintStream with {@code autoFlush == true} will call
     * {@link OutputStream#flush()} at least once, after the last byte
     * was written.
     */
    public void test_autoFlush_flushesEverything() {
        CountFlushOutputStream counter = new CountFlushOutputStream(new ByteArrayOutputStream());
        PrintStream printStream = new PrintStream(counter, true /* autoFlush */);
        printStream.print("Hello, world!");
        assertTrue(counter.hasBeenFlushedSinceLastWrite());
        printStream.print(Math.PI);
        assertTrue(counter.hasBeenFlushedSinceLastWrite());
        printStream.print("\n");
        assertTrue(counter.hasBeenFlushedSinceLastWrite());
        printStream.println();
        assertTrue(counter.hasBeenFlushedSinceLastWrite());
        printStream.println("Lots\nof\nnewlines\n");
        assertTrue(counter.hasBeenFlushedSinceLastWrite());
        printStream.print("Line 1\nLine 2\n".toCharArray());
        assertTrue(counter.hasBeenFlushedSinceLastWrite());

        byte[] bytes = "Line without a newline".getBytes(StandardCharsets.UTF_8);
        printStream.write(bytes, 0, bytes.length);
        assertTrue(counter.hasBeenFlushedSinceLastWrite());
    }

    /**
     * Tests that a PrintStream with {@code autoFlush == false} will not
     * call {@link OutputStream#flush()} in regular (non-error) operation.
     */
    public void test_noAutoFlush() {
        CountFlushOutputStream counter = new CountFlushOutputStream(new ByteArrayOutputStream());
        PrintStream printStream = new PrintStream(counter, false /* autoFlush */);
        printStream.print("Hello, world!");
        printStream.print(Math.PI);
        printStream.print("\n");
        printStream.println();
        printStream.println("Lots\nof\nnewlines\n");
        printStream.print("Line 1\nLine 2\n".toCharArray());
        byte[] bytes = "Line without a newline".getBytes(StandardCharsets.UTF_8);
        printStream.write(bytes, 0, bytes.length);
        assertFalse(counter.hasEverBeenFlushed());
        assertFalse(counter.hasBeenFlushedSinceLastWrite());

        // checkError() still causes the PrintStream to flush(), even when autoFlush == false.
        printStream.checkError();
        assertTrue(counter.hasEverBeenFlushed());
        assertTrue(counter.hasBeenFlushedSinceLastWrite());
        printStream.print("This data\nwill not be flushed.");
        assertTrue(counter.hasEverBeenFlushed());
        assertFalse(counter.hasBeenFlushedSinceLastWrite());
    }

    /**
     * java.io.PrintStream#printf(java.lang.String, java.lang.Object...)
     */
    public void test_printfLjava_lang_String$Ljava_lang_Object() {
        PrintStream os = new PrintStream(bos, false);
        os.printf("%s %s", "Hello", "World");
        os.flush();
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        byte[] rbytes = new byte[11];
        bis.read(rbytes, 0, rbytes.length);
        assertEquals("Wrote incorrect string", "Hello World",
                new String(rbytes));
    }

    /**
     * java.io.PrintStream#printf(java.util.Locale, java.lang.String,
     *java.lang.Object...)
     */
    public void test_printfLjava_util_Locale_Ljava_lang_String_$Ljava_lang_Object() {
        PrintStream os = new PrintStream(bos, false);
        os.printf(Locale.US, "%s %s", "Hello", "World");
        os.flush();
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        byte[] rbytes = new byte[11];
        bis.read(rbytes, 0, rbytes.length);
        assertEquals("Wrote incorrect string", "Hello World",
                new String(rbytes));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testFile = File.createTempFile("test", null);
        testFilePath = testFile.getAbsolutePath();
    }

    @Override
    protected void tearDown() throws Exception {
        testFile.delete();
        testFile = null;
        testFilePath = null;
        super.tearDown();
    }

    private static void assertByteArraysEqual(byte[] expected, byte[] actual) {
        String message = "Expected " + Base64.getEncoder().encodeToString(expected) + ", got: "
                + Base64.getEncoder().encodeToString(actual);
        assertTrue(message, Arrays.equals(actual, expected));
    }

    private static void assertFileContents(byte[] expected, File file) throws IOException {
        byte[] actual = IoUtils.readFileAsByteArray(file.getAbsolutePath());
        assertByteArraysEqual(expected, actual);
    }

    static class CountFlushOutputStream extends FilterOutputStream {
        private boolean hasBeenFlushedSinceLastWrite = false;
        private boolean hasEverBeenFlushed = false;

        public CountFlushOutputStream(OutputStream delegate) {
            super(delegate);
        }

        @Override
        public void write(int b) throws IOException {
            super.write(b);
            hasBeenFlushedSinceLastWrite = false;
        }

        @Override
        public void flush() throws IOException {
            super.flush();
            hasBeenFlushedSinceLastWrite = true;
            hasEverBeenFlushed = true;
        }

        /** Whether {@link #flush()} has been called since the last write. */
        public boolean hasBeenFlushedSinceLastWrite() {
            return hasBeenFlushedSinceLastWrite;
        }

        /** Whether {@link #flush()} has ever been called after this stream was constructed. */
        public boolean hasEverBeenFlushed() {
            return hasEverBeenFlushed;
        }
    }

}
