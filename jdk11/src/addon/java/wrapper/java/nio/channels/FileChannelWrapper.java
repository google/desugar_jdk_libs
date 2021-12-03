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

package wrapper.java.nio.channels;

import android.os.Build.VERSION;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Set;

/** Wrapper class of {@link FileChannel} with extended methods. */
public final class FileChannelWrapper {

  public static FileChannel open(
      Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
    if (VERSION.SDK_INT >= 26) {
      return FileChannel.open(path, options, attrs);
    }
    // TODO(b/192427790): Add Support for SDK_INT < 26
    throw new UnsupportedOperationException();
  }

  public static FileChannel open(Path path, OpenOption... options) throws IOException {
    if (VERSION.SDK_INT >= 26) {
      return FileChannel.open(path, options);
    }
    // TODO(b/192427790): Add Support for SDK_INT < 26
    throw new UnsupportedOperationException();
  }

  private FileChannelWrapper() {}
}
