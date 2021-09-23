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
import org.apache.harmony.testframework.CharSinkTester;
import org.apache.harmony.testframework.CharWrapperTester;
import tests.support.Streams;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Tests basic {@link Writer} behaviors for the luni implementations of the type.
 */
public class WriterTesterTest {

    // TODO: Rewrite this test so that id doesn't need a suite().
    private static junit.framework.Test suite() {
        TestSuite suite = new TestSuite();

        // sink tests
        suite.addTest(new FileWriterCharSinkTester(true).createTests());
        suite.addTest(new FileWriterCharSinkTester(false).createTests());
        suite.addTest(new CharArrayWriterCharSinkTester().setThrowsExceptions(false).createTests());
        suite.addTest(new StringWriterCharSinkTester().setThrowsExceptions(false).createTests());
        suite.addTest(new PipedWriterCharSinkTester().createTests());

        // wrapper tests
        suite.addTest(new BufferedWriterCharSinkTester(1).createTests());
        suite.addTest(new BufferedWriterCharSinkTester(5).createTests());
        suite.addTest(new BufferedWriterCharSinkTester(1024).createTests());
        suite.addTest(new FilterWriterCharSinkTester().createTests());
        suite.addTest(new PrintWriterCharSinkTester().setThrowsExceptions(false).createTests());
        suite.addTest(new OutputStreamWriterCharSinkTester().createTests());

        return suite;
    }

    private static class FileWriterCharSinkTester extends CharSinkTester {
        private final boolean append;
        private File file;

        public FileWriterCharSinkTester(boolean append) {
            this.append = append;
        }

        @Override
        public Writer create() throws Exception {
            file = File.createTempFile("FileOutputStreamSinkTester", "tmp");
            file.deleteOnExit();
            return new FileWriter(file, append);
        }

        @Override
        public char[] getChars() throws Exception {
            return Streams.streamToString(new FileReader(file)).toCharArray();
        }
    }

    private static class CharArrayWriterCharSinkTester extends CharSinkTester {
        private CharArrayWriter writer;

        @Override
        public Writer create() throws Exception {
            writer = new CharArrayWriter();
            return writer;
        }

        @Override
        public char[] getChars() throws Exception {
            return writer.toCharArray();
        }
    }

    private static class PipedWriterCharSinkTester extends CharSinkTester {

        private ExecutorService executor;
        private Future<char[]> future;

        public Writer create() throws IOException {
            final PipedReader in = new PipedReader();
            PipedWriter out = new PipedWriter(in);

            executor = Executors.newSingleThreadExecutor();
            future = executor.submit(new Callable<char[]>() {
                final CharArrayWriter chars = new CharArrayWriter();

                public char[] call() throws Exception {
                    char[] buffer = new char[256];
                    int count;
                    while ((count = in.read(buffer)) != -1) {
                        chars.write(buffer, 0, count);
                    }
                    return chars.toCharArray();
                }
            });

            return out;
        }

        @Override
        public char[] getChars() throws Exception {
            executor.shutdown();
            return future.get();
        }
    }

    private static class StringWriterCharSinkTester extends CharSinkTester {
        private StringWriter writer;

        @Override
        public Writer create() throws Exception {
            writer = new StringWriter();
            return writer;
        }

        @Override
        public char[] getChars() throws Exception {
            return writer.toString().toCharArray();
        }
    }

    private static class BufferedWriterCharSinkTester extends CharWrapperTester {
        private final int bufferSize;

        private BufferedWriterCharSinkTester(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        @Override
        public Writer create(Writer delegate) throws Exception {
            return new BufferedWriter(delegate, bufferSize);
        }

        @Override
        public char[] decode(char[] delegateChars) throws Exception {
            return delegateChars;
        }
    }

    private static class FilterWriterCharSinkTester extends CharWrapperTester {
        @Override
        public Writer create(Writer delegate) throws Exception {
            return new FilterWriter(delegate) {
            };
        }

        @Override
        public char[] decode(char[] delegateChars) throws Exception {
            return delegateChars;
        }
    }

    private static class PrintWriterCharSinkTester extends CharWrapperTester {
        @Override
        public Writer create(Writer delegate) throws Exception {
            return new PrintWriter(delegate) {
            };
        }

        @Override
        public char[] decode(char[] delegateChars) throws Exception {
            return delegateChars;
        }
    }

    private static class OutputStreamWriterCharSinkTester extends CharSinkTester {
        private ByteArrayOutputStream out;

        @Override
        public Writer create() throws Exception {
            out = new ByteArrayOutputStream();
            return new OutputStreamWriter(out, "UTF-8");
        }

        @Override
        public char[] getChars() throws Exception {
            return new String(out.toByteArray(), "UTF-8").toCharArray();
        }
    }
}
