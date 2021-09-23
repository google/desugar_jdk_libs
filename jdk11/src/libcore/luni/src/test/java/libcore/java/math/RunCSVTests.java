package libcore.java.math;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests functions in java.lang.Math
 * Looks for the filenames in csvFileNames in tests/resources
 * Tests functions and numbers found in those files.
 * Run: vogar --classpath out/target/common/obj/JAVA_LIBRARIES/core-tests-support_intermediates/javalib.jar
 * libcore/luni/src/test/java/libcore/java/math/RunCSVTests.java
 */
public class RunCSVTests extends CSVTest {
    /** Stores ulps of error allowed for each function, if not 1 ulp.*/
    private static final Map<String, Double> UlpMap;
    static {
        final HashMap<String, Double> funcUlps = new HashMap<String, Double>();
        funcUlps.put("sinh", 2.5);
        funcUlps.put("cosh", 2.5);
        funcUlps.put("tanh", 2.5);
        funcUlps.put("abs", 0.0);
        funcUlps.put("signum", 0.0);
        funcUlps.put("getExponent", 0.0);
        funcUlps.put("toRadians", 0.0);
        funcUlps.put("toDegrees", 0.0);
        funcUlps.put("sqrt", 0.0);
        funcUlps.put("ceil", 0.0);
        funcUlps.put("floor", 0.0);
        funcUlps.put("rint", 0.0);
        funcUlps.put("atan2", 2.0);
        funcUlps.put("round", 0.0);
        funcUlps.put("max", 0.0);
        funcUlps.put("min", 0.0);
        funcUlps.put("copySign", 0.0);
        funcUlps.put("nextAfter", 0.0);
        funcUlps.put("scalb", 0.0);
        UlpMap = Collections.unmodifiableMap(funcUlps);
    }

    public static final String[] csvFileNames = { "/math_tests.csv",
            "/math_important_numbers.csv", "/math_java_only.csv" };

    public void test_csv() throws Exception {
        this.TestCSVInputs(csvFileNames);
    }

    /**
     * Runs a standard single-input test using assertEquals.
     * Allows error based on UlpMap, but defaults to 1 ulp.
     */
    @Override
    void runTest(String func, double expectedOutput, double input, String extra)
            throws Exception {
        Class<Math> mathClass = Math.class;
        Method m = mathClass.getMethod(func, new Class[] { Double.TYPE });
        Object returnValue = m.invoke(null, input);

        double allowedError;
        if (UlpMap.containsKey(func)) {
            allowedError = UlpMap.get(func)*Math.ulp(expectedOutput);
        } else {
            allowedError = Math.ulp(expectedOutput);
        }

        try {
            assertEquals(extra + ": " + m + ": " + input + ": ", expectedOutput,
                         (double) returnValue, allowedError);
        } catch (ClassCastException e) {
            assertEquals(extra + ": " + m + ": " + input + ": ", (int) expectedOutput,
                         (int) returnValue, allowedError);
        }
    }

    /**
     * Runs a 2-input test using assertEquals.
     * Allows error based on UlpMap, but defaults to 1 ulp.
     */
    @Override
    void run2InputTest(String func, double expectedOutput, double input1,
            double input2, String extra) throws Exception {
        Class<Math> mathClass = Math.class;
        Method m;
        Object returnValue;
        if (func.equals("scalb")) {
            m = mathClass.getMethod(func, new Class[] { Double.TYPE, Integer.TYPE });
            returnValue = m.invoke(null, input1, (int) input2);
        } else {
            m = mathClass.getMethod(func, new Class[] { Double.TYPE, Double.TYPE });
            returnValue = m.invoke(null, input1, input2);
        }

        double allowedError;
        if (UlpMap.containsKey(func)) {
            allowedError = UlpMap.get(func)*Math.ulp(expectedOutput);
        } else {
            allowedError = Math.ulp(expectedOutput);
        }

        try {
            assertEquals(extra + ": " + m + ": ", expectedOutput, (double) returnValue,
                         allowedError);
        } catch (ClassCastException e) {
            assertEquals(extra + ": " + m + ": ", (int) expectedOutput, (int) returnValue,
                         allowedError);
        }
    }
}
