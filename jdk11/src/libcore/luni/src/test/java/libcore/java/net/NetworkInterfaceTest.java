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

package libcore.java.net;

import junit.framework.TestCase;
import org.mockito.Mockito;

import android.system.StructIfaddrs;
import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import libcore.io.IoUtils;
import libcore.io.Libcore;
import libcore.io.Os;

import static android.system.OsConstants.AF_INET;
import static android.system.OsConstants.IFF_LOOPBACK;
import static android.system.OsConstants.IFF_MULTICAST;
import static android.system.OsConstants.IFF_POINTOPOINT;
import static android.system.OsConstants.IFF_RUNNING;
import static android.system.OsConstants.IFF_UP;
import static android.system.OsConstants.SOCK_DGRAM;
import static java.net.NetworkInterface.getNetworkInterfaces;
import static org.mockito.ArgumentMatchers.anyString;

public class NetworkInterfaceTest extends TestCase {
    // http://code.google.com/p/android/issues/detail?id=13784
    private final static int ARPHRD_ETHER = 1; // from if_arp.h
    public void testIPv6() throws Exception {
        NetworkInterface lo = NetworkInterface.getByName("lo");
        Set<InetAddress> actual = new HashSet<InetAddress>(Collections.list(lo.getInetAddresses()));

        Set<InetAddress> expected = new HashSet<InetAddress>();
        expected.add(Inet4Address.LOOPBACK);
        expected.add(Inet6Address.getByAddress("localhost", new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, null));

        assertEquals(expected, actual);
    }

    /*
    // http://code.google.com/p/android/issues/detail?id=34022
    public void test_collectIpv6Addresses_3digitInterfaceIndex() throws Exception {
        String lines[] = new String[] {
                "fe800000000000000000000000000000 407 40 20 80    wlan0" };
        List<InetAddress> addresses = new ArrayList<InetAddress>(1);
        List<InterfaceAddress> ifAddresses = new ArrayList<InterfaceAddress>(1);

        NetworkInterface.collectIpv6Addresses("wlan0", 1, addresses,
                ifAddresses, lines);
        assertEquals(1, addresses.size());
        assertEquals(1, ifAddresses.size());
        // Make sure the prefix length (field #3) is parsed correctly
        assertEquals(4*16 + 0, ifAddresses.get(0).getNetworkPrefixLength());
    }

    public void test_collectIpv6Addresses_skipsUnmatchedLines() throws Exception {
        String[] lines = new String[] {
                "fe800000000000000000000000000000 40 40 20 80    wlan0",
                "fe100000000000000000000000000000 41 40 20 80    wlan1",
                "feb00000000000000000000000000000 42 40 20 80    wlan2" };
        List<InetAddress> addresses = new ArrayList<InetAddress>(1);
        List<InterfaceAddress> ifAddresses = new ArrayList<InterfaceAddress>(1);

        NetworkInterface.collectIpv6Addresses("wlan0", 1, addresses,
                ifAddresses, lines);
        assertEquals(1, addresses.size());
        assertEquals(1, ifAddresses.size());
    }*/

    public void testInterfaceProperties() throws Exception {
        for (NetworkInterface nif : Collections.list(getNetworkInterfaces())) {
            assertEquals(nif, NetworkInterface.getByName(nif.getName()));
            // Skip interfaces that are inactive
            if (nif.isUp() == false) {
                continue;
            }
            // Ethernet
            if (isEthernet(nif.getName())) {
                for (InterfaceAddress ia : nif.getInterfaceAddresses()) {
                    if (ia.getAddress() instanceof Inet4Address) {
                        assertNotNull(ia.getBroadcast());
                    }
                }
            }
        }
    }

    public void testGetHardwareAddress_returnsNull() throws Exception {
        // Hardware addresses should be unavailable to non-system apps.
        for (NetworkInterface nif : Collections.list(getNetworkInterfaces())) {
            assertNull(nif.getHardwareAddress());
        }
    }

    public void testLoopback() throws Exception {
        NetworkInterface lo = NetworkInterface.getByName("lo");
        assertNull(lo.getHardwareAddress());
        for (InterfaceAddress ia : lo.getInterfaceAddresses()) {
            assertNull(ia.getBroadcast());
        }
    }

    public void testDumpAll() throws Exception {
        Set<String> allNames = new HashSet<String>();
        Set<Integer> allIndexes = new HashSet<Integer>();
        for (NetworkInterface nif : Collections.list(getNetworkInterfaces())) {
            System.err.println(nif);
            System.err.println(nif.getInterfaceAddresses());
            String flags = nif.isUp() ? "UP" : "DOWN";
            if (nif.isLoopback()) {
                flags += " LOOPBACK";
            }
            if (nif.isPointToPoint()) {
                flags += " PTP";
            }
            if (nif.isVirtual()) {
                flags += " VIRTUAL";
            }
            if (nif.supportsMulticast()) {
                flags += " MULTICAST";
            }
            flags += " MTU=" + nif.getMTU();
            byte[] mac = nif.getHardwareAddress();
            if (mac != null) {
                flags += " HWADDR=";
                for (int i = 0; i < mac.length; ++i) {
                    if (i > 0) {
                        flags += ":";
                    }
                    flags += String.format("%02x", mac[i]);
                }
            }
            System.err.println(flags);
            System.err.println("-");

            assertFalse(allNames.contains(nif.getName()));
            allNames.add(nif.getName());

            assertFalse(allIndexes.contains(nif.getIndex()));
            allIndexes.add(nif.getIndex());
        }
    }

