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

import static com.google.common.truth.Truth.assertThat;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Unit Tests for {@link JarFileClassEntrySelector}. */
@RunWith(JUnit4.class)
public final class JarFileClassEntrySelectorTest {

  @Test
  public void matchTopLevelJavaTypes_emptyInputEntriesAndPatterns() throws IOException {
    Path input = createTestJarFile();
    Path output = Files.createTempFile("output", ".jar");

    LinkedHashSet<String> selectedEntryNames = new LinkedHashSet<>();
    JarFileClassEntrySelector selector =
        new JarFileClassEntrySelector(input, output, selectedEntryNames);
    selector.matchTopLevelJavaTypes(List.of());
    selector.sinkToOutput();

    assertThat(getJarEntries(output)).isEmpty();
  }

  @Test
  public void matchTopLevelJavaTypes_singleMatch() throws IOException {
    Path input = createTestJarFile("a/b/A.class");
    Path output = Files.createTempFile("output", ".jar");

    LinkedHashSet<String> selectedEntryNames = new LinkedHashSet<>();
    JarFileClassEntrySelector selector =
        new JarFileClassEntrySelector(input, output, selectedEntryNames);
    selector.matchTopLevelJavaTypes(List.of("a/b/A"));
    selector.sinkToOutput();

    assertThat(getJarEntries(output)).containsExactly("a/b/A.class");
  }

  @Test
  public void matchTopLevelJavaTypes_matchInnerClassesThroughOuterClass() throws IOException {
    Path input =
        createTestJarFile("a/b/A.class", "a/b/A$B.class", "a/b/A$B$C.class", "a/b/AB.class");
    Path output = Files.createTempFile("output", ".jar");

    LinkedHashSet<String> selectedEntryNames = new LinkedHashSet<>();
    JarFileClassEntrySelector selector =
        new JarFileClassEntrySelector(input, output, selectedEntryNames);
    selector.matchTopLevelJavaTypes(List.of("a/b/A"));
    selector.sinkToOutput();

    assertThat(getJarEntries(output))
        .containsExactly("a/b/A.class", "a/b/A$B.class", "a/b/A$B$C.class");
  }

  @Test
  public void matchTopLevelJavaTypes_matchClassPrefixes() throws IOException {
    Path input =
        createTestJarFile(
            "a/b/A.class", "a/b/A$B.class", "a/b/A$B$C.class", "a/b/AB.class", "a/b/c/D.class");
    Path output = Files.createTempFile("output", ".jar");

    LinkedHashSet<String> selectedEntryNames = new LinkedHashSet<>();
    JarFileClassEntrySelector selector =
        new JarFileClassEntrySelector(input, output, selectedEntryNames);
    selector.matchTopLevelJavaTypes(List.of("a/b/*"));
    selector.sinkToOutput();

    assertThat(getJarEntries(output))
        .containsExactly(
            "a/b/A.class", "a/b/A$B.class", "a/b/A$B$C.class", "a/b/AB.class", "a/b/c/D.class");
  }

  @Test
  public void matchTopLevelJavaTypes_comprehensiveMatching() throws IOException {
    Path input =
        createTestJarFile(
            "a/b/A.class",
            "a/b/A$B.class",
            "a/b/A$B$C.class",
            "a/b/AB.class",
            "a/b/c/D.class",
            "a/b/c/D$E.class",
            "a/b/c/D$E$F.class",
            "a/b/c/DE.class");

    Path output = Files.createTempFile("output", ".jar");

    LinkedHashSet<String> selectedEntryNames = new LinkedHashSet<>();
    JarFileClassEntrySelector selector =
        new JarFileClassEntrySelector(input, output, selectedEntryNames);
    selector.matchTopLevelJavaTypes(List.of("a/b/A*", "a/b/c/D"));
    selector.sinkToOutput();

    assertThat(getJarEntries(output))
        .containsExactly(
            "a/b/A.class",
            "a/b/A$B.class",
            "a/b/A$B$C.class",
            "a/b/AB.class",
            "a/b/c/D.class",
            "a/b/c/D$E.class",
            "a/b/c/D$E$F.class");
  }

  private static Path createTestJarFile(String... jarEntryNames) throws IOException {
    Path input = Files.createTempFile("input", ".jar");
    try (JarOutputStream jos = new JarOutputStream(Files.newOutputStream(input))) {
      for (String entryName : jarEntryNames) {
        jos.putNextEntry(new JarEntry(entryName));
        jos.closeEntry();
      }
    }
    return input;
  }

  private static List<String> getJarEntries(Path jarPath) throws IOException {
    JarFile jarFile = new JarFile(jarPath.toFile());
    return jarFile.stream().map(JarEntry::getName).collect(toList());
  }
}
