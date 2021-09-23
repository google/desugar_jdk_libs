/*
 * Copyright (C) 2020 The Android Open Source Project
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
 * limitations under the License
 */

package libcore.java.util.stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Streams tests required for Mainline coverage.
 *
 * TODO(b/153297830): Use existing CtsLibcoreOjTestCases for coverage instead.
 */

@RunWith(JUnit4.class)
public class StreamTest {
  private static final int[] TEST_ARRAY_SIZES = { 0, 1, 2, 10, 100, 1000 };

  /**
   * Stream<T>.of() has two overloads, Stream.of(T t) and Stream.of(T... values)
   *
   * The first builds a Stream<T> whose functionality is tested in CtsLibcoreOjTestCases, so we
   * just check the contents are as expected
   *
   * The second is a thin wrapper around Arrays.Stream(), which is tested in ArraysTest,
   * so again we just check the contents are as expected.
   *
   */
  @Test
  public void streamOfSingleObject() {
    String object = "string";
    String[] array = Stream.of(object).toArray(String[]::new);
    assertEquals(1, array.length);
    assertSame(object, array[0]);
  }

  @Test
  public void streamOfObjects() {
    for (int size : TEST_ARRAY_SIZES) {
      String[] sourceArray = stringTestArray(size);

      // Stream.of(T[] t) is equivalent to Stream.of(T... t)
      String[] destArray = Stream.of(sourceArray)
          .toArray(String[]::new);
      assertNotSame(sourceArray, destArray);
      assertArrayEquals(sourceArray, destArray);
    }
  }

  private String[] stringTestArray(int size) {
    String[] array = new String[size];
    for (int i = 0; i < size; i++) {
      array[i] = String.valueOf(i);
    }
    return array;
  }
}
