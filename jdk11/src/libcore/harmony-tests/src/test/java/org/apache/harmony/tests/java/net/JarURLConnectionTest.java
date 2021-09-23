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

package org.apache.harmony.tests.java.net;

import tests.support.resource.Support_Resources;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JarURLConnectionTest extends junit.framework.TestCase {

    private JarURLConnection juc;

    private URL copyAndOpenResourceStream(String jarFile, String inFile)
            throws MalformedURLException {

        File resources = Support_Resources.createTempFolder();

        Support_Resources.copyFile(resources, "net", jarFile);
        File file = new File(resources.toString() + "/net/" + jarFile);

        return new URL("jar:file:" + file.getPath() + "!/" + inFile);
    }


    /**
     * java.net.JarURLConnection#getAttributes()
     */
    public void test_getAttributes() throws Exception {
        URL u = copyAndOpenResourceStream("lf.jar", "swt.dll");

        juc = (JarURLConnection) u.openConnection();
        java.util.jar.Attributes a = juc.getJarEntry().getAttributes();
        assertEquals("Returned incorrect Attributes", "SHA MD5", a
                .get(new java.util.jar.Attributes.Name("Digest-Algorithms")));
    }

    /**
     * @throws Exception
     * java.net.JarURLConnection#getEntryName()
     */
    public void test_getEntryName() throws Exception {
        URL u = copyAndOpenResourceStream("lf.jar", "plus.bmp");
        juc = (JarURLConnection) u.openConnection();
        assertEquals("Returned incorrect entryName", "plus.bmp", juc
                .getEntryName());
        u = copyAndOpenResourceStream("lf.jar", "");
        juc = (JarURLConnection) u.openConnection();
        assertNull("Returned incorrect entryName", juc.getEntryName());
//      Regression test for harmony-3053
        URL url = copyAndOpenResourceStream("lf.jar", "foo.jar!/Bugs/HelloWorld.class");
        assertEquals("foo.jar!/Bugs/HelloWorld.class", ((JarURLConnection) url.openConnection()).getEntryName());
    }

    /**
     * java.net.JarURLConnection#getJarEntry()
     */
    public void test_getJarEntry() throws Exception {
        URL u = copyAndOpenResourceStream("lf.jar", "plus.bmp");
        juc = (JarURLConnection) u.openConnection();
        assertEquals("Returned incorrect JarEntry", "plus.bmp", juc
                .getJarEntry().getName());
        u = copyAndOpenResourceStream("lf.jar", "");
        juc = (JarURLConnection) u.openConnection();
        assertNull("Returned incorrect JarEntry", juc.getJarEntry());
    }

    /**
     * java.net.JarURLConnection#getJarFile()
     */
    public void test_getJarFile() throws MalformedURLException, IOException {
        URL url = copyAndOpenResourceStream("lf.jar", "missing");

        JarURLConnection connection = null;
        connection = (JarURLConnection) url.openConnection();
        try {
            connection.connect();
            fail("Did not throw exception on connect");
        } catch (IOException e) {
            // expected
        }

        try {
            connection.getJarFile();
            fail("Did not throw exception after connect");
        } catch (IOException e) {
            // expected
        }

        File resources = Support_Resources.createTempFolder();

        Support_Resources.copyFile(resources, null, "hyts_att.jar");
        File file = new File(resources.toString() + "/hyts_att.jar");
        URL fUrl1 = new URL("jar:file:" + file.getPath() + "!/");
        JarURLConnection con1 = (JarURLConnection) fUrl1.openConnection();
        ZipFile jf1 = con1.getJarFile();
        JarURLConnection con2 = (JarURLConnection) fUrl1.openConnection();
        ZipFile jf2 = con2.getJarFile();
        assertTrue("file: JarFiles not the same", jf1 == jf2);
        jf1.close();
        assertTrue("File should exist", file.exists());
        con1 = (JarURLConnection) fUrl1.openConnection();
        jf1 = con1.getJarFile();
        con2 = (JarURLConnection) fUrl1.openConnection();
        jf2 = con2.getJarFile();
        assertTrue("http: JarFiles not the same", jf1 == jf2);
        jf1.close();
    }

    /**
     * java.net.JarURLConnection.getJarFile()
     * <p/>
     * Regression test for HARMONY-29
     */
    public void test_getJarFile29() throws Exception {
        File jarFile = File.createTempFile("1+2 3", "test.jar");
        jarFile.deleteOnExit();
        JarOutputStream out = new JarOutputStream(new FileOutputStream(jarFile));
        out.putNextEntry(new ZipEntry("test"));
        out.closeEntry();
        out.close();

        JarURLConnection conn = (JarURLConnection) new URL("jar:file:"
                + jarFile.getAbsolutePath().replaceAll(" ", "%20") + "!/")
                .openConnection();
        conn.getJarFile().entries();
    }

    //Regression for HARMONY-3436
    public void test_setUseCaches() throws Exception {
        File resources = Support_Resources.createTempFolder();
        Support_Resources.copyFile(resources, null, "hyts_att.jar");
        File file = new File(resources.toString() + "/hyts_att.jar");
        URL url = new URL("jar:file:" + file.getPath() + "!/HasAttributes.txt");

        JarURLConnection connection = (JarURLConnection) url.openConnection();
        connection.setUseCaches(false);
        InputStream in = connection.getInputStream();
        JarFile jarFile1 = connection.getJarFile();
        JarEntry jarEntry1 = connection.getJarEntry();
        byte[] data = new byte[1024];
        while (in.read(data) >= 0)
            ;
        in.close();
        JarFile jarFile2 = connection.getJarFile();
        JarEntry jarEntry2 = connection.getJarEntry();
        assertSame(jarFile1, jarFile2);
        assertSame(jarEntry1, jarEntry2);

        try {
            connection.getInputStream();
            fail("should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * java.net.JarURLConnection#getJarFileURL()
     */
    public void test_getJarFileURL() throws Exception {
        // Regression test for harmony-3053
        URL url = new URL("jar:file:///bar.jar!/foo.jar!/Bugs/HelloWorld.class");
        assertEquals("file:///bar.jar", ((JarURLConnection) url.openConnection()).getJarFileURL().toString());
    }

    /**
     * java.net.JarURLConnection#getMainAttributes()
     */
    public void test_getMainAttributes() throws Exception {
        URL u = copyAndOpenResourceStream("lf.jar", "swt.dll");
        juc = (JarURLConnection) u.openConnection();
        java.util.jar.Attributes a = juc.getMainAttributes();
        assertEquals("Returned incorrect Attributes", "1.0", a
                .get(java.util.jar.Attributes.Name.MANIFEST_VERSION));
    }

    /**
     * java.net.JarURLConnection#getInputStream()
     */
    public void test_getInputStream_DeleteJarFileUsingURLConnection()
            throws Exception {
        File file = File.createTempFile("JarUrlConnectionTest", "jar");
        FileOutputStream jarFile = new FileOutputStream(file);
        JarOutputStream out = new JarOutputStream(new BufferedOutputStream(
                jarFile));
        JarEntry jarEntry = new JarEntry("entry.txt");
        out.putNextEntry(jarEntry);
        out.write(new byte[] { 'a', 'b', 'c' });
        out.close();

        URL url = new URL("jar:file:" + file.getAbsolutePath() + "!/entry.txt");
        URLConnection conn = url.openConnection();
        conn.setUseCaches(false);
        InputStream is = conn.getInputStream();
        is.close();
        assertTrue(file.delete());
    }

    /**
     * java.net.JarURLConnection#getManifest()
     */
    public void test_getManifest() throws Exception {
        URL u = copyAndOpenResourceStream("lf.jar", "plus.bmp");
        juc = (JarURLConnection) u.openConnection();
        Manifest mf = juc.getManifest();
        assertNotNull(mf);
        // equal but not same manifest
        assertEquals(mf, juc.getManifest());
        assertNotSame(mf, juc.getManifest());
        // same main attributes
        assertEquals(juc.getMainAttributes(), mf.getMainAttributes());
    }

    /**
     * java.net.JarURLConnection#getCertificates()
     */
    public void test_getCertificates() throws Exception {
        URL u = copyAndOpenResourceStream("lf.jar", "plus.bmp");
        juc = (JarURLConnection) u.openConnection();
        // read incomplete, shall return null
        assertNull(juc.getCertificates());
        assertEquals("Returned incorrect JarEntry", "plus.bmp", juc
                .getJarEntry().getName());
        // read them all
        InputStream is = juc.getInputStream();
        byte[] buf = new byte[80];
        while (is.read(buf) > 0) ;
        // still return null for this type of file
        assertNull(juc.getCertificates());

        URL fileURL = copyAndOpenResourceStream("lf.jar", "");
        juc = (JarURLConnection) fileURL.openConnection();
        is = juc.getJarFileURL().openStream();
        while (is.read(buf) > 0) ;
        // null for this jar file
        assertNull(juc.getCertificates());
    }

    /**
     * java.net.JarURLConnection#getContentLength()
     * Regression test for HARMONY-3665
     */
    public void test_getContentLength() throws Exception {
        // check length for jar file itself
        URL u = copyAndOpenResourceStream("lf.jar", "");
        assertEquals("Returned incorrect size for jar file", 33095,
                u.openConnection().getContentLength());

        // check length for jar entry
        u = copyAndOpenResourceStream("lf.jar", "plus.bmp");
        assertEquals("Returned incorrect size for the entry", 190,
                u.openConnection().getContentLength());
    }

    /**
     * java.net.JarURLConnection#getContentType()
     * Regression test for HARMONY-3665
     */
    public void test_getContentType() throws Exception {
        // check type for jar file itself
        URL u = copyAndOpenResourceStream("lf.jar", "");
        assertEquals("Returned incorrect type for jar file", "x-java/jar",
                u.openConnection().getContentType());

        // check type for jar entry with known type
        u = copyAndOpenResourceStream("lf.jar", "plus.bmp");
        assertEquals("Returned incorrect type for the entry with known type",
                "image/x-ms-bmp", u.openConnection().getContentType());

        // check type for jar entry with unknown type
        u = copyAndOpenResourceStream("lf.jar", "Manifest.mf");
        assertEquals("Returned incorrect type for the entry with known type",
                "content/unknown", u.openConnection().getContentType());
    }

    public void test_getURLEncodedEntry() throws IOException {
        URL url = copyAndOpenResourceStream("url-test.jar", "test%20folder%20for%20url%20test/test");
        if (url != null) {
            // Force existence check
            InputStream is = url.openStream();
            is.close();
        }
    }
}
