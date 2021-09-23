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

package libcore.dalvik.system;

import dalvik.system.BlockGuard;
import dalvik.system.PathClassLoader;
import java.lang.reflect.Method;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

import libcore.io.Streams;
import tests.support.resource.Support_Resources;

import junit.framework.TestCase;

public final class PathClassLoaderTest extends TestCase {

    /**
     * Make sure we're searching the application library path first.
     * http://b/issue?id=2933456
     */
    public void testLibraryPathSearchOrder() throws IOException {
        File tmp = new File(System.getProperty("java.io.tmpdir"));
        File systemLibPath = new File(tmp, "systemLibPath");
        File applicationLibPath = new File(tmp, "applicationLibPath");
        makeTempFile(systemLibPath, "libduplicated.so");
        File applicationLib = makeTempFile(applicationLibPath, "libduplicated.so");

        System.setProperty("java.library.path", systemLibPath.toString());
        PathClassLoader pathClassLoader = new PathClassLoader(applicationLibPath.toString(),
                applicationLibPath.toString(), getClass().getClassLoader());

        String path = pathClassLoader.findLibrary("duplicated");
        assertEquals(applicationLib.toString(), path);
    }

    private File makeTempFile(File directory, String name) throws IOException {
        directory.mkdirs();
        File result = new File(directory, name);
        FileOutputStream stream = new FileOutputStream(result);
        stream.close();
        assertTrue(result.exists());
        return result;
    }

    private static File extractResourceJar(String name) throws Exception {
        // Extract loading-test.jar from the resource.
        ClassLoader pcl = PathClassLoaderTest.class.getClassLoader();
        File jar = File.createTempFile(name, ".jar");
        try (InputStream in = pcl.getResourceAsStream("dalvik/system/loading-test.jar");
             FileOutputStream out = new FileOutputStream(jar)) {
            Streams.copy(in, out);
        }

        return jar;
    }

    public void testAppUseOfPathClassLoader() throws Exception {
        File jar = extractResourceJar("loading-test");

        // Execute code from the jar file using a PathClassLoader.
        PathClassLoader cl = new PathClassLoader(jar.getPath(), Object.class.getClassLoader());
        Class c = cl.loadClass("test.Test1");
        Method m = c.getMethod("test", (Class[]) null);
        String result = (String) m.invoke(null, (Object[]) null);
        assertSame("blort", result);

        // Clean up the extracted jar file.
        assertTrue(jar.delete());
    }

    public void test_classLoader_tampered_certificate_loadsOK_nullCertificates() throws Exception {
        File resources = Support_Resources.createTempFolder();
        String jar = "hyts_signed_wrong_cert.jar";
        String signedEntryName = "coucou/FileAccess.class";
        Support_Resources.copyFile(resources, null, jar);
        File f = new File(resources, jar);
        PathClassLoader pcl = new PathClassLoader(
                f.getAbsolutePath(), this.getClass().getClassLoader());
        InputStream is = pcl.getResourceAsStream(signedEntryName);

        // We can read all the bytes in the resource despite META-INF/TEST.DSA being malformed
        // (lacking first byte compared to the one in hyts_signed.jar, see
        // {@link org.apache.harmony.tests.java.util.jar.JarFileTest}
        while (is.available() > 0) {
            is.read();
        }

        JarFile jarFile = new JarFile(f, true /* verify */, ZipFile.OPEN_READ);
        try {
            JarEntry signedEntry = (JarEntry) jarFile.getEntry(signedEntryName);
            assertNotNull(signedEntry);
            // Check we return null for the certificates and signers.
            assertNull(signedEntry.getCertificates());
            assertNull(signedEntry.getCodeSigners());
        } finally {
            jarFile.close();
        }
    }

    public void test_classLoader_exceptionDuringLoading() throws Exception {
        final File jar = extractResourceJar("loading-test");

        final PathClassLoader pcl = new PathClassLoader(jar.getAbsolutePath(),
                Object.class.getClassLoader());


        BlockGuard.Policy policy = BlockGuard.getThreadPolicy();
        BlockGuard.setThreadPolicy(new BlockGuard.Policy() {
            @Override
            public void onWriteToDisk() {
                throw new RuntimeException("onWriteToDisk");
            }

            @Override
            public void onReadFromDisk() {
                throw new RuntimeException("onReadFromDisk");
            }

            @Override
            public void onNetwork() {
                throw new RuntimeException("onNetwork");
            }

            @Override
            public void onUnbufferedIO() {
                throw new RuntimeException("onUnbufferedIO");
            }

            @Override
            public void onExplicitGc() {
                throw new RuntimeException("onExplicitGc");
            }

            @Override
            public int getPolicyMask() {
                return 0;
            }
        });

        try {
            try {
                // Resource loading involves a blocking operation and will throw a RuntimeException
                // here.
                pcl.getResource("test/Resource1.txt");
                fail();
            } catch (RuntimeException expected) {
            }
        } finally {
            BlockGuard.setThreadPolicy(policy);
        }

        // Assert that the ClassLoader recovers after the failure above when the BlockGuard is
        // removed. This also simulates the ClassLoader being used from another thread with a
        // different BlockGuard policy.
        assertNotNull(pcl.getResource("test/Resource1.txt"));
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
    }

    @Override protected void tearDown() throws Exception {
        super.tearDown();
    }
}
