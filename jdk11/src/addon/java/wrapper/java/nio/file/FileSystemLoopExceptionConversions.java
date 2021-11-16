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
 * Type conversions between {@link java.nio.file.FileSystemLoopException} and {@link
 * j$.nio.file.FileSystemLoopException}.
 */
public final class FileSystemLoopExceptionConversions {

  public static j$.nio.file.FileSystemLoopException encode(
      java.nio.file.FileSystemLoopException raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedFileSystemLoopException) {
      return ((DecodedFileSystemLoopException) raw).delegate;
    }
    return new EncodedFileSystemLoopException(raw);
  }

  public static java.nio.file.FileSystemLoopException decode(
      j$.nio.file.FileSystemLoopException encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedFileSystemLoopException) {
      return ((EncodedFileSystemLoopException) encoded).delegate;
    }
    return new DecodedFileSystemLoopException(encoded);
  }

  private FileSystemLoopExceptionConversions() {}

  static class EncodedFileSystemLoopException extends j$.nio.file.FileSystemLoopException {
    private final java.nio.file.FileSystemLoopException delegate;

    EncodedFileSystemLoopException(java.nio.file.FileSystemLoopException delegate) {
      super(delegate.getFile());
      this.delegate = delegate;
    }
  }

  static class DecodedFileSystemLoopException extends java.nio.file.FileSystemLoopException {
    private final j$.nio.file.FileSystemLoopException delegate;

    DecodedFileSystemLoopException(j$.nio.file.FileSystemLoopException delegate) {
      super(delegate.getFile());
      this.delegate = delegate;
    }
  }
}
