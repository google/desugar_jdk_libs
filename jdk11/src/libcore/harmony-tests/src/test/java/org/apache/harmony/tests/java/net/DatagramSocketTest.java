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
import java.io.InterruptedIOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.DatagramSocketImpl;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import libcore.io.Libcore;
import libcore.junit.junit3.TestCaseWithRules;
import libcore.junit.util.ResourceLeakageDetector;
import org.junit.Rule;
import org.junit.rules.TestRule;

import static android.system.OsConstants.IPPROTO_IP;
import static android.system.OsConstants.IP_MULTICAST_ALL;

public class DatagramSocketTest extends TestCaseWithRules {
    @Rule
    public TestRule guardRule = ResourceLeakageDetector.getRule();

    static final class DatagramServer extends Thread implements AutoCloseable {

        volatile boolean running = true;

        private final boolean echo;
        private final byte[] rbuf;
        private final DatagramPacket rdp;
        final DatagramSocket serverSocket;

        public DatagramServer(InetAddress address, boolean echo)
                throws IOException {
            this.echo = echo;
            rbuf = new byte[512];
            rbuf[0] = -1;
            rdp = new DatagramPacket(rbuf, rbuf.length);
            serverSocket = new DatagramSocket(0, address);
            serverSocket.setSoTimeout(2000);
        }

        public DatagramServer(InetAddress address) throws IOException {
            this(address, true /* echo */);
        }

        public void run() {
            try {
                while (running) {
                    try {
                        serverSocket.receive(rdp);
                        if (echo) {
                            serverSocket.send(rdp);
                        }
                    } catch (InterruptedIOException e) {
                    }
                }
            } catch (IOException e) {
                fail();
            }
        }

        public int getPort() {
            return serverSocket.getLocalPort();
        }

        @Override
        public void close() throws Exception {
            running = false;
            try {
                join();
            } finally {
                serverSocket.close();
            }
        }
    }

    /**
     * java.net.DatagramSocket#DatagramSocket()
     */
    public void test_Constructor() throws Exception {
        try (DatagramSocket ds = new DatagramSocket()) {
            // Datagram sockets bound to the wildcard INADDR_ANY address should by default only
            // receive messages from groups they explicitly joined.
            boolean multicastAllEnabled = Libcore.os.getsockoptInt(ds.getFileDescriptor$(),
                    IPPROTO_IP, IP_MULTICAST_ALL) == 1;
            assertFalse(multicastAllEnabled);
        }
    }

    /**
     * java.net.DatagramSocket#DatagramSocket(int)
     */
    public void test_ConstructorI() throws SocketException {
        DatagramSocket ds = new DatagramSocket(0);
        ds.close();
    }

    /**
     * java.net.DatagramSocket#DatagramSocket(int, java.net.InetAddress)
     */
    public void test_ConstructorILjava_net_InetAddress() throws IOException {
        try (DatagramSocket ds = new DatagramSocket(0, InetAddress.getLocalHost())) {
            assertTrue("Created socket with incorrect port", ds.getLocalPort() != 0);
            assertEquals("Created socket with incorrect address", InetAddress
                    .getLocalHost(), ds.getLocalAddress());
        }
    }

    /**
     * java.net.DatagramSocket#close()
     */
    public void test_close() throws UnknownHostException, SocketException {
        DatagramSocket ds = new DatagramSocket(0);
        DatagramPacket dp = new DatagramPacket("Test String".getBytes(), 11,
                InetAddress.getLocalHost(), 0);
        ds.close();
        try {
            ds.send(dp);
            fail("Data sent after close");
        } catch (IOException e) {
            // Expected
        }
    }

    public void test_connectLjava_net_InetAddressI() throws Exception {
        try (DatagramSocket ds = new DatagramSocket()) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ds.connect(inetAddress, 0);
            assertEquals("Incorrect InetAddress", inetAddress, ds.getInetAddress());
            assertEquals("Incorrect Port", 0, ds.getPort());
            ds.disconnect();
        }

