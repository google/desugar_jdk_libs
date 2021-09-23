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

import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;

/**
 */
public class FakeSSLSocketFactory extends SSLSocketFactory {

    public FakeSSLSocketFactory() {
    }

    @Override
    public String[] getDefaultCipherSuites() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress,
            int localPort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Socket createSocket(InetAddress host, int port) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Socket createSocket(String host, int port) {
        throw new UnsupportedOperationException();
    }
}
