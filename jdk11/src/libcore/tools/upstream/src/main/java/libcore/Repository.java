/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package libcore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A set of .java files (either from ojluni or from an upstream).
 */
abstract class Repository {

    /**
     * Maps from a file's (current) relPath to the corresponding OpenJDK relPath from
     * which it has been, and still remains, renamed.
     */
    static final Map<Path, Path> OPENJDK_REL_PATH = historicRenames();

    static Map<Path, Path> historicRenames() {
        Map<Path, Path> result = new HashMap<>();
        // renamed in libcore commit 583eb0e4738456f0547014a4857a14456be267ee
        result.put(Paths.get("native/linux_close.cpp"), Paths.get("native/linux_close.c"));
        // Map ByteBufferAs*Buffer.java to an upstream file, even though there is
        // not a 1:1 correspondence. This isn't perfect, but allows some rough
        // comparison. See http://b/111583940
        //
        // More detail:
        // The RI has four different generated files ...Buffer{B,L,RB,RL}.java
        // for each of these six files specializing on big endian, little endian,
        // read-only big endian, and read-only little endian, respectively. Those
        // 6 x 4 files are generated from a single template:
        //     java/nio/ByteBufferAs-X-Buffer.java.template
        //
        // On Android, the four variants {B,L,RB,RL} for each of the six types
        // are folded into a single class with behavior configured via additional
        // constructor arguments.
        //
        // For now, we map to upstream's "B" variant; "B" is more similar to
        // Android's files than "RB" or "RL"; the choice of "B" vs. "L" is arbitrary.
        for (String s : Arrays.asList("Char", "Double", "Float", "Int", "Long", "Short")) {
            Path ojluniPath = Paths.get("java/nio/ByteBufferAs" + s + "Buffer.java");
            Path upstreamPath =
                    Paths.get("java/nio/ByteBufferAs" + s + "BufferB.java");
            result.put(ojluniPath, upstreamPath);
        }
        return Collections.unmodifiableMap(result);
    }

    protected final Path rootPath;
    protected final String name;
    protected final List<String> sourceDirs;

    protected Repository(Path rootPath, String name, List<String> sourceDirs) {
        this.rootPath = Objects.requireNonNull(rootPath);
        this.name = Objects.requireNonNull(name);
        this.sourceDirs = Objects.requireNonNull(sourceDirs);
        if (!rootPath.toFile().isDirectory()) {
            throw new IllegalArgumentException("Missing or not a directory: " + rootPath);
        }
    }

    /**
     * @param relPath a relative path of a .java file in the repository, e.g.
     *        "java/util/ArrayList.java".
     * @return the path of the indicated file (either absolute, or relative to the current
     *         working directory), or null if the file does not exist in this Repository.
     */
    public final Path absolutePath(Path relPath) {
        Path p = pathFromRepository(relPath);
        return p == null ? null : rootPath.resolve(p).toAbsolutePath();
    }

    public Path pathFromRepository(Path relPath) {
        // Search across all sourceDirs for the indicated file.
        for (String sourceDir : sourceDirs) {
            Path repositoryRelativePath = Paths.get(sourceDir).resolve(relPath);
            File file = rootPath.resolve(repositoryRelativePath).toFile();
            if (file.exists()) {
                return repositoryRelativePath;
            }
        }
        return null;
    }

    public final Path rootPath() {
        return rootPath;
    }

    @Override
    public int hashCode() {
      return rootPath.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return (obj instanceof Repository) && rootPath.equals(((Repository) obj).rootPath);
    }

    /**
     * @return A human readable name to identify this repository, suitable for use as a
     *         directory name.
     */
    public final String name() {
        return name;
    }

    @Override
    public String toString() {
        return name() + " repository";
    }

    /**
     * A checkout of the hg repository of OpenJDK 9 or higher, located in the
     * subdirectory {@code upstreamName} under the directory {@code upstreamRoot}.
     */
    public static Repository openJdk9(Path upstreamRoot, String upstreamName) {
        List<String> sourceDirs = Arrays.asList(
            "jdk/src/java.base/share/classes",
            "jdk/src/java.logging/share/classes",
            "jdk/src/java.prefs/share/classes",
            "jdk/src/java.sql/share/classes",
            "jdk/src/java.desktop/share/classes",
            "jdk/src/java.base/solaris/classes",
            "jdk/src/java.base/unix/classes",
            "jdk/src/java.prefs/unix/classes",
            "jdk/src/jdk.unsupported/share/classes",
            "jdk/src/jdk.net/share/classes",
            "jdk/src/java.base/linux/classes",
            "build/linux-x86_64-normal-server-release/support/gensrc/java.base",

            // Native (.c) files
            "jdk/src/java.base/unix/native/libjava",
            "jdk/src/java.base/share/native/libjava",
            "jdk/src/java.base/unix/native/libnio",
            "jdk/src/java.base/unix/native/libnio/ch",
            "jdk/src/java.base/unix/native/libnio/fs",
            "jdk/src/java.base/unix/native/libnet"
        );
        return new OpenJdkRepository(upstreamRoot, upstreamName, sourceDirs);
    }

