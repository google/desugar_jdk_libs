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
class AVAKeyword {

    private AVAKeyword(
            java.lang.String keyword,
            sun.security.util.ObjectIdentifier oid,
            boolean rfc1779Compliant,
            boolean rfc2253Compliant) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private boolean isCompliant(int standard) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    static sun.security.util.ObjectIdentifier getOID(
            java.lang.String keyword,
            int standard,
            java.util.Map<java.lang.String, java.lang.String> extraKeywordMap)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    static java.lang.String getKeyword(sun.security.util.ObjectIdentifier oid, int standard) {
        throw new RuntimeException("Stub!");
    }

    static java.lang.String getKeyword(
            sun.security.util.ObjectIdentifier oid,
            int standard,
            java.util.Map<java.lang.String, java.lang.String> extraOidMap) {
        throw new RuntimeException("Stub!");
    }

    static boolean hasKeyword(sun.security.util.ObjectIdentifier oid, int standard) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage private java.lang.String keyword;

    @UnsupportedAppUsage
    private static final java.util.Map<java.lang.String, sun.security.x509.AVAKeyword> keywordMap;

    static {
        keywordMap = null;
    }

    @UnsupportedAppUsage private sun.security.util.ObjectIdentifier oid;

    @UnsupportedAppUsage
    private static final java.util.Map<
                    sun.security.util.ObjectIdentifier, sun.security.x509.AVAKeyword>
            oidMap;

    static {
        oidMap = null;
    }

    private boolean rfc1779Compliant;

    private boolean rfc2253Compliant;
}
