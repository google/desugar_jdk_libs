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

import wrapper.java.nio.file.attribute.BasicFileAttributesConversions.DecodedBasicFileAttributes;
import wrapper.java.nio.file.attribute.BasicFileAttributesConversions.EncodedBasicFileAttributes;

/**
 * Type conversions between {@link java.nio.file.attribute.DosFileAttributes} and {@link
 * j$.nio.file.attribute.DosFileAttributes}.
 */
public final class DosFileAttributesConversions {

  public static j$.nio.file.attribute.DosFileAttributes encode(
      java.nio.file.attribute.DosFileAttributes raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedDosFileAttributes) {
      return ((DecodedDosFileAttributes) raw).delegate;
    }
    return new EncodedDosFileAttributes(raw);
  }

  public static java.nio.file.attribute.DosFileAttributes decode(
      j$.nio.file.attribute.DosFileAttributes encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedDosFileAttributes) {
      return ((EncodedDosFileAttributes) encoded).delegate;
    }
    return new DecodedDosFileAttributes(encoded);
  }

  private DosFileAttributesConversions() {}

  static class EncodedDosFileAttributes
      extends EncodedBasicFileAttributes<java.nio.file.attribute.DosFileAttributes>
      implements j$.nio.file.attribute.DosFileAttributes {

    public EncodedDosFileAttributes(java.nio.file.attribute.DosFileAttributes delegate) {
      super(delegate);
    }

    @Override
    public boolean isReadOnly() {
      return delegate.isReadOnly();
    }

    @Override
    public boolean isHidden() {
      return delegate.isHidden();
    }

    @Override
    public boolean isArchive() {
      return delegate.isArchive();
    }

    @Override
    public boolean isSystem() {
      return delegate.isSystem();
    }
  }

  static class DecodedDosFileAttributes
      extends DecodedBasicFileAttributes<j$.nio.file.attribute.DosFileAttributes>
      implements java.nio.file.attribute.DosFileAttributes {

    public DecodedDosFileAttributes(j$.nio.file.attribute.DosFileAttributes delegate) {
      super(delegate);
    }

    @Override
    public boolean isReadOnly() {
      return delegate.isReadOnly();
    }

    @Override
    public boolean isHidden() {
      return delegate.isHidden();
    }

    @Override
    public boolean isArchive() {
      return delegate.isArchive();
    }

    @Override
    public boolean isSystem() {
      return delegate.isSystem();
    }
  }
}
