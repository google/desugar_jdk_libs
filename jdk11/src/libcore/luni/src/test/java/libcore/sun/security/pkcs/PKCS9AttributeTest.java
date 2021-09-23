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

package libcore.sun.security.pkcs;

import junit.framework.TestCase;

import sun.security.pkcs.PKCS9Attribute;
import sun.security.util.DerValue;

public class PKCS9AttributeTest extends TestCase {
    // Before rev/f9224fb49890, the unstructuredName attributes supported only IA5 strings. They
    // support printable strings as well.
    // See https://bugs.openjdk.java.net/browse/JDK-8016916
    public void testUnstructuredNameWithPrintableString() throws Exception {
        // SEQUENCE
        //   OBJECT IDENTIFIER1.2.840.113549.1.9.2 (unstructuredName)
        //   SET(1 elem)
        //     PrintableString requestTestWithExt
        byte[] unstructuredNamePkcs9Attribute = {
                0x30, 0x21, 0x06, 0x09, 0x2A, (byte) 0x86, 0x48, (byte) 0x86, (byte) 0xF7, 0x0D,
                0x01, 0x09, 0x02, 0x31, 0x14, 0x13, 0x12, 0x72, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74,
                0x54, 0x65, 0x73, 0x74, 0x57, 0x69, 0x74, 0x68, 0x45, 0x78, 0x74};
        new PKCS9Attribute(new DerValue(unstructuredNamePkcs9Attribute));
    }
}

