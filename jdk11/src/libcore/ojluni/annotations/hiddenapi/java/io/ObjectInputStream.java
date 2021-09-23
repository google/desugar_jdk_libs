/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

import android.compat.annotation.UnsupportedAppUsage;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public class ObjectInputStream extends java.io.InputStream
        implements java.io.ObjectInput, java.io.ObjectStreamConstants {

    public ObjectInputStream(java.io.InputStream in) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected ObjectInputStream() throws java.io.IOException, java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public final java.lang.Object readObject()
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Object readObjectOverride()
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.Object readUnshared()
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void defaultReadObject() throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.io.ObjectInputStream.GetField readFields()
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void registerValidation(java.io.ObjectInputValidation obj, int prio)
            throws java.io.InvalidObjectException, java.io.NotActiveException {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Class<?> resolveClass(java.io.ObjectStreamClass desc)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Class<?> resolveProxyClass(java.lang.String[] interfaces)
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Object resolveObject(java.lang.Object obj) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected boolean enableResolveObject(boolean enable) throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    protected void readStreamHeader() throws java.io.IOException, java.io.StreamCorruptedException {
        throw new RuntimeException("Stub!");
    }

    protected java.io.ObjectStreamClass readClassDescriptor()
            throws java.lang.ClassNotFoundException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int read() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int read(byte[] buf, int off, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int available() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void close() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean readBoolean() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public byte readByte() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int readUnsignedByte() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public char readChar() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public short readShort() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int readUnsignedShort() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int readInt() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public long readLong() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public float readFloat() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public double readDouble() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void readFully(byte[] buf) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void readFully(byte[] buf, int off, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int skipBytes(int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public java.lang.String readLine() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.lang.String readUTF() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void verifySubclass() {
        throw new RuntimeException("Stub!");
    }

    private static boolean auditSubclass(java.lang.Class<?> subcl) {
        throw new RuntimeException("Stub!");
    }

    private void clear() {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Object readObject0(boolean unshared) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Object checkResolve(java.lang.Object obj) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    java.lang.String readTypeString() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Object readNull() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Object readHandle(boolean unshared) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Class<?> readClass(boolean unshared) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.io.ObjectStreamClass readClassDesc(boolean unshared) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private boolean isCustomSubclass() {
        throw new RuntimeException("Stub!");
    }

    private java.io.ObjectStreamClass readProxyDesc(boolean unshared) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.io.ObjectStreamClass readNonProxyDesc(boolean unshared)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.String readString(boolean unshared) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Object readArray(boolean unshared) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Enum<?> readEnum(boolean unshared) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.lang.Object readOrdinaryObject(boolean unshared) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readExternalData(java.io.Externalizable obj, java.io.ObjectStreamClass desc)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void readSerialData(java.lang.Object obj, java.io.ObjectStreamClass desc)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void skipCustomData() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void defaultReadFields(java.lang.Object obj, java.io.ObjectStreamClass desc)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private java.io.IOException readFatalException() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void handleReset() throws java.io.StreamCorruptedException {
        throw new RuntimeException("Stub!");
    }

    @UnsupportedAppUsage
    private static native void bytesToFloats(
            byte[] src, int srcpos, float[] dst, int dstpos, int nfloats);

    @UnsupportedAppUsage
    private static native void bytesToDoubles(
            byte[] src, int srcpos, double[] dst, int dstpos, int ndoubles);

    private static java.lang.ClassLoader latestUserDefinedLoader() {
        throw new RuntimeException("Stub!");
    }

    private static java.lang.Object cloneArray(java.lang.Object array) {
        throw new RuntimeException("Stub!");
    }

    private static final int NULL_HANDLE = -1; // 0xffffffff

    @UnsupportedAppUsage
    private final java.io.ObjectInputStream.BlockDataInputStream bin;

    {
        bin = null;
    }

    private boolean closed;

    private java.io.SerialCallbackContext curContext;

    private boolean defaultDataEnd = false;

    private int depth;

    private final boolean enableOverride;

    {
        enableOverride = false;
    }

    private boolean enableResolve;

    private final java.io.ObjectInputStream.HandleTable handles;

    {
        handles = null;
    }

    private int passHandle = -1; // 0xffffffff

    private static final java.util.HashMap<java.lang.String, java.lang.Class<?>> primClasses;

    static {
        primClasses = null;
    }

    private byte[] primVals;

    private static final java.lang.Object unsharedMarker;

    static {
        unsharedMarker = null;
    }

    private final java.io.ObjectInputStream.ValidationList vlist;

    {
        vlist = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class BlockDataInputStream extends java.io.InputStream implements java.io.DataInput {

        BlockDataInputStream(java.io.InputStream in) {
            throw new RuntimeException("Stub!");
        }

        boolean setBlockDataMode(boolean newmode) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        boolean getBlockDataMode() {
            throw new RuntimeException("Stub!");
        }

        void skipBlockData() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private int readBlockHeader(boolean canBlock) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private void refill() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        int currentBlockRemaining() {
            throw new RuntimeException("Stub!");
        }

        int peek() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        byte peekByte() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int read() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int read(byte[] b, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public long skip(long len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int available() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void close() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        int read(byte[] b, int off, int len, boolean copy) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void readFully(byte[] b) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void readFully(byte[] b, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void readFully(byte[] b, int off, int len, boolean copy) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int skipBytes(int n) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public boolean readBoolean() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public byte readByte() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int readUnsignedByte() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public char readChar() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public short readShort() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int readUnsignedShort() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int readInt() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public float readFloat() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public long readLong() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public double readDouble() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String readUTF() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String readLine() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void readBooleans(boolean[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void readChars(char[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void readShorts(short[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void readInts(int[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void readFloats(float[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void readLongs(long[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void readDoubles(double[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        java.lang.String readLongUTF() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private java.lang.String readUTFBody(long utflen) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private long readUTFSpan(java.lang.StringBuilder sbuf, long utflen)
                throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private int readUTFChar(java.lang.StringBuilder sbuf, long utflen)
                throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        long getBytesRead() {
            throw new RuntimeException("Stub!");
        }

        private static final int CHAR_BUF_SIZE = 256; // 0x100

        private static final int HEADER_BLOCKED = -2; // 0xfffffffe

        private static final int MAX_BLOCK_SIZE = 1024; // 0x400

        private static final int MAX_HEADER_SIZE = 5; // 0x5

        private boolean blkmode = false;

        private final byte[] buf;

        {
            buf = new byte[0];
        }

        private final char[] cbuf;

        {
            cbuf = new char[0];
        }

        private final java.io.DataInputStream din;

        {
            din = null;
        }

        private int end = -1; // 0xffffffff

        private final byte[] hbuf;

        {
            hbuf = new byte[0];
        }

        private final java.io.ObjectInputStream.PeekInputStream in;

        {
            in = null;
        }

        private int pos = 0; // 0x0

        private int unread = 0; // 0x0
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class Caches {

        private Caches() {
            throw new RuntimeException("Stub!");
        }

        static final java.util.concurrent.ConcurrentMap<
                        java.io.ObjectStreamClass.WeakClassKey, java.lang.Boolean>
                subclassAudits;

        static {
            subclassAudits = null;
        }

        static final java.lang.ref.ReferenceQueue<java.lang.Class<?>> subclassAuditsQueue;

        static {
            subclassAuditsQueue = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public abstract static class GetField {

        public GetField() {
            throw new RuntimeException("Stub!");
        }

        public abstract java.io.ObjectStreamClass getObjectStreamClass();

        public abstract boolean defaulted(java.lang.String name) throws java.io.IOException;

        public abstract boolean get(java.lang.String name, boolean val) throws java.io.IOException;

        public abstract byte get(java.lang.String name, byte val) throws java.io.IOException;

        public abstract char get(java.lang.String name, char val) throws java.io.IOException;

        public abstract short get(java.lang.String name, short val) throws java.io.IOException;

        public abstract int get(java.lang.String name, int val) throws java.io.IOException;

        public abstract long get(java.lang.String name, long val) throws java.io.IOException;

        public abstract float get(java.lang.String name, float val) throws java.io.IOException;

        public abstract double get(java.lang.String name, double val) throws java.io.IOException;

        public abstract java.lang.Object get(java.lang.String name, java.lang.Object val)
                throws java.io.IOException;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class GetFieldImpl extends java.io.ObjectInputStream.GetField {

        GetFieldImpl(java.io.ObjectStreamClass desc) {
            throw new RuntimeException("Stub!");
        }

        public java.io.ObjectStreamClass getObjectStreamClass() {
            throw new RuntimeException("Stub!");
        }

        public boolean defaulted(java.lang.String name) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public boolean get(java.lang.String name, boolean val) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public byte get(java.lang.String name, byte val) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public char get(java.lang.String name, char val) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public short get(java.lang.String name, short val) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int get(java.lang.String name, int val) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public float get(java.lang.String name, float val) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public long get(java.lang.String name, long val) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public double get(java.lang.String name, double val) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public java.lang.Object get(java.lang.String name, java.lang.Object val)
                throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void readFields() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private int getFieldOffset(java.lang.String name, java.lang.Class<?> type) {
            throw new RuntimeException("Stub!");
        }

        private final java.io.ObjectStreamClass desc;

        {
            desc = null;
        }

        private final int[] objHandles;

        {
            objHandles = new int[0];
        }

        private final java.lang.Object[] objVals;

        {
            objVals = new java.lang.Object[0];
        }

        private final byte[] primVals;

        {
            primVals = new byte[0];
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class HandleTable {

        HandleTable(int initialCapacity) {
            throw new RuntimeException("Stub!");
        }

        int assign(java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        void markDependency(int dependent, int target) {
            throw new RuntimeException("Stub!");
        }

        void markException(int handle, java.lang.ClassNotFoundException ex) {
            throw new RuntimeException("Stub!");
        }

        void finish(int handle) {
            throw new RuntimeException("Stub!");
        }

        void setObject(int handle, java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        java.lang.Object lookupObject(int handle) {
            throw new RuntimeException("Stub!");
        }

        java.lang.ClassNotFoundException lookupException(int handle) {
            throw new RuntimeException("Stub!");
        }

        void clear() {
            throw new RuntimeException("Stub!");
        }

        int size() {
            throw new RuntimeException("Stub!");
        }

        private void grow() {
            throw new RuntimeException("Stub!");
        }

        private static final byte STATUS_EXCEPTION = 3; // 0x3

        private static final byte STATUS_OK = 1; // 0x1

        private static final byte STATUS_UNKNOWN = 2; // 0x2

        java.io.ObjectInputStream.HandleTable.HandleList[] deps;

        java.lang.Object[] entries;

        int lowDep = -1; // 0xffffffff

        int size = 0; // 0x0

        byte[] status;

        @SuppressWarnings({"unchecked", "deprecation", "all"})
        private static class HandleList {

            public HandleList() {
                throw new RuntimeException("Stub!");
            }

            public void add(int handle) {
                throw new RuntimeException("Stub!");
            }

            public int get(int index) {
                throw new RuntimeException("Stub!");
            }

            public int size() {
                throw new RuntimeException("Stub!");
            }

            private int[] list;

            private int size = 0; // 0x0
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class PeekInputStream extends java.io.InputStream {

        PeekInputStream(java.io.InputStream in) {
            throw new RuntimeException("Stub!");
        }

        int peek() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int read() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int read(byte[] b, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void readFully(byte[] b, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public long skip(long n) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public int available() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void close() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public long getBytesRead() {
            throw new RuntimeException("Stub!");
        }

        private final java.io.InputStream in;

        {
            in = null;
        }

        private int peekb = -1; // 0xffffffff

        private long totalBytesRead = 0; // 0x0
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class ValidationList {

        ValidationList() {
            throw new RuntimeException("Stub!");
        }

        void register(java.io.ObjectInputValidation obj, int priority)
                throws java.io.InvalidObjectException {
            throw new RuntimeException("Stub!");
        }

        void doCallbacks() throws java.io.InvalidObjectException {
            throw new RuntimeException("Stub!");
        }

        public void clear() {
            throw new RuntimeException("Stub!");
        }

        private java.io.ObjectInputStream.ValidationList.Callback list;

        @SuppressWarnings({"unchecked", "deprecation", "all"})
        private static class Callback {

            Callback(
                    java.io.ObjectInputValidation obj,
                    int priority,
                    java.io.ObjectInputStream.ValidationList.Callback next,
                    java.security.AccessControlContext acc) {
                throw new RuntimeException("Stub!");
            }

            final java.security.AccessControlContext acc;

            {
                acc = null;
            }

            java.io.ObjectInputStream.ValidationList.Callback next;

            final java.io.ObjectInputValidation obj;

            {
                obj = null;
            }

            final int priority;

            {
                priority = 0;
            }
        }
    }
}
