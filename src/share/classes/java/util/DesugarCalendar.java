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

/**
 * The <code>Calendar</code> class is an abstract class that provides methods
 * for converting between a specific instant in time and a set of {@link
 * #fields calendar fields} such as <code>YEAR</code>, <code>MONTH</code>,
 * <code>DAY_OF_MONTH</code>, <code>HOUR</code>, and so on, and for
 * manipulating the calendar fields, such as getting the date of the next
 * week. An instant in time can be represented by a millisecond value that is
 * an offset from the <a name="Epoch"><em>Epoch</em></a>, January 1, 1970
 * 00:00:00.000 GMT (Gregorian).
 *
 * <p>The class also provides additional fields and methods for
 * implementing a concrete calendar system outside the package. Those
 * fields and methods are defined as <code>protected</code>.
 *
 * <p>
 * Like other locale-sensitive classes, <code>Calendar</code> provides a
 * class method, <code>getInstance</code>, for getting a generally useful
 * object of this type. <code>Calendar</code>'s <code>getInstance</code> method
 * returns a <code>Calendar</code> object whose
 * calendar fields have been initialized with the current date and time:
 * <blockquote>
 * <pre>
 *     Calendar rightNow = Calendar.getInstance();
 * </pre>
 * </blockquote>
 *
 * <p>A <code>Calendar</code> object can produce all the calendar field values
 * needed to implement the date-time formatting for a particular language and
 * calendar style (for example, Japanese-Gregorian, Japanese-Traditional).
 * <code>Calendar</code> defines the range of values returned by
 * certain calendar fields, as well as their meaning.  For example,
 * the first month of the calendar system has value <code>MONTH ==
 * JANUARY</code> for all calendars.  Other values are defined by the
 * concrete subclass, such as <code>ERA</code>.  See individual field
 * documentation and subclass documentation for details.
 *
 * <h3>Getting and Setting Calendar Field Values</h3>
 *
 * <p>The calendar field values can be set by calling the <code>set</code>
 * methods. Any field values set in a <code>Calendar</code> will not be
 * interpreted until it needs to calculate its time value (milliseconds from
 * the Epoch) or values of the calendar fields. Calling the
 * <code>get</code>, <code>getTimeInMillis</code>, <code>getTime</code>,
 * <code>add</code> and <code>roll</code> involves such calculation.
 *
 * <h4>Leniency</h4>
 *
 * <p><code>Calendar</code> has two modes for interpreting the calendar
 * fields, <em>lenient</em> and <em>non-lenient</em>.  When a
 * <code>Calendar</code> is in lenient mode, it accepts a wider range of
 * calendar field values than it produces.  When a <code>Calendar</code>
 * recomputes calendar field values for return by <code>get()</code>, all of
 * the calendar fields are normalized. For example, a lenient
 * <code>GregorianCalendar</code> interprets <code>MONTH == JANUARY</code>,
 * <code>DAY_OF_MONTH == 32</code> as February 1.

 * <p>When a <code>Calendar</code> is in non-lenient mode, it throws an
 * exception if there is any inconsistency in its calendar fields. For
 * example, a <code>GregorianCalendar</code> always produces
 * <code>DAY_OF_MONTH</code> values between 1 and the length of the month. A
 * non-lenient <code>GregorianCalendar</code> throws an exception upon
 * calculating its time or calendar field values if any out-of-range field
 * value has been set.
 *
 * <h4><a name="first_week">First Week</a></h4>
 *
 * <code>Calendar</code> defines a locale-specific seven day week using two
 * parameters: the first day of the week and the minimal days in first week
 * (from 1 to 7).  These numbers are taken from the locale resource data when a
 * <code>Calendar</code> is constructed.  They may also be specified explicitly
 * through the methods for setting their values.
 *
 * <p>When setting or getting the <code>WEEK_OF_MONTH</code> or
 * <code>WEEK_OF_YEAR</code> fields, <code>Calendar</code> must determine the
 * first week of the month or year as a reference point.  The first week of a
 * month or year is defined as the earliest seven day period beginning on
 * <code>getFirstDayOfWeek()</code> and containing at least
 * <code>getMinimalDaysInFirstWeek()</code> days of that month or year.  Weeks
 * numbered ..., -1, 0 precede the first week; weeks numbered 2, 3,... follow
 * it.  Note that the normalized numbering returned by <code>get()</code> may be
 * different.  For example, a specific <code>Calendar</code> subclass may
 * designate the week before week 1 of a year as week <code><i>n</i></code> of
 * the previous year.
 *
 * <h4>Calendar Fields Resolution</h4>
 *
 * When computing a date and time from the calendar fields, there
 * may be insufficient information for the computation (such as only
 * year and month with no day of month), or there may be inconsistent
 * information (such as Tuesday, July 15, 1996 (Gregorian) -- July 15,
 * 1996 is actually a Monday). <code>Calendar</code> will resolve
 * calendar field values to determine the date and time in the
 * following way.
 *
 * <p><a name="resolution">If there is any conflict in calendar field values,
 * <code>Calendar</code> gives priorities to calendar fields that have been set
 * more recently.</a> The following are the default combinations of the
 * calendar fields. The most recent combination, as determined by the
 * most recently set single field, will be used.
 *
 * <p><a name="date_resolution">For the date fields</a>:
 * <blockquote>
 * <pre>
 * YEAR + MONTH + DAY_OF_MONTH
 * YEAR + MONTH + WEEK_OF_MONTH + DAY_OF_WEEK
 * YEAR + MONTH + DAY_OF_WEEK_IN_MONTH + DAY_OF_WEEK
 * YEAR + DAY_OF_YEAR
 * YEAR + DAY_OF_WEEK + WEEK_OF_YEAR
 * </pre></blockquote>
 *
 * <a name="time_resolution">For the time of day fields</a>:
 * <blockquote>
 * <pre>
 * HOUR_OF_DAY
 * AM_PM + HOUR
 * </pre></blockquote>
 *
 * <p>If there are any calendar fields whose values haven't been set in the selected
 * field combination, <code>Calendar</code> uses their default values. The default
 * value of each field may vary by concrete calendar systems. For example, in
 * <code>GregorianCalendar</code>, the default of a field is the same as that
 * of the start of the Epoch: i.e., <code>YEAR = 1970</code>, <code>MONTH =
 * JANUARY</code>, <code>DAY_OF_MONTH = 1</code>, etc.
 *
 * <p>
 * <strong>Note:</strong> There are certain possible ambiguities in
 * interpretation of certain singular times, which are resolved in the
 * following ways:
 * <ol>
 *     <li> 23:59 is the last minute of the day and 00:00 is the first
 *          minute of the next day. Thus, 23:59 on Dec 31, 1999 &lt; 00:00 on
 *          Jan 1, 2000 &lt; 00:01 on Jan 1, 2000.
 *
 *     <li> Although historically not precise, midnight also belongs to "am",
 *          and noon belongs to "pm", so on the same day,
 *          12:00 am (midnight) &lt; 12:01 am, and 12:00 pm (noon) &lt; 12:01 pm
 * </ol>
 *
 * <p>
 * The date or time format strings are not part of the definition of a
 * calendar, as those must be modifiable or overridable by the user at
 * runtime. Use {@link DateFormat}
 * to format dates.
 *
 * <h4>Field Manipulation</h4>
 *
 * The calendar fields can be changed using three methods:
 * <code>set()</code>, <code>add()</code>, and <code>roll()</code>.
 *
 * <p><strong><code>set(f, value)</code></strong> changes calendar field
 * <code>f</code> to <code>value</code>.  In addition, it sets an
 * internal member variable to indicate that calendar field <code>f</code> has
 * been changed. Although calendar field <code>f</code> is changed immediately,
 * the calendar's time value in milliseconds is not recomputed until the next call to
 * <code>get()</code>, <code>getTime()</code>, <code>getTimeInMillis()</code>,
 * <code>add()</code>, or <code>roll()</code> is made. Thus, multiple calls to
 * <code>set()</code> do not trigger multiple, unnecessary
 * computations. As a result of changing a calendar field using
 * <code>set()</code>, other calendar fields may also change, depending on the
 * calendar field, the calendar field value, and the calendar system. In addition,
 * <code>get(f)</code> will not necessarily return <code>value</code> set by
 * the call to the <code>set</code> method
 * after the calendar fields have been recomputed. The specifics are determined by
 * the concrete calendar class.</p>
 *
 * <p><em>Example</em>: Consider a <code>GregorianCalendar</code>
 * originally set to August 31, 1999. Calling <code>set(Calendar.MONTH,
 * Calendar.SEPTEMBER)</code> sets the date to September 31,
 * 1999. This is a temporary internal representation that resolves to
 * October 1, 1999 if <code>getTime()</code>is then called. However, a
 * call to <code>set(Calendar.DAY_OF_MONTH, 30)</code> before the call to
 * <code>getTime()</code> sets the date to September 30, 1999, since
 * no recomputation occurs after <code>set()</code> itself.</p>
 *
 * <p><strong><code>add(f, delta)</code></strong> adds <code>delta</code>
 * to field <code>f</code>.  This is equivalent to calling <code>set(f,
 * get(f) + delta)</code> with two adjustments:</p>
 *
 * <blockquote>
 *   <p><strong>Add rule 1</strong>. The value of field <code>f</code>
 *   after the call minus the value of field <code>f</code> before the
 *   call is <code>delta</code>, modulo any overflow that has occurred in
 *   field <code>f</code>. Overflow occurs when a field value exceeds its
 *   range and, as a result, the next larger field is incremented or
 *   decremented and the field value is adjusted back into its range.</p>
 *
 *   <p><strong>Add rule 2</strong>. If a smaller field is expected to be
 *   invariant, but it is impossible for it to be equal to its
 *   prior value because of changes in its minimum or maximum after field
 *   <code>f</code> is changed or other constraints, such as time zone
 *   offset changes, then its value is adjusted to be as close
 *   as possible to its expected value. A smaller field represents a
 *   smaller unit of time. <code>HOUR</code> is a smaller field than
 *   <code>DAY_OF_MONTH</code>. No adjustment is made to smaller fields
 *   that are not expected to be invariant. The calendar system
 *   determines what fields are expected to be invariant.</p>
 * </blockquote>
 *
 * <p>In addition, unlike <code>set()</code>, <code>add()</code> forces
 * an immediate recomputation of the calendar's milliseconds and all
 * fields.</p>
 *
 * <p><em>Example</em>: Consider a <code>GregorianCalendar</code>
 * originally set to August 31, 1999. Calling <code>add(Calendar.MONTH,
 * 13)</code> sets the calendar to September 30, 2000. <strong>Add rule
 * 1</strong> sets the <code>MONTH</code> field to September, since
 * adding 13 months to August gives September of the next year. Since
 * <code>DAY_OF_MONTH</code> cannot be 31 in September in a
 * <code>GregorianCalendar</code>, <strong>add rule 2</strong> sets the
 * <code>DAY_OF_MONTH</code> to 30, the closest possible value. Although
 * it is a smaller field, <code>DAY_OF_WEEK</code> is not adjusted by
 * rule 2, since it is expected to change when the month changes in a
 * <code>GregorianCalendar</code>.</p>
 *
 * <p><strong><code>roll(f, delta)</code></strong> adds
 * <code>delta</code> to field <code>f</code> without changing larger
 * fields. This is equivalent to calling <code>add(f, delta)</code> with
 * the following adjustment:</p>
 *
 * <blockquote>
 *   <p><strong>Roll rule</strong>. Larger fields are unchanged after the
 *   call. A larger field represents a larger unit of
 *   time. <code>DAY_OF_MONTH</code> is a larger field than
 *   <code>HOUR</code>.</p>
 * </blockquote>
 *
 * <p><em>Example</em>: See {@link java.util.GregorianCalendar#roll(int, int)}.
 *
 * <p><strong>Usage model</strong>. To motivate the behavior of
 * <code>add()</code> and <code>roll()</code>, consider a user interface
 * component with increment and decrement buttons for the month, day, and
 * year, and an underlying <code>GregorianCalendar</code>. If the
 * interface reads January 31, 1999 and the user presses the month
 * increment button, what should it read? If the underlying
 * implementation uses <code>set()</code>, it might read March 3, 1999. A
 * better result would be February 28, 1999. Furthermore, if the user
 * presses the month increment button again, it should read March 31,
 * 1999, not March 28, 1999. By saving the original date and using either
 * <code>add()</code> or <code>roll()</code>, depending on whether larger
 * fields should be affected, the user interface can behave as most users
 * will intuitively expect.</p>
 *
 * @see          java.lang.System#currentTimeMillis()
 * @see          Date
 * @see          GregorianCalendar
 * @see          TimeZone
 * @see          java.text.DateFormat
 * @author Mark Davis, David Goldsmith, Chen-Lieh Huang, Alan Liu
 * @since JDK1.1
 */