    // b/28903817
    public void testInterfaceRemoval() throws Exception {
        NetworkInterface lo = NetworkInterface.getByName("lo");

        // Simulate interface removal by changing it's name to unused value.
        // This works only because getHardwareAddress (and others) is using name to fetch
        // the NI data. If this changes, this test needs an update.
        Field nameField = NetworkInterface.class.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(lo, "noSuchInterface");

        try {
            lo.getHardwareAddress();
            fail();
        } catch(SocketException expected) {}

        try {
            lo.getMTU();
            fail();
        } catch(SocketException expected) {}

        try {
            lo.isLoopback();
            fail();
        } catch(SocketException expected) {}

        try {
            lo.isUp();
            fail();
        } catch(SocketException expected) {}

        try {
            lo.isPointToPoint();
            fail();
        } catch(SocketException expected) {}

        try {
            lo.supportsMulticast();
            fail();
        } catch(SocketException expected) {}
    }

    public void testGetNetworkInterfaces_matchesIfaddrs() throws Exception {
        StructIfaddrs[] ifaddrs = Libcore.os.getifaddrs();
        Set<String> ifaddrsNames = new HashSet<>();
        Arrays.asList(ifaddrs).forEach(ifa -> ifaddrsNames.add(ifa.ifa_name));

        List<NetworkInterface> nifs = Collections.list(NetworkInterface.getNetworkInterfaces());
        Set<String> actualNiNames = new HashSet<>();
        nifs.forEach(ni -> actualNiNames.add(ni.getName()));

        assertEquals(ifaddrsNames, actualNiNames);
    }

    // Validate that we don't fail to enumerate interfaces if there is virtual interface without parent interface present.
    // b/159277702
    public void testGetNetworkInterfaces_OrphanInterfaceDoesNotThrow() throws Exception {
        Os originalOs = Libcore.getOs();
        Os mockOs = Mockito.mock(Os.class);

        try {    
            Mockito.when(mockOs.getifaddrs()).thenReturn(new StructIfaddrs[] {
                new StructIfaddrs("dummy0:1", 0, null, null, null, null),
            });

            Mockito.when(mockOs.if_nametoindex(anyString())).thenReturn(1);

            assertTrue("Failed to swap OS implementation", Libcore.compareAndSetOs(originalOs, mockOs));

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            assertEquals(1, Collections.list(interfaces).size());
        }
        finally {
            assertTrue("Failed to revert OS implementation", Libcore.compareAndSetOs(mockOs, originalOs));
        }
    }

    // Calling getSubInterfaces on interfaces with no subinterface should not throw NPE.
    // http://b/33844501
    public void testGetSubInterfaces() throws Exception {
        List<NetworkInterface> nifs = Collections.list(NetworkInterface.getNetworkInterfaces());

        for (NetworkInterface nif : nifs) {
            nif.getSubInterfaces();
        }
    }

    // b/71977275
    public void testIsUp() throws Exception {
        for (NetworkInterface nif : Collections.list(getNetworkInterfaces())) {
            int flags = getFlags(nif);
            assertEquals(
                    ((flags & IFF_UP) == IFF_UP) && ((flags & IFF_RUNNING) == IFF_RUNNING),
                    nif.isUp());
        }
    }

    public void testIsLoopback() throws Exception {
        for (NetworkInterface nif : Collections.list(getNetworkInterfaces())) {
            assertEquals((getFlags(nif) & IFF_LOOPBACK) == IFF_LOOPBACK, nif.isLoopback());
        }
    }

    public void testIsPointToPoint() throws Exception {
        for (NetworkInterface nif : Collections.list(getNetworkInterfaces())) {
            assertEquals((getFlags(nif) & IFF_POINTOPOINT) == IFF_POINTOPOINT, nif.isPointToPoint());
        }
    }

    public void testSupportsMulticast() throws Exception {
        for (NetworkInterface nif : Collections.list(getNetworkInterfaces())) {
            assertEquals((getFlags(nif) & IFF_MULTICAST) == IFF_MULTICAST, nif.supportsMulticast());
        }
    }

    // Is ifName a name of a Ethernet device?
    private static Pattern ethernetNamePattern = Pattern.compile("^(eth|wlan)[0-9]+$");
    private static boolean isEthernet(String ifName) throws Exception {
        return ethernetNamePattern.matcher(ifName).matches();
    }

    public void testGetInterfaceAddressesDoesNotThrowNPE() throws Exception {
        try (MulticastSocket mcastSock = new MulticastSocket()) {
            mcastSock.getNetworkInterface().getInterfaceAddresses();
        }
    }

    private int getFlags(NetworkInterface nif) throws Exception {
        FileDescriptor fd = Libcore.rawOs.socket(AF_INET, SOCK_DGRAM, 0);
        try {
            return Libcore.rawOs.ioctlFlags(fd, nif.getName());
        } finally {
            IoUtils.closeQuietly(fd);
        }
    }
}
