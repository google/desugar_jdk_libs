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

/** Type conversions between {@link java.nio.file.AccessMode} and {@link j$.nio.file.AccessMode}. */
public final class AccessModeConversions {

  public static j$.nio.file.AccessMode encode(java.nio.file.AccessMode raw) {
    if (raw == null) {
      return null;
    }
    switch (raw) {
      case READ:
        return j$.nio.file.AccessMode.READ;
      case WRITE:
        return j$.nio.file.AccessMode.WRITE;
      case EXECUTE:
        return j$.nio.file.AccessMode.EXECUTE;
    }
    throw new AssertionError("Unexpected AccessMode: " + raw);
  }

  public static j$.nio.file.AccessMode[] encode(java.nio.file.AccessMode[] raw) {
    if (raw == null) {
      return null;
    }
    int n = raw.length;
    var result = new j$.nio.file.AccessMode[n];
    for (int i = 0; i < n; i++) {
      result[i] = encode(raw[i]);
    }
    return result;
  }

  public static java.nio.file.AccessMode decode(j$.nio.file.AccessMode encoded) {
    if (encoded == null) {
      return null;
    }
    switch (encoded) {
      case READ:
        return java.nio.file.AccessMode.READ;
      case WRITE:
        return java.nio.file.AccessMode.WRITE;
      case EXECUTE:
        return java.nio.file.AccessMode.EXECUTE;
    }
    throw new AssertionError("Unexpected AccessMode: " + encoded);
  }

  public static java.nio.file.AccessMode[] decode(j$.nio.file.AccessMode[] encoded) {
    if (encoded == null) {
      return null;
    }
    int n = encoded.length;
    var result = new java.nio.file.AccessMode[n];
    for (int i = 0; i < n; i++) {
      result[i] = decode(encoded[i]);
    }
    return result;
  }

  private AccessModeConversions() {}
}
