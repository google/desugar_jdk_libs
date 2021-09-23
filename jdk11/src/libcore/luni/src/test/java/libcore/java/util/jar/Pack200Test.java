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

package libcore.java.util.jar;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.jar.Pack200;

@RunWith(JUnit4.class)
public class Pack200Test {

    @Test
    public void newPacker_throwsNpe() {
        try {
            // Default implementation is removed and alternative is not available
            Pack200.newPacker();
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }
    }

    @Test
    public void newUnpacker_throwsNpe() {
        try {
            // Default implementation is removed and alternative is not available
            Pack200.newUnpacker();
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }
    }


}
