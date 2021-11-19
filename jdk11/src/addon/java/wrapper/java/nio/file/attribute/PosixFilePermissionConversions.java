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

package wrapper.java.nio.file.attribute;

import j$.nio.file.attribute.PosixFilePermission;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Type conversions between {@link java.nio.file.attribute.PosixFilePermission} and {@link
 * j$.nio.file.attribute.PosixFilePermission}.
 */
public class PosixFilePermissionConversions {

  public static j$.nio.file.attribute.PosixFilePermission encode(
      java.nio.file.attribute.PosixFilePermission raw) {
    if (raw == null) {
      return null;
    }
    switch (raw) {
      case OWNER_READ:
        return PosixFilePermission.OWNER_READ;
      case OWNER_WRITE:
        return PosixFilePermission.OWNER_WRITE;
      case OWNER_EXECUTE:
        return PosixFilePermission.OWNER_EXECUTE;
      case GROUP_READ:
        return PosixFilePermission.GROUP_READ;
      case GROUP_WRITE:
        return PosixFilePermission.GROUP_WRITE;
      case GROUP_EXECUTE:
        return PosixFilePermission.GROUP_EXECUTE;
      case OTHERS_READ:
        return PosixFilePermission.OTHERS_READ;
      case OTHERS_WRITE:
        return PosixFilePermission.OTHERS_WRITE;
      case OTHERS_EXECUTE:
        return PosixFilePermission.OTHERS_EXECUTE;
    }
    throw new AssertionError("Unexpected PosixFilePermission case: " + raw);
  }

  public static Set<j$.nio.file.attribute.PosixFilePermission> encode(
      Set<java.nio.file.attribute.PosixFilePermission> raw) {
    if (raw == null) {
      return null;
    }
    var results = new LinkedHashSet<j$.nio.file.attribute.PosixFilePermission>();
    for (var permission : raw) {
      results.add(encode(permission));
    }
    return results;
  }

  public static java.nio.file.attribute.PosixFilePermission decode(
      j$.nio.file.attribute.PosixFilePermission encoded) {
    if (encoded == null) {
      return null;
    }
    switch (encoded) {
      case OWNER_READ:
        return java.nio.file.attribute.PosixFilePermission.OWNER_READ;
      case OWNER_WRITE:
        return java.nio.file.attribute.PosixFilePermission.OWNER_WRITE;
      case OWNER_EXECUTE:
        return java.nio.file.attribute.PosixFilePermission.OWNER_EXECUTE;
      case GROUP_READ:
        return java.nio.file.attribute.PosixFilePermission.GROUP_READ;
      case GROUP_WRITE:
        return java.nio.file.attribute.PosixFilePermission.GROUP_WRITE;
      case GROUP_EXECUTE:
        return java.nio.file.attribute.PosixFilePermission.GROUP_EXECUTE;
      case OTHERS_READ:
        return java.nio.file.attribute.PosixFilePermission.OTHERS_READ;
      case OTHERS_WRITE:
        return java.nio.file.attribute.PosixFilePermission.OTHERS_WRITE;
      case OTHERS_EXECUTE:
        return java.nio.file.attribute.PosixFilePermission.OTHERS_EXECUTE;
    }
    throw new AssertionError("Unexpected PosixFilePermission case: " + encoded);
  }

  public static Set<java.nio.file.attribute.PosixFilePermission> decode(
      Set<j$.nio.file.attribute.PosixFilePermission> encoded) {
    if (encoded == null) {
      return null;
    }
    var results = new LinkedHashSet<java.nio.file.attribute.PosixFilePermission>();
    for (var permission : encoded) {
      results.add(decode(permission));
    }
    return results;
  }

  private PosixFilePermissionConversions() {}
}
