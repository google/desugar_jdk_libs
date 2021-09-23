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
import sun.security.x509.ReasonFlags;


public class ReasonFlagsTest extends TestCase {
    /**
     * The logic for toString was changed in rev/04cda5b7a3c1. The expected result is the same
     * before and after the change.
     */
    public void testToString() throws Exception {
        String prefix = "Reason Flags [\n";

        String[] parts = new String[] {
            "  Unused\n",
            "  Key Compromise\n",
            "  CA Compromise\n",
            "  Affiliation_Changed\n",
            "  Superseded\n",
            "  Cessation Of Operation\n",
            "  Certificate Hold\n",
            "  Privilege Withdrawn\n",
            "  AA Compromise\n"
        };

        String suffix = "]\n";
        Function<byte[], Object> objectCreator = byteArray -> new ReasonFlags(byteArray);
        Utils.test_toString_bitArrayBasedClass(parts, objectCreator, prefix, suffix);
    }
}

