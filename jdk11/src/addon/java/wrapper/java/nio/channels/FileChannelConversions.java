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

package wrapper.java.nio.channels;

import android.os.Build.VERSION;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import wrapper.java.nio.file.IOExceptionConversions;

/**
 * Type conversions of {@link java.nio.channels.FileChannel} with {@link
 * j$.nio.channels.SeekableByteChannel} support.
 */
public final class FileChannelConversions {

  public static FileChannel encode(FileChannel raw) {
    if (raw == null) {
      return null;
    }
    if (VERSION.SDK_INT < 24) {
      return raw;
    }
    return new EncodedFileChannel(raw);
  }

  private FileChannelConversions() {}

  static class EncodedFileChannel extends FileChannel
      implements j$.nio.channels.SeekableByteChannel {

    private final FileChannel delegate;

    private EncodedFileChannel(FileChannel delegate) {
      this.delegate = delegate;
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
      try {
        return delegate.read(dst);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
      try {
        return delegate.read(dsts, offset, length);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
      try {
        return delegate.write(src);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
      try {
        return delegate.write(srcs, offset, length);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public long position() throws IOException {
      try {
        return delegate.position();
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public EncodedFileChannel position(long newPosition) throws IOException {
      try {
        return (EncodedFileChannel) encode(delegate.position(newPosition));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public long size() throws IOException {
      try {
        return delegate.size();
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public EncodedFileChannel truncate(long size) throws IOException {
      try {
        return (EncodedFileChannel) encode(delegate.truncate(size));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void force(boolean metaData) throws IOException {
      try {
        delegate.force(metaData);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public long transferTo(long position, long count, WritableByteChannel target)
        throws IOException {
      try {
        return delegate.transferTo(position, count, target);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public long transferFrom(ReadableByteChannel src, long position, long count)
        throws IOException {
      try {
        return delegate.transferFrom(src, position, count);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public int read(ByteBuffer dst, long position) throws IOException {
      try {
        return delegate.read(dst, position);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public int write(ByteBuffer src, long position) throws IOException {
      try {
        return delegate.write(src, position);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public MappedByteBuffer map(MapMode mode, long position, long size) throws IOException {
      try {
        return delegate.map(mode, position, size);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public FileLock lock(long position, long size, boolean shared) throws IOException {
      try {
        return delegate.lock(position, size, shared);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public FileLock tryLock(long position, long size, boolean shared) throws IOException {
      try {
        return delegate.tryLock(position, size, shared);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void implCloseChannel() throws IOException {
      try {
        // Workaround protected method.
        Method implCloseChannel =
            AbstractInterruptibleChannel.class.getDeclaredMethod("implCloseChannel");
        implCloseChannel.setAccessible(true);
        implCloseChannel.invoke(delegate);
      } catch (ReflectiveOperationException e) {
        Throwable cause = e.getCause();
        if (cause instanceof IOException) {
          throw IOExceptionConversions.encodeChecked((IOException) cause);
        }
        throw new AssertionError(e);
      }
    }
  }
}
