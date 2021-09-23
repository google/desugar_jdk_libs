/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.harmony.tests.java.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.harmony.testframework.serialization.SerializationTest;
import org.apache.harmony.testframework.serialization.SerializationTest.SerializableAssert;


public class InetAddressTest extends junit.framework.TestCase {

    /**
     * java.net.InetAddress#getByName(String)
     */
    public void test_getByName_exceptionContainsUsefulMessage() {
        // Related to HARMONY-5784
        try {
            InetAddress.getByName("1.2.3.4hello");
            fail();
        } catch (UnknownHostException e) {
            assertTrue(e.getMessage().contains("1.2.3.4hello"));
        }
    }

    public void test_equalsLjava_lang_Object() throws Exception {
        InetAddress ia1 = InetAddress.getByName("ip6-localhost");
        InetAddress ia2 = InetAddress.getByName("::1");
        assertEquals(ia2, ia1);
    }

    /**
     * java.net.InetAddress#getAddress()
     */
    public void test_getAddress() throws UnknownHostException {
        // Test for method byte [] java.net.InetAddress.getAddress()
        try {
            InetAddress ia = InetAddress.getByName("127.0.0.1");
            byte[] caddr = new byte[] { 127, 0, 0, 1 };
            byte[] addr = ia.getAddress();
            for (int i = 0; i < addr.length; i++)
                assertTrue("Incorrect address returned", caddr[i] == addr[i]);
        } catch (java.net.UnknownHostException e) {
        }

        byte[] origBytes = new byte[] { 0, 1, 2, 3 };
        InetAddress address = InetAddress.getByAddress(origBytes);
        origBytes[0] = -1;
        byte[] newBytes = address.getAddress();
        assertSame((byte) 0, newBytes[0]);
    }

    /**
     * java.net.InetAddress#getAllByName(java.lang.String)
     */
    @SuppressWarnings("nls")
    public void test_getAllByNameLjava_lang_String() throws Exception {
        // Test for method java.net.InetAddress []
        // java.net.InetAddress.getAllByName(java.lang.String)
        InetAddress[] all = InetAddress.getAllByName("localhost");
        assertNotNull(all);
        // Number of aliases depends on individual test machine
        assertTrue(all.length >= 1);
        for (InetAddress alias : all) {
            // Check that each alias has the same hostname. Intentionally not
            // checking for exact string match.
            assertTrue(alias.getHostName().startsWith("localhost"));
        }// end for all aliases

        //Regression for HARMONY-56
        InetAddress[] ias = InetAddress.getAllByName(null);
        assertEquals(2, ias.length);
        for (InetAddress ia : ias) {
            assertTrue(ia.isLoopbackAddress());
        }
        ias = InetAddress.getAllByName("");
        assertEquals(2, ias.length);
        for (InetAddress ia : ias) {
            assertTrue(ia.isLoopbackAddress());
        }

        // Check that getting addresses by dotted string distinguish
        // IPv4 and IPv6 subtypes.
        InetAddress[] list = InetAddress.getAllByName("192.168.0.1");
        for (InetAddress addr : list) {
            assertFalse("Expected subclass returned",
                    addr.getClass().equals(InetAddress.class));
        }
    }

    /**
     * java.net.InetAddress#getHostAddress()
     */
    public void test_getHostAddress() throws Exception {
        assertEquals("1.2.3.4", InetAddress.getByName("1.2.3.4").getHostAddress());
        assertEquals("::1", InetAddress.getByName("::1").getHostAddress());
    }

    /**
     * java.net.InetAddress#getLocalHost()
     */
    public void test_getLocalHost() throws Exception {
        // Test for method java.net.InetAddress
        // java.net.InetAddress.getLocalHost()

        // We don't know the host name or ip of the machine
        // running the test, so we can't build our own address
        DatagramSocket dg = new DatagramSocket(0, InetAddress
                .getLocalHost());
        assertTrue("Incorrect host returned", InetAddress.getLocalHost()
                .equals(dg.getLocalAddress()));
        dg.close();
    }

    /**
     * java.net.InetAddress#getLocalHost()
     */
    public void test_getLocalHost_extended() throws Exception {
        // Bogus, but we don't know the host name or ip of the machine
        // running the test, so we can't build our own address
        DatagramSocket dg = new DatagramSocket(0, InetAddress.getLocalHost());
        assertEquals("Incorrect host returned", InetAddress.getLocalHost(), dg.getLocalAddress());
        dg.close();
    }

    /**
     * java.net.InetAddress#isMulticastAddress()
     */
    public void test_isMulticastAddress() throws UnknownHostException {
        InetAddress ia2 = InetAddress.getByName("239.255.255.255");
        assertTrue(ia2.isMulticastAddress());
        ia2 = InetAddress.getByName("localhost");
        assertFalse(ia2.isMulticastAddress());
    }

