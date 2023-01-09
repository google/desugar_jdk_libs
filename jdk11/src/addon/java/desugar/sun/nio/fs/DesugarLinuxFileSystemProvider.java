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
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.nio.file.spi.FileTypeDetector;
import java.util.Iterator;
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
    if (filter == null) {
      throw new NullPointerException();
    }
    return new DesugarDirectoryStream(dir, filter);
  }

  @Override
  public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
    if (dir.getParent() != null && !Files.exists(dir.getParent())) {
      throw new NoSuchFileException(dir.toString());
    }
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
    throw new NoSuchFileException(path.toString());
  }

  @Override
  public boolean deleteIfExists(Path path) throws IOException {
    return path.toFile().delete();
  }

  @Override
  public SeekableByteChannel newByteChannel(
      Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
    // A FileChannel is a SeekableByteChannel.
    return newFileChannel(path, options, attrs);
  }

  @Override
  public FileChannel newFileChannel(
      Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
    if (path.toFile().isDirectory()) {
      throw new UnsupportedOperationException(
          "The desugar library does not support creating a file channel on a directory: " + path);
    }
    return DesugarFileChannel.openEmulatedFileChannel(path, options, attrs);
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
    if (!containsCopyOption(options, StandardCopyOption.REPLACE_EXISTING) && Files.exists(target)) {
      throw new FileAlreadyExistsException(target.toString());
    }
    if (containsCopyOption(options, StandardCopyOption.ATOMIC_MOVE)) {
      throw new UnsupportedOperationException("Unsupported copy option");
    }
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
    if (!containsCopyOption(options, StandardCopyOption.REPLACE_EXISTING) && Files.exists(target)) {
      throw new FileAlreadyExistsException(target.toString());
    }
    if (containsCopyOption(options, StandardCopyOption.COPY_ATTRIBUTES)) {
      throw new UnsupportedOperationException("Unsupported copy option");
    }
    File sourceFile = source.toFile();
    File targetFile = target.toFile();
    sourceFile.renameTo(targetFile);
  }

  private boolean containsCopyOption(CopyOption[] options, CopyOption option) {
    for (CopyOption copyOption : options) {
      if (copyOption == option) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isSameFile(Path path, Path path2) throws IOException {
    // If the paths are equals, then it answers true even if they do not exist.
    if (path.equals(path2)) {
      return true;
    }
    // If the paths are not equal, they could still be equal due to symbolic link and so on, but
    // in that case accessibility is checked.
    checkAccess(path);
    checkAccess(path2);
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
    boolean permittedToAccess = true;
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

  class DesugarPathIterator implements Iterator<Path> {

    private final Filter<? super Path> filter;
    private final File[] candidates;
    private int index = 0;

    DesugarPathIterator(Path dir, Filter<? super Path> filter) {
      // We compute the list of files upfront instead of lazily, which can lead to exceptions
      // being raised at a slightly different time.
      File[] theCandidates = dir.toFile().listFiles();
      this.candidates = theCandidates == null ? new File[] {} : theCandidates;
      this.filter = filter;
    }

    @Override
    public boolean hasNext() {
      if (next() != null) {
        index--;
        return true;
      }
      return false;
    }

    @Override
    public Path next() {
      // Look for the next matching path, if none, return null;
      for (; ; ) {
        if (index >= candidates.length) {
          return null;
        }
        File nextFile = candidates[index++];
        Path pathEntry = new DesugarUnixPath(theFileSystem, nextFile.getPath(), userDir, rootDir);
        boolean accept;
        try {
          accept = filter.accept(pathEntry);
        } catch (IOException ioe) {
          throw new DirectoryIteratorException(ioe);
        }
        if (accept) {
          return pathEntry;
        }
      }
    }
  }

  class DesugarDirectoryStream implements DirectoryStream<Path> {

    DesugarPathIterator iterator;

    DesugarDirectoryStream(Path dir, Filter<? super Path> filter) {
      this.iterator = new DesugarPathIterator(dir, filter);
    }

    @Override
    public Iterator<Path> iterator() {
      return iterator;
    }

    @Override
    public void close() throws IOException {}
  }
}
