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

/**
 * Type conversions between {@link java.nio.file.WatchEvent.Kind} and {@link
 * j$.nio.file.WatchEvent.Kind}.
 */
public class WatchEventKindConversions {

  public static <T> j$.nio.file.WatchEvent.Kind<T> encode(java.nio.file.WatchEvent.Kind<T> raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedWatchEventKind) {
      return ((DecodedWatchEventKind<T>) raw).delegate;
    }
    return new EncodedWatchEventKind<>(raw);
  }

  public static j$.nio.file.WatchEvent.Kind<?>[] encode(java.nio.file.WatchEvent.Kind<?>[] raw) {
    if (raw == null) {
      return null;
    }
    int n = raw.length;
    var results = new j$.nio.file.WatchEvent.Kind<?>[n];
    for (int i = 0; i < n; i++) {
      results[i] = encode(raw[i]);
    }
    return results;
  }

  public static <T> java.nio.file.WatchEvent.Kind<T> decode(
      j$.nio.file.WatchEvent.Kind<T> encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedWatchEventKind) {
      return ((EncodedWatchEventKind<T>) encoded).delegate;
    }
    return new DecodedWatchEventKind<>(encoded);
  }

  public static java.nio.file.WatchEvent.Kind<?>[] decode(
      j$.nio.file.WatchEvent.Kind<?>[] encoded) {
    if (encoded == null) {
      return null;
    }
    int n = encoded.length;
    var results = new java.nio.file.WatchEvent.Kind<?>[n];
    for (int i = 0; i < n; i++) {
      results[i] = decode(encoded[i]);
    }
    return results;
  }

  private WatchEventKindConversions() {}

  static class EncodedWatchEventKind<T> implements j$.nio.file.WatchEvent.Kind<T> {

    private final java.nio.file.WatchEvent.Kind<T> delegate;

    public EncodedWatchEventKind(java.nio.file.WatchEvent.Kind<T> delegate) {
      this.delegate = delegate;
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public Class<T> type() {
      return delegate.type();
    }
  }

  static class DecodedWatchEventKind<T> implements java.nio.file.WatchEvent.Kind<T> {

    private final j$.nio.file.WatchEvent.Kind<T> delegate;

    public DecodedWatchEventKind(j$.nio.file.WatchEvent.Kind<T> delegate) {
      this.delegate = delegate;
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public Class<T> type() {
      return delegate.type();
    }
  }
}
