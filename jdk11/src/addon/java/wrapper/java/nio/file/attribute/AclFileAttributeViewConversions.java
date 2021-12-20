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
import java.util.List;
import wrapper.java.nio.file.IOExceptionConversions;

/**
 * Type conversions between {@link java.nio.file.attribute.AclFileAttributeView} and {@link
 * j$.nio.file.attribute.AclFileAttributeView}.
 */
public final class AclFileAttributeViewConversions {

  public static j$.nio.file.attribute.AclFileAttributeView encode(
      java.nio.file.attribute.AclFileAttributeView raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedAclFileAttributeView<?>) {
      return ((DecodedAclFileAttributeView<?>) raw).delegate;
    }
    return new EncodedAclFileAttributeView<>(
        raw, java.nio.file.attribute.AclFileAttributeView.class);
  }

  public static java.nio.file.attribute.AclFileAttributeView decode(
      j$.nio.file.attribute.AclFileAttributeView encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedAclFileAttributeView<?>) {
      return ((EncodedAclFileAttributeView<?>) encoded).delegate;
    }
    return new DecodedAclFileAttributeView<>(
        encoded, j$.nio.file.attribute.AclFileAttributeView.class);
  }

  private AclFileAttributeViewConversions() {}

  static class EncodedAclFileAttributeView<T extends java.nio.file.attribute.AclFileAttributeView>
      extends FileOwnerAttributeViewConversions.EncodedFileOwnerAttributeView<T>
      implements j$.nio.file.attribute.AclFileAttributeView {

    public EncodedAclFileAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public List<j$.nio.file.attribute.AclEntry> getAcl() throws IOException {
      try {
        return AclEntryConversions.encode(delegate.getAcl());
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void setAcl(List<j$.nio.file.attribute.AclEntry> acl) throws IOException {
      try {
        delegate.setAcl(AclEntryConversions.decode(acl));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }
  }

  static class DecodedAclFileAttributeView<T extends j$.nio.file.attribute.AclFileAttributeView>
      extends FileOwnerAttributeViewConversions.DecodedFileOwnerAttributeView<T>
      implements java.nio.file.attribute.AclFileAttributeView {

    public DecodedAclFileAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public List<java.nio.file.attribute.AclEntry> getAcl() throws IOException {
      return AclEntryConversions.decode(delegate.getAcl());
    }

    @Override
    public void setAcl(List<java.nio.file.attribute.AclEntry> acl) throws IOException {
      delegate.setAcl(AclEntryConversions.encode(acl));
    }
  }
}
