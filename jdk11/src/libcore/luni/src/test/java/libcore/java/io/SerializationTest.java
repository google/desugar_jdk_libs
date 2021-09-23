/*
 * Copyright (C) 2011 The Android Open Source Project
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
 */

package libcore.java.io;

import junit.framework.TestCase;

import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.NotSerializableException;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import libcore.libcore.util.SerializationTester;

public final class SerializationTest extends TestCase {

    // http://b/4471249
    public void testSerializeFieldMadeTransient() throws Exception {
        // Does ObjectStreamClass have the right idea?
        ObjectStreamClass osc = ObjectStreamClass.lookup(FieldMadeTransient.class);
        ObjectStreamField[] fields = osc.getFields();
        assertEquals(1, fields.length);
        assertEquals("nonTransientInt", fields[0].getName());
        assertEquals(int.class, fields[0].getType());

        // this was created by serializing a FieldMadeTransient with a non-0 transientInt
        String s = "aced0005737200346c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6e54657"
                + "374244669656c644d6164655472616e7369656e74000000000000000002000149000c7472616e736"
                + "9656e74496e747870abababab";
        FieldMadeTransient deserialized = (FieldMadeTransient) SerializationTester.deserializeHex(s);
        assertEquals(0, deserialized.transientInt);
    }

    static class FieldMadeTransient implements Serializable {
        private static final long serialVersionUID = 0L;
        @SuppressWarnings("unused")
        private transient int transientInt;
        @SuppressWarnings("unused")
        private int nonTransientInt;
    }

    public void testSerializeFieldMadeStatic() throws Exception {
        // Does ObjectStreamClass have the right idea?
        ObjectStreamClass osc = ObjectStreamClass.lookup(FieldMadeStatic.class);
        ObjectStreamField[] fields = osc.getFields();
        assertEquals(0, fields.length);

        // This was created by serializing a FieldMadeStatic with a non-static staticInt
        String s = "aced0005737200316c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6e54657"
                + "374244669656c644d6164655374617469630000000000000000020001490009737461746963496e7"
                + "47870000022b8";
        FieldMadeStatic deserialized = (FieldMadeStatic) SerializationTester.deserializeHex(s);
        // The field data must be ignored if it is static.
        assertEquals(9999, deserialized.staticInt);
    }

    static class FieldMadeStatic implements Serializable {
        private static final long serialVersionUID = 0L;
        // private int staticInt = 8888;
        private static int staticInt = 9999;
    }

    public static boolean serializableContainer1InitializedFlag = false;
    public static boolean unserializable1InitializedFlag = false;

    public static class Unserializable1 {
        static {
            SerializationTest.unserializable1InitializedFlag = true;
        }
    }

    static class SerializableContainer1 implements Serializable {
        private static final long serialVersionUID = 0L;
        private Unserializable1 unserializable = null;

        static {
            serializableContainer1InitializedFlag = true;
        }
    }

    // We can serialize an object that has an unserializable field providing it is null.
    public void testDeserializeNullUnserializableField() throws Exception {
        // This was created by creating a new SerializableContainer and not setting the
        // unserializable field. A canned serialized form is used so we can tell if the static
        // initializers were executed during deserialization.
        // SerializationTester.serializeHex(new SerializableContainer1());
        String s = "aced0005737200386c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6e54657"
                  + "3742453657269616c697a61626c65436f6e7461696e65723100000000000000000200014c000e7"
                  + "56e73657269616c697a61626c657400124c6a6176612f6c616e672f4f626a6563743b787070";

        assertFalse(serializableContainer1InitializedFlag);
        assertFalse(unserializable1InitializedFlag);

        SerializableContainer1 sc = (SerializableContainer1) SerializationTester.deserializeHex(s);
        assertNull(sc.unserializable);

        // Confirm the container was initialized, but the class for the null field was not.
        assertTrue(serializableContainer1InitializedFlag);
        assertFalse(unserializable1InitializedFlag);
    }

    static class Unserializable2 {
    }

