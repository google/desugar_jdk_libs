/*
 * Copyright (C) 2021 The Android Open Source Project
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

package libcore.benchmark;

import androidx.benchmark.BenchmarkState;
import androidx.benchmark.junit4.BenchmarkRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

@RunWith(AndroidJUnit4.class)
public class FormatterTest {
    @Rule
    public BenchmarkRule benchmarkRule = new BenchmarkRule();

    @Test
    public void stringFormatNumber_allLocales() {
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            for (Locale locale : Locale.getAvailableLocales()) {
                // String.format is the most common usage of the Formatter because it's implemented
                // by Formatter
                String.format(locale, "%d s", 0);
            }
        }
    }

    @Test
    public void stringFormatNumber_withWidth_allLocales() {
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            for (Locale locale : Locale.getAvailableLocales()) {
                String.format(locale, "%05d s", 123);
            }
        }
    }

    @Test
    public void stringFormatString_allLocales() {
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            for (Locale locale : Locale.getAvailableLocales()) {
                String.format(locale, "foo-%s-baz", "bar");
            }
        }
    }
}
