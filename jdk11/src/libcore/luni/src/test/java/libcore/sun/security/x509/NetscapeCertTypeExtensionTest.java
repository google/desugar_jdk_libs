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

import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionTest extends TestCase {
    /**
     * The logic for toString was changed in rev/04cda5b7a3c1. The expected result is the same
     * before and after the change.
     */
    public void testToString() throws Exception {
        String prefix = "ObjectId: 2.16.840.1.113730.1.1 Criticality=true\n"
                + "NetscapeCertType [\n";

        String[] parts = new String[] {
                "   SSL client\n",
                "   SSL server\n",
                "   S/MIME\n",
                "   Object Signing\n",
                "", // Note: byte 4 is reserved.
                "   SSL CA\n",
                "   S/MIME CA\n",
                "   Object Signing CA",
        };

        String suffix = "]\n";
        Function<byte[], Object> objectCreator = byteArray -> {
            try {
                return new NetscapeCertTypeExtension(byteArray);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Utils.test_toString_bitArrayBasedClass(parts, objectCreator, prefix, suffix);
    }
}