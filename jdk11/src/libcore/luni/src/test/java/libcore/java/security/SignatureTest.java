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

package libcore.java.security;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Method;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import libcore.junit.junit3.TestCaseWithRules;
import libcore.junit.util.EnableDeprecatedBouncyCastleAlgorithmsRule;
import libcore.util.HexEncoding;
import org.junit.Rule;
import org.junit.rules.TestRule;

public class SignatureTest extends TestCaseWithRules {

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

    public void testSignature_getInstance_SuppliedProviderNotRegistered_Success() throws Exception {
        Provider mockProvider = new MockProvider("MockProvider") {
            @Override
            public void setup() {
                put("Signature.FOO", MockSignatureSpi.AllKeyTypes.class.getName());
            }
        };

        {
            Signature s = Signature.getInstance("FOO", mockProvider);
            s.initSign(new MockPrivateKey());
            assertEquals(mockProvider, s.getProvider());
        }
    }

    public void testSignature_getInstance_DoesNotSupportKeyClass_Success() throws Exception {
        Provider mockProvider = new MockProvider("MockProvider") {
            @Override
            public void setup() {
                put("Signature.FOO", MockSignatureSpi.AllKeyTypes.class.getName());
                put("Signature.FOO SupportedKeyClasses", "None");
            }
        };

        Security.addProvider(mockProvider);
        try {
            Signature s = Signature.getInstance("FOO", mockProvider);
            s.initSign(new MockPrivateKey());
            assertEquals(mockProvider, s.getProvider());
        } finally {
            Security.removeProvider(mockProvider.getName());
        }
    }

    /**
     * Several exceptions can be thrown by init. Check that in this case we throw the right one,
     * as the error could fall under the umbrella of other exceptions.
     * http://b/18987633
     */
    public void testSignature_init_DoesNotSupportKeyClass_throwsInvalidKeyException()
            throws Exception {
        Provider mockProvider = new MockProvider("MockProvider") {
            @Override
            public void setup() {
                put("Signature.FOO", MockSignatureSpi.AllKeyTypes.class.getName());
                put("Signature.FOO SupportedKeyClasses", "None");
            }
        };

        Security.addProvider(mockProvider);
        try {
            Signature s = Signature.getInstance("FOO");
            s.initSign(new MockPrivateKey());
            fail("Expected InvalidKeyException");
        } catch (InvalidKeyException expected) {
        } finally {
            Security.removeProvider(mockProvider.getName());
        }
    }

    public void testSignature_getInstance_OnlyUsesSpecifiedProvider_SameNameAndClass_Success()
            throws Exception {
        Provider mockProvider = new MockProvider("MockProvider") {
            @Override
            public void setup() {
                put("Signature.FOO", MockSignatureSpi.AllKeyTypes.class.getName());
            }
        };

        Security.addProvider(mockProvider);
        try {
            {
                Provider mockProvider2 = new MockProvider("MockProvider") {
                    @Override
                    public void setup() {
                        put("Signature.FOO", MockSignatureSpi.AllKeyTypes.class.getName());
                    }
                };
                Signature s = Signature.getInstance("FOO", mockProvider2);
                assertEquals(mockProvider2, s.getProvider());
            }
        } finally {
            Security.removeProvider(mockProvider.getName());
        }
    }

