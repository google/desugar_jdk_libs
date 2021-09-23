/*
 * Copyright (C) 2014 The Android Open Source Project
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

package libcore.android.system;

import android.system.OsConstants;

import junit.framework.TestCase;

public class OsConstantsTest extends TestCase {

    // http://b/15602893
    public void testBug15602893() {
        assertTrue(OsConstants.RT_SCOPE_HOST > 0);
        assertTrue(OsConstants.RT_SCOPE_LINK > 0);
        assertTrue(OsConstants.RT_SCOPE_SITE > 0);

        assertTrue(OsConstants.IFA_F_TENTATIVE > 0);
    }

    // introduced for http://b/30402085
    public void testTcpUserTimeoutIsDefined() {
        assertTrue(OsConstants.TCP_USER_TIMEOUT > 0);
    }

    /**
     * Verifies equality assertions given in the documentation for
     * {@link OsConstants#SOCK_CLOEXEC} and {@link OsConstants#SOCK_NONBLOCK}.
     */
    public void testConstantsEqual() {
        assertEquals(OsConstants.O_CLOEXEC,  OsConstants.SOCK_CLOEXEC);
        assertEquals(OsConstants.O_NONBLOCK, OsConstants.SOCK_NONBLOCK);
    }
}
