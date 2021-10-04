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

package desugar.sun.nio.fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

class DesugarBasicFileAttributes implements BasicFileAttributes {

  private final FileTime lastModifiedTime;
  private final FileTime lastAccessTime;
  private final FileTime creationTime;

  private final boolean isRegularFile;
  private final boolean isDirectory;
  private final boolean isSymbolicLink;
  private final boolean isOther;

  private final long size;
  private final Object fileKey;

  public static DesugarBasicFileAttributes create(File file) {
    FileTime lastModifiedTime = FileTime.from(file.lastModified(), TimeUnit.MILLISECONDS);
    boolean isRegularFile = file.isFile();
    boolean isDirectory = file.isDirectory();
    boolean isSymbolicLink = isSymlink(file);
    return new DesugarBasicFileAttributes(
        lastModifiedTime,
        lastModifiedTime,
        lastModifiedTime,
        isRegularFile,
        isDirectory,
        isSymbolicLink,
        !(isRegularFile || isDirectory || isSymbolicLink),
        file.length(),
        // TODO(b/192427790): Replace the file key with a UnixFileAttributes-like implementation,
        // i.e. a pair of Device Id (st_dev) and file serial number (st_ino), or find an equivalent
        // alternative way to distinguish unique files on a device.
        /* fileKey= */ file.hashCode());
  }

  public DesugarBasicFileAttributes(
      FileTime lastModifiedTime,
      FileTime lastAccessTime,
      FileTime creationTime,
      boolean isRegularFile,
      boolean isDirectory,
      boolean isSymbolicLink,
      boolean isOther,
      long size,
      Object fileKey) {
    this.lastModifiedTime = lastModifiedTime;
    this.lastAccessTime = lastAccessTime;
    this.creationTime = creationTime;
    this.isRegularFile = isRegularFile;
    this.isDirectory = isDirectory;
    this.isSymbolicLink = isSymbolicLink;
    this.isOther = isOther;
    this.size = size;
    this.fileKey = fileKey;
  }

  @Override
  public FileTime lastModifiedTime() {
    return lastModifiedTime;
  }

  @Override
  public FileTime lastAccessTime() {
    return lastAccessTime;
  }

  @Override
  public FileTime creationTime() {
    return creationTime;
  }

  @Override
  public boolean isRegularFile() {
    return isRegularFile;
  }

  @Override
  public boolean isDirectory() {
    return isDirectory;
  }

  @Override
  public boolean isSymbolicLink() {
    return isSymbolicLink;
  }

  @Override
  public boolean isOther() {
    return isOther;
  }

  @Override
  public long size() {
    return size;
  }

  @Override
  public Object fileKey() {
    return fileKey;
  }

  public static boolean isSymlink(File file) {
    if (file == null) {
      throw new NullPointerException("File must not be null");
    }
    File canonicalFile;
    try {
      if (file.getParent() == null) {
        canonicalFile = file;
      } else {
        File canonicalDir = file.getParentFile().getCanonicalFile();
        canonicalFile = new File(canonicalDir, file.getName());
      }
      return !canonicalFile.getCanonicalFile().equals(canonicalFile.getAbsoluteFile());
    } catch (IOException e) {
      return false;
    }
  }
}