    public void testSignature_getInstance_DelayedInitialization_KeyType() throws Exception {
        Provider mockProviderSpecific = new MockProvider("MockProviderSpecific") {
            @Override
            public void setup() {
                put("Signature.FOO", MockSignatureSpi.SpecificKeyTypes.class.getName());
                put("Signature.FOO SupportedKeyClasses", MockPrivateKey.class.getName());
            }
        };
        Provider mockProviderSpecific2 = new MockProvider("MockProviderSpecific2") {
            @Override
            public void setup() {
                put("Signature.FOO", MockSignatureSpi.SpecificKeyTypes2.class.getName());
                put("Signature.FOO SupportedKeyClasses", MockPrivateKey2.class.getName());
            }
        };
        Provider mockProviderAll = new MockProvider("MockProviderAll") {
            @Override
            public void setup() {
                put("Signature.FOO", MockSignatureSpi.AllKeyTypes.class.getName());
            }
        };

        Security.addProvider(mockProviderSpecific);
        Security.addProvider(mockProviderSpecific2);
        Security.addProvider(mockProviderAll);

        try {
            {
                Signature s = Signature.getInstance("FOO");
                s.initSign(new MockPrivateKey());
                assertEquals(mockProviderSpecific, s.getProvider());

                try {
                    s.initSign(new MockPrivateKey2());
                    assertEquals(mockProviderSpecific2, s.getProvider());
                    if (StandardNames.IS_RI) {
                        fail("RI was broken before; fix tests now that it works!");
                    }
                } catch (InvalidKeyException e) {
                    if (!StandardNames.IS_RI) {
                        fail("Non-RI should select the right provider");
                    }
                }
            }

            {
                Signature s = Signature.getInstance("FOO");
                s.initSign(new PrivateKey() {
                    @Override
                    public String getAlgorithm() {
                        throw new UnsupportedOperationException("not implemented");
                    }

                    @Override
                    public String getFormat() {
                        throw new UnsupportedOperationException("not implemented");
                    }

                    @Override
                    public byte[] getEncoded() {
                        throw new UnsupportedOperationException("not implemented");
                    }
                });
                assertEquals(mockProviderAll, s.getProvider());
            }

            {
                Signature s = Signature.getInstance("FOO");
                assertEquals(mockProviderSpecific, s.getProvider());
            }
        } finally {
            Security.removeProvider(mockProviderSpecific.getName());
            Security.removeProvider(mockProviderSpecific2.getName());
            Security.removeProvider(mockProviderAll.getName());
        }
    }

    private static class MySignature extends Signature {
        protected MySignature(String algorithm) {
            super(algorithm);
        }

        @Override
        protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        }

