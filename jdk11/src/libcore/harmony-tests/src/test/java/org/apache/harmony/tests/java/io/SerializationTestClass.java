package org.apache.harmony.tests.java.io;

/**
 * Test classes for {@link ComputeSerialVersionUIDTest}. Used to ensure
 * that serial version UIDs are generated correctly for various combinations
 * of interfaces, fields, constructors and methods.
 */
public class SerializationTestClass implements java.io.Serializable {

    // Test class names
    public static class TestClassName1 implements java.io.Serializable {
    }

    public static class TestClassName2T_T$T implements java.io.Serializable {
    }

    // Test Modifiers
    public static class TestClassModifierPublic implements java.io.Serializable {
    }

    interface TestClassModifierInterfaceHelper extends java.io.Serializable {
    }

    public static class TestClassModifierInterface implements
            TestClassModifierInterfaceHelper {
    }

    final static class TestClassModifierFinal implements java.io.Serializable {
    }

    abstract static class TestClassModifierAbstractHelper implements
            java.io.Serializable {
    }

    public static class TestClassModifierAbstract extends
            TestClassModifierAbstractHelper {
    }


    // TODO Arrays always are abstract

    // TODO Non public interface has no abstract modifier


    // Test interfaces
    interface A extends java.io.Serializable {
    }

    interface B extends java.io.Serializable {
    }

    interface C extends A {
    }

    public static class TestInterfaces implements java.io.Serializable {
    }

    public static class TestInterfacesA implements A {
    }

    public static class TestInterfacesAB implements A, B {
    }

    public static class TestInterfacesBA implements B, A {
    }

    public static class TestInterfacesC implements C {
    }

    public static class TestInterfacesCA implements C, A {
    }

    public static class TestInterfacesABC implements A, B, C {
    }

    public static class TestInterfacesACB implements A, C, B {
    }

    public static class TestInterfacesBAC implements B, A, C {
    }

    public static class TestInterfacesBCA implements B, C, A {
    }

    public static class TestInterfacesCAB implements C, A, B {
    }

    public static class TestInterfacesCBA implements C, B, A {
    }

    /**
     * Modifier.PUBLIC | Modifier.PRIVATE | Modifier.PROTECTED | Modifier.STATIC |
     * Modifier.FINAL | Modifier.VOLATILE | Modifier.TRANSIENT
     */
    // Test Fields
    public static class TestFieldsNone implements java.io.Serializable {
    }

    public static class TestFieldsOnePublic implements java.io.Serializable {
        public int one;
    }

    public static class TestFieldsTwoPublic implements java.io.Serializable {
        public int one;
        public int two;
    }

    @SuppressWarnings("unused")
    public static class TestFieldsOnePrivate implements java.io.Serializable {
        private int one;
    }

    @SuppressWarnings("unused")
    public static class TestFieldsTwoPrivate implements java.io.Serializable {
        private int one;
        private int two;
    }

    public static class TestFieldsOneProtected implements java.io.Serializable {
        protected int one;
    }

    public static class TestFieldsTwoProtected implements java.io.Serializable {
        protected int one;
        protected int two;
    }

    public static class TestFieldsOneStatic implements java.io.Serializable {
        static int one;
    }

    public static class TestFieldsTwoStatic implements java.io.Serializable {
        static int one;
        static int two;
    }

    public static class TestFieldsOneFinal implements java.io.Serializable {
        final int one = 0;
    }

    public static class TestFieldsTwoFinal implements java.io.Serializable {
        final int one = 0;
        final int two = 0;
    }

    public static class TestFieldsOneVolatile implements java.io.Serializable {
        volatile int one;
    }

    public static class TestFieldsTwoVolatile implements java.io.Serializable {
        volatile int one;
        volatile int two;
    }

    public static class TestFieldsOneTransient implements java.io.Serializable {
        transient int one;
    }

    public static class TestFieldsTwoTransient implements java.io.Serializable {
        transient int one;
        transient int two;
    }

    public static class TestFieldSignatures implements java.io.Serializable {
        Object l;
        int i;
        short s;
        long j;
        boolean z;
        char c;
        double d;
        float f;
        byte b;
    }


    // Test Constructors
    public static class TestConstructorNone implements java.io.Serializable {
    }

    public static class TestConstructorOne implements java.io.Serializable {
        public TestConstructorOne() {
        }
    }

    public static class TestConstructorTwo implements java.io.Serializable {
        public TestConstructorTwo(byte b) {
        }

        public TestConstructorTwo(char c) {
        }
    }

    public static class TestConstructorTwoReverse implements java.io.Serializable {
        public TestConstructorTwoReverse(char c) {
        }

        public TestConstructorTwoReverse(byte b) {
        }
    }


    // Test Constructor Modifiers
    public static class TestConstructorPublic implements java.io.Serializable {
        public TestConstructorPublic() {
        }
    }

    public static class TestConstructorPrivate implements java.io.Serializable {
        private TestConstructorPrivate() {
        }

        public TestConstructorPrivate(int i) {
            this();
        }
    }

    public static class TestConstructorProtected implements java.io.Serializable {
        protected TestConstructorProtected() {
        }
    }
    // TODO constructor modifier strict?
    // TODO constructor modifier static?
    // TODO constructor modifier final?
    // TODO constructor modifier synchronized?
    // TODO constructor modifier native?
    // TODO constructor modifier abstract?


    // Test constructor signature
    public static class TestConstructorSignature implements java.io.Serializable {
        public TestConstructorSignature(boolean z, byte b, char c, short s,
                int i, float f, double j, Object l) {
        }
    }


    // Test Method Modifiers
    public static class TestMethodPublic implements java.io.Serializable {
        public void method() {
        }
    }

    @SuppressWarnings("unused")
    public static class TestMethodPrivate implements java.io.Serializable {
        private void method() {
        }
    }

    public static class TestMethodProtected implements java.io.Serializable {
        protected void method() {
        }
    }

    public static class TestMethodStrict implements java.io.Serializable {
        strictfp void method() {
        }
    }

    public static class TestMethodStatic implements java.io.Serializable {
        static void method() {
        }
    }

    public static class TestMethodFinal implements java.io.Serializable {
        final void method() {
        }
    }

    public static class TestMethodSynchronized implements java.io.Serializable {
        synchronized void method() {
        }
    }

    public static class TestMethodNative implements java.io.Serializable {
        native void method();
    }

    public static abstract class TestMethodAbstractHelper implements
            java.io.Serializable {
        abstract void method();
    }

    public static class TestMethodAbstract extends TestMethodAbstractHelper implements
            java.io.Serializable {
        @Override
        void method() {
        }
    }


    // Test method signature
    public static class TestMethodSignature implements java.io.Serializable {
        public void method(boolean z, byte b, char c, short s, int i, float f,
                double j, Object l) {
        }
    }


    // Test method return signature
    public static class TestMethodReturnSignature implements java.io.Serializable {
        public void methodV() {
        }

        public boolean methodZ() {
            return false;
        }

        public byte methodB() {
            return 0;
        }

        public char methodC() {
            return '0';
        }

        public short methodS() {
            return 0;
        }

        public int methodI() {
            return 0;
        }

        public float methodF() {
            return 0F;
        }

        public double methodD() {
            return 0D;
        }

        public Object methodL() {
            return null;
        }
    }
}
