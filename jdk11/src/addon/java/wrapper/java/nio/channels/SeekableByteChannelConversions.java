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
import wrapper.java.nio.file.IOExceptionConversions;

/**
 * Type conversions between {@link java.nio.channels.SeekableByteChannel} and {@link
 * j$.nio.channels.SeekableByteChannel}.
 */
public final class SeekableByteChannelConversions {

  public static j$.nio.channels.SeekableByteChannel encode(
      java.nio.channels.SeekableByteChannel raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedSeekableByteChannel) {
      return ((DecodedSeekableByteChannel) raw).delegate;
    }
    return new EncodedSeekableByteChannel(raw);
  }

  public static java.nio.channels.SeekableByteChannel decode(
      j$.nio.channels.SeekableByteChannel encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedSeekableByteChannel) {
      return ((EncodedSeekableByteChannel) encoded).delegate;
    }
    return new DecodedSeekableByteChannel(encoded);
  }

  private SeekableByteChannelConversions() {}

  static class EncodedSeekableByteChannel implements j$.nio.channels.SeekableByteChannel {

    private final java.nio.channels.SeekableByteChannel delegate;

    public EncodedSeekableByteChannel(java.nio.channels.SeekableByteChannel delegate) {
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
    public int write(ByteBuffer src) throws IOException {
      try {
        return delegate.write(src);
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
    public j$.nio.channels.SeekableByteChannel position(long newPosition) throws IOException {
      try {
        return encode(delegate.position(newPosition));
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
    public j$.nio.channels.SeekableByteChannel truncate(long size) throws IOException {
      try {
        return encode(delegate.truncate(size));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public boolean isOpen() {
      return delegate.isOpen();
    }

    @Override
    public void close() throws IOException {
      delegate.close();
    }
  }

  static class DecodedSeekableByteChannel implements java.nio.channels.SeekableByteChannel {

    private final j$.nio.channels.SeekableByteChannel delegate;

    public DecodedSeekableByteChannel(j$.nio.channels.SeekableByteChannel delegate) {
      this.delegate = delegate;
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
      return delegate.read(dst);
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
      return delegate.write(src);
    }

    @Override
    public long position() throws IOException {
      return delegate.position();
    }

    @Override
    public java.nio.channels.SeekableByteChannel position(long newPosition) throws IOException {
      return decode(delegate.position(newPosition));
    }

    @Override
    public long size() throws IOException {
      return delegate.size();
    }

    @Override
    public java.nio.channels.SeekableByteChannel truncate(long size) throws IOException {
      return decode(delegate.truncate(size));
    }

    @Override
    public boolean isOpen() {
      return delegate.isOpen();
    }

    @Override
    public void close() throws IOException {
      delegate.close();
    }
  }
}
