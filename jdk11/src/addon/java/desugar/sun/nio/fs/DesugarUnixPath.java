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

import static java.util.Arrays.stream;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** Linux implementation of {@link java.nio.file.Path} for desugar support. */
public class DesugarUnixPath implements Path {

  private static final String SEPARATOR = "/";
  private static final Pattern PATH_COMPONENT_SPLITERATOR = Pattern.compile("/+");

  private final FileSystem fileSystem;
  private final String pathText;
  private final List<String> fileNames;
  private final boolean isAbsolutePath;

  private final String userDir;
  private final String rootDir;

  // byte array representation (created lazily)
  private volatile byte[] byteArrayValue;

  public DesugarUnixPath(FileSystem fileSystem, String rawPath, String userDir, String rootDir) {
    this(fileSystem, rawPath.startsWith(SEPARATOR), getFileNames(rawPath), userDir, rootDir);
  }

  private DesugarUnixPath(
      FileSystem fileSystem,
      boolean isAbsolutePath,
      List<String> fileNames,
      String userDir,
      String rootDir) {
    this.fileSystem = fileSystem;
    this.isAbsolutePath = isAbsolutePath;
    this.fileNames = fileNames;

    this.pathText = getPathText(isAbsolutePath, fileNames);
    this.userDir = userDir;
    this.rootDir = rootDir;
  }

  private static List<String> getFileNames(String rawTextPath) {
    if (rawTextPath.isEmpty()) {
      return Collections.singletonList("");
    }
    return stream(PATH_COMPONENT_SPLITERATOR.split(rawTextPath))
        .filter(name -> !name.isEmpty())
        .collect(Collectors.toUnmodifiableList());
  }

  private static String getPathText(boolean isAbsolutePath, Collection<String> fileNames) {
    return (isAbsolutePath ? SEPARATOR : "") + String.join(SEPARATOR, fileNames);
  }

  @Override
  public FileSystem getFileSystem() {
    return fileSystem;
  }

  @Override
  public boolean isAbsolute() {
    return isAbsolutePath;
  }

  @Override
  public DesugarUnixPath getRoot() {
    if (isAbsolute()) {
      return new DesugarUnixPath(getFileSystem(), /* rawPath= */ rootDir, userDir, rootDir);
    } else {
      return null;
    }
  }

  DesugarUnixPath getUserDir() {
    return new DesugarUnixPath(getFileSystem(), /* rawPath= */ userDir, userDir, rootDir);
  }

  @Override
  public DesugarUnixPath getFileName() {
    return fileNames.isEmpty()
        ? null
        : new DesugarUnixPath(fileSystem, fileNames.get(getNameCount() - 1), userDir, rootDir);
  }

  @Override
  public DesugarUnixPath getParent() {
    int nameCount = getNameCount();
    if (nameCount == 0 || (nameCount == 1 && !isAbsolutePath)) {
      return null;
    }
    StringBuilder pathBuilder = new StringBuilder();
    if (isAbsolutePath) {
      pathBuilder.append(SEPARATOR);
    }
    String pathText = pathBuilder.append(subPathName(0, nameCount - 1)).toString();
    return new DesugarUnixPath(fileSystem, pathText, userDir, rootDir);
  }

  @Override
  public int getNameCount() {
    return fileNames.size();
  }

  @Override
  public DesugarUnixPath getName(int index) {
    if (index < 0 || index >= getNameCount()) {
      throw new IllegalArgumentException(
          String.format("Requested name for index (%d) is out of bound in \n%s.", index, this));
    }
    return new DesugarUnixPath(fileSystem, fileNames.get(index), userDir, rootDir);
  }

  @Override
  public DesugarUnixPath subpath(int beginIndex, int endIndex) {
    return new DesugarUnixPath(fileSystem, subPathName(beginIndex, endIndex), userDir, rootDir);
  }

  private String subPathName(int beginIndex, int endIndex) {
    return String.join(SEPARATOR, fileNames.subList(beginIndex, endIndex));
  }

  @Override
  public boolean startsWith(String other) {
    return startsWith(new DesugarUnixPath(fileSystem, other, userDir, rootDir));
  }

