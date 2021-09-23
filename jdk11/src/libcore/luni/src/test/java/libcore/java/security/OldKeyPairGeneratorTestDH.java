/*
 * Copyright (C) 2009 The Android Open Source Project
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
package libcore.java.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import junit.framework.TestCase;

public class OldKeyPairGeneratorTestDH extends TestCase {

    // Broken Test: Takes ages due to DH computations. Disabling for now.
    public void testKeyPairGenerator() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("DH");

        generator.initialize(1024);

        KeyPair keyPair = generator.generateKeyPair();

        assertNotNull("no keypair generated", keyPair);
        assertNotNull("no public key generated", keyPair.getPublic());
        assertNotNull("no private key generated", keyPair.getPrivate());

        new KeyAgreementHelper("DH").test(keyPair);
    }
}
