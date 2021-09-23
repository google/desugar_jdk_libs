/*
 * Copyright (C) 2017 The Android Open Source Project
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

package libcore.java.lang.reflect.parameter;

import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * This class is used by {@link libcore.java.lang.reflect.ParameterTest}. It contains various
 * anonymous class / constructor / method definitions used by the test. This class must be compiled
 * using a toolchain that preserves the parameter metadata as far as the .dex file used on the
 * device. Parameter metadata is not expected to be preserved by the toolchain by default so this
 * is built with a special target.
 */
public class ParameterMetadataTestClasses {
    static class SingleParameter {
        SingleParameter(String p0) {}

        void oneParameter(String p0) {}
    }

    static class GenericParameter {
        GenericParameter(Function<String, Integer> p0) {}

        void genericParameter(Function<String, Integer> p0) {}
    }

    static class TwoParameters {
        TwoParameters(String p0, Integer p1) {}

        void twoParameters(String p0, Integer p1) {}
    }

    static class FinalParameter {
        FinalParameter(final String p0) {}

        void finalParameter(final String p0) {}
    }

    class InnerClass {
        public InnerClass() {}

        public InnerClass(String p1) {}

        public InnerClass(Function<String, Integer> p1) {}
    }

    enum TestEnum { ONE, TWO }

    static class SingleVarArgs {
        SingleVarArgs(String... p0) {}

        void varArgs(String... p0) {}
    }

    static class MixedVarArgs {
        MixedVarArgs(Integer[] p0, String... p1) {}

        void both(Integer[] p0, String... p1) {}
    }

    static class NonVarArgs {
        NonVarArgs(Integer[] p0) {}

        void notVarArgs(Integer[] p0) {}
    }

    static class NonIdenticalParameters {
        void method0(String p1) {}

        void method1(String p1) {}
    }

    private String outerClassMethod() {
        return "Howdy";
    }

    public Class<?> getAnonymousClassWith1ParameterConstructor() {
        // Deliberately not implemented with a lambda. Do not refactor.
        Callable<String> anonymousClassObject = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return ParameterMetadataTestClasses.this.outerClassMethod();
            }
        };
        return anonymousClassObject.getClass();
    }

    public Class<?> getMethodClassWith1ImplicitParameterConstructor() {
        class MethodClass {
            MethodClass() {
                ParameterMetadataTestClasses.this.outerClassMethod();
            }
        }
        return MethodClass.class;
    }
}
