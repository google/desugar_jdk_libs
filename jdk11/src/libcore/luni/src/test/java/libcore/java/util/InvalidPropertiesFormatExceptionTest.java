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
 * limitations under the License.
 *
 */

package libcore.java.util;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.InvalidPropertiesFormatException;
import libcore.libcore.util.SerializationTester;

public class InvalidPropertiesFormatExceptionTest extends TestCase {

    public void testConstructorArgs() {
        InvalidPropertiesFormatException e = new InvalidPropertiesFormatException("testing");
        assertEquals("testing", e.getMessage());
        assertNull(e.getCause());

        InvalidPropertiesFormatException e2 = new InvalidPropertiesFormatException(e);
        assertSame(e, e2.getCause());
        assertEquals(e.toString(), e2.getMessage());
    }

    public void testDeserialize_notSupported() throws Exception {
        // Result of
        // SerializationTester.serializeHex(new InvalidPropertiesFormatException("testing"))
        // using a InvalidPropertiesFormatException class that had its
        // writeObject() method commented out.
        String hex = "aced00057372002a6a6176612e7574696c2e496e76616c696450726f"
                + "70657274696573466f726d6174457863657074696f6e6bbbea5ee5f9cb5"
                + "b020000787200136a6176612e696f2e494f457863657074696f6e6c8073"
                + "646525f0ab020000787200136a6176612e6c616e672e457863657074696"
                + "f6ed0fd1f3e1a3b1cc4020000787200136a6176612e6c616e672e546872"
                + "6f7761626c65d5c635273977b8cb0300044c000563617573657400154c6"
                + "a6176612f6c616e672f5468726f7761626c653b4c000d64657461696c4d"
                + "6573736167657400124c6a6176612f6c616e672f537472696e673b5b000"
                + "a737461636b547261636574001e5b4c6a6176612f6c616e672f53746163"
                + "6b5472616365456c656d656e743b4c00147375707072657373656445786"
                + "3657074696f6e737400104c6a6176612f7574696c2f4c6973743b787071"
                + "007e000874000774657374696e677572001e5b4c6a6176612e6c616e672"
                + "e537461636b5472616365456c656d656e743b02462a3c3cfd2239020000"
                + "78700000000a7372001b6a6176612e6c616e672e537461636b547261636"
                + "5456c656d656e746109c59a2636dd8502000449000a6c696e654e756d62"
                + "65724c000e6465636c6172696e67436c61737371007e00054c000866696"
                + "c654e616d6571007e00054c000a6d6574686f644e616d6571007e000578"
                + "70000000457400366c6962636f72652e6a6176612e7574696c2e496e766"
                + "16c696450726f70657274696573466f726d6174457863657074696f6e54"
                + "657374740029496e76616c696450726f70657274696573466f726d61744"
                + "57863657074696f6e546573742e6a61766174001a746573745365726961"
                + "6c697a655f6e6f74537570706f727465647371007e000cfffffffe74001"
                + "86a6176612e6c616e672e7265666c6563742e4d6574686f6474000b4d65"
                + "74686f642e6a617661740006696e766f6b657371007e000c000000c2740"
                + "028766f6761722e7461726765742e6a756e69742e4a756e69743324566f"
                + "6761724a556e69745465737474000b4a756e6974332e6a6176617400037"
                + "2756e7371007e000c0000003b740024766f6761722e7461726765742e6a"
                + "756e69742e566f6761725465737452756e6e65722431740014566f67617"
                + "25465737452756e6e65722e6a6176617400086576616c75617465737100"
                + "7e000c0000004874002b766f6761722e7461726765742e6a756e69742e5"
                + "4696d656f7574416e6441626f727452756e52756c65243274001b54696d"
                + "656f7574416e6441626f727452756e52756c652e6a61766174000463616"
                + "c6c7371007e000c0000004474002b766f6761722e7461726765742e6a75"
                + "6e69742e54696d656f7574416e6441626f727452756e52756c652432740"
                + "01b54696d656f7574416e6441626f727452756e52756c652e6a61766174"
                + "000463616c6c7371007e000c000000ed74001f6a6176612e7574696c2e6"
                + "36f6e63757272656e742e4675747572655461736b74000f467574757265"
                + "5461736b2e6a61766174000372756e7371007e000c0000046d7400276a6"
                + "176612e7574696c2e636f6e63757272656e742e546872656164506f6f6c"
                + "4578656375746f72740017546872656164506f6f6c4578656375746f722"
                + "e6a61766174000972756e576f726b65727371007e000c0000025f74002e"
                + "6a6176612e7574696c2e636f6e63757272656e742e546872656164506f6"
                + "f6c4578656375746f7224576f726b6572740017546872656164506f6f6c"
                + "4578656375746f722e6a61766174000372756e7371007e000c000002f87"
                + "400106a6176612e6c616e672e54687265616474000b5468726561642e6a"
                + "61766174000372756e7372001f6a6176612e7574696c2e436f6c6c65637"
                + "4696f6e7324456d7074794c6973747ab817b43ca79ede020000787078";
        try {
            Object obj = SerializationTester.deserializeHex(hex);
            fail("Deserialized to " + obj);
        } catch (NotSerializableException expected) {
            // Check that this is the right exception that we expected.
            assertEquals("Not serializable.", expected.getMessage());
        }
    }

    public void testSerialize_notSupported() throws Exception {
        Serializable notActuallySerializable = new InvalidPropertiesFormatException("testing");
        try {
            try (ObjectOutputStream out = new ObjectOutputStream(new ByteArrayOutputStream())) {
                out.writeObject(notActuallySerializable);
            }
            fail();
        } catch (NotSerializableException expected) {
            // Check that this is the right exception that we expected.
            assertEquals("Not serializable.", expected.getMessage());
        }
    }
}
