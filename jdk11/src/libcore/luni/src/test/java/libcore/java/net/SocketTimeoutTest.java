/*
 * Copyright (C) 2017 The Android Open Source Project
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

package libcore.java.net;

import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import libcore.util.EmptyArray;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests socket timeout behavior for various different socket types.
 */
public class SocketTimeoutTest {

    private static final int TIMEOUT_MILLIS = 500;

    private static final InetSocketAddress UNREACHABLE_ADDRESS
            = new InetSocketAddress("192.0.2.0", 0); // RFC 5737

    @FunctionalInterface
    private interface SocketOperation<T> {
        void operate(T s) throws IOException;
    }

    @FunctionalInterface
    private interface SocketConstructor<T> {
        T get() throws IOException;
    }

    private static <T extends Closeable> void checkOperationTimesOut(SocketConstructor<T> construct,
            SocketOperation<T> op) throws Exception {
        try (T socket = construct.get()) {
            long startingTime = System.currentTimeMillis();
            try {
                op.operate(socket);
                fail();
            } catch (SocketTimeoutException timeoutException) {
                long timeElapsed = System.currentTimeMillis() - startingTime;
                assertTrue(
                        Math.abs(((float) timeElapsed / TIMEOUT_MILLIS) - 1)
                                < 0.2f); // Allow some error.
            }
        }
    }

    @Test
    public void testSocketConnectTimeout() throws Exception {
        // #connect(SocketAddress endpoint, int timeout)
        checkOperationTimesOut(() -> new Socket(), s -> s.connect(UNREACHABLE_ADDRESS,
                TIMEOUT_MILLIS));

        // Setting SO_TIMEOUT should not affect connect timeout.
        checkOperationTimesOut(() -> new Socket(),
                s -> {
                    s.setSoTimeout(TIMEOUT_MILLIS / 2);
                    s.connect(UNREACHABLE_ADDRESS, TIMEOUT_MILLIS);
                });
    }

    @Test
    public void testSocketReadTimeout() throws Exception {
        // #read()
        try (ServerSocket ss = new ServerSocket(0)) {
            // The server socket will accept the connection without explicitly calling accept() due
            // to TCP backlog.

            checkOperationTimesOut(() -> new Socket(), s -> {
                s.connect(ss.getLocalSocketAddress());
                s.setSoTimeout(TIMEOUT_MILLIS);
                s.getInputStream().read();
            });
        }
    }

    @Test
    public void testSocketWriteNeverTimeouts() throws Exception {
        // #write() should block if the buffers are full, and does not drop packets or throw
        // SocketTimeoutException.
        try (Socket sock = new Socket();
             ServerSocket serverSocket = new ServerSocket(0)) {
            // Setting this option should not affect behaviour, as specified by the spec.
            sock.setSoTimeout(TIMEOUT_MILLIS);

            // Set SO_SNDBUF and SO_RCVBUF to minimum value allowed by kernel.
            sock.setSendBufferSize(1);
            serverSocket.setReceiveBufferSize(1);
            int actualSize = sock.getSendBufferSize() + serverSocket.getReceiveBufferSize();

            sock.connect(serverSocket.getLocalSocketAddress());

            CountDownLatch threadStarted = new CountDownLatch(1);
            CountDownLatch writeCompleted = new CountDownLatch(1);
            Thread thread = new Thread(() -> {
                threadStarted.countDown();
                try {
                    // Should block
                    sock.getOutputStream().write(new byte[actualSize + 1]);
                    writeCompleted.countDown();
                } catch (IOException ignored) {
                } finally {
                    writeCompleted.countDown();
                }
            });

            thread.start();

            // Wait for the thread to start.
            assertTrue(threadStarted.await(500, TimeUnit.MILLISECONDS));

            // Wait for TIMEOUT_MILLIS + slop. If write does not complete by then, we assume it has
            // blocked.
            boolean blocked =
                    !writeCompleted.await(TIMEOUT_MILLIS * 2, TimeUnit.MILLISECONDS);
            assertTrue(blocked);

            // Make sure the writing thread completes after the socket is closed.
            sock.close();
            assertTrue(writeCompleted.await(5000, TimeUnit.MILLISECONDS));
        }
    }

    @Test
    public void testServerSocketAcceptTimeout() throws Exception {
        // #accept()
        checkOperationTimesOut(() -> new ServerSocket(0),
                s -> {
                    s.setSoTimeout(TIMEOUT_MILLIS);
                    s.accept();
                });
    }

    @Test
    public void testServerSocketChannelAcceptTimeout() throws Exception {
        // #accept()
        checkOperationTimesOut(() -> ServerSocketChannel.open(),
                s -> {
                    s.bind(null, 0);
                    s.socket().setSoTimeout(TIMEOUT_MILLIS);
                    s.socket().accept();
                });
    }

    @Test
    public void testDatagramSocketReceive() throws Exception {
        checkOperationTimesOut(() -> new DatagramSocket(), s -> {
            s.setSoTimeout(TIMEOUT_MILLIS);
            s.receive(new DatagramPacket(EmptyArray.BYTE, 0));
        });
    }

    // TODO(yikong), http://b/35867657:
    // Add tests for SocksSocketImpl once a mock Socks server is implemented.
}
