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

import j$.nio.file.attribute.PosixFileAttributes;
import java.util.Set;
import wrapper.java.nio.file.attribute.BasicFileAttributesConversions.DecodedBasicFileAttributes;
import wrapper.java.nio.file.attribute.BasicFileAttributesConversions.EncodedBasicFileAttributes;

/**
 * Type conversions between {@link java.nio.file.attribute.PosixFileAttributes} and {@link
 * j$.nio.file.attribute.PosixFileAttributes}.
 */
public final class PosixFileAttributesConversions {

  public static j$.nio.file.attribute.PosixFileAttributes encode(
      java.nio.file.attribute.PosixFileAttributes raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedPosixFileAttributes) {
      return ((DecodedPosixFileAttributes) raw).delegate;
    }
    return new EncodedPosixFileAttributes(raw);
  }

  public static java.nio.file.attribute.PosixFileAttributes decode(
      j$.nio.file.attribute.PosixFileAttributes encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedPosixFileAttributes) {
      return ((EncodedPosixFileAttributes) encoded).delegate;
    }
    return new DecodedPosixFileAttributes(encoded);
  }

  private PosixFileAttributesConversions() {}

  static class EncodedPosixFileAttributes
      extends EncodedBasicFileAttributes<java.nio.file.attribute.PosixFileAttributes>
      implements j$.nio.file.attribute.PosixFileAttributes {

    public EncodedPosixFileAttributes(java.nio.file.attribute.PosixFileAttributes delegate) {
      super(delegate);
    }

    @Override
    public j$.nio.file.attribute.UserPrincipal owner() {
      return UserPrincipalConversions.encode(delegate.owner());
    }

    @Override
    public j$.nio.file.attribute.GroupPrincipal group() {
      return GroupPrincipalConversions.encode(delegate.group());
    }

    @Override
    public Set<j$.nio.file.attribute.PosixFilePermission> permissions() {
      return PosixFilePermissionConversions.encode(delegate.permissions());
    }
  }

  static class DecodedPosixFileAttributes
      extends DecodedBasicFileAttributes<j$.nio.file.attribute.PosixFileAttributes>
      implements java.nio.file.attribute.PosixFileAttributes {

    public DecodedPosixFileAttributes(PosixFileAttributes delegate) {
      super(delegate);
    }

    @Override
    public java.nio.file.attribute.UserPrincipal owner() {
      return UserPrincipalConversions.decode(delegate.owner());
    }

    @Override
    public java.nio.file.attribute.GroupPrincipal group() {
      return GroupPrincipalConversions.decode(delegate.group());
    }

    @Override
    public Set<java.nio.file.attribute.PosixFilePermission> permissions() {
      return PosixFilePermissionConversions.decode(delegate.permissions());
    }
  }
}
