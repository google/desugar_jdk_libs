/*
 * Copyright (C) 2011 The Android Open Source Project
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
 * limitations under the License.
 */

package libcore.android.system;

import android.system.ErrnoException;
import android.system.Int64Ref;
import android.system.NetlinkSocketAddress;
import android.system.Os;
import android.system.OsConstants;
import android.system.PacketSocketAddress;
import android.system.StructCmsghdr;
import android.system.StructMsghdr;
import android.system.StructRlimit;
import android.system.StructStat;
import android.system.StructTimeval;
import android.system.StructUcred;
import android.system.UnixSocketAddress;
import android.system.VmSocketAddress;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import libcore.io.IoUtils;
import libcore.testing.io.TestIoUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.system.OsConstants.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeTrue;

@RunWith(JUnit4.class)
public class OsTest {

    @Test
    public void testIsSocket() throws Exception {
        File f = new File("/dev/null");
        FileInputStream fis = new FileInputStream(f);
        assertFalse(S_ISSOCK(Os.fstat(fis.getFD()).st_mode));
        fis.close();

        ServerSocket s = new ServerSocket();
        assertTrue(S_ISSOCK(Os.fstat(s.getImpl().getFD$()).st_mode));
        s.close();
    }

    @Test
    public void testFcntlInt() throws Exception {
        File f = File.createTempFile("OsTest", "tst");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            Os.fcntlInt(fis.getFD(), F_SETFD, FD_CLOEXEC);
            int flags = Os.fcntlVoid(fis.getFD(), F_GETFD);
            assertTrue((flags & FD_CLOEXEC) != 0);
        } finally {
            TestIoUtils.closeQuietly(fis);
            f.delete();
        }
    }

    @Test
    public void testFcntlInt_udpSocket() throws Exception {
        final FileDescriptor fd = Os.socket(AF_INET, SOCK_DGRAM, 0);
        try {
            assertEquals(0, (Os.fcntlVoid(fd, F_GETFL) & O_NONBLOCK));

            // Verify that we can set file descriptor flags on sockets
            Os.fcntlInt(fd, F_SETFL, SOCK_DGRAM | O_NONBLOCK);
            assertTrue((Os.fcntlVoid(fd, F_GETFL) & O_NONBLOCK) != 0);

            // Check that we can turn it off also.
            Os.fcntlInt(fd, F_SETFL, SOCK_DGRAM);
            assertEquals(0, (Os.fcntlVoid(fd, F_GETFL) & O_NONBLOCK));
        } finally {
            Os.close(fd);
        }
    }

    @Test
    public void testFcntlInt_invalidCmd() throws Exception {
        final FileDescriptor fd = Os.socket(AF_INET, SOCK_DGRAM, 0);
        try {
            final int unknownCmd = -1;
            Os.fcntlInt(fd, unknownCmd, 0);
            fail("Expected failure due to invalid cmd");
        } catch (ErrnoException expected) {
            assertEquals(EINVAL, expected.errno);
        } finally {
            Os.close(fd);
        }
    }

    @Test
    public void testFcntlInt_nullFd() {
        try {
            Os.fcntlInt(null, F_SETFL, O_NONBLOCK);
            fail("Expected failure due to null file descriptor");
        } catch (ErrnoException expected) {
            assertEquals(EBADF, expected.errno);
        }
    }

    @Test
    public void testUnixDomainSockets_in_file_system() throws Exception {
        String path = System.getProperty("java.io.tmpdir") + "/test_unix_socket";
        new File(path).delete();
        checkUnixDomainSocket(UnixSocketAddress.createFileSystem(path), false);
    }

    @Test
    public void testUnixDomainSocket_abstract_name() throws Exception {
        // Linux treats a sun_path starting with a NUL byte as an abstract name. See unix(7).
        checkUnixDomainSocket(UnixSocketAddress.createAbstract("/abstract_name_unix_socket"), true);
    }

    @Test
    public void testUnixDomainSocket_unnamed() throws Exception {
        final FileDescriptor fd = Os.socket(AF_UNIX, SOCK_STREAM, 0);
        // unix(7) says an unbound socket is unnamed.
        checkNoSockName(fd);
        Os.close(fd);
    }

    private void checkUnixDomainSocket(final UnixSocketAddress address, final boolean isAbstract)
            throws Exception {
        final FileDescriptor serverFd = Os.socket(AF_UNIX, SOCK_STREAM, 0);
        Os.bind(serverFd, address);
        Os.listen(serverFd, 5);

        checkSockName(serverFd, isAbstract, address);

        Thread server = new Thread(new Runnable() {
            public void run() {
                try {
                    UnixSocketAddress peerAddress = UnixSocketAddress.createUnnamed();
                    FileDescriptor clientFd = Os.accept(serverFd, peerAddress);
                    checkSockName(clientFd, isAbstract, address);
                    checkNoName(peerAddress);

                    checkNoPeerName(clientFd);

                    StructUcred credentials = Os.getsockoptUcred(clientFd, SOL_SOCKET, SO_PEERCRED);
                    assertEquals(Os.getpid(), credentials.pid);
                    assertEquals(Os.getuid(), credentials.uid);
                    assertEquals(Os.getgid(), credentials.gid);

                    byte[] request = new byte[256];
                    Os.read(clientFd, request, 0, request.length);

                    String s = new String(request, StandardCharsets.UTF_8);
                    byte[] response = s.toUpperCase(Locale.ROOT).getBytes(StandardCharsets.UTF_8);
                    Os.write(clientFd, response, 0, response.length);

                    Os.close(clientFd);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        server.start();

        FileDescriptor clientFd = Os.socket(AF_UNIX, SOCK_STREAM, 0);

        Os.connect(clientFd, address);
        checkNoSockName(clientFd);

        String string = "hello, world!";

        byte[] request = string.getBytes(StandardCharsets.UTF_8);
        assertEquals(request.length, Os.write(clientFd, request, 0, request.length));

        byte[] response = new byte[request.length];
        assertEquals(response.length, Os.read(clientFd, response, 0, response.length));

        assertEquals(string.toUpperCase(Locale.ROOT), new String(response, StandardCharsets.UTF_8));

        Os.close(clientFd);
    }

    private static void checkSockName(FileDescriptor fd, boolean isAbstract,
            UnixSocketAddress address) throws Exception {
        UnixSocketAddress isa = (UnixSocketAddress) Os.getsockname(fd);
        assertEquals(address, isa);
        if (isAbstract) {
            assertEquals(0, isa.getSunPath()[0]);
        }
    }

    private void checkNoName(UnixSocketAddress usa) {
        assertEquals(0, usa.getSunPath().length);
    }

    private void checkNoPeerName(FileDescriptor fd) throws Exception {
        checkNoName((UnixSocketAddress) Os.getpeername(fd));
    }

    private void checkNoSockName(FileDescriptor fd) throws Exception {
        checkNoName((UnixSocketAddress) Os.getsockname(fd));
    }

    @Test
    public void test_strsignal() {
        assertEquals("Killed", Os.strsignal(9));
        assertEquals("Unknown signal -1", Os.strsignal(-1));
    }

    @Test
    public void test_byteBufferPositions_write_pwrite() throws Exception {
        FileOutputStream fos = new FileOutputStream(new File("/dev/null"));
        FileDescriptor fd = fos.getFD();
        final byte[] contents = "goodbye, cruel world".getBytes(StandardCharsets.US_ASCII);
        ByteBuffer byteBuffer = ByteBuffer.wrap(contents);

        byteBuffer.position(0);
        int written = Os.write(fd, byteBuffer);
        assertTrue(written > 0);
        assertEquals(written, byteBuffer.position());

        byteBuffer.position(4);
        written = Os.write(fd, byteBuffer);
        assertTrue(written > 0);
        assertEquals(written + 4, byteBuffer.position());

        byteBuffer.position(0);
        written = Os.pwrite(fd, byteBuffer, 64 /* offset */);
        assertTrue(written > 0);
        assertEquals(written, byteBuffer.position());

        byteBuffer.position(4);
        written = Os.pwrite(fd, byteBuffer, 64 /* offset */);
        assertTrue(written > 0);
        assertEquals(written + 4, byteBuffer.position());

        fos.close();
    }

    @Test
    public void test_byteBufferPositions_read_pread() throws Exception {
        FileInputStream fis = new FileInputStream(new File("/dev/zero"));
        FileDescriptor fd = fis.getFD();
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        byteBuffer.position(0);
        int read = Os.read(fd, byteBuffer);
        assertTrue(read > 0);
        assertEquals(read, byteBuffer.position());

        byteBuffer.position(4);
        read = Os.read(fd, byteBuffer);
        assertTrue(read > 0);
        assertEquals(read + 4, byteBuffer.position());

        byteBuffer.position(0);
        read = Os.pread(fd, byteBuffer, 64 /* offset */);
        assertTrue(read > 0);
        assertEquals(read, byteBuffer.position());

        byteBuffer.position(4);
        read = Os.pread(fd, byteBuffer, 64 /* offset */);
        assertTrue(read > 0);
        assertEquals(read + 4, byteBuffer.position());

        fis.close();
    }

    private static void checkByteBufferPositions_sendto_recvfrom(
            int family, InetAddress loopback) throws Exception {
        final FileDescriptor serverFd = Os.socket(family, SOCK_STREAM, 0);
        Os.bind(serverFd, loopback, 0);
        Os.listen(serverFd, 5);

        InetSocketAddress address = (InetSocketAddress) Os.getsockname(serverFd);

        final Thread server = new Thread(() -> {
            try {
                InetSocketAddress peerAddress = new InetSocketAddress();
                FileDescriptor clientFd = Os.accept(serverFd, peerAddress);

                // Attempt to receive a maximum of 24 bytes from the client, and then
                // close the connection.
                ByteBuffer buffer = ByteBuffer.allocate(16);
                int received = Os.recvfrom(clientFd, buffer, 0, null);
                assertTrue(received > 0);
                assertEquals(received, buffer.position());

                ByteBuffer buffer2 = ByteBuffer.allocate(16);
                buffer2.position(8);
                received = Os.recvfrom(clientFd, buffer2, 0, null);
                assertTrue(received > 0);
                assertEquals(received + 8, buffer.position());

                Os.close(clientFd);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        server.start();

        FileDescriptor clientFd = Os.socket(family, SOCK_STREAM, 0);
        Os.connect(clientFd, address.getAddress(), address.getPort());

        final byte[] bytes = "good bye, cruel black hole with fancy distortion"
                .getBytes(StandardCharsets.US_ASCII);
        assertTrue(bytes.length > 24);

        ByteBuffer input = ByteBuffer.wrap(bytes);
        input.position(0);
        input.limit(16);

        int sent = Os.sendto(clientFd, input, 0, address.getAddress(), address.getPort());
        assertTrue(sent > 0);
        assertEquals(sent, input.position());

        input.position(16);
        input.limit(24);
        sent = Os.sendto(clientFd, input, 0, address.getAddress(), address.getPort());
        assertTrue(sent > 0);
        assertEquals(sent + 16, input.position());

        Os.close(clientFd);
    }

    private interface ExceptionalRunnable {
        void run() throws Exception;
    }

    /**
     * Expects that the given Runnable will throw an exception of the specified class. If the class
     * is ErrnoException, and expectedErrno is non-null, also checks that the errno is equal to
     * expectedErrno.
     */
    private static void expectException(ExceptionalRunnable r, Class<? extends Exception> exClass,
            Integer expectedErrno, String msg) {
        try {
            r.run();
            fail(msg + " did not throw exception");
        } catch (Exception e) {
            assertEquals(msg + " threw unexpected exception", exClass, e.getClass());

            if (expectedErrno != null) {
                if (e instanceof ErrnoException) {
                    assertEquals(msg + "threw ErrnoException with unexpected error number",
                            (int) expectedErrno, ((ErrnoException) e).errno);
                } else {
                    fail("Can only pass expectedErrno when expecting ErrnoException");
                }
            }

        }
    }

    private static void expectBindException(FileDescriptor socket, SocketAddress addr,
            Class<? extends Exception> exClass, Integer expectedErrno) {
        String msg = String.format("bind(%s, %s)", socket, addr);
        expectException(() -> {
            Os.bind(socket, addr);
        }, exClass, expectedErrno, msg);
    }

    private static void expectConnectException(FileDescriptor socket, SocketAddress addr,
            Class<? extends Exception> exClass, Integer expectedErrno) {
        String msg = String.format("connect(%s, %s)", socket, addr);
        expectException(() -> {
            Os.connect(socket, addr);
        }, exClass, expectedErrno, msg);
    }

    private static void expectSendtoException(FileDescriptor socket, SocketAddress addr,
        Integer expectedErrno) {
        String msg = String.format("sendto(%s, %s)", socket, addr);
        byte[] packet = new byte[42];
        expectException(() -> {
                    Os.sendto(socket, packet, 0, packet.length, 0, addr);
                },
            ErrnoException.class, expectedErrno, msg);
    }

    private static void expectBindConnectSendtoSuccess(FileDescriptor socket, String socketDesc,
            SocketAddress addr) {
        String msg = socketDesc + " socket to " + addr.toString();

        try {
            try {
                // Expect that bind throws when any of its arguments are null.
                expectBindException(null, addr, ErrnoException.class, EBADF);
                expectBindException(socket, null, NullPointerException.class, null);
                expectBindException(null, null, NullPointerException.class, null);

                // Expect bind to succeed.
                Os.bind(socket, addr);

                // Find out which port we're actually bound to, and use that in subsequent connect()
                // and send() calls. We can't send to addr because that has a port of 0.
                if (addr instanceof InetSocketAddress) {
                    InetSocketAddress addrISA = (InetSocketAddress) addr;
                    InetSocketAddress socknameISA = (InetSocketAddress) Os.getsockname(socket);

                    assertEquals(addrISA.getAddress(), socknameISA.getAddress());
                    assertEquals(0, addrISA.getPort());
                    assertNotEquals(0, socknameISA.getPort());
                    addr = socknameISA;
                }

                // Expect sendto with a null address to throw because the socket is not connected,
                // but to succeed with a non-null address.
                byte[] packet = new byte[42];
                Os.sendto(socket, packet, 0, packet.length, 0, addr);
                // UNIX and IP sockets return different errors for this operation, so we can't check
                // errno.
                expectSendtoException(socket, null, null);
                expectSendtoException(null, null, EBADF);
                expectSendtoException(null, addr, EBADF);

                // Expect that connect throws when any of its arguments are null.
                expectConnectException(null, addr, ErrnoException.class, EBADF);
                expectConnectException(socket, null, NullPointerException.class, null);
                expectConnectException(null, null, NullPointerException.class, null);

                // Expect connect to succeed.
                Os.connect(socket, addr);
                assertEquals(Os.getsockname(socket), Os.getpeername(socket));

                // Expect sendto to succeed both when given an explicit address and a null address.
                Os.sendto(socket, packet, 0, packet.length, 0, addr);
                Os.sendto(socket, packet, 0, packet.length, 0, null);
            } catch (SocketException | ErrnoException e) {
                fail("Expected success for " + msg + ", but got: " + e);
            }

        } finally {
            IoUtils.closeQuietly(socket);
        }
    }

    private static void expectBindConnectSendtoErrno(int bindErrno, int connectErrno,
            int sendtoErrno, FileDescriptor socket, String socketDesc, SocketAddress addr) {
        try {

            // Expect bind to fail with bindErrno.
            String msg = "bind " + socketDesc + " socket to " + addr.toString();
            try {
                Os.bind(socket, addr);
                fail("Expected to fail " + msg);
            } catch (ErrnoException e) {
                assertEquals("Expected errno " + bindErrno + " " + msg, bindErrno, e.errno);
            } catch (SocketException e) {
                fail("Unexpected SocketException " + msg);
            }

            // Expect connect to fail with connectErrno.
            msg = "connect " + socketDesc + " socket to " + addr.toString();
            try {
                Os.connect(socket, addr);
                fail("Expected to fail " + msg);
            } catch (ErrnoException e) {
                assertEquals("Expected errno " + connectErrno + " " + msg, connectErrno, e.errno);
            } catch (SocketException e) {
                fail("Unexpected SocketException " + msg);
            }

            // Expect sendto to fail with sendtoErrno.
            byte[] packet = new byte[42];
            msg = "sendto " + socketDesc + " socket to " + addr.toString();
            try {
                Os.sendto(socket, packet, 0, packet.length, 0, addr);
                fail("Expected to fail " + msg);
            } catch (ErrnoException e) {
                assertEquals("Expected errno " + sendtoErrno + " " + msg, sendtoErrno, e.errno);
            } catch (SocketException e) {
                fail("Unexpected SocketException " + msg);
            }

        } finally {
            // No matter what happened, close the socket.
            IoUtils.closeQuietly(socket);
        }
    }

    private FileDescriptor makeIpv4Socket() throws Exception {
        return Os.socket(AF_INET, SOCK_DGRAM, 0);
    }

    private FileDescriptor makeIpv6Socket() throws Exception {
        return Os.socket(AF_INET6, SOCK_DGRAM, 0);
    }

    private FileDescriptor makeUnixSocket() throws Exception {
        return Os.socket(AF_UNIX, SOCK_DGRAM, 0);
    }

    @Test
    public void testCrossFamilyBindConnectSendto() throws Exception {
        SocketAddress addrIpv4 = new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 0);
        SocketAddress addrIpv6 = new InetSocketAddress(InetAddress.getByName("::1"), 0);
        SocketAddress addrUnix = UnixSocketAddress.createAbstract("/abstract_name_unix_socket");

        expectBindConnectSendtoSuccess(makeIpv4Socket(), "ipv4", addrIpv4);
        expectBindConnectSendtoErrno(EAFNOSUPPORT, EAFNOSUPPORT, EAFNOSUPPORT,
                makeIpv4Socket(), "ipv4", addrIpv6);
        expectBindConnectSendtoErrno(EAFNOSUPPORT, EAFNOSUPPORT, EAFNOSUPPORT,
                makeIpv4Socket(), "ipv4", addrUnix);

        // This succeeds because Java always uses dual-stack sockets and all InetAddress and
        // InetSocketAddress objects represent IPv4 addresses using IPv4-mapped IPv6 addresses.
        expectBindConnectSendtoSuccess(makeIpv6Socket(), "ipv6", addrIpv4);
        expectBindConnectSendtoSuccess(makeIpv6Socket(), "ipv6", addrIpv6);
        expectBindConnectSendtoErrno(EAFNOSUPPORT, EAFNOSUPPORT, EINVAL,
                makeIpv6Socket(), "ipv6", addrUnix);

        expectBindConnectSendtoErrno(EINVAL, EINVAL, EINVAL,
                makeUnixSocket(), "unix", addrIpv4);
        expectBindConnectSendtoErrno(EINVAL, EINVAL, EINVAL,
                makeUnixSocket(), "unix", addrIpv6);
        expectBindConnectSendtoSuccess(makeUnixSocket(), "unix", addrUnix);
    }

    @Test
    public void testUnknownSocketAddressSubclass() throws Exception {
        class MySocketAddress extends SocketAddress {

        }
        MySocketAddress myaddr = new MySocketAddress();

        for (int family : new int[] { AF_INET, AF_INET6, AF_NETLINK }) {
            FileDescriptor s = Os.socket(family, SOCK_DGRAM, 0);
            try {

                try {
                    Os.bind(s, myaddr);
                    fail("bind socket family " + family
                            + " to unknown SocketAddress subclass succeeded");
                } catch (UnsupportedOperationException expected) {
                }

                try {
                    Os.connect(s, myaddr);
                    fail("connect socket family " + family
                            + " to unknown SocketAddress subclass succeeded");
                } catch (UnsupportedOperationException expected) {
                }

                byte[] msg = new byte[42];
                try {
                    Os.sendto(s, msg, 0, msg.length, 0, myaddr);
                    fail("sendto socket family " + family
                            + " to unknown SocketAddress subclass succeeded");
                } catch (UnsupportedOperationException expected) {
                }

            } finally {
                Os.close(s);
            }
        }
    }

    @Test
    public void test_NetlinkSocket() throws Exception {
        FileDescriptor nlSocket = Os.socket(AF_NETLINK, SOCK_DGRAM, NETLINK_ROUTE);
        try {
            Os.bind(nlSocket, new NetlinkSocketAddress());
            // Non-system processes should not be allowed to bind() to NETLINK_ROUTE sockets.
            // http://b/141455849
            fail("bind() on NETLINK_ROUTE socket succeeded");
        } catch (ErrnoException expectedException) {
            assertEquals(expectedException.errno, EACCES);
        }

        NetlinkSocketAddress nlKernel = new NetlinkSocketAddress();
        Os.connect(nlSocket, nlKernel);
        NetlinkSocketAddress nlPeer = (NetlinkSocketAddress) Os.getpeername(nlSocket);
        assertEquals(0, nlPeer.getPortId());
        assertEquals(0, nlPeer.getGroupsMask());
        Os.close(nlSocket);
    }

    // This test is excluded from CTS via the knownfailures.txt because it requires extra
    // permissions not available in CTS. To run it you have to use an -eng build and use a tool like
    // vogar that runs the Android runtime as a privileged user.
    @Test
    public void test_PacketSocketAddress() throws Exception {
        NetworkInterface lo = NetworkInterface.getByName("lo");
        assertNotNull(lo);
        FileDescriptor fd = Os.socket(AF_PACKET, SOCK_DGRAM, ETH_P_IPV6);
        PacketSocketAddress addr =
                new PacketSocketAddress(ETH_P_IPV6, lo.getIndex(), null /* sll_addr */);
        Os.bind(fd, addr);

        PacketSocketAddress bound = (PacketSocketAddress) Os.getsockname(fd);
        assertEquals(ETH_P_IPV6, bound.sll_protocol);
        assertEquals(lo.getIndex(), bound.sll_ifindex);
        assertEquals(ARPHRD_LOOPBACK, bound.sll_hatype);
        assertEquals(0, bound.sll_pkttype);

        // The loopback address is ETH_ALEN bytes long and is all zeros.
        // http://lxr.free-electrons.com/source/drivers/net/loopback.c?v=3.10#L167
        assertNotNull(bound.sll_addr);
        assertEquals(6, bound.sll_addr.length);
        for (int i = 0; i < 6; i++) {
            assertEquals(0, bound.sll_addr[i]);
        }

        // The following checks that the packet socket address was constructed correctly in a form
        // that the kernel understands. If the address is correct, the bind should result in a
        // socket that is listening only for IPv6 packets, and only on loopback.

        // Send an IPv4 packet on loopback.
        // We send ourselves an IPv4 packet first. If we don't receive it, that (with high
        // probability) ensures that the packet socket does not see IPv4 packets.
        try (DatagramSocket s = new DatagramSocket()) {
            byte[] packet = new byte[64];
            s.send(new DatagramPacket(packet, 0, packet.length, Inet4Address.LOOPBACK,
                    53 /* arbitrary port */));
        }

        // Send an IPv6 packet on loopback.
        // Sending ourselves an IPv6 packet should cause the socket to receive a packet.
        // The idea is that if the code gets sll_protocol wrong, then the packet socket will receive
        // no packets and the test will fail.
        try (DatagramSocket s = new DatagramSocket()) {
            byte[] packet = new byte[64];
            s.send(new DatagramPacket(packet, 0, packet.length, Inet6Address.LOOPBACK,
                    53 /* arbitrary port */));
        }

        // Check that the socket associated with fd has received an IPv6 packet, not necessarily the
        // UDP one we sent above. IPv6 packets always begin with the nibble 6. If we get anything
        // else it means we're catching non-IPv6 or non-loopback packets unexpectedly. Since the
        // socket is not discriminating it may catch packets unrelated to this test from things
        // happening on the device at the same time, so we can't assert too much about the received
        // packet, i.e. no length / content check.
        {
            byte[] receivedPacket = new byte[4096];
            Os.read(fd, receivedPacket, 0, receivedPacket.length);
            assertEquals(6, (receivedPacket[0] & 0xf0) >> 4);

            byte[] sourceAddress = getIPv6AddressBytesAtOffset(receivedPacket, 8);
            assertArrayEquals(Inet6Address.LOOPBACK.getAddress(), sourceAddress);

            byte[] destAddress = getIPv6AddressBytesAtOffset(receivedPacket, 24);
            assertArrayEquals(Inet6Address.LOOPBACK.getAddress(), destAddress);
        }

        Os.close(fd);
    }

    @Test
    public void test_VmSocketAddress() {
        try {
            final VmSocketAddress addr = new VmSocketAddress(111, 222);
            assertEquals(111, addr.getSvmPort());
            assertEquals(222, addr.getSvmCid());
        } catch (UnsupportedOperationException ignore) {
            assumeNoException(ignore);  // the platform does not support virtio-vsock
        }
    }

    private static Thread createVmSocketEchoServer(final FileDescriptor serverFd) {
        return new Thread(new Runnable() {
            public void run() {
                final VmSocketAddress peer =
                    new VmSocketAddress(VMADDR_PORT_ANY, VMADDR_CID_ANY);

                try {
                    final FileDescriptor clientFd = Os.accept(serverFd, peer);
                    try {
                        final byte[] requestBuf = new byte[256];
                        final int len = Os.read(clientFd, requestBuf, 0, requestBuf.length);
                        final String request =
                            new String(requestBuf, 0, len, StandardCharsets.UTF_8);
                        final byte[] responseBuf =
                            request.toUpperCase(Locale.ROOT).getBytes(StandardCharsets.UTF_8);
                        Os.write(clientFd, responseBuf, 0, responseBuf.length);
                    } finally {
                        Os.close(clientFd);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Test
    public void test_VmSocket() throws Exception {
        try {
            final VmSocketAddress serverAddr = new VmSocketAddress(12345, VMADDR_CID_LOCAL);

            final FileDescriptor serverFd = Os.socket(AF_VSOCK, SOCK_STREAM, 0);

            try {
                Os.bind(serverFd, serverAddr);
                Os.listen(serverFd, 3);

                final Thread server = createVmSocketEchoServer(serverFd);
                server.start();

                final FileDescriptor clientFd = Os.socket(AF_VSOCK, SOCK_STREAM, 0);
                try {
                    Os.connect(clientFd, serverAddr);

                    final String request = "hello, world!";
                    final byte[] requestBuf = request.getBytes(StandardCharsets.UTF_8);

                    assertEquals(requestBuf.length,
                                 Os.write(clientFd, requestBuf, 0, requestBuf.length));

                    final byte[] responseBuf = new byte[requestBuf.length];
                    assertEquals(responseBuf.length,
                                 Os.read(clientFd, responseBuf, 0, responseBuf.length));

                    final String response = new String(responseBuf, StandardCharsets.UTF_8);

                    assertEquals(request.toUpperCase(Locale.ROOT), response);
                } finally {
                    Os.close(clientFd);
                }
            } finally {
                Os.close(serverFd);
            }
        } catch (UnsupportedOperationException ignore) {
            assumeNoException(ignore);  // the platform does not support virtio-vsock
        } catch (ErrnoException e) {
            // the platform does not support vsock
            assumeTrue(e.errno != EAFNOSUPPORT && e.errno != EACCES);
            throw e;
        }
    }

    private static byte[] getIPv6AddressBytesAtOffset(byte[] packet, int offsetIndex) {
        byte[] address = new byte[16];
        System.arraycopy(packet, offsetIndex, address, 0, 16);
        return address;
    }

    @Test
    public void test_byteBufferPositions_sendto_recvfrom_af_inet() throws Exception {
        checkByteBufferPositions_sendto_recvfrom(AF_INET, InetAddress.getByName("127.0.0.1"));
    }

    @Test
    public void test_byteBufferPositions_sendto_recvfrom_af_inet6() throws Exception {
        checkByteBufferPositions_sendto_recvfrom(AF_INET6, InetAddress.getByName("::1"));
    }

    private void checkSendToSocketAddress(int family, InetAddress loopback) throws Exception {
        FileDescriptor recvFd = Os.socket(family, SOCK_DGRAM, 0);
        Os.bind(recvFd, loopback, 0);
        StructTimeval tv = StructTimeval.fromMillis(20);
        Os.setsockoptTimeval(recvFd, SOL_SOCKET, SO_RCVTIMEO, tv);

        InetSocketAddress to = ((InetSocketAddress) Os.getsockname(recvFd));
        FileDescriptor sendFd = Os.socket(family, SOCK_DGRAM, 0);
        byte[] msg = ("Hello, I'm going to a socket address: " + to.toString()).getBytes(
            StandardCharsets.UTF_8);
        int len = msg.length;

        assertEquals(len, Os.sendto(sendFd, msg, 0, len, 0, to));
        byte[] received = new byte[msg.length + 42];
        InetSocketAddress from = new InetSocketAddress();
        assertEquals(len, Os.recvfrom(recvFd, received, 0, received.length, 0, from));
        assertEquals(loopback, from.getAddress());
    }

    @Test
    public void test_sendtoSocketAddress_af_inet() throws Exception {
        checkSendToSocketAddress(AF_INET, InetAddress.getByName("127.0.0.1"));
    }

    @Test
    public void test_sendtoSocketAddress_af_inet6() throws Exception {
        checkSendToSocketAddress(AF_INET6, InetAddress.getByName("::1"));
    }

    private static short asShort(StructCmsghdr cmsg) {
        ByteBuffer buf = ByteBuffer.wrap(cmsg.cmsg_data).order(ByteOrder.nativeOrder());
        assertEquals(Short.BYTES, buf.capacity());
        return buf.getShort();
    }

    private static int asInt(StructCmsghdr cmsg) {
        ByteBuffer buf = ByteBuffer.wrap(cmsg.cmsg_data).order(ByteOrder.nativeOrder());
        assertEquals(Integer.BYTES, buf.capacity());
        return buf.getInt();
    }

    @Test
    public void test_StructCmsgHdrConstructors() throws Exception {
        final StructCmsghdr cmsg1 = new StructCmsghdr(1, 2, (short) 32005);
        assertEquals(1, cmsg1.cmsg_level);
        assertEquals(2, cmsg1.cmsg_type);
        assertEquals(32005, asShort(cmsg1));

        ByteBuffer buf = ByteBuffer.allocate(Short.BYTES);
        buf.order(ByteOrder.nativeOrder());
        buf.putShort((short) 32005);
        assertArrayEquals(cmsg1.cmsg_data, buf.array());

        buf = ByteBuffer.allocate(Integer.BYTES);
        buf.order(ByteOrder.nativeOrder());
        buf.putInt(1000042);

        final StructCmsghdr cmsg2 = new StructCmsghdr(456789, 123456, buf.array());
        assertEquals(456789, cmsg2.cmsg_level);
        assertEquals(123456, cmsg2.cmsg_type);
        assertEquals(1000042, asInt(cmsg2));
        assertArrayEquals(buf.array(), cmsg2.cmsg_data);
    }

    /*
     * Test case for sendmsg with/without GSO in loopback iface,
     * recvmsg/gro would not happen since in loopback
     */
    private void checkSendmsgSocketAddress(int family, InetSocketAddress loopbackAddr,
            StructMsghdr sendmsgHdr, StructMsghdr recvmsgHdr, int sendSize) throws Exception {

        FileDescriptor sendFd = Os.socket(family, SOCK_DGRAM, 0);
        FileDescriptor recvFd = Os.socket(family, SOCK_DGRAM, 0);
        int rc = 0;

        //recvmsg cleanup data
        if (loopbackAddr.getAddress() instanceof Inet6Address) {
            Os.bind(recvFd, Inet6Address.ANY, loopbackAddr.getPort());
        } else {
            Os.bind(recvFd, Inet4Address.ANY, loopbackAddr.getPort());
        }

        StructTimeval tv = StructTimeval.fromMillis(20);
        Os.setsockoptTimeval(recvFd, SOL_SOCKET, SO_RCVTIMEO, tv);
        Os.setsockoptInt(recvFd, IPPROTO_UDP, UDP_GRO, 1); //enable GRO
        Os.setsockoptInt(recvFd, SOL_SOCKET, SO_RCVBUF, 1024 * 1024);

        try {
            assertEquals(sendSize, Os.sendmsg(sendFd, sendmsgHdr, 0));
            rc = 0;
            do {
                int temp_rc = Os.recvmsg(recvFd, recvmsgHdr, OsConstants.MSG_TRUNC);
                rc += temp_rc;
                if (recvmsgHdr.msg_control != null && recvmsgHdr.msg_control.length > 0) {
                    byte[] sendCmsgByte = sendmsgHdr.msg_control[0].cmsg_data;
                    byte[] recvCmsgByte = recvmsgHdr.msg_control[0].cmsg_data;
                    /* Note:
                     * GSO: is set with Short(2Byte) values;
                     * GRO: IP stack return with Int(4Bytes) value;
                     */
                    assertEquals(
                            ByteBuffer.wrap(sendCmsgByte).order(
                                    ByteOrder.nativeOrder()).getShort(0),
                            ByteBuffer.wrap(recvCmsgByte).order(
                                    ByteOrder.nativeOrder()).getInt(0));
                }

                recvmsgHdr = new StructMsghdr(recvmsgHdr.msg_name, recvmsgHdr.msg_iov,
                                              null,
                                              recvmsgHdr.msg_flags);
            }while(rc < sendSize);
        } finally {
            Os.close(sendFd);
            Os.close(recvFd);
        }
    }

    @Test
    public void test_sendmsg_af_inet_4K() throws Exception {
        // UDP GRO not required to be enabled on kernels prior to 5.4
        assumeTrue(kernelIsAtLeast(5, 4));

        InetSocketAddress loopbackAddr = new InetSocketAddress(InetAddress.getByName("127.0.0.1"),
                10234);
        StructCmsghdr[] cmsg = new StructCmsghdr[1];
        cmsg[0] = new StructCmsghdr(SOL_UDP, UDP_SEGMENT, (short) 1400);

        //sendmsg/recvmsg with 1*4K ByteBuffer
        ByteBuffer[] bufferArray = new ByteBuffer[1];
        ByteBuffer[] bufferArrayRecv = new ByteBuffer[1];
        bufferArray[0] = ByteBuffer.allocate(4096);
        bufferArrayRecv[0] = ByteBuffer.allocate(4096);

        StructMsghdr sendmsgHdr = new StructMsghdr(loopbackAddr,
                                                   bufferArray,
                                                   cmsg, 0);
        StructMsghdr recvmsgHdr = new StructMsghdr(new InetSocketAddress(),
                                                   bufferArrayRecv,
                                                   null, 0);

        checkSendmsgSocketAddress(AF_INET, loopbackAddr, sendmsgHdr, recvmsgHdr, 4096);
    }

    @Test
    public void test_sendmsg_af_inet6_4K() throws Exception {
        // UDP GRO not required to be enabled on kernels prior to 5.4
        assumeTrue(kernelIsAtLeast(5, 4));

        InetSocketAddress loopbackAddr = new InetSocketAddress(InetAddress.getByName("::1"), 10234);
        StructCmsghdr[] cmsg = new StructCmsghdr[1];
        cmsg[0] = new StructCmsghdr(SOL_UDP, UDP_SEGMENT, (short) 1400);

        //sendmsg/recvmsg with 1*4K ByteBuffer
        ByteBuffer[] bufferArray = new ByteBuffer[1];
        ByteBuffer[] bufferArrayRecv = new ByteBuffer[1];
        bufferArray[0] = ByteBuffer.allocate(4096);
        bufferArrayRecv[0] = ByteBuffer.allocate(4096);

        StructMsghdr sendmsgHdr = new StructMsghdr(loopbackAddr,
                                                   bufferArray,
                                                   cmsg, 0);
        StructMsghdr recvmsgHdr = new StructMsghdr(new InetSocketAddress(),
                                                   bufferArrayRecv,
                                                   null, 0);

        checkSendmsgSocketAddress(AF_INET6, loopbackAddr, sendmsgHdr, recvmsgHdr, 4096);
    }

    @Test
    public void test_sendmsg_af_inet6_4K_directBuffer() throws Exception {
        // UDP GRO not required to be enabled on kernels prior to 5.4
        assumeTrue(kernelIsAtLeast(5, 4));

        InetSocketAddress loopbackAddr = new InetSocketAddress(InetAddress.getByName("127.0.0.1"),
                                                               10234);
        StructCmsghdr[] cmsg = new StructCmsghdr[1];
        cmsg[0] = new StructCmsghdr(SOL_UDP, UDP_SEGMENT, (short) 1400);

        //sendmsg/recvmsg with 1*4K ByteBuffer
        ByteBuffer[] bufferArray = new ByteBuffer[1];
        ByteBuffer[] bufferArrayRecv = new ByteBuffer[1];
        bufferArray[0] = ByteBuffer.allocateDirect(4096); // DirectBuffer
        bufferArrayRecv[0] = ByteBuffer.allocateDirect(4096); // DirectBuffer

        StructMsghdr sendmsgHdr = new StructMsghdr(loopbackAddr,
                                                   bufferArray,
                                                   cmsg, 0);
        StructMsghdr recvmsgHdr = new StructMsghdr(new InetSocketAddress(),
                                                   bufferArrayRecv,
                                                   null, 0);

        checkSendmsgSocketAddress(AF_INET6, loopbackAddr, sendmsgHdr, recvmsgHdr, 4096);
    }

    @Test
    public void test_sendmsg_af_inet_16K_recvparts() throws Exception {
        // UDP GRO not required to be enabled on kernels prior to 5.4
        assumeTrue(kernelIsAtLeast(5, 4));

        InetSocketAddress loopbackAddr = new InetSocketAddress(InetAddress.getByName("127.0.0.1"),
                                                               10234);
        StructCmsghdr[] cmsg = new StructCmsghdr[1];
        cmsg[0] = new StructCmsghdr(SOL_UDP, UDP_SEGMENT, (short) 1400);

        //sendmsg with 4*4K ByteBuffer, recv with 1*4K ByteBuffer(already with MSG_TRUNC option)
        ByteBuffer[] bufferArray = new ByteBuffer[4];
        ByteBuffer[] bufferArrayRecv = new ByteBuffer[1];
        bufferArray[0] = ByteBuffer.allocate(4096);
        bufferArray[1] = ByteBuffer.allocate(4096);
        bufferArray[2] = ByteBuffer.allocate(4096);
        bufferArray[3] = ByteBuffer.allocate(4096);
        bufferArrayRecv[0] = ByteBuffer.allocate(4096); //receive only part of data

        StructMsghdr sendmsgHdr = new StructMsghdr(loopbackAddr,
                                                   bufferArray,
                                                   cmsg, 0);
        StructMsghdr recvmsgHdr = new StructMsghdr(new InetSocketAddress(),
                                                   bufferArrayRecv,
                                                   null, 0);

        checkSendmsgSocketAddress(AF_INET, loopbackAddr, sendmsgHdr, recvmsgHdr, 4096 * 4);
    }

    @Test
    public void test_sendmsg_af_inet_16K_reciveall() throws Exception {
        // UDP GRO not required to be enabled on kernels prior to 5.4
        assumeTrue(kernelIsAtLeast(5, 4));

        InetSocketAddress loopbackAddr = new InetSocketAddress(InetAddress.getByName("127.0.0.1"),
                                                               10234);
        StructCmsghdr[] cmsg = new StructCmsghdr[1];
        cmsg[0] = new StructCmsghdr(SOL_UDP, UDP_SEGMENT, (short) 1400);

        // Create sendmsg/recvmsg with 4*4K ByteBuffer
        ByteBuffer[] bufferArray = new ByteBuffer[4];
        bufferArray[0] = ByteBuffer.allocate(4096);
        bufferArray[1] = ByteBuffer.allocate(4096);
        bufferArray[2] = ByteBuffer.allocate(4096);
        bufferArray[3] = ByteBuffer.allocate(4096);

        StructMsghdr sendmsgHdr = new StructMsghdr(loopbackAddr, bufferArray, cmsg, 0);
        StructMsghdr recvmsgHdr = new StructMsghdr(new InetSocketAddress(), bufferArray, null, 0);

        checkSendmsgSocketAddress(AF_INET, loopbackAddr, sendmsgHdr, recvmsgHdr, 4096 * 4);
    }

    @Test
    public void test_sendmsg_af_inet_16K_receiveall_without_recv_msgname() throws Exception {
        // UDP GRO not required to be enabled on kernels prior to 5.4
        assumeTrue(kernelIsAtLeast(5, 4));

        InetSocketAddress loopbackAddr = new InetSocketAddress(InetAddress.getByName("127.0.0.1"),
                                                               10234);
        StructCmsghdr[] cmsg = new StructCmsghdr[1];
        cmsg[0] = new StructCmsghdr(SOL_UDP, UDP_SEGMENT, (short) 1400);

        // Create sendmsg/recvmsg with 4*4K ByteBuffer
        ByteBuffer[] bufferArray = new ByteBuffer[4];
        bufferArray[0] = ByteBuffer.allocate(4096);
        bufferArray[1] = ByteBuffer.allocate(4096);
        bufferArray[2] = ByteBuffer.allocate(4096);
        bufferArray[3] = ByteBuffer.allocate(4096);

        StructMsghdr sendmsgHdr = new StructMsghdr(loopbackAddr, bufferArray, cmsg, 0);
        // msg_name is unnecessary.
        StructMsghdr recvmsgHdr = new StructMsghdr(null, bufferArray, null, 0);

        checkSendmsgSocketAddress(AF_INET, loopbackAddr, sendmsgHdr, recvmsgHdr, 4096 * 4);
    }

    @Test
    public void test_sendmsg_af_inet_16K_without_send_msgcontrl() throws Exception {
        // UDP GRO not required to be enabled on kernels prior to 5.4
        assumeTrue(kernelIsAtLeast(5, 4));

        InetSocketAddress loopbackAddr = new InetSocketAddress(InetAddress.getByName("127.0.0.1"),
                                                               10234);

        // Create sendmsg/recvmsg with 4*4K ByteBuffer
        ByteBuffer[] bufferArray = new ByteBuffer[4];
        bufferArray[0] = ByteBuffer.allocate(4096);
        bufferArray[1] = ByteBuffer.allocate(4096);
        bufferArray[2] = ByteBuffer.allocate(4096);
        bufferArray[3] = ByteBuffer.allocate(4096);

        // GSO will not happen without msgcontrol.
        StructMsghdr sendmsgHdr = new StructMsghdr(loopbackAddr, bufferArray, null, 0);
        StructMsghdr recvmsgHdr = new StructMsghdr(null, bufferArray, null, 0);

        checkSendmsgSocketAddress(AF_INET, loopbackAddr, sendmsgHdr, recvmsgHdr, 4096 * 4);
    }

    @Test
    public void test_sendmsg_af_inet_abnormal() throws Exception {
        //sendmsg socket set
        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName("127.0.0.1"),
                                                          10234);
        FileDescriptor sendFd = Os.socket(AF_INET, SOCK_DGRAM, 0);

        ByteBuffer[] bufferArray = new ByteBuffer[1];
        bufferArray[0] = ByteBuffer.allocate(8192);

        try {
            StructMsghdr msgHdr = new StructMsghdr(address, null, null, 0);
            Os.sendmsg(sendFd, msgHdr, 0);
            fail("Expected NullPointerException due to invalid StructMsghdr.msg_iov(NULL)");
        } catch (NullPointerException expected) {
        }

        try {
            StructMsghdr msgHdr = new StructMsghdr(null, bufferArray, null, 0);
            Os.sendmsg(sendFd, msgHdr, 0);
            fail("Expected ErrnoException due to invalid StructMsghdr.msg_name(NULL)");
        } catch (ErrnoException expected) {
            assertEquals("Expected EDESTADDRREQ binding IPv4 socket to ::", EDESTADDRREQ,
                    expected.errno);
        }

    }

    @Test
    public void test_socketFamilies() throws Exception {
        FileDescriptor fd = Os.socket(AF_INET6, SOCK_STREAM, 0);
        Os.bind(fd, InetAddress.getByName("::"), 0);
        InetSocketAddress localSocketAddress = (InetSocketAddress) Os.getsockname(fd);
        assertEquals(Inet6Address.ANY, localSocketAddress.getAddress());

        fd = Os.socket(AF_INET6, SOCK_STREAM, 0);
        Os.bind(fd, InetAddress.getByName("0.0.0.0"), 0);
        localSocketAddress = (InetSocketAddress) Os.getsockname(fd);
        assertEquals(Inet6Address.ANY, localSocketAddress.getAddress());

        fd = Os.socket(AF_INET, SOCK_STREAM, 0);
        Os.bind(fd, InetAddress.getByName("0.0.0.0"), 0);
        localSocketAddress = (InetSocketAddress) Os.getsockname(fd);
        assertEquals(Inet4Address.ANY, localSocketAddress.getAddress());
        try {
            Os.bind(fd, InetAddress.getByName("::"), 0);
            fail("Expected ErrnoException binding IPv4 socket to ::");
        } catch (ErrnoException expected) {
            assertEquals("Expected EAFNOSUPPORT binding IPv4 socket to ::", EAFNOSUPPORT,
                    expected.errno);
        }
    }

    private static void checkSocketPing(FileDescriptor fd, InetAddress to, byte[] packet,
            byte type, byte responseType, boolean useSendto) throws Exception {
        int len = packet.length;
        packet[0] = type;
        if (useSendto) {
            assertEquals(len, Os.sendto(fd, packet, 0, len, 0, to, 0));
        } else {
            Os.connect(fd, to, 0);
            assertEquals(len, Os.sendto(fd, packet, 0, len, 0, null, 0));
        }

        int icmpId = ((InetSocketAddress) Os.getsockname(fd)).getPort();
        byte[] received = new byte[4096];
        InetSocketAddress srcAddress = new InetSocketAddress();
        assertEquals(len, Os.recvfrom(fd, received, 0, received.length, 0, srcAddress));
        assertEquals(to, srcAddress.getAddress());
        assertEquals(responseType, received[0]);
        assertEquals(received[4], (byte) (icmpId >> 8));
        assertEquals(received[5], (byte) (icmpId & 0xff));

        received = Arrays.copyOf(received, len);
        received[0] = type;
        received[2] = received[3] = 0;  // Checksum.
        received[4] = received[5] = 0;  // ICMP ID.
        assertArrayEquals(packet, received);
    }

    @Test
    public void test_socketPing() throws Exception {
        final byte ICMP_ECHO = 8, ICMP_ECHOREPLY = 0;
        final byte ICMPV6_ECHO_REQUEST = (byte) 128, ICMPV6_ECHO_REPLY = (byte) 129;
        final byte[] packet = ("\000\000\000\000" +  // ICMP type, code.
                "\000\000\000\003" +  // ICMP ID (== port), sequence number.
                "Hello myself").getBytes(StandardCharsets.US_ASCII);

        FileDescriptor fd = Os.socket(AF_INET6, SOCK_DGRAM, IPPROTO_ICMPV6);
        InetAddress ipv6Loopback = InetAddress.getByName("::1");
        checkSocketPing(fd, ipv6Loopback, packet, ICMPV6_ECHO_REQUEST, ICMPV6_ECHO_REPLY, true);
        checkSocketPing(fd, ipv6Loopback, packet, ICMPV6_ECHO_REQUEST, ICMPV6_ECHO_REPLY, false);

        fd = Os.socket(AF_INET, SOCK_DGRAM, IPPROTO_ICMP);
        InetAddress ipv4Loopback = InetAddress.getByName("127.0.0.1");
        checkSocketPing(fd, ipv4Loopback, packet, ICMP_ECHO, ICMP_ECHOREPLY, true);
        checkSocketPing(fd, ipv4Loopback, packet, ICMP_ECHO, ICMP_ECHOREPLY, false);
    }

    @Test
    public void test_Ipv4Fallback() throws Exception {
        // This number of iterations gives a ~60% chance of creating the conditions that caused
        // http://b/23088314 without making test times too long. On a hammerhead running MRZ37C
        // using vogar, this test takes about 4s.
        final int ITERATIONS = 10000;
        for (int i = 0; i < ITERATIONS; i++) {
            FileDescriptor mUdpSock = Os.socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);
            try {
                Os.bind(mUdpSock, Inet4Address.ANY, 0);
            } catch (ErrnoException e) {
                fail("ErrnoException after " + i + " iterations: " + e);
            } finally {
                Os.close(mUdpSock);
            }
        }
    }

    @Test
    public void test_unlink() throws Exception {
        File f = File.createTempFile("OsTest", "tst");
        assertTrue(f.exists());
        Os.unlink(f.getAbsolutePath());
        assertFalse(f.exists());

        try {
            Os.unlink(f.getAbsolutePath());
            fail();
        } catch (ErrnoException e) {
            assertEquals(OsConstants.ENOENT, e.errno);
        }
    }

    // b/27294715
    @Test
    public void test_recvfrom_concurrentShutdown() throws Exception {
        final FileDescriptor serverFd = Os.socket(AF_INET, SOCK_DGRAM, 0);
        Os.bind(serverFd, InetAddress.getByName("127.0.0.1"), 0);
        // Set 4s timeout
        StructTimeval tv = StructTimeval.fromMillis(4000);
        Os.setsockoptTimeval(serverFd, SOL_SOCKET, SO_RCVTIMEO, tv);

        final AtomicReference<Exception> killerThreadException = new AtomicReference<>(
            null);
        final Thread killer = new Thread(() -> {
            try {
                Thread.sleep(2000);
                try {
                    Os.shutdown(serverFd, SHUT_RDWR);
                } catch (ErrnoException expected) {
                    if (OsConstants.ENOTCONN != expected.errno) {
                        killerThreadException.set(expected);
                    }
                }
            } catch (Exception ex) {
                killerThreadException.set(ex);
            }
        });
        killer.start();

        ByteBuffer buffer = ByteBuffer.allocate(16);
        InetSocketAddress srcAddress = new InetSocketAddress();
        int received = Os.recvfrom(serverFd, buffer, 0, srcAddress);
        assertEquals(0, received);
        Os.close(serverFd);

        killer.join();
        assertNull(killerThreadException.get());
    }

    @Test
    public void test_xattr() throws Exception {
        final String NAME_TEST = "user.meow";

        final byte[] VALUE_CAKE = "cake cake cake".getBytes(StandardCharsets.UTF_8);
        final byte[] VALUE_PIE = "pie".getBytes(StandardCharsets.UTF_8);

        File file = File.createTempFile("xattr", "test");
        String path = file.getAbsolutePath();

        try {
            try {
                Os.getxattr(path, NAME_TEST);
                fail("Expected ENODATA");
            } catch (ErrnoException e) {
                assertEquals(OsConstants.ENODATA, e.errno);
            }
            assertFalse(Arrays.asList(Os.listxattr(path)).contains(NAME_TEST));

            Os.setxattr(path, NAME_TEST, VALUE_CAKE, OsConstants.XATTR_CREATE);
            byte[] xattr_create = Os.getxattr(path, NAME_TEST);
            assertTrue(Arrays.asList(Os.listxattr(path)).contains(NAME_TEST));
            assertEquals(VALUE_CAKE.length, xattr_create.length);
            assertStartsWith(VALUE_CAKE, xattr_create);

            try {
                Os.setxattr(path, NAME_TEST, VALUE_PIE, OsConstants.XATTR_CREATE);
                fail("Expected EEXIST");
            } catch (ErrnoException e) {
                assertEquals(OsConstants.EEXIST, e.errno);
            }

            Os.setxattr(path, NAME_TEST, VALUE_PIE, OsConstants.XATTR_REPLACE);
            byte[] xattr_replace = Os.getxattr(path, NAME_TEST);
            assertTrue(Arrays.asList(Os.listxattr(path)).contains(NAME_TEST));
            assertEquals(VALUE_PIE.length, xattr_replace.length);
            assertStartsWith(VALUE_PIE, xattr_replace);

            Os.removexattr(path, NAME_TEST);
            try {
                Os.getxattr(path, NAME_TEST);
                fail("Expected ENODATA");
            } catch (ErrnoException e) {
                assertEquals(OsConstants.ENODATA, e.errno);
            }
            assertFalse(Arrays.asList(Os.listxattr(path)).contains(NAME_TEST));

        } finally {
            file.delete();
        }
    }

    @Test
    public void test_xattr_NPE() throws Exception {
        File file = File.createTempFile("xattr", "test");
        final String path = file.getAbsolutePath();
        final String NAME_TEST = "user.meow";
        final byte[] VALUE_CAKE = "cake cake cake".getBytes(StandardCharsets.UTF_8);

        // getxattr
        try {
            Os.getxattr(null, NAME_TEST);
            fail();
        } catch (NullPointerException expected) {
        }
        try {
            Os.getxattr(path, null);
            fail();
        } catch (NullPointerException expected) {
        }

        // listxattr
        try {
            Os.listxattr(null);
            fail();
        } catch (NullPointerException expected) {
        }

        // removexattr
        try {
            Os.removexattr(null, NAME_TEST);
            fail();
        } catch (NullPointerException expected) {
        }
        try {
            Os.removexattr(path, null);
            fail();
        } catch (NullPointerException expected) {
        }

        // setxattr
        try {
            Os.setxattr(null, NAME_TEST, VALUE_CAKE, OsConstants.XATTR_CREATE);
            fail();
        } catch (NullPointerException expected) {
        }
        try {
            Os.setxattr(path, null, VALUE_CAKE, OsConstants.XATTR_CREATE);
            fail();
        } catch (NullPointerException expected) {
        }
        try {
            Os.setxattr(path, NAME_TEST, null, OsConstants.XATTR_CREATE);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void test_xattr_Errno() {
        final String NAME_TEST = "user.meow";
        final byte[] VALUE_CAKE = "cake cake cake".getBytes(StandardCharsets.UTF_8);

        // ENOENT, No such file or directory.
        try {
            Os.getxattr("", NAME_TEST);
            fail();
        } catch (ErrnoException e) {
            assertEquals(ENOENT, e.errno);
        }
        try {
            Os.listxattr("");
            fail();
        } catch (ErrnoException e) {
            assertEquals(ENOENT, e.errno);
        }
        try {
            Os.removexattr("", NAME_TEST);
            fail();
        } catch (ErrnoException e) {
            assertEquals(ENOENT, e.errno);
        }
        try {
            Os.setxattr("", NAME_TEST, VALUE_CAKE, OsConstants.XATTR_CREATE);
            fail();
        } catch (ErrnoException e) {
            assertEquals(ENOENT, e.errno);
        }

        // ENOTSUP, Extended attributes are not supported by the filesystem, or are disabled.
        // Since kernel version 4.9 (or some other version after 4.4), *xattr() methods
        // may set errno to EACCES instead. This behavior change is likely related to
        // https://patchwork.kernel.org/patch/9294421/ which reimplemented getxattr, setxattr,
        // and removexattr on top of generic handlers.
        final String path = "/proc/self/stat";
        try {
            Os.setxattr(path, NAME_TEST, VALUE_CAKE, OsConstants.XATTR_CREATE);
            fail();
        } catch (ErrnoException e) {
            assertTrue("Unexpected errno: " + e.errno, e.errno == ENOTSUP || e.errno == EACCES);
        }
        try {
            Os.getxattr(path, NAME_TEST);
            fail();
        } catch (ErrnoException e) {
            assertEquals(ENOTSUP, e.errno);
        }
        try {
            // Linux listxattr does not set errno.
            Os.listxattr(path);
        } catch (ErrnoException e) {
            fail();
        }
        try {
            Os.removexattr(path, NAME_TEST);
            fail();
        } catch (ErrnoException e) {
            assertTrue("Unexpected errno: " + e.errno, e.errno == ENOTSUP || e.errno == EACCES);
        }
    }

    @Test
    public void test_realpath() throws Exception {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        // This is a chicken and egg problem. We have no way of knowing whether
        // the temporary directory or one of its path elements were symlinked, so
        // we'll need this call to realpath.
        String canonicalTmpDir = Os.realpath(tmpDir.getAbsolutePath());

        // Test that "." and ".." are resolved correctly.
        assertEquals(canonicalTmpDir,
                Os.realpath(canonicalTmpDir + "/./../" + tmpDir.getName()));

        // Test that symlinks are resolved correctly.
        File target = new File(tmpDir, "target");
        File link = new File(tmpDir, "link");
        try {
            assertTrue(target.createNewFile());
            Os.symlink(target.getAbsolutePath(), link.getAbsolutePath());

            assertEquals(canonicalTmpDir + "/target",
                    Os.realpath(canonicalTmpDir + "/link"));
        } finally {
            boolean deletedTarget = target.delete();
            boolean deletedLink = link.delete();
            // Asserting this here to provide a definitive reason for
            // a subsequent failure on the same run.
            assertTrue("deletedTarget = " + deletedTarget + ", deletedLink =" + deletedLink,
                    deletedTarget && deletedLink);
        }
    }

    private int[] getKernelVersion() {
        // Example:
        // 4.9.29-g958411d --> 4.9
        String release = Os.uname().release;
        Matcher m = Pattern.compile("^(\\d+)\\.(\\d+)").matcher(release);
        assertTrue("No pattern in release string: " + release, m.find());
        return new int[]{ Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)) };
    }

    private boolean kernelIsAtLeast(int major, int minor) {
        int[] version = getKernelVersion();
        return version[0] > major || (version[0] == major && version[1] >= minor);
    }

    @Test
    public void test_socket_udpGro_setAndGet() throws Exception {
        // UDP GRO not required to be enabled on kernels prior to 5.4
        assumeTrue(kernelIsAtLeast(5, 4));

        final FileDescriptor fd = Os.socket(AF_INET6, SOCK_DGRAM, 0);
        try {
            final int setValue = 1;
            Os.setsockoptInt(fd, IPPROTO_UDP, UDP_GRO, setValue);
            // getsockopt(IPPROTO_UDP, UDP_GRO) is not implemented.
        } finally {
            Os.close(fd);
        }
    }

    @Test
    public void test_socket_udpGso_set() throws Exception {
        // UDP GSO not required to be enabled on kernels prior to 4.19.
        assumeTrue(kernelIsAtLeast(4, 19));

        final FileDescriptor fd = Os.socket(AF_INET, SOCK_DGRAM, 0);
        try {
            assertEquals(0, Os.getsockoptInt(fd, IPPROTO_UDP, UDP_SEGMENT));

            final int setValue = 1452;
            Os.setsockoptInt(fd, IPPROTO_UDP, UDP_SEGMENT, setValue);
            assertEquals(setValue, Os.getsockoptInt(fd, IPPROTO_UDP, UDP_SEGMENT));
        } finally {
            Os.close(fd);
        }
    }

    /**
     * Tests that TCP_USER_TIMEOUT can be set on a TCP socket, but doesn't test
     * that it behaves as expected.
     */
    @Test
    public void test_socket_tcpUserTimeout_setAndGet() throws Exception {
        final FileDescriptor fd = Os.socket(AF_INET, SOCK_STREAM, 0);
        try {
            int v = Os.getsockoptInt(fd, OsConstants.IPPROTO_TCP, OsConstants.TCP_USER_TIMEOUT);
            assertEquals(0, v); // system default value
            int newValue = 3000;
            Os.setsockoptInt(fd, OsConstants.IPPROTO_TCP, OsConstants.TCP_USER_TIMEOUT,
                    newValue);
            int actualValue = Os.getsockoptInt(fd, OsConstants.IPPROTO_TCP,
                    OsConstants.TCP_USER_TIMEOUT);
            // The kernel can round the requested value based on the HZ setting. We allow up to 10ms
            // difference.
            assertTrue("Returned incorrect timeout:" + actualValue,
                    Math.abs(newValue - actualValue) <= 10);
            // No need to reset the value to 0, since we're throwing the socket away
        } finally {
            Os.close(fd);
        }
    }

    @Test
    public void test_socket_tcpUserTimeout_doesNotWorkOnDatagramSocket() throws Exception {
        final FileDescriptor fd = Os.socket(AF_INET, SOCK_DGRAM, 0);
        try {
            Os.setsockoptInt(fd, OsConstants.IPPROTO_TCP, OsConstants.TCP_USER_TIMEOUT,
                    3000);
            fail("datagram (connectionless) sockets shouldn't support TCP_USER_TIMEOUT");
        } catch (ErrnoException expected) {
            // expected
        } finally {
            Os.close(fd);
        }
    }

    @Test
    public void test_socket_sockoptTimeval_readWrite() throws Exception {
        FileDescriptor fd = Os.socket(AF_INET6, SOCK_STREAM, 0);
        try {
            StructTimeval v = Os.getsockoptTimeval(fd, SOL_SOCKET, SO_RCVTIMEO);
            assertEquals(0, v.toMillis()); // system default value

            StructTimeval newValue = StructTimeval.fromMillis(3000);
            Os.setsockoptTimeval(fd, SOL_SOCKET, SO_RCVTIMEO, newValue);

            StructTimeval actualValue = Os.getsockoptTimeval(fd, SOL_SOCKET, SO_RCVTIMEO);

            // The kernel can round the requested value based on the HZ setting. We allow up to 10ms
            // difference.
            assertTrue("Returned incorrect timeout:" + actualValue,
                    Math.abs(newValue.toMillis() - actualValue.toMillis()) <= 10);
            // No need to reset the value to 0, since we're throwing the socket away
        } finally {
            Os.close(fd);
        }
    }

    @Test
    public void test_socket_setSockoptTimeval_effective() throws Exception {
        // b/176104885 Older devices can return a few ms early, add a tolerance for them
        long timeoutTolerance = kernelIsAtLeast(3, 18) ? 0 : 10;

        int timeoutValueMillis = 250;
        int allowedTimeoutMillis = 3000;

        FileDescriptor fd = Os.socket(AF_INET6, SOCK_DGRAM, IPPROTO_UDP);
        try {
            StructTimeval tv = StructTimeval.fromMillis(timeoutValueMillis);
            Os.setsockoptTimeval(fd, SOL_SOCKET, SO_RCVTIMEO, tv);
            Os.bind(fd, InetAddress.getByName("::1"), 0);

            byte[] request = new byte[1];
            long startTime = System.nanoTime();
            expectException(() -> Os.read(fd, request, 0, request.length),
                    ErrnoException.class, EAGAIN, "Expected timeout");
            long durationMillis = Duration.ofNanos(System.nanoTime() - startTime).toMillis();
            assertTrue("Timeout of " + timeoutValueMillis + "ms returned after "
                    + durationMillis +"ms",
                durationMillis >= timeoutValueMillis - timeoutTolerance);
            assertTrue("Timeout of " + timeoutValueMillis + "ms failed to return within "
                    + allowedTimeoutMillis  + "ms",
                durationMillis < allowedTimeoutMillis);
        } finally {
            Os.close(fd);
        }
    }

    @Test
    public void test_socket_setSockoptTimeval_nullFd() {
        StructTimeval tv = StructTimeval.fromMillis(500);
        expectException(
                () -> Os.setsockoptTimeval(null, SOL_SOCKET, SO_RCVTIMEO, tv),
                ErrnoException.class, EBADF, "setsockoptTimeval(null, ...)");
    }

    @Test
    public void test_socket_setSockoptTimeval_fileFd() throws Exception {
        File testFile = createTempFile("test_socket_setSockoptTimeval_invalidFd", "");
        try (FileInputStream fis = new FileInputStream(testFile)) {
            final FileDescriptor fd = fis.getFD();

            StructTimeval tv = StructTimeval.fromMillis(500);
            expectException(
                    () -> Os.setsockoptTimeval(fd, SOL_SOCKET, SO_RCVTIMEO, tv),
                    ErrnoException.class, ENOTSOCK, "setsockoptTimeval(<file fd>, ...)");
        }
    }

    @Test
    public void test_socket_setSockoptTimeval_badFd() throws Exception {
        StructTimeval tv = StructTimeval.fromMillis(500);
        FileDescriptor invalidFd = Os.socket(AF_INET6, SOCK_STREAM, 0);
        Os.close(invalidFd);

        expectException(
                () -> Os.setsockoptTimeval(invalidFd, SOL_SOCKET, SO_RCVTIMEO, tv),
                ErrnoException.class, EBADF, "setsockoptTimeval(<closed fd>, ...)");
    }

    @Test
    public void test_socket_setSockoptTimeval_invalidLevel() throws Exception {
        StructTimeval tv = StructTimeval.fromMillis(500);
        FileDescriptor fd = Os.socket(AF_INET6, SOCK_STREAM, 0);
        try {
            expectException(
                    () -> Os.setsockoptTimeval(fd, -1, SO_RCVTIMEO, tv),
                    ErrnoException.class, ENOPROTOOPT,
                    "setsockoptTimeval(fd, <invalid level>, ...)");
        } finally {
            Os.close(fd);
        }
    }

    @Test
    public void test_socket_setSockoptTimeval_invalidOpt() throws Exception {
        StructTimeval tv = StructTimeval.fromMillis(500);
        FileDescriptor fd = Os.socket(AF_INET6, SOCK_STREAM, 0);
        try {
            expectException(
                    () -> Os.setsockoptTimeval(fd, SOL_SOCKET, -1, tv),
                    ErrnoException.class, ENOPROTOOPT,
                    "setsockoptTimeval(fd, <invalid level>, ...)");
        } finally {
            Os.close(fd);
        }
    }

    @Test
    public void test_socket_setSockoptTimeval_nullTimeVal() throws Exception {
        FileDescriptor fd = Os.socket(AF_INET6, SOCK_STREAM, 0);
        try {
            expectException(
                    () -> Os.setsockoptTimeval(fd, SOL_SOCKET, SO_RCVTIMEO, null),
                    NullPointerException.class, null, "setsockoptTimeval(..., null)");
        } finally {
            Os.close(fd);
        }
    }

    @Test
    public void test_socket_getSockoptTimeval_invalidOption() throws Exception {
        FileDescriptor fd = Os.socket(AF_INET6, SOCK_STREAM, 0);
        try {
            expectException(
                    () -> Os.getsockoptTimeval(fd, SOL_SOCKET, SO_DEBUG),
                    IllegalArgumentException.class, null,
                    "getsockoptTimeval(..., <non-timeval option>)");
        } finally {
            Os.close(fd);
        }
    }

    @Test
    public void test_if_nametoindex_if_indextoname() throws Exception {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        assertNotNull(networkInterfaces);
        List<NetworkInterface> nis = Collections.list(networkInterfaces);

        assertTrue(nis.size() > 0);
        for (NetworkInterface ni : nis) {
            int index = ni.getIndex();
            String name = ni.getName();
            assertEquals(index, Os.if_nametoindex(name));
            assertEquals(Os.if_indextoname(index), name);
        }

        assertEquals(0, Os.if_nametoindex("this-interface-does-not-exist"));
        assertNull(Os.if_indextoname(-1000));

        try {
            Os.if_nametoindex(null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    private static void assertStartsWith(byte[] expectedContents, byte[] container) {
        for (int i = 0; i < expectedContents.length; i++) {
            if (expectedContents[i] != container[i]) {
                fail("Expected " + Arrays.toString(expectedContents) + " but found "
                        + Arrays.toString(expectedContents));
            }
        }
    }

    @Test
    public void test_readlink() throws Exception {
        File path = new File(TestIoUtils.createTemporaryDirectory("test_readlink"), "symlink");

        // ext2 and ext4 have PAGE_SIZE limits on symlink targets.
        // If file encryption is enabled, there's extra overhead to store the
        // size of the encrypted symlink target. There's also an off-by-one
        // in current kernels (and marlin/sailfish where we're seeing this
        // failure are still on 3.18, far from current). Given that we don't
        // really care here, just use 2048 instead. http://b/33306057.
        int size = 2048;
        StringBuilder xs = new StringBuilder();
        for (int i = 0; i < size - 1; ++i) {
            xs.append("x");
        }

        Os.symlink(xs.toString(), path.getPath());

        assertEquals(xs.toString(), Os.readlink(path.getPath()));
    }

    // Address should be correctly set for empty packets. http://b/33481605
    @Test
    public void test_recvfrom_EmptyPacket() throws Exception {
        try (DatagramSocket ds = new DatagramSocket();
             DatagramSocket srcSock = new DatagramSocket()) {
            srcSock.send(new DatagramPacket(new byte[0], 0, ds.getLocalSocketAddress()));

            byte[] recvBuf = new byte[16];
            InetSocketAddress address = new InetSocketAddress();
            int recvCount =
                    android.system.Os.recvfrom(ds.getFileDescriptor$(), recvBuf, 0, 16, 0, address);
            assertEquals(0, recvCount);
            assertTrue(address.getAddress().isLoopbackAddress());
            assertEquals(srcSock.getLocalPort(), address.getPort());
        }
    }

    @Test
    public void test_fstat_times() throws Exception {
        File file = File.createTempFile("OsTest", "fstattest");
        FileOutputStream fos = new FileOutputStream(file);
        StructStat structStat1 = Os.fstat(fos.getFD());
        assertEquals(structStat1.st_mtim.tv_sec, structStat1.st_mtime);
        assertEquals(structStat1.st_ctim.tv_sec, structStat1.st_ctime);
        assertEquals(structStat1.st_atim.tv_sec, structStat1.st_atime);
        Thread.sleep(100);
        fos.write(new byte[] { 1, 2, 3 });
        fos.flush();
        StructStat structStat2 = Os.fstat(fos.getFD());
        fos.close();

        assertEquals(-1, structStat1.st_mtim.compareTo(structStat2.st_mtim));
        assertEquals(-1, structStat1.st_ctim.compareTo(structStat2.st_ctim));
        assertEquals(0, structStat1.st_atim.compareTo(structStat2.st_atim));
    }

    @Test
    public void test_getrlimit() throws Exception {
        StructRlimit rlimit = Os.getrlimit(OsConstants.RLIMIT_NOFILE);
        // We can't really make any assertions about these values since they might vary from
        // device to device and even process to process. We do know that they will be greater
        // than zero, though.
        assertTrue(rlimit.rlim_cur > 0);
        assertTrue(rlimit.rlim_max > 0);
    }

    // http://b/65051835
    @Test
    public void test_pipe2_errno() {
        try {
            // flag=-1 is not a valid value for pip2, will EINVAL
            Os.pipe2(-1);
            fail();
        } catch (ErrnoException expected) {
        }
    }

    // http://b/65051835
    @Test
    public void test_sendfile_errno() {
        try {
            // FileDescriptor.out is not open for input, will cause EBADF
            Int64Ref offset = new Int64Ref(10);
            Os.sendfile(FileDescriptor.out, FileDescriptor.out, offset, 10);
            fail();
        } catch (ErrnoException expected) {
        }
    }

    @Test
    public void test_sendfile_null() throws Exception {
        File in = createTempFile("test_sendfile_null", "Hello, world!");
        try {
            int len = "Hello".length();
            assertEquals("Hello", checkSendfile(in, null, len, null));
        } finally {
            in.delete();
        }
    }

    @Test
    public void test_sendfile_offset() throws Exception {
        File in = createTempFile("test_sendfile_offset", "Hello, world!");
        try {
            // checkSendfile(sendFileImplToUse, in, startOffset, maxBytes, expectedEndOffset)
            assertEquals("Hello", checkSendfile(in, 0L, 5, 5L));
            assertEquals("ello,", checkSendfile(in, 1L, 5, 6L));
            // At offset 9, only 4 bytes/chars available, even though we're asking for 5.
            assertEquals("rld!", checkSendfile(in, 9L, 5, 13L));
            assertEquals("", checkSendfile(in, 1L, 0, 1L));
        } finally {
            in.delete();
        }
    }

    private static String checkSendfile(File in, Long startOffset,
            int maxBytes, Long expectedEndOffset) throws IOException, ErrnoException {
        File out = File.createTempFile(OsTest.class.getSimpleName() + "_checkSendFile", ".out");
        try (FileInputStream inStream = new FileInputStream(in)) {
            FileDescriptor inFd = inStream.getFD();
            try (FileOutputStream outStream = new FileOutputStream(out)) {
                FileDescriptor outFd = outStream.getFD();
                Int64Ref offset = (startOffset == null) ? null : new Int64Ref(startOffset);
                android.system.Os.sendfile(outFd, inFd, offset, maxBytes);
                assertEquals(expectedEndOffset, offset == null ? null : offset.value);
            }
            return TestIoUtils.readFileAsString(out.getPath());
        } finally {
            out.delete();
        }
    }

    private static File createTempFile(String namePart, String contents) throws IOException {
        File f = File.createTempFile(OsTest.class.getSimpleName() + namePart, ".in");
        try (FileWriter writer = new FileWriter(f)) {
            writer.write(contents);
        }
        return f;
    }

    @Test
    public void test_odirect() throws Exception {
        File testFile = createTempFile("test_odirect", "");
        try {
            FileDescriptor fd =
                    Os.open(testFile.toString(), O_WRONLY | O_DIRECT, S_IRUSR | S_IWUSR);
            assertNotNull(fd);
            assertTrue(fd.valid());
            int flags = Os.fcntlVoid(fd, F_GETFL);
            assertTrue("Expected file flags to include " + O_DIRECT + ", actual value: " + flags,
                    0 != (flags & O_DIRECT));
            Os.close(fd);
        } finally {
            testFile.delete();
        }
    }

    @Test
    public void test_splice() throws Exception {
        FileDescriptor[] pipe = Os.pipe2(0);
        File in = createTempFile("splice1", "foobar");
        File out = createTempFile("splice2", "");

        Int64Ref offIn = new Int64Ref(1);
        Int64Ref offOut = new Int64Ref(0);

        // Splice into pipe
        try (FileInputStream streamIn = new FileInputStream(in)) {
            FileDescriptor fdIn = streamIn.getFD();
            long result = Os
                    .splice(fdIn, offIn, pipe[1], null /* offOut */, 10 /* len */, 0 /* flags */);
            assertEquals(5, result);
            assertEquals(6, offIn.value);
        }

        // Splice from pipe
        try (FileOutputStream streamOut = new FileOutputStream(out)) {
            FileDescriptor fdOut = streamOut.getFD();
            long result = Os
                    .splice(pipe[0], null /* offIn */, fdOut, offOut, 10 /* len */, 0 /* flags */);
            assertEquals(5, result);
            assertEquals(5, offOut.value);
        }

        assertEquals("oobar", TestIoUtils.readFileAsString(out.getPath()));

        Os.close(pipe[0]);
        Os.close(pipe[1]);
    }

    @Test
    public void test_splice_errors() throws Exception {
        File in = createTempFile("splice3", "");
        File out = createTempFile("splice4", "");
        FileDescriptor[] pipe = Os.pipe2(0);

        //.fdIn == null
        try {
            Os.splice(null /* fdIn */, null /* offIn */, pipe[1],
                    null /*offOut*/, 10 /* len */, 0 /* flags */);
            fail();
        } catch (ErrnoException expected) {
            assertEquals(EBADF, expected.errno);
        }

        //.fdOut == null
        try {
            Os.splice(pipe[0] /* fdIn */, null /* offIn */, null  /* fdOut */,
                    null /*offOut*/, 10 /* len */, 0 /* flags */);
            fail();
        } catch (ErrnoException expected) {
            assertEquals(EBADF, expected.errno);
        }

        // No pipe fd
        try (FileOutputStream streamOut = new FileOutputStream(out)) {
            try (FileInputStream streamIn = new FileInputStream(in)) {
                FileDescriptor fdIn = streamIn.getFD();
                FileDescriptor fdOut = streamOut.getFD();
                Os.splice(fdIn, null  /* offIn */, fdOut, null /* offOut */, 10 /* len */,
                        0 /* flags */);
                fail();
            } catch (ErrnoException expected) {
                assertEquals(EINVAL, expected.errno);
            }
        }

        Os.close(pipe[0]);
        Os.close(pipe[1]);
    }

    @Test
    public void testCloseNullFileDescriptor() throws Exception {
        try {
            Os.close(null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void testSocketpairNullFileDescriptor1() throws Exception {
        try {
            Os.socketpair(AF_UNIX, SOCK_STREAM, 0, null, new FileDescriptor());
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void testSocketpairNullFileDescriptor2() throws Exception {
        try {
            Os.socketpair(AF_UNIX, SOCK_STREAM, 0, new FileDescriptor(), null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void testSocketpairNullFileDescriptorBoth() throws Exception {
        try {
            Os.socketpair(AF_UNIX, SOCK_STREAM, 0, null, null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void testInetPtonIpv4() {
        String srcAddress = "127.0.0.1";
        InetAddress inetAddress = Os.inet_pton(AF_INET, srcAddress);
        assertEquals(srcAddress, inetAddress.getHostAddress());
    }

    @Test
    public void testInetPtonIpv6() {
        String srcAddress = "1123:4567:89ab:cdef:fedc:ba98:7654:3210";
        InetAddress inetAddress = Os.inet_pton(AF_INET6, srcAddress);
        assertEquals(srcAddress, inetAddress.getHostAddress());
    }

    @Test
    public void testInetPtonInvalidFamily() {
        String srcAddress = "127.0.0.1";
        InetAddress inetAddress = Os.inet_pton(AF_UNIX, srcAddress);
        assertNull(inetAddress);
    }

    @Test
    public void testInetPtonWrongFamily() {
        String srcAddress = "127.0.0.1";
        InetAddress inetAddress = Os.inet_pton(AF_INET6, srcAddress);
        assertNull(inetAddress);
    }

    @Test
    public void testInetPtonInvalidData() {
        String srcAddress = "10.1";
        InetAddress inetAddress = Os.inet_pton(AF_INET, srcAddress);
        assertNull(inetAddress);
    }

    /**
     * Verifies the {@link OsConstants#MAP_ANONYMOUS}.
     */
    @Test
    public void testMapAnonymous() throws Exception {
        final long size = 4096;
        final long address = Os.mmap(0, size, PROT_READ,
                MAP_PRIVATE | MAP_ANONYMOUS, new FileDescriptor(), 0);
        assertTrue(address > 0);
        Os.munmap(address, size);
    }

    @Test
    public void testMemfdCreate() throws Exception {
        FileDescriptor fd = null;
        try {
            fd = Os.memfd_create("test_memfd", 0);
            assertNotNull(fd);
            assertTrue(fd.valid());

            StructStat stat = Os.fstat(fd);
            assertEquals(0, stat.st_size);

            final byte[] expected = new byte[] {1, 2, 3, 4};
            Os.write(fd, expected, 0, expected.length);
            stat = Os.fstat(fd);
            assertEquals(expected.length, stat.st_size);

            byte[] actual = new byte[expected.length];
            // should be seekable
            Os.lseek(fd, 0, SEEK_SET);
            Os.read(fd, actual, 0, actual.length);
            assertArrayEquals(expected, actual);
        } finally {
            if (fd != null) {
                Os.close(fd);
                fd = null;
            }
        }
    }

    @Test
    public void testMemfdCreateFlags() throws Exception {
        FileDescriptor fd = null;

        // test that MFD_CLOEXEC is obeyed
        try {
            fd = Os.memfd_create("test_memfd", 0);
            assertNotNull(fd);
            assertTrue(fd.valid());
            int flags = Os.fcntlVoid(fd, F_GETFD);
            assertEquals("Expected flags to not include " + FD_CLOEXEC + ", actual value: " + flags,
                0, (flags & FD_CLOEXEC));
        } finally {
            if (fd != null) {
                Os.close(fd);
                fd = null;
            }
        }
        try {
            fd = Os.memfd_create("test_memfd", MFD_CLOEXEC);
            assertNotNull(fd);
            assertTrue(fd.valid());
            int flags = Os.fcntlVoid(fd, F_GETFD);
            assertTrue("Expected flags to include " + FD_CLOEXEC + ", actual value: " + flags,
                    0 != (flags & FD_CLOEXEC));
        } finally {
            if (fd != null) {
                Os.close(fd);
                fd = null;
            }
        }
    }

    @Test
    public void testMemfdCreateErrno() {
        expectException(() -> Os.memfd_create(null, 0), NullPointerException.class, null,
                "memfd_create(null, 0)");

        expectException(() -> Os.memfd_create("test_memfd", 0xffff), ErrnoException.class, EINVAL,
                "memfd_create(\"test_memfd\", 0xffff)");
    }
}
