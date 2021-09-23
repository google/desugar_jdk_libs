/*
 * Copyright (C) 2015 The Android Open Source Project
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

package libcore.libcore.io;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.UnknownServiceException;
import java.util.Arrays;

import libcore.io.ClassPathURLStreamHandler;
import libcore.io.Streams;

import tests.support.resource.Support_Resources;

public class ClassPathURLStreamHandlerTest extends TestCase {

    // A well formed jar file with 6 entries.
    private static final String JAR = "ClassPathURLStreamHandlerTest.jar";
    private static final String ENTRY_IN_ROOT = "root.txt";
    private static final String DIR_ENTRY_WITHOUT_SLASH = "foo";
    private static final String DIR_ENTRY_WITH_SLASH = DIR_ENTRY_WITHOUT_SLASH + "/";
    private static final String ENTRY_IN_SUBDIR = "foo/bar/baz.txt";
    private static final String ENTRY_STORED = "stored_file.txt";
    private static final String ENTRY_WITH_SPACES_ENCODED = "file%20with%20spaces.txt";
    private static final String ENTRY_WITH_SPACES_UNENCODED = "file with spaces.txt";
    private static final String ENTRY_THAT_NEEDS_ESCAPING = "file_with_percent20_%20.txt";
    private static final String ENTRY_THAT_NEEDS_ESCAPING_ENCODED = "file_with_percent20_%2520.txt";
    private static final String ENTRY_WITH_RELATIVE_PATH = "foo/../foo/bar/baz.txt";
    private static final String MISSING_ENTRY = "Wrong.resource";

    private File jarFile;

    @Override
    protected void setUp() throws Exception {
        File resources = Support_Resources.createTempFolder().getCanonicalFile();
        Support_Resources.copyFile(resources, null, JAR);
        jarFile = new File(resources, JAR);
    }

    public void testConstructor() throws Exception {
        try {
            ClassPathURLStreamHandler streamHandler = new ClassPathURLStreamHandler("Missing.file");
            fail("Should throw IOException");
        } catch (IOException expected) {
        }

        String fileName = jarFile.getPath();
        ClassPathURLStreamHandler streamHandler = new ClassPathURLStreamHandler(fileName);
        streamHandler.close();
    }

    public void testGetEntryOrNull() throws Exception {
        String fileName = jarFile.getPath();
        ClassPathURLStreamHandler streamHandler = new ClassPathURLStreamHandler(fileName);

        checkGetEntryUrlOrNull(streamHandler, ENTRY_IN_ROOT, ENTRY_IN_ROOT);
        checkGetEntryUrlOrNull(streamHandler, ENTRY_IN_SUBDIR, ENTRY_IN_SUBDIR);
        checkGetEntryUrlOrNull(streamHandler, ENTRY_WITH_SPACES_UNENCODED,
                ENTRY_WITH_SPACES_ENCODED);
        checkGetEntryUrlOrNull(streamHandler, ENTRY_THAT_NEEDS_ESCAPING,
                ENTRY_THAT_NEEDS_ESCAPING_ENCODED);

        // getEntryOrNull() performs a lookup with and without trailing slash to handle directories.
        // http://b/22527772
        checkGetEntryUrlOrNull(streamHandler, DIR_ENTRY_WITHOUT_SLASH,
                DIR_ENTRY_WITHOUT_SLASH);
        checkGetEntryUrlOrNull(streamHandler, DIR_ENTRY_WITH_SLASH, DIR_ENTRY_WITH_SLASH);

        assertNull(streamHandler.getEntryUrlOrNull(MISSING_ENTRY));
        assertNull(streamHandler.getEntryUrlOrNull("/" + ENTRY_IN_ROOT));
        assertNull(streamHandler.getEntryUrlOrNull("/" + ENTRY_IN_SUBDIR));
        assertNull(streamHandler.getEntryUrlOrNull(ENTRY_WITH_SPACES_ENCODED));
        assertNull(streamHandler.getEntryUrlOrNull(ENTRY_WITH_RELATIVE_PATH));
        assertNull(streamHandler.getEntryUrlOrNull("/" + DIR_ENTRY_WITHOUT_SLASH));
        assertNull(streamHandler.getEntryUrlOrNull("/" + DIR_ENTRY_WITH_SLASH));
        streamHandler.close();
    }

    /**
     * Check that the call to {@link ClassPathURLStreamHandler#getEntryUrlOrNull(String)} works as
     * expected.
     */
    private void checkGetEntryUrlOrNull(ClassPathURLStreamHandler streamHandler,
            String entryName, String expectedJarRelativeURI) throws IOException {

        String fileName = jarFile.getPath();
        URL urlOrNull = streamHandler.getEntryUrlOrNull(entryName);
        assertNotNull("URL was unexpectedly null for " + entryName, urlOrNull);
        assertEquals("jar:file:" + fileName + "!/" + expectedJarRelativeURI,
                urlOrNull.toExternalForm());

        // Make sure that the resource could be opened and the correct contents returned, i.e. the
        // same as those read from the jar file directly.
        assertOpenConnectionOk(jarFile, expectedJarRelativeURI, streamHandler);
    }

    public void testIsEntryStored() throws IOException {
        String fileName = jarFile.getPath();
        ClassPathURLStreamHandler streamHandler = new ClassPathURLStreamHandler(fileName);

        assertFalse(streamHandler.isEntryStored("this/file/does/not/exist.txt"));
        // This one is compressed
        assertFalse(streamHandler.isEntryStored(ENTRY_IN_SUBDIR));
        assertTrue(streamHandler.isEntryStored(ENTRY_STORED));

        assertTrue(streamHandler.isEntryStored(DIR_ENTRY_WITHOUT_SLASH));

        // Directory entries are just stored, empty entries with "/" on the end of the name, so
        // "true".
        assertTrue(streamHandler.isEntryStored(DIR_ENTRY_WITH_SLASH));
    }

    public void testOpenConnection() throws Exception {
        String fileName = jarFile.getPath();
        ClassPathURLStreamHandler streamHandler = new ClassPathURLStreamHandler(fileName);

        assertOpenConnectionOk(jarFile, ENTRY_IN_ROOT, streamHandler);
        assertOpenConnectionOk(jarFile, ENTRY_IN_SUBDIR, streamHandler);
        assertOpenConnectionOk(jarFile, ENTRY_WITH_SPACES_ENCODED, streamHandler);
        assertOpenConnectionOk(jarFile, ENTRY_WITH_SPACES_UNENCODED, streamHandler);
        assertOpenConnectionOk(jarFile, DIR_ENTRY_WITH_SLASH, streamHandler);
        assertOpenConnectionOk(jarFile, DIR_ENTRY_WITHOUT_SLASH, streamHandler);

        assertOpenConnectionConnectFails(jarFile, ENTRY_WITH_RELATIVE_PATH, streamHandler);
        assertOpenConnectionConnectFails(jarFile, MISSING_ENTRY, streamHandler);
        assertOpenConnectionConnectFails(jarFile, ENTRY_THAT_NEEDS_ESCAPING, streamHandler);

        streamHandler.close();
    }

    private void assertOpenConnectionConnectFails(
            File jarFile, String entryName, URLStreamHandler streamHandler) throws IOException {

        URL standardUrl = createJarUrl(jarFile, entryName, null /* use default stream handler */);
        try {
            standardUrl.openConnection().connect();
            fail();
        } catch (FileNotFoundException expected) {
        }

        URL actualUrl = createJarUrl(jarFile, entryName, streamHandler);
        try {
            actualUrl.openConnection().connect();
            fail();
        } catch (FileNotFoundException expected) {
        }
    }

    private static void assertOpenConnectionOk(File jarFile, String entryName,
            ClassPathURLStreamHandler streamHandler) throws IOException {
        URL standardUrl = createJarUrl(jarFile, entryName, null /* use default stream handler */);
        URLConnection standardUrlConnection = standardUrl.openConnection();
        assertNotNull(standardUrlConnection);

        URL actualUrl = createJarUrl(jarFile, entryName, streamHandler);
        URLConnection actualUrlConnection = actualUrl.openConnection();
        assertNotNull(actualUrlConnection);
        assertBehaviorSame(standardUrlConnection, actualUrlConnection);
    }

    private static void assertBehaviorSame(URLConnection standardURLConnection,
            URLConnection actualUrlConnection) throws IOException {

        JarURLConnection standardJarUrlConnection = (JarURLConnection) standardURLConnection;
        JarURLConnection actualJarUrlConnection = (JarURLConnection) actualUrlConnection;

        byte[] actualBytes = Streams.readFully(actualJarUrlConnection.getInputStream());
        byte[] standardBytes = Streams.readFully(standardJarUrlConnection.getInputStream());
        assertEquals(Arrays.toString(standardBytes), Arrays.toString(actualBytes));

        try {
            actualJarUrlConnection.getOutputStream();
            fail();
        } catch (UnknownServiceException expected) {
        }

        assertEquals(
                standardJarUrlConnection.getJarFile().getName(),
                actualJarUrlConnection.getJarFile().getName());

        assertEquals(
                standardJarUrlConnection.getJarEntry().getName(),
                actualJarUrlConnection.getJarEntry().getName());

        assertEquals(
                standardJarUrlConnection.getJarFileURL(),
                actualJarUrlConnection.getJarFileURL());

        assertEquals(
                standardJarUrlConnection.getContentType(),
                actualJarUrlConnection.getContentType());

        assertEquals(
                standardJarUrlConnection.getContentLength(),
                actualJarUrlConnection.getContentLength());
    }

    private static URL createJarUrl(File jarFile, String entryName, URLStreamHandler streamHandler)
            throws MalformedURLException {
        return new URL("jar", null, -1, jarFile.toURI() + "!/" + entryName, streamHandler);
    }

}
