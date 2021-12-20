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
import wrapper.java.nio.file.IOExceptionConversions;

/**
 * Type conversions between {@link java.nio.file.attribute.FileOwnerAttributeView} and {@link
 * j$.nio.file.attribute.FileOwnerAttributeView}.
 */
public final class FileOwnerAttributeViewConversions {

  public static j$.nio.file.attribute.FileOwnerAttributeView encode(
      java.nio.file.attribute.FileOwnerAttributeView raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedFileOwnerAttributeView<?>) {
      return ((DecodedFileOwnerAttributeView<?>) raw).delegate;
    }
    return new EncodedFileOwnerAttributeView<>(
        raw, java.nio.file.attribute.FileOwnerAttributeView.class);
  }

  public static java.nio.file.attribute.FileOwnerAttributeView decode(
      j$.nio.file.attribute.FileOwnerAttributeView encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedFileOwnerAttributeView<?>) {
      return ((EncodedFileOwnerAttributeView<?>) encoded).delegate;
    }
    return new DecodedFileOwnerAttributeView<>(
        encoded, j$.nio.file.attribute.FileOwnerAttributeView.class);
  }

  private FileOwnerAttributeViewConversions() {}

  static class EncodedFileOwnerAttributeView<
          T extends java.nio.file.attribute.FileOwnerAttributeView>
      extends FileAttributeViewConversions.EncodedFileAttributeView<T>
      implements j$.nio.file.attribute.FileOwnerAttributeView {

    public EncodedFileOwnerAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }

    @Override
    public String name() {
      return delegate.name();
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

  static class DecodedFileOwnerAttributeView<T extends j$.nio.file.attribute.FileOwnerAttributeView>
      extends FileAttributeViewConversions.DecodedFileAttributeView<T>
      implements java.nio.file.attribute.FileOwnerAttributeView {

    public DecodedFileOwnerAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }

    @Override
    public String name() {
      return delegate.name();
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
