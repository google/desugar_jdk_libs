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
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.NetPermission;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.security.Permission;
import java.util.Map;

import junit.framework.TestCase;

public class ResponseCacheTest extends TestCase {

    /**
     * java.net.ResponseCache#getDefault()
     */
    public void test_GetDefault() throws Exception {
        assertNull(ResponseCache.getDefault());
    }

    /**
     * java.net.ResponseCache#setDefault(ResponseCache)
     */
    public void test_SetDefaultLjava_net_ResponseCache_Normal()
            throws Exception {
        ResponseCache rc1 = new MockResponseCache();
        ResponseCache rc2 = new MockResponseCache();
        ResponseCache.setDefault(rc1);
        assertSame(ResponseCache.getDefault(), rc1);
        ResponseCache.setDefault(rc2);
        assertSame(ResponseCache.getDefault(), rc2);
        ResponseCache.setDefault(null);
        assertNull(ResponseCache.getDefault());
    }

    /*
      * MockResponseCache for testSetDefault(ResponseCache)
      */
    class MockResponseCache extends ResponseCache {

        public CacheResponse get(URI arg0, String arg1, Map arg2)
                throws IOException {
            return null;
        }

        public CacheRequest put(URI arg0, URLConnection arg1)
                throws IOException {
            return null;
        }
    }
}