    static class HasUnserializableField implements Serializable {
        private static final long serialVersionUID = 0L;
        @SuppressWarnings("unused") // Required to make objects unserializable.
        private Unserializable2 unserializable = new Unserializable2();
    }

    // We must not serialize an object that has a non-null unserializable field.
    public void testSerializeUnserializableField() throws Exception {
        HasUnserializableField uf = new HasUnserializableField();
        try {
            SerializationTester.serializeHex(uf);
            fail();
        } catch (NotSerializableException expected) {
        }
    }

    public static boolean serializableContainer2InitializedFlag = false;

    @SuppressWarnings("unused") // Required for deserialization test
    static class SerializableContainer2 implements Serializable {
        private static final long serialVersionUID = 0L;
        private WasSerializable unserializable = null;

        static {
            serializableContainer2InitializedFlag = true;
        }
    }

    // It must not be possible to deserialize an object if a field is no longer serializable.
    public void testDeserializeUnserializableField() throws Exception {
        // This was generated by creating a SerializableContainer2 and setting the unserializable
        // field to a WasSerializable when it was still Serializable. A canned serialized form is
        // used so we can tell if the static initializers were executed during deserialization.
        // SerializableContainer2 sc = new SerializableContainer2();
        // sc.unserializable = new WasSerializable();
        // SerializationTester.serializeHex(sc);
        String s = "aced0005737200386c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6e54657"
                  + "3742453657269616c697a61626c65436f6e7461696e65723200000000000000000200014c000e7"
                  + "56e73657269616c697a61626c657400334c6c6962636f72652f6a6176612f696f2f53657269616"
                  + "c697a6174696f6e546573742457617353657269616c697a61626c653b7870737200316c6962636"
                  + "f72652e6a6176612e696f2e53657269616c697a6174696f6e546573742457617353657269616c6"
                  + "97a61626c65000000000000000002000149000169787000000000";

        assertFalse(serializableContainer2InitializedFlag);
        assertFalse(wasSerializableInitializedFlag);
        try {
            SerializationTester.deserializeHex(s);
            fail();
        } catch (InvalidClassException expected) {
        }
        // The container class will be initialized to establish the serialVersionUID.
        assertTrue(serializableContainer2InitializedFlag);
        // Confirm the contained class was initialized.
        assertFalse(wasSerializableInitializedFlag);
    }

    public void testSerialVersionUidChange() throws Exception {
        // this was created by serializing a SerialVersionUidChanged with serialVersionUID = 0L
        String s = "aced0005737200396c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6e54657"
                + "3742453657269616c56657273696f6e5569644368616e67656400000000000000000200014900016"
                + "1787000000003";
        try {
            SerializationTester.deserializeHex(s);
            fail();
        } catch (InvalidClassException expected) {
        }
    }

    @SuppressWarnings("unused") // Required for deserialization test
    static class SerialVersionUidChanged implements Serializable {
        private static final long serialVersionUID = 1L; // was 0L
        private int a;
    }

    public void testMissingSerialVersionUid() throws Exception {
        // this was created by serializing a FieldsChanged with one int field named 'a'
        String s = "aced00057372002f6c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6e54657"
                + "374244669656c64734368616e6765643bcfb934e310fa1c02000149000161787000000003";
        try {
            SerializationTester.deserializeHex(s);
            fail();
        } catch (InvalidClassException expected) {
        }
    }

    @SuppressWarnings("unused") // Required for deserialization test
    static class FieldsChanged implements Serializable {
        private int b; // was 'a'
    }

    public static boolean wasSerializableInitializedFlag = false;

    @SuppressWarnings("unused")  // Required for deserialization test.
    public static class WasSerializable /* implements java.io.Serializable */ {
        static final long serialVersionUID = 0L;
        static {
            SerializationTest.wasSerializableInitializedFlag = true;
        }
        private int i;
    }

