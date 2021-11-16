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
 * Type conversions between {@link java.nio.file.NotDirectoryException} and {@link
 * j$.nio.file.NotDirectoryException}.
 */
public final class NotDirectoryExceptionConversions {

  public static j$.nio.file.NotDirectoryException encode(java.nio.file.NotDirectoryException raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedNotDirectoryException) {
      return ((DecodedNotDirectoryException) raw).delegate;
    }
    return new EncodedNotDirectoryException(raw);
  }

  public static java.nio.file.NotDirectoryException decode(
      j$.nio.file.NotDirectoryException encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedNotDirectoryException) {
      return ((EncodedNotDirectoryException) encoded).delegate;
    }
    return new DecodedNotDirectoryException(encoded);
  }

  private NotDirectoryExceptionConversions() {}

  static class EncodedNotDirectoryException extends j$.nio.file.NotDirectoryException {
    private final java.nio.file.NotDirectoryException delegate;

    EncodedNotDirectoryException(java.nio.file.NotDirectoryException delegate) {
      super(delegate.getFile());
      this.delegate = delegate;
    }
  }

  static class DecodedNotDirectoryException extends java.nio.file.NotDirectoryException {
    private final j$.nio.file.NotDirectoryException delegate;

    DecodedNotDirectoryException(j$.nio.file.NotDirectoryException delegate) {
      super(delegate.getFile());
      this.delegate = delegate;
    }
  }
}
