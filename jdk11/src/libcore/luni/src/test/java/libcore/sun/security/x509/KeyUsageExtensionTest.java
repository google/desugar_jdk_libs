/*
 * Copyright (C) 2016 The Android Open Source Project
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
 * limitations under the License
 */

package libcore.sun.security.x509;


import junit.framework.TestCase;

import java.io.IOException;
import java.util.function.Function;

import sun.security.x509.KeyUsageExtension;

public class KeyUsageExtensionTest extends TestCase {
    /**
     * The logic for toString was changed in rev/04cda5b7a3c1. The expected result is the same
     * before and after the change.
     */
    public void testToString() throws Exception {
        String prefix = "ObjectId: 2.5.29.15 Criticality=true\n"
                + "KeyUsage [\n";

        String[] parts = new String[] {
                "  DigitalSignature\n",
                "  Non_repudiation\n",
                "  Key_Encipherment\n",
                "  Data_Encipherment\n",
                "  Key_Agreement\n",
                "  Key_CertSign\n",
                "  Crl_Sign\n",
                "  Encipher_Only\n",
                "  Decipher_Only\n"
        };

        String suffix = "]\n";
        Function<byte[], Object> objectCreator = byteArray -> {
            try {
                return new KeyUsageExtension(byteArray);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Utils.test_toString_bitArrayBasedClass(parts, objectCreator, prefix, suffix);
    }
}
