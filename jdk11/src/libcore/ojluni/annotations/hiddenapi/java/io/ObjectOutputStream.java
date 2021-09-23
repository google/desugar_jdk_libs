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
public class ObjectOutputStream extends java.io.OutputStream
        implements java.io.ObjectOutput, java.io.ObjectStreamConstants {

    public ObjectOutputStream(java.io.OutputStream out) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected ObjectOutputStream() throws java.io.IOException, java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    public void useProtocolVersion(int version) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public final void writeObject(java.lang.Object obj) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void writeObjectOverride(java.lang.Object obj) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeUnshared(java.lang.Object obj) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void defaultWriteObject() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public java.io.ObjectOutputStream.PutField putFields() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeFields() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void reset() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void annotateClass(java.lang.Class<?> cl) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void annotateProxyClass(java.lang.Class<?> cl) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected java.lang.Object replaceObject(java.lang.Object obj) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected boolean enableReplaceObject(boolean enable) throws java.lang.SecurityException {
        throw new RuntimeException("Stub!");
    }

    protected void writeStreamHeader() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void writeClassDescriptor(java.io.ObjectStreamClass desc) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void write(int val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void write(byte[] buf) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void write(byte[] buf, int off, int len) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void flush() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected void drain() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void close() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeBoolean(boolean val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeByte(int val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeShort(int val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeChar(int val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeInt(int val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeLong(long val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeFloat(float val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeDouble(double val) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeBytes(java.lang.String str) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeChars(java.lang.String str) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeUTF(java.lang.String str) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    int getProtocolVersion() {
        throw new RuntimeException("Stub!");
    }

    void writeTypeString(java.lang.String str) throws java.io.IOException {
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

    private void writeObject0(java.lang.Object obj, boolean unshared) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeNull() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeHandle(int handle) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeClass(java.lang.Class<?> cl, boolean unshared) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeClassDesc(java.io.ObjectStreamClass desc, boolean unshared)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private boolean isCustomSubclass() {
        throw new RuntimeException("Stub!");
    }

    private void writeProxyDesc(java.io.ObjectStreamClass desc, boolean unshared)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeNonProxyDesc(java.io.ObjectStreamClass desc, boolean unshared)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeString(java.lang.String str, boolean unshared) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeArray(
            java.lang.Object array, java.io.ObjectStreamClass desc, boolean unshared)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeEnum(java.lang.Enum<?> en, java.io.ObjectStreamClass desc, boolean unshared)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeOrdinaryObject(
            java.lang.Object obj, java.io.ObjectStreamClass desc, boolean unshared)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeExternalData(java.io.Externalizable obj) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeSerialData(java.lang.Object obj, java.io.ObjectStreamClass desc)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void defaultWriteFields(java.lang.Object obj, java.io.ObjectStreamClass desc)
            throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private void writeFatalException(java.io.IOException ex) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    private static native void floatsToBytes(
            float[] src, int srcpos, byte[] dst, int dstpos, int nfloats);

    private static native void doublesToBytes(
            double[] src, int srcpos, byte[] dst, int dstpos, int ndoubles);

    private final java.io.ObjectOutputStream.BlockDataOutputStream bout;

    {
        bout = null;
    }

    private java.io.SerialCallbackContext curContext;

    private java.io.ObjectOutputStream.PutFieldImpl curPut;

    private final java.io.ObjectOutputStream.DebugTraceInfoStack debugInfoStack;

    {
        debugInfoStack = null;
    }

    private int depth;

    private final boolean enableOverride;

    {
        enableOverride = false;
    }

    private boolean enableReplace;

    private static final boolean extendedDebugInfo = false;

    private final java.io.ObjectOutputStream.HandleTable handles;

    {
        handles = null;
    }

    private byte[] primVals;

    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
    private int protocol = 2; // 0x2

    private final java.io.ObjectOutputStream.ReplaceTable subs;

    {
        subs = null;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class BlockDataOutputStream extends java.io.OutputStream
            implements java.io.DataOutput {

        BlockDataOutputStream(java.io.OutputStream out) {
            throw new RuntimeException("Stub!");
        }

        boolean setBlockDataMode(boolean mode) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        boolean getBlockDataMode() {
            throw new RuntimeException("Stub!");
        }

        private void warnIfClosed() {
            throw new RuntimeException("Stub!");
        }

        public void write(int b) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void write(byte[] b) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void write(byte[] b, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void flush() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void close() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void write(byte[] b, int off, int len, boolean copy) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void drain() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private void writeBlockHeader(int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void writeBoolean(boolean v) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void writeByte(int v) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void writeChar(int v) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void writeShort(int v) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void writeInt(int v) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void writeFloat(float v) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void writeLong(long v) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void writeDouble(double v) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void writeBytes(java.lang.String s) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void writeChars(java.lang.String s) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        public void writeUTF(java.lang.String s) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void writeBooleans(boolean[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void writeChars(char[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void writeShorts(short[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void writeInts(int[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void writeFloats(float[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void writeLongs(long[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void writeDoubles(double[] v, int off, int len) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        long getUTFLength(java.lang.String s) {
            throw new RuntimeException("Stub!");
        }

        void writeUTF(java.lang.String s, long utflen) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void writeLongUTF(java.lang.String s) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void writeLongUTF(java.lang.String s, long utflen) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private void writeUTFBody(java.lang.String s) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private static final int CHAR_BUF_SIZE = 256; // 0x100

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

        private final java.io.DataOutputStream dout;

        {
            dout = null;
        }

        private final byte[] hbuf;

        {
            hbuf = new byte[0];
        }

        private final java.io.OutputStream out;

        {
            out = null;
        }

        private int pos = 0; // 0x0

        private boolean warnOnceWhenWriting;
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
    private static class DebugTraceInfoStack {

        DebugTraceInfoStack() {
            throw new RuntimeException("Stub!");
        }

        void clear() {
            throw new RuntimeException("Stub!");
        }

        void pop() {
            throw new RuntimeException("Stub!");
        }

        void push(java.lang.String entry) {
            throw new RuntimeException("Stub!");
        }

        public java.lang.String toString() {
            throw new RuntimeException("Stub!");
        }

        private final java.util.List<java.lang.String> stack;

        {
            stack = null;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private static class HandleTable {

        HandleTable(int initialCapacity, float loadFactor) {
            throw new RuntimeException("Stub!");
        }

        int assign(java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        int lookup(java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        void clear() {
            throw new RuntimeException("Stub!");
        }

        int size() {
            throw new RuntimeException("Stub!");
        }

        private void insert(java.lang.Object obj, int handle) {
            throw new RuntimeException("Stub!");
        }

        private void growSpine() {
            throw new RuntimeException("Stub!");
        }

        private void growEntries() {
            throw new RuntimeException("Stub!");
        }

        private int hash(java.lang.Object obj) {
            throw new RuntimeException("Stub!");
        }

        private final float loadFactor;

        {
            loadFactor = 0;
        }

        private int[] next;

        private java.lang.Object[] objs;

        private int size;

        private int[] spine;

        private int threshold;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public abstract static class PutField {

        public PutField() {
            throw new RuntimeException("Stub!");
        }

        public abstract void put(java.lang.String name, boolean val);

        public abstract void put(java.lang.String name, byte val);

        public abstract void put(java.lang.String name, char val);

        public abstract void put(java.lang.String name, short val);

        public abstract void put(java.lang.String name, int val);

        public abstract void put(java.lang.String name, long val);

        public abstract void put(java.lang.String name, float val);

        public abstract void put(java.lang.String name, double val);

        public abstract void put(java.lang.String name, java.lang.Object val);

        @Deprecated
        public abstract void write(java.io.ObjectOutput out) throws java.io.IOException;
    }

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    private class PutFieldImpl extends java.io.ObjectOutputStream.PutField {

        PutFieldImpl(java.io.ObjectStreamClass desc) {
            throw new RuntimeException("Stub!");
        }

        public void put(java.lang.String name, boolean val) {
            throw new RuntimeException("Stub!");
        }

        public void put(java.lang.String name, byte val) {
            throw new RuntimeException("Stub!");
        }

        public void put(java.lang.String name, char val) {
            throw new RuntimeException("Stub!");
        }

        public void put(java.lang.String name, short val) {
            throw new RuntimeException("Stub!");
        }

        public void put(java.lang.String name, int val) {
            throw new RuntimeException("Stub!");
        }

        public void put(java.lang.String name, float val) {
            throw new RuntimeException("Stub!");
        }

        public void put(java.lang.String name, long val) {
            throw new RuntimeException("Stub!");
        }

        public void put(java.lang.String name, double val) {
            throw new RuntimeException("Stub!");
        }

        public void put(java.lang.String name, java.lang.Object val) {
            throw new RuntimeException("Stub!");
        }

        public void write(java.io.ObjectOutput out) throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        void writeFields() throws java.io.IOException {
            throw new RuntimeException("Stub!");
        }

        private int getFieldOffset(java.lang.String name, java.lang.Class<?> type) {
            throw new RuntimeException("Stub!");
        }

        private final java.io.ObjectStreamClass desc;

        {
            desc = null;
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
    private static class ReplaceTable {

        ReplaceTable(int initialCapacity, float loadFactor) {
            throw new RuntimeException("Stub!");
        }

        void assign(java.lang.Object obj, java.lang.Object rep) {
            throw new RuntimeException("Stub!");
        }

        java.lang.Object lookup(java.lang.Object obj) {
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

        private final java.io.ObjectOutputStream.HandleTable htab;

        {
            htab = null;
        }

        private java.lang.Object[] reps;
    }
}
