/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package libcore.java.sql;

import java.sql.Timestamp;
import java.util.TimeZone;
import junit.framework.TestCase;

public final class TimestampTest extends TestCase {

  public void testToString() {
    // Timestamp uses the current default timezone in toString() to convert to
    // human-readable strings.
    TimeZone defaultTimeZone = TimeZone.getDefault();
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    try {
      Timestamp t1 = new Timestamp(Long.MIN_VALUE);
      assertEquals("292278994-08-17 07:12:55.192", t1.toString());

      Timestamp t2 = new Timestamp(Long.MIN_VALUE + 1);
      assertEquals("292278994-08-17 07:12:55.193", t2.toString());

      Timestamp t3 = new Timestamp(Long.MIN_VALUE + 807);
      assertEquals("292278994-08-17 07:12:55.999", t3.toString());

      Timestamp t4 = new Timestamp(Long.MIN_VALUE + 808);
      assertEquals("292269055-12-02 16:47:05.0", t4.toString());
    } finally {
      TimeZone.setDefault(defaultTimeZone);
    }
  }

  public void testValueOf() {
    // Timestamp uses the current default timezone in valueOf(String) to convert
    // from human-readable strings.
    TimeZone defaultTimeZone = TimeZone.getDefault();
    TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    try {
      Timestamp t1 = Timestamp.valueOf("2001-12-31 21:45:57.123456789");
      assertEquals(1009835157000L + 123456789 / 1000000, t1.getTime());
      assertEquals(123456789, t1.getNanos());

      Timestamp t2 = Timestamp.valueOf("2001-01-02 01:05:07.123");
      assertEquals(978397507000L + 123000000 / 1000000, t2.getTime());
      assertEquals(123000000, t2.getNanos());

      Timestamp t3 = Timestamp.valueOf("2001-01-02 01:05:07");
      assertEquals(978397507000L, t3.getTime());
      assertEquals(0, t3.getNanos());
    } finally {
      TimeZone.setDefault(defaultTimeZone);
    }
  }

  public void testValueOfInvalid() {
    try {
      Timestamp.valueOf("");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("+2001-12-31");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("2001-+12-31");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("2001-12-+31");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("-2001-12-31");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("2001--12-31");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("2001-12--31");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("2001--");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("2001--31");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("-12-31");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("-12-");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("--31");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("2001-12-31 21:45:57.+12345678");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("2001-12-31 21:45:57.-12345678");
      fail();
    } catch (IllegalArgumentException expected) { }

    try {
      Timestamp.valueOf("2001-12-31 21:45:57.1234567891");
      fail();
    } catch (IllegalArgumentException expected) { }
  }

  // http://b/19756610
  public void testAsymmetricEquals() {
    Timestamp timestamp = new Timestamp(0);
    java.util.Date date = new java.util.Date(0);

    assertTrue(date.equals(timestamp));
    assertFalse(timestamp.equals(date));
  }
}
