/*
 * Copyright (C) 2018 The Android Open Source Project
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

package libcore.libcore.io;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import junit.framework.TestCase;

import libcore.io.Libcore;

public class FdsanTest extends TestCase {
    public void testFileInputStream() throws Exception {
        try (FileInputStream fis = new FileInputStream("/dev/null")) {
            FileDescriptor fd = fis.getFD();
            long tag = Libcore.os.android_fdsan_get_owner_tag(fd);
            assertTrue(tag != FileDescriptor.NO_OWNER);
            assertEquals("FileInputStream", Libcore.os.android_fdsan_get_tag_type(tag));
            assertEquals(System.identityHashCode(fis), Libcore.os.android_fdsan_get_tag_value(tag));
        }
    }

    public void testFileOutputStream() throws Exception {
        try (FileOutputStream fis = new FileOutputStream("/dev/null")) {
            FileDescriptor fd = fis.getFD();
            long tag = Libcore.os.android_fdsan_get_owner_tag(fd);
            assertTrue(tag != FileDescriptor.NO_OWNER);
            assertEquals("FileOutputStream", Libcore.os.android_fdsan_get_tag_type(tag));
            assertEquals(System.identityHashCode(fis), Libcore.os.android_fdsan_get_tag_value(tag));
        }
    }

    public void testRandomAccessFile() throws Exception {
        try (RandomAccessFile fis = new RandomAccessFile("/dev/null", "r")) {
            FileDescriptor fd = fis.getFD();
            long tag = Libcore.os.android_fdsan_get_owner_tag(fd);
            assertTrue(tag != FileDescriptor.NO_OWNER);
            assertEquals("RandomAccessFile", Libcore.os.android_fdsan_get_tag_type(tag));
            assertEquals(System.identityHashCode(fis), Libcore.os.android_fdsan_get_tag_value(tag));
        }
    }

    public void testParcelFileDescriptor() throws Exception {
        Class pfdClass;
        try {
            pfdClass = Class.forName("android.os.ParcelFileDescriptor");
        } catch (ClassNotFoundException ex) {
            // Don't fail if ParcelFileDescriptor isn't on our classpath, e.g. in ART host tests.
            return;
        }

        try (FileInputStream fis = new FileInputStream("/dev/null")) {
            Method pfdMethodDup = pfdClass.getMethod("dup", FileDescriptor.class);
            Method pfdMethodClose = pfdClass.getMethod("close");
            Method pfdMethodGetFileDescriptor = pfdClass.getMethod("getFileDescriptor");
            Field readonly = pfdClass.getField("MODE_READ_ONLY");

            Object pfd = pfdMethodDup.invoke(null, fis.getFD());
            FileDescriptor fd = (FileDescriptor)pfdMethodGetFileDescriptor.invoke(pfd);
            long tag = Libcore.os.android_fdsan_get_owner_tag(fd);
            assertTrue(tag != FileDescriptor.NO_OWNER);
            assertEquals("ParcelFileDescriptor", Libcore.os.android_fdsan_get_tag_type(tag));
            assertEquals(System.identityHashCode(pfd), Libcore.os.android_fdsan_get_tag_value(tag));
            pfdMethodClose.invoke(pfd);
        }
    }

    public void testDatagramSocket() throws Exception {
        try (DatagramSocket socket = new DatagramSocket()) {
            FileDescriptor fd = socket.getFileDescriptor$();
            assertTrue(fd.valid());

            long tag = Libcore.os.android_fdsan_get_owner_tag(fd);
            assertTrue(tag != FileDescriptor.NO_OWNER);
            assertEquals("DatagramSocketImpl", Libcore.os.android_fdsan_get_tag_type(tag));
            assertTrue(Libcore.os.android_fdsan_get_tag_value(tag) != 0);
            socket.close();
        }
    }

    public void assertFdOwnedBySocket(FileDescriptor fd) throws Exception {
        long tag = Libcore.os.android_fdsan_get_owner_tag(fd);
        assertTrue(tag != FileDescriptor.NO_OWNER);
        assertEquals("SocketImpl", Libcore.os.android_fdsan_get_tag_type(tag));
        assertTrue(Libcore.os.android_fdsan_get_tag_value(tag) != 0);
    }

    public void testSocket() throws Exception {
        try (Socket socket = new Socket()) {
            assertFalse("new Socket shouldn't have an associated FileDescriptor",
                        socket.getFileDescriptor$().valid());
        }

        int port = 0; // auto-allocate port
        try (ServerSocket serverSocket = new ServerSocket(port, /* backlog */ 1)) {
            assertFdOwnedBySocket(serverSocket.getFileDescriptor$());

            Socket client = new Socket(serverSocket.getInetAddress(), serverSocket.getLocalPort());
            Socket server = serverSocket.accept();
            assertFdOwnedBySocket(client.getFileDescriptor$());
            assertFdOwnedBySocket(server.getFileDescriptor$());
            client.close();
            server.close();
        }
    }
}
