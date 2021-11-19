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

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Type conversions between {@link java.nio.file.attribute.AclEntryPermission} and {@link
 * j$.nio.file.attribute.AclEntryPermission}.
 */
public final class AclEntryPermissionConversions {

  public static j$.nio.file.attribute.AclEntryPermission encode(
      java.nio.file.attribute.AclEntryPermission raw) {
    if (raw == null) {
      return null;
    }
    switch (raw) {
      case READ_DATA:
        return j$.nio.file.attribute.AclEntryPermission.READ_DATA;
      case WRITE_DATA:
        return j$.nio.file.attribute.AclEntryPermission.WRITE_DATA;
      case APPEND_DATA:
        return j$.nio.file.attribute.AclEntryPermission.APPEND_DATA;
      case READ_NAMED_ATTRS:
        return j$.nio.file.attribute.AclEntryPermission.READ_NAMED_ATTRS;
      case WRITE_NAMED_ATTRS:
        return j$.nio.file.attribute.AclEntryPermission.WRITE_NAMED_ATTRS;
      case EXECUTE:
        return j$.nio.file.attribute.AclEntryPermission.EXECUTE;
      case DELETE_CHILD:
        return j$.nio.file.attribute.AclEntryPermission.DELETE_CHILD;
      case READ_ATTRIBUTES:
        return j$.nio.file.attribute.AclEntryPermission.READ_ATTRIBUTES;
      case WRITE_ATTRIBUTES:
        return j$.nio.file.attribute.AclEntryPermission.WRITE_ATTRIBUTES;
      case DELETE:
        return j$.nio.file.attribute.AclEntryPermission.DELETE;
      case READ_ACL:
        return j$.nio.file.attribute.AclEntryPermission.READ_ACL;
      case WRITE_ACL:
        return j$.nio.file.attribute.AclEntryPermission.WRITE_ACL;
      case WRITE_OWNER:
        return j$.nio.file.attribute.AclEntryPermission.WRITE_OWNER;
      case SYNCHRONIZE:
        return j$.nio.file.attribute.AclEntryPermission.SYNCHRONIZE;
    }
    throw new AssertionError("Unexpected AclEntryPermission: " + raw);
  }

  public static Set<j$.nio.file.attribute.AclEntryPermission> encode(
      Set<java.nio.file.attribute.AclEntryPermission> raw) {
    if (raw == null) {
      return null;
    }
    var permissions = new LinkedHashSet<j$.nio.file.attribute.AclEntryPermission>();
    for (var permission : raw) {
      permissions.add(encode(permission));
    }
    return permissions;
  }

  public static java.nio.file.attribute.AclEntryPermission decode(
      j$.nio.file.attribute.AclEntryPermission encoded) {
    if (encoded == null) {
      return null;
    }
    switch (encoded) {
      case READ_DATA:
        return java.nio.file.attribute.AclEntryPermission.READ_DATA;
      case WRITE_DATA:
        return java.nio.file.attribute.AclEntryPermission.WRITE_DATA;
      case APPEND_DATA:
        return java.nio.file.attribute.AclEntryPermission.APPEND_DATA;
      case READ_NAMED_ATTRS:
        return java.nio.file.attribute.AclEntryPermission.READ_NAMED_ATTRS;
      case WRITE_NAMED_ATTRS:
        return java.nio.file.attribute.AclEntryPermission.WRITE_NAMED_ATTRS;
      case EXECUTE:
        return java.nio.file.attribute.AclEntryPermission.EXECUTE;
      case DELETE_CHILD:
        return java.nio.file.attribute.AclEntryPermission.DELETE_CHILD;
      case READ_ATTRIBUTES:
        return java.nio.file.attribute.AclEntryPermission.READ_ATTRIBUTES;
      case WRITE_ATTRIBUTES:
        return java.nio.file.attribute.AclEntryPermission.WRITE_ATTRIBUTES;
      case DELETE:
        return java.nio.file.attribute.AclEntryPermission.DELETE;
      case READ_ACL:
        return java.nio.file.attribute.AclEntryPermission.READ_ACL;
      case WRITE_ACL:
        return java.nio.file.attribute.AclEntryPermission.WRITE_ACL;
      case WRITE_OWNER:
        return java.nio.file.attribute.AclEntryPermission.WRITE_OWNER;
      case SYNCHRONIZE:
        return java.nio.file.attribute.AclEntryPermission.SYNCHRONIZE;
    }
    throw new AssertionError("Unexpected AclEntryPermission: " + encoded);
  }

  public static Set<java.nio.file.attribute.AclEntryPermission> decode(
      Set<j$.nio.file.attribute.AclEntryPermission> encoded) {
    if (encoded == null) {
      return null;
    }
    var permissions = new LinkedHashSet<java.nio.file.attribute.AclEntryPermission>();
    for (var permission : encoded) {
      permissions.add(decode(permission));
    }
    return permissions;
  }

  private AclEntryPermissionConversions() {}
}
