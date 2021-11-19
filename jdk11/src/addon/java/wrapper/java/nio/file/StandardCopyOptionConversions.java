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

package wrapper.java.nio.file;

/**
 * Type conversions between {@link java.nio.file.StandardCopyOption} and {@link
 * j$.nio.file.StandardCopyOption}.
 */
public final class StandardCopyOptionConversions {

  public static j$.nio.file.StandardCopyOption encode(java.nio.file.StandardCopyOption raw) {
    if (raw == null) {
      return null;
    }
    switch (raw) {
      case REPLACE_EXISTING:
        return j$.nio.file.StandardCopyOption.REPLACE_EXISTING;
      case COPY_ATTRIBUTES:
        return j$.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
      case ATOMIC_MOVE:
        return j$.nio.file.StandardCopyOption.ATOMIC_MOVE;
    }
    throw new AssertionError("Unexpected StandardOpenOption: " + raw);
  }

  public static java.nio.file.StandardCopyOption decode(j$.nio.file.StandardCopyOption encoded) {
    if (encoded == null) {
      return null;
    }
    switch (encoded) {
      case REPLACE_EXISTING:
        return java.nio.file.StandardCopyOption.REPLACE_EXISTING;
      case COPY_ATTRIBUTES:
        return java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
      case ATOMIC_MOVE:
        return java.nio.file.StandardCopyOption.ATOMIC_MOVE;
    }
    throw new AssertionError("Unexpected StandardOpenOption: " + encoded);
  }

  private StandardCopyOptionConversions() {}
}
