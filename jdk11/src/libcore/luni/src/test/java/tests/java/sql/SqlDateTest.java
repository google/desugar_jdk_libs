/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tests.java.sql;

import java.sql.Date;

import junit.framework.TestCase;

public class SqlDateTest extends TestCase {

    public void testValueOf() {
        String[] dates = {
            "2001-12-31", "2001-12-1", "2001-1-1", "1900-12-31"
        };

        for (String date : dates) {
            Date.valueOf(date);
        }
    }

    public void testValueOfInvalidDate() {
        String[] invalidDates = {
            "",
            "+2001-12-31", "2001-+12-31", "2001-12-+31",
            "-2001-12-31", "2001--12-31", "2001-12--31",
            "2001--","2001--31","-12-31", "-12-", "--31",
            "2000000001-12-31"
        };

        for (String date : invalidDates) {
            try {
                Date.valueOf(date);
                fail();
            } catch (IllegalArgumentException expected) { }
        }
    }

}
