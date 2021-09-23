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

package org.apache.harmony.tests.java.util;

import java.util.ServiceConfigurationError;

import junit.framework.TestCase;

import org.apache.harmony.testframework.serialization.SerializationTest;

/**
 * Tests for java.util.ServiceConfigurationError
 *
 * @since 1.6
 */
public class ServiceConfigurationErrorTest extends TestCase {

    /**
     * {@link java.util.ServiceConfigurationError#ServiceConfigurationError(String)}
     */
    @SuppressWarnings("nls")
    public void test_ConstructorLjava_lang_String() {
        ServiceConfigurationError e = new ServiceConfigurationError("fixture");
        assertEquals("fixture", e.getMessage());
        assertNull(e.getCause());
    }

    /**
     * {@link java.util.ServiceConfigurationError#ServiceConfigurationError(String, Throwable)}
     */
    @SuppressWarnings("nls")
    public void test_ConstructorLjava_lang_StringLjava_lang_Throwable() {
        IllegalArgumentException iae = new IllegalArgumentException(
                "info in the IAE");
        ServiceConfigurationError e = new ServiceConfigurationError("fixture",
                iae);
        assertEquals("fixture", e.getMessage());
        assertEquals(iae, e.getCause());
        assertEquals("info in the IAE", e.getCause().getMessage());
    }

    /**
     * @throws Exception
     * serialization/deserialization.
     */
    @SuppressWarnings("nls")
    public void testSerializationSelf() throws Exception {
        SerializationTest.verifySelf(new ServiceConfigurationError("fixture"));
        SerializationTest.verifySelf(new ServiceConfigurationError("fixture",
                new IllegalArgumentException("info in the IAE")));
    }

    /**
     * @throws Exception
     * serialization/deserialization compatibility with RI.
     */
    @SuppressWarnings("nls")
    public void testSerializationCompatibility() throws Exception {
        ServiceConfigurationError e = new ServiceConfigurationError("fixture",
                new IllegalArgumentException("info in the IAE"));
        SerializationTest.verifyGolden(this, e);
    }
}
