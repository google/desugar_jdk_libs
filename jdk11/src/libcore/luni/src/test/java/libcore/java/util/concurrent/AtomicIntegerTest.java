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
 * limitations under the License
 */

package libcore.java.util.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AtomicIntegerTest {

    @Test
    public void testCompareAndExchange() {
        AtomicInteger val = new AtomicInteger(42);
        assertEquals(42, val.compareAndExchange(0, 1));
        assertEquals(42, val.get());
        assertEquals(42, val.compareAndExchange(42, 1));
        assertEquals(1, val.get());
        assertEquals(1, val.compareAndExchange(1, -1));
        assertEquals(-1, val.get());
        assertEquals(-1, val.compareAndExchange(42, 0));
        assertEquals(-1, val.get());
    }

    @Test
    public void testCompareAndExchangeAcquire() {
        AtomicInteger val = new AtomicInteger(42);
        assertEquals(42, val.compareAndExchangeAcquire(0, 1));
        assertEquals(42, val.get());
        assertEquals(42, val.compareAndExchangeAcquire(42, 1));
        assertEquals(1, val.get());
        assertEquals(1, val.compareAndExchangeAcquire(1, -1));
        assertEquals(-1, val.get());
        assertEquals(-1, val.compareAndExchangeAcquire(42, 0));
        assertEquals(-1, val.get());
    }

    @Test
    public void testCompareAndExchangeRelease() {
        AtomicInteger val = new AtomicInteger(42);
        assertEquals(42, val.compareAndExchangeRelease(0, 1));
        assertEquals(42, val.get());
        assertEquals(42, val.compareAndExchangeRelease(42, 1));
        assertEquals(1, val.get());
        assertEquals(1, val.compareAndExchangeRelease(1, -1));
        assertEquals(-1, val.get());
        assertEquals(-1, val.compareAndExchangeRelease(42, 0));
        assertEquals(-1, val.get());
    }

    @Test
    public void testGetAcquire() {
        AtomicInteger val = new AtomicInteger(42);
        assertEquals(42, val.getAcquire());
        val.set(0);
        assertEquals(0, val.getAcquire());
        val.set(5);
        assertEquals(5, val.getAcquire());
    }

    @Test
    public void testGetOpaque() {
        AtomicInteger val = new AtomicInteger(42);
        assertEquals(42, val.getOpaque());
        val.set(0);
        assertEquals(0, val.getOpaque());
        val.set(5);
        assertEquals(5, val.getOpaque());
    }

    @Test
    public void testGetPlain() {
        AtomicInteger val = new AtomicInteger(42);
        assertEquals(42, val.getPlain());
        val.set(0);
        assertEquals(0, val.getPlain());
        val.set(5);
        assertEquals(5, val.getPlain());
    }

    @Test
    public void testSetOpaque() {
        AtomicInteger val = new AtomicInteger(42);
        val.setOpaque(0);
        assertEquals(0, val.get());
        val.setOpaque(5);
        assertEquals(5, val.get());
        val.setOpaque(-1);
        assertEquals(-1, val.get());
    }

    @Test
    public void testSetPlain() {
        AtomicInteger val = new AtomicInteger(42);
        val.setPlain(0);
        assertEquals(0, val.get());
        val.setPlain(5);
        assertEquals(5, val.get());
        val.setPlain(-1);
        assertEquals(-1, val.get());
    }

    @Test
    public void testSetRelease() {
        AtomicInteger val = new AtomicInteger(42);
        val.setRelease(0);
        assertEquals(0, val.get());
        val.setRelease(5);
        assertEquals(5, val.get());
        val.setRelease(-1);
        assertEquals(-1, val.get());
    }

    @Test
    public void testWeakCompareAndSetAcquire() {
        AtomicInteger val = new AtomicInteger(42);
        for (int i = 0; i < 10; ++i) {
            assertFalse(val.weakCompareAndSetAcquire(0, -1));
            assertEquals(42, val.get());
        }
        do { } while (!val.weakCompareAndSetAcquire(42, 0));
        assertEquals(0, val.get());
        do { } while (!val.weakCompareAndSetAcquire(0, 5));
        assertEquals(5, val.get());
        do { } while (!val.weakCompareAndSetAcquire(5, -1));
        assertEquals(-1, val.get());
    }

    @Test
    public void testWeakCompareAndSetPlain() {
        AtomicInteger val = new AtomicInteger(42);
        for (int i = 0; i < 10; ++i) {
            assertFalse(val.weakCompareAndSetPlain(0, -1));
            assertEquals(42, val.get());
        }
        do { } while (!val.weakCompareAndSetPlain(42, 0));
        assertEquals(0, val.get());
        do { } while (!val.weakCompareAndSetPlain(0, 5));
        assertEquals(5, val.get());
        do { } while (!val.weakCompareAndSetPlain(5, -1));
        assertEquals(-1, val.get());
    }

    @Test
    public void testWeakCompareAndSetRelease() {
        AtomicInteger val = new AtomicInteger(42);
        for (int i = 0; i < 10; ++i) {
            assertFalse(val.weakCompareAndSetRelease(0, -1));
            assertEquals(42, val.get());
        }
        do { } while (!val.weakCompareAndSetRelease(42, 0));
        assertEquals(0, val.get());
        do { } while (!val.weakCompareAndSetRelease(0, 5));
        assertEquals(5, val.get());
        do { } while (!val.weakCompareAndSetRelease(5, -1));
        assertEquals(-1, val.get());
    }

    @Test
    public void testWeakCompareAndSetVolatile() {
        AtomicInteger val = new AtomicInteger(42);
        for (int i = 0; i < 10; ++i) {
            assertFalse(val.weakCompareAndSetVolatile(0, -1));
            assertEquals(42, val.get());
        }
        do { } while (!val.weakCompareAndSetVolatile(42, 0));
        assertEquals(0, val.get());
        do { } while (!val.weakCompareAndSetVolatile(0, 5));
        assertEquals(5, val.get());
        do { } while (!val.weakCompareAndSetVolatile(5, -1));
        assertEquals(-1, val.get());
    }

}
