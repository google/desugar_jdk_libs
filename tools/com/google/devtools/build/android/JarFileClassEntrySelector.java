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

package com.google.devtools.build.android;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;

/**
 * A helper class that matches *.class entries in a JAR file and delivers the matched entries to a
 * new JAR file.
 *
 * <p>The matcher supports class name prefix matching and top-level-to-inner-class matching.
 */
public final class JarFileClassEntrySelector {

  private static final LocalDateTime DEFAULT_LOCAL_TIME =
      LocalDateTime.of(2010, 1, 1, 0, 0, 0).atZone(ZoneId.systemDefault()).toLocalDateTime();

  private static final String[] ANDROID_JDK11_LIB_TOP_LEVEL_TYPE_PATTERNS = {
    "java/io/DesugarBufferedReader",
    "java/io/UncheckedIOException",
    "java/lang/Desugar*",
    "java/lang/Iterable",
    "java/time/*",
    "java/util/AbstractList",
    "java/util/CollSer",
    "java/util/Collection",
    "java/util/Comparator",
    "java/util/Comparators",
    "java/util/Deque",
    "java/util/Desugar*",
    "java/util/DoubleSummaryStatistics",
    "java/util/ImmutableCollections",
    "java/util/IntSummaryStatistics",
    "java/util/Iterator",
    "java/util/KeyValueHolder",
    "java/util/List",
    "java/util/ListIterator",
    "java/util/LongSummaryStatistics",
    "java/util/Map",
    "java/util/NavigableMap",
    "java/util/NavigableSet",
    "java/util/Objects",
    "java/util/Optional*",
    "java/util/PrimitiveIterator",
    "java/util/Queue",
    "java/util/Set",
    "java/util/SortedMap",
    "java/util/SortedSet",
    "java/util/Spliterator",
    "java/util/Spliterators",
    "java/util/StringJoiner",
    "java/util/Tripwire",
    "java/util/concurrent/BlockingDeque",
    "java/util/concurrent/BlockingQueue",
    "java/util/concurrent/ConcurrentHashMap",
    "java/util/concurrent/ConcurrentMap",
    "java/util/concurrent/ConcurrentNavigableMap",
    "java/util/concurrent/Helpers",
    "java/util/concurrent/ThreadLocalRandom",
    "java/util/concurrent/TransferQueue",
    "java/util/concurrent/atomic/Desugar*",
    "java/util/function/*",
    "java/util/stream/*",
    "sun/misc/Desugar*",
    "jdk/internal/util/Preconditions",
  };

  private static final String[] ANDROID_CONCURRENT_FIX_LIB_TOP_LEVEL_TYPE_PATTERNS = {
    "java/util/concurrent/ConcurrentHashMap",
    "java/util/concurrent/Helpers",
    "java/util/concurrent/ThreadLocalRandom",
    "sun/misc/Desugar*"
  };

  private final Path inputJarPath;
  private final Path outputJarPath;
  private final LinkedHashSet<String> selectedEntryNames;

  public JarFileClassEntrySelector(
      Path inputJarPath, Path outputJarPath, LinkedHashSet<String> selectedEntryNames) {
    this.inputJarPath = inputJarPath;
    this.outputJarPath = outputJarPath;
    this.selectedEntryNames = selectedEntryNames;
  }

