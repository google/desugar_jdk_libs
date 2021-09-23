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

package libcore.java.nio.file;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.ClosedDirectoryStreamException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SecureDirectoryStream;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import libcore.junit.util.ResourceLeakageDetector;
import libcore.junit.util.ResourceLeakageDetector.LeakageDetectorRule;

import static libcore.java.nio.file.FilesSetup.TEST_FILE_DATA;
import static libcore.java.nio.file.FilesSetup.writeToFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DefaultSecureDirectoryStreamTest {

    Path path_root;
    Path path_dir1;
    Path path_dir2;
    Path path_dir3;
    Path path_f1;
    Path path_f2;
    Path path_f3;
    Path path_dir4;

    @Rule
    public FilesSetup filesSetup = new FilesSetup();
    @Rule
    public LeakageDetectorRule resourceLeakageDetectorRule = ResourceLeakageDetector.getRule();

    @Before
    public void setup() throws Exception {

        // Initial setup of directory.
        path_root = filesSetup.getPathInTestDir("dir");
        path_dir1 = filesSetup.getPathInTestDir("dir/dir1");
        path_dir2 = filesSetup.getPathInTestDir("dir/dir2");
        path_dir3 = filesSetup.getPathInTestDir("dir/dir3");
        path_dir4 = filesSetup.getPathInTestDir("dir/dir1/dir4");

        path_f1 = filesSetup.getPathInTestDir("dir/f1");
        path_f2 = filesSetup.getPathInTestDir("dir/f2");
        path_f3 = filesSetup.getPathInTestDir("dir/f3");

        Files.createDirectory(path_root);
        Files.createDirectory(path_dir1);
        Files.createDirectory(path_dir2);
        Files.createDirectory(path_dir3);
        Files.createDirectory(path_dir4);
        Files.createFile(path_f1);
        Files.createFile(path_f2);
        Files.createFile(path_f3);
    }

    @Test
    public void testIterator() throws IOException {
        HashSet<Path> pathsSet = new HashSet<>();
        HashSet<Path> expectedPathsSet = new HashSet<>();

        expectedPathsSet.add(path_dir1);
        expectedPathsSet.add(path_dir2);
        expectedPathsSet.add(path_dir3);

        // Filter all the directories.
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path_root,
                file -> Files.isDirectory(file))) {
            Iterator<Path> directoryStreamIterator = directoryStream.iterator();
            directoryStreamIterator.forEachRemaining(path -> pathsSet.add(path));
            assertEquals(expectedPathsSet, pathsSet);
        }
    }

    @Test
    public void testIterator_calledTwice() throws IOException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path_root,
                file -> Files.isDirectory(file))) {
            directoryStream.iterator();
            try {
                directoryStream.iterator();
                fail();
            } catch (IllegalStateException expected) {}
        }
    }

    @Test
    public void testIterator_afterClose() throws IOException {
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path_root,
                file -> Files.isDirectory(file));
        directoryStream.close();
        try {
            directoryStream.iterator();
            fail();
        } catch (IllegalStateException expected) {}
    }

    @Test
    public void test_newDirectoryStream() throws IOException {
        HashSet<Path> pathsSet = new HashSet<>();
        HashSet<Path> expectedPathsSet = new HashSet<>();

        expectedPathsSet.add(path_dir4);

        try (SecureDirectoryStream<Path> ds_path_root = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root);
             DirectoryStream<Path> ds_path_dir1 =  ds_path_root.newDirectoryStream(path_root.
                     relativize(path_dir1))) {

            ds_path_dir1.forEach(path -> pathsSet.add(path));
            assertEquals(expectedPathsSet, pathsSet);
        }
    }

    @Test
    public void test_newDirectoryStream_symbolicLink() throws IOException {
        Path symlinkPath = Paths.get(path_dir1.toString(), "symlink");
        Files.createSymbolicLink(symlinkPath, path_dir3);
        assertTrue(Files.isSymbolicLink(symlinkPath));

        try (SecureDirectoryStream<Path> ds_path_dir1 = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root)) {
            try (DirectoryStream<Path> ds_path_dir2 =  ds_path_dir1.newDirectoryStream(path_root.
                    relativize(symlinkPath), LinkOption.NOFOLLOW_LINKS)) {
                fail();
            } catch (FileSystemException expected) {}
        }
    }

    @Test
    public void test_newDirectoryStream_Exception() throws IOException {

        try (SecureDirectoryStream<Path> ds_path_root = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root)) {
            // When file is not a directory.
            try (DirectoryStream<Path> ds_path_dir1 =  ds_path_root.newDirectoryStream(path_root.
                    relativize(path_f1))) {
                fail();
            } catch (NotDirectoryException expected) {}


            // NPE
            try (DirectoryStream<Path> ds_path_dir1 =  ds_path_root.newDirectoryStream(null)) {
                fail();
            } catch (NullPointerException expected) {}

            // NPE
            try (DirectoryStream<Path> ds_path_dir1 =  ds_path_root.newDirectoryStream(path_root.
                    relativize(path_f1), (LinkOption) null)) {
                fail();
            } catch (NullPointerException expected) {}

            // When stream is closed.
            ds_path_root.close();
            try (DirectoryStream<Path> ds_path_dir1 =  ds_path_root.newDirectoryStream(path_root.
                    relativize(path_dir1))) {
                fail();
            } catch (ClosedDirectoryStreamException expected) {}
        }
    }

    @Test
    public void test_newByteChannel() throws IOException {
        Set<OpenOption> set = new HashSet<OpenOption>();

        // When file doesn't exist.
        try (SecureDirectoryStream<Path> ds_path_root = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root)) {

            try (SeekableByteChannel sbc = ds_path_root.newByteChannel(filesSetup.getTestPath(),
                    set)) {
                fail();
            } catch (NoSuchFileException expected) {
                assertTrue(expected.getMessage().contains(filesSetup.getTestPath().toString()));
            }

            // When file exists.
            // File opens in READ mode by default. The channel is non writable by default.
            try (SeekableByteChannel sbc = ds_path_root.newByteChannel(
                    path_root.relativize(path_f1), set)) {
                sbc.write(ByteBuffer.allocate(10));
                fail();
            } catch (NonWritableChannelException expected) {
            }

            // Read a file.
            writeToFile(path_f1, TEST_FILE_DATA);
            try (SeekableByteChannel sbc = ds_path_root.newByteChannel(
                    path_root.relativize(path_f1), set)) {
                ByteBuffer readBuffer = ByteBuffer.allocate(10);
                int bytesReadCount = sbc.read(readBuffer);

                String readData = new String(Arrays.copyOf(readBuffer.array(), bytesReadCount),
                        "UTF-8");
                assertEquals(TEST_FILE_DATA, readData);
            }

            // when directory stream is closed.
            ds_path_root.close();
            try (SeekableByteChannel sbc = ds_path_root.newByteChannel(
                    path_root.relativize(path_f1), set)) {
                fail();
            } catch (ClosedDirectoryStreamException expected) {}
        }
    }

    @Test
    public void test_newByteChannel_NPE() throws IOException {
        try (SecureDirectoryStream<Path> ds_path_root = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root)) {
            Set<OpenOption> set = new HashSet<OpenOption>();
            try (SeekableByteChannel sbc = ds_path_root.newByteChannel(null, set)) {
                fail();
            } catch (NullPointerException expected) {
            }

            try (SeekableByteChannel sbc = ds_path_root.newByteChannel(filesSetup.getDataFilePath(),
                    null)) {
                fail();
            } catch (NullPointerException expected) {
            }
        }
    }

    @Test
    public void test_deleteFile() throws IOException {
        try (SecureDirectoryStream<Path> ds_path_root = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root)) {
            ds_path_root.deleteFile(path_root.relativize(path_f1));
            assertFalse(Files.exists(path_f1));

            // --- Exceptions ---
            // When the file is a directory.
            try {
                ds_path_root.deleteFile(path_root.relativize(path_dir1));
                fail();
            } catch (FileSystemException expected) {}

            // When file doesn't exists.
            try {
                ds_path_root.deleteFile(filesSetup.getTestPath());
                fail();
            } catch (NoSuchFileException expected) {}

            // NullPointerException
            try {
                ds_path_root.deleteFile(null);
                fail();
            } catch (NullPointerException expected) {}

            // When the directory stream is closed.
            ds_path_root.close();
            try {
                ds_path_root.deleteFile(path_root.relativize(path_f2));
                fail();
            } catch (ClosedDirectoryStreamException expected) {}

        }
    }

    @Test
    public void test_deleteDirectory() throws IOException {
        try (SecureDirectoryStream<Path> ds_path_root = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root)) {
            ds_path_root.deleteDirectory(path_root.relativize(path_dir2));
            assertFalse(Files.exists(path_dir2));

            // When file is not a directory.
            try {
                ds_path_root.deleteDirectory(path_root.relativize(path_f1));
                fail();
            } catch (FileSystemException expected) {}

            // When path doesn't exists.
            try {
                ds_path_root.deleteDirectory(filesSetup.getTestPath());
                fail();
            } catch (NoSuchFileException expected) {}

            // When the directory is not empty.
            try {
                ds_path_root.deleteDirectory(path_root.relativize(path_dir1));
                fail();
            } catch (DirectoryNotEmptyException expected) {}

            // --- Exceptions ---
            // NullPointerException
            try {
                ds_path_root.deleteDirectory(null);
                fail();
            } catch (NullPointerException expected) {}

            // When the directory stream is closed.
            ds_path_root.close();
            try {
                ds_path_root.deleteDirectory(path_root.relativize(path_f2));
                fail();
            } catch (ClosedDirectoryStreamException expected) {}
        }
    }

    @Test
    public void test_move() throws IOException {
        SecureDirectoryStream<Path> ds_path_dir1 = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_dir1);

        // moving a file.
        ds_path_dir1.move(path_f1, ds_path_dir1, Paths.get("f1"));
        assertTrue(Files.exists(Paths.get(path_dir1.toString(), "f1")));
        assertFalse(Files.exists(path_f1));

        // moving a directory.
        ds_path_dir1.move(path_dir2, ds_path_dir1, Paths.get(path_dir4.toString(), "path_dir2"));
        assertTrue(Files.exists(Paths.get(path_dir4.toString(), "path_dir2")));
        assertFalse(Files.exists(path_dir2));

        // when directory already exists of the same name.
        ds_path_dir1.move(path_dir3, ds_path_dir1, Paths.get("path_dir2"));
        assertTrue(Files.exists(Paths.get(path_dir1.toString(), "path_dir2")));
        assertFalse(Files.exists(path_dir3));

        // moving a non empty directory.
        ds_path_dir1.move(path_dir1, ds_path_dir1, Paths.get(path_root.getParent().toString(),
                "path_dir1"));
        assertTrue(Files.exists(Paths.get(path_root.getParent().toString(), "path_dir1")));
        assertFalse(Files.exists(path_dir1));

        // --- Exceptions ---
        // NullPointerException.
        try {
            ds_path_dir1.move(null, ds_path_dir1,
                    Paths.get(path_root.getParent().toString(), "path_dir1"));
            fail();
        } catch (NullPointerException expected) {}

        try {
            ds_path_dir1.move(path_dir1, null,
                    Paths.get(path_root.getParent().toString(), "path_dir1"));
            fail();
        } catch (NullPointerException expected) {}

        try {
            ds_path_dir1.move(path_dir1, ds_path_dir1,
                    Paths.get(path_root.getParent().toString(), (String) null));
            fail();
        } catch (NullPointerException expected) {}

        try {
            // when targetDir stream is closed.
            ds_path_dir1.close();
            ds_path_dir1.move(path_root, ds_path_dir1, path_f3);
            fail();
        } catch (ClosedDirectoryStreamException expected) {}
    }

    @Test
    public void test_getFileAttributeView() throws IOException {
        try (SecureDirectoryStream<Path> ds_path_root = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root)) {
            BasicFileAttributeView fileAttributeView = ds_path_root
                    .getFileAttributeView(BasicFileAttributeView.class);

            assertFalse(fileAttributeView.readAttributes().isRegularFile());
            assertTrue(fileAttributeView.readAttributes().isDirectory());
            assertFalse(fileAttributeView.readAttributes().isSymbolicLink());

            // --- Exceptions ---
            // NullPointerException
            try {
                ds_path_root.getFileAttributeView(null);
            } catch (NullPointerException expected) {}

            // When directory stream is closed.
            ds_path_root.close();
            fileAttributeView = ds_path_root.getFileAttributeView(BasicFileAttributeView.class);
            try {
                fileAttributeView.readAttributes();
                fail();
            } catch (ClosedDirectoryStreamException expected) {}
        }
    }

    @Test
    public void test_getFileAttributeView_Path() throws IOException {
        try (SecureDirectoryStream<Path> ds_path_root = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root)) {
            BasicFileAttributeView fileAttributeView = ds_path_root.getFileAttributeView(
                    path_root.relativize(path_dir1), BasicFileAttributeView.class);

            assertFalse(fileAttributeView.readAttributes().isRegularFile());
            assertTrue(fileAttributeView.readAttributes().isDirectory());
            assertFalse(fileAttributeView.readAttributes().isSymbolicLink());

            fileAttributeView = ds_path_root.getFileAttributeView(path_root.relativize(path_f1),
                            BasicFileAttributeView.class);

            assertTrue(fileAttributeView.readAttributes().isRegularFile());
            assertFalse(fileAttributeView.readAttributes().isDirectory());
            assertFalse(fileAttributeView.readAttributes().isSymbolicLink());

            // When file is a symbolic link.
            Path symlinkPath = Paths.get(path_root.toString(), "symlink");
            Files.createSymbolicLink(symlinkPath, path_dir1);
            assertTrue(Files.isSymbolicLink(symlinkPath));
            // When file is a symbolic link and method is invoked with LinkOptions.NOFOLLOW_LINKS.
            fileAttributeView = ds_path_root.getFileAttributeView(path_root.relativize(symlinkPath),
                    BasicFileAttributeView.class);
            assertTrue(fileAttributeView.readAttributes().isDirectory());

            // --- Exceptions ---
            try {
                ds_path_root.getFileAttributeView(null, BasicFileAttributeView.class);
                fail();
            } catch (NullPointerException expected) {}

            try {
                ds_path_root.getFileAttributeView(path_root.relativize(path_f1), null);
                fail();
            } catch (NullPointerException expected) {}

            // When directory stream is closed.
            ds_path_root.close();
            fileAttributeView = ds_path_root.getFileAttributeView(path_root.relativize(path_f1),
                    BasicFileAttributeView.class);
            try {
                fileAttributeView.readAttributes();
                fail();
            } catch (ClosedDirectoryStreamException expected) {}
        }
    }

    @Test
    public void test_getFileAttributeView_Path_LinkOptions() throws IOException {
        Path symlinkPath = Paths.get(path_root.toString(), "symlink");
        Files.createSymbolicLink(symlinkPath, path_dir1);
        assertTrue(Files.isSymbolicLink(symlinkPath));
        // When file is a symbolic link and method is invoked with LinkOptions.NOFOLLOW_LINKS.
        try (SecureDirectoryStream<Path> ds_path_root = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root)) {
            BasicFileAttributeView fileAttributeView = ds_path_root.getFileAttributeView(
                    path_root.relativize(symlinkPath), BasicFileAttributeView.class,
                    LinkOption.NOFOLLOW_LINKS);
            assertTrue(fileAttributeView.readAttributes().isSymbolicLink());
        }

        // When file is not a symbolic link.
        try (SecureDirectoryStream<Path> ds_path_root = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root)) {
            BasicFileAttributeView fileAttributeView = ds_path_root
                    .getFileAttributeView(path_root.relativize(path_f1),
                            BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
            assertTrue(fileAttributeView.readAttributes().isRegularFile());
            assertFalse(fileAttributeView.readAttributes().isDirectory());
            assertFalse(fileAttributeView.readAttributes().isSymbolicLink());
        }

        // --- Exceptions ---
        // NullPointerException
        try (SecureDirectoryStream<Path> ds_path_root = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root)) {
            ds_path_root.getFileAttributeView(path_root.relativize(path_f1),
                    BasicFileAttributeView.class, (LinkOption) null);
            fail();
        } catch (NullPointerException expected) {}
    }

    @Test
    public void testUnixSecureDirectoryStreamHasFinalizer() throws IOException {
        try (SecureDirectoryStream<Path> ds_path_root = (SecureDirectoryStream<Path>)
                Files.newDirectoryStream(path_root)) {
            resourceLeakageDetectorRule.assertUnreleasedResourceCount(ds_path_root, 1);
        }
    }
}
