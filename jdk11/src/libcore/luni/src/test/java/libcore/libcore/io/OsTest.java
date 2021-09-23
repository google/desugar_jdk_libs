/*
 * Copyright (C) 2011 The Android Open Source Project
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

import junit.framework.TestCase;

import libcore.io.ForwardingOs;
import libcore.io.Os;

import org.mockito.Mockito;

public class OsTest extends TestCase {

    public void testCompareAndSetDefault_success() throws Exception {
        Os defaultOs = Os.getDefault();
        Os mockOs = Mockito.mock(Os.class);
        try {
            // There shouldn't be any concurrent threads replacing the default Os.
            assertTrue(Os.compareAndSetDefault(defaultOs, mockOs));
            assertSame(mockOs, Os.getDefault());

            // Calls to android.system.Os should now reach our custom Os instance.
            android.system.Os.rename("/old/path", "/new/path");
            Mockito.verify(mockOs).rename("/old/path", "/new/path");
        } finally {
            assertTrue(Os.compareAndSetDefault(mockOs, defaultOs));
            assertSame(defaultOs, Os.getDefault());
        }
    }

    public void testCompareandSetDefault_null() {
        Os defaultOs = Os.getDefault();
        // update == null is not allowed
        try {
            Os.compareAndSetDefault(defaultOs, null);
            fail();
        } catch (NullPointerException expected) {
        }
        // value hasn't changed
        assertSame(defaultOs, Os.getDefault());
    }

    public void testCompareAndSetDefault_comparisonFailure() throws Exception {
        Os defaultOs = Os.getDefault();
        Os otherOs = new ForwardingOs(defaultOs) { };

        // current default is non-null, but expect is null
        assertFalse(Os.compareAndSetDefault(null, otherOs));
        assertSame(defaultOs, Os.getDefault());

        // current default != expect (both non-null)
        assertFalse(Os.compareAndSetDefault(otherOs, otherOs));
        assertSame(defaultOs, Os.getDefault());
    }
}
