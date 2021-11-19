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

import j$.nio.file.WatchEvent.Modifier;

/**
 * Type conversions between {@link java.nio.file.WatchEvent.Modifier} and {@link
 * j$.nio.file.WatchEvent.Modifier}.
 */
public class WatchEventModifierConversions {

  public static j$.nio.file.WatchEvent.Modifier encode(java.nio.file.WatchEvent.Modifier raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedWatchEventModifier) {
      return ((DecodedWatchEventModifier) raw).delegate;
    }
    return new EncodedWatchEventModifier(raw);
  }

  public static j$.nio.file.WatchEvent.Modifier[] encode(java.nio.file.WatchEvent.Modifier[] raw) {
    if (raw == null) {
      return null;
    }
    int n = raw.length;
    var results = new Modifier[n];
    for (int i = 0; i < n; i++) {
      results[i] = encode(raw[i]);
    }
    return results;
  }

  public static java.nio.file.WatchEvent.Modifier decode(j$.nio.file.WatchEvent.Modifier encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedWatchEventModifier) {
      return ((EncodedWatchEventModifier) encoded).delegate;
    }
    return new DecodedWatchEventModifier(encoded);
  }

  public static java.nio.file.WatchEvent.Modifier[] decode(
      j$.nio.file.WatchEvent.Modifier[] encoded) {
    if (encoded == null) {
      return null;
    }
    int n = encoded.length;
    var results = new java.nio.file.WatchEvent.Modifier[n];
    for (int i = 0; i < n; i++) {
      results[i] = decode(encoded[i]);
    }
    return results;
  }

  private WatchEventModifierConversions() {}

  static class EncodedWatchEventModifier implements j$.nio.file.WatchEvent.Modifier {

    private final java.nio.file.WatchEvent.Modifier delegate;

    public EncodedWatchEventModifier(java.nio.file.WatchEvent.Modifier delegate) {
      this.delegate = delegate;
    }

    @Override
    public String name() {
      return delegate.name();
    }
  }

  static class DecodedWatchEventModifier implements java.nio.file.WatchEvent.Modifier {

    private final j$.nio.file.WatchEvent.Modifier delegate;

    public DecodedWatchEventModifier(j$.nio.file.WatchEvent.Modifier delegate) {
      this.delegate = delegate;
    }

    @Override
    public String name() {
      return delegate.name();
    }
  }
}
