/*
 * Copyright (C) 2016 The Android Open Source Project
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

package libcore.libcore.util;

import junit.framework.TestCase;

import libcore.util.NativeAllocationRegistry;

public class NativeAllocationRegistryTest extends TestCase {

    static {
        System.loadLibrary("javacoretests");
    }

    private ClassLoader classLoader = NativeAllocationRegistryTest.class.getClassLoader();

    private static class TestConfig {
        public boolean treatAsMalloced;
        public boolean shareRegistry;

        public TestConfig(boolean treatAsMalloced, boolean shareRegistry) {
            this.shareRegistry = shareRegistry;
        }
    }

    private static class Allocation {
        public byte[] javaAllocation;
        public long nativeAllocation;
    }

    // Verify that NativeAllocations and their referents are freed before we run
    // out of space for new allocations.
    private void testNativeAllocation(TestConfig config) {
        if (isNativeBridgedABI()) {
            // 1. This test is intended to test platform internals, not public API.
            // 2. The test would fail under native bridge as a side effect of how the tests work:
            //  - The tests run using the app architecture instead of the platform architecture
            //  - That scenario will never happen in practice due to (1)
            // 3. This leaves a hole in testing for the case of native bridge, due to limitations
            //    in the testing infrastructure from (2).
            System.logI("Skipping test for native bridged ABI");
            return;
        }
        Runtime.getRuntime().gc();
        System.runFinalization();
        long nativeBytes = getNumNativeBytesAllocated();
        assertEquals("Native bytes already allocated", 0, nativeBytes);
        long max = Runtime.getRuntime().maxMemory();
        long total = Runtime.getRuntime().totalMemory();
        int size = 1024 * 1024;
        final int nativeSize = size / 2;
        int javaSize = size / 2;
        int expectedMaxNumAllocations = (int)(max-total) / javaSize;
        int numSavedAllocations = expectedMaxNumAllocations / 2;
        Allocation[] saved = new Allocation[numSavedAllocations];

        NativeAllocationRegistry registry = null;
        int numAllocationsToSimulate = 10 * expectedMaxNumAllocations;

        // Allocate more native allocations than will fit in memory. This should
        // not throw OutOfMemoryError because the few allocations we save
        // references to should easily fit.
        for (int i = 0; i < numAllocationsToSimulate; i++) {
            if (!config.shareRegistry || registry == null) {
                if (config.treatAsMalloced) {
                    registry = NativeAllocationRegistry.createMalloced(
                            classLoader, getNativeFinalizer(), nativeSize);
                } else {
                    registry = NativeAllocationRegistry.createNonmalloced(
                            classLoader, getNativeFinalizer(), nativeSize);
                }
            }

            final Allocation alloc = new Allocation();
            alloc.javaAllocation = new byte[javaSize];
            alloc.nativeAllocation = doNativeAllocation(nativeSize);
            registry.registerNativeAllocation(alloc, alloc.nativeAllocation);

            saved[i % numSavedAllocations] = alloc;
        }

        // Verify most of the allocations have been freed.  Since we use fairly large Java
        // objects, this doesn't test the GC triggering effect; we do that elsewhere.
        //
        // Since native and java objects have the same size, and we can only have max Java bytes
        // in use, there should ideally be no more than max native bytes in use, once all enqueued
        // deallocations have been processed. We call runFinalization() to make sure that the
        // ReferenceQueueDaemon has processed all pending requests, and then check.
        // (runFinalization() isn't documented to guarantee this, but it waits for a sentinel
        // object to make it all the way through the pending reference queue, and hence has that
        // effect.)
        //
        // However the garbage collector enqueues references asynchronously, by enqueuing
        // another heap task. If the GC runs before we finish our allocation, but reference
        // enqueueing is delayed, and runFinalization() runs between the time the GC reclaims
        // memory and the references are enqueued, then runFinalization() may complete
        // immediately, and further allocation may have occurred between the GC and the invocation
        // of runFinalization(). Thus, under unlikely conditions, we may see up to twice as much
        // native memory as the Java heap, and that's the actual condition we test.
        System.runFinalization();
        nativeBytes = getNumNativeBytesAllocated();
        assertTrue("Excessive native bytes still allocated (" + nativeBytes + ")"
                + " given max memory of (" + max + ")", nativeBytes <= 2 * max);
        // Check that the array is fully populated, and sufficiently many native bytes
        // are live.
        long nativeReachableBytes = numSavedAllocations * nativeSize;
        for (int i = 0; i < numSavedAllocations; i++) {
            assertNotNull(saved[i]);
            assertNotNull(saved[i].javaAllocation);
            assertTrue(saved[i].nativeAllocation != 0);
        }
        assertTrue("Too few native bytes still allocated (" + nativeBytes + "); "
                + nativeReachableBytes + " bytes are reachable",
                nativeBytes >= nativeReachableBytes);
    }

    public void testNativeAllocationNonmallocNoSharedRegistry() {
        testNativeAllocation(new TestConfig(false, false));
    }

    public void testNativeAllocationNonmallocSharedRegistry() {
        testNativeAllocation(new TestConfig(false, true));
    }

    public void testNativeAllocationMallocNoSharedRegistry() {
        testNativeAllocation(new TestConfig(true, false));
    }

    public void testNativeAllocationMallocSharedRegistry() {
        testNativeAllocation(new TestConfig(true, true));
    }

    public void testBadSize() {
        assertThrowsIllegalArgumentException(new Runnable() {
            public void run() {
                NativeAllocationRegistry registry = new NativeAllocationRegistry(
                        classLoader, getNativeFinalizer(), -8);
            }
        });
    }

    public void testEarlyFree() {
        if (isNativeBridgedABI()) {
            // See the explanation in testNativeAllocation.
            System.logI("Skipping test for native bridged ABI");
            return;
        }
        long size = 1234;
        NativeAllocationRegistry registry
            = new NativeAllocationRegistry(classLoader, getNativeFinalizer(), size);
        long nativePtr = doNativeAllocation(size);
        Object referent = new Object();
        Runnable cleaner = registry.registerNativeAllocation(referent, nativePtr);
        long numBytesAllocatedBeforeClean = getNumNativeBytesAllocated();

        // Running the cleaner should cause the native finalizer to run.
        cleaner.run();
        long numBytesAllocatedAfterClean = getNumNativeBytesAllocated();
        assertEquals(numBytesAllocatedBeforeClean - size, numBytesAllocatedAfterClean);

        // Running the cleaner again should have no effect.
        cleaner.run();
        assertEquals(numBytesAllocatedAfterClean, getNumNativeBytesAllocated());

        // There shouldn't be any problems when the referent object is GC'd.
        referent = null;
        Runtime.getRuntime().gc();
    }

    public void testApplyFreeFunction() {
        if (isNativeBridgedABI()) {
            // See the explanation in testNativeAllocation.
            System.logI("Skipping test for native bridged ABI");
            return;
        }
        long size = 1234;
        long nativePtr = doNativeAllocation(size);
        long numBytesAllocatedBeforeFree = getNumNativeBytesAllocated();

        // Applying the free function should cause the native finalizer to run.
        NativeAllocationRegistry.applyFreeFunction(getNativeFinalizer(), nativePtr);
        long numBytesAllocatedAfterFree = getNumNativeBytesAllocated();
        assertEquals(numBytesAllocatedBeforeFree - size, numBytesAllocatedAfterFree);
    }

    public void testNullArguments() {
        final NativeAllocationRegistry registry
            = new NativeAllocationRegistry(classLoader, getNativeFinalizer(), 1024);
        final long fakeNativePtr = 0x1;
        final Object referent = new Object();

        // referent should not be null
        assertThrowsIllegalArgumentException(new Runnable() {
            public void run() {
                registry.registerNativeAllocation(null, fakeNativePtr);
            }
        });

        // nativePtr should not be null
        assertThrowsIllegalArgumentException(new Runnable() {
            public void run() {
                registry.registerNativeAllocation(referent, 0);
            }
        });
    }

    private static void assertThrowsIllegalArgumentException(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException ex) {
            return;
        }
        fail("Expected IllegalArgumentException, but no exception was thrown.");
    }

    private static native boolean isNativeBridgedABI();
    private static native long getNativeFinalizer();
    private static native long doNativeAllocation(long size);
    private static native long getNumNativeBytesAllocated();
}