    /**
     * java.net.InetAddress#isAnyLocalAddress()
     */
    public void test_isAnyLocalAddress() throws UnknownHostException {
        InetAddress ia2 = InetAddress.getByName("239.255.255.255");
        assertFalse(ia2.isAnyLocalAddress());
        ia2 = InetAddress.getByName("localhost");
        assertFalse(ia2.isAnyLocalAddress());
    }

    /**
     * java.net.InetAddress#isLinkLocalAddress()
     */
    public void test_isLinkLocalAddress() throws UnknownHostException {
        InetAddress ia2 = InetAddress.getByName("239.255.255.255");
        assertFalse(ia2.isLinkLocalAddress());
        ia2 = InetAddress.getByName("localhost");
        assertFalse(ia2.isLinkLocalAddress());
    }

    /**
     * java.net.InetAddress#isLoopbackAddress()
     */
    public void test_isLoopbackAddress() throws UnknownHostException {
        InetAddress ia2 = InetAddress.getByName("239.255.255.255");
        assertFalse(ia2.isLoopbackAddress());
        ia2 = InetAddress.getByName("localhost");
        assertTrue(ia2.isLoopbackAddress());
        ia2 = InetAddress.getByName("127.0.0.2");
        assertTrue(ia2.isLoopbackAddress());
    }

    /**
     * java.net.InetAddress#isLoopbackAddress()
     */
    public void test_isSiteLocalAddress() throws UnknownHostException {
        InetAddress ia2 = InetAddress.getByName("239.255.255.255");
        assertFalse(ia2.isSiteLocalAddress());
        ia2 = InetAddress.getByName("localhost");
        assertFalse(ia2.isSiteLocalAddress());
        ia2 = InetAddress.getByName("127.0.0.2");
        assertFalse(ia2.isSiteLocalAddress());
        ia2 = InetAddress.getByName("243.243.45.3");
        assertFalse(ia2.isSiteLocalAddress());
        ia2 = InetAddress.getByName("10.0.0.2");
        assertTrue(ia2.isSiteLocalAddress());
    }

    /**
     * java.net.InetAddress#isMCGlobal()/isMCLinkLocal/isMCNodeLocal/isMCOrgLocal/isMCSiteLocal
     */
    public void test_isMCVerify() throws UnknownHostException {
        InetAddress ia2 = InetAddress.getByName("239.255.255.255");
        assertFalse(ia2.isMCGlobal());
        assertFalse(ia2.isMCLinkLocal());
        assertFalse(ia2.isMCNodeLocal());
        assertFalse(ia2.isMCOrgLocal());
        assertTrue(ia2.isMCSiteLocal());
        ia2 = InetAddress.getByName("243.243.45.3");
        assertFalse(ia2.isMCGlobal());
        assertFalse(ia2.isMCLinkLocal());
        assertFalse(ia2.isMCNodeLocal());
        assertFalse(ia2.isMCOrgLocal());
        assertFalse(ia2.isMCSiteLocal());
        ia2 = InetAddress.getByName("250.255.255.254");
        assertFalse(ia2.isMCGlobal());
        assertFalse(ia2.isMCLinkLocal());
        assertFalse(ia2.isMCNodeLocal());
        assertFalse(ia2.isMCOrgLocal());
        assertFalse(ia2.isMCSiteLocal());
        ia2 = InetAddress.getByName("10.0.0.2");
        assertFalse(ia2.isMCGlobal());
        assertFalse(ia2.isMCLinkLocal());
        assertFalse(ia2.isMCNodeLocal());
        assertFalse(ia2.isMCOrgLocal());
        assertFalse(ia2.isMCSiteLocal());
    }

