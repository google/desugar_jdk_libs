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
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * A specialization of {@link AbstractMetricInstrumentation} where the measurement is taken by
 * dumping the process's heaps.
 */
public class HeapDumpInstrumentation extends AbstractMetricInstrumentation {

    private static final String TAG = "HeapDumpInstrumentation";

    @Override
    protected void takeMeasurement(String label) throws IOException {
        File dumpFile = resolveRelativeOutputFilename(label + ".hprof");
        tryRemoveGarbage();
        Debug.dumpHprofData(dumpFile.getCanonicalPath());
        Log.i(TAG, "Wrote to heap dump to " + dumpFile.getCanonicalPath());
    }
}
