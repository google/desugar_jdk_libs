/*
 * Copyright (C) 2018 The Android Open Source Project
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

package sun.security.x509;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
class AVAComparator implements java.util.Comparator<sun.security.x509.AVA> {

    private AVAComparator() {
        throw new RuntimeException("Stub!");
    }

    static java.util.Comparator<sun.security.x509.AVA> getInstance() {
        throw new RuntimeException("Stub!");
    }

    public int compare(sun.security.x509.AVA a1, sun.security.x509.AVA a2) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private static final java.util.Comparator<sun.security.x509.AVA> INSTANCE;

    static {
        INSTANCE = null;
    }
}
