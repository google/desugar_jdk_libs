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

import java.util.concurrent.Flow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FlowTest {

    @Test
    /**
     * defaultBufferSize returns default value for Publisher or Subscriber buffering.
     */
    public void testDefaultBufferSize() {
        // Currently the implementation always returns 256, as documented in the API. If this
        // changes, the test would need to be adjusted as well. This, at least, can serve as a
        // reminder to update the documentation.
        assertEquals(256, Flow.defaultBufferSize());
    }
}
