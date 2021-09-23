/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.lang.invoke;

import android.compat.annotation.UnsupportedAppUsage;
import java.io.Serializable;

public final class SerializedLambda implements Serializable {

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public SerializedLambda(Class<?> capturingClass,
                            String functionalInterfaceClass,
                            String functionalInterfaceMethodName,
                            String functionalInterfaceMethodSignature,
                            int implMethodKind,
                            String implClass,
                            String implMethodName,
                            String implMethodSignature,
                            String instantiatedMethodType,
                            Object[] capturedArgs) { }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public String getCapturingClass() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public String getFunctionalInterfaceClass() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public String getFunctionalInterfaceMethodName() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public String getFunctionalInterfaceMethodSignature() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public String getImplClass() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public String getImplMethodName() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public String getImplMethodSignature() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public int getImplMethodKind() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public final String getInstantiatedMethodType() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    public int getCapturedArgCount() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    public Object getCapturedArg(int i) {
        throw new RuntimeException("Stub!");
    }

}
