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

package com.google.testing.junit;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/** A JUnit4 runner that supports executing TestNg tests on a JUnit4 engine. */
public final class TestNgJUnit4 extends BlockJUnit4ClassRunner {

  private static final ImmutableSet<Class<? extends Annotation>> SUPPORTED_TEST_NG_ANNOTATIONS =
      ImmutableSet.of(
          BeforeTest.class,
          BeforeClass.class,
          BeforeMethod.class,
          Test.class,
          AfterMethod.class,
          AfterClass.class,
          AfterTest.class,
          DataProvider.class);

  /**
   * All {@link DataProvider}-annotated methods for method parameterization. The map key is the data
   * provider's name from {@link DataProvider#name()}
   */
  private ImmutableMap<String, Method> dataProviders;

  /**
   * The first-level dimension for each data provider. See {@link #dataProviders} for map key
   * details.
   */
  private ImmutableMap<String, Integer> dataDimensions;

  public TestNgJUnit4(Class<?> testClass) throws Exception {
    super(testClass);
  }

  private static ImmutableMap<String, Method> validateDependentDataProviders(
      TestClass testClass, List<Throwable> errors) {
    ImmutableMap.Builder<String, Method> dataProvidersBuilder = ImmutableMap.builder();
    for (FrameworkMethod frameworkMethod : testClass.getAnnotatedMethods(Test.class)) {
      Test testAnnotation = frameworkMethod.getAnnotation(Test.class);
      String dataProviderName = testAnnotation.dataProvider();
      if (!dataProviderName.isEmpty()) {
        Class<?> dataProviderClass = testAnnotation.dataProviderClass();
        if (dataProviderClass == Object.class) {
          dataProviderClass = testClass.getJavaClass();
        }
        Method dataProviderMethod = null;
        for (Method method : dataProviderClass.getMethods()) {
          DataProvider dataProviderAnnotation = method.getDeclaredAnnotation(DataProvider.class);
          if (dataProviderAnnotation != null
              && dataProviderAnnotation.name().equals(dataProviderName)) {
            dataProviderMethod = method;
            break;
          }
        }
        if (dataProviderMethod != null) {
          dataProvidersBuilder.put(dataProviderName, dataProviderMethod);
        } else {
          errors.add(
              new NoSuchMethodException(
                  String.format(
                      "Data Provider named %s on Class %s not found",
                      dataProviderName, dataProviderClass)));
        }
      }
    }
    return dataProvidersBuilder.build();
  }

  @Override
  protected void collectInitializationErrors(List<Throwable> errors) {
    TestClass testClass = getTestClass();
    validateSupportedTestNgAnnotations(testClass, errors);
    dataProviders = validateDependentDataProviders(testClass, errors);
    dataDimensions = validateDataGeneration(errors);
    super.collectInitializationErrors(errors);
  }

  private ImmutableMap<String, Integer> validateDataGeneration(List<Throwable> errors) {
    ImmutableMap.Builder<String, Integer> dataDimensions = ImmutableMap.builder();
    for (Map.Entry<String, Method> dataProvider : dataProviders.entrySet()) {
      try {
        String dataProviderName = dataProvider.getKey();
        Method evaluationMethod = dataProvider.getValue();
        Constructor<?> constructor = evaluationMethod.getDeclaringClass().getDeclaredConstructor();
        constructor.setAccessible(true);
        Object dataProviderHost = constructor.newInstance();
        Object[][] evaluatedData = (Object[][]) evaluationMethod.invoke(dataProviderHost);
        dataDimensions.put(dataProviderName, evaluatedData.length);
      } catch (Throwable e) {
        errors.add(e);
      }
    }
    return dataDimensions.build();
  }

  private static void validateSupportedTestNgAnnotations(
      TestClass testClass, List<Throwable> errors) {
    for (Class<?> cKlass = testClass.getJavaClass();
        cKlass != null;
        cKlass = cKlass.getSuperclass()) {
      for (Method member : cKlass.getDeclaredMethods()) {
        for (Annotation annotation : member.getDeclaredAnnotations()) {
          Class<? extends Annotation> annotationType = annotation.annotationType();
          if (annotationType.getName().startsWith("org.testng.annotations")
              && !SUPPORTED_TEST_NG_ANNOTATIONS.contains(annotationType)) {
            errors.add(
                new UnsupportedOperationException(
                    String.format(
                        "TestNg Annotation %s on %s is yet supported", annotation, member)));
          }
        }
      }
      for (Field member : cKlass.getDeclaredFields()) {
        for (Annotation annotation : member.getDeclaredAnnotations()) {
          Class<? extends Annotation> annotationType = annotation.annotationType();
          if (annotationType.getName().startsWith("org.testng.annotations")
              && !SUPPORTED_TEST_NG_ANNOTATIONS.contains(annotationType)) {
            errors.add(
                new UnsupportedOperationException(
                    String.format(
                        "TestNg Annotation %s on %s is yet supported", annotation, member)));
          }
        }
      }
    }
  }

