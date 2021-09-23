package libcore.java.math;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Standard single-input test framework for csv math tests
 */
public abstract class CSVTest extends junit.framework.TestCase {
    /*
     * csv file should have the following format:
     * function,expected_output,input,extra_info
     * e.g. cos,-0x1.0000000000000p+0,0x1.921fb54442d18p+1,cos(pi)
     * for two input: function,expected_output,input1,input2,extra
     * vogar classpath: obj/JAVA_LIBRARIES/core-tests-support_intermediates/javalib.jar
     */

    /**
     * This is a set of functions in java.Math/StrictMath that take two inputs.
     * These functions will call run2InputTest; others will call runTest.
     */
    protected static final Set<String> twoInputFunctions;
    static {
        Set<String> twoInFunc = new HashSet<String>();
        twoInFunc.add("atan2");
        twoInFunc.add("copySign");
        twoInFunc.add("hypot");
        twoInFunc.add("IEEEremainder");
        twoInFunc.add("max");
        twoInFunc.add("min");
        twoInFunc.add("nextAfter");
        twoInFunc.add("pow");
        twoInFunc.add("scalb");
        twoInputFunctions = Collections.unmodifiableSet(twoInFunc);
    }

    void TestCSVInputs(String[] csvFileNames) throws Exception {
        int totalTests = 0;
        for (String csvFileName : csvFileNames) {
            String line = "";
            BufferedReader br = null;

            try {
                br = new BufferedReader(new InputStreamReader(
                        getClass().getResourceAsStream(csvFileName)));
                while ((line = br.readLine()) != null) {
                    if (line.charAt(0) != '#') {
                        String[] testCase = line.split(",");
                        runTest(testCase);
                        totalTests++;
                    }
                }
            } finally {
                if (br != null) {
                    br.close();
                }
            }
        }
        System.out.println("Completed running " + totalTests + " tests");
    }

    protected void runTest(String[] testCase) throws Exception {
        String function = testCase[0];
        double expectedOutput = Double.parseDouble(testCase[1]);
        double input = Double.parseDouble(testCase[2]);
        String extra = "";
        if (twoInputFunctions.contains(function)) {
            double input2 = Double.parseDouble(testCase[3]);
            if (testCase.length > 4) {
                extra = testCase[4];
            }
            run2InputTest(function, expectedOutput, input, input2, extra);
        } else {
            if (testCase.length > 3) {
                extra = testCase[3];
            }
            runTest(function, expectedOutput, input, extra);
        }
    }

    abstract void runTest(String func, double expectedOutput, double input,
            String extra) throws Exception;

    abstract void run2InputTest(String func, double expectedOutput, double input1, double input2, String extra) throws Exception;
}