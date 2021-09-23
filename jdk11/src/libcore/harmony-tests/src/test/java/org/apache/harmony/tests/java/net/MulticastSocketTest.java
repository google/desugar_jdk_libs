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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import libcore.junit.util.ResourceLeakageDetector;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.Test;

public class MulticastSocketTest {
    @Rule
    public TestRule guardRule = ResourceLeakageDetector.getRule();

    private static InetAddress lookup(String s) {
        try {
            return InetAddress.getByName(s);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // These IP addresses aren't inherently "good" or "bad"; they're just used like that.
    // We use the "good" addresses for our actual group, and the "bad" addresses are for
    // a group that we won't actually set up.

    private static InetAddress GOOD_IPv4 = lookup("224.0.0.3");
    private static InetAddress BAD_IPv4 = lookup("224.0.0.4");
    private static InetAddress GOOD_IPv6 = lookup("ff05::7:7");
    private static InetAddress BAD_IPv6 = lookup("ff05::7:8");

    private NetworkInterface loopbackInterface;
    private NetworkInterface ipv4NetworkInterface;
    private NetworkInterface ipv6NetworkInterface;
    private boolean supportsMulticast;

    @Before
    public void setUp() throws Exception {
        // The loopback interface isn't actually useful for sending/receiving multicast messages
        // but it can be used as a fake for tests where that does not matter.
        loopbackInterface = NetworkInterface.getByInetAddress(InetAddress.getLoopbackAddress());
        assertNotNull(loopbackInterface);
        assertTrue(loopbackInterface.isLoopback());
        assertFalse(loopbackInterface.supportsMulticast());

        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        assertNotNull(interfaces);

        // Determine if the device is marked to support multicast or not. If this propery is not
        // set we assume the device has an interface capable of supporting multicast.
        supportsMulticast = Boolean.parseBoolean(
                System.getProperty("android.cts.device.multicast", "true"));
        Assume.assumeTrue(supportsMulticast);

        while (interfaces.hasMoreElements()
                && (ipv4NetworkInterface == null || ipv6NetworkInterface == null)) {
            NetworkInterface nextInterface = interfaces.nextElement();
            if (willWorkForMulticast(nextInterface)) {
                Enumeration<InetAddress> addresses = nextInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    final InetAddress nextAddress = addresses.nextElement();
                    if (nextAddress instanceof Inet6Address && ipv6NetworkInterface == null) {
                        ipv6NetworkInterface = nextInterface;
                    } else if (nextAddress instanceof Inet4Address
                            && ipv4NetworkInterface == null) {
                        ipv4NetworkInterface = nextInterface;
                    }
                }
            }
        }
        assertTrue("Test environment must have at least one interface capable of multicast for IPv4"
                        + " and IPv6",
                ipv4NetworkInterface != null && ipv6NetworkInterface != null);
    }

    @Test
    public void constructor() throws IOException {
        Assume.assumeTrue(supportsMulticast);
        // Regression test for 497.
        MulticastSocket s = new MulticastSocket();
        // Regression test for Harmony-1162.
        assertTrue(s.getReuseAddress());

        s.close();
    }

    @Test
    public void constructorI() throws IOException {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket orig = new MulticastSocket();
        int port = orig.getLocalPort();
        orig.close();

        MulticastSocket dup = new MulticastSocket(port);
        // Regression test for Harmony-1162.
        assertTrue(dup.getReuseAddress());
        dup.close();
    }

    @Test
    public void getInterface() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        // Validate that we get the expected response when one was not set.
        MulticastSocket mss = new MulticastSocket(0);
        // We expect an ANY address in this case.
        assertTrue(mss.getInterface().isAnyLocalAddress());

        // Validate that we get the expected response when we set via setInterface.
        Enumeration addresses = ipv4NetworkInterface.getInetAddresses();
        if (addresses.hasMoreElements()) {
            InetAddress firstAddress = (InetAddress) addresses.nextElement();
            mss.setInterface(firstAddress);
            assertEquals("getNetworkInterface did not return interface set by setInterface",
                    firstAddress, mss.getInterface());

            mss.close();
            mss = new MulticastSocket(0);
            mss.setNetworkInterface(ipv4NetworkInterface);
            assertEquals("getInterface did not return interface set by setNetworkInterface",
                    ipv4NetworkInterface, NetworkInterface.getByInetAddress(mss.getInterface()));
        }

        mss.close();
    }

