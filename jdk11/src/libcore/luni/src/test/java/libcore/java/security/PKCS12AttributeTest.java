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

package libcore.java.security;

import junit.framework.TestCase;

import java.io.IOException;
import java.security.PKCS12Attribute;
import java.util.Arrays;


public class PKCS12AttributeTest extends TestCase {
    private static final String PKCS9_EMAIL_ADDRESS_OID = "1.2.840.113549.1.9.1";
    private static final String PKCS9_CONTENT_TYPE_OID = "1.2.840.113549.1.9.3";
    private static final String PKCS7_SIGNED_DATA_OID = "1.2.840.113549.1.7.2";
    private static final String EXAMPLE_EMAIL_ADDRESS = "someemail@server.com";
    private static final String EXAMPLE_EMAIL_ADDRESS_2 = "someotheremail@server.com";
    private static final String EXAMPLE_SEQUENCE_OF_EMAILS =
            "[" + EXAMPLE_EMAIL_ADDRESS + ", " + EXAMPLE_EMAIL_ADDRESS_2 + "]";

    /*
     * Encoded attribute obtained using BouncyCastle as an oracle for the known answer:
     *
            DERSequence s = new DERSequence(new ASN1Encodable[] {
                new ASN1ObjectIdentifier("1.2.840.113549.1.9.1"),
                new DERSet(new ASN1Encodable[] { new DERUTF8String("someemail@server.com") })
            });
            System.out.println(Arrays.toString(s.getEncoded()));
     */
    private static final byte[] ENCODED_ATTRIBUTE_UTF8_EMAIL_ADDRESS = new byte[] {
            48, 35, 6, 9, 42, -122, 72, -122, -9, 13, 1, 9, 1, 49, 22, 12, 20, 115, 111, 109,
            101, 101, 109, 97, 105, 108, 64, 115, 101, 114, 118, 101, 114, 46, 99, 111, 109
    };

    /*
     * Encoded attribute obtained using BouncyCastle as an oracle for the known answer:
     *
            DERSequence s = new DERSequence(new ASN1Encodable[] {
                new ASN1ObjectIdentifier("1.2.840.113549.1.9.1"),
                    new DERSet(new ASN1Encodable[] {
                        new DEROctetString("someemail@server.com".getBytes())
                    })
            });
            System.out.println(Arrays.toString(s.getEncoded()));
    */
    private static final byte[] ENCODED_ATTRIBUTE_OCTET_EMAIL_ADDRESS = new byte[] {
            48, 35, 6, 9, 42, -122, 72, -122, -9, 13, 1, 9, 1, 49, 22, 4, 20, 115, 111, 109,
            101, 101, 109, 97, 105, 108, 64, 115, 101, 114, 118, 101, 114, 46, 99, 111, 109
    };

    /*
     * Encoded attribute obtained using BouncyCastle as an oracle for the known answer:
     *
            DERSequence s = new DERSequence(new ASN1Encodable[] {
                new ASN1ObjectIdentifier("1.2.840.113549.1.9.1"),
                new DERSet(new ASN1Encodable[] {
                    new DERUTF8String("someemail@server.com"),
                    new DERUTF8String("someotheremail@server.com"),
                })
            });
     */
    private static final byte[] ENCODED_ATTRIBUTE_SEQUENCE_OF_EMAIL_ADDRESSES = new byte[] {
            48, 62, 6, 9, 42, -122, 72, -122, -9, 13, 1, 9, 1, 49, 49, 12, 20, 115, 111, 109,
            101, 101, 109, 97, 105, 108, 64, 115, 101, 114, 118, 101, 114, 46, 99, 111, 109, 12, 25,
            115, 111, 109, 101, 111, 116, 104, 101, 114, 101, 109, 97, 105, 108, 64, 115, 101,
            114, 118, 101, 114, 46, 99, 111, 109
    };

    /*
     * Encoded attribute obtained using BouncyCastle as an oracle for the known answer:
     *
            DERSequence s = new DERSequence(new ASN1Encodable[] {
                new ASN1ObjectIdentifier("1.2.840.113549.1.9.3"),
                new DERSet(new ASN1Encodable[] {
                    new ASN1ObjectIdentifier("1.2.840.113549.1.7.2")
                })
            });
            System.out.println(Arrays.toString(s.getEncoded()));
    */
    private static final byte[] ENCODED_ATTRIBUTE_CONTENT_TYPE_SIGNED_DATA = new byte[] {
            48, 24, 6, 9, 42, -122, 72, -122, -9, 13, 1, 9, 3, 49, 11, 6, 9, 42, -122, 72, -122, -9,
            13, 1, 7, 2
    };

