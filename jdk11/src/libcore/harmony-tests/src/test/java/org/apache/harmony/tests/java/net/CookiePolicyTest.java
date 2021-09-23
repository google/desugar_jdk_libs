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

import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;

import junit.framework.TestCase;

public class CookiePolicyTest extends TestCase {

    /**
     * java.net.CookiePolicy#shouldAccept(java.net.URI,
     *java.net.HttpCookie).
     * @since 1.6
     */
    public void test_ShouldAccept_LURI_LHttpCookie() throws URISyntaxException {
        HttpCookie cookie = new HttpCookie("Harmony_6", "ongoing");
        URI uri = new URI("");
        boolean accept;

        // Policy: ACCEPT_ALL, always returns true
        accept = CookiePolicy.ACCEPT_ALL.shouldAccept(null, cookie);
        assertTrue(accept);

        accept = CookiePolicy.ACCEPT_ALL.shouldAccept(null, null);
        assertTrue(accept);

        accept = CookiePolicy.ACCEPT_ALL.shouldAccept(uri, null);
        assertTrue(accept);

        // Policy: ACCEPT_NONE, always returns false
        accept = CookiePolicy.ACCEPT_NONE.shouldAccept(null, cookie);
        assertFalse(accept);

        accept = CookiePolicy.ACCEPT_NONE.shouldAccept(null, null);
        assertFalse(accept);

        accept = CookiePolicy.ACCEPT_NONE.shouldAccept(uri, null);
        assertFalse(accept);

        // Policy: ACCEPT_ORIGINAL_SERVER
        accept = CookiePolicy.ACCEPT_ORIGINAL_SERVER.shouldAccept(uri, cookie);
        assertFalse(accept);

        cookie.setDomain(".b.c");
        accept = CookiePolicy.ACCEPT_ORIGINAL_SERVER.shouldAccept(new URI(
                "schema://a.b.c"), cookie);
        assertTrue(accept);

        cookie.setDomain(".b.c");
        accept = CookiePolicy.ACCEPT_ORIGINAL_SERVER.shouldAccept(new URI(
                "s://a.b.c.d"), cookie);
        assertFalse(accept);

        cookie.setDomain("b.c");
        accept = CookiePolicy.ACCEPT_ORIGINAL_SERVER.shouldAccept(new URI(
                "s://a.b.c.d"), cookie);
        assertFalse(accept);

        cookie.setDomain("a.b.c.d");
        accept = CookiePolicy.ACCEPT_ORIGINAL_SERVER.shouldAccept(new URI(
                "s://a.b.c.d"), cookie);
        assertTrue(accept);

        cookie.setDomain(".");
        accept = CookiePolicy.ACCEPT_ORIGINAL_SERVER.shouldAccept(new URI(
                "s://a.b.c.d"), cookie);
        assertFalse(accept);

        cookie.setDomain("");
        accept = CookiePolicy.ACCEPT_ORIGINAL_SERVER.shouldAccept(new URI(
                "s://a.b.c.d"), cookie);
        assertFalse(accept);

        accept = CookiePolicy.ACCEPT_ORIGINAL_SERVER.shouldAccept(null, cookie);
        assertFalse(accept);

        accept = CookiePolicy.ACCEPT_ORIGINAL_SERVER.shouldAccept(uri, null);
        assertFalse(accept);

        accept = CookiePolicy.ACCEPT_ORIGINAL_SERVER.shouldAccept(null, null);
        assertFalse(accept);
    }

}
