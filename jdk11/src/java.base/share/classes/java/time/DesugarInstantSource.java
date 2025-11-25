/*
 * Copyright (c) 2021, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package java.time;

import java.time.InstantSource.SourceClock;

/**
 * FOR DESUGAR: In between api 26 and 34, Clock does not implement InstantSource. R8 rewrites the
 * invoke interface to these methods. R8 rewrites `instance of InstantSource` to the last method.
 */
public final class DesugarInstantSource {

  private DesugarInstantSource() {}

  public static Instant instant(InstantSource source) {
    if (source instanceof Clock) {
      return ((Clock) source).instant();
    }
    return source.instant();
  }

  public static long millis(InstantSource source) {
    if (source instanceof Clock) {
      return ((Clock) source).millis();
    }
    return source.millis();
  }

  public static Clock withZone(InstantSource source, ZoneId zone) {
    if (source instanceof Clock) {
      return ((Clock) source).withZone(zone);
    }
    return source.withZone(zone);
  }

  public static long superMillis(InstantSource source) {
    return instant(source).toEpochMilli();
  }

  public static Clock superWithZone(InstantSource source, ZoneId zone) {
    return new SourceClock(source, zone);
  }

  public static boolean instanceofInstantSource(Object o) {
    return o instanceof Clock || o instanceof InstantSource;
  }

  public static InstantSource checkCastInstantSource(Object o) {
    // FOR DESUGAR: The checkcast in this method is removed by R8.
    if (o instanceof Clock || o instanceof InstantSource) {
      return (InstantSource) o;
    }
    throw new ClassCastException(o + " cannot be cast to InstantSource");
  }
}
