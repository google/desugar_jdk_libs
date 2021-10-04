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

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

/** Linux implementation of {@link SeekableByteChannel} for desugar support. */
public class DesugarSeekableByteChannel implements SeekableByteChannel {

  private final FileChannel fileChannel;

  private final Set<? extends OpenOption> openOptions;

  public static DesugarSeekableByteChannel create(Path path, Set<? extends OpenOption> openOptions)
      throws IOException {
    RandomAccessFile randomAccessFile =
        new RandomAccessFile(path.toFile(), getFileAccessModeText(openOptions));
    if (openOptions.contains(StandardOpenOption.TRUNCATE_EXISTING)) {
      randomAccessFile.setLength(0);
    }

    return new DesugarSeekableByteChannel(randomAccessFile.getChannel(), openOptions);
  }

  private static String getFileAccessModeText(Set<? extends OpenOption> options) {
    if (!options.contains(StandardOpenOption.WRITE)) {
      return "r";
    }
    if (options.contains(StandardOpenOption.SYNC)) {
      return "rws";
    }
    if (options.contains(StandardOpenOption.DSYNC)) {
      return "rwd";
    }
    return "rw";
  }

  private DesugarSeekableByteChannel(
      FileChannel fileChannel, Set<? extends OpenOption> openOptions) {
    this.fileChannel = fileChannel;
    this.openOptions = openOptions;
  }

  public FileChannel getFileChannel() {
    return fileChannel;
  }

  @Override
  public int read(ByteBuffer dst) throws IOException {
    return fileChannel.read(dst);
  }

  @Override
  public int write(ByteBuffer src) throws IOException {
    if (openOptions.contains(StandardOpenOption.APPEND)) {
      return fileChannel.write(src, size());
    }
    return fileChannel.write(src);
  }

  @Override
  public long position() throws IOException {
    return fileChannel.position();
  }

  @Override
  public SeekableByteChannel position(long newPosition) throws IOException {
    return fileChannel.position(newPosition);
  }

  @Override
  public long size() throws IOException {
    return fileChannel.size();
  }

  @Override
  public SeekableByteChannel truncate(long size) throws IOException {
    return fileChannel.truncate(size);
  }

  @Override
  public boolean isOpen() {
    return fileChannel.isOpen();
  }

  @Override
  public void close() throws IOException {
    fileChannel.close();
  }
}