    public void testDeserializeWasSerializableClass() throws Exception {
        // This was created by serializing a WasSerializable when it was serializable.
        // String s = SerializationTester.serializeHex(new WasSerializable());
        final String s = "aced0005737200316c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6"
                + "e546573742457617353657269616c697a61626c65000000000000000002000149000169787000000"
                + "000";

        assertFalse(wasSerializableInitializedFlag);
        try {
            SerializationTester.deserializeHex(s);
            fail();
        } catch (InvalidClassException expected) {
        }
        assertFalse(wasSerializableInitializedFlag);
    }

    // The WasExternalizable class before it was modified.
    /*
    public static class WasExternalizable implements Externalizable {
        static final long serialVersionUID = 0L;

        @Override
        public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

        }

        @Override
        public void writeExternal(ObjectOutput output) throws IOException {

        }
    }
    */

    public static boolean wasExternalizableInitializedFlag = false;

    @SuppressWarnings("unused") // Required for deserialization test
    public static class WasExternalizable implements Serializable {
        static final long serialVersionUID = 0L;
        static {
            SerializationTest.wasExternalizableInitializedFlag = true;
        }

    }

    public void testDeserializeWasExternalizableClass() throws Exception {
        // This was created by serializing a WasExternalizable when it was externalizable.
        // String s = SerializationTester.serializeHex(new WasExternalizable());
        final String s = "aced0005737200336c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6"
                + "e546573742457617345787465726e616c697a61626c6500000000000000000c0000787078";

        assertFalse(wasExternalizableInitializedFlag);
        try {
            SerializationTester.deserializeHex(s);
            fail();
        } catch (InvalidClassException expected) {
        }
        // Unlike other similar tests static initialization will take place if the local class is
        // Serializable or Externalizable because serialVersionUID field is accessed.
        // The RI appears to do the same.
        assertTrue(wasExternalizableInitializedFlag);
    }

    // The WasEnum class before it was modified.
    /*
    public enum WasEnum {
        VALUE
    }
    */

    public static boolean wasEnumInitializedFlag = false;

    @SuppressWarnings("unused") // Required for deserialization test
    public static class WasEnum {
        static final long serialVersionUID = 0L;
        static {
            SerializationTest.wasEnumInitializedFlag = true;
        }
    }

    public void testDeserializeWasEnum() throws Exception {
        // This was created by serializing a WasEnum when it was an enum.
        // String s = SerializationTester.serializeHex(WasEnum.VALUE);
        final String s = "aced00057e7200296c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6"
                + "e5465737424576173456e756d00000000000000001200007872000e6a6176612e6c616e672e456e7"
                + "56d0000000000000000120000787074000556414c5545";

        assertFalse(wasEnumInitializedFlag);
        try {
            SerializationTester.deserializeHex(s);
            fail();
        } catch (InvalidClassException expected) {
        }
        assertFalse(wasEnumInitializedFlag);
    }

    // The WasObject class before it was modified.
    /*
    public static class WasObject implements java.io.Serializable {
        static final long serialVersionUID = 0L;
        private int i;
    }
    */

    public static boolean wasObjectInitializedFlag;

    @SuppressWarnings("unused") // Required for deserialization test
    public enum WasObject {
        VALUE;

        static {
            SerializationTest.wasObjectInitializedFlag = true;
        }
    }

    public void testDeserializeWasObject() throws Exception {
        // This was created by serializing a WasObject when it wasn't yet an enum.
        // String s = SerializationTester.serializeHex(new WasObject());
        final String s = "aced00057372002b6c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6"
                + "e54657374245761734f626a656374000000000000000002000149000169787000000000";

        assertFalse(wasObjectInitializedFlag);
        try {
            SerializationTester.deserializeHex(s);
            fail();
        } catch (InvalidClassException expected) {
        }
        assertFalse(wasObjectInitializedFlag);
    }

    @SuppressWarnings("unused") // Required for deserialization test
    public enum EnumMissingValue {
        /*MISSING_VALUE*/
    }

