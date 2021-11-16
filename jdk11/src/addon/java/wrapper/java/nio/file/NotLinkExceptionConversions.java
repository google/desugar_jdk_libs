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
 * Type conversions between {@link java.nio.file.NotLinkException} and {@link
 * j$.nio.file.NotLinkException}.
 */
public final class NotLinkExceptionConversions {

  public static j$.nio.file.NotLinkException encode(java.nio.file.NotLinkException raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedNotLinkException) {
      return ((DecodedNotLinkException) raw).delegate;
    }
    return new EncodedNotLinkException(raw);
  }

  public static java.nio.file.NotLinkException decode(j$.nio.file.NotLinkException encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedNotLinkException) {
      return ((EncodedNotLinkException) encoded).delegate;
    }
    return new DecodedNotLinkException(encoded);
  }

  private NotLinkExceptionConversions() {}

  static class EncodedNotLinkException extends j$.nio.file.NotLinkException {
    private final java.nio.file.NotLinkException delegate;

    EncodedNotLinkException(java.nio.file.NotLinkException delegate) {
      super(delegate.getFile(), delegate.getOtherFile(), delegate.getReason());
      this.delegate = delegate;
    }
  }

  static class DecodedNotLinkException extends java.nio.file.NotLinkException {
    private final j$.nio.file.NotLinkException delegate;

    DecodedNotLinkException(j$.nio.file.NotLinkException delegate) {
      super(delegate.getFile(), delegate.getOtherFile(), delegate.getReason());
      this.delegate = delegate;
    }
  }
}
