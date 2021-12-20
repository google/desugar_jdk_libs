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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;

/** Type conversions between {@link java.nio.file.AccessMode} and {@link j$.nio.file.AccessMode}. */
public final class PathConversions {

  public static j$.nio.file.Path encode(java.nio.file.Path raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedPath) {
      return ((DecodedPath) raw).delegate;
    }
    return new EncodedPath(raw);
  }

  public static java.nio.file.Path decode(j$.nio.file.Path encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedPath) {
      return ((EncodedPath) encoded).delegate;
    }
    return new DecodedPath(encoded);
  }

  private PathConversions() {}

  static class EncodedPath implements j$.nio.file.Path {

    private final java.nio.file.Path delegate;

    public EncodedPath(java.nio.file.Path delegate) {
      this.delegate = delegate;
    }

    @Override
    public j$.nio.file.FileSystem getFileSystem() {
      return FileSystemConversions.encode(delegate.getFileSystem());
    }

    @Override
    public boolean isAbsolute() {
      return delegate.isAbsolute();
    }

    @Override
    public j$.nio.file.Path getRoot() {
      return encode(delegate.getRoot());
    }

    @Override
    public j$.nio.file.Path getFileName() {
      return encode(delegate.getFileName());
    }

    @Override
    public j$.nio.file.Path getParent() {
      return encode(delegate.getParent());
    }

    @Override
    public int getNameCount() {
      return delegate.getNameCount();
    }

    @Override
    public j$.nio.file.Path getName(int index) {
      return encode(delegate.getName(index));
    }

    @Override
    public j$.nio.file.Path subpath(int beginIndex, int endIndex) {
      return encode(delegate.subpath(beginIndex, endIndex));
    }

    @Override
    public boolean startsWith(j$.nio.file.Path other) {
      return delegate.startsWith(decode(other));
    }

    @Override
    public boolean startsWith(String other) {
      return delegate.startsWith(other);
    }

    @Override
    public boolean endsWith(j$.nio.file.Path other) {
      return delegate.endsWith(decode(other));
    }

    @Override
    public boolean endsWith(String other) {
      return delegate.endsWith(other);
    }

    @Override
    public j$.nio.file.Path normalize() {
      return encode(delegate.normalize());
    }

    @Override
    public j$.nio.file.Path resolve(j$.nio.file.Path other) {
      return encode(delegate.resolve(decode(other)));
    }

    @Override
    public j$.nio.file.Path resolve(String other) {
      return encode(delegate.resolve(other));
    }

    @Override
    public j$.nio.file.Path resolveSibling(j$.nio.file.Path other) {
      return encode(delegate.resolveSibling(decode(other)));
    }

    @Override
    public j$.nio.file.Path resolveSibling(String other) {
      return encode(delegate.resolveSibling(other));
    }

    @Override
    public j$.nio.file.Path relativize(j$.nio.file.Path other) {
      return encode(delegate.relativize(decode(other)));
    }

    @Override
    public URI toUri() {
      return delegate.toUri();
    }

    @Override
    public j$.nio.file.Path toAbsolutePath() {
      return encode(delegate.toAbsolutePath());
    }

    @Override
    public j$.nio.file.Path toRealPath(j$.nio.file.LinkOption... options) throws IOException {
      try {
        return encode(delegate.toRealPath(LinkOptionConversions.decode(options)));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public File toFile() {
      return delegate.toFile();
    }

    @Override
    public j$.nio.file.WatchKey register(
        j$.nio.file.WatchService watcher,
        j$.nio.file.WatchEvent.Kind<?>[] events,
        j$.nio.file.WatchEvent.Modifier... modifiers)
        throws IOException {
      try {
        return WatchKeyConversions.encode(
            delegate.register(
                WatchServiceConversions.decode(watcher),
                WatchEventKindConversions.decode(events),
                WatchEventModifierConversions.decode(modifiers)));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public j$.nio.file.WatchKey register(
        j$.nio.file.WatchService watcher, j$.nio.file.WatchEvent.Kind<?>... events)
        throws IOException {
      try {
        return WatchKeyConversions.encode(
            delegate.register(
                WatchServiceConversions.decode(watcher), WatchEventKindConversions.decode(events)));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public Iterator<j$.nio.file.Path> iterator() {
      Iterator<java.nio.file.Path> iterator = delegate.iterator();
      return new Iterator<>() {
        @Override
        public boolean hasNext() {
          return iterator.hasNext();
        }

        @Override
        public j$.nio.file.Path next() {
          return encode(iterator.next());
        }
      };
    }

    @Override
    public int compareTo(j$.nio.file.Path other) {
      return delegate.compareTo(decode(other));
    }

    @Override
    public boolean equals(Object other) {
      if (!(other instanceof EncodedPath)) {
        return false;
      }
      return delegate.equals(((EncodedPath) other).delegate);
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    @Override
    public String toString() {
      return delegate.toString();
    }
  }

  static class DecodedPath implements java.nio.file.Path {

    private final j$.nio.file.Path delegate;

    public DecodedPath(j$.nio.file.Path delegate) {
      this.delegate = delegate;
    }

    @Override
    public java.nio.file.FileSystem getFileSystem() {
      return FileSystemConversions.decode(delegate.getFileSystem());
    }

    @Override
    public boolean isAbsolute() {
      return delegate.isAbsolute();
    }

    @Override
    public java.nio.file.Path getRoot() {
      return decode(delegate.getRoot());
    }

    @Override
    public java.nio.file.Path getFileName() {
      return decode(delegate.getFileName());
    }

    @Override
    public java.nio.file.Path getParent() {
      return decode(delegate.getParent());
    }

    @Override
    public int getNameCount() {
      return delegate.getNameCount();
    }

    @Override
    public java.nio.file.Path getName(int index) {
      return decode(delegate.getName(index));
    }

    @Override
    public java.nio.file.Path subpath(int beginIndex, int endIndex) {
      return decode(delegate.subpath(beginIndex, endIndex));
    }

    @Override
    public boolean startsWith(java.nio.file.Path other) {
      return delegate.startsWith(encode(other));
    }

    @Override
    public boolean startsWith(String other) {
      return delegate.startsWith(other);
    }

    @Override
    public boolean endsWith(java.nio.file.Path other) {
      return delegate.endsWith(encode(other));
    }

    @Override
    public boolean endsWith(String other) {
      return delegate.endsWith(other);
    }

    @Override
    public java.nio.file.Path normalize() {
      return decode(delegate.normalize());
    }

    @Override
    public java.nio.file.Path resolve(java.nio.file.Path other) {
      return decode(delegate.resolve(encode(other)));
    }

    @Override
    public java.nio.file.Path resolve(String other) {
      return decode(delegate.resolve(other));
    }

    @Override
    public java.nio.file.Path resolveSibling(java.nio.file.Path other) {
      return decode(delegate.resolveSibling(encode(other)));
    }

    @Override
    public java.nio.file.Path resolveSibling(String other) {
      return decode(delegate.resolveSibling(other));
    }

    @Override
    public java.nio.file.Path relativize(java.nio.file.Path other) {
      return decode(delegate.relativize(encode(other)));
    }

    @Override
    public URI toUri() {
      return delegate.toUri();
    }

    @Override
    public java.nio.file.Path toAbsolutePath() {
      return decode(delegate.toAbsolutePath());
    }

    @Override
    public java.nio.file.Path toRealPath(java.nio.file.LinkOption... options) throws IOException {
      return decode(delegate.toRealPath(LinkOptionConversions.encode(options)));
    }

    @Override
    public File toFile() {
      return delegate.toFile();
    }

    @Override
    public java.nio.file.WatchKey register(
        java.nio.file.WatchService watcher,
        java.nio.file.WatchEvent.Kind<?>[] events,
        java.nio.file.WatchEvent.Modifier... modifiers)
        throws IOException {
      return WatchKeyConversions.decode(
          delegate.register(
              WatchServiceConversions.encode(watcher),
              WatchEventKindConversions.encode(events),
              WatchEventModifierConversions.encode(modifiers)));
    }

    @Override
    public java.nio.file.WatchKey register(
        java.nio.file.WatchService watcher, java.nio.file.WatchEvent.Kind<?>... events)
        throws IOException {
      return WatchKeyConversions.decode(
          delegate.register(
              WatchServiceConversions.encode(watcher), WatchEventKindConversions.encode(events)));
    }

    @Override
    public Iterator<java.nio.file.Path> iterator() {
      Iterator<j$.nio.file.Path> iterator = delegate.iterator();
      return new Iterator<>() {
        @Override
        public boolean hasNext() {
          return iterator.hasNext();
        }

        @Override
        public java.nio.file.Path next() {
          return decode(iterator.next());
        }
      };
    }

    @Override
    public int compareTo(java.nio.file.Path other) {
      return delegate.compareTo(encode(other));
    }

    @Override
    public boolean equals(Object other) {
      if (!(other instanceof DecodedPath)) {
        return false;
      }
      return delegate.equals(((DecodedPath) other).delegate);
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    @Override
    public String toString() {
      return delegate.toString();
    }
  }
}
