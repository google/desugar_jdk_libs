/*
 * Copyright (c) 2012, 2020, Oracle and/or its affiliates. All rights reserved.
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
 * Copyright (c) 2008-2012, Stephen Colebourne & Michael Nascimento Santos
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
package java.time.format;


import android.icu.text.TimeZoneNames;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatterBuilder.DateTimePrinterParser;
import java.time.format.DateTimeFormatterBuilder.PrefixTree;
import java.time.format.DateTimeFormatterBuilder.ZoneIdPrinterParser;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.zone.ZoneRulesProvider;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Builder to create date-time formatters.
 * <p>
 * This allows a {@code DateTimeFormatter} to be created.
 * All date-time formatters are created ultimately using this builder.
 * <p>
 * The basic elements of date-time can all be added:
 * <ul>
 * <li>Value - a numeric value</li>
 * <li>Fraction - a fractional value including the decimal place. Always use this when
 * outputting fractions to ensure that the fraction is parsed correctly</li>
 * <li>Text - the textual equivalent for the value</li>
 * <li>OffsetId/Offset - the {@linkplain ZoneOffset zone offset}</li>
 * <li>ZoneId - the {@linkplain ZoneId time-zone} id</li>
 * <li>ZoneText - the name of the time-zone</li>
 * <li>ChronologyId - the {@linkplain Chronology chronology} id</li>
 * <li>ChronologyText - the name of the chronology</li>
 * <li>Literal - a text literal</li>
 * <li>Nested and Optional - formats can be nested or made optional</li>
 * </ul>
 * In addition, any of the elements may be decorated by padding, either with spaces or any other character.
 * <p>
 * Finally, a shorthand pattern, mostly compatible with {@code java.text.SimpleDateFormat SimpleDateFormat}
 * can be used, see {@link #appendPattern(String)}.
 * In practice, this simply parses the pattern and calls other methods on the builder.
 *
 * @implSpec
 * This class is a mutable builder intended for use from a single thread.
 *
 * @since 1.8
 */
public final class DesugarDateTimeFormatterBuilder {

  private DesugarDateTimeFormatterBuilder() {}

  /**
     * Appends the generic time-zone name, such as 'Pacific Time', to the formatter.
     * <p>
     * This appends an instruction to format/parse the generic textual
     * name of the zone to the builder. The generic name is the same throughout the whole
     * year, ignoring any daylight saving changes. For example, 'Pacific Time' is the
     * generic name, whereas 'Pacific Standard Time' and 'Pacific Daylight Time' are the
     * specific names, see {@link #appendZoneText(TextStyle)}.
     * <p>
     * During formatting, the zone is obtained using a mechanism equivalent
     * to querying the temporal with {@link TemporalQueries#zoneId()}.
     * If the zone is a {@code ZoneOffset} it will be printed using the
     * result of {@link ZoneOffset#getId()}.
     * If the zone is not an offset, the textual name will be looked up
     * for the locale set in the {@link DateTimeFormatter}.
     * If the lookup for text does not find any suitable result, then the
     * {@link ZoneId#getId() ID} will be printed.
     * If the zone cannot be obtained then an exception is thrown unless the
     * section of the formatter is optional.
     * <p>
     * During parsing, either the textual zone name, the zone ID or the offset
     * is accepted. Many textual zone names are not unique, such as CST can be
     * for both "Central Standard Time" and "China Standard Time". In this
     * situation, the zone id will be determined by the region information from
     * formatter's  {@link DateTimeFormatter#getLocale() locale} and the standard
     * zone id for that area, for example, America/New_York for the America Eastern zone.
     * The {@link #appendGenericZoneText(TextStyle, Set)} may be used
     * to specify a set of preferred {@link ZoneId} in this situation.
     *
     * @param textStyle  the text style to use, not null
     * @return this, for chaining, not null
     * @since 9
     */
    public static DateTimeFormatterBuilder appendGenericZoneText(
        DateTimeFormatterBuilder receiver, TextStyle textStyle) {
        appendInternal(
            receiver, new ZoneTextPrinterParser(textStyle, null));
        return receiver;
    }

