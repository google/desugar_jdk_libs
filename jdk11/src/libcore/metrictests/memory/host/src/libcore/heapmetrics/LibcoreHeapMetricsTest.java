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

import com.android.ahat.heapdump.AhatHeap;
import com.android.ahat.heapdump.AhatInstance;
import com.android.ahat.heapdump.AhatSnapshot;
import com.android.ahat.heapdump.Size;
import com.android.tradefed.device.DeviceNotAvailableException;
import com.android.tradefed.device.ITestDevice;
import com.android.tradefed.testtype.DeviceJUnit4ClassRunner;
import com.android.tradefed.testtype.DeviceJUnit4ClassRunner.TestLogData;
import com.android.tradefed.testtype.DeviceJUnit4ClassRunner.TestMetrics;
import com.android.tradefed.testtype.IDeviceTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.EnumMap;
import java.util.Map;
import libcore.heapmetrics.HeapCategorization.HeapCategory;

/**
 * Tests that gather metrics about zygote+image heap and about the impact of core library calls on
 * app heap.
 */
@RunWith(DeviceJUnit4ClassRunner.class)
public class LibcoreHeapMetricsTest implements IDeviceTest {

    @Rule public TestMetrics metrics = new TestMetrics();
    @Rule public TestLogData logs = new TestLogData();

    private ITestDevice testDevice;
    private MetricsRunner metricsRunner;

    @Override
    public void setDevice(ITestDevice device) {
        testDevice = device;
    }

    @Override
    public ITestDevice getDevice() {
        return testDevice;
    }

    @Before
    public void initializeHeapDumperRunner() throws DeviceNotAvailableException {
        metricsRunner = MetricsRunner.create(testDevice, logs);
    }

    @Test
    public void measureNoop() throws Exception {
        MetricsRunner.Result result = metricsRunner.runAllInstrumentations("NOOP");
        AhatSnapshot beforeDump = result.getBeforeDump();
        AhatSnapshot afterDump = result.getAfterDump();
        recordHeapMetrics(beforeDump, "zygoteSize", "zygote");
        recordHeapMetrics(beforeDump, "imageSize", "image");
        Map<HeapCategory, Size> zygoteAndImageSizesByCategory = HeapCategorization
                .of(beforeDump, beforeDump.getHeap("zygote"), beforeDump.getHeap("image"))
                .sizesByCategory();
        for (Map.Entry<HeapCategory, Size> entry : zygoteAndImageSizesByCategory.entrySet()) {
            recordSizeMetric(entry.getKey().metricName("zygoteAndImage_"), entry.getValue());
        }
        recordBeforeAndAfterAppHeapMetrics(beforeDump, afterDump);
        recordBytesMetric("beforeTotalPss", result.getBeforeTotalPssKb() * 1024L);
        recordBytesMetric(
                "deltaTotalPss",
                (result.getAfterTotalPssKb() - result.getBeforeTotalPssKb()) * 1024L);
    }

    @Test
    public void measureCollatorRootLocale() throws Exception {
        MetricsRunner.Result result = metricsRunner.runAllInstrumentations("COLLATOR_ROOT_LOCALE");
        recordBeforeAndAfterAppHeapMetrics(result.getBeforeDump(), result.getAfterDump());
    }

    @Test
    public void measureCollatorEnUsLocale() throws Exception {
        MetricsRunner.Result result = metricsRunner.runAllInstrumentations("COLLATOR_EN_US_LOCALE");
        recordBeforeAndAfterAppHeapMetrics(result.getBeforeDump(), result.getAfterDump());
    }

    @Test
    public void measureCollatorKoreanLocale() throws Exception {
        MetricsRunner.Result result =
                metricsRunner.runAllInstrumentations("COLLATOR_KOREAN_LOCALE");
        recordBeforeAndAfterAppHeapMetrics(result.getBeforeDump(), result.getAfterDump());
    }

    @Test
    public void measureRegexes() throws Exception {
        MetricsRunner.Result result = metricsRunner.runAllInstrumentations("REGEX");
        recordBeforeAndAfterAppHeapMetrics(result.getBeforeDump(), result.getAfterDump());
    }

    private void recordHeapMetrics(AhatSnapshot dump, String metricPrefix, String heapName) {
        AhatHeap heap = dump.getHeap(heapName);
        recordSizeMetric(metricPrefix, heap.getSize());
        Map<Reachability, Size> sizesByReachability = sizesByReachability(dump, heap);
        for (Reachability reachability : Reachability.values()) {
            recordSizeMetric(
                    reachability.metricName(metricPrefix), sizesByReachability.get(reachability));
        }
    }

    private void recordBeforeAndAfterAppHeapMetrics(
            AhatSnapshot beforeDump,
            AhatSnapshot afterDump) {
        AhatHeap beforeHeap = beforeDump.getHeap("app");
        AhatHeap afterHeap = afterDump.getHeap("app");
        recordSizeMetric("beforeAppSize", beforeHeap.getSize());
        recordSizeDeltaMetric("deltaAppSize", beforeHeap.getSize(), afterHeap.getSize());
        Map<Reachability, Size> beforeSizesByReachability =
                sizesByReachability(beforeDump, beforeHeap);
        Map<Reachability, Size> afterSizesByReachability = sizesByReachability(afterDump, afterHeap);
        for (Reachability reachability : Reachability.values()) {
            recordSizeMetric(
                    reachability.metricName("beforeAppSize"),
                    beforeSizesByReachability.get(reachability));
            recordSizeDeltaMetric(
                    reachability.metricName("deltaAppSize"),
                    beforeSizesByReachability.get(reachability),
                    afterSizesByReachability.get(reachability));
        }
    }

    private void recordSizeMetric(String name, Size size) {
        recordBytesMetric(name, size.getSize());
    }

    private void recordSizeDeltaMetric(String name, Size before, Size after) {
        recordBytesMetric(name, after.getSize() - before.getSize());
    }

    private void recordBytesMetric(String name, long bytes) {
        metrics.addTestMetric(name, Long.toString(bytes));
    }

    private static Map<Reachability, Size> sizesByReachability(AhatSnapshot dump, AhatHeap heap) {
        EnumMap<Reachability, Size> map = new EnumMap<>(Reachability.class);
        for (Reachability reachability : Reachability.values()) {
            map.put(reachability, Size.ZERO);
        }
        for (AhatInstance instance : dump.getRooted()) {
            Reachability reachability = Reachability.ofInstance(instance);
            Size size = instance.getRetainedSize(heap);
            map.put(reachability, map.get(reachability).plus(size));
        }
        return map;
    }
}
