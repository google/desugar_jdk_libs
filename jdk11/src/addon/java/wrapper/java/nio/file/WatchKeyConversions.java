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

import java.util.List;

/** Type conversions between {@link java.nio.file.WatchKey} and {@link j$.nio.file.WatchKey}. */
public final class WatchKeyConversions {

  public static j$.nio.file.WatchKey encode(java.nio.file.WatchKey raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedWatchKey) {
      return ((DecodedWatchKey) raw).delegate;
    }
    return new EncodedWatchKey(raw);
  }

  public static java.nio.file.WatchKey decode(j$.nio.file.WatchKey encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedWatchKey) {
      return ((EncodedWatchKey) encoded).delegate;
    }
    return new DecodedWatchKey(encoded);
  }

  private WatchKeyConversions() {}

  static class EncodedWatchKey implements j$.nio.file.WatchKey {

    private final java.nio.file.WatchKey delegate;

    public EncodedWatchKey(java.nio.file.WatchKey delegate) {
      this.delegate = delegate;
    }

    @Override
    public boolean isValid() {
      return delegate.isValid();
    }

    @Override
    public List<j$.nio.file.WatchEvent<?>> pollEvents() {
      return WatchEventConversions.encode(delegate.pollEvents());
    }

    @Override
    public boolean reset() {
      return delegate.reset();
    }

    @Override
    public void cancel() {
      delegate.cancel();
    }

    @Override
    public j$.nio.file.Watchable watchable() {
      return WatchableConversions.encode(delegate.watchable());
    }
  }

  static class DecodedWatchKey implements java.nio.file.WatchKey {

    private final j$.nio.file.WatchKey delegate;

    public DecodedWatchKey(j$.nio.file.WatchKey delegate) {
      this.delegate = delegate;
    }

    @Override
    public boolean isValid() {
      return delegate.isValid();
    }

    @Override
    public List<java.nio.file.WatchEvent<?>> pollEvents() {
      return WatchEventConversions.decode(delegate.pollEvents());
    }

    @Override
    public boolean reset() {
      return delegate.reset();
    }

    @Override
    public void cancel() {
      delegate.cancel();
    }

    @Override
    public java.nio.file.Watchable watchable() {
      return WatchableConversions.decode(delegate.watchable());
    }
  }
}
