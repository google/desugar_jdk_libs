/*
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class KeyPairGenerator extends java.security.KeyPairGeneratorSpi {

    protected KeyPairGenerator(java.lang.String algorithm) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getAlgorithm() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private static java.security.KeyPairGenerator getInstance(
            sun.security.jca.GetInstance.Instance instance, java.lang.String algorithm) {
        throw new RuntimeException("Stub!");
    }

    public static java.security.KeyPairGenerator getInstance(java.lang.String algorithm)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    public static java.security.KeyPairGenerator getInstance(
            java.lang.String algorithm, java.lang.String provider)
            throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        throw new RuntimeException("Stub!");
    }

    public static java.security.KeyPairGenerator getInstance(
            java.lang.String algorithm, java.security.Provider provider)
            throws java.security.NoSuchAlgorithmException {
        throw new RuntimeException("Stub!");
    }

    public final java.security.Provider getProvider() {
        throw new RuntimeException("Stub!");
    }

    void disableFailover() {
        throw new RuntimeException("Stub!");
    }

    public void initialize(int keysize) {
        throw new RuntimeException("Stub!");
    }

    public void initialize(int keysize, java.security.SecureRandom random) {
        throw new RuntimeException("Stub!");
    }

    public void initialize(java.security.spec.AlgorithmParameterSpec params)
            throws java.security.InvalidAlgorithmParameterException {
        throw new RuntimeException("Stub!");
    }

    public void initialize(
            java.security.spec.AlgorithmParameterSpec params, java.security.SecureRandom random)
            throws java.security.InvalidAlgorithmParameterException {
        throw new RuntimeException("Stub!");
    }

    public final java.security.KeyPair genKeyPair() {
        throw new RuntimeException("Stub!");
    }

    public java.security.KeyPair generateKeyPair() {
        throw new RuntimeException("Stub!");
    }

    private final java.lang.String algorithm;

    {
        algorithm = null;
    }

    java.security.Provider provider;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static final class Delegate extends java.security.KeyPairGenerator {

        Delegate(java.security.KeyPairGeneratorSpi spi, java.lang.String algorithm) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        Delegate(
                sun.security.jca.GetInstance.Instance instance,
                java.util.Iterator<java.security.Provider.Service> serviceIterator,
                java.lang.String algorithm) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        private java.security.KeyPairGeneratorSpi nextSpi(
                java.security.KeyPairGeneratorSpi oldSpi, boolean reinit) {
            throw new RuntimeException("Stub!");
        }

        void disableFailover() {
            throw new RuntimeException("Stub!");
        }

        public void initialize(int keysize, java.security.SecureRandom random) {
            throw new RuntimeException("Stub!");
        }

        public void initialize(
                java.security.spec.AlgorithmParameterSpec params, java.security.SecureRandom random)
                throws java.security.InvalidAlgorithmParameterException {
            throw new RuntimeException("Stub!");
        }

        public java.security.KeyPair generateKeyPair() {
            throw new RuntimeException("Stub!");
        }

        private static final int I_NONE = 1; // 0x1

        private static final int I_PARAMS = 3; // 0x3

        private static final int I_SIZE = 2; // 0x2

        private int initKeySize;

        private java.security.spec.AlgorithmParameterSpec initParams;

        private java.security.SecureRandom initRandom;

        private int initType;

        private final java.lang.Object lock;

        {
            lock = null;
        }

        private java.util.Iterator<java.security.Provider.Service> serviceIterator;

        private volatile java.security.KeyPairGeneratorSpi spi;
    }
}
