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

import java.io.InvalidClassException;

import junit.framework.TestCase;

public class InvalidClassExceptionTest extends TestCase {

    /**
     * java.io.InvalidClassException#InvalidClassException(java.lang.String)
     */
    public void test_ConstructorLjava_lang_String() {
        final String message = "A message";
        try {
            if (true) {
                throw new InvalidClassException(message);
            }
            fail("Failed to throw exception");
        } catch (InvalidClassException e) {
            // correct
            assertTrue("Incorrect message read", e.getMessage().equals(message));
        }
    }

    /**
     * java.io.InvalidClassException#InvalidClassException(java.lang.String,
     *java.lang.String)
     */
    public void test_ConstructorLjava_lang_StringLjava_lang_String() {
        final String message = "A message";
        final String className = "Object";
        try {
            if (true) {
                throw new InvalidClassException(className, message);
            }
            fail("Failed to throw exception");
        } catch (InvalidClassException e) {
            // correct
            String returnedMessage = e.getMessage();
            assertTrue("Incorrect message read: " + e.getMessage(),
                    returnedMessage.indexOf(className) >= 0
                            && returnedMessage.indexOf(message) >= 0);
        }
    }

    /**
     * java.io.InvalidClassException#getMessage()
     */
    public void test_getMessage() {
        // Test for method java.lang.String
        // java.io.InvalidClassException.getMessage()
        // used to test
    }
}
