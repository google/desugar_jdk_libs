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
 * Type conversions between {@link java.nio.file.FileAlreadyExistsException} and {@link
 * j$.nio.file.FileAlreadyExistsException}.
 */
public final class FileAlreadyExistsExceptionConversions {

  public static j$.nio.file.FileAlreadyExistsException encode(
      java.nio.file.FileAlreadyExistsException raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedFileAlreadyExistsException) {
      return ((DecodedFileAlreadyExistsException) raw).delegate;
    }
    return new EncodedFileAlreadyExistsException(raw);
  }

  public static java.nio.file.FileAlreadyExistsException decode(
      j$.nio.file.FileAlreadyExistsException encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedFileAlreadyExistsException) {
      return ((EncodedFileAlreadyExistsException) encoded).delegate;
    }
    return new DecodedFileAlreadyExistsException(encoded);
  }

  private FileAlreadyExistsExceptionConversions() {}

  static class EncodedFileAlreadyExistsException extends j$.nio.file.FileAlreadyExistsException {
    private final java.nio.file.FileAlreadyExistsException delegate;

    EncodedFileAlreadyExistsException(java.nio.file.FileAlreadyExistsException delegate) {
      super(delegate.getFile(), delegate.getOtherFile(), delegate.getReason());
      this.delegate = delegate;
    }
  }

  static class DecodedFileAlreadyExistsException extends java.nio.file.FileAlreadyExistsException {
    private final j$.nio.file.FileAlreadyExistsException delegate;

    DecodedFileAlreadyExistsException(j$.nio.file.FileAlreadyExistsException delegate) {
      super(delegate.getFile(), delegate.getOtherFile(), delegate.getReason());
      this.delegate = delegate;
    }
  }
}
