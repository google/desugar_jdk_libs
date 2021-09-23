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

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helps compare openjdk_java_files contents against upstream file contents.
 *
 * Outputs a tab-separated table comparing each openjdk_java_files entry
 * against OpenJDK upstreams. This can help verify updates to later upstreams
 * or focus attention towards files that may have been missed in a previous
 * update (http://b/36461944) or are otherwise surprising (http://b/36429512).
 *
 * - Identifies each file as identical to, different from or missing from
 * each upstream; diffs are not produced.
 * - Optionally, copies all openjdk_java_files from the default upstream
 * (eg. OpenJDK8u121-b13) to a new directory, for easy directory comparison
 * using e.g. kdiff3, which allows inspecting detailed diffs.
 * - The ANDROID_BUILD_TOP environment variable must be set to point to the
 * AOSP root directory (parent of libcore).
 *
 * To check out upstreams OpenJDK 7u40, 8u60, 8u121-b13, 8u222-b01 and 9+181, run:
 *
 *  mkdir ~/openjdk
 *  cd ~/openjdk
 *  export OPENJDK_HOME=$PWD
 *  hg clone http://hg.openjdk.java.net/jdk7u/jdk7u40/ 7u40
 *  (cd !$ ; sh get_source.sh)
 *  hg clone http://hg.openjdk.java.net/jdk8u/jdk8u 8u121-b13
 *  (cd !$ ; hg update -r jdk8u121-b13 && sh get_source.sh && sh common/bin/hgforest.sh update -r jdk8u121-b13)
 *  hg clone http://hg.openjdk.java.net/jdk8u/jdk8u60/ 8u60
 *  (cd !$ ; sh get_source.sh)
 *  hg clone http://hg.openjdk.java.net/jdk8u/jdk8u 8u222-b01
 *  (cd !$ ; hg update -r jdk8u222-b01 && sh get_source.sh && sh common/bin/hgforest.sh update -r jdk8u222-b01)
 *  hg clone http://hg.openjdk.java.net/jdk9/jdk9/ 9+181
 *  (cd !$ ; hg update -r jdk-9+181 && sh get_source.sh && sh common/bin/hgforest.sh update -r jdk-9+181)
 *
 *  To get the 9b113+ upstream, follow the instructions from the commit
 *  message of AOSP libcore commit 29957558cf0db700bfaae360a80c42dc3871d0e5
 *  at https://android-review.googlesource.com/c/304056/
 *
 *  To get OpenJDK head: hg clone http://hg.openjdk.java.net/jdk/jdk/ head
 */
public class CompareUpstreams {

    /**
     * Whether to compare against snapshots based on (a) the output of {@link CopyUpstreamFiles},
     * as opposed to (b) directly against checked-out upstream source {@link Repository}s.
     *
     * Because the snapshots are currently kept on x20 which is slow to access, (b) run much
     * faster (a few seconds vs. 30 minutes), but it requires the checked-out and compiled
     * upstream repositories to exist which is not the case for everyone / not easily achievable
     * (OpenJDK 8 requires an old C++ compiler to build).
     */
    public static final boolean COMPARE_AGAINST_UPSTREAM_SNAPSHOT = true;

    private final StandardRepositories standardRepositories;

    public CompareUpstreams(StandardRepositories standardRepositories) {
        this.standardRepositories = Objects.requireNonNull(standardRepositories);
    }

    private static Map<String, Integer> androidChangedComments(List<String> lines) {
        List<String> problems = new ArrayList<>();
        Map<String, Integer> result = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile(
                "// (BEGIN |END |)Android-((?:changed|added|removed|note)(?:: )?.*)$");
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String type = matcher.group(1);
                if (type.equals("END")) {
                    continue;
                }
                String match = matcher.group(2);
                if (match.isEmpty()) {
                    match = "[empty comment]";
                }
                Integer oldCount = result.get(match);
                if (oldCount == null) {
                    oldCount = 0;
                }
                result.put(match, oldCount + 1);
            } else if (line.contains("Android-")) {
                problems.add(line);
            }
        }
        if (!problems.isEmpty()) {
            throw new IllegalArgumentException(problems.toString());
        }
        return result;
    }

    private static String androidChangedCommentsSummary(List<String> lines) {
        Map<String, Integer> map = androidChangedComments(lines);
        List<String> comments = new ArrayList<>(map.keySet());
        Collections.sort(comments, Comparator.comparing(map::get).reversed());
        List<String> result = new ArrayList<>();
        for (String comment : comments) {
            int count = map.get(comment);
            if (count == 1) {
                result.add(comment);
            } else {
                result.add(comment + " (x" + count + ")");
            }
        }
        return escapeTsv(String.join("\n", result));
    }

    private static String escapeTsv(String value) {
        if (value.contains("\t")) {
            throw new IllegalArgumentException(value); // tsv doesn't support escaping tabs
        }
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private static void printTsv(PrintStream out, List<String> values) {
        out.println(String.join("\t", values));
    }

    /**
     * Prints tab-separated values comparing ojluni files vs. each
     * upstream, for each of the rel_paths, suitable for human
     * analysis in a spreadsheet.
     * This includes whether the corresponding upstream file is
     * missing, identical, or by how many lines it differs, and
     * a guess as to the correct upstream based on minimal line
     * difference (ties broken in favor of upstreams that occur
     * earlier in the list).
     */
    private void run(PrintStream out, List<Path> relPaths) throws IOException {
        // upstreams are in decreasing order of preference
        List<String> headers = new ArrayList<>();
        headers.addAll(Arrays.asList(
                "rel_path", "expected_upstream", "guessed_upstream", "changes", "vs. expected"));
        for (Repository upstream : standardRepositories.historicUpstreams()) {
            headers.add(upstream.name());
        }
        headers.add("diff");
        printTsv(out, headers);

        Path snapshotRoot = COMPARE_AGAINST_UPSTREAM_SNAPSHOT
                ? Util.pathFromEnvOrThrow("OJLUNI_UPSTREAMS")
                : null;

        for (Path relPath : relPaths) {
            Repository expectedUpstream = standardRepositories.referenceUpstream(relPath);
            out.print(relPath + "\t");
            Path ojluniFile = standardRepositories.ojluni().absolutePath(relPath);
            List<String> linesB = Util.readLines(ojluniFile);
            int bestDistance = Integer.MAX_VALUE;
            Repository guessedUpstream = null;
            List<Repository> upstreams = new ArrayList<>();
            upstreams.add(expectedUpstream);
            upstreams.addAll(standardRepositories.historicUpstreams());
            List<String> comparisons = new ArrayList<>(upstreams.size());
            for (Repository upstream : upstreams) {
                final String comparison;
                final Path upstreamFile;
                if (COMPARE_AGAINST_UPSTREAM_SNAPSHOT) {
                    Path maybePath = snapshotRoot
                            .resolve(upstream.name())
                            .resolve(relPath);
                    upstreamFile = maybePath.toFile().exists() ? maybePath : null;
                } else {
                    upstreamFile = upstream.absolutePath(relPath);
                }
                if (upstreamFile == null) {
                    comparison = "missing";
                } else {
                    List<String> linesA = Util.readLines(upstreamFile);
                    int distance = Util.editDistance(linesA, linesB);
                    if (distance == 0) {
                        comparison = "identical";
                    } else {
                        double percentDifferent = 100.0 * distance / Math
                                .max(linesA.size(), linesB.size());
                        comparison = String
                                .format(Locale.US, "%.1f%% different (%d lines)", percentDifferent,
                                        distance);
                    }
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        guessedUpstream = upstream;
                    }
                }
                comparisons.add(comparison);
            }
            String changedCommentsSummary = androidChangedCommentsSummary(linesB);

            String diffCommand = "";
            if (!comparisons.get(0).equals("identical")) {
                Path expectedUpstreamPath = expectedUpstream.pathFromRepository(relPath);
                if (expectedUpstreamPath != null) {
                    diffCommand = "${ANDROID_BUILD_TOP}/libcore/tools/upstream/upstream-diff "
                            + "-r ojluni," + expectedUpstream.name() + " " + relPath;
                } else {
                    diffCommand = "FILE MISSING";
                }
            }
            List<String> values = new ArrayList<>();
            values.add(expectedUpstream.name());
            values.add(guessedUpstream == null ? "" : guessedUpstream.name());
            values.add(changedCommentsSummary);
            values.addAll(comparisons);
            values.add(diffCommand);
            printTsv(out, values);
        }
    }

    public void run() throws IOException {
        List<Path> relPaths = standardRepositories.ojluni().loadRelPathsFromBlueprint();
        run(System.out, relPaths);
    }

    public static void main(String[] args) throws IOException {
        StandardRepositories standardRepositories = StandardRepositories.fromEnv();
        CompareUpstreams action = new CompareUpstreams(standardRepositories);
        action.run();
    }
}
