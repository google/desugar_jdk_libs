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

package desugar.sun.nio.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.nio.file.spi.FileTypeDetector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/** Linux implementation of {@link FileSystemProvider} for desugar support. */
public class DesugarLinuxFileSystemProvider extends FileSystemProvider {

  private static final String FILE_SCHEME = "file";
  private static final int DEFAULT_BUFFER_SIZE = 8192;

  private final String userDir;
  private final String rootDir;

  private volatile DesugarLinuxFileSystem theFileSystem;

  public static DesugarLinuxFileSystemProvider create() {
    return new DesugarLinuxFileSystemProvider(System.getProperty("user.dir"), "/");
  }

  DesugarLinuxFileSystemProvider(String userDir, String rootDir) {
    this.userDir = userDir;
    this.rootDir = rootDir;
  }

  @Override
  public String getScheme() {
    return FILE_SCHEME;
  }

  @Override
  public DesugarLinuxFileSystem newFileSystem(URI uri, Map<String, ?> env) {
    checkFileUri(uri);
    throw new FileSystemAlreadyExistsException();
  }

  @Override
  public final DesugarLinuxFileSystem getFileSystem(URI uri) {
    checkFileUri(uri);
    DesugarLinuxFileSystem fs = theFileSystem;
    if (fs == null) {
      synchronized (this) {
        fs = theFileSystem;
        if (fs == null) {
          theFileSystem = fs = new DesugarLinuxFileSystem(this, userDir, rootDir);
        }
      }
    }
    return fs;
  }

  @Override
  public Path getPath(URI uri) {
    return DesugarUnixUriUtils.fromUri(theFileSystem, uri, userDir, rootDir);
  }

