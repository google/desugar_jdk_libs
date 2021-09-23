/*
 * Copyright (C) 2009 The Android Open Source Project
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
package tests.support;

import java.io.File;
import java.net.URL;

import dalvik.system.DexClassLoader;

/**
 * Implementation for Dalvik. Uses the DexClassLoader, so we can write
 * temporary DEX files to a special directory. We don't want to spoil the
 * system's DEX cache with our files. Also, we might not have write access
 * to the system's DEX cache at all (which is the case when we're running
 * CTS).
 */
class Support_ClassLoaderDalvik extends Support_ClassLoader {

    private static File tmp;

    static {
        tmp = new File(System.getProperty("java.io.tmpdir"), "dex-cache");
        tmp.mkdirs();
    }

    @Override
    public ClassLoader getClassLoader(URL url, ClassLoader parent) {
        return new DexClassLoader(url.getPath(), tmp.getAbsolutePath(),
                null, parent);
    }
}
