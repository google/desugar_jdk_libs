/*
 * Copyright (C) 2017 The Android Open Source Project
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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Rule;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

@RunWith(JUnit4.class)
public class WatchServiceTest {
    private static final WatchEvent.Kind<?>[] ALL_EVENTS_KINDS =
        {ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY};

    @Rule
    public final FilesSetup filesSetup = new FilesSetup();

    static class WatchEventResult {
        final WatchEvent.Kind<Path> expectedKind;
        final int expectedCount;
        final boolean testCount;

        public WatchEventResult(WatchEvent.Kind<Path> expectedKind) {
            this.expectedKind = expectedKind;
            this.expectedCount = 0;
            this.testCount = false;
        }

        public WatchEventResult(WatchEvent.Kind<Path> expectedKind,
                                int expectedCount) {
            this.expectedKind = expectedKind;
            this.expectedCount = expectedCount;
            this.testCount = true;
        }
    }

    private static void checkWatchServiceEventMultipleKeys(WatchService watchService,
            Map<WatchKey, List<WatchEventResult>> expectedResults,
            boolean expectedResetResult) throws InterruptedException {

        // Make a deep copy
        HashMap<WatchKey, ArrayList<WatchEventResult>> expectedResultsCopy
                = new HashMap<>();
        for (Map.Entry<WatchKey, List<WatchEventResult>> entry : expectedResults.entrySet()) {
            expectedResultsCopy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }

        while (!expectedResultsCopy.isEmpty()) {
            WatchKey watchKey = watchService.poll(2, TimeUnit.SECONDS);
            assertNotNull(watchKey);

            List<WatchEventResult> expectedEvents = expectedResultsCopy.get(watchKey);
            assertNotNull(expectedEvents);

            Iterator<WatchEventResult> expectedEventsIterator = expectedEvents.iterator();
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                WatchEventResult expectedEventResult = expectedEventsIterator.next();
                assertNotNull(expectedEventResult);

                assertEquals(expectedEventResult.expectedKind, event.kind());
                if (expectedEventResult.testCount) {
                    assertEquals(expectedEventResult.expectedCount, event.count());
                }
                expectedEventsIterator.remove();
            }
            assertEquals(expectedResetResult, watchKey.reset());
            if (!expectedEventsIterator.hasNext()) {
                expectedResultsCopy.remove(watchKey);
            }
        }
    }

    private static void checkWatchServiceEvent(WatchService watchService,
            WatchKey expectedWatchKey,
            List<WatchEventResult> expectedEvents,
            boolean expectedResetResult) throws InterruptedException {
        Map<WatchKey, List<WatchEventResult>> expected = new HashMap<>();
        expected.put(expectedWatchKey, expectedEvents);
        checkWatchServiceEventMultipleKeys(watchService, expected, expectedResetResult);
    }

    @Test
    public void test_singleFile() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path file = Paths.get(filesSetup.getTestDir(), "directory/file");
        Path directory = Paths.get(filesSetup.getTestDir(), "directory");
        assertFalse(Files.exists(file));
        Files.createDirectories(directory);
        WatchKey directoryKey1 = directory.register(watchService, ALL_EVENTS_KINDS);

        // emit EVENT_CREATE
        Files.createFile(file);
        checkWatchServiceEvent(watchService, directoryKey1,
            Arrays.asList(new WatchEventResult(ENTRY_CREATE, 1)), true);
        assertNull(watchService.poll());

        // emit EVENT_MODIFY
        Files.write(file, "hello1".getBytes());
        checkWatchServiceEvent(watchService, directoryKey1,
            Arrays.asList(new WatchEventResult(ENTRY_MODIFY)), true);

        // http:///b/35346596
        // Sometimes we receive a second, latent EVENT_MODIFY that happens shortly
        // after the first one. This will intercept it and make sure it won't
        // mess with ENTRY_DELETE later.
        Thread.sleep(500);
        WatchKey doubleModifyKey = watchService.poll();
        if (doubleModifyKey != null) {
            List<WatchEvent<?>> event = doubleModifyKey.pollEvents();
            assertEquals(ENTRY_MODIFY, event.get(0).kind());
            doubleModifyKey.reset();
        }
        assertNull(watchService.poll());

        // emit EVENT_DELETE
        Files.delete(file);
        checkWatchServiceEvent(watchService, directoryKey1,
            Arrays.asList(new WatchEventResult(ENTRY_DELETE, 1)), true);

        // Assert no more events
        assertNull(watchService.poll());
        watchService.close();
    }

    @Test
    public void test_EventMask() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        WatchEvent.Kind<?>[] events = {ENTRY_DELETE};
        Path file = Paths.get(filesSetup.getTestDir(), "directory/file");
        Path directory = Paths.get(filesSetup.getTestDir(), "directory");
        assertFalse(Files.exists(file));
        Files.createDirectories(directory);
        WatchKey directoryKey1 = directory.register(watchService, events);

        // emit EVENT_CREATE
        Files.createFile(file);
        // emit EVENT_MODIFY (masked)
        Files.write(file, "hello1".getBytes());
        // emit EVENT_DELETE (masked)
        Files.delete(file);

        checkWatchServiceEvent(watchService, directoryKey1,
                Arrays.asList(new WatchEventResult(ENTRY_DELETE, 1)), true);
        assertNull(watchService.poll());
        watchService.close();
    }

    @Test
    public void test_singleDirectory() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path dirInDir = Paths.get(filesSetup.getTestDir(), "directory/dir");
        Path directory = Paths.get(filesSetup.getTestDir(), "directory");
        assertFalse(Files.exists(dirInDir));
        Files.createDirectories(directory);
        WatchKey directoryKey1 = directory.register(watchService, ALL_EVENTS_KINDS);

        // emit EVENT_CREATE
        Files.createDirectories(dirInDir);

        // Shouldn't emit EVENT_MODIFY
        Path dirInDirInDir = Paths.get(filesSetup.getTestDir(), "directory/dir/dir");
        Files.createDirectories(dirInDirInDir);
        Files.delete(dirInDirInDir);

        // emit EVENT_DELETE
        Files.delete(dirInDir);

        checkWatchServiceEvent(watchService, directoryKey1,
                Arrays.asList(new WatchEventResult(ENTRY_CREATE, 1),
                              new WatchEventResult(ENTRY_DELETE, 1)), true);
        assertNull(watchService.poll());
        watchService.close();

        watchService.close();
    }

    @Test
    public void test_cancel() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path file = Paths.get(filesSetup.getTestDir(), "directory/file");
        Path directory = Paths.get(filesSetup.getTestDir(), "directory");
        assertFalse(Files.exists(file));
        Files.createDirectories(directory);
        WatchKey directoryKey1 = directory.register(watchService, ALL_EVENTS_KINDS);

        // emit EVENT_CREATE
        Files.createFile(file);

        // Canceling the key may prevent the EVENT_CREATE from being picked-up...
        // TODO: Fix this (b/35190858).
        Thread.sleep(500);

        // Cancel the key
        directoryKey1.cancel();
        assertFalse(directoryKey1.isValid());

        // Shouldn't emit EVENT_MODIFY and EVENT_DELETE
        Files.write(file, "hello1".getBytes());
        Files.delete(file);

        checkWatchServiceEvent(watchService, directoryKey1,
                Arrays.asList(new WatchEventResult(ENTRY_CREATE, 1)), false);
        assertNull(watchService.poll());
        watchService.close();
    }

    @Test
    public void test_removeTarget() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path file = Paths.get(filesSetup.getTestDir(), "directory/file");
        Path directory = Paths.get(filesSetup.getTestDir(), "directory");
        assertFalse(Files.exists(file));
        Files.createDirectories(directory);
        WatchKey directoryKey1 = directory.register(watchService, ALL_EVENTS_KINDS);

        // emit EVENT_CREATE x1
        Files.createFile(file);
        Files.delete(file);

        // Delete underlying target.
        assertTrue(directoryKey1.isValid());
        Files.delete(directory);

        // We need to give some time to watch service thread to catch up with the
        // deletion
        while (directoryKey1.isValid()) {
            Thread.sleep(500);
        }

        checkWatchServiceEvent(watchService, directoryKey1,
                Arrays.asList(new WatchEventResult(ENTRY_CREATE, 1),
                              new WatchEventResult(ENTRY_DELETE, 1)), false);
        assertNull(watchService.poll());

        watchService.close();
    }

    @Test
    public void test_multipleKeys() throws Exception {
        WatchService watchService1 = FileSystems.getDefault().newWatchService();

        Path directory1 = Paths.get(filesSetup.getTestDir(), "directory1");
        Path directory2 = Paths.get(filesSetup.getTestDir(), "directory2");

        Path dir1file1 = Paths.get(filesSetup.getTestDir(), "directory1/file1");
        assertFalse(Files.exists(dir1file1));
        Path dir2file1 = Paths.get(filesSetup.getTestDir(), "directory2/file1");
        assertFalse(Files.exists(dir2file1));

        Files.createDirectories(directory1);
        Files.createDirectories(directory2);
        WatchKey directoryKey1 = directory1.register(watchService1, ALL_EVENTS_KINDS);
        WatchKey directoryKey2 = directory2.register(watchService1, ALL_EVENTS_KINDS);

        // emit EVENT_CREATE/DELETE for all
        Path[] allFiles = new Path[]{dir1file1, dir2file1};
        for (Path path : allFiles) {
            Files.createFile(path);
            Files.delete(path);
        }

        Map<WatchKey, List<WatchEventResult>> expected = new HashMap<>();
        expected.put(directoryKey1,
                Arrays.asList(
                        new WatchEventResult(ENTRY_CREATE, 1),
                        new WatchEventResult(ENTRY_DELETE, 1)));
        expected.put(directoryKey2,
                Arrays.asList(
                        new WatchEventResult(ENTRY_CREATE, 1),
                        new WatchEventResult(ENTRY_DELETE, 1)));

        checkWatchServiceEventMultipleKeys(watchService1, expected, true);
        assertNull(watchService1.poll());
        watchService1.close();
    }

    @Test
    public void test_multipleServices() throws Exception {
        WatchService watchService1 = FileSystems.getDefault().newWatchService();
        WatchService watchService2 = FileSystems.getDefault().newWatchService();

        Path directory1 = Paths.get(filesSetup.getTestDir(), "directory1");
        Path directory2 = Paths.get(filesSetup.getTestDir(), "directory2");

        Path dir1file1 = Paths.get(filesSetup.getTestDir(), "directory1/file1");
        assertFalse(Files.exists(dir1file1));
        Path dir2file1 = Paths.get(filesSetup.getTestDir(), "directory2/file1");
        assertFalse(Files.exists(dir2file1));

        Files.createDirectories(directory1);
        Files.createDirectories(directory2);

        // 2 services listening for distinct directories
        WatchKey directoryKey1 = directory1.register(watchService1, ALL_EVENTS_KINDS);
        WatchKey directoryKey2 = directory2.register(watchService2, ALL_EVENTS_KINDS);
        // emit EVENT_CREATE/DELETE for all
        Path[] allFiles = new Path[]{dir1file1, dir2file1};
        for (Path path : allFiles) {
            Files.createFile(path);
            Files.delete(path);
        }

        checkWatchServiceEvent(watchService1, directoryKey1,
                                Arrays.asList(new WatchEventResult(ENTRY_CREATE, 1),
                                              new WatchEventResult(ENTRY_DELETE, 1)), true);
        checkWatchServiceEvent(watchService2, directoryKey2,
                                Arrays.asList(new WatchEventResult(ENTRY_CREATE, 1),
                                              new WatchEventResult(ENTRY_DELETE, 1)), true);

        // 2 services listening for a same directory
        WatchKey directoryKey3 = directory1.register(watchService2, ALL_EVENTS_KINDS);
        {
            Files.createFile(dir1file1);
            Files.delete(dir1file1);
        }
        checkWatchServiceEvent(watchService1, directoryKey1,
                                Arrays.asList(new WatchEventResult(ENTRY_CREATE, 1),
                                              new WatchEventResult(ENTRY_DELETE, 1)), true);
        checkWatchServiceEvent(watchService2, directoryKey3,
                                Arrays.asList(new WatchEventResult(ENTRY_CREATE, 1),
                                              new WatchEventResult(ENTRY_DELETE, 1)), true);



        assertNull(watchService1.poll());
        watchService1.close();
        assertNull(watchService2.poll());
        watchService2.close();
    }
}
