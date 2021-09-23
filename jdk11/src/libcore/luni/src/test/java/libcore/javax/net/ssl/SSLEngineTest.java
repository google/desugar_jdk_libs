/*
 * Copyright (C) 2018 The Android Open Source Project
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

import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import junit.framework.TestCase;

public class SSLEngineTest extends TestCase {
  /**
   * A basic SSLEngine that has no behavior beyond that of the base class.
   */
  private static class PlainSSLEngine extends SSLEngine {
    @Override public SSLEngineResult wrap(ByteBuffer[] byteBuffers, int i, int i1,
        ByteBuffer byteBuffer) throws SSLException { return null; }
    @Override public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBuffers,
        int i, int i1) throws SSLException { return null; }
    @Override public Runnable getDelegatedTask() { return null; }
    @Override public void closeInbound() throws SSLException {}
    @Override public boolean isInboundDone() {  return false; }
    @Override public void closeOutbound() {}
    @Override public boolean isOutboundDone() { return false; }
    @Override public String[] getSupportedCipherSuites() { return new String[0]; }
    @Override public String[] getEnabledCipherSuites() { return new String[0]; }
    @Override public void setEnabledCipherSuites(String[] strings) {}
    @Override public String[] getSupportedProtocols() { return new String[0]; }
    @Override public String[] getEnabledProtocols() { return new String[0]; }
    @Override public void setEnabledProtocols(String[] strings) {}
    @Override public SSLSession getSession() { return null; }
    @Override public void beginHandshake() throws SSLException {}
    @Override public HandshakeStatus getHandshakeStatus() { return null; }
    @Override public void setUseClientMode(boolean b) {}
    @Override public boolean getUseClientMode() { return false; }
    @Override public void setNeedClientAuth(boolean b) {}
    @Override public boolean getNeedClientAuth() { return false; }
    @Override public void setWantClientAuth(boolean b) {}
    @Override public boolean getWantClientAuth() { return false; }
    @Override public void setEnableSessionCreation(boolean b) {}
    @Override public boolean getEnableSessionCreation() { return false; }
  }

  public void test_Alpn() throws Exception {
    SSLEngine engine = new PlainSSLEngine();
    try {
      engine.getApplicationProtocol();
      fail();
    } catch (UnsupportedOperationException expected) {
    }
    try {
      engine.getHandshakeApplicationProtocol();
      fail();
    } catch (UnsupportedOperationException expected) {
    }
    try {
      engine.setHandshakeApplicationProtocolSelector(
          new BiFunction<SSLEngine, List<String>, String>() {
            @Override
            public String apply(SSLEngine sslEngine, List<String> strings) {
              return "";
            }
          });
      fail();
    } catch (UnsupportedOperationException expected) {
    }
    try {
      engine.getHandshakeApplicationProtocolSelector();
      fail();
    } catch (UnsupportedOperationException expected) {
    }
  }
}
