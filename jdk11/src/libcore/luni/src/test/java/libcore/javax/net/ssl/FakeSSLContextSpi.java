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

import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 */
public final class FakeSSLContextSpi extends SSLContextSpi {

    @Override
    protected void engineInit(KeyManager[] keyManagers, TrustManager[] trustManagers,
            SecureRandom secureRandom) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected SSLSocketFactory engineGetSocketFactory() {
        return new FakeSSLSocketFactory();
    }

    @Override
    protected SSLServerSocketFactory engineGetServerSocketFactory() {
        return new FakeSSLServerSocketFactory();
    }

    @Override
    protected SSLEngine engineCreateSSLEngine(String s, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected SSLEngine engineCreateSSLEngine() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected SSLSessionContext engineGetServerSessionContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected SSLSessionContext engineGetClientSessionContext() {
        throw new UnsupportedOperationException();
    }
}
