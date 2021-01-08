/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright Taligent, Inc. 1996-1998 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996-1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 */

package java.util;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

/**
 * <code>GregorianCalendar</code> is a concrete subclass of
 * <code>Calendar</code> and provides the standard calendar system
 * used by most of the world.
 *
 * <p> <code>GregorianCalendar</code> is a hybrid calendar that
 * supports both the Julian and Gregorian calendar systems with the
 * support of a single discontinuity, which corresponds by default to
 * the Gregorian date when the Gregorian calendar was instituted
 * (October 15, 1582 in some countries, later in others).  The cutover
 * date may be changed by the caller by calling {@link
 * #setGregorianChange(Date) setGregorianChange()}.
 *
 * <p>
 * Historically, in those countries which adopted the Gregorian calendar first,
 * October 4, 1582 (Julian) was thus followed by October 15, 1582 (Gregorian). This calendar models
 * this correctly.  Before the Gregorian cutover, <code>GregorianCalendar</code>
 * implements the Julian calendar.  The only difference between the Gregorian
 * and the Julian calendar is the leap year rule. The Julian calendar specifies
 * leap years every four years, whereas the Gregorian calendar omits century
 * years which are not divisible by 400.
 *
 * <p>
 * <code>GregorianCalendar</code> implements <em>proleptic</em> Gregorian and
 * Julian calendars. That is, dates are computed by extrapolating the current
 * rules indefinitely far backward and forward in time. As a result,
 * <code>GregorianCalendar</code> may be used for all years to generate
 * meaningful and consistent results. However, dates obtained using
 * <code>GregorianCalendar</code> are historically accurate only from March 1, 4
 * AD onward, when modern Julian calendar rules were adopted.  Before this date,
 * leap year rules were applied irregularly, and before 45 BC the Julian
 * calendar did not even exist.
 *
 * <p>
 * Prior to the institution of the Gregorian calendar, New Year's Day was
 * March 25. To avoid confusion, this calendar always uses January 1. A manual
 * adjustment may be made if desired for dates that are prior to the Gregorian
 * changeover and which fall between January 1 and March 24.
 *
 * <h3><a name="week_and_year">Week Of Year and Week Year</a></h3>
 *
 * <p>Values calculated for the {@link Calendar#WEEK_OF_YEAR
 * WEEK_OF_YEAR} field range from 1 to 53. The first week of a
 * calendar year is the earliest seven day period starting on {@link
 * Calendar#getFirstDayOfWeek() getFirstDayOfWeek()} that contains at
 * least {@link Calendar#getMinimalDaysInFirstWeek()
 * getMinimalDaysInFirstWeek()} days from that year. It thus depends
 * on the values of {@code getMinimalDaysInFirstWeek()}, {@code
 * getFirstDayOfWeek()}, and the day of the week of January 1. Weeks
 * between week 1 of one year and week 1 of the following year
 * (exclusive) are numbered sequentially from 2 to 52 or 53 (except
 * for year(s) involved in the Julian-Gregorian transition).
 *
 * <p>The {@code getFirstDayOfWeek()} and {@code
 * getMinimalDaysInFirstWeek()} values are initialized using
 * locale-dependent resources when constructing a {@code
 * GregorianCalendar}. <a name="iso8601_compatible_setting">The week
 * determination is compatible</a> with the ISO 8601 standard when {@code
 * getFirstDayOfWeek()} is {@code MONDAY} and {@code
 * getMinimalDaysInFirstWeek()} is 4, which values are used in locales
 * where the standard is preferred. These values can explicitly be set by
 * calling {@link Calendar#setFirstDayOfWeek(int) setFirstDayOfWeek()} and
 * {@link Calendar#setMinimalDaysInFirstWeek(int)
 * setMinimalDaysInFirstWeek()}.
 *
 * <p>A <a name="week_year"><em>week year</em></a> is in sync with a
 * {@code WEEK_OF_YEAR} cycle. All weeks between the first and last
 * weeks (inclusive) have the same <em>week year</em> value.
 * Therefore, the first and last days of a week year may have
 * different calendar year values.
 *
 * <p>For example, January 1, 1998 is a Thursday. If {@code
 * getFirstDayOfWeek()} is {@code MONDAY} and {@code
 * getMinimalDaysInFirstWeek()} is 4 (ISO 8601 standard compatible
 * setting), then week 1 of 1998 starts on December 29, 1997, and ends
 * on January 4, 1998. The week year is 1998 for the last three days
 * of calendar year 1997. If, however, {@code getFirstDayOfWeek()} is
 * {@code SUNDAY}, then week 1 of 1998 starts on January 4, 1998, and
 * ends on January 10, 1998; the first three days of 1998 then are
 * part of week 53 of 1997 and their week year is 1997.
 *
 * <h4>Week Of Month</h4>
 *
 * <p>Values calculated for the <code>WEEK_OF_MONTH</code> field range from 0
 * to 6.  Week 1 of a month (the days with <code>WEEK_OF_MONTH =
 * 1</code>) is the earliest set of at least
 * <code>getMinimalDaysInFirstWeek()</code> contiguous days in that month,
 * ending on the day before <code>getFirstDayOfWeek()</code>.  Unlike
 * week 1 of a year, week 1 of a month may be shorter than 7 days, need
 * not start on <code>getFirstDayOfWeek()</code>, and will not include days of
 * the previous month.  Days of a month before week 1 have a
 * <code>WEEK_OF_MONTH</code> of 0.
 *
 * <p>For example, if <code>getFirstDayOfWeek()</code> is <code>SUNDAY</code>
 * and <code>getMinimalDaysInFirstWeek()</code> is 4, then the first week of
 * January 1998 is Sunday, January 4 through Saturday, January 10.  These days
 * have a <code>WEEK_OF_MONTH</code> of 1.  Thursday, January 1 through
 * Saturday, January 3 have a <code>WEEK_OF_MONTH</code> of 0.  If
 * <code>getMinimalDaysInFirstWeek()</code> is changed to 3, then January 1
 * through January 3 have a <code>WEEK_OF_MONTH</code> of 1.
 *
 * <h4>Default Fields Values</h4>
 *
 * <p>The <code>clear</code> method sets calendar field(s)
 * undefined. <code>GregorianCalendar</code> uses the following
 * default value for each calendar field if its value is undefined.
 *
 * <table cellpadding="0" cellspacing="3" border="0"
 *        summary="GregorianCalendar default field values"
 *        style="text-align: left; width: 66%;">
 *   <tbody>
 *     <tr>
 *       <th style="vertical-align: top; background-color: rgb(204, 204, 255);
 *           text-align: center;">Field<br>
 *       </th>
 *       <th style="vertical-align: top; background-color: rgb(204, 204, 255);
 *           text-align: center;">Default Value<br>
 *       </th>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: middle;">
 *              <code>ERA<br></code>
 *       </td>
 *       <td style="vertical-align: middle;">
 *              <code>AD<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: middle; background-color: rgb(238, 238, 255);">
 *              <code>YEAR<br></code>
 *       </td>
 *       <td style="vertical-align: middle; background-color: rgb(238, 238, 255);">
 *              <code>1970<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: middle;">
 *              <code>MONTH<br></code>
 *       </td>
 *       <td style="vertical-align: middle;">
 *              <code>JANUARY<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: top; background-color: rgb(238, 238, 255);">
 *              <code>DAY_OF_MONTH<br></code>
 *       </td>
 *       <td style="vertical-align: top; background-color: rgb(238, 238, 255);">
 *              <code>1<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: middle;">
 *              <code>DAY_OF_WEEK<br></code>
 *       </td>
 *       <td style="vertical-align: middle;">
 *              <code>the first day of week<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: top; background-color: rgb(238, 238, 255);">
 *              <code>WEEK_OF_MONTH<br></code>
 *       </td>
 *       <td style="vertical-align: top; background-color: rgb(238, 238, 255);">
 *              <code>0<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: top;">
 *              <code>DAY_OF_WEEK_IN_MONTH<br></code>
 *       </td>
 *       <td style="vertical-align: top;">
 *              <code>1<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: middle; background-color: rgb(238, 238, 255);">
 *              <code>AM_PM<br></code>
 *       </td>
 *       <td style="vertical-align: middle; background-color: rgb(238, 238, 255);">
 *              <code>AM<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: middle;">
 *              <code>HOUR, HOUR_OF_DAY, MINUTE, SECOND, MILLISECOND<br></code>
 *       </td>
 *       <td style="vertical-align: middle;">
 *              <code>0<br></code>
 *       </td>
 *     </tr>
 *   </tbody>
 * </table>
 * <br>Default values are not applicable for the fields not listed above.
 *
 * <p>
 * <strong>Example:</strong>
 * <blockquote>
 * <pre>
 * // get the supported ids for GMT-08:00 (Pacific Standard Time)
 * String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
 * // if no ids were returned, something is wrong. get out.
 * if (ids.length == 0)
 *     System.exit(0);
 *
 *  // begin output
 * System.out.println("Current Time");
 *
 * // create a Pacific Standard Time time zone
 * SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);
 *
 * // set up rules for Daylight Saving Time
 * pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
 * pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
 *
 * // create a GregorianCalendar with the Pacific Daylight time zone
 * // and the current date and time
 * Calendar calendar = new GregorianCalendar(pdt);
 * Date trialTime = new Date();
 * calendar.setTime(trialTime);
 *
 * // print out a bunch of interesting things
 * System.out.println("ERA: " + calendar.get(Calendar.ERA));
 * System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
 * System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
 * System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
 * System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
 * System.out.println("DATE: " + calendar.get(Calendar.DATE));
 * System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
 * System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
 * System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
 * System.out.println("DAY_OF_WEEK_IN_MONTH: "
 *                    + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
 * System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
 * System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
 * System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
 * System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
 * System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
 * System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
 * System.out.println("ZONE_OFFSET: "
 *                    + (calendar.get(Calendar.ZONE_OFFSET)/(60*60*1000)));
 * System.out.println("DST_OFFSET: "
 *                    + (calendar.get(Calendar.DST_OFFSET)/(60*60*1000)));

 * System.out.println("Current Time, with hour reset to 3");
 * calendar.clear(Calendar.HOUR_OF_DAY); // so doesn't override
 * calendar.set(Calendar.HOUR, 3);
 * System.out.println("ERA: " + calendar.get(Calendar.ERA));
 * System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
 * System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
 * System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
 * System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
 * System.out.println("DATE: " + calendar.get(Calendar.DATE));
 * System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
 * System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
 * System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
 * System.out.println("DAY_OF_WEEK_IN_MONTH: "
 *                    + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
 * System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
 * System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
 * System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
 * System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
 * System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
 * System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
 * System.out.println("ZONE_OFFSET: "
 *        + (calendar.get(Calendar.ZONE_OFFSET)/(60*60*1000))); // in hours
 * System.out.println("DST_OFFSET: "
 *        + (calendar.get(Calendar.DST_OFFSET)/(60*60*1000))); // in hours
 * </pre>
 * </blockquote>
 *
 * @see          TimeZone
 * @author David Goldsmith, Mark Davis, Chen-Lieh Huang, Alan Liu
 * @since JDK1.1
 */
