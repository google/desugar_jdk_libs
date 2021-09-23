/*
 * Copyright 2014 The Android Open Source Project
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

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;

/**
 * Mock SignatureSpi used by {@link SignatureTest}.
 */
public class MockSignatureSpi extends SignatureSpi {
    public static class SpecificKeyTypes extends MockSignatureSpi {
        @Override
        public void checkKeyType(Key key) throws InvalidKeyException {
            if (!(key instanceof MockPrivateKey)) {
                throw new InvalidKeyException("Must be MockPrivateKey!");
            }
        }
    }

    public static class SpecificKeyTypes2 extends MockSignatureSpi {
        @Override
        public void checkKeyType(Key key) throws InvalidKeyException {
            if (!(key instanceof MockPrivateKey2)) {
                throw new InvalidKeyException("Must be MockPrivateKey2!");
            }
        }
    }

    public static class AllKeyTypes extends MockSignatureSpi {
    }

    public void checkKeyType(Key key) throws InvalidKeyException {
    }

    /* (non-Javadoc)
     * @see java.security.SignatureSpi#engineInitVerify(java.security.PublicKey)
     */
    @Override
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see java.security.SignatureSpi#engineInitSign(java.security.PrivateKey)
     */
    @Override
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        checkKeyType(privateKey);
    }

    /* (non-Javadoc)
     * @see java.security.SignatureSpi#engineUpdate(byte)
     */
    @Override
    protected void engineUpdate(byte b) throws SignatureException {
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see java.security.SignatureSpi#engineSign()
     */
    @Override
    protected byte[] engineSign() throws SignatureException {
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see java.security.SignatureSpi#engineVerify(byte[])
     */
    @Override
    protected boolean engineVerify(byte[] sigBytes) throws SignatureException {
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see java.security.SignatureSpi#engineSetParameter(java.lang.String, java.lang.Object)
     */
    @Override
    protected void engineSetParameter(String param, Object value) throws InvalidParameterException {
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see java.security.SignatureSpi#engineGetParameter(java.lang.String)
     */
    @Override
    protected Object engineGetParameter(String param) throws InvalidParameterException {
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see java.security.SignatureSpi#engineUpdate(byte[], int, int)
     */
    @Override
    protected void engineUpdate(byte[] b, int off, int len) throws SignatureException {
        throw new UnsupportedOperationException("not implemented");
    }
}
