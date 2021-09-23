/*
 * Copyright (c) 2000, 2012, Oracle and/or its affiliates. All rights reserved.
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

package sun.nio.ch;

import java.nio.channels.*;
import java.nio.channels.spi.*;
import java.util.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class SelectorImpl extends java.nio.channels.spi.AbstractSelector {

    protected SelectorImpl(java.nio.channels.spi.SelectorProvider sp) {
        super(null);
        throw new RuntimeException("Stub!");
    }

    public java.util.Set<java.nio.channels.SelectionKey> keys() {
        throw new RuntimeException("Stub!");
    }

    public java.util.Set<java.nio.channels.SelectionKey> selectedKeys() {
        throw new RuntimeException("Stub!");
    }

    protected abstract int doSelect(long timeout) throws java.io.IOException;

    private int lockAndDoSelect(long timeout) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int select(long timeout) throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int select() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public int selectNow() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public void implCloseSelector() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected abstract void implClose() throws java.io.IOException;

    public void putEventOps(sun.nio.ch.SelectionKeyImpl sk, int ops) {
        throw new RuntimeException("Stub!");
    }

    protected final java.nio.channels.SelectionKey register(
            java.nio.channels.spi.AbstractSelectableChannel ch,
            int ops,
            java.lang.Object attachment) {
        throw new RuntimeException("Stub!");
    }

    protected abstract void implRegister(sun.nio.ch.SelectionKeyImpl ski);

    void processDeregisterQueue() throws java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    protected abstract void implDereg(sun.nio.ch.SelectionKeyImpl ski) throws java.io.IOException;

    public abstract java.nio.channels.Selector wakeup();

    protected java.util.HashSet<java.nio.channels.SelectionKey> keys;

    private java.util.Set<java.nio.channels.SelectionKey> publicKeys;

    @android.compat.annotation.UnsupportedAppUsage
    private java.util.Set<java.nio.channels.SelectionKey> publicSelectedKeys;

    @android.compat.annotation.UnsupportedAppUsage
    protected java.util.Set<java.nio.channels.SelectionKey> selectedKeys;
}
