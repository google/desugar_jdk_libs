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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LinkageErrorTest {

    private final String errorMessage = "error message";

    @Test
    public void constructorLString_LThrowable() {
        Exception cause = new Exception();
        LinkageError error = new LinkageError(errorMessage, cause);

        assertEquals(cause, error.getCause());
        assertEquals(errorMessage, error.getMessage());
    }

    @Test
    public void constructorLString_LThrowable_nullMessage() {
        Exception cause = new Exception();
        LinkageError error = new LinkageError(null, cause);

        assertNull(error.getMessage());
        assertEquals(cause, error.getCause());
    }

    @Test
    public void constructorLString_LThrowable_nullCause() {
        LinkageError error = new LinkageError(errorMessage, null /* cause */);

        assertEquals(errorMessage, error.getMessage());
        assertNull(error.getCause());
    }

    @Test
    public void constructorLString_LThrowable_bothArgumentsNull() {
        LinkageError error = new LinkageError(null, null);

        assertNull(error.getCause());
        assertNull(error.getMessage());
    }

}
