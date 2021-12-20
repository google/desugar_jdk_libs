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

import java.io.IOException;
import java.util.Set;
import wrapper.java.nio.file.IOExceptionConversions;

/**
 * Type conversions between {@link java.nio.file.attribute.PosixFileAttributeView} and {@link
 * j$.nio.file.attribute.PosixFileAttributeView}.
 */
public final class PosixFileAttributeViewConversions {

  public static j$.nio.file.attribute.PosixFileAttributeView encode(
      java.nio.file.attribute.PosixFileAttributeView raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedPosixFileAttributeView<?>) {
      return ((DecodedPosixFileAttributeView<?>) raw).delegate;
    }
    return new EncodedPosixFileAttributeView<>(
        raw, java.nio.file.attribute.PosixFileAttributeView.class);
  }

  public static java.nio.file.attribute.PosixFileAttributeView decode(
      j$.nio.file.attribute.PosixFileAttributeView encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedPosixFileAttributeView<?>) {
      return ((EncodedPosixFileAttributeView<?>) encoded).delegate;
    }
    return new DecodedPosixFileAttributeView<>(
        encoded, j$.nio.file.attribute.PosixFileAttributeView.class);
  }

  private PosixFileAttributeViewConversions() {}

  static class EncodedPosixFileAttributeView<
          T extends
              java.nio.file.attribute.PosixFileAttributeView
                  & java.nio.file.attribute.FileOwnerAttributeView>
      extends BasicFileAttributeViewConversions.EncodedBasicFileAttributeView<T>
      implements j$.nio.file.attribute.PosixFileAttributeView {

    public EncodedPosixFileAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public j$.nio.file.attribute.PosixFileAttributes readAttributes() throws IOException {
      try {
        return PosixFileAttributesConversions.encode(delegate.readAttributes());
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void setPermissions(Set<j$.nio.file.attribute.PosixFilePermission> perms)
        throws IOException {
      try {
        delegate.setPermissions(PosixFilePermissionConversions.decode(perms));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void setGroup(j$.nio.file.attribute.GroupPrincipal group) throws IOException {
      try {
        delegate.setGroup(GroupPrincipalConversions.decode(group));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public j$.nio.file.attribute.UserPrincipal getOwner() throws IOException {
      try {
        return UserPrincipalConversions.encode(delegate.getOwner());
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void setOwner(j$.nio.file.attribute.UserPrincipal owner) throws IOException {
      try {
        delegate.setOwner(UserPrincipalConversions.decode(owner));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }
  }

  static class DecodedPosixFileAttributeView<
          T extends
              j$.nio.file.attribute.PosixFileAttributeView
                  & j$.nio.file.attribute.FileOwnerAttributeView>
      extends BasicFileAttributeViewConversions.DecodedBasicFileAttributeView<T>
      implements java.nio.file.attribute.PosixFileAttributeView {

    public DecodedPosixFileAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public java.nio.file.attribute.PosixFileAttributes readAttributes() throws IOException {
      return PosixFileAttributesConversions.decode(delegate.readAttributes());
    }

    @Override
    public void setPermissions(Set<java.nio.file.attribute.PosixFilePermission> perms)
        throws IOException {
      delegate.setPermissions(PosixFilePermissionConversions.encode(perms));
    }

    @Override
    public void setGroup(java.nio.file.attribute.GroupPrincipal group) throws IOException {
      delegate.setGroup(GroupPrincipalConversions.encode(group));
    }

    @Override
    public java.nio.file.attribute.UserPrincipal getOwner() throws IOException {
      return UserPrincipalConversions.decode(delegate.getOwner());
    }

    @Override
    public void setOwner(java.nio.file.attribute.UserPrincipal owner) throws IOException {
      delegate.setOwner(UserPrincipalConversions.encode(owner));
    }
  }
}
