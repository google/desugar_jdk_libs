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
 * Type conversions between {@link java.nio.file.attribute.DosFileAttributeView} and {@link
 * j$.nio.file.attribute.DosFileAttributeView}.
 */
public final class DosFileAttributeViewConversions {

  public static j$.nio.file.attribute.DosFileAttributeView encode(
      java.nio.file.attribute.DosFileAttributeView raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedDosFileAttributeView<?>) {
      return ((DecodedDosFileAttributeView<?>) raw).delegate;
    }
    return new EncodedDosFileAttributeView<>(
        raw, java.nio.file.attribute.DosFileAttributeView.class);
  }

  public static java.nio.file.attribute.DosFileAttributeView decode(
      j$.nio.file.attribute.DosFileAttributeView encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedDosFileAttributeView<?>) {
      return ((EncodedDosFileAttributeView<?>) encoded).delegate;
    }
    return new DecodedDosFileAttributeView<>(
        encoded, j$.nio.file.attribute.DosFileAttributeView.class);
  }

  private DosFileAttributeViewConversions() {}

  static class EncodedDosFileAttributeView<T extends java.nio.file.attribute.DosFileAttributeView>
      extends BasicFileAttributeViewConversions.EncodedBasicFileAttributeView<T>
      implements j$.nio.file.attribute.DosFileAttributeView {

    public EncodedDosFileAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }

    @Override
    public j$.nio.file.attribute.DosFileAttributes readAttributes() throws IOException {
      try {
        return DosFileAttributesConversions.encode(delegate.readAttributes());
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void setReadOnly(boolean value) throws IOException {
      try {
        delegate.setReadOnly(value);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void setHidden(boolean value) throws IOException {
      try {
        delegate.setHidden(value);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void setSystem(boolean value) throws IOException {
      try {
        delegate.setSystem(value);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void setArchive(boolean value) throws IOException {
      try {
        delegate.setArchive(value);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }
  }

  static class DecodedDosFileAttributeView<T extends j$.nio.file.attribute.DosFileAttributeView>
      extends BasicFileAttributeViewConversions.DecodedBasicFileAttributeView<T>
      implements java.nio.file.attribute.DosFileAttributeView {

    public DecodedDosFileAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }

    @Override
    public java.nio.file.attribute.DosFileAttributes readAttributes() throws IOException {
      return DosFileAttributesConversions.decode(delegate.readAttributes());
    }

    @Override
    public void setReadOnly(boolean value) throws IOException {
      delegate.setReadOnly(value);
    }

    @Override
    public void setHidden(boolean value) throws IOException {
      delegate.setHidden(value);
    }

    @Override
    public void setSystem(boolean value) throws IOException {
      delegate.setSystem(value);
    }

    @Override
    public void setArchive(boolean value) throws IOException {
      delegate.setArchive(value);
    }
  }
}
