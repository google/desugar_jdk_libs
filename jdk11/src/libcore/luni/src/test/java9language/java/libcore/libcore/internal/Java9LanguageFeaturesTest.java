/*
 * Copyright (C) 2018 The Android Open Source Project
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

package libcore.libcore.internal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import junit.framework.TestCase;
import libcore.internal.Java9LanguageFeatures;

public class Java9LanguageFeaturesTest extends TestCase {

    public static class SimplePerson implements Java9LanguageFeatures.Person {
        private final String name;
        public SimplePerson(String name) { this.name = Objects.requireNonNull(name); }
        @Override public String toString() { return "Person: " + name; }
        @Override public String name() { return name; }
    }

    public void testPrivateInterfaceMethods() {
        assertFalse(new SimplePerson("Anna").isPalindrome());
        assertTrue(new SimplePerson("Anna").isPalindromeIgnoreCase());
        assertTrue(new SimplePerson("anna").isPalindrome());
        assertTrue(new SimplePerson("bob").isPalindrome());
        assertFalse(new SimplePerson("larry").isPalindrome());
        assertFalse(new SimplePerson("larry").isPalindromeIgnoreCase());
    }

    public void testTryOnEffectivelyFinalVariables() throws IOException {
        byte[] data = "Hello, world!".getBytes();
        byte[] dataCopy = Java9LanguageFeatures.copy(data);
        assertTrue(Arrays.equals(data, dataCopy));
        assertTrue(data != dataCopy);
    }

    public void testDiamondOnAnonymousClasses() {
        AtomicReference<String> ref = new Java9LanguageFeatures().createReference("Hello, world");
        assertSame("Hello, world", ref.get());
    }

    public void testSafeVarargsOnPrivateMethod() {
        assertEquals("[23, and, 42]", Java9LanguageFeatures.toListString(23, "and", 42L));
    }

}
