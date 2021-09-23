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

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AtomicBooleanTest {

    @Test
    public void testCompareAndExchange() {
        AtomicBoolean val = new AtomicBoolean(true);
        boolean expected = false;
        boolean newValue = false;
        assertEquals(true, val.compareAndExchange(expected, newValue));
        assertEquals(true, val.get());
        assertEquals(true, val.compareAndExchange(expected, newValue));
        assertEquals(true, val.get());
        expected = true;
        assertEquals(true, val.compareAndExchange(expected, newValue));
        assertEquals(false, val.get());
        assertEquals(false, val.compareAndExchange(expected, newValue));
        assertEquals(false, val.get());
        expected = false;
        newValue = true;
        assertEquals(false, val.compareAndExchange(expected, newValue));
        assertEquals(true, val.get());
    }

    @Test
    public void testCompareAndExchangeAcquire() {
        AtomicBoolean val = new AtomicBoolean(true);
        boolean expected = false;
        boolean newValue = false;
        assertEquals(true, val.compareAndExchangeAcquire(expected, newValue));
        assertEquals(true, val.get());
        assertEquals(true, val.compareAndExchangeAcquire(expected, newValue));
        assertEquals(true, val.get());
        expected = true;
        assertEquals(true, val.compareAndExchangeAcquire(expected, newValue));
        assertEquals(false, val.get());
        assertEquals(false, val.compareAndExchangeAcquire(expected, newValue));
        assertEquals(false, val.get());
        expected = false;
        newValue = true;
        assertEquals(false, val.compareAndExchangeAcquire(expected, newValue));
        assertEquals(true, val.get());
    }

    @Test
    public void testCompareAndExchangeRelease() {
        AtomicBoolean val = new AtomicBoolean(true);
        boolean expected = false;
        boolean newValue = false;
        assertEquals(true, val.compareAndExchangeRelease(expected, newValue));
        assertEquals(true, val.get());
        assertEquals(true, val.compareAndExchangeRelease(expected, newValue));
        assertEquals(true, val.get());
        expected = true;
        assertEquals(true, val.compareAndExchangeRelease(expected, newValue));
        assertEquals(false, val.get());
        assertEquals(false, val.compareAndExchangeRelease(expected, newValue));
        assertEquals(false, val.get());
        expected = false;
        newValue = true;
        assertEquals(false, val.compareAndExchangeRelease(expected, newValue));
        assertEquals(true, val.get());
    }

    @Test
    public void testGetAcquire() {
        AtomicBoolean val = new AtomicBoolean(true);
        assertEquals(true, val.getAcquire());
        val.set(false);
        assertEquals(false, val.getAcquire());
        val.set(true);
        assertEquals(true, val.getAcquire());
    }

    @Test
    public void testGetOpaque() {
        AtomicBoolean val = new AtomicBoolean(true);
        assertEquals(true, val.getOpaque());
        val.set(false);
        assertEquals(false, val.getOpaque());
        val.set(true);
        assertEquals(true, val.getOpaque());
    }

    @Test
    public void testGetPlain() {
        AtomicBoolean val = new AtomicBoolean(true);
        assertEquals(true, val.getPlain());
        val.set(false);
        assertEquals(false, val.getPlain());
        val.set(true);
        assertEquals(true, val.getPlain());
    }

    @Test
    public void testSetOpaque() {
        AtomicBoolean val = new AtomicBoolean(true);
        val.setOpaque(false);
        assertEquals(false, val.get());
        val.setOpaque(true);
        assertEquals(true, val.get());
        val.setOpaque(false);
        assertEquals(false, val.get());
    }

    @Test
    public void testSetPlain() {
        AtomicBoolean val = new AtomicBoolean(true);
        val.setPlain(false);
        assertEquals(false, val.get());
        val.setPlain(true);
        assertEquals(true, val.get());
        val.setPlain(false);
        assertEquals(false, val.get());
    }

    @Test
    public void testSetRelease() {
        AtomicBoolean val = new AtomicBoolean(true);
        val.setRelease(false);
        assertEquals(false, val.get());
        val.setRelease(true);
        assertEquals(true, val.get());
        val.setRelease(false);
        assertEquals(false, val.get());
    }

    @Test
    public void testWeakCompareAndSetAcquire() {
        AtomicBoolean val = new AtomicBoolean(true);
        boolean expected = false;
        boolean newValue = false;
        for (int i = 0; i < 10; ++i) {
            assertFalse(val.weakCompareAndSetAcquire(expected, newValue));
            assertEquals(true, val.get());
        }

        expected = true;
        do { } while (!val.weakCompareAndSetAcquire(expected, newValue));
        assertEquals(false, val.get());

        expected = false;
        newValue = true;
        do { } while (!val.weakCompareAndSetAcquire(expected, newValue));
        assertEquals(true, val.get());

        expected = true;
        newValue = false;
        do { } while (!val.weakCompareAndSetAcquire(expected, newValue));
        assertEquals(false, val.get());
    }

    @Test
    public void testWeakCompareAndSetPlain() {
        AtomicBoolean val = new AtomicBoolean(true);
        boolean expected = false;
        boolean newValue = false;
        for (int i = 0; i < 10; ++i) {
            assertFalse(val.weakCompareAndSetPlain(expected, newValue));
            assertEquals(true, val.get());
        }

        expected = true;
        do { } while (!val.weakCompareAndSetPlain(expected, newValue));
        assertEquals(false, val.get());

        expected = false;
        newValue = true;
        do { } while (!val.weakCompareAndSetPlain(expected, newValue));
        assertEquals(true, val.get());

        expected = true;
        newValue = false;
        do { } while (!val.weakCompareAndSetPlain(expected, newValue));
        assertEquals(false, val.get());
    }

    @Test
    public void testWeakCompareAndSetRelease() {
        AtomicBoolean val = new AtomicBoolean(true);
        boolean expected = false;
        boolean newValue = false;
        for (int i = 0; i < 10; ++i) {
            assertFalse(val.weakCompareAndSetRelease(expected, newValue));
            assertEquals(true, val.get());
        }

        expected = true;
        do { } while (!val.weakCompareAndSetRelease(expected, newValue));
        assertEquals(false, val.get());

        expected = false;
        newValue = true;
        do { } while (!val.weakCompareAndSetRelease(expected, newValue));
        assertEquals(true, val.get());

        expected = true;
        newValue = false;
        do { } while (!val.weakCompareAndSetRelease(expected, newValue));
        assertEquals(false, val.get());
    }

    @Test
    public void testWeakCompareAndSetVolatile() {
        AtomicBoolean val = new AtomicBoolean(true);
        boolean expected = false;
        boolean newValue = false;
        for (int i = 0; i < 10; ++i) {
            assertFalse(val.weakCompareAndSetVolatile(expected, newValue));
            assertEquals(true, val.get());
        }

        expected = true;
        do { } while (!val.weakCompareAndSetVolatile(expected, newValue));
        assertEquals(false, val.get());

        expected = false;
        newValue = true;
        do { } while (!val.weakCompareAndSetVolatile(expected, newValue));
        assertEquals(true, val.get());

        expected = true;
        newValue = false;
        do { } while (!val.weakCompareAndSetVolatile(expected, newValue));
        assertEquals(false, val.get());
    }

}
