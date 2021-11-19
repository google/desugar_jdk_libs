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

import java.util.ArrayList;
import java.util.List;

/**
 * Type conversions between {@link java.nio.file.attribute.AclEntry} and {@link
 * j$.nio.file.attribute.AclEntry}.
 */
public final class AclEntryConversions {

  public static j$.nio.file.attribute.AclEntry encode(java.nio.file.attribute.AclEntry raw) {
    if (raw == null) {
      return null;
    }
    return j$.nio.file.attribute.AclEntry.newBuilder()
        .setFlags(AclEntryFlagConversions.encode(raw.flags()))
        .setPermissions(AclEntryPermissionConversions.encode(raw.permissions()))
        .setPrincipal(UserPrincipalConversions.encode(raw.principal()))
        .setType(AclEntryTypeConversions.encode(raw.type()))
        .build();
  }

  public static List<j$.nio.file.attribute.AclEntry> encode(
      List<java.nio.file.attribute.AclEntry> raw) {
    if (raw == null) {
      return null;
    }
    var values = new ArrayList<j$.nio.file.attribute.AclEntry>();
    for (var value : raw) {
      values.add(encode(value));
    }
    return values;
  }

  public static java.nio.file.attribute.AclEntry decode(j$.nio.file.attribute.AclEntry encoded) {
    if (encoded == null) {
      return null;
    }
    return java.nio.file.attribute.AclEntry.newBuilder()
        .setFlags(AclEntryFlagConversions.decode(encoded.flags()))
        .setPermissions(AclEntryPermissionConversions.decode(encoded.permissions()))
        .setPrincipal(UserPrincipalConversions.decode(encoded.principal()))
        .setType(AclEntryTypeConversions.decode(encoded.type()))
        .build();
  }

  public static List<java.nio.file.attribute.AclEntry> decode(
      List<j$.nio.file.attribute.AclEntry> encoded) {
    if (encoded == null) {
      return null;
    }
    var values = new ArrayList<java.nio.file.attribute.AclEntry>();
    for (var value : encoded) {
      values.add(decode(value));
    }
    return values;
  }

  private AclEntryConversions() {}
}
