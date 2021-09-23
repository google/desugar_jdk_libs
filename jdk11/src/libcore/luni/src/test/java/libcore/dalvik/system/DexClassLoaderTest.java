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

package libcore.dalvik.system;

import java.lang.reflect.Method;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import libcore.io.Streams;
import junit.framework.TestCase;

import dalvik.system.DexClassLoader;

/**
 * Tests for the class {@link DexClassLoader}.
 */
public class DexClassLoaderTest extends TestCase {

    private File dex1;
    private File dex2;
    private File jar1;
    private File jar2;
    private File nativeLib1;

    private Map<String, File> resourcesMap;

    protected void setUp() throws Exception {
        resourcesMap = ClassLoaderTestSupport.setupAndCopyResources(Arrays.asList(
                "loading-test.dex", "loading-test2.dex", "loading-test.jar", "loading-test2.jar",
                "libfake.so"));

        dex1 = resourcesMap.get("loading-test.dex");
        assertNotNull(dex1);
        dex2 = resourcesMap.get("loading-test2.dex");
        assertNotNull(dex2);
        jar1 = resourcesMap.get("loading-test.jar");
        assertNotNull(jar1);
        jar2 = resourcesMap.get("loading-test2.jar");
        assertNotNull(jar2);
        nativeLib1 = resourcesMap.get("libfake.so");
        assertNotNull(nativeLib1);
    }

    protected void tearDown() {
        ClassLoaderTestSupport.cleanUpResources(resourcesMap);
    }

    /**
     * Helper to construct a DexClassLoader instance to test.
     *
     * @param files The .dex or .jar files to use for the class path.
     */
    private ClassLoader createLoader(File... files) {
        assertNotNull(files);
        assertTrue(files.length > 0);
        String path = files[0].getAbsolutePath();
        for (int i = 1; i < files.length; i++) {
            path += File.pathSeparator + files[i].getAbsolutePath();
        }
        return new DexClassLoader(path, null, null,
            ClassLoader.getSystemClassLoader());
    }

    /**
     * Helper to construct a new DexClassLoader instance to test, using the
     * given files as the class path, and call a named no-argument static
     * method on a named class.
     *
     * @param className The name of the class of the method to call.
     * @param methodName The name of the method to call.
     * @param files The .dex or .jar files to use for the class path.
     */
    public Object createLoaderAndCallMethod(
            String className, String methodName, File... files)
            throws ReflectiveOperationException {
        ClassLoader cl = createLoader(files);
        Class c = cl.loadClass(className);
        Method m = c.getMethod(methodName, (Class[]) null);
        return m.invoke(null, (Object[]) null);
    }

    /**
     * Helper to construct a new DexClassLoader instance to test, using the
     * given files as the class path, and read the contents of the named
     * resource as a String.
     *
     * @param resourceName The name of the resource to get.
     * @param files The .dex or .jar files to use for the class path.
     */
    private String createLoaderAndGetResource(String resourceName, File... files) throws Exception {
        ClassLoader cl = createLoader(files);
        InputStream in = cl.getResourceAsStream(resourceName);
        if (in == null) {
            throw new IllegalStateException("Resource not found: " + resourceName);
        }

        byte[] contents = Streams.readFully(in);
        return new String(contents, "UTF-8");
    }

    // ONE_JAR

    /**
     * Just a trivial test of construction. This one merely makes
     * sure that a valid construction doesn't fail. It doesn't try
     * to verify anything about the constructed instance, other than
     * checking for the existence of optimized dex files.
     */
    public void test_oneJar_init() throws Exception {
        ClassLoader cl = createLoader(jar1);
    }

    /**
     * Check that a class in the jar/dex file may be used successfully. In this
     * case, a trivial static method is called.
     */
    public void test_oneJar_simpleUse() throws Exception {
        String result = (String) createLoaderAndCallMethod("test.Test1", "test", jar1);
        assertSame("blort", result);
    }

    /*
     * All the following tests are just pass-throughs to test code
     * that lives inside the loading-test dex/jar file.
     */

