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

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

class DesugarBasicFileAttributeView extends DesugarAbstractBasicFileAttributeView {

  private final Path path;

  public DesugarBasicFileAttributeView(Path path) {
    this.path = path;
  }

  @Override
  public BasicFileAttributes readAttributes() throws IOException {
    return DesugarBasicFileAttributes.create(path.toFile());
  }

  @Override
  public void setTimes(FileTime lastModifiedTime, FileTime lastAccessTime, FileTime createTime)
      throws IOException {
    File file = path.toFile();
    file.setLastModified(lastModifiedTime.to(MILLISECONDS));
  }
}
