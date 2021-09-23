/*
 * Copyright (C) 2015 The Android Open Source Project
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

package libcore.javax.crypto;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.InvalidKeyException;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import libcore.junit.junit3.TestCaseWithRules;
import libcore.junit.util.EnableDeprecatedBouncyCastleAlgorithmsRule;
import org.junit.Rule;
import org.junit.rules.TestRule;

public class MacTest extends TestCaseWithRules {

    // Allow access to deprecated BC algorithms in this test, so we can ensure they
    // continue to work
    @Rule
    public TestRule enableDeprecatedBCAlgorithmsRule =
            EnableDeprecatedBouncyCastleAlgorithmsRule.getInstance();

    private static abstract class MockProvider extends Provider {
        public MockProvider(String name) {
            super(name, 1.0, "Mock provider used for testing");
            setup();
        }

        public abstract void setup();
    }

    /**
     * Several exceptions can be thrown by init. Check that in this case we throw the right one,
     * as the error could fall under the umbrella of other exceptions.
     * http://b/18987633
     */
    public void testMac_init_DoesNotSupportKeyClass_throwsInvalidKeyException()
            throws Exception {
        Provider mockProvider = new MockProvider("MockProvider") {
            @Override
            public void setup() {
                put("Mac.FOO", MockMacSpi.AllKeyTypes.class.getName());
                put("Mac.FOO SupportedKeyClasses", "none");

            }
        };

        Security.addProvider(mockProvider);
        try {
            Mac c = Mac.getInstance("FOO");
            c.init(new MockKey());
            fail("Expected InvalidKeyException");
        } catch (InvalidKeyException expected) {
        } finally {
            Security.removeProvider(mockProvider.getName());
        }
    }

    /**
     * Aliases used to be wrong due to a typo.
     * http://b/31114355
     */
    public void testMac_correctAlias() throws Exception {
        Provider androidOpenSSLProvider = Security.getProvider("AndroidOpenSSL");
        assertEquals("HmacSHA224", androidOpenSSLProvider.get("Alg.Alias.Mac.1.2.840.113549.2.8"));
        assertEquals("HmacSHA256", androidOpenSSLProvider.get("Alg.Alias.Mac.1.2.840.113549.2.9"));
    }

    // Known answers from the SunJCE provider using the code below. Run with
    //     vogar --classpath sunjce_provider.jar
    //
    // secretKeyFactory = SecretKeyFactory.getInstance("PBEWithHmacSHA" + shaVariant + "AndAES_128",
    //        new com.sun.crypto.provider.SunJCE());
    // pbeKeySpec = new PBEKeySpec(password);
    //
    // secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
    // mac = Mac.getInstance("PBEWITHHMACSHA" + shaVariant, new com.sun.crypto.provider.SunJCE());
    //        mac.init(secretKey, new PBEParameterSpec(salt, iterationCount));
    // byte[] sunResult = mac.doFinal(plaintext);
    private final byte[][] SUN_JCA_KNOWN_ANSWERS_FOR_SHA_VARIANTS = {
            { 44, -78, -97, -109, -125, 49, 68, 58, -9, -99, -27, -122, 58, 27, 7, 45, 87, -92,
                    -74, 64 },
            { 59, -13, 28, 53, 79, -79, -127, 117, 3, -23, -75, -127, -44, -47, -43, 28, 76, -114,
                    -110, 26, 59, 70, -91, 19, -52, 36, -64, -54 },
            { 88, 54, -105, -122, 14, 73, -40, -43, 52, -21, -33, -103, 32, 81, 115, 53, 111, 78,
                    32, -108, 71, -74, -84, 125, 80, 13, -35, -36, 27, 56, 32, 104 },
            { -83, 60, -92, 44, -58, 86, -121, 104, 114, -67, 14, 80, 84, -48, -14, 38, 14, -62,
                    -96, 118, 53, -59, -33, -90, 85, -110, 105, -119, -81, 57, 43, -66, 99, 106, 35,
                    -16, -115, 29, -56, -52, -39, 102, -1, -90, 110, -52, 48, -32},
            { -22, -69, -77, 11, -14, -128, -121, 5, 48, 18, 107, -22, 64, -45, 18, 60, 24, -42,
                    -67, 111, 110, -99, -19, 14, -21, -43, 26, 68, -40, 82, 123, 39, 115, 34, 6,
                    -67, 27, -73, -63, -56, -39, 65, -75, -14, -5, -94, -8, 126, -44, -97, 95, 31,
                    61, 123, -17, 14, 117, 71, -45, 53, -76, -91, 91, -121}
    };

    /**
     * Test that default PBEWITHHMACSHA implementation has the same results as the SunJCA provider.
     */
    public void test_PBEWITHHMACSHA_Variants() throws Exception {
        byte[] plaintext = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
                17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34 };
        byte[] salt = "saltsalt".getBytes(UTF_8);
        char[] password = "password".toCharArray();
        int iterationCount = 100;
        int[] shaVariants = { 1, 224, 256, 384, 512 };

        for (int shaVariantIndex = 0; shaVariantIndex < shaVariants.length; shaVariantIndex++) {
            int shaVariant = shaVariants[shaVariantIndex];
            SecretKeyFactory secretKeyFactory =
                    SecretKeyFactory.getInstance("PBKDF2WITHHMACSHA" + shaVariant);
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password,
                    salt,
                    iterationCount,
                    // Key depending on block size!
                    (shaVariant < 384) ? 64 : 128);
            SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
            Mac mac = Mac.getInstance("PBEWITHHMACSHA" + shaVariant);
            mac.init(secretKey);
            byte[] bcResult = mac.doFinal(plaintext);
            assertEquals(
                    Arrays.toString(SUN_JCA_KNOWN_ANSWERS_FOR_SHA_VARIANTS[shaVariantIndex]),
                    Arrays.toString(bcResult));
        }
    }
}
