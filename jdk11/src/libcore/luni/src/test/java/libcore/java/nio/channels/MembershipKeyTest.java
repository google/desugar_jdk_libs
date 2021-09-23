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

package libcore.java.nio.channels;

import junit.framework.TestCase;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;

public class MembershipKeyTest extends TestCase {

    private MembershipKey key;
    private final int PORT = 5000;
    private final String TEST_MESSAGE = "hello";
    private DatagramChannel client;
    private InetAddress sourceAddress = Inet4Address.LOOPBACK;
    private final static InetAddress MULTICAST_ADDRESS = getMulticastAddress();
    private final static NetworkInterface NETWORK_INTERFACE = getNetworkInterface();

    private void init(boolean withSource) throws Exception {
        client = DatagramChannel.open(StandardProtocolFamily.INET)
                .bind(new InetSocketAddress(Inet4Address.ANY, PORT));
        client.configureBlocking(false);

        if (withSource) {
            key = client.join(MULTICAST_ADDRESS, NETWORK_INTERFACE, sourceAddress);
        } else {
            key = client.join(MULTICAST_ADDRESS, NETWORK_INTERFACE);
        }
    }

    @Override
    public void tearDown() throws IOException {
        client.close();
        key = null;
    }

    public void test_isValid_OnChannelCloseWithJoinWithoutSource() throws Exception {
        init(false);
        check_isValid();
    }

    public void test_isValid_OnChannelCloseWithJoinWithSource() throws Exception {
        init(true);
        check_isValid();
    }

    private void check_isValid() throws IOException {
        assertTrue(key.isValid());
        client.close();
        assertFalse(key.isValid());
    }

    public void test_isValid_OnDropJoinWithoutSource() throws Exception {
        init(false);
        check_isValid_OnDrop();
    }

    public void test_isValid_OnDropJoinWithSource() throws Exception {
        init(true);
        check_isValid_OnDrop();
    }

    private void check_isValid_OnDrop() {
        assertTrue(key.isValid());
        key.drop();
        assertFalse(key.isValid());
    }

    public void test_dropWithJoinWithoutSource() throws Exception {
        init(false);
        check_drop();
    }

    public void test_dropWithJoinWithSource() throws Exception {
        init(true);
        check_drop();
    }

    private void check_drop() throws IOException {
        key.drop();
        try(DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET)) {
            assertEquals(TEST_MESSAGE.length(), dc
                    .bind(new InetSocketAddress(Inet4Address.LOOPBACK, 0))
                    .send(ByteBuffer.wrap(TEST_MESSAGE.getBytes()),
                            new InetSocketAddress(MULTICAST_ADDRESS, PORT)));
        }

        ByteBuffer buffer = ByteBuffer.allocate(1048);
        client.receive(buffer);
        buffer.flip();
        assertEquals(0, buffer.limit());
    }

    public void test_networkInterface() throws Exception {
        init(false);
        assertEquals(NETWORK_INTERFACE, key.networkInterface());
        client.close();
        assertEquals(NETWORK_INTERFACE, key.networkInterface());
    }

    public void test_sourceAddressWithJoinWithSource() throws Exception {
        init(true);
        assertEquals(sourceAddress, key.sourceAddress());
    }

    public void test_sourceAddressWithJoinWithoutSource() throws Exception {
        init(false);
        assertNull(key.sourceAddress());
    }

    public void test_groupWithJoinWithSource() throws Exception {
        init(true);
        assertEquals(MULTICAST_ADDRESS, key.group());
    }

    public void test_groupWithoutJoinWIthSource() throws Exception {
        init(false);
        assertEquals(MULTICAST_ADDRESS, key.group());
    }

    public void test_channelWithJoinWithSource() throws Exception {
        init(true);
        assertEquals(client, key.channel());
        key.drop();
        assertEquals(client, key.channel());
    }

    public void test_channelWithJoinWithoutSource() throws Exception {
        init(false);
        assertEquals(client, key.channel());
        key.drop();
        assertEquals(client, key.channel());
    }

    public void test_blockWithJoinWithSource() throws Exception {
        init(true);
        try {
            key.block(sourceAddress);
            fail();
        } catch (IllegalStateException expected) {}
    }

    public void test_blockWithJoinWithoutSource() throws Exception {
        init(false);
        key.block(sourceAddress);

        try (DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET)) {
            assertEquals(TEST_MESSAGE.length(), dc
                    .bind(new InetSocketAddress(Inet4Address.LOOPBACK, 0))
                    .send(ByteBuffer.wrap(TEST_MESSAGE.getBytes()),
                            new InetSocketAddress(MULTICAST_ADDRESS, PORT)));
        }

        ByteBuffer buffer = ByteBuffer.allocate(1048);
        client.receive(buffer);
        buffer.flip();
        assertEquals(0, buffer.limit());
    }

    public void test_block_Exception () throws Exception {
        init(false);

        // Blocking a multicast channel
        try {
            key.block(Inet4Address.getByName("224.0.0.10"));
            fail();
        } catch (IllegalArgumentException expected) {}

        // Different address type than the group
        try {
            key.block(Inet6Address.LOOPBACK);
            fail();
        } catch (IllegalArgumentException expected) {}

        key.drop();
        try {
            key.block(sourceAddress);
            fail();
        } catch (IllegalStateException expected) {}
    }

    public void test_unblockWithJoinWithSource() throws Exception {
        init(true);
        try {
            key.unblock(Inet4Address.getByName("127.0.0.2"));
            fail();
        } catch (IllegalStateException expected) {}
    }

    public void test_unblockWithJoinWithoutSource() throws Exception {
        init(false);

        key.block(sourceAddress);
        key.unblock(sourceAddress);

        try (DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET)) {
            assertEquals(TEST_MESSAGE.length(), dc
                    .bind(new InetSocketAddress(Inet4Address.LOOPBACK, 0))
                    .setOption(StandardSocketOptions.IP_MULTICAST_LOOP, true /* enable loop */)
                    .send(ByteBuffer.wrap(TEST_MESSAGE.getBytes()),
                            new InetSocketAddress(MULTICAST_ADDRESS, PORT)));
        }

        ByteBuffer buffer = ByteBuffer.allocate(1048);
        client.receive(buffer);
        buffer.flip();
        int limits = buffer.limit();
        byte bytes[] = new byte[limits];
        buffer.get(bytes, 0, limits);
        String receivedMessage = new String(bytes);
        assertEquals(TEST_MESSAGE, receivedMessage);
    }

    public void test_unblock_Exception() throws Exception {
        init(false);
        try {
            key.unblock(sourceAddress);
            fail();
        } catch (IllegalStateException expected) {}

        key.drop();

        try {
            key.unblock(sourceAddress);
            fail();
        } catch (IllegalStateException expected) {}
    }

    private static InetAddress getMulticastAddress() {
        try {
            return InetAddress.getByName("239.255.0.1");
        } catch (UnknownHostException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static NetworkInterface getNetworkInterface() {
        try {
            return NetworkInterface.getByName("lo");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
