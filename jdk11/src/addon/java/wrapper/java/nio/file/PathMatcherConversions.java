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

/**
 * Type conversions between {@link java.nio.file.PathMatcher} and {@link j$.nio.file.PathMatcher}.
 */
public final class PathMatcherConversions {

  public static j$.nio.file.PathMatcher encode(java.nio.file.PathMatcher raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedPathMatcher) {
      return ((DecodedPathMatcher) raw).delegate;
    }
    return new EncodedPathMatcher(raw);
  }

  public static java.nio.file.PathMatcher decode(j$.nio.file.PathMatcher encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedPathMatcher) {
      return ((EncodedPathMatcher) encoded).delegate;
    }
    return new DecodedPathMatcher(encoded);
  }

  private PathMatcherConversions() {}

  static class EncodedPathMatcher implements j$.nio.file.PathMatcher {

    private final java.nio.file.PathMatcher delegate;

    public EncodedPathMatcher(java.nio.file.PathMatcher delegate) {
      this.delegate = delegate;
    }

    @Override
    public boolean matches(j$.nio.file.Path path) {
      return delegate.matches(PathConversions.decode(path));
    }
  }

  static class DecodedPathMatcher implements java.nio.file.PathMatcher {

    private final j$.nio.file.PathMatcher delegate;

    public DecodedPathMatcher(j$.nio.file.PathMatcher delegate) {
      this.delegate = delegate;
    }

    @Override
    public boolean matches(java.nio.file.Path path) {
      return delegate.matches(PathConversions.encode(path));
    }
  }
}