    public void test_oneJar_constructor() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_constructor", jar1);
    }

    public void test_oneJar_callStaticMethod() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_callStaticMethod", jar1);
    }

    public void test_oneJar_getStaticVariable() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_getStaticVariable", jar1);
    }

    public void test_oneJar_callInstanceMethod() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_callInstanceMethod", jar1);
    }

    public void test_oneJar_getInstanceVariable() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_getInstanceVariable", jar1);
    }

    // ONE_DEX

    public void test_oneDex_init() throws Exception {
        ClassLoader cl = createLoader(dex1);
    }

    public void test_oneDex_simpleUse() throws Exception {
        String result = (String) createLoaderAndCallMethod("test.Test1", "test", dex1);
        assertSame("blort", result);
    }

    public void test_oneDex_constructor() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_constructor", dex1);
    }

    public void test_oneDex_callStaticMethod() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_callStaticMethod", dex1);
    }

    public void test_oneDex_getStaticVariable() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_getStaticVariable", dex1);
    }

    public void test_oneDex_callInstanceMethod() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_callInstanceMethod", dex1);
    }

    public void test_oneDex_getInstanceVariable() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_getInstanceVariable", dex1);
    }

    // TWO_JAR

    public void test_twoJar_init() throws Exception {
        ClassLoader cl = createLoader(jar1, jar2);
    }

    public void test_twoJar_simpleUse() throws Exception {
        String result = (String) createLoaderAndCallMethod("test.Test1", "test", jar1, jar2);
        assertSame("blort", result);
    }

    public void test_twoJar_constructor() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_constructor", jar1, jar2);
    }

    public void test_twoJar_callStaticMethod() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_callStaticMethod", jar1, jar2);
    }

    public void test_twoJar_getStaticVariable() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_getStaticVariable", jar1, jar2);
    }

    public void test_twoJar_callInstanceMethod() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_callInstanceMethod", jar1, jar2);
    }

    public void test_twoJar_getInstanceVariable() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_getInstanceVariable", jar1, jar2);
    }

    public void test_twoJar_diff_constructor() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_diff_constructor", jar1, jar2);
    }

    public void test_twoJar_diff_callStaticMethod() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_diff_callStaticMethod", jar1, jar2);
    }

    public void test_twoJar_diff_getStaticVariable() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_diff_getStaticVariable", jar1, jar2);
    }

    public void test_twoJar_diff_callInstanceMethod() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_diff_callInstanceMethod", jar1, jar2);
    }

    public void test_twoJar_diff_getInstanceVariable() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_diff_getInstanceVariable", jar1, jar2);
    }

    // TWO_DEX

    public void test_twoDex_init() throws Exception {
        ClassLoader cl = createLoader(dex1, dex2);
    }

    public void test_twoDex_simpleUse() throws Exception {
        String result = (String) createLoaderAndCallMethod("test.Test1", "test", dex1, dex2);
        assertSame("blort", result);
    }

    public void test_twoDex_constructor() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_constructor", dex1, dex2);
    }

    public void test_twoDex_callStaticMethod() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_callStaticMethod", dex1, dex2);
    }

    public void test_twoDex_getStaticVariable() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_getStaticVariable", dex1, dex2);
    }

    public void test_twoDex_callInstanceMethod() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_callInstanceMethod", dex1, dex2);
    }

    public void test_twoDex_getInstanceVariable() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_getInstanceVariable", dex1, dex2);
    }

    public void test_twoDex_diff_constructor() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_diff_constructor", dex1, dex2);
    }

    public void test_twoDex_diff_callStaticMethod() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_diff_callStaticMethod", dex1, dex2);
    }

    public void test_twoDex_diff_getStaticVariable() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_diff_getStaticVariable", dex1, dex2);
    }

    public void test_twoDex_diff_callInstanceMethod() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_diff_callInstanceMethod", dex1, dex2);
    }

    public void test_twoDex_diff_getInstanceVariable() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_diff_getInstanceVariable", dex1, dex2);
    }

    /*
     * Tests specifically for resource-related functionality.  Since
     * raw dex files don't contain resources, these test only work
     * with jar files.
     */

    /**
     * Check that a resource in the jar file is retrievable and contains
     * the expected contents.
     */
    public void test_oneJar_directGetResourceAsStream() throws Exception {
        String result = createLoaderAndGetResource("test/Resource1.txt", jar1);
        assertEquals("Muffins are tasty!\n", result);
    }

    /**
     * Check that a resource in the jar file can be retrieved from
     * a class within that jar file.
     */
    public void test_oneJar_getResourceAsStream() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_getResourceAsStream", jar1);
    }

    public void test_twoJar_directGetResourceAsStream() throws Exception {
        String result = createLoaderAndGetResource("test/Resource1.txt", jar1, jar2);
        assertEquals("Muffins are tasty!\n", result);
    }

    public void test_twoJar_getResourceAsStream() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_getResourceAsStream", jar1, jar2);
    }

    /**
     * Check that a resource in the second jar file is retrievable and
     * contains the expected contents.
     */
    public void test_twoJar_diff_directGetResourceAsStream() throws Exception {
        String result = createLoaderAndGetResource("test2/Resource2.txt", jar1, jar2);
        assertEquals("Who doesn't like a good biscuit?\n", result);
    }

    /**
     * Check that a resource in a jar file can be retrieved from
     * a class within the other jar file.
     */
    public void test_twoJar_diff_getResourceAsStream() throws Exception {
        createLoaderAndCallMethod("test.TestMethods", "test_diff_getResourceAsStream", jar1, jar2);
    }

    /*
     * Tests native modification behaviors
     */

    private boolean isLibraryFound(DexClassLoader loader, String libName) {
        String ret = loader.findLibrary(libName);
        if (ret != null && ret.startsWith("/")) {
            return true;
        }
        // This case includes findLibrary returning null or a mapped libname
        // e.g. "libfake.so" when libName was "fake".
        return false;
    }

    /**
     * Checks that a adding a native library to an existing class loader makes it visible for
     * subsequent calls.
     * @throws Exception
     */
    public void test_oneDex_addNative_findsLibrary() throws Exception {
        String path = nativeLib1.getParentFile().getAbsolutePath();
        DexClassLoader classLoader = (DexClassLoader) createLoader(dex1);

        assertFalse("findLibrary should not find un-added path",
                isLibraryFound(classLoader, "fake"));

        classLoader.addNativePath(Collections.singletonList(path));

        assertTrue("findLibrary should find newly added path",
                isLibraryFound(classLoader, "fake"));
    }
}
