/*
 * Copyright (c) 2012, 2018, Oracle and/or its affiliates. All rights reserved.
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
 * Copyright (c) 2007-2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package java.time;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Objects;

/**
 * A time-based amount of time, such as '34.5 seconds'.
 * <p>
 * This class models a quantity or amount of time in terms of seconds and nanoseconds.
 * It can be accessed using other duration-based units, such as minutes and hours.
 * In addition, the {@link ChronoUnit#DAYS DAYS} unit can be used and is treated as
 * exactly equal to 24 hours, thus ignoring daylight savings effects.
 * See {@link Period} for the date-based equivalent to this class.
 * <p>
 * A physical duration could be of infinite length.
 * For practicality, the duration is stored with constraints similar to {@link Instant}.
 * The duration uses nanosecond resolution with a maximum value of the seconds that can
 * be held in a {@code long}. This is greater than the current estimated age of the universe.
 * <p>
 * The range of a duration requires the storage of a number larger than a {@code long}.
 * To achieve this, the class stores a {@code long} representing seconds and an {@code int}
 * representing nanosecond-of-second, which will always be between 0 and 999,999,999.
 * The model is of a directed duration, meaning that the duration may be negative.
 * <p>
 * The duration is measured in "seconds", but these are not necessarily identical to
 * the scientific "SI second" definition based on atomic clocks.
 * This difference only impacts durations measured near a leap-second and should not affect
 * most applications.
 * See {@link Instant} for a discussion as to the meaning of the second and time-scales.
 *
 * <p>
 * This is a <a href="{@docRoot}/java.base/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code Duration} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * @since 1.8
 */
public final class DesugarDuration {

    static final int SECONDS_PER_DAY = 86400;
    static final int MINUTES_PER_HOUR = 60;
    static final long NANOS_PER_DAY = 86400000000000L;
    static final int SECONDS_PER_MINUTE = 60;

    /**
     * Returns number of whole times a specified Duration occurs within this Duration.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param divisor the value to divide the duration by, positive or negative, not null
     * @return number of whole times, rounded toward zero, a specified
     *         {@code Duration} occurs within this Duration, may be negative
     * @throws ArithmeticException if the divisor is zero, or if numeric overflow occurs
     * @since 9
     */
    public static long dividedBy(Duration receiver, Duration divisor) {
        Objects.requireNonNull(divisor, "divisor");
        BigDecimal dividendBigD = toBigDecimalSeconds(receiver);
        BigDecimal divisorBigD = toBigDecimalSeconds(divisor);
        return dividendBigD.divideToIntegralValue(divisorBigD).longValueExact();
    }

    /**
     * Gets the number of seconds in this duration.
     * <p>
     * This returns the total number of whole seconds in the duration.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @return the whole seconds part of the length of the duration, positive or negative
     * @since 9
     */
    public static long toSeconds(Duration receiver) {
        // For desugar: Use the same implementation that's available on SDK 26 or above.
        return receiver.getSeconds();
    }

    /**
     * Extracts the number of days in the duration.
     * <p>
     * This returns the total number of days in the duration by dividing the
     * number of seconds by 86400.
     * This is based on the standard definition of a day as 24 hours.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @return the number of days in the duration, may be negative
     * @since 9
     */
    public static long toDaysPart(Duration receiver) {
        long seconds = receiver.getSeconds();
        return seconds / SECONDS_PER_DAY;
    }

    /**
     * Extracts the number of hours part in the duration.
     * <p>
     * This returns the number of remaining hours when dividing {@link #toHours}
     * by hours in a day.
     * This is based on the standard definition of a day as 24 hours.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @return the number of hours part in the duration, may be negative
     * @since 9
     */
    public static int toHoursPart(Duration receiver) {
        return (int) (receiver.toHours() % 24);
    }

    /**
     * Extracts the number of minutes part in the duration.
     * <p>
     * This returns the number of remaining minutes when dividing {@link #toMinutes}
     * by minutes in an hour.
     * This is based on the standard definition of an hour as 60 minutes.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @return the number of minutes parts in the duration, may be negative
     * @since 9
     */
    public static int toMinutesPart(Duration receiver) {
        return (int) (receiver.toMinutes() % MINUTES_PER_HOUR);
    }

