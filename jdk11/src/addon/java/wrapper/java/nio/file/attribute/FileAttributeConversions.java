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
 * Type conversions between {@link java.nio.file.attribute.FileAttribute} and {@link
 * j$.nio.file.attribute.FileAttribute}.
 */
// TODO(deltazulu): Handle the cases of T conversions.
public final class FileAttributeConversions {

  public static <T> j$.nio.file.attribute.FileAttribute<T> encode(
      java.nio.file.attribute.FileAttribute<T> raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedFileAttribute<?>) {
      return ((DecodedFileAttribute<T>) raw).delegate;
    }
    return new EncodedFileAttribute<>(raw);
  }

  public static j$.nio.file.attribute.FileAttribute<?>[] encode(
      java.nio.file.attribute.FileAttribute<?>[] raw) {
    if (raw == null) {
      return null;
    }
    int n = raw.length;
    var results = new j$.nio.file.attribute.FileAttribute<?>[n];
    for (int i = 0; i < n; i++) {
      results[i] = encode(raw[i]);
    }
    return results;
  }

  public static <T> java.nio.file.attribute.FileAttribute<T> decode(
      j$.nio.file.attribute.FileAttribute<T> encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedFileAttribute<?>) {
      return ((EncodedFileAttribute<T>) encoded).delegate;
    }
    return new DecodedFileAttribute<>(encoded);
  }

  public static java.nio.file.attribute.FileAttribute<?>[] decode(
      j$.nio.file.attribute.FileAttribute<?>[] encoded) {
    if (encoded == null) {
      return null;
    }
    int n = encoded.length;
    var results = new java.nio.file.attribute.FileAttribute<?>[n];
    for (int i = 0; i < n; i++) {
      results[i] = decode(encoded[i]);
    }
    return results;
  }

  private FileAttributeConversions() {}

  static class EncodedFileAttribute<T> implements j$.nio.file.attribute.FileAttribute<T> {

    private final java.nio.file.attribute.FileAttribute<T> delegate;

    public EncodedFileAttribute(java.nio.file.attribute.FileAttribute<T> delegate) {
      this.delegate = delegate;
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public T value() {
      return delegate.value();
    }
  }

  static class DecodedFileAttribute<T> implements java.nio.file.attribute.FileAttribute<T> {

    private final j$.nio.file.attribute.FileAttribute<T> delegate;

    public DecodedFileAttribute(j$.nio.file.attribute.FileAttribute<T> delegate) {
      this.delegate = delegate;
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public T value() {
      return delegate.value();
    }
  }
}
