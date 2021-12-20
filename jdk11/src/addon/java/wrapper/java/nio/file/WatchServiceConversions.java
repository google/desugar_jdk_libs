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

package wrapper.java.nio.file;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Type conversions between {@link java.nio.file.WatchService} and {@link j$.nio.file.WatchService}.
 */
public final class WatchServiceConversions {

  public static j$.nio.file.WatchService encode(java.nio.file.WatchService raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedWatchService) {
      return ((DecodedWatchService) raw).delegate;
    }
    return new EncodedWatchService(raw);
  }

  public static java.nio.file.WatchService decode(j$.nio.file.WatchService encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedWatchService) {
      return ((EncodedWatchService) encoded).delegate;
    }
    return new DecodedWatchService(encoded);
  }

  private WatchServiceConversions() {}

  static class EncodedWatchService implements j$.nio.file.WatchService {

    private final java.nio.file.WatchService delegate;

    public EncodedWatchService(java.nio.file.WatchService delegate) {
      this.delegate = delegate;
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
    public j$.nio.file.WatchKey poll() {
      return WatchKeyConversions.encode(delegate.poll());
    }

    @Override
    public j$.nio.file.WatchKey poll(long timeout, TimeUnit unit) {
      try {
        return WatchKeyConversions.encode(delegate.poll(timeout, unit));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IllegalStateException(e);
      }
    }

    @Override
    public j$.nio.file.WatchKey take() {
      try {
        return WatchKeyConversions.encode(delegate.take());
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
    }
  }

  static class DecodedWatchService implements java.nio.file.WatchService {

    private final j$.nio.file.WatchService delegate;

    public DecodedWatchService(j$.nio.file.WatchService delegate) {
      this.delegate = delegate;
    }

    @Override
    public void close() throws IOException {
      delegate.close();
    }

    @Override
    public java.nio.file.WatchKey poll() {
      return WatchKeyConversions.decode(delegate.poll());
    }

    @Override
    public java.nio.file.WatchKey poll(long timeout, TimeUnit unit) throws InterruptedException {
      return WatchKeyConversions.decode(delegate.poll(timeout, unit));
    }

    @Override
    public java.nio.file.WatchKey take() throws InterruptedException {
      return WatchKeyConversions.decode(delegate.take());
    }
  }
}
