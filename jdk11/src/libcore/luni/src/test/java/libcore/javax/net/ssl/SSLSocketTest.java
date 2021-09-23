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

import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import junit.framework.TestCase;

public class SSLSocketTest extends TestCase {

    /**
     * A basic SSLSocket that has no behavior beyond that of the base class.
     */
    private static class PlainSSLSocket extends SSLSocket {
        @Override public String[] getSupportedCipherSuites() { return new String[0]; }
        @Override public String[] getEnabledCipherSuites() { return new String[0]; }
        @Override public void setEnabledCipherSuites(String[] strings) { }
        @Override public String[] getSupportedProtocols() { return new String[0]; }
        @Override public String[] getEnabledProtocols() { return new String[0]; }
        @Override public void setEnabledProtocols(String[] strings) { }
        @Override public SSLSession getSession() { return null; }
        @Override public void addHandshakeCompletedListener(
            HandshakeCompletedListener handshakeCompletedListener) { }
        @Override public void removeHandshakeCompletedListener(
            HandshakeCompletedListener handshakeCompletedListener) { }
        @Override public void startHandshake() throws IOException { }
        @Override public void setUseClientMode(boolean b) { }
        @Override public boolean getUseClientMode() { return false; }
        @Override public void setNeedClientAuth(boolean b) { }
        @Override public boolean getNeedClientAuth() { return false; }
        @Override public void setWantClientAuth(boolean b) { }
        @Override public boolean getWantClientAuth() { return false; }
        @Override public void setEnableSessionCreation(boolean b) { }
        @Override public boolean getEnableSessionCreation() { return false; }
    }

    // We modified the toString() of SSLSocket, and it's based on the output
    // of Socket.toString(), so we want to make sure that a change in
    // Socket.toString() doesn't cause us to output nonsense.
    public void test_SSLSocket_toString() throws Exception {
        // The actual implementation from a security provider might do something
        // special for its toString(), so we create our own implementation
        SSLSocket socket = new PlainSSLSocket();
        assertTrue(socket.toString().startsWith("SSLSocket["));
    }

    /**
     * Not run by default by JUnit, but can be run by Vogar by
     * specifying it explicitly (or with main method below)
     */
    public void stress_test_TestSSLSocketPair_create() {
        final boolean verbose = true;
        while (true) {
            TestSSLSocketPair test = TestSSLSocketPair.create();
            if (verbose) {
                System.out.println("client=" + test.client.getLocalPort()
                                   + " server=" + test.server.getLocalPort());
            } else {
                System.out.print("X");
            }

            /*
              We don't close on purpose in this stress test to add
              races in file descriptors reuse when the garbage
              collector runs concurrently and finalizes sockets
            */
            // test.close();

        }
    }

    public void test_Alpn() throws Exception {
        SSLSocket socket = new PlainSSLSocket();
        try {
            socket.getApplicationProtocol();
            fail();
        } catch (UnsupportedOperationException expected) {
        }
        try {
            socket.getHandshakeApplicationProtocol();
            fail();
        } catch (UnsupportedOperationException expected) {
        }
        try {
            socket.setHandshakeApplicationProtocolSelector(
                new BiFunction<SSLSocket, List<String>, String>() {
                    @Override
                    public String apply(SSLSocket sslSocket, List<String> strings) {
                        return "";
                    }
                });
            fail();
        } catch (UnsupportedOperationException expected) {
        }
        try {
            socket.getHandshakeApplicationProtocolSelector();
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }

    public static void main (String[] args) {
        new SSLSocketTest().stress_test_TestSSLSocketPair_create();
    }
}
