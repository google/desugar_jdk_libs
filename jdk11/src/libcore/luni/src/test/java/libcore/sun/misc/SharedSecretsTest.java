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
package libcore.sun.misc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import sun.misc.SharedSecrets;

@RunWith(JUnit4.class)
public class SharedSecretsTest {

    /**
     * This test doesn't completely rule out race conditions such as http://b/80495283 (between
     * FileDescriptor and UnixChannelFactory): Even if this test passes by the time it runs, the
     * condition that it enforces may not have been true earlier in the runtime start.
     */
    @Test
    public void testGetJavaIOFileDescriptorAccess_notNull() {
        Assert.assertNotNull("SharedSecrets.getJavaIOFileDescriptorAccess can't be null",
                SharedSecrets.getJavaIOFileDescriptorAccess());
    }
}
