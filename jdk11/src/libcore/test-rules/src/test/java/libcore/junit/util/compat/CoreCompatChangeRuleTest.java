/*
 * Copyright (C) 2018 The Android Open Source Project
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

import static com.google.common.truth.Truth.assertThat;

import android.compat.testing.FakeApi;

import libcore.junit.util.compat.CoreCompatChangeRule.DisableCompatChanges;
import libcore.junit.util.compat.CoreCompatChangeRule.EnableCompatChanges;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for compatibility change gating.
 */
@RunWith(JUnit4.class)
public class CoreCompatChangeRuleTest {

    @Rule
    public TestRule compatChangeRule = new CoreCompatChangeRule();

    @Test
    @EnableCompatChanges({FakeApi.CHANGE_ID})
    public void testFakeGatingPositive() {
        assertThat(FakeApi.fakeFunc()).isEqualTo("A");
    }

    @Test
    @DisableCompatChanges({FakeApi.CHANGE_ID})
    public void testFakeGatingNegative() {
        assertThat(FakeApi.fakeFunc()).isEqualTo("B");
    }

    @Test
    @DisableCompatChanges({FakeApi.CHANGE_ID_1, FakeApi.CHANGE_ID_2})
    public void testFakeGatingCombined0() {
        assertThat(FakeApi.fakeCombinedFunc()).isEqualTo("0");
    }

    @Test
    @DisableCompatChanges({FakeApi.CHANGE_ID_1})
    @EnableCompatChanges({FakeApi.CHANGE_ID_2})
    public void testFakeGatingCombined1() {
        assertThat(FakeApi.fakeCombinedFunc()).isEqualTo("1");
    }

    @Test
    @EnableCompatChanges({FakeApi.CHANGE_ID_1})
    @DisableCompatChanges({FakeApi.CHANGE_ID_2})
    public void testFakeGatingCombined2() {
        assertThat(FakeApi.fakeCombinedFunc()).isEqualTo("2");
    }

    @Test
    @EnableCompatChanges({FakeApi.CHANGE_ID_1, FakeApi.CHANGE_ID_2})
    public void testFakeGatingCombined3() {
        assertThat(FakeApi.fakeCombinedFunc()).isEqualTo("3");
    }
}