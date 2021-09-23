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
import libcore.junit.util.SwitchTargetSdkVersionRule.TargetSdkVersion;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sun.security.jca.Providers;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link EnableDeprecatedBouncyCastleAlgorithmsRule}.
 */
@RunWith(JUnit4.class)
public class EnableDeprecatedBouncyCastleAlgorithmsRuleTest {

    /**
     * Chain the rules together so that changes to the target sdk version will be visible in the
     * bouncy castle rules.
     */
    @Rule
    public TestRule chain = RuleChain
            .outerRule(SwitchTargetSdkVersionRule.getInstance())
            .around(EnableDeprecatedBouncyCastleAlgorithmsRule.getInstance());

    @Test
    @TargetSdkVersion(23)
    public void testRunningAsIfTargetedAtSDKVersion23() {
        assertEquals(23, Providers.getMaximumAllowableApiLevelForBcDeprecation());
    }

    @Test
    public void testRunningAsIfTargetedAtCurrentSDKVersion() {
        assertEquals(VMRuntime.getRuntime().getTargetSdkVersion(),
                Providers.getMaximumAllowableApiLevelForBcDeprecation());
    }
}