        try (DatagramSocket ds = new DatagramSocket()) {
            InetAddress inetAddress =
                    InetAddress.getByName("FE80:0000:0000:0000:020D:60FF:FE0F:A776%4");
            ds.connect(inetAddress, 0);
            assertEquals(inetAddress, ds.getInetAddress());
            ds.disconnect();
        }
    }

    public void testConnect_connectToSelf() throws Exception {
        // Create a connected datagram socket to test
        // PlainDatagramSocketImpl.peek()
        InetAddress localHost = InetAddress.getLocalHost();
        final DatagramSocket ds = new DatagramSocket(0);
        ds.connect(localHost, ds.getLocalPort());
        DatagramPacket send = new DatagramPacket(new byte[10], 10, localHost,
                ds.getLocalPort());
        ds.send(send);

        DatagramPacket receive = new DatagramPacket(new byte[20], 20);
        ds.setSoTimeout(2000);
        ds.receive(receive);
        ds.close();

        assertEquals(10, receive.getLength());
        assertEquals(localHost, receive.getAddress());
    }

    private static void assertPacketDataEquals(DatagramPacket p1, DatagramPacket p2)
            throws Exception {
        assertEquals(p1.getLength(), p2.getLength());
        final byte[] p1Bytes = p1.getData();
        final byte[] p2Bytes = p2.getData();

        for (int i = 0; i < p1.getLength(); ++i) {
            if (p1Bytes[p1.getOffset() + i] != p2Bytes[p2.getOffset() + i]) {
                String expected = new String(p1Bytes, p1.getOffset(), p1.getLength(),
                        "UTF-8");
                String actual = new String(p2Bytes, p2.getOffset(), p2.getLength(),
                        "UTF-8");
                fail("expected: " + expected + ", actual: " + actual);
            }
        }
    }

    public void testConnect_echoServer() throws Exception {
        try (DatagramSocket ds = new DatagramSocket(0);
             DatagramServer server = new DatagramServer(Inet6Address.LOOPBACK)) {
            server.start();

            ds.connect(Inet6Address.LOOPBACK, server.getPort());

            final byte[] sendBytes = { 'T', 'e', 's', 't', 0 };
            final DatagramPacket send = new DatagramPacket(sendBytes, sendBytes.length);
            final DatagramPacket receive = new DatagramPacket(new byte[20], 20);

            ds.send(send);
            ds.setSoTimeout(2000);
            ds.receive(receive);

            assertEquals(sendBytes.length, receive.getLength());
            assertPacketDataEquals(send, receive);
            assertEquals(Inet6Address.LOOPBACK, receive.getAddress());
        }
    }

    // Validate that once connected we cannot send to another address.
    public void testConnect_throwsOnAddressMismatch() throws Exception {
        try (DatagramSocket ds = new DatagramSocket(0);
             DatagramServer s1 = new DatagramServer(Inet6Address.LOOPBACK);
             DatagramServer s2 = new DatagramServer(Inet6Address.LOOPBACK)) {

            ds.connect(Inet6Address.LOOPBACK, s1.getPort());
            try {
                ds.send(new DatagramPacket(new byte[10], 10, Inet6Address.LOOPBACK, s2.getPort()));
                fail();
            } catch (IllegalArgumentException expected) {
            }
        }
    }

    // Validate that we can connect, then disconnect, then connect then
    // send/recv.
    public void testConnect_connectDisconnectConnectThenSendRecv() throws Exception {
        try (DatagramSocket ds = new DatagramSocket(0);
             DatagramServer server = new DatagramServer(Inet6Address.LOOPBACK);
             DatagramServer broken = new DatagramServer(Inet6Address.LOOPBACK, false)) {
            server.start();
            broken.start();

            final int serverPortNumber = server.getPort();
            ds.connect(Inet6Address.LOOPBACK, broken.getPort());
            ds.disconnect();
            ds.connect(Inet6Address.LOOPBACK, serverPortNumber);

            final byte[] sendBytes = { 'T', 'e', 's', 't', 0 };
            final DatagramPacket send = new DatagramPacket(sendBytes, sendBytes.length);
            final DatagramPacket receive = new DatagramPacket(new byte[20], 20);
            ds.send(send);
            ds.setSoTimeout(2000);
            ds.receive(receive);

            assertPacketDataEquals(send, receive);
            assertEquals(Inet6Address.LOOPBACK, receive.getAddress());
        }
    }

    // Validate that we can connect/disconnect then send/recv to any address
    public void testConnect_connectDisconnectThenSendRecv() throws Exception {
        try (DatagramSocket ds = new DatagramSocket(0);
             DatagramServer server = new DatagramServer(Inet6Address.LOOPBACK)) {
            server.start();

            final int serverPortNumber = server.getPort();
            ds.connect(Inet6Address.LOOPBACK, serverPortNumber);
            ds.disconnect();

            final byte[] sendBytes = { 'T', 'e', 's', 't', 0 };
            final DatagramPacket send = new DatagramPacket(sendBytes, sendBytes.length,
                    Inet6Address.LOOPBACK, serverPortNumber);
            final DatagramPacket receive = new DatagramPacket(new byte[20], 20);
            ds.send(send);
            ds.setSoTimeout(2000);
            ds.receive(receive);

            assertPacketDataEquals(send, receive);
            assertEquals(Inet6Address.LOOPBACK, receive.getAddress());
        }
    }

    public void testConnect_connectTwice() throws Exception {
        try (DatagramSocket ds = new DatagramSocket(0);
             DatagramServer server = new DatagramServer(Inet6Address.LOOPBACK);
             DatagramServer broken = new DatagramServer(Inet6Address.LOOPBACK)) {
            server.start();
            broken.start();

            final int serverPortNumber = server.getPort();
            ds.connect(Inet6Address.LOOPBACK, broken.getPort());
            ds.connect(Inet6Address.LOOPBACK, serverPortNumber);
            ds.disconnect();

            final byte[] sendBytes = { 'T', 'e', 's', 't', 0 };
            final DatagramPacket send = new DatagramPacket(sendBytes, sendBytes.length,
                    Inet6Address.LOOPBACK, serverPortNumber);
            final DatagramPacket receive = new DatagramPacket(new byte[20], 20);
            ds.send(send);
            ds.setSoTimeout(2000);
            ds.receive(receive);

            assertPacketDataEquals(send, receive);
            assertEquals(Inet6Address.LOOPBACK, receive.getAddress());
        }
    }

    public void testConnect_zeroAddress() throws Exception {
        try (DatagramSocket ds = new DatagramSocket()) {
            byte[] addressBytes = { 0, 0, 0, 0 };
            InetAddress inetAddress = InetAddress.getByAddress(addressBytes);
            ds.connect(inetAddress, 0);
        }

        try (DatagramSocket ds = new DatagramSocket()) {
            byte[] addressTestBytes = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0 };
            InetAddress inetAddress = InetAddress.getByAddress(addressTestBytes);
            ds.connect(inetAddress, 0);
        }
    }

    public void test_disconnect() throws Exception {
        try (DatagramSocket ds = new DatagramSocket()) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ds.connect(inetAddress, 0);
            ds.disconnect();
            assertNull("Incorrect InetAddress", ds.getInetAddress());
            assertEquals("Incorrect Port", -1, ds.getPort());
        }

        try (DatagramSocket ds = new DatagramSocket()) {
            InetAddress inetAddress =
                    InetAddress.getByName("FE80:0000:0000:0000:020D:60FF:FE0F:A776%4");
            ds.connect(inetAddress, 0);
            ds.disconnect();
            assertNull("Incorrect InetAddress", ds.getInetAddress());
            assertEquals("Incorrect Port", -1, ds.getPort());
        }
    }

    public void test_getLocalAddress() throws Exception {
        // Test for method java.net.InetAddress
        // java.net.DatagramSocket.getLocalAddress()
        InetAddress local = InetAddress.getLocalHost();
        try (DatagramSocket ds = new DatagramSocket(0, local)) {
            assertEquals(InetAddress.getByName(InetAddress.getLocalHost().getHostName()),
                    ds.getLocalAddress());
        }

        // now check behavior when the ANY address is returned
        try (DatagramSocket s = new DatagramSocket(0)) {
            assertTrue("ANY address not IPv6: " + s.getLocalSocketAddress(),
                    s.getLocalAddress() instanceof Inet6Address);
        }
    }

    public void test_getLocalPort() throws SocketException {
        try (DatagramSocket ds = new DatagramSocket()) {
            assertTrue("Returned incorrect port", ds.getLocalPort() != 0);
        }
    }

    public void test_getPort() throws IOException {
        try (DatagramSocket theSocket = new DatagramSocket()) {
            assertEquals("Expected -1 for remote port as not connected", -1,
                    theSocket.getPort());

            // Now connect the socket and validate that we get the right port
            int portNumber = 49152; // any valid port, even if it is unreachable
            theSocket.connect(InetAddress.getLocalHost(), portNumber);
            assertEquals("getPort returned wrong value", portNumber, theSocket
                    .getPort());
        }
    }

    public void test_getReceiveBufferSize() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        ds.setReceiveBufferSize(130);
        assertTrue("Incorrect buffer size", ds.getReceiveBufferSize() >= 130);
        ds.close();
        try {
            ds.getReceiveBufferSize();
            fail("SocketException was not thrown.");
        } catch(SocketException se) {
            //expected
        }
    }

    public void test_getSendBufferSize() throws Exception {
        final DatagramSocket ds = new java.net.DatagramSocket(0);
        ds.setSendBufferSize(134);
        assertTrue("Incorrect buffer size", ds.getSendBufferSize() >= 134);
        ds.close();
        try {
            ds.getSendBufferSize();
            fail("SocketException was not thrown.");
        } catch(SocketException se) {
            //expected
        }
    }

    public void test_getSoTimeout() throws Exception {
        try (DatagramSocket ds = new DatagramSocket()) {
            final int timeoutSet = 100;
            ds.setSoTimeout(timeoutSet);
            int actualTimeout = ds.getSoTimeout();
            // The kernel can round the requested value based on the HZ setting. We allow up to
            // 10ms.
            assertTrue("Returned incorrect timeout",
                    Math.abs(actualTimeout - timeoutSet) <= 10);
        }
    }

    static final class TestDatagramSocketImpl extends DatagramSocketImpl {
        // This field exists solely to force initialization of this class
        // inside a test method.
        public static final Object ACCESS = new Object();

        @Override
        protected void create() throws SocketException {
        }

        @Override
        protected void bind(int arg0, InetAddress arg1)
                throws SocketException {
        }

        @Override
        protected void send(DatagramPacket arg0) throws IOException {
        }

        @Override
        protected int peek(InetAddress arg0) throws IOException {
            return 0;
        }

        @Override
        protected int peekData(DatagramPacket arg0) throws IOException {
            return 0;
        }

        @Override
        protected void receive(DatagramPacket arg0) throws IOException {
        }

        @Override
        protected void setTTL(byte arg0) throws IOException {
        }

        @Override
        protected byte getTTL() throws IOException {
            return 0;
        }

        @Override
        protected void setTimeToLive(int arg0) throws IOException {
        }

        @Override
        protected int getTimeToLive() throws IOException {
            return 0;
        }

        @Override
        protected void join(InetAddress arg0) throws IOException {
        }

        @Override
        protected void joinGroup(SocketAddress addr, NetworkInterface netInterface) throws IOException {

        }

        @Override
        protected void leave(InetAddress arg0) throws IOException {
        }

        @Override
        protected void leaveGroup(SocketAddress arg0, NetworkInterface arg1)
                throws IOException {
        }

        @Override
        protected void close() {
        }

        public void setOption(int arg0, Object arg1) throws SocketException {
        }

        public Object getOption(int arg0) throws SocketException {
            return null;
        }
    }

    static final class TestDatagramSocket extends DatagramSocket {
        // This field exists solely to force initialization of this class
        // inside a test method.
        public static final Object ACCESS = new Object();

        public TestDatagramSocket(DatagramSocketImpl impl) {
            super(impl);
        }
    }


    public void testArchivedHarmonyRegressions() throws Exception {
        // Regression for HARMONY-1118
        assertNotNull(TestDatagramSocketImpl.ACCESS);
        assertNotNull(TestDatagramSocket.ACCESS);

        // Regression test for Harmony-2938
        InetAddress i = InetAddress.getByName("127.0.0.1");
        DatagramSocket d = new DatagramSocket(0, i);
        try {
            d.send(new DatagramPacket(new byte[] { 1 }, 1));
            fail();
        } catch (NullPointerException expected) {
        } finally {
            d.close();
        }

        // Regression test for Harmony-6413
        InetSocketAddress addr = InetSocketAddress.createUnresolved(
                "localhost", 0);
        try {
            new DatagramPacket(new byte[272], 3, addr);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void test_sendLjava_net_DatagramPacket_nullDestination() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(0);
        byte[] data = { 65 };
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, null, 25000);
        try {
            datagramSocket.send(sendPacket);
            fail();
        } catch (NullPointerException expected) {
            // Expected
        } finally {
            datagramSocket.close();
        }

    }

    public void test_setSendBufferSizeI() throws Exception {
        final DatagramSocket ds = new DatagramSocket(0);
        ds.setSendBufferSize(134);
        assertTrue("Incorrect buffer size", ds.getSendBufferSize() >= 134);
        ds.close();
        try {
            ds.setSendBufferSize(1);
            fail("SocketException was not thrown.");
        } catch(SocketException se) {
            //expected
        }
    }

    public void test_setReceiveBufferSizeI() throws Exception {
        final DatagramSocket ds = new DatagramSocket(0);
        ds.setReceiveBufferSize(130);
        assertTrue("Incorrect buffer size", ds.getReceiveBufferSize() >= 130);

        try {
            ds.setReceiveBufferSize(0);
            fail("IllegalArgumentException was not thrown.");
        } catch(IllegalArgumentException iae) {
            //expected
        }

        try {
            ds.setReceiveBufferSize(-1);
            fail("IllegalArgumentException was not thrown.");
        } catch(IllegalArgumentException iae) {
            //expected
        }

        ds.close();

        try {
            ds.setReceiveBufferSize(1);
            fail("SocketException was not thrown.");
        } catch (SocketException e) {
            //expected
        }
    }


    public void test_ConstructorLjava_net_DatagramSocketImpl() {
        class SimpleTestDatagramSocket extends DatagramSocket {
            public SimpleTestDatagramSocket(DatagramSocketImpl impl) {
                super(impl);
            }
        }

        try {
            new SimpleTestDatagramSocket(null);
            fail("exception expected");
        } catch (NullPointerException ex) {
            // expected
        }
    }

    public void test_ConstructorLjava_net_SocketAddress() throws Exception {
        class UnsupportedSocketAddress extends SocketAddress {
            public UnsupportedSocketAddress() {
            }
        }

        try (DatagramSocket ds = new DatagramSocket(
                new InetSocketAddress(InetAddress.getLocalHost(), 0))) {
            assertTrue(ds.getBroadcast());
            assertTrue("Created socket with incorrect port", ds.getLocalPort() != 0);
            assertEquals("Created socket with incorrect address", InetAddress
                    .getLocalHost(), ds.getLocalAddress());
        }

        try {
            new DatagramSocket(new UnsupportedSocketAddress());
            fail("No exception when constructing datagramSocket with unsupported SocketAddress type");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // regression for HARMONY-894
        try (DatagramSocket ds = new DatagramSocket(null)) {
            assertTrue(ds.getBroadcast());
        }
    }


    public void test_bindLjava_net_SocketAddress_null() throws Exception {
        // validate if we pass in null that it picks an address for us.
        DatagramSocket theSocket = new DatagramSocket((SocketAddress) null);
        theSocket.bind(null);
        assertNotNull(theSocket.getLocalSocketAddress());
        theSocket.close();
    }

    public void test_bindLjava_net_SocketAddress_address_in_use() throws Exception {
        DatagramSocket socket1 = new DatagramSocket(0);
        try {
            new DatagramSocket(socket1.getLocalPort());
            fail();
        } catch (SocketException expected) {
        }
        socket1.close();
    }

    public void test_bindLjava_net_SocketAddress_unsupported_address_type() throws Exception {
        class mySocketAddress extends SocketAddress {
            public mySocketAddress() {
            }
        }

        // unsupported SocketAddress subclass
        DatagramSocket theSocket = new DatagramSocket((SocketAddress) null);
        try {
            theSocket.bind(new mySocketAddress());
            fail("No exception when binding using unsupported SocketAddress subclass");
        } catch (IllegalArgumentException expected) {
        }
        theSocket.close();
    }

    public void test_isBound() throws Exception {
        DatagramSocket theSocket = new DatagramSocket(0);
        assertTrue(theSocket.isBound());
        theSocket.close();

        theSocket = new DatagramSocket(new InetSocketAddress(Inet6Address.LOOPBACK, 0));
        assertTrue(theSocket.isBound());
        theSocket.close();

        theSocket = new DatagramSocket(null);
        assertFalse(theSocket.isBound());
        theSocket.close();

        // connect causes implicit bind
        theSocket = new DatagramSocket(null);
        theSocket.connect(new InetSocketAddress(Inet6Address.LOOPBACK, 0));
        assertTrue(theSocket.isBound());
        theSocket.close();

        // now test when we bind explicitely
        InetSocketAddress theLocalAddress = new InetSocketAddress(Inet6Address.LOOPBACK, 0);
        theSocket = new DatagramSocket(null);
        assertFalse(theSocket.isBound());
        theSocket.bind(theLocalAddress);
        assertTrue(theSocket.isBound());
        theSocket.close();
        assertTrue(theSocket.isBound());
    }

    public void test_isConnected() throws Exception {
        try (DatagramServer ds = new DatagramServer(Inet6Address.LOOPBACK)) {

            // base test
            try (DatagramSocket theSocket = new DatagramSocket(0)) {
                assertFalse(theSocket.isConnected());
                theSocket.connect(new InetSocketAddress(Inet6Address.LOOPBACK, ds.getPort()));
                assertTrue(theSocket.isConnected());

                // reconnect the socket and make sure we get the right answer
                theSocket.connect(new InetSocketAddress(Inet6Address.LOOPBACK, ds.getPort()));
                assertTrue(theSocket.isConnected());

                // now disconnect the socket and make sure we get the right answer
                theSocket.disconnect();
                assertFalse(theSocket.isConnected());
            }

            // now check behavior when socket is closed when connected
            DatagramSocket theSocket = new DatagramSocket(0);
            theSocket.connect(new InetSocketAddress(Inet6Address.LOOPBACK, ds.getPort()));
            theSocket.close();
            assertTrue(theSocket.isConnected());
        }
    }

    public void test_getRemoteSocketAddress() throws Exception {
        try (DatagramServer server = new DatagramServer(Inet6Address.LOOPBACK)) {
            try (DatagramSocket s = new DatagramSocket(0)) {
                s.connect(new InetSocketAddress(Inet6Address.LOOPBACK, server.getPort()));

                assertEquals(new InetSocketAddress(Inet6Address.LOOPBACK, server.getPort()),
                        s.getRemoteSocketAddress());
            }

            // now create one that is not connected and validate that we get the
            // right answer
            try (DatagramSocket theSocket = new DatagramSocket(null)) {
                theSocket.bind(new InetSocketAddress(Inet6Address.LOOPBACK, 0));
                assertNull(theSocket.getRemoteSocketAddress());

                // now connect and validate we get the right answer
                theSocket.connect(new InetSocketAddress(Inet6Address.LOOPBACK, server.getPort()));
                assertEquals(new InetSocketAddress(Inet6Address.LOOPBACK, server.getPort()),
                        theSocket.getRemoteSocketAddress());
            }
        }
    }

    public void test_getLocalSocketAddress_late_bind() throws Exception {
        // An unbound socket should return null as its local address.
        DatagramSocket theSocket = new DatagramSocket((SocketAddress) null);
        assertNull(theSocket.getLocalSocketAddress());

        // now bind the socket and make sure we get the right answer
        InetSocketAddress localAddress = new InetSocketAddress(InetAddress.getLocalHost(), 0);
        theSocket.bind(localAddress);
        assertEquals(localAddress.getAddress(), theSocket.getLocalAddress());
        assertTrue(theSocket.getLocalPort() > 0);
        theSocket.close();
    }

    public void test_getLocalSocketAddress_unbound() throws Exception {
        InetSocketAddress localAddress1 = new InetSocketAddress(InetAddress.getLocalHost(), 0);
        DatagramSocket s = new DatagramSocket(localAddress1);
        assertEquals(localAddress1.getAddress(), s.getLocalAddress());
        s.close();

        InetSocketAddress remoteAddress = (InetSocketAddress) s.getRemoteSocketAddress();
        assertNull(remoteAddress);
    }

    public void test_getLocalSocketAddress_ANY() throws Exception {
        DatagramSocket s = new DatagramSocket(0);
        assertEquals("ANY address not IPv6: " + s.getLocalSocketAddress(),
                Inet6Address.ANY, s.getLocalAddress());
        s.close();
        s = new DatagramSocket(0, null);
        assertEquals(Inet6Address.ANY, s.getLocalAddress());
        assertFalse(0 == s.getLocalPort());
        s.close();
    }

    public void test_setReuseAddressZ() throws Exception {
        // test case were we set it to false
        DatagramSocket theSocket1 = null;
        DatagramSocket theSocket2 = null;
        try {
            InetSocketAddress theAddress = new InetSocketAddress(InetAddress.getLocalHost(), 0);
            theSocket1 = new DatagramSocket(null);
            theSocket2 = new DatagramSocket(null);
            theSocket1.setReuseAddress(false);
            theSocket2.setReuseAddress(false);
            theSocket1.bind(theAddress);
            theSocket2.bind(new InetSocketAddress(InetAddress.getLocalHost(), theSocket1.getLocalPort()));
            fail();
        } catch (BindException expected) {
        }
        if (theSocket1 != null) {
            theSocket1.close();
        }
        if (theSocket2 != null) {
            theSocket2.close();
        }

        // test case were we set it to true
        InetSocketAddress theAddress = new InetSocketAddress(InetAddress.getLocalHost(), 0);
        theSocket1 = new DatagramSocket(null);
        theSocket2 = new DatagramSocket(null);
        theSocket1.setReuseAddress(true);
        theSocket2.setReuseAddress(true);
        theSocket1.bind(theAddress);
        theSocket2.bind(new InetSocketAddress(InetAddress.getLocalHost(), theSocket1.getLocalPort()));

        if (theSocket1 != null) {
            theSocket1.close();
        }
        if (theSocket2 != null) {
            theSocket2.close();
        }

        // test the default case which we expect to be the same on all
        // platforms
        try {
            theAddress = new InetSocketAddress(InetAddress.getLocalHost(), 0);
            theSocket1 = new DatagramSocket(null);
            theSocket2 = new DatagramSocket(null);
            theSocket1.bind(theAddress);
            theSocket2.bind(new InetSocketAddress(InetAddress.getLocalHost(), theSocket1.getLocalPort()));
            fail("No exception when trying to connect to do duplicate socket bind with re-useaddr left as default");
        } catch (BindException expected) {
        }
        if (theSocket1 != null) {
            theSocket1.close();
        }
        if (theSocket2 != null) {
            theSocket2.close();
        }

        try {
            theSocket1.setReuseAddress(true);
            fail("SocketException was not thrown.");
        } catch(SocketException se) {
            //expected
        }
    }

    public void test_getReuseAddress() throws Exception {
        DatagramSocket theSocket = new DatagramSocket(null);
        theSocket.setReuseAddress(true);
        assertTrue("getReuseAddress false when it should be true", theSocket.getReuseAddress());
        theSocket.setReuseAddress(false);
        assertFalse("getReuseAddress true when it should be False", theSocket.getReuseAddress());
        theSocket.close();
        try {
            theSocket.getReuseAddress();
            fail("SocketException was not thrown.");
        } catch(SocketException se) {
            //expected
        }
    }

    public void test_setBroadcastZ() throws Exception {
        DatagramSocket theSocket = new DatagramSocket(0);
        theSocket.setBroadcast(false);
        byte theBytes[] = { -1, -1, -1, -1 };

        // validate we cannot connect to the broadcast address when
        // setBroadcast is false
        try {
            theSocket.connect(new InetSocketAddress(InetAddress.getByAddress(theBytes), 0));
            fail();
        } catch (Exception expected) {
        }

        // now validate that we can connect to the broadcast address when
        // setBroadcast is true
        theSocket.setBroadcast(true);
        theSocket.connect(new InetSocketAddress(InetAddress.getByAddress(theBytes), 0));

        theSocket.close();
        try {
            theSocket.setBroadcast(false);
            fail();
        } catch(SocketException se) {
            //expected
        }
    }

    public void test_getBroadcast() throws Exception {
        try (DatagramSocket theSocket = new DatagramSocket()) {
            theSocket.setBroadcast(true);
            assertTrue("getBroadcast false when it should be true", theSocket.getBroadcast());
            theSocket.setBroadcast(false);
            assertFalse("getBroadcast true when it should be False", theSocket.getBroadcast());
        }
    }

    public void test_setTrafficClassI() throws Exception {
        int IPTOS_LOWCOST = 0x2;
        int IPTOS_THROUGHPUT = 0x8;
        try (DatagramSocket theSocket = new DatagramSocket(0)) {

            // validate that value set must be between 0 and 255
            try {
                theSocket.setTrafficClass(256);
                fail("No exception when traffic class set to 256");
            } catch (IllegalArgumentException e) {
            }

            try {
                theSocket.setTrafficClass(-1);
                fail("No exception when traffic class set to -1");
            } catch (IllegalArgumentException e) {
            }

            // now validate that we can set it to some good values
            theSocket.setTrafficClass(IPTOS_LOWCOST);
            theSocket.setTrafficClass(IPTOS_THROUGHPUT);
        }
    }


    public void test_isClosed() throws Exception {
        DatagramSocket theSocket = new DatagramSocket();
        // validate isClosed returns expected values
        assertFalse(theSocket.isClosed());
        theSocket.close();
        assertTrue(theSocket.isClosed());

        InetSocketAddress theAddress = new InetSocketAddress(InetAddress
                .getLocalHost(), 0);
        theSocket = new DatagramSocket(theAddress);
        assertFalse(theSocket.isClosed());
        theSocket.close();
        assertTrue(theSocket.isClosed());
    }

    public void test_getChannel() throws Exception {
        try (DatagramSocket ds = new DatagramSocket()) {
            assertNull(ds.getChannel());
        }

        try (DatagramServer server = new DatagramServer(Inet6Address.LOOPBACK);
             DatagramSocket ds = new DatagramSocket(0)) {
            assertNull(ds.getChannel());
            ds.disconnect();
        }

        try (DatagramChannel channel = DatagramChannel.open();
             DatagramSocket socket = channel.socket()) {
            assertEquals(channel, socket.getChannel());
        }
    }

    public void testReceiveOversizePacket() throws Exception {
        DatagramSocket ds = new DatagramSocket(0);
        DatagramSocket sds = new DatagramSocket(0);

        DatagramPacket rdp = new DatagramPacket("0123456789".getBytes("UTF-8"),
                5, Inet6Address.LOOPBACK, ds.getLocalPort());
        sds.send(rdp);
        sds.close();

        byte[] recvBuffer = new byte[5];
        DatagramPacket receive = new DatagramPacket(recvBuffer, recvBuffer.length);
        ds.receive(receive);
        ds.close();
        assertEquals(new String("01234"), new String(recvBuffer, 0, recvBuffer.length, "UTF-8"));
    }

    // Receive twice reusing the same DatagramPacket.
    // http://b/33957878
    public void testReceiveTwice() throws Exception {
        try (DatagramSocket ds = new DatagramSocket();
             DatagramSocket sds = new DatagramSocket()) {
            sds.connect(ds.getLocalSocketAddress());
            DatagramPacket p = new DatagramPacket(new byte[16], 16);

            byte[] smallPacketBytes = "01234".getBytes("UTF-8");
            DatagramPacket smallPacket =
                    new DatagramPacket(smallPacketBytes, smallPacketBytes.length);
            sds.send(smallPacket);
            ds.receive(p);
            assertPacketDataEquals(smallPacket, p);

            byte[] largePacketBytes = "0123456789".getBytes("UTF-8");
            DatagramPacket largerPacket =
                    new DatagramPacket(largePacketBytes, largePacketBytes.length);
            sds.send(largerPacket);
            ds.receive(p);
            assertPacketDataEquals(largerPacket, p);
        }
    }
}
