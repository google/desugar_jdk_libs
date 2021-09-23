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

package libcore.java.lang;

import junit.framework.TestCase;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Formatter;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import android.system.Os;

public class SystemTest extends TestCase {

    public void testOsName() throws Exception {
        // Ensure os.name always matches the underlying OS.
        String sysname = Os.uname().sysname;
        String property = System.getProperty("os.name");
        assertEquals(sysname, property);
    }

    public void testLineSeparator() throws Exception {
        try {
            // Before Java 7, the small number of classes that wanted the line separator would
            // use System.getProperty. Now they should use System.lineSeparator instead, and the
            // "line.separator" property has no effect after the VM has started.

            // Test that System.lineSeparator is not changed when the corresponding
            // system property is changed.
            assertEquals("\n", System.lineSeparator());
            System.setProperty("line.separator", "poop");
            assertEquals("\n", System.lineSeparator());

            // java.io.BufferedWriter --- uses System.lineSeparator on Android but not on RI.
            StringWriter sw = new StringWriter();
            BufferedWriter bw = new BufferedWriter(sw);
            bw.newLine();
            bw.flush();
            assertEquals(System.lineSeparator(), sw.toString());

            // java.io.PrintStream --- uses System.lineSeparator on Android but not on RI.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new PrintStream(baos).println();
            assertEquals(System.lineSeparator(), new String(baos.toByteArray(), "UTF-8"));

            // java.io.PrintWriter --- uses System.lineSeparator on Android but not on RI.
            sw = new StringWriter();
            new PrintWriter(sw).println();
            assertEquals(System.lineSeparator(), sw.toString());

            // java.util.Formatter --- uses System.lineSeparator on both.
            assertEquals(System.lineSeparator(), new Formatter().format("%n").toString());
        } finally {
            System.setProperty("line.separator", "\n");
        }
    }

    public void testArrayCopyTargetNotArray() {
        try {
            System.arraycopy(new char[5], 0, "Hello", 0, 3);
            fail();
        } catch (ArrayStoreException e) {
            assertEquals("destination of type java.lang.String is not an array", e.getMessage());
        }
    }

    public void testArrayCopySourceNotArray() {
        try {
            System.arraycopy("Hello", 0, new char[5], 0, 3);
            fail();
        } catch (ArrayStoreException e) {
            assertEquals("source of type java.lang.String is not an array", e.getMessage());
        }
    }

    public void testArrayCopyArrayTypeMismatch() {
        try {
            System.arraycopy(new char[5], 0, new Object[5], 0, 3);
            fail();
        } catch (ArrayStoreException e) {
            assertEquals("Incompatible types: src=char[], dst=java.lang.Object[]", e.getMessage());
        }
    }

    public void testArrayCopyElementTypeMismatch() {
        try {
            System.arraycopy(new Object[] { null, 5, "hello" }, 0,
                    new Integer[] { 1, 2, 3, null, null }, 0, 3);
            fail();
        } catch (ArrayStoreException e) {
            assertEquals("source[2] of type java.lang.String cannot be stored in destination array of type java.lang.Integer[]", e.getMessage());
        }
    }

    public void testArrayCopyNull() {
        try {
            System.arraycopy(null, 0, new char[5], 0, 3);
            fail();
        } catch (NullPointerException e) {
            assertEquals("src == null", e.getMessage());
        }
        try {
            System.arraycopy(new char[5], 0, null, 0, 3);
            fail();
        } catch (NullPointerException e) {
            assertEquals("dst == null", e.getMessage());
        }
    }

    /**
     * System.arraycopy() must never copy objects into arrays that can't store
     * them. We've had bugs where type checks and copying were done separately
     * and racy code could defeat the type checks. http://b/5247258
     */
    public void testArrayCopyConcurrentModification() {
        final AtomicBoolean done = new AtomicBoolean();

        final Object[] source = new Object[512 * 1024];
        String[] target = new String[512 * 1024];

        new Thread() {
            @Override public void run() {
                // the last array element alternates between being a Thread and being null. When
                // it's a Thread it isn't safe for arrayCopy; when its null it is!
                while (!done.get()) {
                    source[source.length - 1] = this;
                    source[source.length - 1] = null;
                }
            }
        }.start();

        for (int i = 0; i < 2048; i++) {
            try {
                System.arraycopy(source, 0, target, 0, source.length);
                assertNull(target[source.length - 1]); // make sure the wrong type didn't sneak in
            } catch (ArrayStoreException ignored) {
            }
        }

        done.set(true);
    }

