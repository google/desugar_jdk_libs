/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.harmony.tests.java.net;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocketImpl;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import libcore.junit.junit3.TestCaseWithRules;
import libcore.junit.util.ResourceLeakageDetector;
import org.junit.Rule;
import org.junit.rules.TestRule;

public class DatagramSocketImplTest extends TestCaseWithRules {
    @Rule
    public TestRule guardRule = ResourceLeakageDetector.getRule();

    /**
     * java.net.DatagramSocketImpl#DatagramSocketImpl()
     */
    public void test_Constructor() throws Exception {
        // regression test for Harmony-1117
        MockDatagramSocketImpl impl = new MockDatagramSocketImpl();
        assertNull(impl.getFileDescriptor());
    }


    public void test_connect() throws Exception {
        MockDatagramSocketImpl impl = new MockDatagramSocketImpl();
        InetAddress localhost = InetAddress.getByName("localhost"); //$NON-NLS-1$
        // connect do nothing, so will not throw exception
        impl.test_connect(localhost, 0);
        impl.test_connect(localhost, -1);
        impl.test_connect(null, -1);
        // disconnect
        impl.test_disconnect();
    }
}

class MockDatagramSocketImpl extends DatagramSocketImpl {

    @Override
    public FileDescriptor getFileDescriptor() {
        return super.getFileDescriptor();
    }

    @Override
    protected void bind(int port, InetAddress addr) throws SocketException {
        // empty
    }

    @Override
    protected void close() {
        // empty
    }

    @Override
    protected void create() throws SocketException {
        // empty
    }

    public Object getOption(int optID) throws SocketException {
        return null;
    }

    @Override
    protected byte getTTL() throws IOException {
        return 0;
    }

    @Override
    protected int getTimeToLive() throws IOException {
        return 0;
    }

    @Override
    protected void join(InetAddress addr) throws IOException {
        // empty
    }

    @Override
    protected void joinGroup(SocketAddress addr, NetworkInterface netInterface)
            throws IOException {
        // empty
    }

    @Override
    protected void leave(InetAddress addr) throws IOException {
        // empty
    }

    @Override
    protected void leaveGroup(SocketAddress addr, NetworkInterface netInterface)
            throws IOException {
        // empty
    }

    @Override
    protected int peek(InetAddress sender) throws IOException {
        return 0;
    }

    @Override
    protected int peekData(DatagramPacket pack) throws IOException {
        return 0;
    }

    @Override
    protected void receive(DatagramPacket pack) throws IOException {
        // empty
    }

    @Override
    protected void send(DatagramPacket pack) throws IOException {
        // empty

    }

    public void setOption(int optID, Object val) throws SocketException {
        // empty
    }

    @Override
    protected void setTTL(byte ttl) throws IOException {
        // empty
    }

    @Override
    protected void setTimeToLive(int ttl) throws IOException {
        // empty
    }

    public void test_connect(InetAddress inetAddr, int port) throws SocketException {
        super.connect(inetAddr, port);
    }

    public void test_disconnect() {
        super.disconnect();
    }
}
