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

package tests.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;

/**
 * Utility methods for working with byte and character streams.
 */
public class Streams {
    private Streams() {
    }

    /**
     * Drains the stream into a byte array and returns the result.
     */
    public static byte[] streamToBytes(InputStream source) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int count;
        while ((count = source.read(buffer)) != -1) {
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }

    /**
     * Drains the stream into a string and returns the result.
     */
    public static String streamToString(Reader fileReader) throws IOException {
        char[] buffer = new char[1024];
        StringWriter out = new StringWriter();
        int count;
        while ((count = fileReader.read(buffer)) != -1) {
            out.write(buffer, 0, count);
        }
        return out.toString();
    }
}
