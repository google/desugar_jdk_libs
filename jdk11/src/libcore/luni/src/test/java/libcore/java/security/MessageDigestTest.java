/*
 * Copyright (C) 2010 The Android Open Source Project
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

package libcore.java.security;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.MessageDigestSpi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;

public final class MessageDigestTest extends TestCase {

    private final int THREAD_COUNT = 10;

    public void testMessageDigest_MultipleThreads_Misuse() throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);

        final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        final MessageDigest md = MessageDigest.getInstance("SHA-256");
        final byte[] message = new byte[64];

        for (int i = 0; i < THREAD_COUNT; i++) {
            es.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    // Try to make sure all the threads are ready first.
                    latch.countDown();
                    latch.await();

                    for (int j = 0; j < 100; j++) {
                        md.update(message);
                        md.digest();
                    }

                    return null;
                }
            });
        }
        es.shutdown();
        assertTrue("Test should not timeout", es.awaitTermination(1, TimeUnit.MINUTES));
    }

    /**
     * When an instance of a MessageDigest is obtained, it's actually wrapped in an implementation
     * which delegates MessageDigestSpi calls through to the underlying SPI implementation. We
     * verify that all these MessageDigestSpi methods are indeed overridden -- if they aren't, they
     * won't be delegated to the SPI implementation.
     */
    public void testMessageDigestDelegateOverridesAllMethods() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        /*
         * Make sure we're dealing with a delegate and not an actual instance of MessageDigest.
         */
        Class<?> mdClass = md.getClass();
        assertFalse(mdClass.equals(MessageDigestSpi.class));
        assertFalse(mdClass.equals(MessageDigest.class));

        List<String> methodsNotOverridden = new ArrayList<String>();

        for (Method spiMethod : MessageDigestSpi.class.getDeclaredMethods()) {
            try {
                mdClass.getDeclaredMethod(spiMethod.getName(), spiMethod.getParameterTypes());
            } catch (NoSuchMethodException e) {
                methodsNotOverridden.add(spiMethod.toString());
            }
        }

        assertEquals(Collections.EMPTY_LIST, methodsNotOverridden);
    }

    public void testIsEqual_nullValues() {
        assertTrue(MessageDigest.isEqual(null, null));
        assertFalse(MessageDigest.isEqual(null, new byte[1]));
        assertFalse(MessageDigest.isEqual(new byte[1], null));
    }
}
