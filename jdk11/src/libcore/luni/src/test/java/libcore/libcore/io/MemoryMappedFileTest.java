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

import junit.framework.TestCase;

import android.system.ErrnoException;
import android.system.OsConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.function.Function;
import libcore.io.BufferIterator;
import libcore.io.MemoryMappedFile;
import libcore.testing.io.TestIoUtils;

public class MemoryMappedFileTest extends TestCase {

    private File tempDir;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        tempDir = TestIoUtils.createTemporaryDirectory("MemoryMappedFileTest");
    }

    public void testMmapRo_missingFile() throws Exception {
        try {
            MemoryMappedFile.mmapRO("doesNotExist");
            fail();
        } catch (ErrnoException e) {
            assertEquals(OsConstants.ENOENT, e.errno);
        }
    }

    public void testMmapRo_emptyFile() throws Exception {
        byte[] bytes = new byte[0];
        File file = createFile(bytes);
        try {
            MemoryMappedFile.mmapRO(file.getPath());
            fail();
        } catch (ErrnoException e) {
            assertEquals(OsConstants.EINVAL, e.errno);
        } finally {
            file.delete();
        }
    }

    public void testMmapRo() throws Exception {
        byte[] bytes = createBytes(10);
        File file = createFile(bytes);
        try (MemoryMappedFile memoryMappedFile = MemoryMappedFile.mmapRO(file.getPath())) {
            assertEquals(10, memoryMappedFile.size());
        } finally {
            file.delete();
        }
    }

    public void testMmapRo_close() throws Exception {
        byte[] bytes = createBytes(10);
        File file = createFile(bytes);
        MemoryMappedFile memoryMappedFile = MemoryMappedFile.mmapRO(file.getPath());
        memoryMappedFile.close();

        try {
            memoryMappedFile.bigEndianIterator();
            fail();
        } catch (IllegalStateException expected) {
        }

        try {
            memoryMappedFile.littleEndianIterator();
            fail();
        } catch (IllegalStateException expected) {
        }

        // Should not have any effect.
        memoryMappedFile.close();
    }

    public void testReadAfterCloseFails() throws Exception {
        byte[] bytes = createBytes(10);
        File file = createFile(bytes);
        MemoryMappedFile memoryMappedFile = MemoryMappedFile.mmapRO(file.getPath());
        BufferIterator iterator = memoryMappedFile.bigEndianIterator();
        memoryMappedFile.close();

        try {
            iterator.readByte();
            fail();
        } catch (IllegalStateException expected) {}
    }

    public void testReadByte() throws Exception {
        checkReadByte(MemoryMappedFile::bigEndianIterator);
        checkReadByte(MemoryMappedFile::littleEndianIterator);
    }

    private void checkReadByte(
            Function<MemoryMappedFile, BufferIterator> iteratorFactory) throws Exception {

        byte[] bytes = createBytes(10);
        File file = createFile(bytes);
        try {
            MemoryMappedFile mappedFile = MemoryMappedFile.mmapRO(file.getPath());
            BufferIterator iterator = iteratorFactory.apply(mappedFile);
            for (int i = 0; i < bytes.length; i++) {
                assertReadByteSucceeds(iterator, bytes[i]);
            }

            // Check skip.
            iterator.seek(0);
            for (int i = 0; i < bytes.length; i += 2) {
                assertReadByteSucceeds(iterator, bytes[i]);
                iterator.skip(1);
            }
        } finally {
            file.delete();
        }
    }

    public void testSeek() throws Exception {
        checkSeek(MemoryMappedFile::bigEndianIterator);
        checkSeek(MemoryMappedFile::littleEndianIterator);
    }

    private void checkSeek(
            Function<MemoryMappedFile, BufferIterator> iteratorFactory) throws Exception {

        byte[] bytes = createBytes(10);
        File file = createFile(bytes);
        try {
            MemoryMappedFile mappedFile = MemoryMappedFile.mmapRO(file.getPath());
            BufferIterator iterator = iteratorFactory.apply(mappedFile);
            seekRead(bytes, iterator, 2);

            seekRead(bytes, iterator, 0);

            seekRead(bytes, iterator, 1);

            seekRead(bytes, iterator, 9);

            seekReadExpectFailure(iterator, -1);

            seekRead(bytes, iterator, 1);

            seekReadExpectFailure(iterator, 10);
            seekReadExpectFailure(iterator, Integer.MAX_VALUE);
            seekReadExpectFailure(iterator, Integer.MIN_VALUE);
        } finally {
            file.delete();
        }
    }

    private static void seekRead(byte[] bytes, BufferIterator iterator, int offset) {
        iterator.seek(offset);
        assertEquals(offset, iterator.pos());
        assertReadByteSucceeds(iterator, bytes[offset]);
    }

    private static void seekReadExpectFailure(BufferIterator iterator, int offset) {
        iterator.seek(offset);
        assertReadByteFails(iterator);
    }

    public void testSkip() throws Exception {
        checkSkip(MemoryMappedFile::bigEndianIterator);
        checkSkip(MemoryMappedFile::littleEndianIterator);
    }

    private void checkSkip(
            Function<MemoryMappedFile, BufferIterator> iteratorFactory) throws Exception {

        byte[] bytes = createBytes(10);
        File file = createFile(bytes);
        try {
            MemoryMappedFile mappedFile = MemoryMappedFile.mmapRO(file.getPath());
            BufferIterator iterator = iteratorFactory.apply(mappedFile);
            iterator.skip(1);
            assertEquals(1, iterator.pos());
            assertReadByteSucceeds(iterator, bytes[1]);

            iterator.skip(-1);
            assertEquals(1, iterator.pos());
            assertReadByteSucceeds(iterator, bytes[1]);

            iterator.skip(2);
            assertEquals(4, iterator.pos());
            assertReadByteSucceeds(iterator, bytes[4]);

            iterator.skip(-2);
            assertEquals(3, iterator.pos());
            assertReadByteSucceeds(iterator, bytes[3]);

            iterator.skip(3);
            assertEquals(7, iterator.pos());
            assertReadByteSucceeds(iterator, bytes[7]);

            iterator.skip(-3);
            assertEquals(5, iterator.pos());
            assertReadByteSucceeds(iterator, bytes[5]);

            iterator.skip(4);
            assertEquals(10, iterator.pos());
            assertReadByteFails(iterator);

            iterator.skip(-1);
            assertEquals(9, iterator.pos());
            assertReadByteSucceeds(iterator, bytes[9]);
        } finally {
            file.delete();
        }
    }

    public void testReadShort_bigEndian() throws Exception {
        byte[] bytes = createBytes(10);
        File file = createFile(bytes);
        try {
            MemoryMappedFile mappedFile = MemoryMappedFile.mmapRO(file.getPath());
            BufferIterator iterator = mappedFile.bigEndianIterator();

            // Even offset
            short expectedValue = (short) ((bytes[0] << 8) | bytes[1]);
            assertReadShortSucceeds(iterator, expectedValue);

            checkShortFailureCases(iterator);

            // Odd offset.
            iterator.seek(1);
            expectedValue = (short) ((bytes[1] << 8) | bytes[2]);
            assertReadShortSucceeds(iterator, expectedValue);
        } finally {
            file.delete();
        }
    }

    public void testReadShort_littleEndian() throws Exception {
        byte[] bytes = createBytes(10);
        File file = createFile(bytes);
        try {
            MemoryMappedFile mappedFile = MemoryMappedFile.mmapRO(file.getPath());
            BufferIterator iterator = mappedFile.littleEndianIterator();

            // Even offset
            short expectedValue = (short) ((bytes[1] << 8) | bytes[0]);
            assertReadShortSucceeds(iterator, expectedValue);

            checkShortFailureCases(iterator);

            // Odd offset.
            iterator.seek(1);
            expectedValue = (short) ((bytes[2] << 8) | bytes[1]);
            assertReadShortSucceeds(iterator, expectedValue);
        } finally {
            file.delete();
        }
    }

    private static void checkShortFailureCases(BufferIterator iterator) {
        // Partly before bounds.
        iterator.seek(-1);
        assertReadShortFails(iterator);

        // Entirely before bounds.
        iterator.seek(-2);
        assertReadShortFails(iterator);

        // Partly after bounds.
        iterator.seek(9);
        assertReadShortFails(iterator);

        // Entirely after bounds.
        iterator.seek(10);
        assertReadShortFails(iterator);
    }

    public void testReadInt_bigEndian() throws Exception {
        byte[] bytes = createBytes(10);
        File file = createFile(bytes);
        try {
            MemoryMappedFile mappedFile = MemoryMappedFile.mmapRO(file.getPath());
            BufferIterator iterator = mappedFile.bigEndianIterator();

            // Even offset
            int expectedValue = (bytes[0] << 24) | (bytes[1] << 16) | (bytes[2] << 8) | bytes[3];
            assertReadIntSucceeds(iterator, expectedValue);

            checkIntFailureCases(iterator);

            // Odd offset.
            iterator.seek(1);
            expectedValue = (bytes[1] << 24) | (bytes[2] << 16) | (bytes[3] << 8) | bytes[4];
            assertReadIntSucceeds(iterator, expectedValue);
        } finally {
            file.delete();
        }
    }

    public void testReadInt_littleEndian() throws Exception {
        byte[] bytes = createBytes(10);
        File file = createFile(bytes);
        try {
            MemoryMappedFile mappedFile = MemoryMappedFile.mmapRO(file.getPath());
            BufferIterator iterator = mappedFile.littleEndianIterator();

            // Even offset
            int expectedValue = (bytes[3] << 24) | (bytes[2] << 16) | (bytes[1] << 8) | bytes[0];
            assertReadIntSucceeds(iterator, expectedValue);

            checkIntFailureCases(iterator);

            // Odd offset.
            iterator.seek(1);
            expectedValue = (bytes[4] << 24) | (bytes[3] << 16) | (bytes[2] << 8) | bytes[1];
            assertReadIntSucceeds(iterator, expectedValue);
        } finally {
            file.delete();
        }
    }

    private static void checkIntFailureCases(BufferIterator iterator) {
        // Partly before bounds.
        iterator.seek(-1);
        assertReadIntFails(iterator);

        // Entirely before bounds.
        iterator.seek(-4);
        assertReadIntFails(iterator);

        // Partly after bounds.
        iterator.seek(7);
        assertReadIntFails(iterator);

        // Entirely after bounds.
        iterator.seek(10);
        assertReadIntFails(iterator);
    }

    public void testReadIntArray() throws Exception {
        checkReadIntArray(MemoryMappedFile::bigEndianIterator, ByteOrder.BIG_ENDIAN);
        checkReadIntArray(MemoryMappedFile::littleEndianIterator, ByteOrder.LITTLE_ENDIAN);
    }

    private void checkReadIntArray(
            Function<MemoryMappedFile, BufferIterator> iteratorFactory,
            ByteOrder byteOrdering) throws Exception {

        byte[] testBytes = createBytes(12);
        File file = createFile(testBytes);
        try {
            MemoryMappedFile mappedFile = MemoryMappedFile.mmapRO(file.getPath());
            BufferIterator iterator = iteratorFactory.apply(mappedFile);

            // Even offsets.
            iterator.seek(4);
            assertReadIntArraySucceeds(iterator, testBytes, byteOrdering, 2 /* intCount */);

            iterator.seek(0);
            assertReadIntArraySucceeds(iterator, testBytes, byteOrdering, 3 /* intCount */);

            checkIntArrayZeroReadCases(iterator);

            // Odd offsets.
            iterator.seek(1);
            assertReadIntArraySucceeds(iterator, testBytes, byteOrdering, 2 /* intCount */);
            iterator.seek(3);
            assertReadIntArraySucceeds(iterator, testBytes, byteOrdering, 2 /* intCount */);
        } finally {
            file.delete();
        }
    }

    private static void checkIntArrayZeroReadCases(BufferIterator iterator) {
        // Zero length reads do nothing.
        int posBeforeRead = iterator.pos();
        int[] dstWithExistingValues = new int[] { 111, 222 };
        iterator.readIntArray(dstWithExistingValues, 0, 0);
        assertEquals(posBeforeRead, iterator.pos());
        assertArrayEquals(new int[] { 111, 222 }, dstWithExistingValues);

        try {
            iterator.readIntArray(null, 0, 0);
            fail();
        } catch (NullPointerException expected) {
        }
        assertEquals(posBeforeRead, iterator.pos());

        int[] dst = new int[2];

        // Partly before bounds.
        iterator.seek(-1);
        assertReadIntArrayFails(iterator, dst, 0, 1);

        // Entirely before bounds.
        iterator.seek(-2);
        assertReadIntArrayFails(iterator, dst, 0, 1);

        // Partly after bounds.
        iterator.seek(9);
        assertReadIntArrayFails(iterator, dst, 0, 1);

        // Entirely after bounds.
        iterator.seek(12);
        assertReadIntArrayFails(iterator, dst, 0, 1);

        // dst too small.
        assertReadIntArrayFails(iterator, dst, 0, 3); // dst can only hold 2 ints

        // offset leaves dst too small.
        assertReadIntArrayFails(iterator, dst, 1, 2);

        // Invalid offset
        assertReadIntArrayFails(iterator, dst, -1, 2);
        assertReadIntArrayFails(iterator, dst, 2, 2);

        // Null dst
        try {
            iterator.readIntArray(null, 0, 1);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void testReadByteArray() throws Exception {
        checkReadByteArray(MemoryMappedFile::bigEndianIterator);
        checkReadByteArray(MemoryMappedFile::littleEndianIterator);
    }

    private void checkReadByteArray(
            Function<MemoryMappedFile, BufferIterator> iteratorFactory) throws Exception {

        byte[] testBytes = createBytes(12);
        File file = createFile(testBytes);
        try {
            MemoryMappedFile mappedFile = MemoryMappedFile.mmapRO(file.getPath());
            BufferIterator iterator = iteratorFactory.apply(mappedFile);

            // Even offsets.
            iterator.seek(4);
            assertReadByteArraySucceeds(iterator, testBytes, 2 /* intCount */);

            iterator.seek(0);
            assertReadByteArraySucceeds(iterator, testBytes, 3 /* intCount */);

            checkByteArrayZeroReadCases(iterator);

            // Odd offsets.
            iterator.seek(1);
            assertReadByteArraySucceeds(iterator, testBytes, 2 /* intCount */);
            iterator.seek(3);
            assertReadByteArraySucceeds(iterator, testBytes, 2 /* intCount */);
        } finally {
            file.delete();
        }
    }

    private static void checkByteArrayZeroReadCases(BufferIterator iterator) {
        // Zero length reads do nothing.
        int posBeforeRead = iterator.pos();
        byte[] dstWithExistingValues = new byte[] { 11, 22, 33, 44, 55, 66, 77, 88 };
        iterator.readByteArray(dstWithExistingValues, 0, 0);
        assertEquals(posBeforeRead, iterator.pos());
        assertArrayEquals(new byte[] { 11, 22, 33, 44, 55, 66, 77, 88 }, dstWithExistingValues);

        try {
            iterator.readByteArray(null, 0, 0);
            fail();
        } catch (NullPointerException expected) {
        }
        assertEquals(posBeforeRead, iterator.pos());

        byte[] dst = new byte[10];

        // Before bounds.
        iterator.seek(-1);
        assertReadByteArrayFails(iterator, dst, 0, 1);

        // After bounds.
        iterator.seek(12);
        assertReadByteArrayFails(iterator, dst, 0, 1);

        // dst too small.
        assertReadByteArrayFails(iterator, dst, 0, 11); // dst can only hold 10 bytes

        // offset leaves dst too small.
        assertReadByteArrayFails(iterator, dst, 1, 10);

        // Invalid offset
        assertReadByteArrayFails(iterator, dst, -1, 2);
        assertReadByteArrayFails(iterator, dst, 2, 2);

        // Null dst
        try {
            iterator.readByteArray(null, 0, 1);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    private static void assertReadByteArrayFails(
            BufferIterator iterator, byte[] dst, int offset, int intCount) {

        int posBefore = iterator.pos();
        try {
            iterator.readByteArray(dst, offset, intCount);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
        assertEquals(posBefore, iterator.pos());
    }

    private static void assertReadByteArraySucceeds(
            BufferIterator iterator, byte[] underlyingData, int byteCount) {

        int posBefore = iterator.pos();

        // Create a byte[] containing book-end bytes we don't expect to be touched:
        // [Byte.MAX_VALUE, {the bytes we expect from underlyingData from posBefore onward},
        // Byte.MIN_VALUE].
        byte[] expectedBytes = new byte[byteCount + 2];
        expectedBytes[0] = Byte.MAX_VALUE;
        expectedBytes[byteCount - 1] = Byte.MIN_VALUE;
        System.arraycopy(underlyingData, posBefore, expectedBytes, 1, byteCount);

        // Get the true data.
        byte[] dst = new byte[byteCount + 2];
        // Copy the two bytes we expect to be untouched.
        dst[0] = expectedBytes[0];
        dst[byteCount - 1] = expectedBytes[byteCount - 1];
        // Do the read.
        iterator.readByteArray(dst, 1, byteCount);

        assertArrayEquals(expectedBytes, dst);
        assertEquals(posBefore + byteCount, iterator.pos());
    }

    private static void assertReadIntArrayFails(
            BufferIterator iterator, int[] dst, int offset, int intCount) {

        int posBefore = iterator.pos();
        try {
            iterator.readIntArray(dst, offset, intCount);
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
        assertEquals(posBefore, iterator.pos());
    }

    private static void assertReadIntArraySucceeds(
            BufferIterator iterator, byte[] underlyingData, ByteOrder byteOrder, int intCount) {

        int posBefore = iterator.pos();

        // Create an int[] containing book-end ints we don't expect to be touched:
        // [Integer.MAX_VALUE, {the ints we expect from underlyingData from posBefore onward},
        // Integer.MIN_VALUE].

        // Create an IntBuffer containing the ints we'd expect from underlyingData from posBefore
        // onward.
        ByteBuffer byteBuffer = ByteBuffer.wrap(underlyingData);
        byteBuffer.position(posBefore);
        IntBuffer expectedIntsBuffer = byteBuffer.slice().order(byteOrder).asIntBuffer();
        assertEquals(byteOrder, expectedIntsBuffer.order());

        // Copy the ints we expect.
        int[] expectedInts = new int[intCount + 2];
        expectedInts[0] = Integer.MAX_VALUE;
        expectedInts[intCount - 1] = Integer.MIN_VALUE;
        expectedIntsBuffer.get(expectedInts, 1, intCount);

        // Get the true data.
        int[] dst = new int[intCount + 2];
        dst[0] = expectedInts[0];
        dst[intCount - 1] = expectedInts[intCount - 1];
        iterator.readIntArray(dst, 1, intCount);

        assertArrayEquals(expectedInts, dst);
        assertEquals(posBefore + (intCount * Integer.BYTES), iterator.pos());
    }

    private static void assertReadIntFails(BufferIterator iterator) {
        int posBefore = iterator.pos();
        try {
            iterator.readInt();
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
        assertEquals(posBefore, iterator.pos());
    }

    private static void assertReadIntSucceeds(BufferIterator iterator, int expectedValue) {
        int posBefore = iterator.pos();
        assertEquals(expectedValue, iterator.readInt());
        assertEquals(posBefore + Integer.BYTES, iterator.pos());
    }

    private static void assertReadShortFails(BufferIterator iterator) {
        int posBefore = iterator.pos();
        try {
            iterator.readShort();
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
        assertEquals(posBefore, iterator.pos());
    }

    private static void assertReadShortSucceeds(BufferIterator iterator, short expectedValue) {
        int posBefore = iterator.pos();
        assertEquals(expectedValue, iterator.readShort());
        assertEquals(posBefore + Short.BYTES, iterator.pos());
    }

    private static void assertReadByteFails(BufferIterator iterator) {
        int posBefore = iterator.pos();
        try {
            iterator.readByte();
            fail();
        } catch (IndexOutOfBoundsException expected) {
        }
        // Must not advance pos.
        assertEquals(posBefore, iterator.pos());
    }

    private static void assertReadByteSucceeds(BufferIterator iterator, byte expectedValue) {
        int posBefore = iterator.pos();
        assertEquals(expectedValue, iterator.readByte());
        assertEquals(posBefore + 1, iterator.pos());
    }

    private static void assertArrayEquals(int[] expected, int[] actual) {
        assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    private static void assertArrayEquals(byte[] expected, byte[] actual) {
        assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    private static byte[] createBytes(int byteCount) {
        byte[] bytes = new byte[byteCount];
        for (int i = 0; i < byteCount; i++) {
            bytes[i] = (byte) i;
        }
        return bytes;
    }

    private File createFile(byte[] bytes) throws Exception {
        File file = File.createTempFile("bytes", null, tempDir);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        }
        return file;
    }
}
