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
import static org.junit.Assert.assertSame;

import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AtomicReferenceTest {

    @Test
    public void testCompareAndExchange() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);

        Integer notCurrent = new Integer(0);
        Integer newValue = Integer.valueOf(1);
        Integer result = val.compareAndExchange(notCurrent, newValue);
        assertEquals(42, result.intValue());
        assertSame(currentValue, result);
        assertSame(currentValue, val.get());
        assertEquals(42, currentValue.intValue());

        notCurrent = new Integer(42);
        result = val.compareAndExchange(notCurrent, newValue);
        assertEquals(42, result.intValue());
        assertSame(currentValue, result);
        assertSame(currentValue, val.get());
        assertEquals(42, currentValue.intValue());

        result = val.compareAndExchange(currentValue, newValue);
        assertEquals(42, result.intValue());
        assertSame(currentValue, result);
        assertSame(newValue, val.get());
        currentValue = val.get();
        assertEquals(1, currentValue.intValue());


        notCurrent = new Integer(42);
        newValue = Integer.valueOf(0);
        result = val.compareAndExchange(notCurrent, newValue);
        assertEquals(1, result.intValue());
        assertSame(currentValue, result);
        assertSame(currentValue, val.get());
        assertEquals(1, currentValue.intValue());

        newValue = Integer.valueOf(0);
        result = val.compareAndExchange(currentValue, newValue);
        assertEquals(1, result.intValue());
        assertSame(currentValue, result);
        assertSame(newValue, val.get());
        currentValue = val.get();
        assertEquals(0, currentValue.intValue());
    }

    @Test
    public void testCompareAndExchangeAcquire() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);

        Integer notCurrent = new Integer(0);
        Integer newValue = Integer.valueOf(1);
        Integer result = val.compareAndExchangeAcquire(notCurrent, newValue);
        assertEquals(42, result.intValue());
        assertSame(currentValue, result);
        assertSame(currentValue, val.get());
        assertEquals(42, currentValue.intValue());

        notCurrent = new Integer(42);
        result = val.compareAndExchangeAcquire(notCurrent, newValue);
        assertEquals(42, result.intValue());
        assertSame(currentValue, result);
        assertSame(currentValue, val.get());
        assertEquals(42, currentValue.intValue());

        result = val.compareAndExchangeAcquire(currentValue, newValue);
        assertEquals(42, result.intValue());
        assertSame(currentValue, result);
        assertSame(newValue, val.get());
        currentValue = val.get();
        assertEquals(1, currentValue.intValue());


        notCurrent = new Integer(42);
        newValue = Integer.valueOf(0);
        result = val.compareAndExchangeAcquire(notCurrent, newValue);
        assertEquals(1, result.intValue());
        assertSame(currentValue, result);
        assertSame(currentValue, val.get());
        assertEquals(1, currentValue.intValue());

        newValue = Integer.valueOf(0);
        result = val.compareAndExchangeAcquire(currentValue, newValue);
        assertEquals(1, result.intValue());
        assertSame(currentValue, result);
        assertSame(newValue, val.get());
        currentValue = val.get();
        assertEquals(0, currentValue.intValue());
    }

    @Test
    public void testCompareAndExchangeRelease() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);

        Integer notCurrent = new Integer(0);
        Integer newValue = Integer.valueOf(1);
        Integer result = val.compareAndExchangeRelease(notCurrent, newValue);
        assertEquals(42, result.intValue());
        assertSame(currentValue, result);
        assertSame(currentValue, val.get());
        assertEquals(42, currentValue.intValue());

        notCurrent = new Integer(42);
        result = val.compareAndExchangeRelease(notCurrent, newValue);
        assertEquals(42, result.intValue());
        assertSame(currentValue, result);
        assertSame(currentValue, val.get());
        assertEquals(42, currentValue.intValue());

        result = val.compareAndExchangeRelease(currentValue, newValue);
        assertEquals(42, result.intValue());
        assertSame(currentValue, result);
        assertSame(newValue, val.get());
        currentValue = val.get();
        assertEquals(1, currentValue.intValue());


        notCurrent = new Integer(42);
        newValue = Integer.valueOf(0);
        result = val.compareAndExchangeRelease(notCurrent, newValue);
        assertEquals(1, result.intValue());
        assertSame(currentValue, result);
        assertSame(currentValue, val.get());
        assertEquals(1, currentValue.intValue());

        newValue = Integer.valueOf(0);
        result = val.compareAndExchangeRelease(currentValue, newValue);
        assertEquals(1, result.intValue());
        assertSame(currentValue, result);
        assertSame(newValue, val.get());
        currentValue = val.get();
        assertEquals(0, currentValue.intValue());
    }

    @Test
    public void testGetAcquire() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);
        assertSame(currentValue, val.getAcquire());
        assertEquals(42, val.getAcquire().intValue());

        currentValue = Integer.valueOf(0);
        val.set(currentValue);
        assertSame(currentValue, val.getAcquire());
        assertEquals(0, val.getAcquire().intValue());

        currentValue = Integer.valueOf(5);
        val.set(currentValue);
        assertSame(currentValue, val.getAcquire());
        assertEquals(5, val.getAcquire().intValue());
    }

    @Test
    public void testGetOpaque() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);
        assertSame(currentValue, val.getOpaque());
        assertEquals(42, val.getOpaque().intValue());

        currentValue = Integer.valueOf(0);
        val.set(currentValue);
        assertSame(currentValue, val.getOpaque());
        assertEquals(0, val.getOpaque().intValue());

        currentValue = Integer.valueOf(5);
        val.set(currentValue);
        assertSame(currentValue, val.getOpaque());
        assertEquals(5, val.getOpaque().intValue());
    }

    @Test
    public void testGetPlain() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);
        assertSame(currentValue, val.getPlain());
        assertEquals(42, val.getPlain().intValue());

        currentValue = Integer.valueOf(0);
        val.set(currentValue);
        assertSame(currentValue, val.getPlain());
        assertEquals(0, val.getPlain().intValue());

        currentValue = Integer.valueOf(5);
        val.set(currentValue);
        assertSame(currentValue, val.getPlain());
        assertEquals(5, val.getPlain().intValue());
    }

    @Test
    public void testSetOpaque() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);
        assertSame(currentValue, val.get());
        assertEquals(42, val.get().intValue());

        currentValue = Integer.valueOf(0);
        val.setOpaque(currentValue);
        assertSame(currentValue, val.get());
        assertEquals(0, val.get().intValue());

        currentValue = Integer.valueOf(5);
        val.setOpaque(currentValue);
        assertSame(currentValue, val.get());
        assertEquals(5, val.get().intValue());
    }

    @Test
    public void testSetPlain() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);
        assertSame(currentValue, val.get());
        assertEquals(42, val.get().intValue());

        currentValue = Integer.valueOf(0);
        val.setPlain(currentValue);
        assertSame(currentValue, val.get());
        assertEquals(0, val.get().intValue());

        currentValue = Integer.valueOf(5);
        val.setPlain(currentValue);
        assertSame(currentValue, val.get());
        assertEquals(5, val.get().intValue());
    }

    @Test
    public void testSetRelease() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);
        assertSame(currentValue, val.get());
        assertEquals(42, val.get().intValue());

        currentValue = Integer.valueOf(0);
        val.setRelease(currentValue);
        assertSame(currentValue, val.get());
        assertEquals(0, val.get().intValue());

        currentValue = Integer.valueOf(5);
        val.setRelease(currentValue);
        assertSame(currentValue, val.get());
        assertEquals(5, val.get().intValue());
    }

    @Test
    public void testWeakCompareAndSetAcquire() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);
        for (int i = 0; i < 10; ++i) {
            assertFalse(val.weakCompareAndSetAcquire(Integer.valueOf(0), Integer.valueOf(-1)));
            assertSame(currentValue, val.get());
            assertEquals(42, val.get().intValue());
        }

        Integer newValue = Integer.valueOf(0);
        do { } while (!val.weakCompareAndSetAcquire(currentValue, newValue));
        currentValue = val.get();
        assertSame(newValue, currentValue);
        assertEquals(0, currentValue.intValue());

        newValue = Integer.valueOf(5);
        do { } while (!val.weakCompareAndSetAcquire(currentValue, newValue));
        currentValue = val.get();
        assertSame(newValue, currentValue);
        assertEquals(5, currentValue.intValue());

        newValue = Integer.valueOf(-1);
        do { } while (!val.weakCompareAndSetAcquire(currentValue, newValue));
        currentValue = val.get();
        assertSame(newValue, currentValue);
        assertEquals(-1, currentValue.intValue());
    }

    @Test
    public void testWeakCompareAndSetPlain() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);
        for (int i = 0; i < 10; ++i) {
            assertFalse(val.weakCompareAndSetPlain(Integer.valueOf(0), Integer.valueOf(-1)));
            assertSame(currentValue, val.get());
            assertEquals(42, val.get().intValue());
        }

        Integer newValue = Integer.valueOf(0);
        do { } while (!val.weakCompareAndSetPlain(currentValue, newValue));
        currentValue = val.get();
        assertSame(newValue, currentValue);
        assertEquals(0, currentValue.intValue());

        newValue = Integer.valueOf(5);
        do { } while (!val.weakCompareAndSetPlain(currentValue, newValue));
        currentValue = val.get();
        assertSame(newValue, currentValue);
        assertEquals(5, currentValue.intValue());

        newValue = Integer.valueOf(-1);
        do { } while (!val.weakCompareAndSetPlain(currentValue, newValue));
        currentValue = val.get();
        assertSame(newValue, currentValue);
        assertEquals(-1, currentValue.intValue());
    }

    @Test
    public void testWeakCompareAndSetRelease() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);
        for (int i = 0; i < 10; ++i) {
            assertFalse(val.weakCompareAndSetRelease(Integer.valueOf(0), Integer.valueOf(-1)));
            assertSame(currentValue, val.get());
            assertEquals(42, val.get().intValue());
        }

        Integer newValue = Integer.valueOf(0);
        do { } while (!val.weakCompareAndSetRelease(currentValue, newValue));
        currentValue = val.get();
        assertSame(newValue, currentValue);
        assertEquals(0, currentValue.intValue());

        newValue = Integer.valueOf(5);
        do { } while (!val.weakCompareAndSetRelease(currentValue, newValue));
        currentValue = val.get();
        assertSame(newValue, currentValue);
        assertEquals(5, currentValue.intValue());

        newValue = Integer.valueOf(-1);
        do { } while (!val.weakCompareAndSetRelease(currentValue, newValue));
        currentValue = val.get();
        assertSame(newValue, currentValue);
        assertEquals(-1, currentValue.intValue());
    }

    @Test
    public void testWeakCompareAndSetVolatile() {
        Integer currentValue = Integer.valueOf(42);
        AtomicReference<Integer> val = new AtomicReference(currentValue);
        for (int i = 0; i < 10; ++i) {
            assertFalse(val.weakCompareAndSetVolatile(Integer.valueOf(0), Integer.valueOf(-1)));
            assertSame(currentValue, val.get());
            assertEquals(42, val.get().intValue());
        }

        Integer newValue = Integer.valueOf(0);
        do { } while (!val.weakCompareAndSetVolatile(currentValue, newValue));
        currentValue = val.get();
        assertSame(newValue, currentValue);
        assertEquals(0, currentValue.intValue());

        newValue = Integer.valueOf(5);
        do { } while (!val.weakCompareAndSetVolatile(currentValue, newValue));
        currentValue = val.get();
        assertSame(newValue, currentValue);
        assertEquals(5, currentValue.intValue());

        newValue = Integer.valueOf(-1);
        do { } while (!val.weakCompareAndSetVolatile(currentValue, newValue));
        currentValue = val.get();
        assertSame(newValue, currentValue);
        assertEquals(-1, currentValue.intValue());
    }

}
