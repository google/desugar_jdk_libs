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

package libcore.testing.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class TestIoUtils {
    private final static Random random = new Random();

    private TestIoUtils() {}

    /**
     * Returns the contents of 'path' as a string. The contents are assumed to be UTF-8.
     */
    public static String readFileAsString(String absolutePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(absolutePath)), StandardCharsets.UTF_8);
    }

    /**
     * Creates a unique new temporary directory under "java.io.tmpdir".
     */
    public static File createTemporaryDirectory(String prefix) {
        while (true) {
            String candidateName = prefix + nextRandomInt();
            File result = new File(System.getProperty("java.io.tmpdir"), candidateName);
            if (result.mkdir()) {
                return result;
            }
        }
    }

    /**
     * Closes 'closeable', ignoring any checked exceptions. Does nothing if 'closeable' is null.
     */
    public static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }

    private synchronized static int nextRandomInt() {
        return random.nextInt();
    }
}
