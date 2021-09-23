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

package libcore.java.util;

import java.util.UUID;
import junit.framework.TestCase;

// There are more tests in the harmony suite:
// harmony-tests/src/test/java/org/apache/harmony/tests/java/util/UUIDTest.java
public class UUIDTest extends TestCase {

  public void testFromStringInvalidValues() {
    try {
      UUID.fromString("+f81d4fae-7dec-11d0-a765-00a0c91e6bf6");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      UUID.fromString("f81d4fae-+7dec-11d0-a765-00a0c91e6bf6");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      UUID.fromString("f81d4fae-7dec-+11d0-a765-00a0c91e6bf6");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      UUID.fromString("f81d4fae-7dec-11d0-+a765-00a0c91e6bf6");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      UUID.fromString("f81d4fae-7dec-11d0-a765-+00a0c91e6bf6");
      fail();
    } catch (IllegalArgumentException expected) { }
  }

}
