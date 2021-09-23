/*
 * Copyright (C) 2018 The Android Open Source Project
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

package libcore.libcore.io;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import libcore.io.ForwardingOs;
import libcore.io.Os;

@RunWith(JUnit4.class)
public class ForwardingOsTest {
    @Test
    public void constructor_nullDelegate() {
        try {
            new ForwardingOs(null) {};
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void toStringContainsDelegate() {
        String msg = "toString() for testing";
        Os mockOs = Mockito.mock(Os.class);
        Mockito.when(mockOs.toString()).thenReturn(msg);
        ForwardingOs os = new ForwardingOs(mockOs) {};
        // Wrapping may either keep the wrapped toString(), or expand on it; either is
        // fine, as the wrapped toString() is still contained.
        assertTrue(os.toString(), os.toString().contains(msg));
    }
}
