/*
 * Copyright (c) 2021 Google LLC
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Google designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Google in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.google.testing.junit.adapter.testng;

import static com.google.testing.junit.adapter.testng.DataProviderKey.fromDataConsumer;

import com.google.common.collect.ImmutableList;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

/** Static utility methods relating to {@link org.testng.annotations.DataProvider}. */
class DataProviderHelper {

  private static final Method ZERO_ARG_PROVIDER_METHOD = zeroArgProviderMethod();

  private DataProviderHelper() {}

  public static ImmutableList<ParameterInputEntry> findParameterInputs(Executable method) {
    return isDataConsumer(method)
        ? DataProviderKey.fromDataConsumer(method).getDatasets()
        : ImmutableList.of(
            ParameterInputEntry.create(
                ParameterizedMemberTag.builder()
                    .setMember(ZERO_ARG_PROVIDER_METHOD)
                    .setInputSerialNumber(0)
                    .setOutputSerialNumber(0)
                    .build(),
                new Object[0]));
  }

  public static Method zeroArgProviderMethod() {
    try {
      return DataProviderHelper.class.getDeclaredMethod("zeroArgProvider");
    } catch (NoSuchMethodException e) {
      throw new AssertionError(e);
    }
  }

  public static Object[] zeroArgProvider() {
    return new Object[0];
  }

  public static boolean isDataConsumer(Executable method) {
    Test testMethodSpec = method.getDeclaredAnnotation(Test.class);
    if (testMethodSpec != null) {
      return !testMethodSpec.dataProvider().isEmpty();
    }
    Factory factoryMethodSpec = method.getDeclaredAnnotation(Factory.class);
    if (factoryMethodSpec != null) {
      return !factoryMethodSpec.dataProvider().isEmpty();
    }
    return false;
  }

  /**
   * Creates all factories for the given class literal, include both @Factory-annotated methods and
   * the zero-arg default constructor if any.
   */
  public static ImmutableList<SingleInstanceFactory> createInstanceFactories(
      Class<?> instanceType, List<Throwable> errors) {
    ImmutableList.Builder<SingleInstanceFactory> instanceFactoriesBuilder = ImmutableList.builder();
    for (Method method : instanceType.getDeclaredMethods()) {
      if (method.isAnnotationPresent(Factory.class)) {
        if (!Modifier.isStatic(method.getModifiers())) {
          errors.add(
              new IllegalStateException(
                  String.format("Expected a factory method %s to be static.", method)));
        }
        ImmutableList<ParameterInputEntry> parameterInputs = findParameterInputs(method);
        if (method.getReturnType().isArray()) {
          for (int i = 0, inputSize = parameterInputs.size(); i < inputSize; i++) {
            Object[] parameterData = parameterInputs.get(i).payload();
            ParameterizedMemberTag baseTag =
                ParameterizedMemberTag.builder()
                    .setMember(method)
                    .setInputSerialNumber(i)
                    .setOutputSerialNumber(Integer.MIN_VALUE)
                    .build();
            try {
              instanceFactoriesBuilder.addAll(
                  SingleInstanceFactory.fromTestInstanceArrayFactory(
                      baseTag, () -> (Object[]) method.invoke(null, parameterData)));
            } catch (ReflectiveOperationException e) {
              errors.add(e);
            }
          }
        } else {
          for (int i = 0, inputSize = parameterInputs.size(); i < inputSize; i++) {
            Object[] parameterData = parameterInputs.get(i).payload();
            instanceFactoriesBuilder.add(
                SingleInstanceFactory.create(
                    ParameterizedMemberTag.builder()
                        .setMember(method)
                        .setInputSerialNumber(i)
                        .setOutputSerialNumber(0)
                        .build(),
                    () -> method.invoke(null, parameterData)));
          }
        }
      }
    }

    for (Constructor<?> constructor : instanceType.getConstructors()) {
      if (constructor.getParameterCount() == 0) {
        ParameterizedMemberTag tag =
            ParameterizedMemberTag.builder()
                .setMember(constructor)
                .setInputSerialNumber(0)
                .setOutputSerialNumber(0)
                .build();
        instanceFactoriesBuilder.add(SingleInstanceFactory.create(tag, constructor::newInstance));
      } else if (constructor.isAnnotationPresent(Factory.class)) {
        ImmutableList<ParameterInputEntry> allDatasets =
            fromDataConsumer(constructor).getDatasets();
        for (int i = 0, allDatasetsSize = allDatasets.size(); i < allDatasetsSize; i++) {
          Object[] parameterData = allDatasets.get(i).payload();
          ParameterizedMemberTag.Builder tag =
              ParameterizedMemberTag.builder()
                  .setMember(constructor)
                  .setInputSerialNumber(i)
                  .setOutputSerialNumber(0);
          instanceFactoriesBuilder.add(
              SingleInstanceFactory.create(
                  tag.build(), () -> constructor.newInstance(parameterData)));
        }
      }
    }

    ImmutableList<SingleInstanceFactory> instanceFactories = instanceFactoriesBuilder.build();
    if (instanceFactories.isEmpty()) {
      errors.add(
          new IllegalStateException(
              String.format(
                  "Failed to find a factory method or constructor to create instances for %s",
                  instanceType)));
    }
    return instanceFactories;
  }
}