    public void testDeserializeEnumMissingValue() throws Exception {
        // This was created by serializing a EnumMissingValue when it had MISSING_VALUE.
        // String s = SerializationTester.serializeHex(EnumMissingValue.MISSING_VALUE);
        final String s = "aced00057e7200326c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6"
                + "e5465737424456e756d4d697373696e6756616c756500000000000000001200007872000e6a61766"
                + "12e6c616e672e456e756d0000000000000000120000787074000d4d495353494e475f56414c5545";

        try {
            SerializationTester.deserializeHex(s);
            fail();
        } catch (InvalidObjectException expected) {
        }
    }


    public static Object hasStaticInitializerObject;

    public static class HasStaticInitializer implements Serializable {
        static {
            SerializationTest.hasStaticInitializerObject = new Object();
        }
    }

    public void testDeserializeStaticInitializerIsRunEventually() throws Exception {
        // This was created by serializing a HasStaticInitializer
        // String s = SerializationTester.serializeHex(new HasStaticInitializer());
        final String s = "aced0005737200366c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6"
                + "e5465737424486173537461746963496e697469616c697a6572138aa8ed9e9b660a0200007870";

        // Confirm the ClassLoader behaves as it should.
        Class.forName(
                HasStaticInitializer.class.getName(),
                false /* shouldInitialize */,
                Thread.currentThread().getContextClassLoader());
        assertNull(hasStaticInitializerObject);

        SerializationTester.deserializeHex(s);

        assertNotNull(hasStaticInitializerObject);
    }

    @SuppressWarnings("unused") // Required for deserialization test
    public static /*interface*/ class WasInterface {
    }

    @SuppressWarnings("unused") // Required for deserialization test
    public static class SerializableInvocationHandler implements InvocationHandler, Serializable {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            return null;
        }
    }

    public void testDeserializeProxyWasInterface() throws Exception {
        // This was created by serializing a proxy referencing WasInterface when it was an
        // interface.
        // Object o = Proxy.newProxyInstance(
        //        Thread.currentThread().getContextClassLoader(),
        //        new Class[] { WasInterface.class },
        //        new SerializableInvocationHandler());
        // String s = SerializationTester.serializeHex(o);
        final String s = "aced0005737d00000001002e6c6962636f72652e6a6176612e696f2e53657269616c697a6"
                + "174696f6e5465737424576173496e74657266616365787200176a6176612e6c616e672e7265666c6"
                + "563742e50726f7879e127da20cc1043cb0200014c0001687400254c6a6176612f6c616e672f72656"
                + "66c6563742f496e766f636174696f6e48616e646c65723b78707372003f6c6962636f72652e6a617"
                + "6612e696f2e53657269616c697a6174696f6e546573742453657269616c697a61626c65496e766f6"
                + "36174696f6e48616e646c6572e6ceffa2941ee3210200007870";
        try {
            SerializationTester.deserializeHex(s);
            fail();
        } catch (ClassNotFoundException expected) {
        }
    }

    @SuppressWarnings("unused") // Required for deserialization test
    public static class WasSerializableInvocationHandler
            implements InvocationHandler /*, Serializable*/ {
        static final long serialVersionUID = 0L;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            return null;
        }
    }

    public void testDeserializeProxyInvocationHandlerWasSerializable() throws Exception {
        // This was created by serializing a proxy referencing WasSerializableInvocationHandler when
        // it was Serializable.
        // Object o = Proxy.newProxyInstance(
        //        Thread.currentThread().getContextClassLoader(),
        //        new Class[] { Comparable.class },
        //        new WasSerializableInvocationHandler());
        // String s = SerializationTester.serializeHex(o);
        final String s = "aced0005737d0000000100146a6176612e6c616e672e436f6d70617261626c65787200176"
                + "a6176612e6c616e672e7265666c6563742e50726f7879e127da20cc1043cb0200014c00016874002"
                + "54c6a6176612f6c616e672f7265666c6563742f496e766f636174696f6e48616e646c65723b78707"
                + "37200426c6962636f72652e6a6176612e696f2e53657269616c697a6174696f6e546573742457617"
                + "353657269616c697a61626c65496e766f636174696f6e48616e646c6572000000000000000002000"
                + "07870";
        try {
            SerializationTester.deserializeHex(s);
            fail();
        } catch (InvalidClassException expected) {
        }
    }
}
