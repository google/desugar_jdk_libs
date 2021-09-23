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

package libcore.java.security;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.security.AuthProvider;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;

@RunWith(JUnit4.class)
public class AuthProviderTest {

    private static class TestAuthProvider extends AuthProvider {
        final String name;

        protected TestAuthProvider(String name, double version, String info) {
            super(name, version, info);
            this.name = name;
        }

        @Override
        public void login(Subject subject, CallbackHandler handler) {
        }

        @Override
        public void logout() {
        }

        @Override
        public void setCallbackHandler(CallbackHandler handler) {
        }
    }

    @Test
    public void testConstructor() {
        TestAuthProvider provider = new TestAuthProvider("test", 1.0d, "info");
        assertEquals("test", provider.name);
    }
}
