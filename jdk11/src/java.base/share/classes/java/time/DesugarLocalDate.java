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

import java.time.zone.ZoneRules;
import java.util.Objects;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A date without a time-zone in the ISO-8601 calendar system,
 * such as {@code 2007-12-03}.
 * <p>
 * {@code LocalDate} is an immutable date-time object that represents a date,
 * often viewed as year-month-day. Other date fields, such as day-of-year,
 * day-of-week and week-of-year, can also be accessed.
 * For example, the value "2nd October 2007" can be stored in a {@code LocalDate}.
 * <p>
 * This class does not store or represent a time or time-zone.
 * Instead, it is a description of the date, as used for birthdays.
 * It cannot represent an instant on the time-line without additional information
 * such as an offset or time-zone.
 * <p>
 * The ISO-8601 calendar system is the modern civil calendar system used today
 * in most of the world. It is equivalent to the proleptic Gregorian calendar
 * system, in which today's rules for leap years are applied for all time.
 * For most applications written today, the ISO-8601 rules are entirely suitable.
 * However, any application that makes use of historical dates, and requires them
 * to be accurate will find the ISO-8601 approach unsuitable.
 *
 * <p>
 * This is a <a href="{@docRoot}/java.base/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code LocalDate} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * @since 1.8
 */
public final class DesugarLocalDate {

    /**
     * The epoch year {@code LocalDate}, '1970-01-01'.
     */
    public static final LocalDate EPOCH = LocalDate.of(1970, 1, 1);

    private static final int SECONDS_PER_DAY = 86400;

    private DesugarLocalDate() {}

    /**
     * Returns a sequential ordered stream of dates. The returned stream starts from this date
     * (inclusive) and goes to {@code endExclusive} (exclusive) by an incremental step of 1 day.
     * <p>
     * This method is equivalent to {@code datesUntil(endExclusive, Period.ofDays(1))}.
     *
     * @param endExclusive  the end date, exclusive, not null
     * @return a sequential {@code Stream} for the range of {@code LocalDate} values
     * @throws IllegalArgumentException if end date is before this date
     * @since 9
     */
    public static Stream<LocalDate> datesUntil(LocalDate receiver, LocalDate endExclusive) {
        long end = endExclusive.toEpochDay();
        long start = receiver.toEpochDay();
        if (end < start) {
            throw new IllegalArgumentException(endExclusive + " < " + receiver);
        }
        return LongStream.range(start, end).mapToObj(LocalDate::ofEpochDay);
    }

    /**
     * Returns a sequential ordered stream of dates by given incremental step. The returned stream
     * starts from this date (inclusive) and goes to {@code endExclusive} (exclusive).
     * <p>
     * The n-th date which appears in the stream is equal to {@code this.plus(step.multipliedBy(n))}
     * (but the result of step multiplication never overflows). For example, if this date is
     * {@code 2015-01-31}, the end date is {@code 2015-05-01} and the step is 1 month, then the
     * stream contains {@code 2015-01-31}, {@code 2015-02-28}, {@code 2015-03-31}, and
     * {@code 2015-04-30}.
     *
     * @param endExclusive  the end date, exclusive, not null
     * @param step  the non-zero, non-negative {@code Period} which represents the step.
     * @return a sequential {@code Stream} for the range of {@code LocalDate} values
     * @throws IllegalArgumentException if step is zero, or {@code step.getDays()} and
     *             {@code step.toTotalMonths()} have opposite sign, or end date is before this date
     *             and step is positive, or end date is after this date and step is negative
     * @since 9
     */
    public static Stream<LocalDate> datesUntil(LocalDate receiver, LocalDate endExclusive, Period step) {
        if (step.isZero()) {
            throw new IllegalArgumentException("step is zero");
        }
        long end = endExclusive.toEpochDay();
        long start = receiver.toEpochDay();
        long until = end - start;
        long months = step.toTotalMonths();
        long days = step.getDays();
        if ((months < 0 && days > 0) || (months > 0 && days < 0)) {
            throw new IllegalArgumentException("period months and days are of opposite sign");
        }
        if (until == 0) {
            return Stream.empty();
        }
        int sign = months > 0 || days > 0 ? 1 : -1;
        if (sign < 0 ^ until < 0) {
            throw new IllegalArgumentException(endExclusive + (sign < 0 ? " > " : " < ") + receiver);
        }
        if (months == 0) {
            long steps = (until - sign) / days; // non-negative
            return LongStream.rangeClosed(0, steps).mapToObj(
                n -> LocalDate.ofEpochDay(start + n * days));
        }
        // 48699/1600 = 365.2425/12, no overflow, non-negative result
        long steps = until * 1600 / (months * 48699 + days * 1600) + 1;
        long addMonths = months * steps;
        long addDays = days * steps;
        long maxAddMonths = months > 0 ? getProlepticMonth(LocalDate.MAX) - getProlepticMonth(receiver)
            : getProlepticMonth(receiver) - getProlepticMonth(LocalDate.MIN);
        // adjust steps estimation
        if (addMonths * sign > maxAddMonths
            || (receiver.plusMonths(addMonths).toEpochDay() + addDays) * sign >= end * sign) {
            steps--;
            addMonths -= months;
            addDays -= days;
            if (addMonths * sign > maxAddMonths
                || (receiver.plusMonths(addMonths).toEpochDay() + addDays) * sign >= end * sign) {
                steps--;
            }
        }
        return LongStream.rangeClosed(0, steps).mapToObj(
            n -> receiver.plusMonths(months * n).plusDays(days * n));
    }

    /**
     * Converts this {@code LocalDate} to the number of seconds since the epoch
     * of 1970-01-01T00:00:00Z.
     * <p>
     * This combines this local date with the specified time and
     * offset to calculate the epoch-second value, which is the
     * number of elapsed seconds from 1970-01-01T00:00:00Z.
     * Instants on the time-line after the epoch are positive, earlier
     * are negative.
     *
     * @param time the local time, not null
     * @param offset the zone offset, not null
     * @return the number of seconds since the epoch of 1970-01-01T00:00:00Z, may be negative
     * @since 9
     */
    public static long toEpochSecond(LocalDate receiver, LocalTime time, ZoneOffset offset) {
        Objects.requireNonNull(time, "time");
        Objects.requireNonNull(offset, "offset");
        long secs = receiver.toEpochDay() * SECONDS_PER_DAY + time.toSecondOfDay();
        secs -= offset.getTotalSeconds();
        return secs;
    }

    /**
     * Obtains an instance of {@code LocalDate} from an {@code Instant} and zone ID.
     * <p>
     * This creates a local date based on the specified instant.
     * First, the offset from UTC/Greenwich is obtained using the zone ID and instant,
     * which is simple as there is only one valid offset for each instant.
     * Then, the instant and offset are used to calculate the local date.
     *
     * @param instant  the instant to create the date from, not null
     * @param zone  the time-zone, which may be an offset, not null
     * @return the local date, not null
     * @throws DateTimeException if the result exceeds the supported range
     * @since 9
     */
    public static LocalDate ofInstant(Instant instant, ZoneId zone) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zone, "zone");
        ZoneRules rules = zone.getRules();
        ZoneOffset offset = rules.getOffset(instant);
        long localSecond = instant.getEpochSecond() + offset.getTotalSeconds();
        long localEpochDay = Math.floorDiv(localSecond, SECONDS_PER_DAY);
        return LocalDate.ofEpochDay(localEpochDay);
    }

    private static long getProlepticMonth(LocalDate receiver) {
        int year = receiver.getYear();
        int month = receiver.getMonthValue();
        return (year * 12L + month - 1);
    }
}
