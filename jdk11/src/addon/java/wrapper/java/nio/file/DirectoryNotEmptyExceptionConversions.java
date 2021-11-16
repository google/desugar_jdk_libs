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
 */

package wrapper.java.nio.file;


/**
 * Type conversions between {@link java.nio.file.DirectoryNotEmptyException} and {@link
 * j$.nio.file.DirectoryNotEmptyException}.
 */
public final class DirectoryNotEmptyExceptionConversions {

  public static j$.nio.file.DirectoryNotEmptyException encode(
      java.nio.file.DirectoryNotEmptyException raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedDirectoryNotEmptyException) {
      return ((DecodedDirectoryNotEmptyException) raw).delegate;
    }
    return new EncodedDirectoryNotEmptyException(raw);
  }

  public static java.nio.file.DirectoryNotEmptyException decode(
      j$.nio.file.DirectoryNotEmptyException encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedDirectoryNotEmptyException) {
      return ((EncodedDirectoryNotEmptyException) encoded).delegate;
    }
    return new DecodedDirectoryNotEmptyException(encoded);
  }

  private DirectoryNotEmptyExceptionConversions() {}

  static class EncodedDirectoryNotEmptyException extends j$.nio.file.DirectoryNotEmptyException {
    private final java.nio.file.DirectoryNotEmptyException delegate;

    EncodedDirectoryNotEmptyException(java.nio.file.DirectoryNotEmptyException delegate) {
      super(delegate.getFile());
      this.delegate = delegate;
    }
  }

  static class DecodedDirectoryNotEmptyException extends java.nio.file.DirectoryNotEmptyException {
    private final j$.nio.file.DirectoryNotEmptyException delegate;

    DecodedDirectoryNotEmptyException(j$.nio.file.DirectoryNotEmptyException delegate) {
      super(delegate.getFile());
      this.delegate = delegate;
    }
  }
}