  @Override
  protected Statement withBefores(FrameworkMethod method, Object target, Statement statement) {
    List<FrameworkMethod> befores = new ArrayList<>();
    befores.addAll(getTestClassAnnotatedMethods(BeforeTest.class));
    befores.addAll(getTestClassAnnotatedMethods(BeforeClass.class));
    befores.addAll(getTestClassAnnotatedMethods(BeforeMethod.class));
    return befores.isEmpty() ? statement : new RunBefores(statement, befores, target);
  }

  @Override
  protected List<FrameworkMethod> computeTestMethods() {
    List<FrameworkMethod> baseFrameworkMethods =
        this.getTestClass().getAnnotatedMethods(Test.class);
    List<FrameworkMethod> frameworkMethods = new ArrayList<>();
    for (FrameworkMethod frameworkMethod : baseFrameworkMethods) {
      Method reflectMethod = frameworkMethod.getMethod();
      Test testAnnotation = reflectMethod.getDeclaredAnnotation(Test.class);
      String dataProviderName = testAnnotation.dataProvider();
      if (dataProviderName.isEmpty()) {
        frameworkMethods.add(frameworkMethod);
      } else {
        int dataDim = dataDimensions.get(dataProviderName);
        for (int i = 0; i < dataDim; i++) {
          frameworkMethods.add(new TestMethodWithDataProvider(reflectMethod, dataProviderName, i));
        }
      }
    }
    return frameworkMethods;
  }

  @Override
  protected Statement methodInvoker(FrameworkMethod method, Object test) {
    if (method instanceof TestMethodWithDataProvider) {
      TestMethodWithDataProvider parameterizedMethod = (TestMethodWithDataProvider) method;
      try {
        return new InvokeMethodWithParams(
            method, test, parameterizedMethod.evaluateOnDataProvider(test, dataProviders::get));
      } catch (ReflectiveOperationException e) {
        throw new IllegalStateException(e);
      }
    }
    return super.methodInvoker(method, test);
  }

  @Override
  protected Statement withAfters(FrameworkMethod method, Object target, Statement statement) {
    List<FrameworkMethod> afters = new ArrayList<>();
    afters.addAll(getTestClassAnnotatedMethods(org.testng.annotations.AfterMethod.class));
    afters.addAll(getTestClassAnnotatedMethods(org.testng.annotations.AfterClass.class));
    afters.addAll(getTestClassAnnotatedMethods(org.testng.annotations.AfterTest.class));
    return afters.isEmpty() ? statement : new RunAfters(statement, afters, target);
  }

  private List<FrameworkMethod> getTestClassAnnotatedMethods(
      Class<? extends Annotation> annotationClass) {
    return this.getTestClass().getAnnotatedMethods(annotationClass);
  }

  /** Describes a test method with a particular data set. */
  static class TestMethodWithDataProvider extends FrameworkMethod {

    private final String dataProviderName;
    private final int dataSetIndex;

    public TestMethodWithDataProvider(Method method, String dataProvider, int dataSetIndex) {
      super(method);
      this.dataProviderName = dataProvider;
      this.dataSetIndex = dataSetIndex;
    }

    public Object[] evaluateOnDataProvider(
        Object testInstance, Function<String, Method> dataProviders)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
      Method dataProviderMethod = dataProviders.apply(dataProviderName);
      final Object[][] dataContainer;
      if (Modifier.isStatic(dataProviderMethod.getModifiers())) {
        dataContainer = (Object[][]) dataProviderMethod.invoke(null);
      } else if (dataProviderMethod.getDeclaringClass().isAssignableFrom(testInstance.getClass())) {
        dataContainer = (Object[][]) dataProviderMethod.invoke(testInstance);
      } else {
        Constructor<?> constructor =
            dataProviderMethod.getDeclaringClass().getDeclaredConstructor();
        constructor.setAccessible(true);
        Object dataProviderHost = constructor.newInstance();
        dataContainer = (Object[][]) dataProviderMethod.invoke(dataProviderHost);
      }
      return dataContainer[dataSetIndex];
    }

    @Override
    public String getName() {
      return super.getName() + "_" + dataProviderName + "_" + dataSetIndex;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (obj instanceof TestMethodWithDataProvider) {
        TestMethodWithDataProvider that = (TestMethodWithDataProvider) obj;
        return this.dataProviderName.equals(that.dataProviderName)
            && this.dataSetIndex == that.dataSetIndex
            && this.getMethod().equals(that.getMethod());
      }
      return false;
    }

    @Override
    public int hashCode() {
      return Objects.hash(getMethod(), dataProviderName, dataSetIndex);
    }

    @Override
    public String toString() {
      return super.toString() + "_" + dataProviderName + "_" + dataSetIndex;
    }
  }

  /**
   * Used in replacement of {@link org.junit.internal.runners.statements.InvokeMethod} for @Test
   * method execution.
   */
  static class InvokeMethodWithParams extends Statement {

    private final FrameworkMethod testMethod;
    private final Object target;
    private final Object[] params;

    InvokeMethodWithParams(FrameworkMethod testMethod, Object target, Object... params) {
      this.testMethod = testMethod;
      this.target = target;
      this.params = params;
    }

    @Override
    public void evaluate() throws Throwable {
      testMethod.invokeExplosively(target, params);
    }
  }
}
