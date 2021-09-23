/*
 * Copyright (C) 2017 The Android Open Source Project
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

package libcore.build;

import java.lang.Enum;
import java.lang.NoSuchMethodException;
import java.lang.reflect.Method;

import libcore.io.IoTracker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public final class DuplicateBridgeMethodsTest {

    /**
     * Checks that javac+turbine is not causing duplicate bridge methods to be inserted into
     * subclasses (b/65645120).  Turbine doesn't generate bridge methods.  When javac
     * compiles a subclass against a superclass that is missing bridge methods it will by
     * default insert the missing bridge methods into the subclass.  Android's bundled javac
     * prebuilts support a -XDskipDuplicateBridges=true flag to not insert the missing bridge
     * methods.  If the prebuilt javac is not being used then TURBINE_ENABLED=false should be
     * passed on the command line to make for the platform build.
     * This test ensures that an extra bridge method did not get inserted into the
     * libcore.io.IoTracker.Mode enum.
     */
    @Test
    public void testSubclassHasNoBridgeMethod() throws NoSuchMethodException {
        Method method = IoTracker.Mode.class.getMethod("compareTo",
                                                       new Class[]{java.lang.Object.class});
        assertTrue(method.isBridge());
        assertTrue(method.isSynthetic());
        assertSame("Extra bridge methods found, use a javac that supports " +
                   "-XDskipDuplicateBridges=true or set TURBINE_ENABLED=false " +
                   "when building the platform",
                   method.getDeclaringClass(), java.lang.Enum.class);
    }
}
