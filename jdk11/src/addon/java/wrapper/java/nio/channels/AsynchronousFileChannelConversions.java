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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileLock;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import wrapper.java.nio.file.IOExceptionConversions;

/**
 * Type conversions between {@link java.nio.channels.AsynchronousFileChannel} and {@link
 * j$.nio.channels.AsynchronousFileChannel}.
 */
public final class AsynchronousFileChannelConversions {

  public static j$.nio.channels.AsynchronousFileChannel encode(
      java.nio.channels.AsynchronousFileChannel raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedAsynchronousFileChannel) {
      return ((DecodedAsynchronousFileChannel) raw).delegate;
    }
    return new EncodedAsynchronousFileChannel(raw);
  }

  public static java.nio.channels.AsynchronousFileChannel decode(
      j$.nio.channels.AsynchronousFileChannel encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedAsynchronousFileChannel) {
      return ((EncodedAsynchronousFileChannel) encoded).delegate;
    }
    return new DecodedAsynchronousFileChannel(encoded);
  }

  public static java.nio.channels.AsynchronousFileChannel open(
      Path file,
      Set<? extends OpenOption> options,
      ExecutorService executor,
      FileAttribute<?>... attrs)
      throws IOException {
    return java.nio.channels.AsynchronousFileChannel.open(file, options, executor, attrs);
  }

  public static java.nio.channels.AsynchronousFileChannel open(Path file, OpenOption... options)
      throws IOException {
    return java.nio.channels.AsynchronousFileChannel.open(file, options);
  }

  private AsynchronousFileChannelConversions() {}

  static class EncodedAsynchronousFileChannel extends j$.nio.channels.AsynchronousFileChannel {

    private final java.nio.channels.AsynchronousFileChannel delegate;

    public EncodedAsynchronousFileChannel(java.nio.channels.AsynchronousFileChannel delegate) {
      this.delegate = delegate;
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
    public j$.nio.channels.AsynchronousFileChannel truncate(long size) throws IOException {
      try {
        delegate.truncate(size);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
      return this;
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
    public <A> void lock(
        long position,
        long size,
        boolean shared,
        A attachment,
        j$.nio.channels.CompletionHandler<FileLock, ? super A> handler) {
      delegate.lock(
          position, size, shared, attachment, CompletionHandlerConversions.decode(handler));
    }

    @Override
    public Future<FileLock> lock(long position, long size, boolean shared) {
      return delegate.lock(position, size, shared);
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
    public <A> void read(
        ByteBuffer dst,
        long position,
        A attachment,
        j$.nio.channels.CompletionHandler<Integer, ? super A> handler) {
      delegate.read(dst, position, attachment, CompletionHandlerConversions.decode(handler));
    }

    @Override
    public Future<Integer> read(ByteBuffer dst, long position) {
      return delegate.read(dst, position);
    }

    @Override
    public <A> void write(
        ByteBuffer src,
        long position,
        A attachment,
        j$.nio.channels.CompletionHandler<Integer, ? super A> handler) {
      delegate.write(src, position, attachment, CompletionHandlerConversions.decode(handler));
    }

    @Override
    public Future<Integer> write(ByteBuffer src, long position) {
      return delegate.write(src, position);
    }

    @Override
    public void close() throws IOException {
      try {
        delegate.close();
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public boolean isOpen() {
      return delegate.isOpen();
    }
  }

  static class DecodedAsynchronousFileChannel extends java.nio.channels.AsynchronousFileChannel {

    private final j$.nio.channels.AsynchronousFileChannel delegate;

    public DecodedAsynchronousFileChannel(j$.nio.channels.AsynchronousFileChannel delegate) {
      this.delegate = delegate;
    }

    @Override
    public long size() throws IOException {
      return delegate.size();
    }

    @Override
    public java.nio.channels.AsynchronousFileChannel truncate(long size) throws IOException {
      delegate.truncate(size);
      return this;
    }

    @Override
    public void force(boolean metaData) throws IOException {
      delegate.force(metaData);
    }

    @Override
    public <A> void lock(
        long position,
        long size,
        boolean shared,
        A attachment,
        java.nio.channels.CompletionHandler<FileLock, ? super A> handler) {
      delegate.lock(
          position, size, shared, attachment, CompletionHandlerConversions.encode(handler));
    }

    @Override
    public Future<FileLock> lock(long position, long size, boolean shared) {
      return delegate.lock(position, size, shared);
    }

    @Override
    public FileLock tryLock(long position, long size, boolean shared) throws IOException {
      return delegate.tryLock(position, size, shared);
    }

    @Override
    public <A> void read(
        ByteBuffer dst,
        long position,
        A attachment,
        java.nio.channels.CompletionHandler<Integer, ? super A> handler) {
      delegate.read(dst, position, attachment, CompletionHandlerConversions.encode(handler));
    }

    @Override
    public Future<Integer> read(ByteBuffer dst, long position) {
      return delegate.read(dst, position);
    }

    @Override
    public <A> void write(
        ByteBuffer src,
        long position,
        A attachment,
        java.nio.channels.CompletionHandler<Integer, ? super A> handler) {
      delegate.write(src, position, attachment, CompletionHandlerConversions.encode(handler));
    }

    @Override
    public Future<Integer> write(ByteBuffer src, long position) {
      return delegate.write(src, position);
    }

    @Override
    public void close() throws IOException {
      delegate.close();
    }

    @Override
    public boolean isOpen() {
      return delegate.isOpen();
    }
  }
}
