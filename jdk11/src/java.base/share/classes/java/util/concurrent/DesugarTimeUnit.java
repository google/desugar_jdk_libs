/*
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

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * A {@code TimeUnit} represents time durations at a given unit of
 * granularity and provides utility methods to convert across units,
 * and to perform timing and delay operations in these units.  A
 * {@code TimeUnit} does not maintain time information, but only
 * helps organize and use time representations that may be maintained
 * separately across various contexts.  A nanosecond is defined as one
 * thousandth of a microsecond, a microsecond as one thousandth of a
 * millisecond, a millisecond as one thousandth of a second, a minute
 * as sixty seconds, an hour as sixty minutes, and a day as twenty four
 * hours.
 *
 * <p>A {@code TimeUnit} is mainly used to inform time-based methods
 * how a given timing parameter should be interpreted. For example,
 * the following code will timeout in 50 milliseconds if the {@link
 * java.util.concurrent.locks.Lock lock} is not available:
 *
 * <pre> {@code
 * Lock lock = ...;
 * if (lock.tryLock(50L, TimeUnit.MILLISECONDS)) ...}</pre>
 *
 * while this code will timeout in 50 seconds:
 * <pre> {@code
 * Lock lock = ...;
 * if (lock.tryLock(50L, TimeUnit.SECONDS)) ...}</pre>
 *
 * Note however, that there is no guarantee that a particular timeout
 * implementation will be able to notice the passage of time at the
 * same granularity as the given {@code TimeUnit}.
 *
 * @since 1.5
 * @author Doug Lea
 */
public class DesugarTimeUnit {

  // Scales as constants
  private static final long NANO_SCALE = 1L;
  private static final long MICRO_SCALE = 1000L * NANO_SCALE;
  private static final long MILLI_SCALE = 1000L * MICRO_SCALE;
  private static final long SECOND_SCALE = 1000L * MILLI_SCALE;
  private static final long MINUTE_SCALE = 60L * SECOND_SCALE;
  private static final long HOUR_SCALE = 60L * MINUTE_SCALE;
  private static final long DAY_SCALE = 24L * HOUR_SCALE;

    private DesugarTimeUnit() {}

    /**
     * Converts this {@code TimeUnit} to the equivalent {@code ChronoUnit}.
     *
     * @return the converted equivalent ChronoUnit
     * @since 9
     */
    public static ChronoUnit toChronoUnit(TimeUnit timeUnit) {
        switch (timeUnit) {
        case NANOSECONDS:  return ChronoUnit.NANOS;
        case MICROSECONDS: return ChronoUnit.MICROS;
        case MILLISECONDS: return ChronoUnit.MILLIS;
        case SECONDS:      return ChronoUnit.SECONDS;
        case MINUTES:      return ChronoUnit.MINUTES;
        case HOURS:        return ChronoUnit.HOURS;
        case DAYS:         return ChronoUnit.DAYS;
        default: throw new AssertionError();
        }
    }

    /**
     * Converts a {@code ChronoUnit} to the equivalent {@code TimeUnit}.
     *
     * @param chronoUnit the ChronoUnit to convert
     * @return the converted equivalent TimeUnit
     * @throws IllegalArgumentException if {@code chronoUnit} has no
     *         equivalent TimeUnit
     * @throws NullPointerException if {@code chronoUnit} is null
     * @since 9
     */
    public static TimeUnit of(ChronoUnit chronoUnit) {
        switch (Objects.requireNonNull(chronoUnit, "chronoUnit")) {
        case NANOS:   return TimeUnit.NANOSECONDS;
        case MICROS:  return TimeUnit.MICROSECONDS;
        case MILLIS:  return TimeUnit.MILLISECONDS;
        case SECONDS: return TimeUnit.SECONDS;
        case MINUTES: return TimeUnit.MINUTES;
        case HOURS:   return TimeUnit.HOURS;
        case DAYS:    return TimeUnit.DAYS;
        default:
            throw new IllegalArgumentException(
                "No TimeUnit equivalent for " + chronoUnit);
        }
    }

  /**
   * Converts the given time duration to this unit.
   *
   * <p>For any TimeUnit {@code unit}, {@code unit.convert(Duration.ofNanos(n))} is equivalent to
   * {@code unit.convert(n, NANOSECONDS)}, and {@code unit.convert(Duration.of(n,
   * unit.toChronoUnit()))} is equivalent to {@code n} (in the absence of overflow).
   *
   * @apiNote This method differs from {@link Duration#toNanos()} in that it does not throw {@link
   *     ArithmeticException} on numeric overflow.
   * @param duration the time duration
   * @return the converted duration in this unit, or {@code Long.MIN_VALUE} if conversion would
   *     negatively overflow, or {@code Long.MAX_VALUE} if it would positively overflow.
   * @throws NullPointerException if {@code duration} is null
   * @see Duration#of(long,TemporalUnit)
   * @since 11
   */
  public static long convert(TimeUnit timeUnit, Duration duration) {
    return timeUnit.convert(duration.toNanos(), TimeUnit.NANOSECONDS);
  }
}
