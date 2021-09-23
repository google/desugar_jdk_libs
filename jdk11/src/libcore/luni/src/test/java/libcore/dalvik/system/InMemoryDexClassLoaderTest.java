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
 */

package libcore.dalvik.system;

import java.lang.reflect.Method;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import libcore.io.Streams;
import junit.framework.TestCase;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.InMemoryDexClassLoader;
import dalvik.system.DexPathList;

/**
 * Tests for the class {@link InMemoryDexClassLoader}.
 */
public class InMemoryDexClassLoaderTest extends TestCase {
    private static final String PACKAGE_PATH = "dalvik/system/";

    private File srcDir;
    private File dex1;
    private File dex2;

    protected void setUp() throws Exception {
        srcDir = File.createTempFile("src", "");
        assertTrue(srcDir.delete());
        assertTrue(srcDir.mkdirs());

        dex1 = new File(srcDir, "loading-test.dex");
        dex2 = new File(srcDir, "loading-test2.dex");

        copyResource("loading-test.dex", dex1);
        copyResource("loading-test2.dex", dex2);
    }

    protected void tearDown() {
        cleanUpDir(srcDir);
    }

    private static void cleanUpDir(File dir) {
        if (!dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                cleanUpDir(file);
            } else {
                assertTrue(file.delete());
            }
        }
        assertTrue(dir.delete());
    }

    /**
     * Copy a resource in the package directory to the indicated
     * target file.
     */
    private static void copyResource(String resourceName,
            File destination) throws IOException {
        ClassLoader loader = InMemoryDexClassLoaderTest.class.getClassLoader();
        InputStream in = loader.getResourceAsStream(PACKAGE_PATH + resourceName);
        if (in == null) {
            throw new IllegalStateException("Resource not found: " + PACKAGE_PATH + resourceName);
        }
        try (FileOutputStream out = new FileOutputStream(destination)) {
            Streams.copy(in, out);
        } finally {
            in.close();
        }
    }

    /**
     * Helper to construct a direct ByteBuffer with the contents of a given file.
     *
     * Constructs a new direct ByteBuffer and inserts {@code paddingBefore} amount of
     * zero padding followed by the contents of {@code file}. The buffer's position is
     * set to the beginning of the file's data.
     *
     * @param file The file to be read
     * @param paddingBefore Number of zero bytes to be inserted at the beginning of the buffer.
     */
    private static ByteBuffer readFileToByteBufferDirect(File file, int paddingBefore)
            throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(paddingBefore + (int)file.length());
            buffer.put(new byte[paddingBefore]);
            int done = 0;
            while (done != file.length()) {
                done += raf.getChannel().read(buffer);
            }
            buffer.rewind();
            buffer.position(paddingBefore);
            return buffer;
        }
    }

    /**
     * Helper to construct a direct ByteBuffer with the contents of a given file.
     *
     * Constructs a new direct ByteBuffer and the contents of {@code file}. The buffer's
     * position is zero.
     *
     * @param file The file to be read
     */
    private static ByteBuffer readFileToByteBufferDirect(File file) throws IOException {
        return readFileToByteBufferDirect(file, /* paddingBefore */ 0);
    }

    /**
     * Helper to construct an indirect ByteBuffer with the contents of a given file.
     *
     * Constructs a new indirect ByteBuffer and inserts {@code paddingBefore} amount of
     * zero padding followed by the contents of {@code file}. The buffer's position is
     * set to the beginning of the file's data.
     *
     * @param file The file to be read
     * @param paddingBefore Number of zero bytes to be inserted at the beginning of the buffer.
     */
    private static ByteBuffer readFileToByteBufferIndirect(File file, int paddingBefore)
            throws IOException {
        ByteBuffer direct = readFileToByteBufferDirect(file, paddingBefore);
        direct.rewind();
        byte[] array = new byte[direct.limit()];
        direct.get(array);
        ByteBuffer buf = ByteBuffer.wrap(array);
        buf.position(paddingBefore);
        return buf;
    }

    /**
     * Helper to construct an indirect ByteBuffer with the contents of a given file.
     *
     * Constructs a new indirect ByteBuffer and the contents of {@code file}. The buffer's
     * position is zero.
     *
     * @param file The file to be read
     */
    private static ByteBuffer readFileToByteBufferIndirect(File file) throws IOException {
        return readFileToByteBufferIndirect(file, /* paddingBefore */ 0);
    }

    /**
     * Helper to construct a InMemoryDexClassLoader instance to test.
     *
     * Creates InMemoryDexClassLoader from ByteBuffer instances that are
     * direct allocated.
     *
     * @param files The .dex files to use for the class path.
     */
    private ClassLoader createLoaderDirect(File... files) throws IOException {
        assertNotNull(files);
        assertTrue(files.length > 0);
        ClassLoader result = ClassLoader.getSystemClassLoader();
        for (int i = 0; i < files.length; ++i) {
            ByteBuffer buffer = readFileToByteBufferDirect(files[i]);
            result = new InMemoryDexClassLoader(new ByteBuffer[] { buffer }, result);
        }
        return result;
    }

    /**
     * Helper to construct a InMemoryDexClassLoader instance to test.
     *
     * Creates InMemoryDexClassLoader from ByteBuffer instances that are
     * heap allocated.
     *
     * @param files The .dex files to use for the class path.
     */
    private ClassLoader createLoaderIndirect(File... files) throws IOException {
        assertNotNull(files);
        assertTrue(files.length > 0);
        ClassLoader result = ClassLoader.getSystemClassLoader();
        for (int i = 0; i < files.length; ++i) {
            ByteBuffer buffer = readFileToByteBufferIndirect(files[i]);
            result = new InMemoryDexClassLoader(new ByteBuffer[] { buffer }, result);
        }
        return result;
    }

    /**
     * Helper to construct a new InMemoryDexClassLoader via direct
     * ByteBuffer instances.
     *
     * @param className The name of the class of the method to call.
     * @param methodName The name of the method to call.
     * @param files The .dex or .jar files to use for the class path.
     */
    private Object createLoaderDirectAndCallMethod(
            String className, String methodName, File... files)
            throws IOException, ReflectiveOperationException {
        ClassLoader cl = createLoaderDirect(files);
        Class c = cl.loadClass(className);
        Method m = c.getMethod(methodName, (Class[]) null);
        assertNotNull(m);
        return m.invoke(null, (Object[]) null);
    }

    /**
     * Helper to construct a new InMemoryDexClassLoader via indirect
     * ByteBuffer instances.
     *
     * @param className The name of the class of the method to call.
     * @param methodName The name of the method to call.
     * @param files The .dex or .jar files to use for the class path.
     */
    private Object createLoaderIndirectAndCallMethod(
            String className, String methodName, File... files)
            throws IOException, ReflectiveOperationException {
        ClassLoader cl = createLoaderIndirect(files);
        Class c = cl.loadClass(className);
        Method m = c.getMethod(methodName, (Class[]) null);
        assertNotNull(m);
        return m.invoke(null, (Object[]) null);
    }

    /**
     * Creates an InMemoryDexClassLoader using the content of {@code dex} and with a
     * library path of {@code applicationLibPath}. The parent classloader is the boot
     * classloader.
     *
     * @param dex The .dex file to be loaded.
     * @param applicationLibPath Library search path of the new class loader.
     */
    private static InMemoryDexClassLoader createLoaderWithLibPath(File dex, File applicationLibPath)
            throws IOException {
        return new InMemoryDexClassLoader(
                new ByteBuffer[] { readFileToByteBufferIndirect(dex) },
                applicationLibPath.toString(), null);
    }

    // ONE_DEX with direct ByteBuffer.

    public void test_oneDexDirect_simpleUse() throws Exception {
        String result = (String) createLoaderDirectAndCallMethod("test.Test1", "test", dex1);
        assertSame("blort", result);
    }

    public void test_oneDexDirect_constructor() throws Exception {
        createLoaderDirectAndCallMethod("test.TestMethods", "test_constructor", dex1);
    }

    public void test_oneDexDirect_callStaticMethod() throws Exception {
        createLoaderDirectAndCallMethod("test.TestMethods", "test_callStaticMethod", dex1);
    }

    public void test_oneDexDirect_getStaticVariable() throws Exception {
        createLoaderDirectAndCallMethod("test.TestMethods", "test_getStaticVariable", dex1);
    }

    public void test_oneDexDirect_callInstanceMethod() throws Exception {
        createLoaderDirectAndCallMethod("test.TestMethods", "test_callInstanceMethod", dex1);
    }

    public void test_oneDexDirect_getInstanceVariable() throws Exception {
        createLoaderDirectAndCallMethod("test.TestMethods", "test_getInstanceVariable", dex1);
    }

    // ONE_DEX with non-direct ByteBuffer.

    public void test_oneDexIndirect_simpleUse() throws Exception {
        String result = (String) createLoaderIndirectAndCallMethod("test.Test1", "test", dex1);
        assertSame("blort", result);
    }

    public void test_oneDexIndirect_constructor() throws Exception {
        createLoaderIndirectAndCallMethod("test.TestMethods", "test_constructor", dex1);
    }

    public void test_oneDexIndirect_callStaticMethod() throws Exception {
        createLoaderIndirectAndCallMethod("test.TestMethods", "test_callStaticMethod", dex1);
    }

    public void test_oneDexIndirect_getStaticVariable() throws Exception {
        createLoaderIndirectAndCallMethod("test.TestMethods", "test_getStaticVariable", dex1);
    }

    public void test_oneDexIndirect_callInstanceMethod() throws Exception {
        createLoaderIndirectAndCallMethod("test.TestMethods", "test_callInstanceMethod", dex1);
    }

    public void test_oneDexIndirect_getInstanceVariable() throws Exception {
        createLoaderIndirectAndCallMethod("test.TestMethods", "test_getInstanceVariable", dex1);
    }

    // TWO_DEX with direct ByteBuffer

    public void test_twoDexDirect_simpleUse() throws Exception {
        String result = (String) createLoaderDirectAndCallMethod("test.Test1", "test", dex1, dex2);
        assertSame("blort", result);
    }

    public void test_twoDexDirect_constructor() throws Exception {
        createLoaderDirectAndCallMethod("test.TestMethods", "test_constructor", dex1, dex2);
    }

    public void test_twoDexDirect_callStaticMethod() throws Exception {
        createLoaderDirectAndCallMethod("test.TestMethods", "test_callStaticMethod", dex1, dex2);
    }

    public void test_twoDexDirect_getStaticVariable() throws Exception {
        createLoaderDirectAndCallMethod("test.TestMethods", "test_getStaticVariable", dex1, dex2);
    }

    public void test_twoDexDirect_callInstanceMethod() throws Exception {
        createLoaderDirectAndCallMethod("test.TestMethods", "test_callInstanceMethod", dex1, dex2);
    }

    public void test_twoDexDirect_getInstanceVariable() throws Exception {
        createLoaderDirectAndCallMethod(
            "test.TestMethods", "test_getInstanceVariable", dex1, dex2);
    }

    public void test_twoDexDirect_target2_static_method() throws Exception {
        String result =
                (String) createLoaderDirectAndCallMethod("test2.Target2", "frotz", dex1, dex2);
        assertSame("frotz", result);
    }

    public void test_twoDexDirect_diff_constructor() throws Exception {
        // NB Ordering dex2 then dex1 as classloader's are nested and
        // each only supports a single DEX image. The
        // test.TestMethods.test_diff* methods depend on dex2 hence
        // ordering.
        createLoaderDirectAndCallMethod("test.TestMethods", "test_diff_constructor", dex2, dex1);
    }

    public void test_twoDexDirect_diff_callStaticMethod() throws Exception {
        // NB See comment in test_twoDexDirect_diff_constructor.
        createLoaderDirectAndCallMethod(
            "test.TestMethods", "test_diff_callStaticMethod", dex2, dex1);
    }

    public void test_twoDexDirect_diff_getStaticVariable() throws Exception {
        // NB See comment in test_twoDexDirect_diff_constructor.
        createLoaderDirectAndCallMethod(
            "test.TestMethods", "test_diff_getStaticVariable", dex2, dex1);
    }

    public void test_twoDexDirect_diff_callInstanceMethod() throws Exception {
        // NB See comment in test_twoDexDirect_diff_constructor.
        createLoaderDirectAndCallMethod(
            "test.TestMethods", "test_diff_callInstanceMethod", dex2, dex1);
    }

    public void test_twoDexDirect_diff_getInstanceVariable() throws Exception {
        // NB See comment in test_twoDexDirect_diff_constructor.
        createLoaderDirectAndCallMethod(
            "test.TestMethods", "test_diff_getInstanceVariable", dex2, dex1);
    }

    public void testLibraryPath() throws IOException {
        File applicationLibPath = new File(srcDir, "applicationLibPath");
        File applicationLib = makeEmptyFile(applicationLibPath, "libtestlibpath.so");

        InMemoryDexClassLoader classLoader = createLoaderWithLibPath(dex1, applicationLibPath);

        String path = classLoader.findLibrary("testlibpath");
        assertEquals(applicationLib.toString(), path);
    }

    public void testLibraryPathSearchOrder() throws IOException {
        File systemLibPath = new File(srcDir, "systemLibPath");
        File applicationLibPath = new File(srcDir, "applicationLibPath");
        makeEmptyFile(systemLibPath, "libduplicated.so");
        File applicationLib = makeEmptyFile(applicationLibPath, "libduplicated.so");

        System.setProperty("java.library.path", systemLibPath.toString());
        InMemoryDexClassLoader classLoader = createLoaderWithLibPath(dex1, applicationLibPath);

        String path = classLoader.findLibrary("duplicated");
        assertEquals(applicationLib.toString(), path);
    }

    public void testNullParent() throws IOException, ClassNotFoundException {
        // Other tests set up InMemoryDexClassLoader with the system class loader
        // as parent. Test that passing {@code null} works too (b/120603906).
        InMemoryDexClassLoader classLoader = new InMemoryDexClassLoader(
                new ByteBuffer[] { readFileToByteBufferIndirect(dex1) },
                /* parent */ null);

        // Try to load a class from the boot class loader.
        Class<?> objectClass = classLoader.loadClass("java.lang.Object");
        assertEquals(objectClass, Object.class);

        // Try to load a class from this class loader.
        classLoader.loadClass("test.TestMethods");
    }

    public void testNonZeroBufferOffsetDirect() throws IOException, ClassNotFoundException {
        // Arbitrary amount of padding to prove a non-zero buffer position is supported.
        int paddingBefore = 13;
        InMemoryDexClassLoader classLoader = new InMemoryDexClassLoader(
                new ByteBuffer[] { readFileToByteBufferDirect(dex1, paddingBefore) },
                /* parent */ null);
        classLoader.loadClass("test.TestMethods");
    }

    public void testNonZeroBufferOffsetIndirect() throws IOException, ClassNotFoundException {
        // Arbitrary amount of padding to prove a non-zero buffer position is supported.
        int paddingBefore = 13;
        InMemoryDexClassLoader classLoader = new InMemoryDexClassLoader(
                new ByteBuffer[] { readFileToByteBufferIndirect(dex1, paddingBefore) },
                /* parent */ null);
        classLoader.loadClass("test.TestMethods");
    }

    /**
     * DexPathList.makeInMemoryDexElements() is a legacy code path not used by
     * InMemoryDexClassLoader anymore but heavily used by 3p apps. Test that it still works.
     */
    public void testMakeInMemoryDexElements() throws Exception {
        ArrayList<IOException> exceptions = new ArrayList<>();
        Object[] elements = DexPathList.makeInMemoryDexElements(
                new ByteBuffer[] { readFileToByteBufferDirect(dex1),
                                   readFileToByteBufferIndirect(dex2) },
                exceptions);
        assertEquals(2, elements.length);
        assertTrue(exceptions.isEmpty());
    }

    private static File makeEmptyFile(File directory, String name) throws IOException {
        assertTrue(directory.mkdirs());
        File result = new File(directory, name);
        FileOutputStream stream = new FileOutputStream(result);
        stream.close();
        assertTrue(result.exists());
        return result;
    }

}