  /**
   * Finds all JAR entries whose associated classes match any of the specified top-level name
   * patterns.
   *
   * @param selectedTopLevelTypePatterns Either 1) the exact binary name of a class and matches both
   *     the outer class and its inner classes, or 2) When the pattern ends with a "*", The binary
   *     name prefix of a class and matches any class with its name starting with the given prefix.
   */
  public JarFileClassEntrySelector matchTopLevelJavaTypes(List<String> selectedTopLevelTypePatterns)
      throws IOException {
    JarFile jarFile = new JarFile(inputJarPath.toFile());
    // Sort the names of the input jar entries and the top-level patterns before two-way merge for
    // pattern matching.
    List<String> inputTypes =
        jarFile.stream()
            .map(JarEntry::getName)
            .filter(entryName -> entryName.endsWith(".class")) // only match class files.
            .sorted()
            .collect(toList());
    List<String> topLevelTypePatterns =
        selectedTopLevelTypePatterns.stream().sorted().distinct().collect(toList());
    Set<String> selectedEntryNames = new LinkedHashSet<>();
    int numOfInputTypes = inputTypes.size();
    int numOfTopLevelTypePatterns = topLevelTypePatterns.size();
    int i = 0;
    int j = 0;
    int comparisonStatus = -1;
    while (i < numOfInputTypes && j < numOfTopLevelTypePatterns) {
      String inputType = inputTypes.get(i);
      String topLevelPattern = topLevelTypePatterns.get(j);
      comparisonStatus = compareAgainstTopLevelPattern(inputType, topLevelPattern);
      if (comparisonStatus < 0) {
        i++;
      } else if (comparisonStatus == 0) {
        selectedEntryNames.add(inputType);
        i++;
      } else {
        j++;
      }
    }
    // Expected all given top-level patterns exhausted during matching.
    boolean isPostMatchingValid =
        (j == numOfTopLevelTypePatterns)
            || (comparisonStatus == 0 && j == numOfTopLevelTypePatterns - 1);
    if (!isPostMatchingValid) {
      throw new IllegalArgumentException(
          String.format(
              "Expected all top-level patterns are consumed for entry matching. %s, %s",
              j, String.join("\n", selectedEntryNames)));
    }
    this.selectedEntryNames.addAll(selectedEntryNames);
    return this;
  }

  public JarFileClassEntrySelector sinkToOutput() throws IOException {
    try (JarInputStream in = new JarInputStream(Files.newInputStream(inputJarPath));
        JarOutputStream out = new JarOutputStream(Files.newOutputStream(outputJarPath))) {
      for (JarEntry inEntry; (inEntry = in.getNextJarEntry()) != null; ) {
        String inEntryName = inEntry.getName();
        if (selectedEntryNames.contains(inEntryName)) {
          byte[] entryBytes = in.readAllBytes();
          out.putNextEntry(createJarEntry(inEntryName, entryBytes));
          out.write(entryBytes);
          out.closeEntry();
        }
      }
    }
    selectedEntryNames.clear();
    return this;
  }

  private static JarEntry createJarEntry(String entryName, byte[] bytes) {
    JarEntry je = new JarEntry(entryName);
    je.setTimeLocal(DEFAULT_LOCAL_TIME);
    je.setMethod(JarEntry.STORED);
    int byteLength = bytes.length;
    je.setSize(byteLength);
    CRC32 checksum = new CRC32();
    checksum.update(bytes);
    je.setCrc(checksum.getValue());
    return je;
  }

  /**
   * The comparator helper for merging two sorted list Jar entry names and pattern names for
   * matching. A return value of zero indicates a match.
   */
  private static int compareAgainstTopLevelPattern(String inputEntryName, String topLevelPattern) {
    if (topLevelPattern.endsWith("*")) {
      String prefix = topLevelPattern.substring(0, topLevelPattern.length() - 1);
      return inputEntryName.startsWith(prefix) ? 0 : inputEntryName.compareTo(prefix);
    }
    return inputEntryName.equals(topLevelPattern + ".class")
            || inputEntryName.startsWith(topLevelPattern + "$")
        ? 0
        : inputEntryName.compareTo(topLevelPattern + ".class");
  }

  public static void main(String[] args) throws IOException {
    // TODO(deltazulu): Choose a flag library to handle command-line argument parsing.
    if (args.length < 2) {
      throw new IllegalArgumentException(
          "Expected input jar path and output jar path to be specified.");
    }
    Path inPath = Paths.get(args[0]);
    Path outPath = Paths.get(args[1]);
    List<String> patterns = new ArrayList<>();
    if (args.length >= 3 && "--config=android_fix_libs".equals(args[2])) {
      Collections.addAll(patterns, ANDROID_CONCURRENT_FIX_LIB_TOP_LEVEL_TYPE_PATTERNS);
    } else {
      Collections.addAll(patterns, ANDROID_JDK11_LIB_TOP_LEVEL_TYPE_PATTERNS);
    }
    JarFileClassEntrySelector jarFileClassSelector =
        new JarFileClassEntrySelector(inPath, outPath, new LinkedHashSet<>());
    jarFileClassSelector.matchTopLevelJavaTypes(patterns);
    jarFileClassSelector.sinkToOutput();
  }
}
