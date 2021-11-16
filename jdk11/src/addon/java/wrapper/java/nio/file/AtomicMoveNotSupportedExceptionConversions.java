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
 * Type conversions between {@link java.nio.file.AtomicMoveNotSupportedException} and {@link
 * j$.nio.file.AtomicMoveNotSupportedException}.
 */
public final class AtomicMoveNotSupportedExceptionConversions {

  public static j$.nio.file.AtomicMoveNotSupportedException encode(
      java.nio.file.AtomicMoveNotSupportedException raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedAtomicMoveNotSupportedException) {
      return ((DecodedAtomicMoveNotSupportedException) raw).delegate;
    }
    return new EncodedAtomicMoveNotSupportedException(raw);
  }

  public static java.nio.file.AtomicMoveNotSupportedException decode(
      j$.nio.file.AtomicMoveNotSupportedException encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedAtomicMoveNotSupportedException) {
      return ((EncodedAtomicMoveNotSupportedException) encoded).delegate;
    }
    return new DecodedAtomicMoveNotSupportedException(encoded);
  }

  private AtomicMoveNotSupportedExceptionConversions() {}

  static class EncodedAtomicMoveNotSupportedException
      extends j$.nio.file.AtomicMoveNotSupportedException {
    private final java.nio.file.AtomicMoveNotSupportedException delegate;

    EncodedAtomicMoveNotSupportedException(java.nio.file.AtomicMoveNotSupportedException delegate) {
      super(delegate.getFile(), delegate.getOtherFile(), delegate.getReason());
      this.delegate = delegate;
    }
  }

  static class DecodedAtomicMoveNotSupportedException
      extends java.nio.file.AtomicMoveNotSupportedException {
    private final j$.nio.file.AtomicMoveNotSupportedException delegate;

    DecodedAtomicMoveNotSupportedException(j$.nio.file.AtomicMoveNotSupportedException delegate) {
      super(delegate.getFile(), delegate.getOtherFile(), delegate.getReason());
      this.delegate = delegate;
    }
  }
}
