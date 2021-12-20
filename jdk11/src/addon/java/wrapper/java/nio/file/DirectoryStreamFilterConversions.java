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
import java.nio.file.DirectoryStream;
import wrapper.model.WrapperTranslator;

/**
 * Type conversions between {@link java.nio.file.DirectoryStream.Filter} and {@link
 * j$.nio.file.DirectoryStream.Filter}.
 */
public class DirectoryStreamFilterConversions {

  public static <S, T> j$.nio.file.DirectoryStream.Filter<T> encode(
      DirectoryStream.Filter<S> raw, WrapperTranslator<T, S> decoder) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedDirectoryStreamFilter<?, ?>) {
      return ((DecodedDirectoryStreamFilter<S, T>) raw).delegate;
    }
    return new EncodedDirectoryStreamFilter<>(raw, decoder);
  }

  public static <S, T> java.nio.file.DirectoryStream.Filter<S> decode(
      j$.nio.file.DirectoryStream.Filter<T> encoded, WrapperTranslator<S, T> encoder) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedDirectoryStreamFilter<?, ?>) {
      return ((EncodedDirectoryStreamFilter<S, T>) encoded).delegate;
    }
    return new DecodedDirectoryStreamFilter<>(encoded, encoder);
  }

  private DirectoryStreamFilterConversions() {}

  static class EncodedDirectoryStreamFilter<S, T> implements j$.nio.file.DirectoryStream.Filter<T> {

    private final DirectoryStream.Filter<S> delegate;
    private final WrapperTranslator<T, S> decoder;

    public EncodedDirectoryStreamFilter(
        DirectoryStream.Filter<S> delegate, WrapperTranslator<T, S> decoder) {
      this.delegate = delegate;
      this.decoder = decoder;
    }

    @Override
    public boolean accept(T entry) throws IOException {
      try {
        return delegate.accept(decoder.translate(entry));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }
  }

  static class DecodedDirectoryStreamFilter<S, T>
      implements java.nio.file.DirectoryStream.Filter<S> {

    private final j$.nio.file.DirectoryStream.Filter<T> delegate;
    private final WrapperTranslator<S, T> encoder;

    DecodedDirectoryStreamFilter(
        j$.nio.file.DirectoryStream.Filter<T> delegate, WrapperTranslator<S, T> encoder) {
      this.delegate = delegate;
      this.encoder = encoder;
    }

    @Override
    public boolean accept(S entry) throws IOException {
      return delegate.accept(encoder.translate(entry));
    }
  }
}
