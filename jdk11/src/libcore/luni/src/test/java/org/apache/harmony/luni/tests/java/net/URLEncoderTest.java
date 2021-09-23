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

package org.apache.harmony.luni.tests.java.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class URLEncoderTest {

    private static final String HomeAddress = "jcltest.apache.org";

    /**
     * java.net.URLEncoder#encode(java.lang.String)
     */
    @Test
    @SuppressWarnings("deprecation")
    public void test_encodeLjava_lang_String() {
        // TODO(deltazulu): Use tests.support.Support_Configuration.HomeAddress when
        // libcore.support is im imported.
        final String URL = "http://" + URLEncoderTest.HomeAddress;
        final String URL2 = "telnet://justWantToHaveFun.com:400";
        final String URL3 = "file://myServer.org/a file with spaces.jpg";

        assertTrue("1. Incorrect encoding/decoding", URLDecoder.decode(
                URLEncoder.encode(URL)).equals(URL));
        assertTrue("2. Incorrect encoding/decoding", URLDecoder.decode(
                URLEncoder.encode(URL2)).equals(URL2));
        assertTrue("3. Incorrect encoding/decoding", URLDecoder.decode(
                URLEncoder.encode(URL3)).equals(URL3));
    }

    /**
     * URLEncoder#encode(String, String)
     */
    @Test
    public void test_encodeLjava_lang_StringLjava_lang_String()
            throws Exception {
        // Regression for HARMONY-24
        try {
            URLEncoder.encode("str", "unknown_enc");
            fail("Assert 0: Should throw UEE for invalid encoding");
        } catch (UnsupportedEncodingException e) {
        } catch (UnsupportedCharsetException e) {
            // expected
        }

        // Regression for HARMONY-1233
        try {
            URLEncoder.encode(null, "harmony");
            fail("NullPointerException expected");
        } catch (NullPointerException expected) {
        // For desugar: UnsupportedCharsetException has been wrapped in UnsupportedEncodingException
        // } catch (UnsupportedCharsetException expected) {
        } catch (UnsupportedEncodingException expected) {
        }
    }

    // http://b/11571917
    @Test
    public void test11571917() throws Exception {
        assertEquals("%82%A0", URLEncoder.encode("あ", "Shift_JIS"));
        assertEquals("%82%A9", URLEncoder.encode("か", "Shift_JIS"));
        assertEquals("%97%43", URLEncoder.encode("佑", "Shift_JIS"));
        assertEquals("%24", URLEncoder.encode("$", "Shift_JIS"));
        assertEquals("%E3%81%8B", URLEncoder.encode("か", "UTF-8"));
        assertEquals("%E3%81%8B", URLEncoder.encode("か", StandardCharsets.UTF_8));

        assertEquals("%82%A0%82%A9%97%43%24%E3%81%8B", URLEncoder.encode("あ", "Shift_JIS") +
            URLEncoder.encode("か", "Shift_JIS") +
            URLEncoder.encode("佑", "Shift_JIS") +
            URLEncoder.encode("$", "Shift_JIS") +
            URLEncoder.encode("か", "UTF-8"));

        assertEquals("%82%A0%82%A9%97%43%24%E3%81%8B", URLEncoder.encode("あ", "Shift_JIS") +
            URLEncoder.encode("か", "Shift_JIS") +
            URLEncoder.encode("佑", "Shift_JIS") +
            URLEncoder.encode("$", "Shift_JIS") +
            URLEncoder.encode("か", StandardCharsets.UTF_8));
    }
}
