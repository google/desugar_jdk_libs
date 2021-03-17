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

import static com.google.testing.junit.TestNgJUnit4.DataProviderHelper.findParameterInputs;
import static com.google.testing.junit.TestNgJUnit4.DataProviderKey.fromDataConsumer;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import org.junit.internal.runners.model.ReflectiveCallable;
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

  /**
   * Creates all factories for the given class literal, include both @Factory-annotated methods and
   * the zero-arg default constructor if any.
   */
  private static ImmutableList<SingleInstanceFactory> createInstanceFactories(
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
            Object[] parameterData = parameterInputs.get(i).getPayload();
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
            Object[] parameterData = parameterInputs.get(i).getPayload();
            instanceFactoriesBuilder.add(
                new SingleInstanceFactory(
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
        instanceFactoriesBuilder.add(new SingleInstanceFactory(tag, constructor::newInstance));
      } else if (constructor.isAnnotationPresent(Factory.class)) {
        ImmutableList<ParameterInputEntry> allDatasets =
            fromDataConsumer(constructor).getDatasets();
        for (int i = 0, allDatasetsSize = allDatasets.size(); i < allDatasetsSize; i++) {
          Object[] parameterData = allDatasets.get(i).getPayload();
          ParameterizedMemberTag.Builder tag =
              ParameterizedMemberTag.builder()
                  .setMember(constructor)
                  .setInputSerialNumber(i)
                  .setOutputSerialNumber(0);
          instanceFactoriesBuilder.add(
              new SingleInstanceFactory(tag.build(), () -> constructor.newInstance(parameterData)));
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

  @Override
  protected void validateConstructor(List<Throwable> errors) {
    testInstanceFactories = createInstanceFactories(getTestClass().getJavaClass(), errors);
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
    ComposedStatement stmt = new ComposedStatement(new EmptyStatement(), statement);
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

  /** Describes a test method with a particular data set. */
  static class ParameterizedMethod extends FrameworkMethod {

    private final Method baseMethod;
    private final ParameterInputEntry datasetEntry;
    private final SingleInstanceFactory testFactory;

    public ParameterizedMethod(
        Method baseMethod, ParameterInputEntry datasetEntry, SingleInstanceFactory testFactory) {
      super(baseMethod);
      this.baseMethod = baseMethod;
      this.datasetEntry = datasetEntry;
      this.testFactory = testFactory;
    }

    public Object createTestInstance() throws Exception {
      return testFactory.call();
    }

    public Object[] evaluateParameters() {
      return datasetEntry.getPayload();
    }

    @Override
    public String getName() {
      return super.getName() + getParameterizationSuffix();
    }

    @Override
    public String toString() {
      return super.toString() + getParameterizationSuffix();
    }

    private String getParameterizationSuffix() {
      return "_param:" + datasetEntry + "_factory:" + testFactory;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (obj instanceof ParameterizedMethod) {
        ParameterizedMethod that = (ParameterizedMethod) obj;
        return this.testFactory.equals(that.testFactory)
            && this.datasetEntry.equals(that.datasetEntry)
            && this.baseMethod.equals(that.baseMethod);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return Objects.hash(super.hashCode(), datasetEntry, testFactory);
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

  @AutoValue
  abstract static class ParameterizedMemberTag {

    abstract Executable member();

    abstract int inputSerialNumber();

    abstract int outputSerialNumber();

    public static ParameterizedMemberTag.Builder builder() {
      return new AutoValue_TestNgJUnit4_ParameterizedMemberTag.Builder();
    }

    public abstract ParameterizedMemberTag.Builder toBuilder();

    public String shortMessage() {
      StringBuilder sb = new StringBuilder();
      sb.append(member().getDeclaringClass().getSimpleName());
      sb.append("#");
      if (member() instanceof Constructor) {
        sb.append("<init>");
      } else {
        sb.append(member().getName());
      }

      sb.append(":in");
      sb.append(inputSerialNumber());

      sb.append(":out");
      sb.append(outputSerialNumber());

      return sb.toString();
    }

    @AutoValue.Builder
    public abstract static class Builder {

      public abstract Builder setMember(Executable value);

      public abstract Builder setInputSerialNumber(int value);

      public abstract Builder setOutputSerialNumber(int value);

      public abstract ParameterizedMemberTag build();
    }
  }

  static class EmptyStatement extends Statement {
    @Override
    public void evaluate() throws Throwable {}
  }

  static class ComposedStatement extends Statement {
    private final Statement baseStatement;
    private final Statement nextStatement;

    ComposedStatement(Statement baseStatement, Statement nextStatement) {
      this.baseStatement = baseStatement;
      this.nextStatement = nextStatement;
    }

    @Override
    public void evaluate() throws Throwable {
      baseStatement.evaluate();
      nextStatement.evaluate();
    }

    public ComposedStatement andThen(Statement statement) {
      return new ComposedStatement(this, statement);
    }
  }

  /** Describes an entry that indexes an dataset instance. */
  abstract static class DatasetEntry<T> {

    private final ParameterizedMemberTag tag;

    private final T payload;

    DatasetEntry(ParameterizedMemberTag tag, T payload) {
      this.tag = tag;
      this.payload = payload;
    }

    public ParameterizedMemberTag getTag() {
      return tag;
    }

    public T getPayload() {
      return payload;
    }

    @Override
    public final String toString() {
      return tag.shortMessage();
    }
  }

  static class ParameterInputEntry extends DatasetEntry<Object[]> {

    static ParameterInputEntry create(ParameterizedMemberTag tag, Object[] payload) {
      return new ParameterInputEntry(tag, payload);
    }

    private ParameterInputEntry(ParameterizedMemberTag tag, Object[] payload) {
      super(tag, payload);
    }
  }

  static class SingleInstanceFactory
      extends DatasetEntry<ThrowingCallable<Object, ReflectiveOperationException>>
      implements ThrowingCallable<Object, ReflectiveOperationException> {

    SingleInstanceFactory(
        ParameterizedMemberTag tag,
        ThrowingCallable<Object, ReflectiveOperationException> factory) {
      super(tag, factory);
    }

    @Override
    public Object call() throws ReflectiveOperationException {
      return getPayload().call();
    }

    public static List<SingleInstanceFactory> fromTestInstanceArrayFactory(
        ParameterizedMemberTag baseTag,
        ThrowingCallable<Object[], ReflectiveOperationException> instanceArrayFactory)
        throws ReflectiveOperationException {
      Object[] preRunResults = instanceArrayFactory.call();
      int instanceArrayLength = preRunResults.length;
      List<SingleInstanceFactory> singleTestInstanceFactories =
          new ArrayList<>(instanceArrayLength);
      for (int i = 0; i < instanceArrayLength; i++) {
        ParameterizedMemberTag creationTag = baseTag.toBuilder().setOutputSerialNumber(i).build();
        singleTestInstanceFactories.add(
            new SingleInstanceFactory(
                creationTag, () -> instanceArrayFactory.call()[creationTag.outputSerialNumber()]));
      }
      return singleTestInstanceFactories;
    }
  }

  interface ThrowingCallable<R, E extends Exception> extends Callable<R> {

    @Override
    R call() throws E;
  }

  /** Describes the key to a data provider. */
  @AutoValue
  abstract static class DataProviderKey {

    abstract Method providerMethod();

    private static DataProviderKey create(Class<?> providerClass, String providerName) {
      for (Method method : providerClass.getDeclaredMethods()) {
        DataProvider dataProviderSpec = method.getDeclaredAnnotation(DataProvider.class);
        if (dataProviderSpec != null && dataProviderSpec.name().equals(providerName)) {
          return new AutoValue_TestNgJUnit4_DataProviderKey(method);
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
          createInstanceFactories(providerMethod().getDeclaringClass(), new ArrayList<>());
      ImmutableList.Builder<Object> providerClassInstances = ImmutableList.builder();
      for (SingleInstanceFactory factory : providerClassFactories) {
        try {
          providerClassInstances.add(factory.call());
        } catch (ReflectiveOperationException e) {
          throw new AssertionError(e);
        }
      }
      return providerClassInstances.build();
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
        for (ParameterInputEntry parameterInput : parameterInputs) {
          try {
            Object[][] rawOutputs =
                (Object[][]) providerMethod().invoke(providerInstance, parameterInput.getPayload());
            for (int i = 0, rawDatasetsLength = rawOutputs.length; i < rawDatasetsLength; i++) {
              ParameterizedMemberTag tag =
                  ParameterizedMemberTag.builder()
                      .setMember(providerMethod())
                      .setInputSerialNumber(parameterInput.getTag().outputSerialNumber())
                      .setOutputSerialNumber(i)
                      .build();
              dataContainer.add(ParameterInputEntry.create(tag, rawOutputs[i]));
            }
          } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
          }
        }
      }
      return dataContainer.build();
    }
  }

  static class DataProviderHelper {

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
  }
}
