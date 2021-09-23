/* Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.harmony.tests.java.util.prefs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;
import junit.framework.TestCase;

import libcore.testing.io.TestIoUtils;

/**
 *
 */
public class PreferencesTest extends TestCase {

    private static final String PREFS =
            "<!DOCTYPE preferences SYSTEM \"http://java.sun.com/dtd/preferences.dtd\">" +
                    "<preferences>" +
                      "<root type=\"user\">" +
                        "<map></map>" +
                      "</root>" +
                    "</preferences>";


    private MockInputStream stream ;
    private InputStream in;
    private PreferencesFactory defaultFactory;
    private File backendDir;

    @Override
    protected void setUp() throws Exception {
        backendDir = TestIoUtils.createTemporaryDirectory("OldAbstractPreferencesTest");
        defaultFactory = Preferences.setPreferencesFactory(
                new AbstractPreferencesTest.TestPreferencesFactory(backendDir.getAbsolutePath()));

        in = new ByteArrayInputStream(PREFS.getBytes(StandardCharsets.US_ASCII));
        stream = new MockInputStream(in);
    }

    @Override
    protected void tearDown() throws Exception {
        Preferences.setPreferencesFactory(defaultFactory);
        stream.close();
    }

    public void testSystemNodeForPackage() throws Exception {
        Preferences p = Preferences.systemNodeForPackage(Object.class);

        assertEquals("/java/lang", p.absolutePath());
        assertTrue(p instanceof AbstractPreferences);
        Preferences root = Preferences.systemRoot();
        Preferences parent = root.node("java");
        assertSame(parent, p.parent());
        assertFalse(p.isUserNode());
        assertEquals("lang", p.name());
        assertEquals("System Preference Node: " + p.absolutePath(), p
                .toString());

        assertEquals(0, p.childrenNames().length);
        assertEquals(0, p.keys().length);
        parent.removeNode();
        try {
            Preferences.userNodeForPackage(null);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    public void testSystemRoot() throws BackingStoreException {
        Preferences p = Preferences.systemRoot();
        assertTrue(p instanceof AbstractPreferences);
        assertEquals("/", p.absolutePath());
        assertSame(null, p.parent());
        assertFalse(p.isUserNode());
        assertEquals("", p.name());
        assertEquals("System Preference Node: " + p.absolutePath(), p
                .toString());
    }

    public void testConsts() {
        assertEquals(80, Preferences.MAX_KEY_LENGTH);
        assertEquals(80, Preferences.MAX_NAME_LENGTH);
        assertEquals(8192, Preferences.MAX_VALUE_LENGTH);
    }

    public void testUserNodeForPackage() throws BackingStoreException {
        Preferences p = Preferences.userNodeForPackage(Object.class);
        assertEquals("/java/lang", p.absolutePath());
        assertTrue(p instanceof AbstractPreferences);
        Preferences root = Preferences.userRoot();
        Preferences parent = root.node("java");
        assertSame(parent, p.parent());
        assertTrue(p.isUserNode());
        assertEquals("lang", p.name());
        assertEquals("User Preference Node: " + p.absolutePath(), p.toString());
        assertEquals(0, p.childrenNames().length);
        assertEquals(0, p.keys().length);

        try {
            Preferences.userNodeForPackage(null);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    public void testUserRoot() throws BackingStoreException {
        Preferences p = Preferences.userRoot();
        assertTrue(p instanceof AbstractPreferences);
        assertEquals("/", p.absolutePath());
        assertSame(null, p.parent());
        assertTrue(p.isUserNode());
        assertEquals("", p.name());
        assertEquals("User Preference Node: " + p.absolutePath(), p.toString());
    }

    public void testImportPreferences() throws Exception {
        Preferences prefs = null;
        try {
            prefs = Preferences.userNodeForPackage(PreferencesTest.class);

            prefs.put("prefskey", "oldvalue");
            prefs.put("prefskey2", "oldvalue2");
            in = PreferencesTest.class
                    .getResourceAsStream("/resources/prefs/java/util/prefs/userprefs.xml");
            Preferences.importPreferences(in);

            prefs = Preferences.userNodeForPackage(PreferencesTest.class);
            assertEquals(1, prefs.childrenNames().length);
            assertTrue(prefs.nodeExists("mock/child/grandson"));
            assertEquals("newvalue", prefs.get("prefskey", null));
            assertEquals("oldvalue2", prefs.get("prefskey2", null));
            assertEquals("newvalue3", prefs.get("prefskey3", null));

            in = PreferencesTest.class.getResourceAsStream(
                    "/prefs/java/util/prefs/userprefs-badform.xml");
            try {
                Preferences.importPreferences(in);
                fail("should throw InvalidPreferencesFormatException");
            } catch (InvalidPreferencesFormatException expected) {
            }
        } finally {
            try {
                prefs = Preferences.userNodeForPackage(PreferencesTest.class);
                prefs.removeNode();
            } catch (Exception e) {
            }
        }
    }

    public void testImportPreferencesException() throws Exception {
        try {
            Preferences.importPreferences(null);
            fail("should throw InvalidPreferencesFormatException");
        } catch (InvalidPreferencesFormatException expected) {
        }

        byte[] source = new byte[0];
        InputStream in = new ByteArrayInputStream(source);
        try {
            Preferences.importPreferences(in);
            fail("should throw InvalidPreferencesFormatException");
        } catch (InvalidPreferencesFormatException expected) {
        }

        stream.setResult(MockInputStream.exception);
        try {
            Preferences.importPreferences(stream);
            fail("should throw IOException");
        } catch (IOException expected) {
        }

        stream.setResult(MockInputStream.runtimeException);
        try {
            Preferences.importPreferences(stream);
            fail("should throw RuntimeException");
        } catch (RuntimeException expected) {
        }
    }

    static class MockInputStream extends InputStream {

        static final int normal = 0;

        static final int exception = 1;

        static final int runtimeException = 2;

        int result = normal;

        InputStream wrapper;

        public void setResult(int i) {
            result = i;
        }

        private void checkException() throws IOException {
            switch (result) {
                case normal:
                    return;
                case exception:
                    throw new IOException("test");
                case runtimeException:
                    throw new RuntimeException("test");
            }
        }

        public MockInputStream(InputStream in) {
            wrapper = in;
        }

        @Override
        public int read() throws IOException {
            checkException();
            return wrapper.read();
        }
    }

    static class MockPreferences extends Preferences {

        public MockPreferences() {
            super();
        }

        @Override
        public String absolutePath() {
            return null;
        }

        @Override
        public String[] childrenNames() throws BackingStoreException {
            return null;
        }

        @Override
        public void clear() throws BackingStoreException {
        }

        @Override
        public void exportNode(OutputStream ostream) throws IOException,
                BackingStoreException {
        }

        @Override
        public void exportSubtree(OutputStream ostream) throws IOException,
                BackingStoreException {
        }

        @Override
        public void flush() throws BackingStoreException {
        }

        @Override
        public String get(String key, String deflt) {
            return null;
        }

        @Override
        public boolean getBoolean(String key, boolean deflt) {
            return false;
        }

        @Override
        public byte[] getByteArray(String key, byte[] deflt) {
            return null;
        }

        @Override
        public double getDouble(String key, double deflt) {
            return 0;
        }

        @Override
        public float getFloat(String key, float deflt) {
            return 0;
        }

        @Override
        public int getInt(String key, int deflt) {
            return 0;
        }

        @Override
        public long getLong(String key, long deflt) {
            return 0;
        }

        @Override
        public boolean isUserNode() {
            return false;
        }

        @Override
        public String[] keys() throws BackingStoreException {
            return null;
        }

        @Override
        public String name() {
            return null;
        }

        @Override
        public Preferences node(String name) {
            return null;
        }

        @Override
        public boolean nodeExists(String name) throws BackingStoreException {
            return false;
        }

        @Override
        public Preferences parent() {
            return null;
        }

        @Override
        public void put(String key, String value) {

        }

        @Override
        public void putBoolean(String key, boolean value) {

        }

        @Override
        public void putByteArray(String key, byte[] value) {

        }

        @Override
        public void putDouble(String key, double value) {

        }

        @Override
        public void putFloat(String key, float value) {

        }

        @Override
        public void putInt(String key, int value) {

        }

        @Override
        public void putLong(String key, long value) {

        }

        @Override
        public void remove(String key) {

        }

        @Override
        public void removeNode() throws BackingStoreException {

        }

        @Override
        public void addNodeChangeListener(NodeChangeListener ncl) {

        }

        @Override
        public void addPreferenceChangeListener(PreferenceChangeListener pcl) {

        }

        @Override
        public void removeNodeChangeListener(NodeChangeListener ncl) {

        }

        @Override
        public void removePreferenceChangeListener(PreferenceChangeListener pcl) {

        }

        @Override
        public void sync() throws BackingStoreException {

        }

        @Override
        public String toString() {
            return null;
        }

    }

    // b/27645233
    public void testReadingFromBackendCache() throws Exception {
        // Create a preferences filesystem backend storage file.
        // It emulates a situation where we saved some data in the past,
        // restarted the application and we're trying to read it.
        //
        // We need a package without the existing backing file (so a package that
        // wasn't used in this test case), java.io is a good candidate.
        File backendFile = new File(backendDir, "/user/java/io/prefs.xml");
        InputStream inputData = PreferencesTest.class
            .getResourceAsStream("/resources/prefs/java/util/prefs/backendread.xml");
        assertTrue(new File(backendDir, "/user/java/io").mkdirs());
        assertTrue(backendFile.createNewFile());

        // Copy the example content (one test=test1 entry) to a storage file
        FileOutputStream fos = new FileOutputStream(backendFile);
        try {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputData.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        } finally {
            fos.close();
            inputData.close();
        }

        Preferences prefs = Preferences.userNodeForPackage(OutputStream.class);
        // Any exception from reading the file will be swallowed and ignored,
        // only result we will see is a lack of requested key-value pair.
        assertEquals("test1", prefs.get("test",""));
    }


}
