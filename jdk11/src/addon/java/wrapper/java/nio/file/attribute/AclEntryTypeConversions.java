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

/**
 * Type conversions between {@link java.nio.file.attribute.AclEntryType} and {@link
 * j$.nio.file.attribute.AclEntryType}.
 */
public class AclEntryTypeConversions {

  public static j$.nio.file.attribute.AclEntryType encode(
      java.nio.file.attribute.AclEntryType raw) {
    if (raw == null) {
      return null;
    }
    switch (raw) {
      case ALLOW:
        return j$.nio.file.attribute.AclEntryType.ALLOW;
      case DENY:
        return j$.nio.file.attribute.AclEntryType.DENY;
      case AUDIT:
        return j$.nio.file.attribute.AclEntryType.AUDIT;
      case ALARM:
        return j$.nio.file.attribute.AclEntryType.ALARM;
    }
    throw new AssertionError("Unexpected AclEntryType: " + raw);
  }

  public static java.nio.file.attribute.AclEntryType decode(
      j$.nio.file.attribute.AclEntryType encoded) {
    if (encoded == null) {
      return null;
    }
    switch (encoded) {
      case ALLOW:
        return java.nio.file.attribute.AclEntryType.ALLOW;
      case DENY:
        return java.nio.file.attribute.AclEntryType.DENY;
      case AUDIT:
        return java.nio.file.attribute.AclEntryType.AUDIT;
      case ALARM:
        return java.nio.file.attribute.AclEntryType.ALARM;
    }
    throw new AssertionError("Unexpected AclEntryType: " + encoded);
  }

  private AclEntryTypeConversions() {}
}
