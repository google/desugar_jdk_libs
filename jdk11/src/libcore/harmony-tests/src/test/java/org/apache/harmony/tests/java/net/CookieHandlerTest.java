/* Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.harmony.tests.java.net;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.NetPermission;
import java.net.URI;
import java.security.Permission;
import java.util.Map;

import junit.framework.TestCase;

public class CookieHandlerTest extends TestCase {

    /**
     * java.net.CookieHandler#getDefault()
     */
    public void test_GetDefault() {
        assertNull(CookieHandler.getDefault());
    }

    /**
     * java.net.CookieHandler#setDefault(CookieHandler)
     */
    public void test_SetDefault_java_net_cookieHandler() {
        MockCookieHandler rc1 = new MockCookieHandler();
        MockCookieHandler rc2 = new MockCookieHandler();
        CookieHandler.setDefault(rc1);
        assertSame(CookieHandler.getDefault(), rc1);
        CookieHandler.setDefault(rc2);
        assertSame(CookieHandler.getDefault(), rc2);
        CookieHandler.setDefault(null);
        assertNull(CookieHandler.getDefault());
    }

    class MockCookieHandler extends CookieHandler {

        public Map get(URI uri, Map requestHeaders) throws IOException {
            return null;
        }

        public void put(URI uri, Map responseHeaders) throws IOException {
            // empty
        }

    }
}
