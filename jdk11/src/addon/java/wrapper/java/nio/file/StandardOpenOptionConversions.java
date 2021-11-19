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
 * Type conversions between {@link java.nio.file.StandardOpenOption} and {@link
 * j$.nio.file.StandardOpenOption}.
 */
public final class StandardOpenOptionConversions {

  public static j$.nio.file.StandardOpenOption encode(java.nio.file.StandardOpenOption raw) {
    if (raw == null) {
      return null;
    }
    switch (raw) {
      case READ:
        return j$.nio.file.StandardOpenOption.READ;
      case WRITE:
        return j$.nio.file.StandardOpenOption.WRITE;
      case APPEND:
        return j$.nio.file.StandardOpenOption.APPEND;
      case TRUNCATE_EXISTING:
        return j$.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
      case CREATE:
        return j$.nio.file.StandardOpenOption.CREATE;
      case CREATE_NEW:
        return j$.nio.file.StandardOpenOption.CREATE_NEW;
      case DELETE_ON_CLOSE:
        return j$.nio.file.StandardOpenOption.DELETE_ON_CLOSE;
      case SPARSE:
        return j$.nio.file.StandardOpenOption.SPARSE;
      case SYNC:
        return j$.nio.file.StandardOpenOption.SYNC;
      case DSYNC:
        return j$.nio.file.StandardOpenOption.DSYNC;
    }
    throw new AssertionError("Unexpected StandardOpenOption: " + raw);
  }

  public static java.nio.file.StandardOpenOption decode(j$.nio.file.StandardOpenOption encoded) {
    if (encoded == null) {
      return null;
    }
    switch (encoded) {
      case READ:
        return java.nio.file.StandardOpenOption.READ;
      case WRITE:
        return java.nio.file.StandardOpenOption.WRITE;
      case APPEND:
        return java.nio.file.StandardOpenOption.APPEND;
      case TRUNCATE_EXISTING:
        return java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
      case CREATE:
        return java.nio.file.StandardOpenOption.CREATE;
      case CREATE_NEW:
        return java.nio.file.StandardOpenOption.CREATE_NEW;
      case DELETE_ON_CLOSE:
        return java.nio.file.StandardOpenOption.DELETE_ON_CLOSE;
      case SPARSE:
        return java.nio.file.StandardOpenOption.SPARSE;
      case SYNC:
        return java.nio.file.StandardOpenOption.SYNC;
      case DSYNC:
        return java.nio.file.StandardOpenOption.DSYNC;
    }
    throw new AssertionError("Unexpected StandardOpenOption: " + encoded);
  }

  private StandardOpenOptionConversions() {}
}
