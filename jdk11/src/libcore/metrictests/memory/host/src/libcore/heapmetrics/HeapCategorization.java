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

package libcore.heapmetrics;

import com.android.ahat.heapdump.AhatClassObj;
import com.android.ahat.heapdump.AhatHeap;
import com.android.ahat.heapdump.AhatInstance;
import com.android.ahat.heapdump.AhatSnapshot;
import com.android.ahat.heapdump.RootType;
import com.android.ahat.heapdump.Size;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Representation of the break-down of a heap dump into categories.
 */
class HeapCategorization
{

    /**
     * Enumeration of the categories used.
     */
    enum HeapCategory {

        /**
         * Interned strings that are mostly ASCII alphabetic characters, and have a bit of
         * whitespace. These are probably human-readable text e.g. error messages.
         */
        INTERNED_STRING_TEXT_ISH("internedStringTextIsh"),

        /**
         * Interned strings that are mostly non-ASCII alphabetic characters. These are probably ICU
         * data.
         */
        INTERNED_STRING_UNICODE_ALPHABET_ISH("internedStringUnicodeAlphabetIsh"),

        /**
         * Interned strings that are don't meet the criterea of {@link #INTERNED_STRING_TEXT_ISH} or
         * {@link #INTERNED_STRING_UNICODE_ALPHABET_ISH}. These are probably code e.g. a regex.
         */
        INTERNED_STRING_CODE_ISH("internedStringCodeIsh"),

        /** Objects in a {@code android.icu} package, or strongly reachable from such an object. */
        PACKAGE_ANDROID_ICU("packageAndroidIcu"),

        /** Objects in a {@code android.util} or {@code com.internal.android.util} package, or
         * strongly reachable from such an object. */
        PACKAGE_ANDROID_UTIL("packageAndroidUtil"),

        /**
         * Objects in a {@code android} package other than {@code android.icu} or
         * {@code android.util}, or a {@code com.android.internal} package other than
         * {@code com.android.internal.util}, or strongly reachable from such an object. Includes
         * {@code app}, {@code widget}, {@code graphics}, {@code os}, and many more.
         */
        ANDROID_FRAMEWORK("androidFramework"),

        /**
         * Objects in a {@code java.security}, {@code sun.security},
         * {@code com.android.org.conscrypt}, or {@code com.android.org.bouncycastle} package, or
         * strongly reachable from such an object.
         */
        SECURITY("security"),

        /**
         * Objects in a {@code com.android.org.conscrypt} package, or strongly reachable from such
         * an object.
         */
        SECURITY_CONSCRYPT("securityConscrypt"),

        /**
         * Objects in a {@code com.android.org.bouncycastle} package, or strongly reachable from
         * such an object.
         */
        SECURITY_BOUNCYCASTLE("securityBouncycastle"),

        /**
         * Objects in a {@code java.security.keystore} package, or strongly reachable from such an
         * object.
         */
        SECURITY_KEYSTORE("securityKeystore"),

        /**
         * Objects in a {@code java}, {@code javax}, {@code sun}, {@code com.sun}, or
         * {@code libcore} package, and strongly reachable only from such objects (i.e. the entire
         * reference graph is libcore). Excludes interned strings (which are in {@code java.lang}
         * and have no references).
         */
        PURE_LIBCORE("pureLibcore"),

        /**
         * The subset of {@link #PURE_LIBCORE} which is static, rather than instance, state.
         */
        PURE_LIBCORE_STATIC("pureLibcoreStatic"),

        /**
         * Objects which don't fall into any of the above categories. (N.B. This ensures that every
         * object is in at least one category, but objects may be in more than one of the above.)
         */
        NONE_OF_THE_ABOVE("noneOfTheAbove"),
        ;

        private final String metricSuffix;

        HeapCategory(String metricSuffix) {
            this.metricSuffix = metricSuffix;
        }

