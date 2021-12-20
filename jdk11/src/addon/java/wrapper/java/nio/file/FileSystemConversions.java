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
import java.util.Iterator;
import java.util.Set;
import wrapper.java.nio.file.attribute.UserPrincipalLookupServiceConversions;
import wrapper.java.nio.file.spi.FileSystemProviderConversions;

/** Type conversions between {@link java.nio.file.FileSystem} and {@link j$.nio.file.FileSystem}. */
public final class FileSystemConversions {

  public static j$.nio.file.FileSystem encode(java.nio.file.FileSystem raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedFileSystem) {
      return ((DecodedFileSystem) raw).delegate;
    }
    return new EncodedFileSystem(raw);
  }

  public static java.nio.file.FileSystem decode(j$.nio.file.FileSystem encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedFileSystem) {
      return ((EncodedFileSystem) encoded).delegate;
    }
    return new DecodedFileSystem(encoded);
  }

  private FileSystemConversions() {}

  static class EncodedFileSystem extends j$.nio.file.FileSystem {

    private final java.nio.file.FileSystem delegate;

    public EncodedFileSystem(java.nio.file.FileSystem delegate) {
      this.delegate = delegate;
    }

    @Override
    public j$.nio.file.spi.FileSystemProvider provider() {
      return FileSystemProviderConversions.encode(delegate.provider());
    }

    @Override
    public void close() throws IOException {
      try {
        delegate.close();
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public boolean isOpen() {
      return delegate.isOpen();
    }

    @Override
    public boolean isReadOnly() {
      return delegate.isReadOnly();
    }

    @Override
    public String getSeparator() {
      return delegate.getSeparator();
    }

    @Override
    public Iterable<j$.nio.file.Path> getRootDirectories() {
      Iterable<java.nio.file.Path> rootDirectories = delegate.getRootDirectories();
      Iterator<java.nio.file.Path> iterator = rootDirectories.iterator();
      return new Iterable<j$.nio.file.Path>() {
        @Override
        public Iterator<j$.nio.file.Path> iterator() {
          return new Iterator<j$.nio.file.Path>() {
            @Override
            public boolean hasNext() {
              return iterator.hasNext();
            }

            @Override
            public j$.nio.file.Path next() {
              return PathConversions.encode(iterator.next());
            }
          };
        }
      };
    }

    @Override
    public Iterable<j$.nio.file.FileStore> getFileStores() {
      Iterable<java.nio.file.FileStore> rootDirectories = delegate.getFileStores();
      Iterator<java.nio.file.FileStore> iterator = rootDirectories.iterator();
      return new Iterable<>() {
        @Override
        public Iterator<j$.nio.file.FileStore> iterator() {
          return new Iterator<>() {

            @Override
            public boolean hasNext() {
              return iterator.hasNext();
            }

            @Override
            public j$.nio.file.FileStore next() {
              return FileStoreConversions.encode(iterator.next());
            }
          };
        }
      };
    }

    @Override
    public Set<String> supportedFileAttributeViews() {
      return delegate.supportedFileAttributeViews();
    }

    @Override
    public j$.nio.file.Path getPath(String first, String... more) {
      return PathConversions.encode(delegate.getPath(first, more));
    }

    @Override
    public j$.nio.file.PathMatcher getPathMatcher(String syntaxAndPattern) {
      return PathMatcherConversions.encode(delegate.getPathMatcher(syntaxAndPattern));
    }

    @Override
    public j$.nio.file.attribute.UserPrincipalLookupService getUserPrincipalLookupService() {
      return UserPrincipalLookupServiceConversions.encode(delegate.getUserPrincipalLookupService());
    }

    @Override
    public j$.nio.file.WatchService newWatchService() throws IOException {
      try {
        return WatchServiceConversions.encode(delegate.newWatchService());
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof EncodedFileSystem)) {
        return false;
      }
      return delegate.equals(((EncodedFileSystem) obj).delegate);
    }

    @Override
    public String toString() {
      return delegate.toString();
    }
  }

  static class DecodedFileSystem extends java.nio.file.FileSystem {

    private final j$.nio.file.FileSystem delegate;

    public DecodedFileSystem(j$.nio.file.FileSystem delegate) {
      this.delegate = delegate;
    }

    @Override
    public java.nio.file.spi.FileSystemProvider provider() {
      return FileSystemProviderConversions.decode(delegate.provider());
    }

    @Override
    public void close() throws IOException {
      delegate.close();
    }

    @Override
    public boolean isOpen() {
      return delegate.isOpen();
    }

    @Override
    public boolean isReadOnly() {
      return delegate.isReadOnly();
    }

    @Override
    public String getSeparator() {
      return delegate.getSeparator();
    }

    @Override
    public Iterable<java.nio.file.Path> getRootDirectories() {
      Iterable<j$.nio.file.Path> rootDirectories = delegate.getRootDirectories();
      Iterator<j$.nio.file.Path> iterator = rootDirectories.iterator();
      return new Iterable<java.nio.file.Path>() {
        @Override
        public Iterator<java.nio.file.Path> iterator() {
          return new Iterator<>() {
            @Override
            public boolean hasNext() {
              return iterator.hasNext();
            }

            @Override
            public java.nio.file.Path next() {
              return PathConversions.decode(iterator.next());
            }
          };
        }
      };
    }

    @Override
    public Iterable<java.nio.file.FileStore> getFileStores() {
      Iterable<j$.nio.file.FileStore> rootDirectories = delegate.getFileStores();
      Iterator<j$.nio.file.FileStore> iterator = rootDirectories.iterator();
      return new Iterable<>() {
        @Override
        public Iterator<java.nio.file.FileStore> iterator() {
          return new Iterator<>() {

            @Override
            public boolean hasNext() {
              return iterator.hasNext();
            }

            @Override
            public java.nio.file.FileStore next() {
              return FileStoreConversions.decode(iterator.next());
            }
          };
        }
      };
    }

    @Override
    public Set<String> supportedFileAttributeViews() {
      return delegate.supportedFileAttributeViews();
    }

    @Override
    public java.nio.file.Path getPath(String first, String... more) {
      return PathConversions.decode(delegate.getPath(first, more));
    }

    @Override
    public java.nio.file.PathMatcher getPathMatcher(String syntaxAndPattern) {
      return PathMatcherConversions.decode(delegate.getPathMatcher(syntaxAndPattern));
    }

    @Override
    public java.nio.file.attribute.UserPrincipalLookupService getUserPrincipalLookupService() {
      return UserPrincipalLookupServiceConversions.decode(delegate.getUserPrincipalLookupService());
    }

    @Override
    public java.nio.file.WatchService newWatchService() throws IOException {
      return WatchServiceConversions.decode(delegate.newWatchService());
    }
  }
}
