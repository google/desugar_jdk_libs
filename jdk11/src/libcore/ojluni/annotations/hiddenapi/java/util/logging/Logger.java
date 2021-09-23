/*
 * Copyright (c) 2000, 2014, Oracle and/or its affiliates. All rights reserved.
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
public class Logger {

    protected Logger(java.lang.String name, java.lang.String resourceBundleName) {
        throw new RuntimeException("Stub!");
    }

    Logger(
            java.lang.String name,
            java.lang.String resourceBundleName,
            java.lang.Class<?> caller,
            java.util.logging.LogManager manager,
            boolean isSystemLogger) {
        throw new RuntimeException("Stub!");
    }

    private Logger(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public static final java.util.logging.Logger getGlobal() {
        throw new RuntimeException("Stub!");
    }

    private void setCallersClassLoaderRef(java.lang.Class<?> caller) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.ClassLoader getCallersClassLoader() {
        throw new RuntimeException("Stub!");
    }

    void setLogManager(java.util.logging.LogManager manager) {
        throw new RuntimeException("Stub!");
    }

    private void checkPermission() throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    private static java.util.logging.Logger demandLogger(
            java.lang.String name, java.lang.String resourceBundleName, java.lang.Class<?> caller) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.logging.Logger getLogger(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.logging.Logger getLogger(
            java.lang.String name, java.lang.String resourceBundleName) {
        throw new RuntimeException("Stub!");
    }

    static java.util.logging.Logger getPlatformLogger(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public static java.util.logging.Logger getAnonymousLogger() {
        throw new RuntimeException("Stub!");
    }

    public static java.util.logging.Logger getAnonymousLogger(java.lang.String resourceBundleName) {
        throw new RuntimeException("Stub!");
    }

    public java.util.ResourceBundle getResourceBundle() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getResourceBundleName() {
        throw new RuntimeException("Stub!");
    }

    public void setFilter(java.util.logging.Filter newFilter) throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public java.util.logging.Filter getFilter() {
        throw new RuntimeException("Stub!");
    }

    public void log(java.util.logging.LogRecord record) {
        throw new RuntimeException("Stub!");
    }

    private void doLog(java.util.logging.LogRecord lr) {
        throw new RuntimeException("Stub!");
    }

    public void log(java.util.logging.Level level, java.lang.String msg) {
        throw new RuntimeException("Stub!");
    }

    public void log(
            java.util.logging.Level level,
            java.util.function.Supplier<java.lang.String> msgSupplier) {
        throw new RuntimeException("Stub!");
    }

    public void log(java.util.logging.Level level, java.lang.String msg, java.lang.Object param1) {
        throw new RuntimeException("Stub!");
    }

    public void log(
            java.util.logging.Level level, java.lang.String msg, java.lang.Object[] params) {
        throw new RuntimeException("Stub!");
    }

    public void log(
            java.util.logging.Level level, java.lang.String msg, java.lang.Throwable thrown) {
        throw new RuntimeException("Stub!");
    }

    public void log(
            java.util.logging.Level level,
            java.lang.Throwable thrown,
            java.util.function.Supplier<java.lang.String> msgSupplier) {
        throw new RuntimeException("Stub!");
    }

    public void logp(
            java.util.logging.Level level,
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.lang.String msg) {
        throw new RuntimeException("Stub!");
    }

    public void logp(
            java.util.logging.Level level,
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.util.function.Supplier<java.lang.String> msgSupplier) {
        throw new RuntimeException("Stub!");
    }

    public void logp(
            java.util.logging.Level level,
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.lang.String msg,
            java.lang.Object param1) {
        throw new RuntimeException("Stub!");
    }

    public void logp(
            java.util.logging.Level level,
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.lang.String msg,
            java.lang.Object[] params) {
        throw new RuntimeException("Stub!");
    }

    public void logp(
            java.util.logging.Level level,
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.lang.String msg,
            java.lang.Throwable thrown) {
        throw new RuntimeException("Stub!");
    }

    public void logp(
            java.util.logging.Level level,
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.lang.Throwable thrown,
            java.util.function.Supplier<java.lang.String> msgSupplier) {
        throw new RuntimeException("Stub!");
    }

    private void doLog(java.util.logging.LogRecord lr, java.lang.String rbname) {
        throw new RuntimeException("Stub!");
    }

    private void doLog(java.util.logging.LogRecord lr, java.util.ResourceBundle rb) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public void logrb(
            java.util.logging.Level level,
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.lang.String bundleName,
            java.lang.String msg) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public void logrb(
            java.util.logging.Level level,
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.lang.String bundleName,
            java.lang.String msg,
            java.lang.Object param1) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public void logrb(
            java.util.logging.Level level,
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.lang.String bundleName,
            java.lang.String msg,
            java.lang.Object[] params) {
        throw new RuntimeException("Stub!");
    }

    public void logrb(
            java.util.logging.Level level,
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.util.ResourceBundle bundle,
            java.lang.String msg,
            java.lang.Object... params) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public void logrb(
            java.util.logging.Level level,
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.lang.String bundleName,
            java.lang.String msg,
            java.lang.Throwable thrown) {
        throw new RuntimeException("Stub!");
    }

    public void logrb(
            java.util.logging.Level level,
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.util.ResourceBundle bundle,
            java.lang.String msg,
            java.lang.Throwable thrown) {
        throw new RuntimeException("Stub!");
    }

    public void entering(java.lang.String sourceClass, java.lang.String sourceMethod) {
        throw new RuntimeException("Stub!");
    }

    public void entering(
            java.lang.String sourceClass, java.lang.String sourceMethod, java.lang.Object param1) {
        throw new RuntimeException("Stub!");
    }

    public void entering(
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.lang.Object[] params) {
        throw new RuntimeException("Stub!");
    }

    public void exiting(java.lang.String sourceClass, java.lang.String sourceMethod) {
        throw new RuntimeException("Stub!");
    }

    public void exiting(
            java.lang.String sourceClass, java.lang.String sourceMethod, java.lang.Object result) {
        throw new RuntimeException("Stub!");
    }

    public void throwing(
            java.lang.String sourceClass,
            java.lang.String sourceMethod,
            java.lang.Throwable thrown) {
        throw new RuntimeException("Stub!");
    }

    public void severe(java.lang.String msg) {
        throw new RuntimeException("Stub!");
    }

    public void warning(java.lang.String msg) {
        throw new RuntimeException("Stub!");
    }

    public void info(java.lang.String msg) {
        throw new RuntimeException("Stub!");
    }

    public void config(java.lang.String msg) {
        throw new RuntimeException("Stub!");
    }

    public void fine(java.lang.String msg) {
        throw new RuntimeException("Stub!");
    }

    public void finer(java.lang.String msg) {
        throw new RuntimeException("Stub!");
    }

    public void finest(java.lang.String msg) {
        throw new RuntimeException("Stub!");
    }

    public void severe(java.util.function.Supplier<java.lang.String> msgSupplier) {
        throw new RuntimeException("Stub!");
    }

    public void warning(java.util.function.Supplier<java.lang.String> msgSupplier) {
        throw new RuntimeException("Stub!");
    }

    public void info(java.util.function.Supplier<java.lang.String> msgSupplier) {
        throw new RuntimeException("Stub!");
    }

    public void config(java.util.function.Supplier<java.lang.String> msgSupplier) {
        throw new RuntimeException("Stub!");
    }

    public void fine(java.util.function.Supplier<java.lang.String> msgSupplier) {
        throw new RuntimeException("Stub!");
    }

    public void finer(java.util.function.Supplier<java.lang.String> msgSupplier) {
        throw new RuntimeException("Stub!");
    }

    public void finest(java.util.function.Supplier<java.lang.String> msgSupplier) {
        throw new RuntimeException("Stub!");
    }

    public void setLevel(java.util.logging.Level newLevel) throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    final boolean isLevelInitialized() {
        throw new RuntimeException("Stub!");
    }

    public java.util.logging.Level getLevel() {
        throw new RuntimeException("Stub!");
    }

    public boolean isLoggable(java.util.logging.Level level) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getName() {
        throw new RuntimeException("Stub!");
    }

    public void addHandler(java.util.logging.Handler handler) throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public void removeHandler(java.util.logging.Handler handler)
            throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public java.util.logging.Handler[] getHandlers() {
        throw new RuntimeException("Stub!");
    }

    java.util.logging.Handler[] accessCheckedHandlers() {
        throw new RuntimeException("Stub!");
    }

    public void setUseParentHandlers(boolean useParentHandlers) {
        throw new RuntimeException("Stub!");
    }

    public boolean getUseParentHandlers() {
        throw new RuntimeException("Stub!");
    }

    private static java.util.ResourceBundle findSystemResourceBundle(java.util.Locale locale) {
        throw new RuntimeException("Stub!");
    }

    private synchronized java.util.ResourceBundle findResourceBundle(
            java.lang.String name, boolean useCallersClassLoader) {
        throw new RuntimeException("Stub!");
    }

    private synchronized void setupResourceInfo(
            java.lang.String name, java.lang.Class<?> callersClass) {
        throw new RuntimeException("Stub!");
    }

    public void setResourceBundle(java.util.ResourceBundle bundle) {
        throw new RuntimeException("Stub!");
    }

    public java.util.logging.Logger getParent() {
        throw new RuntimeException("Stub!");
    }

    public void setParent(java.util.logging.Logger parent) {
        throw new RuntimeException("Stub!");
    }

    private void doSetParent(java.util.logging.Logger newParent) {
        throw new RuntimeException("Stub!");
    }

    final void removeChildLogger(java.util.logging.LogManager.LoggerWeakRef child) {
        throw new RuntimeException("Stub!");
    }

    private void updateEffectiveLevel() {
        throw new RuntimeException("Stub!");
    }

    private java.util.logging.Logger.LoggerBundle getEffectiveLoggerBundle() {
        throw new RuntimeException("Stub!");
    }

    public static final java.lang.String GLOBAL_LOGGER_NAME = "global";

    private static final java.util.logging.Logger.LoggerBundle NO_RESOURCE_BUNDLE;

    static {
        NO_RESOURCE_BUNDLE = null;
    }

    private static final java.util.logging.Logger.LoggerBundle SYSTEM_BUNDLE;

    static {
        SYSTEM_BUNDLE = null;
    }

    static final java.lang.String SYSTEM_LOGGER_RB_NAME = "sun.util.logging.resources.logging";

    private boolean anonymous;

    private java.lang.ref.WeakReference<java.lang.ClassLoader> callersClassLoaderRef;

    private java.util.ResourceBundle catalog;

    private java.util.Locale catalogLocale;

    private java.lang.String catalogName;

    private static final java.util.logging.Handler[] emptyHandlers;

    static {
        emptyHandlers = new java.util.logging.Handler[0];
    }

    private volatile java.util.logging.Filter filter;

    @Deprecated public static final java.util.logging.Logger global;

    static {
        global = null;
    }

    private final java.util.concurrent.CopyOnWriteArrayList<java.util.logging.Handler> handlers;

    {
        handlers = null;
    }

    private final boolean isSystemLogger;

    {
        isSystemLogger = false;
    }

    private java.util.ArrayList<java.util.logging.LogManager.LoggerWeakRef> kids;

    private volatile java.util.logging.Level levelObject;

    private volatile int levelValue;

    private volatile java.util.logging.Logger.LoggerBundle loggerBundle;

    private volatile java.util.logging.LogManager manager;

    private java.lang.String name;

    private static final int offValue;

    static {
        offValue = 0;
    }

    private volatile java.util.logging.Logger parent;

    @UnsupportedAppUsage
    private static final java.lang.Object treeLock;

    static {
        treeLock = null;
    }

    private volatile boolean useParentHandlers = true;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static final class LoggerBundle {

        private LoggerBundle(java.lang.String resourceBundleName, java.util.ResourceBundle bundle) {
            throw new RuntimeException("Stub!");
        }

        boolean isSystemBundle() {
            throw new RuntimeException("Stub!");
        }

        static java.util.logging.Logger.LoggerBundle get(
                java.lang.String name, java.util.ResourceBundle bundle) {
            throw new RuntimeException("Stub!");
        }

        final java.lang.String resourceBundleName;

        {
            resourceBundleName = null;
        }

        final java.util.ResourceBundle userBundle;

        {
            userBundle = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class SystemLoggerHelper {

        private SystemLoggerHelper() {
            throw new RuntimeException("Stub!");
        }

        private static boolean getBooleanProperty(java.lang.String key) {
            throw new RuntimeException("Stub!");
        }

        static boolean disableCallerCheck;
    }
}
