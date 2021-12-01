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

import android.os.Build.VERSION;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import j$.desugar.sun.nio.fs.DesugarDefaultFileSystemProvider;
import j$.nio.file.FileSystem;
import j$.nio.file.spi.FileSystemProvider;
import java.net.URI;
import wrapper.java.nio.file.spi.FileSystemProviderConversions;

/**
 * A hybrid file system provider adapter that delegates different implementations based on the
 * runtime environment.
 */
public final class HybridFileSystemProvider {
  private static final FileSystemProvider INSTANCE = getFileSystemProvider();
  private static final FileSystem FILE_SYSTEM_INSTANCE =
      INSTANCE.getFileSystem(URI.create("file:///"));

  private static FileSystemProvider getFileSystemProvider() {
    if (VERSION.SDK_INT >= 26) {
      return FileSystemProviderConversions.encode(
          java.nio.file.FileSystems.getDefault().provider());
    } else {
      // TODO(b/207004118): Fix the strict mode allowlisting.
      ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
      StrictMode.setThreadPolicy(new ThreadPolicy.Builder(threadPolicy).permitDiskReads().build());
      return DesugarDefaultFileSystemProvider.instance();
    }
  }

  private HybridFileSystemProvider() {}

  /** Returns the platform's default file system provider. */
  public static FileSystemProvider instance() {
    return INSTANCE;
  }

  /** Returns the platform's default file system. */
  public static FileSystem theFileSystem() {
    return FILE_SYSTEM_INSTANCE;
  }
}
