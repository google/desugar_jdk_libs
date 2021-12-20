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

package wrapper.java.nio.file.spi;

import java.io.IOException;
import wrapper.java.nio.file.IOExceptionConversions;
import wrapper.java.nio.file.PathConversions;

/**
 * Type conversions between {@link java.nio.file.spi.FileTypeDetector} and {@link
 * j$.nio.file.spi.FileTypeDetector}.
 */
public final class FileTypeDetectorConversions {

  public static j$.nio.file.spi.FileTypeDetector encode(java.nio.file.spi.FileTypeDetector raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedFileTypeDetector) {
      return ((DecodedFileTypeDetector) raw).delegate;
    }
    return new EncodedFileTypeDetector(raw);
  }

  public static java.nio.file.spi.FileTypeDetector decode(
      j$.nio.file.spi.FileTypeDetector encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedFileTypeDetector) {
      return ((EncodedFileTypeDetector) encoded).delegate;
    }
    return new DecodedFileTypeDetector(encoded);
  }

  private FileTypeDetectorConversions() {}

  static class EncodedFileTypeDetector extends j$.nio.file.spi.FileTypeDetector {

    private final java.nio.file.spi.FileTypeDetector delegate;

    public EncodedFileTypeDetector(java.nio.file.spi.FileTypeDetector delegate) {
      this.delegate = delegate;
    }

    @Override
    public String probeContentType(j$.nio.file.Path path) throws IOException {
      try {
        return delegate.probeContentType(PathConversions.decode(path));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }
  }

  static class DecodedFileTypeDetector extends java.nio.file.spi.FileTypeDetector {

    private final j$.nio.file.spi.FileTypeDetector delegate;

    public DecodedFileTypeDetector(j$.nio.file.spi.FileTypeDetector delegate) {
      this.delegate = delegate;
    }

    @Override
    public String probeContentType(java.nio.file.Path path) throws IOException {
      return delegate.probeContentType(PathConversions.encode(path));
    }
  }
}
