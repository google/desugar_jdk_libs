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

import java.util.Map;

/**
 * Type conversions between {@link java.nio.file.attribute.AttributeView} and {@link
 * j$.nio.file.attribute.AttributeView} with considerations of sub classes.
 * <li>JavaAclFileAttributeView
 * <li>JavaBasicFileAttributeView
 * <li>JavaDosFileAttributeView
 * <li>JavaFileAttributeView
 * <li>JavaFileOwnerAttributeView
 * <li>JavaFileStoreAttributeView
 * <li>JavaPosixFileAttributeView
 * <li>JavaUserDefinedFileAttributeView
 */
public final class AttributeViewConversions {

  private static final Map<
          Class<? extends java.nio.file.attribute.AttributeView>,
          Class<? extends j$.nio.file.attribute.AttributeView>>
      ATTRIBUTE_VIEW_ENCODE_MAP =
          Map.of(
              java.nio.file.attribute.AclFileAttributeView.class,
                  j$.nio.file.attribute.AclFileAttributeView.class,
              java.nio.file.attribute.BasicFileAttributeView.class,
                  j$.nio.file.attribute.BasicFileAttributeView.class,
              java.nio.file.attribute.DosFileAttributeView.class,
                  j$.nio.file.attribute.DosFileAttributeView.class,
              java.nio.file.attribute.FileAttributeView.class,
                  j$.nio.file.attribute.FileAttributeView.class,
              java.nio.file.attribute.FileOwnerAttributeView.class,
                  j$.nio.file.attribute.FileOwnerAttributeView.class,
              java.nio.file.attribute.FileStoreAttributeView.class,
                  j$.nio.file.attribute.FileStoreAttributeView.class,
              java.nio.file.attribute.PosixFileAttributeView.class,
                  j$.nio.file.attribute.PosixFileAttributeView.class,
              java.nio.file.attribute.UserDefinedFileAttributeView.class,
                  j$.nio.file.attribute.UserDefinedFileAttributeView.class);

  private static final Map<
          Class<? extends j$.nio.file.attribute.AttributeView>,
          Class<? extends java.nio.file.attribute.AttributeView>>
      ATTRIBUTE_VIEW_DECODE_MAP =
          Map.of(
              j$.nio.file.attribute.AclFileAttributeView.class,
                  java.nio.file.attribute.AclFileAttributeView.class,
              j$.nio.file.attribute.BasicFileAttributeView.class,
                  java.nio.file.attribute.BasicFileAttributeView.class,
              j$.nio.file.attribute.DosFileAttributeView.class,
                  java.nio.file.attribute.DosFileAttributeView.class,
              j$.nio.file.attribute.FileAttributeView.class,
                  java.nio.file.attribute.FileAttributeView.class,
              j$.nio.file.attribute.FileOwnerAttributeView.class,
                  java.nio.file.attribute.FileOwnerAttributeView.class,
              j$.nio.file.attribute.FileStoreAttributeView.class,
                  java.nio.file.attribute.FileStoreAttributeView.class,
              j$.nio.file.attribute.PosixFileAttributeView.class,
                  java.nio.file.attribute.PosixFileAttributeView.class,
              j$.nio.file.attribute.UserDefinedFileAttributeView.class,
                  java.nio.file.attribute.UserDefinedFileAttributeView.class);

  public static Class<? extends j$.nio.file.attribute.AttributeView> encode(
      Class<? extends java.nio.file.attribute.AttributeView> raw) {
    return raw == null
        ? null
        : ATTRIBUTE_VIEW_ENCODE_MAP.getOrDefault(
            raw, j$.nio.file.attribute.FileAttributeView.class);
  }

  public static <T extends j$.nio.file.attribute.AttributeView> T encode(
      java.nio.file.attribute.AttributeView raw, Class<T> encodedType) {
    if (encodedType == null) {
      return null;
    }
    if (encodedType == j$.nio.file.attribute.AclFileAttributeView.class) {
      return encodedType.cast(
          AclFileAttributeViewConversions.encode(
              (java.nio.file.attribute.AclFileAttributeView) raw));
    }
    if (encodedType == j$.nio.file.attribute.BasicFileAttributeView.class) {
      return encodedType.cast(
          BasicFileAttributeViewConversions.encode(
              (java.nio.file.attribute.BasicFileAttributeView) raw));
    }
    if (encodedType == j$.nio.file.attribute.DosFileAttributeView.class) {
      return encodedType.cast(
          DosFileAttributeViewConversions.encode(
              (java.nio.file.attribute.DosFileAttributeView) raw));
    }
    if (encodedType == j$.nio.file.attribute.FileAttributeView.class) {
      return encodedType.cast(
          FileAttributeViewConversions.encode((java.nio.file.attribute.FileAttributeView) raw));
    }
    if (encodedType == j$.nio.file.attribute.FileOwnerAttributeView.class) {
      return encodedType.cast(
          FileOwnerAttributeViewConversions.encode(
              (java.nio.file.attribute.FileOwnerAttributeView) raw));
    }
    if (encodedType == j$.nio.file.attribute.FileStoreAttributeView.class) {
      return encodedType.cast(
          FileStoreAttributeViewConversions.encode(
              (java.nio.file.attribute.FileStoreAttributeView) raw));
    }
    if (encodedType == j$.nio.file.attribute.PosixFileAttributeView.class) {
      return encodedType.cast(
          PosixFileAttributeViewConversions.encode(
              (java.nio.file.attribute.PosixFileAttributeView) raw));
    }
    if (encodedType == j$.nio.file.attribute.UserDefinedFileAttributeView.class) {
      return encodedType.cast(
          UserDefinedFileAttributeViewConversions.encode(
              (java.nio.file.attribute.UserDefinedFileAttributeView) raw));
    }
    if (encodedType == j$.nio.file.attribute.AttributeView.class) {
      return encodedType.cast(AttributeViewConversions.encode(raw));
    }
    throw new UnsupportedOperationException(
        "Expected a platform-wrapped type to decode, but got: " + raw);
  }

