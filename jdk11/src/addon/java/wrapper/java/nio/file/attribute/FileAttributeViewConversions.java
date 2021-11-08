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

import wrapper.java.nio.file.attribute.AttributeViewConversions.DecodedAttributeView;
import wrapper.java.nio.file.attribute.AttributeViewConversions.EncodedAttributeView;

/**
 * Type conversions between {@link java.nio.file.attribute.FileAttributeView} and {@link
 * j$.nio.file.attribute.FileAttributeView}.
 */
public final class FileAttributeViewConversions {

  public static j$.nio.file.attribute.FileAttributeView encode(
      java.nio.file.attribute.FileAttributeView raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedFileAttributeView<?>) {
      return ((DecodedFileAttributeView<?>) raw).delegate;
    }
    return new EncodedFileAttributeView<>(raw, java.nio.file.attribute.FileAttributeView.class);
  }

  public static java.nio.file.attribute.FileAttributeView decode(
      j$.nio.file.attribute.FileAttributeView encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedFileAttributeView<?>) {
      return ((EncodedFileAttributeView<?>) encoded).delegate;
    }
    return new DecodedFileAttributeView<>(encoded, j$.nio.file.attribute.FileAttributeView.class);
  }

  private FileAttributeViewConversions() {}

  static class EncodedFileAttributeView<T extends java.nio.file.attribute.FileAttributeView>
      extends EncodedAttributeView<T> implements j$.nio.file.attribute.FileAttributeView {

    public EncodedFileAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }
  }

  static class DecodedFileAttributeView<T extends j$.nio.file.attribute.FileAttributeView>
      extends DecodedAttributeView<T> implements java.nio.file.attribute.FileAttributeView {

    public DecodedFileAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }
  }
}
