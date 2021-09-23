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
package libcore.junit.util;

import static org.junit.Assert.fail;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Allows tests to specify the target SDK version that they want to run as.
 *
 * <p>To use add the following to the test class. It will only change the behavior of a test method
 * if it is annotated with {@link TargetSdkVersion}.
 *
 * <pre>
 * &#64;Rule
 * public TestRule switchTargetSdkVersionRule = SwitchTargetSdkVersionRule.getInstance();
 * </pre>
 *
 * <p>Each test method that needs to run with a specific target SDK version needs to be annotated
 * with {@link TargetSdkVersion} specifying the required SDK version. e.g.
 *
 * <pre>
 *   &#64;Test
 *   &#64;TargetSdkVersion(23)
 *   public void testAsIfTargetedAtSDK23() {
 *     assertEquals(23, VMRuntime.getRuntime().getTargetSdkVersion());
 *   }
 * </pre>
 *
 * <p>Within the body of the method the {@code VMRuntime.getTargetSdkVersion()} will be set to the
 * value specified in the annotation.
 *
 * <p>If used on a platform that does not support the {@code dalvik.system.VMRuntime} class then any
 * test annotated with {@link TargetSdkVersion} will fail with a message explaining that it is not
 * supported.
 */
public abstract class SwitchTargetSdkVersionRule implements TestRule {

  private static final TestRule INSTANCE;

  private static final String VMRUNTIME = "dalvik.system.VMRuntime";

  // Use reflection so that this rule can compile and run against RI core libraries.
  static {
    TestRule rule;
    try {
      // Assume that VMRuntime is supported and create a rule instance that will use it.
      rule = new SwitchVMRuntimeUsingReflection();
    } catch (ClassNotFoundException | NoSuchMethodException e) {
      // VMRuntime is not supported.
      rule = new VMRuntimeNotSupported();
    }

    INSTANCE = rule;
  }

  public static TestRule getInstance() {
    return INSTANCE;
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface TargetSdkVersion {
    int value();
  }

  private SwitchTargetSdkVersionRule() {
  }

  @Override
  public Statement apply(final Statement statement, Description description) {
    TargetSdkVersion targetSdkVersion = description.getAnnotation(TargetSdkVersion.class);
    if (targetSdkVersion == null) {
      return statement;
    }

    return createStatement(statement, targetSdkVersion.value());
  }

  /**
   * Create the {@link Statement} that will be run for the specific {@code targetSdkVersion}.
   *
   * @param statement the {@link Statement} encapsulating the test method.
   * @param targetSdkVersion the target SDK version to use within the body of the test.
   * @return the created {@link Statement}.
   */
  protected abstract Statement createStatement(Statement statement, int targetSdkVersion);

  /**
   * Switch the value of {@code VMRuntime.getTargetSdkVersion()} for tests annotated with
   * {@link TargetSdkVersion}.
   *
   * <p>Uses reflection so this class can compile and run on OpenJDK.
   */
  private static class SwitchVMRuntimeUsingReflection extends SwitchTargetSdkVersionRule {

    private final Method runtimeInstanceGetter;
    private final Method targetSdkVersionGetter;
    private final Method targetSdkVersionSetter;

    private SwitchVMRuntimeUsingReflection() throws ClassNotFoundException, NoSuchMethodException {
      ClassLoader classLoader = ClassLoader.getSystemClassLoader();
      Class<?> runtimeClass = classLoader.loadClass(VMRUNTIME);

      this.runtimeInstanceGetter = runtimeClass.getMethod("getRuntime");
      this.targetSdkVersionGetter = runtimeClass.getMethod("getTargetSdkVersion");
      this.targetSdkVersionSetter = runtimeClass.getMethod("setTargetSdkVersion", int.class);
    }

    @Override
    protected Statement createStatement(Statement statement, int targetSdkVersion) {
      return new Statement() {
        @Override
        public void evaluate() throws Throwable {
          Object runtime = runtimeInstanceGetter.invoke(null);
          int oldTargetSdkVersion = (int) targetSdkVersionGetter.invoke(runtime);
          targetSdkVersionSetter.invoke(runtime, targetSdkVersion);
          try {
            statement.evaluate();
          } finally {
            targetSdkVersionSetter.invoke(runtime, oldTargetSdkVersion);
          }
        }
      };
    }
  }

  /**
   * VMRuntime is not supported on this platform so fail all tests that target a specific SDK
   * version
   */
  private static class VMRuntimeNotSupported extends SwitchTargetSdkVersionRule {

    @Override
    protected Statement createStatement(Statement statement, int targetSdkVersion) {
      return new Statement() {
        @Override
        public void evaluate() {
          fail("Targeting SDK version not supported as " + VMRUNTIME + " is not supported");
        }
      };
    }
  }
}
