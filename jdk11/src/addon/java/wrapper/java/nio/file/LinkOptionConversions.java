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

/** Type conversions between {@link java.nio.file.LinkOption} and {@link j$.nio.file.LinkOption}. */
public final class LinkOptionConversions {

  public static j$.nio.file.LinkOption encode(java.nio.file.LinkOption linkOption) {
    if (linkOption == null) {
      return null;
    }
    switch (linkOption) {
      case NOFOLLOW_LINKS:
        return j$.nio.file.LinkOption.NOFOLLOW_LINKS;
    }
    throw new AssertionError("Unexpected LinkOption: " + linkOption);
  }

  public static j$.nio.file.LinkOption[] encode(java.nio.file.LinkOption[] values) {
    if (values == null) {
      return null;
    }
    int n = values.length;
    var results = new j$.nio.file.LinkOption[n];
    for (int i = 0; i < n; i++) {
      results[i] = encode(values[i]);
    }
    return results;
  }

  public static java.nio.file.LinkOption decode(j$.nio.file.LinkOption linkOption) {
    switch (linkOption) {
      case NOFOLLOW_LINKS:
        return java.nio.file.LinkOption.NOFOLLOW_LINKS;
    }
    throw new AssertionError("Unexpected LinkOption: " + linkOption);
  }

  public static java.nio.file.LinkOption[] decode(j$.nio.file.LinkOption[] values) {
    int n = values.length;
    var results = new java.nio.file.LinkOption[n];
    for (int i = 0; i < n; i++) {
      results[i] = decode(values[i]);
    }
    return results;
  }

  private LinkOptionConversions() {}
}
