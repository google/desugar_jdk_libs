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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CopyUpstreamFiles {

    private final StandardRepositories standardRepositories;
    private final Path outputDir;

    private CopyUpstreamFiles(StandardRepositories standardRepositories, Path outputDir) {
        this.standardRepositories = Objects.requireNonNull(standardRepositories);
        this.outputDir = Objects.requireNonNull(outputDir);
    }

    public void run() throws IOException {
        List<Path> relPaths = standardRepositories.ojluni().loadRelPathsFromBlueprint();
        if (outputDir.toFile().exists()) {
            throw new IOException(outputDir + " already exists");
        } else {
            boolean success = outputDir.toFile().mkdir();
            if (!success) {
                throw new IOException("Failed to create directory " + outputDir);
            }
        }
        for (Path relPath : relPaths) {
            Repository expectedUpstream = standardRepositories.referenceUpstream(relPath);
            for (Repository upstream : standardRepositories.upstreams()) {
                Path upstreamFile = upstream.absolutePath(relPath);
                if (upstreamFile != null) {
                    Path outputFile = outputDir
                            .resolve(upstream.name())
                            .resolve(relPath);
                    copyFile(upstreamFile, outputFile);
                    if (upstream.equals(expectedUpstream)) {
                        copyFile(upstreamFile, outputDir.resolve("expected").resolve(relPath));
                    }
                }
            }
        }
    }

    private void copyFile(Path from, Path to) throws IOException {
        if (!from.toFile().canRead()) {
            throw new IOException("Error reading " + from);
        }
        Path toDir = to.getParent();
        if (!toDir.toFile().exists()) {
            boolean success = toDir.toFile().mkdirs();
            if (!success) {
                throw new IOException("Failed to create directory " + toDir);
            }
        }
        Files.copy(from, to);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException(Arrays.asList(args).toString());
        }
        Path outputDir = new File(args[0]).toPath();
        StandardRepositories standardRepositories = StandardRepositories.fromEnv();
        new CopyUpstreamFiles(standardRepositories, outputDir).run();
    }
}
