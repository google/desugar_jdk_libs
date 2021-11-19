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
 */

package wrapper.java.nio.file.attribute;

import j$.nio.file.attribute.AclEntryFlag;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Type conversions between {@link java.nio.file.attribute.AclEntryFlag} and {@link
 * j$.nio.file.attribute.AclEntryFlag}.
 */
public final class AclEntryFlagConversions {

  public static j$.nio.file.attribute.AclEntryFlag encode(
      java.nio.file.attribute.AclEntryFlag raw) {
    if (raw == null) {
      return null;
    }
    switch (raw) {
      case FILE_INHERIT:
        return j$.nio.file.attribute.AclEntryFlag.FILE_INHERIT;
      case DIRECTORY_INHERIT:
        return j$.nio.file.attribute.AclEntryFlag.DIRECTORY_INHERIT;
      case NO_PROPAGATE_INHERIT:
        return j$.nio.file.attribute.AclEntryFlag.NO_PROPAGATE_INHERIT;
      case INHERIT_ONLY:
        return j$.nio.file.attribute.AclEntryFlag.INHERIT_ONLY;
    }
    throw new AssertionError("Unexpected AclEntryFlag: " + raw);
  }

  public static Set<j$.nio.file.attribute.AclEntryFlag> encode(
      Set<java.nio.file.attribute.AclEntryFlag> raw) {
    if (raw == null) {
      return null;
    }
    var results = new LinkedHashSet<AclEntryFlag>();
    for (var value : raw) {
      results.add(encode(value));
    }
    return results;
  }

  public static java.nio.file.attribute.AclEntryFlag decode(
      j$.nio.file.attribute.AclEntryFlag encoded) {
    if (encoded == null) {
      return null;
    }
    switch (encoded) {
      case FILE_INHERIT:
        return java.nio.file.attribute.AclEntryFlag.FILE_INHERIT;
      case DIRECTORY_INHERIT:
        return java.nio.file.attribute.AclEntryFlag.DIRECTORY_INHERIT;
      case NO_PROPAGATE_INHERIT:
        return java.nio.file.attribute.AclEntryFlag.NO_PROPAGATE_INHERIT;
      case INHERIT_ONLY:
        return java.nio.file.attribute.AclEntryFlag.INHERIT_ONLY;
    }
    throw new AssertionError("Unexpected AclEntryFlag: " + encoded);
  }

  public static Set<java.nio.file.attribute.AclEntryFlag> decode(
      Set<j$.nio.file.attribute.AclEntryFlag> encoded) {
    if (encoded == null) {
      return null;
    }
    var results = new LinkedHashSet<java.nio.file.attribute.AclEntryFlag>();
    for (var value : encoded) {
      results.add(decode(value));
    }
    return results;
  }

  private AclEntryFlagConversions() {}
}
