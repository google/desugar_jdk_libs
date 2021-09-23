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

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

/**
 */
public class FakeSSLServerSocketFactory extends SSLServerSocketFactory {

    public FakeSSLServerSocketFactory() {
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
    public ServerSocket createServerSocket(int port) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServerSocket createServerSocket(int port, int backlog) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress)
            throws IOException {
        throw new UnsupportedOperationException();
    }
}
