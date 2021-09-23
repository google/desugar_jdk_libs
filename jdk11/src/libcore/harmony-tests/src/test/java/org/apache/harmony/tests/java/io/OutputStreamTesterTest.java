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

import junit.framework.TestSuite;
import org.apache.harmony.testframework.SinkTester;
import org.apache.harmony.testframework.WrapperTester;
import tests.support.Streams;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Tests basic {@link OutputStream} behaviors for the luni implementations of
 * the type.
 */
public class OutputStreamTesterTest {

    // TODO: Rewrite this test so that id doesn't need a suite().
    private static junit.framework.Test suite() {
        TestSuite suite = new TestSuite();

        // sink tests
        suite.addTest(new FileOutputStreamSinkTester(true).createTests());
        suite.addTest(new FileOutputStreamSinkTester(false).createTests());
        suite.addTest(new ByteArrayOutputStreamSinkTester(0).setThrowsExceptions(false).createTests());
        suite.addTest(new ByteArrayOutputStreamSinkTester(4).setThrowsExceptions(false).createTests());
        suite.addTest(new PipedOutputStreamSinkTester().createTests());

        // wrapper tests
        suite.addTest(new BufferedOutputStreamTester(1).createTests());
        suite.addTest(new BufferedOutputStreamTester(5).createTests());
        suite.addTest(new BufferedOutputStreamTester(1024).createTests());
        suite.addTest(new FilterOutputStreamTester().createTests());
        suite.addTest(new DataOutputStreamTester().createTests());
        // fails wrapperTestFlushThrowsViaClose() and sinkTestWriteAfterClose():
        // suite.addTest(new ObjectOutputStreamTester().createTests());
        suite.addTest(new PrintStreamTester().setThrowsExceptions(false).createTests());

        return suite;
    }

    private static class FileOutputStreamSinkTester extends SinkTester {

        private final boolean append;
        private File file;

        private FileOutputStreamSinkTester(boolean append) {
            this.append = append;
        }

        public OutputStream create() throws IOException {
            file = File.createTempFile("FileOutputStreamSinkTester", "tmp");
            file.deleteOnExit();
            return new FileOutputStream(file, append);
        }

        public byte[] getBytes() throws IOException {
            return Streams.streamToBytes(new FileInputStream(file));
        }
    }

    private static class ByteArrayOutputStreamSinkTester extends SinkTester {

        private final int size;
        private ByteArrayOutputStream stream;

        private ByteArrayOutputStreamSinkTester(int size) {
            this.size = size;
        }

        public OutputStream create() throws IOException {
            stream = new ByteArrayOutputStream(size);
            return stream;
        }

        public byte[] getBytes() throws IOException {
            return stream.toByteArray();
        }
    }

    private static class PipedOutputStreamSinkTester extends SinkTester {

        private ExecutorService executor;
        private Future<byte[]> future;

        public OutputStream create() throws IOException {
            final PipedInputStream in = new PipedInputStream();
            PipedOutputStream out = new PipedOutputStream(in);

            executor = Executors.newSingleThreadExecutor();
            future = executor.submit(new Callable<byte[]>() {
                final ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                public byte[] call() throws Exception {
                    byte[] buffer = new byte[256];
                    int count;
                    while ((count = in.read(buffer)) != -1) {
                        bytes.write(buffer, 0, count);
                    }
                    return bytes.toByteArray();
                }
            });

            return out;
        }

        public byte[] getBytes() throws Exception {
            executor.shutdown();
            return future.get();
        }
    }

    private static class FilterOutputStreamTester extends WrapperTester {

        public OutputStream create(OutputStream delegate) throws Exception {
            return new FilterOutputStream(delegate);
        }

        public byte[] decode(byte[] delegateBytes) throws Exception {
            return delegateBytes;
        }
    }

    private static class BufferedOutputStreamTester extends WrapperTester {
        private final int bufferSize;

        private BufferedOutputStreamTester(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        public OutputStream create(OutputStream delegate) throws Exception {
            return new BufferedOutputStream(delegate, bufferSize);
        }

        public byte[] decode(byte[] delegateBytes) throws Exception {
            return delegateBytes;
        }
    }

    private static class DataOutputStreamTester extends WrapperTester {

        public OutputStream create(OutputStream delegate) throws Exception {
            return new DataOutputStream(delegate);
        }

        public byte[] decode(byte[] delegateBytes) throws Exception {
            return delegateBytes;
        }
    }

    private static class ObjectOutputStreamTester extends WrapperTester {

        public OutputStream create(OutputStream delegate) throws Exception {
            return new ObjectOutputStream(delegate);
        }

        public byte[] decode(byte[] delegateBytes) throws Exception {
            return Streams.streamToBytes(new ObjectInputStream(
                    new ByteArrayInputStream(delegateBytes)));
        }
    }

    private static class PrintStreamTester extends WrapperTester {

        public OutputStream create(OutputStream delegate) throws Exception {
            return new PrintStream(delegate);
        }

        public byte[] decode(byte[] delegateBytes) throws Exception {
            return delegateBytes;
        }
    }
}
