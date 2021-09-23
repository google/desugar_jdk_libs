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

package libcore.heapdumper;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * An abstract base class for an {@link Instrumentation} that takes some measurement, performs some
 * action, and then takes the measurement again, all without launching any activities.
 *
 * <p>The metric to be collected is defined by the concrete subclass's implementation of
 * {@link #takeMeasurement}. The action to be performed is determined by an invocation argument.
 *
 * <p>The instrumentation should be invoked with two arguments:
 * <ul>
 *     <li>one called {@code dumpdir} which gives the name of a directory to put the dumps in,
 *     relative to the public external storage directory;
 *     <li>one called {@code action} which gives the name of an {@link Actions} value to run between
 *     the two measurements.
 * </ul>
 *
 * <p>If there is a problem, it will try to create a file called {@code error} in the output
 * directory, containing a failure message.
 */
public abstract class AbstractMetricInstrumentation extends Instrumentation {

    private static final String TAG = "AbstractMetricInstrumentation";

    private File mOutputDirectory;

    @Override
    public void onCreate(Bundle icicle) {
        mOutputDirectory = resolveOutputDirectory(icicle);
        try {
            Runnable mAction = loadAction(icicle);
            takeMeasurement("before");
            mAction.run();
            takeMeasurement("after");
        } catch (Exception e) {
            recordException(e);
        }
        super.onCreate(icicle);
        finish(Activity.RESULT_OK, new Bundle());
    }

    /**
     * Takes a measurement, including the given label in the filename of the output.
     */
    protected abstract void takeMeasurement(String label) throws IOException;

    /**
     * Returns a {@link File} in the correct output directory with the given relative filename.
     */
    protected final File resolveRelativeOutputFilename(String relativeOutputFilename) {
        return new File(mOutputDirectory, relativeOutputFilename);
    }

    /**
     * Does its best to force as much garbage as possible to be collected.
     */
    protected final void tryRemoveGarbage() {
        Runtime runtime = Runtime.getRuntime();
        // Do a GC run.
        runtime.gc();
        // Run finalizers for any objects pending finalization.
        runtime.runFinalization();
        // Do another GC run, for objects made eligible for collection by the finalization process.
        runtime.gc();
    }

    /**
     * Resolves the directory to use for output, based on the arguments in the bundle.
     */
    private static File resolveOutputDirectory(Bundle icicle) {
        String relativeDirectoryName = icicle.getString("dumpdir");
        if (relativeDirectoryName == null) {
            throw new IllegalArgumentException(
                    "Instrumentation invocation missing dumpdir argument");
        }
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            throw new IllegalStateException("External storage unavailable");
        }
        File dir = Environment.getExternalStoragePublicDirectory(relativeDirectoryName);
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException(
                    "Instrumentation invocation's dumpdir argument is not a directory: "
                            + dir.getAbsolutePath());
        }
        return dir;
    }

    /**
     * Returns the {@link Runnable} to run between measurements, based on the arguments in the
     * bundle.
     */
    private static Runnable loadAction(Bundle icicle) {
        String name = icicle.getString("action");
        if (name == null) {
            throw new IllegalArgumentException(
                    "Instrumentation invocation missing action argument");
        }
        return Actions.valueOf(name);
    }

    /**
     * Write an {@code error} file into {@link #mOutputDirectory} containing the message of the
     * exception.
     */
    private void recordException(Exception e) {
        Log.e(TAG, "Exception while taking measurements", e);
        String contents = e.getMessage();
        File errorFile = new File(mOutputDirectory, "error");
        try {
            try (OutputStream errorStream = new FileOutputStream(errorFile)) {
                errorStream.write(contents.getBytes("UTF-8"));
            }
        } catch (IOException e2) {
            throw new RuntimeException("Exception writing error file!", e2);
        }
    }
}
