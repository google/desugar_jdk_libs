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

/** Type conversions between {@link java.nio.file.CopyOption} and {@link j$.nio.file.CopyOption}. */
public final class CopyOptionConversions {

  public static j$.nio.file.CopyOption encode(java.nio.file.CopyOption raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof java.nio.file.StandardCopyOption) {
      return StandardCopyOptionConversions.encode((java.nio.file.StandardCopyOption) raw);
    }
    if (raw instanceof java.nio.file.LinkOption) {
      return LinkOptionConversions.encode((java.nio.file.LinkOption) raw);
    }
    throw new UnsupportedOperationException("Unexpected OpenOption: " + raw);
  }

  public static j$.nio.file.CopyOption[] encode(java.nio.file.CopyOption[] raw) {
    if (raw == null) {
      return null;
    }
    int n = raw.length;
    var results = new j$.nio.file.CopyOption[n];
    for (int i = 0; i < n; i++) {
      results[i] = encode(raw[i]);
    }
    return results;
  }

  public static java.nio.file.CopyOption decode(j$.nio.file.CopyOption encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof j$.nio.file.StandardCopyOption) {
      return StandardCopyOptionConversions.decode((j$.nio.file.StandardCopyOption) encoded);
    }
    if (encoded instanceof j$.nio.file.LinkOption) {
      return LinkOptionConversions.decode((j$.nio.file.LinkOption) encoded);
    }
    throw new UnsupportedOperationException("Unexpected OpenOption: " + encoded);
  }

  public static java.nio.file.CopyOption[] decode(j$.nio.file.CopyOption[] encoded) {
    if (encoded == null) {
      return null;
    }
    int n = encoded.length;
    var results = new java.nio.file.CopyOption[n];
    for (int i = 0; i < n; i++) {
      results[i] = decode(encoded[i]);
    }
    return results;
  }

  private CopyOptionConversions() {}
}
