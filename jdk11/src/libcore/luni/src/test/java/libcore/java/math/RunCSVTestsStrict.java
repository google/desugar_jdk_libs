package libcore.java.math;

import java.lang.reflect.Method;

/**
 * Tests java.lang.StrictMath
 * Looks for the filenames in csvFileNames in tests/resources
 * Tests functions and numbers found in those files.
 * Run: vogar --classpath out/target/common/obj/JAVA_LIBRARIES/core-tests-support_intermediates/javalib.jar
 * libcore/luni/src/test/java/libcore/java/math/RunCSVTestsStrict.java
 */
public class RunCSVTestsStrict extends CSVTest {
    public static final String[] csvFileNames = { "/math_tests.csv",
            "/math_important_numbers.csv", "/math_java_only.csv" };

    public void test_csv() throws Exception {
        this.TestCSVInputs(csvFileNames);
    }

    /**
     * Runs single-input test using assertEquals.
     */
    @Override
    void runTest(String func, double expectedOutput, double input, String extra)
            throws Exception {
        Class<StrictMath> mathClass = StrictMath.class;
        Method m = mathClass.getMethod(func, new Class[] { Double.TYPE });
        Object returnValue = m.invoke(null, input);

        try {
            assertEquals(extra + ": " + m + ": " + input + ": ", expectedOutput,
                    (double) returnValue, 0D);
        } catch (ClassCastException e) {
            assertEquals(extra + ": " + m + ": " + input + ": ", (int) expectedOutput,
                    (int) returnValue, 0D);
        }
    }

    /**
     * Runs 2-input test using assertEquals.
     */
    @Override
    void run2InputTest(String func, double expectedOutput, double input1,
            double input2, String extra) throws Exception {
        Class<StrictMath> mathClass = StrictMath.class;
        Method m;
        Object returnValue;
        if (func.equals("scalb")) {
            m = mathClass.getMethod(func, new Class[] { Double.TYPE, Integer.TYPE });
            returnValue = m.invoke(null, input1, (int) input2);
        } else {
            m = mathClass.getMethod(func, new Class[] { Double.TYPE, Double.TYPE });
            returnValue = m.invoke(null, input1, input2);
        }

        try {
            assertEquals(extra + ": " + m + ": " , expectedOutput, (double) returnValue, 0D);
        } catch (ClassCastException e) {
            assertEquals(extra + ": " + m + ": ", (int) expectedOutput, (int) returnValue, 0D);
        }
    }
}
