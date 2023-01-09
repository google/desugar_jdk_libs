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
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Set;

/** Linux implementation of {@link FileChannel} for desugar support. */
public class DesugarFileChannel {

  /**
   * All FileChannel creation go through the FileSystemProvider which then comes here if the Api is
   * strictly below 26, and to the plaform FileSystemProvider if the Api is above or equal to 26.
   *
   * <p>Below Api 26 there is no way to create a FileChannel, so we create instead an emulated
   * version using RandomAccessFile which tries, with a best effort, to support all settings.
   *
   * <p>The FileAttributes are ignored.
   */
  public static FileChannel openEmulatedFileChannel(
      Path path, Set<? extends OpenOption> openOptions, FileAttribute<?>... attrs)
      throws IOException {

    validateOpenOptions(path, openOptions);

    RandomAccessFile randomAccessFile =
        new RandomAccessFile(path.toFile(), getFileAccessModeText(openOptions));
    // TRUNCATE_EXISTING is ignored if the file is not writable.
    // TRUNCATE_EXISTING is not compatible with APPEND, so we just need to check for WRITE.
    if (openOptions.contains(StandardOpenOption.TRUNCATE_EXISTING)
        && openOptions.contains(StandardOpenOption.WRITE)) {
      randomAccessFile.setLength(0);
    }

    if (!openOptions.contains(StandardOpenOption.APPEND)
        && !openOptions.contains(StandardOpenOption.DELETE_ON_CLOSE)) {
      // This one may be retargeted, below 24, to support SeekableByteChannel.
      return randomAccessFile.getChannel();
    }

    return WrappedFileChannel.withExtraOptions(randomAccessFile.getChannel(), openOptions, path);
  }

  private static void validateOpenOptions(Path path, Set<? extends OpenOption> openOptions)
      throws IOException {
    for (OpenOption openOption : openOptions) {
      if (openOption == null) {
        throw new NullPointerException();
      }
    }
    if (Files.exists(path)) {
      if (openOptions.contains(StandardOpenOption.CREATE_NEW)
          && openOptions.contains(StandardOpenOption.WRITE)) {
        throw new FileAlreadyExistsException(path.toString());
      }
    } else {
      if (!(openOptions.contains(StandardOpenOption.CREATE)
          || openOptions.contains(StandardOpenOption.CREATE_NEW))) {
        throw new NoSuchFileException(path.toString());
      }
    }
    // Validations that resemble sun.nio.fs.UnixChannelFactory#newFileChannel.
    if (openOptions.contains(StandardOpenOption.READ)
        && openOptions.contains(StandardOpenOption.APPEND)) {
      throw new IllegalArgumentException("READ + APPEND not allowed");
    }
    if (openOptions.contains(StandardOpenOption.APPEND)
        && openOptions.contains(StandardOpenOption.TRUNCATE_EXISTING)) {
      throw new IllegalArgumentException("APPEND + TRUNCATE_EXISTING not allowed");
    }
  }

  private static String getFileAccessModeText(Set<? extends OpenOption> options) {
    if (!options.contains(StandardOpenOption.WRITE)
        && !options.contains(StandardOpenOption.APPEND)) {
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

  public static FileChannel wrap(FileChannel raw) {
    return WrappedFileChannel.wrap(raw);
  }

  /**
   * All FileChannels below 24 are wrapped to support the new interface SeekableByteChannel.
   * FileChannels between 24 and 26 are wrapped only to improve the emulation of program opened
   * FileChannels, especially with the append and delete on close options.
   */
  static class WrappedFileChannel extends FileChannel implements SeekableByteChannel {

    final FileChannel delegate;
    final boolean deleteOnClose;
    final boolean appendMode;
    final Path path;

    public static FileChannel wrap(FileChannel channel) {
      if (channel instanceof WrappedFileChannel) {
        return channel;
      }
      return new WrappedFileChannel(channel, false, false, null);
    }

    public static FileChannel withExtraOptions(
        FileChannel channel, Set<? extends OpenOption> options, Path path) {
      FileChannel raw =
          channel instanceof WrappedFileChannel ? ((WrappedFileChannel) channel).delegate : channel;
      return new WrappedFileChannel(
          raw,
          options.contains(StandardOpenOption.DELETE_ON_CLOSE),
          options.contains(StandardOpenOption.APPEND),
          path);
    }

    private WrappedFileChannel(
        FileChannel delegate, boolean deleteOnClose, boolean appendMode, Path path) {
      this.delegate = delegate;
      this.deleteOnClose = deleteOnClose;
      this.appendMode = appendMode;
      this.path = deleteOnClose ? path : null;
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
      return delegate.read(dst);
    }

    @Override
    public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
      return delegate.read(dsts, offset, length);
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
      if (appendMode) {
        return delegate.write(src, size());
      }
      return delegate.write(src);
    }

    @Override
    public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
      return delegate.write(srcs, offset, length);
    }

    @Override
    public long position() throws IOException {
      return delegate.position();
    }

    @Override
    public FileChannel position(long newPosition) throws IOException {
      return WrappedFileChannel.wrap(delegate.position(newPosition));
    }

    @Override
    public long size() throws IOException {
      return delegate.size();
    }

    @Override
    public FileChannel truncate(long size) throws IOException {
      return WrappedFileChannel.wrap(delegate.truncate(size));
    }

    @Override
    public void force(boolean metaData) throws IOException {
      delegate.force(metaData);
    }

    @Override
    public long transferTo(long position, long count, WritableByteChannel target)
        throws IOException {
      return delegate.transferTo(position, count, target);
    }

    @Override
    public long transferFrom(ReadableByteChannel src, long position, long count)
        throws IOException {
      return delegate.transferFrom(src, position, count);
    }

    @Override
    public int read(ByteBuffer dst, long position) throws IOException {
      return delegate.read(dst, position);
    }

    @Override
    public int write(ByteBuffer src, long position) throws IOException {
      return delegate.write(src, position);
    }

    @Override
    public MappedByteBuffer map(MapMode mode, long position, long size) throws IOException {
      return delegate.map(mode, position, size);
    }

    @Override
    public FileLock lock(long position, long size, boolean shared) throws IOException {
      return wrapLock(delegate.lock(position, size, shared));
    }

    @Override
    public FileLock tryLock(long position, long size, boolean shared) throws IOException {
      return wrapLock(delegate.tryLock(position, size, shared));
    }

    private FileLock wrapLock(FileLock lock) {
      if (lock == null) {
        return null;
      }
      return new WrappedFileChannelFileLock(lock, this);
    }

    @Override
    public void implCloseChannel() throws IOException {
      // We cannot call the protected method, this should be effectively equivalent.
      delegate.close();
      if (deleteOnClose) {
        Files.deleteIfExists(path);
      }
    }
  }

  /**
   * The FileLock state is final and duplicated in the wrapper, besides the FileChannel where the
   * wrapped file channel is used. All methods in FileLock, even channel(), use the duplicated and
   * corrected state. Only 2 methods require to dispatch to the delegate which is effectively
   * holding the lock.
   */
  static class WrappedFileChannelFileLock extends FileLock {

    private final FileLock delegate;

    WrappedFileChannelFileLock(FileLock delegate, WrappedFileChannel wrappedFileChannel) {
      super(wrappedFileChannel, delegate.position(), delegate.size(), delegate.isShared());
      this.delegate = delegate;
    }

    @Override
    public boolean isValid() {
      return delegate.isValid();
    }

    @Override
    public void release() throws IOException {
      delegate.release();
    }
  }
}
