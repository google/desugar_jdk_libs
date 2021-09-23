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
 * limitations under the License.
 */

package libcore.java.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class InheritableThreadLocalTest {

    @Test
    public void childValue_shouldReturnInputArgument() {
        TestInheritableThreadLocal<Object> threadLocal = new TestInheritableThreadLocal<>();

        assertNull(threadLocal.childValue(null));
        Object parentValue = new Object();
        assertEquals(parentValue, threadLocal.childValue(parentValue));
    }

    private static final class TestInheritableThreadLocal<T> extends InheritableThreadLocal<T> {

        @Override
        public T childValue(T parentValue) {
            return super.childValue(parentValue);
        }
    }

}
