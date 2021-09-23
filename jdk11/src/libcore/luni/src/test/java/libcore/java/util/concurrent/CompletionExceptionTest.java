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
 * limitations under the License
 */

package libcore.java.util.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.concurrent.CompletionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

public class CompletionExceptionTest {

    // Adding derived class to be able to test the protected constructors
    private class TestCompletionException extends CompletionException {
        public TestCompletionException() {
            super();
        }
        public TestCompletionException(String message) {
            super(message);
        }
    }

    /**
     * constructor creates exception without any details
     */
    @Test
    public void testConstructNoMessage() {
        CompletionException exception = new TestCompletionException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    /**
     * constructor creates exception with detail message
     */
    @Test
    public void testConstructWithMessage() {
        CompletionException exception = new TestCompletionException("test");
        assertEquals("test", exception.getMessage());
        assertNull(exception.getCause());
    }

    /**
     * constructor creates exception with detail message and cause
     */
    @Test
    public void testConstructWithMessageAndCause() {
        Throwable cause = new Exception();
        CompletionException exception = new CompletionException("test", cause);
        assertEquals("test", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
