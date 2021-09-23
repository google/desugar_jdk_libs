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

package libcore.java.lang;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RuntimeExceptionTest {

    @Test
    public void withNonWritableStackTrace_getStackTraceIsEmpty() {
        RuntimeException exception = new TestRuntimeException("message", null /* cause */,
                false /* enableSuppression */,
                false /* writableStackTrace */);

        assertEquals(0, exception.getStackTrace().length);
    }

    @Test
    public void withWritableStackTrace_nonEmptyGetStackTrace() {
        RuntimeException exception = new TestRuntimeException("message", null /* cause */,
                false /* enableSuppression */,
                true /* writableStackTrace */);

        assertNotEquals(0, exception.getStackTrace().length);

        StackTraceElement topStackTraceElement = exception.getStackTrace()[0];
        assertEquals("withWritableStackTrace_nonEmptyGetStackTrace",
                topStackTraceElement.getMethodName());
    }

    @Test
    public void whenSuppressionDisabled_addSuppressHasNoEffect() {
        RuntimeException exception = new TestRuntimeException("Message", null,
                false /* enableSuppression */, true /* writableStackTrace */);

        assertEquals(0, exception.getSuppressed().length);
        exception.addSuppressed(new Exception("suppressed exception"));
        assertEquals(0, exception.getSuppressed().length);
    }

    @Test
    public void whenSuppressionEnabled_addsSuppressed() {
        RuntimeException error = new TestRuntimeException("message", null /* cause */,
                true /* enableSuppression */, true /* writableStackTrace */);

        assertEquals(0, error.getSuppressed().length);

        Exception suppressed = new Exception("suppressed");
        error.addSuppressed(suppressed);
        assertArrayEquals(new Throwable[] {suppressed}, error.getSuppressed());
    }

    private static final class TestRuntimeException extends RuntimeException {
        TestRuntimeException(String message, Throwable cause, boolean enableSuppression,
                boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