  @Override
  public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter)
      throws IOException {
    File dirAsFile = dir.toFile();
    List<Path> listedFilePaths = new ArrayList<>();
    File[] files = dirAsFile.listFiles();
    if (files != null) {
      for (File file : files) {
        Path pathEntry = new DesugarUnixPath(theFileSystem, file.getPath(), userDir, rootDir);
        if (filter.accept(pathEntry)) {
          listedFilePaths.add(pathEntry);
        }
      }
    }

    return new PathCollectionBasedDirectoryStream(listedFilePaths);
  }

  @Override
  public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
    File dirFile = dir.toFile();
    boolean mkdirStatus = dirFile.mkdirs();
    if (!mkdirStatus) {
      throw new FileAlreadyExistsException(dir.toString());
    }
  }

  private boolean exists(Path file) {
    try {
      checkAccess(file);
      return true;
    } catch (IOException ioe) {
      return false;
    }
  }

  @Override
  public void delete(Path path) throws IOException {
    if (exists(path)) {
      deleteIfExists(path);
      return;
    }
    throw new IOException(String.format("Expected there exists %s before deletion.", path));
  }

  @Override
  public boolean deleteIfExists(Path path) throws IOException {
    return path.toFile().delete();
  }

  @Override
  public InputStream newInputStream(Path path, OpenOption... options) throws IOException {
    return new FileInputStream(path.toFile());
  }

  @Override
  public OutputStream newOutputStream(Path path, OpenOption... options) throws IOException {
    return new FileOutputStream(path.toFile());
  }

  @Override
  public DesugarSeekableByteChannel newByteChannel(
      Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
    if (path.toFile().isDirectory()) {
      throw new UnsupportedOperationException(
          "The desugar library does not support creating a file channel on a directory: " + path);
    }
    return DesugarSeekableByteChannel.create(path, options);
  }

  @Override
  public FileChannel newFileChannel(
      Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
    return newByteChannel(path, options, attrs).getFileChannel();
  }

  @Override
  public AsynchronousFileChannel newAsynchronousFileChannel(
      Path path,
      Set<? extends OpenOption> options,
      ExecutorService executor,
      FileAttribute<?>... attrs)
      throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void createLink(Path link, Path existing) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs)
      throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Path readSymbolicLink(Path link) throws IOException {
    return new DesugarUnixPath(theFileSystem, link.toFile().getCanonicalPath(), userDir, rootDir);
  }

  @Override
  public void copy(Path source, Path target, CopyOption... options) throws IOException {
    try (InputStream in = new FileInputStream(source.toFile());
        OutputStream out = new FileOutputStream(target.toFile())) {
      byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
      int read;
      while ((read = in.read(buffer, 0, DEFAULT_BUFFER_SIZE)) >= 0) {
        out.write(buffer, 0, read);
      }
    }
  }

  @Override
  public void move(Path source, Path target, CopyOption... options) throws IOException {
    File sourceFile = source.toFile();
    File targetFile = target.toFile();
    sourceFile.renameTo(targetFile);
  }

  @Override
  public boolean isSameFile(Path path, Path path2) throws IOException {
    return path.toFile().equals(path2.toFile());
  }

  @Override
  public boolean isHidden(Path path) throws IOException {
    return path.toFile().isHidden();
  }

  @Override
  public FileStore getFileStore(Path path) throws IOException {
    // For desugar: Follow Android SDK's custom implementation.
    // Android-changed: Complete information about file systems is neither available to regular
    // apps nor the system server due to SELinux policies.
    // return new LinuxFileStore(path);

    throw new SecurityException("getFileStore");
  }

  @Override
  public <V extends FileAttributeView> V getFileAttributeView(
      Path path, Class<V> type, LinkOption... options) {
    if (type == null) {
      throw new NullPointerException();
    }
    if (type == BasicFileAttributeView.class) {
      return type.cast(new DesugarBasicFileAttributeView(path));
    }
    return null;
  }

  @Override
  public void checkAccess(Path path, AccessMode... modes) throws IOException {
    File file = path.toFile();
    if (!file.exists()) {
      throw new NoSuchFileException(path.toString());
    }
    boolean permittedToAccess = file.exists();
    for (AccessMode accessMode : modes) {
      switch (accessMode) {
        case READ:
          permittedToAccess &= file.canRead();
          break;
        case WRITE:
          permittedToAccess &= file.canWrite();
          break;
        case EXECUTE:
          permittedToAccess &= file.canExecute();
          break;
      }
    }
    if (!permittedToAccess) {
      throw new IOException(String.format("Unable to access file %s", path));
    }
  }

  @Override
  public <A extends BasicFileAttributes> A readAttributes(
      Path path, Class<A> type, LinkOption... options) throws IOException {
    Class<? extends BasicFileAttributeView> view;
    if (type == BasicFileAttributes.class) {
      view = BasicFileAttributeView.class;
    } else {
      throw new UnsupportedOperationException();
    }
    return type.cast(getFileAttributeView(path, view, options).readAttributes());
  }

  @Override
  public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options)
      throws IOException {
    int attributesTypeIndexEnd = attributes.indexOf(":");
    final Class<? extends BasicFileAttributeView> attributeViewType;
    final String[] requestedAttributes;
    if (attributesTypeIndexEnd == -1) {
      attributeViewType = BasicFileAttributeView.class;
      requestedAttributes = attributes.split(",");
    } else {
      String attributeTypeSpec = attributes.substring(0, attributesTypeIndexEnd);
      if ("basic".equals(attributeTypeSpec)) {
        attributeViewType = BasicFileAttributeView.class;
      } else {
        throw new UnsupportedOperationException(
            String.format("Requested attribute type for: %s is not available.", attributeTypeSpec));
      }
      requestedAttributes = attributes.substring(attributesTypeIndexEnd + 1).split(",");
    }
    if (attributeViewType == BasicFileAttributeView.class) {
      DesugarBasicFileAttributeView attrView = new DesugarBasicFileAttributeView(path);
      return attrView.readAttributes(requestedAttributes);
    }
    throw new AssertionError("Unexpected View '" + attributeViewType + "' requested");
  }

  @Override
  public void setAttribute(Path path, String attribute, Object value, LinkOption... options)
      throws IOException {
    int attributesTypeIndexEnd = attribute.indexOf(":");
    final Class<? extends BasicFileAttributeView> attributeViewType;
    final String requestedAttribute;
    if (attributesTypeIndexEnd == -1) {
      attributeViewType = BasicFileAttributeView.class;
      requestedAttribute = attribute;
    } else {
      String attributeTypeSpec = attribute.substring(0, attributesTypeIndexEnd);
      if ("basic".equals(attributeTypeSpec)) {
        attributeViewType = BasicFileAttributeView.class;
      } else {
        throw new UnsupportedOperationException(
            String.format("Requested attribute type for: %s is not available.", attributeTypeSpec));
      }
      requestedAttribute = attribute.substring(attributesTypeIndexEnd + 1);
    }
    if (attributeViewType == BasicFileAttributeView.class) {
      DesugarBasicFileAttributeView attrView = new DesugarBasicFileAttributeView(path);
      attrView.setAttribute(requestedAttribute, value);
      return;
    }
    throw new AssertionError("Unexpected View '" + attributeViewType + "' requested");
  }

  FileTypeDetector getFileTypeDetector() {
    // For desugar: Follow Android SDK's custom implementation.
    // Android-changed: As libgio & libmagic is not available, GnomeFileTypeDetector and
    // MagicFileTypeDetector have been removed. MimeTypeFileDetector detects file type based
    // on the file extension, which may give false results.
    /*
    Path userMimeTypes = Paths.get(AccessController.doPrivileged(
        new GetPropertyAction("user.home")), ".mime.types");
    Path etcMimeTypes = Paths.get("/etc/mime.types");

    return chain(new GnomeFileTypeDetector(),
                 new MimeTypesFileTypeDetector(userMimeTypes),
                 new MimeTypesFileTypeDetector(etcMimeTypes),
                 new MagicFileTypeDetector());
    */
    return new DesugarMimeTypesFileTypeDetector();
  }

  private void checkFileUri(URI uri) {
    if (!uri.getScheme().equalsIgnoreCase(getScheme())) {
      throw new IllegalArgumentException("URI does not match this provider");
    }
    if (uri.getRawAuthority() != null) {
      throw new IllegalArgumentException("Authority component present");
    }
    String path = uri.getPath();
    if (path == null) {
      throw new IllegalArgumentException("Path component is undefined");
    }
    if (!path.equals("/")) {
      throw new IllegalArgumentException("Path component should be '/'");
    }
    if (uri.getRawQuery() != null) {
      throw new IllegalArgumentException("Query component present");
    }
    if (uri.getRawFragment() != null) {
      throw new IllegalArgumentException("Fragment component present");
    }
  }

  static class PathCollectionBasedDirectoryStream implements DirectoryStream<Path> {
    private final Collection<Path> paths;

    PathCollectionBasedDirectoryStream(Collection<Path> paths) {
      this.paths = paths;
    }

    @Override
    public Iterator<Path> iterator() {
      return paths.iterator();
    }

    @Override
    public void close() throws IOException {}
  }
}
