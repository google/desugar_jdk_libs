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

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

import junit.framework.TestCase;

public class PipedReaderTest extends TestCase {

    static class PWriter implements Runnable {
        public PipedWriter pw;

        public PWriter(PipedReader reader) {
            try {
                pw = new PipedWriter(reader);
            } catch (Exception e) {
                System.out.println("Couldn't create writer");
            }
        }

        public PWriter() {
            pw = new PipedWriter();
        }

        public void run() {
            try {
                char[] c = new char[11];
                "Hello World".getChars(0, 11, c, 0);
                pw.write(c);
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            } catch (Exception e) {
                System.out.println("Exception occurred: " + e.toString());
            }
        }
    }

    PipedReader preader;

    PWriter pwriter;

    Thread t;

    /**
     * java.io.PipedReader#PipedReader()
     */
    public void test_Constructor() {
        // Used in test
    }

    /**
     * java.io.PipedReader#PipedReader(java.io.PipedWriter)
     */
    public void test_ConstructorLjava_io_PipedWriter() throws IOException {
        preader = new PipedReader(new PipedWriter());
    }

    /**
     * java.io.PipedReader#PipedReader(java.io.PipedWriter,
     *int)
     * @since 1.6
     */
    public void test_Constructor_LPipedWriter_I() throws Exception {
        // Test for method java.io.PipedReader(java.io.PipedWriter,
        // int)
        try {
            preader = new PipedReader(null, -1);
            fail("Should throw IllegalArgumentException"); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            preader = new PipedReader(null, 0);
            fail("Should throw IllegalArgumentException"); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * java.io.PipedReader#PipedReader(int)
     * @since 1.6
     */
    public void test_Constructor_I() throws Exception {
        // Test for method java.io.PipedReader(int)
        try {
            preader = new PipedReader(-1);
            fail("Should throw IllegalArgumentException"); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            preader = new PipedReader(0);
            fail("Should throw IllegalArgumentException"); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * java.io.PipedReader#close()
     */
    public void test_close() throws Exception {
        char[] c = null;
        preader = new PipedReader();
        t = new Thread(new PWriter(preader), "");
        t.start();
        Thread.sleep(500); // Allow writer to start
        c = new char[11];
        preader.read(c, 0, 11);
        preader.close();
        assertEquals("Read incorrect chars", "Hello World", new String(c));
    }

    /**
     * java.io.PipedReader#connect(java.io.PipedWriter)
     */
    public void test_connectLjava_io_PipedWriter() throws Exception {
        char[] c = null;

        preader = new PipedReader();
        t = new Thread(pwriter = new PWriter(), "");
        preader.connect(pwriter.pw);
        t.start();
        Thread.sleep(500); // Allow writer to start
        c = new char[11];
        preader.read(c, 0, 11);

        assertEquals("Read incorrect chars", "Hello World", new String(c));
        try {
            preader.connect(pwriter.pw);
            fail("Failed to throw exception connecting to pre-connected reader");
        } catch (IOException e) {
            // Expected
        }
    }

    /**
     * java.io.PipedReader#read()
     */
    public void test_read() throws Exception {
        char[] c = null;
        preader = new PipedReader();
        t = new Thread(new PWriter(preader), "");
        t.start();
        Thread.sleep(500); // Allow writer to start
        c = new char[11];
        for (int i = 0; i < c.length; i++) {
            c[i] = (char) preader.read();
        }
        assertEquals("Read incorrect chars", "Hello World", new String(c));
    }

    /**
     * java.io.PipedReader#read(char[], int, int)
     */
    public void test_read$CII() throws Exception {
        char[] c = null;
        preader = new PipedReader();
        t = new Thread(new PWriter(preader), "");
        t.start();
        Thread.sleep(500); // Allow writer to start
        c = new char[11];
        int n = 0;
        int x = n;
        while (x < 11) {
            n = preader.read(c, x, 11 - x);
            x = x + n;
        }
        assertEquals("Read incorrect chars", "Hello World", new String(c));
        try {
            preader.close();
            preader.read(c, 8, 7);
            fail("Failed to throw exception reading from closed reader");
        } catch (IOException e) {
            // Expected
        }
    }

    public void test_read$CII_ExceptionPriority() throws IOException {
        // Regression for HARMONY-387
        PipedWriter pw = new PipedWriter();
        PipedReader obj = null;
        try {
            obj = new PipedReader(pw);
            obj.read(new char[0], (int) 0, (int) -1);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    public void test_read$CII_ExceptionPriority2() throws IOException {
        PipedWriter pw = new PipedWriter();
        PipedReader obj = null;
        try {
            obj = new PipedReader(pw);
            obj.read(new char[0], (int) -1, (int) 0);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    public void test_read$CII_ExceptionPriority3() throws IOException {
        PipedWriter pw = new PipedWriter();
        PipedReader obj = null;
        try {
            obj = new PipedReader(pw);
            obj.read(new char[0], (int) -1, (int) -1);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    public void test_read$CII_ExceptionPriority4() throws IOException {
        PipedWriter pw = new PipedWriter();
        PipedReader pr = new PipedReader(pw);
        try {
            pr.read(null, -1, 1);
            fail();
        } catch (NullPointerException expected) {
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    public void test_read_$CII_IOException() throws IOException {
        PipedWriter pw = new PipedWriter();
        PipedReader pr = new PipedReader(pw);
        pr.close();
        try {
            pr.read(null, 0, 10);
            fail("Should throws IOException"); //$NON-NLS-1$
        } catch (IOException e) {
            // expected
        } finally {
            pw = null;
            pr = null;
        }

        pr = new PipedReader();
        pr.close();
        try {
            pr.read(null, 0, 10);
            fail("Should throws IOException"); //$NON-NLS-1$
        } catch (IOException e) {
            // expected
        } finally {
            pr = null;
        }

        pw = new PipedWriter();
        pr = new PipedReader(pw);
        pr.close();
        try {
            pr.read(new char[10], -1, 0);
            fail("Should throws IOException"); //$NON-NLS-1$
        } catch (IOException e) {
            // expected
        } finally {
            pw = null;
            pr = null;
        }

        pw = new PipedWriter();
        pr = new PipedReader(pw);
        pr.close();
        try {
            pr.read(new char[10], 0, -1);
            fail("Should throws IOException"); //$NON-NLS-1$
        } catch (IOException e) {
            // expected
        } finally {
            pw = null;
            pr = null;
        }

        pw = new PipedWriter();
        pr = new PipedReader(pw);
        pr.close();
        try {
            pr.read(new char[10], 1, 10);
            fail("Should throws IOException"); //$NON-NLS-1$
        } catch (IOException e) {
            // expected
        } finally {
            pw = null;
            pr = null;
        }

        pw = new PipedWriter();
        pr = new PipedReader(pw);
        pr.close();
        try {
            pr.read(new char[0], -1, -1);
            fail("should throw IOException"); //$NON-NLS-1$
        } catch (IOException e) {
            // expected
        } finally {
            pw = null;
            pr = null;
        }

        pw = new PipedWriter();
        pr = new PipedReader(pw);
        pr.close();
        try {
            pr.read(null, 0, 1);
            fail("should throw IOException"); //$NON-NLS-1$
        } catch (IOException e) {
            // expected
        } finally {
            pw = null;
            pr = null;
        }

        pw = new PipedWriter();
        pr = new PipedReader(pw);
        try {
            pr.read(null, 0, -1);
            fail("should throw NullPointerException"); //$NON-NLS-1$
        } catch (NullPointerException e) {
            // expected
        } finally {
            pw = null;
            pr = null;
        }

        pw = new PipedWriter();
        pr = new PipedReader(pw);
        try {
            pr.read(new char[10], 11, 0);
            fail("should throw IndexOutOfBoundsException"); //$NON-NLS-1$
        } catch (IndexOutOfBoundsException e) {
            // expected
        } finally {
            pw = null;
            pr = null;
        }

        pw = new PipedWriter();
        pr = new PipedReader(pw);
        try {
            pr.read(null, 1, 0);
            fail("should throw NullPointerException"); //$NON-NLS-1$
        } catch (NullPointerException e) {
            // expected
        } finally {
            pw = null;
            pr = null;
        }
    }

    /**
     * java.io.PipedReader#ready()
     */
    public void test_ready() throws Exception {
        char[] c = null;
        preader = new PipedReader();
        t = new Thread(new PWriter(preader), "");
        t.start();
        Thread.sleep(500); // Allow writer to start
        assertTrue("Reader should be ready", preader.ready());
        c = new char[11];
        for (int i = 0; i < c.length; i++)
            c[i] = (char) preader.read();
        assertFalse("Reader should not be ready after reading all chars",
                preader.ready());
    }

    /**
     * Tears down the fixture, for example, close a network connection. This
     * method is called after a test is executed.
     */
    protected void tearDown() throws Exception {
        if (t != null) {
            t.interrupt();
        }
        super.tearDown();
    }
}
