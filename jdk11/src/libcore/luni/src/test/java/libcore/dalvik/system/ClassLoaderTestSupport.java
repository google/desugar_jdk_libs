/*
 * Copyright (C) 2017 The Android Open Source Project
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

package libcore.dalvik.system;

import libcore.io.Streams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

class ClassLoaderTestSupport {
    private static final String PACKAGE_PATH = "dalvik/system/";

    static Map<String, File> setupAndCopyResources(List<String> resources) throws Exception {
        File srcDir = File.createTempFile("src", "");
        assertTrue(srcDir.delete());
        assertTrue(srcDir.mkdirs());

        HashMap<String, File> resourcesMap = new HashMap<>();
        resourcesMap.put(null, srcDir);

        for (String resource: resources) {
            File resourceFile = new File(srcDir, resource);
            copyResource(resource, resourceFile);

            resourcesMap.put(resource, resourceFile);
        }

        return resourcesMap;
    }

    static void cleanUpResources(Map<String, File> resources) {
        cleanUpDir(resources.get(null));
    }

    private static void cleanUpDir(File dir) {
        if (dir == null || !dir.isDirectory()) {
            return;
        }

        // The runtime may create files in the background. Loop until we remove all such files.
        while (!dir.delete()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    cleanUpDir(file);
                } else {
                    assertTrue(file.delete());
                }
            }
        }
    }

    /**
     * Copy a resource in the package directory to the indicated
     * target file.
     */
    private static void copyResource(String resourceName,
                                     File destination) throws IOException {
        ClassLoader loader = DexClassLoaderTest.class.getClassLoader();
        assertFalse(destination.exists());
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

}
