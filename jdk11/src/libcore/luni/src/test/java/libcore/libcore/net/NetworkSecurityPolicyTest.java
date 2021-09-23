/*
 * Copyright (C) 2015 The Android Open Source Project
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

package libcore.libcore.net;

import junit.framework.TestCase;
import libcore.io.IoUtils;
import libcore.net.NetworkSecurityPolicy;
import java.io.Closeable;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.ErrorManager;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SocketHandler;

public class NetworkSecurityPolicyTest extends TestCase {

    private NetworkSecurityPolicy mOriginalPolicy;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mOriginalPolicy = NetworkSecurityPolicy.getInstance();
    }

    @Override
    protected void tearDown() throws Exception {
        try {
            NetworkSecurityPolicy.setInstance(mOriginalPolicy);
        } finally {
            super.tearDown();
        }
    }

    public void testCleartextTrafficPolicySetterAndGetter() {
        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(false));
        assertEquals(false, NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted());

        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(true));
        assertEquals(true, NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted());

        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(false));
        assertEquals(false, NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted());

        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(true));
        assertEquals(true, NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted());
    }

    public void testHostnameAwareCleartextTrafficPolicySetterAndGetter() {
        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(false));
        assertEquals(false,
                NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted("localhost"));

        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(true));
        assertEquals(true,
                NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted("localhost"));

        TestNetworkSecurityPolicy policy = new TestNetworkSecurityPolicy(false);
        policy.addHostMapping("localhost", true);
        policy.addHostMapping("example.com", false);
        NetworkSecurityPolicy.setInstance(policy);
        assertEquals(false, NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted());
        assertEquals(true,
                NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted("localhost"));
        assertEquals(false,
                NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted("example.com"));

    }

    public void testCleartextTrafficPolicyWithHttpURLConnection() throws Exception {
        // Assert that client transmits some data when cleartext traffic is permitted.
        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(true));
        try (CapturingServerSocket server = new CapturingServerSocket()) {
            URL url = new URL("http://localhost:" + server.getPort() + "/test.txt");
            try {
                url.openConnection().getContent();
                fail();
            } catch (IOException expected) {
            }
            server.assertDataTransmittedByClient();
        }

        // Assert that client does not transmit any data when cleartext traffic is not permitted and
        // that URLConnection.openConnection or getContent fail with an IOException.
        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(false));
        try (CapturingServerSocket server = new CapturingServerSocket()) {
            URL url = new URL("http://localhost:" + server.getPort() + "/test.txt");
            try {
                url.openConnection().getContent();
                fail();
            } catch (IOException expected) {
            }
            server.assertNoDataTransmittedByClient();
        }
    }

    public void testCleartextTrafficPolicyWithFtpURLConnection() throws Exception {
        // Assert that client transmits some data when cleartext traffic is permitted.
        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(true));
        byte[] serverReplyOnConnect = "220\r\n".getBytes("US-ASCII");
        try (CapturingServerSocket server = new CapturingServerSocket(serverReplyOnConnect)) {
            URL url = new URL("ftp://localhost:" + server.getPort() + "/test.txt");
            try {
                url.openConnection().getContent();
                fail();
            } catch (IOException expected) {
            }
            server.assertDataTransmittedByClient();
        }

        // Assert that client does not transmit any data when cleartext traffic is not permitted and
        // that URLConnection.openConnection or getContent fail with an IOException.
        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(false));
        try (CapturingServerSocket server = new CapturingServerSocket(serverReplyOnConnect)) {
            URL url = new URL("ftp://localhost:" + server.getPort() + "/test.txt");
            try {
                url.openConnection().getContent();
                fail();
            } catch (IOException expected) {
            }
            server.assertNoDataTransmittedByClient();
        }
    }

    public void testCleartextTrafficPolicyWithJarHttpURLConnection() throws Exception {
        // Assert that client transmits some data when cleartext traffic is permitted.
        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(true));
        try (CapturingServerSocket server = new CapturingServerSocket()) {
            URL url = new URL("jar:http://localhost:" + server.getPort() + "/test.jar!/");
            try {
                ((JarURLConnection) url.openConnection()).getManifest();
                fail();
            } catch (IOException expected) {
            }
            server.assertDataTransmittedByClient();
        }

        // Assert that client does not transmit any data when cleartext traffic is not permitted and
        // that JarURLConnection.openConnection or getManifest fail with an IOException.
        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(false));
        try (CapturingServerSocket server = new CapturingServerSocket()) {
            URL url = new URL("jar:http://localhost:" + server.getPort() + "/test.jar!/");
            try {
                ((JarURLConnection) url.openConnection()).getManifest();
                fail();
            } catch (IOException expected) {
            }
            server.assertNoDataTransmittedByClient();
        }
    }

    public void testCleartextTrafficPolicyWithJarFtpURLConnection() throws Exception {
        // Assert that client transmits some data when cleartext traffic is permitted.
        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(true));
        byte[] serverReplyOnConnect = "220\r\n".getBytes("US-ASCII");
        try (CapturingServerSocket server = new CapturingServerSocket(serverReplyOnConnect)) {
            URL url = new URL("jar:ftp://localhost:" + server.getPort() + "/test.jar!/");
            try {
                ((JarURLConnection) url.openConnection()).getManifest();
                fail();
            } catch (IOException expected) {
            }
            server.assertDataTransmittedByClient();
        }

        // Assert that client does not transmit any data when cleartext traffic is not permitted and
        // that JarURLConnection.openConnection or getManifest fail with an IOException.
        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(false));
        try (CapturingServerSocket server = new CapturingServerSocket(serverReplyOnConnect)) {
            URL url = new URL("jar:ftp://localhost:" + server.getPort() + "/test.jar!/");
            try {
                ((JarURLConnection) url.openConnection()).getManifest();
                fail();
            } catch (IOException expected) {
            }
            server.assertNoDataTransmittedByClient();
        }
    }

    public void testCleartextTrafficPolicyWithLoggingSocketHandler() throws Exception {
        // Assert that client transmits some data when cleartext traffic is permitted.
        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(true));
        try (CapturingServerSocket server = new CapturingServerSocket()) {
            SocketHandler logger = new SocketHandler("localhost", server.getPort());
            MockErrorManager mockErrorManager = new MockErrorManager();
            logger.setErrorManager(mockErrorManager);
            logger.setLevel(Level.ALL);
            LogRecord record = new LogRecord(Level.INFO, "A log record");
            assertTrue(logger.isLoggable(record));
            logger.publish(record);
            assertNull(mockErrorManager.getMostRecentException());
            server.assertDataTransmittedByClient();
            logger.close();
        }

        // Assert that client does not transmit any data when cleartext traffic is not permitted.
        NetworkSecurityPolicy.setInstance(new TestNetworkSecurityPolicy(false));
        try (CapturingServerSocket server = new CapturingServerSocket()) {
            try {
                new SocketHandler("localhost", server.getPort());
                fail();
            } catch (IOException expected) {
            }
            server.assertNoDataTransmittedByClient();
        }
    }

    /**
     * Server socket which listens on a local port and captures the first chunk of data transmitted
     * by the client.
     */
    private static class CapturingServerSocket implements Closeable {
        private final ServerSocket mSocket;
        private final int mPort;
        private final ExecutorService executor;
        private final Future<byte[]> mFirstChunkReceivedFuture;

        /**
         * Constructs a new socket listening on a local port.
         */
        public CapturingServerSocket() throws IOException {
            this(null);
        }

        /**
         * Constructs a new socket listening on a local port, which sends the provided reply as
         * soon as a client connects to it.
         */
        public CapturingServerSocket(final byte[] replyOnConnect) throws IOException {
            mSocket = new ServerSocket(0);
            mPort = mSocket.getLocalPort();
            Callable<byte[]> callable = () -> {
                try (Socket client = mSocket.accept()) {
                    // Reply (if requested)
                    if (replyOnConnect != null) {
                        client.getOutputStream().write(replyOnConnect);
                        client.getOutputStream().flush();
                    }

                    // Read request
                    byte[] buf = new byte[64 * 1024];
                    int chunkSize = client.getInputStream().read(buf);
                    if (chunkSize == -1) {
                        // Connection closed without any data received
                        return new byte[0];
                    }
                    // Received some data
                    return Arrays.copyOf(buf, chunkSize);
                } finally {
                    IoUtils.closeQuietly(mSocket);
                }
            };
            executor = Executors.newSingleThreadExecutor();
            mFirstChunkReceivedFuture = executor.submit(callable);
        }

        public int getPort() {
            return mPort;
        }

        public Future<byte[]> getFirstReceivedChunkFuture() {
            return mFirstChunkReceivedFuture;
        }

        @Override
        public void close() {
            IoUtils.closeQuietly(mSocket);
            executor.shutdown();
        }

        private void assertDataTransmittedByClient()
                throws Exception {
            byte[] firstChunkFromClient = getFirstReceivedChunkFuture().get(4, TimeUnit.SECONDS);
            if ((firstChunkFromClient == null) || (firstChunkFromClient.length == 0)) {
                fail("Client did not transmit any data to server");
            }
        }

        private void assertNoDataTransmittedByClient()
                throws Exception {
            byte[] firstChunkFromClient;
            try {
                firstChunkFromClient = getFirstReceivedChunkFuture().get(4, TimeUnit.SECONDS);
            } catch (TimeoutException expected) {
                return;
            }
            if ((firstChunkFromClient != null) && (firstChunkFromClient.length > 0)) {
                fail("Client transmitted " + firstChunkFromClient.length+ " bytes: "
                        + new String(firstChunkFromClient, "US-ASCII"));
            }
        }
    }

    private static class MockErrorManager extends ErrorManager {
        private Exception mMostRecentException;

        public Exception getMostRecentException() {
            synchronized (this) {
                return mMostRecentException;
            }
        }

        @Override
        public void error(String message, Exception exception, int errorCode) {
            synchronized (this) {
                mMostRecentException = exception;
            }
        }
    }

    private static class TestNetworkSecurityPolicy extends NetworkSecurityPolicy {
        private final boolean mCleartextTrafficPermitted;
        private final Map<String, Boolean> mHostMap = new HashMap<String, Boolean>();

        public TestNetworkSecurityPolicy(boolean cleartextTrafficPermitted) {
            mCleartextTrafficPermitted = cleartextTrafficPermitted;
        }

        public void addHostMapping(String hostname, boolean isCleartextTrafficPermitted) {
            mHostMap.put(hostname, isCleartextTrafficPermitted);
        }

        @Override
        public boolean isCleartextTrafficPermitted() {
            return mCleartextTrafficPermitted;
        }

        @Override
        public boolean isCleartextTrafficPermitted(String hostname) {
            if (mHostMap.containsKey(hostname)) {
                return mHostMap.get(hostname);
            }

            return isCleartextTrafficPermitted();
        }

        @Override
        public boolean isCertificateTransparencyVerificationRequired(String hostname) {
            return false;
        }
    }
}
