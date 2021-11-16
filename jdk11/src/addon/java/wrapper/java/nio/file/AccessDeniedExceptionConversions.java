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
 * Type conversions between {@link java.nio.file.AccessDeniedException} and {@link
 * j$.nio.file.AccessDeniedException}.
 */
public final class AccessDeniedExceptionConversions {

  public static j$.nio.file.AccessDeniedException encode(java.nio.file.AccessDeniedException raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedAccessDeniedException) {
      return ((DecodedAccessDeniedException) raw).delegate;
    }
    return new EncodedAccessDeniedException(raw);
  }

  public static java.nio.file.AccessDeniedException decode(
      j$.nio.file.AccessDeniedException encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedAccessDeniedException) {
      return ((EncodedAccessDeniedException) encoded).delegate;
    }
    return new DecodedAccessDeniedException(encoded);
  }

  private AccessDeniedExceptionConversions() {}

  static class EncodedAccessDeniedException extends j$.nio.file.AccessDeniedException {
    private final java.nio.file.AccessDeniedException delegate;

    EncodedAccessDeniedException(java.nio.file.AccessDeniedException delegate) {
      super(delegate.getFile(), delegate.getOtherFile(), delegate.getReason());
      this.delegate = delegate;
    }
  }

  static class DecodedAccessDeniedException extends java.nio.file.AccessDeniedException {
    private final j$.nio.file.AccessDeniedException delegate;

    DecodedAccessDeniedException(j$.nio.file.AccessDeniedException delegate) {
      super(delegate.getFile(), delegate.getOtherFile(), delegate.getReason());
      this.delegate = delegate;
    }
  }
}