  public static j$.nio.file.attribute.AttributeView encode(
      java.nio.file.attribute.AttributeView raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedAttributeView<?>) {
      return ((DecodedAttributeView<?>) raw).delegate;
    }
    return new EncodedAttributeView<>(raw, java.nio.file.attribute.AttributeView.class);
  }

  public static Class<? extends java.nio.file.attribute.AttributeView> decode(
      Class<? extends j$.nio.file.attribute.AttributeView> encoded) {
    if (encoded == null) {
      return null;
    }
    return ATTRIBUTE_VIEW_DECODE_MAP.getOrDefault(
        encoded, java.nio.file.attribute.AttributeView.class);
  }

  public static <T extends java.nio.file.attribute.AttributeView> T decode(
      j$.nio.file.attribute.AttributeView encoded, Class<T> decodedType) {
    if (encoded == null) {
      return null;
    }
    if (decodedType == java.nio.file.attribute.AclFileAttributeView.class) {
      return decodedType.cast(
          AclFileAttributeViewConversions.decode(
              (j$.nio.file.attribute.AclFileAttributeView) encoded));
    }
    if (decodedType == java.nio.file.attribute.BasicFileAttributeView.class) {
      return decodedType.cast(
          BasicFileAttributeViewConversions.decode(
              (j$.nio.file.attribute.BasicFileAttributeView) encoded));
    }
    if (decodedType == java.nio.file.attribute.DosFileAttributeView.class) {
      return decodedType.cast(
          DosFileAttributeViewConversions.decode(
              (j$.nio.file.attribute.DosFileAttributeView) encoded));
    }
    if (decodedType == java.nio.file.attribute.FileAttributeView.class) {
      return decodedType.cast(
          FileAttributeViewConversions.decode((j$.nio.file.attribute.FileAttributeView) encoded));
    }
    if (decodedType == java.nio.file.attribute.FileOwnerAttributeView.class) {
      return decodedType.cast(
          FileOwnerAttributeViewConversions.decode(
              (j$.nio.file.attribute.FileOwnerAttributeView) encoded));
    }
    if (decodedType == java.nio.file.attribute.FileStoreAttributeView.class) {
      return decodedType.cast(
          FileStoreAttributeViewConversions.decode(
              (j$.nio.file.attribute.FileStoreAttributeView) encoded));
    }
    if (decodedType == java.nio.file.attribute.PosixFileAttributeView.class) {
      return decodedType.cast(
          PosixFileAttributeViewConversions.decode(
              (j$.nio.file.attribute.PosixFileAttributeView) encoded));
    }
    if (decodedType == java.nio.file.attribute.UserDefinedFileAttributeView.class) {
      return decodedType.cast(
          UserDefinedFileAttributeViewConversions.decode(
              (j$.nio.file.attribute.UserDefinedFileAttributeView) encoded));
    }
    if (decodedType == java.nio.file.attribute.AttributeView.class) {
      return decodedType.cast(AttributeViewConversions.decode(encoded));
    }
    throw new UnsupportedOperationException(
        "Expected a platform-wrapped type to decode, but got: " + encoded);
  }

  public static java.nio.file.attribute.AttributeView decode(
      j$.nio.file.attribute.AttributeView encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedAttributeView<?>) {
      return ((EncodedAttributeView<?>) encoded).delegate;
    }
    return new DecodedAttributeView<>(encoded, j$.nio.file.attribute.AttributeView.class);
  }

  private AttributeViewConversions() {}

  static class EncodedAttributeView<T extends java.nio.file.attribute.AttributeView>
      implements j$.nio.file.attribute.AttributeView {
    final T delegate;
    final Class<T> delegateType;

    public EncodedAttributeView(T delegate, Class<T> delegateType) {
      this.delegate = delegate;
      this.delegateType = delegateType;
    }

    public T getDelegate() {
      return delegate;
    }

    public Class<T> getDelegateType() {
      return delegateType;
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof EncodedAttributeView<?>)) {
        return false;
      }
      return delegate.equals(((EncodedAttributeView<?>) obj).delegate);
    }

    @Override
    public String toString() {
      return delegate.toString();
    }
  }

  static class DecodedAttributeView<T extends j$.nio.file.attribute.AttributeView>
      implements java.nio.file.attribute.AttributeView {

    final T delegate;

    final Class<T> delegateType;

    public DecodedAttributeView(T delegate, Class<T> delegateType) {
      this.delegate = delegate;
      this.delegateType = delegateType;
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public String toString() {
      return delegate.toString();
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof DecodedAttributeView<?>)) {
        return false;
      }
      return delegate.equals(((DecodedAttributeView<?>) obj).delegate);
    }
  }
}
