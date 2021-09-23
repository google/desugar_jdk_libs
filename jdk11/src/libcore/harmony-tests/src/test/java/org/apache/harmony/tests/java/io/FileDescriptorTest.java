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
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import junit.framework.TestCase;

public class FileDescriptorTest extends TestCase {

    public void test_sync() throws IOException {
        File f = File.createTempFile("FileDescriptorText", "txt");

        FileOutputStream fos = null;
        FileInputStream fis = null;
        RandomAccessFile raf = null;

        try {
            fos = new FileOutputStream(f.getAbsolutePath());
            fos.write("Test String".getBytes(StandardCharsets.US_ASCII));

            fis = new FileInputStream(f.getPath());
            FileDescriptor fd = fos.getFD();
            fd.sync();

            int length = "Test String".length();
            assertEquals(length, fis.available());

            // Regression test for Harmony-1494
            fd = fis.getFD();
            fd.sync();
            assertEquals(length, fis.available());

            raf = new RandomAccessFile(f, "r");
            fd = raf.getFD();
            fd.sync();
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (fis != null) {
                fis.close();
            }
            if (raf != null) {
                raf.close();
            }
        }
    }

    /**
     * java.io.FileDescriptor#valid()
     */
    public void test_valid() throws IOException {
        File f = File.createTempFile("FileDescriptorText", "txt");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f.getAbsolutePath());
            FileDescriptor fd = fos.getFD();
            assertTrue(fd.valid());
            fos.close();
            assertFalse(fd.valid());
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}
