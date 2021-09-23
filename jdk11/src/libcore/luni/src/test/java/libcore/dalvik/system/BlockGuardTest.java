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

package libcore.dalvik.system;

import android.system.Os;
import android.system.OsConstants;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import dalvik.system.BlockGuard;

public class BlockGuardTest extends TestCase {

    private BlockGuard.Policy oldPolicy;
    private RecordingPolicy recorder = new RecordingPolicy();

    @Override
    public void setUp() {
        recorder.setChecks(EnumSet.allOf(RecordingPolicy.Check.class));
        oldPolicy = BlockGuard.getThreadPolicy();
        BlockGuard.setThreadPolicy(recorder);
    }

    @Override
    public void tearDown() {
        BlockGuard.setThreadPolicy(oldPolicy);
        recorder.clear();
    }

    public void testFile() throws Exception {
        File f = File.createTempFile("foo", "bar");
        recorder.expectAndClear("onReadFromDisk", "onWriteToDisk");

        f.getAbsolutePath();
        f.getParentFile();
        f.getName();
        f.getParent();
        f.getPath();
        f.isAbsolute();
        recorder.expectNoViolations();

        f.mkdir();
        recorder.expectAndClear("onWriteToDisk");

        f.listFiles();
        recorder.expectAndClear("onReadFromDisk");

        f.list();
        recorder.expectAndClear("onReadFromDisk");

        f.length();
        recorder.expectAndClear("onReadFromDisk");

        f.lastModified();
        recorder.expectAndClear("onReadFromDisk");

        f.canExecute();
        recorder.expectAndClear("onReadFromDisk");

        f.canRead();
        recorder.expectAndClear("onReadFromDisk");

        f.canWrite();
        recorder.expectAndClear("onReadFromDisk");

        f.isFile();
        recorder.expectAndClear("onReadFromDisk");

        f.isDirectory();
        recorder.expectAndClear("onReadFromDisk");

        f.setExecutable(true, false);
        recorder.expectAndClear("onWriteToDisk");

        f.setReadable(true, false);
        recorder.expectAndClear("onWriteToDisk");

        f.setWritable(true, false);
        recorder.expectAndClear("onWriteToDisk");

        f.delete();
        recorder.expectAndClear("onWriteToDisk");
    }

    public void testFileInputStream() throws Exception {
        // The file itself doesn't matter: it just has to exist and allow the creation of the
        // FileInputStream. The BlockGuard should have the same behavior towards a normal file and
        // system file.
        File tmpFile = File.createTempFile("inputFile", ".txt");
        try (FileOutputStream fos = new FileOutputStream(tmpFile)) {
            fos.write("01234567890".getBytes());
        }

        try {
            recorder.clear();

            // Opening a file for read triggers:
            // 1. Read violation from open()
            // 2. Read violation from EISDIR check
            FileInputStream fis = new FileInputStream(tmpFile);
            recorder.expectAndClear("onReadFromDisk", "onReadFromDisk");

            fis.read(new byte[4], 0, 4);
            recorder.expectAndClear("onReadFromDisk");

            fis.read();
            recorder.expectAndClear("onReadFromDisk");

            fis.skip(1);
            recorder.expectAndClear("onReadFromDisk");

            fis.close();
        } finally {
            tmpFile.delete();
        }
    }

    public void testFileOutputStream() throws Exception {
        File f = File.createTempFile("foo", "bar");
        recorder.clear();

        // Opening a file for write triggers:
        // 1. Read violation from open()
        // 2. Write violation from open()
        // 3. Read violation from EISDIR check
        FileOutputStream fos = new FileOutputStream(f);
        recorder.expectAndClear("onReadFromDisk", "onWriteToDisk", "onReadFromDisk");

        fos.write(new byte[3]);
        recorder.expectAndClear("onWriteToDisk");

        fos.write(4);
        recorder.expectAndClear("onWriteToDisk");

        fos.flush();
        recorder.expectNoViolations();

        fos.close();
        recorder.expectNoViolations();
    }

    public void testRandomAccessFile() throws Exception {
        File f = File.createTempFile("foo", "bar");
        recorder.clear();

        // Opening a file for write triggers:
        // 1. Read violation from open()
        // 2. Write violation from open()
        // 3. Read violation from EISDIR check
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        recorder.expectAndClear("onReadFromDisk", "onWriteToDisk", "onReadFromDisk");

        raf.seek(0);
        recorder.expectAndClear("onReadFromDisk");

        raf.length();
        recorder.expectAndClear("onReadFromDisk");

        raf.read();
        recorder.expectAndClear("onReadFromDisk");

        raf.write(42);
        recorder.expectAndClear("onWriteToDisk");

        raf.close();
    }

