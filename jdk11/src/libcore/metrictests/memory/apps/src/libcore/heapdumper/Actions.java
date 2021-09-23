/*
 * Copyright (C) 2017 The Android Open Source Project
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

package libcore.heapdumper;

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An enumeration of actions for which we'd like to measure the effect on the post-GC heap.
 */
enum Actions implements Runnable {

    /**
     * Does nothing. Exists to measure the overhead inherent in the measurement system.
     */
    NOOP {
        @Override
        public void run() {
            // noop!
        }
    },

    /**
     * Uses a collator for the root locale to trivially sort some strings.
     */
    COLLATOR_ROOT_LOCALE {
        @Override
        public void run() {
            useCollatorForLocale(Locale.ROOT);
        }
    },

    /**
     * Uses a collator for the US English locale to trivially sort some strings.
     */
    COLLATOR_EN_US_LOCALE {
        @Override
        public void run() {
            useCollatorForLocale(Locale.US);
        }
    },

    /**
     * Uses a collator for the Korean locale to trivially sort some strings.
     */
    COLLATOR_KOREAN_LOCALE {
        @Override
        public void run() {
            useCollatorForLocale(Locale.KOREAN);
        }
    },

    REGEX {
        @Override
        public void run() {
            final String sequence = "foo 123 bar baz";
            Pattern p = Pattern.compile("foo (\\d+) bar (\\w+)");
            Matcher m = p.matcher(sequence);

            boolean found = m.find();
            boolean matches = m.matches();
            int groups = m.groupCount();
            String first = m.group(1);
            String second = m.group(2);
            boolean hitEnd = m.hitEnd();

            // Set region to prefix of the original sequence
            m.region(0, "foo 123".length());
            boolean matchesPrefix = m.lookingAt();
            boolean requireEnd = m.requireEnd();

            m.useTransparentBounds(true);
            boolean matchesPrefixTransparentBounds = m.lookingAt();

            m.useAnchoringBounds(true);
            boolean matchesPrefixAnchoringBounds = m.lookingAt();
        }
    }

    ;

    private static void useCollatorForLocale(Locale locale) {
        String[] strings = { "caff", "café", "cafe", "안녕", "잘 가" };
        Collator collator = Collator.getInstance(locale);
        Arrays.sort(strings, collator);
    }
}
