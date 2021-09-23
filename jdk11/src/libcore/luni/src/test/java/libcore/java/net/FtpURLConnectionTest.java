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

import junit.framework.TestCase;

import org.mockftpserver.core.util.IoUtil;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import sun.net.ftp.FtpLoginException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Tests URLConnections for ftp:// URLs.
 */
public class FtpURLConnectionTest extends TestCase {

    private static final String FILE_PATH = "test/file/for/FtpURLConnectionTest.txt";
    private static final String SERVER_HOSTNAME = "localhost";
    private static final String VALID_USER = "user";
    private static final String VALID_PASSWORD = "password";
    private static final String VALID_USER_HOME_DIR = "/home/user";

    private FakeFtpServer fakeFtpServer;
    private UnixFakeFileSystem fileSystem;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.setServerControlPort(0 /* allocate port number automatically */);
        fakeFtpServer.addUserAccount(new UserAccount(VALID_USER, VALID_PASSWORD,
                VALID_USER_HOME_DIR));
        fileSystem = new UnixFakeFileSystem();
        fakeFtpServer.setFileSystem(fileSystem);
        fileSystem.add(new DirectoryEntry(VALID_USER_HOME_DIR));
        fakeFtpServer.start();
    }

    @Override
    public void tearDown() throws Exception {
        fakeFtpServer.stop();
        super.tearDown();
    }

    public void testInputUrl() throws Exception {
        byte[] fileContents = "abcdef 1234567890".getBytes(UTF_8);
        addFileEntry(FILE_PATH, fileContents);
        URL fileUrl = getFileUrlWithCredentials(VALID_USER, VALID_PASSWORD, FILE_PATH);
        URLConnection connection = fileUrl.openConnection();
        assertContents(fileContents, connection.getInputStream());
    }

    public void testInputUrl_invalidUserOrPassword() throws Exception {
        checkInputUrl_invalidUserOrPassword("wrong_user", VALID_PASSWORD);
        checkInputUrl_invalidUserOrPassword(VALID_USER, "wrong password");
    }

    public void testInputUrl_missingPassword() throws Exception {
        URL noPasswordUrl = getFileUrlWithCredentials(VALID_USER, null, FILE_PATH);
        URLConnection noPasswordConnection = noPasswordUrl.openConnection();
        try {
            noPasswordConnection.getInputStream();
            fail();
        } catch (IOException expected) {
        }
    }

    private void checkInputUrl_invalidUserOrPassword(String user, String password)
            throws IOException {
        URL fileUrl = getFileUrlWithCredentials(user, password, FILE_PATH);
        URLConnection connection = fileUrl.openConnection();
        try {
            connection.getInputStream();
            fail();
        } catch (sun.net.ftp.FtpLoginException expected) {
            assertEquals("Invalid username/password", expected.getMessage());
        }
    }

    public void testOutputUrl() throws Exception {
        byte[] fileContents = "abcdef 1234567890".getBytes(UTF_8);
        addFileEntry("test/output-url/existing file.txt", fileContents);
        byte[] newFileContents = "contents of brand new file".getBytes(UTF_8);
        String filePath = "test/output-url/file that is newly created.txt";
        URL fileUrl = getFileUrlWithCredentials(VALID_USER, VALID_PASSWORD, filePath);
        URLConnection connection = fileUrl.openConnection();
        connection.setDoInput(false);
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        writeBytes(os, newFileContents);

        assertContents(newFileContents, openFileSystemContents(filePath));
    }

    public void testConnectOverProxy_noProxy() throws Exception {
        Proxy proxy = Proxy.NO_PROXY;
        byte[] fileContents = "abcdef 1234567890".getBytes(UTF_8);
        URL fileUrl = addFileEntry(FILE_PATH, fileContents);
        URLConnection connection = fileUrl.openConnection(proxy);
        assertContents(fileContents, connection.getInputStream());
        // Check that NO_PROXY covers the Type.DIRECT case
        assertEquals(Proxy.Type.DIRECT, proxy.type());
    }

    /**
     * Tests that the helper class {@link CountingProxy} correctly accepts and
     * counts connection attempts to the address represented by {@code asProxy()}.
     */
    public void testCountingProxy() throws Exception {
        Socket socket = new Socket();
        try {
            CountingProxy countingProxy = CountingProxy.start();
            try {
                Proxy proxy = countingProxy.asProxy();
                assertEquals(Proxy.Type.HTTP, proxy.type());
                SocketAddress address = proxy.address();
                socket.connect(address, /* timeout (msec) */ 200); // attempt one connection
                countingProxy.waitAndAssertConnectionCount(1);
            } finally {
                countingProxy.shutdown();
            }
        } finally {
            socket.close();
        }
    }

    /**
     * Tests that a HTTP proxy explicitly passed to {@link URL#openConnection(Proxy)}
     * ignores HTTP proxies (since it doesn't support them) and attempts a direct
     * connection instead.
     */
    public void testConnectOverProxy_explicit_http_uses_direct_connection() throws Exception {
        byte[] fileContents = "abcdef 1234567890".getBytes(UTF_8);
        URL fileUrl = addFileEntry(FILE_PATH, fileContents);
        CountingProxy countingProxy = CountingProxy.start();
        try {
            Proxy proxy = countingProxy.asProxy();
            URLConnection connection = fileUrl.openConnection(proxy);
            // direct connection succeeds
            assertContents(fileContents, connection.getInputStream());
            countingProxy.waitAndAssertConnectionCount(0);
        } finally {
            countingProxy.shutdown();
        }
    }

    /**
     * Tests that if a ProxySelector is set, any HTTP proxies selected for
     * ftp:// URLs will be rejected. A direct connection will
     * be selected once the ProxySelector's proxies have failed.
     */
    public void testConnectOverProxy_implicit_http_fails() throws Exception {
        byte[] fileContents = "abcdef 1234567890".getBytes(UTF_8);
        URL fileUrl = addFileEntry(FILE_PATH, fileContents);
        ProxySelector defaultProxySelector = ProxySelector.getDefault();
        try {
            CountingProxy countingProxy = CountingProxy.start();
            try {
                Proxy proxy = countingProxy.asProxy();
                SingleProxySelector proxySelector = new SingleProxySelector(proxy);
                ProxySelector.setDefault(proxySelector);
                URLConnection connection = fileUrl.openConnection();
                InputStream inputStream = connection.getInputStream();

                IOException e = proxySelector.getLastException();
                assertEquals("FTP connections over HTTP proxy not supported",
                        e.getMessage());

                // The direct connection is successful
                assertContents(fileContents, inputStream);
                countingProxy.waitAndAssertConnectionCount(0);
            } finally {
                countingProxy.shutdown();
            }
        } finally {
            ProxySelector.setDefault(defaultProxySelector);
        }
    }

    public void testInputUrlWithSpaces() throws Exception {
        byte[] fileContents = "abcdef 1234567890".getBytes(UTF_8);
        URL url = addFileEntry("file with spaces.txt", fileContents);
        URLConnection connection = url.openConnection();
        assertContents(fileContents, connection.getInputStream());
    }

    public void testBinaryFileContents() throws Exception {
        byte[] data = new byte[4096];
        new Random(31337).nextBytes(data); // arbitrary pseudo-random but repeatable test data
        URL url = addFileEntry("binaryfile.dat", data.clone());
        assertContents(data, url.openConnection().getInputStream());
    }

    // https://code.google.com/p/android/issues/detail?id=160725
    public void testInputUrlWithSpacesViaProxySelector() throws Exception {
        byte[] fileContents = "abcdef 1234567890".getBytes(UTF_8);
        ProxySelector defaultProxySelector = ProxySelector.getDefault();
        try {
            SingleProxySelector proxySelector = new SingleProxySelector(Proxy.NO_PROXY);
            ProxySelector.setDefault(proxySelector);
            URL url = addFileEntry("file with spaces.txt", fileContents);
            assertContents(fileContents, url.openConnection().getInputStream());
            assertNull(proxySelector.getLastException());
        } finally {
            ProxySelector.setDefault(defaultProxySelector);
        }
    }

    // http://b/35784677
    public void testCRLFInUserinfo() throws Exception {
        int serverPort = fakeFtpServer.getServerControlPort();
        List<String> encodedUserInfos = Arrays.asList(
                // '\r\n' in the username with password
                "user%0D%0Acommand:password",
                // '\r\n' in the password
                "user:password%0D%0Acommand",
                // just '\n' in the password
                "user:password%0Acommand",
                // just '\n' in the username
                "user%0Acommand:password"
        );
        for (String encodedUserInfo : encodedUserInfos) {
            String urlString = String.format(Locale.US, "ftp://%s@%s:%s/%s",
                    encodedUserInfo, SERVER_HOSTNAME, serverPort, FILE_PATH);
            try {
                new URL(urlString).openConnection().connect();
                fail("Connection shouldn't have succeeded: " + urlString);
            } catch (FtpLoginException expected) {
                // The original message "Illegal carriage return" gets lost
                // where FtpURLConnection.connect() translates the
                // original FtpProtocolException into FtpLoginException.
                assertEquals("Invalid username/password", expected.getMessage());
            }
        }
    }

    private InputStream openFileSystemContents(String fileName) throws IOException {
        String fullFileName = VALID_USER_HOME_DIR + "/" + fileName;
        FileEntry entry = (FileEntry) fileSystem.getEntry(fullFileName);
        assertNotNull("File must exist with name " + fullFileName, entry);
        return entry.createInputStream();
    }

    private static void writeBytes(OutputStream os, byte[] fileContents) throws IOException {
        os.write(fileContents);
        os.close();
    }

    private static void assertContents(byte[] expectedContents, InputStream inputStream)
            throws IOException {
        try {
            byte[] contentBytes = IoUtil.readBytes(inputStream);
            if (!Arrays.equals(expectedContents, contentBytes)) {
                // optimize the error message for the case of the content being character data
                fail("Expected " + new String(expectedContents, UTF_8) + ", but got "
                        + new String(contentBytes, UTF_8));
            }
        } finally {
            inputStream.close();
        }
    }

    private URL getFileUrlWithCredentials(String user, String password, String filePath) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(filePath);
        int serverPort = fakeFtpServer.getServerControlPort();
        String credentials = user + (password == null ? "" : (":" + password));
        String urlString = String.format(Locale.US, "ftp://%s@%s:%s/%s",
                credentials, SERVER_HOSTNAME, serverPort, filePath);
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            fail("Malformed URL: " + urlString);
            throw new AssertionError("Can never happen");
        }
    }

    private URL addFileEntry(String filePath, byte[] fileContents) {
        FileEntry fileEntry = new FileEntry(VALID_USER_HOME_DIR + "/" + filePath);
        fileEntry.setContents(fileContents);
        fileSystem.add(fileEntry);
        return getFileUrlWithCredentials(VALID_USER, VALID_PASSWORD, filePath);
    }

    /**
     * A {@link ProxySelector} that selects the same (given) Proxy for all URIs.
     */
    static class SingleProxySelector extends ProxySelector {
        private final Proxy proxy;
        private IOException lastException = null;

        public SingleProxySelector(Proxy proxy) {
            this.proxy = proxy;
        }

        @Override
        public List<Proxy> select(URI uri) {
            assertNotNull(uri);
            return Collections.singletonList(proxy);
        }

        @Override
        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
            lastException = ioe;
        }

        public IOException getLastException() {
            return lastException;
        }
    }

    /**
     * Counts the number of attempts to connect to a ServerSocket exposed
     * {@link #asProxy() as a Proxy}. From {@link #start()} until
     * {@link #shutdown()}, a background server thread accepts and counts
     * connections on the socket but immediately closes them without
     * reading any data.
     */
    static class CountingProxy {
        class ServerThread extends Thread {
            public ServerThread(String name) {
                super(name);
            }

            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        connectionAttempts.release(1); // count one connection attempt
                        socket.close();
                    } catch (SocketException e) {
                        shutdownLatch.countDown();
                        return;
                    } catch (IOException e) {
                        // retry
                    }
                }
            }
        }

        // Signals that serverThread has gracefully completed shutdown (not crashed)
        private final CountDownLatch shutdownLatch = new CountDownLatch(1);
        private final ServerSocket serverSocket;
        private final Proxy proxy;
        private final Thread serverThread;
        // holds one permit for each connection attempt encountered; this allows
        // us to block until a certain number of attempts have taken place.
        private final Semaphore connectionAttempts = new Semaphore(0);

        private CountingProxy() throws IOException {
            serverSocket = new ServerSocket(0 /* allocate port number automatically */);
            SocketAddress socketAddress = serverSocket.getLocalSocketAddress();
            proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
            String threadName = getClass().getSimpleName() + " @ " + socketAddress;
            serverThread = new ServerThread(threadName);
        }

        public static CountingProxy start() throws IOException {
            CountingProxy result = new CountingProxy();
            // only start the thread once the object has been properly constructed
            result.serverThread.start();
            try {
                // Give ServerThread time to call accept().
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new IOException("Unexpectedly interrupted", e);
            }
            return result;
        }

        /**
         * Returns the HTTP {@link Proxy} that can represents the ServerSocket
         * connections to which this class manages/counts.
         */
        public Proxy asProxy() {
            return proxy;
        }

        /**
         * Causes the ServerSocket represented by {@link #asProxy()} to stop accepting
         * connections by shutting down the server thread.
         *
         * @return the number of connections that were attempted during the proxy's lifetime
         */
        public void waitAndAssertConnectionCount(int expectedConnectionAttempts)
                throws IOException, InterruptedException {
            // Wait for a timeout, or fail early if expected # of connections is exceeded
            boolean tooManyConnections = connectionAttempts.tryAcquire(
                    expectedConnectionAttempts + 1, 300, TimeUnit.MILLISECONDS);
            assertFalse("Observed more connections than the expected " + expectedConnectionAttempts,
                    tooManyConnections);
            assertEquals(expectedConnectionAttempts, connectionAttempts.availablePermits());
        }

        public void shutdown() throws IOException, InterruptedException {
            serverSocket.close();
            // Check that the server shuts down quickly and gracefully via the expected
            // code path (as opposed to an uncaught exception).
            shutdownLatch.await(1, TimeUnit.SECONDS);
            serverThread.join(1000);
            assertFalse("serverThread failed to shut down quickly", serverThread.isAlive());
        }

        @Override
        public String toString() {
            return serverThread.toString() ;
        }
    }

}