    public void testUnbufferedIO() throws Exception {
        File f = File.createTempFile("foo", "bar");
        recorder.setChecks(EnumSet.of(RecordingPolicy.Check.UNBUFFERED_IO));
        recorder.clear();

        try (FileOutputStream fos = new FileOutputStream(f)) {
            recorder.expectNoViolations();
            for (int i = 0; i < 11; i++) {
                recorder.expectNoViolations();
                fos.write("a".getBytes());
            }
            recorder.expectAndClear("onUnbufferedIO");
        }

        try (FileInputStream fis = new FileInputStream(new File("/dev/null"))) {
            recorder.expectNoViolations();
            byte[] b = new byte[1];
            for (int i = 0; i < 11; i++) {
                recorder.expectNoViolations();
                fis.read(b);
            }
            recorder.expectAndClear("onUnbufferedIO");
        }

        try (RandomAccessFile ras = new RandomAccessFile(f, "rw")) {
            // seek should reset the IoTracker.
            ras.seek(0);
            recorder.expectNoViolations();
            for (int i = 0; i < 11; i++) {
                recorder.expectNoViolations();
                ras.read("a".getBytes());
            }
            recorder.expectAndClear("onUnbufferedIO");
        }

        try (RandomAccessFile ras = new RandomAccessFile(f, "rw")) {
            // No violation is expected as a write is called while reading which should reset the
            // IoTracker counter.
            for (int i = 0; i < 11; i++) {
                recorder.expectNoViolations();
                if (i == 5) {
                    ras.write("a".getBytes());
                }
                ras.read("a".getBytes());
            }
            recorder.expectNoViolations();
        }

        try (RandomAccessFile ras = new RandomAccessFile(f, "rw")) {
            // No violation is expected as a seek is called while reading which should reset the
            // IoTracker counter.
            for (int i = 0; i < 11; i++) {
                recorder.expectNoViolations();
                if (i == 5) {
                    ras.seek(0);
                }
                ras.read("a".getBytes());
            }
            recorder.expectNoViolations();
        }

        try (RandomAccessFile ras = new RandomAccessFile(f, "rw")) {
            // seek should reset the IoTracker.
            for (int i = 0; i < 11; i++) {
                recorder.expectNoViolations();
                ras.write("a".getBytes());
            }
            recorder.expectAndClear("onUnbufferedIO");
        }

        try (RandomAccessFile ras = new RandomAccessFile(f, "rw")) {
            // No violation is expected as a read is called while writing which should reset the
            // IoTracker counter.
            for (int i = 0; i < 11; i++) {
                recorder.expectNoViolations();
                if (i == 5) {
                    ras.read("a".getBytes());
                }
                ras.write("a".getBytes());
            }
            recorder.expectNoViolations();
        }

        try (RandomAccessFile ras = new RandomAccessFile(f, "rw")) {
            for (int i = 0; i < 11; i++) {
                recorder.expectNoViolations();
                if (i == 5) {
                    ras.seek(0);
                }
                ras.write("a".getBytes());
            }
            recorder.expectNoViolations();
        }
    }

    public void testOpen() throws Exception {
        File temp = File.createTempFile("foo", "bar");
        recorder.clear();

        // Open in read/write mode : should be recorded as a read and a write to disk.
        FileDescriptor fd = Os.open(temp.getPath(), OsConstants.O_RDWR, 0);
        recorder.expectAndClear("onReadFromDisk", "onWriteToDisk");
        Os.close(fd);

        // Open in read only mode : should be recorded as a read from disk.
        recorder.clear();
        fd = Os.open(temp.getPath(), OsConstants.O_RDONLY, 0);
        recorder.expectAndClear("onReadFromDisk");
        Os.close(fd);
    }

    public void testSystemGc() throws Exception {
        recorder.clear();
        Runtime.getRuntime().gc();
        recorder.expectAndClear("onExplicitGc");
    }

    private static class RecordingPolicy implements BlockGuard.Policy {
        private final List<String> violations = new ArrayList<>();
        private Set<Check> checksList;

        public enum Check {
            WRITE_TO_DISK,
            READ_FROM_DISK,
            NETWORK,
            UNBUFFERED_IO,
            EXPLICIT_GC,
        }

        public void setChecks(EnumSet<Check> checksList) {
            this.checksList = checksList;
        }

        @Override
        public void onWriteToDisk() {
            if (checksList != null && checksList.contains(Check.WRITE_TO_DISK)) {
                addViolation("onWriteToDisk");
            }
        }

        @Override
        public void onReadFromDisk() {
            if (checksList != null && checksList.contains(Check.READ_FROM_DISK)) {
                addViolation("onReadFromDisk");
            }
        }

        @Override
        public void onNetwork() {
            if (checksList != null && checksList.contains(Check.NETWORK)) {
                addViolation("onNetwork");
            }
        }

        @Override
        public void onUnbufferedIO() {
            if (checksList != null && checksList.contains(Check.UNBUFFERED_IO)) {
                addViolation("onUnbufferedIO");
            }
        }

        @Override
        public void onExplicitGc() {
            if (checksList != null && checksList.contains(Check.EXPLICIT_GC)) {
                addViolation("onExplicitGc");
            }
        }

        private void addViolation(String type) {
            StackTraceElement[] threadTrace = Thread.currentThread().getStackTrace();

            final StackTraceElement violator = threadTrace[4];
            violations.add(type + " [caller= " + violator.getMethodName() + "]");
        }

        public void clear() {
            violations.clear();
        }

        public void expectNoViolations() {
            if (violations.size() != 0) {
                throw new AssertionError("Expected 0 violations but found " + violations.size());
            }
        }

        public void expectAndClear(String... expected) {
            if (expected.length != violations.size()) {
                throw new AssertionError("Expected " + expected.length + " violations but found "
                        + violations.size());
            }

            for (int i = 0; i < expected.length; ++i) {
                if (!violations.get(i).startsWith(expected[i])) {
                    throw new AssertionError("Expected: " + expected[i] + " but was "
                            + violations.get(i));
                }
            }

            clear();
        }

        @Override
        public int getPolicyMask() {
            return 0;
        }
    }
}
