/*
 * Copyright (C) 2014 The Android Open Source Project
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

package libcore.libcore.net.event;

import junit.framework.TestCase;

import libcore.net.event.NetworkEventDispatcher;
import libcore.net.event.NetworkEventListener;

/**
 * Tests for {@link NetworkEventDispatcher}.
 */
public class NetworkEventDispatcherTest extends TestCase {

  public void testGetInstance_isSingleton() {
    assertSame(NetworkEventDispatcher.getInstance(), NetworkEventDispatcher.getInstance());
  }

  public void testAddListener_null() throws Exception {
    NetworkEventDispatcher networkEventDispatcher = new NetworkEventDispatcher();
    try {
      networkEventDispatcher.addListener(null);
      fail();
    } catch (NullPointerException expected) {
    }
  }

  public void testOnNetworkConfigurationChanged_noListeners() throws Exception {
    NetworkEventDispatcher networkEventDispatcher = new NetworkEventDispatcher();
    networkEventDispatcher.dispatchNetworkConfigurationChange();
  }

  public void testFireNetworkEvent_oneListener() throws Exception {
    FakeNetworkEventListener listener = new FakeNetworkEventListener();
    NetworkEventDispatcher networkEventDispatcher = new NetworkEventDispatcher();
    networkEventDispatcher.addListener(listener);

    networkEventDispatcher.dispatchNetworkConfigurationChange();

    listener.assertNetworkConfigurationChangedEvent(1);
  }

  public void testRemoveEventListener() throws Exception {
    FakeNetworkEventListener listener = new FakeNetworkEventListener();
    NetworkEventDispatcher networkEventDispatcher = new NetworkEventDispatcher();
    networkEventDispatcher.addListener(listener);
    networkEventDispatcher.removeListener(listener);

    networkEventDispatcher.dispatchNetworkConfigurationChange();

    listener.assertNetworkConfigurationChangedEvent(0);
  }

  private static class FakeNetworkEventListener extends NetworkEventListener {

    private int networkConfigurationChangedCount;

    @Override
    public void onNetworkConfigurationChanged() {
      networkConfigurationChangedCount++;
    }

    public void assertNetworkConfigurationChangedEvent(int expectedCount) {
      assertEquals(expectedCount, networkConfigurationChangedCount);
    }
  }
}
