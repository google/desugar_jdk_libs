/*
 * Copyright (C) 2016 The Android Open Source Project
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

package libcore.java.lang.reflect;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.MalformedParametersException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.function.Function;
import libcore.io.Streams;

import dalvik.system.PathClassLoader;

/**
 * Tests for {@link Parameter}. For annotation-related tests see
 * {@link libcore.java.lang.reflect.annotations.AnnotatedElementParameterTest} and
 * {@link libcore.java.lang.reflect.annotations.ExecutableParameterTest}.
 *
 * <p>Tests suffixed with _withMetadata() require parameter metadata compiled in to work properly.
 * These are handled by loading pre-compiled .dex files.
 * See also {@link DependsOnParameterMetadata}.
 */
public class ParameterTest extends TestCase {

    /**
     * A ClassLoader that can be used to load the
     * libcore.java.lang.reflect.parameter.ParameterMetadataTestClasses class and its nested
     * classes. The loaded classes have valid metadata that can be created by a valid Java compiler
     * / Android dexer.
     */
    private ClassLoader classesWithMetadataClassLoader;

    /**
     * A ClassLoader that can be used to load the
     * libcore.java.lang.reflect.parameter.MetadataVariations class.
     * The loaded class has invalid metadata that could not be created by a valid Java compiler /
     * Android dexer.
     */
    private ClassLoader metadataVariationsClassLoader;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        File tempDir = File.createTempFile("tempDir", "");
        assertTrue(tempDir.delete());
        assertTrue(tempDir.mkdirs());

