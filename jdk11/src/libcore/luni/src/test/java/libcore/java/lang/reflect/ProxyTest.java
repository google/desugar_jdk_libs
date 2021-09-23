/*
 * Copyright (C) 2010 The Android Open Source Project
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

package libcore.java.lang.reflect;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.SocketException;
import junit.framework.TestCase;
import tests.util.ClassLoaderBuilder;

public final class ProxyTest extends TestCase {
    private final ClassLoader loader = getClass().getClassLoader();
    private final InvocationHandler returnHandler = new TestInvocationHandler();
    private final InvocationHandler throwHandler = new InvocationHandler() {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            throw (Throwable) args[0];
        }
    };

    /**
     * Make sure the proxy's class loader fails if it cannot see the class
     * loaders of its implemented interfaces. http://b/1608481
     */
    public void testClassLoaderMustSeeImplementedInterfaces() throws Exception {
        String prefix = ProxyTest.class.getName();
        ClassLoader loaderA = new ClassLoaderBuilder().withPrivateCopy(prefix).build();
        ClassLoader loaderB = new ClassLoaderBuilder().withPrivateCopy(prefix).build();

        Class[] interfacesA = { loaderA.loadClass(prefix + "$Echo") };
        try {
            Proxy.newProxyInstance(loaderB, interfacesA, returnHandler);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testClassLoaderDoesNotNeedToSeeInvocationHandlerLoader() throws Exception {
        String prefix = ProxyTest.class.getName();
        ClassLoader loaderA = new ClassLoaderBuilder().withPrivateCopy(prefix).build();
        ClassLoader loaderB = new ClassLoaderBuilder().withPrivateCopy(prefix).build();
        InvocationHandler invocationHandlerB = (InvocationHandler) loaderB.loadClass(
                prefix + "$TestInvocationHandler").newInstance();

        Class[] interfacesA = { loaderA.loadClass(prefix + "$Echo") };
        Object proxy = Proxy.newProxyInstance(loaderA, interfacesA, invocationHandlerB);
        assertEquals(loaderA, proxy.getClass().getClassLoader());
        assertEquals("foo", proxy.getClass().getMethod("echo", String.class).invoke(proxy, "foo"));
    }

    public void testIncompatibleReturnTypesPrimitiveAndPrimitive() {
        try {
            Proxy.newProxyInstance(loader, new Class[] {ReturnsInt.class, ReturnsFloat.class},
                    returnHandler);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testIncompatibleReturnTypesPrimitiveAndWrapper() {
        try {
            Proxy.newProxyInstance(loader, new Class[] {ReturnsInt.class, ReturnsInteger.class},
                    returnHandler);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testIncompatibleReturnTypesPrimitiveAndVoid() {
        try {
            Proxy.newProxyInstance(loader, new Class[] {ReturnsInt.class, ReturnsVoid.class},
                    returnHandler);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testIncompatibleReturnTypesIncompatibleObjects() {
        try {
            Proxy.newProxyInstance(loader, new Class[] {ReturnsInteger.class, ReturnsString.class },
                    returnHandler);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testCompatibleReturnTypesImplementedInterface() {
        Proxy.newProxyInstance(loader, new Class[] {ReturnsString.class, ReturnsCharSequence.class},
                returnHandler);
        Proxy.newProxyInstance(loader, new Class[]{ReturnsObject.class, ReturnsCharSequence.class,
                ReturnsString.class}, returnHandler);
        Proxy.newProxyInstance(loader, new Class[]{ReturnsObject.class, ReturnsCharSequence.class,
                ReturnsString.class, ReturnsSerializable.class, ReturnsComparable.class},
                returnHandler);
    }


    public void testCompatibleReturnTypesSuperclass() {
        Proxy.newProxyInstance(loader, new Class[] {ReturnsString.class, ReturnsObject.class},
                returnHandler);
    }

    public void testDeclaredExceptionIntersectionIsSubtype() throws Exception {
        ThrowsIOException instance = (ThrowsIOException) Proxy.newProxyInstance(loader,
                new Class[] {ThrowsIOException.class, ThrowsEOFException.class},
                throwHandler);
        try {
            instance.run(new EOFException());
            fail();
        } catch (EOFException expected) {
        }
        try {
            instance.run(new IOException());
            fail();
        } catch (UndeclaredThrowableException expected) {
        }
        try {
            instance.run(new Exception());
            fail();
        } catch (UndeclaredThrowableException expected) {
        }
    }

    public void testDeclaredExceptionIntersectionIsEmpty() throws Exception {
        ThrowsEOFException instance = (ThrowsEOFException) Proxy.newProxyInstance(loader,
                new Class[] {ThrowsSocketException.class, ThrowsEOFException.class},
                throwHandler);
        try {
            instance.run(new EOFException());
            fail();
        } catch (UndeclaredThrowableException expected) {
        }
        try {
            instance.run(new SocketException());
            fail();
        } catch (UndeclaredThrowableException expected) {
        }
    }

    public void testDeclaredExceptionIntersectionIsSubset() throws Exception {
        ThrowsEOFException instance = (ThrowsEOFException) Proxy.newProxyInstance(loader,
                new Class[] {ThrowsEOFException.class, ThrowsSocketExceptionAndEOFException.class},
                throwHandler);
        try {
            instance.run(new EOFException());
            fail();
        } catch (EOFException expected) {
        }
        try {
            instance.run(new SocketException());
            fail();
        } catch (UndeclaredThrowableException expected) {
        }
        try {
            instance.run(new IOException());
            fail();
        } catch (UndeclaredThrowableException expected) {
        }
    }

    public void testDeclaredExceptionIntersectedByExactReturnTypes() throws Exception {
        ThrowsIOException instance = (ThrowsIOException) Proxy.newProxyInstance(loader,
                new Class[] {ThrowsIOException.class, ThrowsEOFExceptionReturnsString.class},
                throwHandler);
        try {
            instance.run(new EOFException());
            fail();
        } catch (EOFException expected) {
        }
        try {
            instance.run(new IOException());
            fail();
        } catch (IOException expected) {
        }
        try {
            ((ThrowsEOFExceptionReturnsString) instance).run(new EOFException());
            fail();
        } catch (EOFException expected) {
        }
        try {
            ((ThrowsEOFExceptionReturnsString) instance).run(new IOException());
            fail();
        } catch (UndeclaredThrowableException expected) {
        }
    }

    public void test_getProxyClass_nullInterfaces() {
        try {
            Proxy.getProxyClass(loader, new Class<?>[] { null });
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            Proxy.getProxyClass(loader, Echo.class, null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void test_getProxyClass_duplicateInterfaces() {
        try {
            Proxy.getProxyClass(loader, Echo.class, Echo.class);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void test_getProxyClass_caching() throws Exception {
        Class<?> proxy1 = Proxy.getProxyClass(loader, Echo.class, ReturnsInt.class);
        Class<?> proxy2 = Proxy.getProxyClass(loader, Echo.class, ReturnsInt.class);
        Class<?> proxy3 = Proxy.getProxyClass(loader, ReturnsInt.class, Echo.class);

        assertSame(proxy1, proxy2);
        assertTrue(!proxy2.equals(proxy3));
    }

    public void testMethodsImplementedByFarIndirectInterface() {
        ExtendsExtendsDeclaresFiveMethods instance = (ExtendsExtendsDeclaresFiveMethods)
                Proxy.newProxyInstance(loader, new Class[]{ExtendsExtendsDeclaresFiveMethods.class},
                returnHandler);
        assertEquals("foo", instance.a("foo"));
        assertEquals(0x12345678, instance.b(0x12345678));
        assertEquals(Double.MIN_VALUE, instance.c(Double.MIN_VALUE));
        assertEquals(null, instance.d(null));
        assertEquals(0x1234567890abcdefL, instance.e(0x1234567890abcdefL));
    }

    public void testEquals() {
        InvocationHandler handler = new InvocationHandler() {
            @Override public Object invoke(Object proxy, Method method, Object[] args) {
                return args[0] == ProxyTest.class; // bogus as equals(), but good for testing
            }
        };
        Echo instance = (Echo) Proxy.newProxyInstance(loader, new Class[]{Echo.class}, handler);
        assertTrue(instance.equals(ProxyTest.class));
        assertFalse(instance.equals(new Object()));
        assertFalse(instance.equals(instance));
        assertFalse(instance.equals(null));
    }

    public void testHashCode() {
        InvocationHandler handler = new InvocationHandler() {
            @Override public Object invoke(Object proxy, Method method, Object[] args) {
                return 0x12345678;
            }
        };
        Echo instance = (Echo) Proxy.newProxyInstance(loader, new Class[]{Echo.class}, handler);
        assertEquals(0x12345678, instance.hashCode());
    }

    public void testToString() {
        InvocationHandler handler = new InvocationHandler() {
            @Override public Object invoke(Object proxy, Method method, Object[] args) {
                return "foo";
            }
        };
        Echo instance = (Echo) Proxy.newProxyInstance(loader, new Class[]{Echo.class}, handler);
        assertEquals("foo", instance.toString());
    }

    public void testReturnTypeDoesNotSatisfyAllConstraintsWithLenientCaller() {
        InvocationHandler handler = new InvocationHandler() {
            @Override public Object invoke(Object proxy, Method method, Object[] args) {
                assertEquals(Object.class, method.getReturnType());
                return Boolean.TRUE; // not the right type for 'ReturnsString' callers
            }
        };
        ReturnsObject returnsObject = (ReturnsObject) Proxy.newProxyInstance(loader,
                new Class[] {ReturnsString.class, ReturnsObject.class}, handler);
        assertEquals(true, returnsObject.foo());
    }

    public void testReturnTypeDoesNotSatisfyAllConstraintsWithStrictCaller() {
        InvocationHandler handler = new InvocationHandler() {
            @Override public Object invoke(Object proxy, Method method, Object[] args) {
                assertEquals(String.class, method.getReturnType());
                return Boolean.TRUE; // not the right type for 'ReturnsString' callers
            }
        };
        ReturnsString returnsString = (ReturnsString) Proxy.newProxyInstance(loader,
                new Class[] {ReturnsString.class, ReturnsObject.class}, handler);
        try {
            returnsString.foo();
            fail();
        } catch (ClassCastException expected) {
        }
    }

    public void testReturnsTypeAndInterfaceNotImplementedByThatType() {
        try {
            Proxy.newProxyInstance(loader, new Class[] {ReturnsString.class, ReturnsEcho.class},
                    returnHandler);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public interface Echo {
        String echo(String s);
    }

    public interface ReturnsInt {
        int foo();
    }

    public interface ReturnsFloat {
        float foo();
    }

    public interface ReturnsInteger {
        Integer foo();
    }

    public interface ReturnsString {
        String foo();
    }

    public interface ReturnsCharSequence {
        CharSequence foo();
    }

    public interface ReturnsSerializable {
        CharSequence foo();
    }

    public interface ReturnsComparable {
        CharSequence foo();
    }

    public interface ReturnsObject {
        Object foo();
    }

    public interface ReturnsVoid {
        void foo();
    }

    public interface ReturnsEcho {
        Echo foo();
    }

    public interface ThrowsIOException {
        Object run(Throwable toThrow) throws IOException;
    }

    public interface ThrowsEOFException {
        Object run(Throwable toThrow) throws EOFException;
    }

    public interface ThrowsEOFExceptionReturnsString {
        String run(Throwable toThrow) throws EOFException;
    }

    public interface ThrowsSocketException {
        Object run(Throwable toThrow) throws SocketException;

    }
    public interface ThrowsSocketExceptionAndEOFException {
        Object run(Throwable toThrow) throws SocketException, EOFException;

    }

    public interface DeclaresFiveMethods {
        String a(String a);
        int b(int b);
        double c(double c);
        Object d(Object d);
        long e(long e);
    }
    public interface ExtendsDeclaresFiveMethods extends DeclaresFiveMethods {
    }
    public interface ExtendsExtendsDeclaresFiveMethods extends ExtendsDeclaresFiveMethods {
    }

    public static class TestInvocationHandler implements InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return args[0];
        }
    }

    // https://code.google.com/p/android/issues/detail?id=24846
    public void test24846() throws Exception {
      ClassLoader cl = getClass().getClassLoader();
      Class[] interfaces = { java.beans.PropertyChangeListener.class };
      Object proxy = Proxy.newProxyInstance(cl, interfaces, new InvocationHandler() {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          return null;
        }
      });
      for (Field field : proxy.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        assertFalse(field.isAnnotationPresent(Deprecated.class));
      }
    }

    public interface DefaultMethod {
      static final Object DEFAULT_RETURN_VALUE = new Object();
      default Object test() {
        return DEFAULT_RETURN_VALUE;
      }
    }

    // Make sure we can proxy default methods.
    public void testProxyDefault() throws Exception {
      Object invocationHandlerReturnValue = new Object();
      // Just always return the different object.
      InvocationHandler handler = (o, m, oa) -> invocationHandlerReturnValue;
      DefaultMethod dm = (DefaultMethod) Proxy.newProxyInstance(
          Thread.currentThread().getContextClassLoader(),
          new Class[] { DefaultMethod.class },
          handler);

      assertTrue(dm != null);
      assertEquals(invocationHandlerReturnValue, dm.test());
    }
}
