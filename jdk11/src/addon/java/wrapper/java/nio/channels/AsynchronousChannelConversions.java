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

/**
 * Type conversions between {@link java.nio.channels.AsynchronousChannel} and {@link
 * j$.nio.channels.AsynchronousChannel}.
 */
public final class AsynchronousChannelConversions {

  public static j$.nio.channels.AsynchronousChannel encode(
      java.nio.channels.AsynchronousChannel raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedAsynchronousChannel) {
      return ((DecodedAsynchronousChannel) raw).delegate;
    }
    return new EncodedAsynchronousChannel(raw);
  }

  public static java.nio.channels.AsynchronousChannel decode(
      j$.nio.channels.AsynchronousChannel encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedAsynchronousChannel) {
      return ((EncodedAsynchronousChannel) encoded).delegate;
    }
    return new DecodedAsynchronousChannel(encoded);
  }

  private AsynchronousChannelConversions() {}

  static class EncodedAsynchronousChannel implements j$.nio.channels.AsynchronousChannel {

    private final java.nio.channels.AsynchronousChannel delegate;

    public EncodedAsynchronousChannel(java.nio.channels.AsynchronousChannel delegate) {
      this.delegate = delegate;
    }

    @Override
    public void close() {
      try {
        delegate.close();
      } catch (IOException e) {
        throw new UnsupportedOperationException(e);
      }
    }

    @Override
    public boolean isOpen() {
      return delegate.isOpen();
    }
  }

  static class DecodedAsynchronousChannel implements java.nio.channels.AsynchronousChannel {

    private final j$.nio.channels.AsynchronousChannel delegate;

    public DecodedAsynchronousChannel(j$.nio.channels.AsynchronousChannel delegate) {
      this.delegate = delegate;
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
