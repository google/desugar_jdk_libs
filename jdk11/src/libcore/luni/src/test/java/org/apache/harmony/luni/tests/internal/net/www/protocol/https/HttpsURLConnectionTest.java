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

package org.apache.harmony.luni.tests.internal.net.www.protocol.https;

import com.google.mockwebserver.Dispatcher;
import com.google.mockwebserver.MockResponse;
import com.google.mockwebserver.MockWebServer;
import com.google.mockwebserver.RecordedRequest;
import com.google.mockwebserver.SocketPolicy;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import junit.framework.TestCase;
import libcore.java.security.TestKeyStore;
import libcore.javax.net.ssl.TestTrustManager;

/**
 * Implementation independent test for HttpsURLConnection.
 */
public class HttpsURLConnectionTest extends TestCase {

    private static final String POST_METHOD = "POST";

    private static final String GET_METHOD = "GET";

    /**
     * Data to be posted by client to the server when the method is POST.
     */
    private static final String POST_DATA = "_.-^ Client's Data ^-._";

    /**
     * The content of the response to be sent during HTTPS session.
     */
    private static final String RESPONSE_CONTENT
            = "<HTML>\n"
            + "<HEAD><TITLE>HTTPS Response Content</TITLE></HEAD>\n"
            + "</HTML>";

    // the password to the store
    private static final String KS_PASSWORD = "password";

    // turn on/off logging
    private static final boolean DO_LOG = false;

    // read/connection timeout value
    private static final int TIMEOUT = 5000;

    // OK response code
    private static final int OK_CODE = 200;

    // Not Found response code
    private static final int NOT_FOUND_CODE = 404;

    // Proxy authentication required response code
    private static final int AUTHENTICATION_REQUIRED_CODE = 407;

    private static File store;