        /**
         * Returns the name for a metric using the given prefix and a category-specific suffix.
         */
        String metricName(String metricPrefix) {
            return metricPrefix + metricSuffix;
        }
    }

    /**
     * Returns the categorization of the given heap dump, counting the retained sizes on the given
     * heaps.
     */
    static HeapCategorization of(AhatSnapshot snapshot, AhatHeap... heaps) {
        HeapCategorization categorization = new HeapCategorization(snapshot, heaps);
        categorization.initializeFromSnapshot();
        return categorization;
    }

    private final Map<HeapCategory, Size> sizesByCategory = new HashMap<>();
    private final AhatSnapshot snapshot;
    private final AhatHeap[] heaps;

    private HeapCategorization(AhatSnapshot snapshot, AhatHeap[] heaps) {
        this.snapshot = snapshot;
        this.heaps = heaps;
    }

    /**
     * Returns an analysis of the configured heap dump, giving the retained sizes on the configured
     * heaps broken down by category.
     */
    Map<HeapCategory, Size> sizesByCategory() {
        return Collections.unmodifiableMap(sizesByCategory);
    }

    private void initializeFromSnapshot() {
        for (AhatInstance rooted : snapshot.getRooted()) {
            initializeFromRooted(rooted);
        }
    }

    private void initializeFromRooted(AhatInstance rooted) {
        int categories = 0;
        if (isInternedString(rooted)) {
            HeapCategory category = categorizeInternedString(rooted.asString());
            incrementSize(rooted, category);
            categories++;
        }

        if (isOwnedByClassMatching(rooted, str -> str.startsWith("android.icu."))) {
            incrementSize(rooted, HeapCategory.PACKAGE_ANDROID_ICU);
            categories++;
        }
        if (isOwnedByClassMatching(rooted, this::isAndroidUtilClass)) {
            incrementSize(rooted, HeapCategory.PACKAGE_ANDROID_UTIL);
            categories++;
        }
        if (isOwnedByClassMatching(rooted, this::isAndroidFrameworkClass)) {
            incrementSize(rooted, HeapCategory.ANDROID_FRAMEWORK);
            categories++;
        }
        if (isOwnedByClassMatching(rooted, this::isSecurityClass)) {
            incrementSize(rooted, HeapCategory.SECURITY);
            categories++;
        }
        if (isOwnedByClassMatching(rooted, str -> str.startsWith("com.android.org.conscrypt."))) {
            incrementSize(rooted, HeapCategory.SECURITY_CONSCRYPT);
            categories++;
        }
        if (isOwnedByClassMatching(rooted, str -> str.startsWith("com.android.org.bouncycastle."))) {
            incrementSize(rooted, HeapCategory.SECURITY_BOUNCYCASTLE);
            categories++;
        }
        if (isOwnedByClassMatching(rooted, str -> str.startsWith("android.security.keystore."))) {
            incrementSize(rooted, HeapCategory.SECURITY_KEYSTORE);
            categories++;
        }

        if (!isInternedString(rooted) && !isOwnedByClassMatching(rooted, c -> !isLibcoreClass(c))) {
            incrementSize(rooted, HeapCategory.PURE_LIBCORE);
            categories++;
        }
        if (rooted.isClassObj() && isLibcoreClass(rooted.asClassObj().getName())) {
            incrementSize(rooted, HeapCategory.PURE_LIBCORE_STATIC);
            categories++;
        }

        if (categories == 0) {
            incrementSize(rooted, HeapCategory.NONE_OF_THE_ABOVE);
        }
    }

