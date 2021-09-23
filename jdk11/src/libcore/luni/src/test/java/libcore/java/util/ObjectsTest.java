/*
 * Copyright (C) 2013 The Android Open Source Project
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

package libcore.java.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

public class ObjectsTest extends junit.framework.TestCase {
  public static final class Hello {
    public String toString() { return "hello"; }
  }

  public void test_checkFromIndexSize_valid() {
    Objects.checkFromIndexSize(/* fromIndex */ 0, /* size */ 0, /* length */ 10);
    Objects.checkFromIndexSize(/* fromIndex */ 10, /* size */ 0, /* length */ 10);
    Objects.checkFromIndexSize(/* fromIndex */ 5, /* size */ 1, /* length */ 10);
    Objects.checkFromIndexSize(/* fromIndex */ 0, /* size */ 10, /* length */ 10);
    Objects.checkFromIndexSize(/* fromIndex */ 1, /* size */ 9, /* length */ 10);
    Objects.checkFromIndexSize(/* fromIndex */ 0, /* size */ 9, /* length */ 10);
  }

  public void test_checkFromIndexSize_negativeSize() {
    assertFromIndexSizeOutOfBounds(/* fromIndex */ -1, /* size */ 10, /* length */ 100);
    assertFromIndexSizeOutOfBounds(/* fromIndex */ 5, /* size */ -1, /* length */ 100);
    assertFromIndexSizeOutOfBounds(/* fromIndex */ 0, /* size */ -1, /* length */ 100);
    assertFromIndexSizeOutOfBounds(/* fromIndex */ 0, /* size */ -1, /* length */ -1);
    assertFromIndexSizeOutOfBounds(/* fromIndex */ 0, /* size */ 0, /* length */ -1);
  }

  public void test_checkFromIndexSize_beyondEnd() {
    assertFromIndexSizeOutOfBounds(/* fromIndex */ 0, /* size */ 10, /* length */ 9);
    assertFromIndexSizeOutOfBounds(/* fromIndex */ 1, /* size */ 10, /* length */ 10);

    // Invalid, but fromIndex + size overflows and is < length.
    assertFromIndexSizeOutOfBounds(/* fromIndex */ Integer.MAX_VALUE - 10, /* size */ 11,
            /* length */ Integer.MAX_VALUE);
  }

  private static void assertFromIndexSizeOutOfBounds(int fromIndex, int size, int length) {
    try {
      Objects.checkFromIndexSize(fromIndex, size, length);
      fail();
    } catch (IndexOutOfBoundsException expected) {
    }
  }

  public void test_checkFromToIndex_valid() {
    Objects.checkFromToIndex(/* fromIndex */ 0, /* toIndex */ 0, /* length */ 10);
    Objects.checkFromToIndex(/* fromIndex */ 10, /* toIndex */ 10, /* length */ 10);
    Objects.checkFromToIndex(/* fromIndex */ 5, /* toIndex */ 6, /* length */ 10);
    Objects.checkFromToIndex(/* fromIndex */ 0, /* toIndex */ 10, /* length */ 10);
    Objects.checkFromToIndex(/* fromIndex */ 1, /* toIndex */ 10, /* length */ 10);
    Objects.checkFromToIndex(/* fromIndex */ 0, /* toIndex */ 0, /* length */ 10);
  }

  public void test_checkFromToIndex_negativeSize() {
    assertFromToIndexOutOfBounds(/* fromIndex */ -1, /* toIndex */ 9, /* length */ 100);
    assertFromToIndexOutOfBounds(/* fromIndex */ 5, /* toIndex */ 4, /* length */ 100);
    assertFromToIndexOutOfBounds(/* fromIndex */ 0, /* toIndex */ -1, /* length */ 100);
    assertFromToIndexOutOfBounds(/* fromIndex */ 0, /* toIndex */ -1, /* length */ -1);
    assertFromToIndexOutOfBounds(/* fromIndex */ 0, /* toIndex */ 0, /* length */ -1);
  }

  public void test_checkFromToIndex_beyondEnd() {
    assertFromToIndexOutOfBounds(/* fromIndex */ 0, /* toIndex */ 10, /* length */ 9);
    assertFromToIndexOutOfBounds(/* fromIndex */ 1, /* toIndex */ 11, /* length */ 10);
    assertFromToIndexOutOfBounds(/* fromIndex */ Integer.MAX_VALUE - 10,
            /* toIndex */ Integer.MIN_VALUE, /* length */ Integer.MAX_VALUE);
  }

  private static void assertFromToIndexOutOfBounds(int fromIndex, int toIndex, int length) {
    try {
      Objects.checkFromToIndex(fromIndex, toIndex, length);
      fail();
    } catch (IndexOutOfBoundsException expected) {
    }
  }

  public void test_checkIndex_empty() {
    assertIndexOutOfBounds(0, 0);
    assertIndexOutOfBounds(1, 0);
    assertIndexOutOfBounds(-1, 0);
    assertIndexOutOfBounds(100, 0);
    assertIndexOutOfBounds(-100, 0);
    assertIndexOutOfBounds(Integer.MAX_VALUE, 0);
    assertIndexOutOfBounds(Integer.MAX_VALUE, 0);
  }

  public void test_checkIndex_size1() {
    Objects.checkIndex(0, 1);
    assertIndexOutOfBounds(1, 1);
    assertIndexOutOfBounds(-1, 1);
    assertIndexOutOfBounds(100, 1);
    assertIndexOutOfBounds(-100, 1);
    assertIndexOutOfBounds(Integer.MAX_VALUE, 1);
    assertIndexOutOfBounds(Integer.MAX_VALUE, 1);
  }

  public void test_checkIndex_largeSize() {
    Objects.checkIndex(0, 100);
    Objects.checkIndex(99, 100);
    Objects.checkIndex(100, Integer.MAX_VALUE);
    Objects.checkIndex(Integer.MAX_VALUE - 1, Integer.MAX_VALUE);
    assertIndexOutOfBounds(-1, 100);
    assertIndexOutOfBounds(100, 100);
    assertIndexOutOfBounds(Integer.MAX_VALUE, Integer.MAX_VALUE);
    assertIndexOutOfBounds(-1, Integer.MAX_VALUE);
  }

  private static void assertIndexOutOfBounds(int index, int length) {
    try {
      Objects.checkIndex(index, length);
      fail();
    } catch (IndexOutOfBoundsException expected) {
    }
  }

  public void test_compare() throws Exception {
    assertEquals(0, Objects.compare(null, null, String.CASE_INSENSITIVE_ORDER));
    assertEquals(0, Objects.compare("a", "A", String.CASE_INSENSITIVE_ORDER));
    assertEquals(-1, Objects.compare("a", "b", String.CASE_INSENSITIVE_ORDER));
    assertEquals(1, Objects.compare("b", "a", String.CASE_INSENSITIVE_ORDER));
  }

  public void test_deepEquals() throws Exception {
    int[] xs = new int[3];
    int[] ys = new int[4];
    int[] zs = new int[3];
    String[] o1 = new String[] { "hello" };
    String[] o2 = new String[] { "world" };
    String[] o3 = new String[] { "hello" };
    assertTrue(Objects.deepEquals(null, null));
    assertFalse(Objects.deepEquals(xs, null));
    assertFalse(Objects.deepEquals(null, xs));
    assertTrue(Objects.deepEquals(xs, xs));
    assertTrue(Objects.deepEquals(xs, zs));
    assertFalse(Objects.deepEquals(xs, ys));
    assertTrue(Objects.deepEquals(o1, o1));
    assertTrue(Objects.deepEquals(o1, o3));
    assertFalse(Objects.deepEquals(o1, o2));
    assertTrue(Objects.deepEquals("hello", "hello"));
    assertFalse(Objects.deepEquals("hello", "world"));
  }

  public void test_equals() throws Exception {
    Hello h1 = new Hello();
    Hello h2 = new Hello();
    assertTrue(Objects.equals(null, null));
    assertFalse(Objects.equals(h1, null));
    assertFalse(Objects.equals(null, h1));
    assertFalse(Objects.equals(h1, h2));
    assertTrue(Objects.equals(h1, h1));
  }

  public void test_hash() throws Exception {
    assertEquals(Arrays.hashCode(new Object[0]), Objects.hash());
    assertEquals(31, Objects.hash((Object) null));
    assertEquals(0, Objects.hash((Object[]) null));
    assertEquals(-1107615551, Objects.hash("hello", "world"));
    assertEquals(23656287, Objects.hash("hello", "world", null));
  }

  public void test_hashCode() throws Exception {
    Hello h = new Hello();
    assertEquals(h.hashCode(), Objects.hashCode(h));
    assertEquals(0, Objects.hashCode(null));
  }

  public void test_requireNonNull_T() throws Exception {
    Hello h = new Hello();
    assertEquals(h, Objects.requireNonNull(h));
    try {
      Objects.requireNonNull(null);
      fail();
    } catch (NullPointerException expected) {
      assertEquals(null, expected.getMessage());
    }
  }

  public void test_requireNonNull_T_String() throws Exception {
    Hello h = new Hello();
    assertEquals(h, Objects.requireNonNull(h, "test"));
    try {
      Objects.requireNonNull(null, "message");
      fail();
    } catch (NullPointerException expected) {
      assertEquals("message", expected.getMessage());
    }
    try {
      Objects.requireNonNull(null, (String) null);
      fail();
    } catch (NullPointerException expected) {
      assertEquals(null, expected.getMessage());
    }
  }

  public void test_requireNonNull_T_Supplier() throws Exception {
    Hello h = new Hello();
    assertEquals(h, Objects.requireNonNull(h, () -> "test"));
    try {
      Objects.requireNonNull(null, () -> "message");
      fail();
    } catch (NullPointerException expected) {
      assertEquals("message", expected.getMessage());
    }

    // The supplier is unexpectedly null.
    try {
      Objects.requireNonNull(null, (Supplier<String>) null);
      fail();
    } catch (NullPointerException expected) {
    }

    // This does not currently throw. The presence of this test ensures that any
    // future behavior change is deliberate.
    assertEquals(h, Objects.requireNonNull(h, (Supplier<String>) null));

    // The message returned by the supplier is null.
    try {
      Objects.requireNonNull(null, () -> null);
      fail();
    } catch (NullPointerException expected) {
      assertEquals(null, expected.getMessage());
    }
  }

  public void test_requireNonNullElse() {
    assertEquals("obj", Objects.requireNonNullElse("obj", "default"));
    assertEquals("default", Objects.requireNonNullElse(null, "default"));
    assertEquals("obj", Objects.requireNonNullElse("obj", null));
    assertThrowsNpe(() -> Objects.requireNonNullElse(null, null));
  }

  public void test_requireNonNullElseGet_obj() {
    assertEquals("obj", Objects.requireNonNullElseGet("obj", () -> "default"));
    // null supplier / supplier that returns null is tolerated when obj != null.
    assertEquals("obj", Objects.requireNonNullElseGet("obj", () -> null));
    assertEquals("obj", Objects.requireNonNullElseGet("obj", null));
  }

  public void test_requireNonNullElseGet_nullObj() {
    assertEquals("default", Objects.requireNonNullElseGet(null, () -> "default"));
    // null supplier and supplier of null both throw.
    assertThrowsNpe(() -> Objects.requireNonNullElseGet(null, (Supplier<?>) () -> null));
    assertThrowsNpe(() -> Objects.requireNonNullElse(null, null));
  }

  private static void assertThrowsNpe(Runnable runnable) {
    try {
      runnable.run();
      fail();
    } catch (NullPointerException expected) {
    }
  }

  public void test_toString_Object() throws Exception {
    assertEquals("hello", Objects.toString(new Hello()));
    assertEquals("null", Objects.toString(null));
  }

  public void test_toString_Object_String() throws Exception {
    assertEquals("hello", Objects.toString(new Hello(), "world"));
    assertEquals("world", Objects.toString(null, "world"));
    assertEquals(null, Objects.toString(null, null));
  }

  public void test_isNull() throws Exception {
    assertTrue(Objects.isNull(null));
    assertFalse(Objects.isNull(new Hello()));
  }

  public void test_nonNull() throws Exception {
    assertFalse(Objects.nonNull(null));
    assertTrue(Objects.nonNull(new Hello()));
  }
}
