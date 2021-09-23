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

package org.apache.harmony.tests.java.lang;

import java.io.IOException;
import java.io.InputStream;

import static tests.support.Support_Exec.javaProcessBuilder;

public class Process2Test extends junit.framework.TestCase {
    /**
     * java.lang.Process#getInputStream(),
     *        java.lang.Process#getErrorStream()
     *        java.lang.Process#getOutputStream()
     * Tests if these methods return buffered streams.
     */
    public void test_streams()
            throws IOException, InterruptedException {
        Process p = javaProcessBuilder().start();
        assertNotNull(p.getInputStream());
        assertNotNull(p.getErrorStream());
        assertNotNull(p.getOutputStream());
    }

    public void test_getErrorStream() {
        String[] commands = {"sh", "-c", "echo"};
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(commands, null, null);
            InputStream is = process.getErrorStream();
            StringBuilder msg = new StringBuilder("");
            while (true) {
                int c = is.read();
                if (c == -1)
                    break;
                msg.append((char) c);
            }
            assertEquals("", msg.toString());
        } catch (IOException e) {
            fail("IOException was thrown.");
        } finally {
            if (process != null) {
                process.destroy();
            }
        }

        String[] unknownCommands = {"sh", "-c", "echo oops >&2"};
        Process erProcess = null;
        try {
            erProcess = Runtime.getRuntime().exec(unknownCommands, null, null);
            InputStream is = erProcess.getErrorStream();
            StringBuilder msg = new StringBuilder("");
            while (true) {
                int c = is.read();
                if (c == -1)
                    break;
                msg.append((char) c);
            }
            assertEquals("oops\n", msg.toString());
        } catch (IOException e) {
            fail("IOException was thrown.");
        } finally {
            if (erProcess != null) {
                erProcess.destroy();
            }
        }
    }
}
