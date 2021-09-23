/*
 * Copyright (C) 2010 The Android Open Source Project
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

package libcore.javax.crypto;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public final class CipherInputStreamTest extends TestCase {

    private final byte[] aesKeyBytes = {
            (byte) 0x50, (byte) 0x98, (byte) 0xF2, (byte) 0xC3, (byte) 0x85, (byte) 0x23,
            (byte) 0xA3, (byte) 0x33, (byte) 0x50, (byte) 0x98, (byte) 0xF2, (byte) 0xC3,
            (byte) 0x85, (byte) 0x23, (byte) 0xA3, (byte) 0x33,
    };

    private final byte[] aesIvBytes = {
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
    };

    private final byte[] aesCipherText = {
            (byte) 0x2F, (byte) 0x2C, (byte) 0x74, (byte) 0x31, (byte) 0xFF, (byte) 0xCC,
            (byte) 0x28, (byte) 0x7D, (byte) 0x59, (byte) 0xBD, (byte) 0xE5, (byte) 0x0A,
            (byte) 0x30, (byte) 0x7E, (byte) 0x6A, (byte) 0x4A
    };

    private final byte[] rc4CipherText = {
            (byte) 0x88, (byte) 0x01, (byte) 0xE3, (byte) 0x52, (byte) 0x7B
    };

    private final String plainText = "abcde";
    private SecretKey key;
    private SecretKey rc4Key;
    private AlgorithmParameterSpec iv;

    @Override protected void setUp() throws Exception {
        key = new SecretKeySpec(aesKeyBytes, "AES");
        rc4Key = new SecretKeySpec(aesKeyBytes, "RC4");
        iv = new IvParameterSpec(aesIvBytes);
    }

    private static class MeasuringInputStream extends FilterInputStream {
        private int totalRead;

        protected MeasuringInputStream(InputStream in) {
            super(in);
        }

        @Override
        public int read() throws IOException {
            int c = super.read();
            totalRead++;
            return c;
        }

        @Override
        public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
            int numRead = super.read(buffer, byteOffset, byteCount);
            if (numRead != -1) {
                totalRead += numRead;
            }
            return numRead;
        }

        public int getTotalRead() {
            return totalRead;
        }
    }

    public void testAvailable() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        MeasuringInputStream in = new MeasuringInputStream(new ByteArrayInputStream(aesCipherText));
        InputStream cin = new CipherInputStream(in, cipher);
        assertTrue(cin.read() != -1);
        assertEquals(aesCipherText.length, in.getTotalRead());
    }

    public void testDecrypt_NullInput_Discarded() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        InputStream in = new CipherInputStream(new ByteArrayInputStream(aesCipherText), cipher);
        int discard = 3;
        while (discard != 0) {
            discard -= in.read(null, 0, discard);
        }
        byte[] bytes = readAll(in);
        assertEquals(Arrays.toString(plainText.substring(3).getBytes("UTF-8")),
                Arrays.toString(bytes));
    }

    public void testEncrypt() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        InputStream in = new CipherInputStream(
                new ByteArrayInputStream(plainText.getBytes("UTF-8")), cipher);
        byte[] bytes = readAll(in);
        assertEquals(Arrays.toString(aesCipherText), Arrays.toString(bytes));

        // Reading again shouldn't throw an exception.
        assertEquals(-1, in.read());
    }

    public void testEncrypt_RC4() throws Exception {
        Cipher cipher = Cipher.getInstance("RC4");
        cipher.init(Cipher.ENCRYPT_MODE, rc4Key);
        InputStream in = new CipherInputStream(
                new ByteArrayInputStream(plainText.getBytes("UTF-8")), cipher);
        byte[] bytes = readAll(in);
        assertEquals(Arrays.toString(rc4CipherText), Arrays.toString(bytes));

        // Reading again shouldn't throw an exception.
        assertEquals(-1, in.read());
    }

    public void testDecrypt() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        InputStream in = new CipherInputStream(new ByteArrayInputStream(aesCipherText), cipher);
        byte[] bytes = readAll(in);
        assertEquals(Arrays.toString(plainText.getBytes("UTF-8")), Arrays.toString(bytes));
    }

    public void testDecrypt_RC4() throws Exception {
        Cipher cipher = Cipher.getInstance("RC4");
        cipher.init(Cipher.DECRYPT_MODE, rc4Key);
        InputStream in = new CipherInputStream(new ByteArrayInputStream(rc4CipherText), cipher);
        byte[] bytes = readAll(in);
        assertEquals(Arrays.toString(plainText.getBytes("UTF-8")), Arrays.toString(bytes));
    }

    public void testSkip() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        InputStream in = new CipherInputStream(new ByteArrayInputStream(aesCipherText), cipher);
        assertTrue(in.skip(5) >= 0);
    }

    private byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int count;
        byte[] buffer = new byte[1024];
        while ((count = in.read(buffer)) != -1) {
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }

    public void testCipherInputStream_TruncatedInput_Failure() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        InputStream is = new CipherInputStream(new ByteArrayInputStream(new byte[31]), cipher);
        is.read(new byte[4]);
        is.close();
    }

    public void testCipherInputStream_NullInputStream_Failure() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        InputStream is = new CipherInputStream(null, cipher);
        try {
            is.read();
            fail("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }

        byte[] buffer = new byte[128];
        try {
            is.read(buffer);
            fail("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }

        try {
            is.read(buffer, 0, buffer.length);
            fail("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    public void testCloseTwice() throws Exception {
        InputStream mockIs = mock(InputStream.class);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        CipherInputStream cis = new CipherInputStream(mockIs, cipher);
        cis.close();
        cis.close();

        verify(mockIs, times(1)).close();
    }

    /**
     * CipherSpi that increments it's engineGetOutputSize output when
     * engineUpdate is called.
     */
    public static class CipherSpiWithGrowingOutputSize extends MockCipherSpi {
        private int outputSizeDelta = 0;

        @Override
        protected int engineGetOutputSize(int inputLen) {
            return inputLen + outputSizeDelta;
        }

        @Override
        protected int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output,
                int outputOffset) throws ShortBufferException {
            int expectedOutputSize = inputLen + outputSizeDelta++;
            if ((output.length - outputOffset) < expectedOutputSize) {
                throw new ShortBufferException();
            }
            return expectedOutputSize;
        }

        @Override
        protected byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
            int expectedOutputSize = inputLen + outputSizeDelta++;
            return new byte[expectedOutputSize];
        }

        @Override
        protected byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen) {
            return input;
        }
    }

    private static class MockProvider extends Provider {
        public MockProvider() {
            super("MockProvider", 1.0, "Mock provider used for testing");
            put("Cipher.GrowingOutputSize",
                CipherSpiWithGrowingOutputSize.class.getName());
        }
    }

    // http://b/32643789, check that CipherSpi.engineGetOutputSize is called and applied
    // to output buffer size before calling CipherSpi.egineUpdate(byte[],int,int,byte[],int).
    public void testCipherOutputSizeChange() throws Exception {
        Provider mockProvider = new MockProvider();

        Cipher cipher = Cipher.getInstance("GrowingOutputSize", mockProvider);

        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        InputStream mockEncryptedInputStream = new ByteArrayInputStream(new byte[1024]);
        try (InputStream is = new CipherInputStream(mockEncryptedInputStream, cipher)) {
            byte[] buffer = new byte[1024];
            // engineGetOutputSize returns 512+0, engineUpdate expects buf >= 512
            assertEquals(512, is.read(buffer));
            // engineGetOutputSize returns 512+1, engineUpdate expects buf >= 513
            // and will throw ShortBufferException buffer is smaller.
            assertEquals(513, is.read(buffer));
        }
    }

    // From b/31590622. CipherInputStream had a bug where it would ignore exceptions
    // thrown during close(), because it was expecting exceptions to be thrown by read().
    public void testDecryptCorruptGCM() throws Exception {
        for (Provider provider : Security.getProviders()) {
            Cipher cipher;
            try {
                cipher = Cipher.getInstance("AES/GCM/NoPadding", provider);
            } catch (NoSuchAlgorithmException e) {
                continue;
            }
            SecretKey key;
            if (provider.getName().equals("AndroidKeyStoreBCWorkaround")) {
                key = getAndroidKeyStoreSecretKey();
            } else {
                KeyGenerator keygen = KeyGenerator.getInstance("AES");
                keygen.init(256);
                key = keygen.generateKey();
            }
            GCMParameterSpec params = new GCMParameterSpec(128, new byte[12]);
            byte[] unencrypted = new byte[200];

            // Normal providers require specifying the IV, but KeyStore prohibits it, so
            // we have to special-case it
            if (provider.getName().equals("AndroidKeyStoreBCWorkaround")) {
                cipher.init(Cipher.ENCRYPT_MODE, key);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key, params);
            }
            byte[] encrypted = cipher.doFinal(unencrypted);

            // Corrupt the final byte, which will corrupt the authentication tag
            encrypted[encrypted.length - 1] ^= 1;

            cipher.init(Cipher.DECRYPT_MODE, key, params);
            CipherInputStream cis = new CipherInputStream(
                    new ByteArrayInputStream(encrypted), cipher);
            try {
                cis.read(unencrypted);
                cis.close();
                fail("Reading a corrupted stream should throw an exception."
                        + "  Provider: " + provider);
            } catch (IOException expected) {
                assertTrue(expected.getCause() instanceof AEADBadTagException);
            }
        }

    }

    // The AndroidKeyStoreBCWorkaround provider can't use keys created by anything
    // but Android KeyStore, which requires using its own parameters class to create
    // keys.  Since we're in javax, we can't link against the frameworks classes, so
    // we have to use reflection to make a suitable key.  This will always be safe
    // because if we're making a key for AndroidKeyStoreBCWorkaround, the KeyStore
    // classes must be present.
    private static SecretKey getAndroidKeyStoreSecretKey() throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES", "AndroidKeyStore");
        Class<?> keyParamsBuilderClass = keygen.getClass().getClassLoader().loadClass(
                "android.security.keystore.KeyGenParameterSpec$Builder");
        Object keyParamsBuilder = keyParamsBuilderClass.getConstructor(String.class, Integer.TYPE)
                // 3 is PURPOSE_ENCRYPT | PURPOSE_DECRYPT
                .newInstance("testDecryptCorruptGCM", 3);
        keyParamsBuilderClass.getMethod("setBlockModes", new Class[]{String[].class})
                .invoke(keyParamsBuilder, new Object[]{new String[]{"GCM"}});
        keyParamsBuilderClass.getMethod("setEncryptionPaddings", new Class[]{String[].class})
                .invoke(keyParamsBuilder, new Object[]{new String[]{"NoPadding"}});
        AlgorithmParameterSpec spec = (AlgorithmParameterSpec)
                keyParamsBuilderClass.getMethod("build", new Class[]{}).invoke(keyParamsBuilder);
        keygen.init(spec);
        return keygen.generateKey();
    }
}
