/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.harmony.tests.javax.net.ssl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyManagementException;

import java.security.KeyStore;
import java.security.SecureRandom;

import org.apache.harmony.xnet.tests.support.SSLContextSpiImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SSLContextSpiTest {

    /**
     * javax.net.ssl.SSLContextSpi#SSLContextSpi()
     */
    @Test
    public void constructor() {
        try {
            SSLContextSpiImpl ssl = new SSLContextSpiImpl();
            assertTrue(ssl instanceof SSLContextSpi);
        } catch (Exception e) {
            fail("Unexpected exception " + e.toString());
        }
    }

    /**
     * javax.net.ssl.SSLContextSpi#engineCreateSSLEngine()
     * Verify exception when SSLContextSpi object wasn't initialiazed.
     */
    @Test
    public void engineCreateSSLEngine_01() {
        SSLContextSpiImpl ssl = new SSLContextSpiImpl();
        try {
            SSLEngine sleng = ssl.engineCreateSSLEngine();
            fail("RuntimeException wasn't thrown");
        } catch (RuntimeException re) {
            String str = re.getMessage();
            if (!str.equals("Not initialiazed"))
                fail("Incorrect exception message: " + str);
        } catch (Exception e) {
            fail("Incorrect exception " + e + " was thrown");
        }
    }

    /**
     * javax.net.ssl.SSLContextSpi#engineCreateSSLEngine(String host, int port)
     * Verify exception when SSLContextSpi object wasn't initialiazed.
     */
    @Test
    public void engineCreateSSLEngine_02() {
        int[] invalid_port = {Integer.MIN_VALUE, -65535, -1, 65536, Integer.MAX_VALUE};
        SSLContextSpiImpl ssl = new SSLContextSpiImpl();
        try {
            SSLEngine sleng = ssl.engineCreateSSLEngine("localhost", 1080);
            fail("RuntimeException wasn't thrown");
        } catch (RuntimeException re) {
            String str = re.getMessage();
            if (!str.equals("Not initialiazed"))
                fail("Incorrect exception message: " + str);
        } catch (Exception e) {
            fail("Incorrect exception " + e + " was thrown");
        }

        for (int i = 0; i < invalid_port.length; i++) {
            try {
                SSLEngine sleng = ssl.engineCreateSSLEngine("localhost", invalid_port[i]);
                fail("IllegalArgumentException wasn't thrown");
            } catch (IllegalArgumentException iae) {
                //expected
            }
        }
    }

    /**
     * SSLContextSpi#engineGetClientSessionContext()
     * SSLContextSpi#engineGetServerSessionContext()
     * SSLContextSpi#engineGetServerSocketFactory()
     * SSLContextSpi#engineGetSocketFactory()
     * Verify exception when SSLContextSpi object wasn't initialiazed.
     */
    @Test
    public void commonTest_01() {
        SSLContextSpiImpl ssl = new SSLContextSpiImpl();

        try {
            SSLSessionContext slsc = ssl.engineGetClientSessionContext();
            fail("RuntimeException wasn't thrown");
        } catch (RuntimeException re) {
            String str = re.getMessage();
            if (!str.equals("Not initialiazed"))
                fail("Incorrect exception message: " + str);
        } catch (Exception e) {
            fail("Incorrect exception " + e + " was thrown");
        }

        try {
            SSLSessionContext slsc = ssl.engineGetServerSessionContext();
            fail("RuntimeException wasn't thrown");
        } catch (RuntimeException re) {
            String str = re.getMessage();
            if (!str.equals("Not initialiazed"))
                fail("Incorrect exception message: " + str);
        } catch (Exception e) {
            fail("Incorrect exception " + e + " was thrown");
        }

        try {
            SSLServerSocketFactory sssf = ssl.engineGetServerSocketFactory();
            fail("RuntimeException wasn't thrown");
        } catch (RuntimeException re) {
            String str = re.getMessage();
            if (!str.equals("Not initialiazed"))
                fail("Incorrect exception message: " + str);
        } catch (Exception e) {
            fail("Incorrect exception " + e + " was thrown");
        }

        try {
            SSLSocketFactory ssf = ssl.engineGetSocketFactory();
            fail("RuntimeException wasn't thrown");
        } catch (RuntimeException re) {
            String str = re.getMessage();
            if (!str.equals("Not initialiazed"))
                fail("Incorrect exception message: " + str);
        } catch (Exception e) {
            fail("Incorrect exception " + e + " was thrown");
        }
    }

    /**
     * SSLContextSpi#engineInit(KeyManager[] km, TrustManager[] tm, SecureRandom sr)
     */
    @Test
    public void engineInit() throws Exception {
        SSLContextSpiImpl ssl = new SSLContextSpiImpl();
        KeyManager[] km = getKeyManagers();
        TrustManager[] tm = getTrustManagers();
        SecureRandom sr = getSecureRandom();
        ssl.engineInit(km, tm, sr);

        try {
            ssl.engineInit(km, tm, null);
            fail("KeyManagementException wasn't thrown");
        } catch (KeyManagementException kme) {
            //expected
        }
    }

    /**
     * SSLContextSpi#engineCreateSSLEngine()
     * SSLContextSpi#engineCreateSSLEngine(String host, int port)
     * SSLContextSpi#engineGetClientSessionContext()
     * SSLContextSpi#engineGetServerSessionContext()
     * SSLContextSpi#engineGetServerSocketFactory()
     * SSLContextSpi#engineGetSocketFactory()
     */
    @Test
    public void commonTest_02() throws Exception {
        SSLContextSpiImpl ssl = new SSLContextSpiImpl();
        ssl.engineInit(getKeyManagers(), getTrustManagers(), getSecureRandom());

        assertNotNull("Subtest_01: Object is NULL", ssl.engineCreateSSLEngine());
        SSLEngine sleng = ssl.engineCreateSSLEngine("localhost", 1080);
        assertNotNull("Subtest_02: Object is NULL", sleng);
        assertEquals(sleng.getPeerPort(), 1080);
        assertEquals(sleng.getPeerHost(), "localhost");
        assertNull("Subtest_03: Object not NULL", ssl.engineGetClientSessionContext());
        assertNull("Subtest_04: Object not NULL", ssl.engineGetServerSessionContext());
        assertNull("Subtest_05: Object not NULL", ssl.engineGetServerSocketFactory());
        assertNull("Subtest_06: Object not NULL", ssl.engineGetSocketFactory());
    }

    private static class SpiWithSocketFactory extends SSLContextSpiImpl {
        @Override
        public SSLSocketFactory engineGetSocketFactory() {
            super.engineGetSocketFactory();
            return (SSLSocketFactory) SSLSocketFactory.getDefault();
        }
    }

    /**
     * Tests the default implementations of SSLContextSpi.engineGetDefaultSSLParameters()
     * and SSLContextSpi.engineGetSupportedSSLParameters().  Requires a subclass which
     * returns non-null from engineGetSocketFactory() for the base class to work.
     *
     * Verifies the returned SSLParameters for consistency.
     */
    @Test
    public void getSslParameters() throws Exception {
        SpiWithSocketFactory spi = new SpiWithSocketFactory();
        spi.engineInit(getKeyManagers(), getTrustManagers(), getSecureRandom());

        SSLParameters defaultParams = spi.engineGetDefaultSSLParameters();
        assertNotNull(defaultParams);
        String[] protocols = defaultParams.getProtocols();
        assertNotNull(protocols);
        assertTrue(protocols.length > 0);
        String[] cipherSuites = defaultParams.getCipherSuites();
        assertNotNull(cipherSuites);
        assertTrue(cipherSuites.length > 0);

        SSLParameters supportedParams = spi.engineGetSupportedSSLParameters();
        assertNotNull(supportedParams);
        protocols = supportedParams.getProtocols();
        assertNotNull(protocols);
        assertTrue(protocols.length > 0);
        cipherSuites = supportedParams.getCipherSuites();
        assertNotNull(cipherSuites);
        assertTrue(cipherSuites.length > 0);
    }

    private SecureRandom getSecureRandom() throws Exception {
        return SecureRandom.getInstance("SHA1PRNG");
    }

    private TrustManager[] getTrustManagers() throws Exception {
        TrustManagerFactory tmf = TrustManagerFactory
            .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        tmf.init(ks);
        return tmf.getTrustManagers();
    }

    private KeyManager[] getKeyManagers() throws Exception {
        KeyManagerFactory kmf = KeyManagerFactory
            .getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(null, "password".toCharArray());
        return kmf.getKeyManagers();
    }
}
