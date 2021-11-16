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
 * Type conversions between {@link java.nio.file.NoSuchFileException} and {@link
 * j$.nio.file.NoSuchFileException}.
 */
public final class NoSuchFileExceptionConversions {

  public static j$.nio.file.NoSuchFileException encode(java.nio.file.NoSuchFileException raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedNoSuchFileException) {
      return ((DecodedNoSuchFileException) raw).delegate;
    }
    return new EncodedNoSuchFileException(raw);
  }

  public static java.nio.file.NoSuchFileException decode(j$.nio.file.NoSuchFileException encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedNoSuchFileException) {
      return ((EncodedNoSuchFileException) encoded).delegate;
    }
    return new DecodedNoSuchFileException(encoded);
  }

  private NoSuchFileExceptionConversions() {}

  static class EncodedNoSuchFileException extends j$.nio.file.NoSuchFileException {
    private final java.nio.file.NoSuchFileException delegate;

    EncodedNoSuchFileException(java.nio.file.NoSuchFileException delegate) {
      super(delegate.getFile(), delegate.getOtherFile(), delegate.getReason());
      this.delegate = delegate;
    }
  }

  static class DecodedNoSuchFileException extends java.nio.file.NoSuchFileException {
    private final j$.nio.file.NoSuchFileException delegate;

    DecodedNoSuchFileException(j$.nio.file.NoSuchFileException delegate) {
      super(delegate.getFile(), delegate.getOtherFile(), delegate.getReason());
      this.delegate = delegate;
    }
  }
}
