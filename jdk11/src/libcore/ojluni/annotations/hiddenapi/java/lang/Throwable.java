/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class Throwable implements java.io.Serializable {

    public Throwable() {
        throw new RuntimeException("Stub!");
    }

    public Throwable(java.lang.String message) {
        throw new RuntimeException("Stub!");
    }

    public Throwable(java.lang.String message, java.lang.Throwable cause) {
        throw new RuntimeException("Stub!");
    }

    public Throwable(java.lang.Throwable cause) {
        throw new RuntimeException("Stub!");
    }

    protected Throwable(
            java.lang.String message,
            java.lang.Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getMessage() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String getLocalizedMessage() {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.Throwable getCause() {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.Throwable initCause(java.lang.Throwable cause) {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    public void printStackTrace() {
        throw new RuntimeException("Stub!");
    }

    public void printStackTrace(java.io.PrintStream s) {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private void printStackTrace(java.lang.Throwable.PrintStreamOrWriter s) {
        throw new RuntimeException("Stub!");
    }

    private void printEnclosedStackTrace(
            java.lang.Throwable.PrintStreamOrWriter s,
            java.lang.StackTraceElement[] enclosingTrace,
            java.lang.String caption,
            java.lang.String prefix,
            java.util.Set<java.lang.Throwable> dejaVu) {
        throw new RuntimeException("Stub!");
    }

    public void printStackTrace(java.io.PrintWriter s) {
        throw new RuntimeException("Stub!");
    }

    public synchronized java.lang.Throwable fillInStackTrace() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private static native java.lang.Object nativeFillInStackTrace();

    public java.lang.StackTraceElement[] getStackTrace() {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private synchronized java.lang.StackTraceElement[] getOurStackTrace() {
        throw new RuntimeException("Stub!");
    }

    public void setStackTrace(java.lang.StackTraceElement[] stackTrace) {
        throw new RuntimeException("Stub!");
    }

    private static native java.lang.StackTraceElement[] nativeGetStackTrace(
            java.lang.Object stackState);

    private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private synchronized void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final synchronized void addSuppressed(java.lang.Throwable exception) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized java.lang.Throwable[] getSuppressed() {
        throw new RuntimeException("Stub!");
    }

    private static final java.lang.String CAUSE_CAPTION = "Caused by: ";

    private static java.lang.Throwable[] EMPTY_THROWABLE_ARRAY;

    private static final java.lang.String NULL_CAUSE_MESSAGE = "Cannot suppress a null exception.";

    private static final java.lang.String SELF_SUPPRESSION_MESSAGE =
            "Self-suppression not permitted";

    private static final java.lang.String SUPPRESSED_CAPTION = "Suppressed: ";

    @UnsupportedAppUsage
    private transient volatile java.lang.Object backtrace;

    @UnsupportedAppUsage
    private java.lang.Throwable cause;

    @UnsupportedAppUsage
    private java.lang.String detailMessage;

    private static final long serialVersionUID = -3042686055658047285L; // 0xd5c635273977b8cbL

    @UnsupportedAppUsage
    private java.lang.StackTraceElement[] stackTrace;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private java.util.List<java.lang.Throwable> suppressedExceptions;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private abstract static class PrintStreamOrWriter {

        private PrintStreamOrWriter() {
            throw new RuntimeException("Stub!");
        }

        abstract java.lang.Object lock();

        abstract void println(java.lang.Object o);
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class SentinelHolder {

        private SentinelHolder() {
            throw new RuntimeException("Stub!");
        }

        public static final java.lang.StackTraceElement STACK_TRACE_ELEMENT_SENTINEL;

        static {
            STACK_TRACE_ELEMENT_SENTINEL = null;
        }

        public static final java.lang.StackTraceElement[] STACK_TRACE_SENTINEL;

        static {
            STACK_TRACE_SENTINEL = new java.lang.StackTraceElement[0];
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class WrappedPrintStream extends java.lang.Throwable.PrintStreamOrWriter {

        WrappedPrintStream(java.io.PrintStream printStream) {
            throw new RuntimeException("Stub!");
        }

        java.lang.Object lock() {
            throw new RuntimeException("Stub!");
        }

        void println(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        private final java.io.PrintStream printStream;

        {
            printStream = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class WrappedPrintWriter extends java.lang.Throwable.PrintStreamOrWriter {

        WrappedPrintWriter(java.io.PrintWriter printWriter) {
            throw new RuntimeException("Stub!");
        }

        java.lang.Object lock() {
            throw new RuntimeException("Stub!");
        }

        void println(java.lang.Object o) {
            throw new RuntimeException("Stub!");
        }

        private final java.io.PrintWriter printWriter;

        {
            printWriter = null;
        }
    }
}
