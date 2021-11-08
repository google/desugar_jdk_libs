/*
 * Copyright (c) 2021 Google LLC
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Google designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Google in the LICENSE file that accompanied this code.
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
 */

package wrapper.java.nio.file;

import java.util.LinkedHashSet;
import java.util.Set;

/** Type conversions between {@link java.nio.file.OpenOption} and {@link j$.nio.file.OpenOption}. */
public final class OpenOptionConversions {

  public static j$.nio.file.OpenOption encode(java.nio.file.OpenOption openOption) {
    if (openOption == null) {
      return null;
    }
    if (openOption instanceof java.nio.file.StandardOpenOption) {
      return StandardOpenOptionConversions.encode((java.nio.file.StandardOpenOption) openOption);
    }
    if (openOption instanceof java.nio.file.LinkOption) {
      return LinkOptionConversions.encode((java.nio.file.LinkOption) openOption);
    }
    throw new UnsupportedOperationException("Unexpected OpenOption: " + openOption);
  }

  public static j$.nio.file.OpenOption[] encode(java.nio.file.OpenOption[] values) {
    if (values == null) {
      return null;
    }
    int n = values.length;
    var results = new j$.nio.file.OpenOption[n];
    for (int i = 0; i < n; i++) {
      results[i] = encode(values[i]);
    }
    return results;
  }

  public static Set<j$.nio.file.OpenOption> encode(Set<? extends java.nio.file.OpenOption> values) {
    if (values == null) {
      return null;
    }
    var results = new LinkedHashSet<j$.nio.file.OpenOption>();
    for (var v : values) {
      results.add(encode(v));
    }
    return results;
  }

  public static java.nio.file.OpenOption decode(j$.nio.file.OpenOption openOption) {
    if (openOption instanceof j$.nio.file.StandardOpenOption) {
      return StandardOpenOptionConversions.decode((j$.nio.file.StandardOpenOption) openOption);
    }
    if (openOption instanceof j$.nio.file.LinkOption) {
      return LinkOptionConversions.decode((j$.nio.file.LinkOption) openOption);
    }
    throw new UnsupportedOperationException("Unexpected OpenOption: " + openOption);
  }

  public static java.nio.file.OpenOption[] decode(j$.nio.file.OpenOption[] values) {
    int n = values.length;
    var results = new java.nio.file.OpenOption[n];
    for (int i = 0; i < n; i++) {
      results[i] = decode(values[i]);
    }
    return results;
  }

  public static Set<java.nio.file.OpenOption> decode(Set<? extends j$.nio.file.OpenOption> values) {
    var results = new LinkedHashSet<java.nio.file.OpenOption>();
    for (var v : values) {
      results.add(decode(v));
    }
    return results;
  }

  public OpenOptionConversions() {}
}
