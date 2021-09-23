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

package libcore.java.io;

import android.system.Os;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import junit.framework.TestCase;

import static org.junit.Assert.assertNotEquals;

public class FileDescriptorTest extends TestCase {
  public void testReadOnlyFileDescriptorSync() throws Exception {
    File f= File.createTempFile("FileDescriptorTest", "tmp");
    new RandomAccessFile(f, "r").getFD().sync();
  }

  public void test_isSocket() throws Exception {
    File f = new File("/dev/null");
    FileInputStream fis = new FileInputStream(f);
    assertFalse(fis.getFD().isSocket$());
    fis.close();

    ServerSocket s = new ServerSocket();
    assertTrue(s.getImpl().getFD$().isSocket$());
    s.close();
  }

  public void testStaticFileDescriptors() {
    assertTrue(FileDescriptor.in.valid());
    assertTrue(FileDescriptor.out.valid());
    assertTrue(FileDescriptor.err.valid());
  }

  public void testFileDescriptorCloneForFork() throws Exception {
    FileDescriptor [] sources = { FileDescriptor.in, FileDescriptor.out, FileDescriptor.err };
    for (FileDescriptor source : sources) {
      // Create a new file descriptor and set it's native descriptor to each of the well
      // known descriptors in FileDescriptor.
      FileDescriptor target = new FileDescriptor();
      target.setInt$(source.getInt$());
      assertEquals(target.getInt$(), source.getInt$());

      // Clone file descriptor, this creates a native file descriptor.
      target.cloneForFork();
      assertTrue(source.valid());
      assertTrue(target.valid());
      assertNotEquals(target.getInt$(), source.getInt$());

      // Clean-up native resource we allocated in cloneForFork. Os.close() may throw
      // an ErrnoException.
      Os.close(target);
    }
  }
}
