/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tests.org.w3c.dom;

import junit.framework.TestCase;

import org.w3c.dom.DOMError;
import org.w3c.dom.ls.LSException;

public class LSExceptionTest extends TestCase {

    public void testConstructor() {
        String msg = "Test message";
        try {
            throw new LSException(DOMError.SEVERITY_FATAL_ERROR, msg);
        } catch (LSException e) {
            assertEquals(DOMError.SEVERITY_FATAL_ERROR, e.code);
            assertEquals(msg, e.getMessage());
        }
    }
}