    /*
      echo -n 'someemail@server.com' | recode ../x1 | tr $'\x0a' ' ' \
          | sed 's/, /:/g' | sed 's/0x//g'
     */
    private static final String EXAMPLE_EMAIL_AS_HEX_BYTES =
            "73:6F:6D:65:65:6D:61:69:6C:40:73:65:72:76:65:72:2E:63:6F:6D";

    public void test_Constructor_String_String_success() {
        PKCS12Attribute att = new PKCS12Attribute(PKCS9_EMAIL_ADDRESS_OID, EXAMPLE_EMAIL_ADDRESS);
        assertEquals(PKCS9_EMAIL_ADDRESS_OID, att.getName());
        assertEquals(EXAMPLE_EMAIL_ADDRESS, att.getValue());
    }

    public void test_Constructor_String_String_nullOID_throwsException() {
        try {
            new PKCS12Attribute(null, EXAMPLE_EMAIL_ADDRESS);
            fail("Constructor allowed a null OID");
        } catch(NullPointerException expected) {
        }
    }

    public void test_Constructor_String_String_nullValue_throwsException() {
        try {
            new PKCS12Attribute(PKCS9_EMAIL_ADDRESS_OID, null);
            fail("Constructor allowed a null value");
        } catch(NullPointerException expected) {
        }
    }

    public void test_Constructor_String_String_wrongOID_throwsException() {
        try {
            PKCS12Attribute att =
                    new PKCS12Attribute("IDontThinkThisIsAnOID", EXAMPLE_EMAIL_ADDRESS);
            fail("Constructor allowed an invalid OID");
        } catch(IllegalArgumentException expected) {
        }
    }

    public void test_Constructor_byteArray_success() {
        PKCS12Attribute att = new PKCS12Attribute(ENCODED_ATTRIBUTE_UTF8_EMAIL_ADDRESS);
        assertEquals(PKCS9_EMAIL_ADDRESS_OID, att.getName());
        assertEquals(EXAMPLE_EMAIL_ADDRESS, att.getValue());
    }

    public void testConstructor_byteArray_nullEncoded_throwsException() {
        try {
            new PKCS12Attribute(null);
            fail("Constructor accepted null encoded value");
        } catch (NullPointerException expected) {
        }
    }

    public void test_Constructor_byteArray_wrongEncoding_throwsException() {
        try {
            new PKCS12Attribute(new byte[]{3, 14, 16});
            fail("Constructor accepted invalid encoding");
        } catch (IllegalArgumentException expected) {
        }
    }

    public void test_Constructor_String_String_sequenceValue() {
        PKCS12Attribute att = new PKCS12Attribute(
                PKCS9_EMAIL_ADDRESS_OID, EXAMPLE_SEQUENCE_OF_EMAILS);
        assertEquals(PKCS9_EMAIL_ADDRESS_OID, att.getName());
        assertEquals(EXAMPLE_SEQUENCE_OF_EMAILS, att.getValue());
        assertEquals(Arrays.toString(ENCODED_ATTRIBUTE_SEQUENCE_OF_EMAIL_ADDRESSES),
                Arrays.toString(att.getEncoded()));
    }

    public void test_Constructor_String_String_hexValues() {
        PKCS12Attribute att = new PKCS12Attribute(
                PKCS9_EMAIL_ADDRESS_OID, EXAMPLE_EMAIL_AS_HEX_BYTES);
        assertEquals(PKCS9_EMAIL_ADDRESS_OID, att.getName());
        assertEquals(EXAMPLE_EMAIL_AS_HEX_BYTES, att.getValue());
        // When specified as hex bytes, the underlying encoding is a DER octet string.
        assertEquals(Arrays.toString(ENCODED_ATTRIBUTE_OCTET_EMAIL_ADDRESS),
                Arrays.toString(att.getEncoded()));
    }

    @SuppressWarnings("SelfEquals")
    public void test_Equals() {
        PKCS12Attribute att = new PKCS12Attribute(
                PKCS9_EMAIL_ADDRESS_OID, EXAMPLE_EMAIL_ADDRESS);
        assertTrue(att.equals(att));
        assertFalse(att.equals(new Object()));
        assertFalse(att.equals(null));
        assertTrue(att.equals(new PKCS12Attribute(ENCODED_ATTRIBUTE_UTF8_EMAIL_ADDRESS)));
        assertFalse(att.equals(
                new PKCS12Attribute(ENCODED_ATTRIBUTE_SEQUENCE_OF_EMAIL_ADDRESSES)));
    }

    /* Test the case in which the value encoded is an object id.*/
    public void test_encoding_ObjectIdValue() {
        PKCS12Attribute att = new PKCS12Attribute(ENCODED_ATTRIBUTE_CONTENT_TYPE_SIGNED_DATA);
        assertEquals(PKCS9_CONTENT_TYPE_OID, att.getName());
        /* Value is correctly decoded to a string. */
        assertEquals(PKCS7_SIGNED_DATA_OID, att.getValue());
    }
}
