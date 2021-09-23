/*
 * Copyright (C) 2014 The Android Open Source Project
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
package libcore.java.nio.channels;

import junit.framework.TestCase;

import android.system.OsConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import libcore.io.Libcore;

import static libcore.io.IoUtils.closeQuietly;

/**
 * A test for file interrupt behavior. Because forcing a real file to block on read or write is
 * difficult this test uses Unix FIFO / Named Pipes. FIFOs appear to Java as files but the test
 * has more control over the available data. Reader will block until the other end writes, and
 * writers can also be made to block.
 *
 * <p>Using FIFOs has a few drawbacks:
 * <ol>
 * <li>FIFOs are not supported from Java or the command-line on Android, so this test includes
 * native code to create the FIFO.
 * <li>FIFOs will not open() until there is both a reader and a writer of the FIFO; each test must
 * always attach both ends or experience a blocked test.
 * <li>FIFOs are not supported on some file systems. e.g. VFAT, so the test has to be particular
 * about the temporary directory it uses to hold the FIFO.
 * <li>Writes to FIFOs are buffered by the OS which makes blocking behavior more difficult to
 * induce. See {@link ChannelWriter} and {@link StreamWriter}.
 * </ol>
 */
public class FileIOInterruptTest extends TestCase {

  private static File VOGAR_DEVICE_TEMP_DIR = new File("/data/data/file_io_interrupt_test");

  private File fifoFile;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    // This test relies on a FIFO file. The file system must support FIFOs, so we check the path.
    String tmpDirName = System.getProperty("java.io.tmpdir");
    File tmpDir;
    if (tmpDirName.startsWith("/sdcard")) {
      // Vogar execution on device runs in /sdcard. Unfortunately the file system used does not
      // support FIFOs so the test must use one that is more likely to work.
      if (!VOGAR_DEVICE_TEMP_DIR.exists()) {
        assertTrue(VOGAR_DEVICE_TEMP_DIR.mkdir());
      }
      VOGAR_DEVICE_TEMP_DIR.deleteOnExit();
      tmpDir = VOGAR_DEVICE_TEMP_DIR;
    } else {
      tmpDir = new File(tmpDirName);
    }
    fifoFile = new File(tmpDir, "fifo_file.tmp");
    if (fifoFile.exists()) {
      fifoFile.delete();
    }
    fifoFile.deleteOnExit();

