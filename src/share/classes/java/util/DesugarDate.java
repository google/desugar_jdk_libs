/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

import java.time.Instant;

/**
 * The class <code>Date</code> represents a specific instant
 * in time, with millisecond precision.
 * <p>
 * Prior to JDK&nbsp;1.1, the class <code>Date</code> had two additional
 * functions.  It allowed the interpretation of dates as year, month, day, hour,
 * minute, and second values.  It also allowed the formatting and parsing
 * of date strings.  Unfortunately, the API for these functions was not
 * amenable to internationalization.  As of JDK&nbsp;1.1, the
 * <code>Calendar</code> class should be used to convert between dates and time
 * fields and the <code>DateFormat</code> class should be used to format and
 * parse date strings.
 * The corresponding methods in <code>Date</code> are deprecated.
 * <p>
 * Although the <code>Date</code> class is intended to reflect
 * coordinated universal time (UTC), it may not do so exactly,
 * depending on the host environment of the Java Virtual Machine.
 * Nearly all modern operating systems assume that 1&nbsp;day&nbsp;=
 * 24&nbsp;&times;&nbsp;60&nbsp;&times;&nbsp;60&nbsp;= 86400 seconds
 * in all cases. In UTC, however, about once every year or two there
 * is an extra second, called a "leap second." The leap
 * second is always added as the last second of the day, and always
 * on December 31 or June 30. For example, the last minute of the
 * year 1995 was 61 seconds long, thanks to an added leap second.
 * Most computer clocks are not accurate enough to be able to reflect
 * the leap-second distinction.
 * <p>
 * Some computer standards are defined in terms of Greenwich mean
 * time (GMT), which is equivalent to universal time (UT).  GMT is
 * the "civil" name for the standard; UT is the
 * "scientific" name for the same standard. The
 * distinction between UTC and UT is that UTC is based on an atomic
 * clock and UT is based on astronomical observations, which for all
 * practical purposes is an invisibly fine hair to split. Because the
 * earth's rotation is not uniform (it slows down and speeds up
 * in complicated ways), UT does not always flow uniformly. Leap
 * seconds are introduced as needed into UTC so as to keep UTC within
 * 0.9 seconds of UT1, which is a version of UT with certain
 * corrections applied. There are other time and date systems as
 * well; for example, the time scale used by the satellite-based
 * global positioning system (GPS) is synchronized to UTC but is
 * <i>not</i> adjusted for leap seconds. An interesting source of
 * further information is the U.S. Naval Observatory, particularly
 * the Directorate of Time at:
 * <blockquote><pre>
 *     <a href=http://tycho.usno.navy.mil>http://tycho.usno.navy.mil</a>
 * </pre></blockquote>
 * <p>
 * and their definitions of "Systems of Time" at:
 * <blockquote><pre>
 *     <a href=http://tycho.usno.navy.mil/systime.html>http://tycho.usno.navy.mil/systime.html</a>
 * </pre></blockquote>
 * <p>
 * In all methods of class <code>Date</code> that accept or return
 * year, month, date, hours, minutes, and seconds values, the
 * following representations are used:
 * <ul>
 * <li>A year <i>y</i> is represented by the integer
 *     <i>y</i>&nbsp;<code>-&nbsp;1900</code>.
 * <li>A month is represented by an integer from 0 to 11; 0 is January,
 *     1 is February, and so forth; thus 11 is December.
 * <li>A date (day of month) is represented by an integer from 1 to 31
 *     in the usual manner.
 * <li>An hour is represented by an integer from 0 to 23. Thus, the hour
 *     from midnight to 1 a.m. is hour 0, and the hour from noon to 1
 *     p.m. is hour 12.
 * <li>A minute is represented by an integer from 0 to 59 in the usual manner.
 * <li>A second is represented by an integer from 0 to 61; the values 60 and
 *     61 occur only for leap seconds and even then only in Java
 *     implementations that actually track leap seconds correctly. Because
 *     of the manner in which leap seconds are currently introduced, it is
 *     extremely unlikely that two leap seconds will occur in the same
 *     minute, but this specification follows the date and time conventions
 *     for ISO C.
 * </ul>
 * <p>
 * In all cases, arguments given to methods for these purposes need
 * not fall within the indicated ranges; for example, a date may be
 * specified as January 32 and is interpreted as meaning February 1.
 *
 * @author  James Gosling
 * @author  Arthur van Hoff
 * @author  Alan Liu
 * @see     java.text.DateFormat
 * @see     java.util.Calendar
 * @see     java.util.TimeZone
 * @since   JDK1.0
 */
// for desugar: copy of java.util.Date with conversion methods for java.time.Instant
public class DesugarDate
{
    // for desugar: static methods only
    private DesugarDate() {
    }

    /**
     * Obtains an instance of {@code Date} from an {@code Instant} object.
     * <p>
     * {@code Instant} uses a precision of nanoseconds, whereas {@code Date}
     * uses a precision of milliseconds.  The conversion will trancate any
     * excess precision information as though the amount in nanoseconds was
     * subject to integer division by one million.
     * <p>
     * {@code Instant} can store points on the time-line further in the future
     * and further in the past than {@code Date}. In this scenario, this method
     * will throw an exception.
     *
     * @param instant  the instant to convert
     * @return a {@code Date} representing the same point on the time-line as
     *  the provided instant
     * @exception NullPointerException if {@code instant} is null.
     * @exception IllegalArgumentException if the instant is too large to
     *  represent as a {@code Date}
     * @since 1.8
     */
    public static Date from(Instant instant) {
        try {
            return new Date(instant.toEpochMilli());
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Converts this {@code Date} object to an {@code Instant}.
     * <p>
     * The conversion creates an {@code Instant} that represents the same
     * point on the time-line as this {@code Date}.
     *
     * @return an instant representing the same point on the time-line as
     *  this {@code Date} object
     * @since 1.8
     */
    // for desugar: static method since it's declared outside of java.util.Date here
    public static Instant toInstant(Date date) {
        return Instant.ofEpochMilli(date.getTime());
    }
}
