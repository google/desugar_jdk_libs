/*
 * Copyright (C) 2017 The Android Open Source Project
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

package libcore.java.security;

import com.android.org.conscrypt.PSKKeyManager;

import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * Prints a list of all algorithms provided by security providers.  Intended to be run
 * via vogar as part of the algorithm documentation update process.
 * <p>
 * {@code vogar libcore/tools/src/java/libcore/java/security/ListProviders.java}
 */
public class ListProviders {

    private static final boolean SHOW_PROVIDER = false;

    // These algorithms were previously provided, but now are aliases for a different
    // algorithm.  For documentation purposes, we want to continue having them show up
    // as supported.
    private static final Set<String> KNOWN_ALIASES = new TreeSet<>(Arrays.asList(new String[]{
            "Alg.Alias.Signature.DSA",
            "Alg.Alias.Signature.DSAwithSHA1",
            "Alg.Alias.Signature.ECDSA",
            "Alg.Alias.Signature.ECDSAwithSHA1",
    }));

    // Ciphers come in algorithm/mode/padding combinations, and not all combinations are explicitly
    // registered by the providers (sometimes only the base algorithm is registered).  While there
    // is a mechanism for providers to specify which modes and/or paddings are supported for a
    // given algorithm, none of our providers use it.  Thus, when a base algorithm is seen, all
    // combinations of modes and paddings will be tried to see which ones are supported.
    private static final Set<String> CIPHER_MODES = new TreeSet<>(Arrays.asList(new String[]{
            "CBC",
            "CFB",
            "CTR",
            "CTS",
            "ECB",
            "GCM",
            "OFB",
            "NONE",
    }));
    private static final Set<String> CIPHER_PADDINGS = new TreeSet<>(Arrays.asList(new String[]{
            "NoPadding",
            "OAEPPadding",
            "OAEPwithSHA-1andMGF1Padding",
            "OAEPwithSHA-224andMGF1Padding",
            "OAEPwithSHA-256andMGF1Padding",
            "OAEPwithSHA-384andMGF1Padding",
            "OAEPwithSHA-512andMGF1Padding",
            "PKCS1Padding",
            "PKCS5Padding",
            "ISO10126Padding",
    }));

    private static void print(Provider p, String type, String algorithm) {
        System.out.println((SHOW_PROVIDER ? p.getName() + ": " : "") + type + " " + algorithm);
    }

    public static void main(String[] argv) throws Exception {
        System.out.println("BEGIN ALGORITHM LIST");
        for (Provider p : Security.getProviders()) {
            Set<Provider.Service> services = new TreeSet<Provider.Service>(
                    new Comparator<Provider.Service>() {
                        public int compare(Provider.Service a, Provider.Service b) {
                            int typeCompare = a.getType().compareTo(b.getType());
                            if (typeCompare != 0) {
                                return typeCompare;
                            }
                            return a.getAlgorithm().compareTo(b.getAlgorithm());
                        }
                    });
            services.addAll(p.getServices());
            for (Provider.Service s : services) {
                if (s.getType().equals("Cipher") && s.getAlgorithm().startsWith("PBE")) {
                    // PBE ciphers are a mess and generally don't do anything but delegate
                    // to the underlying cipher.  We don't want to document them.
                    continue;
                }
                if (s.getType().equals("Cipher") && s.getAlgorithm().indexOf('/') == -1) {
                    for (String mode : CIPHER_MODES) {
                        for (String padding : CIPHER_PADDINGS) {
                            try {
                                String name = s.getAlgorithm() + "/" + mode + "/" + padding;
                                Cipher.getInstance(name, p);
                                print(p, s.getType(), name);
                            } catch (NoSuchAlgorithmException
                                    |NoSuchPaddingException
                                    |IllegalArgumentException e) {
                                // This combination doesn't work
                            }
                        }
                    }
                } else {
                    print(p, s.getType(), s.getAlgorithm());
                }
            }
            for (String alias : KNOWN_ALIASES) {
                if (p.containsKey(alias)) {
                    String[] elements = alias.split("\\.");  // Split takes a regex
                    print(p, elements[2], elements[3]);
                }
            }
        }
        // SSLEngine and SSLSocket algorithms are handled outside the default provider system
        SSLContext defaultContext = SSLContext.getDefault();
        SSLContext tls13Context = SSLContext.getInstance("TLSv1.3");
        tls13Context.init(null, null, null);
        // PSK cipher suites are only enabled when a PskKeyManager is available, but some other
        // suites are disabled in that case, so check for both
        SSLContext pskContext = SSLContext.getInstance("TLS");
        pskContext.init(
                new KeyManager[] {new FakeKeyManager()},
                new TrustManager[0],
                null);
        for (SSLContext sslContext : new SSLContext[] {defaultContext, tls13Context, pskContext}) {
            SSLEngine engine = sslContext.createSSLEngine();
            for (String suite : engine.getSupportedCipherSuites()) {
                print(sslContext.getProvider(), "SSLEngine.Supported", suite);
            }
            for (String suite : engine.getEnabledCipherSuites()) {
                print(sslContext.getProvider(), "SSLEngine.Enabled", suite);
            }
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            for (String suite : socketFactory.getSupportedCipherSuites()) {
                print(sslContext.getProvider(), "SSLSocket.Supported", suite);
            }
            for (String suite : socketFactory.getDefaultCipherSuites()) {
                print(sslContext.getProvider(), "SSLSocket.Enabled", suite);
            }
        }
        System.out.println("END ALGORITHM LIST");
    }

    private static class FakeKeyManager implements PSKKeyManager {
        @Override public String chooseServerKeyIdentityHint(Socket socket) { return null; }
        @Override public String chooseServerKeyIdentityHint(SSLEngine engine) { return null; }
        @Override public String chooseClientKeyIdentity(String identityHint, Socket socket) { return null; }
        @Override public String chooseClientKeyIdentity(String identityHint, SSLEngine engine) { return null; }
        @Override public SecretKey getKey(String identityHint, String identity, Socket socket) { return null; }
        @Override public SecretKey getKey(String identityHint, String identity, SSLEngine engine) { return null; }
    }
}