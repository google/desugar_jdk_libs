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

import junit.framework.TestCase;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This file is test for java.io.Console. Due to the redirect problem, it can
 * only be run on Harmony.
 *
 * @since 1.6
 */
public class ConsoleTest extends TestCase {
    private static final byte[] bytes = "hello world\n".getBytes();

    private InputStream in = new ByteArrayInputStream(bytes);
    private OutputStream out = new ByteArrayOutputStream();
    private Console console = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Constructor<Console> constructor =
                Console.class.getDeclaredConstructor(InputStream.class, OutputStream.class);
        constructor.setAccessible(true);
        console = constructor.newInstance(in, out);
    }

    @Override
    protected void tearDown() throws Exception {
        console = null;
        super.tearDown();
    }

    public void test_flush() {
        console.flush();
        assertFalse(console.writer().checkError());
    }

    public void test_format_LString_LObject() {
        assertSame(console, console.format("%d %s", 1, "hello"));
        String prompt = new String(((ByteArrayOutputStream) out).toByteArray());
        assertEquals("1 hello", prompt);
    }

    public void test_printf_LString_LObject() {
        Calendar c = new GregorianCalendar(1983, 2, 21);
        assertSame(console, console.printf("%1$tm %1$te,%1$tY", c));
        String prompt = new String(((ByteArrayOutputStream) out).toByteArray());
        assertEquals("03 21,1983", prompt);
    }

    public void test_reader() throws IOException {
        Reader reader1 = console.reader();
        assertTrue(reader1.ready());
        Reader reader2 = console.reader();
        assertSame(reader1, reader2);
    }

    public void test_readLine() {
        String line = console.readLine();
        assertEquals("hello world", line);
    }

    public void test_readLine_LString_LObject() {
        String line = console.readLine("%d %s", 2, "Please input a line of string to test:");
        assertEquals("hello world", line);
        String prompt = new String(((ByteArrayOutputStream) out).toByteArray());
        assertEquals("2 Please input a line of string to test:", prompt);
    }

    /**
     * {@link java.io.Console#writer()}
     */
    public void test_writer() {
        PrintWriter writer1 = console.writer();
        PrintWriter writer2 = console.writer();
        assertSame(writer1, writer2);
    }
}
