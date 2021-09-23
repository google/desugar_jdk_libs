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
public class VirtualMachineErrorTest {
    @Test
    public void constructorLThrowable() {
        Throwable cause = new Exception("cause");
        VirtualMachineError error = new TestVirtualMachineError(cause);

        assertEquals(cause, error.getCause());
        assertEquals("java.lang.Exception: cause", error.getMessage());
    }

    @Test
    public void constructorLThrowable_withNull() {
        VirtualMachineError error = new TestVirtualMachineError(null /* cause */);

        assertNull(error.getCause());
        assertNull(error.getMessage());
    }

    private static final class TestVirtualMachineError extends VirtualMachineError {
        public TestVirtualMachineError(Throwable cause) {
            super(cause);
        }
    }
}
