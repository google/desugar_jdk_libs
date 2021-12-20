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

package wrapper.java.nio.file.spi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import wrapper.java.nio.JavaNioCentralConversions;
import wrapper.java.nio.channels.AsynchronousFileChannelConversions;
import wrapper.java.nio.channels.SeekableByteChannelConversions;
import wrapper.java.nio.file.AccessModeConversions;
import wrapper.java.nio.file.CopyOptionConversions;
import wrapper.java.nio.file.DirectoryStreamConversions;
import wrapper.java.nio.file.DirectoryStreamFilterConversions;
import wrapper.java.nio.file.FileStoreConversions;
import wrapper.java.nio.file.FileSystemConversions;
import wrapper.java.nio.file.IOExceptionConversions;
import wrapper.java.nio.file.LinkOptionConversions;
import wrapper.java.nio.file.OpenOptionConversions;
import wrapper.java.nio.file.PathConversions;
import wrapper.java.nio.file.attribute.AttributeViewConversions;
import wrapper.java.nio.file.attribute.BasicFileAttributesConversions;
import wrapper.java.nio.file.attribute.FileAttributeConversions;

/**
 * Type conversions between {@link java.nio.file.spi.FileSystemProvider} and {@link
 * j$.nio.file.spi.FileSystemProvider}.
 */
public final class FileSystemProviderConversions {