    /**
     * A checkout of the hg repository of OpenJDK 8 or earlier, located in the
     * subdirectory {@code upstreamName} under the directory {@code upstreamRoot}.
     */
    public static Repository openJdkLegacy(Path upstreamRoot, String upstreamName) {
        List<String> sourceDirs = new ArrayList<>();
        sourceDirs.addAll(Arrays.asList(
            "jdk/src/share/classes",
            "jdk/src/solaris/classes",
            "build/linux-x86_64-normal-server-release/jdk/gensrc"
        ));

        // In legacy OpenJDK versions, the source files are organized into a subfolder
        // hierarchy based on package name, whereas in Android and OpenJDK 9+ they're in
        // a flat folder. We work around this by just searching through all of the
        // applicable folders (from which we have sources) in legacy OpenJDK versions.
        List<String> nativeSourceDirs = new ArrayList<>();
        List<String> pkgPaths = Arrays.asList("", "java/io", "java/lang", "java/net", "java/nio",
            "java/util", "java/util/zip", "sun/nio/ch", "sun/nio/fs");
        for (String pkgPath : pkgPaths) {
            nativeSourceDirs.add("jdk/src/solaris/native/" + pkgPath);
            nativeSourceDirs.add("jdk/src/share/native/" + pkgPath);
            nativeSourceDirs.add("jdk/src/solaris/native/common/" + pkgPath);
            nativeSourceDirs.add("jdk/src/share/native/common/" + pkgPath);
        }
        sourceDirs.addAll(nativeSourceDirs);

        return new OpenJdkRepository(upstreamRoot, upstreamName, sourceDirs);
    }

    /**
     * Checkouts of hg repositories of OpenJDK 8 or earlier, located in the
     * respective {@code upstreamNames} subdirectories under the join parent
     * directory {@code upstreamRoot}.
     */
    public static List<Repository> openJdkLegacy(Path upstreamRoot, List<String> upstreamNames) {
        List<Repository> result = new ArrayList<>();
        for (String upstreamName : upstreamNames) {
            result.add(openJdkLegacy(upstreamRoot, upstreamName));
        }
        return Collections.unmodifiableList(result);
    }

    static class OjluniRepository extends Repository {
        /**
         * The repository of ojluni java files belonging to the Android sources under
         * {@code buildTop}.
         *
         * @param buildTop The root path of an Android checkout, as identified by the
         *        {@quote ANDROID_BUILD_TOP} environment variable.
         */
        public OjluniRepository(Path buildTop) {
            super(buildTop.resolve("libcore"), "ojluni",
                /* sourceDirs */ Arrays.asList("ojluni/src/main/java", "ojluni/src/main/native"));
        }


        @Override
        public Path pathFromRepository(Path relPath) {
            // Enforce that the file exists in ojluni
            return Objects.requireNonNull(super.pathFromRepository(relPath));
        }

        /**
         * Returns the list of relative paths to files parsed from blueprint files.
         */
        public List<Path> loadRelPathsFromBlueprint() throws IOException {
            List<Path> result = new ArrayList<>();
            result.addAll(loadOrderedRelPathsSetFromBlueprint(
                "openjdk_java_files.bp", "\"ojluni/src/main/java/(.+\\.java)\""));
            result.addAll(loadOrderedRelPathsSetFromBlueprint(
                "ojluni/src/main/native/Android.bp", "\\s+\"(.+\\.(?:c|cpp))\","));
            return result;
        }

        private Set<Path> loadOrderedRelPathsSetFromBlueprint(
            String blueprintPathString, String patternString) throws IOException {
            Path blueprintPath = rootPath.resolve(blueprintPathString);
            Pattern pattern = Pattern.compile(patternString);
            // Use TreeSet to sort and de-duplicate the result.
            Set<Path> result = new TreeSet<>();
            for (String line : Util.readLines(blueprintPath)) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    Path relPath = Paths.get(matcher.group(1));
                    result.add(relPath);
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return "libcore ojluni";
        }
    }

    static class OpenJdkRepository extends Repository {

        public OpenJdkRepository(Path upstreamRoot, String name, List<String> sourceDirs) {
            super(upstreamRoot.resolve(name), name, sourceDirs);
        }

        @Override
        public Path pathFromRepository(Path relPath) {
            if (OPENJDK_REL_PATH.containsKey(relPath)) {
                relPath = OPENJDK_REL_PATH.get(relPath);
            }
            return super.pathFromRepository(relPath);
        }

        @Override
        public String toString() {
            return "OpenJDK " + name;
        }
    }

}
