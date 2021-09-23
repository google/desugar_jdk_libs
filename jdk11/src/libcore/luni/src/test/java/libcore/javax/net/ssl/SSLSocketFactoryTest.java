/*
 * Copyright (C) 2010 The Android Open Source Project
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

package libcore.javax.net.ssl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.io.InputStream;
import java.net.Socket;
import java.security.Provider;
import java.security.Security;
import java.util.Properties;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import junit.framework.TestCase;
import libcore.java.security.StandardNames;

import static org.junit.Assert.assertNotEquals;

public class SSLSocketFactoryTest extends TestCase {
    private static final String SSL_PROPERTY = "ssl.SocketFactory.provider";

    public void test_SSLSocketFactory_getDefault_cacheInvalidate() throws Exception {
        String origProvider = resetSslProvider();
        try {
            SocketFactory sf1 = SSLSocketFactory.getDefault();
            assertNotNull(sf1);
            assertTrue(SSLSocketFactory.class.isAssignableFrom(sf1.getClass()));

            Provider fakeProvider = new FakeSSLSocketProvider();
            SocketFactory sf4;
            SSLContext origContext = null;
            try {
                origContext = SSLContext.getDefault();
                Security.insertProviderAt(fakeProvider, 1);
                SSLContext.setDefault(SSLContext.getInstance("Default", fakeProvider));

                sf4 = SSLSocketFactory.getDefault();
                assertNotNull(sf4);
                assertTrue(SSLSocketFactory.class.isAssignableFrom(sf4.getClass()));

                assertNotEquals(sf1.getClass(), sf4.getClass());
            } finally {
                SSLContext.setDefault(origContext);
                Security.removeProvider(fakeProvider.getName());
            }

            SocketFactory sf3 = SSLSocketFactory.getDefault();
            assertNotNull(sf3);
            assertTrue(SSLSocketFactory.class.isAssignableFrom(sf3.getClass()));

            assertEquals(sf1.getClass(), sf3.getClass());

            if (!StandardNames.IS_RI) {
                Security.setProperty(SSL_PROPERTY, FakeSSLSocketFactory.class.getName());
                SocketFactory sf2 = SSLSocketFactory.getDefault();
                assertNotNull(sf2);
                assertTrue(SSLSocketFactory.class.isAssignableFrom(sf2.getClass()));

                assertNotEquals(sf1.getClass(), sf2.getClass());
                assertEquals(sf2.getClass(), sf4.getClass());

                resetSslProvider();
            }
        } finally {
            Security.setProperty(SSL_PROPERTY, origProvider);
        }
    }

    /**
     * Should only run on Android.
     */
    private String resetSslProvider() {
        String origProvider = Security.getProperty(SSL_PROPERTY);

        try {
            Field field_secprops = Security.class.getDeclaredField("props");
            field_secprops.setAccessible(true);
            Properties secprops = (Properties) field_secprops.get(null);
            secprops.remove(SSL_PROPERTY);
            Method m_increaseVersion = Security.class.getDeclaredMethod("increaseVersion");
            m_increaseVersion.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not clear security provider", e);
        }

        assertNull(Security.getProperty(SSL_PROPERTY));
        return origProvider;
    }


    public void test_SSLSocketFactory_createSocket_withConsumedInputStream()
            throws Exception {
        try {
            SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
            Socket sslSocket = sf.createSocket(null, (InputStream) null, false);
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }
}