    // Create the fifo. This will throw an exception if the file system does not support it.
    Libcore.os.mkfifo(fifoFile.getAbsolutePath(), OsConstants.S_IRWXU);
  }

  @Override
  public void tearDown() throws Exception {
    super.tearDown();
    fifoFile.delete();
    VOGAR_DEVICE_TEMP_DIR.delete();

    // Clear the interrupted state, if set.
    Thread.interrupted();
  }

  public void testStreamRead_exceptionWhenAlreadyClosed() throws Exception {
    FifoWriter fifoWriter = new FifoWriter(fifoFile);
    fifoWriter.start();

    FileInputStream fis = new FileInputStream(fifoFile);
    fis.close();

    byte[] buffer = new byte[10];
    try {
      fis.read(buffer);
      fail();
    } catch (IOException expected) {
      assertSame(IOException.class, expected.getClass());
    }

    fifoWriter.tidyUp();
  }

  // This test fails on the RI: close() does not wake up a blocking FileInputStream.read() call.
  public void testStreamRead_exceptionOnCloseWhenBlocked() throws Exception {
    FifoWriter fifoWriter = new FifoWriter(fifoFile);
    fifoWriter.start();

    FileInputStream fis = new FileInputStream(fifoFile);
    StreamReader streamReader = new StreamReader(fis);
    Thread streamReaderThread = createAndStartThread("StreamReader", streamReader);

    // Delay until we can be fairly sure the reader thread is blocking.
    streamReader.waitForThreadToBlock();

    // Now close the OutputStream to see what happens.
    fis.close();

    // Test for expected behavior in the reader thread.
    waitToDie(streamReaderThread);
    assertSame(InterruptedIOException.class, streamReader.ioe.getClass());
    assertFalse(streamReader.wasInterrupted);

    // Tidy up the writer thread.
    fifoWriter.tidyUp();
  }

  public void testStreamWrite_exceptionWhenAlreadyClosed() throws Exception {
    FifoReader fifoReader = new FifoReader(fifoFile);
    fifoReader.start();

    FileOutputStream fos = new FileOutputStream(fifoFile);
    byte[] buffer = new byte[10];
    fos.close();

    try {
      fos.write(buffer);
      fail();
    } catch (IOException expected) {
      assertSame(IOException.class, expected.getClass());
    }

    fifoReader.tidyUp();
  }

  // This test fails on the RI: close() does not wake up a blocking FileInputStream.write() call.
  public void testStreamWrite_exceptionOnCloseWhenBlocked() throws Exception {
    FifoReader fifoReader = new FifoReader(fifoFile);
    fifoReader.start();

    FileOutputStream fos = new FileOutputStream(fifoFile);
    StreamWriter streamWriter = new StreamWriter(fos);
    Thread streamWriterThread = createAndStartThread("StreamWriter", streamWriter);

    // Delay until we can be fairly sure the writer thread is blocking.
    streamWriter.waitForThreadToBlock();

    // Now close the OutputStream to see what happens.
    fos.close();

    // Test for expected behavior in the writer thread.
    waitToDie(streamWriterThread);
    assertSame(InterruptedIOException.class, streamWriter.ioe.getClass());
    assertFalse(streamWriter.wasInterrupted);

    // Tidy up the reader thread.
    fifoReader.tidyUp();
  }

  public void testChannelRead_exceptionWhenAlreadyClosed() throws Exception {
    testChannelRead_exceptionWhenAlreadyClosed(ChannelReader.Method.READ);
  }

  public void testChannelReadV_exceptionWhenAlreadyClosed() throws Exception {
    testChannelRead_exceptionWhenAlreadyClosed(ChannelReader.Method.READV);
  }

  private void testChannelRead_exceptionWhenAlreadyClosed(ChannelReader.Method method)
      throws Exception {
    FifoWriter fifoWriter = new FifoWriter(fifoFile);
    fifoWriter.start();
    FileInputStream fis = new FileInputStream(fifoFile);
    FileChannel fileInputChannel = fis.getChannel();
    fileInputChannel.close();

    ByteBuffer buffer = ByteBuffer.allocateDirect(10);
    try {
      if (method == ChannelReader.Method.READ) {
        fileInputChannel.read(buffer);
      } else {
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(10);
        fileInputChannel.read(new ByteBuffer[] { buffer, buffer2});
      }
      fail();
    } catch (IOException expected) {
      assertSame(ClosedChannelException.class, expected.getClass());
    }

    fifoWriter.tidyUp();
  }

  public void testChannelRead_exceptionWhenAlreadyInterrupted() throws Exception {
    testChannelRead_exceptionWhenAlreadyInterrupted(ChannelReader.Method.READ);
  }

  public void testChannelReadV_exceptionWhenAlreadyInterrupted() throws Exception {
    testChannelRead_exceptionWhenAlreadyInterrupted(ChannelReader.Method.READV);
  }

  private void testChannelRead_exceptionWhenAlreadyInterrupted(ChannelReader.Method method)
      throws Exception {
    FifoWriter fifoWriter = new FifoWriter(fifoFile);
    fifoWriter.start();
    FileInputStream fis = new FileInputStream(fifoFile);
    FileChannel fileInputChannel = fis.getChannel();

    Thread.currentThread().interrupt();

    ByteBuffer buffer = ByteBuffer.allocateDirect(10);
    try {
      if (method == ChannelReader.Method.READ) {
        fileInputChannel.read(buffer);
      } else {
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(10);
        fileInputChannel.read(new ByteBuffer[] { buffer, buffer2});
      }
      fail();
    } catch (IOException expected) {
      assertSame(ClosedByInterruptException.class, expected.getClass());
    }

    // Check but also clear the interrupted status, so we can wait for the FifoWriter thread in
    // tidyUp().
    assertTrue(Thread.interrupted());

    fifoWriter.tidyUp();
  }

  public void testChannelRead_exceptionOnCloseWhenBlocked() throws Exception {
    testChannelRead_exceptionOnCloseWhenBlocked(ChannelReader.Method.READ);
  }

  public void testChannelReadV_exceptionOnCloseWhenBlocked() throws Exception {
    testChannelRead_exceptionOnCloseWhenBlocked(ChannelReader.Method.READV);
  }

  private void testChannelRead_exceptionOnCloseWhenBlocked(ChannelReader.Method method)
      throws Exception {
    FifoWriter fifoWriter = new FifoWriter(fifoFile);
    fifoWriter.start();
    FileInputStream fis = new FileInputStream(fifoFile);
    FileChannel fileInputChannel = fis.getChannel();

    ChannelReader channelReader = new ChannelReader(fileInputChannel, method);
    Thread channelReaderThread = createAndStartThread("ChannelReader", channelReader);

    // Delay until we can be fairly sure the reader thread is blocking.
    channelReader.waitForThreadToBlock();

    // Now close the FileChannel to see what happens.
    fileInputChannel.close();

    // Test for expected behavior in the reader thread.
    waitToDie(channelReaderThread);
    assertSame(AsynchronousCloseException.class, channelReader.ioe.getClass());
    assertFalse(channelReader.wasInterrupted);

    // Tidy up the writer thread.
    fifoWriter.tidyUp();
  }

  public void testChannelRead_exceptionOnInterrupt() throws Exception {
    testChannelRead_exceptionOnInterrupt(ChannelReader.Method.READ);
  }

  public void testChannelReadV_exceptionOnInterrupt() throws Exception {
    testChannelRead_exceptionOnInterrupt(ChannelReader.Method.READV);
  }

  private void testChannelRead_exceptionOnInterrupt(ChannelReader.Method method) throws Exception {
    FifoWriter fifoWriter = new FifoWriter(fifoFile);
    fifoWriter.start();
    FileChannel fileChannel = new FileInputStream(fifoFile).getChannel();

    ChannelReader channelReader = new ChannelReader(fileChannel, method);
    Thread channelReaderThread = createAndStartThread("ChannelReader", channelReader);

    // Delay until we can be fairly sure the reader thread is blocking.
    channelReader.waitForThreadToBlock();

    // Now interrupt the reader thread to see what happens.
    channelReaderThread.interrupt();

    // Test for expected behavior in the reader thread.
    waitToDie(channelReaderThread);
    assertSame(ClosedByInterruptException.class, channelReader.ioe.getClass());
    assertTrue(channelReader.wasInterrupted);

    // Tidy up the writer thread.
    fifoWriter.tidyUp();
  }

  public void testChannelWrite_exceptionWhenAlreadyClosed() throws Exception {
    testChannelWrite_exceptionWhenAlreadyClosed(ChannelWriter.Method.WRITE);
  }

  public void testChannelWriteV_exceptionWhenAlreadyClosed() throws Exception {
    testChannelWrite_exceptionWhenAlreadyClosed(ChannelWriter.Method.WRITEV);
  }

  private void testChannelWrite_exceptionWhenAlreadyClosed(ChannelWriter.Method method)
      throws Exception {
    FifoReader fifoReader = new FifoReader(fifoFile);
    fifoReader.start();
    FileChannel fileOutputChannel = new FileOutputStream(fifoFile).getChannel();
    fileOutputChannel.close();

    ByteBuffer buffer = ByteBuffer.allocateDirect(10);
    try {
      if (method == ChannelWriter.Method.WRITE) {
        fileOutputChannel.write(buffer);
      } else {
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(10);
        fileOutputChannel.write(new ByteBuffer[] { buffer, buffer2 });
      }
      fail();
    } catch (IOException expected) {
      assertSame(ClosedChannelException.class, expected.getClass());
    }

    fifoReader.tidyUp();
  }

  public void testChannelWrite_exceptionWhenAlreadyInterrupted() throws Exception {
    testChannelWrite_exceptionWhenAlreadyInterrupted(ChannelWriter.Method.WRITE);
  }

  public void testChannelWriteV_exceptionWhenAlreadyInterrupted() throws Exception {
    testChannelWrite_exceptionWhenAlreadyInterrupted(ChannelWriter.Method.WRITEV);
  }

  private void testChannelWrite_exceptionWhenAlreadyInterrupted(ChannelWriter.Method method)
      throws Exception {
    FifoReader fifoReader = new FifoReader(fifoFile);
    fifoReader.start();
    FileOutputStream fos = new FileOutputStream(fifoFile);
    FileChannel fileInputChannel = fos.getChannel();

    Thread.currentThread().interrupt();

    ByteBuffer buffer = ByteBuffer.allocateDirect(10);
    try {
      if (method == ChannelWriter.Method.WRITE) {
        fileInputChannel.write(buffer);
      } else {
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(10);
        fileInputChannel.write(new ByteBuffer[] { buffer, buffer2 });
      }
      fail();
    } catch (IOException expected) {
      assertSame(ClosedByInterruptException.class, expected.getClass());
    }

    // Check but also clear the interrupted status, so we can wait for the FifoReader thread in
    // tidyUp().
    assertTrue(Thread.interrupted());

    fifoReader.tidyUp();
  }

  public void testChannelWrite_exceptionOnCloseWhenBlocked() throws Exception {
    testChannelWrite_exceptionOnCloseWhenBlocked(ChannelWriter.Method.WRITE);
  }

  public void testChannelWriteV_exceptionOnCloseWhenBlocked() throws Exception {
    testChannelWrite_exceptionOnCloseWhenBlocked(ChannelWriter.Method.WRITEV);
  }

  private void testChannelWrite_exceptionOnCloseWhenBlocked(ChannelWriter.Method method)
      throws Exception {
    FifoReader fifoReader = new FifoReader(fifoFile);
    fifoReader.start();
    FileChannel fileOutputChannel = new FileOutputStream(fifoFile).getChannel();

    ChannelWriter channelWriter = new ChannelWriter(fileOutputChannel, method);
    Thread channelWriterThread = createAndStartThread("ChannelWriter", channelWriter);

    // Delay until we can be fairly sure the writer thread is blocking.
    channelWriter.waitForThreadToBlock();

    // Now close the channel to see what happens.
    fileOutputChannel.close();

    // Test for expected behavior in the writer thread.
    waitToDie(channelWriterThread);
    // // The RI throws ChannelClosedException. AsynchronousCloseException is more correct according to
    // // the docs.
    //
    // Lies. RI throws AsynchronousCloseException only if NO data was written before interrupt.
    // I altered the ChannelWriter to write exactly 32k bytes and this triggers this behavior.
    // the AsynchronousCloseException. If some of data is written, the #write will return the number
    // of bytes written, and then the FOLLOWING #write will throw ChannelClosedException because
    // file has been closed. Android is actually doing a wrong thing by always throwing the
    // AsynchronousCloseException. Client application have no idea that SOME data
    //  was written in this case.
    assertSame(AsynchronousCloseException.class, channelWriter.ioe.getClass());
    assertFalse(channelWriter.wasInterrupted);

    // Tidy up the writer thread.
    fifoReader.tidyUp();
  }

  public void testChannelWrite_exceptionOnInterrupt() throws Exception {
    testChannelWrite_exceptionOnInterrupt(ChannelWriter.Method.WRITE);
  }

  public void testChannelWriteV_exceptionOnInterrupt() throws Exception {
    testChannelWrite_exceptionOnInterrupt(ChannelWriter.Method.WRITEV);
  }

  private void testChannelWrite_exceptionOnInterrupt(ChannelWriter.Method method) throws Exception {
    FifoReader fifoReader = new FifoReader(fifoFile);
    fifoReader.start();

    FileChannel fileChannel = new FileOutputStream(fifoFile).getChannel();
    ChannelWriter channelWriter = new ChannelWriter(fileChannel, method);
    Thread channelWriterThread = createAndStartThread("ChannelWriter", channelWriter);

    // Delay until we can be fairly sure the writer thread is blocking.
    channelWriter.waitForThreadToBlock();

    // Now interrupt the writer thread to see what happens.
    channelWriterThread.interrupt();

    // Test for expected behavior in the writer thread.
    waitToDie(channelWriterThread);
    assertSame(ClosedByInterruptException.class, channelWriter.ioe.getClass());
    assertTrue(channelWriter.wasInterrupted);

    // Tidy up the reader thread.
    fifoReader.tidyUp();
  }

  private static class StreamReader implements Runnable {

    private final FileInputStream inputStream;
    volatile boolean started;
    volatile IOException ioe;
    volatile boolean wasInterrupted;

    StreamReader(FileInputStream inputStream) {
      this.inputStream = inputStream;
    }

    @Override
    public void run() {
      byte[] buffer = new byte[10];
      try {
        started = true;
        int bytesRead = inputStream.read(buffer);
        fail("This isn't supposed to happen: read() returned: " + bytesRead);
      } catch (IOException e) {
        this.ioe = e;
      }
      wasInterrupted = Thread.interrupted();
    }

    public void waitForThreadToBlock() {
      for (int i = 0; i < 10 && !started; i++) {
        delay(100);
      }
      assertTrue(started);
      // Just give it some more time to start blocking.
      delay(100);
    }
  }

  private static class StreamWriter implements Runnable {

    private final FileOutputStream outputStream;
    volatile int bytesWritten;
    volatile IOException ioe;
    volatile boolean wasInterrupted;

    StreamWriter(FileOutputStream outputStream) {
      this.outputStream = outputStream;
    }

    @Override
    public void run() {
      // Writes to FIFOs are buffered. We try to fill the buffer and induce blocking (the
      // buffer is typically 64k).
      byte[] buffer = new byte[10000];
      while (true) {
        try {
          outputStream.write(buffer);
          bytesWritten += buffer.length;
        } catch (IOException e) {
          this.ioe = e;
          break;
        }
        wasInterrupted = Thread.interrupted();
      }
    }

    public void waitForThreadToBlock() {
      int lastCount = bytesWritten;
      for (int i = 0; i < 10; i++) {
        delay(500);
        int newBytesWritten = bytesWritten;
        if (newBytesWritten > 0 && lastCount == newBytesWritten) {
          // The thread is probably blocking.
          return;
        }
        lastCount = bytesWritten;
      }
      fail("Writer never started blocking. Bytes written: " + bytesWritten);
    }
  }

  private static class ChannelReader implements Runnable {
    enum Method {
      READ,
      READV,
    }

    private final FileChannel channel;
    private final Method method;
    volatile boolean started;
    volatile IOException ioe;
    volatile boolean wasInterrupted;

    ChannelReader(FileChannel channel, Method method) {
      this.channel = channel;
      this.method = method;
    }

    @Override
    public void run() {
      ByteBuffer buffer = ByteBuffer.allocateDirect(10);
      try {
        started = true;
        if (method == Method.READ) {
          channel.read(buffer);
        } else {
          ByteBuffer buffer2 = ByteBuffer.allocateDirect(10);
          channel.read(new ByteBuffer[] { buffer, buffer2 });
        }
        fail("All tests should block until an exception");
      } catch (IOException e) {
        this.ioe = e;
      }
      wasInterrupted = Thread.interrupted();
    }

    public void waitForThreadToBlock() {
      for (int i = 0; i < 10 && !started; i++) {
        delay(100);
      }
      assertTrue(started);
      // Just give it some more time to start blocking.
      delay(100);
    }
  }

  private static class ChannelWriter implements Runnable {
    enum Method {
      WRITE,
      WRITEV,
    }

    private final FileChannel channel;
    private final Method method;
    volatile int bytesWritten;
    volatile IOException ioe;
    volatile boolean wasInterrupted;

    ChannelWriter(FileChannel channel, Method method) {
      this.channel = channel;
      this.method = method;
    }

    @Override
    public void run() {
      ByteBuffer buffer1 = ByteBuffer.allocateDirect(32000);
      ByteBuffer buffer2 = ByteBuffer.allocateDirect(32000);
      // Writes to FIFOs are buffered. We try to fill the buffer and induce blocking (the
      // buffer is typically 64k).
      while (true) {
        // Make the buffers look non-empty.
        buffer1.position(0).limit(buffer1.capacity());
        buffer2.position(0).limit(buffer2.capacity());
        try {
          if (method == Method.WRITE) {
            bytesWritten += channel.write(buffer1);
          } else {
            bytesWritten += channel.write(new ByteBuffer[]{ buffer1, buffer2 });
          }
        } catch (IOException e) {
          this.ioe = e;
          break;
        }
      }
      wasInterrupted = Thread.interrupted();
    }

    public void waitForThreadToBlock() {
      int lastCount = bytesWritten;
      for (int i = 0; i < 10; i++) {
        delay(500);
        int newBytesWritten = bytesWritten;
        if (newBytesWritten > 0 && lastCount == newBytesWritten) {
          // The thread is probably blocking.
          return;
        }
        lastCount = bytesWritten;
      }
      fail("Writer never started blocking. Bytes written: " + bytesWritten);
    }
  }

  /**
   * Opens a FIFO for writing. Exists to unblock the other end of the FIFO.
   */
  private static class FifoWriter extends Thread {

    private final File file;
    private FileOutputStream fos;

    public FifoWriter(File file) {
      super("FifoWriter");
      this.file = file;
    }

    @Override
    public void run() {
      try {
        fos = new FileOutputStream(file);
      } catch (IOException ignored) {
      }
    }

    public void tidyUp() {
      FileIOInterruptTest.waitToDie(this);
      closeQuietly(fos);
    }
  }

  /**
   * Opens a FIFO for reading. Exists to unblock the other end of the FIFO.
   */
  private static class FifoReader extends Thread {

    private final File file;
    private FileInputStream fis;

    public FifoReader(File file) {
      super("FifoReader");
      this.file = file;
    }

    @Override
    public void run() {
      try {
        fis = new FileInputStream(file);
      } catch (IOException ignored) {
      }
    }

    public void tidyUp() {
      FileIOInterruptTest.waitToDie(this);
      closeQuietly(fis);
    }
  }

  private static Thread createAndStartThread(String name, Runnable runnable) {
    Thread t = new Thread(runnable, name);
    t.setDaemon(true);
    t.start();
    return t;
  }

  private static void waitToDie(Thread thread) {
    // Protect against this thread already being interrupted, which would prevent the test waiting
    // for the requested time.
    assertFalse(Thread.currentThread().isInterrupted());
    try {
      thread.join(5000);
    } catch (InterruptedException ignored) {
    }

    if (thread.isAlive()) {
      fail("Thread \"" + thread.getName() + "\" did not exit.");
    }
  }

  private static void delay(int millis) {
    // Protect against this thread being interrupted, which would prevent us waiting.
    assertFalse(Thread.currentThread().isInterrupted());
    try {
      Thread.sleep(millis);
    } catch (InterruptedException ignored) {
    }
  }

}
