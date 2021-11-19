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

package wrapper.java.nio.file;

import java.util.ArrayList;
import java.util.List;

/** Type conversions between {@link java.nio.file.WatchEvent} and {@link j$.nio.file.WatchEvent}. */
public final class WatchEventConversions {

  public static <T> j$.nio.file.WatchEvent<T> encode(java.nio.file.WatchEvent<T> raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedWatchEvent<?>) {
      return ((DecodedWatchEvent<T>) raw).delegate;
    }
    return new EncodedWatchEvent<>(raw);
  }

  public static List<j$.nio.file.WatchEvent<?>> encode(List<java.nio.file.WatchEvent<?>> raw) {
    if (raw == null) {
      return null;
    }
    var results = new ArrayList<j$.nio.file.WatchEvent<?>>();
    for (var event : raw) {
      results.add(encode(event));
    }
    return results;
  }

  public static <T> java.nio.file.WatchEvent<T> decode(j$.nio.file.WatchEvent<T> encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedWatchEvent<?>) {
      return ((EncodedWatchEvent<T>) encoded).delegate;
    }
    return new DecodedWatchEvent<>(encoded);
  }

  public static List<java.nio.file.WatchEvent<?>> decode(List<j$.nio.file.WatchEvent<?>> encoded) {
    if (encoded == null) {
      return null;
    }
    var results = new ArrayList<java.nio.file.WatchEvent<?>>();
    for (var event : encoded) {
      results.add(decode(event));
    }
    return results;
  }

  private WatchEventConversions() {}

  static class EncodedWatchEvent<T> implements j$.nio.file.WatchEvent<T> {

    private final java.nio.file.WatchEvent<T> delegate;

    public EncodedWatchEvent(java.nio.file.WatchEvent<T> delegate) {
      this.delegate = delegate;
    }

    @Override
    public j$.nio.file.WatchEvent.Kind<T> kind() {
      return WatchEventKindConversions.encode(delegate.kind());
    }

    @Override
    public int count() {
      return delegate.count();
    }

    @Override
    public T context() {
      return delegate.context();
    }
  }

  static class DecodedWatchEvent<T> implements java.nio.file.WatchEvent<T> {

    private final j$.nio.file.WatchEvent<T> delegate;

    public DecodedWatchEvent(j$.nio.file.WatchEvent<T> delegate) {
      this.delegate = delegate;
    }

    @Override
    public java.nio.file.WatchEvent.Kind<T> kind() {
      return WatchEventKindConversions.decode(delegate.kind());
    }

    @Override
    public int count() {
      return delegate.count();
    }

    @Override
    public T context() {
      return delegate.context();
    }
  }
}
