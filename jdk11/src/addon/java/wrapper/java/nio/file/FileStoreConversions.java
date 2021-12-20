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
import java.nio.file.FileStore;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;
import wrapper.java.nio.file.attribute.AttributeViewConversions;

/** Type conversions between {@link java.nio.file.FileStore} and {@link j$.nio.file.FileStore}. */
public final class FileStoreConversions {

  public static j$.nio.file.FileStore encode(java.nio.file.FileStore raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedFileStore) {
      return ((DecodedFileStore) raw).delegate;
    }
    return new EncodedFileStore(raw);
  }

  public static java.nio.file.FileStore decode(j$.nio.file.FileStore encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedFileStore) {
      return ((EncodedFileStore) encoded).delegate;
    }
    return new DecodedFileStore(encoded);
  }

  private FileStoreConversions() {}

  static class EncodedFileStore extends j$.nio.file.FileStore {

    private final FileStore delegate;

    public EncodedFileStore(FileStore delegate) {
      this.delegate = delegate;
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public String type() {
      return delegate.type();
    }

    @Override
    public boolean isReadOnly() {
      return delegate.isReadOnly();
    }

    @Override
    public long getTotalSpace() throws IOException {
      try {
        return delegate.getTotalSpace();
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public long getUsableSpace() throws IOException {
      try {
        return delegate.getUsableSpace();
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public long getBlockSize() throws IOException {
      try {
        return delegate.getBlockSize();
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public long getUnallocatedSpace() throws IOException {
      try {
        return delegate.getUnallocatedSpace();
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public boolean supportsFileAttributeView(
        Class<? extends j$.nio.file.attribute.FileAttributeView> type) {
      return delegate.supportsFileAttributeView(
          (Class<? extends FileAttributeView>) AttributeViewConversions.decode(type));
    }

    @Override
    public boolean supportsFileAttributeView(String name) {
      return delegate.supportsFileAttributeView(name);
    }

    @Override
    public <V extends j$.nio.file.attribute.FileStoreAttributeView> V getFileStoreAttributeView(
        Class<V> type) {
      Class<? extends FileStoreAttributeView> decode =
          (Class<? extends FileStoreAttributeView>) AttributeViewConversions.decode(type);
      return AttributeViewConversions.encode(delegate.getFileStoreAttributeView(decode), type);
    }

    @Override
    public Object getAttribute(String attribute) throws IOException {
      try {
        return delegate.getAttribute(attribute);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }
  }

  static class DecodedFileStore extends java.nio.file.FileStore {

    private final j$.nio.file.FileStore delegate;

    public DecodedFileStore(j$.nio.file.FileStore delegate) {
      this.delegate = delegate;
    }

    @Override
    public String name() {
      return delegate.name();
    }

    @Override
    public String type() {
      return delegate.type();
    }

    @Override
    public boolean isReadOnly() {
      return delegate.isReadOnly();
    }

    @Override
    public long getTotalSpace() throws IOException {
      return delegate.getTotalSpace();
    }

    @Override
    public long getUsableSpace() throws IOException {
      return delegate.getUsableSpace();
    }

    @Override
    public long getBlockSize() throws IOException {
      return delegate.getBlockSize();
    }

    @Override
    public long getUnallocatedSpace() throws IOException {
      return delegate.getUnallocatedSpace();
    }

    @Override
    public boolean supportsFileAttributeView(
        Class<? extends java.nio.file.attribute.FileAttributeView> type) {
      return delegate.supportsFileAttributeView(
          (Class<? extends j$.nio.file.attribute.FileAttributeView>)
              AttributeViewConversions.encode(type));
    }

    @Override
    public boolean supportsFileAttributeView(String name) {
      return delegate.supportsFileAttributeView(name);
    }

    @Override
    public <V extends java.nio.file.attribute.FileStoreAttributeView> V getFileStoreAttributeView(
        Class<V> type) {
      return AttributeViewConversions.decode(
          delegate.getFileStoreAttributeView(
              (Class<? extends j$.nio.file.attribute.FileStoreAttributeView>)
                  AttributeViewConversions.encode(type)),
          type);
    }

    @Override
    public Object getAttribute(String attribute) throws IOException {
      return delegate.getAttribute(attribute);
    }
  }
}
