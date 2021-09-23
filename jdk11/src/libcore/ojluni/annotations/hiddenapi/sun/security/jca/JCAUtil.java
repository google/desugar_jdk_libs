/*
 * Copyright (c) 2003, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
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
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package sun.security.jca;

import java.lang.ref.*;
import java.security.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class JCAUtil {

    private JCAUtil() {
        throw new RuntimeException("Stub!");
    }

    public static int getTempArraySize(int totalSize) {
        throw new RuntimeException("Stub!");
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public static java.security.SecureRandom getSecureRandom() {
        throw new RuntimeException("Stub!");
    }

    private static final int ARRAY_SIZE = 4096; // 0x1000

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class CachedSecureRandomHolder {

        private CachedSecureRandomHolder() {
            throw new RuntimeException("Stub!");
        }

        public static java.security.SecureRandom instance;
    }
}