    private static boolean isInternedString(AhatInstance instance) {
        if (!instance.isRoot()) {
            return false;
        }
        for (RootType rootType : instance.getRootTypes()) {
            if (rootType.equals(RootType.INTERNED_STRING)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a category for an interned {@link String} with the given value. The categorization is
     * done based on heuristics tuned through experimentation.
     */
    private static HeapCategory categorizeInternedString(String string) {
        int nonAsciiChars = 0;
        int alphabeticChars = 0;
        int whitespaceChars = 0;
        int totalChars = string.length();
        for (int i = 0; i < totalChars; i++) {
            char c = string.charAt(i);
            if (c > '~') {
                nonAsciiChars++;
            }
            if (Character.isAlphabetic(c)) {
                alphabeticChars++;
            }
            if (Character.isWhitespace(c)) {
                whitespaceChars++;
            }
        }
        if (nonAsciiChars >= 0.5 * totalChars && alphabeticChars >= 0.5 * totalChars) {
            // At least 50% non-ASCII and at least 50% alphabetic. There's a good chance that this
            // is backing some kind of ICU property structure.
            return HeapCategory.INTERNED_STRING_UNICODE_ALPHABET_ISH;
        } else if (alphabeticChars >= 0.75 * totalChars && whitespaceChars >= 0.05 * totalChars) {
            // At least 75% alphabetic and at least 5% whitespace and less than 50% non-ASCII.
            // There's a good chance this is human-readable text e.g. an error message.
            return HeapCategory.INTERNED_STRING_TEXT_ISH;
        } else {
            // Neither of the above. There's a good chance that this is something code-like e.g. a
            // regex.
            return HeapCategory.INTERNED_STRING_CODE_ISH;
        }
    }

    private boolean isAndroidUtilClass(String className) {
        return className.startsWith("android.util.")
                || className.startsWith("com.android.internal.util.");
    }

    private boolean isAndroidFrameworkClass(String className) {
        return (className.startsWith("android.")
                        && !className.startsWith("android.icu.")
                        && !className.startsWith("android.util."))
                ||
                (className.startsWith("com.android.internal.")
                        && !className.startsWith("com.android.internal.util."));
    }

    private boolean isSecurityClass(String className) {
        return className.startsWith("java.security.")
                || className.startsWith("sun.security.")
                || className.startsWith("com.android.org.bouncycastle.")
                || className.startsWith("com.android.org.conscrypt.");
    }

    private boolean isOwnedByClassMatching(AhatInstance rooted, Predicate<String> predicate) {
        // Do a BFS of the strong reference graph looking for matching classes.
        Set<AhatInstance> visited = new HashSet<>();
        Queue<AhatInstance> queue = new ArrayDeque<>();
        visited.add(rooted);
        queue.add(rooted);
        while (!queue.isEmpty()) {
            AhatInstance instance = queue.remove();
            if (instance.isClassObj()) {
                // This is the heap allocation for the static state of a class. Check the class.
                // Don't continue up the reference tree, as every instance of this class has a
                // reference to it.
                return predicate.test(instance.asClassObj().getName());
            } else if (instance.isPlaceHolder()) {
                // Placeholders have no retained size and so can be ignored.
                return false;
            } else {
                // This is the heap allocation for the instance state of an object. Check its class.
                // If it's not a match, continue searching up the strong reference graph.
                AhatClassObj classObj = instance.getClassObj();
                if (predicate.test(classObj.getName())) {
                    return true;
                } else {
                    for (AhatInstance reference : instance.getHardReverseReferences()) {
                        if (!visited.contains(reference)) {
                            visited.add(reference);
                            queue.add(reference);
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isLibcoreClass(String name) {
        return name.startsWith("java.")
                || name.startsWith("javax.")
                || name.startsWith("sun.")
                || name.startsWith("com.sun.")
                || name.startsWith("libcore.");
    }

    /**
     * Increments the stored size for the given category by the retain size of the given rooted
     * instance on the configured heaps.
     */
    private void incrementSize(AhatInstance rooted, HeapCategory category) {
        Size size = Size.ZERO;
        for (AhatHeap heap : heaps) {
            size = size.plus(rooted.getRetainedSize(heap));
        }
        if (sizesByCategory.containsKey(category)) {
            sizesByCategory.put(category, sizesByCategory.get(category).plus(size));
        } else {
            sizesByCategory.put(category, size);
        }
    }
}
