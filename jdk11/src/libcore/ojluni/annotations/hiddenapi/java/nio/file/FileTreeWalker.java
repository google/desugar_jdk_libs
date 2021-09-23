/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file;

@SuppressWarnings({"unchecked", "deprecation", "all"})
class FileTreeWalker implements java.io.Closeable {

    FileTreeWalker(java.util.Collection<java.nio.file.FileVisitOption> options, int maxDepth) {
        throw new RuntimeException("Stub!");
    }

    private java.nio.file.attribute.BasicFileAttributes getAttributes(
            java.nio.file.Path file, boolean canUseCached) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private boolean wouldLoop(java.nio.file.Path dir, java.lang.Object key) {
        throw new RuntimeException("Stub!");
    }

    private java.nio.file.FileTreeWalker.Event visit(
            java.nio.file.Path entry, boolean ignoreSecurityException, boolean canUseCached) {
        throw new RuntimeException("Stub!");
    }

    java.nio.file.FileTreeWalker.Event walk(java.nio.file.Path file) {
        throw new RuntimeException("Stub!");
    }

    java.nio.file.FileTreeWalker.Event next() {
        throw new RuntimeException("Stub!");
    }

    void pop() {
        throw new RuntimeException("Stub!");
    }

    void skipRemainingSiblings() {
        throw new RuntimeException("Stub!");
    }

    boolean isOpen() {
        throw new RuntimeException("Stub!");
    }

    public void close() {
        throw new RuntimeException("Stub!");
    }

    private boolean closed;

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553) private final boolean followLinks;

    {
        followLinks = false;
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private final java.nio.file.LinkOption[] linkOptions;

    {
        linkOptions = new java.nio.file.LinkOption[0];
    }

    @android.compat.annotation.UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553) private final int maxDepth;

    {
        maxDepth = 0;
    }

    private final java.util.ArrayDeque<java.nio.file.FileTreeWalker.DirectoryNode> stack;

    {
        stack = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class DirectoryNode {

        DirectoryNode(
                java.nio.file.Path dir,
                java.lang.Object key,
                java.nio.file.DirectoryStream<java.nio.file.Path> stream) {
            throw new RuntimeException("Stub!");
        }

        java.nio.file.Path directory() {
            throw new RuntimeException("Stub!");
        }

        java.lang.Object key() {
            throw new RuntimeException("Stub!");
        }

        java.nio.file.DirectoryStream<java.nio.file.Path> stream() {
            throw new RuntimeException("Stub!");
        }

        java.util.Iterator<java.nio.file.Path> iterator() {
            throw new RuntimeException("Stub!");
        }

        void skip() {
            throw new RuntimeException("Stub!");
        }

        boolean skipped() {
            throw new RuntimeException("Stub!");
        }

        private final java.nio.file.Path dir;

        {
            dir = null;
        }

        private final java.util.Iterator<java.nio.file.Path> iterator;

        {
            iterator = null;
        }

        private final java.lang.Object key;

        {
            key = null;
        }

        private boolean skipped;

        private final java.nio.file.DirectoryStream<java.nio.file.Path> stream;

        {
            stream = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static class Event {

        private Event(
                java.nio.file.FileTreeWalker.EventType type,
                java.nio.file.Path file,
                java.nio.file.attribute.BasicFileAttributes attrs,
                java.io.IOException ioe) {
            throw new RuntimeException("Stub!");
        }

        Event(
                java.nio.file.FileTreeWalker.EventType type,
                java.nio.file.Path file,
                java.nio.file.attribute.BasicFileAttributes attrs) {
            throw new RuntimeException("Stub!");
        }

        Event(
                java.nio.file.FileTreeWalker.EventType type,
                java.nio.file.Path file,
                java.io.IOException ioe) {
            throw new RuntimeException("Stub!");
        }

        java.nio.file.FileTreeWalker.EventType type() {
            throw new RuntimeException("Stub!");
        }

        java.nio.file.Path file() {
            throw new RuntimeException("Stub!");
        }

        java.nio.file.attribute.BasicFileAttributes attributes() {
            throw new RuntimeException("Stub!");
        }

        java.io.IOException ioeException() {
            throw new RuntimeException("Stub!");
        }

        private final java.nio.file.attribute.BasicFileAttributes attrs;

        {
            attrs = null;
        }

        private final java.nio.file.Path file;

        {
            file = null;
        }

        private final java.io.IOException ioe;

        {
            ioe = null;
        }

        private final java.nio.file.FileTreeWalker.EventType type;

        {
            type = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    static enum EventType {
        START_DIRECTORY,
        END_DIRECTORY,
        ENTRY;

        private EventType() {
            throw new RuntimeException("Stub!");
        }
    }
}
