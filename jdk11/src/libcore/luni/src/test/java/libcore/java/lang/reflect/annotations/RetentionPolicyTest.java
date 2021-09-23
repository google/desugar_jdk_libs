/*
 * Copyright (C) 2019 The Android Open Source Project
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
package libcore.java.lang.reflect.annotations;

import dalvik.system.PathClassLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import libcore.io.Streams;
import libcore.junit.util.SwitchTargetSdkVersionRule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Runs tests against classes loaded from the annotations-test.jar.
 *
 * <p>The annotated classes are built separately in order to ensure class-retention annotations are
 * kept which is no longer the default behavior of the platform dexer.
 */
@RunWith(JUnit4.class)
public class RetentionPolicyTest {

    @Rule
    public TestRule switchTargetSdkVersionRule = SwitchTargetSdkVersionRule.getInstance();

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    /**
     * Static reference to the classloader created for annotations-test.jar.
     *
     * <p>This is created statically as it is expensive to create.
     */
    private static ClassLoader classLoader;

    @BeforeClass
    public static void openClassLoader() throws IOException {
        ClassLoader myClassLoader = RetentionPolicyTest.class.getClassLoader();
        assertNotNull(myClassLoader);

        // Load the annotated class (and annotation classes) from the annotations-test.jar.
        File jarFile = new File(temporaryFolder.getRoot(), "annotations-test.jar");
        try (InputStream in = myClassLoader.getResourceAsStream("annotations-test.jar");
             OutputStream out = new FileOutputStream(jarFile)) {
            Streams.copy(in, out);
        }

        classLoader = new PathClassLoader(jarFile.getAbsolutePath(), myClassLoader);
    }

    @AfterClass
    public static void closeClassLoader() {
        // Null the reference to allow it to be garbage collected if necessary.
        classLoader = null;
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Annotation> getAnnotationClass(String name) throws Exception {
        return (Class<? extends Annotation>) classLoader.loadClass(name);
    }

    private Class<?> getRetentionAnnotationsClass() throws Exception {
        return classLoader.loadClass("libcore.tests.annotations.RetentionAnnotations");
    }

    // b/29500035
    @SwitchTargetSdkVersionRule.TargetSdkVersion(23)
    @Test
    public void testRetentionPolicy_targetSdkVersion_23() throws Exception {
        Class<? extends Annotation> annotationClass = getAnnotationClass(
                "libcore.tests.annotations.ClassRetentionAnnotation");
        // Test pre-N behavior
        Annotation classRetentionAnnotation =
                getRetentionAnnotationsClass().getAnnotation(annotationClass);
        assertNotNull(classRetentionAnnotation);
    }

    // b/29500035
    @SwitchTargetSdkVersionRule.TargetSdkVersion(24)
    @Test
    public void testRetentionPolicy_targetSdkVersion_24() throws Exception {
        // Test N and later behavior
        Class<? extends Annotation> annotationClass = getAnnotationClass(
                "libcore.tests.annotations.ClassRetentionAnnotation");
        Annotation classRetentionAnnotation =
                getRetentionAnnotationsClass().getAnnotation(annotationClass);
        assertNull(classRetentionAnnotation);
    }

    @Test
    public void testRetentionPolicy() throws Exception {
        Class<?> retentionAnnotationsClass = getRetentionAnnotationsClass();
        assertNotNull(retentionAnnotationsClass.getAnnotation(getAnnotationClass(
                "libcore.tests.annotations.RuntimeRetentionAnnotation")));
        assertNull(retentionAnnotationsClass.getAnnotation(getAnnotationClass(
                "libcore.tests.annotations.SourceRetentionAnnotation")));
    }
}
