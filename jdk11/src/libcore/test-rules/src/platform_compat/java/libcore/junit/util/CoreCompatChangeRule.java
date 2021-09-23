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

package libcore.junit.util.compat;

import android.compat.Compatibility;
import android.compat.Compatibility.ChangeConfig;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashSet;
import java.util.Set;
import com.google.common.primitives.Longs;

/**
 * Allows tests to specify the which change to disable.
 *
 * <p>To use add the following to the test class. It will only change the behavior of a test method
 * if it is annotated with {@link EnableCompatChanges} and/or {@link DisableCompatChanges}.
 *
 * <pre>
 * &#64;Rule
 * public TestRule compatChangeRule = new CoreCompatChangeRule();
 * </pre>
 *
 * <p>Each test method that needs to disable a specific change needs to be annotated
 * with {@link EnableCompatChanges} and/or {@link DisableCompatChanges} specifying the change id.
 * e.g.:
 *
 * <pre>
 *   &#64;Test
 *   &#64;DisableCompatChanges({42})
 *   public void testAsIfChange42Disabled() {
 *     // check behavior
 *   }
 *
 *   &#64;Test
 *   &#64;EnableCompatChanges({42})
 *   public void testAsIfChange42Enabled() {
 *     // check behavior
 *
 * </pre>
 */
public class CoreCompatChangeRule implements TestRule {

    @Override
    public Statement apply(final Statement statement, Description description) {
        Set<Long> enabled = new HashSet<>();
        Set<Long> disabled = new HashSet<>();
        EnableCompatChanges enableCompatChanges = description.getAnnotation(
                EnableCompatChanges.class);
        DisableCompatChanges disableCompatChanges = description.getAnnotation(
                DisableCompatChanges.class);
        if (enableCompatChanges != null) {
            enabled.addAll(Longs.asList(enableCompatChanges.value()));
        }
        if (disableCompatChanges != null) {
            disabled.addAll(Longs.asList(disableCompatChanges.value()));
        }
        ChangeConfig config = new ChangeConfig(enabled, disabled);
        if (config.isEmpty()) {
            return statement;
        } else {
            return createStatementForConfig(statement, config);
        }
    }

    protected Statement createStatementForConfig(final Statement statement, ChangeConfig config) {
            return new CompatChangeStatement(statement, config);
    }

    private static class CompatChangeStatement extends Statement {
        private final Statement testStatement;
        private final ChangeConfig config;

        private CompatChangeStatement(Statement testStatement, ChangeConfig config) {
            this.testStatement = testStatement;
            this.config = config;
        }

        @Override
        public void evaluate() throws Throwable {
            Compatibility.setOverrides(config);
            try {
                testStatement.evaluate();
            } finally {
                Compatibility.clearOverrides();
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface EnableCompatChanges {
        long[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface DisableCompatChanges {
        long[] value();
    }
}
