/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.harmony.tests.java.net;

import java.net.InetAddress;

import tests.support.Support_Configuration;

public class InetAddressThreadTest extends junit.framework.TestCase {

    private static boolean someoneDone[] = new boolean[2];

    protected static boolean threadedTestSucceeded;

    protected static String threadedTestErrorString;

    /**
     * This class is used to test inet_ntoa, gethostbyaddr and gethostbyname
     * functions in the VM to make sure they're threadsafe. getByName will cause
     * the gethostbyname function to be called. getHostName will cause the
     * gethostbyaddr to be called. getHostAddress will cause inet_ntoa to be
     * called.
     */
    static class threadsafeTestThread extends Thread {
        private String lookupName;

        private InetAddress testAddress;

        private int testType;

        /*
         * REP_NUM can be adjusted if desired. Since this error is
         * non-deterministic it may not always occur. Setting REP_NUM higher,
         * increases the chances of an error being detected, but causes the test
         * to take longer. Because the Java threads spend a lot of time
         * performing operations other than running the native code that may not
         * be threadsafe, it is quite likely that several thousand iterations
         * will elapse before the first error is detected.
         */
        private static final int REP_NUM = 20000;

        public threadsafeTestThread(String name, String lookupName,
                InetAddress testAddress, int type) {
            super(name);
            this.lookupName = lookupName;
            this.testAddress = testAddress;
            testType = type;
        }

        public void run() {
            try {
                String correctName = testAddress.getHostName();
                String correctAddress = testAddress.getHostAddress();
                long startTime = System.currentTimeMillis();

                synchronized (someoneDone) {
                }

                for (int i = 0; i < REP_NUM; i++) {
                    if (someoneDone[testType]) {
                        break;
                    } else if ((i % 25) == 0
                            && System.currentTimeMillis() - startTime > 240000) {
                        System.out
                                .println("Exiting due to time limitation after "
                                        + i + " iterations");
                        break;
                    }

                    InetAddress ia = InetAddress.getByName(lookupName);
                    String hostName = ia.getHostName();
                    String hostAddress = ia.getHostAddress();

                    // Intentionally not looking for exact name match so that
                    // the test works across different platforms that may or
                    // may not include a domain suffix on the hostname
                    if (!hostName.startsWith(correctName)) {
                        threadedTestSucceeded = false;
                        threadedTestErrorString = (testType == 0 ? "gethostbyname"
                                : "gethostbyaddr")
                                + ": getHostName() returned "
                                + hostName
                                + " instead of " + correctName;
                        break;
                    }
                    // IP addresses should match exactly
                    if (!correctAddress.equals(hostAddress)) {
                        threadedTestSucceeded = false;
                        threadedTestErrorString = (testType == 0 ? "gethostbyname"
                                : "gethostbyaddr")
                                + ": getHostName() returned "
                                + hostAddress
                                + " instead of " + correctAddress;
                        break;
                    }

                }
                someoneDone[testType] = true;
            } catch (Exception e) {
                threadedTestSucceeded = false;
                threadedTestErrorString = e.toString();
            }
        }
    }

    /**
     * java.net.InetAddress#getHostName()
     */
    public void test_getHostName() throws Exception {
        // Test for method java.lang.String java.net.InetAddress.getHostName()

        // Make sure there is no caching
        String originalPropertyValue = System
                .getProperty("networkaddress.cache.ttl");
        System.setProperty("networkaddress.cache.ttl", "0");

        // Test for threadsafety
        try {
            InetAddress lookup1 = InetAddress.getByName("localhost");
            assertEquals("127.0.0.1", lookup1.getHostAddress());
            InetAddress lookup2 = InetAddress.getByName("localhost");
            assertEquals("127.0.0.1", lookup2.getHostAddress());
            threadsafeTestThread thread1 = new threadsafeTestThread("1",
                    lookup1.getHostName(), lookup1, 0);
            threadsafeTestThread thread2 = new threadsafeTestThread("2",
                    lookup2.getHostName(), lookup2, 0);
            threadsafeTestThread thread3 = new threadsafeTestThread("3",
                    lookup1.getHostAddress(), lookup1, 1);
            threadsafeTestThread thread4 = new threadsafeTestThread("4",
                    lookup2.getHostAddress(), lookup2, 1);

            // initialize the flags
            threadedTestSucceeded = true;
            synchronized (someoneDone) {
                thread1.start();
                thread2.start();
                thread3.start();
                thread4.start();
            }
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            /* FIXME: comment the assertion below because it is platform/configuration dependent
             * Please refer to HARMONY-1664 (https://issues.apache.org/jira/browse/HARMONY-1664)
             * for details
             */
//            assertTrue(threadedTestErrorString, threadedTestSucceeded);
        } finally {
            // restore the old value of the property
            if (originalPropertyValue == null)
                // setting the property to -1 has the same effect as having the
                // property be null
                System.setProperty("networkaddress.cache.ttl", "-1");
            else
                System.setProperty("networkaddress.cache.ttl",
                        originalPropertyValue);
        }
    }
}
