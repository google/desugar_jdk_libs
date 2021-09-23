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

import java.util.function.Function;

import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;


public class AlgorithmIdTest extends TestCase {

    public void test_get_String() throws Exception {
        assertEquals("1.3.14.3.2.26", AlgorithmId.get("SHA-1").getOID().toString());
        assertEquals("1.3.14.3.2.26", AlgorithmId.get("SHA1").getOID().toString());
        assertEquals("2.16.840.1.101.3.4.2.4", AlgorithmId.get("SHA-224").getOID().toString());

        // Would throw NoSuchAlgorithmException in N
        assertEquals("2.16.840.1.101.3.4.2.4", AlgorithmId.get("SHA224").getOID().toString());

        assertEquals("2.16.840.1.101.3.4.2.1", AlgorithmId.get("SHA-256").getOID().toString());

        // Would throw NoSuchAlgorithmException in N
        assertEquals("2.16.840.1.101.3.4.2.1", AlgorithmId.get("SHA256").getOID().toString());

        assertEquals(
                "2.16.840.1.101.3.4.3.1", AlgorithmId.get("SHA224WithDSA").getOID().toString());
        assertEquals(
                "2.16.840.1.101.3.4.3.2", AlgorithmId.get("SHA256WithDSA").getOID().toString());
        // Case is irrelevant.
        assertEquals(
                "2.16.840.1.101.3.4.3.1", AlgorithmId.get("sHA224withDSA").getOID().toString());
        assertEquals(
                "2.16.840.1.101.3.4.3.2", AlgorithmId.get("sHA256withDSA").getOID().toString());

        // Used to be 2.16.840.1.101.3.4.42 until N because BouncyCastle accepts this alias. It
        // started with a typo they once had and for compatibility they still support it. Since we
        // scan the aliases, we were picking it as the canonical OID for AES. See:
        // http://www.docjar.org/html/api/org/bouncycastle/jce/provider/symmetric/AESMappings.java.html
        assertEquals("2.16.840.1.101.3.4.1", AlgorithmId.get("AES").getOID().toString());
        assertEquals("1.3.132.1.12", AlgorithmId.get("ECDH").getOID().toString());
    }

    public void test_getName() throws Exception {
        // Was "SHA" in N
        assertEquals("SHA-1", getOidName("1.3.14.3.2.26"));
        assertEquals("SHA-224", getOidName("2.16.840.1.101.3.4.2.4"));
        // Was "SHA256" in N
        assertEquals("SHA-256", getOidName("2.16.840.1.101.3.4.2.1"));
        // Were SHA224WITHDSA, etc in N
        assertEquals("SHA224withDSA", getOidName("2.16.840.1.101.3.4.3.1"));
        assertEquals("SHA256withDSA", getOidName("2.16.840.1.101.3.4.3.2"));
        assertEquals("SHA224withRSA", getOidName("1.2.840.113549.1.1.14"));

        assertEquals("AES", getOidName("2.16.840.1.101.3.4.1"));
        // AES is also the result of 2.16.840.1.101.3.4.42 because BouncyCastle accepts this alias.
        // It started with a typo they once had and for compatibility they still support it. Since
        // we scan the aliases, we were picking it. See:
        // http://www.docjar.org/html/api/org/bouncycastle/jce/provider/symmetric/AESMappings.java.html
        assertEquals("AES", getOidName("2.16.840.1.101.3.4.42"));

        // ECDH not present before and in N
        assertEquals("ECDH", getOidName("1.3.132.1.12"));
    }

    private String getOidName(String oid) throws Exception {
        return new AlgorithmId(new ObjectIdentifier(oid)).getName();
    }
}