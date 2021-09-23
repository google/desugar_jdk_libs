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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for dealing with text file contents.
 */
class Util {
    private Util() {
    }

    public static Path pathFromEnvOrThrow(String name) {
        String envValue = getEnvOrThrow(name);
        Path result = Paths.get(envValue);
        if (!result.toFile().exists()) {
            throw new IllegalArgumentException("Path not found: " + result);
        }
        return result;
    }

    private static String getEnvOrThrow(String name) {
        String result = System.getenv(name);
        if (result == null) {
            throw new IllegalStateException("Environment variable undefined: " + name);
        }
        return result;
    }

    public static Lines readLines(Reader reader) throws IOException {
        List<String> result = new ArrayList<>();
        BufferedReader br = (reader instanceof BufferedReader)
                ? (BufferedReader) reader : new BufferedReader(reader);
        String line;
        while ((line = br.readLine()) != null) {
            result.add(line);
        }
        return new Lines(result);
    }

    public static Lines readLines(Path path) throws IOException {
        try (Reader reader = new FileReader(path.toFile())) {
            return readLines(reader);
        }
    }

    public static void writeLines(Path path, Lines lines) throws IOException {
        try (PrintStream printStream = new PrintStream(path.toFile())) {
            for (String line : lines) {
                printStream.println(line);
            }
        }
    }

    /**
     * Computes the edit distance of two lists, i.e. the smallest number of list items to delete,
     * insert or replace that would transform the content of one list into the other.
     */
    public static <T> int editDistance(List<T> a, List<T> b) {
        if (a.equals(b)) {
            return 0;
        }
        int numB = b.size();
        int[] prevCost = new int[numB + 1];
        for (int i = 0; i <= numB; i++) {
            prevCost[i] = i;
        }
        int[] curCost = new int[numB + 1];
        for (int endA = 1; endA <= a.size(); endA++) {
            // For each valid index i, prevCost[i] is the edit distance between
            // a.subList(0, endA-1) and b.sublist(0, i).
            // We now calculate curCost[end_b] as the edit distance between
            // a.subList(0, endA) and b.subList(0, endB)
            curCost[0] = endA;
            for (int endB = 1; endB <= numB; endB++) {
                boolean endsMatch = a.get(endA - 1).equals(b.get(endB - 1));
                curCost[endB] = min(
                        curCost[endB - 1] + 1, // append item from b
                        prevCost[endB] + 1, // append item from a
                        prevCost[endB - 1] + (endsMatch ? 0 : 1)); // match or replace item
            }
            int[] tmp = curCost;
            curCost = prevCost;
            prevCost = tmp;
        }
        return prevCost[numB];
    }

    private static int min(int a, int b, int c) {
        if (a < b) {
            return a < c ? a : c;
        } else {
            return b < c ? b : c;
        }
    }

}
