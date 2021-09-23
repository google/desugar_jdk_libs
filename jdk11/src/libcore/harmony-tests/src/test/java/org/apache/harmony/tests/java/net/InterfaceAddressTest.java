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

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

import junit.framework.TestCase;

public class InterfaceAddressTest extends TestCase {
    private InterfaceAddress interfaceAddr;

    private InterfaceAddress anotherInterfaceAddr;

    /**
     * java.net.InterfaceAddress.hashCode()
     * @since 1.6
     */
    public void test_hashCode() {
        // RI may fail on this when both broadcast addresses are null
        if (interfaceAddr != null) {
            assertEquals(anotherInterfaceAddr, interfaceAddr);
            assertEquals(anotherInterfaceAddr.hashCode(), interfaceAddr
                    .hashCode());
        }
    }

    /**
     * java.net.InterfaceAddress.equals(Object)
     * @since 1.6
     */
    public void test_equals_LObject() {
        // RI may fail on this when both broadcast addresses are null
        if (interfaceAddr != null) {
            assertFalse(interfaceAddr.equals(null));
            assertFalse(interfaceAddr.equals(new Object()));

            assertTrue(interfaceAddr.equals(anotherInterfaceAddr));
            assertNotSame(anotherInterfaceAddr, interfaceAddr);
        }
    }

    /**
     * java.net.InterfaceAddress.toString()
     * @since 1.6
     */
    public void test_toString() {
        if (interfaceAddr != null) {
            assertNotNull(interfaceAddr.toString());
            assertEquals(anotherInterfaceAddr.toString(), interfaceAddr
                    .toString());
            assertTrue(interfaceAddr.toString().contains("/"));
            assertTrue(interfaceAddr.toString().contains("["));
            assertTrue(interfaceAddr.toString().contains("]"));
        }
    }

    /**
     * java.net.InterfaceAddress.getAddress()
     * @since 1.6
     */
    public void test_getAddress() {
        if (interfaceAddr != null) {
            InetAddress addr1 = interfaceAddr.getAddress();
            assertNotNull(addr1);
            InetAddress addr2 = anotherInterfaceAddr.getAddress();
            assertNotNull(addr2);
            assertEquals(addr2, addr1);
        }
    }

    /**
     * java.net.InterfaceAddress.getBroadcast()
     * @since 1.6
     */
    public void test_getBroadcast() {
        if (interfaceAddr != null) {
            InetAddress addr = interfaceAddr.getAddress();
            InetAddress addr1 = interfaceAddr.getBroadcast();
            InetAddress addr2 = anotherInterfaceAddr.getBroadcast();
            if (addr instanceof Inet4Address) {
                assertEquals(addr2, addr1);
            } else if (addr instanceof Inet6Address) {
                assertNull(addr1);
                assertNull(addr2);
            }
        }
    }

    /**
     * java.net.InterfaceAddress.getNetworkPrefixLength()
     * @since 1.6
     */
    public void test_getNetworkPrefixLength() {
        if (interfaceAddr != null) {
            short prefix1 = interfaceAddr.getNetworkPrefixLength();
            short prefix2 = anotherInterfaceAddr.getNetworkPrefixLength();
            assertEquals(prefix2, prefix1);
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Enumeration<NetworkInterface> netifs = NetworkInterface
                .getNetworkInterfaces();
        NetworkInterface theInterface = null;
        if (netifs != null) {
            while (netifs.hasMoreElements()) {
                theInterface = netifs.nextElement();
                if (theInterface != null) {
                    List<InterfaceAddress> addrs = theInterface
                            .getInterfaceAddresses();
                    if (!(addrs == null || addrs.isEmpty())) {
                        interfaceAddr = addrs.get(0);
                        break;
                    }
                }
            }
        }

        // get another InterfaceAddress object if the interfaceAddr exists. It
        // equals to interfaceAddr, but is not the same one.
        if (theInterface != null && interfaceAddr != null) {
            Enumeration<InetAddress> addresses = theInterface
                    .getInetAddresses();
            if (addresses != null && addresses.hasMoreElements()) {
                NetworkInterface anotherNetworkInter = NetworkInterface
                        .getByInetAddress(addresses.nextElement());
                anotherInterfaceAddr = anotherNetworkInter
                        .getInterfaceAddresses().get(0);
            }
        }
    }

    @Override
    protected void tearDown() throws Exception {
        interfaceAddr = null;
        anotherInterfaceAddr = null;
        super.tearDown();
    }

}
