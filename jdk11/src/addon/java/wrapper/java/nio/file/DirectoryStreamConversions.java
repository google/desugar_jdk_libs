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

import java.io.IOException;
import java.util.Iterator;
import wrapper.model.WrapperTranslator;

/**
 * Type conversions between {@link java.nio.file.DirectoryStream} and {@link
 * j$.nio.file.DirectoryStream}.
 */
public final class DirectoryStreamConversions {

  public static <S, T> j$.nio.file.DirectoryStream<T> encode(
      java.nio.file.DirectoryStream<S> raw, WrapperTranslator<S, T> encoder) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedDirectoryStream<?, ?>) {
      return ((DecodedDirectoryStream<S, T>) raw).delegate;
    }
    return new EncodedDirectoryStream<>(raw, encoder);
  }

  public static <S, T> java.nio.file.DirectoryStream<S> decode(
      j$.nio.file.DirectoryStream<T> encoded, WrapperTranslator<T, S> decoder) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedDirectoryStream<?, ?>) {
      return ((EncodedDirectoryStream<S, T>) encoded).delegate;
    }
    return new DecodedDirectoryStream<>(encoded, decoder);
  }

  private DirectoryStreamConversions() {}

  static class EncodedDirectoryStream<S, T> implements j$.nio.file.DirectoryStream<T> {

    private final java.nio.file.DirectoryStream<S> delegate;
    private final WrapperTranslator<S, T> encoder;

    public EncodedDirectoryStream(
        java.nio.file.DirectoryStream<S> delegate, WrapperTranslator<S, T> encoder) {
      this.delegate = delegate;
      this.encoder = encoder;
    }

    @Override
    public Iterator<T> iterator() {
      Iterator<S> iterator = delegate.iterator();
      return new Iterator<T>() {
        @Override
        public boolean hasNext() {
          return iterator.hasNext();
        }

        @Override
        public T next() {
          return encoder.translate(iterator.next());
        }
      };
    }

    @Override
    public void close() throws IOException {
      delegate.close();
    }
  }

  static class DecodedDirectoryStream<S, T> implements java.nio.file.DirectoryStream<S> {

    private final j$.nio.file.DirectoryStream<T> delegate;
    private final WrapperTranslator<T, S> decoder;

    public DecodedDirectoryStream(
        j$.nio.file.DirectoryStream<T> delegate, WrapperTranslator<T, S> decoder) {
      this.delegate = delegate;
      this.decoder = decoder;
    }

    @Override
    public Iterator<S> iterator() {
      Iterator<T> iterator = delegate.iterator();
      return new Iterator<S>() {
        @Override
        public boolean hasNext() {
          return iterator.hasNext();
        }

        @Override
        public S next() {
          return decoder.translate(iterator.next());
        }
      };
    }

    @Override
    public void close() throws IOException {
      delegate.close();
    }
  }
}
