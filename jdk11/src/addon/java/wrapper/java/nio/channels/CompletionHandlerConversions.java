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

/**
 * Type conversions between {@link java.nio.channels.CompletionHandler} and {@link
 * j$.nio.channels.CompletionHandler}.
 */
public final class CompletionHandlerConversions {

  public static <V, A> j$.nio.channels.CompletionHandler<V, A> encode(
      java.nio.channels.CompletionHandler<V, A> raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedCompletionHandler<?, ?>) {
      return ((DecodedCompletionHandler<V, A>) raw).delegate;
    }
    return new EncodedCompletionHandler<>(raw);
  }

  public static <V, A> java.nio.channels.CompletionHandler<V, A> decode(
      j$.nio.channels.CompletionHandler<V, A> encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedCompletionHandler<?, ?>) {
      return ((EncodedCompletionHandler<V, A>) encoded).delegate;
    }
    return new DecodedCompletionHandler<>(encoded);
  }

  private CompletionHandlerConversions() {}

  static class EncodedCompletionHandler<V, A> implements j$.nio.channels.CompletionHandler<V, A> {

    private final java.nio.channels.CompletionHandler<V, A> delegate;

    public EncodedCompletionHandler(java.nio.channels.CompletionHandler<V, A> delegate) {
      this.delegate = delegate;
    }

    @Override
    public void completed(V result, A attachment) {
      delegate.completed(result, attachment);
    }

    @Override
    public void failed(Throwable exc, A attachment) {
      delegate.failed(exc, attachment);
    }
  }

  static class DecodedCompletionHandler<V, A> implements java.nio.channels.CompletionHandler<V, A> {

    private final j$.nio.channels.CompletionHandler<V, A> delegate;

    public DecodedCompletionHandler(j$.nio.channels.CompletionHandler<V, A> delegate) {
      this.delegate = delegate;
    }

    @Override
    public void completed(V result, A attachment) {
      delegate.completed(result, attachment);
    }

    @Override
    public void failed(Throwable exc, A attachment) {
      delegate.failed(exc, attachment);
    }
  }
}
