/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.harmony.tests.java.lang;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RuntimeTest extends junit.framework.TestCase {

    Runtime r = Runtime.getRuntime();

    InputStream is;

    String s;

    static boolean flag = false;

    static boolean ranFinalize = false;

    class HasFinalizer {
        String internalString;

        HasFinalizer(String s) {
            internalString = s;
        }

        @Override
        protected void finalize() {
            internalString = "hit";
        }
    }

    @Override
    protected void finalize() {
        if (flag)
            ranFinalize = true;
    }

    protected RuntimeTest createInstance() {
        return new RuntimeTest("FT");
    }

    /**
     * java.lang.Runtime#exit(int)
     */
    public void test_exitI() {
        // Test for method void java.lang.Runtime.exit(int)
        assertTrue("Can't really test this", true);
    }

    /**
     * java.lang.Runtime#exec(java.lang.String)
     */
    public void test_exec() {
        boolean success = false;

        /* successful exec's are tested by java.lang.Process */
        try {
            Runtime.getRuntime().exec("AnInexistentProgram");
        } catch (IOException e) {
            success = true;
        }
        assertTrue(
                "failed to throw IOException when exec'ed inexistent program",
                success);
    }

    /**
     * java.lang.Runtime#getRuntime()
     */
    public void test_getRuntime() {
        // Test for method java.lang.Runtime java.lang.Runtime.getRuntime()
        assertTrue("Used to test", true);
    }

    /**
     * java.lang.Runtime#runFinalization()
     */
    public void test_runFinalization() {
        // Test for method void java.lang.Runtime.runFinalization()

        flag = true;
        createInstance();
        int count = 10;
        // the gc below likely bogosifies the test, but will have to do for
        // the moment
        while (!ranFinalize && count-- > 0) {
            r.gc();
            r.runFinalization();
        }
        assertTrue("Failed to run finalization", ranFinalize);
    }

    /**
     * java.lang.Runtime#freeMemory() / java.lang.Runtime#totalMemory() /
     * java.lang.Runtime#maxMemory()
     */
    public void test_memory() {
        assertTrue("freeMemory < 0", r.freeMemory() >= 0);
        assertTrue("totalMemory() < freeMemory()", r.totalMemory() >= r.freeMemory());
        assertTrue("maxMemory() < totalMemory()", r.maxMemory() >= r.totalMemory());
    }

    public RuntimeTest() {
    }

    public RuntimeTest(String name) {
        super(name);
    }
}