  public static j$.nio.file.spi.FileSystemProvider encode(FileSystemProvider raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedFileSystemProvider) {
      return ((DecodedFileSystemProvider) raw).delegate;
    }
    return new EncodedFileSystemProvider(raw);
  }

  public static FileSystemProvider decode(j$.nio.file.spi.FileSystemProvider encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedFileSystemProvider) {
      return ((EncodedFileSystemProvider) encoded).delegate;
    }
    return new DecodedFileSystemProvider(encoded);
  }

  private FileSystemProviderConversions() {}

  static class EncodedFileSystemProvider extends j$.nio.file.spi.FileSystemProvider {

    private final FileSystemProvider delegate;

    public EncodedFileSystemProvider(FileSystemProvider delegate) {
      this.delegate = delegate;
    }

    public static List<j$.nio.file.spi.FileSystemProvider> installedProviders() {
      List<j$.nio.file.spi.FileSystemProvider> results = new ArrayList<>();
      for (FileSystemProvider provider : FileSystemProvider.installedProviders()) {
        results.add(FileSystemProviderConversions.encode(provider));
      }
      return results;
    }

    @Override
    public String getScheme() {
      return delegate.getScheme();
    }

    @Override
    public j$.nio.file.FileSystem newFileSystem(URI uri, Map<String, ?> env) throws IOException {
      try {
        return FileSystemConversions.encode(delegate.newFileSystem(uri, env));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      } catch (FileSystemAlreadyExistsException e) {
        throw new j$.nio.file.FileSystemAlreadyExistsException();
      }
    }

    @Override
    public j$.nio.file.FileSystem getFileSystem(URI uri) {
      return FileSystemConversions.encode(delegate.getFileSystem(uri));
    }

    @Override
    public j$.nio.file.Path getPath(URI uri) {
      return PathConversions.encode(delegate.getPath(uri));
    }

    @Override
    public j$.nio.file.FileSystem newFileSystem(j$.nio.file.Path path, Map<String, ?> env)
        throws IOException {
      try {
        return FileSystemConversions.encode(
            delegate.newFileSystem(PathConversions.decode(path), env));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public InputStream newInputStream(j$.nio.file.Path path, j$.nio.file.OpenOption... options)
        throws IOException {
      try {
        return delegate.newInputStream(
            PathConversions.decode(path), OpenOptionConversions.decode(options));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public OutputStream newOutputStream(j$.nio.file.Path path, j$.nio.file.OpenOption... options)
        throws IOException {
      try {
        return delegate.newOutputStream(
            PathConversions.decode(path), OpenOptionConversions.decode(options));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public FileChannel newFileChannel(
        j$.nio.file.Path path,
        Set<? extends j$.nio.file.OpenOption> options,
        j$.nio.file.attribute.FileAttribute<?>... attrs)
        throws IOException {
      try {
        return delegate.newFileChannel(
            PathConversions.decode(path),
            OpenOptionConversions.decode(options),
            FileAttributeConversions.decode(attrs));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public j$.nio.channels.AsynchronousFileChannel newAsynchronousFileChannel(
        j$.nio.file.Path path,
        Set<? extends j$.nio.file.OpenOption> options,
        ExecutorService executor,
        j$.nio.file.attribute.FileAttribute<?>... attrs)
        throws IOException {
      try {
        return AsynchronousFileChannelConversions.encode(
            delegate.newAsynchronousFileChannel(
                PathConversions.decode(path),
                OpenOptionConversions.decode(options),
                executor,
                FileAttributeConversions.decode(attrs)));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public j$.nio.channels.SeekableByteChannel newByteChannel(
        j$.nio.file.Path path,
        Set<? extends j$.nio.file.OpenOption> options,
        j$.nio.file.attribute.FileAttribute<?>... attrs)
        throws IOException {
      try {
        SeekableByteChannel javaSeekableByteChannel =
            delegate.newByteChannel(
                PathConversions.decode(path),
                OpenOptionConversions.decode(options),
                FileAttributeConversions.decode(attrs));
        return SeekableByteChannelConversions.encode(javaSeekableByteChannel);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public j$.nio.file.DirectoryStream<j$.nio.file.Path> newDirectoryStream(
        j$.nio.file.Path dir, j$.nio.file.DirectoryStream.Filter<? super j$.nio.file.Path> filter)
        throws IOException {
      try {
        Path javaPath = PathConversions.decode(dir);
        java.nio.file.DirectoryStream.Filter<Path> javaFilter =
            DirectoryStreamFilterConversions.decode(filter, PathConversions::encode);
        java.nio.file.DirectoryStream<Path> javaDirectoryStream =
            delegate.newDirectoryStream(javaPath, javaFilter);
        return DirectoryStreamConversions.encode(javaDirectoryStream, PathConversions::encode);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void createDirectory(
        j$.nio.file.Path dir, j$.nio.file.attribute.FileAttribute<?>... attrs) throws IOException {
      try {
        Path javaPath = PathConversions.decode(dir);
        java.nio.file.attribute.FileAttribute<?>[] javaFileAttrs =
            FileAttributeConversions.decode(attrs);
        delegate.createDirectory(javaPath, javaFileAttrs);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void createSymbolicLink(
        j$.nio.file.Path link,
        j$.nio.file.Path target,
        j$.nio.file.attribute.FileAttribute<?>... attrs)
        throws IOException {
      try {
        Path javaLink = PathConversions.decode(link);
        Path javaTarget = PathConversions.decode(target);
        java.nio.file.attribute.FileAttribute<?>[] javaFileAttrs =
            FileAttributeConversions.decode(attrs);
        delegate.createSymbolicLink(javaLink, javaTarget, javaFileAttrs);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void createLink(j$.nio.file.Path link, j$.nio.file.Path existing) throws IOException {
      try {
        Path javaLink = PathConversions.decode(link);
        Path javaExisting = PathConversions.decode(existing);
        delegate.createLink(javaLink, javaExisting);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void delete(j$.nio.file.Path path) throws IOException {
      try {
        Path javaPath = PathConversions.decode(path);
        delegate.delete(javaPath);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public boolean deleteIfExists(j$.nio.file.Path path) throws IOException {
      try {
        Path javaPath = PathConversions.decode(path);
        return delegate.deleteIfExists(javaPath);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public j$.nio.file.Path readSymbolicLink(j$.nio.file.Path link) throws IOException {
      try {
        Path javaLink = PathConversions.decode(link);
        Path actualPath = delegate.readSymbolicLink(javaLink);
        return PathConversions.encode(actualPath);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void copy(
        j$.nio.file.Path source, j$.nio.file.Path target, j$.nio.file.CopyOption... options)
        throws IOException {
      try {
        Path javaSource = PathConversions.decode(source);
        Path javaTarget = PathConversions.decode(target);
        java.nio.file.CopyOption[] javaCopyOptions = CopyOptionConversions.decode(options);
        delegate.copy(javaSource, javaTarget, javaCopyOptions);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void move(
        j$.nio.file.Path source, j$.nio.file.Path target, j$.nio.file.CopyOption... options)
        throws IOException {
      try {
        Path javaSource = PathConversions.decode(source);
        Path javaTarget = PathConversions.decode(target);
        java.nio.file.CopyOption[] javaCopyOptions = CopyOptionConversions.decode(options);
        delegate.move(javaSource, javaTarget, javaCopyOptions);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public boolean isSameFile(j$.nio.file.Path path, j$.nio.file.Path path2) throws IOException {
      Path javaPath = PathConversions.decode(path);
      Path javaPath2 = PathConversions.decode(path2);
      try {
        return delegate.isSameFile(javaPath, javaPath2);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public boolean isHidden(j$.nio.file.Path path) throws IOException {
      Path javaPath = PathConversions.decode(path);
      try {
        return delegate.isHidden(javaPath);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public j$.nio.file.FileStore getFileStore(j$.nio.file.Path path) throws IOException {
      Path javaPath = PathConversions.decode(path);
      try {
        java.nio.file.FileStore javaFileStore = delegate.getFileStore(javaPath);
        return FileStoreConversions.encode(javaFileStore);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void checkAccess(j$.nio.file.Path path, j$.nio.file.AccessMode... modes)
        throws IOException {
      Path javaPath = PathConversions.decode(path);
      java.nio.file.AccessMode[] javaAccessModes = AccessModeConversions.decode(modes);
      try {
        delegate.checkAccess(javaPath, javaAccessModes);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public <V extends j$.nio.file.attribute.FileAttributeView> V getFileAttributeView(
        j$.nio.file.Path path, Class<V> type, j$.nio.file.LinkOption... options) {
      return AttributeViewConversions.encode(
          delegate.getFileAttributeView(
              PathConversions.decode(path),
              (Class<? extends FileAttributeView>) AttributeViewConversions.decode(type),
              LinkOptionConversions.decode(options)),
          type);
    }

    @Override
    public <A extends j$.nio.file.attribute.BasicFileAttributes> A readAttributes(
        j$.nio.file.Path path, Class<A> type, j$.nio.file.LinkOption... options)
        throws IOException {
      Path javaPath = PathConversions.decode(path);
      Class<? extends java.nio.file.attribute.BasicFileAttributes> javaType =
          BasicFileAttributesConversions.decode(type);
      java.nio.file.LinkOption[] javaLinkOptions = LinkOptionConversions.decode(options);
      try {
        return BasicFileAttributesConversions.encode(
            delegate.readAttributes(javaPath, javaType, javaLinkOptions), type);
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public Map<String, Object> readAttributes(
        j$.nio.file.Path path, String attributes, j$.nio.file.LinkOption... options)
        throws IOException {
      try {
        return JavaNioCentralConversions.encodeMapValue(
            delegate.readAttributes(
                PathConversions.decode(path), attributes, LinkOptionConversions.decode(options)));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public void setAttribute(
        j$.nio.file.Path path, String attribute, Object value, j$.nio.file.LinkOption... options)
        throws IOException {
      try {
        delegate.setAttribute(
            PathConversions.decode(path),
            attribute,
            JavaNioCentralConversions.decode(value),
            LinkOptionConversions.decode(options));
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
      if (!(obj instanceof EncodedFileSystemProvider)) {
        return false;
      }
      return delegate.equals(((EncodedFileSystemProvider) obj).delegate);
    }

    @Override
    public String toString() {
      return delegate.toString();
    }
  }

  static class DecodedFileSystemProvider extends java.nio.file.spi.FileSystemProvider {

    private final j$.nio.file.spi.FileSystemProvider delegate;

    public DecodedFileSystemProvider(j$.nio.file.spi.FileSystemProvider delegate) {
      this.delegate = delegate;
    }

    public static List<java.nio.file.spi.FileSystemProvider> installedProviders() {
      List<java.nio.file.spi.FileSystemProvider> results = new ArrayList<>();
      for (var provider : j$.nio.file.spi.FileSystemProvider.installedProviders()) {
        results.add(decode(provider));
      }
      return results;
    }

    @Override
    public String getScheme() {
      return delegate.getScheme();
    }

    @Override
    public java.nio.file.FileSystem newFileSystem(URI uri, Map<String, ?> env) throws IOException {
      try {
        return FileSystemConversions.decode(delegate.newFileSystem(uri, env));
      } catch (FileSystemAlreadyExistsException e) {
        throw new java.nio.file.FileSystemAlreadyExistsException(e.getMessage());
      }
    }

    @Override
    public java.nio.file.FileSystem getFileSystem(URI uri) {
      return FileSystemConversions.decode(delegate.getFileSystem(uri));
    }

    @Override
    public java.nio.file.Path getPath(URI uri) {
      return PathConversions.decode(delegate.getPath(uri));
    }

    @Override
    public java.nio.file.FileSystem newFileSystem(java.nio.file.Path path, Map<String, ?> env)
        throws IOException {
      return FileSystemConversions.decode(
          delegate.newFileSystem(PathConversions.encode(path), env));
    }

    @Override
    public InputStream newInputStream(java.nio.file.Path path, java.nio.file.OpenOption... options)
        throws IOException {
      return delegate.newInputStream(
          PathConversions.encode(path), OpenOptionConversions.encode(options));
    }

    @Override
    public OutputStream newOutputStream(
        java.nio.file.Path path, java.nio.file.OpenOption... options) throws IOException {
      return delegate.newOutputStream(
          PathConversions.encode(path), OpenOptionConversions.encode(options));
    }

    @Override
    public FileChannel newFileChannel(
        java.nio.file.Path path,
        Set<? extends java.nio.file.OpenOption> options,
        java.nio.file.attribute.FileAttribute<?>... attrs)
        throws IOException {
      return delegate.newFileChannel(
          PathConversions.encode(path),
          OpenOptionConversions.encode(options),
          FileAttributeConversions.encode(attrs));
    }

    @Override
    public java.nio.channels.AsynchronousFileChannel newAsynchronousFileChannel(
        java.nio.file.Path path,
        Set<? extends java.nio.file.OpenOption> options,
        ExecutorService executor,
        java.nio.file.attribute.FileAttribute<?>... attrs)
        throws IOException {
      return AsynchronousFileChannelConversions.decode(
          delegate.newAsynchronousFileChannel(
              PathConversions.encode(path),
              OpenOptionConversions.encode(options),
              executor,
              FileAttributeConversions.encode(attrs)));
    }

    @Override
    public java.nio.channels.SeekableByteChannel newByteChannel(
        java.nio.file.Path path,
        Set<? extends java.nio.file.OpenOption> options,
        java.nio.file.attribute.FileAttribute<?>... attrs)
        throws IOException {
      return SeekableByteChannelConversions.decode(
          delegate.newByteChannel(
              PathConversions.encode(path),
              OpenOptionConversions.encode(options),
              FileAttributeConversions.encode(attrs)));
    }

    @Override
    public java.nio.file.DirectoryStream<java.nio.file.Path> newDirectoryStream(
        java.nio.file.Path dir,
        java.nio.file.DirectoryStream.Filter<? super java.nio.file.Path> filter)
        throws IOException {
      return DirectoryStreamConversions.decode(
          delegate.newDirectoryStream(
              PathConversions.encode(dir), DirectoryStreamFilterConversions.encode(filter, null)),
          PathConversions::decode);
    }

    @Override
    public void createDirectory(
        java.nio.file.Path dir, java.nio.file.attribute.FileAttribute<?>... attrs)
        throws IOException {
      delegate.createDirectory(PathConversions.encode(dir), FileAttributeConversions.encode(attrs));
    }

    @Override
    public void createSymbolicLink(
        java.nio.file.Path link,
        java.nio.file.Path target,
        java.nio.file.attribute.FileAttribute<?>... attrs)
        throws IOException {
      delegate.createSymbolicLink(
          PathConversions.encode(link),
          PathConversions.encode(target),
          FileAttributeConversions.encode(attrs));
    }

    @Override
    public void createLink(java.nio.file.Path link, java.nio.file.Path existing)
        throws IOException {
      delegate.createLink(PathConversions.encode(link), PathConversions.encode(existing));
    }

    @Override
    public void delete(java.nio.file.Path path) throws IOException {
      try {
        delegate.delete(PathConversions.encode(path));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @Override
    public boolean deleteIfExists(java.nio.file.Path path) throws IOException {
      return delegate.deleteIfExists(PathConversions.encode(path));
    }

    @Override
    public java.nio.file.Path readSymbolicLink(java.nio.file.Path link) throws IOException {
      return PathConversions.decode(delegate.readSymbolicLink(PathConversions.encode(link)));
    }

    @Override
    public void copy(
        java.nio.file.Path source, java.nio.file.Path target, java.nio.file.CopyOption... options)
        throws IOException {
      delegate.copy(
          PathConversions.encode(source),
          PathConversions.encode(target),
          CopyOptionConversions.encode(options));
    }

    @Override
    public void move(
        java.nio.file.Path source, java.nio.file.Path target, java.nio.file.CopyOption... options)
        throws IOException {
      delegate.move(
          PathConversions.encode(source),
          PathConversions.encode(target),
          CopyOptionConversions.encode(options));
    }

    @Override
    public boolean isSameFile(java.nio.file.Path path, java.nio.file.Path path2)
        throws IOException {
      return delegate.isSameFile(PathConversions.encode(path), PathConversions.encode(path2));
    }

    @Override
    public boolean isHidden(java.nio.file.Path path) throws IOException {
      return delegate.isHidden(PathConversions.encode(path));
    }

    @Override
    public java.nio.file.FileStore getFileStore(java.nio.file.Path path) throws IOException {
      return FileStoreConversions.decode(delegate.getFileStore(PathConversions.encode(path)));
    }

    @Override
    public void checkAccess(java.nio.file.Path path, java.nio.file.AccessMode... modes)
        throws IOException {
      delegate.checkAccess(PathConversions.encode(path), AccessModeConversions.encode(modes));
    }

    @Override
    public <V extends java.nio.file.attribute.FileAttributeView> V getFileAttributeView(
        java.nio.file.Path path, Class<V> type, java.nio.file.LinkOption... options) {

      return AttributeViewConversions.decode(
          delegate.getFileAttributeView(
              PathConversions.encode(path),
              (Class<? extends j$.nio.file.attribute.FileAttributeView>)
                  AttributeViewConversions.encode(type),
              LinkOptionConversions.encode(options)),
          type);
    }

    @Override
    public <A extends java.nio.file.attribute.BasicFileAttributes> A readAttributes(
        java.nio.file.Path path, Class<A> type, java.nio.file.LinkOption... options)
        throws IOException {
      return BasicFileAttributesConversions.decode(
          delegate.readAttributes(
              PathConversions.encode(path),
              BasicFileAttributesConversions.encode(type),
              LinkOptionConversions.encode(options)),
          type);
    }

    @Override
    public Map<String, Object> readAttributes(
        java.nio.file.Path path, String attributes, java.nio.file.LinkOption... options)
        throws IOException {
      return JavaNioCentralConversions.decodeMapValue(
          delegate.readAttributes(
              PathConversions.encode(path), attributes, LinkOptionConversions.encode(options)));
    }

    @Override
    public void setAttribute(
        java.nio.file.Path path,
        String attribute,
        Object value,
        java.nio.file.LinkOption... options)
        throws IOException {
      delegate.setAttribute(
          PathConversions.encode(path),
          attribute,
          JavaNioCentralConversions.encode(value),
          LinkOptionConversions.encode(options));
    }
  }
}
