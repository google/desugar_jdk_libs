/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package libcore.java.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import java.util.Base64;
import java.util.Base64.Decoder;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * Additional tests for {@link java.util.Base64} derived from old tests for
 * the removed class {@code libcore.io.Base64}.
 */
public final class LibcoreIoDerivedBase64Test extends TestCase {

    public void testEncodeDecode() throws Exception {
        assertEncodeDecode("");
        assertEncodeDecode("Eg==", 0x12);
        assertEncodeDecode("EjQ=", 0x12, 0x34);
        assertEncodeDecode("EjRW", 0x12, 0x34, 0x56);
        assertEncodeDecode("EjRWeA==", 0x12, 0x34, 0x56, 0x78);
        assertEncodeDecode("EjRWeJo=", 0x12, 0x34, 0x56, 0x78, 0x9A);
        assertEncodeDecode("EjRWeJq8", 0x12, 0x34, 0x56, 0x78, 0x9a, 0xbc);
    }

    public void testEncode_doesNotWrap() throws Exception {
        int[] data = new int[61];
        Arrays.fill(data, 0xff);
        String expected = "///////////////////////////////////////////////////////////////////////"
                + "//////////w=="; // 84 chars
        assertEncodeDecode(expected, data);
    }

    private static void assertEncodeDecode(String expectedEncoded, int... toEncode)
            throws Exception {
        // We should never expect (or receive) non-ASCII text from Base64.encoder.
        asciiToBytes(expectedEncoded);

        // Convert the convenient ints to the bytes we need.
        byte[] inputBytes = new byte[toEncode.length];
        for (int i = 0; i < toEncode.length; i++) {
            inputBytes[i] = (byte) toEncode[i];
        }
        String encoded = encode(inputBytes);
        assertEquals(expectedEncoded, encoded);

        // Check we can round-trip the encoded bytes to
        // arrive at what we started with.
        int[] actualDecodedBytes = decodeToInts(encoded);
        assertArrayEquals(toEncode, actualDecodedBytes);
    }

    public void testDecode_empty() throws Exception {
        byte[] decoded = decode(new byte[0]);
        assertEquals(0, decoded.length);
    }

    public void testDecode_truncated() throws Exception {
        // Correct data, for reference.
        assertEquals("hello, world", decodeToString("aGVsbG8sIHdvcmxk"));

        // The following are missing the final bytes
        assertEquals("hello, worl", decodeToString("aGVsbG8sIHdvcmx"));
        assertEquals("hello, wor",  decodeToString("aGVsbG8sIHdvcm"));
        assertEquals(null,          decodeToString("aGVsbG8sIHdvc"));
        assertEquals("hello, wo",   decodeToString("aGVsbG8sIHdv"));
    }

