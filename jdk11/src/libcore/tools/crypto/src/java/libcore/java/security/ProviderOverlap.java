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

package libcore.java.security;

import java.security.Provider;
import java.security.Security;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Prints a list of all algorithms (including aliases) that overlap between Conscrypt and
 * Bouncy Castle.  Intended to be run via vogar.
 * <p>
 * {@code vogar libcore/tools/crypto/src/java/libcore/java/security/ProviderOverlap.java}
 */
public class ProviderOverlap {

    public static void main(String[] argv) throws Exception {
        Set<String> conscrypt = getAlgorithms(Security.getProvider("AndroidOpenSSL"));
        Set<String> bc = getAlgorithms(Security.getProvider("BC"));
        SortedSet<String> overlap = new TreeSet<>(conscrypt);
        overlap.retainAll(bc);
        for (String s : overlap) {
            System.out.println(s);
        }
    }

    private static Set<String> getAlgorithms(Provider p) {
        Set<String> result = new HashSet<>();
        for (Object keyObj : p.keySet()) {
            String key = (String) keyObj;
            if (key.contains(" ")) {
                // These are implementation properties like "Provider.id name"
                continue;
            }
            if (key.startsWith("Alg.Alias.")) {
                // This is an alias, strip its prefix
                key = key.substring("Alg.Alias.".length());
            }
            result.add(key.toUpperCase(Locale.US));
        }
        return result;
    }
}