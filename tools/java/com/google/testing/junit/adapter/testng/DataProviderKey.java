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

import static com.google.testing.junit.adapter.testng.DataProviderHelper.findParameterInputs;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

/** Describes the key that indexes a data provider. */
@AutoValue
abstract class DataProviderKey {

  abstract Method providerMethod();

  private static DataProviderKey create(Class<?> providerClass, String providerName) {
    for (Method method : providerClass.getDeclaredMethods()) {
      DataProvider dataProviderSpec = method.getDeclaredAnnotation(DataProvider.class);
      if (dataProviderSpec != null) {
        String dataProviderLabel =
            dataProviderSpec.name().isEmpty() ? method.getName() : dataProviderSpec.name();
        if (dataProviderLabel.equals(providerName)) {
          method.trySetAccessible();
          return new AutoValue_DataProviderKey(method);
        }
      }
    }
    throw new NoSuchMethodError(
        String.format(
            "Expected Data Provider for (%s#%s) present, but not found.",
            providerClass, providerName));
  }

  public static DataProviderKey fromDataConsumer(Executable method) {
    Preconditions.checkState(
        DataProviderHelper.isDataConsumer(method),
        "Expected a data consumer method, but gets %s",
        method);

    final Class<?> dataProviderClass;
    final String dataProviderName;

    if (method.isAnnotationPresent(Test.class)) {
      Test methodSpec = method.getDeclaredAnnotation(Test.class);
      dataProviderName = methodSpec.dataProvider();
      dataProviderClass =
          methodSpec.dataProviderClass() == Object.class
              ? method.getDeclaringClass()
              : methodSpec.dataProviderClass();
      return create(dataProviderClass, dataProviderName);
    }

    if (method.isAnnotationPresent(Factory.class)) {
      Factory methodSpec = method.getDeclaredAnnotation(Factory.class);
      dataProviderName = methodSpec.dataProvider();
      dataProviderClass =
          methodSpec.dataProviderClass() == Object.class
              ? method.getDeclaringClass()
              : methodSpec.dataProviderClass();
      return create(dataProviderClass, dataProviderName);
    }

    throw new AssertionError(String.format("Failed to extract DataProviderKey from %s", method));
  }

  @Memoized
  public boolean isStaticProvider() {
    return Modifier.isStatic(providerMethod().getModifiers());
  }

  @Memoized
  public ImmutableList<Object> availableProviderClassInstances() {
    if (isStaticProvider()) {
      return ImmutableList.of();
    }
    ImmutableList<SingleInstanceFactory> providerClassFactories =
        DataProviderHelper.createInstanceFactories(
            providerMethod().getDeclaringClass(), new ArrayList<>());
    ImmutableList.Builder<Object> providerClassInstances = ImmutableList.builder();
    for (SingleInstanceFactory factory : providerClassFactories) {
      try {
        providerClassInstances.add(factory.call());
      } catch (ReflectiveOperationException e) {
        throw new LinkageError(e.getMessage(), e);
      }
    }
    return providerClassInstances.build();
  }

  private void evaluateBeforeMethods(Object providerInstance) {
    for (Method method : providerInstance.getClass().getMethods()) {
      for (Class<? extends Annotation> annotation :
          ImmutableList.of(BeforeTest.class, BeforeClass.class, BeforeMethod.class)) {
        if (method.isAnnotationPresent(annotation)) {
          try {
            method.invoke(providerInstance);
            break;
          } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
          }
        }
      }
    }
  }

  @Memoized
  public int dimension() {
    return getDatasets().size();
  }

  @Memoized
  public ImmutableList<ParameterInputEntry> getDatasets() {
    ImmutableList.Builder<ParameterInputEntry> dataContainer = ImmutableList.builder();
    ImmutableList<ParameterInputEntry> parameterInputs = findParameterInputs(providerMethod());
    List<Object> dataProviderOwnerInstances =
        isStaticProvider() ? Collections.singletonList(null) : availableProviderClassInstances();
    for (Object providerInstance : dataProviderOwnerInstances) {
      if (!isStaticProvider()) {
        evaluateBeforeMethods(providerInstance);
      }
      for (ParameterInputEntry parameterInput : parameterInputs) {
        try {
          Class<?> providerReturnType = providerMethod().getReturnType();
          Object providerResult =
              providerMethod().invoke(providerInstance, parameterInput.payload());
          if (Object[][].class.isAssignableFrom(providerReturnType)) {
            Object[][] rawOutputs = (Object[][]) providerResult;
            for (int i = 0, rawDatasetsLength = rawOutputs.length; i < rawDatasetsLength; i++) {
              ParameterizedMemberTag tag =
                  ParameterizedMemberTag.builder()
                      .setMember(providerMethod())
                      .setInputSerialNumber(parameterInput.tag().outputSerialNumber())
                      .setOutputSerialNumber(i)
                      .build();
              dataContainer.add(ParameterInputEntry.create(tag, rawOutputs[i]));
            }
          } else if (Iterator.class.isAssignableFrom(providerReturnType)) {
            Iterator<Object[]> rawOutputs = (Iterator<Object[]>) providerResult;
            for (int i = 0; rawOutputs.hasNext(); i++) {
              Object[] rawOutput = rawOutputs.next();
              ParameterizedMemberTag tag =
                  ParameterizedMemberTag.builder()
                      .setMember(providerMethod())
                      .setInputSerialNumber(parameterInput.tag().outputSerialNumber())
                      .setOutputSerialNumber(i)
                      .build();
              dataContainer.add(ParameterInputEntry.create(tag, rawOutput));
            }
          } else {
            throw new UnsupportedOperationException(
                String.format("Unsupported return type for data provider: %s", providerMethod()));
          }
        } catch (ReflectiveOperationException | ClassCastException e) {
          throw new AssertionError(
              String.format("Failed at %s.\n%s", providerMethod(), e.getCause()));
        }
      }
    }
    return dataContainer.build();
  }
}
