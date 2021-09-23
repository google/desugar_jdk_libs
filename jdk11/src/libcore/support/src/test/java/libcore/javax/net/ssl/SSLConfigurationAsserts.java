/*
 * Copyright (C) 2013 The Android Open Source Project
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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import junit.framework.Assert;

/**
 * Assertions about the configuration of TLS/SSL primitives.
 */
public class SSLConfigurationAsserts extends Assert {

  /** Hidden constructor to prevent instantiation. */
  private SSLConfigurationAsserts() {}

  /**
   * Asserts that the provided {@link SSLSocketFactory} has the expected default configuration and
   * that {@link SSLSocket} instances created by the factory match the configuration.
   */
  public static void assertSSLSocketFactoryDefaultConfiguration(
      SSLSocketFactory sslSocketFactory) throws Exception {
    assertSSLSocketFactoryConfigSameAsSSLContext(sslSocketFactory,
        SSLContext.getDefault());
  }

  /**
   * Asserts that {@link SSLSocketFactory}'s configuration matches {@code SSLContext}'s
   * configuration, and that {@link SSLSocket} instances obtained from the factory match this
   * configuration as well.
   */
  private static void assertSSLSocketFactoryConfigSameAsSSLContext(
      SSLSocketFactory sslSocketFactory, SSLContext sslContext) throws IOException {
    assertCipherSuitesEqual(sslContext.getDefaultSSLParameters().getCipherSuites(),
        sslSocketFactory.getDefaultCipherSuites());
    assertCipherSuitesEqual(sslContext.getSupportedSSLParameters().getCipherSuites(),
        sslSocketFactory.getSupportedCipherSuites());

    try (SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket()) {
      assertTrue(sslSocket.getUseClientMode());
      assertTrue(sslSocket.getEnableSessionCreation());
      assertSSLSocketConfigSameAsSSLContext(sslSocket, sslContext);
    }
  }

  /**
   * Asserts that {@link SSLSocket}'s configuration matches {@code SSLContext's} configuration.
   */
  private static void assertSSLSocketConfigSameAsSSLContext(SSLSocket sslSocket,
      SSLContext sslContext) {
    assertSSLParametersEqual(sslSocket.getSSLParameters(), sslContext.getDefaultSSLParameters());
    assertCipherSuitesEqual(sslSocket.getEnabledCipherSuites(),
        sslContext.getDefaultSSLParameters().getCipherSuites());
    assertProtocolsEqual(sslSocket.getEnabledProtocols(),
        sslContext.getDefaultSSLParameters().getProtocols());

    assertCipherSuitesEqual(sslSocket.getSupportedCipherSuites(),
        sslContext.getSupportedSSLParameters().getCipherSuites());
    assertProtocolsEqual(sslSocket.getSupportedProtocols(),
        sslContext.getSupportedSSLParameters().getProtocols());
  }

  private static void assertSSLParametersEqual(SSLParameters expected, SSLParameters actual) {
    assertCipherSuitesEqual(expected.getCipherSuites(), actual.getCipherSuites());
    assertProtocolsEqual(expected.getProtocols(), actual.getProtocols());
    assertEquals(expected.getNeedClientAuth(), actual.getNeedClientAuth());
    assertEquals(expected.getWantClientAuth(), actual.getWantClientAuth());
  }

  private static void assertCipherSuitesEqual(String[] expected, String[] actual) {
    assertEquals(Arrays.asList(expected), Arrays.asList(actual));
  }

  private static void assertProtocolsEqual(String[] expected, String[] actual) {
    // IMPLEMENTATION NOTE: The order of protocols versions does not matter. Similarly, it only
    // matters whether a protocol version is present or absent in the array. These arrays are
    // supposed to represent sets of protocol versions. Thus, we treat them as such.
    assertEquals(new HashSet<String>(Arrays.asList(expected)),
        new HashSet<String>(Arrays.asList(actual)));
  }
}