    public void testSystemProperties_immutable() {
        // Android-note: The RI does not have a concept of immutable properties.

        // user.dir is an immutable property
        String userDir = System.getProperty("user.dir");
        assertNotNull(userDir);
        System.setProperty("user.dir", "not poop");
        assertEquals(userDir, System.getProperty("user.dir"));

        System.getProperties().setProperty("user.dir", "hmmph");
        assertEquals(userDir, System.getProperty("user.dir"));

        System.getProperties().clear();
        assertEquals(userDir, System.getProperty("user.dir"));

        Properties p = new Properties();
        p.setProperty("user.dir", "meh");
        System.setProperties(p);

        assertEquals(userDir, System.getProperty("user.dir"));
    }

    public void testSystemProperties_mutable() {
        // We allow "java.io.tmpdir" and "user.home" to be changed however
        // we can't test for "java.io.tmpdir" consistently across test runners because
        // it will be immutable if set on the dalvikvm command line "-Djava.io.tmpdir="
        // like vogar does.
        String oldUserHome = System.getProperty("user.home");
        try {
            System.setProperty("user.home", "/user/home");
            assertEquals("/user/home", System.getProperty("user.home"));
        } finally {
            System.setProperty("user.home", oldUserHome);
        }
    }

    public void testSystemProperties_setProperties_null() {
        // user.dir is an immutable property
        String userDir = System.getProperty("user.dir");
        assertNotNull(userDir);

        // Add a non-standard property
        System.setProperty("p1", "v1");

        // Reset using setProperties(null)
        System.setProperties(null);

        // All the immutable properties should be reset.
        assertEquals(userDir, System.getProperty("user.dir"));
        // Non-standard properties are cleared.
        assertNull(System.getProperty("p1"));
    }

    public void testSystemProperties_setProperties_nonNull() {
        String userDir = System.getProperty("user.dir");

        Properties newProperties = new Properties();
        // Immutable property
        newProperties.setProperty("user.dir", "v1");
        // Non-standard property
        newProperties.setProperty("p1", "v2");

        System.setProperties(newProperties);

        // Android-note: The RI makes the setProperties() argument the system properties object.
        // Android makes a new Properties object and copies the properties.
        assertNotSame(newProperties, System.getProperties());
        // Android-note: The RI does not have a concept of immutable properties.
        assertEquals(userDir, System.getProperty("user.dir"));

        assertEquals("v2", System.getProperty("p1"));
    }

    public void testSystemProperties_getProperties_clear() {
        String userDir = System.getProperty("user.dir");
        assertNotNull(userDir);
        System.setProperty("p1", "v1");

        Properties properties = System.getProperties();
        assertEquals("v1", properties.getProperty("p1"));

        properties.clear();

        // Android-note: The RI clears everything, Android resets to immutable defaults.
        assertEquals(userDir, System.getProperty("user.dir"));
        assertNull(System.getProperty("p1"));
    }

    /**
     * Assert that the following ICU-related system properties exist
     */
    public void testSystemProperties_getProperties_icu() {
        String icuVersion = System.getProperty("android.icu.library.version");
        assertNotNull(icuVersion);
        assertTrue(icuVersion.length() > 0);
        String unicodeVersion = System.getProperty("android.icu.unicode.version");
        assertNotNull(unicodeVersion);
        assertTrue(unicodeVersion.length() > 0);
        String cldrVersion = System.getProperty("android.icu.cldr.version");
        assertNotNull(cldrVersion);
        assertTrue(cldrVersion.length() > 0);
    }

    public void testSystem_setSecurityManager_null_noException() {
        System.setSecurityManager(null);
    }

    public void testSystem_setSecurityManager_notNull_throwsException() {
        try  {
            System.setSecurityManager(new SecurityManager());
            fail("Expected " + SecurityException.class.getName());
        } catch (SecurityException expected) {
        }
    }

    /**
     * Overall {@link System#console()} return value depends on how exactly runtime was started, but
     * for Android apps it will be null.
     */
    public void testSystem_console() {
        assertNull(System.console());
    }
}
