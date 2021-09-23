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
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CountDownLatch;

public class PipedInputStreamTest extends junit.framework.TestCase {

    static class PWriter implements Runnable {
        PipedOutputStream pos;

        public byte bytes[];

        public void run() {
            try {
                pos.write(bytes);
                synchronized (this) {
                    notify();
                }
            } catch (IOException e) {
                e.printStackTrace(System.out);
                System.out.println("Could not write bytes");
            }
        }

        public PWriter(PipedOutputStream pout, int nbytes) {
            pos = pout;
            bytes = new byte[nbytes];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (System.currentTimeMillis() % 9);
            }
        }
    }

    Thread t;

    PWriter pw;

    PipedInputStream pis;

    PipedOutputStream pos;

    /**
     * java.io.PipedInputStream#PipedInputStream()
     */
    public void test_Constructor() {
        // Test for method java.io.PipedInputStream()
        // Used in tests
    }

    /**
     * java.io.PipedInputStream#PipedInputStream(java.io.PipedOutputStream)
     */
    public void test_ConstructorLjava_io_PipedOutputStream() throws Exception {
        // Test for method java.io.PipedInputStream(java.io.PipedOutputStream)
        pis = new PipedInputStream(new PipedOutputStream());
        pis.available();
    }


    public void test_readException() throws IOException {
        pis = new PipedInputStream();
        pos = new PipedOutputStream();

        try {
            pis.connect(pos);
            t = new Thread(pw = new PWriter(pos, 1000));
            t.start();
            while (true) {
                pis.read();
                t.interrupt();
            }
        } catch (IOException expected) {
        } finally {
            try {
                pis.close();
                pos.close();
            } catch (IOException ee) {
            }
        }
    }

    /**
     * java.io.PipedInputStream#available()
     */
    public void test_available() throws Exception {
        pis = new PipedInputStream();
        pos = new PipedOutputStream();

        pis.connect(pos);
        t = new Thread(pw = new PWriter(pos, 1000));
        t.start();

        synchronized (pw) {
            pw.wait(10000);
        }
        assertTrue("Available returned incorrect number of bytes: "
                + pis.available(), pis.available() == 1000);

        PipedInputStream pin = new PipedInputStream();
        PipedOutputStream pout = new PipedOutputStream(pin);
        // We know the PipedInputStream buffer size is 1024.
        // Writing another byte would cause the write to wait
        // for a read before returning
        for (int i = 0; i < 1024; i++) {
            pout.write(i);
        }
        assertEquals("Incorrect available count", 1024, pin.available());
    }

    /**
     * java.io.PipedInputStream#close()
     */
    public void test_close() throws IOException {
        // Test for method void java.io.PipedInputStream.close()
        pis = new PipedInputStream();
        pos = new PipedOutputStream();
        pis.connect(pos);
        pis.close();
        try {
            pos.write((byte) 127);
            fail("Failed to throw expected exception");
        } catch (IOException e) {
            // The spec for PipedInput saya an exception should be thrown if
            // a write is attempted to a closed input. The PipedOuput spec
            // indicates that an exception should be thrown only when the
            // piped input thread is terminated without closing
            return;
        }
    }

    /**
     * java.io.PipedInputStream#connect(java.io.PipedOutputStream)
     */
    public void test_connectLjava_io_PipedOutputStream() throws Exception {
        pis = new PipedInputStream();
        pos = new PipedOutputStream();
        assertEquals("Non-conected pipe returned non-zero available bytes", 0,
                pis.available());

        pis.connect(pos);
        t = new Thread(pw = new PWriter(pos, 1000));
        t.start();

        synchronized (pw) {
            pw.wait(10000);
        }
        assertEquals("Available returned incorrect number of bytes", 1000, pis
                .available());
    }

    /**
     * java.io.PipedInputStream#read()
     */
    public void test_read() throws Exception {
        pis = new PipedInputStream();
        pos = new PipedOutputStream();

        pis.connect(pos);
        t = new Thread(pw = new PWriter(pos, 1000));
        t.start();

        synchronized (pw) {
            pw.wait(10000);
        }
        assertEquals("Available returned incorrect number of bytes", 1000, pis
                .available());
        assertEquals("read returned incorrect byte", pw.bytes[0], (byte) pis
                .read());
    }

    /**
     * java.io.PipedInputStream#read(byte[], int, int)
     */
    public void test_read$BII() throws Exception {
        pis = new PipedInputStream();
        pos = new PipedOutputStream();

        pis.connect(pos);
        t = new Thread(pw = new PWriter(pos, 1000));
        t.start();

        byte[] buf = new byte[400];
        synchronized (pw) {
            pw.wait(10000);
        }
        assertTrue("Available returned incorrect number of bytes: "
                + pis.available(), pis.available() == 1000);
        pis.read(buf, 0, 400);
        for (int i = 0; i < 400; i++) {
            assertEquals("read returned incorrect byte[]", pw.bytes[i], buf[i]);
        }
    }

    /**
     * java.io.PipedInputStream#read(byte[], int, int)
     * Regression for HARMONY-387
     */
    public void test_read$BII_2() throws IOException {
        PipedInputStream obj = new PipedInputStream();
        try {
            obj.read(new byte[0], 0, -1);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    /**
     * java.io.PipedInputStream#read(byte[], int, int)
     */
    public void test_read$BII_3() throws IOException {
        PipedInputStream obj = new PipedInputStream();
        try {
            obj.read(new byte[0], -1, 0);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    /**
     * java.io.PipedInputStream#read(byte[], int, int)
     */
    public void test_read$BII_4() throws IOException {
        PipedInputStream obj = new PipedInputStream();
        try {
            obj.read(new byte[0], -1, -1);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    /**
     * java.io.PipedInputStream#receive(int)
     */
    public void test_write_failsAfterReaderDead() throws Exception {
        pis = new PipedInputStream();
        pos = new PipedOutputStream();

        // test if writer recognizes dead reader
        pis.connect(pos);

        class WriteRunnable implements Runnable {

            final CountDownLatch readerAlive = new CountDownLatch(1);

            public void run() {
                try {
                    pos.write(1);

                    try {
                        readerAlive.await();
                    } catch (InterruptedException ie) {
                        fail();
                        return;
                    }

                    try {
                        // should throw exception since reader thread
                        // is now dead
                        pos.write(1);
                        fail();
                    } catch (IOException expected) {
                    }
                } catch (IOException e) {
                }
            }
        }

        class ReadRunnable implements Runnable {
            public void run() {
                try {
                    pis.read();
                } catch (IOException e) {
                    fail();
                }
            }
        }

        WriteRunnable writeRunnable = new WriteRunnable();
        Thread writeThread = new Thread(writeRunnable);

        ReadRunnable readRunnable = new ReadRunnable();
        Thread readThread = new Thread(readRunnable);
        writeThread.start();
        readThread.start();
        readThread.join();

        writeRunnable.readerAlive.countDown();
        writeThread.join();
    }

    static final class PipedInputStreamWithPublicReceive extends PipedInputStream {
        @Override
        public void receive(int oneByte) throws IOException {
            super.receive(oneByte);
        }
    }


    public void test_receive_failsIfWriterClosed() throws Exception {
        // attempt to write to stream after writer closed
        PipedInputStreamWithPublicReceive pis = new PipedInputStreamWithPublicReceive();

        pos = new PipedOutputStream();
        pos.connect(pis);
        pos.close();
        try {
            pis.receive(1);
            fail();
        } catch (IOException expected) {
        }
    }

    static class Worker extends Thread {
        PipedOutputStream out;

        Worker(PipedOutputStream pos) {
            this.out = pos;
        }

        public void run() {
            try {
                out.write(20);
                out.close();
                Thread.sleep(5000);
            } catch (Exception e) {
            }
        }
    }

    public void test_read_after_write_close() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream();
        in.connect(out);
        Thread worker = new Worker(out);
        worker.start();
        Thread.sleep(2000);
        assertEquals("Should read 20.", 20, in.read());
        worker.join();
        assertEquals("Write end is closed, should return -1", -1, in.read());
        byte[] buf = new byte[1];
        assertEquals("Write end is closed, should return -1", -1, in.read(buf, 0, 1));
        assertEquals("Buf len 0 should return first", 0, in.read(buf, 0, 0));
        in.close();
        out.close();
    }

    /**
     * Tears down the fixture, for example, close a network connection. This
     * method is called after a test is executed.
     */
    protected void tearDown() throws Exception {
        try {
            if (t != null) {
                t.interrupt();
            }
        } catch (Exception ignore) {
        }
        super.tearDown();
    }


    /**
     * java.io.PipedInputStream#PipedInputStream(java.io.PipedOutputStream,
     *int)
     * @since 1.6
     */
    public void test_Constructor_LPipedOutputStream_I() throws Exception {
        // Test for method java.io.PipedInputStream(java.io.PipedOutputStream,
        // int)
        MockPipedInputStream mpis = new MockPipedInputStream(
                new PipedOutputStream(), 100);
        int bufferLength = mpis.bufferLength();
        assertEquals(100, bufferLength);

        try {
            pis = new PipedInputStream(null, -1);
            fail("Should throw IllegalArgumentException"); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            pis = new PipedInputStream(null, 0);
            fail("Should throw IllegalArgumentException"); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * java.io.PipedInputStream#PipedInputStream(int)
     * @since 1.6
     */
    public void test_Constructor_I() throws Exception {
        // Test for method java.io.PipedInputStream(int)
        MockPipedInputStream mpis = new MockPipedInputStream(100);
        int bufferLength = mpis.bufferLength();
        assertEquals(100, bufferLength);

        try {
            pis = new PipedInputStream(-1);
            fail("Should throw IllegalArgumentException"); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            pis = new PipedInputStream(0);
            fail("Should throw IllegalArgumentException"); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static class MockPipedInputStream extends PipedInputStream {

        public MockPipedInputStream(java.io.PipedOutputStream src,
                int bufferSize) throws IOException {
            super(src, bufferSize);
        }

        public MockPipedInputStream(int bufferSize) {
            super(bufferSize);
        }

        public int bufferLength() {
            return super.buffer.length;
        }
    }
}