    @Test
    public void getNetworkInterface() throws IOException {
        Assume.assumeTrue(supportsMulticast);
        // Validate that we get the expected response when one was not set.
        MulticastSocket mss = new MulticastSocket(0);
        NetworkInterface theInterface = mss.getNetworkInterface();
        assertTrue(
                "network interface returned wrong network interface when not set:" + theInterface,
                theInterface.getInetAddresses().hasMoreElements());
        InetAddress firstAddress = theInterface.getInetAddresses().nextElement();
        // Validate we the first address in the network interface is the ANY address.
        assertTrue(firstAddress.isAnyLocalAddress());

        mss.setNetworkInterface(ipv4NetworkInterface);
        assertEquals("getNetworkInterface did not return interface set by setNeworkInterface",
                ipv4NetworkInterface, mss.getNetworkInterface());

        mss.setNetworkInterface(loopbackInterface);
        assertEquals(
                "getNetworkInterface did not return network interface set by second"
                        + " setNetworkInterface call",
                loopbackInterface, mss.getNetworkInterface());
        mss.close();

        if (ipv6NetworkInterface != null) {
            mss = new MulticastSocket(0);
            mss.setNetworkInterface(ipv6NetworkInterface);
            assertEquals("getNetworkInterface did not return interface set by setNeworkInterface",
                    ipv6NetworkInterface, mss.getNetworkInterface());
            mss.close();
        }

        // Validate that we get the expected response when we set via setInterface.
        mss = new MulticastSocket(0);
        Enumeration addresses = ipv4NetworkInterface.getInetAddresses();
        if (addresses.hasMoreElements()) {
            firstAddress = (InetAddress) addresses.nextElement();
            mss.setInterface(firstAddress);
            assertEquals("getNetworkInterface did not return interface set by setInterface",
                    ipv4NetworkInterface, mss.getNetworkInterface());
        }
        mss.close();
    }

