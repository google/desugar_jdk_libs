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

package libcore.libcore.reflect;

import com.google.common.base.Joiner;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Regression tests for http://b/124315589
 */
public class ParameterizedTypeRegressionTest {

    // A set of nested classes used by some of the tests in this class.
    class A<X> {
        class B<Y> {
            class C1<Z> {
                // Leaf not generic.
                class D1 {
                }
            }
            // Non-leaf not generic.
            class C2 {
                class D2<Z> {
                }
            }
        }
    }

    @Test
    public void testNoGeneric() {
        Type actual = new ParameterizedClass<List>() {
            // Anonymous class declaration.
        }.getArgumentType();
        Class<List> expected = List.class;
        Assert.assertEquals(expected, actual);
        Assert.assertEquals("interface java.util.List", actual.toString());
    }

    @Test
    public void testGeneric() {
        Type actual = new ParameterizedClass<List<Integer>>() {
            // Anonymous class declaration.
        }.getArgumentType();
        ParameterizedTypeImpl expected = new ParameterizedTypeImpl(
                null,
                List.class,
                Integer.class
        );
        Assert.assertEquals(expected, actual);
        Assert.assertEquals("java.util.List<java.lang.Integer>", actual.toString());
    }

    @Test
    public void testGenericOfGeneric() {
        Type actual = new ParameterizedClass<List<Map<String, Float>>>() {
            // Anonymous class declaration.
        }.getArgumentType();
        ParameterizedTypeImpl expected = new ParameterizedTypeImpl(
                null,
                List.class,
                new ParameterizedTypeImpl(
                        null,
                        Map.class,
                        String.class,
                        Float.class)
        );
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(
                "java.util.List<java.util.Map<java.lang.String, java.lang.Float>>",
                actual.toString());
    }

    @Test
    public void testNested1() {
        Type actual = new ParameterizedClass<A<Integer>>() {
            // Anonymous class declaration.
        }.getArgumentType();
        ParameterizedTypeImpl expected = new ParameterizedTypeImpl(
                ParameterizedTypeRegressionTest.class,
                A.class,
                Integer.class
        );
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(
                "libcore.libcore.reflect.ParameterizedTypeRegressionTest$A<java.lang.Integer>",
                actual.toString());
    }

    @Test
    public void testNested2() {
        Type actual = new ParameterizedClass<A<Integer>.B<Float>>() {
            // Anonymous class declaration.
        }.getArgumentType();
        ParameterizedTypeImpl expected = new ParameterizedTypeImpl(
                new ParameterizedTypeImpl(
                        ParameterizedTypeRegressionTest.class,
                        A.class,
                        Integer.class),
                A.B.class,
                Float.class
        );
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(
                "libcore.libcore.reflect.ParameterizedTypeRegressionTest$A<java.lang.Integer>"
                        + "$B<java.lang.Float>",
                actual.toString());
    }

    @Test
    public void testNested3() {
        Type actual = new ParameterizedClass<A<Integer>.B<Float>.C1<String>>() {
            // Anonymous class declaration.
        }.getArgumentType();
        ParameterizedTypeImpl expected = new ParameterizedTypeImpl(
                new ParameterizedTypeImpl(
                        new ParameterizedTypeImpl(
                                ParameterizedTypeRegressionTest.class,
                                A.class,
                                Integer.class),
                        A.B.class,
                        Float.class),
                A.B.C1.class,
                String.class);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(
                "libcore.libcore.reflect.ParameterizedTypeRegressionTest$A<java.lang.Integer>"
                        + "$B<java.lang.Float>"
                        + "$C1<java.lang.String>",
                actual.toString());
    }

    @Test
    public void testNested4_nonGenericLeaf() {
        // This anonymous class has a non-generic leaf class (D).
        Type actual = new ParameterizedClass<A<Integer>.B<Float>.C1<String>.D1>() {
            // Anonymous class declaration.
        }.getArgumentType();
        ParameterizedTypeImpl expected = new ParameterizedTypeImpl(
                new ParameterizedTypeImpl(
                        new ParameterizedTypeImpl(
                                new ParameterizedTypeImpl(
                                        ParameterizedTypeRegressionTest.class,
                                        A.class,
                                        Integer.class),
                                A.B.class,
                                Float.class),
                        A.B.C1.class,
                        String.class),
                A.B.C1.D1.class
        );
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(
                "libcore.libcore.reflect.ParameterizedTypeRegressionTest$A<java.lang.Integer>"
                        + "$B<java.lang.Float>$C1<java.lang.String>$D1",
                actual.toString());
    }

    @Test
    public void testNested4_nonGenericNonLeaf() {
        // This anonymous class has a non-generic class (C2).
        Type actual = new ParameterizedClass<A<Integer>.B<Float>.C2.D2<String>>() {
            // Anonymous class declaration.
        }.getArgumentType();
        ParameterizedTypeImpl expected = new ParameterizedTypeImpl(
                new ParameterizedTypeImpl(
                        new ParameterizedTypeImpl(
                                new ParameterizedTypeImpl(
                                        ParameterizedTypeRegressionTest.class,
                                        A.class,
                                        Integer.class),
                                A.B.class,
                                Float.class),
                        A.B.C2.class),
                A.B.C2.D2.class,
                String.class
        );
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(
                "libcore.libcore.reflect.ParameterizedTypeRegressionTest$A<java.lang.Integer>"
                        + "$B<java.lang.Float>$C2$D2<java.lang.String>",
                actual.toString());
    }

    private static class ParameterizedClass<T> {
        public Type getArgumentType() {
            Class<?> parameterizedClass = getClass();
            return ((ParameterizedType) parameterizedClass.getGenericSuperclass())
                    .getActualTypeArguments()[0];
        }
    }

    /**
     * A straightforward implementation of ParameterizedType that implements equals() and can be
     * used when comparing against the platform behavior.
     */
    private static class ParameterizedTypeImpl implements ParameterizedType {
        private final Type ownerType;
        private final Class<?> rawType;
        private final Type[] typeArguments;

        ParameterizedTypeImpl(Type ownerType, Class<?> rawType, Type... typeArguments) {
            this.ownerType = ownerType;
            this.rawType = rawType;
            this.typeArguments = typeArguments;
        }

        @Override
        public Class getRawType() {
            return rawType;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return typeArguments;
        }

        @Override
        public Type getOwnerType() {
            return ownerType;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            if (ownerType != null) {
                stringBuilder.append(ownerType.getTypeName());
                stringBuilder.append('$');
                stringBuilder.append(rawType.getSimpleName());
            } else {
                stringBuilder.append(rawType.getName());
            }
            stringBuilder.append('<');
            List<String> typeArgumentNames = Arrays.stream(typeArguments)
                    .map(Type::getTypeName)
                    .collect(Collectors.toList());
            stringBuilder.append(Joiner.on(", ").join(typeArgumentNames));
            stringBuilder.append('>');
            return stringBuilder.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType other = (ParameterizedType) obj;
            return getRawType().equals(other.getRawType()) &&
                    Arrays.equals(getActualTypeArguments(), other.getActualTypeArguments()) &&
                    Objects.equals(getOwnerType(), other.getOwnerType());
        }
    }
}
