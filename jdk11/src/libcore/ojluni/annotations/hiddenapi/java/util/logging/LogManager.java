/*
 * Copyright (C) 2014 The Android Open Source Project
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
public class LogManager {

    protected LogManager() {
        throw new RuntimeException("Stub!");
    }

    private LogManager(java.lang.Void checked) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.Void checkSubclassPermissions() {
        throw new RuntimeException("Stub!");
    }

    final void ensureLogManagerInitialized() {
        throw new RuntimeException("Stub!");
    }

    public static java.util.logging.LogManager getLogManager() {
        throw new RuntimeException("Stub!");
    }

    private void readPrimordialConfiguration() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public void addPropertyChangeListener(java.beans.PropertyChangeListener l)
            throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public void removePropertyChangeListener(java.beans.PropertyChangeListener l)
            throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    private java.util.logging.LogManager.LoggerContext getUserContext() {
        throw new RuntimeException("Stub!");
    }

    final java.util.logging.LogManager.LoggerContext getSystemContext() {
        throw new RuntimeException("Stub!");
    }

    private java.util.List<java.util.logging.LogManager.LoggerContext> contexts() {
        throw new RuntimeException("Stub!");
    }

    java.util.logging.Logger demandLogger(
            java.lang.String name, java.lang.String resourceBundleName, java.lang.Class<?> caller) {
        throw new RuntimeException("Stub!");
    }

    java.util.logging.Logger demandSystemLogger(
            java.lang.String name, java.lang.String resourceBundleName) {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.Class getClassInstance(java.lang.String cname)
            throws java.lang.ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }

    private void loadLoggerHandlers(
            java.util.logging.Logger logger,
            java.lang.String name,
            java.lang.String handlersPropertyName) {
        throw new RuntimeException("Stub!");
    }

    final void drainLoggerRefQueueBounded() {
        throw new RuntimeException("Stub!");
    }

    public boolean addLogger(java.util.logging.Logger logger) {
        throw new RuntimeException("Stub!");
    }

    private static void doSetLevel(java.util.logging.Logger logger, java.util.logging.Level level) {
        throw new RuntimeException("Stub!");
    }

    private static void doSetParent(
            java.util.logging.Logger logger, java.util.logging.Logger parent) {
        throw new RuntimeException("Stub!");
    }

    public java.util.logging.Logger getLogger(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    public java.util.Enumeration<java.lang.String> getLoggerNames() {
        throw new RuntimeException("Stub!");
    }

    public void readConfiguration() throws java.io.IOException, java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public void reset() throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    private void resetLogger(java.util.logging.Logger logger) {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String[] parseClassNames(java.lang.String propertyName) {
        throw new RuntimeException("Stub!");
    }

    public void readConfiguration(java.io.InputStream ins)
            throws java.io.IOException, java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getProperty(java.lang.String name) {
        throw new RuntimeException("Stub!");
    }

    java.lang.String getStringProperty(java.lang.String name, java.lang.String defaultValue) {
        throw new RuntimeException("Stub!");
    }

    int getIntProperty(java.lang.String name, int defaultValue) {
        throw new RuntimeException("Stub!");
    }

    boolean getBooleanProperty(java.lang.String name, boolean defaultValue) {
        throw new RuntimeException("Stub!");
    }

    java.util.logging.Level getLevelProperty(
            java.lang.String name, java.util.logging.Level defaultValue) {
        throw new RuntimeException("Stub!");
    }

    java.util.logging.Filter getFilterProperty(
            java.lang.String name, java.util.logging.Filter defaultValue) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    java.util.logging.Formatter getFormatterProperty(
            java.lang.String name, java.util.logging.Formatter defaultValue) {
        throw new RuntimeException("Stub!");
    }

    private synchronized void initializeGlobalHandlers() {
        throw new RuntimeException("Stub!");
    }

    void checkPermission() {
        throw new RuntimeException("Stub!");
    }

    public void checkAccess() throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    private synchronized void setLevelsOnExistingLoggers() {
        throw new RuntimeException("Stub!");
    }

    public static synchronized java.util.logging.LoggingMXBean getLoggingMXBean() {
        throw new RuntimeException("Stub!");
    }

    public static final java.lang.String LOGGING_MXBEAN_NAME = "java.util.logging:type=Logging";

    private static final int MAX_ITERATIONS = 400; // 0x190

    private java.util.WeakHashMap<java.lang.Object, java.util.logging.LogManager.LoggerContext>
            contextsMap;

    private final java.security.Permission controlPermission;

    {
        controlPermission = null;
    }

    private boolean deathImminent;

    private static final java.util.logging.Level defaultLevel;

    static {
        defaultLevel = null;
    }

    private volatile boolean initializationDone = false;

    private boolean initializedCalled = false;

    private boolean initializedGlobalHandlers = true;

    private final java.util.Map<java.lang.Object, java.lang.Integer> listenerMap;

    {
        listenerMap = null;
    }

    private final java.lang.ref.ReferenceQueue<java.util.logging.Logger> loggerRefQueue;

    {
        loggerRefQueue = null;
    }

    private static java.util.logging.LoggingMXBean loggingMXBean;

    private static final java.util.logging.LogManager manager;

    static {
        manager = null;
    }

    private volatile java.util.Properties props;

    private volatile boolean readPrimordialConfiguration;

    private volatile java.util.logging.Logger rootLogger;

    private final java.util.logging.LogManager.LoggerContext systemContext;

    {
        systemContext = null;
    }

    private final java.util.logging.LogManager.LoggerContext userContext;

    {
        userContext = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class Beans {

        private Beans() {
            throw new RuntimeException("Stub!");
        }

        private static java.lang.Class<?> getClass(java.lang.String name) {
            throw new RuntimeException("Stub!");
        }

        private static java.lang.reflect.Constructor<?> getConstructor(
                java.lang.Class<?> c, java.lang.Class<?>... types) {
            throw new RuntimeException("Stub!");
        }

        private static java.lang.reflect.Method getMethod(
                java.lang.Class<?> c, java.lang.String name, java.lang.Class<?>... types) {
            throw new RuntimeException("Stub!");
        }

        static boolean isBeansPresent() {
            throw new RuntimeException("Stub!");
        }

        static java.lang.Object newPropertyChangeEvent(
                java.lang.Object source,
                java.lang.String prop,
                java.lang.Object oldValue,
                java.lang.Object newValue) {
            throw new RuntimeException("Stub!");
        }

        static void invokePropertyChange(java.lang.Object listener, java.lang.Object ev) {
            throw new RuntimeException("Stub!");
        }

        private static final java.lang.Class<?> propertyChangeEventClass;

        static {
            propertyChangeEventClass = null;
        }

        private static final java.lang.Class<?> propertyChangeListenerClass;

        static {
            propertyChangeListenerClass = null;
        }

        private static final java.lang.reflect.Method propertyChangeMethod;

        static {
            propertyChangeMethod = null;
        }

        private static final java.lang.reflect.Constructor<?> propertyEventCtor;

        static {
            propertyEventCtor = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class Cleaner extends java.lang.Thread {

        private Cleaner() {
            throw new RuntimeException("Stub!");
        }

        public void run() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class LogNode {

        LogNode(
                java.util.logging.LogManager.LogNode parent,
                java.util.logging.LogManager.LoggerContext context) {
            throw new RuntimeException("Stub!");
        }

        void walkAndSetParent(java.util.logging.Logger parent) {
            throw new RuntimeException("Stub!");
        }

        java.util.HashMap<java.lang.String, java.util.logging.LogManager.LogNode> children;

        final java.util.logging.LogManager.LoggerContext context;

        {
            context = null;
        }

        java.util.logging.LogManager.LoggerWeakRef loggerRef;

        java.util.logging.LogManager.LogNode parent;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    class LoggerContext {

        private LoggerContext() {
            throw new RuntimeException("Stub!");
        }

        final boolean requiresDefaultLoggers() {
            throw new RuntimeException("Stub!");
        }

        final java.util.logging.LogManager getOwner() {
            throw new RuntimeException("Stub!");
        }

        final java.util.logging.Logger getRootLogger() {
            throw new RuntimeException("Stub!");
        }

        final java.util.logging.Logger getGlobalLogger() {
            throw new RuntimeException("Stub!");
        }

        java.util.logging.Logger demandLogger(
                java.lang.String name, java.lang.String resourceBundleName) {
            throw new RuntimeException("Stub!");
        }

        private void ensureInitialized() {
            throw new RuntimeException("Stub!");
        }

        synchronized java.util.logging.Logger findLogger(java.lang.String name) {
            throw new RuntimeException("Stub!");
        }

        private void ensureAllDefaultLoggers(java.util.logging.Logger logger) {
            throw new RuntimeException("Stub!");
        }

        private void ensureDefaultLogger(java.util.logging.Logger logger) {
            throw new RuntimeException("Stub!");
        }

        boolean addLocalLogger(java.util.logging.Logger logger) {
            throw new RuntimeException("Stub!");
        }

        synchronized boolean addLocalLogger(
                java.util.logging.Logger logger, boolean addDefaultLoggersIfNeeded) {
            throw new RuntimeException("Stub!");
        }

        synchronized void removeLoggerRef(
                java.lang.String name, java.util.logging.LogManager.LoggerWeakRef ref) {
            throw new RuntimeException("Stub!");
        }

        synchronized java.util.Enumeration<java.lang.String> getLoggerNames() {
            throw new RuntimeException("Stub!");
        }

        private void processParentHandlers(java.util.logging.Logger logger, java.lang.String name) {
            throw new RuntimeException("Stub!");
        }

        java.util.logging.LogManager.LogNode getNode(java.lang.String name) {
            throw new RuntimeException("Stub!");
        }

        private final java.util.Hashtable<
                        java.lang.String, java.util.logging.LogManager.LoggerWeakRef>
                namedLoggers;

        {
            namedLoggers = null;
        }

        private final java.util.logging.LogManager.LogNode root;

        {
            root = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class LoggerWeakRef extends java.lang.ref.WeakReference<java.util.logging.Logger> {

        LoggerWeakRef(java.util.logging.Logger logger) {
            super(null);
            throw new RuntimeException("Stub!");
        }

        void dispose() {
            throw new RuntimeException("Stub!");
        }

        void setNode(java.util.logging.LogManager.LogNode node) {
            throw new RuntimeException("Stub!");
        }

        void setParentRef(java.lang.ref.WeakReference<java.util.logging.Logger> parentRef) {
            throw new RuntimeException("Stub!");
        }

        private boolean disposed = false;

        private java.lang.String name;

        private java.util.logging.LogManager.LogNode node;

        private java.lang.ref.WeakReference<java.util.logging.Logger> parentRef;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private final class RootLogger extends java.util.logging.Logger {

        private RootLogger() {
            super("", null, null, LogManager.this, true);
            throw new RuntimeException("Stub!");
        }

        public void log(java.util.logging.LogRecord record) {
            throw new RuntimeException("Stub!");
        }

        public void addHandler(java.util.logging.Handler h) {
            throw new RuntimeException("Stub!");
        }

        public void removeHandler(java.util.logging.Handler h) {
            throw new RuntimeException("Stub!");
        }

        java.util.logging.Handler[] accessCheckedHandlers() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    final class SystemLoggerContext extends java.util.logging.LogManager.LoggerContext {

        SystemLoggerContext() {
            throw new RuntimeException("Stub!");
        }

        java.util.logging.Logger demandLogger(
                java.lang.String name, java.lang.String resourceBundleName) {
            throw new RuntimeException("Stub!");
        }
    }
}