// For desugar: Copy of j.u.Calendar with members introduced since 1.8
public class DesugarCalendar {
    private DesugarCalendar() {}  // for desugar: no instantiation, static methods only

    static final int STANDALONE_MASK = 0x8000;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating a narrow name used for format. Narrow names
     * are typically single character strings, such as "M" for Monday.
     *
     * @see #NARROW_STANDALONE
     * @see #SHORT_FORMAT
     * @see #LONG_FORMAT
     * @since 1.8
     */
    public static final int NARROW_FORMAT = 4;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating a narrow name independently. Narrow names
     * are typically single character strings, such as "M" for Monday.
     *
     * @see #NARROW_FORMAT
     * @see #SHORT_STANDALONE
     * @see #LONG_STANDALONE
     * @since 1.8
     */
    public static final int NARROW_STANDALONE = NARROW_FORMAT | STANDALONE_MASK;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating a short name used for format.
     *
     * @see #SHORT_STANDALONE
     * @see #LONG_FORMAT
     * @see #LONG_STANDALONE
     * @since 1.8
     */
    public static final int SHORT_FORMAT = 1;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating a long name used for format.
     *
     * @see #LONG_STANDALONE
     * @see #SHORT_FORMAT
     * @see #SHORT_STANDALONE
     * @since 1.8
     */
    public static final int LONG_FORMAT = 2;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating a short name used independently,
     * such as a month abbreviation as calendar headers.
     *
     * @see #SHORT_FORMAT
     * @see #LONG_FORMAT
     * @see #LONG_STANDALONE
     * @since 1.8
     */
    public static final int SHORT_STANDALONE = Calendar.SHORT | STANDALONE_MASK;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating a long name used independently,
     * such as a month name as calendar headers.
     *
     * @see #LONG_FORMAT
     * @see #SHORT_FORMAT
     * @see #SHORT_STANDALONE
     * @since 1.8
     */
    public static final int LONG_STANDALONE = Calendar.LONG | STANDALONE_MASK;

    /**
     * Converts this object to an {@link Instant}.
     * <p>
     * The conversion creates an {@code Instant} that represents the
     * same point on the time-line as this {@code Calendar}.
     *
     * @return the instant representing the same point on the time-line
     * @since 1.8
     */
    // For desugar: made static so it can exist outside original class
    public static Instant toInstant(Calendar instance) {
        return Instant.ofEpochMilli(instance.getTimeInMillis());
    }
}