    /**
     * Appends the generic time-zone name, such as 'Pacific Time', to the formatter.
     * <p>
     * This appends an instruction to format/parse the generic textual
     * name of the zone to the builder. The generic name is the same throughout the whole
     * year, ignoring any daylight saving changes. For example, 'Pacific Time' is the
     * generic name, whereas 'Pacific Standard Time' and 'Pacific Daylight Time' are the
     * specific names, see {@link #appendZoneText(TextStyle)}.
     * <p>
     * This method also allows a set of preferred {@link ZoneId} to be
     * specified for parsing. The matched preferred zone id will be used if the
     * textural zone name being parsed is not unique.
     * <p>
     * See {@link #appendGenericZoneText(TextStyle)} for details about
     * formatting and parsing.
     *
     * @param textStyle  the text style to use, not null
     * @param preferredZones  the set of preferred zone ids, not null
     * @return this, for chaining, not null
     * @since 9
     */
    public static DateTimeFormatterBuilder appendGenericZoneText(
        DateTimeFormatterBuilder receiver, TextStyle textStyle, Set<ZoneId> preferredZones) {
      appendInternal(receiver, new ZoneTextPrinterParser(textStyle, preferredZones));
      return receiver;
    }

    private static void appendInternal(
        DateTimeFormatterBuilder receiver, DateTimePrinterParser pp) {
      try {
        // For desugar: workaround private method invocation from a companion class.
        // receiver.appendInternal(zoneTextPrinterParser);
        Method appendInternal = DateTimeFormatterBuilder.class.getDeclaredMethod(
            "appendInternal",
            DateTimePrinterParser.class);
        appendInternal.setAccessible(true);
        appendInternal.invoke(receiver, pp);
        // For desugar: Avoid using ReflectiveOperationException due to unavailability in
        // lower SDK levels.
      } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
        throw new AssertionError("Unhandled exception", e);
      }
    }


  /**
   * Prints or parses a zone ID.
   */
  // For Desugar: Use libcore's implementation.
  static final class ZoneTextPrinterParser extends ZoneIdPrinterParser {

    /** The text style to output. */
    private final TextStyle textStyle;

    /** The preferred zoneid map */
    private Set<String> preferredZones;

    ZoneTextPrinterParser(TextStyle textStyle, Set<ZoneId> preferredZones) {
      super(TemporalQueries.zone(), "ZoneText(" + textStyle + ")");
      this.textStyle = Objects.requireNonNull(textStyle, "textStyle");
      if (preferredZones != null && preferredZones.size() != 0) {
        this.preferredZones = new HashSet<>();
        for (ZoneId id : preferredZones) {
          this.preferredZones.add(id.getId());
        }
      }
    }

    private static final int STD = 0;
    private static final int DST = 1;
    private static final int GENERIC = 2;

    // BEGIN Android-added: Lists of types used by getDisplayName().
    private static final TimeZoneNames.NameType[] TYPES = new TimeZoneNames.NameType[] {
        TimeZoneNames.NameType.LONG_STANDARD,
        TimeZoneNames.NameType.SHORT_STANDARD,
        TimeZoneNames.NameType.LONG_DAYLIGHT,
        TimeZoneNames.NameType.SHORT_DAYLIGHT,
        TimeZoneNames.NameType.LONG_GENERIC,
        TimeZoneNames.NameType.SHORT_GENERIC,
    };

    private static final TimeZoneNames.NameType[] FULL_TYPES = new TimeZoneNames.NameType[] {
        TimeZoneNames.NameType.LONG_STANDARD,
        TimeZoneNames.NameType.LONG_DAYLIGHT,
        TimeZoneNames.NameType.LONG_GENERIC,
    };

    private static final TimeZoneNames.NameType[] SHORT_TYPES = new TimeZoneNames.NameType[] {
        TimeZoneNames.NameType.SHORT_STANDARD,
        TimeZoneNames.NameType.SHORT_DAYLIGHT,
        TimeZoneNames.NameType.SHORT_GENERIC,
    };
    // END Android-added: Lists of types used by getDisplayName().

    private static final Map<String, SoftReference<Map<Locale, String[]>>> cache =
        new ConcurrentHashMap<>();

    private String getDisplayName(String id, int type, Locale locale) {
      if (textStyle == TextStyle.NARROW) {
        return null;
      }
      String[] names;
      SoftReference<Map<Locale, String[]>> ref = cache.get(id);
      Map<Locale, String[]> perLocale = null;
      if (ref == null || (perLocale = ref.get()) == null ||
          (names = perLocale.get(locale)) == null) {
        // BEGIN Android-changed: use ICU TimeZoneNames instead of TimeZoneNameUtility.
                /*
                names = TimeZoneNameUtility.retrieveDisplayNames(id, locale);
                if (names == null) {
                    return null;
                }
                names = Arrays.copyOfRange(names, 0, 7);
                names[5] =
                    TimeZoneNameUtility.retrieveGenericDisplayName(id, TimeZone.LONG, locale);
                if (names[5] == null) {
                    names[5] = names[0]; // use the id
                }
                names[6] =
                    TimeZoneNameUtility.retrieveGenericDisplayName(id, TimeZone.SHORT, locale);
                */
        TimeZoneNames timeZoneNames = TimeZoneNames.getInstance(locale);
        names = new String[TYPES.length + 1];
        // Zeroth index used for id, other indexes based on NameType constant + 1.
        names[0] = id;
        String canonicalId = ZoneName.getSystemCanonicalID(id);
        libcore.icu.TimeZoneNames.getDisplayNames(timeZoneNames, canonicalId, TYPES,
            System.currentTimeMillis(), /* dest */ names, /* destoffset */ 1);
        if (names == null) {
          return null;
        }
        if (names[1] == null || names[2] == null || names[3] == null || names[4] == null) {
          // Use "GMT+XX:XX" analogous to java.util.TimeZone.getDisplayName()
          TimeZone tz = TimeZone.getTimeZone(id);
          String stdString = TimeZone.createGmtOffsetString(
              /* includeGmt */ true, /* includeMinuteSeparator */ true,
              tz.getRawOffset());
          String dstString = TimeZone.createGmtOffsetString(
              /* includeGmt */ true, /* includeMinuteSeparator */ true,
              tz.getRawOffset() + tz.getDSTSavings());
          names[1] = names[1] != null ? names[1] : stdString;
          names[2] = names[2] != null ? names[2] : stdString;
          names[3] = names[3] != null ? names[3] : dstString;
          names[4] = names[4] != null ? names[4] : dstString;
        }
        if (names[5] == null) {
          names[5] = names[0]; // use the id
        }
        // END Android-changed: use ICU TimeZoneNames instead of TimeZoneNameUtility.
        if (names[6] == null) {
          names[6] = names[0];
        }
        if (perLocale == null) {
          perLocale = new ConcurrentHashMap<>();
        }
        perLocale.put(locale, names);
        cache.put(id, new SoftReference<>(perLocale));
      }
      switch (type) {
        case STD:
          return names[textStyle.zoneNameStyleIndex() + 1];
        case DST:
          return names[textStyle.zoneNameStyleIndex() + 3];
      }
      return names[textStyle.zoneNameStyleIndex() + 5];
    }

    @Override
    public boolean format(DateTimePrintContext context, StringBuilder buf) {
      ZoneId zone = context.getValue(TemporalQueries.zoneId());
      if (zone == null) {
        return false;
      }
      String zname = zone.getId();
      if (!(zone instanceof ZoneOffset)) {
        TemporalAccessor dt = context.getTemporal();
        String name = getDisplayName(zname,
            dt.isSupported(ChronoField.INSTANT_SECONDS)
                ? (zone.getRules().isDaylightSavings(Instant.from(dt)) ? DST : STD)
                : GENERIC,
            context.getLocale());
        if (name != null) {
          zname = name;
        }
      }
      buf.append(zname);
      return true;
    }

    // cache per instance for now
    private final Map<Locale, Entry<Integer, SoftReference<PrefixTree>>>
        cachedTree = new HashMap<>();
    private final Map<Locale, Entry<Integer, SoftReference<PrefixTree>>>
        cachedTreeCI = new HashMap<>();

    @Override
    protected PrefixTree getTree(DateTimeParseContext context) {
      if (textStyle == TextStyle.NARROW) {
        return super.getTree(context);
      }
      Locale locale = context.getLocale();
      boolean isCaseSensitive = context.isCaseSensitive();
      Set<String> regionIds = ZoneRulesProvider.getAvailableZoneIds();
      int regionIdsSize = regionIds.size();

      Map<Locale, Entry<Integer, SoftReference<PrefixTree>>> cached =
          isCaseSensitive ? cachedTree : cachedTreeCI;

      Entry<Integer, SoftReference<PrefixTree>> entry = null;
      PrefixTree tree = null;
      String[][] zoneStrings = null;
      if ((entry = cached.get(locale)) == null ||
          (entry.getKey() != regionIdsSize ||
              (tree = entry.getValue().get()) == null)) {
        tree = PrefixTree.newTree(context);
        // BEGIN Android-changed: use ICU TimeZoneNames to get Zone names.
                /*
                zoneStrings = TimeZoneNameUtility.getZoneStrings(locale);
                for (String[] names : zoneStrings) {
                    String zid = names[0];
                    if (!regionIds.contains(zid)) {
                        continue;
                    }
                    tree.add(zid, zid);    // don't convert zid -> metazone
                    zid = ZoneName.toZid(zid, locale);
                    int i = textStyle == TextStyle.FULL ? 1 : 2;
                    for (; i < names.length; i += 2) {
                        tree.add(names[i], zid);
                    }
                }
                // if we have a set of preferred zones, need a copy and
                // add the preferred zones again to overwrite
                if (preferredZones != null) {
                    for (String[] names : zoneStrings) {
                        String zid = names[0];
                        if (!preferredZones.contains(zid) || !regionIds.contains(zid)) {
                            continue;
                        }
                        int i = textStyle == TextStyle.FULL ? 1 : 2;
                        for (; i < names.length; i += 2) {
                            tree.add(names[i], zid);
                       }
                    }
                }
                */
        TimeZoneNames timeZoneNames = TimeZoneNames.getInstance(locale);
        long now = System.currentTimeMillis();
        TimeZoneNames.NameType[] types =
            textStyle == TextStyle.FULL ? FULL_TYPES : SHORT_TYPES;
        String[] names = new String[types.length];
        for (String zid : regionIds) {
          tree.add(zid, zid);    // don't convert zid -> metazone
          zid = ZoneName.toZid(zid, locale);
          libcore.icu.TimeZoneNames.getDisplayNames(timeZoneNames, zid, types, now,
              names, 0);
          for (int i = 0; i < names.length; i++) {
            if (names[i] != null) {
              tree.add(names[i], zid);
            }
          }
        }
        // if we have a set of preferred zones, need a copy and
        // add the preferred zones again to overwrite
        if (preferredZones != null) {
          for (String zid : regionIds) {
            if (!preferredZones.contains(zid)) {
              continue;
            }
            String canonicalId = ZoneName.toZid(zid, locale);
            libcore.icu.TimeZoneNames.getDisplayNames(timeZoneNames, canonicalId, types,
                now, names, 0);
            for (int i = 0; i < names.length; i++) {
              if (names[i] != null) {
                tree.add(names[i], zid);
              }
            }
          }
        }
        // END Android-changed: use ICU TimeZoneNames to get Zone names.
        cached.put(locale, new SimpleImmutableEntry<>(regionIdsSize, new SoftReference<>(tree)));
      }
      return tree;
    }
  }
}
