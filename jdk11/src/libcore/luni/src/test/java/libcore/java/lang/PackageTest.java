/*
 * Copyright (C) 2011 The Android Open Source Project
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

import dalvik.system.VMRuntime;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import libcore.junit.junit3.TestCaseWithRules;
import libcore.junit.util.SwitchTargetSdkVersionRule;
import libcore.junit.util.SwitchTargetSdkVersionRule.TargetSdkVersion;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.mockito.internal.matchers.Null;

public final class PackageTest extends TestCaseWithRules {

    @Rule
    public TestRule switchTargetSdkVersionRule = SwitchTargetSdkVersionRule.getInstance();

    /** assign packages immediately so that Class.getPackage() calls cannot side-effect it */
    private static final List<Package> packages = Arrays.asList(Package.getPackages());

    public void test_getAnnotations() throws Exception {
        // Pre-ICS we crashed. To pass, the package-info and TestPackageAnnotation classes must be
        // on the classpath.
        assertEquals(1, getClass().getPackage().getAnnotations().length);
        assertEquals(1, getClass().getPackage().getDeclaredAnnotations().length);
    }

    public void testGetPackage() {
        Package libcoreJavaLang = Package.getPackage("libcore.java.lang");
        assertEquals("libcore.java.lang", libcoreJavaLang.getName());
        assertEquals(getClass().getPackage(), libcoreJavaLang);
    }

    public void testGetPackageName() {
        Package libcoreJavaLang = Package.getPackage("libcore.java.lang");
        assertEquals("libcore.java.lang", getClass().getPackageName());
        assertEquals(getClass().getPackageName(), libcoreJavaLang.getName());
    }

    // http://b/28057303
    @TargetSdkVersion(24)
    public void test_toString_targetSdkVersion_24() throws Exception {
        Package libcoreJavaLang = Package.getPackage("libcore.java.lang");
        assertEquals("package libcore.java.lang", libcoreJavaLang.toString());
    }

    // http://b/28057303
    @TargetSdkVersion(25)
    public void test_toString_targetSdkVersion_25() throws Exception {
        Package libcoreJavaLang = Package.getPackage("libcore.java.lang");
        assertEquals("package libcore.java.lang, Unknown, version 0.0", libcoreJavaLang.toString());
    }

    // http://b/5171136
    public void testGetPackages() {
        assertTrue(packages.contains(getClass().getPackage()));
    }

    public void testGetAnnotationsByType() {
        Package libcoreJavaLang = Package.getPackage("libcore.java.lang");

        assertEquals(1, libcoreJavaLang.getAnnotationsByType(TestPackageAnnotation.class).length);
        assertEquals(0, libcoreJavaLang.getAnnotationsByType(Override.class).length);
    }

    public void testGetAnnotationsByType_shouldThrowNPE_whenNullIsPassed() {
        Package libcoreJavaLang = Package.getPackage("libcore.java.lang");

        try {
            libcoreJavaLang.getAnnotationsByType(null);
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }
    }

    public void testIsSealed_nonSealedPackage() throws Exception {
        TestClassLoader testClassLoader = new TestClassLoader();
        Package nonSealedPackage = testClassLoader.definePackage(
                "libcore.java.lang.nonsealed",
                "spec title",
                "spec version",
                "spec vendor",
                "impl title",
                "impl version",
                "impl vendor",
                null /* sealBase */);

        assertFalse(nonSealedPackage.isSealed());
        assertFalse(nonSealedPackage.isSealed(new URL("file://libcore/java/lang/nonsealed")));
    }

    public void testIsSealed_sealedPackage() throws Exception {
        TestClassLoader testClassLoader = new TestClassLoader();
        URL sealBase = new URL("file://libcore/java/lang/sealed");
        Package sealedPackage = testClassLoader.definePackage(
                "libcore.java.lang.sealed",
                "spec title",
                "spec version",
                "spec vendor",
                "impl title",
                "impl version",
                "impl vendor",
                sealBase);

        assertTrue(sealedPackage.isSealed());
        assertTrue(sealedPackage.isSealed(sealBase));
        assertFalse(sealedPackage.isSealed(new URL("file://libcore/java/lang")));
        try {
            sealedPackage.isSealed(null);
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }
    }

    public void testGetSpecificationVendor() {
        String specVendor = "specification vendor";
        TestClassLoader testClassLoader = new TestClassLoader();
        Package aPackage = testClassLoader.definePackage(
                "libcore.java.lang.nonsealed",
                "spec title",
                "spec version",
                specVendor,
                "impl title",
                "impl version",
                "impl vendor",
                null /* sealBase */);

        assertEquals(specVendor, aPackage.getSpecificationVendor());
    }

    public void testGetSpecificationVersion() {
        String specVersion = "specification version";
        TestClassLoader testClassLoader = new TestClassLoader();
        Package aPackage = testClassLoader.definePackage(
                "libcore.java.lang.nonsealed",
                "spec title",
                specVersion,
                "spec vendor",
                "impl title",
                "impl version",
                "impl vendor",
                null /* sealBase */);

        assertEquals(specVersion, aPackage.getSpecificationVersion());
    }

    public void testIsCompatibleWith() {
        String specVersion = "2.3.1";
        TestClassLoader testClassLoader = new TestClassLoader();
        Package aPackage = testClassLoader.definePackage(
                "libcore.java.lang.nonsealed",
                "spec title",
                specVersion,
                "spec vendor",
                "impl title",
                "impl version",
                "impl vendor",
                null /* sealBase */);

        assertTrue(aPackage.isCompatibleWith(specVersion));
        assertTrue(aPackage.isCompatibleWith("2.2.99.1"));
        assertTrue(aPackage.isCompatibleWith("1.0"));
        assertTrue(aPackage.isCompatibleWith("2.3.1.0"));
        assertTrue(aPackage.isCompatibleWith("2.3"));
        assertFalse(aPackage.isCompatibleWith("2.4"));
        try {
            aPackage.isCompatibleWith(null);
            fail();
        } catch (NullPointerException ignored) {
            // expected
        }
    }


    /**
     * {@link java.lang.Package} constructors are package-private and
     * {@link java.lang.ClassLoader#definePackage(String, String, String, String, String, String, String, URL)}
     * is other way to create instance of {@link java.lang.Package}
     */
    private static final class TestClassLoader extends ClassLoader {
        @Override
        public Package definePackage(String name, String specTitle, String specVersion,
                String specVendor, String implTitle, String implVersion, String implVendor,
                URL sealBase) throws IllegalArgumentException {
            return super.definePackage(name, specTitle, specVersion, specVendor, implTitle,
                    implVersion, implVendor, sealBase);
        }
    }
}
