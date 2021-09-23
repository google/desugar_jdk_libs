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

package libcore.java.nio.channels;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class AsynchronousFileChannelTest {

    @Test
    public void testOpen_create() throws Throwable {
        Path tempDir = Files.createTempDirectory("ASFCTest_test_open_create");

        Path newFile = tempDir.resolve("newFile");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(newFile,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        assertTrue(channel.isOpen());
        assertEquals(0, channel.size());
        channel.close();
    }

    @Test
    public void testOpen_existing() throws Throwable {
        // Demonstrates a few warts related to re-opening files that already exist.
        Path tempDir = Files.createTempDirectory("ASFCTest_test_open_existing");
        Path newFile = tempDir.resolve("newFile");

        // Create a new file.
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(newFile,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        channel.close();

        // This should fail, but it doesn't..
        AsynchronousFileChannel.open(newFile, StandardOpenOption.CREATE_NEW);
        // ..unless it's paired with a write.
        try {
            AsynchronousFileChannel.open(newFile, StandardOpenOption.CREATE_NEW,
                    StandardOpenOption.WRITE);
            fail();
        } catch (FileAlreadyExistsException expected) {
        }

        // Create should still work, though.
        channel = AsynchronousFileChannel.open(newFile, StandardOpenOption.CREATE,
                StandardOpenOption.WRITE);
        channel.close();
    }

    @Test
    public void testOpen_nonexistent() throws Throwable {
        Path tempDir = Files.createTempDirectory("ASFCTest_test_open_nonexistent");

        Path nonExistent = tempDir.resolve("nonExistentFile");
        try {
            AsynchronousFileChannel.open(nonExistent, StandardOpenOption.READ);
            fail();
        } catch (NoSuchFileException expected) {
        }

        try {
            AsynchronousFileChannel.open(nonExistent, StandardOpenOption.WRITE);
            fail();
        } catch (NoSuchFileException expected) {
        }

        // The following three cases haven't clearly been mentioned in the documentation.
        // CREATE / CREATE_NEW fail unless they're paired with WRITE.
        //
        // CREATE will succeed without WRITE in the case that the file already exists, which
        // seems like a wart.
        try {
            AsynchronousFileChannel.open(nonExistent, StandardOpenOption.CREATE);
            fail();
        } catch (NoSuchFileException expected) {
        }

        try {
            AsynchronousFileChannel.open(nonExistent, StandardOpenOption.CREATE,
                    StandardOpenOption.READ);
            fail();
        } catch (NoSuchFileException expected) {
        }

        try {
            AsynchronousFileChannel.open(nonExistent, StandardOpenOption.CREATE_NEW);
            fail();
        } catch (NoSuchFileException expected) {
        }
    }

    private static File createTemporaryFile(int size) throws IOException {
        if (size % 256 != 0) {
            throw new IllegalArgumentException("size % 256 != 0: " + size);
        }

        File temp = File.createTempFile("AFCTest_tempfile", "");
        byte[] buf = new byte[256];

        try (FileOutputStream fos = new FileOutputStream(temp)) {
            int bytesWritten = 0;
            while (bytesWritten < size) {
                fos.write(buf);
                bytesWritten += buf.length;
            }
        }

        return temp;
    }

    private static File createTemporaryFile(byte[] contents) throws IOException {
        File temp = File.createTempFile("AFCTest_tempfile", "");

        try (FileOutputStream fos = new FileOutputStream(temp)) {
            fos.write(contents);
        }

        return temp;
    }

    @Test
    public void testOpen_truncate() throws Throwable {
        File temp = createTemporaryFile(256);
        assertEquals(256, temp.length());

        AsynchronousFileChannel afc = AsynchronousFileChannel.open(temp.toPath(),
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        afc.close();

        assertEquals(0, temp.length());
    }

    @Test
    public void testOpen_deleteOnClose() throws Throwable {
        File temp = createTemporaryFile(256);
        assertTrue(temp.exists());

        AsynchronousFileChannel afc = AsynchronousFileChannel.open(temp.toPath(),
                StandardOpenOption.DELETE_ON_CLOSE, StandardOpenOption.READ);
        assertEquals(256, afc.size());
        afc.close();

        assertFalse(temp.exists());
    }

    @Test
    public void testRead_Future() throws Throwable {
        byte[] contents = new byte[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        File temp = createTemporaryFile(contents);

        byte[] readBuf = new byte[4];
        ByteBuffer buf = ByteBuffer.wrap(readBuf);

        AsynchronousFileChannel afc = AsynchronousFileChannel.open(temp.toPath(),
                StandardOpenOption.WRITE);
        try {
            afc.read(buf, 0);
            fail();
        } catch (NonReadableChannelException exception) {
        }

        afc.close();

        afc = AsynchronousFileChannel.open(temp.toPath(),
                StandardOpenOption.READ);

        Future<Integer> fut = afc.read(buf, 0);
        assertEquals(4, (int) fut.get());
        buf.flip();
        assertEquals('a', readBuf[0]);
        assertEquals('b', readBuf[1]);
        assertEquals('c', readBuf[2]);
        assertEquals('d', readBuf[3]);

        // Short read: at the end of the file.
        fut = afc.read(buf, 6);
        assertEquals(2, (int) fut.get());
        assertEquals('g', readBuf[0]);
        assertEquals('h', readBuf[1]);

        // Reads past the end of the file.
        fut = afc.read(buf, 8);
        assertEquals(-1, (int) fut.get());

        fut = afc.read(buf, 9);
        assertEquals(-1, (int) fut.get());

        try {
            afc.read(buf, -1);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            afc.read(null, 1);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            afc.read(buf.asReadOnlyBuffer(), 1);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        afc.close();
    }

    static class RecordingHandler implements CompletionHandler<Integer, String> {
        public String attachment;
        public int result;
        public Throwable exc;

        private final CountDownLatch cdl = new CountDownLatch(1);

        @Override
        public void completed(Integer result, String attachment) {
            this.result = result;
            this.attachment = attachment;

            cdl.countDown();
        }

        @Override
        public void failed(Throwable exc, String attachment) {
            this.exc = exc;
            this.attachment = attachment;
        }

        public boolean awaitCompletion() throws InterruptedException {
            return cdl.await(10, TimeUnit.SECONDS);
        }
    }

    @Test
    public void testRead_CompletionListener() throws Throwable {
        byte[] contents = new byte[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        File temp = createTemporaryFile(contents);

        byte[] readBuf = new byte[4];
        ByteBuffer buf = ByteBuffer.wrap(readBuf);

        AsynchronousFileChannel afc = AsynchronousFileChannel.open(temp.toPath(),
                StandardOpenOption.WRITE);
        String attachment = "ATTACHMENT";
        RecordingHandler handler = new RecordingHandler();

        try {
            afc.read(buf, 0, attachment, handler);
            fail();
        } catch (NonReadableChannelException exception) {
        }

        afc.close();

        afc = AsynchronousFileChannel.open(temp.toPath(),
                StandardOpenOption.READ);
        afc.read(buf, 0, attachment, handler);
        assertTrue(handler.awaitCompletion());
        assertEquals(4, handler.result);
        assertSame(attachment, handler.attachment);
        buf.flip();
        assertEquals('a', readBuf[0]);
        assertEquals('b', readBuf[1]);
        assertEquals('c', readBuf[2]);
        assertEquals('d', readBuf[3]);

        // Short read: at the end of the file.
        handler = new RecordingHandler();
        attachment = "ATTACHMENT2";
        afc.read(buf, 6, attachment, handler);
        assertTrue(handler.awaitCompletion());
        assertEquals(2, handler.result);
        assertSame(attachment, handler.attachment);
        assertEquals('g', readBuf[0]);
        assertEquals('h', readBuf[1]);

        // Reads past the end of the file.
        handler = new RecordingHandler();
        attachment = "ATTACHMENT3";
        afc.read(buf, 8, attachment, handler);
        assertTrue(handler.awaitCompletion());
        assertEquals(-1, handler.result);
        assertSame(attachment, handler.attachment);

        handler = new RecordingHandler();
        attachment = "ATTACHMENT4";
        afc.read(buf, 9, attachment, handler);
        assertTrue(handler.awaitCompletion());
        assertEquals(-1, handler.result);
        assertSame(attachment, handler.attachment);

        handler = new RecordingHandler();
        attachment = "ATTACHMENT5";
        try {
            afc.read(buf, -1, attachment, handler);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            afc.read(null, 1, attachment, handler);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            afc.read(buf.asReadOnlyBuffer(), 1, attachment, handler);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        afc.close();
    }

    @Test
    public void testWrite_Future() throws Throwable {
        byte[] contents = new byte[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        File temp = createTemporaryFile(contents);

        byte[] readBuf = new byte[4];
        ByteBuffer buf = ByteBuffer.wrap(readBuf);

        AsynchronousFileChannel afc = AsynchronousFileChannel.open(temp.toPath(),
                StandardOpenOption.READ);
        try {
            afc.write(buf, 0);
            fail();
        } catch (NonWritableChannelException exception) {
        }

        afc.close();

        afc = AsynchronousFileChannel.open(temp.toPath(),
                StandardOpenOption.WRITE, StandardOpenOption.READ);

        assertEquals(2, (int) afc.write(ByteBuffer.wrap(new byte[] { 'x', 'y'}), 0).get());

        Future<Integer> fut = afc.read(buf, 0);

        assertEquals(4, (int) fut.get());
        buf.flip();
        assertEquals('x', readBuf[0]);
        assertEquals('y', readBuf[1]);
        assertEquals('c', readBuf[2]);
        assertEquals('d', readBuf[3]);

        // Write that expands beyond the end of the file.
        assertEquals(3, (int) afc.write(ByteBuffer.wrap(new byte[] { 'x', 'y', 'z'}), 6).get());
        assertEquals(9, afc.size());

        buf.rewind();
        fut = afc.read(buf, 6);
        assertEquals(3, (int) fut.get());
        buf.flip();
        assertEquals('x', readBuf[0]);
        assertEquals('y', readBuf[1]);
        assertEquals('z', readBuf[2]);

        // Writes at the end of the file.
        assertEquals(2, (int) afc.write(ByteBuffer.wrap(new byte[] { 'x', 'y' }), 9).get());
        assertEquals(11, afc.size());
        buf.rewind();
        fut = afc.read(buf, 9);
        assertEquals(2, (int) fut.get());
        buf.flip();
        assertEquals('x', readBuf[0]);
        assertEquals('y', readBuf[1]);

        // Writes past the end of the file.
        assertEquals(2, (int) afc.write(ByteBuffer.wrap(new byte[] { '0', '2' }), 13).get());
        assertEquals(15, afc.size());

        // This is broken behaviour. At this point, there are 4 readable bytes in the file
        // and our buffer should be filled with {0, 0, 48, 50}...
        buf.rewind();
        fut = afc.read(buf, 11);
        assertEquals(2, (int) fut.get());
        buf.flip();
        assertEquals(0, readBuf[0]);
        assertEquals(0, readBuf[1]);

        // ... if we explicitly read at position 13, things are somehow fine again.
        buf.rewind();
        fut = afc.read(buf, 13);
        assertEquals(2, (int) fut.get());
        buf.flip();
        assertEquals('0', readBuf[0]);
        assertEquals('2', readBuf[1]);

        try {
            afc.write(buf, -1);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            afc.write(null, 1);
            fail();
        } catch (NullPointerException expected) {
        }

        afc.close();
    }

    @Test
    public void testWrite_CompletionListener() throws Throwable {
        byte[] contents = new byte[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        File temp = createTemporaryFile(contents);

        byte[] readBuf = new byte[4];
        ByteBuffer buf = ByteBuffer.wrap(readBuf);

        AsynchronousFileChannel afc = AsynchronousFileChannel.open(temp.toPath(),
                StandardOpenOption.READ);

        String attachment = "ATTACHMENT";
        RecordingHandler handler = new RecordingHandler();
        try {
            afc.write(buf, 0, attachment, handler);
            fail();
        } catch (NonWritableChannelException exception) {
        }

        afc.close();

        afc = AsynchronousFileChannel.open(temp.toPath(),
                StandardOpenOption.WRITE, StandardOpenOption.READ);

        attachment = "ATTACHMENT";
        handler = new RecordingHandler();
        afc.write(ByteBuffer.wrap(new byte[] { 'x', 'y'}), 0, attachment, handler);
        assertTrue(handler.awaitCompletion());
        assertSame(attachment, handler.attachment);
        assertEquals(2, handler.result);

        Future<Integer> fut = afc.read(buf, 0);

        assertEquals(4, (int) fut.get());
        buf.flip();
        assertEquals('x', readBuf[0]);
        assertEquals('y', readBuf[1]);
        assertEquals('c', readBuf[2]);
        assertEquals('d', readBuf[3]);

        // Write that expands beyond the end of the file.
        attachment = "ATTACHMENT2";
        handler = new RecordingHandler();
        afc.write(ByteBuffer.wrap(new byte[] { 'x', 'y', 'z'}), 6, attachment, handler);
        assertTrue(handler.awaitCompletion());
        assertSame(attachment, handler.attachment);
        assertEquals(3, handler.result);

        assertEquals(9, afc.size());

        buf.rewind();
        fut = afc.read(buf, 6);
        assertEquals(3, (int) fut.get());
        buf.flip();
        assertEquals('x', readBuf[0]);
        assertEquals('y', readBuf[1]);
        assertEquals('z', readBuf[2]);

        // Writes at the end of the file.
        attachment = "ATTACHMENT3";
        handler = new RecordingHandler();
        afc.write(ByteBuffer.wrap(new byte[] { 'x', 'y' }), 9, attachment, handler);
        assertTrue(handler.awaitCompletion());
        assertSame(attachment, handler.attachment);
        assertEquals(2, handler.result);

        assertEquals(11, afc.size());
        buf.rewind();
        fut = afc.read(buf, 9);
        assertEquals(2, (int) fut.get());
        buf.flip();
        assertEquals('x', readBuf[0]);
        assertEquals('y', readBuf[1]);

        // Writes past the end of the file.
        attachment = "ATTACHMENT4";
        handler = new RecordingHandler();
        afc.write(ByteBuffer.wrap(new byte[] { '0', '2' }), 13, attachment, handler);
        assertTrue(handler.awaitCompletion());
        assertSame(attachment, handler.attachment);
        assertEquals(2, handler.result);

        assertEquals(15, afc.size());

        // This is broken behaviour. At this point, there are 4 readable bytes in the file
        // and our buffer should be filled with {0, 0, 48, 50}...
        buf.rewind();
        fut = afc.read(buf, 11);
        assertEquals(2, (int) fut.get());
        buf.flip();
        assertEquals(0, readBuf[0]);
        assertEquals(0, readBuf[1]);

        // ... if we explicitly read at position 13, things are somehow fine again.
        buf.rewind();
        fut = afc.read(buf, 13);
        assertEquals(2, (int) fut.get());
        buf.flip();
        assertEquals('0', readBuf[0]);
        assertEquals('2', readBuf[1]);

        try {
            afc.write(buf, -1);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            afc.write(null, 1);
            fail();
        } catch (NullPointerException expected) {
        }

        afc.close();
    }

    @Test
    public void testWrite_Append() throws Throwable {
        File temp = createTemporaryFile(256);

        try {
            AsynchronousFileChannel.open(temp.toPath(),
                    StandardOpenOption.WRITE, StandardOpenOption.APPEND);
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }

    @Test
    public void testSize() throws Throwable {
        File temp = createTemporaryFile(256);
        assertEquals(256, temp.length());

        AsynchronousFileChannel afc = AsynchronousFileChannel.open(temp.toPath(),
                StandardOpenOption.WRITE);
        // Test initial size.
        assertEquals(256, afc.size());

        // Test that the size is updated after a write at the end of the file.
        ByteBuffer buf = ByteBuffer.allocate(16);
        assertEquals(16, (int) afc.write(buf, 256).get());
        assertEquals(272, afc.size());

        // Test that the size is updated after a truncate.
        afc.truncate(16);
        assertEquals(16, afc.size());
        afc.close();
    }

    @Test
    public void testTruncate() throws Throwable {
        File temp = createTemporaryFile(256);
        assertEquals(256, temp.length());

        AsynchronousFileChannel afc = AsynchronousFileChannel.open(temp.toPath(),
                StandardOpenOption.WRITE);
        afc.truncate(128);
        assertEquals(128, afc.size());
        assertEquals(128, temp.length());

        afc.truncate(0);
        assertEquals(0, afc.size());
        assertEquals(0, temp.length());

        // Should be a no-op if the length is greater than the current length.
        afc.truncate(128);
        assertEquals(0, afc.size());
        assertEquals(0, temp.length());

        try {
            afc.truncate(-1);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        afc.close();

        // Attempts to truncate a file that's not writeable should throw a NWCE.
        temp = createTemporaryFile(256);
        afc = AsynchronousFileChannel.open(temp.toPath(), StandardOpenOption.READ);
        try {
            afc.truncate(128);
            fail();
        } catch (NonWritableChannelException expected) {
        }

        try {
            afc.truncate(384);
            fail();
        } catch (NonWritableChannelException expected) {
        }

        afc.close();
    }

    @Test
    public void testCustomExecutor() throws Throwable {
        AtomicReference<Thread> serviceThread = new AtomicReference<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                serviceThread.set(new Thread(r));
                return serviceThread.get();
            }
        });

        Set<OpenOption> openOptions = new HashSet<>();
        openOptions.add(StandardOpenOption.READ);
        openOptions.add(StandardOpenOption.WRITE);

        AsynchronousFileChannel afc = AsynchronousFileChannel.open(
                Files.createTempFile("AFCTest_testCustomExecutor", ""),
                openOptions, executorService);

        final LinkedBlockingQueue<Thread> observedThreads = new LinkedBlockingQueue<>();
        CompletionHandler<Integer, String> handler = new CompletionHandler<Integer, String>() {
            @Override
            public void completed(Integer result, String attachment) {
                assertTrue(observedThreads.offer(Thread.currentThread()));
            }

            @Override
            public void failed(Throwable exc, String attachment) {
                assertTrue(observedThreads.offer(Thread.currentThread()));
            }
        };

        afc.write(ByteBuffer.allocate(16), 0, "foo", handler);
        assertSame(serviceThread.get(), observedThreads.take());
        assertEquals(0, observedThreads.size());

        afc.read(ByteBuffer.allocate(16), 0, "foo", handler);
        assertSame(serviceThread.get(), observedThreads.take());
        assertEquals(0, observedThreads.size());
    }

    @Test
    public void testForce() throws Throwable {
        Path tempDir = Files.createTempFile("ASFCTest_test_force", "");

        AsynchronousFileChannel afc = AsynchronousFileChannel.open(tempDir,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        // Test that force can be called, not much else can be tested.
        assertEquals(2, (int) afc.write(ByteBuffer.wrap(new byte[] { 'x', 'y'}), 0).get());
        afc.force(false);
        assertEquals(2, (int) afc.write(ByteBuffer.wrap(new byte[] { 'x', 'y'}), 0).get());
        afc.force(true);
        afc.close();

        try {
            afc.force(true);
            fail();
        } catch(ClosedChannelException expected) {}
    }

}
