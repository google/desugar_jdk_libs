/* Licensed to the Apache Software Foundation (ASF) under one or more
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

package org.apache.harmony.tests.java.util.prefs;

import java.io.NotSerializableException;
import java.util.prefs.NodeChangeEvent;
import java.util.prefs.Preferences;
import junit.framework.TestCase;
import org.apache.harmony.testframework.serialization.SerializationTest;

/**
 *
 */
public class NodeChangeEventTest extends TestCase {

    NodeChangeEvent event;

    public void testConstructor() {
        event = new NodeChangeEvent(Preferences.systemRoot(), Preferences
                .userRoot());
        assertSame(Preferences.systemRoot(), event.getParent());
        assertSame(Preferences.userRoot(), event.getChild());
        assertSame(Preferences.systemRoot(), event.getSource());
    }

    public void testConstructorNullParam() {
        try {
            event = new NodeChangeEvent(null, Preferences.userRoot());
            fail();
        } catch (IllegalArgumentException e) {
        }

        event = new NodeChangeEvent(Preferences.systemRoot(), null);
        assertSame(Preferences.systemRoot(), event.getParent());
        assertNull(event.getChild());
        assertSame(Preferences.systemRoot(), event.getSource());
    }

    public void testSerialization() throws Exception {

        event = new NodeChangeEvent(Preferences.systemRoot(), null);

        try {
            SerializationTest.copySerializable(event);
            fail("No expected NotSerializableException");
        } catch (NotSerializableException e) {
        }
    }
}
