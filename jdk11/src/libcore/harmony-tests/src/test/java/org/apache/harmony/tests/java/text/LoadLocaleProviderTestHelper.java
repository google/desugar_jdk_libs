/* 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.harmony.tests.java.text;

import java.net.URL;
import java.net.URLClassLoader;

public abstract class LoadLocaleProviderTestHelper implements Runnable {
    private Throwable throwable;

    public LoadLocaleProviderTestHelper(URL[] classpathes)
            throws InterruptedException {
        URLClassLoader loader = new URLClassLoader(classpathes);
        Thread thread = new Thread(this);
        thread.setContextClassLoader(loader);
        thread.start();
        thread.join();
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void run() {
        try {
            test();
        } catch (Throwable t) {
            throwable = t;
        }
    }

    public abstract void test();
}
