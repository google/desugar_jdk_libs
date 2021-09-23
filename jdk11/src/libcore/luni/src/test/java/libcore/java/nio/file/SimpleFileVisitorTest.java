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

import junit.framework.TestCase;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static org.mockito.Mockito.mock;

public class SimpleFileVisitorTest extends TestCase {

    public void test_preVisitDirectory() throws IOException {
        Path stubPath = mock(Path.class);
        BasicFileAttributes stubAttributes = mock(BasicFileAttributes.class);
        SimpleFileVisitor<Path> fileVisitor = new TestSimpleFileVisitor();

        assertEquals(FileVisitResult.CONTINUE, fileVisitor.preVisitDirectory(stubPath,
                stubAttributes));

        try {
            fileVisitor.preVisitDirectory(null, stubAttributes);
            fail();
        } catch (NullPointerException expected) {}

        try {
            fileVisitor.preVisitDirectory(stubPath, null);
            fail();
        } catch (NullPointerException expected) {}
    }

    public void test_postVisitDirectory() throws IOException {
        Path stubPath = mock(Path.class);
        IOException ioException = new IOException();
        SimpleFileVisitor<Path> fileVisitor = new TestSimpleFileVisitor();

        assertEquals(FileVisitResult.CONTINUE, fileVisitor.postVisitDirectory(stubPath, null));

        try {
            fileVisitor.postVisitDirectory(null, ioException);
            fail();
        } catch (NullPointerException expected) {}

        try {
            fileVisitor.postVisitDirectory(stubPath, ioException);
            fail();
        } catch (IOException actual) {
            assertSame(ioException, actual);
        }
    }

    public void test_visitFile() throws IOException {
        Path stubPath = mock(Path.class);
        BasicFileAttributes stubAttributes = mock(BasicFileAttributes.class);
        SimpleFileVisitor<Path> fileVisitor = new TestSimpleFileVisitor();

        assertEquals(FileVisitResult.CONTINUE, fileVisitor.visitFile(stubPath, stubAttributes));

        try {
            fileVisitor.visitFile(null, stubAttributes);
            fail();
        } catch (NullPointerException expected) {}

        try {
            fileVisitor.visitFile(stubPath, null);
            fail();
        } catch (NullPointerException expected) {}
    }

    public void test_visitFileFailed() throws IOException {
        Path stubPath = mock(Path.class);
        IOException ioException = new IOException();
        SimpleFileVisitor<Path> fileVisitor = new TestSimpleFileVisitor();

        try {
            assertEquals(FileVisitResult.CONTINUE, fileVisitor.visitFileFailed(stubPath, null));
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            assertEquals(FileVisitResult.CONTINUE, fileVisitor.visitFileFailed(null,
                    ioException));
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            assertEquals(FileVisitResult.CONTINUE, fileVisitor.visitFileFailed(stubPath,
                    ioException));
            fail();
        } catch (IOException actual) {
            assertSame(ioException, actual);
        }
    }

    /**
     * SimpleFileVisitor only has a protected constructor so we use a basic subclass for tests.
     */
    private static class TestSimpleFileVisitor extends SimpleFileVisitor<Path> {
    }
}
