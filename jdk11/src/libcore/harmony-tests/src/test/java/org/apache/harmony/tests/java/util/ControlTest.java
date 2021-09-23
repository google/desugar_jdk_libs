/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.harmony.tests.java.util;

import junit.framework.TestCase;
import tests.support.resource.Support_Resources;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.Scanner;
import static java.util.ResourceBundle.Control.*;

/**
 * Test cases for java.util.ResourceBundle.Control
 *
 * @since 1.6
 */
public class ControlTest extends TestCase {

    public static final String RESOURCE_PACKAGE_NAME = "tests.resources.control_test";

    /**
     * Control with format:FORMAT_PROPERTIES
     */
    private Control controlP;

    /**
     * Control with format:FORMAT_CLASS
     */
    private Control controlC;

    /**
     * Control with format:FORMAT_DEFAULT
     */
    private Control control;

    /**
     * {@link java.util.ResourceBundle.Control#Control()}.
     */
    @SuppressWarnings("nls")
    public void test_Constructor() {

        class SubControl extends Control {
            SubControl() {
                super();
            }
        }
        Control subControl = new SubControl();
        assertEquals(FORMAT_DEFAULT, subControl.getFormats(""));
        assertFalse(control.equals(subControl));
    }

    /**
     * Test for all the public constants.
     *
     * {@link java.util.ResourceBundle.Control#FORMAT_CLASS}
     * {@link java.util.ResourceBundle.Control#FORMAT_DEFAULT}
     * {@link java.util.ResourceBundle.Control#FORMAT_PROPERTIES}
     * {@link java.util.ResourceBundle.Control#TTL_DONT_CACHE}
     * {@link java.util.ResourceBundle.Control#TTL_NO_EXPIRATION_CONTROL}
     */
    @SuppressWarnings("nls")
    public void test_Constants() {
        List<String> list = FORMAT_CLASS;
        assertEquals(1, list.size());
        assertEquals("java.class", list.get(0));
        list = FORMAT_PROPERTIES;
        assertEquals(1, list.size());
        assertEquals("java.properties", list.get(0));
        list = FORMAT_DEFAULT;
        assertEquals(2, list.size());
        assertEquals("java.class", list.get(0));
        assertEquals("java.properties", list.get(1));
        try {
            FORMAT_CLASS.add("");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        try {
            FORMAT_DEFAULT.add("");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        try {
            FORMAT_PROPERTIES.add("");
            fail("Should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        Class<?> unmodifiableListClass = Collections.unmodifiableList(
                new ArrayList<String>()).getClass();
        assertEquals(FORMAT_CLASS.getClass(), unmodifiableListClass);
        assertEquals(FORMAT_DEFAULT.getClass(), unmodifiableListClass);
        assertEquals(FORMAT_PROPERTIES.getClass(), unmodifiableListClass);
        assertEquals(-1L, TTL_DONT_CACHE);
        assertEquals(-2L, TTL_NO_EXPIRATION_CONTROL);
    }

    /**
     * {@link java.util.ResourceBundle.Control#getControl(java.util.List)}.
     */
    @SuppressWarnings("nls")
    public void test_getControl_LList() {
        // singleton
        assertSame(control, Control.getControl(FORMAT_DEFAULT));
        assertSame(controlC, Control.getControl(FORMAT_CLASS));
        assertSame(controlP, Control.getControl(FORMAT_PROPERTIES));

        // class
        assertTrue(control.getClass() == Control.class);
        assertTrue(controlC.getClass() != Control.class);
        assertTrue(controlP.getClass() != Control.class);

        // formats: need not same, just need equal
        List<String> list = new ArrayList<String>(FORMAT_CLASS);
        assertSame(controlC, Control.getControl(list));
        // can add
        list.add(FORMAT_PROPERTIES.get(0));
        assertSame(control, Control.getControl(list));

        // exceptions
        try {
            Control.getControl(null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        list = new ArrayList<String>();
        try {
            Control.getControl(list);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        list = new ArrayList<String>(FORMAT_CLASS);
        // java.class -> JAVA.CLASS
        list.set(0, list.get(0).toUpperCase());
        try {
            Control.getControl(list);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        list = new ArrayList<String>(FORMAT_CLASS);
        list.add("");
        try {
            Control.getControl(list);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * {@link java.util.ResourceBundle.Control#getNoFallbackControl(java.util.List)}.
     */
    @SuppressWarnings("nls")
    public void test_getNoFallbackControl_LList() {
        assertNotSame(control, Control.getNoFallbackControl(FORMAT_DEFAULT));
        assertNotSame(controlC, Control.getNoFallbackControl(FORMAT_CLASS));
        assertNotSame(controlP, Control.getNoFallbackControl(FORMAT_PROPERTIES));
        controlP = Control.getNoFallbackControl(FORMAT_PROPERTIES);
        controlC = Control.getNoFallbackControl(FORMAT_CLASS);
        control = Control.getNoFallbackControl(FORMAT_DEFAULT);
        // singleton
        assertSame(control, Control.getNoFallbackControl(FORMAT_DEFAULT));
        assertSame(controlC, Control.getNoFallbackControl(FORMAT_CLASS));
        assertSame(controlP, Control.getNoFallbackControl(FORMAT_PROPERTIES));

        // class
        assertTrue(control.getClass() != Control.class);
        assertTrue(controlC.getClass() != Control.class);
        assertTrue(controlP.getClass() != Control.class);

        // format
        assertEquals(FORMAT_CLASS, controlC.getFormats(""));
        assertEquals(FORMAT_DEFAULT, control.getFormats(""));
        assertEquals(FORMAT_PROPERTIES, controlP.getFormats(""));

        // no fall back locale
        Locale defaultLocale = Locale.getDefault();
        Locale.setDefault(new Locale("TestLanguage", "TestCountry", "Var"));
        assertNull(control.getFallbackLocale("message", Locale.US));
        try {
            control.getFallbackLocale("message", null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            control.getFallbackLocale(null, Locale.US);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        Locale.setDefault(defaultLocale);

        // formats: need not same, just need equal
        List<String> list = new ArrayList<String>(FORMAT_CLASS);
        assertSame(controlC, Control.getNoFallbackControl(list));
        // can add
        list.add(FORMAT_PROPERTIES.get(0));
        assertSame(control, Control.getNoFallbackControl(list));

        // exceptions
        try {
            Control.getNoFallbackControl(null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        list = new ArrayList<String>();
        try {
            Control.getNoFallbackControl(list);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        list = new ArrayList<String>(FORMAT_CLASS);
        // java.class -> JAVA.CLASS
        list.set(0, list.get(0).toUpperCase());
        try {
            Control.getNoFallbackControl(list);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        list = new ArrayList<String>(FORMAT_CLASS);
        list.add("");
        try {
            Control.getNoFallbackControl(list);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * {@link java.util.ResourceBundle.Control#getFormats(java.lang.String)}.
     */
    @SuppressWarnings("nls")
    public void test_getFormats_LString() {
        assertEquals(FORMAT_DEFAULT, control.getFormats(""));
        assertEquals(FORMAT_PROPERTIES, controlP.getFormats(""));
        assertEquals(FORMAT_CLASS, controlC.getFormats(""));
        try {
            controlC.getFormats(null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * {@link java.util.ResourceBundle.Control#getCandidateLocales(java.lang.String, java.util.Locale)}.
     */
    @SuppressWarnings("nls")
    public void test_getCandidateLocales_LStringLLocale() {
        // the ResourceBundle for this baseName and Locale does not exists
        List<Locale> result = control.getCandidateLocales("baseName",
                new Locale("one", "two", "three"));
        assertEquals(4, result.size());
        Locale locale = result.get(0);
        assertEquals("one", locale.getLanguage());
        assertEquals("TWO", locale.getCountry());
        assertEquals("three", locale.getVariant());
        assertEquals(new Locale("one", "TWO"), result.get(1));
        assertEquals(new Locale("one"), result.get(2));
        assertSame(Locale.ROOT, result.get(3));
        // ArrayList is not immutable
        assertTrue(ArrayList.class == result.getClass());

        result = control.getCandidateLocales("baseName", new Locale("one",
                "two", ""));
        assertEquals(new Locale("one", "TWO"), result.get(0));
        assertEquals(new Locale("one"), result.get(1));
        assertSame(Locale.ROOT, result.get(2));

        result = control.getCandidateLocales("baseName", new Locale("one", "",
                "three"));
        assertEquals(new Locale("one", "", "three"), result.get(0));
        assertEquals(new Locale("one"), result.get(1));
        assertSame(Locale.ROOT, result.get(2));

        result = control.getCandidateLocales("baseName", new Locale("", "two",
                "three"));
        assertEquals(new Locale("", "TWO", "three"), result.get(0));
        assertEquals(new Locale("", "TWO"), result.get(1));
        assertSame(Locale.ROOT, result.get(2));

        result = control.getCandidateLocales("baseName", new Locale("", "",
                "three"));
        assertEquals(new Locale("", "", "three"), result.get(0));
        assertSame(Locale.ROOT, result.get(1));

        result = control.getCandidateLocales("baseName", new Locale("", "two",
                ""));
        assertEquals(new Locale("", "TWO"), result.get(0));
        assertSame(Locale.ROOT, result.get(1));

        result = control.getCandidateLocales("baseName", Locale.ROOT);
        assertSame(Locale.ROOT, result.get(0));

        try {
            control.getCandidateLocales(null, Locale.US);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            control.getCandidateLocales("baseName", null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * {@link java.util.ResourceBundle.Control#getFallbackLocale(java.lang.String, java.util.Locale)}.
     */
    @SuppressWarnings("nls")
    public void test_getFallbackLocale_LStringLLocale() {
        Locale defaultLocale = Locale.getDefault();
        Locale testLocale = new Locale("TestLanguage", "TestCountry", "Var");
        Locale.setDefault(testLocale);
        assertSame(testLocale, control.getFallbackLocale("baseName",
                Locale.ROOT));
        assertSame(testLocale, control.getFallbackLocale("baseName", Locale.US));
        assertSame(null, control.getFallbackLocale("baseName", testLocale));
        try {
            control.getFallbackLocale(null, Locale.US);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            control.getFallbackLocale("baseName", null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        // restore
        Locale.setDefault(defaultLocale);
    }

    @SuppressWarnings("nls")
    static File copyFile(final URL src, final String targetDir) throws IOException {
        String tail = src.getFile().split("hyts_resource")[1];
        String copyName = targetDir + File.separator + "hyts_resource_copy" + tail;
        File copy = new File(copyName);
        if (copy.exists()) {
            copy.delete();
        }
        copy.createNewFile();
        copy.deleteOnExit();

        Reader in = new InputStreamReader(src.openStream());
        Writer out = new FileWriter(copy);
        int c;
        while ((c = in.read()) != -1) {
            out.write(c);
        }
        in.close();
        out.close();
        return copy;
    }

    static class SubRBStaticPrivate extends ListResourceBundle {
        private SubRBStaticPrivate() {
            super();
        }

        @Override
        protected Object[][] getContents() {
            return null;
        }
    }

    /*
     * change the value in the .properties file
     */
    @SuppressWarnings("nls")
    static void changeProperties(File file) throws FileNotFoundException {
        String newValue = "property=changedValue";
        PrintWriter writer = new PrintWriter(file);
        writer.write(newValue);
        writer.flush();
        writer.close();
        Scanner scanner = new Scanner(file);
        assertEquals(newValue, scanner.nextLine());
        scanner.close();
    }

    /**
     * {@link java.util.ResourceBundle.Control#getTimeToLive(java.lang.String, java.util.Locale)}.
     */
    @SuppressWarnings("nls")
    public void test_getTimeToLive_LStringLLocale() {
        assertEquals(TTL_NO_EXPIRATION_CONTROL, control.getTimeToLive(
                "baseName", Locale.US));
        try {
            control.getTimeToLive(null, Locale.US);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            control.getTimeToLive("baseName", null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * @throws Exception
     * {@link java.util.ResourceBundle.Control#needsReload(java.lang.String, java.util.Locale, java.lang.String, java.lang.ClassLoader, java.util.ResourceBundle, long)}.
     */
    @SuppressWarnings("nls")
    public void test_needsReload_LStringLLocaleLStringLClassLoaderResourceBundleJ()
            throws Exception {
        String className = "tests.support.Support_TestResource";
        String propertiesName = RESOURCE_PACKAGE_NAME + ".hyts_resource";
        String propertiesNameCopy = "hyts_resource_copy";
        String CLASS = "java.class";
        String PROPERTIES = "java.properties";
        Locale frFR = new Locale("fr", "FR");
        ClassLoader testCodeClassLoader = this.getClass().getClassLoader();
        ResourceBundle bundle = null;
        long time = 0L;
        final URL srcFile = testCodeClassLoader.getResource(control.toResourceName(
                control.toBundleName(propertiesName, frFR), "properties"));
        assertNotNull(srcFile);

        String tmpdir = System.getProperty("java.io.tmpdir");
        assertNotNull(tmpdir);
        final File copyFile = copyFile(srcFile, tmpdir);
        ClassLoader URLLoader = new URLClassLoader(
                new URL[] { new File(tmpdir).toURL() },
                testCodeClassLoader);

        // 1. format = "java.properties"
        if (null != URLLoader.getResourceAsStream(copyFile.getName())) {
            Thread.sleep(1000);
            bundle = control.newBundle(propertiesNameCopy, frFR, PROPERTIES,
                    URLLoader, false);
            time = System.currentTimeMillis();
            assertTrue(bundle.getClass() == PropertyResourceBundle.class);
            assertEquals("fr_FR_resource", bundle.getString("property"));
            assertFalse(control.needsReload(propertiesNameCopy, frFR,
                    PROPERTIES, URLLoader, bundle, time));
            // change the file
            Thread.sleep(2000);
            changeProperties(copyFile);
            assertTrue(control.needsReload(propertiesNameCopy, frFR,
                    PROPERTIES, URLLoader, bundle, time));
            // detect again
            assertTrue(control.needsReload(propertiesNameCopy, frFR,
                    PROPERTIES, URLLoader, bundle, time));
            // long long ago
            assertTrue(control.needsReload(propertiesNameCopy, frFR,
                    PROPERTIES, URLLoader, bundle, 2006L));
            // other loader
            assertFalse(control.needsReload(propertiesNameCopy, frFR,
                    PROPERTIES, testCodeClassLoader, bundle, time));
            // other bundle
            ResourceBundle otherBundle = control.newBundle(propertiesName,
                    Locale.ROOT, PROPERTIES, URLLoader, false);
            assertEquals("resource", otherBundle.getString("property"));
            assertTrue(control.needsReload(propertiesNameCopy, frFR,
                    PROPERTIES, URLLoader, otherBundle, time));
            // other time
            assertFalse(control.needsReload(propertiesNameCopy, frFR,
                    PROPERTIES, URLLoader, bundle, System.currentTimeMillis()));
        } else {
            fail("Can not find the test file:" + copyFile);
        }

        // 2. format = "java.class"
        bundle = control.newBundle(className, frFR, CLASS, testCodeClassLoader, false);
        time = System.currentTimeMillis();
        assertEquals("frFRValue3", bundle.getString("parent3"));
        assertFalse(control.needsReload(className, frFR, CLASS, testCodeClassLoader,
                bundle, time));
        // exceptions
        control.needsReload(propertiesName, frFR, PROPERTIES, URLLoader,
                bundle, time);
        try {
            control
                    .needsReload(null, frFR, PROPERTIES, URLLoader, bundle,
                            time);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            control.needsReload(propertiesName, null, PROPERTIES, URLLoader,
                    bundle, time);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            control.needsReload(propertiesName, frFR, null, URLLoader, bundle,
                    time);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            control.needsReload(propertiesName, frFR, PROPERTIES, null, bundle,
                    time);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            control.needsReload(propertiesName, frFR, PROPERTIES, URLLoader,
                    null, time);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * {@link java.util.ResourceBundle.Control#toBundleName(java.lang.String, java.util.Locale)}.
     */
    @SuppressWarnings("nls")
    public void test_toBundleName_LStringLLocale() {
        assertEquals("baseName_one_TWO_three", control.toBundleName("baseName",
                new Locale("one", "two", "three")));
        assertEquals("baseName_one_TWO", control.toBundleName("baseName",
                new Locale("one", "two")));
        assertEquals("baseName_one__three", control.toBundleName("baseName",
                new Locale("one", "", "three")));
        assertEquals("baseName__TWO_three", control.toBundleName("baseName",
                new Locale("", "two", "three")));
        assertEquals("baseName_one", control.toBundleName("baseName",
                new Locale("one", "", "")));
        assertEquals("baseName___three", control.toBundleName("baseName",
                new Locale("", "", "three")));
        assertEquals("baseName__TWO", control.toBundleName("baseName",
                new Locale("", "two", "")));
        assertEquals("baseName", control.toBundleName("baseName", new Locale(
                "", "", "")));
        assertEquals("baseName", control.toBundleName("baseName", Locale.ROOT));
        assertEquals("_one_TWO_three", control.toBundleName("", new Locale(
                "one", "two", "three")));
        assertEquals("", control.toBundleName("", Locale.ROOT));

        assertEquals("does.not.exists_one_TWO_three", control.toBundleName(
                "does.not.exists", new Locale("one", "two", "three")));
        assertEquals("does/not/exists_one_TWO_three", control.toBundleName(
                "does/not/exists", new Locale("one", "two", "three")));
        assertEquals("does_not_exists__one_TWO_three", control.toBundleName(
                "does_not_exists_", new Locale("one", "two", "three")));

        assertEquals("...", control.toBundleName("...", Locale.ROOT));
        assertEquals("s/./\\//g", control
                .toBundleName("s/./\\//g", Locale.ROOT));
        assertEquals("123_one", control.toBundleName("123", new Locale("one")));

        try {
            control.toBundleName(null, Locale.US);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            control.toBundleName("baseName", null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * {@link java.util.ResourceBundle.Control#toResourceName(java.lang.String, java.lang.String)}.
     */
    @SuppressWarnings("nls")
    public void test_toResourceNameLStringLString() {
        assertEquals("does/not/exists_language_country.someSuffix", control
                .toResourceName("does.not.exists_language_country",
                        "someSuffix"));
        assertEquals("does/not/exists_language_country.someSuffix", control
                .toResourceName("does/not/exists_language_country",
                        "someSuffix"));
        assertEquals("does///not//exists_language/country.someSuffix", control
                .toResourceName("does...not..exists_language.country",
                        "someSuffix"));
        assertEquals("does\\not\\exists_language_country.someSuffix", control
                .toResourceName("does\\not\\exists_language_country",
                        "someSuffix"));
        assertEquals("does/not/exists_language_country/.someSuffix", control
                .toResourceName("does.not.exists_language_country.",
                        "someSuffix"));
        assertEquals("does/not/exists_language_country../someSuffix", control
                .toResourceName("does.not.exists_language_country",
                        "./someSuffix"));

        assertEquals("///.//", control.toResourceName("...", "//"));
        assertEquals("///...", control.toResourceName("///", ".."));
        assertEquals("123...", control.toResourceName("123", ".."));
        assertEquals("base.", control.toResourceName("base", ""));
        assertEquals(".suffix", control.toResourceName("", "suffix"));
        assertEquals(".", control.toResourceName("", ""));

        try {
            control.toResourceName(null, "suffix");
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            control.toResourceName("bundleName", null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

    }

    /**
     * @throws java.lang.Exception
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * @throws java.lang.Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        controlP = Control.getControl(FORMAT_PROPERTIES);
        controlC = Control.getControl(FORMAT_CLASS);
        control = Control.getControl(FORMAT_DEFAULT);
    }

}
