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
 * limitations under the License
 */

package libcore.java.lang.invoke;

import junit.framework.TestCase;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.VolatileCallSite;
import java.lang.invoke.WrongMethodTypeException;

import static java.lang.invoke.MethodHandles.Lookup.*;

public class CallSitesTest extends TestCase {
    public void test_ConstantCallSite() throws Throwable {
        final MethodType type = MethodType.methodType(int.class, int.class, int.class);
        final MethodHandle mh =
                MethodHandles.lookup().findStatic(CallSitesTest.class, "add2", type);
        final ConstantCallSite site = new ConstantCallSite(mh);
        assertEquals(mh, site.getTarget());
        assertEquals(type, site.type());

        int n = (int) site.dynamicInvoker().invokeExact(7, 37);
        assertEquals(44, n);
        try {
            site.setTarget(mh);
            fail();
        } catch (UnsupportedOperationException e) {
        }
    }

    public void test_EarlyBoundMutableCallSite() throws Throwable {
        final MethodType type = MethodType.methodType(int.class, int.class, int.class);
        final MethodHandle add2 =
                MethodHandles.lookup().findStatic(CallSitesTest.class, "add2", type);
        MutableCallSite site = new MutableCallSite(type);
        commonMutableCallSitesTest(site, add2);
    }

    public void test_EarlyBoundVolatileCallSite() throws Throwable {
        final MethodType type = MethodType.methodType(int.class, int.class, int.class);
        final MethodHandle add2 =
                MethodHandles.lookup().findStatic(CallSitesTest.class, "add2", type);
        VolatileCallSite site = new VolatileCallSite(type);
        commonMutableCallSitesTest(site, add2);
    }

    public void test_LateBoundMutableCallSite() throws Throwable {
        final MethodType type = MethodType.methodType(int.class, int.class, int.class);
        MutableCallSite site = new MutableCallSite(type);
        assertEquals(type, site.type());
        try {
            int fake = (int) site.getTarget().invokeExact(1, 1);
            fail();
        } catch (IllegalStateException e) {
            assertEquals("uninitialized call site", e.getMessage());
        }
        final MethodHandle add2 =
                MethodHandles.lookup().findStatic(CallSitesTest.class, "add2", type);
        site.setTarget(add2);
        commonMutableCallSitesTest(site, add2);
    }

    public void test_LateBoundVolatileCallSite() throws Throwable {
        final MethodType type = MethodType.methodType(int.class, int.class, int.class);
        VolatileCallSite site = new VolatileCallSite(type);
        assertEquals(type, site.type());
        try {
            int fake = (int) site.getTarget().invokeExact(1, 1);
            fail();
        } catch (IllegalStateException e) {
            assertEquals("uninitialized call site", e.getMessage());
        }
        final MethodHandle add2 =
                MethodHandles.lookup().findStatic(CallSitesTest.class, "add2", type);
        site.setTarget(add2);
        commonMutableCallSitesTest(site, add2);
    }

    private static void commonMutableCallSitesTest(CallSite site,
                                                  MethodHandle firstTarget) throws Throwable{
        site.setTarget(firstTarget);
        site.setTarget(firstTarget);

        int x = (int) firstTarget.invokeExact(2, 6);
        assertEquals(8, x);

        int y = (int) site.getTarget().invokeExact(2, 6);
        assertEquals(8, y);

        int z = (int) site.dynamicInvoker().invokeExact(2, 6);
        assertEquals(8, z);

        try {
            site.setTarget(null);
            fail();
        } catch (NullPointerException e) {
        }

        final MethodHandle other = MethodHandles.lookup().findStatic(
            CallSitesTest.class, "add3",
            MethodType.methodType(int.class, int.class, int.class, int.class));
        try {
            site.setTarget(other);
            fail();
        } catch (WrongMethodTypeException e) {
        }
        assertEquals(firstTarget, site.getTarget());

        final MethodHandle sub2 =
                MethodHandles.lookup().findStatic(CallSitesTest.class, "sub2", firstTarget.type());
        site.setTarget(sub2);
        assertEquals(sub2, site.getTarget());
        assertEquals(100, (int) site.dynamicInvoker().invokeExact(147, 47));
    }

    private static int add2(int x, int y) {
        return x + y;
    }

    private static int add3(int x, int y, int z) {
        return x + y + z;
    }

    private static int sub2(int x, int y) {
        return x - y;
    }
}