        @Override
        protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        }

        @Override
        protected void engineUpdate(byte b) throws SignatureException {
        }

        @Override
        protected void engineUpdate(byte[] b, int off, int len) throws SignatureException {
        }

        @Override
        protected byte[] engineSign() throws SignatureException {
            return new byte[10];
        }

        @Override
        protected boolean engineVerify(byte[] sigBytes) throws SignatureException {
            return true;
        }

        @Override
        protected void engineSetParameter(String param, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected Object engineGetParameter(String param) throws InvalidParameterException {
            throw new UnsupportedOperationException();
        }
    }

    public void testSignature_signArray_nullArray_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            s.sign(null /* outbuf */, 1 /* offset */, 1 /* length */);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testSignature_signArray_negativeOffset_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            s.sign(new byte[4], -1 /* offset */, 1 /* length */);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testSignature_signArray_negativeLength_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            s.sign(new byte[4], 1 /* offset */ , -1 /* length */);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testSignature_signArray_invalidLengths_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            // Start at offset 3 with length 2, thus attempting to overread from an array of size 4.
            s.sign(new byte[4], 3 /* offset */ , 2 /* length */);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    private static final byte[] PK_BYTES = HexEncoding.decode(
            "30819f300d06092a864886f70d010101050003818d0030818902818100cd769d178f61475fce3001"
                    + "2604218320c77a427121d3b41dd76756c8fc0c428cd15cb754adc85466f47547b1c85623d9c17fc6"
                    + "4f202fca21099caf99460c824ad657caa8c2db34996838d32623c4f23c8b6a4e6698603901262619"
                    + "4840e0896b1a6ec4f6652484aad04569bb6a885b822a10d700224359c632dc7324520cbb3d020301"
                    + "0001");

    private static PublicKey createPublicKey() throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(PK_BYTES);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public void testSignature_verifyArray_nullArray_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            s.initVerify(createPublicKey());
            s.verify(null /* outbuf */, 1 /* offset */, 1 /* length */);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testSignature_verifyArray_negativeOffset_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            s.initVerify(createPublicKey());
            s.verify(new byte[4], -1 /* offset */, 1 /* length */);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testSignature_verifyArray_negativeLength_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            s.initVerify(createPublicKey());
            s.verify(new byte[4], 1 /* offset */ , -1 /* length */);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testSignature_verifyArray_invalidLengths_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            s.initVerify(createPublicKey());
            // Start at offset 3 with length 2, thus attempting to overread from an array of size 4.
            s.verify(new byte[4], 3 /* offset */ , 2 /* length */);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testSignature_verifyArray_correctParameters_ok() throws Exception {
        Signature s = new MySignature("FOO");
        s.initVerify(createPublicKey());
        // Start at offset 3 with length 2, thus attempting to overread from an array of size 4.
        s.verify(new byte[4], 1 /* offset */, 2 /* length */);
    }

    public void testSignature_updateArray_nullArray_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            s.initVerify(createPublicKey());
            s.update(null /* outbuf */, 1 /* offset */, 1 /* length */);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testSignature_updateArray_negativeOffset_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            s.initVerify(createPublicKey());
            s.update(new byte[4], -1 /* offset */, 1 /* length */);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testSignature_updateArray_negativeLength_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            s.initVerify(createPublicKey());
            s.update(new byte[4], 1 /* offset */ , -1 /* length */);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testSignature_updateArray_invalidLengths_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            s.initVerify(createPublicKey());
            // Start at offset 3 with length 2, thus attempting to overread from an array of size 4.
            s.update(new byte[4], 3 /* offset */ , 2 /* length */);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testSignature_updateArray_wrongState_throws() throws Exception {
        try {
            Signature s = new MySignature("FOO");
            s.update(new byte[4], 0 /* offset */ , 1 /* length */);
            fail();
        } catch (SignatureException expected) {
        }
    }

    public void testSignature_updateArray_correctStateAndParameters_ok() throws Exception {
        Signature s = new MySignature("FOO");
        s.initVerify(createPublicKey());
        s.update(new byte[4], 0 /* offset */ , 1 /* length */);
    }

    public void testSignature_getProvider_Subclass() throws Exception {
        Provider mockProviderNonSpi = new MockProvider("MockProviderNonSpi") {
            @Override
            public void setup() {
                put("Signature.FOO", MySignature.class.getName());
            }
        };

        Security.addProvider(mockProviderNonSpi);

        try {
            Signature s = new MySignature("FOO");
            assertNull(s.getProvider());
        } finally {
            Security.removeProvider(mockProviderNonSpi.getName());
        }
    }

    /**
     * When an instance of a Signature is obtained, it's actually wrapped in an
     * implementation that makes sure the correct SPI is selected and then calls
     * through to the underlying SPI. We need to make sure that all methods on
     * the delegate are wrapped and don't call directly into
     * {@link SignatureSpi}.
     */
    public void testSignatureDelegateOverridesAllMethods() throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");

        /*
         * Make sure we're dealing with a delegate and not an actual instance of
         * Signature.
         */
        Class<?> sigClass = sig.getClass();
        assertFalse(sigClass.equals(SignatureSpi.class));
        assertFalse(sigClass.equals(Signature.class));

        List<String> methodsNotOverridden = new ArrayList<String>();

        for (Method spiMethod : SignatureSpi.class.getDeclaredMethods()) {
            try {
                sigClass.getDeclaredMethod(spiMethod.getName(), spiMethod.getParameterTypes());
            } catch (NoSuchMethodException e) {
                methodsNotOverridden.add(spiMethod.toString());
            }
        }

        assertEquals(Collections.EMPTY_LIST, methodsNotOverridden);
    }

    public void testGetParameters_IsCalled() throws Exception {
        Provider provider = spy(new MockableProvider());
        Provider.Service service = spy(new Provider.Service(provider, "Signature",
                "FAKEFORGETPARAMETERS", "fake", null, null));
        MockableSignatureSpi signatureSpi = mock(MockableSignatureSpi.class);

        // Since these are spies, we want to use the doReturn(...) syntax to
        // avoid calling the real methods.
        doReturn(service).when(provider).getService(service.getType(), service.getAlgorithm());
        doReturn(signatureSpi).when(service).newInstance(null);

        Signature sig = Signature.getInstance(service.getAlgorithm(), provider);
        sig.getParameters();
        verify(signatureSpi).engineGetParameters();
    }

    public static class MockableProvider extends Provider {
        protected MockableProvider() {
            super("MockableProvider", 1.0, "Used by Mockito");
        }
    }

    public static class MockableSignatureSpi extends SignatureSpi {
        @Override
        public void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void engineUpdate(byte b) throws SignatureException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void engineUpdate(byte[] b, int off, int len) throws SignatureException {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte[] engineSign() throws SignatureException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean engineVerify(byte[] sigBytes) throws SignatureException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void engineSetParameter(String param, Object value) throws InvalidParameterException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object engineGetParameter(String param) throws InvalidParameterException {
            throw new UnsupportedOperationException();
        }

        @Override
        public AlgorithmParameters engineGetParameters() {
            throw new UnsupportedOperationException();
        }
    }
}
