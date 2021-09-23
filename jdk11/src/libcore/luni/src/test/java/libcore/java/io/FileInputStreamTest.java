/*
 * Copyright (C) 2010 The Android Open Source Project
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

package libcore.java.io;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStatVfs;

import libcore.io.IoUtils;
import libcore.io.Libcore;
import libcore.junit.junit3.TestCaseWithRules;
import libcore.junit.util.ResourceLeakageDetector;
import libcore.testing.io.TestIoUtils;

import org.junit.Rule;
import org.junit.rules.TestRule;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FileInputStreamTest extends TestCaseWithRules {
    @Rule
    public TestRule guardRule = ResourceLeakageDetector.getRule();

    private static final int TOTAL_SIZE = 1024;
    private static final int SKIP_SIZE = 100;

    private static class DataFeeder extends Thread {
        private FileDescriptor mOutFd;

        public DataFeeder(FileDescriptor fd) {
            mOutFd = fd;
        }

        @Override
        public void run() {
            try {
                FileOutputStream fos = new FileOutputStream(mOutFd);
                try {
                    byte[] buffer = new byte[TOTAL_SIZE];
                    for (int i = 0; i < buffer.length; ++i) {
                        buffer[i] = (byte) i;
                    }
                    fos.write(buffer);
                } finally {
                    TestIoUtils.closeQuietly(fos);
                    IoUtils.close(mOutFd);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void verifyData(FileInputStream is, int start, int count) throws IOException {
        byte buffer[] = new byte[count];
        assertEquals(count, is.read(buffer));
        for (int i = 0; i < count; ++i) {
            assertEquals((byte) (i + start), buffer[i]);
        }
    }

    public void testSkipInPipes() throws Exception {
        FileDescriptor[] pipe = Libcore.os.pipe2(0);
        DataFeeder feeder = new DataFeeder(pipe[1]);
        try {
            feeder.start();
            FileInputStream fis = new FileInputStream(pipe[0]);
            fis.skip(SKIP_SIZE);
            verifyData(fis, SKIP_SIZE, TOTAL_SIZE - SKIP_SIZE);
            assertEquals(-1, fis.read());
            feeder.join(1000);
            assertFalse(feeder.isAlive());
        } finally {
            IoUtils.closeQuietly(pipe[0]);
        }
    }

    public void testDirectories() throws Exception {
        try {
            new FileInputStream(".");
            fail();
        } catch (FileNotFoundException expected) {
        }
    }

    private File makeFile() throws Exception {
        File tmp = File.createTempFile("FileOutputStreamTest", "tmp");
        FileOutputStream fos = new FileOutputStream(tmp);
        fos.write(1);
        fos.write(1);
        fos.close();
        return tmp;
    }

    public void testFileDescriptorOwnership() throws Exception {
        File tmp = makeFile();

        FileInputStream fis1 = new FileInputStream(tmp);
        FileInputStream fis2 = new FileInputStream(fis1.getFD());

        // Close the second FileDescriptor and check we can't use it...
        fis2.close();

        try {
            fis2.available();
            fail();
        } catch (IOException expected) {
        }
        try {
            fis2.read();
            fail();
        } catch (IOException expected) {
        }
        try {
            fis2.read(new byte[1], 0, 1);
            fail();
        } catch (IOException expected) {
        }
        try {
            fis2.skip(1);
            fail();
        } catch (IOException expected) {
        }
        // ...but that we can still use the first.
        assertTrue(fis1.getFD().valid());
        assertFalse(fis1.read() == -1);

        // Close the first FileDescriptor and check we can't use it...
        fis1.close();
        try {
            fis1.available();
            fail();
        } catch (IOException expected) {
        }
        try {
            fis1.read();
            fail();
        } catch (IOException expected) {
        }
        try {
            fis1.read(new byte[1], 0, 1);
            fail();
        } catch (IOException expected) {
        }
        try {
            fis1.skip(1);
            fail();
        } catch (IOException expected) {
        }

        // FD is no longer owned by any stream, should be invalidated.
        assertFalse(fis1.getFD().valid());
    }

    public void testClose() throws Exception {
        File tmp = makeFile();
        FileInputStream fis = new FileInputStream(tmp);

        // Closing an already-closed stream is a no-op...
        fis.close();
        fis.close();

        // But any explicit activity is an error.
        try {
            fis.available();
            fail();
        } catch (IOException expected) {
        }
        try {
            fis.read();
            fail();
        } catch (IOException expected) {
        }
        try {
            fis.read(new byte[1], 0, 1);
            fail();
        } catch (IOException expected) {
        }
        try {
            fis.skip(1);
            fail();
        } catch (IOException expected) {
        }
        // Including 0-byte skips...
        try {
            fis.skip(0);
            fail();
        } catch (IOException expected) {
        }
        // ...but not 0-byte reads...
        fis.read(new byte[0], 0, 0);
    }

    // http://b/26117827
    //
    // Return 0 (the conservative estimate) for files for which ioctl is not implemented.
    public void test_available_on_nonIOCTL_supported_file() throws Exception {
        File file = new File("/dev/zero");
        try (FileInputStream input = new FileInputStream(file)) {
            assertEquals(0, input.available());
        }

        try (FileInputStream input = new FileInputStream(file)) {
            android.system.Os.ioctlInt(input.getFD(), OsConstants.FIONREAD);
            fail();
        } catch (ErrnoException expected) {
            assertEquals("FIONREAD should have returned ENOTTY for the file. If it doesn't return"
                    + " FIONREAD, the test is no longer valid.", OsConstants.ENOTTY,
                    expected.errno);
        }
    }

    // http://b/25695227
    public void testFdLeakWhenOpeningDirectory() throws Exception {
        File phile = TestIoUtils.createTemporaryDirectory("test_bug_25695227");

        try {
            new FileInputStream(phile);
            fail();
        } catch (FileNotFoundException expected) {
        }

        assertTrue(getOpenFdsForPrefix("test_bug_25695227").isEmpty());
    }

    // http://b/28192631
    public void testSkipOnLargeFiles() throws Exception {
        File largeFile = File.createTempFile("FileInputStreamTest_testSkipOnLargeFiles", "");
        // Required space is 3.1 GB: 3GB for file plus 100M headroom.
        final long requiredFreeSpaceBytes = 3172L * 1024 * 1024;
        long fileSize = 3 * 1024L * 1024 * 1024; // 3 GiB
        // If system doesn't have enough space free for this test, skip it.
        final StructStatVfs statVfs = Os.statvfs(largeFile.getPath());
        final long freeSpaceAvailableBytes = statVfs.f_bsize * statVfs.f_bavail;
        if (freeSpaceAvailableBytes < requiredFreeSpaceBytes) {
            return;
        }
        try {
            allocateEmptyFile(largeFile, fileSize);
            assertEquals(fileSize, largeFile.length());
            try (FileInputStream fis = new FileInputStream(largeFile)) {
                long lastByte = fileSize - 1;
                assertEquals(0, Libcore.os.lseek(fis.getFD(), 0, OsConstants.SEEK_CUR));
                assertEquals(lastByte, fis.skip(lastByte));
            }
        } finally {
            // Proactively cleanup - it's a pretty large file.
            assertTrue(largeFile.delete());
        }
    }

    /**
     * Allocates a file to the specified size using fallocate, falling back to ftruncate.
     */
    private static void allocateEmptyFile(File file, long fileSize)
            throws IOException, InterruptedException {
        // fallocate is much faster than ftruncate (<<1sec rather than 24sec for 3 GiB on Nexus 6P)
        try (FileOutputStream fos = new FileOutputStream(file)) {
            try {
                Os.posix_fallocate(fos.getFD(), 0, fileSize);
                return;
            } catch (ErrnoException e) {
                // Fall back to ftruncate, which works on all filesystems but is slower
            }
        }
        // Need to reopen the file to get a valid FileDescriptor
        try (FileOutputStream fos = new FileOutputStream(file)) {
            Os.ftruncate(fos.getFD(), fileSize);
        } catch (ErrnoException e2) {
            throw new IOException("Failed to truncate: " + file, e2);
        }
    }

    private static List<Integer> getOpenFdsForPrefix(String path) throws Exception {
        File[] fds = new File("/proc/self/fd").listFiles();
        List<Integer> list = new ArrayList<>();
        for (File fd : fds) {
            try {
                File fdPath = new File(android.system.Os.readlink(fd.getAbsolutePath()));
                if (fdPath.getName().startsWith(path)) {
                    list.add(Integer.valueOf(fd.getName()));
                }
            } catch (ErrnoException e) {
                if (e.errno != OsConstants.ENOENT) {
                    throw e.rethrowAsIOException();
                }
            }
        }

        return list;
    }
}
