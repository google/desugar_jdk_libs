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

import dalvik.system.VMRuntime;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Supplier;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import sun.security.jca.Providers;

/**
 * Allows tests to temporarily enable deprecated BouncyCastle algorithms to verify that their
 * behavior has not changed.
 *
 * <p>To use add the following to the test class.
 *
 * <pre>
 * &#64;Rule
 * public TestRule enableDeprecatedBCAlgorithmsRule =
 *     EnableDeprecatedBouncyCastleAlgorithmsRule.getInstance();
 * </pre>
 *
 * <p>It will give all test methods access to the deprecated algorithms.
 */
public class EnableDeprecatedBouncyCastleAlgorithmsRule implements TestRule {

    private static final TestRule INSTANCE = new EnableDeprecatedBouncyCastleAlgorithmsRule();

    public static TestRule getInstance() {
        return INSTANCE;
    }

    private EnableDeprecatedBouncyCastleAlgorithmsRule() {
    }

    @Override
    public Statement apply(final Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                int currentMaximum =
                        Providers.getMaximumAllowableApiLevelForBcDeprecation();
                try {
                    int newMaximum = VMRuntime.getRuntime().getTargetSdkVersion();
                    Providers.setMaximumAllowableApiLevelForBcDeprecation(newMaximum);
                    statement.evaluate();
                } finally {
                    Providers.setMaximumAllowableApiLevelForBcDeprecation(currentMaximum);
                }
            }
        };
    }
}
