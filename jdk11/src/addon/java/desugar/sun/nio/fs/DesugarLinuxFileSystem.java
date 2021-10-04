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

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/** Linux implementation of {@link FileSystemProvider} for desugar support. */
public class DesugarLinuxFileSystem extends FileSystem {

  public static final String SEPARATOR = "/";
  private static final String GLOB_SYNTAX = "glob";
  private static final String REGEX_SYNTAX = "regex";

  private final String userDir;
  private final String rootDir;

  private final DesugarLinuxFileSystemProvider provider;

  public DesugarLinuxFileSystem(
      DesugarLinuxFileSystemProvider provider, String userDir, String rootDir) {
    this.provider = provider;
    this.userDir = userDir;
    this.rootDir = rootDir;
  }

  @Override
  public FileSystemProvider provider() {
    return provider;
  }

  @Override
  public void close() throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isOpen() {
    return true;
  }

  @Override
  public boolean isReadOnly() {
    return false;
  }

  @Override
  public String getSeparator() {
    return SEPARATOR;
  }

  public String getUserDir() {
    return userDir;
  }

  public String getRootDir() {
    return rootDir;
  }

  @Override
  public Iterable<Path> getRootDirectories() {
    return List.of(
        new DesugarUnixPath(/* fileSystem= */ this, /* rawPath= */ SEPARATOR, userDir, rootDir));
  }

  @Override
  public Iterable<FileStore> getFileStores() {
    // TODO(b/192427790): Looking into an alternative implementation to support this feature.
    throw new UnsupportedOperationException("");
  }

  @Override
  public Set<String> supportedFileAttributeViews() {
    return Set.of("basic");
  }

  @Override
  public DesugarUnixPath getPath(String first, String... more) {
    String path;
    if (more.length == 0) {
      path = first;
    } else {
      StringBuilder sb = new StringBuilder();
      sb.append(first);
      for (String segment : more) {
        if (!segment.isEmpty()) {
          if (sb.length() > 0) {
            sb.append('/');
          }
          sb.append(segment);
        }
      }
      path = sb.toString();
    }
    return new DesugarUnixPath(this, path, userDir, rootDir);
  }

  @Override
  public PathMatcher getPathMatcher(String syntaxAndPattern) {
    int pos = syntaxAndPattern.indexOf(':');
    if (pos <= 0 || pos == syntaxAndPattern.length()) {
      throw new IllegalArgumentException(
          String.format(
              "Requested <syntax>:<pattern> spliterator(':') position(%d) is out of bound in %s",
              pos, syntaxAndPattern));
    }
    String syntax = syntaxAndPattern.substring(0, pos);
    String input = syntaxAndPattern.substring(pos + 1);

    String expr;
    if (syntax.equalsIgnoreCase(GLOB_SYNTAX)) {
      expr = DesugarGlobs.toUnixRegexPattern(input);
    } else {
      if (syntax.equalsIgnoreCase(REGEX_SYNTAX)) {
        expr = input;
      } else {
        throw new UnsupportedOperationException("Syntax '" + syntax + "' not recognized");
      }
    }

    // return matcher
    final Pattern pattern = Pattern.compile(expr);

    return path -> pattern.matcher(path.toString()).matches();
  }

  @Override
  public UserPrincipalLookupService getUserPrincipalLookupService() {
    // TODO(deltazulu): Support this API
    throw new UnsupportedOperationException();
  }

  @Override
  public WatchService newWatchService() throws IOException {
    // TODO(deltazulu): Support this API
    throw new UnsupportedOperationException();
  }
}
