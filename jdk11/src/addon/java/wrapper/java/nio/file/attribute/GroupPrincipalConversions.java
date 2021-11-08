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

import java.nio.file.attribute.GroupPrincipal;
import wrapper.java.nio.file.attribute.UserPrincipalConversions.DecodedUserPrincipal;
import wrapper.java.nio.file.attribute.UserPrincipalConversions.EncodedUserPrincipal;

/**
 * Type conversions between {@link java.nio.file.attribute.GroupPrincipal} and {@link
 * j$.nio.file.attribute.GroupPrincipal}.
 */
public final class GroupPrincipalConversions {

  public static j$.nio.file.attribute.GroupPrincipal encode(
      java.nio.file.attribute.GroupPrincipal raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedGroupPrincipal) {
      return ((DecodedGroupPrincipal) raw).getDelegate();
    }
    return new EncodedGroupPrincipal(raw);
  }

  public static GroupPrincipal decode(j$.nio.file.attribute.GroupPrincipal encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedGroupPrincipal) {
      return ((EncodedGroupPrincipal) encoded).getDelegate();
    }
    return new DecodedGroupPrincipal(encoded);
  }

  private GroupPrincipalConversions() {}

  static class EncodedGroupPrincipal extends EncodedUserPrincipal
      implements j$.nio.file.attribute.GroupPrincipal {

    public EncodedGroupPrincipal(java.nio.file.attribute.GroupPrincipal delegate) {
      super(delegate);
    }

    @Override
    public java.nio.file.attribute.GroupPrincipal getDelegate() {
      return (java.nio.file.attribute.GroupPrincipal) super.getDelegate();
    }
  }

  static class DecodedGroupPrincipal extends DecodedUserPrincipal
      implements java.nio.file.attribute.GroupPrincipal {

    public DecodedGroupPrincipal(j$.nio.file.attribute.GroupPrincipal delegate) {
      super(delegate);
    }

    @Override
    public j$.nio.file.attribute.GroupPrincipal getDelegate() {
      return (j$.nio.file.attribute.GroupPrincipal) super.getDelegate();
    }
  }
}
