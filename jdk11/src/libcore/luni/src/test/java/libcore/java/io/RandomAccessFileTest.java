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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import libcore.junit.junit3.TestCaseWithRules;
import libcore.junit.util.ResourceLeakageDetector;
import libcore.junit.util.ResourceLeakageDetector.LeakageDetectorRule;
import org.junit.Rule;
import org.junit.rules.TestRule;

public final class RandomAccessFileTest extends TestCaseWithRules {
    @Rule
    public LeakageDetectorRule resourceLeakageDetectorRule = ResourceLeakageDetector.getRule();

    private File file;

    @Override protected void setUp() throws Exception {
        file = File.createTempFile("RandomAccessFileTest", "tmp");
    }

    @Override protected void tearDown() throws Exception {
        file.delete();
    }

    public void testSetLengthTooLarge() throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            try {
                raf.setLength(Long.MAX_VALUE);
                fail();
            } catch (IOException expected) {
            }
        }
    }

    public void testSetLength64() throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.setLength(0);
            assertEquals(0, file.length());
            long moreThanFourGig = ((long) Integer.MAX_VALUE) + 1L;
            raf.setLength(moreThanFourGig);
            assertEquals(moreThanFourGig, file.length());
        }
    }

    // http://b/3015023
    public void testRandomAccessFileHasCleanupFinalizer() throws Exception {
        File file = File.createTempFile("RandomAccessFileTest", "tmp");
        try (RandomAccessFile accessFile = new RandomAccessFile(file, "rw")) {
            resourceLeakageDetectorRule.assertUnreleasedResourceCount(accessFile, 1);
        }
    }

    // http://b/19892782
    public void testCloseRaf_sameChannelReturned() throws Exception {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        FileChannel fileChannelBeforeClosing = raf.getChannel();
        raf.close();
        FileChannel fileChannelAfterClosing = raf.getChannel();
        assertSame(fileChannelBeforeClosing, fileChannelAfterClosing);
    }

    // http://b/19892782
    public void testCloseRaf_channelIsClosed() throws Exception {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        FileChannel fileChannelBeforeClosing = raf.getChannel();
        raf.close();
        FileChannel fileChannelAfterClosing = raf.getChannel();
        assertFalse(fileChannelBeforeClosing.isOpen());
    }

    // http://b/19892782
    public void testCloseFileChannel_sameChannelReturned() throws Exception {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        FileChannel fileChannelBeforeClosing = raf.getChannel();
        fileChannelBeforeClosing.close();

        FileChannel fileChannelAfterClosing = raf.getChannel();
        assertSame(fileChannelBeforeClosing, fileChannelAfterClosing);
    }

    // http://b/19892782
    public void testCloseFileChannel_returnedFileChannelIsClosed() throws Exception {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        FileChannel fileChannelBeforeClosing = raf.getChannel();
        // This should close the Raf, and previous implementations wrongly returned a new
        // open (but useless) channel in this case.
        fileChannelBeforeClosing.close();
        FileChannel fileChannelAfterClosing = raf.getChannel();
        assertFalse(fileChannelBeforeClosing.isOpen());
    }

    // http://b/19892782
    public void testCloseRafBeforeGetChannel_returnChannelWithCloseFdAfterClose() throws Exception {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.close();
        try {
            raf.getChannel().size();
            fail();
        } catch (IOException expected) {
        }
    }

    private void createRandomAccessFile(File file) throws Exception {
        // TODO: fix our register maps and remove this otherwise unnecessary
        // indirection! (http://b/5412580)
        new RandomAccessFile(file, "rw");
    }

    public void testDirectories() throws Exception {
        try {
            new RandomAccessFile(".", "r");
            fail();
        } catch (FileNotFoundException expected) {
        }
        try {
            new RandomAccessFile(".", "rw");
            fail();
        } catch (FileNotFoundException expected) {
        }
    }
}
