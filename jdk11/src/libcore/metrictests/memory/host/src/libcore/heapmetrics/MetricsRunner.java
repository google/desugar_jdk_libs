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
 * limitations under the License.
 */

package libcore.heapmetrics;

import com.android.ahat.heapdump.AhatSnapshot;
import com.android.ahat.heapdump.Diff;
import com.android.ahat.heapdump.HprofFormatException;
import com.android.ahat.heapdump.Parser;
import com.android.ahat.proguard.ProguardMap;
import com.android.tradefed.device.DeviceNotAvailableException;
import com.android.tradefed.device.ITestDevice;
import com.android.tradefed.result.FileInputStreamSource;
import com.android.tradefed.result.LogDataType;
import com.android.tradefed.testtype.DeviceJUnit4ClassRunner.TestLogData;
import com.android.tradefed.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

/**
 * Helper class that runs the metric instrumentations on a test device.
 */
class MetricsRunner {

    private final ITestDevice testDevice;
    private final String deviceParentDirectory;
    private final TestLogData logs;
    private final String timestampedLabel;

    /**
     * Creates a helper using the given {@link ITestDevice}, uploading heap dumps to the given
     * {@link TestLogData}.
     */
    static MetricsRunner create(ITestDevice testDevice, TestLogData logs)
            throws DeviceNotAvailableException {
        String deviceParentDirectory =
                testDevice.executeShellCommand("echo -n ${EXTERNAL_STORAGE}");
        return new MetricsRunner(testDevice, deviceParentDirectory, logs);
    }

    private MetricsRunner(
            ITestDevice testDevice, String deviceParentDirectory, TestLogData logs) {
        this.testDevice = testDevice;
        this.deviceParentDirectory = deviceParentDirectory;
        this.logs = logs;
        this.timestampedLabel = "LibcoreHeapMetricsTest-" + getCurrentTimeIso8601();
    }

    /**
     * Contains the results of running the instrumentation.
     */
    static class Result {

        private final AhatSnapshot afterDump;
        private final int beforeTotalPssKb;
        private final int afterTotalPssKb;

        private Result(
                AhatSnapshot beforeDump, AhatSnapshot afterDump,
                int beforeTotalPssKb, int afterTotalPssKb) {
            Diff.snapshots(afterDump, beforeDump);
            this.beforeTotalPssKb = beforeTotalPssKb;
            this.afterTotalPssKb = afterTotalPssKb;
            this.afterDump = afterDump;
        }

        /**
         * Returns the parsed form of the heap dump captured when the instrumentation starts.
         */
        AhatSnapshot getBeforeDump() {
            return afterDump.getBaseline();
        }

        /**
         * Returns the parsed form of the heap dump captured after the instrumentation action has
         * been executed. The first heap dump will be set as the baseline for this second one.
         */
        AhatSnapshot getAfterDump() {
            return afterDump;
        }

        /**
         * Returns the PSS measured when the instrumentation starts, in kB.
         */
        int getBeforeTotalPssKb() {
            return beforeTotalPssKb;
        }

        /**
         * Returns the PSS measured after the instrumentation action has been executed, in kB.
         */
        int getAfterTotalPssKb() {
            return afterTotalPssKb;
        }
    }

    /**
     * Runs all the instrumentation and fetches the metrics.
     *
     * @param action The name of the action to run, to be sent as an argument to the instrumentation
     * @return The combined results of the instrumentations.
     */
    Result runAllInstrumentations(String action)
            throws DeviceNotAvailableException, IOException, HprofFormatException {
        String relativeDirectoryName = String.format("%s-%s", timestampedLabel, action);
        String deviceDirectoryName =
                String.format("%s/%s", deviceParentDirectory, relativeDirectoryName);
        testDevice.executeShellCommand(String.format("mkdir %s", deviceDirectoryName));
        try {
            runInstrumentation(
                    action, relativeDirectoryName, deviceDirectoryName,
                    "libcore.heapdumper/.HeapDumpInstrumentation");
            runInstrumentation(
                    action, relativeDirectoryName, deviceDirectoryName,
                    "libcore.heapdumper/.PssInstrumentation");
            AhatSnapshot beforeDump = fetchHeapDump(deviceDirectoryName, "before.hprof", action);
            AhatSnapshot afterDump = fetchHeapDump(deviceDirectoryName, "after.hprof", action);
            int beforeTotalPssKb = fetchTotalPssKb(deviceDirectoryName, "before.pss.txt");
            int afterTotalPssKb = fetchTotalPssKb(deviceDirectoryName, "after.pss.txt");
            return new Result(beforeDump, afterDump, beforeTotalPssKb, afterTotalPssKb);
        } finally {
            testDevice.executeShellCommand(String.format("rm -r %s", deviceDirectoryName));
        }
    }

