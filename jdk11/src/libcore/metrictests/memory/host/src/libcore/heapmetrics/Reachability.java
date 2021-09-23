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

import com.android.ahat.heapdump.AhatInstance;

/**
 * An enumeration of the different ways that an object could be reachable.
 */
enum Reachability {

    /** Objects that are rooted. */
    ROOTED("Rooted"),

    /** Objects that are strongly reachable but not rooted. */
    NON_ROOTED_STRONG("NonRootedStrong"),

    /** Objects that are weakly reachable (only through soft/weak/phantom/finalizer references). */
    WEAK("Weak"),

    /** Objects that are unreachable. */
    UNREACHABLE("Unreachable");

    private final String metricSuffix;

    Reachability(String metricSuffix) {
        this.metricSuffix = metricSuffix;
    }

    /** Returns a name for a metric combining the given prefix with a reachability suffix. */
    String metricName(String metricPrefix) {
        return metricPrefix + metricSuffix;
    }

    /** Returns the reachability of the given object. */
    static final Reachability ofInstance(AhatInstance instance) {
        if (instance.isRoot()) {
            return ROOTED;
        } else if (instance.isStronglyReachable()) {
            return NON_ROOTED_STRONG;
        } else if (instance.isWeaklyReachable()) {
            return WEAK;
        } else if (instance.isUnreachable()) {
            return UNREACHABLE;
        }
        throw new AssertionError("Impossible reachability data for instance " + instance);
    }
}
