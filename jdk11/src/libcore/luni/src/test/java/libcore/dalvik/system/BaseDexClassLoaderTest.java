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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DelegateLastClassLoader;
import dalvik.system.PathClassLoader;
import java.lang.reflect.Method;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libcore.io.Streams;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class BaseDexClassLoaderTest {
    private static class Reporter implements BaseDexClassLoader.Reporter {
        public final Map<String, String> loadedDexMapping = new HashMap<String, String>();

        @Override
        public void report(Map<String, String> contextMap) {
            loadedDexMapping.putAll(contextMap);
        }

        void reset() {
            loadedDexMapping.clear();
        }
    }

    private ClassLoader pcl;
    private File jar;
    private File jar2;
    private Reporter reporter;

    // For resources that we will load in this test. We're re-using parent.jar and child.jar
    // from DelegateLastClassLoaderTest for convenience.
    private Map<String, File> resourcesMap;

    @Before
    public void setupResourcesMap() throws Exception {
        resourcesMap = ClassLoaderTestSupport.setupAndCopyResources(
                Arrays.asList("parent.jar", "child.jar"));
    }

    @After
    public void cleanupResourcesMap() throws Exception {
        ClassLoaderTestSupport.cleanUpResources(resourcesMap);
    }

    @Before
    public void extractTestJars() throws Exception {
        // Extract loading-test.jar from the resource.
        pcl = BaseDexClassLoaderTest.class.getClassLoader();
        jar = File.createTempFile("loading-test", ".jar");
        try (InputStream in = pcl.getResourceAsStream("dalvik/system/loading-test.jar");
             FileOutputStream out = new FileOutputStream(jar)) {
          Streams.copy(in, out);
        }
        jar2 = File.createTempFile("loading-test2", ".jar");
        try (InputStream in = pcl.getResourceAsStream("dalvik/system/loading-test2.jar");
             FileOutputStream out = new FileOutputStream(jar2)) {
          Streams.copy(in, out);
        }
    }

    @Before
    public void registerReporter() {
        reporter = new Reporter();
        BaseDexClassLoader.setReporter(reporter);
    }

    @After
    public void unregisterReporter() {
        BaseDexClassLoader.setReporter(null);
    }

    @After
    public void deleteTestJars() throws Exception {
        assertTrue(jar.delete());
        assertTrue(jar2.delete());
    }

    @Test
    public void testReporting() throws Exception {
        // Load the jar file using a PathClassLoader.
        BaseDexClassLoader cl1 = new PathClassLoader(jar.getPath(),
            ClassLoader.getSystemClassLoader());

        assertEquals(1, reporter.loadedDexMapping.size());

        String[] contexts = reporter.loadedDexMapping.get(jar.getPath()).split(";");
        assertEquals(2, contexts.length);
        // Verify the context for the loaded dex files.
        assertEquals("PCL[]", contexts[0]);
        // We cannot fully verify the context of the system class loader because its classpath
        // may vary based on system properties and whether or not we are in a test environment.
        assertTrue(contexts[1].startsWith("PCL["));
    }

    @Test
    public void testReportingUnknownLoader() throws Exception {
        // Add an unknown classloader between cl1 and the system
        ClassLoader unknownLoader = new ClassLoader(ClassLoader.getSystemClassLoader()) {};
        BaseDexClassLoader cl1 = new PathClassLoader(jar.getPath(), unknownLoader);

        // Verify the dex path gets reported, but with no class loader context due to the foreign
        // class loader.
        assertEquals(Map.of(jar.getPath(), "=UnsupportedClassLoaderContext="),
                reporter.loadedDexMapping);
    }

    @Test
    public void testNoReportingAfterResetting() throws Exception {
        BaseDexClassLoader cl1 = new PathClassLoader(jar.getPath(),
            ClassLoader.getSystemClassLoader());

        assertEquals(1, reporter.loadedDexMapping.size());

        String[] contexts = reporter.loadedDexMapping.get(jar.getPath()).split(";");
        assertEquals(2, contexts.length);
        // Verify the context for the loaded dex files.
        assertEquals("PCL[]", contexts[0]);
        // We cannot fully verify the context of the system class loader because its classpath
        // may vary based on system properties and whether or not we are in a test environment.
        assertTrue(contexts[1].startsWith("PCL["));

        // Check we don't report after the reporter is unregistered.
        unregisterReporter();
        reporter.reset();

        // Load the jar file using another PathClassLoader.
        BaseDexClassLoader cl2 = new PathClassLoader(jar.getPath(), pcl);

        // Verify nothing reported
        assertEquals(Map.<String, String>of(), reporter.loadedDexMapping);
    }

    @Test
    public void testReporting_multipleJars() throws Exception {
        // Load the jar file using a PathClassLoader.
        BaseDexClassLoader cl1 = new PathClassLoader(
            String.join(File.pathSeparator, jar.getPath(), jar2.getPath()),
            ClassLoader.getSystemClassLoader());

        assertEquals(2, reporter.loadedDexMapping.size());
        // Verify the first jar.
        String[] contexts = reporter.loadedDexMapping.get(jar.getPath()).split(";");
        assertEquals(2, contexts.length);
        // Verify the context for the loaded dex files.
        assertEquals("PCL[]", contexts[0]);
        // We cannot fully verify the context of the system class loader because its classpath
        // may vary based on system properties and whether or not we are in a test environment.
        assertTrue(contexts[1].startsWith("PCL["));

        // Verify the second jar.
        String[] contexts2 = reporter.loadedDexMapping.get(jar2.getPath()).split(";");
        assertEquals(2, contexts2.length);
        // Verify the context for the loaded dex files.
        assertEquals("PCL[" + jar.getPath() + "]", contexts2[0]);
        // We cannot fully verify the context of the system class loader because its classpath
        // may vary based on system properties and whether or not we are in a test environment.
        assertTrue(contexts2[1].startsWith("PCL["));
    }


    /**
      * Separates the system class loader context from the rest of the context.
      * Returns an array of 2 elements, where index 0 is the application class loader context
      * without the system class loader and index 0 is the system class loader context.
      */
    private String[] separateSystemClassLoaderContext(String context) {
        int clcSeparatorIndex = context.lastIndexOf(";");
        String jarContext = context.substring(0, clcSeparatorIndex);
        String systemClassLoaderContext = context.substring(clcSeparatorIndex + 1);
        return new String[] {jarContext, systemClassLoaderContext};
    }

    @Test
    public void testReporting_withSharedLibraries() throws Exception {
        final ClassLoader parent = ClassLoader.getSystemClassLoader();
        final ClassLoader sharedLoaders[] = new ClassLoader[] {
            new PathClassLoader(jar2.getPath(), /* librarySearchPath */ null, parent),
        };
        // Reset so we don't get load reports from creating the shared library CL
        reporter.reset();

        BaseDexClassLoader bdcl = new PathClassLoader(jar.getPath(), null, parent, sharedLoaders);

        assertEquals(1, reporter.loadedDexMapping.size());

        String[] contexts = separateSystemClassLoaderContext(
            reporter.loadedDexMapping.get(jar.getPath()));
        // We cannot fully verify the context of the system class loader because its classpath
        // may vary based on system properties and whether or not we are in a test environment.
        assertTrue(contexts[1].startsWith("PCL["));
        // Verify the context for the loaded dex files. The system class loader should be part
        // of the shared library class loader.
        assertEquals("PCL[]{PCL[" + jar2.getPath() + "];" + contexts[1] + "}",
                     contexts[0]);
    }

    @Test
    public void testReporting_multipleJars_withSharedLibraries() throws Exception {
        final ClassLoader parent = ClassLoader.getSystemClassLoader();
        final String sharedJarPath = resourcesMap.get("parent.jar").getAbsolutePath();
        final ClassLoader sharedLoaders[] = new ClassLoader[] {
            new PathClassLoader(sharedJarPath, /* librarySearchPath */ null, parent),
        };
        // Reset so we don't get load reports from creating the shared library CL
        reporter.reset();

        BaseDexClassLoader bdcl = new PathClassLoader(
                String.join(File.pathSeparator, jar.getPath(), jar2.getPath()),
                null, parent, sharedLoaders);

        assertEquals(2, reporter.loadedDexMapping.size());


        // Verify the first jar.
        String[] contexts = separateSystemClassLoaderContext(
            reporter.loadedDexMapping.get(jar.getPath()));
        String contextSuffix = "{PCL[" + sharedJarPath + "];" + contexts[1] + "}";

        // We cannot fully verify the context of the system class loader because its classpath
        // may vary based on system properties and whether or not we are in a test environment.
        assertTrue(contexts[1].startsWith("PCL["));
        // Verify the context for the loaded dex files.
        assertEquals("PCL[]" + contextSuffix, contexts[0]);
        // We cannot fully verify the context of the system class loader because its classpath
        // may vary based on system properties and whether or not we are in a test environment.
        assertTrue(contexts[1].startsWith("PCL["));

        // Verify the second jar.
        String[] contexts2 = separateSystemClassLoaderContext(
            reporter.loadedDexMapping.get(jar2.getPath()));
        String contextSuffix2 = "{PCL[" + sharedJarPath + "];" + contexts2[1] + "}";

        // Verify the context for the loaded dex files.
        assertEquals("PCL[" + jar.getPath() + "]" + contextSuffix2, contexts2[0]);
        // We cannot fully verify the context of the system class loader because its classpath
        // may vary based on system properties and whether or not we are in a test environment.
        assertTrue(contexts2[1].startsWith("PCL[")) ;
    }

    @Test
    public void testReporting_emptyPath() throws Exception {
        BaseDexClassLoader cl1 = new PathClassLoader("", ClassLoader.getSystemClassLoader());
        assertEquals(Map.<String, String>of(), reporter.loadedDexMapping);
    }

    /* package */ static List<String> readResources(ClassLoader cl, String resourceName)
            throws Exception {
        Enumeration<URL> resources = cl.getResources(resourceName);

        List<String> contents = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();

            try (InputStream is = url.openStream()) {
                byte[] bytes = Streams.readFully(is);
                contents.add(new String(bytes, StandardCharsets.UTF_8));
            }
        }

        return contents;
    }

    /* package */ static String readResource(ClassLoader cl, String resourceName) throws Exception {
        InputStream in = cl.getResourceAsStream(resourceName);
        if (in == null) {
            return null;
        }

        byte[] contents = Streams.readFully(in);
        return new String(contents, StandardCharsets.UTF_8);
    }

    private void checkResources(ClassLoader loader) throws Exception {
        List<String> resources = readResources(loader, "resource.txt");

        assertEquals(2, resources.size());
        assertTrue(resources.contains("parent"));
        assertTrue(resources.contains("child"));

        resources = readResources(loader, "resource2.txt");

        assertEquals(1, resources.size());
        assertEquals("parent2", resources.get(0));
    }

    @Test
    public void testGetResourceSharedLibraries1() throws Exception {
        File parentPath = resourcesMap.get("parent.jar");
        File childPath = resourcesMap.get("child.jar");
        assertTrue(parentPath != null);
        assertTrue(childPath != null);

        ClassLoader parent = Object.class.getClassLoader();

        ClassLoader[] sharedLibraries = {
          new PathClassLoader(parentPath.getAbsolutePath(), null, parent),
          new PathClassLoader(childPath.getAbsolutePath(), null, parent),
        };

        // PCL[]{PCL[parent.jar]#PCL[child.jar]}
        ClassLoader loader = new PathClassLoader("", null, parent, sharedLibraries);
        assertEquals("parent", readResource(loader, "resource.txt"));
        checkResources(loader);

        // DLC[]{PCL[parent.jar]#PCL[child.jar]}
        loader = new DelegateLastClassLoader("", null, parent, sharedLibraries);
        assertEquals("parent", readResource(loader, "resource.txt"));
        checkResources(loader);
    }

    @Test
    public void testGetResourceSharedLibraries2() throws Exception {
        File parentPath = resourcesMap.get("parent.jar");
        File childPath = resourcesMap.get("child.jar");
        assertTrue(parentPath != null);
        assertTrue(childPath != null);

        ClassLoader parent = Object.class.getClassLoader();

        ClassLoader[] sharedLibraries = {
          new PathClassLoader(childPath.getAbsolutePath(), null, parent),
          new PathClassLoader(parentPath.getAbsolutePath(), null, parent),
        };

        // PCL[]{PCL[child.jar]#PCL[parent.jar]}
        ClassLoader loader = new PathClassLoader("", null, parent, sharedLibraries);
        assertEquals("child", readResource(loader, "resource.txt"));
        checkResources(loader);

        // DLC[]{PCL[child.jar]#PCL[parent.jar]}
        loader = new DelegateLastClassLoader("", null, parent, sharedLibraries);
        assertEquals("child", readResource(loader, "resource.txt"));
        checkResources(loader);
    }

    @Test
    public void testGetResourceSharedLibraries3() throws Exception {
        File parentPath = resourcesMap.get("parent.jar");
        File childPath = resourcesMap.get("child.jar");
        assertTrue(parentPath != null);
        assertTrue(childPath != null);

        ClassLoader parent = Object.class.getClassLoader();

        ClassLoader[] sharedLibraryLevel2 = {
          new PathClassLoader(parentPath.getAbsolutePath(), null, parent),
        };

        ClassLoader[] sharedLibraryLevel1 = {
          new PathClassLoader(childPath.getAbsolutePath(), null, parent, sharedLibraryLevel2),
        };

        // PCL[]{PCL[child.jar]{PCL[parent.jar]}}
        ClassLoader loader = new PathClassLoader("", null, parent, sharedLibraryLevel1);
        assertEquals("parent", readResource(loader, "resource.txt"));
        checkResources(loader);

        // DLC[]{PCL[child.jar]{PCL[parent.jar]}}
        loader = new DelegateLastClassLoader("", null, parent, sharedLibraryLevel1);
        assertEquals("parent", readResource(loader, "resource.txt"));
        checkResources(loader);
    }

    @Test
    public void testGetResourceSharedLibraries4() throws Exception {
        File parentPath = resourcesMap.get("parent.jar");
        File childPath = resourcesMap.get("child.jar");
        assertTrue(parentPath != null);
        assertTrue(childPath != null);

        ClassLoader parent = Object.class.getClassLoader();

        ClassLoader[] sharedLibraryLevel2 = {
          new PathClassLoader(childPath.getAbsolutePath(), null, parent),
        };

        ClassLoader[] sharedLibraryLevel1 = {
          new PathClassLoader(parentPath.getAbsolutePath(), null, parent, sharedLibraryLevel2),
        };

        // PCL[]{PCL[parent.jar]{PCL[child.jar]}}
        ClassLoader loader = new PathClassLoader("", null, parent, sharedLibraryLevel1);
        assertEquals("child", readResource(loader, "resource.txt"));
        checkResources(loader);

        // DLC[]{PCL[parent.jar]{PCL[child.jar]}}
        loader = new DelegateLastClassLoader("", null, parent, sharedLibraryLevel1);
        assertEquals("child", readResource(loader, "resource.txt"));
        checkResources(loader);
    }

    @Test
    public void testGetResourceSharedLibraries5() throws Exception {
        File parentPath = resourcesMap.get("parent.jar");
        File childPath = resourcesMap.get("child.jar");
        assertTrue(parentPath != null);
        assertTrue(childPath != null);

        ClassLoader parentParent = Object.class.getClassLoader();
        ClassLoader parent = new PathClassLoader(parentPath.getAbsolutePath(), null, parentParent);

        ClassLoader[] sharedLibrary = {
          new PathClassLoader(childPath.getAbsolutePath(), null, parentParent),
        };

        // PCL[]{PCL[child.jar]};PCL[parent.jar]
        ClassLoader pathLoader = new PathClassLoader("", null, parent, sharedLibrary);

        // Check that the parent was queried first.
        assertEquals("parent", readResource(pathLoader, "resource.txt"));

        // DLC[]{PCL[child.jar]};PCL[parent.jar]
        ClassLoader delegateLast = new DelegateLastClassLoader("", null, parent, sharedLibrary);

        // Check that the shared library was queried first.
        assertEquals("child", readResource(delegateLast, "resource.txt"));

    }

    @Test
    public void testAddDexPath() throws Exception {
        BaseDexClassLoader bdcl = new PathClassLoader(jar.getPath(),
            ClassLoader.getSystemClassLoader());

        Class test1Class = bdcl.loadClass("test.Test1");
        Method testMethod = test1Class.getMethod("test", (Class[]) null);
        String testResult = (String) testMethod.invoke(null, (Object[]) null);
        assertEquals("blort", testResult);

        // Just for completeness sake, prove that we were able to load
        // the class only after addDexPath was called.
        try {
          bdcl.loadClass("test2.Target2");
          fail();
        } catch (ClassNotFoundException expected) {
        }

        bdcl.addDexPath(jar2.getPath());

        Class target2Class = bdcl.loadClass("test2.Target2");
        Method frotzMethod = target2Class.getMethod("frotz", (Class[]) null);
        String frotzResult = (String) frotzMethod.invoke(null, (Object[]) null);
        assertEquals("frotz", frotzResult);
    }
}
