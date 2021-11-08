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

package wrapper.java.nio.file.attribute;

import j$.io.UncheckedIOException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * Type conversions between {@link java.nio.file.attribute.UserDefinedFileAttributeView} and {@link
 * j$.nio.file.attribute.UserDefinedFileAttributeView}.
 */
public final class UserDefinedFileAttributeViewConversions {

  public static j$.nio.file.attribute.UserDefinedFileAttributeView encode(
      java.nio.file.attribute.UserDefinedFileAttributeView raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedUserDefinedFileAttributeView<?>) {
      return ((DecodedUserDefinedFileAttributeView<?>) raw).delegate;
    }
    return new EncodedUserDefinedFileAttributeView<>(
        raw, java.nio.file.attribute.UserDefinedFileAttributeView.class);
  }

  public static java.nio.file.attribute.UserDefinedFileAttributeView decode(
      j$.nio.file.attribute.UserDefinedFileAttributeView encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedUserDefinedFileAttributeView<?>) {
      return ((EncodedUserDefinedFileAttributeView<?>) encoded).delegate;
    }
    return new DecodedUserDefinedFileAttributeView<>(
        encoded, j$.nio.file.attribute.UserDefinedFileAttributeView.class);
  }

  private UserDefinedFileAttributeViewConversions() {}

  static class EncodedUserDefinedFileAttributeView<
          T extends java.nio.file.attribute.UserDefinedFileAttributeView>
      extends FileAttributeViewConversions.EncodedFileAttributeView<T>
      implements j$.nio.file.attribute.UserDefinedFileAttributeView {

    public EncodedUserDefinedFileAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public List<String> list() {
      try {
        return delegate.list();
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }

    @Override
    public int size(String name) {
      try {
        return delegate.size(name);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }

    @Override
    public int read(String name, ByteBuffer dst) {
      try {
        return delegate.read(name, dst);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }

    @Override
    public int write(String name, ByteBuffer src) {
      try {
        return delegate.write(name, src);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }

    @Override
    public void delete(String name) {
      try {
        delegate.delete(name);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
  }

  static class DecodedUserDefinedFileAttributeView<
          T extends j$.nio.file.attribute.UserDefinedFileAttributeView>
      extends FileAttributeViewConversions.DecodedFileAttributeView<T>
      implements java.nio.file.attribute.UserDefinedFileAttributeView {

    public DecodedUserDefinedFileAttributeView(T delegate, Class<T> delegateType) {
      super(delegate, delegateType);
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public List<String> list() {
      return delegate.list();
    }

    @Override
    public int size(String name) {
      return delegate.size(name);
    }

    @Override
    public int read(String name, ByteBuffer dst) {
      return delegate.read(name, dst);
    }

    @Override
    public int write(String name, ByteBuffer src) {
      return delegate.write(name, src);
    }

    @Override
    public void delete(String name) {
      delegate.delete(name);
    }
  }
}
