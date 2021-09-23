/*
 * Copyright (C) 2009 The Android Open Source Project
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

package libcore.java.lang;

import android.system.ErrnoException;
import android.system.Os;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.ProcessBuilder.Redirect;
import java.lang.ProcessBuilder.Redirect.Type;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import junit.framework.TestCase;
import libcore.io.IoUtils;

import static java.lang.ProcessBuilder.Redirect.INHERIT;
import static java.lang.ProcessBuilder.Redirect.PIPE;

public class ProcessBuilderTest extends TestCase {
    private static final String TAG = ProcessBuilderTest.class.getSimpleName();

    /**
     * Returns the path to a command that is in /system/bin/ on Android but
     * /bin/ elsewhere.
     *
     * @param desktopPath the command path outside Android; must start with /bin/.
     */
    private static String commandPath(String desktopPath) {
        if (!desktopPath.startsWith("/bin/")) {
            throw new IllegalArgumentException(desktopPath);
        }
        String devicePath = System.getenv("ANDROID_ROOT") + desktopPath;
        return new File(devicePath).exists() ? devicePath : desktopPath;
    }

    private static String shell() {
        return commandPath("/bin/sh");
    }

    private static void assertRedirectErrorStream(boolean doRedirect,
            String expectedOut, String expectedErr) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(shell(), "-c", "echo out; echo err 1>&2");
        pb.redirectErrorStream(doRedirect);
        checkProcessExecution(pb, ResultCodes.ZERO,
                "" /* processInput */, expectedOut, expectedErr);
    }

    public void test_redirectErrorStream_true() throws Exception {
        assertRedirectErrorStream(true, "out\nerr\n", "");
    }

    public void test_redirectErrorStream_false() throws Exception {
        assertRedirectErrorStream(false, "out\n", "err\n");
    }

    public void testRedirectErrorStream_outputAndErrorAreMerged() throws Exception {
        Process process = new ProcessBuilder(shell())
                .redirectErrorStream(true)
                .start();
        try {
            int pid = getChildProcessPid(process);
            String path = "/proc/" + pid + "/fd/";
            assertEquals("stdout and stderr should point to the same socket",
                    Os.stat(path + "1").st_ino, Os.stat(path + "2").st_ino);
        } finally {
            process.destroy();
        }
    }

    /**
     * Tests that a child process can INHERIT this parent process's
     * stdin / stdout / stderr file descriptors.
     */
    public void testRedirectInherit() throws Exception {
        // We can't run shell() here because that exits when run with INHERITed
        // file descriptors from this process; "sleep" is less picky.
        Process process = new ProcessBuilder()
                .command(commandPath("/bin/sleep"), "5") // in seconds
                .redirectInput(Redirect.INHERIT)
                .redirectOutput(Redirect.INHERIT)
                .redirectError(Redirect.INHERIT)
                .start();
        try {
            List<Long> parentInodes = Arrays.asList(
                    Os.fstat(FileDescriptor.in).st_ino,
                    Os.fstat(FileDescriptor.out).st_ino,
                    Os.fstat(FileDescriptor.err).st_ino);
            int childPid = getChildProcessPid(process);
            // Get the inode numbers of the ends of the symlink chains
            List<Long> childInodes = Arrays.asList(
                    Os.stat("/proc/" + childPid + "/fd/0").st_ino,
                    Os.stat("/proc/" + childPid + "/fd/1").st_ino,
                    Os.stat("/proc/" + childPid + "/fd/2").st_ino);

            assertEquals(parentInodes, childInodes);
        } catch (ErrnoException e) {
            // Either (a) Os.fstat on our PID, or (b) Os.stat on our child's PID, failed.
            throw new AssertionError("stat failed; child process: " + process, e);
        } finally {
            process.destroy();
        }
    }

    public void testRedirectFile_input() throws Exception {
        String inputFileContents = "process input for testing\n" + TAG;
        File file = File.createTempFile(TAG, "in");
        try (Writer writer = new FileWriter(file)) {
            writer.write(inputFileContents);
        }
        ProcessBuilder pb = new ProcessBuilder(shell(), "-c", "cat").redirectInput(file);
        checkProcessExecution(pb, ResultCodes.ZERO, /* processInput */ "",
                /* expectedOutput */ inputFileContents, /* expectedError */ "");
        assertTrue(file.delete());
    }

    public void testRedirectFile_output() throws Exception {
        File file = File.createTempFile(TAG, "out");
        String processInput = TAG + "\narbitrary string for testing!";
        ProcessBuilder pb = new ProcessBuilder(shell(), "-c", "cat").redirectOutput(file);
        checkProcessExecution(pb, ResultCodes.ZERO, processInput,
                /* expectedOutput */ "", /* expectedError */ "");

        String fileContents = new String(IoUtils.readFileAsByteArray(
                file.getAbsolutePath()));
        assertEquals(processInput, fileContents);
        assertTrue(file.delete());
    }

    public void testRedirectFile_error() throws Exception {
        File file = File.createTempFile(TAG, "err");
        String processInput = "";
        String missingFilePath = "/test-missing-file-" + TAG;
        ProcessBuilder pb = new ProcessBuilder("ls", missingFilePath).redirectError(file);
        checkProcessExecution(pb, ResultCodes.NONZERO, processInput,
                /* expectedOutput */ "", /* expectedError */ "");

        String fileContents = new String(IoUtils.readFileAsByteArray(file.getAbsolutePath()));
        assertTrue(file.delete());
        // We assume that the path of the missing file occurs in the ls stderr.
        assertTrue("Unexpected output: " + fileContents,
                fileContents.contains(missingFilePath) && !fileContents.equals(missingFilePath));
    }

    public void testRedirectPipe_inputAndOutput() throws Exception {
        //checkProcessExecution(pb, expectedResultCode, processInput, expectedOutput, expectedError)

        String testString = "process input and output for testing\n" + TAG;
        {
            ProcessBuilder pb = new ProcessBuilder(shell(), "-c", "cat")
                    .redirectInput(PIPE)
                    .redirectOutput(PIPE);
            checkProcessExecution(pb, ResultCodes.ZERO, testString, testString, "");
        }

        // Check again without specifying PIPE explicitly, since that is the default
        {
        ProcessBuilder pb = new ProcessBuilder(shell(), "-c", "cat");
        checkProcessExecution(pb, ResultCodes.ZERO, testString, testString, "");
        }

        // Because the above test is symmetric regarding input vs. output, test
        // another case where input and output are different.
        {
            ProcessBuilder pb = new ProcessBuilder("echo", testString);
            checkProcessExecution(pb, ResultCodes.ZERO, "", testString + "\n", "");
        }
    }

    public void testRedirectPipe_error() throws Exception {
        String missingFilePath = "/test-missing-file-" + TAG;

        // Can't use checkProcessExecution() because we don't want to rely on an exact error content
        Process process = new ProcessBuilder("ls", missingFilePath)
                .redirectError(Redirect.PIPE).start();
        process.getOutputStream().close(); // no process input
        int resultCode = process.waitFor();
        ResultCodes.NONZERO.assertMatches(resultCode);
        assertEquals("", readAsString(process.getInputStream())); // no process output
        String errorString = readAsString(process.getErrorStream());
        // We assume that the path of the missing file occurs in the ls stderr.
        assertTrue("Unexpected output: " + errorString,
                errorString.contains(missingFilePath) && !errorString.equals(missingFilePath));
    }

    public void testRedirect_nullStreams() throws IOException {
        Process process = new ProcessBuilder()
                .command(shell())
                .inheritIO()
                .start();
        try {
            assertNullInputStream(process.getInputStream());
            assertNullOutputStream(process.getOutputStream());
            assertNullInputStream(process.getErrorStream());
        } finally {
            process.destroy();
        }
    }

    public void testRedirectErrorStream_nullStream() throws IOException {
        Process process = new ProcessBuilder()
                .command(shell())
                .redirectErrorStream(true)
                .start();
        try {
            assertNullInputStream(process.getErrorStream());
        } finally {
            process.destroy();
        }
    }

    public void testEnvironment() throws Exception {
        ProcessBuilder pb = new ProcessBuilder(shell(), "-c", "echo $A");
        pb.environment().put("A", "android");
        checkProcessExecution(pb, ResultCodes.ZERO, "", "android\n", "");
    }

    public void testDestroyClosesEverything() throws IOException {
        Process process = new ProcessBuilder(shell(), "-c", "echo out; echo err 1>&2").start();
        InputStream in = process.getInputStream();
        InputStream err = process.getErrorStream();
        OutputStream out = process.getOutputStream();
        process.destroy();

        try {
            in.read();
            fail();
        } catch (IOException expected) {
        }
        try {
            err.read();
            fail();
        } catch (IOException expected) {
        }
        try {
            /*
             * We test write+flush because the RI returns a wrapped stream, but
             * only bothers to close the underlying stream.
             */
            out.write(1);
            out.flush();
            fail();
        } catch (IOException expected) {
        }
    }

    public void testDestroyDoesNotLeak() throws IOException {
        Process process = new ProcessBuilder(shell(), "-c", "echo out; echo err 1>&2").start();
        process.destroy();
    }

    public void testEnvironmentMapForbidsNulls() throws Exception {
        ProcessBuilder pb = new ProcessBuilder(shell(), "-c", "echo $A");
        Map<String, String> environment = pb.environment();
        Map<String, String> before = new HashMap<String, String>(environment);
        try {
            environment.put("A", null);
            fail();
        } catch (NullPointerException expected) {
        }
        try {
            environment.put(null, "android");
            fail();
        } catch (NullPointerException expected) {
        }
        try {
            environment.containsKey(null);
            fail("Attempting to check the presence of a null key should throw");
        } catch (NullPointerException expected) {
        }
        try {
            environment.containsValue(null);
            fail("Attempting to check the presence of a null value should throw");
        } catch (NullPointerException expected) {
        }
        assertEquals(before, environment);
    }

    /**
     * Tests attempting to query the presence of a non-String key or value
     * in the environment map. Since that is a {@code Map<String, String>},
     * it's hard to imagine this ever breaking, but it's good to have a test
     * since it's called out in the documentation.
     */
    @SuppressWarnings("CollectionIncompatibleType")
    public void testEnvironmentMapForbidsNonStringKeysAndValues() {
        ProcessBuilder pb = new ProcessBuilder("echo", "Hello, world!");
        Map<String, String> environment = pb.environment();
        Integer nonString = Integer.valueOf(23);
        try {
            environment.containsKey(nonString);
            fail("Attempting to query the presence of a non-String key should throw");
        } catch (ClassCastException expected) {
        }
        try {
            environment.get(nonString);
            fail("Attempting to query the presence of a non-String key should throw");
        } catch (ClassCastException expected) {
        }
        try {
            environment.containsValue(nonString);
            fail("Attempting to query the presence of a non-String value should throw");
        } catch (ClassCastException expected) {
        }
    }

    /**
     * Checks that INHERIT and PIPE tend to have different hashCodes
     * in any particular instance of the runtime.
     * We test this by asserting that they use the identity hashCode,
     * which is a sufficient but not necessary condition for this.
     * If the implementation changes to a different sufficient condition
     * in future, this test should be updated accordingly.
     */
    public void testRedirect_inheritAndPipeTendToHaveDifferentHashCode() {
        assertIdentityHashCode(INHERIT);
        assertIdentityHashCode(PIPE);
    }

    public void testRedirect_hashCodeDependsOnFile() {
        File file = new File("/tmp/file");
        File otherFile = new File("/tmp/some_other_file") {
            @Override public int hashCode() { return 1 + file.hashCode(); }
        };
        Redirect a = Redirect.from(file);
        Redirect b = Redirect.from(otherFile);
        assertFalse("Unexpectedly equal hashCode: " + a + " vs. " + b,
                a.hashCode() == b.hashCode());
    }

    /**
     * Tests that {@link Redirect}'s equals() and hashCode() is useful.
     */
    public void testRedirect_equals() {
        File fileA = new File("/tmp/fileA");
        File fileB = new File("/tmp/fileB");
        File fileB2 = new File("/tmp/fileB");
        // check that test is set up correctly
        assertFalse(fileA.equals(fileB));
        assertEquals(fileB, fileB2);

        assertSymmetricEquals(Redirect.appendTo(fileB), Redirect.appendTo(fileB2));
        assertSymmetricEquals(Redirect.from(fileB), Redirect.from(fileB2));
        assertSymmetricEquals(Redirect.to(fileB), Redirect.to(fileB2));

        Redirect[] redirects = new Redirect[] {
                INHERIT,
                PIPE,
                Redirect.appendTo(fileA),
                Redirect.from(fileA),
                Redirect.to(fileA),
                Redirect.appendTo(fileB),
                Redirect.from(fileB),
                Redirect.to(fileB),
        };
        for (Redirect a : redirects) {
            for (Redirect b : redirects) {
                if (a != b) {
                    assertFalse("Unexpectedly equal: " + a + " vs. " + b, a.equals(b));
                    assertFalse("Unexpected asymmetric equality: " + a + " vs. " + b, b.equals(a));
                }
            }
        }
    }

    /**
     * Tests the {@link Redirect#type() type} and {@link Redirect#file() file} of
     * various Redirects. These guarantees are made in the respective javadocs,
     * so we're testing them together here.
     */
    public void testRedirect_fileAndType() {
        File file = new File("/tmp/fake-file-for/java.lang.ProcessBuilderTest");
        assertRedirectFileAndType(null, Type.INHERIT, INHERIT);
        assertRedirectFileAndType(null, Type.PIPE, PIPE);
        assertRedirectFileAndType(file, Type.APPEND, Redirect.appendTo(file));
        assertRedirectFileAndType(file, Type.READ, Redirect.from(file));
        assertRedirectFileAndType(file, Type.WRITE, Redirect.to(file));
    }

    private static void assertRedirectFileAndType(File expectedFile, Type expectedType,
            Redirect redirect) {
        assertEquals(redirect.toString(), expectedFile, redirect.file());
        assertEquals(redirect.toString(), expectedType, redirect.type());
    }

    public void testRedirect_defaultsToPipe() {
        assertRedirects(PIPE, PIPE, PIPE, new ProcessBuilder());
    }

    public void testRedirect_setAndGet() {
        File file = new File("/tmp/fake-file-for/java.lang.ProcessBuilderTest");
        assertRedirects(Redirect.from(file), PIPE, PIPE, new ProcessBuilder().redirectInput(file));
        assertRedirects(PIPE, Redirect.to(file), PIPE, new ProcessBuilder().redirectOutput(file));
        assertRedirects(PIPE, PIPE, Redirect.to(file), new ProcessBuilder().redirectError(file));
        assertRedirects(Redirect.from(file), INHERIT, Redirect.to(file),
                new ProcessBuilder()
                        .redirectInput(PIPE)
                        .redirectOutput(INHERIT)
                        .redirectError(file)
                        .redirectInput(file));

        assertRedirects(Redirect.INHERIT, Redirect.INHERIT, Redirect.INHERIT,
                new ProcessBuilder().inheritIO());
    }

    public void testCommand_setAndGet() {
        List<String> expected = Collections.unmodifiableList(
                Arrays.asList("echo", "fake", "command", "for", TAG));
        assertEquals(expected, new ProcessBuilder().command(expected).command());
        assertEquals(expected, new ProcessBuilder().command("echo", "fake", "command", "for", TAG)
                .command());
    }

    public void testDirectory_setAndGet() {
        File directory = new File("/tmp/fake/directory/for/" + TAG);
        assertEquals(directory, new ProcessBuilder().directory(directory).directory());
        assertNull(new ProcessBuilder().directory());
        assertNull(new ProcessBuilder()
                .directory(directory)
                .directory(null)
                .directory());
    }

    /**
     * One or more result codes returned by {@link Process#waitFor()}.
     */
    enum ResultCodes {
        ZERO { @Override void assertMatches(int actualResultCode) {
            assertEquals(0, actualResultCode);
        } },
        NONZERO { @Override void assertMatches(int actualResultCode) {
            assertTrue("Expected resultCode != 0, got 0", actualResultCode != 0);
        } };

        /** asserts that the given code falls within this ResultCodes */
        abstract void assertMatches(int actualResultCode);
    }

    /**
     * Starts the specified process, writes the specified input to it and waits for the process
     * to finish; then, then checks that the result code and output / error are expected.
     *
     * <p>This method assumes that the process consumes and produces character data encoded with
     * the platform default charset.
     */
    private static void checkProcessExecution(ProcessBuilder pb,
            ResultCodes expectedResultCode, String processInput,
            String expectedOutput, String expectedError) throws Exception {
        Process process = pb.start();
        Future<String> processOutput = asyncRead(process.getInputStream());
        Future<String> processError = asyncRead(process.getErrorStream());
        try (OutputStream outputStream = process.getOutputStream()) {
            outputStream.write(processInput.getBytes(Charset.defaultCharset()));
        }
        int actualResultCode = process.waitFor();
        expectedResultCode.assertMatches(actualResultCode);
        assertEquals(expectedOutput, processOutput.get());
        assertEquals(expectedError, processError.get());
    }

    /**
     * Asserts that inputStream is a <a href="ProcessBuilder#redirect-input">null input stream</a>.
     */
    private static void assertNullInputStream(InputStream inputStream) throws IOException {
        assertEquals(-1, inputStream.read());
        assertEquals(0, inputStream.available());
        inputStream.close(); // should do nothing
    }

    /**
     * Asserts that outputStream is a <a href="ProcessBuilder#redirect-output">null output
     * stream</a>.
     */
    private static void assertNullOutputStream(OutputStream outputStream) throws IOException {
        try {
            outputStream.write(42);
            fail("NullOutputStream.write(int) must throw IOException: " + outputStream);
        } catch (IOException expected) {
            // expected
        }
        outputStream.close(); // should do nothing
    }

    private static void assertRedirects(Redirect in, Redirect out, Redirect err, ProcessBuilder pb) {
        List<Redirect> expected = Arrays.asList(in, out, err);
        List<Redirect> actual = Arrays.asList(
                pb.redirectInput(), pb.redirectOutput(), pb.redirectError());
        assertEquals(expected, actual);
    }

    private static void assertIdentityHashCode(Redirect redirect) {
        assertEquals(System.identityHashCode(redirect), redirect.hashCode());
    }

    private static void assertSymmetricEquals(Redirect a, Redirect b) {
        assertEquals(a, b);
        assertEquals(b, a);
        assertEquals(a.hashCode(), b.hashCode());
    }

    private static int getChildProcessPid(Process process) {
        // Hack: UNIXProcess.pid is private; parse toString() instead of reflection
        Matcher matcher = Pattern.compile("pid=(\\d+)").matcher(process.toString());
        assertTrue("Can't find PID in: " + process, matcher.find());
        int result = Integer.parseInt(matcher.group(1));
        return result;
    }

    static String readAsString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int numRead;
        while ((numRead = inputStream.read(data)) >= 0) {
            outputStream.write(data, 0, numRead);
        }
        return new String(outputStream.toByteArray(), Charset.defaultCharset());
    }

    /**
     * Reads the entire specified {@code inputStream} asynchronously.
     */
    static FutureTask<String> asyncRead(final InputStream inputStream) {
        final FutureTask<String> result = new FutureTask<>(() -> readAsString(inputStream));
        new Thread("read asynchronously from " + inputStream) {
            @Override
            public void run() {
                result.run();
            }
        }.start();
        return result;
    }

}
