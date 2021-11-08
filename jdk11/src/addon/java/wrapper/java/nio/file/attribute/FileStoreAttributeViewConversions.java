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
 * Type conversions between {@link java.nio.file.attribute.FileStoreAttributeView} and {@link
 * j$.nio.file.attribute.FileStoreAttributeView}.
 */
public class FileStoreAttributeViewConversions {

  public static j$.nio.file.attribute.FileStoreAttributeView encode(
      java.nio.file.attribute.FileStoreAttributeView raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedFileStoreAttributeView<?>) {
      return ((DecodedFileStoreAttributeView<?>) raw).delegate;
    }
    return new EncodedFileStoreAttributeView<>(
        raw, java.nio.file.attribute.FileStoreAttributeView.class);
  }

  public static java.nio.file.attribute.FileStoreAttributeView decode(
      j$.nio.file.attribute.FileStoreAttributeView encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedFileStoreAttributeView<?>) {
      return ((EncodedFileStoreAttributeView<?>) encoded).delegate;
    }
    return new DecodedFileStoreAttributeView<>(
        encoded, j$.nio.file.attribute.FileStoreAttributeView.class);
  }

  private FileStoreAttributeViewConversions() {}

  static class EncodedFileStoreAttributeView<
          T extends java.nio.file.attribute.FileStoreAttributeView>
      extends EncodedAttributeView<T> implements j$.nio.file.attribute.FileStoreAttributeView {

    public EncodedFileStoreAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }
  }

  static class DecodedFileStoreAttributeView<T extends j$.nio.file.attribute.FileStoreAttributeView>
      extends DecodedAttributeView<T> implements java.nio.file.attribute.FileStoreAttributeView {

    public DecodedFileStoreAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }
  }
}