    @Test
    public void getTimeToLive() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket mss = new MulticastSocket();
        mss.setTimeToLive(120);
        assertEquals("Returned incorrect 1st TTL", 120, mss.getTimeToLive());
        mss.setTimeToLive(220);
        assertEquals("Returned incorrect 2nd TTL", 220, mss.getTimeToLive());
        mss.close();
    }

    @Test
    public void getTTL() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket mss = new MulticastSocket();
        mss.setTTL((byte) 120);
        assertEquals("Returned incorrect TTL", 120, mss.getTTL());
        mss.close();
    }

    @Test
    public void joinGroupLjava_net_InetAddress_IPv4() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        test_joinGroupLjava_net_InetAddress(GOOD_IPv4);
    }

    @Test
    public void joinGroupLjava_net_InetAddress_IPv6() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        test_joinGroupLjava_net_InetAddress(GOOD_IPv6);
    }

    private void test_joinGroupLjava_net_InetAddress(InetAddress group) throws Exception {
        MulticastSocket receivingSocket = createReceivingSocket(0);
        receivingSocket.joinGroup(group);

        String msg = "Hello World";
        MulticastSocket sendingSocket = new MulticastSocket(receivingSocket.getLocalPort());
        InetSocketAddress groupAddress =
                new InetSocketAddress(group, receivingSocket.getLocalPort());
        DatagramPacket sdp = createSendDatagramPacket(groupAddress, msg);
        sendingSocket.send(sdp, (byte) 10 /* ttl */);

        DatagramPacket rdp = createReceiveDatagramPacket();
        receivingSocket.receive(rdp);
        String receivedMessage = extractMessage(rdp);
        assertEquals("Group member did not recv data", msg, receivedMessage);

        sendingSocket.close();
        receivingSocket.close();
    }

    @Test
    public void joinGroup_null_null() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket mss = new MulticastSocket(0);
        try {
            mss.joinGroup(null, null);
            fail();
        } catch (IllegalArgumentException expected) {
        }
        mss.close();
    }

    @Test
    public void joinGroup_non_multicast_address_IPv4() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket mss = new MulticastSocket(0);
        try {
            mss.joinGroup(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 0), null);
            fail();
        } catch (IOException expected) {
        }
        mss.close();
    }

    @Test
    public void joinGroup_non_multicast_address_IPv6() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket mss = new MulticastSocket(0);
        try {
            mss.joinGroup(new InetSocketAddress(InetAddress.getByName("::1"), 0), null);
            fail();
        } catch (IOException expected) {
        }
        mss.close();
    }

    @Test
    public void joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface_IPv4()
            throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface(
                ipv4NetworkInterface, GOOD_IPv4, BAD_IPv4);
    }

    @Test
    public void joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface_IPv6()
            throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface(
                ipv6NetworkInterface, GOOD_IPv6, BAD_IPv6);
    }

    @Test
    public void joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface_IPv4_nullInterface()
            throws Exception {
        check_joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface(null, GOOD_IPv4, BAD_IPv4);
    }

    @Test
    public void joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface_IPv6_nullInterface()
            throws Exception {
        check_joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface(null, GOOD_IPv6, BAD_IPv6);
    }

    private void check_joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface(
            NetworkInterface networkInterface, InetAddress group, InetAddress group2)
            throws Exception {
        // Create the sending socket and specify the interface to use as needed (otherwise use the
        // default).
        MulticastSocket sendingSocket = new MulticastSocket(0);
        if (networkInterface != null) {
            sendingSocket.setNetworkInterface(networkInterface);
        }
        sendingSocket.setTimeToLive(2);

        MulticastSocket receivingSocket = createReceivingSocket(0);
        InetSocketAddress groupAddress =
                new InetSocketAddress(group, receivingSocket.getLocalPort());
        // Join the group. A null network interface is valid and means "use default".
        receivingSocket.joinGroup(groupAddress, networkInterface);

        String msg = "Hello World";
        DatagramPacket sdp = createSendDatagramPacket(groupAddress, msg);
        sendingSocket.send(sdp);

        DatagramPacket rdp = createReceiveDatagramPacket();
        receivingSocket.receive(rdp);
        // Now validate that we received the data as expected.
        assertEquals("Group member did not recv data", msg, extractMessage(rdp));
        receivingSocket.close();
        sendingSocket.close();

        // Create the sending socket and specify the interface to use as needed (otherwise use the
        // default).
        sendingSocket = new MulticastSocket(0);
        if (networkInterface != null) {
            sendingSocket.setNetworkInterface(networkInterface);
        }
        sendingSocket.setTimeToLive(10);

        receivingSocket = createReceivingSocket(0);
        groupAddress = new InetSocketAddress(group, receivingSocket.getLocalPort());
        // Join the group. A null network interface is valid and means "use default".
        receivingSocket.joinGroup(groupAddress, networkInterface);

        msg = "Hello World - Different Group";
        InetSocketAddress group2Address =
                new InetSocketAddress(group2, receivingSocket.getLocalPort());
        sdp = createSendDatagramPacket(group2Address, msg);
        sendingSocket.send(sdp);

        rdp = createReceiveDatagramPacket();
        try {
            receivingSocket.receive(rdp);
            fail("Expected timeout");
        } catch (SocketTimeoutException expected) {
        }

        receivingSocket.close();
        sendingSocket.close();
    }

    @Test
    public void joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        // Check that we can join on specific interfaces and that we only receive if data is
        // received on that interface. This test is only really useful on devices with multiple
        // non-loopback interfaces.

        List<NetworkInterface> realInterfaces = new ArrayList<NetworkInterface>();
        Enumeration<NetworkInterface> theInterfaces = NetworkInterface.getNetworkInterfaces();
        while (theInterfaces.hasMoreElements()) {
            NetworkInterface thisInterface = theInterfaces.nextElement();
            // Skip interfaces that do not support multicast - there's no point in proving
            // they cannot send / receive multicast messages.
            if (willWorkForMulticast(thisInterface)) {
                realInterfaces.add(thisInterface);
            }
        }

        for (NetworkInterface thisInterface : realInterfaces) {
            // Find a suitable group IP and interface to use to sent packets to thisInterface.
            Enumeration<InetAddress> addresses = thisInterface.getInetAddresses();

            NetworkInterface sendingInterface = null;
            InetAddress group = null;
            if (addresses.hasMoreElements()) {
                InetAddress firstAddress = addresses.nextElement();
                if (firstAddress instanceof Inet4Address) {
                    group = GOOD_IPv4;
                    sendingInterface = ipv4NetworkInterface;
                } else {
                    // if this interface only seems to support IPV6 addresses
                    group = GOOD_IPv6;
                    sendingInterface = ipv6NetworkInterface;
                }
            }

            // Create a receivingSocket which is joined to the group and has only asked for packets
            // on thisInterface.
            MulticastSocket receivingSocket = createReceivingSocket(0);
            InetSocketAddress groupAddress =
                    new InetSocketAddress(group, receivingSocket.getLocalPort());
            receivingSocket.joinGroup(groupAddress, thisInterface);

            // Now send out a packet on sendingInterface. We should only see the packet if we send
            // it on thisInterface.
            MulticastSocket sendingSocket = new MulticastSocket(0);
            sendingSocket.setNetworkInterface(sendingInterface);
            String msg = "Hello World - Again " + thisInterface.getName();
            DatagramPacket sdp = createSendDatagramPacket(groupAddress, msg);
            sendingSocket.send(sdp);

            DatagramPacket rdp = createReceiveDatagramPacket();
            try {
                receivingSocket.receive(rdp);

                // If the packet is received....
                assertEquals(thisInterface, sendingInterface);
                assertEquals("Group member did not recv data when bound on specific interface",
                        msg, extractMessage(rdp));
            } catch (SocketTimeoutException e) {
                // If the packet was not received...
                assertTrue(!thisInterface.equals(sendingInterface));
            }

            receivingSocket.close();
            sendingSocket.close();
        }
    }

    @Test
    public void joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface_multiple_joins_IPv4()
            throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface_multiple_joins(
                ipv4NetworkInterface, GOOD_IPv4);
    }

    @Test
    public void joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface_multiple_joins_IPv6()
            throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface_multiple_joins(
                ipv6NetworkInterface, GOOD_IPv6);
    }

    private void check_joinGroupLjava_net_SocketAddressLjava_net_NetworkInterface_multiple_joins(
            NetworkInterface networkInterface, InetAddress group) throws Exception {
        // Validate that we can join the same address on two different interfaces but not on the
        // same interface.
        MulticastSocket mss = new MulticastSocket(0);
        SocketAddress groupSockAddr = new InetSocketAddress(group, mss.getLocalPort());
        mss.joinGroup(groupSockAddr, networkInterface);
        mss.joinGroup(groupSockAddr, loopbackInterface);
        try {
            mss.joinGroup(groupSockAddr, networkInterface);
            fail("Did not get expected exception when joining for second time on same interface");
        } catch (IOException e) {
        }
        mss.close();
    }

    @Test
    public void leaveGroupLjava_net_InetAddress_IPv4() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_leaveGroupLjava_net_InetAddress(GOOD_IPv4);
    }

    @Test
    public void leaveGroupLjava_net_InetAddress_IPv6() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_leaveGroupLjava_net_InetAddress(GOOD_IPv6);
    }

    private void check_leaveGroupLjava_net_InetAddress(InetAddress group) throws Exception {
        String msg = "Hello World";
        MulticastSocket mss = new MulticastSocket(0);
        InetSocketAddress groupAddress = new InetSocketAddress(group, mss.getLocalPort());
        DatagramPacket sdp = createSendDatagramPacket(groupAddress, msg);
        mss.send(sdp, (byte) 10 /* ttl */);
        try {
            // Try to leave a group we didn't join.
            mss.leaveGroup(group);
            fail();
        } catch (IOException expected) {
        }
        mss.close();
    }

    @Test
    public void leaveGroup_null_null() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket mss = new MulticastSocket(0);
        try {
            mss.leaveGroup(null, null);
            fail();
        } catch (IllegalArgumentException expected) {
        }
        mss.close();
    }

    @Test
    public void leaveGroup_non_multicast_address_IPv4() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket mss = new MulticastSocket(0);
        try {
            mss.leaveGroup(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 0), null);
            fail();
        } catch (IOException expected) {
        }
        mss.close();
    }

    @Test
    public void leaveGroup_non_multicast_address_IPv6() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket mss = new MulticastSocket(0);
        try {
            mss.leaveGroup(new InetSocketAddress(InetAddress.getByName("::1"), 0), null);
            fail();
        } catch (IOException expected) {
        }
        mss.close();
    }

    @Test
    public void leaveGroupLjava_net_SocketAddressLjava_net_NetworkInterface_IPv4()
            throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_leaveGroupLjava_net_SocketAddressLjava_net_NetworkInterface(
                ipv4NetworkInterface, GOOD_IPv4, BAD_IPv4);
    }

    @Test
    public void leaveGroupLjava_net_SocketAddressLjava_net_NetworkInterface_IPv6()
            throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_leaveGroupLjava_net_SocketAddressLjava_net_NetworkInterface(
                ipv6NetworkInterface, GOOD_IPv6, BAD_IPv6);
    }

    private void check_leaveGroupLjava_net_SocketAddressLjava_net_NetworkInterface(
            NetworkInterface networkInterface, InetAddress group, InetAddress group2)
            throws Exception {
        SocketAddress groupSockAddr = null;
        SocketAddress groupSockAddr2 = null;

        try (MulticastSocket mss = new MulticastSocket(0)) {
            groupSockAddr = new InetSocketAddress(group, mss.getLocalPort());
            mss.joinGroup(groupSockAddr, null);
            mss.leaveGroup(groupSockAddr, null);
            try {
                mss.leaveGroup(groupSockAddr, null);
                fail("Did not get exception when trying to leave group that was already left");
            } catch (IOException expected) {
            }

            groupSockAddr2 = new InetSocketAddress(group2, mss.getLocalPort());
            mss.joinGroup(groupSockAddr, networkInterface);
            try {
                mss.leaveGroup(groupSockAddr2, networkInterface);
                fail("Did not get exception when trying to leave group that was never joined");
            } catch (IOException expected) {
            }

            mss.leaveGroup(groupSockAddr, networkInterface);

            mss.joinGroup(groupSockAddr, networkInterface);
            try {
                mss.leaveGroup(groupSockAddr, loopbackInterface);
                fail("Did not get exception when trying to leave group on wrong interface " +
                        "joined on [" + networkInterface + "] left on [" + loopbackInterface + "]");
            } catch (IOException expected) {
            }
        }
    }

    @Test
    public void sendLjava_net_DatagramPacketB_IPv4() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_sendLjava_net_DatagramPacketB(GOOD_IPv4);
    }

    @Test
    public void sendLjava_net_DatagramPacketB_IPv6() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_sendLjava_net_DatagramPacketB(GOOD_IPv6);
    }

    private void check_sendLjava_net_DatagramPacketB(InetAddress group) throws Exception {
        String msg = "Hello World";
        MulticastSocket sendingSocket = new MulticastSocket(0);
        MulticastSocket receivingSocket = createReceivingSocket(sendingSocket.getLocalPort());
        receivingSocket.joinGroup(group);

        InetSocketAddress groupAddress = new InetSocketAddress(group, sendingSocket.getLocalPort());
        DatagramPacket sdp = createSendDatagramPacket(groupAddress, msg);
        sendingSocket.send(sdp, (byte) 10 /* ttl */);
        sendingSocket.close();

        DatagramPacket rdp = createReceiveDatagramPacket();
        receivingSocket.receive(rdp);
        String receivedMessage = extractMessage(rdp);
        assertEquals("Failed to send data. Received " + rdp.getLength(), msg, receivedMessage);
        receivingSocket.close();
    }

    @Test
    public void setInterfaceLjava_net_InetAddress() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket mss = new MulticastSocket();
        mss.setInterface(InetAddress.getLocalHost());
        InetAddress theInterface = mss.getInterface();
        // Under IPV6 we are not guaranteed to get the same address back as the address that was
        // set, all we should be guaranteed is that we get an address on the same interface.
        if (theInterface instanceof Inet6Address) {
            assertEquals("Failed to return correct interface IPV6",
                    NetworkInterface.getByInetAddress(mss.getInterface()),
                    NetworkInterface.getByInetAddress(theInterface));
        } else {
            assertTrue("Failed to return correct interface IPV4 got:" + mss.getInterface() +
                    " expected: " + InetAddress.getLocalHost(),
                    mss.getInterface().equals(InetAddress.getLocalHost()));
        }
        mss.close();
    }

    @Test
    public void setInterface_unbound_address_IPv4() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        test_setInterface_unbound_address(GOOD_IPv4);
    }

    @Test
    public void setInterface_unbound_address_IPv6() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        test_setInterface_unbound_address(GOOD_IPv6);
    }

    // Regression test for Harmony-2410.
    private void test_setInterface_unbound_address(InetAddress address) throws Exception {
        MulticastSocket mss = new MulticastSocket();
        try {
            mss.setInterface(address);
            fail();
        } catch (SocketException expected) {
        }
        mss.close();
    }

    @Test
    public void setNetworkInterfaceLjava_net_NetworkInterface_null() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        // Validate that null interface is handled ok.
        MulticastSocket mss = new MulticastSocket();
        try {
            mss.setNetworkInterface(null);
            fail("No socket exception when we set then network interface with NULL");
        } catch (SocketException ex) {
        }
        mss.close();
    }

    @Test
    public void setNetworkInterfaceLjava_net_NetworkInterface_round_trip() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        // Validate that we can get and set the interface.
        MulticastSocket mss = new MulticastSocket();
        mss.setNetworkInterface(ipv4NetworkInterface);
        assertEquals("Interface did not seem to be set by setNeworkInterface",
                ipv4NetworkInterface, mss.getNetworkInterface());
        mss.close();
    }

    @Test
    public void setNetworkInterfaceLjava_net_NetworkInterface_IPv4() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_setNetworkInterfaceLjava_net_NetworkInterface(ipv4NetworkInterface, GOOD_IPv4);
    }

    @Test
    public void setNetworkInterfaceLjava_net_NetworkInterface_IPv6() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_setNetworkInterfaceLjava_net_NetworkInterface(ipv6NetworkInterface, GOOD_IPv6);
    }

    private void check_setNetworkInterfaceLjava_net_NetworkInterface(
            NetworkInterface networkInterface, InetAddress group)
            throws IOException, InterruptedException {
        // Set up the receiving socket and join the group.
        MulticastSocket receivingSocket = createReceivingSocket(0);
        InetSocketAddress groupAddress =
                new InetSocketAddress(group, receivingSocket.getLocalPort());
        receivingSocket.joinGroup(groupAddress, networkInterface);

        // Send the packets on a particular interface. The source address in the
        // received packet should be one of the addresses for the interface set.
        MulticastSocket sendingSocket = new MulticastSocket(0);
        sendingSocket.setNetworkInterface(networkInterface);
        String msg = networkInterface.getName();
        DatagramPacket sdp = createSendDatagramPacket(groupAddress, msg);
        sendingSocket.send(sdp);

        DatagramPacket rdp = createReceiveDatagramPacket();
        receivingSocket.receive(rdp);
        String receivedMessage = extractMessage(rdp);
        assertEquals("Group member did not recv data sent on a specific interface",
                msg, receivedMessage);
        // Stop the server.
        receivingSocket.close();
        sendingSocket.close();
    }

    @Test
    public void setTimeToLiveI() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket mss = new MulticastSocket();
        mss.setTimeToLive(120);
        assertEquals("Returned incorrect 1st TTL", 120, mss.getTimeToLive());
        mss.setTimeToLive(220);
        assertEquals("Returned incorrect 2nd TTL", 220, mss.getTimeToLive());
        mss.close();
    }

    @Test
    public void setTTLB() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket mss = new MulticastSocket();
        mss.setTTL((byte) 120);
        assertEquals("Failed to set TTL", 120, mss.getTTL());
        mss.close();
    }

    @Test
    public void constructorLjava_net_SocketAddress() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket ms = new MulticastSocket((SocketAddress) null);
        assertTrue("should not be bound", !ms.isBound() && !ms.isClosed() && !ms.isConnected());
        ms.bind(null);
        assertTrue("should be bound", ms.isBound() && !ms.isClosed() && !ms.isConnected());
        ms.close();
        assertTrue("should be closed", ms.isClosed());

        ms = new MulticastSocket(0);
        assertTrue("should be bound", ms.isBound() && !ms.isClosed() && !ms.isConnected());
        ms.close();
        assertTrue("should be closed", ms.isClosed());

        ms = new MulticastSocket(0);
        assertTrue("should be bound", ms.isBound() && !ms.isClosed() && !ms.isConnected());
        ms.close();
        assertTrue("should be closed", ms.isClosed());

        try {
            new MulticastSocket(new InetSocketAddress("unresolvedname", 31415));
            fail();
        } catch (IOException expected) {
        }

        // Regression test for Harmony-1162.
        InetSocketAddress addr = new InetSocketAddress("0.0.0.0", 0);
        MulticastSocket s = new MulticastSocket(addr);
        assertTrue(s.getReuseAddress());
        s.close();
    }

    @Test
    public void getLoopbackMode() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket ms = new MulticastSocket(null);
        assertTrue("should not be bound", !ms.isBound() && !ms.isClosed() && !ms.isConnected());
        ms.getLoopbackMode();
        assertTrue("should not be bound", !ms.isBound() && !ms.isClosed() && !ms.isConnected());
        ms.close();
        assertTrue("should be closed", ms.isClosed());
    }

    @Test
    public void setLoopbackModeZ() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        MulticastSocket ms = new MulticastSocket();
        ms.setLoopbackMode(true);
        assertTrue("loopback should be true", ms.getLoopbackMode());
        ms.setLoopbackMode(false);
        assertTrue("loopback should be false", !ms.getLoopbackMode());
        ms.close();
        assertTrue("should be closed", ms.isClosed());
    }

    @Test
    public void setLoopbackModeSendReceive_IPv4() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_setLoopbackModeSendReceive(GOOD_IPv4);
    }

    @Test
    public void setLoopbackModeSendReceive_IPv6() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        check_setLoopbackModeSendReceive(GOOD_IPv6);
    }

    private void check_setLoopbackModeSendReceive(InetAddress group) throws IOException {
        // Test send receive.
        final String message = "Hello, world!";

        MulticastSocket socket = new MulticastSocket(0);
        socket.setLoopbackMode(false); // false indicates doing loop back
        socket.joinGroup(group);

        // Send the datagram.
        InetSocketAddress groupAddress = new InetSocketAddress(group, socket.getLocalPort());
        DatagramPacket sendDatagram = createSendDatagramPacket(groupAddress, message);
        socket.send(sendDatagram);

        // Receive the datagram.
        DatagramPacket recvDatagram = createReceiveDatagramPacket();
        socket.setSoTimeout(5000); // Prevent eternal block in.
        socket.receive(recvDatagram);
        String recvMessage = extractMessage(recvDatagram);
        assertEquals(message, recvMessage);
        socket.close();
    }

    @Test
    public void setReuseAddressZ() throws Exception {
        Assume.assumeTrue(supportsMulticast);
        // Test case were we to set ReuseAddress to false.
        MulticastSocket theSocket1 = new MulticastSocket(null);
        theSocket1.setReuseAddress(false);

        MulticastSocket theSocket2 = new MulticastSocket(null);
        theSocket2.setReuseAddress(false);

        InetSocketAddress addr = new InetSocketAddress(Inet4Address.getLocalHost(), 0);
        theSocket1.bind(addr);
        addr = new InetSocketAddress(Inet4Address.getLocalHost(), theSocket1.getLocalPort());
        try {
            theSocket2.bind(addr);
            fail("No exception when trying to connect to do duplicate socket bind with re-useaddr"
                    + " set to false");
        } catch (BindException expected) {
        }
        theSocket1.close();
        theSocket2.close();

        // Test case were we set it to true.
        theSocket1 = new MulticastSocket(null);
        theSocket2 = new MulticastSocket(null);
        theSocket1.setReuseAddress(true);
        theSocket2.setReuseAddress(true);
        addr = new InetSocketAddress(Inet4Address.getLocalHost(), 0);
        theSocket1.bind(addr);
        addr = new InetSocketAddress(Inet4Address.getLocalHost(), theSocket1.getLocalPort());
        theSocket2.bind(addr);

        theSocket1.close();
        theSocket2.close();

        // Test the default case which we expect to be the same on all platforms.
        theSocket1 = new MulticastSocket(null);
        theSocket2 = new MulticastSocket(null);
        addr = new InetSocketAddress(Inet4Address.getLocalHost(), 0);
        theSocket1.bind(addr);
        addr = new InetSocketAddress(Inet4Address.getLocalHost(), theSocket1.getLocalPort());
        theSocket2.bind(addr);
        theSocket1.close();
        theSocket2.close();
    }

    private static boolean willWorkForMulticast(NetworkInterface iface) throws IOException {
        return iface.isUp()
                // Typically loopback interfaces do not support multicast, but we rule them out
                // explicitly anyway.
                && !iface.isLoopback()
                // Point-to-point interfaces are known to cause problems. http://b/23279677
                && !iface.isPointToPoint()
                && iface.supportsMulticast()
                && iface.getInetAddresses().hasMoreElements();
    }

    private static MulticastSocket createReceivingSocket(int aPort) throws IOException {
        MulticastSocket ms = new MulticastSocket(aPort);
        ms.setSoTimeout(2000);
        return ms;
    }

    private static DatagramPacket createReceiveDatagramPacket() {
        byte[] rbuf = new byte[512];
        return new DatagramPacket(rbuf, rbuf.length);
    }

    private static DatagramPacket createSendDatagramPacket(
            InetSocketAddress groupAndPort, String msg) {
        return new DatagramPacket(
                msg.getBytes(), msg.length(), groupAndPort.getAddress(), groupAndPort.getPort());
    }

    private static String extractMessage(DatagramPacket rdp) {
        return new String(rdp.getData(), 0, rdp.getLength());
    }
}
