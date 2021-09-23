/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util.logging;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class Handler {

    protected Handler() {
        throw new RuntimeException("Stub!");
    }

    public abstract void publish(java.util.logging.LogRecord record);

    public abstract void flush();

    public abstract void close() throws java.lang.SecurityException;

    public synchronized void setFormatter(java.util.logging.Formatter newFormatter)
            throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public java.util.logging.Formatter getFormatter() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setEncoding(java.lang.String encoding)
            throws java.lang.SecurityException, java.io.UnsupportedEncodingException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getEncoding() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setFilter(java.util.logging.Filter newFilter)
            throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public java.util.logging.Filter getFilter() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setErrorManager(java.util.logging.ErrorManager em) {
        throw new RuntimeException("Stub!");
    }

    public java.util.logging.ErrorManager getErrorManager() {
        throw new RuntimeException("Stub!");
    }

    protected void reportError(java.lang.String msg, java.lang.Exception ex, int code) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setLevel(java.util.logging.Level newLevel)
            throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public java.util.logging.Level getLevel() {
        throw new RuntimeException("Stub!");
    }

    public boolean isLoggable(java.util.logging.LogRecord record) {
        throw new RuntimeException("Stub!");
    }

    void checkPermission() throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    private volatile java.lang.String encoding;

    private volatile java.util.logging.ErrorManager errorManager;

    private volatile java.util.logging.Filter filter;

    private volatile java.util.logging.Formatter formatter;

    private volatile java.util.logging.Level logLevel;

    private final java.util.logging.LogManager manager;

    {
        manager = null;
    }

    private static final int offValue;

    static {
        offValue = 0;
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    boolean sealed = true;
}
