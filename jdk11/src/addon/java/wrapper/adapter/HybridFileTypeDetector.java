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

package wrapper.adapter;

import j$.desugar.sun.nio.fs.DesugarDefaultFileTypeDetector;
import j$.nio.file.spi.FileTypeDetector;
import java.io.IOException;
import java.nio.file.Path;
import wrapper.java.nio.file.spi.FileTypeDetectorConversions;

/**
 * A hybrid file type detector adapter that delegates different implementations based on the runtime
 * environment.
 */
public final class HybridFileTypeDetector {
  private HybridFileTypeDetector() {}

  public static FileTypeDetector create() {
    try {
      return FileTypeDetectorConversions.encode(new PlatformFileTypeDetector());
    } catch (NoClassDefFoundError e) {
      return DesugarDefaultFileTypeDetector.create();
    }
  }

  static class PlatformFileTypeDetector extends java.nio.file.spi.FileTypeDetector {
    @Override
    public String probeContentType(Path path) throws IOException {
      return java.nio.file.Files.probeContentType(path);
    }
  }
}
