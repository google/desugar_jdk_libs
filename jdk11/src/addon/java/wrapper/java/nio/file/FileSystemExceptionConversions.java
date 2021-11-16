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
 * Type conversions between {@link java.nio.file.FileSystemException} and {@link
 * j$.nio.file.FileSystemException}.
 */
public final class FileSystemExceptionConversions {

  public static j$.nio.file.FileSystemException encode(java.nio.file.FileSystemException raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedFileSystemException) {
      return ((DecodedFileSystemException) raw).delegate;
    }
    return new EncodedFileSystemException(raw);
  }

  public static java.nio.file.FileSystemException decode(j$.nio.file.FileSystemException encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedFileSystemException) {
      return ((EncodedFileSystemException) encoded).delegate;
    }
    return new DecodedFileSystemException(encoded);
  }

  private FileSystemExceptionConversions() {}

  static class EncodedFileSystemException extends j$.nio.file.FileSystemException {
    private final java.nio.file.FileSystemException delegate;

    EncodedFileSystemException(java.nio.file.FileSystemException delegate) {
      super(delegate.getFile(), delegate.getOtherFile(), delegate.getReason());
      this.delegate = delegate;
    }
  }

  static class DecodedFileSystemException extends java.nio.file.FileSystemException {
    private final j$.nio.file.FileSystemException delegate;

    DecodedFileSystemException(j$.nio.file.FileSystemException delegate) {
      super(delegate.getFile(), delegate.getOtherFile(), delegate.getReason());
      this.delegate = delegate;
    }
  }
}
