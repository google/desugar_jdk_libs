/*
 * Copyright (C) 2020 The Android Open Source Project
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

package libcore.javax.crypto;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import libcore.java.security.CpuFeatures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class HardwareAesTest {

    @Test
    public void hardwareAesAvailability() {
        // Test is only applicable if we know for sure that the device should support
        // hardware AES.  That covers the important cases (non-emulated ARM and x86_64),
        // For everything else we assume BoringSSL does the right thing.
        assumeTrue(CpuFeatures.isKnownToSupportHardwareAes());
        assertTrue(CpuFeatures.isAesHardwareAccelerated());
    }
}
