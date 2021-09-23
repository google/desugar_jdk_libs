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

import junit.framework.TestCase;

public class IOExceptionTest extends TestCase {

    /**
     * java.io.IOException#IOException()
     */
    public void test_Constructor() {
        try {
            if (true) {
                throw new IOException();
            }
            fail("Exception during IOException test");
        } catch (IOException e) {
            // Expected
        }
    }

    /**
     * java.io.IOException#IOException(java.lang.String)
     */
    public void test_ConstructorLjava_lang_String() {
        try {
            if (true) {
                throw new IOException("Some error message");
            }
            fail("Failed to generate exception");
        } catch (IOException e) {
            // Expected
        }
    }

    /**
     * java.io.IOException#IOException(java.lang.String,
     *java.lang.Throwable)
     * @since 1.6
     */
    public void test_ConstructorLString_LThrowable() {
        // Test for constructor java.io.IOException(java.lang.String, java.lang.Throwable)

        IOException ioException = new IOException(
                "A fake IOException", new Throwable("A fake Throwable")); //$NON-NLS-1$//$NON-NLS-2$
        assertEquals("A fake IOException", ioException.getMessage()); //$NON-NLS-1$

        try {
            throw new IOException(
                    "A fake error", new Throwable("Some error message")); //$NON-NLS-1$ //$NON-NLS-2$
        } catch (IOException e) {
            return;
        } catch (Exception e) {
            fail("Exception during IOException test" + e.toString()); //$NON-NLS-1$
        }
        fail("Failed to generate exception"); //$NON-NLS-1$
    }

    /**
     * java.io.IOException#IOException(java.lang.Throwable)
     * @since 1.6
     */
    public void test_Constructor_LThrowable() {
        // Test for constructor java.io.IOException(java.lang.Throwable)
        Throwable cause = new Throwable("A fake Throwable"); //$NON-NLS-1$
        IOException ioException = new IOException(cause);
        assertEquals(cause.toString(), ioException.getMessage());

        ioException = new IOException((Throwable) null);
        assertNull(ioException.getMessage());

        try {
            throw new IOException(new Throwable("Some error message")); //$NON-NLS-1$
        } catch (IOException e) {
            return;
        } catch (Exception e) {
            fail("Exception during IOException test" + e.toString()); //$NON-NLS-1$
        }
        fail("Failed to generate exception"); //$NON-NLS-1$
    }

    /**
     * Sets up the fixture, for example, open a network connection. This method
     * is called before a test is executed.
     */
    protected void setUp() {
    }

    /**
     * Tears down the fixture, for example, close a network connection. This
     * method is called after a test is executed.
     */
    protected void tearDown() {
    }
}
