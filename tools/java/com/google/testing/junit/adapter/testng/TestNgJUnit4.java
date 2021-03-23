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
import static com.google.testing.junit.adapter.testng.DataProviderKey.fromDataConsumer;
import static com.google.testing.junit.adapter.testng.JUnitStatements.ComposedStatement;
import static com.google.testing.junit.adapter.testng.JUnitStatements.EmptyStatement;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.internal.runners.statements.Fail;
import org.junit.internal.runners.statements.InvokeMethod;
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
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

/** A JUnit4 runner that supports executing TestNg tests on a JUnit4 engine. */
public final class TestNgJUnit4 extends BlockJUnit4ClassRunner {

  private static final ImmutableSet<Class<? extends Annotation>> SUPPORTED_TEST_NG_ANNOTATIONS =
      ImmutableSet.of(
          Factory.class,
          BeforeTest.class,
          BeforeClass.class,
          BeforeMethod.class,
          Test.class,
          AfterMethod.class,
          AfterClass.class,
          AfterTest.class,
          DataProvider.class);

  /**
   * All declared means to create an instance for the test class, including constructions through
   * parametrized factory methods and public constructor.
   */
  private ImmutableList<SingleInstanceFactory> testInstanceFactories;

  public TestNgJUnit4(Class<?> testClass) throws Exception {
    super(testClass);
  }

  @Override
  protected void collectInitializationErrors(List<Throwable> errors) {
    TestClass testClass = getTestClass();
    validateSupportedTestNgAnnotations(testClass, errors);
    validateDependentDataProviders(errors);
    super.collectInitializationErrors(errors);
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

  private void validateDependentDataProviders(List<Throwable> errors) {
    Set<DataProviderKey> dataProviders = new HashSet<>();
    for (FrameworkMethod frameworkMethod :
        getTestClassAnnotatedMethods(Factory.class, Test.class)) {
      Method reflectMethod = frameworkMethod.getMethod();
      if (DataProviderHelper.isDataConsumer(reflectMethod)) {
        dataProviders.add(fromDataConsumer(reflectMethod));
      }
    }
    for (DataProviderKey dataProviderKey : dataProviders) {
      try {
        dataProviderKey.providerMethod().setAccessible(true);
      } catch (NoSuchMethodError e) {
        errors.add(e);
      }
    }
  }

  @Override
  protected void validateConstructor(List<Throwable> errors) {
    testInstanceFactories =
        DataProviderHelper.createInstanceFactories(getTestClass().getJavaClass(), errors);
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
    List<FrameworkMethod> frameworkMethods = new ArrayList<>();
    for (Method method : computeBaseTestMethods()) {
      ImmutableList<ParameterInputEntry> parameterInputs = findParameterInputs(method);
      for (SingleInstanceFactory testFactory : testInstanceFactories) {
        for (ParameterInputEntry parameterInput : parameterInputs) {
          frameworkMethods.add(new ParameterizedMethod(method, parameterInput, testFactory));
        }
      }
    }
    return frameworkMethods;
  }

  private ImmutableList<Method> computeBaseTestMethods() {
    ImmutableList.Builder<Method> baseMethods = ImmutableList.builder();
    for (Method method : getTestClass().getJavaClass().getMethods()) {
      if (method.isAnnotationPresent(Test.class)) {
        baseMethods.add(method);
      } else if (method.getName().startsWith("test")
          && method.getParameterCount() == 0
          && !Modifier.isStatic(method.getModifiers())) {
        baseMethods.add(method);
      }
    }
    return baseMethods.build();
  }

  @Override
  protected Object createTest() throws Exception {
    throw new UnsupportedOperationException();
  }

  @Override
  protected Statement methodBlock(FrameworkMethod method) {
    if (method instanceof ParameterizedMethod) {
      return methodBlock((ParameterizedMethod) method);
    }
    throw new AssertionError(
        String.format("Expected %s to be an instance of %s.", method, ParameterizedMethod.class));
  }

  private Statement methodBlock(ParameterizedMethod method) {
    Object test;
    try {
      test =
          new ReflectiveCallable() {
            @Override
            protected Object runReflectiveCall() throws Throwable {
              return method.createTestInstance();
            }
          }.run();
    } catch (Throwable e) {
      return new Fail(e);
    }

    Statement statement = methodInvoker(method, test);
    statement = possiblyExpectingExceptions(method, test, statement);
    statement = withPotentialTimeout(method, test, statement);
    statement = withBefores(method, test, statement);
    statement = withAfters(method, test, statement);
    return statement;
  }

  @Override
  protected Statement possiblyExpectingExceptions(FrameworkMethod method, Object test,
      Statement next) {
    Test testAnnotation = method.getAnnotation(Test.class);
    if (testAnnotation == null){
      return super.possiblyExpectingExceptions(method, test, next);
    }
    Class<? extends Throwable>[] expectedExceptions = testAnnotation.expectedExceptions();
    if (expectedExceptions.length == 0) {
      return super.possiblyExpectingExceptions(method, test, next);
    } if (expectedExceptions.length == 1) {
      return new ExpectException(next, expectedExceptions[0]);
    } else {
      // TODO(deltazulu): Add multi-exception support once there is a real case for it.
      throw new UnsupportedOperationException(
          String.format("%s does not support multiple expected exceptions on %s", getClass(), method));
    }
  }

  @Override
  protected Statement methodInvoker(FrameworkMethod method, Object test) {
    if (method instanceof ParameterizedMethod) {
      ParameterizedMethod parameterizedMethod = (ParameterizedMethod) method;
      return new InvokeMethodWithParams(method, test, parameterizedMethod.evaluateParameters());
    }
    return super.methodInvoker(method, test);
  }

  @Override
  protected Statement withAfters(FrameworkMethod method, Object target, Statement statement) {
    List<FrameworkMethod> afterMethods = getTestClassAnnotatedMethods(AfterMethod.class);
    JUnitStatements.ComposedStatement stmt =
        new ComposedStatement(EmptyStatement.getDefaultInstance(), statement);
    for (FrameworkMethod afterMethod : afterMethods) {
      if (afterMethod.getMethod().getParameterCount() != 0) {
        // TestNg supports parameter injections of a TestResult instance in an @AfterMethod-method.
        // Under this runner, inject a default instance since the presented result won't be used.
        stmt = stmt.andThen(new InvokeMethodWithParams(afterMethod, target, new TestResult()));
      } else {
        stmt = stmt.andThen(new InvokeMethod(afterMethod, target));
      }
    }
    List<FrameworkMethod> afters = new ArrayList<>();
    afters.addAll(getTestClassAnnotatedMethods(AfterClass.class));
    afters.addAll(getTestClassAnnotatedMethods(AfterTest.class));
    return afterMethods.isEmpty() && afters.isEmpty() ? stmt : new RunAfters(stmt, afters, target);
  }

  private List<FrameworkMethod> getTestClassAnnotatedMethods(
      Class<? extends Annotation> annotationClass,
      Class<? extends Annotation>... additionalAnnotationClasses) {
    List<FrameworkMethod> annotatedMethods = getTestClass().getAnnotatedMethods(annotationClass);
    if (additionalAnnotationClasses.length == 0) {
      return annotatedMethods;
    }
    List<FrameworkMethod> methods = new ArrayList<>(annotatedMethods);
    for (Class<? extends Annotation> annotation : additionalAnnotationClasses) {
      methods.addAll(getTestClass().getAnnotatedMethods(annotation));
    }
    return methods;
  }
}
