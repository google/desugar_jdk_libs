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

import android.os.Build.VERSION;
import java.nio.file.AccessDeniedException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.FileSystemLoopException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.NotLinkException;

/** Converts between {@link java.io.IOException} derived types. */
public final class IOExceptionConversions {

  public static java.io.IOException encodeChecked(java.io.IOException e) {
    return encode(e);
  }

  public static java.io.IOException decodeChecked(java.io.IOException e) {
    return decode(e);
  }

  private static java.io.IOException encode(java.io.IOException e) {
    if (e == null) {
      return null;
    }

    if (VERSION.SDK_INT < 26) {
      return e;
    }

    if (e instanceof AccessDeniedException) {
      return AccessDeniedExceptionConversions.encode((java.nio.file.AccessDeniedException) e);
    }
    if (e instanceof AtomicMoveNotSupportedException) {
      return AtomicMoveNotSupportedExceptionConversions.encode(
          (java.nio.file.AtomicMoveNotSupportedException) e);
    }
    if (e instanceof DirectoryNotEmptyException) {
      return DirectoryNotEmptyExceptionConversions.encode(
          (java.nio.file.DirectoryNotEmptyException) e);
    }
    if (e instanceof FileAlreadyExistsException) {
      return FileAlreadyExistsExceptionConversions.encode(
          (java.nio.file.FileAlreadyExistsException) e);
    }
    if (e instanceof FileSystemLoopException) {
      return FileSystemLoopExceptionConversions.encode((java.nio.file.FileSystemLoopException) e);
    }
    if (e instanceof NoSuchFileException) {
      return NoSuchFileExceptionConversions.encode((java.nio.file.NoSuchFileException) e);
    }
    if (e instanceof NotDirectoryException) {
      return NotDirectoryExceptionConversions.encode((java.nio.file.NotDirectoryException) e);
    }
    if (e instanceof NotLinkException) {
      return NotLinkExceptionConversions.encode((java.nio.file.NotLinkException) e);
    }
    if (e instanceof FileSystemException) {
      return FileSystemExceptionConversions.encode((java.nio.file.FileSystemException) e);
    }
    return e;
  }

  private static java.io.IOException decode(java.io.IOException e) {
    if (e == null) {
      return null;
    }
    if (e instanceof j$.nio.file.AccessDeniedException) {
      return AccessDeniedExceptionConversions.decode((j$.nio.file.AccessDeniedException) e);
    }
    if (e instanceof j$.nio.file.AtomicMoveNotSupportedException) {
      return AtomicMoveNotSupportedExceptionConversions.decode(
          (j$.nio.file.AtomicMoveNotSupportedException) e);
    }
    if (e instanceof j$.nio.file.DirectoryNotEmptyException) {
      return DirectoryNotEmptyExceptionConversions.decode(
          (j$.nio.file.DirectoryNotEmptyException) e);
    }
    if (e instanceof j$.nio.file.FileAlreadyExistsException) {
      return FileAlreadyExistsExceptionConversions.decode(
          (j$.nio.file.FileAlreadyExistsException) e);
    }
    if (e instanceof j$.nio.file.FileSystemLoopException) {
      return FileSystemLoopExceptionConversions.decode((j$.nio.file.FileSystemLoopException) e);
    }
    if (e instanceof j$.nio.file.NoSuchFileException) {
      return NoSuchFileExceptionConversions.decode((j$.nio.file.NoSuchFileException) e);
    }
    if (e instanceof j$.nio.file.NotDirectoryException) {
      return NotDirectoryExceptionConversions.decode((j$.nio.file.NotDirectoryException) e);
    }
    if (e instanceof j$.nio.file.NotLinkException) {
      return NotLinkExceptionConversions.decode((j$.nio.file.NotLinkException) e);
    }
    if (e instanceof j$.nio.file.FileSystemException) {
      return FileSystemExceptionConversions.decode((j$.nio.file.FileSystemException) e);
    }
    return e;
  }

  private IOExceptionConversions() {}
}
