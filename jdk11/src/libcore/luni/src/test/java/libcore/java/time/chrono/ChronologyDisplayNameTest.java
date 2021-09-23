/*
 * Copyright (C) 2016 The Android Open Source Project
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
package libcore.java.time.chrono;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test display names of chronologies and their eras.
 * The primary reason for this test is to ensure that newly added chronologies have a display name
 * and that their eras have a display name as well.
 */
@RunWith(Parameterized.class)
public class ChronologyDisplayNameTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Chronology> getChronologies() {
        return new ArrayList<>(Chronology.getAvailableChronologies());
    }

    private final Chronology chronology;

    public ChronologyDisplayNameTest(Chronology chronology) {
        this.chronology = chronology;
    }

    @Test
    public void testDisplayName() {
        String displayName = chronology.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        assertNotNull(displayName);
        assertFalse("".equals(displayName));
    }

    @Test
    public void testEras() {
        List<Era> eras = chronology.eras();
        Set<String> eraNames = new HashSet<>();
        for (Era era : eras) {
            assertNotNull(era);
            String displayName = era.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            assertNotNull(displayName);
            assertFalse("".equals(displayName));
            assertTrue("Era name for " + era + " not unique.", eraNames.add(displayName));
        }
    }
}