// For desugar: Copy of j.u.GregorianCalendar with methods introduced since 1.8
public class DesugarGregorianCalendar {
    private DesugarGregorianCalendar() {}  // for desugar: no instantiation, static methods only

    /**
     * Converts this object to a {@code ZonedDateTime} that represents
     * the same point on the time-line as this {@code GregorianCalendar}.
     * <p>
     * Since this object supports a Julian-Gregorian cutover date and
     * {@code ZonedDateTime} does not, it is possible that the resulting year,
     * month and day will have different values.  The result will represent the
     * correct date in the ISO calendar system, which will also be the same value
     * for Modified Julian Days.
     *
     * @return a zoned date-time representing the same point on the time-line
     *  as this gregorian calendar
     * @since 1.8
     */
    // For desugar: made static so it can exist outside original class
    public static ZonedDateTime toZonedDateTime(GregorianCalendar instance) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(instance.getTimeInMillis()),
                                       // For desugar: use TimeZone helper for @since 1.8 methods
                                       // getTimeZone().toZoneId();
                                       DesugarTimeZone.toZoneId(instance.getTimeZone()));
    }

    /**
     * Obtains an instance of {@code GregorianCalendar} with the default locale
     * from a {@code ZonedDateTime} object.
     * <p>
     * Since {@code ZonedDateTime} does not support a Julian-Gregorian cutover
     * date and uses ISO calendar system, the return GregorianCalendar is a pure
     * Gregorian calendar and uses ISO 8601 standard for week definitions,
     * which has {@code MONDAY} as the {@link Calendar#getFirstDayOfWeek()
     * FirstDayOfWeek} and {@code 4} as the value of the
     * {@link Calendar#getMinimalDaysInFirstWeek() MinimalDaysInFirstWeek}.
     * <p>
     * {@code ZoneDateTime} can store points on the time-line further in the
     * future and further in the past than {@code GregorianCalendar}. In this
     * scenario, this method will throw an {@code IllegalArgumentException}
     * exception.
     *
     * @param zdt  the zoned date-time object to convert
     * @return  the gregorian calendar representing the same point on the
     *  time-line as the zoned date-time provided
     * @exception NullPointerException if {@code zdt} is null
     * @exception IllegalArgumentException if the zoned date-time is too
     * large to represent as a {@code GregorianCalendar}
     * @since 1.8
     */
    public static GregorianCalendar from(ZonedDateTime zdt) {
        // For desugar: use TimeZone helper for @since 1.8 methods
        // GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone(zdt.getZone()));
        GregorianCalendar cal = new GregorianCalendar(DesugarTimeZone.getTimeZone(zdt.getZone()));
        cal.setGregorianChange(new Date(Long.MIN_VALUE));
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setMinimalDaysInFirstWeek(4);
        try {
            // For desugar: use j.l.Math helper for @since 1.8 methods
            cal.setTimeInMillis(Math.addExact(Math.multiplyExact(zdt.toEpochSecond(), 1000),
                                              zdt.get(ChronoField.MILLI_OF_SECOND)));
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException(ex);
        }
        return cal;
    }
}
