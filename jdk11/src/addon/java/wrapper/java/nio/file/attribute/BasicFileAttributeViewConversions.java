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
 * Type conversions between {@link java.nio.file.attribute.BasicFileAttributeView} and {@link
 * j$.nio.file.attribute.BasicFileAttributeView}.
 */
public class BasicFileAttributeViewConversions {

  public static j$.nio.file.attribute.BasicFileAttributeView encode(
      java.nio.file.attribute.BasicFileAttributeView raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedBasicFileAttributeView<?>) {
      return ((DecodedBasicFileAttributeView<?>) raw).delegate;
    }
    return new EncodedBasicFileAttributeView<>(
        raw, java.nio.file.attribute.BasicFileAttributeView.class);
  }

  public static java.nio.file.attribute.BasicFileAttributeView decode(
      j$.nio.file.attribute.BasicFileAttributeView encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedBasicFileAttributeView<?>) {
      return ((EncodedBasicFileAttributeView<?>) encoded).delegate;
    }
    return new DecodedBasicFileAttributeView<>(
        encoded, j$.nio.file.attribute.BasicFileAttributeView.class);
  }

  private BasicFileAttributeViewConversions() {}

  static class EncodedBasicFileAttributeView<
          T extends java.nio.file.attribute.BasicFileAttributeView>
      extends FileAttributeViewConversions.EncodedFileAttributeView<T>
      implements j$.nio.file.attribute.BasicFileAttributeView {

    public EncodedBasicFileAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }

    @Override
    public String name() {
      return getDelegate().name();
    }

    @Override
    public j$.nio.file.attribute.BasicFileAttributes readAttributes() throws IOException {
      try {
        return BasicFileAttributesConversions.encode(
            getDelegate().readAttributes(), j$.nio.file.attribute.BasicFileAttributes.class);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void setTimes(
        j$.nio.file.attribute.FileTime lastModifiedTime,
        j$.nio.file.attribute.FileTime lastAccessTime,
        j$.nio.file.attribute.FileTime createTime)
        throws IOException {
      try {
        delegate.setTimes(
            FileTimeConversions.decode(lastModifiedTime),
            FileTimeConversions.decode(lastAccessTime),
            FileTimeConversions.decode(createTime));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }
  }

  static class DecodedBasicFileAttributeView<T extends j$.nio.file.attribute.BasicFileAttributeView>
      extends FileAttributeViewConversions.DecodedFileAttributeView<T>
      implements java.nio.file.attribute.BasicFileAttributeView {

    public DecodedBasicFileAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }

    @Override
    public java.nio.file.attribute.BasicFileAttributes readAttributes() throws IOException {
      return BasicFileAttributesConversions.decode(
          delegate.readAttributes(), java.nio.file.attribute.BasicFileAttributes.class);
    }

    @Override
    public void setTimes(
        java.nio.file.attribute.FileTime lastModifiedTime,
        java.nio.file.attribute.FileTime lastAccessTime,
        java.nio.file.attribute.FileTime createTime)
        throws IOException {
      delegate.setTimes(
          FileTimeConversions.encode(lastModifiedTime),
          FileTimeConversions.encode(lastAccessTime),
          FileTimeConversions.encode(createTime));
    }
  }
}
