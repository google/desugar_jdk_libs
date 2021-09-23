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

package libcore.libcore.net.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import libcore.net.http.HttpURLConnectionFactory;
import libcore.net.http.Dns;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;

@RunWith(JUnit4.class)
public class HttpURLConnectionFactoryTest {

    @Test
    public void testCreateInstance() {
        final HttpURLConnectionFactory factory = HttpURLConnectionFactory.createInstance();
        assertNotNull(factory);
        assertTrue(factory instanceof HttpURLConnectionFactory);
    }

    @Test
    public void testOpenConnection() throws IOException {
        final Map<String, InetAddress> lookupResult = new HashMap();
        lookupResult.put("test.com", Inet4Address.ANY);
        final HttpURLConnectionFactory factory = HttpURLConnectionFactory.createInstance();
        final Dns dns = hostname -> Arrays.asList(lookupResult.get(hostname));
        factory.setDns(dns);
        factory.setNewConnectionPool(5, 5000, TimeUnit.MILLISECONDS);
        final URL url = new URL("http://test.com");
        final URLConnection connection = factory.openConnection(url, SocketFactory.getDefault(),
                java.net.Proxy.NO_PROXY);
        assertEquals(connection.getURL(), url);
    }

    @Test
    public void testDns() throws IOException  {
        final String testHostName1 = "test1.com";
        final String testHostName2 = "test2.com";
        final String testHostName3 = "test3.com";
        final List<InetAddress> testHostAddresses1 = List.of(Inet4Address.ALL);
        final List<InetAddress> testHostAddresses2 = List.of(Inet6Address.ANY);
        final List<InetAddress> testHostAddresses3 =
                List.of(Inet4Address.ANY, Inet6Address.LOOPBACK);
        final Map<String, List<InetAddress>> lookupResult = new HashMap();
        lookupResult.put(testHostName1, testHostAddresses1);
        lookupResult.put(testHostName2, testHostAddresses2);
        lookupResult.put(testHostName3, testHostAddresses3);

        final Dns dns = hostname -> lookupResult.get(hostname);
        assertEquals(testHostAddresses1, dns.lookup(testHostName1));
        assertEquals(testHostAddresses2, dns.lookup(testHostName2));
        assertEquals(testHostAddresses3, dns.lookup(testHostName3));
    }
}
