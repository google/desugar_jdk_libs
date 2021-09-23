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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static java.lang.invoke.MethodHandles.Lookup.*;

public class MethodHandlesTest extends TestCase {
    private static final int ALL_LOOKUP_MODES = (PUBLIC | PRIVATE | PACKAGE | PROTECTED);

    public void test_publicLookupClassAndModes() {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        assertSame(Object.class, publicLookup.lookupClass());
        assertEquals(PUBLIC, publicLookup.lookupModes());
    }

    public void test_defaultLookupClassAndModes() {
        MethodHandles.Lookup defaultLookup = MethodHandles.lookup();
        assertSame(MethodHandlesTest.class, defaultLookup.lookupClass());
        assertEquals(ALL_LOOKUP_MODES, defaultLookup.lookupModes());
    }

    public void test_LookupIn() {
        MethodHandles.Lookup defaultLookup = MethodHandles.lookup();

        // A class in the same package loses the privilege to lookup protected and private
        // members.
        MethodHandles.Lookup siblingLookup = defaultLookup.in(PackageSibling.class);
        assertEquals(ALL_LOOKUP_MODES & ~(PROTECTED | PRIVATE),  siblingLookup.lookupModes());

        // The new lookup isn't in the same package, so it loses all its privileges except
        // for public.
        MethodHandles.Lookup nonSibling = defaultLookup.in(Vector.class);
        assertEquals(PUBLIC, nonSibling.lookupModes());

        // Special case, sibling inner classes in the same parent class
        MethodHandles.Lookup inner2 = Inner1.lookup.in(Inner2.class);
        assertEquals(PUBLIC | PRIVATE | PACKAGE, inner2.lookupModes());

        try {
            MethodHandles.lookup().in(null);
            fail();
        } catch (NullPointerException expected) {
        }

        // Callers cannot change the lookup context to anything within the java.lang.invoke package.
        try {
            MethodHandles.lookup().in(MethodHandle.class);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void test_findStatic() throws Exception {
        MethodHandles.Lookup defaultLookup = MethodHandles.lookup();

        // Handle for String String#valueOf(char[]).
        MethodHandle handle = defaultLookup.findStatic(String.class, "valueOf",
                MethodType.methodType(String.class, char[].class));
        assertNotNull(handle);

        assertEquals(String.class, handle.type().returnType());
        assertEquals(1, handle.type().parameterCount());
        assertEquals(char[].class, handle.type().parameterArray()[0]);
        assertEquals(MethodHandle.INVOKE_STATIC, handle.getHandleKind());

        MethodHandles.Lookup inUtil = defaultLookup.in(Vector.class);

        // Package private in a public class in a different package from the lookup.
        try {
            inUtil.findStatic(MethodHandlesTest.class, "packagePrivateStaticMethod",
                    MethodType.methodType(void.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Protected in a public class in a different package from the lookup.
        try {
            inUtil.findStatic(MethodHandlesTest.class, "protectedStaticMethod",
                    MethodType.methodType(void.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Private in a public class in a different package from the lookup.
        try {
            inUtil.findStatic(MethodHandlesTest.class, "privateStaticMethod",
                    MethodType.methodType(void.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Public method in a package private class in a different package from the lookup.
        try {
            inUtil.findStatic(PackageSibling.class, "publicStaticMethod",
                    MethodType.methodType(void.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Public virtual method should not discoverable via findStatic.
        try {
            inUtil.findStatic(MethodHandlesTest.class, "publicMethod",
                    MethodType.methodType(void.class));
            fail();
        } catch (IllegalAccessException expected) {
        }
    }

    public void test_findConstructor() throws Exception {
        MethodHandles.Lookup defaultLookup = MethodHandles.lookup();

        // Handle for String.<init>(String). The requested type of the constructor declares
        // a void return type (to match the bytecode) but the handle that's created will declare
        // a return type that's equal to the type being constructed.
        MethodHandle handle = defaultLookup.findConstructor(String.class,
                MethodType.methodType(void.class, String.class));
        assertNotNull(handle);

        assertEquals(String.class, handle.type().returnType());
        assertEquals(1, handle.type().parameterCount());

        assertEquals(String.class, handle.type().parameterArray()[0]);
        assertEquals(MethodHandle.INVOKE_DIRECT, handle.getHandleKind());

        MethodHandles.Lookup inUtil = defaultLookup.in(Vector.class);

        // Package private in a public class in a different package from the lookup.
        try {
            inUtil.findConstructor(ConstructorTest.class,
                    MethodType.methodType(void.class, String.class, int.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Protected in a public class in a different package from the lookup.
        try {
            inUtil.findConstructor(ConstructorTest.class,
                    MethodType.methodType(void.class, String.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Private in a public class in a different package from the lookup.
        try {
            inUtil.findConstructor(ConstructorTest.class,
                    MethodType.methodType(void.class, String.class, char.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Protected constructor in a package private class in a different package from the lookup.
        try {
            inUtil.findConstructor(PackageSibling.class,
                    MethodType.methodType(void.class, String.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Public constructor in a package private class in a different package from the lookup.
        try {
            inUtil.findConstructor(PackageSibling.class,
                    MethodType.methodType(void.class, String.class, char.class));
            fail();
        } catch (IllegalAccessException expected) {
        }
    }

    public void test_findVirtual() throws Exception {
        MethodHandles.Lookup defaultLookup = MethodHandles.lookup();

        // String.replaceAll(String, String);
        MethodHandle handle = defaultLookup.findVirtual(String.class, "replaceAll",
                MethodType.methodType(String.class, String.class, String.class));
        assertNotNull(handle);

        assertEquals(String.class, handle.type().returnType());
        // Note that the input type was (String,String)String but the handle's type is
        // (String, String, String)String - since it's a non static call, we prepend the
        // receiver to the type.
        assertEquals(3, handle.type().parameterCount());
        MethodType expectedType = MethodType.methodType(String.class,
                new Class<?>[] { String.class, String.class, String.class});

        assertEquals(expectedType, handle.type());
        assertEquals(MethodHandle.INVOKE_VIRTUAL, handle.getHandleKind());

        MethodHandles.Lookup inUtil = defaultLookup.in(Vector.class);

        // Package private in a public class in a different package from the lookup.
        try {
            inUtil.findVirtual(MethodHandlesTest.class, "packagePrivateMethod",
                    MethodType.methodType(void.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Protected in a public class in a different package from the lookup.
        try {
            inUtil.findVirtual(MethodHandlesTest.class, "protectedMethod",
                    MethodType.methodType(void.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Protected in a public class in a different package from the lookup.
        try {
            inUtil.findVirtual(MethodHandlesTest.class, "privateMethod",
                    MethodType.methodType(void.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Public method in a package private class in a different package from the lookup.
        try {
            inUtil.findVirtual(PackageSibling.class, "publicMethod",
                    MethodType.methodType(void.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Public static method should not discoverable via findVirtual.
        try {
            inUtil.findVirtual(MethodHandlesTest.class, "publicStaticMethod",
                    MethodType.methodType(void.class));
            fail();
        } catch (IllegalAccessException expected) {
        }
    }

    public static class A {
        public boolean aCalled;

        public A() {}

        public void foo() {
            aCalled = true;
        }

        public static final Lookup lookup = MethodHandles.lookup();
    }

    public static class B extends A {
        public boolean bCalled;

        public void foo() {
            bCalled = true;
        }

        public static final Lookup lookup = MethodHandles.lookup();
    }

    public static class C extends B {
        public static final Lookup lookup = MethodHandles.lookup();
    }

    public static class D {
        public boolean privateDCalled;

        private final void privateRyan() {
            privateDCalled = true;
        }

        public static final Lookup lookup = MethodHandles.lookup();
    }

    public static class E extends D {
        public static final Lookup lookup = MethodHandles.lookup();
    }

    public interface F {
        public default void callInner(Consumer<Class<?>> c) {
            c.accept(F.class);
        }
    }

    public class G implements F {
        public void callInner(Consumer<Class<?>> c) {
            c.accept(G.class);
        }
    }

    public void testfindSpecial_invokeSuperBehaviour() throws Throwable {
        // This is equivalent to an invoke-super instruction where the referrer
        // is B.class.
        MethodHandle mh1 = B.lookup.findSpecial(A.class /* refC */, "foo",
                MethodType.methodType(void.class), B.class /* specialCaller */);

        // This should be as if an invoke-super was called from one of B's methods.
        B bInstance = new B();
        mh1.invokeExact(bInstance);
        assertTrue(bInstance.aCalled);

        bInstance = new B();
        mh1.invoke(bInstance);
        assertTrue(bInstance.aCalled);

        // This should not work. The receiver type in the handle will be suitably
        // restricted to B and subclasses.
        try {
            mh1.invoke(new A());
            fail();
        } catch (ClassCastException expected) {
        }

        try {
            mh1.invokeExact(new A());
            fail();
        } catch (WrongMethodTypeException expected) {
        }


        // This should *still* be as if an invoke-super was called from one of B's
        // methods, despite the fact that we're operating on a C.
        C cInstance = new C();
        mh1.invoke(cInstance);
        assertTrue(cInstance.aCalled);

        // Now that C is the special caller, the next invoke will call B.foo.
        MethodHandle mh2 = C.lookup.findSpecial(A.class /* refC */, "foo",
                MethodType.methodType(void.class), C.class /* specialCaller */);
        cInstance = new C();
        mh2.invokeExact(cInstance);
        assertTrue(cInstance.bCalled);

        // Shouldn't allow invoke-super semantics from an unrelated special caller.
        try {
            C.lookup.findSpecial(A.class, "foo",
                    MethodType.methodType(void.class), D.class /* specialCaller */);
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Check return type matches for find.
        try {
            B.lookup.findSpecial(A.class /* refC */, "foo",
                    MethodType.methodType(int.class), B.class /* specialCaller */);
            fail();
        } catch (NoSuchMethodException e) {}
        // Check constructors
        try {
            B.lookup.findSpecial(A.class /* refC */, "<init>",
                    MethodType.methodType(void.class), B.class /* specialCaller */);
            fail();
        } catch (NoSuchMethodException e) {}
    }

    public void testfindSpecial_invokeSuperInterfaceBehaviour() throws Throwable {
        // Check interface invoke super on unrelated lookup (with some private access).
        Class<?>[] res = new Class<?>[2];
        MethodHandle mh = MethodHandles.lookup().findSpecial(F.class /* refC */, "callInner",
                  MethodType.methodType(void.class, Consumer.class), G.class /* specialCaller */);
        G g = new G();
        Consumer<Class<?>> oc = (Class<?> c) -> { res[0] = c; };
        mh.invokeExact(g, oc);
        g.callInner((Class<?> c) -> { res[1] = c; });
        // Make sure the method-handle calls the default implementatoin
        assertTrue(res[0] == F.class);
        // Make sure the normal one works as we expect.
        assertTrue(res[1] == G.class);

        // Check findSpecial always fails if the lookup has only public access
        try {
          MethodHandles.publicLookup().findSpecial(F.class /* refC */, "callInner",
                  MethodType.methodType(void.class, Consumer.class), G.class /* specialCaller */);
          fail();
        } catch (IllegalAccessException e) {}

        // Check doing invokeSpecial on abstract interface methods gets appropriate errors. We
        // expect it to throw an IllegalAccessError.
        MethodHandle mh2 =
            MethodHandles.lookup().findSpecial(
                    Foo.class /* refC */,
                    "foo",
                    MethodType.methodType(String.class),
                    Bar.class /* specialCaller */);
        try {
          mh2.invoke(new BarImpl());
          fail();
        } catch (IllegalAccessException e) {}
    }

    public void testfindSpecial_invokeDirectBehaviour() throws Throwable {
        D dInstance = new D();

        MethodHandle mh3 = D.lookup.findSpecial(D.class, "privateRyan",
                MethodType.methodType(void.class), D.class /* specialCaller */);
        mh3.invoke(dInstance);

        // The private method shouldn't be accessible from any special caller except
        // itself...
        try {
            D.lookup.findSpecial(D.class, "privateRyan", MethodType.methodType(void.class),
                    C.class);
            fail();
        } catch (IllegalAccessException expected) {
        }

        // ... or from any lookup context except its own.
        try {
            E.lookup.findSpecial(D.class, "privateRyan", MethodType.methodType(void.class),
                    E.class);
            fail();
        } catch (IllegalAccessException expected) {
        }
    }

    public void testExceptionDetailMessages() throws Throwable {
        MethodHandle handle = MethodHandles.lookup().findVirtual(String.class, "concat",
                MethodType.methodType(String.class, String.class));

        try {
            handle.invokeExact("a", new Object());
            fail();
        } catch (WrongMethodTypeException ex) {
            assertEquals(
                    "Expected (java.lang.String, java.lang.String)java.lang.String " +
                    "but was (java.lang.String, java.lang.Object)void",
                    ex.getMessage());
        }
    }

    public interface Foo {
        public String foo();
    }

    public interface Bar extends Foo {
        public String bar();
    }

    public static abstract class BarAbstractSuper {
        public abstract String abstractSuperPublicMethod();
    }

    public static class BarSuper extends BarAbstractSuper {
        public String superPublicMethod() {
            return "superPublicMethod";
        }

        protected String superProtectedMethod() {
            return "superProtectedMethod";
        }

        String superPackageMethod() {
            return "superPackageMethod";
        }

        public String abstractSuperPublicMethod() {
            return "abstractSuperPublicMethod";
        }
    }

    public static class BarImpl extends BarSuper implements Bar {
        public BarImpl() {
        }

        @Override
        public String foo() {
            return "foo";
        }

        @Override
        public String bar() {
            return "bar";
        }

        public String add(int x, int y) {
            return Arrays.toString(new int[] { x, y });
        }

        private String privateMethod() { return "privateMethod"; }

        public static String staticMethod() { return staticString; }

        private static String staticString;

        {
            // Static constructor
            staticString = Long.toString(System.currentTimeMillis());
        }

        static final MethodHandles.Lookup lookup = MethodHandles.lookup();
    }

    public void testfindVirtual() throws Throwable {
        // Virtual lookups on static methods should not succeed.
        try {
            MethodHandles.lookup().findVirtual(
                    BarImpl.class,  "staticMethod", MethodType.methodType(String.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Virtual lookups on private methods should not succeed, unless the Lookup
        // context had sufficient privileges.
        try {
            MethodHandles.lookup().findVirtual(
                    BarImpl.class,  "privateMethod", MethodType.methodType(String.class));
            fail();
        } catch (IllegalAccessException expected) {
        }

        // Virtual lookup on a private method with a context that *does* have sufficient
        // privileges.
        MethodHandle mh = BarImpl.lookup.findVirtual(
                BarImpl.class,  "privateMethod", MethodType.methodType(String.class));
        String str = (String) mh.invoke(new BarImpl());
        assertEquals("privateMethod", str);

        // Find virtual must find interface methods defined by interfaces implemented
        // by the class.
        mh = MethodHandles.lookup().findVirtual(BarImpl.class, "foo",
                MethodType.methodType(String.class));
        str = (String) mh.invoke(new BarImpl());
        assertEquals("foo", str);

        // Find virtual should check rtype.
        try {
            MethodHandles.lookup().findVirtual(BarImpl.class, "foo",
                    MethodType.methodType(void.class));
            fail();
        } catch (NoSuchMethodException expected) {
        }

        // And ptypes
        mh = MethodHandles.lookup().findVirtual(
                BarImpl.class, "add", MethodType.methodType(String.class, int.class, int.class));
        try {
            MethodHandles.lookup().findVirtual(
                    BarImpl.class, "add",
                    MethodType.methodType(String.class, Integer.class, int.class));
        } catch (NoSuchMethodException expected) {
        }

        // .. and their super-interfaces.
        mh = MethodHandles.lookup().findVirtual(BarImpl.class, "bar",
                MethodType.methodType(String.class));
        str = (String) mh.invoke(new BarImpl());
        assertEquals("bar", str);


        mh = MethodHandles.lookup().findVirtual(BarImpl.class, "bar",
                                                MethodType.methodType(String.class));
        str = (String) mh.invoke(new BarImpl());
        assertEquals("bar", str);

        mh = MethodHandles.lookup().findVirtual(BarAbstractSuper.class, "abstractSuperPublicMethod",
                                                MethodType.methodType(String.class));
        str = (String) mh.invoke(new BarImpl());
        assertEquals("abstractSuperPublicMethod", str);

        // We should also be able to lookup public / protected / package methods in
        // the super class, given sufficient access privileges.
        mh = MethodHandles.lookup().findVirtual(BarImpl.class, "superPublicMethod",
                MethodType.methodType(String.class));
        str = (String) mh.invoke(new BarImpl());
        assertEquals("superPublicMethod", str);

        mh = MethodHandles.lookup().findVirtual(BarImpl.class, "superProtectedMethod",
                MethodType.methodType(String.class));
        str = (String) mh.invoke(new BarImpl());
        assertEquals("superProtectedMethod", str);

        mh = MethodHandles.lookup().findVirtual(BarImpl.class, "superPackageMethod",
                MethodType.methodType(String.class));
        str = (String) mh.invoke(new BarImpl());
        assertEquals("superPackageMethod", str);

        try {
            MethodHandles.lookup().findVirtual(BarImpl.class, "<init>",
                    MethodType.methodType(void.class));
            fail();
        } catch (NoSuchMethodException expected) {
        }
    }

    public void testfindStatic() throws Throwable {
        MethodHandles.lookup().findStatic(BarImpl.class, "staticMethod",
                MethodType.methodType(String.class));
        try {
            MethodHandles.lookup().findStatic(BarImpl.class, "staticMethod",
                    MethodType.methodType(void.class));
            fail();
        } catch (NoSuchMethodException expected) {
        }

        try {
            MethodHandles.lookup().findStatic(BarImpl.class, "staticMethod",
                    MethodType.methodType(String.class, int.class));
            fail();
        } catch (NoSuchMethodException expected) {
        }

        try {
            MethodHandles.lookup().findStatic(BarImpl.class, "<clinit>",
                    MethodType.methodType(void.class));
            fail();
        } catch (NoSuchMethodException expected) {
        }

        try {
            MethodHandles.lookup().findStatic(BarImpl.class, "<init>",
                    MethodType.methodType(void.class));
            fail();
        } catch (NoSuchMethodException expected) {
        }
    }

    static class UnreflectTesterBase {
        public String overridenMethod() {
            return "Base";
        }
    }

    static class UnreflectTester extends UnreflectTesterBase {
        public String publicField;
        private String privateField;

        public static String publicStaticField = "publicStaticValue";
        private static String privateStaticField = "privateStaticValue";

        private UnreflectTester(String val) {
            publicField = val;
            privateField = val;
        }

        // NOTE: The boolean constructor argument only exists to give this a
        // different signature.
        public UnreflectTester(String val, boolean unused) {
            this(val);
        }

        private static String privateStaticMethod() {
            return "privateStaticMethod";
        }

        private String privateMethod() {
            return "privateMethod";
        }

        public static String publicStaticMethod() {
            return "publicStaticMethod";
        }

        public String publicMethod() {
            return "publicMethod";
        }

        public String publicVarArgsMethod(String... args) {
            return "publicVarArgsMethod";
        }

        @Override
        public String overridenMethod() {
            return "Override";
        }

        public static final Lookup lookup = MethodHandles.lookup();
    }

    public void testUnreflects_publicMethods() throws Throwable {
        UnreflectTester instance = new UnreflectTester("unused");
        Method publicMethod = UnreflectTester.class.getMethod("publicMethod");

        MethodHandle mh = MethodHandles.lookup().unreflect(publicMethod);
        assertEquals("publicMethod", (String) mh.invoke(instance));
        assertEquals("publicMethod", (String) mh.invokeExact(instance));

        Method publicStaticMethod = UnreflectTester.class.getMethod("publicStaticMethod");
        mh = MethodHandles.lookup().unreflect(publicStaticMethod);
        assertEquals("publicStaticMethod", (String) mh.invoke());
        assertEquals("publicStaticMethod", (String) mh.invokeExact());
    }

    public void testUnreflects_privateMethods() throws Throwable {
        Method privateMethod = UnreflectTester.class.getDeclaredMethod("privateMethod");

        try {
            MethodHandles.lookup().unreflect(privateMethod);
            fail();
        } catch (IllegalAccessException expected) {
        }

        UnreflectTester instance = new UnreflectTester("unused");
        MethodHandle mh = UnreflectTester.lookup.unreflectSpecial(privateMethod,
                UnreflectTester.class);
        assertEquals("privateMethod", (String) mh.invoke(instance));
        assertEquals("privateMethod", (String) mh.invokeExact(instance));

        privateMethod.setAccessible(true);
        mh = MethodHandles.lookup().unreflect(privateMethod);
        assertEquals("privateMethod", (String) mh.invoke(instance));
        assertEquals("privateMethod", (String) mh.invokeExact(instance));

        Method privateStaticMethod = UnreflectTester.class.getDeclaredMethod("privateStaticMethod");
        try {
            MethodHandles.lookup().unreflect(privateStaticMethod);
            fail();
        } catch (IllegalAccessException expected) {
        }

        try {
            mh = UnreflectTester.lookup.unreflectSpecial(privateStaticMethod,
                    UnreflectTester.class);
            fail();
        } catch (IllegalAccessException expected) {
        }

        privateStaticMethod.setAccessible(true);
        mh = MethodHandles.lookup().unreflect(privateStaticMethod);
        assertEquals("privateStaticMethod", (String) mh.invoke());
        assertEquals("privateStaticMethod", (String) mh.invokeExact());
    }

    public void testUnreflectSpecial_superCalls() throws Throwable {
        Method overridenMethod = UnreflectTesterBase.class.getMethod("overridenMethod");
        UnreflectTester instance = new UnreflectTester("unused");
        MethodHandle mh = UnreflectTester.lookup.unreflectSpecial(overridenMethod,
                UnreflectTester.class);
        assertEquals("Base", (String) mh.invoke(instance));
    }

    public void testUnreflects_constructors() throws Throwable {
        Constructor privateConstructor = UnreflectTester.class.getDeclaredConstructor(String.class);

        try {
            MethodHandles.lookup().unreflectConstructor(privateConstructor);
            fail();
        } catch (IllegalAccessException expected) {
        }

        privateConstructor.setAccessible(true);
        MethodHandle mh = MethodHandles.lookup().unreflectConstructor(privateConstructor);
        UnreflectTester instance = (UnreflectTester) mh.invokeExact("abc");
        assertEquals("abc", instance.publicField);
        instance = (UnreflectTester) mh.invoke("def");
        assertEquals("def", instance.publicField);
        Constructor publicConstructor = UnreflectTester.class.getConstructor(String.class,
                boolean.class);
        mh = MethodHandles.lookup().unreflectConstructor(publicConstructor);
        instance = (UnreflectTester) mh.invokeExact("abc", false);
        assertEquals("abc", instance.publicField);
        instance = (UnreflectTester) mh.invoke("def", true);
        assertEquals("def", instance.publicField);
    }

    public void testUnreflects_publicFields() throws Throwable {
        Field publicField = UnreflectTester.class.getField("publicField");
        MethodHandle mh = MethodHandles.lookup().unreflectGetter(publicField);
        UnreflectTester instance = new UnreflectTester("instanceValue");
        assertEquals("instanceValue", (String) mh.invokeExact(instance));

        mh = MethodHandles.lookup().unreflectSetter(publicField);
        instance = new UnreflectTester("instanceValue");
        mh.invokeExact(instance, "updatedInstanceValue");
        assertEquals("updatedInstanceValue", instance.publicField);

        Field publicStaticField = UnreflectTester.class.getField("publicStaticField");
        mh = MethodHandles.lookup().unreflectGetter(publicStaticField);
        UnreflectTester.publicStaticField = "updatedStaticValue";
        assertEquals("updatedStaticValue", (String) mh.invokeExact());

        mh = MethodHandles.lookup().unreflectSetter(publicStaticField);
        UnreflectTester.publicStaticField = "updatedStaticValue";
        mh.invokeExact("updatedStaticValue2");
        assertEquals("updatedStaticValue2", UnreflectTester.publicStaticField);
    }

    public void testUnreflects_privateFields() throws Throwable {
        Field privateField = UnreflectTester.class.getDeclaredField("privateField");
        try {
            MethodHandles.lookup().unreflectGetter(privateField);
            fail();
        } catch (IllegalAccessException expected) {
        }
        try {
            MethodHandles.lookup().unreflectSetter(privateField);
            fail();
        } catch (IllegalAccessException expected) {
        }

        privateField.setAccessible(true);

        MethodHandle mh = MethodHandles.lookup().unreflectGetter(privateField);
        UnreflectTester instance = new UnreflectTester("instanceValue");
        assertEquals("instanceValue", (String) mh.invokeExact(instance));

        mh = MethodHandles.lookup().unreflectSetter(privateField);
        instance = new UnreflectTester("instanceValue");
        mh.invokeExact(instance, "updatedInstanceValue");
        assertEquals("updatedInstanceValue", instance.privateField);

        Field privateStaticField = UnreflectTester.class.getDeclaredField("privateStaticField");
        try {
            MethodHandles.lookup().unreflectGetter(privateStaticField);
            fail();
        } catch (IllegalAccessException expected) {
        }
        try {
            MethodHandles.lookup().unreflectSetter(privateStaticField);
            fail();
        } catch (IllegalAccessException expected) {
        }

        privateStaticField.setAccessible(true);
        mh = MethodHandles.lookup().unreflectGetter(privateStaticField);
        privateStaticField.set(null, "updatedStaticValue");
        assertEquals("updatedStaticValue", (String) mh.invokeExact());

        mh = MethodHandles.lookup().unreflectSetter(privateStaticField);
        privateStaticField.set(null, "updatedStaticValue");
        mh.invokeExact("updatedStaticValue2");
        assertEquals("updatedStaticValue2", (String) privateStaticField.get(null));
    }

    // This method only exists to fool Jack's handling of types. See b/32536744.
    public static CharSequence getSequence() {
        return "foo";
    }

    public void testAsType() throws Throwable {
        // The type of this handle is (String, String)String.
        MethodHandle mh = MethodHandles.lookup().findVirtual(String.class,
                "concat", MethodType.methodType(String.class, String.class));

        // Change it to (CharSequence, String)Object.
        MethodHandle asType = mh.asType(
                MethodType.methodType(Object.class, CharSequence.class, String.class));

        Object obj = asType.invokeExact((CharSequence) getSequence(), "bar");
        assertEquals("foobar", (String) obj);

        // Should fail due to a wrong return type.
        try {
            String str = (String) asType.invokeExact((CharSequence) getSequence(), "bar");
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // Should fail due to a wrong argument type (String instead of Charsequence).
        try {
            String str = (String) asType.invokeExact("baz", "bar");
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // Calls to asType should fail if the types are not convertible.
        //
        // Bad return type conversion.
        try {
            mh.asType(MethodType.methodType(int.class, String.class, String.class));
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // Bad argument conversion.
        try {
            mh.asType(MethodType.methodType(String.class, int.class, String.class));
            fail();
        } catch (WrongMethodTypeException expected) {
        }
    }

    public void testConstructors() throws Throwable {
        MethodHandle mh =
                MethodHandles.lookup().findConstructor(Float.class,
                        MethodType.methodType(void.class,
                                float.class));
        Float value = (Float) mh.invokeExact(0.33f);
        assertEquals(0.33f, value);

        value = (Float) mh.invoke(3.34f);
        assertEquals(3.34f, value);

        mh = MethodHandles.lookup().findConstructor(Double.class,
                MethodType.methodType(void.class, String.class));
        Double d = (Double) mh.invoke("8.45e3");
        assertEquals(8.45e3, d);

        mh = MethodHandles.lookup().findConstructor(Double.class,
                MethodType.methodType(void.class, double.class));
        d = (Double) mh.invoke(8.45e3);
        assertEquals(8.45e3, d);

        // Primitive type
        try {
            mh = MethodHandles.lookup().findConstructor(int.class, MethodType.methodType(void.class));
            fail("Unexpected lookup success for primitive constructor");
        } catch (NoSuchMethodException expected) {
        }

        // Interface
        try {
            mh = MethodHandles.lookup().findConstructor(Readable.class,
                    MethodType.methodType(void.class));
            fail("Unexpected lookup success for interface constructor");
        } catch (NoSuchMethodException expected) {
        }

        // Abstract
        mh = MethodHandles.lookup().findConstructor(Process.class, MethodType.methodType(void.class));
        try {
            mh.invoke();
            fail("Unexpected ability to instantiate an abstract class");
        } catch (InstantiationException expected) {
        }

        // Non-existent
        try {
            MethodHandles.lookup().findConstructor(
                    String.class, MethodType.methodType(String.class, Float.class));
            fail("Unexpected success for non-existent constructor");
        } catch (NoSuchMethodException expected) {
        }

        // Non-void constructor search. (I)I instead of (I)V.
        try {
            MethodHandles.lookup().findConstructor(
                    Integer.class, MethodType.methodType(Integer.class, Integer.class));
            fail("Unexpected success for non-void type for findConstructor");
        } catch (NoSuchMethodException expected) {
        }

        // Array class constructor.
        try {
            MethodHandles.lookup().findConstructor(
                    Object[].class, MethodType.methodType(void.class));
            fail("Unexpected success for array class type for findConstructor");
        } catch (NoSuchMethodException expected) {
        }
    }

    public void testStringConstructors() throws Throwable {
        final String testPattern = "The system as we know it is broken";

        // String()
        MethodHandle mh = MethodHandles.lookup().findConstructor(
                String.class, MethodType.methodType(void.class));
        String s = (String) mh.invokeExact();
        assertEquals("", s);

        // String(String)
        mh = MethodHandles.lookup().findConstructor(
                String.class, MethodType.methodType(void.class, String.class));
        s = (String) mh.invokeExact(testPattern);
        assertEquals(testPattern, s);


        // String(char[])
        mh = MethodHandles.lookup().findConstructor(
                String.class, MethodType.methodType(void.class, char[].class));
        s = (String) mh.invokeExact(testPattern.toCharArray());
        assertEquals(testPattern, s);

        // String(char[], int, int)
        mh = MethodHandles.lookup().findConstructor(
                String.class, MethodType.methodType(void.class, char[].class, int.class, int.class));
        s = (String) mh.invokeExact(new char [] { 'a', 'b', 'c', 'd', 'e'}, 2, 3);
        assertEquals("cde", s);

        // String(int[] codePoints, int offset, int count)
        StringBuffer sb = new StringBuffer(testPattern);
        int[] codePoints = new int[sb.codePointCount(0, sb.length())];
        for (int i = 0; i < sb.length(); ++i) {
            codePoints[i] = sb.codePointAt(i);
        }
        mh = MethodHandles.lookup().findConstructor(
                String.class, MethodType.methodType(void.class, int[].class, int.class, int.class));
        s = (String) mh.invokeExact(codePoints, 0, codePoints.length);
        assertEquals(testPattern, s);

        // String(byte ascii[], int hibyte, int offset, int count)
        byte [] ascii = testPattern.getBytes(StandardCharsets.US_ASCII);
        mh = MethodHandles.lookup().findConstructor(
                String.class, MethodType.methodType(void.class, byte[].class, int.class, int.class));
        s = (String) mh.invokeExact(ascii, 0, ascii.length);
        assertEquals(testPattern, s);

        // String(byte bytes[], int offset, int length, String charsetName)
        mh = MethodHandles.lookup().findConstructor(
                String.class,
                MethodType.methodType(void.class, byte[].class, int.class, int.class, String.class));
        s = (String) mh.invokeExact(ascii, 0, 5, StandardCharsets.US_ASCII.name());
        assertEquals(testPattern.substring(0, 5), s);

        // String(byte bytes[], int offset, int length, Charset charset)
        mh = MethodHandles.lookup().findConstructor(
                String.class,
                MethodType.methodType(void.class, byte[].class, int.class, int.class, Charset.class));
        s = (String) mh.invokeExact(ascii, 0, 5, StandardCharsets.US_ASCII);
        assertEquals(testPattern.substring(0, 5), s);

        // String(byte bytes[], String charsetName)
        mh = MethodHandles.lookup().findConstructor(
                String.class,
                MethodType.methodType(void.class, byte[].class, String.class));
        s = (String) mh.invokeExact(ascii, StandardCharsets.US_ASCII.name());
        assertEquals(testPattern, s);

        // String(byte bytes[], Charset charset)
        mh = MethodHandles.lookup().findConstructor(
                String.class, MethodType.methodType(void.class, byte[].class, Charset.class));
        s = (String) mh.invokeExact(ascii, StandardCharsets.US_ASCII);
        assertEquals(testPattern, s);

        // String(byte bytes[], int offset, int length)
        mh = MethodHandles.lookup().findConstructor(
                String.class, MethodType.methodType(void.class, byte[].class, int.class, int.class));
        s = (String) mh.invokeExact(ascii, 1, ascii.length - 2);
        s = testPattern.charAt(0) + s + testPattern.charAt(testPattern.length() - 1);
        assertEquals(testPattern, s);

        // String(byte bytes[])
        mh = MethodHandles.lookup().findConstructor(
                String.class, MethodType.methodType(void.class, byte[].class));
        s = (String) mh.invokeExact(ascii);
        assertEquals(testPattern, s);

        // String(StringBuffer buffer)
        mh = MethodHandles.lookup().findConstructor(
                String.class, MethodType.methodType(void.class, StringBuffer.class));
        s = (String) mh.invokeExact(sb);
        assertEquals(testPattern, s);
    }

    public void testReferenceReturnValueConversions() throws Throwable {
        MethodHandle mh = MethodHandles.lookup().findStatic(
                Float.class, "valueOf", MethodType.methodType(Float.class, String.class));

        // No conversion
        Float f = (Float) mh.invokeExact("1.375");
        assertEquals(1.375f, f);

        f = (Float) mh.invoke("1.875");
        assertEquals(1.875f, f);

        // Bad conversion
        try {
            int i = (int) mh.invokeExact("7.77");
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        try {
            int i = (int) mh.invoke("7.77");
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // Assignment to super-class.
        Number n = (Number) mh.invoke("1.11");
        try {
            Number o = (Number) mh.invokeExact("1.11");
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // Assignment to widened boxed primitive class.
        try {
            Double u = (Double) mh.invoke("1.11");
            fail();
        } catch (ClassCastException expected) {
        }

        try {
            Double v = (Double) mh.invokeExact("1.11");
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // Unboxed
        float p = (float) mh.invoke("1.11");
        assertEquals(1.11f, p);

        // Unboxed and widened
        double d = (double) mh.invoke("2.5");
        assertEquals(2.5, d);

        // Interface
        Comparable<Float> c = (Comparable<Float>) mh.invoke("2.125");
        assertEquals(0, c.compareTo(Float.valueOf(2.125f)));
    }

    public void testPrimitiveReturnValueConversions() throws Throwable {
        MethodHandle mh = MethodHandles.lookup().findStatic(
                Math.class, "min", MethodType.methodType(int.class, int.class, int.class));

        final int SMALL = -8972;
        final int LARGE = 7932529;

        // No conversion
        if ((int) mh.invokeExact(LARGE, SMALL) != SMALL) {
            fail();
        } else if ((int) mh.invoke(LARGE, SMALL) != SMALL) {
            fail();
        } else if ((int) mh.invokeExact(SMALL, LARGE) != SMALL) {
            fail();
        } else if ((int) mh.invoke(SMALL, LARGE) != SMALL) {
            fail();
        }

        // int -> long
        try {
            long l = (long) mh.invokeExact(LARGE, SMALL);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        assertEquals((long) SMALL, (long) mh.invoke(LARGE, SMALL));

        // int -> short
        try {
            short s = (short) mh.invokeExact(LARGE, SMALL);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // int -> Integer
        try {
            Integer i = (Integer) mh.invokeExact(LARGE, SMALL);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        assertEquals(Integer.valueOf(SMALL), (Integer) mh.invoke(LARGE, SMALL));

        // int -> Long
        try {
            Long l = (Long) mh.invokeExact(LARGE, SMALL);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        try {
            Long l = (Long) mh.invoke(LARGE, SMALL);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // int -> Short
        try {
            Short s = (Short) mh.invokeExact(LARGE, SMALL);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        try {
            Short s = (Short) mh.invoke(LARGE, SMALL);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // int -> Process
        try {
            Process p = (Process) mh.invokeExact(LARGE, SMALL);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        try {
            Process p = (Process) mh.invoke(LARGE, SMALL);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // void -> Object
        mh = MethodHandles.lookup().findStatic(System.class, "gc", MethodType.methodType(void.class));
        Object o = (Object) mh.invoke();
        assertNull(o);

        // void -> long
        long l = (long) mh.invoke();
        assertEquals(0, l);

        // boolean -> Boolean
        mh = MethodHandles.lookup().findStatic(Boolean.class, "parseBoolean",
                MethodType.methodType(boolean.class, String.class));
        Boolean z = (Boolean) mh.invoke("True");
        assertTrue(z);

        // boolean -> int
        try {
            int fake = (int) mh.invoke("True");
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // boolean -> Integer
        try {
            Integer fake = (Integer) mh.invoke("True");
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // Boolean -> boolean
        mh = MethodHandles.lookup().findStatic(Boolean.class, "valueOf",
                MethodType.methodType(Boolean.class, boolean.class));
        boolean w = (boolean) mh.invoke(false);
        assertFalse(w);

        // Boolean -> int
        try {
            int fake = (int) mh.invoke(false);
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        // Boolean -> Integer
        try {
            Integer fake = (Integer) mh.invoke("True");
            fail();
        } catch (WrongMethodTypeException expected) {
        }
    }

    public static class BaseVariableArityTester {
        public String update(Float f0, Float... floats) {
            return "base " + f0 + ", " + Arrays.toString(floats);
        }
    }

    public static class VariableArityTester extends BaseVariableArityTester {
        private String lastResult;

        // Constructors
        public VariableArityTester() {}
        public VariableArityTester(boolean... booleans) { update(booleans); }
        public VariableArityTester(byte... bytes) { update(bytes); }
        public VariableArityTester(char... chars) { update(chars); }
        public VariableArityTester(short... shorts) { update(shorts); }
        public VariableArityTester(int... ints) { update(ints); }
        public VariableArityTester(long... longs) { update(longs); }
        public VariableArityTester(float... floats) { update(floats); }
        public VariableArityTester(double... doubles) { update(doubles); }
        public VariableArityTester(Float f0, Float... floats) { update(f0, floats); }
        public VariableArityTester(String s0, String... strings) { update(s0, strings); }
        public VariableArityTester(char c, Number... numbers) { update(c, numbers); }
        @SafeVarargs
        public VariableArityTester(ArrayList<Integer> l0, ArrayList<Integer>... lists) {
            update(l0, lists);
        }
        public VariableArityTester(List<?> l0, List<?>... lists) { update(l0, lists); }

        // Methods
        public String update(boolean... booleans) { return lastResult = tally(booleans); }
        public String update(byte... bytes) { return lastResult = tally(bytes); }
        public String update(char... chars) { return lastResult = tally(chars); }
        public String update(short... shorts) { return lastResult = tally(shorts); }
        public String update(int... ints) {
            lastResult = tally(ints);
            return lastResult;
        }
        public String update(long... longs) { return lastResult = tally(longs); }
        public String update(float... floats) { return lastResult = tally(floats); }
        public String update(double... doubles) { return lastResult = tally(doubles); }
        @Override
        public String update(Float f0, Float... floats) { return lastResult = tally(f0, floats); }
        public String update(String s0, String... strings) { return lastResult = tally(s0, strings); }
        public String update(char c, Number... numbers) { return lastResult = tally(c, numbers); }
        @SafeVarargs
        public final String update(ArrayList<Integer> l0, ArrayList<Integer>... lists) {
            lastResult = tally(l0, lists);
            return lastResult;
        }
        public String update(List l0, List... lists) { return lastResult = tally(l0, lists); }

        public String arrayMethod(Object[] o) {
            return Arrays.deepToString(o);
        }

        public String lastResult() { return lastResult; }

        // Static Methods
        public static String tally(boolean... booleans) { return Arrays.toString(booleans); }
        public static String tally(byte... bytes) { return Arrays.toString(bytes); }
        public static String tally(char... chars) { return Arrays.toString(chars); }
        public static String tally(short... shorts) { return Arrays.toString(shorts); }
        public static String tally(int... ints) { return Arrays.toString(ints); }
        public static String tally(long... longs) { return Arrays.toString(longs); }
        public static String tally(float... floats) { return Arrays.toString(floats); }
        public static String tally(double... doubles) { return Arrays.toString(doubles); }
        public static String tally(Float f0, Float... floats) {
            return f0 + ", " + Arrays.toString(floats);
        }
        public static String tally(String s0, String... strings) {
            return s0 + ", " + Arrays.toString(strings);
        }
        public static String tally(char c, Number... numbers) {
            return c + ", " + Arrays.toString(numbers);
        }
        @SafeVarargs
        public static String tally(ArrayList<Integer> l0, ArrayList<Integer>... lists) {
            return Arrays.toString(l0.toArray()) + ", " + Arrays.deepToString(lists);
        }
        public static String tally(List l0, List... lists) {
            return Arrays.deepToString(l0.toArray()) + ", " + Arrays.deepToString(lists);
        }
        public static void foo(int... ints) {}
        public static long sumToPrimitive(int... ints) {
            long result = 0;
            for (int i : ints) result += i;
            return result;
        }
        public static Long sumToReference(int... ints) {
            return new Long(sumToPrimitive(ints));
        }
        public static MethodHandles.Lookup lookup() {
            return MethodHandles.lookup();
        }
    }

    // This method only exists to fool Jack's handling of types. See b/32536744.
    public static Object getAsObject(String[] strings) {
        return (Object) strings;
    }

    public void testVariableArity_boolean() throws Throwable {
        MethodHandle mh;
        VariableArityTester vat = new VariableArityTester();

        assertEquals("[1]", vat.update(1));
        assertEquals("[1, 1]", vat.update(1, 1));
        assertEquals("[1, 1, 1]", vat.update(1, 1, 1));

        // Methods - boolean
        mh = MethodHandles.lookup().findVirtual(VariableArityTester.class, "update",
                MethodType.methodType(String.class, boolean[].class));
        assertTrue(mh.isVarargsCollector());
        assertFalse(mh.asFixedArity().isVarargsCollector());
        assertEquals("[]", mh.invoke(vat));
        assertEquals("[true, false, true]", mh.invoke(vat, true, false, true));
        assertEquals("[true, false, true]", mh.invoke(vat, new boolean[]{true, false, true}));
        assertEquals("[false, true]", mh.invoke(vat, Boolean.valueOf(false), Boolean.valueOf(true)));
        try {
            mh.invoke(vat, true, true, 0);
            fail();
        } catch (WrongMethodTypeException e) {
        }
        try {
            assertEquals("[false, true]", mh.invoke(vat, Boolean.valueOf(false), (Boolean) null));
            fail();
        } catch (NullPointerException e) {
        }
    }

    public void testVariableArity_byte() throws Throwable {
        VariableArityTester vat = new VariableArityTester();

        // Methods - byte
        MethodHandle mh = MethodHandles.lookup().findVirtual(VariableArityTester.class, "update",
                MethodType.methodType(String.class, byte[].class));
        assertTrue(mh.isVarargsCollector());
        assertEquals("[]", mh.invoke(vat));
        assertEquals("[32, 64, 97]", mh.invoke(vat, (byte) 32, Byte.valueOf((byte) 64), (byte) 97));
        assertEquals("[32, 64, 97]", mh.invoke(vat, new byte[]{(byte) 32, (byte) 64, (byte) 97}));
        try {
            mh.invoke(vat, (byte) 1, Integer.valueOf(3), (byte) 0);
            fail();
        } catch (WrongMethodTypeException e) {
        }
    }

    public void testVariableArity_char() throws Throwable {
        MethodHandle mh;
        VariableArityTester vat = new VariableArityTester();

        // Methods - char
        mh = MethodHandles.lookup().findVirtual(VariableArityTester.class, "update",
                MethodType.methodType(String.class, char[].class));
        assertTrue(mh.isVarargsCollector());
        assertEquals("[]", mh.invoke(vat));
        assertEquals("[A, B, C]", mh.invoke(vat, 'A', Character.valueOf('B'), 'C'));
        assertEquals("[W, X, Y, Z]", mh.invoke(vat, new char[]{'W', 'X', 'Y', 'Z'}));
    }

    public void testVariableArity_short() throws Throwable {
        MethodHandle mh;
        VariableArityTester vat = new VariableArityTester();

        // Methods - short
        mh = MethodHandles.lookup().findVirtual(VariableArityTester.class, "update",
                MethodType.methodType(String.class, short[].class));
        assertTrue(mh.isVarargsCollector());
        assertEquals("[]", mh.invoke(vat));
        assertEquals("[32767, -32768, 0]",
                mh.invoke(vat, Short.MAX_VALUE, Short.MIN_VALUE, Short.valueOf((short) 0)));
        assertEquals("[1, -1]", mh.invoke(vat, new short[]{(short) 1, (short) -1}));
    }

    public void testVariableArity_int() throws Throwable {
        MethodHandle mh;
        VariableArityTester vat = new VariableArityTester();

        // Methods - int
        mh = MethodHandles.lookup().findVirtual(VariableArityTester.class, "update",
                MethodType.methodType(String.class, int[].class));
        assertTrue(mh.isVarargsCollector());
        assertEquals("[]", mh.invoke(vat));
        assertEquals("[0, 2147483647, -2147483648, 0]",
                mh.invoke(vat, Integer.valueOf(0), Integer.MAX_VALUE, Integer.MIN_VALUE, 0));
        assertEquals("[0, -1, 1, 0]", mh.invoke(vat, new int[]{0, -1, 1, 0}));

        assertEquals("[5, 4, 3, 2, 1]", (String) mh.invokeExact(vat, new int[]{5, 4, 3, 2, 1}));
        try {
            assertEquals("[5, 4, 3, 2, 1]", (String) mh.invokeExact(vat, 5, 4, 3, 2, 1));
            fail();
        } catch (WrongMethodTypeException expected) {
        }
        assertEquals("[5, 4, 3, 2, 1]", (String) mh.invoke(vat, 5, 4, 3, 2, 1));
    }

    public void testVariableArity_long() throws Throwable {
        VariableArityTester vat = new VariableArityTester();

        // Methods - long
        MethodHandle mh = MethodHandles.lookup().findVirtual(VariableArityTester.class, "update",
                MethodType.methodType(String.class, long[].class));

        assertTrue(mh.isVarargsCollector());
        assertEquals("[]", mh.invoke(vat));
        assertEquals("[0, 9223372036854775807, -9223372036854775808]",
                mh.invoke(vat, Long.valueOf(0), Long.MAX_VALUE, Long.MIN_VALUE));
        assertEquals("[0, -1, 1, 0]", mh.invoke(vat, new long[]{0, -1, 1, 0}));
    }

    public void testVariableArity_float() throws Throwable {
        MethodHandle mh;
        VariableArityTester vat = new VariableArityTester();

        // Methods - float
        mh = MethodHandles.lookup().findVirtual(VariableArityTester.class, "update",
                MethodType.methodType(String.class, float[].class));
        assertTrue(mh.isVarargsCollector());
        assertEquals("[]", mh.invoke(vat));
        assertEquals("[0.0, 1.25, -1.25]",
                mh.invoke(vat, 0.0f, Float.valueOf(1.25f), Float.valueOf(-1.25f)));
        assertEquals("[0.0, -1.0, 1.0, 0.0]",
                mh.invoke(vat, new float[]{0.0f, -1.0f, 1.0f, 0.0f}));
    }

    public void testVariableArity_double() throws Throwable {
        VariableArityTester vat = new VariableArityTester();

        // Methods - double
        MethodHandle mh = MethodHandles.lookup().findVirtual(VariableArityTester.class, "update",
                MethodType.methodType(String.class, double[].class));
        assertTrue(mh.isVarargsCollector());
        assertEquals("[]", mh.invoke(vat));
        assertEquals("[0.0, 1.25, -1.25]",
                mh.invoke(vat, 0.0, Double.valueOf(1.25), Double.valueOf(-1.25)));
        assertEquals("[0.0, -1.0, 1.0, 0.0]",
                mh.invoke(vat, new double[]{0.0, -1.0, 1.0, 0.0}));
        mh.invoke(vat, 0.3f, 1.33, 1.33);
    }

    public void testVariableArity_String() throws Throwable {
        VariableArityTester vat = new VariableArityTester();

        // Methods - String
        MethodHandle mh = MethodHandles.lookup().
                findVirtual(VariableArityTester.class, "update",
                        MethodType.methodType(String.class, String.class, String[].class));
        assertTrue(mh.isVarargsCollector());
        assertEquals("Echidna, []", mh.invoke(vat, "Echidna"));
        assertEquals("Bongo, [Jerboa, Okapi]",
                mh.invoke(vat, "Bongo", "Jerboa", "Okapi"));
    }

    public void testVariableArity_Float() throws Throwable {
        VariableArityTester vat = new VariableArityTester();

        // Methods - Float
        MethodHandle mh = MethodHandles.lookup().
                findVirtual(VariableArityTester.class, "update",
                        MethodType.methodType(String.class, Float.class, Float[].class));

        assertTrue(mh.isVarargsCollector());
        assertEquals("9.99, [0.0, 0.1, 1.1]",
                (String) mh.invoke(vat,
                        Float.valueOf(9.99f),
                        new Float[]{ Float.valueOf(0.0f), Float.valueOf(0.1f), Float.valueOf(1.1f)}));
        assertEquals("9.99, [0.0, 0.1, 1.1]",
                (String) mh.invoke(vat, Float.valueOf(9.99f), Float.valueOf(0.0f),
                        Float.valueOf(0.1f), Float.valueOf(1.1f)));
        assertEquals("9.99, [0.0, 0.1, 1.1]",
                (String) mh.invoke(vat, Float.valueOf(9.99f), 0.0f, 0.1f, 1.1f));
        try {
            assertEquals("9.99, [77.0, 33.0, 64.0]",
                    (String) mh.invoke(vat, Float.valueOf(9.99f), 77, 33, 64));
            fail();
        } catch (WrongMethodTypeException expected) {
        }

        assertEquals("9.99, [0.0, 0.1, 1.1]",
                (String) mh.invokeExact(vat, Float.valueOf(9.99f),
                        new Float[]{Float.valueOf(0.0f),
                                Float.valueOf(0.1f),
                                Float.valueOf(1.1f)}));
        assertEquals("9.99, [0.0, null, 1.1]",
                (String) mh.invokeExact(vat, Float.valueOf(9.99f),
                        new Float[]{Float.valueOf(0.0f),
                                null,
                                Float.valueOf(1.1f)}));
        try {
            assertEquals("9.99, [0.0, 0.1, 1.1]",
                    (String) mh.invokeExact(vat, Float.valueOf(9.99f), 0.0f, 0.1f, 1.1f));
            fail();
        } catch (WrongMethodTypeException expected) {
        }
    }

    public void testVariableArity_Number() throws Throwable {
        MethodHandle mh;
        VariableArityTester vat = new VariableArityTester();

        // Methods - Number
        mh = MethodHandles.lookup().
                findVirtual(VariableArityTester.class, "update",
                        MethodType.methodType(String.class, char.class, Number[].class));
        assertTrue(mh.isVarargsCollector());
        assertFalse(mh.asFixedArity().isVarargsCollector());
        assertEquals("x, []", (String) mh.invoke(vat, 'x'));
        assertEquals("x, [3.141]", (String) mh.invoke(vat, 'x', 3.141));
        assertEquals("x, [null, 3.131, 37]",
                (String) mh.invoke(vat, 'x', null, 3.131, new Integer(37)));
        try {
            assertEquals("x, [null, 3.131, bad, 37]",
                    (String) mh.invoke(vat, 'x', null, 3.131, "bad", new Integer(37)));
            assertTrue(false);
            fail();
        } catch (ClassCastException e) {
        }
        try {
            assertEquals("x, [null, 3.131, bad, 37]",
                    (String) mh.invoke(
                            vat, 'x', (Process) null, 3.131, "bad", new Integer(37)));
            assertTrue(false);
            fail();
        } catch (ClassCastException e) {
        }
    }

    public void testVariableArity_arrayMethod() throws Throwable {
        MethodHandle mh;
        VariableArityTester vat = new VariableArityTester();

        // Methods - an array method that is not variable arity.
        mh = MethodHandles.lookup().findVirtual(
                VariableArityTester.class, "arrayMethod",
                MethodType.methodType(String.class, Object[].class));
        assertFalse(mh.isVarargsCollector());
        mh.invoke(vat, new Object[]{"123"});
        try {
            assertEquals("-", mh.invoke(vat, new Float(3), new Float(4)));
            fail();
        } catch (WrongMethodTypeException e) {
        }
        mh = mh.asVarargsCollector(Object[].class);
        assertTrue(mh.isVarargsCollector());
        assertEquals("[3.0, 4.0]", (String) mh.invoke(vat, new Float(3), new Float(4)));
    }

    public void testVariableArity_booleanConstructors() throws Throwable {
        // Constructors - default
        MethodHandle mh = MethodHandles.lookup().findConstructor(
                VariableArityTester.class, MethodType.methodType(void.class));
        assertFalse(mh.isVarargsCollector());

        // Constructors - boolean
        mh = MethodHandles.lookup().findConstructor(
                VariableArityTester.class, MethodType.methodType(void.class, boolean[].class));
        assertTrue(mh.isVarargsCollector());
        assertEquals("[true, true, false]",
                ((VariableArityTester) mh.invoke(new boolean[]{true, true, false})).lastResult());
        assertEquals("[true, true, false]",
                ((VariableArityTester) mh.invoke(true, true, false)).lastResult());
        try {
            assertEquals("[true, true, false]",
                    ((VariableArityTester) mh.invokeExact(true, true, false)).lastResult());
            fail();
        } catch (WrongMethodTypeException expected) {
        }
    }

    public void testVariableArity_byteConstructors() throws Throwable {
        // Constructors - byte
        MethodHandle mh = MethodHandles.lookup().findConstructor(
                VariableArityTester.class, MethodType.methodType(void.class, byte[].class));
        assertTrue(mh.isVarargsCollector());
        assertEquals("[55, 66, 60]",
                ((VariableArityTester)
                        mh.invoke(new byte[]{(byte) 55, (byte) 66, (byte) 60})).lastResult());
        assertEquals("[55, 66, 60]",
                ((VariableArityTester) mh.invoke(
                        (byte) 55, (byte) 66, (byte) 60)).lastResult());
        try {
            assertEquals("[55, 66, 60]",
                    ((VariableArityTester) mh.invokeExact(
                            (byte) 55, (byte) 66, (byte) 60)).lastResult());
            fail();
        } catch (WrongMethodTypeException expected) {
        }
        try {
            assertEquals("[3, 3]",
                    ((VariableArityTester) mh.invoke(
                            new Number[]{Byte.valueOf((byte) 3), (byte) 3})).lastResult());
            fail();
        } catch (WrongMethodTypeException expected) {
        }
    }

    public void testVariableArity_stringConstructors() throws Throwable {
        // Constructors - String (have a different path than other reference types).
        MethodHandle mh = MethodHandles.lookup().findConstructor(
                VariableArityTester.class,
                MethodType.methodType(void.class, String.class, String[].class));
        assertTrue(mh.isVarargsCollector());
        assertEquals("x, []", ((VariableArityTester) mh.invoke("x")).lastResult());
        assertEquals("x, [y]", ((VariableArityTester) mh.invoke("x", "y")).lastResult());
        assertEquals("x, [y, z]",
                ((VariableArityTester) mh.invoke("x", new String[]{"y", "z"})).lastResult());
        try {
            assertEquals("x, [y]", ((VariableArityTester) mh.invokeExact("x", "y")).lastResult());
            fail();
        } catch (WrongMethodTypeException expected) {
        }
        assertEquals("x, [null, z]",
                ((VariableArityTester) mh.invoke("x", new String[]{null, "z"})).lastResult());
    }

    public void testVariableArity_numberConstructors() throws Throwable {
        // Constructors - Number
        MethodHandle mh = MethodHandles.lookup().findConstructor(
                VariableArityTester.class, MethodType.methodType(void.class, char.class, Number[].class));
        assertTrue(mh.isVarargsCollector());
        assertFalse(mh.asFixedArity().isVarargsCollector());
        assertEquals("x, []", ((VariableArityTester) mh.invoke('x')).lastResult());
        assertEquals("x, [3.141]", ((VariableArityTester) mh.invoke('x', 3.141)).lastResult());
        assertEquals("x, [null, 3.131, 37]",
                ((VariableArityTester) mh.invoke('x', null, 3.131, new Integer(37))).lastResult());
        try {
            assertEquals("x, [null, 3.131, bad, 37]",
                    ((VariableArityTester) mh.invoke(
                            'x', null, 3.131, "bad", new Integer(37))).lastResult());
            fail();
        } catch (ClassCastException expected) {
        }
        try {
            assertEquals("x, [null, 3.131, bad, 37]",
                    ((VariableArityTester) mh.invoke(
                            'x', (Process) null, 3.131, "bad", new Integer(37))).lastResult());
            fail();
        } catch (ClassCastException expected) {
        }
    }

    public void testVariableArity_floatConstructors() throws Throwable {
        // Static Methods - Float
        MethodHandle mh = MethodHandles.lookup().
                findStatic(VariableArityTester.class, "tally",
                        MethodType.methodType(String.class, Float.class, Float[].class));
        assertTrue(mh.isVarargsCollector());
        assertEquals("9.99, [0.0, 0.1, 1.1]",
                (String) mh.invoke(Float.valueOf(9.99f),
                        new Float[]{Float.valueOf(0.0f),
                                Float.valueOf(0.1f),
                                Float.valueOf(1.1f)}));
        assertEquals("9.99, [0.0, 0.1, 1.1]",
                (String) mh.invoke(Float.valueOf(9.99f), Float.valueOf(0.0f),
                        Float.valueOf(0.1f), Float.valueOf(1.1f)));
        assertEquals("9.99, [0.0, 0.1, 1.1]",
                (String) mh.invoke(Float.valueOf(9.99f), 0.0f, 0.1f, 1.1f));
        try {
            assertEquals("9.99, [77.0, 33.0, 64.0]",
                    (String) mh.invoke(Float.valueOf(9.99f), 77, 33, 64));
            fail();
        } catch (WrongMethodTypeException expected) {
        }
        assertEquals("9.99, [0.0, 0.1, 1.1]",
                (String) mh.invokeExact(Float.valueOf(9.99f),
                        new Float[]{Float.valueOf(0.0f),
                                Float.valueOf(0.1f),
                                Float.valueOf(1.1f)}));
        assertEquals("9.99, [0.0, null, 1.1]",
                (String) mh.invokeExact(Float.valueOf(9.99f),
                        new Float[]{Float.valueOf(0.0f),
                                null,
                                Float.valueOf(1.1f)}));
        try {
            assertEquals("9.99, [0.0, 0.1, 1.1]",
                    (String) mh.invokeExact(Float.valueOf(9.99f), 0.0f, 0.1f, 1.1f));
            fail();
        } catch (WrongMethodTypeException expected) {
        }
    }

    public void testVariableArity_specialMethods() throws Throwable {
        VariableArityTester vat = new VariableArityTester();

        // Special methods - Float
        MethodHandle mh = VariableArityTester.lookup().
                findSpecial(BaseVariableArityTester.class, "update",
                        MethodType.methodType(String.class, Float.class, Float[].class),
                        VariableArityTester.class);
        assertTrue(mh.isVarargsCollector());
        assertEquals("base 9.99, [0.0, 0.1, 1.1]",
                (String) mh.invoke(vat,
                        Float.valueOf(9.99f),
                        new Float[]{Float.valueOf(0.0f),
                                Float.valueOf(0.1f),
                                Float.valueOf(1.1f)}));
        assertEquals("base 9.99, [0.0, 0.1, 1.1]",
                (String) mh.invoke(vat, Float.valueOf(9.99f), Float.valueOf(0.0f),
                        Float.valueOf(0.1f), Float.valueOf(1.1f)));
    }

    public void testVariableArity_returnValueConversions() throws Throwable {
        VariableArityTester vat = new VariableArityTester();

        // Return value conversions.
        MethodHandle mh = MethodHandles.lookup().findVirtual(VariableArityTester.class, "update",
                MethodType.methodType(String.class, int[].class));
        assertEquals("[1, 2, 3]", (String) mh.invoke(vat, 1, 2, 3));
        assertEquals("[1, 2, 3]", (Object) mh.invoke(vat, 1, 2, 3));
        try {
            assertEquals("[1, 2, 3, 4]", (long) mh.invoke(vat, 1, 2, 3));
            fail();
        } catch (WrongMethodTypeException expected) {
        }
        assertEquals("[1, 2, 3]", vat.lastResult());
        mh = MethodHandles.lookup().findStatic(VariableArityTester.class, "sumToPrimitive",
                MethodType.methodType(long.class, int[].class));
        assertEquals(10l, (long) mh.invoke(1, 2, 3, 4));
        assertEquals(Long.valueOf(10l), (Long) mh.invoke(1, 2, 3, 4));
        mh = MethodHandles.lookup().findStatic(VariableArityTester.class, "sumToReference",
                MethodType.methodType(Long.class, int[].class));
        Object o = mh.invoke(1, 2, 3, 4);
        long l = (long) mh.invoke(1, 2, 3, 4);
        assertEquals(10l, (long) mh.invoke(1, 2, 3, 4));
        assertEquals(Long.valueOf(10l), (Long) mh.invoke(1, 2, 3, 4));
        try {
            // WrongMethodTypeException should be raised before invoke here.
            assertEquals(Long.valueOf(10l), (Byte) mh.invoke(1, 2, 3, 4));
            fail();
        } catch (ClassCastException expected) {
        }
        try {
            // WrongMethodTypeException should be raised before invoke here.
            byte b = (byte) mh.invoke(1, 2, 3, 4);
            fail();
        } catch (WrongMethodTypeException expected) {
        }
    }

    public void testVariableArity_returnVoid() throws Throwable {
        // Return void produces 0 / null.
        MethodHandle mh = MethodHandles.lookup().findStatic(VariableArityTester.class, "foo",
                MethodType.methodType(void.class, int[].class));
        assertEquals(null, (Object) mh.invoke(3, 2, 1));
        assertEquals(0l, (long) mh.invoke(1, 2, 3));
    }

    public void testVariableArity_combinators() throws Throwable {
        VariableArityTester vat = new VariableArityTester();

        // Combinators
        MethodHandle mh = MethodHandles.lookup().findVirtual(VariableArityTester.class, "update",
                MethodType.methodType(String.class, boolean[].class));
        assertTrue(mh.isVarargsCollector());
        mh = mh.bindTo(vat);
        assertFalse(mh.isVarargsCollector());
        mh = mh.asVarargsCollector(boolean[].class);
        assertTrue(mh.isVarargsCollector());
        assertEquals("[]", mh.invoke());
        assertEquals("[true, false, true]", mh.invoke(true, false, true));
        assertEquals("[true, false, true]", mh.invoke(new boolean[] { true, false, true}));
        assertEquals("[false, true]", mh.invoke(Boolean.valueOf(false), Boolean.valueOf(true)));
        try {
            mh.invoke(true, true, 0);
            fail();
        } catch (WrongMethodTypeException e) {}
    }

    // The same tests as the above, except that we use use MethodHandles.bind instead of
    // MethodHandle.bindTo.
    public void testVariableArity_MethodHandles_bind() throws Throwable {
        VariableArityTester vat = new VariableArityTester();
        MethodHandle mh = MethodHandles.lookup().bind(vat, "update",
                MethodType.methodType(String.class, boolean[].class));
        assertTrue(mh.isVarargsCollector());

        assertEquals("[]", mh.invoke());
        assertEquals("[true, false, true]", mh.invoke(true, false, true));
        assertEquals("[true, false, true]", mh.invoke(new boolean[] { true, false, true}));
        assertEquals("[false, true]", mh.invoke(Boolean.valueOf(false), Boolean.valueOf(true)));

        try {
            mh.invoke(true, true, 0);
            fail();
        } catch (WrongMethodTypeException e) {}
    }

    public void testRevealDirect() throws Throwable {
        // Test with a virtual method :
        MethodType type = MethodType.methodType(String.class);
        MethodHandle handle = MethodHandles.lookup().findVirtual(
                UnreflectTester.class, "publicMethod", type);

        // Comparisons with an equivalent member obtained via reflection :
        MethodHandleInfo info = MethodHandles.lookup().revealDirect(handle);
        Method meth = UnreflectTester.class.getMethod("publicMethod");

        assertEquals(MethodHandleInfo.REF_invokeVirtual, info.getReferenceKind());
        assertEquals("publicMethod", info.getName());
        assertTrue(UnreflectTester.class == info.getDeclaringClass());
        assertFalse(info.isVarArgs());
        assertEquals(meth, info.reflectAs(Method.class, MethodHandles.lookup()));
        assertEquals(type, info.getMethodType());

        // Resolution via a public lookup should fail because the method in question
        // isn't public.
        try {
            info.reflectAs(Method.class, MethodHandles.publicLookup());
            fail();
        } catch (IllegalArgumentException expected) {
        }

        // Test with a static method :
        handle = MethodHandles.lookup().findStatic(UnreflectTester.class,
                "publicStaticMethod",
                MethodType.methodType(String.class));

        info = MethodHandles.lookup().revealDirect(handle);
        meth = UnreflectTester.class.getMethod("publicStaticMethod");
        assertEquals(MethodHandleInfo.REF_invokeStatic, info.getReferenceKind());
        assertEquals("publicStaticMethod", info.getName());
        assertTrue(UnreflectTester.class == info.getDeclaringClass());
        assertFalse(info.isVarArgs());
        assertEquals(meth, info.reflectAs(Method.class, MethodHandles.lookup()));
        assertEquals(type, info.getMethodType());

        // Test with a var-args method :
        type = MethodType.methodType(String.class, String[].class);
        handle = MethodHandles.lookup().findVirtual(UnreflectTester.class,
                "publicVarArgsMethod", type);

        info = MethodHandles.lookup().revealDirect(handle);
        meth = UnreflectTester.class.getMethod("publicVarArgsMethod", String[].class);
        assertEquals(MethodHandleInfo.REF_invokeVirtual, info.getReferenceKind());
        assertEquals("publicVarArgsMethod", info.getName());
        assertTrue(UnreflectTester.class == info.getDeclaringClass());
        assertTrue(info.isVarArgs());
        assertEquals(meth, info.reflectAs(Method.class, MethodHandles.lookup()));
        assertEquals(type, info.getMethodType());

        // Test with a constructor :
        Constructor cons = UnreflectTester.class.getConstructor(String.class, boolean.class);
        type = MethodType.methodType(void.class, String.class, boolean.class);
        handle = MethodHandles.lookup().findConstructor(UnreflectTester.class, type);

        info = MethodHandles.lookup().revealDirect(handle);
        assertEquals(MethodHandleInfo.REF_newInvokeSpecial, info.getReferenceKind());
        assertEquals("<init>", info.getName());
        assertTrue(UnreflectTester.class == info.getDeclaringClass());
        assertFalse(info.isVarArgs());
        assertEquals(cons, info.reflectAs(Constructor.class, MethodHandles.lookup()));
        assertEquals(type, info.getMethodType());

        // Test with a static field :
        Field field = UnreflectTester.class.getField("publicStaticField");

        handle = MethodHandles.lookup().findStaticSetter(
                UnreflectTester.class, "publicStaticField", String.class);

        info = MethodHandles.lookup().revealDirect(handle);
        assertEquals(MethodHandleInfo.REF_putStatic, info.getReferenceKind());
        assertEquals("publicStaticField", info.getName());
        assertTrue(UnreflectTester.class == info.getDeclaringClass());
        assertFalse(info.isVarArgs());
        assertEquals(field, info.reflectAs(Field.class, MethodHandles.lookup()));
        assertEquals(MethodType.methodType(void.class, String.class), info.getMethodType());

        // Test with a setter on the same field, the type of the handle should change
        // but everything else must remain the same.
        handle = MethodHandles.lookup().findStaticGetter(
                UnreflectTester.class, "publicStaticField", String.class);
        info = MethodHandles.lookup().revealDirect(handle);
        assertEquals(MethodHandleInfo.REF_getStatic, info.getReferenceKind());
        assertEquals(field, info.reflectAs(Field.class, MethodHandles.lookup()));
        assertEquals(MethodType.methodType(String.class), info.getMethodType());

        // Test with an instance field :
        field = UnreflectTester.class.getField("publicField");

        handle = MethodHandles.lookup().findSetter(
                UnreflectTester.class, "publicField", String.class);

        info = MethodHandles.lookup().revealDirect(handle);
        assertEquals(MethodHandleInfo.REF_putField, info.getReferenceKind());
        assertEquals("publicField", info.getName());
        assertTrue(UnreflectTester.class == info.getDeclaringClass());
        assertFalse(info.isVarArgs());
        assertEquals(field, info.reflectAs(Field.class, MethodHandles.lookup()));
        assertEquals(MethodType.methodType(void.class, String.class), info.getMethodType());

        // Test with a setter on the same field, the type of the handle should change
        // but everything else must remain the same.
        handle = MethodHandles.lookup().findGetter(
                UnreflectTester.class, "publicField", String.class);
        info = MethodHandles.lookup().revealDirect(handle);
        assertEquals(MethodHandleInfo.REF_getField, info.getReferenceKind());
        assertEquals(field, info.reflectAs(Field.class, MethodHandles.lookup()));
        assertEquals(MethodType.methodType(String.class), info.getMethodType());
    }

    public void testReflectAs() throws Throwable {
        // Test with a virtual method :
        MethodType type = MethodType.methodType(String.class);
        MethodHandle handle = MethodHandles.lookup().findVirtual(
                UnreflectTester.class, "publicMethod", type);

        Method reflected = MethodHandles.reflectAs(Method.class, handle);
        Method meth = UnreflectTester.class.getMethod("publicMethod");
        assertEquals(meth, reflected);

        try {
            MethodHandles.reflectAs(Field.class, handle);
            fail();
        } catch (ClassCastException expected) {
        }

        try {
            MethodHandles.reflectAs(Constructor.class, handle);
            fail();
        } catch (ClassCastException expected) {
        }

        // Test with a private instance method, unlike the "checked crack" (lol..) API exposed
        // by revealDirect, this doesn't perform any access checks.
        handle = UnreflectTester.lookup.findSpecial(
                UnreflectTester.class, "privateMethod", type, UnreflectTester.class);
        meth = UnreflectTester.class.getDeclaredMethod("privateMethod");
        reflected = MethodHandles.reflectAs(Method.class, handle);
        assertEquals(meth, reflected);

        // Test with a constructor :
        type = MethodType.methodType(void.class, String.class, boolean.class);
        handle = MethodHandles.lookup().findConstructor(UnreflectTester.class, type);

        Constructor cons = UnreflectTester.class.getConstructor(String.class, boolean.class);
        Constructor reflectedCons = MethodHandles.reflectAs(Constructor.class, handle);
        assertEquals(cons, reflectedCons);

        try {
            MethodHandles.reflectAs(Method.class, handle);
            fail();
        } catch (ClassCastException expected) {
        }

        // Test with an instance field :
        handle = MethodHandles.lookup().findSetter(
                UnreflectTester.class, "publicField", String.class);

        Field field = UnreflectTester.class.getField("publicField");
        Field reflectedField = MethodHandles.reflectAs(Field.class, handle);
        assertEquals(field, reflectedField);

        try {
            MethodHandles.reflectAs(Method.class, handle);
            fail();
        } catch (ClassCastException expected) {
        }

        // Test with a non-direct method handle.
        try {
            MethodHandles.reflectAs(Method.class, MethodHandles.constant(String.class, "foo"));
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public static class Inner1 {
        public static MethodHandles.Lookup lookup = MethodHandles.lookup();
    }

    public static class Inner2 {
    }

    private static void privateStaticMethod() {}
    public static void publicStaticMethod() {}
    static void packagePrivateStaticMethod() {}
    protected static void protectedStaticMethod() {}

    public void publicMethod() {}
    private void privateMethod() {}
    void packagePrivateMethod() {}
    protected void protectedMethod() {}

    public static class ConstructorTest {
        ConstructorTest(String unused, int unused2) {}
        protected ConstructorTest(String unused) {}
        private ConstructorTest(String unused, char unused2) {}
    }
}

class PackageSibling {
    public void publicMethod() {}
    public static void publicStaticMethod() {}

    protected PackageSibling(String unused) {}
    public PackageSibling(String unused, char unused2) {}
}

