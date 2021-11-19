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

/**
 * Type conversions between {@link java.nio.file.attribute.BasicFileAttributes} and {@link
 * j$.nio.file.attribute.BasicFileAttributes}.
 * <li>PosixFileAttributes
 * <li>DosFileAttributes
 */
public final class BasicFileAttributesConversions {

  public static <T extends j$.nio.file.attribute.BasicFileAttributes> T encode(
      java.nio.file.attribute.BasicFileAttributes raw, Class<T> encodedType) {
    if (raw == null) {
      return null;
    }
    if (encodedType == j$.nio.file.attribute.PosixFileAttributes.class) {
      return encodedType.cast(
          PosixFileAttributesConversions.encode((java.nio.file.attribute.PosixFileAttributes) raw));
    }
    if (encodedType == j$.nio.file.attribute.DosFileAttributes.class) {
      return encodedType.cast(
          DosFileAttributesConversions.encode((java.nio.file.attribute.DosFileAttributes) raw));
    }
    if (encodedType == j$.nio.file.attribute.BasicFileAttributes.class) {
      return encodedType.cast(encode(raw));
    }
    throw new UnsupportedOperationException("Unsupported encodedType: " + encodedType);
  }

  public static j$.nio.file.attribute.BasicFileAttributes encode(
      java.nio.file.attribute.BasicFileAttributes raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedBasicFileAttributes<?>) {
      return ((DecodedBasicFileAttributes<?>) raw).delegate;
    }
    return new EncodedBasicFileAttributes<>(raw);
  }

  public static Class<? extends j$.nio.file.attribute.BasicFileAttributes> encode(
      Class<? extends java.nio.file.attribute.BasicFileAttributes> rawType) {
    if (rawType == null) {
      return null;
    }
    if (rawType == java.nio.file.attribute.PosixFileAttributes.class) {
      return j$.nio.file.attribute.PosixFileAttributes.class;
    }
    if (rawType == java.nio.file.attribute.DosFileAttributes.class) {
      return j$.nio.file.attribute.DosFileAttributes.class;
    }
    if (rawType == java.nio.file.attribute.BasicFileAttributes.class) {
      return j$.nio.file.attribute.BasicFileAttributes.class;
    }
    throw new UnsupportedOperationException("Unsupported file attribute type: " + rawType);
  }

  public static java.nio.file.attribute.BasicFileAttributes decode(
      j$.nio.file.attribute.BasicFileAttributes encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedBasicFileAttributes<?>) {
      return ((EncodedBasicFileAttributes<?>) encoded).delegate;
    }
    return new DecodedBasicFileAttributes<>(encoded);
  }

  public static <T extends java.nio.file.attribute.BasicFileAttributes> T decode(
      j$.nio.file.attribute.BasicFileAttributes encoded, Class<T> decodedType) {
    if (encoded == null) {
      return null;
    }
    if (decodedType == java.nio.file.attribute.PosixFileAttributes.class) {
      return decodedType.cast(
          PosixFileAttributesConversions.decode(
              (j$.nio.file.attribute.PosixFileAttributes) encoded));
    }
    if (decodedType == java.nio.file.attribute.DosFileAttributes.class) {
      return decodedType.cast(
          DosFileAttributesConversions.decode((j$.nio.file.attribute.DosFileAttributes) encoded));
    }
    if (decodedType == java.nio.file.attribute.BasicFileAttributes.class) {
      return decodedType.cast(decode(encoded));
    }
    throw new UnsupportedOperationException("Unsupported encodedType: " + decodedType);
  }

  public static Class<? extends java.nio.file.attribute.BasicFileAttributes> decode(
      Class<? extends j$.nio.file.attribute.BasicFileAttributes> encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded == j$.nio.file.attribute.PosixFileAttributes.class) {
      return java.nio.file.attribute.PosixFileAttributes.class;
    }
    if (encoded == j$.nio.file.attribute.DosFileAttributes.class) {
      return java.nio.file.attribute.DosFileAttributes.class;
    }
    if (encoded == j$.nio.file.attribute.BasicFileAttributes.class) {
      return java.nio.file.attribute.BasicFileAttributes.class;
    }
    throw new UnsupportedOperationException("Unsupported file attribute type: " + encoded);
  }

  private BasicFileAttributesConversions() {}

  static class EncodedBasicFileAttributes<T extends java.nio.file.attribute.BasicFileAttributes>
      implements j$.nio.file.attribute.BasicFileAttributes {

    final T delegate;

    public EncodedBasicFileAttributes(T delegate) {
      this.delegate = delegate;
    }

    @Override
    public j$.nio.file.attribute.FileTime lastModifiedTime() {
      return FileTimeConversions.encode(delegate.lastModifiedTime());
    }

    @Override
    public j$.nio.file.attribute.FileTime lastAccessTime() {
      return FileTimeConversions.encode(delegate.lastAccessTime());
    }

    @Override
    public j$.nio.file.attribute.FileTime creationTime() {
      return FileTimeConversions.encode(delegate.creationTime());
    }

    @Override
    public boolean isRegularFile() {
      return delegate.isRegularFile();
    }

    @Override
    public boolean isDirectory() {
      return delegate.isDirectory();
    }

    @Override
    public boolean isSymbolicLink() {
      return delegate.isSymbolicLink();
    }

    @Override
    public boolean isOther() {
      return delegate.isOther();
    }

    @Override
    public long size() {
      return delegate.size();
    }

    @Override
    public Object fileKey() {
      return delegate.fileKey();
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof EncodedBasicFileAttributes<?>)) {
        return false;
      }
      return delegate.equals(((EncodedBasicFileAttributes<?>) obj).delegate);
    }

    @Override
    public String toString() {
      return delegate.toString();
    }
  }

  static class DecodedBasicFileAttributes<T extends j$.nio.file.attribute.BasicFileAttributes>
      implements java.nio.file.attribute.BasicFileAttributes {

    final T delegate;

    public DecodedBasicFileAttributes(T delegate) {
      this.delegate = delegate;
    }

    @Override
    public java.nio.file.attribute.FileTime lastModifiedTime() {
      return FileTimeConversions.decode(delegate.lastModifiedTime());
    }

    @Override
    public java.nio.file.attribute.FileTime lastAccessTime() {
      return FileTimeConversions.decode(delegate.lastAccessTime());
    }

    @Override
    public java.nio.file.attribute.FileTime creationTime() {
      return FileTimeConversions.decode(delegate.creationTime());
    }

    @Override
    public boolean isRegularFile() {
      return delegate.isRegularFile();
    }

    @Override
    public boolean isDirectory() {
      return delegate.isDirectory();
    }

    @Override
    public boolean isSymbolicLink() {
      return delegate.isSymbolicLink();
    }

    @Override
    public boolean isOther() {
      return delegate.isOther();
    }

    @Override
    public long size() {
      return delegate.size();
    }

    @Override
    public Object fileKey() {
      return delegate.fileKey();
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof DecodedBasicFileAttributes<?>)) {
        return false;
      }
      return delegate.equals(((DecodedBasicFileAttributes<?>) obj).delegate);
    }

    @Override
    public String toString() {
      return delegate.toString();
    }
  }
}