    /**
     * java.net.InetAddress#toString()
     */
    public void test_toString() throws Exception {
        // Test for method java.lang.String java.net.InetAddress.toString()
        InetAddress ia2 = InetAddress.getByName("127.0.0.1");
        assertEquals("/127.0.0.1", ia2.toString());
        // Regression for HARMONY-84
        InetAddress addr2 = InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 });
        assertEquals("Assert 1: wrong string from address", "/127.0.0.1", addr2.toString());
    }

    /**
     * java.net.InetAddress#getByAddress(java.lang.String, byte[])
     */
    public void test_getByAddressLjava_lang_String$B() {
        // Check an IPv4 address with an IPv6 hostname
        byte ipAddress[] = { 127, 0, 0, 1 };
        String addressStr = "::1";
        try {
            InetAddress addr = InetAddress.getByAddress(addressStr, ipAddress);
            addr = InetAddress.getByAddress(ipAddress);
        } catch (UnknownHostException e) {
            fail("Unexpected problem creating IP Address "
                    + ipAddress.length);
        }

        byte ipAddress2[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, 127, 0, 0,
                1 };
        addressStr = "::1";
        try {
            InetAddress addr = InetAddress.getByAddress(addressStr, ipAddress2);
            addr = InetAddress.getByAddress(ipAddress);
        } catch (UnknownHostException e) {
            fail("Unexpected problem creating IP Address "
                    + ipAddress.length);
        }
    }

    /**
     * java.net.InetAddress#getCanonicalHostName()
     */
    public void test_getCanonicalHostName() throws Exception {
        InetAddress theAddress = null;
        theAddress = InetAddress.getLocalHost();
        assertTrue("getCanonicalHostName returned a zero length string ",
                theAddress.getCanonicalHostName().length() != 0);
        assertTrue("getCanonicalHostName returned an empty string ",
                !theAddress.equals(""));
    }

    /**
     * java.net.InetAddress#isReachableI
     */
    public void test_isReachableI() throws Exception {
        InetAddress ia = Inet4Address.getByName("127.0.0.1");
        assertTrue(ia.isReachable(10000));
        ia = Inet4Address.getByName("127.0.0.1");
        try {
            ia.isReachable(-1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // correct
        }
    }

    /**
     * java.net.InetAddress#isReachableLjava_net_NetworkInterfaceII
     */
    public void test_isReachableLjava_net_NetworkInterfaceII() throws Exception {
        // tests local address
        InetAddress ia = Inet4Address.getByName("127.0.0.1");
        assertTrue(ia.isReachable(null, 0, 10000));
        ia = Inet4Address.getByName("127.0.0.1");
        try {
            ia.isReachable(null, -1, 10000);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // correct
        }
        try {
            ia.isReachable(null, 0, -1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // correct
        }
        try {
            ia.isReachable(null, -1, -1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // correct
        }
        // tests nowhere using an address from the  'IPv4 Address Blocks Reserved for Documentation'
        // as specified in https://tools.ietf.org/html/rfc5737
        ia = Inet4Address.getByName("192.0.2.1");
        assertFalse(ia.isReachable(1000));
        assertFalse(ia.isReachable(null, 0, 1000));

        // Regression test for HARMONY-1842.
        ia = InetAddress.getByName("localhost"); //$NON-NLS-1$
        Enumeration<NetworkInterface> nif = NetworkInterface.getNetworkInterfaces();
        NetworkInterface netif;
        while (nif.hasMoreElements()) {
            netif = nif.nextElement();
            ia.isReachable(netif, 10, 1000);
        }
    }

    // comparator for InetAddress objects
    private static final SerializableAssert COMPARATOR = new SerializableAssert() {
        public void assertDeserialized(Serializable initial,
                Serializable deserialized) {

            InetAddress initAddr = (InetAddress) initial;
            InetAddress desrAddr = (InetAddress) deserialized;

            byte[] iaAddresss = initAddr.getAddress();
            byte[] deIAAddresss = desrAddr.getAddress();
            for (int i = 0; i < iaAddresss.length; i++) {
                assertEquals(iaAddresss[i], deIAAddresss[i]);
            }
            assertEquals(initAddr.getHostName(), desrAddr.getHostName());
        }
    };

    // Regression Test for Harmony-2290
    public void test_isReachableLjava_net_NetworkInterfaceII_loopbackInterface() throws IOException {
        final int TTL = 20;
        final int TIME_OUT = 3000;

        NetworkInterface loopbackInterface = null;
        ArrayList<InetAddress> localAddresses = new ArrayList<InetAddress>();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        assertNotNull(networkInterfaces);
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                if (address.isLoopbackAddress()) {
                    loopbackInterface = networkInterface;
                } else {
                    localAddresses.add(address);
                }
            }
        }

        //loopbackInterface can reach local address
        if (null != loopbackInterface) {
            for (InetAddress destAddress : localAddresses) {
                assertTrue(destAddress.isReachable(loopbackInterface, TTL, TIME_OUT));
            }
        }

        //loopback Interface cannot reach outside address
        InetAddress destAddress = InetAddress.getByName("www.google.com");
        assertFalse(destAddress.isReachable(loopbackInterface, TTL, TIME_OUT));
    }

    /**
     * serialization/deserialization compatibility.
     */
    public void testSerializationSelf() throws Exception {

        SerializationTest.verifySelf(InetAddress.getByName("localhost"),
                COMPARATOR);
    }

    /**
     * serialization/deserialization compatibility with RI.
     */
    public void testSerializationCompatibility() throws Exception {

        SerializationTest.verifyGolden(this,
                InetAddress.getByName("localhost"), COMPARATOR);
    }

    /**
     * java.net.InetAddress#getByAddress(byte[])
     */
    public void test_getByAddress() {
        // Regression for HARMONY-61
        try {
            InetAddress.getByAddress(null);
            fail("Assert 0: UnknownHostException must be thrown");
        } catch (UnknownHostException e) {
            // Expected
        }
    }
}
