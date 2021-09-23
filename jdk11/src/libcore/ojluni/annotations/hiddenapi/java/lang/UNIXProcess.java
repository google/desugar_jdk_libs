/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1995, 2010, Oracle and/or its affiliates. All rights reserved.
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


@SuppressWarnings({"unchecked", "deprecation", "all"})
final class UNIXProcess extends java.lang.Process {

    UNIXProcess(
            byte[] prog,
            byte[] argBlock,
            int argc,
            byte[] envBlock,
            int envc,
            byte[] dir,
            int[] fds,
            boolean redirectErrorStream)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private native int waitForProcessExit(int pid);

    private native int forkAndExec(
            byte[] prog,
            byte[] argBlock,
            int argc,
            byte[] envBlock,
            int envc,
            byte[] dir,
            int[] fds,
            boolean redirectErrorStream)
            throws java.io.IOException;

    static java.io.FileDescriptor newFileDescriptor(int fd) {
        throw new RuntimeException("Stub!");
    }

    void initStreams(int[] fds) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    void processExited(int exitcode) {
        throw new RuntimeException("Stub!");
    }

    public java.io.OutputStream getOutputStream() {
        throw new RuntimeException("Stub!");
    }

    public java.io.InputStream getInputStream() {
        throw new RuntimeException("Stub!");
    }

    public java.io.InputStream getErrorStream() {
        throw new RuntimeException("Stub!");
    }

    public synchronized int waitFor() throws java.lang.InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public synchronized int exitValue() {
        throw new RuntimeException("Stub!");
    }

    private static native void destroyProcess(int pid);

    public void destroy() {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String toString() {
        throw new RuntimeException("Stub!");
    }

    private static native void initIDs();

    private int exitcode;

    private boolean hasExited;

    @android.compat.annotation.UnsupportedAppUsage private final int pid;

    {
        pid = 0;
    }

    private static final java.util.concurrent.Executor processReaperExecutor;

    static {
        processReaperExecutor = null;
    }

    private java.io.InputStream stderr;

    private java.io.OutputStream stdin;

    private java.io.InputStream stdout;

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class ProcessPipeInputStream extends java.io.BufferedInputStream {

        ProcessPipeInputStream(int fd) {
            super((java.io.InputStream) null);
            throw new RuntimeException("Stub!");
        }

        private static byte[] drainInputStream(java.io.InputStream in) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        synchronized void processExited() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class ProcessPipeOutputStream extends java.io.BufferedOutputStream {

        ProcessPipeOutputStream(int fd) {
            super((java.io.OutputStream) null);
            throw new RuntimeException("Stub!");
        }

        synchronized void processExited() {
            throw new RuntimeException("Stub!");
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class ProcessReaperThreadFactory implements java.util.concurrent.ThreadFactory {

        private ProcessReaperThreadFactory() {
            throw new RuntimeException("Stub!");
        }

        private static java.lang.ThreadGroup getRootThreadGroup() {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Thread newThread(java.lang.Runnable grimReaper) {
            throw new RuntimeException("Stub!");
        }

        private static final java.lang.ThreadGroup group;

        static {
            group = null;
        }
    }
}
