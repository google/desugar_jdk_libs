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


import java.util.Objects;

/**
 * A time without a time-zone in the ISO-8601 calendar system,
 * such as {@code 10:15:30}.
 * <p>
 * {@code LocalTime} is an immutable date-time object that represents a time,
 * often viewed as hour-minute-second.
 * Time is represented to nanosecond precision.
 * For example, the value "13:45.30.123456789" can be stored in a {@code LocalTime}.
 * <p>
 * This class does not store or represent a date or time-zone.
 * Instead, it is a description of the local time as seen on a wall clock.
 * It cannot represent an instant on the time-line without additional information
 * such as an offset or time-zone.
 * <p>
 * The ISO-8601 calendar system is the modern civil calendar system used today
 * in most of the world. This API assumes that all calendar systems use the same
 * representation, this class, for time-of-day.
 *
 * <p>
 * This is a <a href="{@docRoot}/java.base/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code LocalTime} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * @since 1.8
 */
public final class DesugarLocalTime {

    static final long NANOS_PER_SECOND =  1000_000_000L;
    static final int SECONDS_PER_DAY = 86400;

    private DesugarLocalTime() {}

    /**
     * Converts this {@code LocalTime} to the number of seconds since the epoch
     * of 1970-01-01T00:00:00Z.
     * <p>
     * This combines this local time with the specified date and
     * offset to calculate the epoch-second value, which is the
     * number of elapsed seconds from 1970-01-01T00:00:00Z.
     * Instants on the time-line after the epoch are positive, earlier
     * are negative.
     *
     * @param date the local date, not null
     * @param offset the zone offset, not null
     * @return the number of seconds since the epoch of 1970-01-01T00:00:00Z, may be negative
     * @since 9
     */
    public static long toEpochSecond(LocalTime receiver, LocalDate date, ZoneOffset offset) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(offset, "offset");
        long epochDay = date.toEpochDay();
        long secs = epochDay * 86400 + receiver.toSecondOfDay();
        secs -= offset.getTotalSeconds();
        return secs;
    }

    /**
     * Obtains an instance of {@code LocalTime} from an {@code Instant} and zone ID.
     * <p>
     * This creates a local time based on the specified instant.
     * First, the offset from UTC/Greenwich is obtained using the zone ID and instant,
     * which is simple as there is only one valid offset for each instant.
     * Then, the instant and offset are used to calculate the local time.
     *
     * @param instant  the instant to create the time from, not null
     * @param zone  the time-zone, which may be an offset, not null
     * @return the local time, not null
     * @since 9
     */
    public static LocalTime ofInstant(Instant instant, ZoneId zone) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zone, "zone");
        ZoneOffset offset = zone.getRules().getOffset(instant);
        long localSecond = instant.getEpochSecond() + offset.getTotalSeconds();
        int secsOfDay = Math.floorMod(localSecond, SECONDS_PER_DAY);
        return LocalTime.ofNanoOfDay(secsOfDay * NANOS_PER_SECOND + instant.getNano());
    }
}