        classesWithMetadataClassLoader =
                createClassLoaderForResource(tempDir, "parameter-metadata-test.jar");
        String metadataVariationsResourcePath =
                "libcore/java/lang/reflect/parameter/metadata_variations.dex";
        metadataVariationsClassLoader =
                createClassLoaderForResource(tempDir, metadataVariationsResourcePath);
    }

    /**
     * A source annotation used to mark code below with behavior that is highly dependent on
     * parameter metadata. It is intended to bring readers here for the following:
     *
     * <p>Unless the compiler supports (and is configured to enable) storage of metadata
     * for parameters, the runtime does not have access to the parameter name from the source and
     * some modifier information like "implicit" (AKA "mandated"), "synthetic" and "final".
     *
     * <p>This test class is expected to be compiled <em>without</em> requesting that the metadata
     * be compiled in. dex files that contains classes with metadata are loaded in setUp() and
     * used from the tests suffixed with "_withMetadata".
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    private @interface DependsOnParameterMetadata {}

    private static class SingleParameter {
        @SuppressWarnings("unused")
        SingleParameter(String p0) {}

        @SuppressWarnings("unused")
        void oneParameter(String p0) {}
    }

    public void testSingleParameterConstructor() throws Exception {
        Constructor<?> constructor = SingleParameter.class.getDeclaredConstructor(String.class);
        checkSingleStringParameter(constructor);
    }

    public void testSingleParameterMethod() throws Exception {
        Method method = SingleParameter.class.getDeclaredMethod("oneParameter", String.class);
        checkSingleStringParameter(method);
    }

    private static void checkSingleStringParameter(Executable executable) {
        ExecutableTestHelper helper = new ExecutableTestHelper(executable);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[java.lang.String arg0]")
                .checkParametersMetadataNotAvailable()
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(String.class)
                .checkGetParameterizedType("class java.lang.String");
    }

    public void testSingleParameterConstructor_withMetadata() throws Exception {
        Class<?> clazz = loadTestInnerClassWithMetadata("SingleParameter");
        Constructor<?> constructor  = clazz.getDeclaredConstructor(String.class);
        checkSingleStringParameter_withMetadata(constructor);
    }

    public void testSingleParameterMethod_withMetadata() throws Exception {
        Class<?> clazz = loadTestInnerClassWithMetadata("SingleParameter");
        Method method = clazz.getDeclaredMethod("oneParameter", String.class);
        checkSingleStringParameter_withMetadata(method);
    }

    private static void checkSingleStringParameter_withMetadata(Executable executable) {
        ExecutableTestHelper helper = new ExecutableTestHelper(executable);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[java.lang.String p0]")
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(String.class)
                .checkName(true /* expectedNameIsPresent */, "p0")
                .checkModifiers(0)
                .checkImplicitAndSynthetic(false, false)
                .checkGetParameterizedType("class java.lang.String");
    }

    private static class GenericParameter {
        @SuppressWarnings("unused")
        GenericParameter(Function<String, Integer> p0) {}

        @SuppressWarnings("unused")
        void genericParameter(Function<String, Integer> p0) {}
    }

    public void testGenericParameterConstructor() throws Exception {
        Constructor<?> constructor = GenericParameter.class.getDeclaredConstructor(Function.class);
        checkGenericParameter(constructor);
    }

    public void testGenericParameterMethod() throws Exception {
        Method method = GenericParameter.class.getDeclaredMethod(
                "genericParameter", Function.class);
        checkGenericParameter(method);
    }

    private static void checkGenericParameter(Executable executable) {
        ExecutableTestHelper helper = new ExecutableTestHelper(executable);
        helper.checkStandardParametersBehavior()
                .checkParametersToString(
                        "[java.util.function.Function<java.lang.String, java.lang.Integer> arg0]")
                .checkParametersMetadataNotAvailable()
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(Function.class)
                .checkGetParameterizedType(
                        "java.util.function.Function<java.lang.String, java.lang.Integer>");
    }

    public void testGenericParameterConstructor_withMetadata() throws Exception {
        Class<?> clazz = loadTestInnerClassWithMetadata("GenericParameter");
        Constructor<?> constructor = clazz.getDeclaredConstructor(Function.class);
        checkGenericParameter_withMetadata(constructor);
    }

    public void testGenericParameterMethod_withMetadata() throws Exception {
        Class<?> clazz = loadTestInnerClassWithMetadata("GenericParameter");
        Method method = clazz.getDeclaredMethod("genericParameter", Function.class);
        checkGenericParameter_withMetadata(method);
    }

    private static void checkGenericParameter_withMetadata(Executable executable) {
        ExecutableTestHelper helper = new ExecutableTestHelper(executable);
        helper.checkStandardParametersBehavior()
                .checkParametersToString(
                        "[java.util.function.Function<java.lang.String, java.lang.Integer> p0]")
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(Function.class)
                .checkName(true /* expectedNameIsPresent */, "p0")
                .checkModifiers(0)
                .checkImplicitAndSynthetic(false, false)
                .checkGetParameterizedType(
                        "java.util.function.Function<java.lang.String, java.lang.Integer>");
    }

    private static class TwoParameters {
        @SuppressWarnings("unused")
        TwoParameters(String p0, Integer p1) {}
        @SuppressWarnings("unused")
        void twoParameters(String p0, Integer p1) {}
    }

    public void testTwoParameterConstructor() throws Exception {
        Constructor<?> constructor =
                TwoParameters.class.getDeclaredConstructor(String.class, Integer.class);
        checkTwoParameters(constructor);
    }

    public void testTwoParameterMethod() throws Exception {
        Method method = TwoParameters.class.getDeclaredMethod(
                "twoParameters", String.class, Integer.class);
        checkTwoParameters(method);
    }

    private static void checkTwoParameters(Executable executable) {
        ExecutableTestHelper helper = new ExecutableTestHelper(executable);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[java.lang.String arg0, java.lang.Integer arg1]")
                .checkParametersMetadataNotAvailable()
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(String.class)
                .checkGetParameterizedType("class java.lang.String");

        helper.getParameterTestHelper(1)
                .checkGetType(Integer.class)
                .checkGetParameterizedType("class java.lang.Integer");
    }

    public void testTwoParameterConstructor_withMetadata() throws Exception {
        Class<?> clazz = loadTestInnerClassWithMetadata("TwoParameters");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, Integer.class);
        checkTwoParameters_withMetadata(constructor);
    }

    public void testTwoParameterMethod_withMetadata() throws Exception {
        Class<?> clazz = loadTestInnerClassWithMetadata("TwoParameters");
        Method method = clazz.getDeclaredMethod("twoParameters", String.class, Integer.class);
        checkTwoParameters_withMetadata(method);
    }

    private static void checkTwoParameters_withMetadata(Executable executable) {
        ExecutableTestHelper helper = new ExecutableTestHelper(executable);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[java.lang.String p0, java.lang.Integer p1]")
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(String.class)
                .checkName(true /* expectedNameIsPresent */, "p0")
                .checkModifiers(0)
                .checkImplicitAndSynthetic(false, false)
                .checkGetParameterizedType("class java.lang.String");

        helper.getParameterTestHelper(1)
                .checkGetType(Integer.class)
                .checkName(true /* expectedNameIsPresent */, "p1")
                .checkModifiers(0)
                .checkImplicitAndSynthetic(false, false)
                .checkGetParameterizedType("class java.lang.Integer");
    }

    private static class FinalParameter {
        @SuppressWarnings("unused")
        FinalParameter(final String p0) {}
        @SuppressWarnings("unused")
        void finalParameter(final String p0) {}
    }

    public void testFinalParameterConstructor() throws Exception {
        Constructor<?> constructor = FinalParameter.class.getDeclaredConstructor(String.class);
        checkFinalParameter(constructor);
    }

    public void testFinalParameterMethod() throws Exception {
        Method method = FinalParameter.class.getDeclaredMethod("finalParameter", String.class);
        checkFinalParameter(method);
    }

    private static void checkFinalParameter(Executable executable) {
        ExecutableTestHelper helper = new ExecutableTestHelper(executable);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[java.lang.String arg0]")
                .checkParametersMetadataNotAvailable()
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(String.class)
                .checkGetParameterizedType("class java.lang.String");
    }

    public void testFinalParameterConstructor_withMetdata() throws Exception {
        Class<?> clazz = loadTestInnerClassWithMetadata("FinalParameter");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class);
        checkFinalParameter_withMetadata(constructor);
    }

    public void testFinalParameterMethod_withMetdata() throws Exception {
        Class<?> clazz = loadTestInnerClassWithMetadata("FinalParameter");
        Method method = clazz.getDeclaredMethod("finalParameter", String.class);
        checkFinalParameter_withMetadata(method);
    }

    private static void checkFinalParameter_withMetadata(Executable executable) {
        ExecutableTestHelper helper = new ExecutableTestHelper(executable);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[final java.lang.String p0]")
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(String.class)
                .checkName(true /* expectedNameIsPresent */, "p0")
                .checkModifiers(Modifier.FINAL)
                .checkImplicitAndSynthetic(false, false)
                .checkGetParameterizedType("class java.lang.String");
    }

    /**
     * An inner class, used for checking compiler-inserted parameters: The first parameter is an
     * instance of the surrounding class.
     */
    private class InnerClass {
        @SuppressWarnings("unused")
        public InnerClass() {}
        @SuppressWarnings("unused")
        public InnerClass(String p1) {}
        @SuppressWarnings("unused")
        public InnerClass(Function<String, Integer> p1) {}
    }

    public void testInnerClassSingleParameter() throws Exception {
        Class<?> outerClass = ParameterTest.class;
        Class<?> innerClass = InnerClass.class;
        Constructor<?> constructor = innerClass.getDeclaredConstructor(outerClass);

        ExecutableTestHelper helper = new ExecutableTestHelper(constructor);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[" + outerClass.getName() + " arg0]")
                .checkParametersMetadataNotAvailable()
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(outerClass)
                .checkGetParameterizedType("class " + outerClass.getName() + "");
    }

    public void testInnerClassSingleParameter_withMetadata() throws Exception {
        Class<?> outerClass = loadTestOuterClassWithMetadata();
        Class<?> innerClass = loadTestInnerClassWithMetadata("InnerClass");
        Constructor<?> constructor = innerClass.getDeclaredConstructor(outerClass);

        ExecutableTestHelper helper = new ExecutableTestHelper(constructor);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[final " + outerClass.getName() + " this$0]")
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(outerClass)
                .checkName(true /* expectedNameIsPresent */, "this$0")
                .checkModifiers(32784) // 32784 == Modifier.MANDATED & Modifier.FINAL
                .checkImplicitAndSynthetic(true, false)
                .checkGetParameterizedType("class " + outerClass.getName());
    }

    public void testInnerClassTwoParameters() throws Exception {
        Class<?> outerClass = ParameterTest.class;
        Class<?> innerClass = InnerClass.class;
        Constructor<?> constructor = innerClass.getDeclaredConstructor(outerClass, String.class);

        ExecutableTestHelper helper = new ExecutableTestHelper(constructor);
        helper.checkStandardParametersBehavior()
                .checkParametersToString(
                        "[" + outerClass.getName() + " arg0, java.lang.String arg1]")
                .checkParametersMetadataNotAvailable()
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(outerClass)
                .checkGetParameterizedType("class " + outerClass.getName());

        helper.getParameterTestHelper(1)
                .checkGetType(String.class)
                .checkGetParameterizedType("class java.lang.String");
    }

    public void testInnerClassTwoParameters_withMetadata() throws Exception {
        Class<?> outerClass = loadTestOuterClassWithMetadata();
        Class<?> innerClass = loadTestInnerClassWithMetadata("InnerClass");
        Constructor<?> constructor = innerClass.getDeclaredConstructor(outerClass, String.class);

        ExecutableTestHelper helper = new ExecutableTestHelper(constructor);
        helper.checkStandardParametersBehavior()
                .checkParametersToString(
                        "[final " + outerClass.getName() + " this$0, java.lang.String p1]")
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkName(true /* expectedNameIsPresent */, "this$0")
                .checkModifiers(32784) // 32784 == Modifier.MANDATED & Modifier.FINAL
                .checkImplicitAndSynthetic(true, false)
                .checkGetType(outerClass)
                .checkGetParameterizedType("class " + outerClass.getName() + "");

        helper.getParameterTestHelper(1)
                .checkName(true /* expectedNameIsPresent */, "p1")
                .checkModifiers(0)
                .checkImplicitAndSynthetic(false, false)
                .checkGetType(String.class)
                .checkGetParameterizedType("class java.lang.String");
    }

    public void testInnerClassGenericParameter() throws Exception {
        Class<?> outerClass = ParameterTest.class;
        Class<?> innerClass = InnerClass.class;
        Constructor<?> constructor = innerClass.getDeclaredConstructor(outerClass, Function.class);

        ExecutableTestHelper helper = new ExecutableTestHelper(constructor);
        helper.checkStandardParametersBehavior()
                .checkParametersToString(
                        "[" + outerClass.getName() + " arg0, java.util.function.Function arg1]")
                .checkParametersMetadataNotAvailable()
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(outerClass)
                .checkGetParameterizedType("class " + outerClass.getName() + "");

        helper.getParameterTestHelper(1)
                .checkGetType(Function.class)
                .checkGetParameterizedType("interface java.util.function.Function");

        // The non-genericised string above is probably the result of a spec bug due to a mismatch
        // between the generic signature for the constructor (which suggests a single parameter)
        // and the actual parameters (which suggests two). In the absence of parameter metadata
        // to identify the synthetic parameter the code reverts to using non-Signature (type erased)
        // information.
    }

    public void testInnerClassGenericParameter_withMetadata() throws Exception {
        Class<?> outerClass = loadTestOuterClassWithMetadata();
        Class<?> innerClass = loadTestInnerClassWithMetadata("InnerClass");
        Constructor<?> constructor = innerClass.getDeclaredConstructor(outerClass, Function.class);

        ExecutableTestHelper helper = new ExecutableTestHelper(constructor);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[final " + outerClass.getName() + " this$0, "
                        + "java.util.function.Function<java.lang.String, java.lang.Integer> p1]")
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkName(true /* expectedNameIsPresent */, "this$0")
                .checkModifiers(32784) // 32784 == Modifier.MANDATED & Modifier.FINAL
                .checkImplicitAndSynthetic(true, false)
                .checkGetType(outerClass)
                .checkGetParameterizedType("class " + outerClass.getName() + "");

        helper.getParameterTestHelper(1)
                .checkName(true /* expectedNameIsPresent */, "p1")
                .checkModifiers(0)
                .checkImplicitAndSynthetic(false, false)
                .checkGetType(Function.class)
                .checkGetParameterizedType(
                        "java.util.function.Function<java.lang.String, java.lang.Integer>");
    }

    @SuppressWarnings("unused")
    enum TestEnum { ONE, TWO }

    /**
     * Enums are a documented example of a type of class with synthetic constructor parameters and
     * generated methods. This test may be brittle as it may rely on the compiler's implementation
     * of enums.
     */
    public void testEnumConstructor() throws Exception {
        Constructor<?> constructor = TestEnum.class.getDeclaredConstructor(String.class, int.class);

        ExecutableTestHelper helper = new ExecutableTestHelper(constructor);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[java.lang.String arg0, int arg1]")
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(String.class)
                .checkGetParameterizedType("class java.lang.String");

        helper.getParameterTestHelper(1)
                .checkGetType(int.class)
                .checkGetParameterizedType("int");
    }

    public void testEnumConstructor_withMetadata() throws Exception {
        Class<?> clazz = loadTestInnerClassWithMetadata("TestEnum");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);

        ExecutableTestHelper helper = new ExecutableTestHelper(constructor);
        helper.checkStandardParametersBehavior()
                // The extra spaces below are the result of a trivial upstream bug in
                // Parameter.toString() due to Modifier.toString(int) outputting nothing for
                // "SYNTHETIC".
                .checkParametersToString("[ java.lang.String $enum$name,  int $enum$ordinal]")
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkName(true /* expectedNameIsPresent */, "$enum$name")
                .checkModifiers(4096) // 4096 == Modifier.SYNTHETIC
                .checkImplicitAndSynthetic(false, true)
                .checkGetType(String.class)
                .checkGetParameterizedType("class java.lang.String");

        helper.getParameterTestHelper(1)
                .checkName(true /* expectedNameIsPresent */, "$enum$ordinal")
                .checkModifiers(4096) // 4096 == Modifier.SYNTHETIC
                .checkImplicitAndSynthetic(false, true)
                .checkGetType(int.class)
                .checkGetParameterizedType("int");
    }

    public void testEnumValueOf() throws Exception {
        Method method = TestEnum.class.getDeclaredMethod("valueOf", String.class);

        ExecutableTestHelper helper = new ExecutableTestHelper(method);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[java.lang.String arg0]")
                .checkParametersMetadataNotAvailable()
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(String.class)
                .checkGetParameterizedType("class java.lang.String");
    }

    public void testEnumValueOf_withMetadata() throws Exception {
        Class<?> clazz = loadTestInnerClassWithMetadata("TestEnum");
        Method method = clazz.getDeclaredMethod("valueOf", String.class);

        ExecutableTestHelper helper = new ExecutableTestHelper(method);
        helper.checkStandardParametersBehavior()
                // The extra space below are the result of a trivial upstream bug in
                // Parameter.toString() due to Modifier.toString(int) outputting nothing for
                // "MANDATED".
                .checkParametersToString("[ java.lang.String name]")
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkName(true /* expectedNameIsPresent */, "name")
                .checkModifiers(32768) // 32768 == Modifier.MANDATED
                .checkImplicitAndSynthetic(true, false)
                .checkGetType(String.class)
                .checkGetParameterizedType("class java.lang.String");
    }

    private static class SingleVarArgs {
        @SuppressWarnings("unused")
        SingleVarArgs(String... p0) {}

        @SuppressWarnings("unused")
        void varArgs(String... p0) {}
    }

    public void testSingleVarArgsConstructor() throws Exception {
        Constructor<?> constructor = SingleVarArgs.class.getDeclaredConstructor(String[].class);
        checkSingleVarArgsParameter(constructor);
    }

    public void testSingleVarArgsMethod() throws Exception {
        Method method = SingleVarArgs.class.getDeclaredMethod("varArgs", String[].class);
        checkSingleVarArgsParameter(method);
    }

    private static void checkSingleVarArgsParameter(Executable executable) {
        ExecutableTestHelper helper = new ExecutableTestHelper(executable);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[java.lang.String... arg0]")
                .checkParametersMetadataNotAvailable();

        helper.getParameterTestHelper(0)
                .checkGetType(String[].class)
                .checkIsVarArg(true)
                .checkGetParameterizedType("class [Ljava.lang.String;");
    }

    public void testSingleVarArgsConstructor_withMetadata() throws Exception {
        Class<?> clazz = loadTestInnerClassWithMetadata("SingleVarArgs");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String[].class);
        checkSingleVarArgsParameter_withMetadata(constructor);
    }

    public void testSingleVarArgsMethod_withMetadata() throws Exception {
        Class<?> clazz = loadTestInnerClassWithMetadata("SingleVarArgs");
        Method method = clazz.getDeclaredMethod("varArgs", String[].class);
        checkSingleVarArgsParameter_withMetadata(method);
    }

    private static void checkSingleVarArgsParameter_withMetadata(Executable executable) {
        ExecutableTestHelper helper = new ExecutableTestHelper(executable);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[java.lang.String... p0]");

        helper.getParameterTestHelper(0)
                .checkName(true /* expectedNameIsPresent */, "p0")
                .checkModifiers(0)
                .checkImplicitAndSynthetic(false, false)
                .checkGetType(String[].class)
                .checkIsVarArg(true)
                .checkGetParameterizedType("class [Ljava.lang.String;");
    }

    private static class MixedVarArgs {
        @SuppressWarnings("unused")
        MixedVarArgs(Integer[] p0, String... p1) {}
        @SuppressWarnings("unused")
        void both(Integer[] p0, String... p1) {}
    }

    public void testMixedVarArgsConstructor() throws Exception {
        Constructor<?> constructor =
                MixedVarArgs.class.getDeclaredConstructor(Integer[].class, String[].class);
        checkMixedVarArgsParameter(constructor);
    }

    public void testMixedVarArgsMethod() throws Exception {
        Method method = MixedVarArgs.class.getDeclaredMethod("both", Integer[].class, String[].class);
        checkMixedVarArgsParameter(method);
    }

    private static void checkMixedVarArgsParameter(Executable executable) {
        ExecutableTestHelper helper = new ExecutableTestHelper(executable);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[java.lang.Integer[] arg0, java.lang.String... arg1]")
                .checkParametersMetadataNotAvailable();

        helper.getParameterTestHelper(0)
                .checkGetType(Integer[].class)
                .checkIsVarArg(false)
                .checkGetParameterizedType("class [Ljava.lang.Integer;");

        helper.getParameterTestHelper(1)
                .checkGetType(String[].class)
                .checkIsVarArg(true)
                .checkGetParameterizedType("class [Ljava.lang.String;");
    }

    private static class NonVarArgs {
        @SuppressWarnings("unused")
        NonVarArgs(Integer[] p0) {}
        @SuppressWarnings("unused")
        void notVarArgs(Integer[] p0) {}
    }

    public void testNonVarsArgsConstructor() throws Exception {
        Constructor<?> constructor = NonVarArgs.class.getDeclaredConstructor(Integer[].class);
        checkNonVarsArgsParameter(constructor);
    }

    public void testNonVarsArgsMethod() throws Exception {
        Method method = NonVarArgs.class.getDeclaredMethod("notVarArgs", Integer[].class);
        checkNonVarsArgsParameter(method);
    }

    private static void checkNonVarsArgsParameter(Executable executable) {
        ExecutableTestHelper helper = new ExecutableTestHelper(executable);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[java.lang.Integer[] arg0]")
                .checkParametersMetadataNotAvailable();

        helper.getParameterTestHelper(0)
                .checkGetType(Integer[].class)
                .checkIsVarArg(false)
                .checkGetParameterizedType("class [Ljava.lang.Integer;");
    }

    public void testAnonymousClassConstructor() throws Exception {
        Class<?> outerClass = ParameterTest.class;
        Class<?> innerClass = getAnonymousClassWith1ParameterConstructor();
        Constructor<?> constructor = innerClass.getDeclaredConstructor(outerClass);

        ExecutableTestHelper helper = new ExecutableTestHelper(constructor);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[" + outerClass.getName() + " arg0]")
                .checkParametersMetadataNotAvailable()
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(outerClass)
                .checkGetParameterizedType("class " + outerClass.getName() + "");
    }

    private Class<?> getAnonymousClassWith1ParameterConstructor() {
        // Deliberately not implemented with a lambda. Do not refactor.
        Callable<String> anonymousClassObject = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return ParameterTest.this.outerClassMethod();
            }
        };
        return anonymousClassObject.getClass();
    }

    public void testAnonymousClassConstructor_withMetadata() throws Exception {
        Class<?> outerClass = loadTestOuterClassWithMetadata();
        Object outer = outerClass.newInstance();
        Class<?> innerClass = (Class<?>) outerClass.getDeclaredMethod(
                "getAnonymousClassWith1ParameterConstructor").invoke(outer);
        Constructor<?> constructor = innerClass.getDeclaredConstructor(outerClass);

        ExecutableTestHelper helper = new ExecutableTestHelper(constructor);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[final " + outerClass.getName() + " this$0]")
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkName(true /* expectedNameIsPresent */, "this$0")
                .checkModifiers(32784) // 32784 == Modifier.MANDATED & Modifier.FINAL
                .checkImplicitAndSynthetic(true, false)
                .checkGetType(outerClass)
                .checkGetParameterizedType("class " + outerClass.getName() + "");
    }

    public void testMethodClassConstructor() throws Exception {
        Class<?> outerClass = ParameterTest.class;
        Class<?> innerClass = getMethodClassWith1ImplicitParameterConstructor();
        Constructor<?> constructor = innerClass.getDeclaredConstructor(outerClass);

        ExecutableTestHelper helper = new ExecutableTestHelper(constructor);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[" + outerClass.getName() + " arg0]")
                .checkParametersMetadataNotAvailable()
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkGetType(outerClass)
                .checkGetParameterizedType("class " + outerClass.getName() + "");
    }

    private Class<?> getMethodClassWith1ImplicitParameterConstructor() {
        class MethodClass {
            MethodClass() {
                ParameterTest.this.outerClassMethod();
            }
        }
        return MethodClass.class;
    }

    public void testMethodClassConstructor_withMetadata() throws Exception {
        Class<?> outerClass = loadTestOuterClassWithMetadata();
        Object outer = outerClass.newInstance();
        Class<?> innerClass = (Class<?>) outerClass.getDeclaredMethod(
                "getMethodClassWith1ImplicitParameterConstructor").invoke(outer);
        Constructor<?> constructor = innerClass.getDeclaredConstructor(outerClass);

        ExecutableTestHelper helper = new ExecutableTestHelper(constructor);
        helper.checkStandardParametersBehavior()
                .checkParametersToString("[final " + outerClass.getName() + " this$0]")
                .checkParametersNoVarArgs();

        helper.getParameterTestHelper(0)
                .checkName(true /* expectedNameIsPresent */, "this$0")
                .checkModifiers(32784) // 32784 == Modifier.MANDATED & Modifier.FINAL
                .checkImplicitAndSynthetic(true, false)
                .checkGetType(outerClass)
                .checkGetParameterizedType("class " + outerClass.getName() + "");
    }

    private static class NonIdenticalParameters {
        @SuppressWarnings("unused")
        void method0(String p0) {}
        @SuppressWarnings("unused")
        void method1(String p0) {}
    }

    public void testEquals_checksExecutable() throws Exception {
        Method method0 = NonIdenticalParameters.class.getDeclaredMethod("method0", String.class);
        Method method1 = NonIdenticalParameters.class.getDeclaredMethod("method1", String.class);
        Parameter method0P0 = method0.getParameters()[0];
        Parameter method1P0 = method1.getParameters()[0];
        assertFalse(method0P0.equals(method1P0));
        assertFalse(method1P0.equals(method0P0));
        assertTrue(method0P0.equals(method0P0));
    }

    public void testManyParameters_withMetadata() throws Exception {
        int expectedParameterCount = 300;
        Class<?>[] parameterTypes = new Class[expectedParameterCount];
        Arrays.fill(parameterTypes, int.class);
        Method method = getMetadataVariationsMethod("manyParameters", parameterTypes);
        Parameter[] parameters = method.getParameters();
        assertEquals(expectedParameterCount, parameters.length);

        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setMinimumIntegerDigits(3);
        for (int i = 0; i < parameters.length; i++) {
            assertEquals(true, parameters[i].isNamePresent());
            assertEquals(Modifier.FINAL, parameters[i].getModifiers());
            assertEquals("a" + format.format(i), parameters[i].getName());
        }
    }

    public void testEmptyMethodParametersAnnotation_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("emptyMethodParametersAnnotation");
        assertEquals(0, method.getParameters().length);
    }

    public void testTooManyAccessFlags_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("tooManyAccessFlags", String.class);
        checkGetParametersThrowsMalformedParametersException(method);
    }

    public void testTooFewAccessFlags_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod(
                "tooFewAccessFlags", String.class, String.class);
        checkGetParametersThrowsMalformedParametersException(method);
    }

    public void testTooManyNames_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("tooManyNames", String.class);
        checkGetParametersThrowsMalformedParametersException(method);
    }

    public void testTooFewNames_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("tooFewNames", String.class, String.class);
        checkGetParametersThrowsMalformedParametersException(method);
    }

    public void testTooManyBoth_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("tooManyBoth", String.class);
        checkGetParametersThrowsMalformedParametersException(method);
    }

    public void testTooFewBoth_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("tooFewBoth", String.class, String.class);
        checkGetParametersThrowsMalformedParametersException(method);
    }

    public void testNullName_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("nullName", String.class);
        Parameter parameter0 = method.getParameters()[0];
        assertEquals("arg0", parameter0.getName());
        assertEquals(Modifier.FINAL, parameter0.getModifiers());
    }

    public void testEmptyName_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("emptyName", String.class);
        checkGetParametersThrowsMalformedParametersException(method);
    }

    public void testNameWithSemicolon_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("nameWithSemicolon", String.class);
        checkGetParametersThrowsMalformedParametersException(method);
    }

    public void testNameWithSlash_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("nameWithSlash", String.class);
        checkGetParametersThrowsMalformedParametersException(method);
    }

    public void testNameWithPeriod_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("nameWithPeriod", String.class);
        checkGetParametersThrowsMalformedParametersException(method);
    }

    public void testNameWithOpenSquareBracket_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("nameWithOpenSquareBracket", String.class);
        checkGetParametersThrowsMalformedParametersException(method);
    }

    public void testBadAccessModifier_withMetadata() throws Exception {
        Method method = getMetadataVariationsMethod("badAccessModifier", String.class);
        checkGetParametersThrowsMalformedParametersException(method);
    }

    public void testBadlyFormedAnnotation() throws Exception {
        Method method = getMetadataVariationsMethod("badlyFormedAnnotation", String.class);
        // Badly formed annotations are treated as if the annotation is entirely absent.
        Parameter parameter0 = method.getParameters()[0];
        assertFalse(parameter0.isNamePresent());
    }

    /** A non-static method that exists to be called by inner classes, lambdas, etc. */
    private String outerClassMethod() {
        return "Howdy";
    }

    private static class ExecutableTestHelper {
        private final Executable executable;

        ExecutableTestHelper(Executable executable) {
            this.executable = executable;
        }

        @DependsOnParameterMetadata
        ExecutableTestHelper checkParametersToString(String expectedString) {
            assertEquals(expectedString, Arrays.toString(executable.getParameters()));
            return this;
        }

        /**
         * Combines checks that should be true of any result from
         * {@link Executable#getParameters()}
         */
        ExecutableTestHelper checkStandardParametersBehavior() {
            return checkGetParametersClonesArray()
                    .checkParametersGetDeclaringExecutable()
                    .checkParametersEquals()
                    .checkParametersHashcode();
        }

        ExecutableTestHelper checkParametersGetDeclaringExecutable() {
            for (Parameter p : executable.getParameters()) {
                assertSame(executable, p.getDeclaringExecutable());
            }
            return this;
        }

        ExecutableTestHelper checkGetParametersClonesArray() {
            Parameter[] parameters1 = executable.getParameters();
            Parameter[] parameters2 = executable.getParameters();
            assertNotSame(parameters1, parameters2);

            assertEquals(parameters1.length, parameters2.length);
            for (int i = 0; i < parameters1.length; i++) {
                assertSame(parameters1[i], parameters2[i]);
            }
            return this;
        }

        ExecutableTestHelper checkParametersEquals() {
            Parameter[] parameters = executable.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                assertEquals(parameters[i], parameters[i]);
                if (i > 0) {
                    assertFalse(parameters[0].equals(parameters[i]));
                    assertFalse(parameters[i].equals(parameters[0]));
                }
            }
            return this;
        }

        ExecutableTestHelper checkParametersHashcode() {
            for (Parameter parameter : executable.getParameters()) {
                // Not much to assert. Just call the method and check it is consistent.
                assertEquals(parameter.hashCode(), parameter.hashCode());
            }
            return this;
        }

        @DependsOnParameterMetadata
        ExecutableTestHelper checkParametersMetadataNotAvailable() {
            ParameterTestHelper[] parameterTestHelpers = getParameterTestHelpers();
            for (int i = 0; i < parameterTestHelpers.length; i++) {
                ParameterTestHelper parameterTestHelper = parameterTestHelpers[i];
                parameterTestHelper.checkName(false, "arg" + i)
                        .checkImplicitAndSynthetic(false, false)
                        .checkModifiers(0);
            }
            return this;
        }

        /**
         * Checks that non of the parameters return {@code true} for {@link Parameter#isVarArgs()}.
         */
        ExecutableTestHelper checkParametersNoVarArgs() {
            for (ParameterTestHelper parameterTestHelper : getParameterTestHelpers()) {
                parameterTestHelper.checkIsVarArg(false);
            }
            return this;
        }

        ParameterTestHelper getParameterTestHelper(int index) {
            return new ParameterTestHelper(executable.getParameters()[index]);
        }

        private ParameterTestHelper[] getParameterTestHelpers() {
            final int parameterCount = executable.getParameterCount();
            ParameterTestHelper[] parameterTestHelpers = new ParameterTestHelper[parameterCount];
            for (int i = 0; i < parameterCount; i++) {
                parameterTestHelpers[i] = getParameterTestHelper(i);
            }
            return parameterTestHelpers;
        }

        private static class ParameterTestHelper {
            private final Parameter parameter;

            ParameterTestHelper(Parameter parameter) {
                this.parameter = parameter;
            }

            ParameterTestHelper checkGetType(Class<?> expectedType) {
                assertEquals(expectedType, parameter.getType());
                return this;
            }

            @DependsOnParameterMetadata
            ParameterTestHelper checkName(boolean expectedIsNamePresent, String expectedName) {
                assertEquals(expectedIsNamePresent, parameter.isNamePresent());
                assertEquals(expectedName, parameter.getName());
                return this;
            }

            @DependsOnParameterMetadata
            ParameterTestHelper checkModifiers(int expectedModifiers) {
                assertEquals(expectedModifiers, parameter.getModifiers());
                return this;
            }

            ParameterTestHelper checkGetParameterizedType(String expectedParameterizedTypeString) {
                assertEquals(
                        expectedParameterizedTypeString,
                        parameter.getParameterizedType().toString());
                return this;
            }

            @DependsOnParameterMetadata
            ParameterTestHelper checkImplicitAndSynthetic(
                    boolean expectedIsImplicit, boolean expectedIsSynthetic) {
                assertEquals(expectedIsImplicit, parameter.isImplicit());
                assertEquals(expectedIsSynthetic, parameter.isSynthetic());
                return this;
            }

            ParameterTestHelper checkIsVarArg(boolean expectedIsVarArg) {
                assertEquals(expectedIsVarArg, parameter.isVarArgs());
                return this;
            }
        }
    }

    private static ClassLoader createClassLoaderForResource(File destDir, String resourcePath)
            throws Exception {
        String fileName = new File(resourcePath).getName();
        File dexOrJarFile = new File(destDir, fileName);
        copyResource(resourcePath, dexOrJarFile);
        return new PathClassLoader(
                dexOrJarFile.getAbsolutePath(), ClassLoader.getSystemClassLoader());
    }

    /**
     * Copy a resource in the libcore/java/lang/reflect/parameter/ resource path to the indicated
     * target file.
     */
    private static void copyResource(String resourcePath, File destination) throws Exception {
        assertFalse(destination.exists());
        ClassLoader classLoader = ParameterTest.class.getClassLoader();
        assertNotNull(classLoader);

        try (InputStream in = classLoader.getResourceAsStream(resourcePath);
             FileOutputStream out = new FileOutputStream(destination)) {
            if (in == null) {
                throw new IllegalStateException("Resource not found: " + resourcePath);
            }
            Streams.copy(in, out);
        }
    }

    /**
     * Loads an inner class from the ParameterMetadataTestClasses class defined in a separate dex
     * file. See src/test/java/libcore/java/lang/reflect/parameter/ for the associated source code.
     */
    private Class<?> loadTestInnerClassWithMetadata(String name) throws Exception {
        return classesWithMetadataClassLoader.loadClass(
                "libcore.java.lang.reflect.parameter.ParameterMetadataTestClasses$" + name);
    }

    /**
     * Loads the ParameterMetadataTestClasses class defined in a separate dex file.
     * See src/test/java/libcore/java/lang/reflect/parameter/ for the associated source code.
     */
    private Class<?> loadTestOuterClassWithMetadata() throws Exception {
        return classesWithMetadataClassLoader.loadClass(
                "libcore.java.lang.reflect.parameter.ParameterMetadataTestClasses");
    }

    /**
     * Loads a method from the MetadataVariations class defined in a separate dex file. See
     * src/test/java/libcore/java/lang/reflect/parameter/ for the associated source code.
     */
    private Method getMetadataVariationsMethod(String methodName, Class<?>... parameterTypes)
            throws Exception {
        Class<?> metadataVariationsClass = metadataVariationsClassLoader.loadClass(
                "libcore.java.lang.reflect.parameter.MetadataVariations");
        return metadataVariationsClass.getDeclaredMethod(methodName, parameterTypes);
    }

    private static void checkGetParametersThrowsMalformedParametersException(Method method) {
        try {
            method.getParameters();
            fail();
        } catch (MalformedParametersException expected) {}
    }
}
