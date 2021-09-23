/* Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.harmony.tests.java.util;

import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import junit.framework.TestCase;

public class ScannerParseLargeFileBenchmarkTest extends TestCase {

    /**
     * Check whether the Scanner will exhaust all heap memory when parsing a
     * large file.
     */
    public void testParseLargeFile() throws Exception {
        FakeLargeFile reader = new FakeLargeFile();
        String delimiter = "\r?\n";
        Scanner scanner = new Scanner(reader).useDelimiter(delimiter);

        while (scanner.hasNext()) {
            scanner.next();
        }
        scanner.close();
        reader.close();
    }

    private static class FakeLargeFile extends Reader {
        private static final char[] CONTENT = "large file!\n".toCharArray();
        private static final int FILE_LENGTH = 192 * 1024 * 1024; // 192 MB

        private int count = 0;

        @Override
        public void close() throws IOException {
        }

        @Override
        public int read(char[] buffer, int offset, int length) {
            if (count >= FILE_LENGTH) {
                return -1;
            }

            final int charsToRead = Math.min(FILE_LENGTH - count, length);
            int bufferIndex = offset;
            int contentIndex = count % CONTENT.length;
            int charsRead = 0;
            while (charsRead < charsToRead) {
                buffer[bufferIndex++] = CONTENT[contentIndex++];
                if (contentIndex == CONTENT.length) {
                    contentIndex = 0;
                }
                charsRead++;
            }
            count += charsRead;
            return charsToRead;
        }
    }
}
