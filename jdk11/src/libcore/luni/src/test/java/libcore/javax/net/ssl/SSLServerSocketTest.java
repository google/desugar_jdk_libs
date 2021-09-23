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

import javax.net.ssl.SSLServerSocket;
import junit.framework.TestCase;

public class SSLServerSocketTest extends TestCase {

    // We modified the toString() of SSLServerSocket, and it's based on the output
    // of ServerSocket.toString(), so we want to make sure that a change in
    // ServerSocket.toString() doesn't cause us to output nonsense.
    public void testToString() throws Exception {
        // The actual implementation from a security provider might do something
        // special for its toString(), so we create our own implementation
        SSLServerSocket socket = new SSLServerSocket() {
            @Override public String[] getEnabledCipherSuites() { return new String[0]; }
            @Override public void setEnabledCipherSuites(String[] strings) { }
            @Override public String[] getSupportedCipherSuites() { return new String[0]; }
            @Override public String[] getSupportedProtocols() { return new String[0]; }
            @Override public String[] getEnabledProtocols() { return new String[0]; }
            @Override public void setEnabledProtocols(String[] strings) { }
            @Override public void setNeedClientAuth(boolean b) { }
            @Override public boolean getNeedClientAuth() { return false; }
            @Override public void setWantClientAuth(boolean b) { }
            @Override public boolean getWantClientAuth() { return false; }
            @Override public void setUseClientMode(boolean b) { }
            @Override public boolean getUseClientMode() { return false; }
            @Override public void setEnableSessionCreation(boolean b) { }
            @Override public boolean getEnableSessionCreation() { return false; }
        };
        assertTrue(socket.toString().startsWith("SSLServerSocket["));
    }
}
