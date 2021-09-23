/*
 * Copyright (C) 2016 The Android Open Source Project
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

package libcore.java.net;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.InMemoryCookieStore;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookiesMCompatibilityTest extends AbstractCookiesTest {
    @Override
    public CookieStore createCookieStore() {
        return new InMemoryCookieStore(23 /* VERSION_CODES.M : android marshmallow */);
    }

    // http://b/26456024
    public void testCookiesWithoutLeadingPeriod() throws Exception {
        CookieManager cm = new CookieManager(createCookieStore(), null);
        Map<String, List<String>> responseHeaders = Collections.singletonMap("Set-Cookie",
                Collections.singletonList("a=b; domain=chargepoint.com"));

        URI uri = new URI("http://services.chargepoint.com");
        cm.put(uri, responseHeaders);

        Map<String, List<String>> cookies = cm.get(
                new URI("https://webservices.chargepoint.com/foo"),
                responseHeaders);

        assertEquals(0, cookies.size());
    }

    public void testCookiesWithLeadingPeriod() throws Exception {
        CookieManager cm = new CookieManager(createCookieStore(), null);
        URI uri = new URI("http://services.chargepoint.com");
        List<String> list = new ArrayList<>();
        Map<String, List<String>> responseHeaders = Collections.singletonMap("Set-Cookie",
                Collections.singletonList("b=c; domain=.chargepoint.com;"));
        cm.put(uri, responseHeaders);
        Map<String, List<String>> cookies = cm.get(
                new URI("https://webservices.chargepoint.com/foo"),
                responseHeaders);

        List<String> cookieList = cookies.values().iterator().next();
        assertEquals(Collections.singletonList("b=c"), cookieList);
    }
}
