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

import android.app.ActivityManager;
import android.os.Debug;
import android.os.Process;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A specialization of {@link AbstractMetricInstrumentation} where the measurement taken is the
 * process's total PSS in kB.
 */
public class PssInstrumentation extends AbstractMetricInstrumentation {

    private static final String TAG = "PssInstrumentation";

    @Override
    protected void takeMeasurement(String label) throws IOException {
        ActivityManager activityManager = getContext().getSystemService(ActivityManager.class);
        tryRemoveGarbage();
        int totalPssKb = Math.toIntExact(Debug.getPss());
        File output = resolveRelativeOutputFilename(label + ".pss.txt");
        Charset cs = StandardCharsets.UTF_8;
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(output), cs)) {
            writer.append(Integer.toString(totalPssKb));
        }
        Log.i(TAG, "Wrote to total PSS of " + totalPssKb + " kB to " + output.getCanonicalPath());
    }
}