    /**
     * Runs a given instrumentation.
     *
     * <p>After the instrumentation has been run, checks for any reported errors and throws a
     * {@link ApplicationException} if any are found.
     *
     * @param action The name of the action to run, to be sent as an argument to the instrumentation
     * @param relativeDirectoryName The relative directory name for files on the device, to be sent
     *     as an argument to the instrumentation
     * @param deviceDirectoryName The absolute directory name for files on the device
     * @param apk The name of the APK, in the form {@code test_package/runner_class}
     */
    private void runInstrumentation(
            String action, String relativeDirectoryName, String deviceDirectoryName, String apk)
            throws DeviceNotAvailableException, IOException {
        String command = String.format(
                "am instrument -w -e dumpdir %s -e action %s  %s",
                relativeDirectoryName, action, apk);
        testDevice.executeShellCommand(command);
        checkForErrorFile(deviceDirectoryName);
    }

    /**
     * Looks for a file called {@code error} in the named device directory, and throws an
     * {@link ApplicationException} using the first line of that file as the message if found.
     */
    private void checkForErrorFile(String deviceDirectoryName)
            throws DeviceNotAvailableException, IOException {
        String[] deviceDirectoryContents =
                testDevice.executeShellCommand("ls " + deviceDirectoryName).split("\\s");
        for (String deviceFileName : deviceDirectoryContents) {
            if (deviceFileName.equals("error")) {
                throw new ApplicationException(readErrorFile(deviceDirectoryName));
            }
        }
    }

    /**
     * Returns the first line read from a file called {@code error} on the device in the named
     * directory.
     *
     * <p>The file is pulled into a temporary location on the host, and deleted after reading.
     */
    private String readErrorFile(String deviceDirectoryName)
            throws IOException, DeviceNotAvailableException {
        File file = testDevice.pullFile(String.format("%s/error", deviceDirectoryName));
        if (file == null) {
            throw new RuntimeException(
                    "Failed to pull error log from directory " + deviceDirectoryName);
        }
        try {
            return FileUtil.readStringFromFile(file);
        } finally {
            file.delete();
        }
    }

    /**
     * Returns an {@link AhatSnapshot} parsed from an {@code hprof} file on the device at the
     * given directory and relative filename.
     *
     * <p>The file is pulled into a temporary location on the host, and deleted after reading.
     * It is also logged via {@link TestLogData} under a name formed from the action and the
     * relative filename (e.g. {@code noop-before.hprof}).
     */
    private AhatSnapshot fetchHeapDump(
            String deviceDirectoryName, String relativeDumpFilename, String action)
            throws DeviceNotAvailableException, IOException, HprofFormatException {
        String deviceFileName = String
                .format("%s/%s", deviceDirectoryName, relativeDumpFilename);
        File file = testDevice.pullFile(deviceFileName);
        if (file == null) {
            throw new RuntimeException("Failed to pull dump: " + deviceFileName);
        }
        try {
            logHeapDump(file, String.format("%s-%s", action, relativeDumpFilename));
            return Parser.parseHeapDump(file, new ProguardMap());
        } finally {
            file.delete();
        }
    }

    /**
     * Returns the total PSS in kB read from a stringified integer in a file on the device at the
     * given directory and relative filename.
     */
    private int fetchTotalPssKb(
            String deviceDirectoryName, String relativeFilename)
            throws DeviceNotAvailableException, IOException, HprofFormatException {
        String shellCommand = String.format("cat %s/%s", deviceDirectoryName, relativeFilename);
        String totalPssKbStr = testDevice.executeShellCommand(shellCommand);
        return Integer.parseInt(totalPssKbStr);
    }

    /**
     * Logs the heap dump from the given file via {@link TestLogData} with the given log
     * filename.
     */
    private void logHeapDump(File file, String logFilename) {
        try (FileInputStreamSource dataStream = new FileInputStreamSource(file)) {
            logs.addTestLog(logFilename, LogDataType.HPROF, dataStream);
        }
    }

    /**
     * Returns the ISO 8601 form of the current time in UTC, for use as a timestamp in filenames.
     * (Note that using UTC avoids an issue where the timezone indicator includes a + sign for the
     * offset, which triggers an issue with URL encoding in tradefed, which causes the calls to
     * {@code testDevice.pullFile()} to fail. See b/149018916.)
     */
    private static String getCurrentTimeIso8601() {
        return Instant.now().toString();
    }

    /**
     * An exception indicating that the activity on the device encountered an error which it
     * passed
     * back to the host.
     */
    private static class ApplicationException extends RuntimeException {

        private static final long serialVersionUID = 0;

        ApplicationException(String applicationError) {
            super("Error encountered running application on device: " + applicationError);
        }
    }
}
