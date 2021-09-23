/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package libcore.java.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import libcore.junit.junit3.TestCaseWithRules;
import libcore.junit.util.ResourceLeakageDetector;
import org.junit.Rule;
import org.junit.rules.TestRule;

public final class MulticastSocketTest extends TestCaseWithRules {
    @Rule
    public TestRule guardRule = ResourceLeakageDetector.getRule();

    private static final int SO_TIMEOUT = 1000;

    public void testGroupReceiveIPv4() throws Exception {
        testGroupReceive(InetAddress.getByName("239.1.1.1"));
    }

    public void testGroupReceiveIPv6() throws Exception {
        testGroupReceive(InetAddress.getByName("ff05::1:1"));
    }

    private void testGroupReceive(InetAddress mcGroup) throws IOException {
        final String message = "hello";

        try (MulticastSocket mcSock = new MulticastSocket();
             DatagramSocket ds = new DatagramSocket()) {
            mcSock.setSoTimeout(SO_TIMEOUT);
            final int mcPort = mcSock.getLocalPort();

            DatagramPacket p = new DatagramPacket(message.getBytes(), message.length());
            p.setAddress(mcGroup);
            p.setPort(mcPort);

            mcSock.joinGroup(mcGroup);
            ds.send(p);
            assertRecv(mcSock, true, message);

            mcSock.leaveGroup(mcGroup);
            ds.send(p);
            assertRecv(mcSock, false, message);
        }
    }

    private void assertRecv(MulticastSocket mcSock, boolean expectedSucceed, String expectedMsg)
            throws IOException {
        try {
            byte[] buf = new byte[expectedMsg.length()];
            DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);
            mcSock.receive(recvPacket);
            if (expectedSucceed) {
                assertTrue(new String(buf).equals(expectedMsg));
            } else {
                fail();
            }
        } catch (SocketTimeoutException e) {
            if (expectedSucceed) {
                fail();
            }
        }
    }
}
