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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import tests.support.Support_Configuration;

public class DatagramPacketTest extends junit.framework.TestCase {

    volatile boolean started = false;

    /**
     * java.net.DatagramPacket#DatagramPacket(byte[], int)
     */
    public void test_Constructor$BI() {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 5);
        assertEquals("Created incorrect packet", "Hello", new String(dp
                .getData(), 0, dp.getData().length));
        assertEquals("Wrong length", 5, dp.getLength());

        // Regression for HARMONY-890
        dp = new DatagramPacket(new byte[942], 4);
        assertEquals(-1, dp.getPort());
        try {
            dp.getSocketAddress();
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * java.net.DatagramPacket#DatagramPacket(byte[], int, int)
     */
    public void test_Constructor$BII() {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 2, 3);
        assertEquals("Created incorrect packet", "Hello", new String(dp
                .getData(), 0, dp.getData().length));
        assertEquals("Wrong length", 3, dp.getLength());
        assertEquals("Wrong offset", 2, dp.getOffset());
    }

    /**
     * java.net.DatagramPacket#DatagramPacket(byte[], int, int,
     *java.net.InetAddress, int)
     */
    public void test_Constructor$BIILjava_net_InetAddressI() throws IOException {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 2, 3,
                InetAddress.getLocalHost(), 0);
        assertEquals("Wrong host", InetAddress.getLocalHost(), dp.getAddress());
        assertEquals("Wrong port", 0, dp.getPort());
        assertEquals("Wrong length", 3, dp.getLength());
        assertEquals("Wrong offset", 2, dp.getOffset());
    }

    /**
     * java.net.DatagramPacket#DatagramPacket(byte[], int,
     *java.net.InetAddress, int)
     */
    public void test_Constructor$BILjava_net_InetAddressI() throws IOException {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 5,
                InetAddress.getLocalHost(), 0);
        assertEquals("Wrong address", InetAddress.getLocalHost(), dp
                .getAddress());
        assertEquals("Wrong port", 0, dp.getPort());
        assertEquals("Wrong length", 5, dp.getLength());
    }

    /**
     * java.net.DatagramPacket#getAddress()
     */
    public void test_getAddress() throws IOException {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 5,
                InetAddress.getLocalHost(), 0);
        assertEquals("Incorrect address returned", InetAddress.getLocalHost(),
                dp.getAddress());
    }

    /**
     * java.net.DatagramPacket#getData()
     */
    public void test_getData() {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 5);
        assertEquals("Incorrect length returned", "Hello", new String(dp
                .getData(), 0, dp.getData().length));
    }

    /**
     * java.net.DatagramPacket#getLength()
     */
    public void test_getLength() {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 5);
        assertEquals("Incorrect length returned", 5, dp.getLength());
    }

    /**
     * java.net.DatagramPacket#getOffset()
     */
    public void test_getOffset() {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 3, 2);
        assertEquals("Incorrect length returned", 3, dp.getOffset());
    }

    /**
     * java.net.DatagramPacket#getPort()
     */
    public void test_getPort() throws IOException {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 5,
                InetAddress.getLocalHost(), 1000);
        assertEquals("Incorrect port returned", 1000, dp.getPort());

        final InetAddress localhost = InetAddress.getLocalHost();
        DatagramSocket socket = new DatagramSocket(0, localhost);
        final int port = socket.getLocalPort();

        socket.setSoTimeout(3000);
        DatagramPacket packet = new DatagramPacket(new byte[] { 1, 2, 3, 4, 5,
                6 }, 6, localhost, port);
        socket.send(packet);
        socket.receive(packet);
        socket.close();
        assertTrue("datagram received wrong port: " + packet.getPort(), packet
                .getPort() == port);
    }

    /**
     * java.net.DatagramPacket#setAddress(java.net.InetAddress)
     */
    public void test_setAddressLjava_net_InetAddress() throws IOException {
        InetAddress ia = InetAddress.getByName("127.0.0.1");
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 5,
                InetAddress.getLocalHost(), 0);
        dp.setAddress(ia);
        assertEquals("Incorrect address returned", ia, dp.getAddress());
    }

    /**
     * java.net.DatagramPacket#setData(byte[], int, int)
     */
    public void test_setData$BII() {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 5);
        dp.setData("Wagga Wagga".getBytes(), 2, 3);
        assertEquals("Incorrect data set", "Wagga Wagga", new String(dp
                .getData()));
    }

    /**
     * java.net.DatagramPacket#setData(byte[])
     */
    public void test_setData$B() {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 5);
        dp.setData("Ralph".getBytes());
        assertEquals("Incorrect data set", "Ralph", new String(dp.getData(), 0,
                dp.getData().length));
    }

    /**
     * java.net.DatagramPacket#setLength(int)
     */
    public void test_setLengthI() {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 5);
        dp.setLength(1);
        assertEquals("Failed to set packet length", 1, dp.getLength());
    }

    /**
     * java.net.DatagramPacket#setPort(int)
     */
    public void test_setPortI() throws Exception {
        DatagramPacket dp = new DatagramPacket("Hello".getBytes(), 5,
                InetAddress.getLocalHost(), 1000);
        dp.setPort(2000);
        assertEquals("Port not set", 2000, dp.getPort());
    }

    /**
     * java.net.DatagramPacket#DatagramPacket(byte[], int,
     *java.net.SocketAddress)
     */
    public void test_Constructor$BILjava_net_SocketAddress() throws IOException {
        @SuppressWarnings("serial")
        class UnsupportedSocketAddress extends SocketAddress {

            public UnsupportedSocketAddress() {
            }
        }

        // Unsupported SocketAddress subclass
        byte buf[] = new byte[1];
        try {
            new DatagramPacket(buf, 1, new UnsupportedSocketAddress());
            fail("No exception when constructing using unsupported SocketAddress subclass");
        } catch (IllegalArgumentException ex) {
            // Expected
        }

        // Case were we try to pass in null
        try {
            new DatagramPacket(buf, 1, null);
            fail("No exception when constructing address using null");
        } catch (IllegalArgumentException ex) {
            // Expected
        }

        // Now validate we can construct
        InetSocketAddress theAddress = new InetSocketAddress(InetAddress
                .getLocalHost(), 2067);
        DatagramPacket thePacket = new DatagramPacket(buf, 1, theAddress);
        assertEquals("Socket address not set correctly (1)", theAddress,
                thePacket.getSocketAddress());
        assertEquals("Socket address not set correctly (2)", theAddress,
                new InetSocketAddress(thePacket.getAddress(), thePacket
                        .getPort()));
    }

    /**
     * java.net.DatagramPacket#DatagramPacket(byte[], int, int,
     *java.net.SocketAddress)
     */
    public void test_Constructor$BIILjava_net_SocketAddress()
            throws IOException {
        @SuppressWarnings("serial")
        class UnsupportedSocketAddress extends SocketAddress {

            public UnsupportedSocketAddress() {
            }
        }

        // Unsupported SocketAddress subclass
        byte buf[] = new byte[2];
        try {
            new DatagramPacket(buf, 1, 1, new UnsupportedSocketAddress());
            fail("No exception when constructing using unsupported SocketAddress subclass");
        } catch (IllegalArgumentException ex) {
            // Expected
        }

        // Case were we try to pass in null
        try {
            new DatagramPacket(buf, 1, 1, null);
            fail("No exception when constructing address using null");
        } catch (IllegalArgumentException ex) {
            // Expected
        }

        // now validate we can construct
        InetSocketAddress theAddress = new InetSocketAddress(InetAddress
                .getLocalHost(), 2067);
        DatagramPacket thePacket = new DatagramPacket(buf, 1, 1, theAddress);
        assertEquals("Socket address not set correctly (1)", theAddress,
                thePacket.getSocketAddress());
        assertEquals("Socket address not set correctly (2)", theAddress,
                new InetSocketAddress(thePacket.getAddress(), thePacket
                        .getPort()));
        assertEquals("Offset not set correctly", 1, thePacket.getOffset());
    }

    /**
     * java.net.DatagramPacket#getSocketAddress()
     */
    public void test_getSocketAddress() throws IOException {
        byte buf[] = new byte[1];
        DatagramPacket thePacket = new DatagramPacket(buf, 1);

        // Validate get returns the value we set
        InetSocketAddress theAddress = new InetSocketAddress(InetAddress
                .getLocalHost(), 0);
        thePacket = new DatagramPacket(buf, 1);
        thePacket.setSocketAddress(theAddress);
        assertEquals("Socket address not set correctly (1)", theAddress,
                thePacket.getSocketAddress());
    }

    /**
     * java.net.DatagramPacket#setSocketAddress(java.net.SocketAddress)
     */
    public void test_setSocketAddressLjava_net_SocketAddress()
            throws IOException {

        @SuppressWarnings("serial")
        class UnsupportedSocketAddress extends SocketAddress {

            public UnsupportedSocketAddress() {
            }
        }

        // Unsupported SocketAddress subclass
        byte buf[] = new byte[1];
        DatagramPacket thePacket = new DatagramPacket(buf, 1);
        try {
            thePacket.setSocketAddress(new UnsupportedSocketAddress());
            fail("No exception when setting address using unsupported SocketAddress subclass");
        } catch (IllegalArgumentException ex) {
            // Expected
        }

        // Case were we try to pass in null
        thePacket = new DatagramPacket(buf, 1);
        try {
            thePacket.setSocketAddress(null);
            fail("No exception when setting address using null");
        } catch (IllegalArgumentException ex) {
            // Expected
        }

        // Now validate we can set it correctly
        InetSocketAddress theAddress = new InetSocketAddress(InetAddress
                .getLocalHost(), 2049);
        thePacket = new DatagramPacket(buf, 1);
        thePacket.setSocketAddress(theAddress);
        assertEquals("Socket address not set correctly (1)", theAddress,
                thePacket.getSocketAddress());
        assertEquals("Socket address not set correctly (2)", theAddress,
                new InetSocketAddress(thePacket.getAddress(), thePacket
                        .getPort()));
    }
}
