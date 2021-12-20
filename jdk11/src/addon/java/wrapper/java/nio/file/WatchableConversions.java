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

/** Type conversions between {@link java.nio.file.Watchable} and {@link j$.nio.file.Watchable}. */
public final class WatchableConversions {

  public static j$.nio.file.Watchable encode(java.nio.file.Watchable raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedWatchable) {
      return ((DecodedWatchable) raw).delegate;
    }
    return new EncodedWatchable(raw);
  }

  public static java.nio.file.Watchable decode(j$.nio.file.Watchable encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedWatchable) {
      return ((EncodedWatchable) encoded).delegate;
    }
    return new DecodedWatchable(encoded);
  }

  private WatchableConversions() {}

  static class EncodedWatchable implements j$.nio.file.Watchable {

    private final java.nio.file.Watchable delegate;

    public EncodedWatchable(java.nio.file.Watchable delegate) {
      this.delegate = delegate;
    }

    @Override
    public j$.nio.file.WatchKey register(
        j$.nio.file.WatchService watcher,
        j$.nio.file.WatchEvent.Kind<?>[] events,
        j$.nio.file.WatchEvent.Modifier... modifiers)
        throws IOException {
      try {
        return WatchKeyConversions.encode(
            delegate.register(
                WatchServiceConversions.decode(watcher),
                WatchEventKindConversions.decode(events),
                WatchEventModifierConversions.decode(modifiers)));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public j$.nio.file.WatchKey register(
        j$.nio.file.WatchService watcher, j$.nio.file.WatchEvent.Kind<?>... events)
        throws IOException {
      try {
        return WatchKeyConversions.encode(
            delegate.register(
                WatchServiceConversions.decode(watcher), WatchEventKindConversions.decode(events)));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }
  }

  static class DecodedWatchable implements java.nio.file.Watchable {

    private final j$.nio.file.Watchable delegate;

    public DecodedWatchable(j$.nio.file.Watchable delegate) {
      this.delegate = delegate;
    }

    @Override
    public java.nio.file.WatchKey register(
        java.nio.file.WatchService watcher,
        java.nio.file.WatchEvent.Kind<?>[] events,
        java.nio.file.WatchEvent.Modifier... modifiers)
        throws IOException {
      return WatchKeyConversions.decode(
          delegate.register(
              WatchServiceConversions.encode(watcher),
              WatchEventKindConversions.encode(events),
              WatchEventModifierConversions.encode(modifiers)));
    }

    @Override
    public java.nio.file.WatchKey register(
        java.nio.file.WatchService watcher, java.nio.file.WatchEvent.Kind<?>... events)
        throws IOException {
      return WatchKeyConversions.decode(
          delegate.register(
              WatchServiceConversions.encode(watcher), WatchEventKindConversions.encode(events)));
    }
  }
}