    static {
        try {
            store = File.createTempFile("key_store", "bks");
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * Checks that HttpsURLConnection's default SSLSocketFactory is operable.
     */
    public void testGetDefaultSSLSocketFactory() throws Exception {
        // set up the properties pointing to the key/trust stores
        setUpStoreProperties();

        SSLSocketFactory defaultSSLSF = HttpsURLConnection.getDefaultSSLSocketFactory();
        ServerSocket ss = new ServerSocket(0);
        Socket s = defaultSSLSF.createSocket("localhost", ss.getLocalPort());
        ss.accept();
        s.close();
        ss.close();
    }

    public void testHttpsConnection() throws Throwable {
        // set up the properties pointing to the key/trust stores
        setUpStoreProperties();

        SSLContext ctx = getContext();

        // set the HostnameVerifier required to satisfy SSL - always returns "verified".
        HttpsURLConnection.setDefaultHostnameVerifier(new TestHostnameVerifier());

        // create a webserver to check and respond to requests
        SingleRequestDispatcher dispatcher = new SingleRequestDispatcher(GET_METHOD, OK_CODE);
        MockWebServer webServer = createWebServer(ctx, dispatcher);

        // create url connection to be tested
        URL url = webServer.getUrl("/");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(ctx.getSocketFactory());

        // perform the interaction between the peers
        executeClientRequest(connection, false /* doOutput */);

        checkConnectionStateParameters(connection, dispatcher.getLastRequest());

        // should silently exit
        connection.connect();

        webServer.shutdown();
    }

    /**
     * Tests the behaviour of HTTPS connection in case of unavailability of requested resource.
     */
    public void testHttpsConnection_Not_Found_Response() throws Throwable {
        // set up the properties pointing to the key/trust stores
        setUpStoreProperties();

        SSLContext ctx = getContext();

        // set the HostnameVerifier required to satisfy SSL - always returns "verified".
        HttpsURLConnection.setDefaultHostnameVerifier(new TestHostnameVerifier());

        // create a webserver to check and respond to requests
        SingleRequestDispatcher dispatcher =
                new SingleRequestDispatcher(GET_METHOD, NOT_FOUND_CODE);
        MockWebServer webServer = createWebServer(ctx, dispatcher);

        // create url connection to be tested
        URL url = webServer.getUrl("/");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(ctx.getSocketFactory());

        try {
            executeClientRequest(connection, false /* doOutput */);
            fail("Expected exception was not thrown.");
        } catch (FileNotFoundException e) {
            if (DO_LOG) {
                System.out.println("Expected exception was thrown: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // should silently exit
        connection.connect();

        webServer.shutdown();
    }

    /**
     * Tests possibility to set up the default SSLSocketFactory to be used by HttpsURLConnection.
     */
    public void testSetDefaultSSLSocketFactory() throws Throwable {
        // set up the properties pointing to the key/trust stores
        setUpStoreProperties();

        SSLContext ctx = getContext();

        SSLSocketFactory socketFactory = ctx.getSocketFactory();
        // set up the factory as default
        HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory);
        // check the result
        assertSame("Default SSLSocketFactory differs from expected",
                socketFactory, HttpsURLConnection.getDefaultSSLSocketFactory());

        // set the initial default host name verifier.
        TestHostnameVerifier initialHostnameVerifier = new TestHostnameVerifier();
        HttpsURLConnection.setDefaultHostnameVerifier(initialHostnameVerifier);

        // create a webserver to check and respond to requests
        SingleRequestDispatcher dispatcher = new SingleRequestDispatcher(GET_METHOD, OK_CODE);
        MockWebServer webServer = createWebServer(ctx, dispatcher);

        // create HttpsURLConnection to be tested
        URL url = webServer.getUrl("/");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        // late initialization: this HostnameVerifier should not be used for created connection
        TestHostnameVerifier lateHostnameVerifier = new TestHostnameVerifier();
        HttpsURLConnection.setDefaultHostnameVerifier(lateHostnameVerifier);

        // perform the interaction between the peers
        executeClientRequest(connection, false /* doOutput */);
        checkConnectionStateParameters(connection, dispatcher.getLastRequest());

        // check the verification process
        assertTrue("Hostname verification was not done", initialHostnameVerifier.verified);
        assertFalse("Hostname verification should not be done by this verifier",
                lateHostnameVerifier.verified);
        // check the used SSLSocketFactory
        assertSame("Default SSLSocketFactory should be used",
                HttpsURLConnection.getDefaultSSLSocketFactory(),
                connection.getSSLSocketFactory());

        webServer.shutdown();
    }

    /**
     * Tests
     * {@link javax.net.ssl.HttpsURLConnection#setSSLSocketFactory(javax.net.ssl.SSLSocketFactory)}.
     */
    public void testSetSSLSocketFactory() throws Throwable {
        // set up the properties pointing to the key/trust stores
        SSLContext ctx = getContext();

        // set the initial default host name verifier.
        TestHostnameVerifier hostnameVerifier = new TestHostnameVerifier();
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

        // create a webserver to check and respond to requests
        SingleRequestDispatcher dispatcher = new SingleRequestDispatcher(GET_METHOD, OK_CODE);
        MockWebServer webServer = createWebServer(ctx, dispatcher);

        // create HttpsURLConnection to be tested
        URL url = webServer.getUrl("/");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        // late initialization: should not be used for the created connection.
        SSLSocketFactory socketFactory = ctx.getSocketFactory();
        connection.setSSLSocketFactory(socketFactory);

        // late initialization: should not be used for created connection
        TestHostnameVerifier lateHostnameVerifier = new TestHostnameVerifier();
        HttpsURLConnection.setDefaultHostnameVerifier(lateHostnameVerifier);

        // perform the interaction between the peers
        executeClientRequest(connection, false /* doOutput */);
        checkConnectionStateParameters(connection, dispatcher.getLastRequest());
        // check the verification process
        assertTrue("Hostname verification was not done", hostnameVerifier.verified);
        assertFalse("Hostname verification should not be done by this verifier",
                lateHostnameVerifier.verified);
        // check the used SSLSocketFactory
        assertNotSame("Default SSLSocketFactory should not be used",
                HttpsURLConnection.getDefaultSSLSocketFactory(),
                connection.getSSLSocketFactory());
        assertSame("Result differs from expected", socketFactory, connection.getSSLSocketFactory());

        webServer.shutdown();
    }

    /**
     * Tests the behaviour of HttpsURLConnection in case of retrieving
     * of the connection state parameters before connection has been made.
     */
    public void testUnconnectedStateParameters() throws Throwable {
        // create HttpsURLConnection to be tested
        URL url = new URL("https://localhost:55555");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        try {
            connection.getCipherSuite();
            fail("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {}
        try {
            connection.getPeerPrincipal();
            fail("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {}
        try {
            connection.getLocalPrincipal();
            fail("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {}

        try {
            connection.getServerCertificates();
            fail("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {}
        try {
            connection.getLocalCertificates();
            fail("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {}
    }

    /**
     * Tests if setHostnameVerifier() method replaces default verifier.
     */
    public void testSetHostnameVerifier() throws Throwable {
        // set up the properties pointing to the key/trust stores
        setUpStoreProperties();

        SSLContext ctx = getContext();

        TestHostnameVerifier defaultHostnameVerifier = new TestHostnameVerifier();
        HttpsURLConnection.setDefaultHostnameVerifier(defaultHostnameVerifier);

        // create a webserver to check and respond to requests
        SingleRequestDispatcher dispatcher = new SingleRequestDispatcher(GET_METHOD, OK_CODE);
        MockWebServer webServer = createWebServer(ctx, dispatcher);

        // create HttpsURLConnection to be tested
        URL url = webServer.getUrl("/");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(getContext().getSocketFactory());

        // replace the default verifier
        TestHostnameVerifier connectionHostnameVerifier = new TestHostnameVerifier();
        connection.setHostnameVerifier(connectionHostnameVerifier);

        // perform the interaction between the peers and check the results
        executeClientRequest(connection, false /* doOutput */);
        assertTrue("Hostname verification was not done", connectionHostnameVerifier.verified);
        assertFalse("Hostname verification should not be done by this verifier",
                defaultHostnameVerifier.verified);

        checkConnectionStateParameters(connection, dispatcher.getLastRequest());

        webServer.shutdown();
    }

    /**
     * Tests the behaviour in case of sending the data to the server.
     */
    public void test_doOutput() throws Throwable {
        // set up the properties pointing to the key/trust stores
        setUpStoreProperties();

        SSLContext ctx = getContext();

        // create a webserver to check and respond to requests
        SingleRequestDispatcher dispatcher = new SingleRequestDispatcher(POST_METHOD, OK_CODE);
        MockWebServer webServer = createWebServer(ctx, dispatcher);

        // set the HostnameVerifier required to satisfy SSL - always returns "verified".
        HttpsURLConnection.setDefaultHostnameVerifier(new TestHostnameVerifier());

        // create HttpsURLConnection to be tested
        URL url = webServer.getUrl("/");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(getContext().getSocketFactory());

        // perform the interaction between the peers and check the results
        executeClientRequest(connection, true /* doOutput */);
        checkConnectionStateParameters(connection, dispatcher.getLastRequest());

        // should silently exit
        connection.connect();

        webServer.shutdown();
    }

    /**
     * Tests HTTPS connection process made through the proxy server.
     */
    public void testProxyConnection() throws Throwable {
        // set up the properties pointing to the key/trust stores
        setUpStoreProperties();

        SSLContext ctx = getContext();

        // set the HostnameVerifier required to satisfy SSL - always returns "verified".
        HttpsURLConnection.setDefaultHostnameVerifier(new TestHostnameVerifier());

        // create a server that pretends to be both a proxy and then the webserver
        // request 1: proxy CONNECT, respond with OK
        ProxyConnectDispatcher proxyConnectDispatcher =
                new ProxyConnectDispatcher(false /* authenticationRequired */);
        // request 2: tunnelled GET, respond with OK
        SingleRequestDispatcher getDispatcher = new SingleRequestDispatcher(GET_METHOD, OK_CODE);
        DelegatingDispatcher delegatingDispatcher =
                new DelegatingDispatcher(proxyConnectDispatcher, getDispatcher);
        MockWebServer proxyAndWebServer = createProxyAndWebServer(ctx, delegatingDispatcher);

        // create HttpsURLConnection to be tested
        URL proxyUrl = proxyAndWebServer.getUrl("/");
        InetSocketAddress proxyAddress = new InetSocketAddress("localhost", proxyUrl.getPort());
        URL url = new URL("https://requested.host:55556/requested.data");
        HttpsURLConnection connection = (HttpsURLConnection)
                url.openConnection(new Proxy(Proxy.Type.HTTP, proxyAddress));
        connection.setSSLSocketFactory(getContext().getSocketFactory());

        // perform the interaction between the peers and check the results
        executeClientRequest(connection, false /* doOutput */);
        checkConnectionStateParameters(connection, getDispatcher.getLastRequest());

        // should silently exit
        connection.connect();

        proxyAndWebServer.shutdown();
    }

    /**
     * Tests HTTPS connection process made through the proxy server.
     * Proxy server needs authentication.
     */
    public void testProxyAuthConnection() throws Throwable {
        // set up the properties pointing to the key/trust stores
        setUpStoreProperties();

        SSLContext ctx = getContext();

        // set the HostnameVerifier required to satisfy SSL - always returns "verified".
        HttpsURLConnection.setDefaultHostnameVerifier(new TestHostnameVerifier());

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("user", "password".toCharArray());
            }
        });

        // create a server that pretends to be both a proxy and then the webserver
        // request 1: proxy CONNECT, respond with auth challenge
        ProxyConnectAuthFailDispatcher authFailDispatcher = new ProxyConnectAuthFailDispatcher();
        // request 2: proxy CONNECT, respond with OK
        ProxyConnectDispatcher proxyConnectDispatcher =
                new ProxyConnectDispatcher(true /* authenticationRequired */);
        // request 3: tunnelled GET, respond with OK
        SingleRequestDispatcher getDispatcher = new SingleRequestDispatcher(GET_METHOD, OK_CODE);
        DelegatingDispatcher delegatingDispatcher = new DelegatingDispatcher(
                authFailDispatcher, proxyConnectDispatcher, getDispatcher);
        MockWebServer proxyAndWebServer = createProxyAndWebServer(ctx, delegatingDispatcher);

        // create HttpsURLConnection to be tested
        URL proxyUrl = proxyAndWebServer.getUrl("/");
        InetSocketAddress proxyAddress = new InetSocketAddress("localhost", proxyUrl.getPort());
        URL url = new URL("https://requested.host:55555/requested.data");
        HttpsURLConnection connection = (HttpsURLConnection)
                url.openConnection(new Proxy(Proxy.Type.HTTP, proxyAddress));
        connection.setSSLSocketFactory(getContext().getSocketFactory());

        // perform the interaction between the peers and check the results
        executeClientRequest(connection, false /* doOutput */);
        checkConnectionStateParameters(connection, getDispatcher.getLastRequest());

        // should silently exit
        connection.connect();

        proxyAndWebServer.shutdown();
    }

    /**
     * Tests HTTPS connection process made through the proxy server.
     * Two HTTPS connections are opened for one URL: the first time the connection is opened
     * through one proxy, the second time it is opened through another.
     */
    public void testConsequentProxyConnection() throws Throwable {
        // set up the properties pointing to the key/trust stores
        setUpStoreProperties();

        // set the HostnameVerifier required to satisfy SSL - always returns "verified".
        HttpsURLConnection.setDefaultHostnameVerifier(new TestHostnameVerifier());

        // create a server that pretends to be both a proxy and then the webserver
        SingleRequestDispatcher getDispatcher1 = new SingleRequestDispatcher(GET_METHOD, OK_CODE);
        MockWebServer proxyAndWebServer1 = createProxiedServer(getDispatcher1);

        // create HttpsURLConnection to be tested
        URL proxyUrl1 = proxyAndWebServer1.getUrl("/");
        URL url = new URL("https://requested.host:55555/requested.data");
        InetSocketAddress proxyAddress = new InetSocketAddress("localhost", proxyUrl1.getPort());
        HttpsURLConnection connection = (HttpsURLConnection)
                url.openConnection(new Proxy(Proxy.Type.HTTP, proxyAddress));
        connection.setSSLSocketFactory(getContext().getSocketFactory());
         executeClientRequest(connection, false /* doOutput */);
        checkConnectionStateParameters(connection, getDispatcher1.getLastRequest());

        proxyAndWebServer1.shutdown();

        // create another server
        SingleRequestDispatcher getDispatcher2 = new SingleRequestDispatcher(GET_METHOD, OK_CODE);
        MockWebServer proxyAndWebServer2 = createProxiedServer(getDispatcher2);

        // create another HttpsURLConnection to be tested
        URL proxyUrl2 = proxyAndWebServer2.getUrl("/");
        InetSocketAddress proxyAddress2 = new InetSocketAddress("localhost", proxyUrl2.getPort());
        HttpsURLConnection connection2 = (HttpsURLConnection) url.openConnection(
                new Proxy(Proxy.Type.HTTP, proxyAddress2));
        connection2.setSSLSocketFactory(getContext().getSocketFactory());

        // perform the interaction between the peers and check the results
        executeClientRequest(connection2, false /* doOutput */);
        checkConnectionStateParameters(connection2, getDispatcher2.getLastRequest());

        proxyAndWebServer2.shutdown();
    }

    private static MockWebServer createProxiedServer(Dispatcher getDispatcher)
            throws Exception {
        // request 1: proxy CONNECT, respond with OK
        ProxyConnectDispatcher proxyConnectDispatcher =
                new ProxyConnectDispatcher(false /* authenticationRequired */);
        // request 2: The get dispatcher.
        DelegatingDispatcher delegatingDispatcher1 =
                new DelegatingDispatcher(proxyConnectDispatcher, getDispatcher);
        return createProxyAndWebServer(getContext(), delegatingDispatcher1);
    }

    /**
     * Tests HTTPS connection process made through the proxy server.
     * Proxy server needs authentication.
     * Client sends data to the server.
     */
    public void testProxyAuthConnection_doOutput() throws Throwable {
        // set up the properties pointing to the key/trust stores
        setUpStoreProperties();

        SSLContext ctx = getContext();

        // set the HostnameVerifier required to satisfy SSL - always returns "verified".
        HttpsURLConnection.setDefaultHostnameVerifier(new TestHostnameVerifier());

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("user", "password".toCharArray());
            }
        });

        // create a server that pretends to be both a proxy and then the webserver
        // request 1: proxy CONNECT, respond with auth challenge
        ProxyConnectAuthFailDispatcher authFailDispatcher = new ProxyConnectAuthFailDispatcher();
        // request 2: proxy CONNECT, respond with OK
        ProxyConnectDispatcher proxyConnectDispatcher =
                new ProxyConnectDispatcher(true /* authenticationRequired */);
        // request 3: tunnelled POST, respond with OK
        SingleRequestDispatcher postDispatcher = new SingleRequestDispatcher(POST_METHOD, OK_CODE);
        DelegatingDispatcher delegatingDispatcher = new DelegatingDispatcher(
                authFailDispatcher, proxyConnectDispatcher, postDispatcher);
        MockWebServer proxyAndWebServer = createProxyAndWebServer(ctx, delegatingDispatcher);
        URL proxyUrl = proxyAndWebServer.getUrl("/");

        // create HttpsURLConnection to be tested
        InetSocketAddress proxyAddress = new InetSocketAddress("localhost", proxyUrl.getPort());
        HttpsURLConnection connection = (HttpsURLConnection)
                proxyUrl.openConnection(new Proxy(Proxy.Type.HTTP, proxyAddress));
        connection.setSSLSocketFactory(getContext().getSocketFactory());

        // perform the interaction between the peers and check the results
        executeClientRequest(connection, true /* doOutput */);
        checkConnectionStateParameters(connection, postDispatcher.getLastRequest());

        // should silently exit
        connection.connect();

        proxyAndWebServer.shutdown();
    }

    /**
     * Tests HTTPS connection process made through the proxy server.
     * Proxy server needs authentication but client fails to authenticate
     * (Authenticator was not set up in the system).
     */
    public void testProxyAuthConnectionFailed() throws Throwable {
        // set up the properties pointing to the key/trust stores
        setUpStoreProperties();

        // set the HostnameVerifier required to satisfy SSL - always returns "verified".
        HttpsURLConnection.setDefaultHostnameVerifier(new TestHostnameVerifier());

        // create a server that pretends to be both a proxy that requests authentication.
        MockWebServer proxyAndWebServer = new MockWebServer();
        ProxyConnectAuthFailDispatcher authFailDispatcher = new ProxyConnectAuthFailDispatcher();
        proxyAndWebServer.setDispatcher(authFailDispatcher);
        proxyAndWebServer.play();

        // create HttpsURLConnection to be tested
        URL proxyUrl = proxyAndWebServer.getUrl("/");
        InetSocketAddress proxyAddress = new InetSocketAddress("localhost", proxyUrl.getPort());
        URL url = new URL("https://requested.host:55555/requested.data");
        HttpsURLConnection connection = (HttpsURLConnection)
                url.openConnection(new Proxy(Proxy.Type.HTTP, proxyAddress));
        connection.setSSLSocketFactory(getContext().getSocketFactory());

        // perform the interaction between the peers and check the results
        try {
            executeClientRequest(connection, false);
        } catch (IOException e) {
            // SSL Tunnelling failed
            if (DO_LOG) {
                System.out.println("Got expected IOException: " + e.getMessage());
            }
        }
        proxyAndWebServer.shutdown();
    }

    /**
     * Tests the behaviour of HTTPS connection in case of unavailability of requested resource (as
     * reported by the target web server).
     */
    public void testProxyConnection_Not_Found_Response() throws Throwable {
        // set up the properties pointing to the key/trust stores
        setUpStoreProperties();

        SSLContext ctx = getContext();

        // set the HostnameVerifier required to satisfy SSL - always returns "verified".
        HttpsURLConnection.setDefaultHostnameVerifier(new TestHostnameVerifier());

        // create a server that pretends to be a proxy
        ProxyConnectDispatcher proxyConnectDispatcher =
                new ProxyConnectDispatcher(false /* authenticationRequired */);
        SingleRequestDispatcher notFoundDispatcher =
                new SingleRequestDispatcher(GET_METHOD, NOT_FOUND_CODE);
        DelegatingDispatcher delegatingDispatcher =
                new DelegatingDispatcher(proxyConnectDispatcher, notFoundDispatcher);
        MockWebServer proxyAndWebServer = createProxyAndWebServer(ctx, delegatingDispatcher);

        // create HttpsURLConnection to be tested
        URL proxyUrl = proxyAndWebServer.getUrl("/");
        InetSocketAddress proxyAddress = new InetSocketAddress("localhost", proxyUrl.getPort());
        URL url = new URL("https://requested.host:55555/requested.data");
        HttpsURLConnection connection = (HttpsURLConnection)
                url.openConnection(new Proxy(Proxy.Type.HTTP, proxyAddress));
        connection.setSSLSocketFactory(getContext().getSocketFactory());

        try {
            executeClientRequest(connection, false /* doOutput */);
            fail("Expected exception was not thrown.");
        } catch (FileNotFoundException e) {
            if (DO_LOG) {
                System.out.println("Expected exception was thrown: " + e.getMessage());
            }
        }
        proxyAndWebServer.shutdown();
    }

    public void setUp() throws Exception {
        super.setUp();

        if (DO_LOG) {
            // Log the name of the test case to be executed.
            System.out.println();
            System.out.println("------------------------");
            System.out.println("------ " + getName());
            System.out.println("------------------------");
        }

        if (store != null) {
            String ksFileName = "org/apache/harmony/luni/tests/key_store." +
                    KeyStore.getDefaultType().toLowerCase();
            InputStream in = getClass().getClassLoader().getResourceAsStream(ksFileName);
            FileOutputStream out = new FileOutputStream(store);
            BufferedInputStream bufIn = new BufferedInputStream(in, 8192);
            while (bufIn.available() > 0) {
                byte[] buf = new byte[128];
                int read = bufIn.read(buf);
                out.write(buf, 0, read);
            }
            bufIn.close();
            out.close();
        } else {
            fail("couldn't set up key store");
        }
    }

    public void tearDown() {
        if (store != null) {
            store.delete();
        }
    }

    private static void checkConnectionStateParameters(
            HttpsURLConnection connection, RecordedRequest request) throws Exception {
        assertEquals(request.getSslCipherSuite(), connection.getCipherSuite());
        assertEquals(request.getSslLocalPrincipal(), connection.getPeerPrincipal());
        assertEquals(request.getSslPeerPrincipal(), connection.getLocalPrincipal());

        Certificate[] serverCertificates = connection.getServerCertificates();
        Certificate[] localCertificates = request.getSslLocalCertificates();
        assertTrue("Server certificates differ from expected",
                Arrays.equals(serverCertificates, localCertificates));

        localCertificates = connection.getLocalCertificates();
        serverCertificates = request.getSslPeerCertificates();
        assertTrue("Local certificates differ from expected",
                Arrays.equals(serverCertificates, localCertificates));
    }

    /**
     * Returns the file name of the key/trust store. The key store file
     * (named as "key_store." + extension equals to the default KeyStore
     * type installed in the system in lower case) is searched in classpath.
     * @throws junit.framework.AssertionFailedError if property was not set
     * or file does not exist.
     */
    private static String getKeyStoreFileName() {
        return store.getAbsolutePath();
    }

    /**
     * Builds and returns the context used for secure socket creation.
     */
    private static SSLContext getContext() throws Exception {
        String type = KeyStore.getDefaultType();
        String keyStore = getKeyStoreFileName();
        File keyStoreFile = new File(keyStore);
        FileInputStream fis = new FileInputStream(keyStoreFile);

        KeyStore ks = KeyStore.getInstance(type);
        ks.load(fis, KS_PASSWORD.toCharArray());
        fis.close();
        if (DO_LOG && false) {
            TestKeyStore.dump("HttpsURLConnection.getContext", ks, KS_PASSWORD.toCharArray());
        }

        String kmfAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(kmfAlgorithm);
        kmf.init(ks, KS_PASSWORD.toCharArray());
        KeyManager[] keyManagers = kmf.getKeyManagers();

        String tmfAlgorthm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorthm);
        tmf.init(ks);
        TrustManager[] trustManagers = tmf.getTrustManagers();
        if (DO_LOG) {
            trustManagers = TestTrustManager.wrap(trustManagers);
        }

        SSLContext ctx = SSLContext.getInstance("TLSv1");
        ctx.init(keyManagers, trustManagers, null);
        return ctx;
    }

    /**
     * Sets up the properties pointing to the key store and trust store
     * and used as default values by JSSE staff. This is needed to test
     * HTTPS behaviour in the case of default SSL Socket Factories.
     */
    private static void setUpStoreProperties() throws Exception {
        String type = KeyStore.getDefaultType();

        System.setProperty("javax.net.ssl.keyStoreType", type);
        System.setProperty("javax.net.ssl.keyStore", getKeyStoreFileName());
        System.setProperty("javax.net.ssl.keyStorePassword", KS_PASSWORD);

        System.setProperty("javax.net.ssl.trustStoreType", type);
        System.setProperty("javax.net.ssl.trustStore", getKeyStoreFileName());
        System.setProperty("javax.net.ssl.trustStorePassword", KS_PASSWORD);
    }

    /**
     * The host name verifier used in test.
     */
    static class TestHostnameVerifier implements HostnameVerifier {

        boolean verified = false;

        public boolean verify(String hostname, SSLSession session) {
            if (DO_LOG) {
                System.out.println("***> verification " + hostname + " "
                                   + session.getPeerHost());
            }
            verified = true;
            return true;
        }
    }

    /**
     * Creates a {@link MockWebServer} that acts as both a proxy and then a web server with the
     * supplied {@link SSLContext} and {@link Dispatcher}. The dispatcher provided must handle the
     * CONNECT request/responses and {@link SocketPolicy} needed to simulate the hand-off from proxy
     * to web server. See {@link HttpsURLConnectionTest.ProxyConnectDispatcher}.
     */
    private static MockWebServer createProxyAndWebServer(SSLContext ctx, Dispatcher dispatcher)
            throws IOException {
        return createServer(ctx, dispatcher, true /* handleProxying */);
    }

    /**
     * Creates a {@link MockWebServer} that acts as (only) a web server with the supplied
     * {@link SSLContext} and {@link Dispatcher}.
     */
    private static MockWebServer createWebServer(SSLContext ctx, Dispatcher dispatcher)
            throws IOException {
        return createServer(ctx, dispatcher, false /* handleProxying */);
    }

    private static MockWebServer createServer(
            SSLContext ctx, Dispatcher dispatcher, boolean handleProxying)
            throws IOException {
        MockWebServer webServer = new MockWebServer();
        webServer.useHttps(ctx.getSocketFactory(), handleProxying /* tunnelProxy */);
        webServer.setDispatcher(dispatcher);
        webServer.play();
        return webServer;
    }

    /**
     * A {@link Dispatcher} that has a list of dispatchers to delegate to, each of which will be
     * used for one request and then discarded.
     */
    private static class DelegatingDispatcher extends Dispatcher {
        private LinkedList<Dispatcher> delegates = new LinkedList<Dispatcher>();

        public DelegatingDispatcher(Dispatcher... dispatchers) {
            addAll(dispatchers);
        }

        private void addAll(Dispatcher... dispatchers) {
            Collections.addAll(delegates, dispatchers);
        }

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            return delegates.removeFirst().dispatch(request);
        }

        @Override
        public MockResponse peek() {
            return delegates.getFirst().peek();
        }
    }

    /** Handles a request for SSL tunnel: Answers with a request to authenticate. */
    private static class ProxyConnectAuthFailDispatcher extends Dispatcher {

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            assertEquals("CONNECT", request.getMethod());

            MockResponse response = new MockResponse();
            response.setResponseCode(AUTHENTICATION_REQUIRED_CODE);
            response.addHeader("Proxy-authenticate: Basic realm=\"localhost\"");
            log("Authentication required. Sending response: " + response);
            return response;
        }

        private void log(String msg) {
            HttpsURLConnectionTest.log("ProxyConnectAuthFailDispatcher", msg);
        }
    }

    /**
     * Handles a request for SSL tunnel: Answers with a success and the socket is upgraded to SSL.
     */
    private static class ProxyConnectDispatcher extends Dispatcher {

        private final boolean authenticationRequired;

        private ProxyConnectDispatcher(boolean authenticationRequired) {
            this.authenticationRequired = authenticationRequired;
        }

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            if (authenticationRequired) {
                // check provided authorization credentials
                assertNotNull("no proxy-authorization credentials: " + request,
                        request.getHeader("proxy-authorization"));
                log("Got authenticated request:\n" + request);
                log("------------------");
            }

            assertEquals("CONNECT", request.getMethod());
            log("Send proxy response");
            MockResponse response = new MockResponse();
            response.setResponseCode(200);
            response.setSocketPolicy(SocketPolicy.UPGRADE_TO_SSL_AT_END);
            return response;
        }

        @Override
        public MockResponse peek() {
            return new MockResponse().setSocketPolicy(SocketPolicy.UPGRADE_TO_SSL_AT_END);
        }

        private void log(String msg) {
            HttpsURLConnectionTest.log("ProxyConnectDispatcher", msg);
        }
    }

    /**
     * Handles a request: Answers with a response with a specified status code.
     * If the {@code expectedMethod} is {@code POST} a hardcoded response body {@link #POST_DATA}
     * will be included in the response.
     */
    private static class SingleRequestDispatcher extends Dispatcher {

        private final String expectedMethod;
        private final int responseCode;

        private RecordedRequest lastRequest;

        private SingleRequestDispatcher(String expectedMethod, int responseCode) {
            this.responseCode = responseCode;
            this.expectedMethod = expectedMethod;
        }

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            if (lastRequest != null) {
                fail("More than one request received");
            }
            log("Request received: " + request);
            lastRequest = request;
            assertEquals(expectedMethod, request.getMethod());
            if (POST_METHOD.equals(expectedMethod)) {
                assertEquals(POST_DATA, request.getUtf8Body());
            }

            MockResponse response = new MockResponse();
            response.setResponseCode(responseCode);
            response.setBody(RESPONSE_CONTENT);

            log("Responding with: " + response);
            return response;
        }

        public RecordedRequest getLastRequest() {
            return lastRequest;
        }

        @Override
        public MockResponse peek() {
            return new MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_END);
        }

        private void log(String msg) {
            HttpsURLConnectionTest.log("SingleRequestDispatcher", msg);
        }
    }

    /**
     * Executes an HTTP request using the supplied connection. If {@code doOutput} is {@code true}
     * the request made is a POST and the request body sent is {@link #POST_DATA}.
     * If {@code doOutput} is {@code false} the request made is a GET. The response must be a
     * success with a body {@link #RESPONSE_CONTENT}.
     */
    private static void executeClientRequest(
            HttpsURLConnection connection, boolean doOutput) throws IOException {

        // set up the connection
        connection.setDoInput(true);
        connection.setConnectTimeout(TIMEOUT);
        connection.setReadTimeout(TIMEOUT);
        connection.setDoOutput(doOutput);

        log("Client", "Opening the connection to " + connection.getURL());
        connection.connect();
        log("Client", "Connection has been ESTABLISHED, using proxy: " + connection.usingProxy());
        if (doOutput) {
            log("Client", "Posting data");
            // connection configured to post data, do so
            OutputStream os = connection.getOutputStream();
            os.write(POST_DATA.getBytes());
        }
        // read the content of HTTP(s) response
        InputStream is = connection.getInputStream();
        log("Client", "Input Stream obtained");
        byte[] buff = new byte[2048];
        int num = 0;
        int byt;
        while ((num < buff.length) && ((byt = is.read()) != -1)) {
            buff[num++] = (byte) byt;
        }
        String message = new String(buff, 0, num);
        log("Client", "Got content:\n" + message);
        log("Client", "------------------");
        log("Client", "Response code: " + connection.getResponseCode());
        assertEquals(RESPONSE_CONTENT, message);
    }

    /**
     * Prints log message.
     */
    public static synchronized void log(String origin, String message) {
        if (DO_LOG) {
            System.out.println("[" + origin + "]: " + message);
        }
    }
}
