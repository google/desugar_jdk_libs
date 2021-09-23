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

package libcore.libcore.io;

import static android.system.OsConstants.AF_INET6;
import static android.system.OsConstants.AF_UNIX;
import static android.system.OsConstants.IPPROTO_TCP;
import static android.system.OsConstants.IPPROTO_UDP;
import static android.system.OsConstants.F_SETFL;
import static android.system.OsConstants.SOCK_DGRAM;
import static android.system.OsConstants.O_NONBLOCK;
import static android.system.OsConstants.SOCK_STREAM;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.system.ErrnoException;
import android.system.OsConstants;
import android.system.StructAddrinfo;
import android.system.UnixSocketAddress;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import libcore.io.BlockGuardOs;
import libcore.io.IoUtils;
import libcore.io.Libcore;
import libcore.io.Os;

import dalvik.system.BlockGuard;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class BlockGuardOsTest {

    final static Pattern pattern = Pattern.compile("[\\w\\$]+\\([^)]*\\)");

    @Mock private Os mockOsDelegate;
    @Mock private BlockGuard.Policy mockThreadPolicy;
    @Mock private BlockGuard.VmPolicy mockVmPolicy;

    private BlockGuard.Policy savedThreadPolicy;
    private BlockGuard.VmPolicy savedVmPolicy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        savedThreadPolicy = BlockGuard.getThreadPolicy();
        savedVmPolicy = BlockGuard.getVmPolicy();
        BlockGuard.setThreadPolicy(mockThreadPolicy);
        BlockGuard.setVmPolicy(mockVmPolicy);
    }

    @After
    public void tearDown() {
        BlockGuard.setVmPolicy(savedVmPolicy);
        BlockGuard.setThreadPolicy(savedThreadPolicy);
    }

    @Test
    public void test_blockguardOsIsNotifiedByDefault_rename() {
        String oldPath = "BlockGuardOsTest/missing/old/path";
        String newPath = "BlockGuardOsTest/missing/new/path";
        try {
            // We try not to be prescriptive about the exact default Os implementation.
            // Whatever default Os is installed, we do expect BlockGuard to be called.
            Os.getDefault().rename(oldPath, newPath);
        } catch (ErrnoException ignored) {
        }
        verify(mockThreadPolicy).onWriteToDisk();
        verify(mockVmPolicy).onPathAccess(oldPath);
        verify(mockVmPolicy).onPathAccess(newPath);
    }

    @Test
    public void test_android_getaddrinfo_networkPolicy() {
        InetAddress[] addresses = new InetAddress[] { InetAddress.getLoopbackAddress() };
        when(mockOsDelegate.android_getaddrinfo(anyString(), any(), anyInt()))
                .thenReturn(addresses);

        BlockGuardOs blockGuardOs = new BlockGuardOs(mockOsDelegate);

        // Test with a numeric address that will not trigger a network policy check.
        {
            final String node = "numeric";
            final int netId = 1234;
            final StructAddrinfo numericAddrInfo = new StructAddrinfo();
            numericAddrInfo.ai_flags = OsConstants.AI_NUMERICHOST;
            InetAddress[] actual =
                    blockGuardOs.android_getaddrinfo(node, numericAddrInfo, netId);

            verify(mockThreadPolicy, times(0)).onNetwork();
            verify(mockOsDelegate, times(1)).android_getaddrinfo(node, numericAddrInfo, netId);
            assertSame(addresses, actual);
        }

        // Test with a non-numeric address that will trigger a network policy check.
        {
            final String node = "non-numeric";
            final int netId = 1234;
            final StructAddrinfo nonNumericAddrInfo = new StructAddrinfo();
            InetAddress[] actual =
                    blockGuardOs.android_getaddrinfo(node, nonNumericAddrInfo, netId);

            verify(mockThreadPolicy, times(1)).onNetwork();
            verify(mockOsDelegate, times(1)).android_getaddrinfo(node, nonNumericAddrInfo, netId);
            assertSame(addresses, actual);
        }
    }

    @Test
    public void test_nonblock() throws ErrnoException, IOException {
        FileDescriptor unixSocket = Libcore.os.socket(AF_UNIX, SOCK_DGRAM, 0);
        FileDescriptor udpSocket = Libcore.os.socket(AF_INET6, SOCK_DGRAM, IPPROTO_UDP);
        Libcore.os.fcntlInt(unixSocket, F_SETFL, O_NONBLOCK);
        Libcore.os.fcntlInt(udpSocket, F_SETFL, O_NONBLOCK);
        try {
            assertTrue(BlockGuardOs.isNonBlockingFile(unixSocket));
            assertTrue(BlockGuardOs.isNonBlockingFile(udpSocket));
        } finally {
            IoUtils.closeQuietly(unixSocket);
        }
    }

    @Test
    public void test_unixSocket() throws ErrnoException, IOException {
        FileDescriptor unixSocket = Libcore.os.socket(AF_UNIX, SOCK_DGRAM, 0);
        FileDescriptor udpSocket = Libcore.os.socket(AF_INET6, SOCK_DGRAM, IPPROTO_UDP);
        try {
            assertTrue(BlockGuardOs.isUnixSocket(unixSocket));
            assertFalse(BlockGuardOs.isUnixSocket(udpSocket));
        } finally {
            IoUtils.closeQuietly(unixSocket);
        }
    }

    @Test
    public void test_accept_networkPolicy() throws ErrnoException, IOException {
        BlockGuardOs blockGuardOs = new BlockGuardOs(mockOsDelegate);

        FileDescriptor unixSocket = Libcore.os.socket(AF_UNIX, SOCK_DGRAM, 0);
        Libcore.os.fcntlInt(unixSocket, F_SETFL, O_NONBLOCK);
        SocketAddress address = UnixSocketAddress.createAbstract("test_accept_networkPolicy");
        Libcore.os.bind(unixSocket, address);
        try {
            assertNull(blockGuardOs.accept(unixSocket, address));
        } finally {
            IoUtils.closeQuietly(unixSocket);
        }
    }

    @Test
    public void test_connect_networkPolicy() throws ErrnoException, IOException {
        BlockGuardOs blockGuardOs = new BlockGuardOs(mockOsDelegate);

        // Test connect with a UDP socket that will not trigger a network policy check.
        FileDescriptor udpSocket = Libcore.os.socket(AF_INET6, SOCK_DGRAM, IPPROTO_UDP);
        try {
            blockGuardOs.connect(udpSocket, InetAddress.getLoopbackAddress(), 0);
            verify(mockThreadPolicy, never()).onNetwork();
            verify(mockOsDelegate, times(1)).connect(eq(udpSocket), any(), anyInt());
        } finally {
            IoUtils.closeQuietly(udpSocket);
        }

        // Test connect with a TCP socket that will trigger a network policy check.
        FileDescriptor tcpSocket = Libcore.os.socket(AF_INET6, SOCK_STREAM, IPPROTO_TCP);
        try {
            blockGuardOs.connect(tcpSocket, InetAddress.getLoopbackAddress(), 0);
            verify(mockThreadPolicy, times(1)).onNetwork();
            verify(mockOsDelegate, times(1)).connect(eq(tcpSocket), any(), anyInt());
        } finally {
            IoUtils.closeQuietly(tcpSocket);
        }
    }

    /**
     * Checks that BlockGuardOs is updated when the Os interface changes. BlockGuardOs extends
     * ForwardingOs so doing so isn't an obvious step and it can be missed. When adding methods to
     * Os developers must give consideration to whether extra behavior should be added to
     * BlockGuardOs. Developers failing this test should add to the list of method below
     * (if the calls cannot block) or should add an override for the method with the appropriate
     * calls to BlockGuard (if the calls can block).
     */
    @Test
    public void test_checkNewMethodsInPosix() {
        List<String> methodsNotRequireBlockGuardChecks = Arrays.asList(
                "android_fdsan_exchange_owner_tag(java.io.FileDescriptor,long,long)",
                "android_fdsan_get_owner_tag(java.io.FileDescriptor)",
                "android_fdsan_get_tag_type(long)",
                "android_fdsan_get_tag_value(long)",
                "bind(java.io.FileDescriptor,java.net.InetAddress,int)",
                "bind(java.io.FileDescriptor,java.net.SocketAddress)",
                "capget(android.system.StructCapUserHeader)",
                "capset(android.system.StructCapUserHeader,android.system.StructCapUserData[])",
                "dup(java.io.FileDescriptor)",
                "dup2(java.io.FileDescriptor,int)",
                "environ()",
                "fcntlInt(java.io.FileDescriptor,int,int)",
                "fcntlVoid(java.io.FileDescriptor,int)",
                "gai_strerror(int)",
                "getegid()",
                "getenv(java.lang.String)",
                "geteuid()",
                "getgid()",
                "getgroups()",
                "getifaddrs()",
                "getnameinfo(java.net.InetAddress,int)",
                "getpeername(java.io.FileDescriptor)",
                "getpgid(int)",
                "getpid()",
                "getppid()",
                "getpwnam(java.lang.String)",
                "getpwuid(int)",
                "getrlimit(int)",
                "getsockname(java.io.FileDescriptor)",
                "getsockoptByte(java.io.FileDescriptor,int,int)",
                "getsockoptInAddr(java.io.FileDescriptor,int,int)",
                "getsockoptInt(java.io.FileDescriptor,int,int)",
                "getsockoptLinger(java.io.FileDescriptor,int,int)",
                "getsockoptTimeval(java.io.FileDescriptor,int,int)",
                "getsockoptUcred(java.io.FileDescriptor,int,int)",
                "gettid()",
                "getuid()",
                "if_indextoname(int)",
                "if_nametoindex(java.lang.String)",
                "inet_pton(int,java.lang.String)",
                "ioctlFlags(java.io.FileDescriptor,java.lang.String)",
                "ioctlInetAddress(java.io.FileDescriptor,int,java.lang.String)",
                "ioctlInt(java.io.FileDescriptor,int)",
                "ioctlMTU(java.io.FileDescriptor,java.lang.String)",
                "isatty(java.io.FileDescriptor)",
                "kill(int,int)",
                "listen(java.io.FileDescriptor,int)",
                "listxattr(java.lang.String)",
                "memfd_create(java.lang.String,int)",
                "mincore(long,long,byte[])",
                "mlock(long,long)",
                "mmap(long,long,int,int,java.io.FileDescriptor,long)",
                "munlock(long,long)",
                "munmap(long,long)",
                "pipe2(int)",
                "prctl(int,long,long,long,long)",
                "setegid(int)",
                "setenv(java.lang.String,java.lang.String,boolean)",
                "seteuid(int)",
                "setgid(int)",
                "setgroups(int[])",
                "setpgid(int,int)",
                "setregid(int,int)",
                "setreuid(int,int)",
                "setsid()",
                "setsockoptByte(java.io.FileDescriptor,int,int,int)",
                "setsockoptGroupReq(java.io.FileDescriptor,int,int,android.system.StructGroupReq)",
                "setsockoptIfreq(java.io.FileDescriptor,int,int,java.lang.String)",
                "setsockoptInt(java.io.FileDescriptor,int,int,int)",
                "setsockoptIpMreqn(java.io.FileDescriptor,int,int,int)",
                "setsockoptLinger(java.io.FileDescriptor,int,int,android.system.StructLinger)",
                "setsockoptTimeval(java.io.FileDescriptor,int,int,android.system.StructTimeval)",
                "setuid(int)",
                "shutdown(java.io.FileDescriptor,int)",
                "strerror(int)",
                "strsignal(int)",
                "sysconf(int)",
                "tcdrain(java.io.FileDescriptor)",
                "tcsendbreak(java.io.FileDescriptor,int)",
                "umask(int)",
                "uname()",
                "unsetenv(java.lang.String)",
                "waitpid(int,android.system.Int32Ref,int)");
        Set<String> methodsNotRequiredBlockGuardCheckSet = new HashSet<>(
                methodsNotRequireBlockGuardChecks);

        Set<String> methodsInBlockGuardOs = new HashSet<>();

        // Populate the set of the public methods implemented in BlockGuardOs.
        for (Method method : BlockGuardOs.class.getDeclaredMethods()) {
            String methodNameAndParameters = getMethodNameAndParameters(method.toString());
            methodsInBlockGuardOs.add(methodNameAndParameters);
        }

        // Verify that all the methods in libcore.io.Os should either be overridden in BlockGuardOs
        // or else they should be in the "methodsNotRequiredBlockGuardCheckSet".
        // We don't care about static methods because they can't be overridden.
        for (Method method : Os.class.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            String methodSignature = method.toString();
            String methodNameAndParameters = getMethodNameAndParameters(methodSignature);
            if (!methodsNotRequiredBlockGuardCheckSet.contains(methodNameAndParameters) &&
                    !methodsInBlockGuardOs.contains(methodNameAndParameters)) {
                fail(methodNameAndParameters + " is not present in "
                        + "methodsNotRequiredBlockGuardCheckSet and is also not overridden in"
                        + " BlockGuardOs class. Either override the method in BlockGuardOs or"
                        + " add it in the methodsNotRequiredBlockGuardCheckSet");

            }
        }
    }

    /**
     * Extract method name and parameter information from the method signature.
     * For example, for input "public void package.class.method(A,B)", the output will be
     * "method(A,B)".
     */
    private static String getMethodNameAndParameters(String methodSignature) {
        Matcher methodPatternMatcher = pattern.matcher(methodSignature);
        if (methodPatternMatcher.find()) {
            return methodPatternMatcher.group();
        } else {
            throw new IllegalArgumentException(methodSignature);
        }
    }
}
