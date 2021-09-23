/* 
 * Licensed to the Apache Software Foundation (ASF) under one or more
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
package org.apache.harmony.tests.java.text;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.ParseException;

@SuppressWarnings("nls")
public class ParseExceptionTest extends junit.framework.TestCase {

    /**
     * @tests java.text.ParseException#ParseException(java.lang.String, int)
     */
    public void test_ConstructorLjava_lang_StringI() {
        try {
            DateFormat df = DateFormat.getInstance();
            df.parse("HelloWorld");
            fail("ParseException not created/thrown.");
        } catch (ParseException e) {
            // expected
        }
    }

    /**
     * @tests java.text.ParseException#getErrorOffset()
     */
    public void test_getErrorOffset() {
        try {
            DateFormat df = DateFormat.getInstance();
            df.parse("1999HelloWorld");
        } catch (ParseException e) {
            assertEquals("getErrorOffsetFailed.", 4, e.getErrorOffset());
        }
    }

    public void test_serialize() throws Exception {
        try (InputStream inputStream = getClass().getResourceAsStream(
                "/serialization/org/apache/harmony/tests/java/text/ParseException.ser");
             ObjectInputStream ois = new ObjectInputStream(inputStream)) {

            Object object = ois.readObject();
            assertTrue("Not a ParseException", object instanceof ParseException);
            ParseException parseException = (ParseException) object;
            assertEquals("fred", parseException.getMessage());
            assertEquals(4, parseException.getErrorOffset());
        }
    }
}