    /**
     * Extracts the number of seconds part in the duration.
     * <p>
     * This returns the remaining seconds when dividing {@link #toSeconds}
     * by seconds in a minute.
     * This is based on the standard definition of a minute as 60 seconds.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @return the number of seconds parts in the duration, may be negative
     * @since 9
     */
    public static int toSecondsPart(Duration receiver) {
        long seconds = receiver.getSeconds();
        return (int) (seconds % SECONDS_PER_MINUTE);
    }

    /**
     * Extracts the number of milliseconds part of the duration.
     * <p>
     * This returns the milliseconds part by dividing the number of nanoseconds by 1,000,000.
     * The length of the duration is stored using two fields - seconds and nanoseconds.
     * The nanoseconds part is a value from 0 to 999,999,999 that is an adjustment to
     * the length in seconds.
     * The total duration is defined by calling {@link #getNano()} and {@link #getSeconds()}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @return the number of milliseconds part of the duration.
     * @since 9
     */
    public static int toMillisPart(Duration receiver) {
        int nanos = receiver.getNano();
        return nanos / 1000_000;
    }

    /**
     * Get the nanoseconds part within seconds of the duration.
     * <p>
     * The length of the duration is stored using two fields - seconds and nanoseconds.
     * The nanoseconds part is a value from 0 to 999,999,999 that is an adjustment to
     * the length in seconds.
     * The total duration is defined by calling {@link #getNano()} and {@link #getSeconds()}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @return the nanoseconds within the second part of the length of the duration, from 0 to 999,999,999
     * @since 9
     */
    public static int toNanosPart(Duration receiver) {
        int nanos = receiver.getNano();
        return nanos;
    }

    /**
     * Returns a copy of this {@code Duration} truncated to the specified unit.
     * <p>
     * Truncating the duration returns a copy of the original with conceptual fields
     * smaller than the specified unit set to zero.
     * For example, truncating with the {@link ChronoUnit#MINUTES MINUTES} unit will
     * round down towards zero to the nearest minute, setting the seconds and
     * nanoseconds to zero.
     * <p>
     * The unit must have a {@linkplain TemporalUnit#getDuration() duration}
     * that divides into the length of a standard day without remainder.
     * This includes all
     * {@linkplain ChronoUnit#isTimeBased() time-based units on {@code ChronoUnit}}
     * and {@link ChronoUnit#DAYS DAYS}. Other ChronoUnits throw an exception.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param unit the unit to truncate to, not null
     * @return a {@code Duration} based on this duration with the time truncated, not null
     * @throws DateTimeException if the unit is invalid for truncation
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @since 9
     */
    public static Duration truncatedTo(Duration receiver, TemporalUnit unit) {
        long seconds = receiver.getSeconds();
        int nanos = receiver.getNano();
        Objects.requireNonNull(unit, "unit");
        if (unit == ChronoUnit.SECONDS && (seconds >= 0 || nanos == 0)) {
            // For Desugar: Use public API to construct instead.
            // return new Duration(seconds, 0);
            return Duration.ofSeconds(seconds);
        } else if (unit == ChronoUnit.NANOS) {
            return receiver;
        }
        Duration unitDur = unit.getDuration();
        if (unitDur.getSeconds() > LocalTime.SECONDS_PER_DAY) {
            throw new UnsupportedTemporalTypeException("Unit is too large to be used for truncation");
        }
        long dur = unitDur.toNanos();
        if ((LocalTime.NANOS_PER_DAY % dur) != 0) {
            throw new UnsupportedTemporalTypeException("Unit must divide into a standard day without remainder");
        }
        long nod = (seconds % LocalTime.SECONDS_PER_DAY) * LocalTime.NANOS_PER_SECOND + nanos;
        long result = (nod / dur) * dur;
        return receiver.plusNanos(result - nod);
    }

    private static BigDecimal toBigDecimalSeconds(Duration receiver) {
        long seconds = receiver.getSeconds();
        int nanos = receiver.getNano();
        return BigDecimal.valueOf(seconds).add(BigDecimal.valueOf(nanos, 9));
    }

}
