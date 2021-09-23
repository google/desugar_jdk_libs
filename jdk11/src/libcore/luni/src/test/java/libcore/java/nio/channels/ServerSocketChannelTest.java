/*
 * Copyright (C) 2011 The Android Open Source Project
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

package libcore.java.nio.channels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.StandardSocketOptions;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ServerSocketChannelTest {
    // http://code.google.com/p/android/issues/detail?id=16579
    @Test
    public void testNonBlockingAccept() throws Exception {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.configureBlocking(false);
            ssc.socket().bind(null);
            // Should return immediately, since we're non-blocking.
            assertNull(ssc.accept());
        }
    }

    /** Checks the state of the ServerSocketChannel and associated ServerSocket after open() */
    @Test
    public void open_initialState() throws Exception {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            assertNull(ssc.socket().getLocalSocketAddress());

            ServerSocket socket = ssc.socket();
            assertFalse(socket.isBound());
            assertFalse(socket.isClosed());
            assertEquals(-1, socket.getLocalPort());
            assertNull(socket.getLocalSocketAddress());
            assertNull(socket.getInetAddress());
            assertTrue(socket.getReuseAddress());

            assertSame(ssc, socket.getChannel());
        }
    }

    @Test
    public void bind_unresolvedAddress() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        try {
            ssc.socket().bind(new InetSocketAddress("unresolvedname", 31415));
            fail();
        } catch (SocketException expected) {
        }

        assertNull(ssc.socket().getLocalSocketAddress());
        assertTrue(ssc.isOpen());

        ssc.close();
    }

    @Test
    public void bind_nullBindsToAll() throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(null);
        InetSocketAddress boundAddress = (InetSocketAddress) ssc.socket().getLocalSocketAddress();
        assertTrue(boundAddress.getAddress().isAnyLocalAddress());
        assertFalse(boundAddress.getAddress().isLinkLocalAddress());
        assertFalse(boundAddress.getAddress().isLoopbackAddress());

        // Attempt to connect to the "any" address.
        assertTrue(canConnect(boundAddress));

        // Go through all local IPs and try to connect to each in turn - all should succeed.
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface nic = interfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = nic.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetSocketAddress address =
                        new InetSocketAddress(inetAddresses.nextElement(), boundAddress.getPort());
                assertTrue(canConnect(address));
            }
        }

        ssc.close();
    }

    /**
     * Server that can be used to generate a Future containing the count of incoming connections it
     * accepts.  Uses a latch to block the main thread until it is ready to start counting.
     */
    private static class CountingServer implements Callable<Integer> {
        private final ServerSocketChannel channel;
        private final CountDownLatch latch;
        private Integer connectionCount = 0;

        private CountingServer(ServerSocketChannel channel, CountDownLatch latch) {
            this.channel = channel;
            this.latch = latch;
        }

        @Override
        public Integer call() throws IOException {
            // Clear any pending connections.
            clearAcceptQueue();
            // Release the main thread.
            latch.countDown();
            // Loop accepting and counting connections until the main thread closes the socket,
            // triggering an IOException.
            while (true) {
                try {
                    SocketChannel client = channel.accept();
                    connectionCount++;
                    client.close();
                } catch (IOException e) {
                    break;
                }
            }
            return connectionCount;
        }

        private void clearAcceptQueue() throws IOException {
            channel.configureBlocking(false);
            SocketChannel client;
            while ((client = channel.accept()) != null) {
                client.close();
            }
            channel.configureBlocking(true);
        }
    }

    @Test
    public void bind_loopback() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 5);
        InetSocketAddress boundAddress = (InetSocketAddress) ssc.socket().getLocalSocketAddress();
        assertFalse(boundAddress.getAddress().isAnyLocalAddress());
        assertFalse(boundAddress.getAddress().isLinkLocalAddress());
        assertTrue(boundAddress.getAddress().isLoopbackAddress());

        // Attempt to connect to the "loopback" address. Note: There can be several loopback
        // addresses, such as 127.0.0.1 (IPv4) and 0:0:0:0:0:0:0:1 (IPv6) and only one will be
        // bound.
        InetSocketAddress loopbackAddress =
                new InetSocketAddress(InetAddress.getLoopbackAddress(), boundAddress.getPort());
        assertTrue(canConnect(loopbackAddress));

        CountDownLatch latch = new CountDownLatch(1);
        Future<Integer> countFuture = executor.submit(new CountingServer(ssc, latch));
        // Wait until the CountingServer thread starts, otherwise there is a risk of
        // canConnect() connecting and closing the socket before the server is ready.
        latch.await();

        // Go through all local IPs and try to connect to each in turn - all should fail except
        // for the loopback.
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        assertNotNull(interfaces);
        while (interfaces.hasMoreElements()) {
            NetworkInterface nic = interfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = nic.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetSocketAddress address =
                        new InetSocketAddress(inetAddresses.nextElement(), boundAddress.getPort());
                if (!address.equals(loopbackAddress)) {
                    // Return value ignored in favour of using countFuture below.
                    canConnect(address);
                }
            }
        }
        ssc.close();
        executor.shutdown();
        // If connectionCount is non-zero then we connected to our own server which is a failure.
        assertEquals(0, (int) countFuture.get());
    }

    @Test
    public void bind_socketAddress() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
        assertEquals(InetAddress.getLoopbackAddress(),
                ((InetSocketAddress)(ssc.getLocalAddress())).getAddress());
        assertTrue(((InetSocketAddress)(ssc.getLocalAddress())).getPort() > 0);

        try {
            ssc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(),
                    ((InetSocketAddress)(ssc.getLocalAddress())).getPort()));
            fail();
        } catch (AlreadyBoundException expected) {
        }

        try {
            ServerSocketChannel ssc1 = ServerSocketChannel.open();
            ssc1.bind(new InetSocketAddress("1.1.1.1.1.1.1", 0));
            fail();
        } catch (UnresolvedAddressException expected) {
        }

        ssc.close();
        try {
            ssc.bind(new InetSocketAddress("1.1.1.1.1.1.1", 0));
            fail();
        } catch (ClosedChannelException expected) {
        }
    }

    @Test
    public void set_option() throws Exception {
        ServerSocketChannel sc = ServerSocketChannel.open();
        sc.setOption(StandardSocketOptions.SO_REUSEADDR, true);

        // Assert that we can read back the option from the channel...
        assertTrue(sc.getOption(StandardSocketOptions.SO_REUSEADDR));

        sc.setOption(StandardSocketOptions.SO_REUSEADDR, false);

        // Assert that we can read back the option from the channel...
        assertEquals(false, (boolean)sc.getOption(StandardSocketOptions.SO_REUSEADDR));

        sc.setOption(StandardSocketOptions.SO_RCVBUF, 1100);
        assertTrue(1100 <= sc.getOption(StandardSocketOptions.SO_RCVBUF));

        sc.close();
        try {
            sc.setOption(StandardSocketOptions.SO_RCVBUF, 2000);
            fail();
        } catch (ClosedChannelException expected) {
        }
    }

    private static boolean canConnect(InetSocketAddress address) {
        try {
            SocketChannel socketChannel = SocketChannel.open(address);
            socketChannel.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
