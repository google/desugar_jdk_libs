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

package libcore.java.lang.invoke;

import junit.framework.TestCase;

import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandleInfo.*;

public class MethodHandleInfoTest extends TestCase {
    public void test_toString() {
        final MethodType type = MethodType.methodType(String.class, String.class);
        String string = MethodHandleInfo.toString(REF_invokeVirtual, String.class, "concat",  type);
        assertEquals("invokeVirtual java.lang.String.concat:(String)String", string);

        try {
            MethodHandleInfo.toString(-1, String.class, "concat", type);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            MethodHandleInfo.toString(REF_invokeVirtual, String.class, null, type);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            MethodHandleInfo.toString(REF_invokeVirtual, null, "concat", type);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            MethodHandleInfo.toString(REF_invokeVirtual, String.class, "concat", null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void test_referenceKindToString() {
        assertEquals("getField", referenceKindToString(REF_getField));
        assertEquals("getStatic", referenceKindToString(REF_getStatic));
        assertEquals("putField", referenceKindToString(REF_putField));
        assertEquals("putStatic", referenceKindToString(REF_putStatic));
        assertEquals("invokeVirtual", referenceKindToString(REF_invokeVirtual));
        assertEquals("invokeStatic", referenceKindToString(REF_invokeStatic));
        assertEquals("invokeSpecial", referenceKindToString(REF_invokeSpecial));
        assertEquals("newInvokeSpecial", referenceKindToString(REF_newInvokeSpecial));
        assertEquals("invokeInterface", referenceKindToString(REF_invokeInterface));

        try {
            referenceKindToString(-1);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            referenceKindToString(256);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }
}
