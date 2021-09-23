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

import junit.framework.Assert;

import java.util.function.Function;

class Utils extends Assert {
    /**
     * Many classes in this package can be created using a bit array, and the toString method
     * depends only on the bit array. The logic for toString was changed in rev/04cda5b7a3c1.
     * The expected result is the same before and after the change.
     * @param parts The different parts of the result
     * @param objectCreator Function to create a new instance of the class tested.
     * @param prefix prefix in all toString results
     * @param suffix suffix in all toString results
     */
    static void test_toString_bitArrayBasedClass(
            String[] parts,
            Function<byte[], Object> objectCreator,
            String prefix,
            String suffix) {
        testWithEachSinglePart(parts, objectCreator, prefix, suffix);
        testWithAllParts(parts, objectCreator, prefix, suffix);
        testWithNoParts(parts, objectCreator, prefix, suffix);
        testWithEveryOtherPart(parts, objectCreator, prefix, suffix);
    }

    private static void testWithEachSinglePart(
            String[] parts, Function<byte[], Object> objectCreator, String prefix, String suffix) {
        int bitCounter = 0, byteCounter = 1;
        for (int i = 0; i < parts.length; i++) {
            byte[] ba = new byte[byteCounter];
            ba[byteCounter - 1] = (byte) (1 << (7 - bitCounter));
            bitCounter++;
            if (bitCounter == 8) {
                bitCounter = 0;
                byteCounter++;
            }
            Object o =  objectCreator.apply(ba);
            assertEquals(prefix + parts[i] + suffix, o.toString());
        }
    }

    private static void testWithAllParts(
            String[] parts, Function<byte[], Object> objectCreator, String prefix, String suffix) {
        int bitsInAByte = 8;
        int allOnesLength = (parts.length + bitsInAByte - 1) / bitsInAByte;
        byte[] allOnes = new byte[allOnesLength];

        for (int i = 0; i < allOnes.length; i++) {
            allOnes[i] = -1;
        }
        assertEquals(prefix + String.join("", parts)
                + suffix, objectCreator.apply(allOnes).toString());
    }

    private static void testWithNoParts(
            String[] parts, Function<byte[], Object> objectCreator, String prefix, String suffix) {
        // Test with empty array
        assertEquals(prefix + suffix, objectCreator.apply(new byte[0]).toString());

        // Test with array will all zeros
        assertEquals(prefix + suffix, objectCreator.apply(new byte[parts.length]).toString());
    }

    private static void testWithEveryOtherPart(
            String[] parts, Function<byte[], Object> objectCreator, String prefix, String suffix) {
        int bitsInAByte = 8;
        byte[] ba = new byte[(parts.length + bitsInAByte - 1) / bitsInAByte];
        String expectedResult = new String();
        for (int i = 0; i < parts.length; i += 2) {
            ba[i / bitsInAByte] = (byte) 170; // Binary 10101010
            expectedResult += parts[i];
        }
        assertEquals(prefix + expectedResult + suffix, objectCreator.apply(ba).toString());
    }
}