    public void testDecode_extraChars() throws Exception {
        // Characters outside of alphabet before padding.
        assertEquals(null, decodeToString(" aGVsbG8sIHdvcmxk"));
        assertEquals(null, decodeToString("aGV sbG8sIHdvcmxk"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxk "));
        assertEquals(null, decodeToString("*aGVsbG8sIHdvcmxk"));
        assertEquals(null, decodeToString("aGV*sbG8sIHdvcmxk"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxk*"));
        assertEquals(null, decodeToString("\r\naGVsbG8sIHdvcmxk"));
        assertEquals(null, decodeToString("aGV\r\nsbG8sIHdvcmxk"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxk\r\n"));
        assertEquals(null, decodeToString("\naGVsbG8sIHdvcmxk"));
        assertEquals(null, decodeToString("aGV\nsbG8sIHdvcmxk"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxk\n"));

        // padding 0
        assertEquals("hello, world", decodeToString("aGVsbG8sIHdvcmxk"));
        // Extra padding
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxk="));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxk=="));
        // Characters outside alphabet intermixed with (too much) padding.
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxk ="));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxk = = "));

        // padding 1
        assertEquals("hello, world?!", decodeToString("aGVsbG8sIHdvcmxkPyE="));
        // Missing padding
        assertEquals("hello, world?!", decodeToString("aGVsbG8sIHdvcmxkPyE"));
        // Characters outside alphabet before padding.
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE ="));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE*="));
        // Trailing characters, otherwise valid.
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE= "));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE=*"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE=X"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE=XY"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE=XYZ"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE=XYZA"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE=\n"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE=\r\n"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE= "));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE=="));
        // Whitespace characters outside alphabet intermixed with (too much) padding.
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE =="));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkPyE = = "));

        // padding 2
        assertEquals("hello, world.", decodeToString("aGVsbG8sIHdvcmxkLg=="));
        // Missing padding
        assertEquals("hello, world.", decodeToString("aGVsbG8sIHdvcmxkLg"));
        // Partially missing padding
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg="));
        // Characters outside alphabet before padding.
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg =="));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg*=="));
        // Trailing characters, otherwise valid.
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg== "));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg==*"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg==X"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg==XY"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg==XYZ"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg==XYZA"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg==\n"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg==\r\n"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg== "));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg==="));
        // Characters outside alphabet inside padding.
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg= ="));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg=*="));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg=\r\n="));
        // Characters inside alphabet inside padding.
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmxkLg=X="));

        // Table 1 chars
        assertEquals(null, decodeToString("_aGVsbG8sIHdvcmx"));
        assertEquals(null, decodeToString("aGV_sbG8sIHdvcmx"));
        assertEquals(null, decodeToString("aGVsbG8sIHdvcmx_"));

        // Table 2 chars.
        assertArrayEquals(
                new int[] {0xfd, 0xa1, 0x95, 0xb1, 0xb1, 0xbc, 0xb0, 0x81, 0xdd, 0xbd, 0xc9,
                        0xb1 },
                decodeToInts("/aGVsbG8sIHdvcmx"));
        assertArrayEquals(
                new int[] { 0x68, 0x65, 0x7f, 0xb1, 0xb1, 0xbc, 0xb0, 0x81, 0xdd, 0xbd, 0xc9,
                        0xb1 },
                decodeToInts("aGV/sbG8sIHdvcmx"));
        assertArrayEquals(
                new int[] { 104, 101, 108, 108, 111, 44, 32, 119, 111, 114, 108, 0x7f },
                decodeToInts("aGVsbG8sIHdvcmx/"));
    }

    private static final int[] BYTE_VALUES = {
            0xff, 0xee, 0xdd, 0xcc, 0xbb, 0xaa, 0x99, 0x88, 0x77
    };

    public void testDecode_nonAsciiBytes() throws Exception {
        assertSubArrayEquals(BYTE_VALUES, 0, decodeToInts(""));
        assertSubArrayEquals(BYTE_VALUES, 1, decodeToInts("/w=="));
        assertSubArrayEquals(BYTE_VALUES, 2, decodeToInts("/+4="));
        assertSubArrayEquals(BYTE_VALUES, 3, decodeToInts("/+7d"));
        assertSubArrayEquals(BYTE_VALUES, 4, decodeToInts("/+7dzA=="));
        assertSubArrayEquals(BYTE_VALUES, 5, decodeToInts("/+7dzLs="));
        assertSubArrayEquals(BYTE_VALUES, 6, decodeToInts("/+7dzLuq"));
        assertSubArrayEquals(BYTE_VALUES, 7, decodeToInts("/+7dzLuqmQ=="));
        assertSubArrayEquals(BYTE_VALUES, 8, decodeToInts("/+7dzLuqmYg="));
    }

    public void testDecode_urlAlphabet() throws Exception {
        assertNull(decodeToInts("_w=="));
        assertNull(decodeToInts("-w=="));
    }

    /**
     * Convenience function for decoding from a Base64 ASCII String to an ASCII String. A String is
     * used for the output to make the tests compact. Can return null if the decoder returns null.
     * If any of the strings involved are non-ASCII an exception is thrown.
     * Use {@link #decodeToInts(String)} for decode tests that produce bytes
     * outside of the ASCII range.
     */
    private static String decodeToString(String in) throws Exception {
        byte[] bytes = asciiToBytes(in);
        byte[] out = decode(bytes);
        if (out == null) {
            return null;
        }
        return bytesToAscii(out);
    }

    private static String bytesToAscii(byte[] bytes) {
        try {
            CharsetDecoder decoder = StandardCharsets.US_ASCII.newDecoder();
            decoder.onMalformedInput(CodingErrorAction.REPORT);
            decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
            ByteBuffer bytesBuffer = ByteBuffer.wrap(bytes);
            CharBuffer charsBuffer = decoder.decode(bytesBuffer);
            char[] chars = new char[charsBuffer.remaining()];
            charsBuffer.get(chars, 0, chars.length);
            return new String(chars);
        } catch (CharacterCodingException e) {
            // Use bytes in your test, not Strings.
            throw new AssertionFailedError("Cannot convert test bytes to String safely: " +
                    Arrays.toString(bytesToInts(bytes)) + " contains non-ASCII codes");
        }
    }

    private static byte[] asciiToBytes(String string) {
        try {
            char[] chars = string.toCharArray();

            CharsetEncoder encoder = StandardCharsets.US_ASCII.newEncoder();
            encoder.onMalformedInput(CodingErrorAction.REPORT);
            encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
            CharBuffer charsBuffer = CharBuffer.wrap(chars);
            ByteBuffer bytesBuffer = encoder.encode(charsBuffer);
            byte[] bytes = new byte[bytesBuffer.remaining()];
            bytesBuffer.get(bytes, 0, bytes.length);
            return bytes;
        } catch (CharacterCodingException e) {
            // Use bytes in your test, not Strings.
            throw new AssertionFailedError("Cannot convert test String to bytes safely: " + string +
                    " contains non-ASCII characters");
        }
    }

    /** Decodes an ASCII string, returning an int array. */
    private static int[] decodeToInts(String in) throws Exception {
        byte[] bytes = decode(asciiToBytes(in));
        return bytesToInts(bytes);
    }

    private static byte[] decode(byte[] encoded) {
        Decoder decoder = Base64.getDecoder();
        try {
            return decoder.decode(encoded);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * Convert a byte[] to an int[]. int is used because it is more convenient to use ints in
     * tests.
     */
    private static int[] bytesToInts(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int[] ints = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            ints[i] = bytes[i] & 0xff;
        }
        return ints;
    }

    private static void assertArrayEquals(int[] expected, int[] actual) {
        assertSubArrayEquals(expected, expected.length, actual);
    }

    /** Assert that actual equals the first len bytes of expected. */
    private static void assertSubArrayEquals(int[] expected, int len, int[] actual) {
        // Convert the arrays to Strings for easy comparison / reporting.
        String expectedString = intsToString(expected, len);
        String actualString = intsToString(actual, actual.length);
        assertEquals(expectedString, actualString);
    }

    private static String intsToString(int[] toConvert, int length) {
        String[] out = new String[length];
        for (int i = 0; i < length; i++) {
            out[i] = "0x" + Integer.toHexString(toConvert[i]);
        }
        return Arrays.toString(out);
    }
}