  @Override
  public boolean startsWith(Path other) {
    Objects.requireNonNull(other);
    if (!(other instanceof DesugarUnixPath)) {
      return false;
    }
    if (isAbsolute() != other.isAbsolute()) {
      return false;
    }
    int otherNameCount = other.getNameCount();
    if (getNameCount() < otherNameCount) {
      return false;
    }
    for (int i = 0; i < otherNameCount; i++) {
      if (!getName(i).equals(other.getName(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean endsWith(String other) {
    return endsWith(new DesugarUnixPath(fileSystem, other, userDir, rootDir));
  }

  @Override
  public boolean endsWith(Path other) {
    Objects.requireNonNull(other);
    if (!(other instanceof DesugarUnixPath)) {
      return false;
    }
    if (other.isAbsolute()) {
      return equals(other);
    }
    int otherNameCount = other.getNameCount();
    if (getNameCount() < otherNameCount) {
      return false;
    }
    int thisNameCount = getNameCount();
    for (int i = otherNameCount - 1; i >= 0; i--) {
      if (!getName(i - otherNameCount + thisNameCount).equals(other.getName(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof DesugarUnixPath)) {
      return false;
    }
    return compareTo((DesugarUnixPath) other) == 0;
  }

  @Override
  public int hashCode() {
     return pathText.hashCode();
  }

  @Override
  public int compareTo(Path other) {
    return pathText.compareTo(((DesugarUnixPath) other).pathText);
  }

  @Override
  public DesugarUnixPath normalize() {
    ArrayDeque<String> normalizedFileNames = new ArrayDeque<>();
    for (String fileName : fileNames) {
      switch (fileName) {
        case ".":
          break;
        case "..":
          normalizedFileNames.removeLast();
          break;
        default:
          normalizedFileNames.add(fileName);
          break;
      }
    }
    return new DesugarUnixPath(
        fileSystem, getPathText(isAbsolutePath, normalizedFileNames), userDir, rootDir);
  }

  @Override
  public DesugarUnixPath resolve(Path other) {
    if (!(other instanceof DesugarUnixPath)) {
      throw new IllegalArgumentException(
          String.format(
              "Expected to resolve paths on the same file system as DesugarUnixPath, but gets %s"
                  + " (%s).",
              other, other.getFileSystem()));
    }

    if (other.isAbsolute()) {
      return (DesugarUnixPath) other;
    }
    return new DesugarUnixPath(fileSystem, pathText + SEPARATOR + other, userDir, rootDir);
  }

  @Override
  public DesugarUnixPath resolveSibling(Path other) {
    if (!(Objects.requireNonNull(other) instanceof DesugarUnixPath)) {
      throw new IllegalArgumentException(
          String.format(
              "Expected to resolve paths on the same file system as DesugarUnixPath, but gets %s"
                  + " (%s).",
              other, other.getFileSystem()));
    }
    DesugarUnixPath parent = getParent();
    return (parent == null) ? (DesugarUnixPath) other : parent.resolve(other);
  }

  @Override
  public DesugarUnixPath resolveSibling(String other) {
    return resolveSibling(new DesugarUnixPath(fileSystem, other, userDir, rootDir));
  }

  @Override
  public DesugarUnixPath relativize(Path other) {
    if (!(other instanceof DesugarUnixPath)) {
      throw new IllegalArgumentException(
          String.format(
              "Expected to resolve paths on the same file system as DesugarUnixPath, but gets %s"
                  + " (%s).",
              other, other.getFileSystem()));
    }

    if (isAbsolute() != other.isAbsolute()) {
      throw new IllegalArgumentException("'other' is different type of Path in absolute property.");
    }

    List<String> otherFileNames = ((DesugarUnixPath) other).fileNames;

    int thisFileNameCount = fileNames.size();
    int otherFileNameCount = otherFileNames.size();

    int i = 0;
    while (i < thisFileNameCount
        && i < otherFileNameCount
        && fileNames.get(i).equals(otherFileNames.get(i))) {
      i++;
    }

    List<String> relativeFileNames = new ArrayList<>();
    for (int j = i; j < thisFileNameCount; j++) {
      relativeFileNames.add("..");
    }
    for (int j = i; j < otherFileNameCount; j++) {
      relativeFileNames.add(otherFileNames.get(j));
    }

    return new DesugarUnixPath(
        fileSystem, /* isAbsolutePath= */ false, relativeFileNames, userDir, rootDir);
  }

  @Override
  public File toFile() {
    return new File(toString());
  }

  @Override
  public URI toUri() {
    return DesugarUnixUriUtils.toUri(this);
  }

  @Override
  public DesugarUnixPath toAbsolutePath() {
    return isAbsolute() ? this : getUserDir().resolve(this);
  }

  @Override
  public DesugarUnixPath toRealPath(LinkOption... options) throws IOException {
    if (Arrays.asList(options).contains(LinkOption.NOFOLLOW_LINKS)) {
      return toAbsolutePath();
    }
    return new DesugarUnixPath(fileSystem, toFile().getCanonicalPath(), userDir, rootDir);
  }

  @Override
  public String toString() {
    return pathText;
  }

  byte[] asByteArray() {
    // OK if two or more threads create the same byte array.
    if (byteArrayValue == null) {
      byteArrayValue = pathText.getBytes(DesugarUtil.jnuEncoding());
    }
    return byteArrayValue;
  }

  // Desugar-keep: The desugar tools fails without explicitly overriding this method.
  @Override
  public Iterator<Path> iterator() {
    return Path.super.iterator();
  }

  @Override
  public WatchKey register(WatchService watcher, Kind<?>[] events, Modifier... modifiers)
      throws IOException {
    throw new UnsupportedOperationException("Watch Service is not supported");
  }
}
