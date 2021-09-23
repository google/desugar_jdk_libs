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

import java.io.IOError;

import org.apache.harmony.testframework.serialization.SerializationTest;

import junit.framework.TestCase;

public class IOErrorTest extends TestCase {

    /**
     * java.io.IOError#IOError(java.lang.Throwable)
     * @since 1.6
     */
    public void test_IOError_LThrowable() {
        IOError e = new IOError(null);
        assertNull(e.getCause());

        String errorMsg = "java.io.IOError"; //$NON-NLS-1$
        assertTrue(e.toString().contains(errorMsg));

        errorMsg = "A fake error"; //$NON-NLS-1$
        e = new IOError(new Throwable(errorMsg));
        assertTrue(e.toString().contains(errorMsg));

        try {
            throw new IOError(null);
        } catch (IOError error) {
            return;
        } catch (Error error) {
            fail("Error during IOException test" + error.toString()); //$NON-NLS-1$
        }
        fail("Failed to generate error"); //$NON-NLS-1$
    }

    /**
     * serialization/deserialization.
     * @since 1.6
     */
    public void testSerializationSelf() throws Exception {
        String errorMsg = "java.io.IOError";
        IOError e = new IOError(new Throwable(errorMsg));
        SerializationTest.verifySelf(e);
    }

    /**
     * serialization/deserialization compatibility with RI.
     * @since 1.6
     */
    public void testSerializationCompatibility() throws Exception {
        String errorMsg = "java.io.IOError";
        IOError e = new IOError(new Throwable(errorMsg));
        SerializationTest.verifyGolden(this, e);
    }

}
